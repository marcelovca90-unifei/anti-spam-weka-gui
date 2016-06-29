package xyz.marcelo.main;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import xyz.marcelo.constants.EmptyPatterns;
import xyz.marcelo.constants.FoldersLocal;
import xyz.marcelo.constants.FoldersRemote;
import xyz.marcelo.helpers.DataHelper;
import xyz.marcelo.helpers.FormatHelper;
import xyz.marcelo.helpers.MethodHelper;
import xyz.marcelo.helpers.StatHelper;
import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.MethodEvaluation;
import xyz.marcelo.method.MethodName;

public class MainRemote {

	private static final int SEED = 1;

	private static final int NREP = 10;

	private static final Random RNG = new Random(SEED);

	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) throws Exception {

		Class[] classes = new Class[] { MainRemote.class, EmptyPatterns.class, FoldersLocal.class, DataHelper.class,
				FormatHelper.class, MethodHelper.class, StatHelper.class, MethodConfiguration.class,
				MethodEvaluation.class, MethodName.class };

		for (Class clazz : classes)
			Class.forName(clazz.getName());

		MethodName[] methods = new MethodName[] { MethodName.J48, MethodName.MLP, MethodName.RBF, MethodName.RF,
				MethodName.SGD, MethodName.SVM };

		LinkedList<String> folders = new LinkedList<String>();
		folders.addAll(Arrays.asList(FoldersRemote.FOLDERS_WARMUP));
		folders.addAll(Arrays.asList(FoldersRemote.FOLDERS_LING));
		folders.addAll(Arrays.asList(FoldersRemote.FOLDERS_SPAMASSASSIN));
		folders.addAll(Arrays.asList(FoldersRemote.FOLDERS_TREC));
		folders.addAll(Arrays.asList(FoldersRemote.FOLDERS_UNIFEI));

		for (MethodName method : methods) {

			FormatHelper.printHeader();

			for (String folder : folders) {

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
				// DataHelper.buildEmptyCsv(folder, dataSet.numAttributes() - 1);
				// DataHelper.csv2arff(emptyCsvPath, emptyArffPath);
				FileReader emptyReader = new FileReader(emptyArffPath);
				Instances emptySet = new Instances(emptyReader);

				try {

					for (int i = 1; i <= NREP; i++) {

						// build test and train sets
						dataSet.randomize(RNG);
						int trainSize = (int) Math.round(dataSet.numInstances() * 0.5);
						int testSize = dataSet.numInstances() - trainSize;
						Instances trainSet = new Instances(dataSet, 0, trainSize);
						Instances testSet = new Instances(dataSet, trainSize, testSize);
						testSet.addAll(emptySet);

						AbstractClassifier classifier = MethodHelper.build(method);

						MethodEvaluation methodEvaluation = MethodHelper.run(classifier, trainSet, testSet);
						methodEvaluation.setFolder(subFolder);
						methodEvaluation.setMethod(method);

						if (!folder.contains("WarmUp"))
							FormatHelper.aggregateResult(methodEvaluation, false);
					}

					if (!folder.contains("WarmUp"))
						FormatHelper.printResults();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

			FormatHelper.printFooter();
		}
	}
}
