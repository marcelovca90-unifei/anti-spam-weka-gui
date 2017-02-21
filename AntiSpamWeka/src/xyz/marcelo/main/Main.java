package xyz.marcelo.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import xyz.marcelo.eval.TimedEvaluation;
import xyz.marcelo.helper.DataSetHelper;
import xyz.marcelo.helper.FormatHelper;
import xyz.marcelo.helper.InputOutputHelper;
import xyz.marcelo.helper.MethodHelper;

public class Main
{
    private static final int[] PRIME_NUMBERS = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

    public static void main(String[] args) throws Exception
    {
        List<String> folders = new ArrayList<>();
        List<MethodHelper> methodConfigurations = new ArrayList<>();
        Integer numberOfRepetitions = 0;

        // exits if the wrong number of arguments was provided
        if (args.length != 3)
        {
            System.out.println("Usage: java -jar AntiSpamWeka.jar \"DATA_SET_FOLDER\" \"COMMA_SEPARATED_METHODS\" NUMBER_OF_REPETITIONS");
            System.out.println("Available classification methods: " + Arrays.toString(MethodHelper.values()));
            System.exit(1);
        }
        else
        {
            // tries to build the folder and method configuration lists
            try
            {
                folders = DataSetHelper.getFolders(args[0]);
                for (String methodString : args[1].split(","))
                    methodConfigurations.add(MethodHelper.valueOf(methodString));
                numberOfRepetitions = Integer.parseInt(args[2]);
            }
            catch (Exception e)
            {
                // if an invalid data set folder was provided
                if (e instanceof IOException)
                    System.out.println("The specified data set folder is invalid.");

                // or if an invalid method was provided
                else if ((e instanceof IllegalArgumentException) && !(e instanceof NumberFormatException))
                    System.out.println("One or more specified method(s) does not exist.");

                // or if an invalid number of repetitions and/or folds was provided
                else if ((e instanceof IllegalArgumentException) && (e instanceof NumberFormatException))
                    System.out.println("The specified number of repetitions and/or folds is invalid.");

                // exit the program
                System.exit(1);
            }
        }

        for (MethodHelper methodConfiguration : methodConfigurations)
        {
            FormatHelper.printHeader();

            for (String folder : folders)
            {
                // import data set
                String hamFilePath = folder + File.separator + "ham";
                String spamFilePath = folder + File.separator + "spam";
                String dataCsvPath = folder + File.separator + "data.csv";
                String dataArffPath = folder + File.separator + "data.arff";
                InputOutputHelper.bin2csv(hamFilePath, spamFilePath, dataCsvPath);
                InputOutputHelper.csv2arff(dataCsvPath, dataArffPath);
                FileReader dataReader = new FileReader(dataArffPath);
                Instances dataSet = new Instances(dataReader);
                dataSet.setClassIndex(dataSet.numAttributes() - 1);

                // build empty patterns set
                String emptyCsvPath = folder + File.separator + "empty.csv";
                String emptyArffPath = folder + File.separator + "empty.arff";
                InputOutputHelper.buildEmptyCsv(folder, dataSet.numAttributes() - 1);
                InputOutputHelper.csv2arff(emptyCsvPath, emptyArffPath);
                FileReader emptyReader = new FileReader(emptyArffPath);
                Instances emptySet = new Instances(emptyReader);

                // initialize random number generator
                Random random = new Random();

                // build the classifier for the given configuration
                Classifier classifier = MethodHelper.buildClassifierFor(methodConfiguration);

                // create the object that will hold the overall evaluations result
                TimedEvaluation timedEvaluation = new TimedEvaluation(folder, methodConfiguration);

                for (int repetition = 0; repetition < numberOfRepetitions; repetition++)
                {
                    // set random number generator's seed
                    random.setSeed(PRIME_NUMBERS[repetition]);

                    // randomize the data set to assure balance and avoid biasing
                    dataSet.randomize(random);

                    // build test and train sets
                    int trainSize = (int) Math.round(dataSet.numInstances() * 0.5);
                    int testSize = dataSet.numInstances() - trainSize;
                    Instances trainSet = new Instances(dataSet, 0, trainSize);
                    Instances testSet = new Instances(dataSet, trainSize, testSize);

                    // add empty patterns to test set
                    testSet.addAll(emptySet);

                    // build the classifier for the given configuration
                    Classifier innerClassifier = AbstractClassifier.makeCopy(classifier);

                    // create the object that will hold the single evaluation result
                    Evaluation innerEvaluation = new Evaluation(dataSet);

                    // evaluate the classifier
                    timedEvaluation.setClassifier(innerClassifier);
                    timedEvaluation.setEvaluation(innerEvaluation);
                    timedEvaluation.run(trainSet, testSet);

                    // log the partial result for this configuration
                    FormatHelper.handleSingleExperiment(timedEvaluation, true);
                }

                // delete temporary .csv and .arff files
                Arrays.asList(dataCsvPath, dataArffPath, emptyCsvPath, emptyArffPath).forEach(path -> new File(path).delete());

                // log the final result for this configuration
                FormatHelper.handleAllExperiments();
            }

            FormatHelper.printFooter();
        }
    }
}
