package xyz.marcelo.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.lang3.tuple.Pair;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class InputOutputHelper
{
    public static void bin2csv(String hamInput, String spamInput, String outputFilename) throws IOException
    {
        File hamFile = new File(hamInput);
        File spamFile = new File(spamInput);
        File outputFile = new File(outputFilename);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

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
        for (int i = 0; i < hamInstanceAmount; i++)
        {
            for (int j = 0; j < hamFeatureAmount; j++)
            {
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
        for (int i = 0; i < spamInstanceAmount; i++)
        {
            for (int j = 0; j < spamFeatureAmount; j++)
            {
                spamData = spamBuffer.getDouble();
                bufferedWriter.write(String.valueOf(spamData) + ",");
            }
            bufferedWriter.write("spam" + System.lineSeparator());
        }
        bufferedWriter.flush();

        bufferedWriter.close();
    }

    public static double[][] bin2double(String filename) throws IOException
    {
        File file = new File(filename);
        FileInputStream stream = new FileInputStream(file);
        FileChannel channel = stream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        channel.read(buffer);
        channel.close();
        stream.close();
        buffer.flip();

        int instanceAmount = buffer.getInt();
        int featureAmount = buffer.getInt();

        double[][] data = new double[instanceAmount][featureAmount];

        for (int i = 0; i < instanceAmount; i++)
            for (int j = 0; j < featureAmount; j++)
                data[i][j] = buffer.getDouble();

        return data;
    }

    public static void buildEmptyCsv(String folder, int featureAmount) throws IOException
    {
        Pair<Integer, Integer> emptyPatterns = EmptyInstanceHelper.getEmptyInstancesCountByFolder(folder);
        int emptyHamCount = emptyPatterns.getLeft();
        int emptySpamCount = emptyPatterns.getRight();

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < featureAmount; i++)
            buffer.append("0.0,");

        String emptyHam = buffer.toString() + "ham";
        String emptySpam = buffer.toString() + "spam";

        String output = folder + File.separator + "empty.csv";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(output)));

        for (int i = 0; i < featureAmount; i++)
            bufferedWriter.write("x" + (i + 1) + ",");
        bufferedWriter.write("class" + System.lineSeparator());

        for (int i = 0; i < emptyHamCount; i++)
            bufferedWriter.write(emptyHam + System.lineSeparator());

        for (int i = 0; i < emptySpamCount; i++)
            bufferedWriter.write(emptySpam + System.lineSeparator());

        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void csv2arff(String inputFilename, String outputFilename) throws IOException
    {
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(inputFilename));
        Instances data = loader.getDataSet();

        // save ARFF
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
        writer.write(data.toString());
        writer.flush();
        writer.close();
    }

    public static void saveModelToFile(String filename, Classifier classifier) throws Exception
    {
        weka.core.SerializationHelper.write(filename, classifier);
    }

    public static Classifier loadModelFromFile(String filename) throws Exception
    {
        return (Classifier) weka.core.SerializationHelper.read(filename);
    }
}
