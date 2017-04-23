package xyz.marcelo.helper;

import static xyz.marcelo.common.Constants.ALL_METRICS;
import static xyz.marcelo.common.Constants.CLASS_HAM;
import static xyz.marcelo.common.Constants.CLASS_SPAM;
import static xyz.marcelo.common.Constants.METRIC_HAM_AREA_UNDER_PRC;
import static xyz.marcelo.common.Constants.METRIC_HAM_AREA_UNDER_ROC;
import static xyz.marcelo.common.Constants.METRIC_HAM_PRECISION;
import static xyz.marcelo.common.Constants.METRIC_HAM_RECALL;
import static xyz.marcelo.common.Constants.METRIC_SPAM_AREA_UNDER_PRC;
import static xyz.marcelo.common.Constants.METRIC_SPAM_AREA_UNDER_ROC;
import static xyz.marcelo.common.Constants.METRIC_SPAM_PRECISION;
import static xyz.marcelo.common.Constants.METRIC_SPAM_RECALL;
import static xyz.marcelo.common.Constants.METRIC_TEST_TIME;
import static xyz.marcelo.common.Constants.METRIC_TRAIN_TIME;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import xyz.marcelo.common.MethodEvaluation;

public class ResultHelper
{
    private static Map<String, List<Double>> results = new LinkedHashMap<>();

    // clears the data in result keeper
    public static void reset()
    {
        results.clear();
    }

    // compute and persist all metrics' results for a given TimedEvaluation
    @SuppressWarnings("unused")
    public static void computeSingleRunResults(MethodEvaluation timedEvaluation)
    {
        Double totalCorrect = timedEvaluation.getEvaluation().correct();
        Double totalIncorrect = timedEvaluation.getEvaluation().incorrect();

        Double totalCorrectPercent = 100.0 * timedEvaluation.getEvaluation().pctCorrect();
        Double totalIncorrectPercent = 100.0 * timedEvaluation.getEvaluation().pctIncorrect();

        Double hamPrecision = 100.0 * timedEvaluation.getEvaluation().precision(CLASS_HAM);
        Double spamPrecision = 100.0 * timedEvaluation.getEvaluation().precision(CLASS_SPAM);

        Double hamRecall = 100.0 * timedEvaluation.getEvaluation().recall(CLASS_HAM);
        Double spamRecall = 100.0 * timedEvaluation.getEvaluation().recall(CLASS_SPAM);

        Double hamAreaUnderPRC = 100.0 * timedEvaluation.getEvaluation().areaUnderPRC(CLASS_HAM);
        Double spamAreaUnderPRC = 100.0 * timedEvaluation.getEvaluation().areaUnderPRC(CLASS_SPAM);

        Double hamAreaUnderROC = 100.0 * timedEvaluation.getEvaluation().areaUnderROC(CLASS_HAM);
        Double spamAreaUnderROC = 100.0 * timedEvaluation.getEvaluation().areaUnderROC(CLASS_SPAM);

        Double trainTime = (double) (timedEvaluation.getTrainEnd() - timedEvaluation.getTrainStart());

        Double testTime = (double) (timedEvaluation.getTestEnd() - timedEvaluation.getTestStart());

        addSingleRunResult(METRIC_HAM_PRECISION, hamPrecision);
        addSingleRunResult(METRIC_SPAM_PRECISION, spamPrecision);
        addSingleRunResult(METRIC_HAM_RECALL, hamRecall);
        addSingleRunResult(METRIC_SPAM_RECALL, spamRecall);
        addSingleRunResult(METRIC_HAM_AREA_UNDER_PRC, hamAreaUnderPRC);
        addSingleRunResult(METRIC_SPAM_AREA_UNDER_PRC, spamAreaUnderPRC);
        addSingleRunResult(METRIC_HAM_AREA_UNDER_ROC, hamAreaUnderROC);
        addSingleRunResult(METRIC_SPAM_AREA_UNDER_ROC, spamAreaUnderROC);
        addSingleRunResult(METRIC_TRAIN_TIME, trainTime);
        addSingleRunResult(METRIC_TEST_TIME, testTime);
    }

    // detects, removes and returns the number of outliers in the result keeper
    // http://www.itl.nist.gov/div898/handbook/eda/section3/eda35h.htm
    public static int detectAndRemoveOutliers(boolean debugOutliers)
    {
        // detect outlier(s) for each metric
        Set<Integer> outlierIndices = new TreeSet<>();
        for (String metric : ALL_METRICS)
        {
            DescriptiveStatistics stats = doubleArrayToDescriptiveStatistics(results.get(metric));
            double mean = stats.getMean();
            double standardDeviation = stats.getStandardDeviation();
            double median = stats.getPercentile(50);
            for (int i = 0; i < stats.getValues().length; i++)
            {
                double value = stats.getElement(i);
                double zScore = (value - mean) / standardDeviation;
                double modifiedZScore = 0.6745 * (value - median) / getMedianAbsoluteDeviation(stats);
                if (Math.abs(zScore) > 3 || Math.abs(modifiedZScore) > 3.5)
                {
                    outlierIndices.add(i);
                }
            }
        }

        // remove outlier(s) from result keeper, if any
        if (!outlierIndices.isEmpty())
        {
            Map<String, List<Double>> filteredResults = new LinkedHashMap<>();
            for (String key : results.keySet())
            {
                filteredResults.put(key, new LinkedList<>());
                for (int i = 0; i < results.get(key).size(); i++)
                {
                    if (!outlierIndices.contains(i))
                    {
                        filteredResults.get(key).add(results.get(key).get(i));
                    }
                }
            }

            results = filteredResults;

            if (debugOutliers)
            {
                String outlierMessage = "%s\t%d outliers found in last batch; rolling back iterations %s ...";
                System.out.println(String.format(outlierMessage, FormatHelper.getCurrentDateTime(), outlierIndices.size(), outlierIndices));
            }
        }

        // returns the number of detected outliers (if any)
        return outlierIndices.size();
    }

    // converts the double resuls for each metric to analog descriptive statistics
    protected static Map<String, DescriptiveStatistics> getMetricsToDescriptiveStatisticsMap()
    {
        Map<String, DescriptiveStatistics> statistics = new LinkedHashMap<>();

        results.forEach((k, v) -> statistics.put(k, doubleArrayToDescriptiveStatistics(v)));

        return statistics;
    }

    private static void addSingleRunResult(String key, Double value)
    {
        results.putIfAbsent(key, new LinkedList<>());
        results.get(key).add(value);
    }

    private static DescriptiveStatistics doubleArrayToDescriptiveStatistics(List<Double> values)
    {
        return new DescriptiveStatistics(ArrayUtils.toPrimitive(values.toArray(new Double[0])));
    }

    private static double getMedianAbsoluteDeviation(DescriptiveStatistics stats)
    {
        double median = stats.getPercentile(50);
        DescriptiveStatistics mads = new DescriptiveStatistics();
        for (double v : stats.getValues())
            mads.addValue(Math.abs(v - median));
        return mads.getPercentile(50);
    }
}
