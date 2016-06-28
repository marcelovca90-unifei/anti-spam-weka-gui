package xyz.marcelo.helpers;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFClassifier;
import weka.classifiers.functions.SGD;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;
import xyz.marcelo.constants.EnhancedEvaluation;
import xyz.marcelo.entity.Config;
import xyz.marcelo.entity.Method;

public class MethodHelper {

	public static AbstractClassifier build(Method method) {

		AbstractClassifier classifier = null;

		try {
			switch (method) {
			case J48:
				classifier = new J48();
				classifier.setOptions(Utils.splitOptions(Config.J48.toString()));
				break;
			case MLP:
				classifier = new MultilayerPerceptron();
				classifier.setOptions(Utils.splitOptions(Config.MLP.toString()));
				break;
			case RBF:
				classifier = new RBFClassifier();
				classifier.setOptions(Utils.splitOptions(Config.RBF.toString()));
				break;
			case RF:
				classifier = new RandomForest();
				classifier.setOptions(Utils.splitOptions(Config.RF.toString()));
				break;
			case SGD:
				classifier = new SGD();
				classifier.setOptions(Utils.splitOptions(Config.SGD.toString()));
				break;
			case SVM:
				classifier = new LibSVM();
				classifier.setOptions(Utils.splitOptions(Config.SVM.toString()));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return classifier;
	}

	public static EnhancedEvaluation run(AbstractClassifier classifier, Instances trainSet, Instances testSet) {

		EnhancedEvaluation enhancedEvaluation = null;

		try {
			
			enhancedEvaluation = new EnhancedEvaluation();
			
			// train the classifier
			enhancedEvaluation.setTrainStart(System.currentTimeMillis());
			classifier.buildClassifier(trainSet);
			enhancedEvaluation.setTrainEnd(System.currentTimeMillis());
			
			// test the classifier
			enhancedEvaluation.setTestStart(System.currentTimeMillis());
			Evaluation evaluation = new Evaluation(trainSet);
			evaluation.evaluateModel(classifier, testSet);
			enhancedEvaluation.setTestEnd(System.currentTimeMillis());
			
			enhancedEvaluation.setEvaluation(evaluation);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return enhancedEvaluation;
	}

}
