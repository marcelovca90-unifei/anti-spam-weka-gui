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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.io.IOException;
import java.util.HashSet;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IOHelper.class)
public class CLIHelperTest
{
    private final String[] emptyArgs = new String[0];
    private final String[] notEmptyArgsWithWrongMetadata = new String[] { "-Metadata", "SOME_METADATA" };
    private final String[] notEmptyArgsWithWrongMethod = new String[] { "-Method", "SOME_METHOD" };
    private final String[] fullArgs = new String[] { "-Metadata", System.getProperty("user.home"), "-Method", "RT" };

    @Test
    public void constructor_shouldReturnNotNullInstance()
    {
        assertThat(new CLIHelper(), notNullValue());
    }

    @Test(expected = ParseException.class)
    public void initialize_emptyArgs_shouldThrowException() throws ParseException
    {
        CLIHelper.initialize(emptyArgs);
    }

    @Test
    public void initialize_notEmptyArgs_shouldNotThrowException() throws ParseException
    {
        CLIHelper.initialize(fullArgs);
    }

    @Test(expected = IOException.class)
    public void getDataSetsMetadata_incorrectArgs_shouldThrowException() throws ParseException, IOException
    {
        try
        {
            CLIHelper.initialize(notEmptyArgsWithWrongMetadata);
        }
        catch (Exception e)
        {
            assertThat(CLIHelper.getDataSetsMetadata(), notNullValue());
            throw e;
        }
    }

    @Test
    public void getDataSetsMetadata_correctArgs_shouldReturnSuccess() throws ParseException, IOException
    {
        mockStatic(IOHelper.class);
        when(IOHelper.loadDataSetsMetadataFromFile(Mockito.anyString())).thenReturn(new HashSet<>());

        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.getDataSetsMetadata(), notNullValue());
        verifyStatic();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMethods_incorrectArgs_shouldThrowException() throws ParseException
    {
        try
        {
            CLIHelper.initialize(notEmptyArgsWithWrongMethod);
        }
        catch (Exception e)
        {
            assertThat(CLIHelper.getMethods(), notNullValue());
            throw e;
        }
    }

    @Test
    public void getMethods_correctArgs_shouldReturnSuccess() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.getMethods(), notNullValue());
    }

    @Test
    public void getNumberOfRuns() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.getNumberOfRuns(), notNullValue());
    }

    @Test
    public void skipTrain() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.skipTrain(), notNullValue());
    }

    @Test
    public void skipTest() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.skipTest(), notNullValue());
    }

    @Test
    public void includeEmptyInstances() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.includeEmptyInstances(), notNullValue());
    }

    @Test
    public void saveModel() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.saveModel(), notNullValue());
    }

    @Test
    public void saveSets() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.saveSets(), notNullValue());
    }

    @Test
    public void shrinkFeatures() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.shrinkFeatures(), notNullValue());
    }

    @Test
    public void balanceClasses() throws ParseException
    {
        CLIHelper.initialize(fullArgs);

        assertThat(CLIHelper.balanceClasses(), notNullValue());
    }

    @Test
    public void printConfiguration() throws ParseException, IOException
    {
        mockStatic(IOHelper.class);
        when(IOHelper.loadDataSetsMetadataFromFile(Mockito.anyString())).thenReturn(new HashSet<>());
        CLIHelper.initialize(fullArgs);

        CLIHelper.printConfiguration();

        verifyStatic();
    }
}
