import java.io.File;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import constants.Folders;
import data.DataConverter;

public class Main {

	private static final int SEED = 17;

	public static void main(String[] args) {

		for (String folder : Folders.FOLDERS_MI) {

			try {

				System.out.println(folder);

				String hamFilePath = folder + File.separator + "ham";
				String spamFilePath = folder + File.separator + "spam";
				String dataCsvPath = folder + File.separator + "data.csv";
				String dataArffPath = folder + File.separator + "data.arff";

				DataConverter.bin2csv(hamFilePath, spamFilePath, dataCsvPath);
				DataConverter.csv2arff(dataCsvPath, dataArffPath);

				FileReader fileReader = new FileReader(dataArffPath);
				Instances dataSet = new Instances(fileReader);
				dataSet.setClassIndex(dataSet.numAttributes() - 1);
				dataSet.randomize(new Random(SEED));

				int trainSize = (int) Math.round(dataSet.numInstances() * 0.5);
				int testSize = dataSet.numInstances() - trainSize;
				Instances trainSet = new Instances(dataSet, 0, trainSize);
				Instances testSet = new Instances(dataSet, trainSize, testSize);

				Classifiers.setTrainSet(trainSet);
				Classifiers.setTestSet(testSet);
				
				// train step
				Classifier mlp = Classifiers.buildMutilayerPerceptron();
				Classifier rbfClassifier = Classifiers.buildRBFClassifier();
				Classifier libSVM = Classifiers.buildLibSVM();
				Classifier randomForest = Classifiers.buildRandomForest();
				Classifier sgd = Classifiers.buildSGD();

				// test step
				Evaluation eval = new Evaluation(dataSet);
				eval.evaluateModel(sgd, testSet);

				// print results
				System.out.println(eval.toSummaryString());
				System.out.println(eval.toClassDetailsString());

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

}
