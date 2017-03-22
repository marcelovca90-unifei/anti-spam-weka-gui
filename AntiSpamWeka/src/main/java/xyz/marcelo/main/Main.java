package xyz.marcelo.main;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import xyz.marcelo.common.DataSetMetadata;
import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.MethodEvaluation;
import xyz.marcelo.helper.CliHelper;
import xyz.marcelo.helper.FilterHelper;
import xyz.marcelo.helper.FormatHelper;
import xyz.marcelo.helper.InputOutputHelper;
import xyz.marcelo.helper.PrimeHelper;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        // change global setting for Logger instances to WARNING level
        Arrays.stream(LogManager.getLogManager().getLogger("").getHandlers()).forEach(h -> h.setLevel(Level.WARNING));

        // initialize the CLI helper with the provided arguments
        CliHelper.initialize(args);

        // print the parsed, args-provided parameters
        CliHelper.printConfiguration();

        // individually save the parsed, args-provided parameters
        Set<DataSetMetadata> metadata = CliHelper.getDataSetsMetadata();
        List<MethodConfiguration> methods = CliHelper.getMethods();
        Integer numberOfRuns = CliHelper.getNumberOfRuns();
        Boolean shouldSkipTrain = CliHelper.shouldSkipTrain();
        Boolean shouldSkipTest = CliHelper.shouldSkipTest();
        Boolean shouldIncludeEmptyInstances = CliHelper.shouldIncludeEmptyInstances();
        Boolean shouldSaveModel = CliHelper.shouldSaveModel();

        // objects that will hold all kinds of data sets
        Instances dataSet = null, trainingSet = null, testingSet = null, emptySet = null;

        for (MethodConfiguration method : methods)
        {
            FormatHelper.printHeader();

            for (DataSetMetadata dataSetMetadata : metadata)
            {
                // import data set
                String hamFilePath = dataSetMetadata.getFolder() + File.separator + InputOutputHelper.TAG_HAM;
                String spamFilePath = dataSetMetadata.getFolder() + File.separator + InputOutputHelper.TAG_SPAM;
                dataSet = InputOutputHelper.loadInstancesFromFile(hamFilePath, spamFilePath);

                // apply attribute and instance filters to the data set
                dataSet = FilterHelper.applyAttributeFilter(dataSet);
                dataSet = FilterHelper.applyInstanceFilter(dataSet);

                // build empty patterns set
                if (shouldIncludeEmptyInstances)
                {
                    emptySet = InputOutputHelper.createEmptyInstances(dataSet.numAttributes() - 1, dataSetMetadata.getEmptyHamCount(),
                            dataSetMetadata.getEmptySpamCount());
                }

                // initialize random number generator
                Random random = new Random();

                // build the classifier for the given configuration
                Classifier classifier = MethodConfiguration.buildClassifierFor(method);

                // create the object that will hold the overall evaluations result
                MethodEvaluation timedEvaluation = new MethodEvaluation(dataSetMetadata.getFolder(), method);

                // reset prime helper index
                PrimeHelper.reset();

                for (int run = 0; run < numberOfRuns; run++)
                {
                    // set random number generator's seed
                    random.setSeed(PrimeHelper.getNextPrime());

                    // randomize the data set to assure balance and avoid biasing
                    dataSet.randomize(random);

                    // build train and test sets
                    double trainPercentage = 0.6;
                    int trainingSetSize = (int) Math.round(dataSet.numInstances() * trainPercentage);
                    int testingSetSize = dataSet.numInstances() - trainingSetSize;
                    trainingSet = new Instances(dataSet, 0, trainingSetSize);
                    testingSet = new Instances(dataSet, trainingSetSize, testingSetSize);

                    // add empty patterns to test set
                    if (shouldIncludeEmptyInstances) testingSet.addAll(emptySet);

                    // build the classifier for the given configuration
                    Classifier innerClassifier;
                    boolean couldLoadClassifierFromFile;
                    String classifierFilename = dataSetMetadata.getFolder() + File.separator
                            + InputOutputHelper.buildClassifierFilename(method, trainPercentage, PrimeHelper.getCurrentPrime());

                    // tries to de-serialize the classifier from a file with the default filename
                    try
                    {
                        innerClassifier = InputOutputHelper.loadModelFromFile(classifierFilename);
                        couldLoadClassifierFromFile = true;
                    }
                    // if the deserialization failed, clone the base classifier
                    catch (Exception e)
                    {
                        innerClassifier = AbstractClassifier.makeCopy(classifier);
                        couldLoadClassifierFromFile = false;
                    }

                    // create the object that will hold the single evaluation result
                    Evaluation innerEvaluation = new Evaluation(testingSet);

                    // setup the classifier evaluation
                    timedEvaluation.setClassifier(innerClassifier);
                    timedEvaluation.setEvaluation(innerEvaluation);

                    // if the classifier could not be loaded from the filesystem, then train it
                    if (!shouldSkipTrain || !couldLoadClassifierFromFile) timedEvaluation.train(trainingSet);

                    // evaluate the classifier
                    if (!shouldSkipTest) timedEvaluation.test(testingSet);

                    // compute and log the partial results for this configuration
                    FormatHelper.computeResults(timedEvaluation);
                    FormatHelper.summarizeResults(false, true);

                    // persist the classifier, if specified in args
                    if (shouldSaveModel) InputOutputHelper.saveModelToFile(classifierFilename, innerClassifier);
                }

                // log the final results for this configuration
                FormatHelper.summarizeResults(true, true);
            }

            FormatHelper.printFooter();
        }
    }
}
