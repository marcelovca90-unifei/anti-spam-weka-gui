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
    private static final int SEED = 1;

    private static final int NREP = 3;

    private static final Random RNG = new Random(SEED);

    public static void main(String[] args) throws Exception
    {
        MethodConfiguration[] methodConfigurations = new MethodConfiguration[] { MethodConfiguration.RF };

        List<String> folders = Folders.getFolders();

        for (MethodConfiguration methodConfiguration : methodConfigurations)
        {
            FormatHelper.printHeader();

            for (String folder : folders)
            {
                System.out.println(folder);
                String subFolder = folder.substring(folder.indexOf("Vectors") + "Vectors".length());

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

                for (int i = 1; i <= NREP; i++)
                {
                    // build test and train sets
                    dataSet.randomize(RNG);
                    int trainSize = (int) Math.round(dataSet.numInstances() * 0.5);
                    int testSize = dataSet.numInstances() - trainSize;
                    Instances trainSet = new Instances(dataSet, 0, trainSize);
                    Instances testSet = new Instances(dataSet, trainSize, testSize);
                    testSet.addAll(emptySet);

                    // build the classifier
                    AbstractClassifier classifier = MethodHelper.build(methodConfiguration);

                    // evaluate the classifier
                    MethodEvaluation methodEvaluation = MethodHelper.run(classifier, trainSet, testSet);
                    methodEvaluation.setFolder(subFolder);
                    methodEvaluation.setMethodConfiguration(methodConfiguration);

                    // log the partial result for this configuration
                    FormatHelper.aggregateResult(methodEvaluation, true);
                }

                // log the final result for this configuration
                FormatHelper.printResults();
            }

            FormatHelper.printFooter();
        }
    }
}
