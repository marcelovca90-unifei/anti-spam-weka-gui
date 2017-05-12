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
package xyz.marcelo.helper;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import xyz.marcelo.common.Constants.MessageType;
import xyz.marcelo.common.Constants.Metric;
import xyz.marcelo.common.MethodEvaluation;

public final class ResultHelper
{
    private static Map<Metric, List<Double>> results = new EnumMap<>(Metric.class);

    // clears the data in result keeper
    public static void reset()
    {
        results.clear();
    }

    // compute and persist all metrics' results for a given TimedEvaluation
    public static void computeSingleRunResults(MethodEvaluation timedEvaluation)
    {
        Double hamPrecision = 100.0 * timedEvaluation.getEvaluation().precision(MessageType.HAM.ordinal());
        Double spamPrecision = 100.0 * timedEvaluation.getEvaluation().precision(MessageType.SPAM.ordinal());

        Double hamRecall = 100.0 * timedEvaluation.getEvaluation().recall(MessageType.HAM.ordinal());
        Double spamRecall = 100.0 * timedEvaluation.getEvaluation().recall(MessageType.SPAM.ordinal());

        Double hamAreaUnderPRC = 100.0 * timedEvaluation.getEvaluation().areaUnderPRC(MessageType.HAM.ordinal());
        Double spamAreaUnderPRC = 100.0 * timedEvaluation.getEvaluation().areaUnderPRC(MessageType.SPAM.ordinal());

        Double hamAreaUnderROC = 100.0 * timedEvaluation.getEvaluation().areaUnderROC(MessageType.HAM.ordinal());
        Double spamAreaUnderROC = 100.0 * timedEvaluation.getEvaluation().areaUnderROC(MessageType.SPAM.ordinal());

        Double trainTime = (double) (timedEvaluation.getTrainEnd() - timedEvaluation.getTrainStart());

        Double testTime = (double) (timedEvaluation.getTestEnd() - timedEvaluation.getTestStart());

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

    // detects, removes and returns the number of outliers in the result keeper
    // http://www.itl.nist.gov/div898/handbook/eda/section3/eda35h.htm
    public static int detectAndRemoveOutliers()
    {
        // detect outlier(s) for each metric
        Set<Integer> outlierIndices = new TreeSet<>();
        for (Metric metric : Metric.values())
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
            Map<Metric, List<Double>> filteredResults = new EnumMap<>(Metric.class);
            for (Entry<Metric, List<Double>> entry : results.entrySet())
            {
                Metric key = entry.getKey();
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
        }

        // returns the number of detected outliers (if any)
        return outlierIndices.size();
    }

    // converts the double resuls for each metric to analog descriptive statistics
    protected static Map<Metric, DescriptiveStatistics> getMetricsToDescriptiveStatisticsMap()
    {
        Map<Metric, DescriptiveStatistics> statistics = new EnumMap<>(Metric.class);

        results.forEach((k, v) -> statistics.put(k, doubleArrayToDescriptiveStatistics(v)));

        return statistics;
    }

    private static void addSingleRunResult(Metric key, Double value)
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
