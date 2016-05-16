package xyz.marcelo.ml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.ml.CalculateScore;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.ea.train.basic.TrainEA;
import org.encog.neural.neat.NEATNetwork;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.NEATUtil;
import org.encog.neural.networks.training.TrainingSetScore;

import xyz.marcelo.common.Enumerates.MessageLabel;

/**
 * @author marcelovca90
 * 
 */
public class MethodNeat {

	private static final Logger logger = LogManager.getLogger(MethodNeat.class);

	public static void run(BasicMLDataSet trainingSet, BasicMLDataSet validationSet, BasicMLDataSet testSet, int seed) {

		int inputCount = testSet.get(0).getInput().size();
		int hiddenCount = MethodUtil.getHiddenNeuronsCount(inputCount, trainingSet.size());
		int outputCount = testSet.get(0).getIdeal().size();

		NEATNetwork network = null;

		NEATPopulation population = new NEATPopulation(inputCount, outputCount, hiddenCount);
		population.reset();

		CalculateScore score = new TrainingSetScore(trainingSet);

		TrainEA train = NEATUtil.constructNEATTrainer(population, score);
		train.setThreadCount(Runtime.getRuntime().availableProcessors());

		double validationErrorBefore = Double.MAX_VALUE, validationErrorAfter = Double.MAX_VALUE;

		do {

			validationErrorBefore = validationErrorAfter;

			train.iteration(20);

			network = (NEATNetwork) train.getCODEC().decode(train.getBestGenome());

			validationErrorAfter = network.calculateError(validationSet);

			/*
			 * logger.debug(String.format("Iteration #%d\tvError = %.12f",
			 * train.getIteration(), validationErrorAfter));
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
	}
}
