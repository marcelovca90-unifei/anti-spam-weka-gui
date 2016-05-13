package xyz.marcelovca90.main;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.Encog;
import org.encog.ml.data.basic.BasicMLDataSet;

import xyz.marcelovca90.common.Enumerates.Method;
import xyz.marcelovca90.common.Folders;
import xyz.marcelovca90.common.Primes;
import xyz.marcelovca90.data.MessageDataSet;
import xyz.marcelovca90.ml.MethodMlpBprop;
import xyz.marcelovca90.ml.MethodMlpRprop;
import xyz.marcelovca90.ml.MethodNeat;
import xyz.marcelovca90.ml.MethodRbfQprop;
import xyz.marcelovca90.ml.MethodSvm;

/**
 * @author marcelovca90
 * 
 */
public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		final int seed = Primes.getRandomPrime();
		
		logger.info("Random prime seed: " + seed);
		
		final Method method = Method.MLP_BPROP;
		
		logger.info("Selected method: " + method.getName());

		for (String folder : Folders.FOLDERS_LING) {

			logger.info("Current folder: " + folder);

			File hamFile = new File(folder + "/ham");
			File spamFile = new File(folder + "/spam");

			MessageDataSet dataSet = new MessageDataSet(hamFile, spamFile);
			MessageDataSet dataSubset = null;

			dataSet.shuffle(seed);

			dataSubset = dataSet.getSubset(0, 40);
			BasicMLDataSet trainingSet = new BasicMLDataSet(
					dataSubset.getInputDataAsPrimitiveMatrix(),
					dataSubset.getOutputDataAsPrimitiveMatrix());

			dataSubset = dataSet.getSubset(40, 60);
			BasicMLDataSet validationSet = new BasicMLDataSet(
					dataSubset.getInputDataAsPrimitiveMatrix(),
					dataSubset.getOutputDataAsPrimitiveMatrix());

			dataSubset = dataSet.getSubset(60, 100);
			BasicMLDataSet testSet = new BasicMLDataSet(
					dataSubset.getInputDataAsPrimitiveMatrix(),
					dataSubset.getOutputDataAsPrimitiveMatrix());

			switch (method) {
			case MLP_BPROP:
				MethodMlpBprop.run(trainingSet, validationSet, testSet, seed);
				break;
			case MLP_RPROP:
				MethodMlpRprop.run(trainingSet, validationSet, testSet, seed);
				break;
			case NEAT:
				MethodNeat.run(trainingSet, validationSet, testSet, seed);
				break;
			case RBF_QPROP:
				MethodRbfQprop.run(trainingSet, validationSet, testSet, seed);
				break;
			case SVM:
				MethodSvm.run(trainingSet, validationSet, testSet, seed);
				break;
			}

		}

		Encog.getInstance().shutdown();
		Runtime.getRuntime().gc();
	}

}
