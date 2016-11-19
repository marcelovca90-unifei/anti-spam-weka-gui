package xyz.marcelo.main;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import xyz.marcelo.constant.EmptyPatterns;
import xyz.marcelo.constant.Folders;
import xyz.marcelo.helper.DataHelper;
import xyz.marcelo.helper.FormatHelper;
import xyz.marcelo.helper.MethodHelper;
import xyz.marcelo.helper.StatHelper;
import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.MethodEvaluation;

public class Main
{
    private static final int SEED = 1;

    private static final int NREP = 10;

    private static final Random RNG = new Random(SEED);

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception
    {
        Class<?>[] classes = new Class[] { Main.class, EmptyPatterns.class, Folders.class, DataHelper.class,
                FormatHelper.class, MethodHelper.class, StatHelper.class, MethodConfiguration.class,
                MethodEvaluation.class, MethodConfiguration.class };

        for (Class<?> clazz : classes)
            Class.forName(clazz.getName());

        MethodConfiguration[] methodConfigurations = new MethodConfiguration[] { MethodConfiguration.MOEFC,
                MethodConfiguration.NBTREE, MethodConfiguration.JRIP, MethodConfiguration.SPEGASOS,
                MethodConfiguration.CHIRP, MethodConfiguration.HMM, MethodConfiguration.FLR,
                // MethodConfiguration.J48,
                // MethodConfiguration.MLP,
                // MethodConfiguration.RBF,
                // MethodConfiguration.RF,
                // MethodConfiguration.SGD,
                // MethodConfiguration.SVM
        };

        LinkedList<String> folders = new LinkedList<>();
        folders.addAll(Arrays.asList(Folders.FOLDERS_WARMUP));
        folders.addAll(Arrays.asList(Folders.FOLDERS_LING));
        folders.addAll(Arrays.asList(Folders.FOLDERS_SPAMASSASSIN));
        folders.addAll(Arrays.asList(Folders.FOLDERS_TREC));
        // folders.addAll(Arrays.asList(Folders.FOLDERS_UNIFEI));

        for (MethodConfiguration methodConfiguration : methodConfigurations)
        {
            FormatHelper.printHeader();

            for (String folder : folders)
            {
                String subFolder = folder.substring(folder.indexOf("Vectors") + "Vectors".length());

                // import data set
                String hamFilePath = folder + File.separator + "ham";
                String spamFilePath = folder + File.separator + "spam";
                String dataCsvPath = folder + File.separator + "data.csv";
                String dataArffPath = folder + File.separator + "data.arff";
                // DataHelper.bin2csv(hamFilePath, spamFilePath, dataCsvPath);
                // DataHelper.csv2arff(dataCsvPath, dataArffPath);
                FileReader dataReader = new FileReader(dataArffPath);
                Instances dataSet = new Instances(dataReader);
                dataSet.setClassIndex(dataSet.numAttributes() - 1);

                // build empty patterns set
                String emptyCsvPath = folder + File.separator + "empty.csv";
                String emptyArffPath = folder + File.separator + "empty.arff";
                // DataHelper.buildEmptyCsv(folder, dataSet.numAttributes() -
                // 1);
                // DataHelper.csv2arff(emptyCsvPath, emptyArffPath);
                FileReader emptyReader = new FileReader(emptyArffPath);
                Instances emptySet = new Instances(emptyReader);

                try
                {
                    for (int i = 1; i <= NREP; i++)
                    {
                        // build test and train sets
                        dataSet.randomize(RNG);
                        int trainSize = (int) Math.round(dataSet.numInstances() * 0.5);
                        int testSize = dataSet.numInstances() - trainSize;
                        Instances trainSet = new Instances(dataSet, 0, trainSize);
                        Instances testSet = new Instances(dataSet, trainSize, testSize);
                        testSet.addAll(emptySet);

                        AbstractClassifier classifier = MethodHelper.build(methodConfiguration);

                        MethodEvaluation methodEvaluation = MethodHelper.run(classifier, trainSet, testSet);
                        methodEvaluation.setFolder(subFolder);
                        methodEvaluation.setMethodConfiguration(methodConfiguration);

                        if (!folder.contains(Folders.WARMUP_TAG))
                            FormatHelper.aggregateResult(methodEvaluation, true);
                    }

                    if (!folder.contains(Folders.WARMUP_TAG))
                        FormatHelper.printResults();

                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }

            FormatHelper.printFooter();
        }
    }
}
