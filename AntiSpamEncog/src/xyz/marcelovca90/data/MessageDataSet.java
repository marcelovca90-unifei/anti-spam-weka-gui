package xyz.marcelovca90.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author marcelovca90
 *
 */
public class MessageDataSet {

	private ArrayList<MessageData> messages;

	private String hamFilePath;

	private String spamFilePath;

	private int hamCount;

	private int spamCount;

	private MessageDataSet(ArrayList<MessageData> messages, String hamFilePath,
			String spamFilePath, int hamCount, int spamCount) {

		this.messages = messages;
		this.hamFilePath = hamFilePath;
		this.spamFilePath = spamFilePath;
		this.hamCount = hamCount;
		this.spamCount = spamCount;
	}

	public MessageDataSet(File hamFile, File spamFile) {

		this.messages = new ArrayList<MessageData>();
		this.hamFilePath = hamFile.getPath().substring(
				hamFile.getPath().indexOf("Vectors") + "Vectors".length());
		this.spamFilePath = spamFile.getPath().substring(
				hamFile.getPath().indexOf("Vectors") + "Vectors".length());
		this.addMessagesFromFile(hamFile);
		this.addMessagesFromFile(spamFile);
	}

	private void addMessagesFromFile(File file) {

		// open file
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		FileChannel fileIn = stream.getChannel();

		// load data
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

			double[] data = new double[featureAmount];

			for (int j = 0; j < featureAmount; j++)
				data[j] = inBuffer.getDouble();

			MessageType type = file.getName().contains("ham") ? MessageType.HAM
					: MessageType.SPAM;

			this.messages.add(new MessageData(data, type));
			if (type == MessageType.HAM)
				this.hamCount++;
			else
				this.spamCount++;
		}
	}

	public int getHamCount() {
		return hamCount;
	}

	public ArrayList<MessageData> getMessages() {
		return messages;
	}

	public int getSpamCount() {
		return spamCount;
	}

	public MessageDataSet getSubset(int startPercentage, int endPercentage) {

		double startPercentageDouble = ((double) startPercentage / 100.0);
		double endPercentageDouble = ((double) endPercentage / 100.0);

		int startIndex = (int) ((double) this.messages.size() * startPercentageDouble);
		int endIndex = (int) ((double) this.messages.size() * endPercentageDouble);

		ArrayList<MessageData> newData = new ArrayList<MessageData>(
				this.messages.subList(startIndex, endIndex));

		String newHamFilePath = String.format("%s[%d,%d]", hamFilePath,
				startPercentage, endPercentage);
		String newSpamFilePath = String.format("%s[%d,%d]", spamFilePath,
				startPercentage, endPercentage);

		int newHamCount = 0;
		int newSpamCount = 0;

		for (MessageData messageData : newData) {
			if (messageData.getType() == MessageType.HAM)
				newHamCount++;
			else
				newSpamCount++;
		}

		return new MessageDataSet(newData, newHamFilePath, newSpamFilePath,
				newHamCount, newSpamCount);
	}

	public void shuffle(long seed) {

		Random random = new Random(seed);

		for (int i = this.messages.size() - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);
			MessageData backup = this.messages.get(index);
			this.messages.set(index, this.messages.get(i));
			this.messages.set(i, backup);
		}
	}

	@Override
	public String toString() {
		return "MessageDataSet [hamFilePath=" + hamFilePath + ", spamFilePath="
				+ spamFilePath + ", hamCount=" + hamCount + ", spamCount="
				+ spamCount + "]";
	}

}
