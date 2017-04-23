package xyz.marcelo.common;

public class Constants
{
    // Command-line options
    public static final String OPTION_METADATA = "metadata";
    public static final String OPTION_METHOD = "method";
    public static final String OPTION_RUNS = "runs";
    public static final String OPTION_SKIP_TRAIN = "skipTrain";
    public static final String OPTION_SKIP_TEST = "skipTest";
    public static final String OPTION_SHRINK_FEATURES = "shrinkFeatures";
    public static final String OPTION_BALANCE_CLASSES = "balanceClasses";
    public static final String OPTION_TEST_EMPTY = "testEmpty";
    public static final String OPTION_SAVE_MODEL = "saveModel";

    // Metrics
    public static final String METRIC_HAM_PRECISION = "hamPrecision";
    public static final String METRIC_SPAM_PRECISION = "spamPrecision";
    public static final String METRIC_HAM_RECALL = "hamRecall";
    public static final String METRIC_SPAM_RECALL = "spamRecall";
    public static final String METRIC_HAM_AREA_UNDER_PRC = "hamAreaUnderPRC";
    public static final String METRIC_SPAM_AREA_UNDER_PRC = "spamAreaUnderPRC";
    public static final String METRIC_HAM_AREA_UNDER_ROC = "hamAreaUnderROC";
    public static final String METRIC_SPAM_AREA_UNDER_ROC = "spamAreaUnderROC";
    public static final String METRIC_TRAIN_TIME = "trainTime";
    public static final String METRIC_TEST_TIME = "testTime";

    public static final String[] ALL_METRICS = { METRIC_HAM_PRECISION, METRIC_SPAM_PRECISION, METRIC_HAM_RECALL, METRIC_SPAM_RECALL, METRIC_HAM_AREA_UNDER_PRC,
            METRIC_SPAM_AREA_UNDER_PRC, METRIC_HAM_AREA_UNDER_ROC, METRIC_SPAM_AREA_UNDER_ROC, METRIC_TRAIN_TIME, METRIC_TEST_TIME };

    // Class tags
    public static final int CLASS_HAM = 0;
    public static final int CLASS_SPAM = 1;

    // IO tags
    public static final String TAG_HAM = "ham";
    public static final String TAG_SPAM = "spam";
    public static final String TAG_CLASS = "class";
}
