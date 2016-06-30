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
import xyz.marcelo.common.Folders;

/**
 * @author marcelovca90
 * 
 */
public class MethodNeat
{
	private static final Logger logger = LogManager.getLogger(MethodNeat.class);

	public static void run(String folder, BasicMLDataSet trainingSet, BasicMLDataSet validationSet,
			BasicMLDataSet testSet, int seed)
	{

		int inputCount = testSet.get(0).getInput().size();
		int hiddenCount = MethodUtil.getHiddenNeuronsCount(inputCount, trainingSet.size());
		int outputCount = testSet.get(0).getIdeal().size();

		NEATNetwork network = null;

		NEATPopulation population = new NEATPopulation(inputCount, outputCount, hiddenCount);
		population.reset();

		CalculateScore score = new TrainingSetScore(trainingSet);

		TrainEA neatTrainer = NEATUtil.constructNEATTrainer(population, score);
		neatTrainer.setThreadCount(Runtime.getRuntime().availableProcessors());

		double validationErrorBefore = Double.MAX_VALUE, validationErrorAfter = Double.MAX_VALUE;

		do
		{

			validationErrorBefore = validationErrorAfter;

			neatTrainer.iteration(20);

			network = (NEATNetwork) neatTrainer.getCODEC().decode(neatTrainer.getBestGenome());

			validationErrorAfter = network.calculateError(validationSet);

			/*
			 * logger.debug(String.format("Iteration #%d\tvError = %.12f",
			 * train.getIteration(), validationErrorAfter));
			 */

		} while (validationErrorAfter < validationErrorBefore);

		neatTrainer.finishTraining();

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
