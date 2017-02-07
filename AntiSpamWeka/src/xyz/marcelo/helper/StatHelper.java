package xyz.marcelo.helper;

import java.util.LinkedList;

public class StatHelper
{
    // Calculates the sum for a given list of values
    public static Double sum(LinkedList<Double> values)
    {
        Double acc = 0.0;
        for (Double v : values)
            acc += v;
        return acc;
    }

    // Calculates the average for a given list of values
    public static Double mean(LinkedList<Double> values)
    {
        Double acc = 0.0;
        for (Double v : values)
            acc += v;
        return (acc / values.size());
    }

    // Calculates the standard deviation for a given list of values
    public static Double standardDeviation(LinkedList<Double> values)
    {
        Double acc = 0.0, avg = mean(values);
        for (Double v : values)
            acc += Math.pow(v - avg, 2.0);
        return Math.sqrt((acc / values.size()));
    }

    public static Double zScore(LinkedList<Double> values, Double value)
    {
        Double localMean = mean(values);
        Double localStandardDeviation = standardDeviation(values);

        // TODO Implement this method
        return Double.NaN;
    }

    public static Double zScoreModified(LinkedList<Double> values, Double value)
    {
        Double localMean = mean(values);
        Double localStandardDeviation = standardDeviation(values);

        // TODO Implement this method
        return Double.NaN;
    }
}
