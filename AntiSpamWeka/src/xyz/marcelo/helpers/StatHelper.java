package xyz.marcelo.helpers;

import java.util.LinkedList;

/**
 * @author marcelovca90
 * 
 */
public class StatHelper
{
	// Calculates the average for a given list of values
	public static Double average(LinkedList<Double> values)
	{
		Double acc = 0.0;
		for (Double v : values)
			acc += v;
		return (acc / values.size());
	}

	// Calculates the sum for a given list of values
	public static Double sum(LinkedList<Double> values)
	{
		Double acc = 0.0;
		for (Double v : values)
			acc += v;
		return acc;
	}

	// Calculates the standard deviation for a given list of values
	public static Double standardDeviation(LinkedList<Double> values)
	{
		Double acc = 0.0, avg = average(values);
		for (Double v : values)
			acc += Math.pow(v - avg, 2.0);
		return Math.sqrt((acc / values.size()));
	}

}
