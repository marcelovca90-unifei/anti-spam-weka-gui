package xyz.marcelo.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.TimedEvaluation;
import xyz.marcelo.helper.DataSetHelper;
import xyz.marcelo.helper.FilterHelper;
import xyz.marcelo.helper.FormatHelper;
import xyz.marcelo.helper.InputOutputHelper;
import xyz.marcelo.helper.PrimeHelper;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        List<String> folders = new ArrayList<>();
        List<MethodConfiguration> methodConfigurations = new ArrayList<>();
        Integer numberOfRepetitions = null;
        Boolean includeEmptyInstances = null;

        // exits if the wrong number of arguments was provided
        if (args.length != 4)
        {
            System.out.println("Usage: java -jar AntiSpamWeka.jar \"DATA_SET_FOLDER\" \"COMMA_SEPARATED_METHODS\" NUMBER_OF_REPETITIONS TEST_EMPTY_INSTANCES");
            System.out.println("Available classification methods: " + Arrays.toString(MethodConfiguration.values()));
            System.exit(1);
        }
        else
        {
            // tries to build the folder and method configuration lists
            try
            {
                folders = DataSetHelper.getFolders(args[0]);
                for (String methodString : args[1].split(","))
                    methodConfigurations.add(MethodConfiguration.valueOf(methodString));
                numberOfRepetitions = Integer.parseInt(args[2]);
                includeEmptyInstances = Boolean.parseBoolean(args[3]);
            }
            catch (Exception e)
            {
                // if an invalid data set folder was provided
                if (e instanceof IOException)
                    System.out.println("The specified data set folder is invalid.");

                // or if an invalid method was provided
                else if ((e instanceof IllegalArgumentException) && !(e instanceof NumberFormatException))
                    System.out.println("One or more specified method(s) does not exist.");

                // or if an invalid number of repetitions was provided
                else if ((e instanceof IllegalArgumentException) && (e instanceof NumberFormatException))
                    System.out.println("The specified number of repetitions is invalid.");

                // exit the program
                System.exit(1);
            }
        }

        // objects that will hold all kinds of data sets
        Instances dataSet = null, trainingSet = null, testingSet = null, emptySet = null;

        for (MethodConfiguration methodConfiguration : methodConfigurations)
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
                if (includeEmptyInstances) emptySet = InputOutputHelper.createEmptyInstances(folder, dataSet.numAttributes() - 1);

                // initialize random number generator
                Random random = new Random();

                // build the classifier for the given configuration
                Classifier classifier = MethodConfiguration.buildClassifierFor(methodConfiguration);

                // create the object that will hold the overall evaluations result
                TimedEvaluation timedEvaluation = new TimedEvaluation(folder, methodConfiguration);

                // reset prime helper index
                PrimeHelper.reset();

                for (int repetition = 0; repetition < numberOfRepetitions; repetition++)
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
                    if (includeEmptyInstances) testingSet.addAll(emptySet);

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
