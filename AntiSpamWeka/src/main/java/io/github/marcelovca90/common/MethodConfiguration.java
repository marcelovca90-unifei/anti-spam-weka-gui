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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Utils;

public enum MethodConfiguration
{
    // polynomial kernel, G {-1, 0, +1}, C {0.1, 1.0, 10.0}
    LIBSVM_POLY_G_neg_C_small("Support vector machine", "-S 0 -K 1 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_zero_C_small("Support vector machine", "-S 0 -K 1 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_pos_C_small("Support vector machine", "-S 0 -K 1 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_neg_C_med("Support vector machine", "-S 0 -K 1 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_zero_C_med("Support vector machine", "-S 0 -K 1 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_pos_C_med("Support vector machine", "-S 0 -K 1 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_neg_C_high("Support vector machine", "-S 0 -K 1 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_zero_C_high("Support vector machine", "-S 0 -K 1 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_POLY_G_pos_C_high("Support vector machine", "-S 0 -K 1 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),

    // radial basis function kernel, G {-1, 0, +1}, C {0.1, 1.0, 10.0}
    LIBSVM_RBF_G_neg_C_small("Support vector machine", "-S 0 -K 2 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_zero_C_small("Support vector machine", "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_pos_C_small("Support vector machine", "-S 0 -K 2 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_neg_C_med("Support vector machine", "-S 0 -K 2 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_zero_C_med("Support vector machine", "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_pos_C_med("Support vector machine", "-S 0 -K 2 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_neg_C_high("Support vector machine", "-S 0 -K 2 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_zero_C_high("Support vector machine", "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_RBF_G_pos_C_high("Support vector machine", "-S 0 -K 2 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),

    // sigmoid kernel, G {-1, 0, +1}, C {0.1, 1.0, 10.0}
    LIBSVM_SIG_G_neg_C_small("Support vector machine", "-S 0 -K 3 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_zero_C_small("Support vector machine", "-S 0 -K 3 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_pos_C_small("Support vector machine", "-S 0 -K 3 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 0.1 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_neg_C_med("Support vector machine", "-S 0 -K 3 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_zero_C_med("Support vector machine", "-S 0 -K 3 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_pos_C_med("Support vector machine", "-S 0 -K 3 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 1.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_neg_C_high("Support vector machine", "-S 0 -K 3 -D 3 -G -1.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_zero_C_high("Support vector machine", "-S 0 -K 3 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class),
    LIBSVM_SIG_G_pos_C_high("Support vector machine", "-S 0 -K 3 -D 3 -G 1.0 -R 0.0 -N 0.5 -M 2048.0 -C 10.0 -E 0.001 -P 0.1 -H -seed 1", LibSVM.class);

    private static final Logger LOGGER = LogManager.getLogger(MethodConfiguration.class);

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
            LOGGER.error(e);
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

