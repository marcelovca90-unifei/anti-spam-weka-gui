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
package xyz.marcelo.helper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import weka.core.Instances;

public class FilterHelperTest
{
    private Instances dataSet;

    @Before
    public void setUp() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("iris.arff").getFile());
        FileReader reader = new FileReader(file);

        dataSet = new Instances(reader);
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
    }

    @Test
    public void constructor_shouldReturnNotNullInstance()
    {
        assertThat(new FilterHelper(), notNullValue());
    }

    @Test
    public void applyAttributeFilter_shouldReturnDataSetWithPotentiallyyReducedAttributes() throws Exception
    {
        int attributesBefore = dataSet.numAttributes();

        dataSet = FilterHelper.applyAttributeFilter(dataSet);

        int attributesAfter = dataSet.numAttributes();

        assertThat(attributesAfter, lessThanOrEqualTo(attributesBefore));
    }

    @Test
    public void applyInstanceFilter_shouldReturnDataSetWithPotentiallyyReducedInstances() throws Exception
    {
        int instancesBefore = dataSet.size();

        dataSet = FilterHelper.applyInstanceFilter(dataSet);

        int instancesAfter = dataSet.size();

        assertThat(instancesAfter, lessThanOrEqualTo(instancesBefore));
    }
}
