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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import weka.classifiers.AbstractClassifier;

@RunWith(MockitoJUnitRunner.class)
public class MethodConfigurationTest
{
    @Test
    public void buildClassifierFor_invalidMethodConfiguration_shouldReturnNullClassifier()
    {
        assertThat(MethodConfiguration.buildClassifierFor((MethodConfiguration) null), nullValue());
    }

    @Test
    public void buildClassifierFor_validMethodConfiguration_shouldReturnNotNullClassifier()
    {
        Arrays
            .stream(MethodConfiguration.values())
            .forEach(v ->
            {
                AbstractClassifier classifier = MethodConfiguration.buildClassifierFor(v);

                assertThat(classifier, notNullValue());
                assertThat(classifier, instanceOf(v.getClazz()));
                assertThat(classifier.getOptions(), notNullValue());
            });
    }

    @Test
    public void enum_MethodConfiguration_shouldReturnTwentyFourValues()
    {
        assertThat(MethodConfiguration.values().length, equalTo(24));
        Arrays
            .stream(MethodConfiguration.values())
            .forEach(v -> assertThat(MethodConfiguration.valueOf(v.name()), notNullValue()));
    }

    @Test
    public void getClazz_allValues_shouldReturnNotNullClass()
    {
        Arrays
            .stream(MethodConfiguration.values())
            .forEach(v -> assertThat(v.getClazz(), notNullValue()));
    }

    @Test
    public void getConfig_allValues_shouldReturnNotNullString()
    {
        Arrays
            .stream(MethodConfiguration.values())
            .forEach(v -> assertThat(v.getConfig(), notNullValue()));
    }

    @Test
    public void getName_allValues_shouldReturnNotNullString()
    {
        Arrays
            .stream(MethodConfiguration.values())
            .forEach(v -> assertThat(v.getName(), notNullValue()));
    }

    @Test
    public void getSplitPercent_allValues_shouldReturnNotNullDouble()
    {
        Arrays
            .stream(MethodConfiguration.values())
            .forEach(v -> assertThat(v.getSplitPercent(), notNullValue()));
    }
}
