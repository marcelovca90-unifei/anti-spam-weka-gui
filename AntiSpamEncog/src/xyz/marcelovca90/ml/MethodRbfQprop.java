package xyz.marcelovca90.ml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.mathutil.rbf.RBFEnum;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.training.propagation.quick.QuickPropagation;
import org.encog.neural.rbf.RBFNetwork;

import xyz.marcelovca90.data.MessageLabel;

/**
 * @author marcelovca90
 * 
 */
public class MethodRbfQprop {

	private static final Logger logger = LogManager
			.getLogger(MethodRbfQprop.class);

	public static void run(BasicMLDataSet trainingSet,
			BasicMLDataSet validationSet, BasicMLDataSet testSet, int seed) {

		int inputCount = testSet.get(0).getInput().size();
		int hiddenCount = MethodNeuralUtil.getHiddenNeuronsCount(inputCount,
				trainingSet.size());
		int outputCount = testSet.get(0).getIdeal().size();

		RBFNetwork network = new RBFNetwork(inputCount, hiddenCount,
				outputCount, RBFEnum.Gaussian);
		network.reset();

		QuickPropagation quickPropagation = new QuickPropagation(network,
				trainingSet);
		quickPropagation.setBatchSize(0);
		quickPropagation.setThreadCount(0);

		double validationErrorBefore = Double.MAX_VALUE, validationErrorAfter = Double.MAX_VALUE;

		do {

			validationErrorBefore = validationErrorAfter;

			quickPropagation.iteration(20);

			validationErrorAfter = network.calculateError(validationSet);

			/*
			 * logger.debug(String.format("Iteration #%d\tvError = %.12f",
			 * quickPropagation.getIteration(), validationErrorAfter));
			 */

		} while (validationErrorAfter < validationErrorBefore);

		int hamCount = 0, hamCorrect = 0;
		int spamCount = 0, spamCorrect = 0;

		for (MLDataPair pair : testSet) {

			MLData input = pair.getInput();
			MLData ideal = pair.getIdeal();
			MLData output = network.compute(input);

			if (MethodNeuralUtil.infer(ideal.getData()) == MessageLabel.HAM) {
				hamCount++;
				if (MethodNeuralUtil.infer(output.getData()) == MessageLabel.HAM) {
					hamCorrect++;
				}
			} else if (MethodNeuralUtil.infer(ideal.getData()) == MessageLabel.SPAM) {
				spamCount++;
				if (MethodNeuralUtil.infer(output.getData()) == MessageLabel.SPAM) {
					spamCorrect++;
				}
			}
		}

		logger.info(String.format(
				"Hams: %.2f%% (%d/%d)\tSpams: %.2f%% (%d/%d)", 100.0
						* (double) hamCorrect / (double) hamCount, hamCorrect,
				hamCount, 100.0 * (double) spamCorrect / (double) spamCount,
				spamCorrect, spamCount));
	}
}
