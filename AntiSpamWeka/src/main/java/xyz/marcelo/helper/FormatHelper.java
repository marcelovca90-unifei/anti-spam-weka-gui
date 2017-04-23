package xyz.marcelo.helper;

import static xyz.marcelo.common.Constants.METRICS;
import static xyz.marcelo.common.Constants.TEST_TIME;
import static xyz.marcelo.common.Constants.TRAIN_TIME;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.MethodEvaluation;

public class FormatHelper
{
    private static final String ZONE_AMERICA_SAO_PAULO = "America/Sao_Paulo";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss.SSS z");

    // displays the experiment's last results OR mean ± standard deviation for every metric
    public static void summarizeResults(MethodEvaluation timedEvaluation, boolean printStats, boolean formatMillis)
    {
        Map<String, DescriptiveStatistics> results = ResultHelper.getMetricsToDescriptiveStatisticsMap();

        String timestamp = getCurrentDateTime();

        String folder = timedEvaluation.getFolder();

        MethodConfiguration methodConfiguration = timedEvaluation.getMethodConfiguration();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s\t", timestamp));
        sb.append(String.format("%s\t", folder));
        sb.append(String.format("%s\t", methodConfiguration.name()));

        for (String metric : METRICS)
        {
            if (!printStats)
            {
                double[] values = results.get(metric).getValues();
                if (formatMillis && (metric.equals(TRAIN_TIME) || metric.equals(TEST_TIME)))
                    sb.append(String.format("%s\t", formatMilliseconds(values[values.length - 1])));
                else
                    sb.append(String.format("%.2f\t", values[values.length - 1]));
            }
            else
            {
                double mean = results.get(metric).getMean();
                double standardDeviation = results.get(metric).getStandardDeviation();
                if (formatMillis && (metric.equals(TRAIN_TIME) || metric.equals(TEST_TIME)))
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

    private static String formatMilliseconds(double millis)
    {
        return DurationFormatUtils.formatDurationHMS((Double.valueOf(millis)).longValue());
    }

    private static String getCurrentDateTime()
    {
        return ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ZONE_AMERICA_SAO_PAULO)).format(FORMATTER);
    }
}
