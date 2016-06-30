package xyz.marcelo.ml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import xyz.marcelo.common.Enumerates.MessageLabel;
import xyz.marcelo.common.Folders;
import xyz.marcelo.math.ActivationLogSig;
import xyz.marcelo.math.ActivationTanSig;

/**
 * @author marcelovca90
 * 
 */
public class MethodMlpRprop
{
	private static final Logger logger = LogManager.getLogger(MethodMlpRprop.class);

	public static void run(String folder, BasicMLDataSet trainingSet, BasicMLDataSet validationSet,
			BasicMLDataSet testSet, int seed)
	{

		int inputCount = testSet.get(0).getInput().size();
		@SuppressWarnings("unused")
		int hiddenCount = MethodUtil.getHiddenNeuronsCount(inputCount, trainingSet.size());
		int outputCount = testSet.get(0).getIdeal().size();

		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, inputCount));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationTanSig(), true, 20));
		network.addLayer(new BasicLayer(new ActivationLogSig(), false, outputCount));
		network.getStructure().finalizeStructure();
		network.reset(seed);

		ResilientPropagation resilientPropagation = new ResilientPropagation(network, trainingSet);
		resilientPropagation.setBatchSize(0);
		resilientPropagation.setThreadCount(0);

		double validationErrorBefore = Double.MAX_VALUE, validationErrorAfter = Double.MAX_VALUE;

		do
		{

			validationErrorBefore = validationErrorAfter;

			resilientPropagation.iteration(20);

			validationErrorAfter = network.calculateError(validationSet);

			/*
			 * logger.debug(String.format("Iteration #%d\tvError = %.12f",
			 * resilientPropagation.getIteration(), validationErrorAfter));
			 */

		} while (validationErrorAfter < validationErrorBefore);

		resilientPropagation.finishTraining();

		int hamCount = 0, hamCorrect = 0;
		int spamCount = 0, spamCorrect = 0;

		for (MLDataPair pair : testSet)
		{

			MLData input = pair.getInput();
			MLData ideal = pair.getIdeal();
			MLData output = network.compute(input);

			if (MethodUtil.infer(ideal.getData()) == MessageLabel.HAM)
			{
				hamCount++;
				if (MethodUtil.infer(output.getData()) == MessageLabel.HAM)
				{
					hamCorrect++;
				}
			} else if (MethodUtil.infer(ideal.getData()) == MessageLabel.SPAM)
			{
				spamCount++;
				if (MethodUtil.infer(output.getData()) == MessageLabel.SPAM)
				{
					spamCorrect++;
				}
			}
		}

		logger.info(String.format("%d\t%s\tHP: %.2f%% (%d/%d)\tSP: %.2f%% (%d/%d)", seed,
				folder.replace(Folders.BASE_FOLDER, ""), 100.0 * (double) hamCorrect / (double) hamCount, hamCorrect,
				hamCount, 100.0 * (double) spamCorrect / (double) spamCount, spamCorrect, spamCount));
	}
}
