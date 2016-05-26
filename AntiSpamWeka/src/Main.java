import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import weka.core.Instances;
import xyz.marcelo.constants.Folders;
import xyz.marcelo.enums.Method;
import xyz.marcelo.helpers.DataHelper;
import xyz.marcelo.helpers.FormatHelper;
import xyz.marcelo.helpers.MethodHelper;

public class Main {

	private static final int SEED = 17;

	public static void main(String[] args) {

		Method[] methods = new Method[] { Method.J48, Method.MLP, Method.RBF, Method.RF, Method.SGD, Method.SVM };

		LinkedList<String> folders = new LinkedList<String>();
		folders.addAll(Arrays.asList(Folders.FOLDERS_LING));
		folders.addAll(Arrays.asList(Folders.FOLDERS_SPAMASSASSIN));
		folders.addAll(Arrays.asList(Folders.FOLDERS_TREC));
		folders.addAll(Arrays.asList(Folders.FOLDERS_UNIFEI));

		FormatHelper.printHeader();

		for (Method method : methods) {

			for (String folder : folders) {

				try {

					String subFolder = folder.substring(folder.indexOf("Vectors") + "Vectors".length());
					String hamFilePath = folder + File.separator + "ham";
					String spamFilePath = folder + File.separator + "spam";
					String dataCsvPath = folder + File.separator + "data.csv";
					String dataArffPath = folder + File.separator + "data.arff";

					DataHelper.bin2csv(hamFilePath, spamFilePath, dataCsvPath);
					DataHelper.csv2arff(dataCsvPath, dataArffPath);

					FileReader fileReader = new FileReader(dataArffPath);
					Instances dataSet = new Instances(fileReader);
					dataSet.setClassIndex(dataSet.numAttributes() - 1);
					dataSet.randomize(new Random(SEED));

					int trainSize = (int) Math.round(dataSet.numInstances() * 0.5);
					int testSize = dataSet.numInstances() - trainSize;
					Instances trainSet = new Instances(dataSet, 0, trainSize);
					Instances testSet = new Instances(dataSet, trainSize, testSize);

					MethodHelper.initialize(subFolder, method, trainSet, testSet);

					MethodHelper.run();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}

}
