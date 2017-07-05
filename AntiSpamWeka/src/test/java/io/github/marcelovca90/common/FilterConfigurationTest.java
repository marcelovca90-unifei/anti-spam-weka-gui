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
package io.github.marcelovca90.common;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.common.FilterConfiguration.AttributeFilter;
import io.github.marcelovca90.common.FilterConfiguration.InstanceFilter;
import io.github.marcelovca90.helper.IOHelper;
import weka.core.Instances;

@RunWith(MockitoJUnitRunner.class)
public class FilterConfigurationTest
{
    private String hamDataFilename;
    private String spamDataFilename;
    private Instances dataSet;

    @Before
    public void setUp() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        hamDataFilename = classLoader.getResource("data-sets-bin/ham").getFile();
        spamDataFilename = classLoader.getResource("data-sets-bin/spam").getFile();
    }

    @Test(expected = IllegalAccessException.class)
    public void privateConstructor_shouldThrowException() throws Exception
    {
        Constructor<FilterConfiguration> constructor = FilterConfiguration.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers()), equalTo(true));

        constructor.setAccessible(true);
        constructor.newInstance();
        FilterConfiguration.class.newInstance();
    }

    @Test
    public void enum_AttributeFilter_shouldReturnTwentyValues()
    {
        assertThat(FilterConfiguration.AttributeFilter.values().length, equalTo(20));
        Arrays
            .stream(FilterConfiguration.AttributeFilter.values())
            .forEach(v -> assertThat(FilterConfiguration.AttributeFilter.valueOf(v.name()), notNullValue()));
    }

    @Test
    public void getters_forAllAttributeFilters_shouldReturnNotNullValues()
    {
        Arrays
            .stream(FilterConfiguration.AttributeFilter.values())
            .forEach(v ->
            {
                assertThat(v.getEvalClazz(), notNullValue());
                assertThat(v.getEvalConfig(), notNullValue());
                assertThat(v.getSearchClazz(), notNullValue());
                assertThat(v.getSearchConfig(), notNullValue());
                assertThat(v.getDescription(), notNullValue());
            });
    }

    @Test
    public void buildAttributeFilterFor_dummyDataSet_shouldReturnNullDataSet()
    {
        assertThat(FilterConfiguration.buildAndApply(mock(Instances.class), AttributeFilter.CfsSubsetEval_BestFirst), nullValue());
    }

    @Test
    public void buildAttributeFilterFor_actualDataSet_shouldReturnNullDataSet() throws IOException
    {
        dataSet = IOHelper.getInstance().loadInstancesFromFile(hamDataFilename, spamDataFilename);

        assertThat(FilterConfiguration.buildAndApply(dataSet, AttributeFilter.CfsSubsetEval_BestFirst), notNullValue());
    }

    @Test
    public void enum_InstanceFilter_shouldReturnFourValues()
    {
        assertThat(FilterConfiguration.InstanceFilter.values().length, equalTo(4));
        Arrays
            .stream(FilterConfiguration.InstanceFilter.values())
            .forEach(v -> assertThat(FilterConfiguration.InstanceFilter.valueOf(v.name()), notNullValue()));
    }

    @Test
    public void getters_forAllInstanceFilters_shouldReturnNotNullValues()
    {
        Arrays
            .stream(FilterConfiguration.InstanceFilter.values())
            .forEach(v ->
            {
                assertThat(v.getClazz(), notNullValue());
                assertThat(v.getConfig(), notNullValue());
                assertThat(v.getDescription(), notNullValue());
            });
    }

    @Test
    public void buildInstanceFilterFor_dummyDataSet_shouldReturnNullDataSet() throws Exception
    {
        dataSet = IOHelper.getInstance().loadInstancesFromFile(hamDataFilename, spamDataFilename);

        assertThat(FilterConfiguration.buildAndApply(dataSet, InstanceFilter.ClassBalancer), notNullValue());
    }
}
