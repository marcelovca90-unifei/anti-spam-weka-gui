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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.common.DataSetMetadata;

@RunWith(MockitoJUnitRunner.class)
public class DataSetMetadataTest
{
    private final String folder = "/some/folder/";
    private final int emptyHamCount = 256;
    private final int emptySpamCount = 512;

    private DataSetMetadata dataSetMetadata;

    @Before
    public void setUp()
    {
        dataSetMetadata = new DataSetMetadata(folder, emptyHamCount, emptySpamCount);
    }

    @Test
    public void constructor_shouldReturnNotNullInstance()
    {
        assertThat(dataSetMetadata, notNullValue());
    }

    @Test
    public void getFolder_shouldReturnValueSetInConstructor()
    {
        assertThat(dataSetMetadata.getFolder(), equalTo(folder));
    }

    @Test
    public void getEmptyHamCount_shouldReturnValueSetInConstructor()
    {
        assertThat(dataSetMetadata.getEmptyHamCount(), equalTo(emptyHamCount));
    }

    @Test
    public void getEmptySpamCount_shouldReturnValueSetInConstructor()
    {
        assertThat(dataSetMetadata.getEmptySpamCount(), equalTo(emptySpamCount));
    }

    @Test
    public void toString_shouldContainAllFieldsValues()
    {
        String string = dataSetMetadata.toString();

        assertThat(string, notNullValue());
        assertThat(string, containsString(folder));
        assertThat(string, containsString(String.valueOf(emptyHamCount)));
        assertThat(string, containsString(String.valueOf(emptySpamCount)));
    }
}
