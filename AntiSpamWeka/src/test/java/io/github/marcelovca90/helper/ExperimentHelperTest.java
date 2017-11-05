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
package io.github.marcelovca90.helper;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.common.Constants.Metric;
import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.common.MethodEvaluation;
import weka.classifiers.Evaluation;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentHelperTest
{
    private final String folder = "/some/folder/DATA_SET/STAT_METHOD/100/";
    private final ExperimentHelper experimentHelper = MetaHelper.getExperimentHelper();
    private final Map<Metric, DescriptiveStatistics> results = new HashMap<>();
    private final MethodConfiguration methodConfiguration = MethodConfiguration.LIBSVM_RBF_G_zero_C_med;
    private final MethodEvaluation methodEvaluation = new MethodEvaluation(folder, methodConfiguration);

    @Mock
    private Evaluation evaluationMock;

    @Mock
    private MethodEvaluation methodEvaluationMock;

    @Before
    public void setUp()
    {
        results.clear();

        Arrays.stream(Metric.values()).forEach(m -> results.put(m, new DescriptiveStatistics(new Random().doubles(10, 0, 100).toArray())));

        Random random = new Random();

        when(evaluationMock.precision(anyInt())).thenReturn(random.nextDouble());
        when(evaluationMock.recall(anyInt())).thenReturn(random.nextDouble());
        when(evaluationMock.areaUnderPRC(anyInt())).thenReturn(random.nextDouble());
        when(evaluationMock.areaUnderROC(anyInt())).thenReturn(random.nextDouble());

        when(methodEvaluationMock.getEvaluation()).thenReturn(evaluationMock);
        when(methodEvaluationMock.getTrainStart()).thenReturn(System.currentTimeMillis() - 1000);
        when(methodEvaluationMock.getTrainEnd()).thenReturn(System.currentTimeMillis() - 750);
        when(methodEvaluationMock.getTestStart()).thenReturn(System.currentTimeMillis() - 500);
        when(methodEvaluationMock.getTestEnd()).thenReturn(System.currentTimeMillis() - 250);
    }

    @Test
    public void computeSingleRunResults_shouldReturnSuccess()
    {
        experimentHelper.computeSingleRunResults(methodEvaluationMock);
    }

    @Test
    public void detectAndRemoveOutliers_shouldReturnPossibleOutliersCount()
    {
        experimentHelper.computeSingleRunResults(methodEvaluationMock);

        assertThat(experimentHelper.detectAndRemoveOutliers(), greaterThanOrEqualTo(0));
    }

    @Test
    public void reset_shouldReturnSuccess()
    {
        experimentHelper.clearResultHistory();
    }

    @Test
    public void summarizeResults_doNotPrintStatsDoFormatMillis_shouldReturnSuccess()
    {
        experimentHelper.summarizeResults(results, methodEvaluation, false, true);
    }

    @Test
    public void summarizeResults_doNotPrintStatsDoNotFormatMillis_shouldReturnSuccess()
    {
        experimentHelper.summarizeResults(results, methodEvaluation, false, false);
    }

    @Test
    public void summarizeResults_doPrintStatsDoFormatMillis_shouldReturnSuccess()
    {
        experimentHelper.summarizeResults(results, methodEvaluation, true, true);
    }

    @Test
    public void summarizeResults_doPrintStatsDoNotFormatMillis_shouldReturnSuccess()
    {
        experimentHelper.summarizeResults(results, methodEvaluation, true, false);
    }
}
