import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.marcelovca90.data.MessageDataSet;

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

		File hamFile = new File("/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/10/ham");
		File spamFile = new File("/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/10/spam");
		
		MessageDataSet dataSet = new MessageDataSet(hamFile, spamFile);
		logger.log(Level.DEBUG, dataSet);
		
		MessageDataSet trainingSet = dataSet.getSubset(0, 40);
		logger.log(Level.DEBUG, trainingSet);
		
		MessageDataSet validationSet = dataSet.getSubset(40, 60);
		logger.log(Level.DEBUG, validationSet);
		
		MessageDataSet testSet = dataSet.getSubset(60, 100);
		logger.log(Level.DEBUG, testSet);
	}

}
