package xyz.marcelo.common;

public class Constants
{

    public static final String OPTION_METADATA = "metadata";
    public static final String OPTION_METHOD = "method";
    public static final String OPTION_RUNS = "runs";
    public static final String OPTION_SKIP_TRAIN = "skipTrain";
    public static final String OPTION_SKIP_TEST = "skipTest";
    public static final String OPTION_SHRINK_FEATURES = "shrinkFeatures";
    public static final String OPTION_BALANCE_CLASSES = "balanceClasses";
    public static final String OPTION_TEST_EMPTY = "testEmpty";
    public static final String OPTION_SAVE_MODEL = "saveModel";

    public static final String HAM_PRECISION = "hamPrecision";
    public static final String SPAM_PRECISION = "spamPrecision";
    public static final String HAM_RECALL = "hamRecall";
    public static final String SPAM_RECALL = "spamRecall";
    public static final String HAM_AREA_UNDER_PRC = "hamAreaUnderPRC";
    public static final String SPAM_AREA_UNDER_PRC = "spamAreaUnderPRC";
    public static final String HAM_AREA_UNDER_ROC = "hamAreaUnderROC";
    public static final String SPAM_AREA_UNDER_ROC = "spamAreaUnderROC";
    public static final String TRAIN_TIME = "trainTime";
    public static final String TEST_TIME = "testTime";

    public static final String[] METRICS = { HAM_PRECISION, SPAM_PRECISION, HAM_RECALL, SPAM_RECALL, HAM_AREA_UNDER_PRC, SPAM_AREA_UNDER_PRC,
            HAM_AREA_UNDER_ROC, SPAM_AREA_UNDER_ROC, TRAIN_TIME, TEST_TIME };

    public static final String TAG_HAM = "ham";
    public static final String TAG_SPAM = "spam";
    public static final String TAG_CLASS = "class";

    public static final int CLASS_HAM = 0;
    public static final int CLASS_SPAM = 1;
}
