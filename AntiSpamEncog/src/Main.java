import java.io.File;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encog.ml.data.basic.BasicMLDataSet;

import xyz.marcelovca90.common.Folders;
import xyz.marcelovca90.common.Primes;
import xyz.marcelovca90.data.MessageDataSet;
import xyz.marcelovca90.ml.MlpBprop;
import xyz.marcelovca90.ml.MlpRprop;

/**
 * @author marcelovca90
 *
 */
public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		logger.info("Process started at " + Calendar.getInstance().getTime());

		int seed = Primes.getRandomPrime();

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

			MlpBprop.run(trainingSet, validationSet, testSet, seed);
			MlpRprop.run(trainingSet, validationSet, testSet, seed);
		}

		logger.info("Process finished at " + Calendar.getInstance().getTime());
	}

}
