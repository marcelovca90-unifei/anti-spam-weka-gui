package xyz.marcelo.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.tuple.Pair;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class InputOutputHelper
{
    private static final String TAG_HAM = "ham";
    private static final String TAG_SPAM = "spam";
    private static final String TAG_CLASS = "class";

    public static File bin2csv(String hamInput, String spamInput, String outputFilename) throws IOException
    {
        // create objects to handle the output file
        File outputFile = new File(outputFilename);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        // read ham data
        File hamFile = new File(hamInput);
        ByteBuffer hamBuffer = extractBytesFromFile(hamFile);

        int hamInstanceAmount = hamBuffer.getInt();
        int hamFeatureAmount = hamBuffer.getInt();
        double hamData;

        // write csv header
        for (int j = 0; j < hamFeatureAmount; j++)
            bufferedWriter.write("x" + (j + 1) + ",");
        bufferedWriter.write(TAG_CLASS + System.lineSeparator());

        // write ham data
        for (int i = 0; i < hamInstanceAmount; i++)
        {
            for (int j = 0; j < hamFeatureAmount; j++)
            {
                hamData = hamBuffer.getDouble();
                bufferedWriter.write(String.valueOf(hamData) + ",");
            }
            bufferedWriter.write(TAG_HAM + System.lineSeparator());
        }
        bufferedWriter.flush();

        // read spam data
        File spamFile = new File(spamInput);
        ByteBuffer spamBuffer = extractBytesFromFile(spamFile);

        int spamInstanceAmount = spamBuffer.getInt();
        int spamFeatureAmount = spamBuffer.getInt();
        double spamData;

        // write ham data
        for (int i = 0; i < spamInstanceAmount; i++)
        {
            for (int j = 0; j < spamFeatureAmount; j++)
            {
                spamData = spamBuffer.getDouble();
                bufferedWriter.write(String.valueOf(spamData) + ",");
            }
            bufferedWriter.write(TAG_SPAM + System.lineSeparator());
        }
        bufferedWriter.flush();

        bufferedWriter.close();

        return outputFile;
    }

    public static Instances bin2instances(String hamInput, String spamInput) throws IOException
    {
        // // read ham amounts
        File hamFile = new File(hamInput);
        ByteBuffer hamBuffer = extractBytesFromFile(hamFile);
        int hamInstanceAmount = hamBuffer.getInt();
        int hamFeatureAmount = hamBuffer.getInt();
        Instance hamInstance;

        // create ham attributes
        ArrayList<Attribute> hamAttributes = createAttributes(hamFeatureAmount);

        // create ham data set
        Instances hamDataSet = new Instances(TAG_HAM, hamAttributes, hamInstanceAmount);
        hamDataSet.setClassIndex(hamAttributes.size() - 1);

        // read ham data and insert in data set
        for (int i = 0; i < hamInstanceAmount; i++)
        {
            hamInstance = new DenseInstance(hamFeatureAmount + 1);
            hamInstance.setDataset(hamDataSet);
            for (int j = 0; j < hamFeatureAmount; j++)
                hamInstance.setValue(j, hamBuffer.getDouble());
            hamInstance.setClassValue(TAG_HAM);
            hamDataSet.add(hamInstance);
        }

        // // read spam amounts
        File spamFile = new File(spamInput);
        ByteBuffer spamBuffer = extractBytesFromFile(spamFile);
        int spamInstanceAmount = spamBuffer.getInt();
        int spamFeatureAmount = spamBuffer.getInt();
        Instance spamInstance;

        // create spam attributes
        ArrayList<Attribute> spamAttributes = createAttributes(spamFeatureAmount);

        // create spam data set
        Instances spamDataSet = new Instances(TAG_SPAM, spamAttributes, spamInstanceAmount);
        spamDataSet.setClassIndex(spamAttributes.size() - 1);

        // read spam data and insert in data set
        for (int i = 0; i < spamInstanceAmount; i++)
        {
            spamInstance = new DenseInstance(spamFeatureAmount + 1);
            spamInstance.setDataset(spamDataSet);
            for (int j = 0; j < spamFeatureAmount; j++)
                spamInstance.setValue(j, spamBuffer.getDouble());
            spamInstance.setClassValue(TAG_SPAM);
            spamDataSet.add(spamInstance);
        }

        // create merged data set attributes
        ArrayList<Attribute> dataSetAttributes = createAttributes(spamFeatureAmount);

        // create and fill merged data set
        Instances dataSet = new Instances("AntiSpamNotEmpty", dataSetAttributes, hamDataSet.size() + spamDataSet.size());
        dataSet.addAll(hamDataSet);
        dataSet.addAll(spamDataSet);
        dataSet.setClassIndex(dataSet.numAttributes() - 1);

        return dataSet;
    }

    public static File buildEmptyCsv(String folder, int featureAmount) throws IOException
    {
        Pair<Integer, Integer> emptyPatterns = EmptyInstanceHelper.getEmptyInstancesCountByFolder(folder);
        int emptyHamCount = emptyPatterns.getLeft();
        int emptySpamCount = emptyPatterns.getRight();

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < featureAmount; i++)
            buffer.append("0.0,");

        String emptyHam = buffer.toString() + TAG_HAM;
        String emptySpam = buffer.toString() + TAG_SPAM;

        String outputFilename = folder + File.separator + "empty.csv";
        File outputFile = new File(outputFilename);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        for (int i = 0; i < featureAmount; i++)
            bufferedWriter.write("x" + (i + 1) + ",");
        bufferedWriter.write(TAG_CLASS + System.lineSeparator());

        for (int i = 0; i < emptyHamCount; i++)
            bufferedWriter.write(emptyHam + System.lineSeparator());

        for (int i = 0; i < emptySpamCount; i++)
            bufferedWriter.write(emptySpam + System.lineSeparator());

        bufferedWriter.flush();
        bufferedWriter.close();

        return outputFile;
    }

    public static Instances buildEmptyInstances(String folder, int featureAmount) throws IOException
    {
        Pair<Integer, Integer> emptyPatterns = EmptyInstanceHelper.getEmptyInstancesCountByFolder(folder);

        // retrieve empty ham count and declare instance to be reused
        int emptyHamCount = emptyPatterns.getLeft();
        Instance hamInstance;

        // create ham attributes
        ArrayList<Attribute> hamAttributes = createAttributes(featureAmount);

        // create ham data set
        Instances hamDataSet = new Instances(TAG_HAM, hamAttributes, emptyHamCount);
        hamDataSet.setClassIndex(hamAttributes.size() - 1);

        // read ham data and insert in data set
        for (int i = 0; i < emptyHamCount; i++)
        {
            hamInstance = new DenseInstance(featureAmount + 1);
            hamInstance.setDataset(hamDataSet);
            for (int j = 0; j < featureAmount; j++)
                hamInstance.setValue(j, 0.0);
            hamInstance.setClassValue(TAG_HAM);
            hamDataSet.add(hamInstance);
        }

        // retrieve empty spam count and declare instance to be reused
        int emptySpamCount = emptyPatterns.getRight();
        Instance spamInstance;

        // create spam attributes
        ArrayList<Attribute> spamAttributes = createAttributes(featureAmount);

        // create spam data set
        Instances spamDataSet = new Instances(TAG_SPAM, spamAttributes, emptySpamCount);
        spamDataSet.setClassIndex(spamAttributes.size() - 1);

        // read spam data and insert in data set
        for (int i = 0; i < emptySpamCount; i++)
        {
            spamInstance = new DenseInstance(featureAmount + 1);
            spamInstance.setDataset(spamDataSet);
            for (int j = 0; j < featureAmount; j++)
                spamInstance.setValue(j, 0.0);
            spamInstance.setClassValue(TAG_SPAM);
            spamDataSet.add(spamInstance);
        }

        // create merged data set attributes
        ArrayList<Attribute> dataSetAttributes = createAttributes(featureAmount);

        // create and fill merged data set
        Instances dataSet = new Instances("AntiSpamEmpty", dataSetAttributes, hamDataSet.size() + spamDataSet.size());
        dataSet.addAll(hamDataSet);
        dataSet.addAll(spamDataSet);
        dataSet.setClassIndex(dataSet.numAttributes() - 1);

        return dataSet;
    }

    public static File csv2arff(String inputFilename, String outputFilename) throws IOException
    {
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(inputFilename));
        Instances data = loader.getDataSet();

        // save ARFF
        File outputFile = new File(outputFilename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(data.toString());
        writer.flush();
        writer.close();

        return outputFile;
    }

    private static ByteBuffer extractBytesFromFile(File file) throws FileNotFoundException, IOException
    {
        FileInputStream hamStream = new FileInputStream(file);
        FileChannel hamChannel = hamStream.getChannel();
        ByteBuffer hamBuffer = ByteBuffer.allocate((int) file.length());
        hamChannel.read(hamBuffer);
        hamChannel.close();
        hamStream.close();
        hamBuffer.flip();

        return hamBuffer;
    }

    private static ArrayList<Attribute> createAttributes(int featureAmount)
    {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < featureAmount; i++)
            attributes.add(new Attribute("x" + i));
        attributes.add(new Attribute(TAG_CLASS, Arrays.asList(TAG_HAM, TAG_SPAM)));
        return attributes;
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
