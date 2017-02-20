package xyz.marcelo.helper;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.AveragedNDependenceEstimators.A1DE;
import weka.classifiers.bayes.AveragedNDependenceEstimators.A2DE;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.MultilayerPerceptronCS;
import weka.classifiers.functions.RBFClassifier;
import weka.classifiers.functions.SGD;
import weka.classifiers.functions.SPegasos;
import weka.classifiers.misc.HyperPipes;
import weka.classifiers.rules.DTNB;
import weka.classifiers.rules.FURIA;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Utils;
import xyz.marcelo.method.MethodConfiguration;

public class MethodHelper
{
    public static Classifier build(MethodConfiguration methodConfiguration)
    {
        Classifier classifier = null;

        try
        {
            switch (methodConfiguration)
            {
                case SVM:
                    classifier = new LibSVM();
                    ((LibSVM) classifier).setOptions(Utils.splitOptions(MethodConfiguration.SVM.getConfig()));
                    break;
                case SPEGASOS:
                    classifier = new SPegasos();
                    ((SPegasos) classifier).setOptions(Utils.splitOptions(MethodConfiguration.SPEGASOS.getConfig()));
                    break;
                case SGD:
                    classifier = new SGD();
                    ((SGD) classifier).setOptions(Utils.splitOptions(MethodConfiguration.SGD.getConfig()));
                    break;
                case RF:
                    classifier = new RandomForest();
                    ((RandomForest) classifier).setOptions(Utils.splitOptions(MethodConfiguration.RF.getConfig()));
                    break;
                case RBF:
                    classifier = new RBFClassifier();
                    ((RBFClassifier) classifier).setOptions(Utils.splitOptions(MethodConfiguration.RBF.getConfig()));
                    break;
                case NBTREE:
                    classifier = new NBTree();
                    ((NBTree) classifier).setOptions(Utils.splitOptions(MethodConfiguration.NBTREE.getConfig()));
                    break;
                case MLPCS:
                    classifier = new MultilayerPerceptronCS();
                    ((MultilayerPerceptronCS) classifier).setOptions(Utils.splitOptions(MethodConfiguration.MLPCS.getConfig()));
                    break;
                case MLP:
                    classifier = new MultilayerPerceptron();
                    ((MultilayerPerceptron) classifier).setOptions(Utils.splitOptions(MethodConfiguration.MLP.getConfig()));
                    break;
                case JRIP:
                    classifier = new JRip();
                    ((JRip) classifier).setOptions(Utils.splitOptions(MethodConfiguration.JRIP.getConfig()));
                    break;
                case J48:
                    classifier = new J48();
                    ((J48) classifier).setOptions(Utils.splitOptions(MethodConfiguration.J48.getConfig()));
                    break;
                case HP:
                    classifier = new HyperPipes();
                    ((HyperPipes) classifier).setOptions(Utils.splitOptions(MethodConfiguration.HP.getConfig()));
                    break;
                case FURIA:
                    classifier = new FURIA();
                    ((FURIA) classifier).setOptions(Utils.splitOptions(MethodConfiguration.FURIA.getConfig()));
                    break;
                case DTNB:
                    classifier = new DTNB();
                    ((DTNB) classifier).setOptions(Utils.splitOptions(MethodConfiguration.DTNB.getConfig()));
                    break;
                case BFTREE:
                    classifier = new BFTree();
                    ((BFTree) classifier).setOptions(Utils.splitOptions(MethodConfiguration.A1DE.getConfig()));
                    break;
                case A2DE:
                    classifier = new A2DE();
                    ((A2DE) classifier).setOptions(Utils.splitOptions(MethodConfiguration.A2DE.getConfig()));
                    break;
                case A1DE:
                    classifier = new A1DE();
                    ((A1DE) classifier).setOptions(Utils.splitOptions(MethodConfiguration.A1DE.getConfig()));
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return classifier;
    }
}
