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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import xyz.marcelo.common.Constants.Metric;
import xyz.marcelo.common.MethodConfiguration;
import xyz.marcelo.common.MethodEvaluation;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ResultHelper.class)
public class FormatHelperTest
{
    private final String folder = "/some/folder/DATA_SET/STAT_METHOD/100/";

    private final MethodConfiguration methodConfiguration = MethodConfiguration.RT;

    private MethodEvaluation methodEvaluation;

    @Before
    public void setUp()
    {
        methodEvaluation = new MethodEvaluation(folder, methodConfiguration);

        mockStatic(ResultHelper.class);

        Map<Metric, DescriptiveStatistics> map = new HashMap<>();
        Arrays.stream(Metric.values()).forEach(m -> map.put(m, new DescriptiveStatistics(new Random().doubles(10, 0, 100).toArray())));

        when(ResultHelper.getMetricsToDescriptiveStatisticsMap()).thenReturn(map);
    }

    @Test
    public void constructor_shouldReturnNotNullInstance()
    {
        assertThat(new FormatHelper(), notNullValue());
    }

    @Test
    public void summarizeResults_doNotPrintStatsDoNotFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.summarizeResults(methodEvaluation, false, false);

        verifyStatic();
    }

    @Test
    public void summarizeResults_doNotPrintStatsDoFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.summarizeResults(methodEvaluation, false, true);

        verifyStatic();
    }

    @Test
    public void summarizeResults_doPrintStatsDoNotFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.summarizeResults(methodEvaluation, true, false);

        verifyStatic();
    }

    @Test
    public void summarizeResults_doPrintStatsDoFormatMillis_shouldReturnSuccess()
    {
        FormatHelper.summarizeResults(methodEvaluation, true, true);

        verifyStatic();
    }

    @Test
    public void printHeader()
    {
        FormatHelper.printHeader();
    }
}
