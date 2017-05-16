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

import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Logger;

import xyz.marcelo.common.Constants.Metric;
import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.MethodEvaluation;

public class FormatHelper
{
    // used to suppress the default public constructor
    private FormatHelper()
    {
    }

    private static final FormatHelper INSTANCE = new FormatHelper();

    public static final FormatHelper getInstance()
    {
        return INSTANCE;
    }

    // displays the experiment's [last results] or [mean ± standard deviation] for every metric
    public void summarizeResults(MethodEvaluation methodEvaluation, boolean printStats, boolean formatMillis)
    {
        summarizeResults(ResultHelper.getInstance().getMetricsToDescriptiveStatisticsMap(), methodEvaluation, printStats, formatMillis);
    }

    // displays the experiment's [last results] or [mean ± standard deviation] for every metric
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
            {
                buildResultLineWithoutStats(results, formatMillis, sb, metric);
            }
            else
            {
                buildResultLineWithStats(results, formatMillis, sb, metric);
            }
        }

        if (!printStats)
            Logger.debug(sb.toString());
        else
            Logger.info(sb.toString());
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

    public void printHeader()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Timestamp\t");
        sb.append("Data Set\t");
        sb.append("Statistics Method\t");
        sb.append("Number of Features\t");
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

        Logger.info(sb.toString());
    }

    private String formatMilliseconds(double millis)
    {
        return DurationFormatUtils.formatDurationHMS((Double.valueOf(Math.abs(millis))).longValue());
    }
}
