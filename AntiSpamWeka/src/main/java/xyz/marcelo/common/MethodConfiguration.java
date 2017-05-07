package xyz.marcelo.common;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.AveragedNDependenceEstimators.A1DE;
import weka.classifiers.bayes.AveragedNDependenceEstimators.A2DE;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.MultilayerPerceptronCS;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.functions.SGD;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SPegasos;
import weka.classifiers.misc.HyperPipes;
import weka.classifiers.rules.DTNB;
import weka.classifiers.rules.FURIA;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.J48Consolidated;
import weka.classifiers.trees.J48graft;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Utils;

public enum MethodConfiguration
{
    // http://weka.sourceforge.net/packageMetaData/AnDE/index.html
    A1DE("Averaged 1-Dependence Estimators", "", A1DE.class),

    // http://weka.sourceforge.net/packageMetaData/AnDE/index.html
    A2DE("Averaged 2-Dependence Estimators", "", A2DE.class),

    // http://weka.sourceforge.net/doc.packages/bestFirstTree/weka/classifiers/trees/BFTree.html
    BFTREE("Best-first tree", "-M 2 -N 5 -C 1.0 -P POSTPRUNED -S 1", BFTree.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/DTNB.html
    DTNB("Decision Table/Naive Bayes hybrid classifier", "-X 1", DTNB.class),

    // http://weka.sourceforge.net/packageMetaData/fuzzyUnorderedRuleInduction/index.html
    FURIA("Fuzzy Unordered Rule Induction Algorithm", "-F 3 -N 2.0 -O 2 -S 1 -p 0 -s 0", FURIA.class),

    // http://weka.sourceforge.net/doc.packages/hyperPipes/weka/classifiers/misc/HyperPipes.html
    HP("HyperPipe classifier", "", HyperPipes.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html
    J48("C4.5 decision tree", "-C 0.25 -M 2 -Q 1", J48.class),

    // http://weka.sourceforge.net/packageMetaData/J48Consolidated/index.html
    J48C("C4.5 consolidated decision tree", "-C 0.25 -M 2 -Q 1 -RM-C -RM-N 99.0 -RM-B -2 -RM-D 50.0", J48Consolidated.class),

    // http://weka.sourceforge.net/doc.packages/J48graft/weka/classifiers/trees/J48graft.html
    J48G("C4.5 grafted decision tree", "-C 0.25 -M 2 -Q 1", J48graft.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/JRip.html
    JRIP("Repeated Incremental Pruning to Produce Error Reduction", "-F 3 -N 2.0 -O 2 -S 1", JRip.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html
    MLP("Multilayer perceptron", "-L 0.3 -M 0.2 -N 500 -V 0 -S 1 -E 20 -H a", MultilayerPerceptron.class),

    // http://weka.sourceforge.net/doc.packages/multilayerPerceptronCS/weka/classifiers/functions/MultilayerPerceptronCS.html
    MLPCS("Multilayer perceptron with context-sensitive Multiple Task Learning", "-L 0.3 -M 0.2 -N 500 -V 0 -S 1 -E 20 -H a", MultilayerPerceptronCS.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/bayes/NaiveBayes.html
    NB("Naive Bayes", "", NaiveBayes.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/trees/NBTree.html
    NBTREE("Decision tree with naive Bayes classifiers at the leaves", "", NBTree.class),

    // http://weka.sourceforge.net/doc.packages/RBFNetwork/weka/classifiers/functions/RBFNetwork.html
    RBF("Radial basis function network", "-B 2 -S 1 -R 1.0E-8 -M -1 -W 0.1", RBFNetwork.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomForest.html
    RF("Random forests", "-P 100 -I 100 -num-slots 1 -K 0 -M 1.0 -V 0.001 -S 1", RandomForest.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomTree.html
    RT("Random tree", "-K 0 -M 1.0 -V 0.001 -S 1", RandomTree.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html
    SGD("Stochastic gradient descent", "-F 0 -L 0.01 -R 1.0E-4 -E 500 -C 0.001 -S 1", SGD.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SMO.html
    SMO("Sequential minimal optimization algorithm", "-C 1.0 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -E 1.0 -C 250007\" -calibrator \"weka.classifiers.functions.Logistic -R 1.0E-8 -M -1 -num-decimal-places 4\"", SMO.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/SPegasos.html
    SPEGASOS("Stochastic Primal Estimated sub-GrAdient SOlver for SVM", "-F 0 -L 1.0E-4 -E 500", SPegasos.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibSVM.html
    SVM("Support vector machine", "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 1024.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class);

    private final String name;
    private final String config;
    private final Class<? extends Classifier> clazz;

    private MethodConfiguration(final String name, final String config, final Class<? extends Classifier> clazz)
    {
        this.name = name;
        this.config = config;
        this.clazz = clazz;
    }

    public String getName()
    {
        return name;
    }

    public String getConfig()
    {
        return config;
    }

    public Class<? extends Classifier> getClazz()
    {
        return clazz;
    }

    // dynamically instantiates a classifier for the given method configuration
    public static Classifier buildClassifierFor(MethodConfiguration methodConfiguration)
    {
        Classifier classifier = null;

        try
        {
            classifier = methodConfiguration.getClazz().newInstance();

            ((AbstractClassifier) classifier).setOptions(Utils.splitOptions(methodConfiguration.getConfig()));
        }
        catch (Exception e)
        {
            System.out.println("Unexpected exception: " + e);
            e.printStackTrace();
        }

        return classifier;
    }
}
