package xyz.marcelo.helpers;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFClassifier;
import weka.classifiers.functions.SGD;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;
import xyz.marcelo.enums.Config;
import xyz.marcelo.enums.Method;

public class MethodHelper {

	private static String folder;
	private static Method method;
	private static Instances testSet;
	private static Instances trainSet;
	private static long trainStart;
	private static long trainEnd;
	private static long testStart;
	private static long testEnd;

	public static void initialize(String folder, Method method, Instances trainSet, Instances testSet) {
		MethodHelper.folder = folder;
		MethodHelper.method = method;
		MethodHelper.trainSet = trainSet;
		MethodHelper.testSet = testSet;
	}

	private static void runJ48() throws Exception {
		J48 j48 = new J48();
		j48.setOptions(Utils.splitOptions(Config.J48.toString()));

		trainStart = System.currentTimeMillis();
		j48.buildClassifier(trainSet);
		trainEnd = System.currentTimeMillis();

		testStart = System.currentTimeMillis();
		Evaluation evaluation = new Evaluation(trainSet);
		evaluation.evaluateModel(j48, testSet);
		testEnd = System.currentTimeMillis();

		FormatHelper.setFolder(folder);
		FormatHelper.setMethod(method);
		FormatHelper.setTrainTime(trainEnd - trainStart);
		FormatHelper.setTestTime(testEnd - testStart);
		FormatHelper.aggregateResult(evaluation);
	}

	private static void runLibSVM() throws Exception {
		LibSVM libSVM = new LibSVM();
		libSVM.setOptions(Utils.splitOptions(Config.SVM.toString()));

		trainStart = System.currentTimeMillis();
		libSVM.setDebug(false);
		libSVM.buildClassifier(trainSet);
		trainEnd = System.currentTimeMillis();

		testStart = System.currentTimeMillis();
		Evaluation evaluation = new Evaluation(trainSet);
		evaluation.evaluateModel(libSVM, testSet);
		testEnd = System.currentTimeMillis();

		FormatHelper.setFolder(folder);
		FormatHelper.setMethod(method);
		FormatHelper.setTrainTime(trainEnd - trainStart);
		FormatHelper.setTestTime(testEnd - testStart);
		FormatHelper.aggregateResult(evaluation);
	}

	public static void run() throws Exception {
		switch (method) {
		case J48:
			runJ48();
			break;
		case MLP:
			runMutilayerPerceptron();
			break;
		case RBF:
			runRBFClassifier();
			break;
		case RF:
			runRandomForest();
			break;
		case SGD:
			runSGD();
			break;
		case SVM:
			runLibSVM();
			break;
		}
	}

	private static void runMutilayerPerceptron() throws Exception {
		MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
		multilayerPerceptron.setOptions(Utils.splitOptions(Config.MLP.toString()));

		trainStart = System.currentTimeMillis();
		multilayerPerceptron.buildClassifier(trainSet);
		trainEnd = System.currentTimeMillis();

		testStart = System.currentTimeMillis();
		Evaluation evaluation = new Evaluation(trainSet);
		evaluation.evaluateModel(multilayerPerceptron, testSet);
		testEnd = System.currentTimeMillis();

		FormatHelper.setFolder(folder);
		FormatHelper.setMethod(method);
		FormatHelper.setTrainTime(trainEnd - trainStart);
		FormatHelper.setTestTime(testEnd - testStart);
		FormatHelper.aggregateResult(evaluation);
	}

	private static void runRandomForest() throws Exception {
		RandomForest randomForest = new RandomForest();
		randomForest.setOptions(Utils.splitOptions(Config.RF.toString()));

		trainStart = System.currentTimeMillis();
		randomForest.buildClassifier(trainSet);
		trainEnd = System.currentTimeMillis();

		testStart = System.currentTimeMillis();
		Evaluation evaluation = new Evaluation(trainSet);
		evaluation.evaluateModel(randomForest, testSet);
		testEnd = System.currentTimeMillis();

		FormatHelper.setFolder(folder);
		FormatHelper.setMethod(method);
		FormatHelper.setTrainTime(trainEnd - trainStart);
		FormatHelper.setTestTime(testEnd - testStart);
		FormatHelper.aggregateResult(evaluation);
	}

	private static void runRBFClassifier() throws Exception {
		RBFClassifier rbfClassifier = new RBFClassifier();
		rbfClassifier.setOptions(Utils.splitOptions(Config.RBF.toString()));

		trainStart = System.currentTimeMillis();
		rbfClassifier.buildClassifier(trainSet);
		trainEnd = System.currentTimeMillis();

		testStart = System.currentTimeMillis();
		Evaluation evaluation = new Evaluation(trainSet);
		evaluation.evaluateModel(rbfClassifier, testSet);
		testEnd = System.currentTimeMillis();

		FormatHelper.setFolder(folder);
		FormatHelper.setMethod(method);
		FormatHelper.setTrainTime(trainEnd - trainStart);
		FormatHelper.setTestTime(testEnd - testStart);
		FormatHelper.aggregateResult(evaluation);
	}

	private static void runSGD() throws Exception {
		SGD sgd = new SGD();
		sgd.setOptions(Utils.splitOptions(Config.SGD.toString()));

		trainStart = System.currentTimeMillis();
		sgd.buildClassifier(trainSet);
		trainEnd = System.currentTimeMillis();

		testStart = System.currentTimeMillis();
		Evaluation evaluation = new Evaluation(trainSet);
		evaluation.evaluateModel(sgd, testSet);
		testEnd = System.currentTimeMillis();

		FormatHelper.setFolder(folder);
		FormatHelper.setMethod(method);
		FormatHelper.setTrainTime(trainEnd - trainStart);
		FormatHelper.setTestTime(testEnd - testStart);
		FormatHelper.aggregateResult(evaluation);
	}

}
