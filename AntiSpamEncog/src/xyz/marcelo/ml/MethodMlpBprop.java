package xyz.marcelo.ml;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

import xyz.marcelo.common.Enumerates.MessageLabel;
import xyz.marcelo.common.Folders;
import xyz.marcelo.math.ActivationLogSig;
import xyz.marcelo.math.ActivationTanSig;

/**
 * @author marcelovca90
 * 
 */
public class MethodMlpBprop
{
	private static final Logger logger = LogManager.getLogger(MethodMlpBprop.class);

	public static void run(String folder, BasicMLDataSet trainingSet, BasicMLDataSet validationSet,
			BasicMLDataSet testSet, int seed)
	{

		int inputCount = testSet.get(0).getInput().size();
		// int hiddenCount = MethodUtil.getHiddenNeuronsCount(inputCount,
		// trainingSet.size());
		int outputCount = testSet.get(0).getIdeal().size();

		double bestHamPrecision = 0, bestSpamPrecision = 0, bestPrecision = Double.MAX_VALUE;
		String bestStructure = "";

		for (int h1 = Math.min(inputCount, outputCount); h1 <= Math.max(inputCount, outputCount); h1++)
		{

			for (int h2 = 1; h2 <= h1; h2++)
			{

				BasicNetwork network = new BasicNetwork();
				network.addLayer(new BasicLayer(new ActivationTanSig(), true, inputCount));
				network.addLayer(new BasicLayer(new ActivationTanSig(), true, h1));
				network.addLayer(new BasicLayer(new ActivationTanSig(), true, h2));
				network.addLayer(new BasicLayer(new ActivationLogSig(), false, outputCount));
				network.getStructure().finalizeStructure();
				network.reset(seed);

				Backpropagation backpropagation = new Backpropagation(network, trainingSet, 1e-3, 0.9);
				backpropagation.setBatchSize(0);
				backpropagation.setThreadCount(0);

				double trainingErrorBefore = Double.MAX_VALUE, trainingErrorAfter = Double.MAX_VALUE;
				double validationErrorBefore = Double.MAX_VALUE, validationErrorAfter = Double.MAX_VALUE;

				do
				{

					trainingErrorBefore = backpropagation.getError();
					validationErrorBefore = validationErrorAfter;

					backpropagation.iteration(20);

					trainingErrorAfter = backpropagation.getError();
					validationErrorAfter = network.calculateError(validationSet);

					if (trainingErrorAfter < trainingErrorBefore)
					{
						backpropagation.setLearningRate(1.02 * backpropagation.getLearningRate());
						backpropagation.setMomentum(backpropagation.getMomentum());
					} else
					{
						backpropagation.setLearningRate(0.50 * backpropagation.getLearningRate());
						backpropagation.setMomentum(0);
					}

					/*
					 * logger.debug(String.format(
					 * "Iteration #%d\tLR = %3.3e\tMO = %.6f\tvError = %.12f",
					 * backpropagation.getIteration(),
					 * backpropagation.getLearningRate(),
					 * backpropagation.getMomentum(), validationErrorAfter));
					 */

				} while (validationErrorAfter < validationErrorBefore);

				backpropagation.finishTraining();

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

				double hamPrecision = 100.0 * (double) hamCorrect / (double) hamCount;
				double spamPrecision = 100.0 * (double) spamCorrect / (double) spamCount;
				double precision = 100.0 * (double) (hamCorrect + spamCorrect) / (double) (hamCount + spamCount);
				String structure = Arrays.toString(network.getFlat().getLayerCounts());

				if (precision < bestPrecision)
				{
					bestHamPrecision = hamPrecision;
					bestSpamPrecision = spamPrecision;
					bestPrecision = precision;
					bestStructure = structure;
				}

				logger.info(String.format("%s @ %d\t%s\tHP: %.2f%% (%d/%d)\tSP: %.2f%% (%d/%d)\tGP: %.2f%%", structure,
						seed, folder.replace(Folders.BASE_FOLDER, ""), hamPrecision, hamCorrect, hamCount,
						spamPrecision, spamCorrect, spamCount, precision));

				try
				{
					Thread.sleep(2000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		logger.info(String.format("%s @ %d\t%s\tHP: %.2f%%\tSP: %.2f%%\tGP: %.2f%%", bestStructure, seed,
				folder.replace(Folders.BASE_FOLDER, ""), bestHamPrecision, bestSpamPrecision, bestPrecision));
	}
}
