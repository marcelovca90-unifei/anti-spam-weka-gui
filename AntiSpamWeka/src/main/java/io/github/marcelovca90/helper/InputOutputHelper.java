/*******************************************************************************
 * Copyright (C) 2017 Marcelo Vinícius Cysneiros Aragão
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package io.github.marcelovca90.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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

import io.github.marcelovca90.common.DataSetMetadata;
import io.github.marcelovca90.common.MethodConfiguration;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class InputOutputHelper
{
    public static final String TAG_CLASS = "class";
    public static final String TAG_HAM = "ham";
    public static final String TAG_SPAM = "spam";

    public String buildClassifierFilename(String folder, MethodConfiguration method, double splitPercent, int seed)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(folder + File.separator);
        sb.append(method.getClazz().getSimpleName());
        sb.append("_TRAIN=" + (int) (100 * splitPercent));
        sb.append("_TEST=" + (int) (100 * (1.0 - splitPercent)));
        sb.append("_SEED=" + seed);
        sb.append(".model");

        return sb.toString();
    }

    public Instances createEmptyInstances(int featureAmount, int emptyHamCount, int emptySpamCount)
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

    public Set<DataSetMetadata> loadDataSetsMetadataFromFile(String filename) throws IOException
    {
        Set<DataSetMetadata> metadata = new LinkedHashSet<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

        String line = null;

        while ((line = reader.readLine()) != null)
        {
            // only process the line if it is not empty and does not start with a comment mark (#)
            if (!StringUtils.isEmpty(line) && !line.startsWith("#"))
            {
                // replaces the user home symbol (~) with the actual folder path
                if (line.startsWith("~"))
                    line = line.replaceAll("~", System.getProperty("user.home"));
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

    public Instances loadInstancesFromFile(String hamDataFilename, String spamDataFilename) throws IOException
    {
        // read ham amounts
        ByteBuffer hamBuffer = readBytesFromFile(hamDataFilename);
        int hamInstanceAmount = 0;
        int hamFeatureAmount = 0;
        Instances hamDataSet;

        hamInstanceAmount = hamBuffer.getInt();
        hamFeatureAmount = hamBuffer.getInt();
        Instance hamInstance;

        // create ham attributes
        ArrayList<Attribute> hamAttributes = createAttributes(hamFeatureAmount);

        // create ham data set
        hamDataSet = new Instances(TAG_HAM, hamAttributes, hamInstanceAmount);
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
        int spamInstanceAmount = 0;
        int spamFeatureAmount = 0;
        Instances spamDataSet = null;

        spamInstanceAmount = spamBuffer.getInt();
        spamFeatureAmount = spamBuffer.getInt();
        Instance spamInstance;

        // create spam attributes
        ArrayList<Attribute> spamAttributes = createAttributes(spamFeatureAmount);

        // create spam data set
        spamDataSet = new Instances(TAG_SPAM, spamAttributes, spamInstanceAmount);
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

    public Classifier loadModelFromFile(String filename) throws Exception
    {
        return (Classifier) weka.core.SerializationHelper.read(filename);
    }

    public File saveInstancesToFile(Instances instances, String filename) throws IOException
    {
        // create output file's buffered writer
        File file = new File(filename);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        // write attribute names
        for (int attrIndex = 0; attrIndex < instances.numAttributes(); attrIndex++)
        {
            if (attrIndex > 0)
                bufferedWriter.write(",");
            bufferedWriter.write(attrIndex != instances.classIndex() ? instances.attribute(attrIndex).name() : TAG_CLASS);
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

        return file;
    }

    public File saveModelToFile(String filename, Classifier classifier) throws Exception
    {
        weka.core.SerializationHelper.write(filename, classifier);

        return new File(filename);
    }

    private ArrayList<Attribute> createAttributes(int featureAmount)
    {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < featureAmount; i++)
            attributes.add(new Attribute("x" + i));
        attributes.add(new Attribute(TAG_CLASS, Arrays.asList(TAG_HAM, TAG_SPAM)));
        return attributes;
    }

    private ByteBuffer readBytesFromFile(String filename) throws IOException
    {
        File file = new File(filename);
        FileChannel channel = null;
        ByteBuffer buffer = null;
        FileInputStream stream = new FileInputStream(file);

        channel = stream.getChannel();
        buffer = ByteBuffer.allocate((int) file.length());
        channel.read(buffer);
        channel.close();
        stream.close();
        buffer.flip();

        return buffer;
    }
}