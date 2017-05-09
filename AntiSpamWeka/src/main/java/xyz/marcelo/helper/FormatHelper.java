package xyz.marcelo.helper;

import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Logger;

import xyz.marcelo.common.Constants.Metric;
import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.MethodEvaluation;

public final class FormatHelper
{
    // displays the experiment's [last results] or [mean ± standard deviation] for every metric
    public static void summarizeResults(MethodEvaluation methodEvaluation, boolean printStats, boolean formatMillis)
    {
        Map<Metric, DescriptiveStatistics> results = ResultHelper.getMetricsToDescriptiveStatisticsMap();

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
                double[] values = results.get(metric).getValues();
                if (formatMillis && (metric == Metric.TRAIN_TIME || metric == Metric.TEST_TIME))
                    sb.append(String.format("%s\t", formatMilliseconds(values[values.length - 1])));
                else
                    sb.append(String.format("%.2f\t", values[values.length - 1]));
            }
            else
            {
                double mean = results.get(metric).getMean();
                double standardDeviation = results.get(metric).getStandardDeviation();
                if (formatMillis && (metric == Metric.TRAIN_TIME || metric == Metric.TEST_TIME))
                    sb.append(String.format("%s ± %s\t", formatMilliseconds(mean), formatMilliseconds(standardDeviation)));
                else
                    sb.append(String.format("%.2f ± %.2f\t", mean, standardDeviation));
            }
        }

        if (!printStats)
            Logger.debug(sb.toString());
        else
            Logger.info(sb.toString());
    }

    public static void printHeader()
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

    private static String formatMilliseconds(double millis)
    {
        return DurationFormatUtils.formatDurationHMS((Double.valueOf(Math.abs(millis))).longValue());
    }
}
