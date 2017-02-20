package xyz.marcelo.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import xyz.marcelo.constant.Folders;
import xyz.marcelo.helper.DataHelper;
import xyz.marcelo.helper.FormatHelper;
import xyz.marcelo.helper.MethodHelper;
import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.MethodEvaluation;

public class Main
{
    private static final int[] PRIME_SEEDS = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

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
            System.out.println("Available classification methods: " + Arrays.toString(MethodConfiguration.getAvailableMethods()));
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

                for (int repetition = 0; repetition < numberOfRepetitions; repetition++)
                {
                    // initialize random number generator
                    Random random = new Random(PRIME_SEEDS[repetition]);

                    // randomize data set using n-th prime
                    dataSet.randomize(random);

                    for (int fold = 0; fold < numberOfFolds; fold++)
                    {
                        Instances trainSet = dataSet.trainCV(numberOfFolds, fold, random);
                        Instances testSet = dataSet.testCV(numberOfFolds, fold);

                        // add empty patterns to test set
                        testSet.addAll(emptySet);

                        // build the classifier
                        AbstractClassifier classifier = MethodHelper.build(methodConfiguration);

                        // evaluate the classifier
                        MethodEvaluation methodEvaluation = MethodHelper.run(classifier, trainSet, testSet);
                        methodEvaluation.setFolder(Folders.shortenFolderName(args[0], folder));
                        methodEvaluation.setMethodConfiguration(methodConfiguration);

                        // if the experiment is valid, log the partial result for this configuration
                        FormatHelper.handleSingleExperiment(methodEvaluation, true, false);
                    }
                }

                // delete temporary .csv and .arff files
                // Arrays.asList(dataCsvPath, dataArffPath, emptyCsvPath, emptyArffPath).forEach(path -> new File(path).delete());

                // log the final result for this configuration
                FormatHelper.handleAllExperiments();
            }

            FormatHelper.printFooter();
        }
    }
}
