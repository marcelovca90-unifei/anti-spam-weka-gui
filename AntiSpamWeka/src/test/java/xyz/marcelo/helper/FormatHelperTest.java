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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import xyz.marcelo.common.Constants.Metric;
import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.MethodEvaluation;

@RunWith(MockitoJUnitRunner.class)
public class FormatHelperTest
{
    private final String folder = "/some/folder/DATA_SET/STAT_METHOD/100/";

    private final MethodConfiguration methodConfiguration = MethodConfiguration.RT;

    private final Map<Metric, DescriptiveStatistics> results = new HashMap<>();

    private MethodEvaluation methodEvaluation = new MethodEvaluation(folder, methodConfiguration);

    @Before
    public void setUp()
    {
        results.clear();

        Arrays.stream(Metric.values()).forEach(m -> results.put(m, new DescriptiveStatistics(new Random().doubles(10, 0, 100).toArray())));
    }

    @Test
    public void summarizeResults_doNotPrintStatsDoNotFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.getInstance().summarizeResults(results, methodEvaluation, false, false);
    }

    @Test
    public void summarizeResults_doNotPrintStatsDoFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.getInstance().summarizeResults(results, methodEvaluation, false, true);
    }

    @Test
    public void summarizeResults_doPrintStatsDoNotFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.getInstance().summarizeResults(results, methodEvaluation, true, false);
    }

    @Test
    public void summarizeResults_doPrintStatsDoFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.getInstance().summarizeResults(results, methodEvaluation, true, true);
    }

    @Test
    public void printHeader_shouldReturnSuccess()
    {
        FormatHelper.getInstance().printHeader();
    }
}
