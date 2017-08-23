/*******************************************************************************
 * Copyright (C) 2017 Marcelo Vinícius Cysneiros Aragão
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package io.github.marcelovca90.helper;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Logger;

import io.github.marcelovca90.common.Constants.MessageType;
import io.github.marcelovca90.common.Constants.Metric;
import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.common.MethodEvaluation;

public class ExperimentHelper
{
    private Map<Metric, List<Double>> resultHistory = new EnumMap<>(Metric.class);

    // clears the data in result keeper
    public void clearResultHistory()
    {
        resultHistory.clear();
    }

    // compute and persist all metrics' resultHistory for a given MethodEvaluation
    public void computeSingleRunResults(MethodEvaluation methodEvaluation)
    {
        Double hamPrecision = 100.0 * methodEvaluation.getEvaluation().precision(MessageType.HAM.ordinal());
        Double spamPrecision = 100.0 * methodEvaluation.getEvaluation().precision(MessageType.SPAM.ordinal());

        Double hamRecall = 100.0 * methodEvaluation.getEvaluation().recall(MessageType.HAM.ordinal());
        Double spamRecall = 100.0 * methodEvaluation.getEvaluation().recall(MessageType.SPAM.ordinal());

        Double hamAreaUnderPRC = 100.0 * methodEvaluation.getEvaluation().areaUnderPRC(MessageType.HAM.ordinal());
        Double spamAreaUnderPRC = 100.0 * methodEvaluation.getEvaluation().areaUnderPRC(MessageType.SPAM.ordinal());

        Double hamAreaUnderROC = 100.0 * methodEvaluation.getEvaluation().areaUnderROC(MessageType.HAM.ordinal());
        Double spamAreaUnderROC = 100.0 * methodEvaluation.getEvaluation().areaUnderROC(MessageType.SPAM.ordinal());

        Double trainTime = (double) (methodEvaluation.getTrainEnd() - methodEvaluation.getTrainStart());

        Double testTime = (double) (methodEvaluation.getTestEnd() - methodEvaluation.getTestStart());

        addSingleRunResult(Metric.HAM_PRECISION, hamPrecision);
        addSingleRunResult(Metric.SPAM_PRECISION, spamPrecision);
        addSingleRunResult(Metric.HAM_RECALL, hamRecall);
        addSingleRunResult(Metric.SPAM_RECALL, spamRecall);
        addSingleRunResult(Metric.HAM_AREA_UNDER_PRC, hamAreaUnderPRC);
        addSingleRunResult(Metric.SPAM_AREA_UNDER_PRC, spamAreaUnderPRC);
        addSingleRunResult(Metric.HAM_AREA_UNDER_ROC, hamAreaUnderROC);
        addSingleRunResult(Metric.SPAM_AREA_UNDER_ROC, spamAreaUnderROC);
        addSingleRunResult(Metric.TRAIN_TIME, trainTime);
        addSingleRunResult(Metric.TEST_TIME, testTime);
    }

    // detects, removes and return the amount of outliers in the result keeper, if any
    public int detectAndRemoveOutliers()
    {
        return removeOutliers(detectOutliers());
    }

    public void printHeader()
    {
        String[] fullMetricNames = new String[]
        {
                "Timestamp", "Data Set", "Statistics Method", "Number of Features",
                "Ham Precision", "Spam Precision", "Ham Recall", "Spam Recall",
                "Ham Area Under PRC", "Spam Area Under PRC", "Ham Area Under ROC", "Spam Area Under ROC",
                "Train Time", "Test Time"
        };

        String header = Arrays.stream(fullMetricNames).collect(Collectors.joining("\t"));

        Logger.info(header);
    }

    // displays the experiment's [last resultHistory] or [mean ± standard deviation] for every metric
    public void summarizeResults(Map<Metric, DescriptiveStatistics> results, MethodEvaluation methodEvaluation, boolean printStats, boolean formatMillis)
    {
        MethodConfiguration methodConfiguration = methodEvaluation.getMethodConfiguration();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s\t", methodEvaluation.getDataSetName()));
        sb.append(String.format("%s\t", methodEvaluation.getStatMethod()));
        sb.append(String.format("%d->%d\t", methodEvaluation.getNumberOfTotalFeatures(), methodEvaluation.getNumberOfActualFeatures()));
        sb.append(String.format("%s\t", methodConfiguration.name()));

        for (Metric metric : Metric.values())
        {
            if (!printStats)
                buildResultLineWithoutStats(results, formatMillis, sb, metric);
            else
                buildResultLineWithStats(results, formatMillis, sb, metric);
        }

        if (!printStats)
            Logger.debug(sb.toString());
        else
            Logger.info(sb.toString());
    }

    // displays the experiment's [last resultHistory] or [mean ± standard deviation] for every metric
    public void summarizeResults(MethodEvaluation methodEvaluation, boolean printStats, boolean formatMillis)
    {
        summarizeResults(getMetricsToDescriptiveStatisticsMap(), methodEvaluation, printStats, formatMillis);
    }

    private void addSingleRunResult(Metric key, Double value)
    {
        resultHistory.putIfAbsent(key, new LinkedList<>());
        resultHistory.get(key).add(value);
    }

    private void buildResultLineWithoutStats(Map<Metric, DescriptiveStatistics> results, boolean formatMillis, StringBuilder sb, Metric metric)
    {
        double[] values = results.get(metric).getValues();
        if (formatMillis && (metric == Metric.TRAIN_TIME || metric == Metric.TEST_TIME))
            sb.append(String.format("%s\t", formatMilliseconds(values[values.length - 1])));
        else
            sb.append(String.format("%.2f\t", values[values.length - 1]));
    }

    private void buildResultLineWithStats(Map<Metric, DescriptiveStatistics> results, boolean formatMillis, StringBuilder sb, Metric metric)
    {
        double mean = results.get(metric).getMean();
        double standardDeviation = results.get(metric).getStandardDeviation();
        if (formatMillis && (metric == Metric.TRAIN_TIME || metric == Metric.TEST_TIME))
            sb.append(String.format("%s ± %s\t", formatMilliseconds(mean), formatMilliseconds(standardDeviation)));
        else
            sb.append(String.format("%.2f ± %.2f\t", mean, standardDeviation));
    }

    // detects and returns the indices of outliers in the result keeper, if any. references:
    // http://www.itl.nist.gov/div898/handbook/eda/section3/eda35h.htm
    // https://commons.wikimedia.org/wiki/File:Normal_distribution_and_scales.gif
    // http://colingorrie.github.io/outlier-detection.html
    private Set<Integer> detectOutliers()
    {
        // set to keep the outlier(s) index(ices)
        Set<Integer> outlierIndices = new TreeSet<>();

        // detect outlier(s) for each metric
        for (Metric metric : Metric.values())
        {
            DescriptiveStatistics stats = doubleArrayToDescriptiveStatistics(resultHistory.get(metric));

            for (int i = 0; i < stats.getValues().length; i++)
            {
                double value = stats.getValues()[i];

                // if the current value extrapolates any of the three thresholds, then it is considered an outlier
                if (isOutlierByZScore(stats, value))
                {
                    Logger.trace("Outlier detected by Z-Score\tMetric={}\tIndex={}\tValue={}", metric, i, value);
                    outlierIndices.add(i);
                }
                else if (isOutlierByModifiedZScore(stats, value))
                {
                    Logger.trace("Outlier detected by Modified Z-Score\tMetric={}\tIndex={}\tValue={}", metric, i, value);
                    outlierIndices.add(i);
                }
                else if (isOutlierByInterquartileRange(stats, value))
                {
                    Logger.trace("Outlier detected by Interquartile Range\tMetric={}\tIndex={}\tValue={}", metric, i, value);
                    outlierIndices.add(i);
                }
            }
        }

        return outlierIndices;
    }

    private DescriptiveStatistics doubleArrayToDescriptiveStatistics(List<Double> values)
    {
        return new DescriptiveStatistics(ArrayUtils.toPrimitive(values.toArray(new Double[0])));
    }

    private String formatMilliseconds(double millis)
    {
        return DurationFormatUtils.formatDurationHMS((Double.valueOf(Math.abs(millis))).longValue());
    }

    // converts the double resuls for each metric to analog descriptive statistics
    private Map<Metric, DescriptiveStatistics> getMetricsToDescriptiveStatisticsMap()
    {
        Map<Metric, DescriptiveStatistics> statistics = new EnumMap<>(Metric.class);

        resultHistory.forEach((k, v) -> statistics.put(k, doubleArrayToDescriptiveStatistics(v)));

        return statistics;
    }

    private boolean isOutlierByZScore(DescriptiveStatistics stats, double value)
    {
        double zScore = (value - stats.getMean()) / stats.getStandardDeviation();

        return Math.abs(zScore) > 3;
    }

    private boolean isOutlierByModifiedZScore(DescriptiveStatistics stats, double value)
    {
        double median = stats.getPercentile(50);
        double medianAbsoluteDeviation = new DescriptiveStatistics(
                Arrays.stream(stats.getValues()).map(v -> Math.abs(v - median)).toArray()).getPercentile(50);
        double modifiedZScore = 0.6745 * (value - median) / medianAbsoluteDeviation;

        return Math.abs(modifiedZScore) > 3.5;
    }

    private boolean isOutlierByInterquartileRange(DescriptiveStatistics stats, double value)
    {
        double quartile1 = stats.getPercentile(25);
        double quartile3 = stats.getPercentile(75);
        double iqr = quartile3 - quartile1;
        double lowerBound = quartile1 - (iqr * 1.5);
        double upperBound = quartile3 + (iqr * 1.5);

        return (value < lowerBound || value > upperBound);
    }

    // removes and returns the number of outliers in the result keeper
    private int removeOutliers(Set<Integer> outlierIndices)
    {
        // remove outlier(s) from result keeper, if any
        if (!outlierIndices.isEmpty())
        {
            Map<Metric, List<Double>> filteredResults = new EnumMap<>(Metric.class);
            for (Entry<Metric, List<Double>> entry : resultHistory.entrySet())
            {
                Metric key = entry.getKey();
                filteredResults.put(key, new LinkedList<>());
                for (int i = 0; i < resultHistory.get(key).size(); i++)
                    if (!outlierIndices.contains(i))
                        filteredResults.get(key).add(resultHistory.get(key).get(i));
            }
            resultHistory = filteredResults;
        }

        // returns the number of detected outliers (if any)
        return outlierIndices.size();
    }
}
