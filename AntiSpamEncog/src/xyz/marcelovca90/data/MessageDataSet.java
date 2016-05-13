package xyz.marcelovca90.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import xyz.marcelovca90.ml.MethodNeuralUtil;

/**
 * @author marcelovca90
 * 
 */
public class MessageDataSet {

	private List<Double[]> inputData;

	private List<Double[]> outputData;

	private String hamFilePath;

	private String spamFilePath;

	private int hamCount;

	private int spamCount;

	public MessageDataSet(File hamFile, File spamFile) {

		this.inputData = new ArrayList<Double[]>();
		this.outputData = new ArrayList<Double[]>();

		this.hamFilePath = hamFile.getPath().substring(
				hamFile.getPath().indexOf("Vectors") + "Vectors".length());
		this.spamFilePath = spamFile.getPath().substring(
				hamFile.getPath().indexOf("Vectors") + "Vectors".length());

		this.addMessagesFromFile(hamFile);
		this.addMessagesFromFile(spamFile);
	}

	private MessageDataSet(List<Double[]> inputData, List<Double[]> outputData,
			String hamFilePath, String spamFilePath, int hamCount, int spamCount) {

		this.inputData = inputData;
		this.outputData = outputData;
		this.hamFilePath = hamFilePath;
		this.spamFilePath = spamFilePath;
		this.hamCount = hamCount;
		this.spamCount = spamCount;
	}

	private void addMessagesFromFile(File file) {

		try {
			if (!file.getName().contains("ham")
					&& !file.getName().contains("spam"))
				throw new IOException("File name must contain 'ham' or 'spam'.");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// open file
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		FileChannel fileIn = stream.getChannel();

		// load input
		int bufferLen = (int) file.length();

		ByteBuffer inBuffer = ByteBuffer.allocate(bufferLen);

		try {
			int readAmount = fileIn.read(inBuffer);
			if (readAmount != bufferLen)
				throw new IOException();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileIn.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inBuffer.flip(); // reset pointer
		}

		int instanceAmount = inBuffer.getInt(); // amount of instances
		int featureAmount = inBuffer.getInt(); // amount of features in each
		// instance

		for (int i = 0; i < instanceAmount; i++) {

			Double[] tempInputData = new Double[featureAmount];

			for (int j = 0; j < featureAmount; j++)
				tempInputData[j] = inBuffer.getDouble();

			this.inputData.add(tempInputData);

			MessageLabel tempOutputLabel = file.getName().contains("ham") ? MessageLabel.HAM
					: MessageLabel.SPAM;

			switch (tempOutputLabel) {
			case HAM:
				this.hamCount++;
				break;
			case SPAM:
				this.spamCount++;
				break;
			}

			Double[] tempOutputData = tempOutputLabel.getValue();

			this.outputData.add(tempOutputData);
		}
	}

	public int getHamCount() {
		return hamCount;
	}

	public String getHamFilePath() {
		return hamFilePath;
	}

	public List<Double[]> getInputData() {
		return inputData;
	}

	public double[][] getInputDataAsPrimitiveMatrix() {
		double[][] matrix = new double[inputData.size()][];
		for (int i = 0; i < matrix.length; i++)
			matrix[i] = ArrayUtils.toPrimitive(inputData.get(i));
		return matrix;
	}

	public List<Double[]> getOutputData() {
		return outputData;
	}

	public double[][] getOutputDataAsPrimitiveMatrix() {

		double[][] matrix = new double[outputData.size()][];
		for (int i = 0; i < matrix.length; i++)
			matrix[i] = ArrayUtils.toPrimitive(outputData.get(i));
		return matrix;
	}

	public int getSpamCount() {
		return spamCount;
	}

	public String getSpamFilePath() {
		return spamFilePath;
	}

	public MessageDataSet getSubset(int startPercentage, int endPercentage) {

		double startPercentageDouble = ((double) startPercentage / 100.0);
		double endPercentageDouble = ((double) endPercentage / 100.0);

		int inputStartIndex = (int) ((double) this.inputData.size() * startPercentageDouble);
		int inputEndIndex = (int) ((double) this.inputData.size() * endPercentageDouble);
		List<Double[]> newInputData = this.inputData.subList(inputStartIndex,
				inputEndIndex);

		int outputStartIndex = (int) ((double) this.outputData.size() * startPercentageDouble);
		int outputEndIndex = (int) ((double) this.outputData.size() * endPercentageDouble);
		List<Double[]> newOutputData = this.outputData.subList(
				outputStartIndex, outputEndIndex);

		String newHamFilePath = String.format("%s[%d,%d]", hamFilePath,
				startPercentage, endPercentage);
		String newSpamFilePath = String.format("%s[%d,%d]", spamFilePath,
				startPercentage, endPercentage);

		int newHamCount = 0;
		int newSpamCount = 0;

		for (Double[] output : newOutputData) {
			switch (MethodNeuralUtil.infer(output)) {
			case HAM:
				newHamCount++;
				break;
			case SPAM:
				newSpamCount++;
				break;
			}
		}

		return new MessageDataSet(newInputData, newOutputData, newHamFilePath,
				newSpamFilePath, newHamCount, newSpamCount);
	}

	public void shuffle(long seed) {

		if (this.inputData.size() != this.outputData.size())
			throw new ArithmeticException(
					"Input and output arrays must have the same size.");

		Random random = new Random(seed);
		Double[] backup;

		for (int i = this.inputData.size() - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);

			backup = this.inputData.get(index);
			this.inputData.set(index, this.inputData.get(i));
			this.inputData.set(i, backup);

			backup = this.outputData.get(index);
			this.outputData.set(index, this.outputData.get(i));
			this.outputData.set(i, backup);
		}
	}

	@Override
	public String toString() {

		return "MessageDataSet [hamFilePath=" + hamFilePath + ", spamFilePath="
				+ spamFilePath + ", hamCount=" + hamCount + ", spamCount="
				+ spamCount + "]";
	}

}
