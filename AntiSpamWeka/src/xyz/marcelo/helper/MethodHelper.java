package xyz.marcelo.helper;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFClassifier;
import weka.classifiers.functions.SGD;
import weka.classifiers.functions.SPegasos;
import weka.classifiers.misc.CHIRP;
import weka.classifiers.misc.FLR;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;
import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.MethodEvaluation;

public class MethodHelper
{
    public static AbstractClassifier build(MethodConfiguration methodConfiguration)
    {
        AbstractClassifier classifier = null;

        try
        {
            switch (methodConfiguration)
            {
            case NBTREE:
                classifier = new NBTree();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.NBTREE.getConfig()));
                break;
            case JRIP:
                classifier = new JRip();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.JRIP.getConfig()));
                break;
            case SPEGASOS:
                classifier = new SPegasos();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.SPEGASOS.getConfig()));
                break;
            case CHIRP:
                classifier = new CHIRP();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.CHIRP.getConfig()));
                break;
            case FLR:
                classifier = new FLR();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.FLR.getConfig()));
                break;
            case J48:
                classifier = new J48();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.J48.getConfig()));
                break;
            case MLP:
                classifier = new MultilayerPerceptron();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.MLP.getConfig()));
                break;
            case RBF:
                classifier = new RBFClassifier();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.RBF.getConfig()));
                break;
            case RF:
                classifier = new RandomForest();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.RF.getConfig()));
                break;
            case SGD:
                classifier = new SGD();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.SGD.getConfig()));
                break;
            case SVM:
                classifier = new LibSVM();
                classifier.setOptions(Utils.splitOptions(MethodConfiguration.SVM.getConfig()));
                break;
            }
        }
        catch (Exception e)
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return methodEvaluation;
    }
}
