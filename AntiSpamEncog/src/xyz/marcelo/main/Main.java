package xyz.marcelo.main;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.Encog;
import org.encog.ml.data.basic.BasicMLDataSet;

import xyz.marcelo.common.Folders;
import xyz.marcelo.common.Primes;
import xyz.marcelo.common.Enumerates.Method;
import xyz.marcelo.data.MessageDataSet;
import xyz.marcelo.ml.MethodMlpBprop;
import xyz.marcelo.ml.MethodMlpRprop;
import xyz.marcelo.ml.MethodNeat;
import xyz.marcelo.ml.MethodRbfQprop;
import xyz.marcelo.ml.MethodSvm;

/**
 * @author marcelovca90
 * 
 */
public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		final int seed = Primes.getRandomPrime();

		logger.info("Random prime seed: " + seed);

		final Method[] methods = new Method[] { Method.MLP_BPROP, Method.MLP_RPROP, Method.NEAT, Method.RBF_QPROP,
				Method.SVM };

		for (Method method : methods) {

			logger.info("Current method: " + method.getName());

			for (String folder : Folders.FOLDERS_LING) {

				logger.info("Current folder: " + folder);

				File hamFile = new File(folder + "/ham");
				File spamFile = new File(folder + "/spam");

				MessageDataSet dataSet = new MessageDataSet(hamFile, spamFile);
				MessageDataSet dataSubset = null;

				dataSet.replicate(seed);
				dataSet.shuffle(seed);

				dataSubset = dataSet.getSubset(0, 40);
				BasicMLDataSet trainingSet = new BasicMLDataSet(dataSubset.getInputDataAsPrimitiveMatrix(),
						dataSubset.getOutputDataAsPrimitiveMatrix());

				dataSubset = dataSet.getSubset(40, 60);
				BasicMLDataSet validationSet = new BasicMLDataSet(dataSubset.getInputDataAsPrimitiveMatrix(),
						dataSubset.getOutputDataAsPrimitiveMatrix());

				dataSubset = dataSet.getSubset(60, 100);
				dataSubset.insertEmptyPatterns(folder);
				BasicMLDataSet testSet = new BasicMLDataSet(dataSubset.getInputDataAsPrimitiveMatrix(),
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
		}

		Encog.getInstance().shutdown();
		Runtime.getRuntime().gc();
	}

}
