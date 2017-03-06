package xyz.marcelo.eval;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import xyz.marcelo.helper.DataSetHelper;
import xyz.marcelo.helper.MethodHelper;

public class TimedEvaluation
{
    private Classifier classifier;
    private Evaluation evaluation;
    private String folder;
    private MethodHelper methodConfiguration;
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

    public MethodHelper getMethodConfiguration()
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

    public TimedEvaluation(String folder, MethodHelper methodConfiguration)
    {
        this.folder = DataSetHelper.getDataSetNameFromFolder(folder);
        this.methodConfiguration = methodConfiguration;
    }

    public void run(Instances trainSet, Instances testSet)
    {
        try
        {
            // train the classifier
            trainStart = System.currentTimeMillis();
            classifier.buildClassifier(trainSet);
            trainEnd = System.currentTimeMillis();

            // test the classifier
            testStart = System.currentTimeMillis();
            evaluation.evaluateModel(classifier, testSet);
            testEnd = System.currentTimeMillis();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
