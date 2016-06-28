import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import xyz.marcelo.constants.EnhancedEvaluation;
import xyz.marcelo.constants.Folders;
import xyz.marcelo.entity.Method;
import xyz.marcelo.helpers.DataHelper;
import xyz.marcelo.helpers.FormatHelper;
import xyz.marcelo.helpers.MethodHelper;

public class Main {

	private static final int SEED = 1;

	private static final int NREP = 10;

	private static final Random RNG = new Random(SEED);

	public static void main(String[] args) throws Exception {

		Method[] methods = new Method[] { Method.J48, Method.MLP, Method.RBF, Method.RF, Method.SGD, Method.SVM };

		LinkedList<String> folders = new LinkedList<String>();
		folders.addAll(Arrays.asList(Folders.FOLDERS_LING));
		folders.addAll(Arrays.asList(Folders.FOLDERS_SPAMASSASSIN));
		folders.addAll(Arrays.asList(Folders.FOLDERS_TREC));
		folders.addAll(Arrays.asList(Folders.FOLDERS_UNIFEI));

		for (Method method : methods) {

			FormatHelper.printHeader();

			for (String folder : folders) {

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

						EnhancedEvaluation enhancedEvaluation = MethodHelper.run(classifier, trainSet, testSet);
						enhancedEvaluation.setFolder(subFolder);
						enhancedEvaluation.setMethod(method);

						FormatHelper.aggregateResult(enhancedEvaluation);
					}

					FormatHelper.printResult();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

			FormatHelper.printFooter();
		}
	}
}
