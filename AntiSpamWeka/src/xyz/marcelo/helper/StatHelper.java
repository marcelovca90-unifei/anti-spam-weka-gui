package xyz.marcelo.helper;

import java.util.LinkedList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class StatHelper
{
    // Calculates the sum for a given list of values
    @Deprecated
    public static Double sum(LinkedList<Double> values)
    {
        Double acc = 0.0;
        for (Double v : values)
            acc += v;
        return acc;
    }

    // Calculates the average for a given list of values
    @Deprecated
    public static Double mean(LinkedList<Double> values)
    {
        Double acc = 0.0;
        for (Double v : values)
            acc += v;
        return (acc / values.size());
    }

    // Calculates the standard deviation for a given list of values
    @Deprecated
    public static Double standardDeviation(LinkedList<Double> values)
    {
        Double acc = 0.0, avg = mean(values);
        for (Double v : values)
            acc += Math.pow(v - avg, 2.0);
        return Math.sqrt((acc / values.size()));
    }

    public static Double zScore(double[] values, double value)
    {
        DescriptiveStatistics stat = new DescriptiveStatistics(values);

        return ((value - stat.getMean()) / stat.getStandardDeviation());
    }

    public static boolean containsOutlier(DescriptiveStatistics stats)
    {
        double stdDev = stats.getStandardDeviation();
        double[] values = stats.getValues();
        boolean outlierCheck = false;
        for (int i = 0; i < values.length && !outlierCheck; i++)
        {
            double zScore = StatHelper.zScore(values, values[i]);
            double absZS = Math.abs(zScore), absSD = Math.abs(stdDev);
            outlierCheck = ((absZS > absSD) ? (absZS / absSD) : (absSD / absZS)) > 3;
        }
        return outlierCheck;
    }
}
