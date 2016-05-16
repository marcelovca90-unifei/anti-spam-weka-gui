package xyz.marcelo.ml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

import xyz.marcelo.common.Enumerates.MessageLabel;
import xyz.marcelo.math.ActivationLogSig;
import xyz.marcelo.math.ActivationTanSig;

/**
 * @author marcelovca90
 * 
 */
public class MethodMlpBprop {

	private static final Logger logger = LogManager.getLogger(MethodMlpBprop.class);

	public static void run(BasicMLDataSet trainingSet, BasicMLDataSet validationSet, BasicMLDataSet testSet, int seed) {

		int inputCount = testSet.get(0).getInput().size();
		int hiddenCount = MethodUtil.getHiddenNeuronsCount(inputCount, trainingSet.size());
		int outputCount = testSet.get(0).getIdeal().size();

		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(new ActivationTanSig(), false, inputCount));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 2 * hiddenCount));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 2 * hiddenCount));
		network.addLayer(new BasicLayer(new ActivationLogSig(), false, outputCount));
		network.getStructure().finalizeStructure();
		network.reset(seed);

		Backpropagation backpropagation = new Backpropagation(network, trainingSet, 1e-4, 0.9);
		backpropagation.setBatchSize(0);
		backpropagation.setThreadCount(0);

		double trainingErrorBefore = Double.MAX_VALUE, trainingErrorAfter = Double.MAX_VALUE;
		double validationErrorBefore = Double.MAX_VALUE, validationErrorAfter = Double.MAX_VALUE;

		do {

			trainingErrorBefore = backpropagation.getError();
			validationErrorBefore = validationErrorAfter;

			backpropagation.iteration(20);

			trainingErrorAfter = backpropagation.getError();
			validationErrorAfter = network.calculateError(validationSet);

			if (trainingErrorAfter < trainingErrorBefore) {
				backpropagation.setLearningRate(1.02 * backpropagation.getLearningRate());
				backpropagation.setMomentum(backpropagation.getMomentum());
			} else {
				backpropagation.setLearningRate(1e-4);
				backpropagation.setMomentum(0);
			}

			/*
			 * logger.debug(String.format(
			 * "Iteration #%d\tLR = %3.3e\tMO = %.6f\tvError = %.12f",
			 * backpropagation.getIteration(),
			 * backpropagation.getLearningRate(), backpropagation.getMomentum(),
			 * validationErrorAfter));
			 */

		} while (validationErrorAfter < validationErrorBefore);

		int hamCount = 0, hamCorrect = 0;
		int spamCount = 0, spamCorrect = 0;

		for (MLDataPair pair : testSet) {

			MLData input = pair.getInput();
			MLData ideal = pair.getIdeal();
			MLData output = network.compute(input);

			if (MethodUtil.infer(ideal.getData()) == MessageLabel.HAM) {
				hamCount++;
				if (MethodUtil.infer(output.getData()) == MessageLabel.HAM) {
					hamCorrect++;
				}
			} else if (MethodUtil.infer(ideal.getData()) == MessageLabel.SPAM) {
				spamCount++;
				if (MethodUtil.infer(output.getData()) == MessageLabel.SPAM) {
					spamCorrect++;
				}
			}
		}

		logger.info(String.format("Hams: %.2f%% (%d/%d)\tSpams: %.2f%% (%d/%d)", 100.0 * (double) hamCorrect
				/ (double) hamCount, hamCorrect, hamCount, 100.0 * (double) spamCorrect / (double) spamCount,
				spamCorrect, spamCount));

		Encog.getInstance().shutdown();
		Runtime.getRuntime().gc();
	}
}
