package xyz.marcelo.main;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogManager;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.TimedEvaluation;
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

        // global and args-provided parameters
        List<String> folders = CliHelper.getFolders();
        List<MethodConfiguration> methods = CliHelper.getMethods();
        Integer numberOfRuns = CliHelper.getNumberOfRuns();
        Boolean shouldSkipTrain = CliHelper.shouldSkipTrain();
        Boolean shouldSkipTest = CliHelper.shouldSkipTest();
        Boolean shouldIncludeEmptyInstances = CliHelper.shouldIncludeEmptyInstances();

        // objects that will hold all kinds of data sets
        Instances dataSet = null, trainingSet = null, testingSet = null, emptySet = null;

        for (MethodConfiguration method : methods)
        {
            FormatHelper.printHeader();

            for (String folder : folders)
            {
                // import data set
                String hamFilePath = folder + File.separator + InputOutputHelper.TAG_HAM;
                String spamFilePath = folder + File.separator + InputOutputHelper.TAG_SPAM;
                dataSet = InputOutputHelper.loadInstancesFromFile(hamFilePath, spamFilePath);

                // check if attribute and instance filters should be applied to the data set
                boolean shouldApplyAttributeFilter = FilterHelper.shouldApplyAttributeFilter(folder);
                boolean shouldApplyInstanceFilter = FilterHelper.shouldApplyInstanceFilter(folder);
                dataSet = FilterHelper.applyFilters(dataSet, shouldApplyAttributeFilter, shouldApplyInstanceFilter);

                // build empty patterns set
                if (shouldIncludeEmptyInstances) emptySet = InputOutputHelper.createEmptyInstances(folder, dataSet.numAttributes() - 1);

                // initialize random number generator
                Random random = new Random();

                // build the classifier for the given configuration
                Classifier classifier = MethodConfiguration.buildClassifierFor(method);

                // create the object that will hold the overall evaluations result
                TimedEvaluation timedEvaluation = new TimedEvaluation(folder, method);

                // reset prime helper index
                PrimeHelper.reset();

                for (int run = 0; run < numberOfRuns; run++)
                {
                    // set random number generator's seed
                    random.setSeed(PrimeHelper.getNextPrime());

                    // randomize the data set to assure balance and avoid biasing
                    dataSet.randomize(random);

                    // build train and test sets
                    int trainingSetSize = (int) Math.round(dataSet.numInstances() * 0.6);
                    int testingSetSize = dataSet.numInstances() - trainingSetSize;
                    trainingSet = new Instances(dataSet, 0, trainingSetSize);
                    testingSet = new Instances(dataSet, trainingSetSize, testingSetSize);

                    // add empty patterns to test set
                    if (shouldIncludeEmptyInstances) testingSet.addAll(emptySet);

                    // build the classifier for the given configuration
                    Classifier innerClassifier = AbstractClassifier.makeCopy(classifier);

                    // create the object that will hold the single evaluation result
                    Evaluation innerEvaluation = new Evaluation(testingSet);

                    // evaluate the classifier
                    timedEvaluation.setClassifier(innerClassifier);
                    timedEvaluation.setEvaluation(innerEvaluation);
                    timedEvaluation.run(trainingSet, testingSet);

                    // compute and log the partial results for this configuration
                    FormatHelper.computeResults(timedEvaluation);
                    FormatHelper.summarizeResults(false);
                }

                // log the final results for this configuration
                FormatHelper.summarizeResults(true);
            }

            FormatHelper.printFooter();
        }
    }
}
