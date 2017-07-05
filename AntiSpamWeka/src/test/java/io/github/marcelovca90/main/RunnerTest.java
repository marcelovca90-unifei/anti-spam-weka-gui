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
package io.github.marcelovca90.main;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

public class RunnerTest
{
    private String metadataFilename;

    @Before
    public void setUp() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        metadataFilename = classLoader.getResource("data-sets-bin/metadata.txt").getFile();
    }

    @Test(expected = ParseException.class)
    public void main_withNullArgs_shouldThrowException() throws Exception
    {
        Runner.main(null);
    }

    @Test(expected = Exception.class)
    public void main_nullBuffer_shouldThrowException() throws Exception
    {
        String[] args = String.format("-Metadata %s -Method RT -Runs 3 -ShrinkFeatures -BalanceClasses -TestEmpty", metadataFilename).split(" ");

        Runner.main(args);
    }
}
