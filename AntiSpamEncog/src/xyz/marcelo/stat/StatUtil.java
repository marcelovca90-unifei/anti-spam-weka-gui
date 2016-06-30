package xyz.marcelo.stat;

import java.util.Set;

/**
 * @author marcelovca90
 * 
 */
public class StatUtil
{
	// Calculates the average for a given set of values
	public static Double average(Set<Double> values)
	{
		Double acc = 0.0;
		for (Double v : values)
			acc += v;
		return (acc / values.size());
	}

	// Calculates the sum for a given set of values
	public static Double sum(Set<Double> values)
	{
		Double acc = 0.0;
		for (Double v : values)
			acc += v;
		return acc;
	}

	// Calculates the standard deviation for a given set of values
	public static Double standardDeviation(Set<Double> values)
	{
		Double acc = 0.0, avg = average(values);
		for (Double v : values)
			acc += Math.pow(v - avg, 2.0);
		return Math.sqrt((acc / values.size()));
	}

}
