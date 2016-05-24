import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class DataConverter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			bin2csv("/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/20/ham",
					"/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/20/spam",
					"/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/20/data.csv");

			csv2arff("/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/20/data.csv",
					"/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/20/data.arff");
			
			FileReader fileReader = new FileReader("/home/marcelovca90/Mestrado/Vectors/Ling_2016/CHI2/20/data.arff");
			Instances train = new Instances(fileReader);
			train.setClassIndex(train.numAttributes() - 1);

			//Instance of NN
			MultilayerPerceptron mlp = new MultilayerPerceptron();

			//Setting Parameters
			mlp.setLearningRate(0.1);
			mlp.setMomentum(0.2);
			mlp.setTrainingTime(500);
			mlp.setHiddenLayers("10,10");
			mlp.buildClassifier(train);
			
			Evaluation eval = new Evaluation(train);
			eval.evaluateModel(mlp, train);

			System.out.println(eval.errorRate()); //Printing Training Mean root squared Error
			System.out.println(eval.toSummaryString()); //Summary of Training

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void bin2csv(String hamInput, String spamInput, String output) throws IOException {

		File hamFile = new File(hamInput);
		File spamFile = new File(spamInput);

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(output)));

		// read ham data
		FileInputStream hamStream = new FileInputStream(hamFile);
		FileChannel hamChannel = hamStream.getChannel();
		ByteBuffer hamBuffer = ByteBuffer.allocate((int) hamFile.length());
		hamChannel.read(hamBuffer);
		hamChannel.close();
		hamStream.close();
		hamBuffer.flip();

		int hamInstanceAmount = hamBuffer.getInt();
		int hamFeatureAmount = hamBuffer.getInt();
		double hamData;

		// write csv header
		for (int j = 0; j < hamFeatureAmount; j++)
			bufferedWriter.write("x" + (j + 1) + ",");
		bufferedWriter.write("class" + System.lineSeparator());

		// write ham data
		for (int i = 0; i < hamInstanceAmount; i++) {
			for (int j = 0; j < hamFeatureAmount; j++) {
				hamData = hamBuffer.getDouble();
				bufferedWriter.write(String.valueOf(hamData) + ",");
			}
			bufferedWriter.write("ham" + System.lineSeparator());
		}
		bufferedWriter.flush();

		// read spam data
		FileInputStream spamStream = new FileInputStream(spamFile);
		FileChannel spamChannel = spamStream.getChannel();
		ByteBuffer spamBuffer = ByteBuffer.allocate((int) spamFile.length());
		spamChannel.read(spamBuffer);
		spamChannel.close();
		spamStream.close();
		spamBuffer.flip();

		int spamInstanceAmount = spamBuffer.getInt();
		int spamFeatureAmount = spamBuffer.getInt();
		double spamData;

		// write spam data
		for (int i = 0; i < spamInstanceAmount; i++) {
			for (int j = 0; j < spamFeatureAmount; j++) {
				spamData = spamBuffer.getDouble();
				bufferedWriter.write(String.valueOf(spamData) + ",");
			}
			bufferedWriter.write("spam" + System.lineSeparator());
		}
		bufferedWriter.flush();

		bufferedWriter.close();
	}

	public static void csv2arff(String input, String output) {

		try {
			// load CSV
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File(input));
			Instances data = loader.getDataSet();
			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			saver.setFile(new File(output));
			saver.setDestination(new File(output));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
