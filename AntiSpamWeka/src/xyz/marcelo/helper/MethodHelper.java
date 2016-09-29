package xyz.marcelo.helper;

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
import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.MethodEvaluation;
import xyz.marcelo.method.MethodName;

public class MethodHelper
{
    public static AbstractClassifier build(MethodName method)
    {
        AbstractClassifier classifier = null;

        try
        {
            switch (method)
            {
            case J48:
                classifier = new J48();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.J48.toString()));
                break;
            case MLP:
                classifier = new MultilayerPerceptron();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.MLP.toString()));
                break;
            case RBF:
                classifier = new RBFClassifier();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.RBF.toString()));
                break;
            case RF:
                classifier = new RandomForest();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.RF.toString()));
                break;
            case SGD:
                classifier = new SGD();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.SGD.toString()));
                break;
            case SVM:
                classifier = new LibSVM();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.SVM.toString()));
                break;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return classifier;
    }

    public static MethodEvaluation run(AbstractClassifier classifier, Instances trainSet, Instances testSet)
    {

        MethodEvaluation methodEvaluation = null;

        try
        {

            methodEvaluation = new MethodEvaluation();

            // train the classifier
            methodEvaluation.setTrainStart(System.currentTimeMillis());
            classifier.buildClassifier(trainSet);
            methodEvaluation.setTrainEnd(System.currentTimeMillis());

            // test the classifier
            methodEvaluation.setTestStart(System.currentTimeMillis());
            Evaluation evaluation = new Evaluation(trainSet);
            evaluation.evaluateModel(classifier, testSet);
            methodEvaluation.setTestEnd(System.currentTimeMillis());

            methodEvaluation.setEvaluation(evaluation);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return methodEvaluation;
    }

}
