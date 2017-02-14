package xyz.marcelo.helper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.MethodEvaluation;

@SuppressWarnings("unused")
public class FormatHelper
{
    private static String folder;
    private static MethodConfiguration methodConfig;

    private static double trainTime;
    private static double testTime;
    private static int totalCorrect = 0;
    private static int totalIncorrect = 0;
    private static double totalCorrectPercent = 0;
    private static double totalIncorrectPercent = 0;
    private static int hamCorrect = 0;
    private static int hamIncorrect = 0;
    private static int spamCorrect = 0;
    private static int spamIncorrect = 0;
    private static double hamPrecision = 0;
    private static double hamRecall = 0;
    private static double spamPrecision = 0;
    private static double spamRecall = 0;

    private static final String TEST_TIME = "testTime";
    private static final String TRAIN_TIME = "trainTime";
    private static final String SPAM_RECALL = "spamRecall";
    private static final String HAM_RECALL = "hamRecall";
    private static final String SPAM_PRECISION = "spamPrecision";
    private static final String HAM_PRECISION = "hamPrecision";

    private static Map<String, Map<String, DescriptiveStatistics>> resultKeeper = new LinkedHashMap<>();

    public static boolean handleSingleExperiment(MethodEvaluation methodEvaluation, boolean printPartialResult, boolean tryDetectOutlier) throws Exception
    {
        folder = methodEvaluation.getFolder();

        methodConfig = methodEvaluation.getMethodConfiguration();

        trainTime = (methodEvaluation.getTrainEnd() - methodEvaluation.getTrainStart());

        testTime = (methodEvaluation.getTestEnd() - methodEvaluation.getTestStart());

        String summary = methodEvaluation.getEvaluation().toSummaryString();

        String confusionMatrix = methodEvaluation.getEvaluation().toMatrixString();

        String classDetail = methodEvaluation.getEvaluation().toClassDetailsString();

        String lines[] = (summary + System.lineSeparator() + confusionMatrix + System.lineSeparator() + classDetail).split("\\r?\\n");

        for (String line : lines)
        {
            String cleanLine = line.replaceAll("\\s+", "\t").trim();

            String[] parts = cleanLine.split("\\t");

            if (line.contains("Correctly Classified Instances"))
            {
                totalCorrect = Integer.parseInt(parts[3]);
                totalCorrectPercent = parseDoubleCommaOrPeriod(parts[4]);
            }

            else if (line.contains("Incorrectly Classified Instances"))
            {
                totalIncorrect = Integer.parseInt(parts[3]);
                totalIncorrectPercent = parseDoubleCommaOrPeriod(parts[4]);
            }

            else if (line.contains("|") && line.contains("ham"))
            {
                hamCorrect = Integer.parseInt(parts[0]);
                hamIncorrect = Integer.parseInt(parts[1]);
            }

            else if (line.contains("|") && line.contains("spam"))
            {
                spamCorrect = Integer.parseInt(parts[0]);
                spamIncorrect = Integer.parseInt(parts[1]);
            }

            else if ((line.contains("0.") || line.contains("0,")) && line.contains("ham"))
            {
                hamPrecision = 100 * parseDoubleCommaOrPeriod(parts[4]);
                hamRecall = 100 * parseDoubleCommaOrPeriod(parts[5]);
            }

            else if ((line.contains("0.") || line.contains("0,")) && line.contains("spam"))
            {
                spamPrecision = 100 * parseDoubleCommaOrPeriod(parts[4]);
                spamRecall = 100 * parseDoubleCommaOrPeriod(parts[5]);
            }
        }

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

        if (tryDetectOutlier)
        {
            String outlier = detectOutlier(false);

            if (outlier != null)
            {
                resultKeeper.get(key).get(HAM_PRECISION).removeMostRecentValue();
                resultKeeper.get(key).get(SPAM_PRECISION).removeMostRecentValue();
                resultKeeper.get(key).get(HAM_RECALL).removeMostRecentValue();
                resultKeeper.get(key).get(SPAM_RECALL).removeMostRecentValue();
                resultKeeper.get(key).get(TRAIN_TIME).removeMostRecentValue();
                resultKeeper.get(key).get(TEST_TIME).removeMostRecentValue();
            }

            if (printPartialResult)
            {
                String outlierMessage = (outlier == null ? "" : "Outlier detected (" + outlier + "); rolling back.");
                System.out.println(String.format("%s\t%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%s", folder, methodConfig.name(), hamPrecision, spamPrecision,
                        hamRecall, spamRecall, trainTime, testTime, outlierMessage));
            }

            return (outlier == null);
        }
        else
        {
            if (printPartialResult)
            {
                System.out.println(String.format("%s\t%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t", folder, methodConfig.name(), hamPrecision, spamPrecision,
                        hamRecall, spamRecall, trainTime, testTime));
            }

            return true;
        }
    }

    private static double parseDoubleCommaOrPeriod(String value)
    {
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            return Double.parseDouble(value.replace(',', '.'));
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

    public static String detectOutlier(boolean verbose)
    {
        String key = buildHashMapKey();

        String outlierName = null;

        for (Entry<String, DescriptiveStatistics> entry : resultKeeper.get(key).entrySet())
        {
            // only try to detect outliers in ham and spam precision
            if (!entry.getKey().equals(HAM_PRECISION) && !entry.getKey().equals(SPAM_PRECISION)) continue;
            if (StatHelper.containsOutlier(entry.getValue()))
            {
                outlierName = entry.getKey();
                break;
            }
        }

        return outlierName;
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

    public static void setFolder(String folder)
    {
        FormatHelper.folder = folder;
    }

    public static void setMethodConfig(MethodConfiguration methodConfig)
    {
        FormatHelper.methodConfig = methodConfig;
    }

    public static void setTestTime(double testTime)
    {
        FormatHelper.testTime = testTime;
    }

    public static void setTrainTime(double trainTime)
    {
        FormatHelper.trainTime = trainTime;
    }
}
