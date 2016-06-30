package xyz.marcelo.math;

import org.encog.engine.network.activation.ActivationFunction;

/**
 * @author marcelovca90
 * 
 */
public class ActivationLogSig implements ActivationFunction
{
	private static final long serialVersionUID = 1L;

	public void activationFunction(double[] d, int start, int size)
	{
		for (int i = 0; i < d.length; i++)
			d[i] = 1.0 / (1.0 + Math.exp(-1.0 * d[i]));
	}

	public double derivativeFunction(double b, double a)
	{
		return (Math.exp(b)) / (Math.pow((Math.exp(b) + 1.0), 2.0));
	}

	public boolean hasDerivative()
	{
		return true;
	}

	public double[] getParams()
	{
		return new double[0];
	}

	public void setParam(int index, double value)
	{
		return;
	}

	public String[] getParamNames()
	{
		return new String[0];
	}

	public String getFactoryCode()
	{
		return this.getClass().getName();
	}

	public ActivationFunction clone()
	{
		return new ActivationLogSig();
	}

	public String getLabel()
	{
		return this.getClass().getName();
	}

}
