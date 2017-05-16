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
package xyz.marcelo.common;

import java.io.File;

import org.pmw.tinylog.Logger;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class MethodEvaluation
{
    private Classifier classifier;
    private Evaluation evaluation;
    private String folder;
    private String dataSetName;
    private String statMethod;
    private int numberOfTotalFeatures;
    private int numberOfActualFeatures;
    private MethodConfiguration methodConfiguration;
    private long trainStart;
    private long trainEnd;
    private long testStart;
    private long testEnd;

    public Classifier getClassifier()
    {
        return classifier;
    }

    public void setClassifier(Classifier classifier)
    {
        this.classifier = classifier;
    }

    public Evaluation getEvaluation()
    {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation)
    {
        this.evaluation = evaluation;
    }

    public String getFolder()
    {
        return folder;
    }

    public String getDataSetName()
    {
        return dataSetName;
    }

    public String getStatMethod()
    {
        return statMethod;
    }

    public int getNumberOfTotalFeatures()
    {
        return numberOfTotalFeatures;
    }

    public void setNumberOfTotalFeatures(int numberOfTotalFeatures)
    {
        this.numberOfTotalFeatures = numberOfTotalFeatures;
    }

    public int getNumberOfActualFeatures()
    {
        return numberOfActualFeatures;
    }

    public void setNumberOfActualFeatures(int numberOfActualFeatures)
    {
        this.numberOfActualFeatures = numberOfActualFeatures;
    }

    public MethodConfiguration getMethodConfiguration()
    {
        return methodConfiguration;
    }

    public long getTrainStart()
    {
        return trainStart;
    }

    public long getTrainEnd()
    {
        return trainEnd;
    }

    public long getTestStart()
    {
        return testStart;
    }

    public long getTestEnd()
    {
        return testEnd;
    }

    public MethodEvaluation(String folder, MethodConfiguration methodConfiguration)
    {
        this.folder = folder;

        String[] parts = folder.split("\\" + File.separator);
        this.dataSetName = parts[parts.length - 3];
        this.statMethod = parts[parts.length - 2];
        this.numberOfTotalFeatures = Integer.parseInt(parts[parts.length - 1]);
        this.numberOfActualFeatures = numberOfTotalFeatures;

        this.methodConfiguration = methodConfiguration;
    }

    public void train(Instances trainSet)
    {
        try
        {
            // train the classifier
            trainStart = System.currentTimeMillis();
            classifier.buildClassifier(trainSet);
            trainEnd = System.currentTimeMillis();
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }
    }

    public void test(Instances testSet)
    {
        try
        {
            // test the classifier
            testStart = System.currentTimeMillis();
            evaluation.evaluateModel(classifier, testSet);
            testEnd = System.currentTimeMillis();
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }
    }
}
