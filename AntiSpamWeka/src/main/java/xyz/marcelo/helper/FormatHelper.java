package xyz.marcelo.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.MethodEvaluation;

@SuppressWarnings("unused")
public class FormatHelper
{
    private static String timestamp;
    private static String folder;
    private static MethodConfiguration methodConfiguration;
    private static double totalCorrect;
    private static double totalIncorrect;
    private static double totalCorrectPercent;
    private static double totalIncorrectPercent;
    private static double hamPrecision;
    private static double spamPrecision;
    private static double hamRecall;
    private static double spamRecall;
    private static double hamAreaUnderPRC;
    private static double spamAreaUnderPRC;
    private static double hamAreaUnderROC;
    private static double spamAreaUnderROC;
    private static double trainTime;
    private static double testTime;

    private static final int CLASS_HAM = 0;
    private static final int CLASS_SPAM = 1;

    private static final String HAM_PRECISION = "hamPrecision";
    private static final String SPAM_PRECISION = "spamPrecision";
    private static final String HAM_RECALL = "hamRecall";
    private static final String SPAM_RECALL = "spamRecall";
    private static final String HAM_AREA_UNDER_PRC = "hamAreaUnderPRC";
    private static final String SPAM_AREA_UNDER_PRC = "spamAreaUnderPRC";
    private static final String HAM_AREA_UNDER_ROC = "hamAreaUnderROC";
    private static final String SPAM_AREA_UNDER_ROC = "spamAreaUnderROC";
    private static final String TRAIN_TIME = "trainTime";
    private static final String TEST_TIME = "testTime";

    private static final String[] METRICS = { HAM_PRECISION, SPAM_PRECISION, HAM_RECALL, SPAM_RECALL, HAM_AREA_UNDER_PRC, SPAM_AREA_UNDER_PRC,
            HAM_AREA_UNDER_ROC, SPAM_AREA_UNDER_ROC, TRAIN_TIME, TEST_TIME };

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss.SSS z");

    private static Map<String, Map<String, DescriptiveStatistics>> resultKeeper = new LinkedHashMap<>();

    // compute and persist all metrics' results for a given TimedEvaluation
    public static void computeResults(MethodEvaluation timedEvaluation)
    {
        timestamp = getCurrentDateTime();

        folder = timedEvaluation.getFolder();

        methodConfiguration = timedEvaluation.getMethodConfiguration();

        totalCorrect = timedEvaluation.getEvaluation().correct();
        totalIncorrect = timedEvaluation.getEvaluation().incorrect();

        totalCorrectPercent = 100.0 * timedEvaluation.getEvaluation().pctCorrect();
        totalIncorrectPercent = 100.0 * timedEvaluation.getEvaluation().pctIncorrect();

        hamPrecision = 100.0 * timedEvaluation.getEvaluation().precision(CLASS_HAM);
        spamPrecision = 100.0 * timedEvaluation.getEvaluation().precision(CLASS_SPAM);

        hamRecall = 100.0 * timedEvaluation.getEvaluation().recall(CLASS_HAM);
        spamRecall = 100.0 * timedEvaluation.getEvaluation().recall(CLASS_SPAM);

        hamAreaUnderPRC = 100.0 * timedEvaluation.getEvaluation().areaUnderPRC(CLASS_HAM);
        spamAreaUnderPRC = 100.0 * timedEvaluation.getEvaluation().areaUnderPRC(CLASS_SPAM);

        hamAreaUnderROC = 100.0 * timedEvaluation.getEvaluation().areaUnderROC(CLASS_HAM);
        spamAreaUnderROC = 100.0 * timedEvaluation.getEvaluation().areaUnderROC(CLASS_SPAM);

        trainTime = (timedEvaluation.getTrainEnd() - timedEvaluation.getTrainStart());

        testTime = (timedEvaluation.getTestEnd() - timedEvaluation.getTestStart());

        String key = buildHashMapKey();

        resultKeeper.putIfAbsent(key, new LinkedHashMap<String, DescriptiveStatistics>());

        putValueCreatingKeyIfNotExists(resultKeeper, key, HAM_PRECISION, hamPrecision);
        putValueCreatingKeyIfNotExists(resultKeeper, key, SPAM_PRECISION, spamPrecision);
        putValueCreatingKeyIfNotExists(resultKeeper, key, HAM_RECALL, hamRecall);
        putValueCreatingKeyIfNotExists(resultKeeper, key, SPAM_RECALL, spamRecall);
        putValueCreatingKeyIfNotExists(resultKeeper, key, HAM_AREA_UNDER_PRC, hamAreaUnderPRC);
        putValueCreatingKeyIfNotExists(resultKeeper, key, SPAM_AREA_UNDER_PRC, spamAreaUnderPRC);
        putValueCreatingKeyIfNotExists(resultKeeper, key, HAM_AREA_UNDER_ROC, hamAreaUnderROC);
        putValueCreatingKeyIfNotExists(resultKeeper, key, SPAM_AREA_UNDER_ROC, spamAreaUnderROC);
        putValueCreatingKeyIfNotExists(resultKeeper, key, TRAIN_TIME, trainTime);
        putValueCreatingKeyIfNotExists(resultKeeper, key, TEST_TIME, testTime);
    }

    // displays the experiment's last results OR mean ± standard deviation for every metric
    public static void summarizeResults(boolean displayMeanAndStandardDeviation, boolean formatMilliseconds)
    {
        String key = buildHashMapKey();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s\t", timestamp));
        sb.append(String.format("%s\t", folder));
        sb.append(String.format("%s\t", methodConfiguration.name()));

        for (String metric : METRICS)
        {
            if (!displayMeanAndStandardDeviation)
            {
                double[] values = resultKeeper.get(key).get(metric).getValues();
                if (formatMilliseconds && (metric.equals(TRAIN_TIME) || metric.equals(TEST_TIME)))
                    sb.append(String.format("%s\t", formatMilliseconds(values[values.length - 1])));
                else
                    sb.append(String.format("%.2f\t", values[values.length - 1]));
            }
            else
            {
                double mean = resultKeeper.get(key).get(metric).getMean();
                double standardDeviation = resultKeeper.get(key).get(metric).getStandardDeviation();
                if (formatMilliseconds && (metric.equals(TRAIN_TIME) || metric.equals(TEST_TIME)))
                    sb.append(String.format("%s ± %s\t", formatMilliseconds(mean), formatMilliseconds(standardDeviation)));
                else
                    sb.append(String.format("%.2f ± %.2f\t", mean, standardDeviation));
            }
        }

        System.out.println(sb.toString());
    }

    public static void printHeader()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Timestamp\t");
        sb.append("Path\t");
        sb.append("Method\t");
        sb.append("Ham Precision\t");
        sb.append("Spam Precision\t");
        sb.append("Ham Recall\t");
        sb.append("Spam Recall\t");
        sb.append("Ham Area Under PRC\t");
        sb.append("Spam Area Under PRC\t");
        sb.append("Ham Area Under ROC\t");
        sb.append("Spam Area Under ROC\t");
        sb.append("Train Time\t");
        sb.append("Test Time");

        System.out.println(sb.toString());
    }

    public static void printFooter()
    {
        System.out.println("--------------------------------" + System.lineSeparator());
    }

    private static String buildHashMapKey()
    {
        return methodConfiguration.toString() + "@" + folder;
    }

    private static String formatMilliseconds(double millis)
    {
        return DurationFormatUtils.formatDurationHMS((Double.valueOf(millis)).longValue());
    }

    private static String getCurrentDateTime()
    {
        return ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/Sao_Paulo")).format(FORMATTER);
    }

    private static void putValueCreatingKeyIfNotExists(Map<String, Map<String, DescriptiveStatistics>> map, String outerKey, String innerKey, Double value)
    {
        resultKeeper.putIfAbsent(outerKey, new LinkedHashMap<String, DescriptiveStatistics>());
        resultKeeper.get(outerKey).putIfAbsent(innerKey, new DescriptiveStatistics());
        resultKeeper.get(outerKey).get(innerKey).addValue(value);
    }
}
