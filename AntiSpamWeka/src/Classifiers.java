import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFClassifier;
import weka.classifiers.functions.SGD;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;

public class Classifiers {

	private static Instances testSet;
	private static Instances trainSet;

	public static Classifier buildJ48() throws Exception {
		J48 j48 = new J48();
		j48.setOptions(Utils.splitOptions(Configurations.J48));
		j48.buildClassifier(trainSet);
		return j48;
	}
	
	public static Classifier buildLibSVM() throws Exception {
		LibSVM libSVM = new LibSVM();
		libSVM.setOptions(Utils.splitOptions(Configurations.SVM));
		libSVM.buildClassifier(trainSet);
		return libSVM;
	}

	public static Classifier buildMutilayerPerceptron() throws Exception {
		MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
		multilayerPerceptron.setOptions(Utils.splitOptions(Configurations.MLP));
		multilayerPerceptron.buildClassifier(trainSet);
		return multilayerPerceptron;
	}

	public static Classifier buildRandomForest() throws Exception {
		RandomForest randomForest = new RandomForest();
		randomForest.setOptions(Utils.splitOptions(Configurations.RF));
		randomForest.buildClassifier(trainSet);
		return randomForest;
	}
	
	public static Classifier buildRBFClassifier() throws Exception {
		RBFClassifier rbfClassifier = new RBFClassifier();
		rbfClassifier.setOptions(Utils.splitOptions(Configurations.RBF));
		rbfClassifier.buildClassifier(trainSet);
		return rbfClassifier;
	}
	
	public static Classifier buildSGD() throws Exception {
		SGD sgd = new SGD();
		sgd.setOptions(Utils.splitOptions(Configurations.SGD));
		sgd.buildClassifier(trainSet);
		return sgd;
	}
	
	public static Evaluation evaluate(Classifier classifier) throws Exception {
		Evaluation evaluation = new Evaluation(trainSet);
		evaluation.evaluateModel(classifier, testSet);
		return evaluation;
	}
	
	public static void setTestSet(Instances testSet) {
		Classifiers.testSet = testSet;
	}
	
	public static void setTrainSet(Instances trainSet) {
		Classifiers.trainSet = trainSet;
	}
	
}
