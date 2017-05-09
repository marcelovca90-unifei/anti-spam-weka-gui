package xyz.marcelo.common;

public final class Constants
{
    public enum CLIOption
    {
        METADATA,
        METHOD,
        RUNS,
        SKIP_TRAIN,
        SKIP_TEST,
        SHRINK_FEATURES,
        BALANCE_CLASSES,
        TEST_EMPTY,
        SAVE_MODEL,
        SAVE_SETS;
    }

    public enum Metric
    {
        HAM_PRECISION,
        SPAM_PRECISION,
        HAM_RECALL,
        SPAM_RECALL,
        HAM_AREA_UNDER_PRC,
        SPAM_AREA_UNDER_PRC,
        HAM_AREA_UNDER_ROC,
        SPAM_AREA_UNDER_ROC,
        TRAIN_TIME,
        TEST_TIME
    }

    public enum MessageType
    {
        HAM,
        SPAM
    }
}
