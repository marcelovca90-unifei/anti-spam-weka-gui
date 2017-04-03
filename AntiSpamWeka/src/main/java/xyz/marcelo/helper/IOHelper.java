package xyz.marcelo.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import xyz.marcelo.common.DataSetMetadata;
import xyz.marcelo.common.MethodConfiguration;

public class IOHelper
{
    public static final String TAG_HAM = "ham";
    public static final String TAG_SPAM = "spam";
    public static final String TAG_CLASS = "class";

    public static Set<DataSetMetadata> loadDataSetsMetadataFromFile(String filename) throws IOException
    {
        Set<DataSetMetadata> metadata = new LinkedHashSet<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

        String line;

        while ((line = reader.readLine()) != null)
        {
            // only process the line if it is not empty and does not start with a comment mark (# or //)
            if (!StringUtils.isEmpty(line) && !line.startsWith("#") && !line.startsWith("//"))
            {
                // replaces the user home symbol (~) with the actual folder path
                if (line.startsWith("~"))
                {
                    line = line.replaceAll("~", System.getProperty("user.home"));
                }
                String[] parts = line.split(",");
                String folder = parts[0];
                Integer emptyHamAmount = Integer.parseInt(parts[1]);
                Integer emptySpamAmount = Integer.parseInt(parts[2]);

                metadata.add(new DataSetMetadata(folder, emptyHamAmount, emptySpamAmount));
            }
        }

        reader.close();

        return metadata;
    }

    public static Instances loadInstancesFromFile(String hamDataFilename, String spamDataFilename) throws IOException
    {
        // read ham amounts
        ByteBuffer hamBuffer = readBytesFromFile(hamDataFilename);
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

        // read spam amounts
        ByteBuffer spamBuffer = readBytesFromFile(spamDataFilename);
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

    public static void saveInstancesToFile(Instances instances, String filename) throws IOException
    {
        // create output file's buffered writer
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filename)));

        // write attribute names
        for (int i = 0; i < instances.numAttributes(); i++)
        {
            if (i > 0) bufferedWriter.write(",");
            bufferedWriter.write(i != instances.classIndex() ? instances.attribute(i).name() : TAG_CLASS);
        }
        bufferedWriter.write(System.lineSeparator());

        // write instances
        for (int i = 0; i < instances.size(); i++)
        {
            bufferedWriter.write(instances.get(i).toStringNoWeight());
            bufferedWriter.write(System.lineSeparator());
        }

        // flush and close the buffered writer
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static Instances createEmptyInstances(int featureAmount, int emptyHamCount, int emptySpamCount)
    {
        // declare ham instance to be reused
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

        // declare spam instance to be reused
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

    public static boolean fileExists(String filename)
    {
        return new File(filename).exists();
    }

    private static ByteBuffer readBytesFromFile(String filename) throws FileNotFoundException, IOException
    {
        File file = new File(filename);
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

    public static String buildClassifierFilename(String folder, MethodConfiguration method, double trainRatio, int seed)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(folder + File.separator);
        sb.append(method.getClazz().getSimpleName());
        sb.append("_TRAIN=" + (int) (100 * trainRatio));
        sb.append("_TEST=" + (int) (100 * (1.0 - trainRatio)));
        sb.append("_SEED=" + seed);
        sb.append(".model");

        return sb.toString();
    }

    public static void saveModelToFile(String filename, Classifier classifier) throws Exception
    {
        weka.core.SerializationHelper.write(filename, classifier);

        File outputFileZip = new File(filename + ".zip");

        if (outputFileZip.exists())
        {
            outputFileZip.delete();
            outputFileZip.createNewFile();
        }

        ZipUtils.compress(filename);
    }

    public static Classifier loadModelFromFile(String filename) throws Exception
    {
        String filenameZip = filename + ".zip";

        if (IOHelper.fileExists(filenameZip) && ZipUtils.isZipped(filenameZip)) ZipUtils.extract(filenameZip);

        return (Classifier) weka.core.SerializationHelper.read(filename);
    }
}
