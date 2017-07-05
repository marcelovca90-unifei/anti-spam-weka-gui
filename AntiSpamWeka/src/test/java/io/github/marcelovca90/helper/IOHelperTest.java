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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.common.DataSetMetadata;
import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.helper.IOHelper;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Instances;

@RunWith(MockitoJUnitRunner.class)
public class IOHelperTest
{
    private String metadataFilename;
    private String hamDataFilename;
    private String spamDataFilename;
    private Instances dataSet;

    @Before
    public void setUp() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        metadataFilename = classLoader.getResource("data-sets-bin/metadata.txt").getFile();
        hamDataFilename = classLoader.getResource("data-sets-bin/ham").getFile();
        spamDataFilename = classLoader.getResource("data-sets-bin/spam").getFile();
    }

    @Test
    public void loadDataSetsMetadataFromFile_shouldProperlyDeserializeDataSetsMetadata() throws IOException
    {
        Set<DataSetMetadata> metadatum = IOHelper.getInstance().loadDataSetsMetadataFromFile(metadataFilename);

        assertThat(metadatum, notNullValue());
        assertThat(metadatum.size(), equalTo(2));

        metadatum
            .stream()
            .forEach(v ->
            {
                assertThat(v.getFolder(), notNullValue());
                assertThat(v.getEmptyHamCount(), notNullValue());
                assertThat(v.getEmptySpamCount(), notNullValue());
            });
    }

    @Test
    public void loadInstancesFromFile_shouldDeserializeInstances() throws IOException
    {
        dataSet = IOHelper.getInstance().loadInstancesFromFile(hamDataFilename, spamDataFilename);

        assertThat(dataSet, notNullValue());
        assertThat(dataSet.size(), greaterThan(0));
        assertThat(dataSet.classAttribute(), notNullValue());
        assertThat(dataSet.numAttributes(), equalTo(11));
        assertThat(dataSet.classIndex(), notNullValue());
        assertThat(dataSet.numClasses(), equalTo(2));
    }

    @Test
    public void saveInstancesToFile_shouldProperlySerializeInstances() throws IOException
    {
        dataSet = IOHelper.getInstance().loadInstancesFromFile(hamDataFilename, spamDataFilename);

        File file = IOHelper.getInstance().saveInstancesToFile(dataSet, "data-set.csv");

        assertThat(file.exists(), equalTo(Boolean.TRUE));
        assertThat(file.delete(), equalTo(Boolean.TRUE));
    }

    @Test
    public void createEmptyInstances_shouldReturnDataSetWithGivenAmountOfInstances()
    {
        int featureAmount = RandomUtils.nextInt(1, 11);
        int emptyHamCount = RandomUtils.nextInt(1, 101);
        int emptySpamCount = RandomUtils.nextInt(1, 101);

        Instances emptyInstances = IOHelper.getInstance().createEmptyInstances(featureAmount, emptyHamCount, emptySpamCount);

        assertThat(emptyInstances, notNullValue());
        assertThat(emptyInstances.size(), equalTo(emptyHamCount + emptySpamCount));
        assertThat(emptyInstances.classAttribute(), notNullValue());
        assertThat(emptyInstances.numAttributes(), equalTo(featureAmount + 1));
        assertThat(emptyInstances.classIndex(), notNullValue());
        assertThat(emptyInstances.numClasses(), equalTo(2));
    }

    @Test
    public void buildClassifierFilename_shouldReturnStringWithClassifierDetails()
    {
        String folder = "/some/folder";
        MethodConfiguration method = MethodConfiguration.RT;
        double splitPercent = RandomUtils.nextDouble();
        int seed = RandomUtils.nextInt();

        String filename = IOHelper.getInstance().buildClassifierFilename(folder, method, splitPercent, seed);

        assertThat(filename, notNullValue());
        assertThat(filename, containsString(folder));
        assertThat(filename, containsString(method.getClazz().getSimpleName()));
        assertThat(filename, containsString(String.valueOf(seed)));
    }

    @Test
    public void saveModelToFile_shouldProperlySerializeModel() throws Exception
    {
        File file = IOHelper.getInstance().saveModelToFile("dummy.model", (Classifier) null);

        assertThat(file.exists(), equalTo(Boolean.TRUE));
        assertThat(file.delete(), equalTo(Boolean.TRUE));
    }

    @Test
    public void loadModelFromFile_shouldProperlyDeserializeModel() throws Exception
    {
        AbstractClassifier baseClassifier = MethodConfiguration.buildClassifierFor(MethodConfiguration.RT);

        File file = IOHelper.getInstance().saveModelToFile("dummy.model", baseClassifier);

        Classifier recoveredClassifier = IOHelper.getInstance().loadModelFromFile("dummy.model");

        assertThat(file.exists(), equalTo(Boolean.TRUE));
        assertThat(file.delete(), equalTo(Boolean.TRUE));
        assertThat(recoveredClassifier, notNullValue());
        assertThat(recoveredClassifier, instanceOf(MethodConfiguration.RT.getClazz()));
    }
}
