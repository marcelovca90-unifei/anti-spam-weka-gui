package xyz.marcelo.helper;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import weka.classifiers.Evaluation;
import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.TimedEvaluation;

@SuppressWarnings("unused")
public class FormatHelper
{
    private static String folder;
    private static MethodConfiguration methodConfig;

    private static double trainTime = 0;
    private static double testTime = 0;
    private static double totalCorrect = 0;
    private static double totalIncorrect = 0;
    private static double totalCorrectPercent = 0;
    private static double totalIncorrectPercent = 0;
    private static double hamPrecision = 0;
    private static double hamRecall = 0;
    private static double spamPrecision = 0;
    private static double spamRecall = 0;

    private static final int CLASS_HAM = 0;
    private static final int CLASS_SPAM = 1;
    private static final String TRAIN_TIME = "trainTime";
    private static final String TEST_TIME = "testTime";
    private static final String HAM_RECALL = "hamRecall";
    private static final String SPAM_RECALL = "spamRecall";
    private static final String HAM_PRECISION = "hamPrecision";
    private static final String SPAM_PRECISION = "spamPrecision";

    private static Map<String, Map<String, DescriptiveStatistics>> resultKeeper = new LinkedHashMap<>();

    public static void handleSingleExperiment(TimedEvaluation methodEvaluation, boolean printPartialResult) throws Exception
    {
        folder = methodEvaluation.getFolder();

        methodConfig = methodEvaluation.getMethodConfiguration();

        trainTime = (methodEvaluation.getTrainEnd() - methodEvaluation.getTrainStart());

        testTime = (methodEvaluation.getTestEnd() - methodEvaluation.getTestStart());

        Evaluation evaluation = methodEvaluation.getEvaluation();

        totalCorrect = evaluation.correct();
        totalCorrectPercent = 100.0 * evaluation.pctCorrect();

        totalIncorrect = evaluation.incorrect();
        totalIncorrectPercent = 100.0 * evaluation.pctIncorrect();

        hamPrecision = 100.0 * evaluation.precision(CLASS_HAM);
        hamRecall = 100.0 * evaluation.recall(CLASS_HAM);

        spamPrecision = 100.0 * evaluation.precision(CLASS_SPAM);
        spamRecall = 100.0 * evaluation.recall(CLASS_SPAM);

        String key = buildHashMapKey();

        if (!resultKeeper.containsKey(key))
        {
            resultKeeper.put(key, new LinkedHashMap<String, DescriptiveStatistics>());
        }

        putValueAndCreatingKeysIfNotPresent(resultKeeper, key, HAM_PRECISION, hamPrecision);
        putValueAndCreatingKeysIfNotPresent(resultKeeper, key, SPAM_PRECISION, spamPrecision);
        putValueAndCreatingKeysIfNotPresent(resultKeeper, key, HAM_RECALL, hamRecall);
        putValueAndCreatingKeysIfNotPresent(resultKeeper, key, SPAM_RECALL, spamRecall);
        putValueAndCreatingKeysIfNotPresent(resultKeeper, key, TRAIN_TIME, trainTime);
        putValueAndCreatingKeysIfNotPresent(resultKeeper, key, TEST_TIME, testTime);

        if (printPartialResult)
        {
            System.out.println(String.format("%s\t%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t", folder, methodConfig.name(), hamPrecision, spamPrecision,
                    hamRecall, spamRecall, trainTime, testTime));
        }
    }

    private static String buildHashMapKey()
    {
        return folder + "\t" + methodConfig.getPseudoHashCode();
    }

    public static void putValueAndCreatingKeysIfNotPresent(Map<String, Map<String, DescriptiveStatistics>> map, String outerKey, String innerKey, Double value)
    {
        if (!resultKeeper.containsKey(outerKey))
        {
            resultKeeper.put(outerKey, new LinkedHashMap<String, DescriptiveStatistics>());
        }
        if (!resultKeeper.get(outerKey).containsKey(innerKey))
        {
            resultKeeper.get(outerKey).put(innerKey, new DescriptiveStatistics());
        }
        resultKeeper.get(outerKey).get(innerKey).addValue(value);
    }

    public static void printHeader()
    {
        System.out.println("PATH\tMTHD\tHP\tSP\tHR\tSR\tTrTi\tTeTi");
    }

    public static void printFooter()
    {
        System.out.println("--------------------------------" + System.lineSeparator());
    }

    public static void handleAllExperiments()
    {
        String key = buildHashMapKey();

        System.out.println(String.format("%s\t%s\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f", folder, methodConfig.name(),
                resultKeeper.get(key).get(HAM_PRECISION).getMean(), resultKeeper.get(key).get(HAM_PRECISION).getStandardDeviation(),
                resultKeeper.get(key).get(SPAM_PRECISION).getMean(), resultKeeper.get(key).get(SPAM_PRECISION).getStandardDeviation(),
                resultKeeper.get(key).get(HAM_RECALL).getMean(), resultKeeper.get(key).get(HAM_RECALL).getStandardDeviation(),
                resultKeeper.get(key).get(SPAM_RECALL).getMean(), resultKeeper.get(key).get(SPAM_RECALL).getStandardDeviation(),
                resultKeeper.get(key).get(TRAIN_TIME).getMean(), resultKeeper.get(key).get(TRAIN_TIME).getStandardDeviation(),
                resultKeeper.get(key).get(TEST_TIME).getMean(), resultKeeper.get(key).get(TEST_TIME).getStandardDeviation()));

        resultKeeper.clear();
    }
}
