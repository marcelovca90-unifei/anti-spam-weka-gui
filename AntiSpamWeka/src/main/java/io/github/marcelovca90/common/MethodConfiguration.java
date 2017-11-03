/*******************************************************************************
 * Copyright (C) 2017 Marcelo Vinícius Cysneiros Aragão
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package io.github.marcelovca90.common;

import org.pmw.tinylog.Logger;

import hr.irb.fastRandomForest.FastRandomForest;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.AveragedNDependenceEstimators.A1DE;
import weka.classifiers.bayes.AveragedNDependenceEstimators.A2DE;
import weka.classifiers.functions.LibLINEAR;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.functions.SGD;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SPegasos;
import weka.classifiers.lazy.IBk;
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

    // http://weka.sourceforge.net/doc.packages/simpleCART/weka/classifiers/trees/SimpleCart.html
    CART("Classification And Regression Tree", "-M 2.0 -N 5 -C 1.0 -S 1", weka.classifiers.trees.SimpleCart.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/DTNB.html
    DTNB("Decision Table/Naive Bayes hybrid classifier", "-X 1", DTNB.class),

    // http://weka.sourceforge.net/packageMetaData/fuzzyUnorderedRuleInduction/index.html
    FURIA("Fuzzy Unordered Rule Induction Algorithm", "-F 3 -N 2.0 -O 2 -S 1 -p 0 -s 0", FURIA.class),

    // https://github.com/fracpete/fastrandomforest-weka-package
    FRF("Fast random forests", "-I 100 -K 0 -S 1", FastRandomForest.class),

    // http://weka.sourceforge.net/doc.packages/hyperPipes/weka/classifiers/misc/HyperPipes.html
    HP("HyperPipe classifier", "", HyperPipes.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/lazy/IBk.html
    IBK("K-nearest neighbours classifier", "-K 1 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\"", IBk.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html
    J48("C4.5 decision tree", "-C 0.25 -M 2 -Q 1", J48.class),

    // http://weka.sourceforge.net/packageMetaData/J48Consolidated/index.html
    J48C("C4.5 consolidated decision tree", "-C 0.25 -M 2 -Q 1 -RM-C -RM-N 99.0 -RM-B -2 -RM-D 50.0", J48Consolidated.class),

    // http://weka.sourceforge.net/doc.packages/J48graft/weka/classifiers/trees/J48graft.html
    J48G("C4.5 grafted decision tree", "-C 0.25 -M 2 -Q 1", J48graft.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/rules/JRip.html
    JRIP("Repeated Incremental Pruning to Produce Error Reduction", "-F 3 -N 2.0 -O 2 -S 1", JRip.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibLINEAR.html
    LIBLINEAR("Large linear classifier", "-S 1 -C 1.0 -E 0.001 -B 1.0 -L 0.1 -I 1000", LibLINEAR.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/LibSVM.html
    LIBSVM("Support vector machine", "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 1024.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html
    MLP("Multilayer perceptron", "-L 0.3 -M 0.2 -N 500 -V 33 -S 1 -E 20 -H a", 0.6, MultilayerPerceptron.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/bayes/NaiveBayes.html
    NB("Naive Bayes", "", NaiveBayes.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/trees/NBTree.html
    NBTREE("Decision tree with naive Bayes classifiers at the leaves", "", NBTree.class),

    // http://weka.sourceforge.net/doc.packages/RBFNetwork/weka/classifiers/functions/RBFNetwork.html
    RBF("Radial basis function network", "-B 2 -S 1 -R 1.0E-8 -M -1 -W 0.1", RBFNetwork.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomTree.html
    RT("Random tree", "-K 0 -M 1.0 -V 0.001 -S 1", RandomTree.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SGD.html
    SGD("Stochastic gradient descent", "-F 0 -L 0.01 -R 1.0E-4 -E 500 -C 0.001 -S 1", SGD.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SMO.html
    SMO("Sequential minimal optimization algorithm",
            "-C 1.0 -L 1.0E-3 -P 1.0E-12 -N 2 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -E 1.0 -C 0\" -calibrator \"weka.classifiers.functions.Logistic -R 1.0E-8 -M -1 -num-decimal-places 4\"", SMO.class),

    // http://weka.sourceforge.net/doc.stable/weka/classifiers/functions/SPegasos.html
    SPEGASOS("Stochastic Primal Estimated sub-GrAdient SOlver for SVM", "-F 0 -L 1.0E-4 -E 500", SPegasos.class),

    // http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/RandomForest.html
    WRF("Weka random forests", "-P 100 -I 100 -num-slots 0 -K 0 -M 1.0 -V 0.001 -S 1", RandomForest.class);

    // dynamically instantiates a classifier for the given method configuration
    public static AbstractClassifier buildClassifierFor(MethodConfiguration methodConfiguration)
    {
        AbstractClassifier classifier = null;

        try
        {
            classifier = methodConfiguration.getClazz().newInstance();

            classifier.setOptions(Utils.splitOptions(methodConfiguration.getConfig()));
        }
        catch (Exception e)
        {
            Logger.error(e);
        }

        return classifier;
    }

    private final Class<? extends AbstractClassifier> clazz;
    private final String config;
    private final String name;

    private final double splitPercent;

    private MethodConfiguration(final String name, final String config, final Class<? extends AbstractClassifier> clazz)
    {
        this.name = name;
        this.config = config;
        this.splitPercent = 0.5;
        this.clazz = clazz;
    }

    private MethodConfiguration(final String name, final String config, final double splitPercent, final Class<? extends AbstractClassifier> clazz)
    {
        this.name = name;
        this.config = config;
        this.splitPercent = splitPercent;
        this.clazz = clazz;
    }

    public Class<? extends AbstractClassifier> getClazz()
    {
        return clazz;
    }

    public String getConfig()
    {
        return config;
    }

    public String getName()
    {
        return name;
    }

    public double getSplitPercent()
    {
        return splitPercent;
    }
}
