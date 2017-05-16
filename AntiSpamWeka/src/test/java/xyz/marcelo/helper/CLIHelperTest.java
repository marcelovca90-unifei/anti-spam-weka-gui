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

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CLIHelperTest
{
    private String[] emptyArgs;
    private String[] notEmptyArgsWithWrongMetadata;
    private String[] notEmptyArgsWithWrongMethod;
    private String[] fullArgs;

    @Before
    public void setUp()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        emptyArgs = new String[0];
        notEmptyArgsWithWrongMetadata = new String[] { "-Metadata", "SOME_METADATA" };
        notEmptyArgsWithWrongMethod = new String[] { "-Method", "SOME_METHOD" };
        fullArgs = new String[] { "-Metadata", classLoader.getResource("data-sets-bin/metadata.txt").getFile(), "-Method", "RT" };
    }

    @Test(expected = ParseException.class)
    public void initialize_emptyArgs_shouldThrowException() throws ParseException
    {
        CLIHelper.getInstance().initialize(emptyArgs);
    }

    @Test
    public void initialize_notEmptyArgs_shouldNotThrowException() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);
    }

    @Test(expected = ParseException.class)
    public void getDataSetsMetadata_incorrectArgs_shouldThrowException() throws ParseException, IOException
    {
        try
        {
            CLIHelper.getInstance().initialize(notEmptyArgsWithWrongMetadata);
        }
        catch (Exception e)
        {
            assertThat(CLIHelper.getInstance().getDataSetsMetadata(), notNullValue());
            throw e;
        }
    }

    @Test
    public void getDataSetsMetadata_correctArgs_shouldReturnSuccess() throws ParseException, IOException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().getDataSetsMetadata(), notNullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMethods_incorrectArgs_shouldThrowException() throws ParseException
    {
        try
        {
            CLIHelper.getInstance().initialize(notEmptyArgsWithWrongMethod);
        }
        catch (Exception e)
        {
            assertThat(CLIHelper.getInstance().getMethods(), notNullValue());
            throw e;
        }
    }

    @Test
    public void getMethods_correctArgs_shouldReturnNotNullMethodsList() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().getMethods(), notNullValue());
    }

    @Test
    public void getNumberOfRuns_shouldReturnNotNullInteger() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().getNumberOfRuns(), notNullValue());
    }

    @Test
    public void skipTrain_shouldReturnNotNullBoolean() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().skipTrain(), notNullValue());
    }

    @Test
    public void skipTest_shouldReturnNotNullBoolean() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().skipTest(), notNullValue());
    }

    @Test
    public void includeEmptyInstances_shouldReturnNotNullBoolean() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().includeEmptyInstances(), notNullValue());
    }

    @Test
    public void saveModel_shouldReturnNotNullBoolean() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().saveModel(), notNullValue());
    }

    @Test
    public void saveSets_shouldReturnNotNullBoolean() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().saveSets(), notNullValue());
    }

    @Test
    public void shrinkFeature_shouldReturnNotNullBoolean() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().shrinkFeatures(), notNullValue());
    }

    @Test
    public void balanceClasse_shouldReturnNotNullBoolean() throws ParseException
    {
        CLIHelper.getInstance().initialize(fullArgs);

        assertThat(CLIHelper.getInstance().balanceClasses(), notNullValue());
    }

    @Test
    public void printConfiguration_shouldReturnSuccess() throws ParseException, IOException
    {
        CLIHelper.getInstance().initialize(fullArgs);
        CLIHelper.getInstance().printConfiguration();
    }
}
