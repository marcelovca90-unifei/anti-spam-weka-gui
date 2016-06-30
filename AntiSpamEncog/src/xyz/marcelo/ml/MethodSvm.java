package xyz.marcelo.ml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.KernelType;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.SVMType;
import org.encog.ml.svm.training.SVMTrain;

import xyz.marcelo.common.Folders;
import xyz.marcelo.common.Enumerates.MessageLabel;

/**
 * @author marcelovca90
 * 
 */
public class MethodSvm
{
	private static final Logger logger = LogManager.getLogger(MethodSvm.class);

	public static void run(String folder, BasicMLDataSet trainingSet, BasicMLDataSet validationSet,
			BasicMLDataSet testSet, int seed)
	{

		int inputCount = testSet.get(0).getInput().size();

		SVM svm = new SVM(inputCount, SVMType.SupportVectorClassification, KernelType.RadialBasisFunction);

		double bestC = 0, bestGamma = 0, bestError = Double.MAX_VALUE;

		for (int i = 1; i <= 1000; i++)
		{
			SVMTrain svmTrainTemp = new SVMTrain(svm, validationSet);
			svmTrainTemp.setC(Math.random());
			svmTrainTemp.setGamma(Math.random());
			svmTrainTemp.setFold((int) Math.log10(inputCount) + 1);
			svmTrainTemp.iteration();
			if (svmTrainTemp.getError() < bestError)
			{
				bestError = svmTrainTemp.getError();
				bestC = svmTrainTemp.getC();
				bestGamma = svmTrainTemp.getGamma();
				/*
				 * logger.debug(String.format(
				 * "Best error so far = %f (i = %d, C=%f, GAMMA=%f)", bestError,
				 * i, bestC, bestGamma));
				 */
			}
		}

		SVMTrain svmTrain = new SVMTrain(svm, trainingSet);
		svmTrain.setC(bestC);
		svmTrain.setGamma(bestGamma);

		svmTrain.iteration();

		int hamCount = 0, hamCorrect = 0;
		int spamCount = 0, spamCorrect = 0;

		for (MLDataPair pair : testSet)
		{

			MLData input = pair.getInput();
			MLData ideal = pair.getIdeal();
			MLData output = svm.compute(input);

			if (MethodUtil.infer(ideal.getData()) == MessageLabel.HAM)
			{
				hamCount++;
				if (Math.abs(output.getData(0) - 0.0) < 1e-6)
				{
					hamCorrect++;
				}
			} else if (MethodUtil.infer(ideal.getData()) == MessageLabel.SPAM)
			{
				spamCount++;
				if (Math.abs(output.getData(0) - 1.0) < 1e-6)
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
