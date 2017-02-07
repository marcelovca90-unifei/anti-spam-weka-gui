package xyz.marcelo.method;

import weka.classifiers.Evaluation;

public class MethodEvaluation
{
    private Evaluation evaluation;
    private String folder;
    private MethodConfiguration methodConfiguration;
    private long trainStart;
    private long trainEnd;
    private long testStart;
    private long testEnd;

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

    public void setFolder(String folder)
    {
        this.folder = folder;
    }

    public MethodConfiguration getMethodConfiguration()
    {
        return methodConfiguration;
    }

    public void setMethodConfiguration(MethodConfiguration methodConfiguration)
    {
        this.methodConfiguration = methodConfiguration;
    }

    public long getTrainStart()
    {
        return trainStart;
    }

    public void setTrainStart(long trainStart)
    {
        this.trainStart = trainStart;
    }

    public long getTrainEnd()
    {
        return trainEnd;
    }

    public void setTrainEnd(long trainEnd)
    {
        this.trainEnd = trainEnd;
    }

    public long getTestStart()
    {
        return testStart;
    }

    public void setTestStart(long testStart)
    {
        this.testStart = testStart;
    }

    public long getTestEnd()
    {
        return testEnd;
    }

    public void setTestEnd(long testEnd)
    {
        this.testEnd = testEnd;
    }
}
