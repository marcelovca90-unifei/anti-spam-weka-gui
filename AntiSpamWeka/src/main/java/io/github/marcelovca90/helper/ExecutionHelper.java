package io.github.marcelovca90.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.FileWriter;

import io.github.marcelovca90.common.Constants.MessageType;
import io.github.marcelovca90.common.DataSetMetadata;
import io.github.marcelovca90.common.FilterConfiguration;
import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.common.MethodEvaluation;
import io.github.marcelovca90.gui.UserInterface;
import io.github.marcelovca90.helper.MailHelper.CryptoProtocol;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class ExecutionHelper
{
    // anti spam settings
    public static Set<DataSetMetadata> metadata;
    public static List<MethodConfiguration> methods;
    public static int numberOfRuns;

    // run settings
    public static boolean skipTrain;
    public static boolean skipTest;
    public static boolean shrinkFeatures;
    public static boolean balanceClasses;
    public static boolean includeEmpty;
    public static boolean removeOutliers;
    public static boolean saveArff;
    public static boolean saveModel;
    public static boolean saveSets;
    public static boolean emailResults;

    // e-mail settings
    public static String sender;
    public static String recipient;
    public static String server;
    public static CryptoProtocol protocol;
    public static String username;
    public static String password;

    public static boolean isRunning = false;

    // prepare the data sets and empty counts to be used in training/testing
    public static void setUpMetadata(String metadataPath) throws IOException
    {
        metadata = MetaHelper
            .getInputOutputHelper()
            .loadDataSetsMetadataFromFile(metadataPath);
    }

    // prepare the machine learning methods to be trained/tested
    public static void setUpMethods(Set<String> methodNames)
    {
        methods = methodNames
            .stream()
            .map(name -> MethodConfiguration.valueOf(name))
            .collect(Collectors.toList());
    }

    // run training/classification for the configured methods, metadata and no. runs
    public static void run()
    {
        try
        {
            // indicates that the training/classification is running
            isRunning = true;

            // objects that will hold all kinds of data sets
            Instances dataSet = null;
            Instances trainingSet = null;
            Instances testingSet = null;
            Instances emptySet = null;

            // configure basic loggers (console and file, i.e. logs/trace.log)
            String traceLogFilename = "logs" + File.separator + "trace.log";
            Configurator
                .currentConfig()
                .writer(new ConsoleWriter(), Level.DEBUG)
                .addWriter(new FileWriter(traceLogFilename, true, true), Level.TRACE)
                .writingThread(true)
                .activate();

            // calculate and set the progress bar maximum value
            setUpProgressBar();

            for (MethodConfiguration method : methods)
            {
                // configure result loggers (i.e. logs/${METHOD}.log)
                String debugLogFilename = "logs" + File.separator + method.name() + ".log";
                Configurator
                    .currentConfig()
                    .addWriter(new FileWriter(debugLogFilename, true, true), Level.DEBUG)
                    .activate();

                MetaHelper.getExperimentHelper().printHeader();

                for (DataSetMetadata metadata : metadata)
                {
                    String folder = metadata.getFolder();
                    int numberOfTotalFeatures;
                    int numberOfActualFeatures;

                    // initialize random number generator
                    MetaHelper.getRandomHelper().reset();

                    String arffFilePath = folder + File.separator + "data.arff";
                    if (Paths.get(arffFilePath).toFile().exists())
                    {
                        // count the number of total features by looking at the file name
                        dataSet = MetaHelper.getInputOutputHelper().loadInstancesFromArffFile(arffFilePath);

                        // count the number of total features by looking at the data set
                        numberOfTotalFeatures = Integer.valueOf(folder.substring(folder.lastIndexOf(folder.contains("\\") ? "\\" : "/") + 1));
                    }
                    else
                    {
                        // import data sets for each class
                        String hamFilePath = folder + File.separator + MessageType.HAM.name().toLowerCase();
                        Instances hamDataSet = MetaHelper.getInputOutputHelper().loadInstancesFromRawFile(hamFilePath, MessageType.HAM);
                        String spamFilePath = folder + File.separator + MessageType.SPAM.name().toLowerCase();
                        Instances spamDataSet = MetaHelper.getInputOutputHelper().loadInstancesFromRawFile(spamFilePath, MessageType.SPAM);

                        // match class cardinalities so data set becomes balanced
                        MetaHelper.getInputOutputHelper().matchCardinalities(hamDataSet, spamDataSet);

                        // merge ham and spam data sets
                        dataSet = MetaHelper.getInputOutputHelper().mergeInstances(hamDataSet, spamDataSet);

                        // count the number of total features by looking at the data set
                        numberOfTotalFeatures = dataSet.numAttributes() - 1;
                    }

                    // apply attribute and instance filters to the data set, if specified
                    if (shrinkFeatures)
                        dataSet = FilterConfiguration.buildAndApply(dataSet, FilterConfiguration.AttributeFilter.CfsSubsetEval_MultiObjectiveEvolutionarySearch);
                    if (balanceClasses)
                        dataSet = FilterConfiguration.buildAndApply(dataSet, FilterConfiguration.InstanceFilter.ClassBalancer);

                    // count the number of actual features by looking at the data set
                    numberOfActualFeatures = dataSet.numAttributes() - 1;

                    // save whole set to .arff file, if specified
                    if (saveArff)
                        MetaHelper.getInputOutputHelper().saveInstancesToArffFile(dataSet, folder + File.separator + "data.arff");

                    // build empty patterns set, if specified
                    if (includeEmpty)
                        emptySet = MetaHelper.getInputOutputHelper().createEmptyInstances(dataSet.numAttributes() - 1, metadata.getEmptyHamCount(), metadata.getEmptySpamCount());

                    // build the classifier for the given configuration
                    Classifier baseClassifier = MethodConfiguration.buildClassifierFor(method);

                    // create the object that will hold the overall evaluations result
                    MethodEvaluation baseEvaluation = new MethodEvaluation(folder, method);

                    // reset run results keeper
                    MetaHelper.getExperimentHelper().clearResultHistory();

                    for (int run = 0; run < numberOfRuns; run++)
                    {
                        // set random number generator's seed
                        MetaHelper.getRandomHelper().update();

                        // randomize the data set to assure balance and avoid biasing
                        dataSet.randomize(MetaHelper.getRandomHelper().getRandom());

                        // build train and test sets
                        double splitPercent = method.getSplitPercent();
                        int trainingSetSize = (int) Math.round(dataSet.numInstances() * splitPercent);
                        int testingSetSize = dataSet.numInstances() - trainingSetSize;
                        trainingSet = new Instances(dataSet, 0, trainingSetSize);
                        testingSet = new Instances(dataSet, trainingSetSize, testingSetSize);

                        // add empty patterns to test set
                        if (includeEmpty)
                            testingSet.addAll(emptySet);

                        // save the data sets to .csv files, if specified
                        if (saveSets)
                        {
                            MetaHelper.getInputOutputHelper().saveInstancesToArffFile(trainingSet, folder + File.separator + "training.arff");
                            MetaHelper.getInputOutputHelper().saveInstancesToArffFile(testingSet, folder + File.separator + "testing.arff");
                        }

                        // if the training should be skipped, then read the classifier from the filesystem; else, clone and train the base classifier
                        Classifier classifier = AbstractClassifier.makeCopy(baseClassifier);

                        // create the object that will hold the single evaluation result
                        Evaluation evaluation = new Evaluation(testingSet);

                        // setup the classifier evaluation
                        baseEvaluation.setClassifier(classifier);
                        baseEvaluation.setEvaluation(evaluation);
                        baseEvaluation.setNumberOfTotalFeatures(numberOfTotalFeatures);
                        baseEvaluation.setNumberOfActualFeatures(numberOfActualFeatures);

                        // if the classifier could not be loaded from the filesystem, then train it
                        if (!skipTrain)
                            baseEvaluation.train(trainingSet);

                        // if the testing should not be skipped
                        if (!skipTest)
                        {
                            // evaluate the classifier
                            baseEvaluation.test(testingSet);

                            // compute and log the partial results for this configuration
                            MetaHelper.getExperimentHelper().computeSingleRunResults(baseEvaluation);
                            MetaHelper.getExperimentHelper().summarizeResults(baseEvaluation, false, true);

                            // if at the end of last run, detect and remove outliers (if specified); this may lead to additional runs
                            if (removeOutliers && run == (numberOfRuns - 1))
                                run -= MetaHelper.getExperimentHelper().detectAndRemoveOutliers();
                        }

                        // persist the classifier, if specified in args
                        if (saveModel)
                        {
                            String classifierFilename = MetaHelper.getInputOutputHelper().buildClassifierFilename(folder, method, splitPercent);
                            MetaHelper.getInputOutputHelper().saveModelToFile(classifierFilename, classifier);
                        }

                        // increment the progress bar current value
                        updateProgressBar();
                    }

                    // log the final results for this configuration
                    if (numberOfRuns > 0 && !skipTest)
                        MetaHelper.getExperimentHelper().summarizeResults(baseEvaluation, true, true);
                }

                if (emailResults)
                {
                    String subject = String.format("[ASW] %s - %s", LocalDateTime.now(), debugLogFilename.substring(debugLogFilename.lastIndexOf(File.separator) + 1));

                    BasicFileAttributes fileAttributes = Files.readAttributes(Paths.get(debugLogFilename), BasicFileAttributes.class);

                    StringBuilder text = new StringBuilder();
                    text.append("creationTime: " + fileAttributes.creationTime() + "\n");
                    text.append("lastAccessTime: " + fileAttributes.lastAccessTime() + "\n");
                    text.append("lastModifiedTime: " + fileAttributes.lastModifiedTime() + "\n");
                    text.append("isDirectory: " + fileAttributes.isDirectory() + "\n");
                    text.append("isOther: " + fileAttributes.isOther() + "\n");
                    text.append("isRegularFile: " + fileAttributes.isRegularFile() + "\n");
                    text.append("isSymbolicLink: " + fileAttributes.isSymbolicLink() + "\n");
                    text.append("size: " + fileAttributes.size() + "\n");

                    MailHelper.sendMail(protocol, username, password, server, sender, recipient, subject, text.toString(), debugLogFilename);
                }
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
    }

    // calculate and set the progress bar maximum value
    private static void setUpProgressBar()
    {
        SwingUtilities.invokeLater(() ->
        {
            UserInterface.progressBar.setMinimum(0);
            UserInterface.progressBar.setMaximum(methods.size() * metadata.size() * numberOfRuns);
        });
    }

    // increment the progress bar current value
    private static void updateProgressBar()
    {
        SwingUtilities.invokeLater(() ->
        {
            UserInterface.progressBar.setValue(UserInterface.progressBar.getValue() + 1);
        });
    }

    // used to suppress the default public constructor
    private ExecutionHelper()
    {
    }
}
