package xyz.marcelo.common;

import java.io.File;

import org.pmw.tinylog.Logger;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public final class MethodEvaluation
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

        this.methodConfiguration = methodConfiguration;
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
            Logger.error("Unexpected exception: " + e);
        }
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
            Logger.error("Unexpected exception: " + e);
        }
    }
}
