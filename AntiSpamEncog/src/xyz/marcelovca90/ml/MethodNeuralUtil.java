/**
 * 
 */
package xyz.marcelovca90.ml;

import org.apache.commons.lang3.ArrayUtils;

import xyz.marcelovca90.data.MessageLabel;

/**
 * @author marcelovca90
 * 
 */
public class MethodNeuralUtil {

	public static int getHiddenNeuronsCount(int numberOfFeatures,
			int trainingSetSize) {

		return (int) Math.ceil(((-(numberOfFeatures + 2)) + Math.sqrt(Math.pow(
				(numberOfFeatures + 2), 2) + 2 * trainingSetSize)));
	}

	public static MessageLabel infer(double[] data) {

		if (data.length == 2 && Double.compare(data[1], data[0]) < 0)
			return MessageLabel.HAM;
		else if (data.length == 2 && Double.compare(data[0], data[1]) < 0)
			return MessageLabel.SPAM;
		else
			return null;
	}

	public static MessageLabel infer(Double[] data) {

		return infer(ArrayUtils.toPrimitive(data));
	}

}
