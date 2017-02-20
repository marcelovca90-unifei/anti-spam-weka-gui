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
import xyz.marcelo.constant.Folders;
import xyz.marcelo.helper.DataHelper;
import xyz.marcelo.helper.FormatHelper;
import xyz.marcelo.helper.MethodHelper;
import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.TimedEvaluation;

public class Main
{
    private static final int[] PRIME_NUMBERS = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

    public static void main(String[] args) throws Exception
    {
        List<String> folders = new ArrayList<>();
        List<MethodConfiguration> methodConfigurations = new ArrayList<>();
        Integer numberOfRepetitions = 0;
        Integer numberOfFolds = 0;

        // exits if the wrong number of arguments was provided
        if (args.length != 4)
        {
            System.out.println("Usage: java -jar AntiSpamWeka.jar \"DATA_SET_FOLDER\" \"COMMA_SEPARATED_METHODS\" NUMBER_OF_REPETITIONS NUMBER_OF_FOLDS");
            System.out.println("Available classification methods: " + Arrays.toString(MethodConfiguration.values()));
            System.exit(1);
        }
        else
        {
            // tries to build the folder and method configuration lists
            try
            {
                folders = Folders.getFolders(args[0]);
                for (String methodString : args[1].split(","))
                    methodConfigurations.add(MethodConfiguration.valueOf(methodString));
                numberOfRepetitions = Integer.parseInt(args[2]);
                numberOfFolds = Integer.parseInt(args[3]);
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

        for (MethodConfiguration methodConfiguration : methodConfigurations)
        {
            FormatHelper.printHeader();

            for (String folder : folders)
            {
                // import data set
                String hamFilePath = folder + File.separator + "ham";
                String spamFilePath = folder + File.separator + "spam";
                String dataCsvPath = folder + File.separator + "data.csv";
                String dataArffPath = folder + File.separator + "data.arff";
                DataHelper.bin2csv(hamFilePath, spamFilePath, dataCsvPath);
                DataHelper.csv2arff(dataCsvPath, dataArffPath);
                FileReader dataReader = new FileReader(dataArffPath);
                Instances dataSet = new Instances(dataReader);
                dataSet.setClassIndex(dataSet.numAttributes() - 1);

                // build empty patterns set
                String emptyCsvPath = folder + File.separator + "empty.csv";
                String emptyArffPath = folder + File.separator + "empty.arff";
                DataHelper.buildEmptyCsv(folder, dataSet.numAttributes() - 1);
                DataHelper.csv2arff(emptyCsvPath, emptyArffPath);
                FileReader emptyReader = new FileReader(emptyArffPath);
                Instances emptySet = new Instances(emptyReader);

                // initialize random number generator
                Random random = new Random();

                for (int repetition = 0; repetition < numberOfRepetitions; repetition++)
                {
                    // set random number generator's seed
                    random.setSeed(PRIME_NUMBERS[repetition]);

                    // randomize the data set to assure balance and avoid biasing
                    dataSet.randomize(random);

                    // stratify the data set to balance each class' instances in each fold
                    dataSet.stratify(numberOfFolds);

                    // create the objects that will hold the evaluation results
                    TimedEvaluation timedEvaluation = new TimedEvaluation(args[0], methodConfiguration);
                    Evaluation evaluation = new Evaluation(dataSet);

                    // build the base classifier
                    Classifier classifierBase = MethodHelper.build(methodConfiguration);

                    // perform a k-fold cross-validation
                    for (int fold = 0; fold < numberOfFolds; fold++)
                    {
                        // create the folded training and test sets
                        Instances trainSet = dataSet.trainCV(numberOfFolds, fold, random);
                        Instances testSet = dataSet.testCV(numberOfFolds, fold);

                        // add empty patterns to test set
                        testSet.addAll(emptySet);

                        // evaluate the classifier
                        Classifier classifierCopy = AbstractClassifier.makeCopy(classifierBase);
                        timedEvaluation.setClassifier(classifierCopy);
                        timedEvaluation.setEvaluation(evaluation);
                        timedEvaluation.run(trainSet, testSet);
                    }

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
