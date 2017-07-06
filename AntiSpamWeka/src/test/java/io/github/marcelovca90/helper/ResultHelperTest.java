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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.common.MethodEvaluation;
import weka.classifiers.Evaluation;

@RunWith(MockitoJUnitRunner.class)
public class ResultHelperTest
{
    @Mock
    private Evaluation evaluation;

    @Mock
    private MethodEvaluation methodEvaluation;

    private final ResultHelper resultHelper = MetaHelper.getResultHelper();

    @Before
    public void setUp()
    {
        Random random = new Random();

        when(evaluation.precision(anyInt())).thenReturn(random.nextDouble());
        when(evaluation.recall(anyInt())).thenReturn(random.nextDouble());
        when(evaluation.areaUnderPRC(anyInt())).thenReturn(random.nextDouble());
        when(evaluation.areaUnderROC(anyInt())).thenReturn(random.nextDouble());

        when(methodEvaluation.getEvaluation()).thenReturn(evaluation);
        when(methodEvaluation.getTrainStart()).thenReturn(System.currentTimeMillis() - 1000);
        when(methodEvaluation.getTrainEnd()).thenReturn(System.currentTimeMillis() - 750);
        when(methodEvaluation.getTestStart()).thenReturn(System.currentTimeMillis() - 500);
        when(methodEvaluation.getTestEnd()).thenReturn(System.currentTimeMillis() - 250);
    }

    @Test
    public void reset_shouldReturnSuccess()
    {
        resultHelper.reset();
    }

    @Test
    public void computeSingleRunResults_shouldReturnSuccess()
    {
        resultHelper.computeSingleRunResults(methodEvaluation);
    }

    @Test
    public void detectAndRemoveOutliers_shouldReturnPossibleOutliersCount()
    {
        resultHelper.computeSingleRunResults(methodEvaluation);

        assertThat(resultHelper.detectAndRemoveOutliers(), greaterThanOrEqualTo(0));
    }

    @Test
    public void getMetricsToDescriptiveStatisticsMap_shouldReturnNotNullMap()
    {
        assertThat(resultHelper.getMetricsToDescriptiveStatisticsMap(), notNullValue());
    }
}
