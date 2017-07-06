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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

@RunWith(MockitoJUnitRunner.class)
public class MethodEvaluationTest
{
    private final String folder = "/some/folder/DATA_SET/STAT_METHOD/100/";
    private final MethodConfiguration methodConfiguration = MethodConfiguration.RT;

    private MethodEvaluation methodEvaluation;

    @Mock
    private Classifier classifier;

    @Mock
    private Evaluation evaluation;

    @Before
    public void setUp()
    {
        methodEvaluation = new MethodEvaluation(folder, methodConfiguration);
        methodEvaluation.setClassifier(classifier);
        methodEvaluation.setEvaluation(evaluation);
    }

    @Test
    public void getClassifier_shouldReturnSetClassifier()
    {
        assertThat(methodEvaluation.getClassifier(), equalTo(classifier));
    }

    @Test
    public void getDataSetName_shouldReturnProperDataSetName()
    {
        assertThat(methodEvaluation.getDataSetName(), equalTo("DATA_SET"));
    }

    @Test
    public void getEvaluation_shouldReturnSetEvaluation()
    {
        assertThat(methodEvaluation.getEvaluation(), equalTo(evaluation));
    }

    @Test
    public void getFolder_shouldReturnSetFolder()
    {
        assertThat(methodEvaluation.getFolder(), equalTo(folder));
    }

    @Test
    public void getMethodConfiguration_shouldReturnSetMethodConfiguration()
    {
        assertThat(methodEvaluation.getMethodConfiguration(), equalTo(methodConfiguration));
    }

    @Test
    public void getSetNumberOfActualFeatures_shouldReturnSetNumberOfActualFeatures()
    {
        assertThat(methodEvaluation.getNumberOfActualFeatures(), equalTo(100));

        methodEvaluation.setNumberOfActualFeatures(50);

        assertThat(methodEvaluation.getNumberOfActualFeatures(), equalTo(50));
    }

    @Test
    public void getSetNumberOfTotalFeatures_shouldReturnSetNumberOfTotalFeatures()
    {
        assertThat(methodEvaluation.getNumberOfTotalFeatures(), equalTo(100));

        methodEvaluation.setNumberOfTotalFeatures(50);

        assertThat(methodEvaluation.getNumberOfTotalFeatures(), equalTo(50));
    }

    @Test
    public void getStatMethod_shouldReturnProperStatMethod()
    {
        assertThat(methodEvaluation.getStatMethod(), equalTo("STAT_METHOD"));
    }

    @Test
    public void getTestEnd_withoutTesting_shouldReturnZero()
    {
        assertThat(methodEvaluation.getTestEnd(), equalTo(0L));
    }

    @Test
    public void getTestStart_withoutTesting_shouldReturnZero()
    {
        assertThat(methodEvaluation.getTestStart(), equalTo(0L));
    }

    @Test
    public void getTrainEnd_withoutTraining_shouldReturnZero()
    {
        assertThat(methodEvaluation.getTrainEnd(), equalTo(0L));
    }

    @Test
    public void getTrainStart_withoutTraining_shouldReturnZero()
    {
        assertThat(methodEvaluation.getTrainStart(), equalTo(0L));
    }

    @Test
    public void test_whenNotThrowingException_shouldFinishTraining() throws Exception
    {
        when(evaluation.evaluateModel(any(Classifier.class), any(Instances.class))).thenReturn(new double[0]);

        methodEvaluation.test(mock(Instances.class));

        verify(evaluation).evaluateModel(any(Classifier.class), any(Instances.class));
        assertThat(methodEvaluation.getTestStart(), not(equalTo(0L)));
        assertThat(methodEvaluation.getTestEnd(), not(equalTo(0L)));
        assertThat(methodEvaluation.getTestEnd(), greaterThanOrEqualTo(methodEvaluation.getTestStart()));
    }

    @Test
    public void test_whenThrowingException_shouldNotFinishTesting() throws Exception
    {
        when(evaluation.evaluateModel(any(Classifier.class), any(Instances.class))).thenThrow(new Exception());

        methodEvaluation.test(mock(Instances.class));

        verify(evaluation).evaluateModel(any(Classifier.class), any(Instances.class));
        assertThat(methodEvaluation.getTestStart(), not(equalTo(0L)));
        assertThat(methodEvaluation.getTestEnd(), equalTo(0L));
    }

    @Test
    public void train_whenNotThrowingException_shouldFinishTraining() throws Exception
    {
        doNothing().when(classifier).buildClassifier(any(Instances.class));

        methodEvaluation.train(mock(Instances.class));

        verify(classifier).buildClassifier(any(Instances.class));
        assertThat(methodEvaluation.getTrainStart(), not(equalTo(0L)));
        assertThat(methodEvaluation.getTrainEnd(), not(equalTo(0L)));
        assertThat(methodEvaluation.getTrainEnd(), greaterThanOrEqualTo(methodEvaluation.getTrainStart()));
    }

    @Test
    public void train_whenThrowingException_shouldNotFinishTraining() throws Exception
    {
        doThrow(new Exception()).when(classifier).buildClassifier(any(Instances.class));

        methodEvaluation.train(mock(Instances.class));

        verify(classifier).buildClassifier(any(Instances.class));
        assertThat(methodEvaluation.getTrainStart(), not(equalTo(0L)));
        assertThat(methodEvaluation.getTrainEnd(), equalTo(0L));
    }
}
