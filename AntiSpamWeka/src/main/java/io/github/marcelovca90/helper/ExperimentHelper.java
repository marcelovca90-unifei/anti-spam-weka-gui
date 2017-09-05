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

import static io.github.marcelovca90.common.Constants.MessageType.HAM;
import static io.github.marcelovca90.common.Constants.MessageType.SPAM;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Logger;

import io.github.marcelovca90.common.Constants.Metric;
import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.common.MethodEvaluation;
import weka.classifiers.Evaluation;

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
        Evaluation evaluation = methodEvaluation.getEvaluation();
        int hamIndex = HAM.ordinal();
        int spamIndex = SPAM.ordinal();

        Double hamPrecision = 100.0 * evaluation.precision(hamIndex);
        Double spamPrecision = 100.0 * evaluation.precision(spamIndex);
        Double weightedPrecision = 100.0 * evaluation.weightedPrecision();

        // androutsopoulos2000evaluation
        Double nll = evaluation.numTruePositives(hamIndex);
        Double nss = evaluation.numTruePositives(spamIndex);
        Double nl = methodEvaluation.getTestingSetCounts().get(HAM).doubleValue();
        Double ns = methodEvaluation.getTestingSetCounts().get(SPAM).doubleValue();
        Double wacc1 = 100.0 * ((1 * nll + nss) / (1 * nl + ns));
        Double wacc9 = 100.0 * ((9 * nll + nss) / (9 * nl + ns));
        Double wacc99 = 100.0 * ((99 * nll + nss) / (99 * nl + ns));
        Double wacc999 = 100.0 * ((999 * nll + nss) / (999 * nl + ns));

        Double hamRecall = 100.0 * evaluation.recall(hamIndex);
        Double spamRecall = 100.0 * evaluation.recall(spamIndex);
        Double weightedRecall = 100.0 * evaluation.weightedRecall();

        Double hamAreaUnderPRC = 100.0 * evaluation.areaUnderPRC(hamIndex);
        Double spamAreaUnderPRC = 100.0 * evaluation.areaUnderPRC(spamIndex);
        Double weightedAreaUnderPRC = 100.0 * evaluation.weightedAreaUnderPRC();

        Double hamAreaUnderROC = 100.0 * evaluation.areaUnderROC(hamIndex);
        Double spamAreaUnderROC = 100.0 * evaluation.areaUnderROC(spamIndex);
        Double weightedAreaUnderROC = 100.0 * evaluation.weightedAreaUnderROC();

        Double hamFMeasure = 100.0 * evaluation.fMeasure(hamIndex);
        Double spamFMeasure = 100.0 * evaluation.fMeasure(spamIndex);
        Double weightedFMeasure = 100.0 * evaluation.weightedFMeasure();

        Double trainTime = (double) (methodEvaluation.getTrainEnd() - methodEvaluation.getTrainStart());

        Double testTime = (double) (methodEvaluation.getTestEnd() - methodEvaluation.getTestStart());

        addSingleRunResult(Metric.HAM_PRECISION, hamPrecision);
        addSingleRunResult(Metric.SPAM_PRECISION, spamPrecision);
        addSingleRunResult(Metric.WEIGHTED_PRECISION, weightedPrecision);
        addSingleRunResult(Metric.HAM_RECALL, hamRecall);
        addSingleRunResult(Metric.SPAM_RECALL, spamRecall);
        addSingleRunResult(Metric.WEIGHTED_RECALL, weightedRecall);
        addSingleRunResult(Metric.HAM_AREA_UNDER_PRC, hamAreaUnderPRC);
        addSingleRunResult(Metric.SPAM_AREA_UNDER_PRC, spamAreaUnderPRC);
        addSingleRunResult(Metric.WEIGHTED_AREA_UNDER_PRC, weightedAreaUnderPRC);
        addSingleRunResult(Metric.HAM_AREA_UNDER_ROC, hamAreaUnderROC);
        addSingleRunResult(Metric.SPAM_AREA_UNDER_ROC, spamAreaUnderROC);
        addSingleRunResult(Metric.WEIGHTED_AREA_UNDER_ROC, weightedAreaUnderROC);
        addSingleRunResult(Metric.HAM_F_MEASURE, hamFMeasure);
        addSingleRunResult(Metric.SPAM_F_MEASURE, spamFMeasure);
        addSingleRunResult(Metric.WEIGHTED_F_MEASURE, weightedFMeasure);
        addSingleRunResult(Metric.TRAIN_TIME, trainTime);
        addSingleRunResult(Metric.TEST_TIME, testTime);
        addSingleRunResult(Metric.WEIGHTED_ACCURACY_1, wacc1);
        addSingleRunResult(Metric.WEIGHTED_ACCURACY_9, wacc9);
        addSingleRunResult(Metric.WEIGHTED_ACCURACY_99, wacc99);
        addSingleRunResult(Metric.WEIGHTED_ACCURACY_999, wacc999);
    }

    // detects, removes and return the amount of outliers in the result keeper, if any
    public int detectAndRemoveOutliers()
    {
        return removeOutliers(detectOutliers());
    }

    public void printHeader()
    {
        Stream<String> metricsWithoutStats = Arrays.stream(new String[] { "Data Set", "Statistics Method", "ML Method", "Number of Features (before)", "Number of Features (after)" });

        String headerWithoutStats = metricsWithoutStats.collect(Collectors.joining("\t", "\t", ""));

        Stream<String> metricsWithStats = Arrays.stream(
            new String[] {
                    "Ham Precision", "Spam Precision", "Weighted Precision",
                    "Ham Recall", "Spam Recall", "Weighted Recall",
                    "Ham Area Under PRC", "Spam Area Under PRC", "Weighted Area Under PRC",
                    "Ham Area Under ROC", "Spam Area Under ROC", "Weighted Area Under ROC",
                    "Ham F-Measure", "Spam F-Measure", "Weighted F-Measure",
                    "Train Time", "Test Time",
                    "WAcc1", "WAcc9", "WAcc99", "WAcc999"
            });

        String headerWithStats = metricsWithStats.collect(Collectors.joining("\tSTDEV\tCI\t", "", "\tSTDEV\tCI"));

        Logger.info(headerWithoutStats + "\t" + headerWithStats);
    }

    // displays the experiment's [last resultHistory] or [mean ± standard deviation] for every metric
    public void summarizeResults(Map<Metric, DescriptiveStatistics> results, MethodEvaluation methodEvaluation, boolean printStats, boolean formatMillis)
    {
        MethodConfiguration methodConfiguration = methodEvaluation.getMethodConfiguration();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("\t%s", methodEvaluation.getDataSetName()));
        sb.append(String.format("\t%s", methodEvaluation.getStatMethod()));
        sb.append(String.format("\t%s", methodConfiguration.name()));
        sb.append(String.format("\t%d\t%d\t", methodEvaluation.getNumberOfTotalFeatures(), methodEvaluation.getNumberOfActualFeatures()));

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
            sb.append(String.format("%s\t", formatMillis(values[values.length - 1])));
        else
            sb.append(String.format("%.2f\t", values[values.length - 1]));
    }

    private void buildResultLineWithStats(Map<Metric, DescriptiveStatistics> results, boolean formatMillis, StringBuilder sb, Metric metric)
    {
        DescriptiveStatistics statistics = results.get(metric);
        double mean = statistics.getMean();
        double stdev = statistics.getStandardDeviation();
        double confidenceInterval = computeConfidenceInterval(statistics, 0.05);

        if (formatMillis && (metric == Metric.TRAIN_TIME || metric == Metric.TEST_TIME))
            sb.append(String.format("%s\t%s\t%s\t", formatMillis(mean), formatMillis(stdev), formatMillis(confidenceInterval)));
        else
            sb.append(String.format("%.2f\t%.2f\t%.2f\t", mean, stdev, confidenceInterval));
    }

    // computes the confidence interval width for the given statistics and significance
    // https://stackoverflow.com/questions/5564621/using-apache-commons-math-to-determine-confidence-intervals
    private double computeConfidenceInterval(DescriptiveStatistics statistics, double significance)
    {
        TDistribution tDist = new TDistribution(statistics.getN() - 1);
        double a = tDist.inverseCumulativeProbability(1.0 - significance / 2);
        return a * statistics.getStandardDeviation() / Math.sqrt(statistics.getN());
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

    private String formatMillis(double millis)
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
