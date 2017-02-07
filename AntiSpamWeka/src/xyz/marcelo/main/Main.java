package xyz.marcelo.main;

import java.io.File;
import java.io.FileReader;
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

    private static final int NUMBER_OF_REPETITIONS = 5;

    public static void main(String[] args) throws Exception
    {
        MethodConfiguration[] methodConfigurations = MethodConfiguration.getTraditionalMethods();

        List<String> folders = Folders.getFolders();

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

                for (int i = 0; i < NUMBER_OF_REPETITIONS; i++)
                {
                    // initialize random number generator
                    Random random = new Random(PRIME_SEEDS[i]);

                    // build test and train sets
                    dataSet.randomize(random);
                    int trainSize = (int) Math.round(dataSet.numInstances() * 0.5);
                    int testSize = dataSet.numInstances() - trainSize;
                    Instances trainSet = new Instances(dataSet, 0, trainSize);
                    Instances testSet = new Instances(dataSet, trainSize, testSize);
                    testSet.addAll(emptySet);

                    // build the classifier
                    AbstractClassifier classifier = MethodHelper.build(methodConfiguration);

                    // evaluate the classifier
                    MethodEvaluation methodEvaluation = MethodHelper.run(classifier, trainSet, testSet);
                    methodEvaluation.setFolder(Folders.shortenFolderName(folder));
                    methodEvaluation.setMethodConfiguration(methodConfiguration);

                    // log the partial result for this configuration
                    FormatHelper.aggregateResult(methodEvaluation, true);
                }

                // optional: delete temporary .csv and .arff files
                new File(dataCsvPath).delete();
                new File(dataArffPath).delete();
                new File(emptyCsvPath).delete();
                new File(emptyArffPath).delete();

                // log the final result for this configuration
                FormatHelper.printResults();
            }

            FormatHelper.printFooter();
        }
    }
}
