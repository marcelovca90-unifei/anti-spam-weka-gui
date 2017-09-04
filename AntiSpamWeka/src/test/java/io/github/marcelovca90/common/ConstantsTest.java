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
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith (MockitoJUnitRunner.class)
public class ConstantsTest
{
    @Test
    public void constructor_shouldReturnNotNullInstance()
    {
        assertThat(new Constants(), notNullValue());
    }

    @Test
    public void enum_CLIOption_shouldReturnTwelveValues()
    {
        assertThat(Constants.CLIOption.values().length, equalTo(12));
        Arrays
            .stream(Constants.CLIOption.values())
            .forEach(v -> assertThat(Constants.CLIOption.valueOf(v.name()), notNullValue()));
    }

    @Test
    public void enum_MessageType_shouldReturnTwoValues()
    {
        assertThat(Constants.MessageType.values().length, equalTo(2));
        Arrays
            .stream(Constants.MessageType.values())
            .forEach(v -> assertThat(Constants.MessageType.valueOf(v.name()), notNullValue()));
    }

    @Test
    public void enum_Metric_shouldReturnSeventeenValues()
    {
        assertThat(Constants.Metric.values().length, equalTo(17));
        Arrays
            .stream(Constants.Metric.values())
            .forEach(v -> assertThat(Constants.Metric.valueOf(v.name()), notNullValue()));
    }
}
