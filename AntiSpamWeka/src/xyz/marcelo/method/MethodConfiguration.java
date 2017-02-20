package xyz.marcelo.method;

public enum MethodConfiguration
{
    // http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibSVM.html
    SVM("Support vector machine", "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 40.0 -C 1.0 -E 0.001 -P 0.1 -seed 1"),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/SPegasos.html
    SPEGASOS("Stochastic Primal Estimated sub-GrAdient SOlver for SVM", "-F 0 -L 1.0E-4 -E 500"),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html
    SGD("Stochastic gradient descent", "-F 0 -L 0.01 -R 1.0E-4 -E 500 -C 0.001 -S 1"),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomForest.html
    RF("Random forests", "-P 100 -I 100 -num-slots 1 -K 0 -M 1.0 -V 0.001 -S 1"),

    // http://weka.sourceforge.net/doc.packages/RBFNetwork/weka/classifiers/functions/RBFClassifier.html
    RBF("Radial basis function network", "-N 2 -R 0.01 -L 1.0E-6 -C 2 -P 1 -E 1 -S 1"),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/trees/NBTree.html
    NBTREE("Decision tree with naive Bayes classifiers at the leaves", ""),

    // http://weka.sourceforge.net/doc.packages/multilayerPerceptronCS/weka/classifiers/functions/MultilayerPerceptronCS.html
    MLPCS("Multilayer perceptron with context-sensitive Multiple Task Learning", "-L 0.3 -M 0.2 -N 1000 -V 20 -S 1 -E 50 -H a"),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html
    MLP("Multilayer perceptron", "-L 0.3 -M 0.2 -N 1000 -V 20 -S 1 -E 50 -H a"),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/JRip.html
    JRIP("Repeated Incremental Pruning to Produce Error Reduction", "-F 3 -N 2.0 -O 2 -S 1"),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html
    J48("C4.5 algorithm", "-C 0.25 -M 2 -Q 1"),

    // http://weka.sourceforge.net/doc.packages/hyperPipes/weka/classifiers/misc/HyperPipes.html
    HP("HyperPipe classifier", ""),

    // http://weka.sourceforge.net/packageMetaData/fuzzyUnorderedRuleInduction/index.html
    FURIA("Fuzzy Unordered Rule Induction Algorithm", "-F 3 -N 2.0 -O 2 -S 1 -p 0 -s 0"),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/DTNB.html
    DTNB("Decision Table/Naive Bayes hybrid classifier", "-X 1"),

    // http://weka.sourceforge.net/doc.packages/bestFirstTree/weka/classifiers/trees/BFTree.html
    BFTREE("Best-first tree", "-M 2 -N 5 -C 1.0 -P POSTPRUNED -S 1"),

    // http://weka.sourceforge.net/packageMetaData/AnDE/index.html
    A2DE("Averaged 2-Dependence Estimators", ""),

    // http://weka.sourceforge.net/packageMetaData/AnDE/index.html
    A1DE("Averaged 1-Dependence Estimators", "");

    private final String name;
    private final String config;

    private MethodConfiguration(final String name, final String config)
    {
        this.name = name;
        this.config = config;
    }

    public String getName()
    {
        return name;
    }

    public String getConfig()
    {
        return config;
    }

    public String getPseudoHashCode()
    {
        return (name + config).toUpperCase().replace(" ", "").trim();
    }
}
