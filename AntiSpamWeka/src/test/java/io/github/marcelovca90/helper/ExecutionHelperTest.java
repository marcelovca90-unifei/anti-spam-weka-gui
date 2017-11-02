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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.utils.Sets;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.common.DataSetMetadata;
import io.github.marcelovca90.common.MethodConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionHelperTest
{
    private static String[] args;
    private static Set<DataSetMetadata> metadata;
    private static List<MethodConfiguration> methods;

    private ExperimentHelper experimentHelper = new ExperimentHelper();

    private InputOutputHelper inputOutputHelper = new InputOutputHelper();

    private RandomHelper randomHelper = new RandomHelper();

    @BeforeClass
    public static void setUp() throws IOException
    {
        args = new String[0];

        DataSetMetadata metadataMock = mock(DataSetMetadata.class);
        when(metadataMock.getEmptyHamCount()).thenReturn(6563);
        when(metadataMock.getEmptySpamCount()).thenReturn(1407);
        when(metadataMock.getFolder()).thenReturn("src/test/resources/data-sets-bin/10");

        metadata = Sets.newHashSet(metadataMock);

        methods = Arrays.asList(MethodConfiguration.SPEGASOS);
    }

    @AfterClass
    public static void tearDown()
    {
        MetaHelper.reset();

        File folder = Paths.get("src/test/resources/data-sets-bin/10").toFile();

        Arrays
            .stream(folder.listFiles((f, p) -> p.endsWith(".arff") || p.endsWith(".csv") || p.endsWith(".model")))
            .forEach(File::delete);
    }

    @Test
    public void main_notPragmaticConfiguration_shouldReturnSucccess() throws Exception
    {
        setUpExecutionHelper(args, metadata, methods, 3, true, true, false, false, false, false, true, false, false);

        MetaHelper.initialize(experimentHelper, inputOutputHelper, randomHelper);

        ExecutionHelper.setUpExecutionThread();
        ExecutionHelper.startExecution();
    }

    @Test
    public void main_pragmaticConfiguration_shouldReturnSucccess() throws Exception
    {
        setUpExecutionHelper(args, metadata, methods, 25, false, false, true, true, true, true, false, true, true);

        MetaHelper.initialize(experimentHelper, inputOutputHelper, randomHelper);

        ExecutionHelper.setUpExecutionThread();
        ExecutionHelper.startExecution();
    }

    private void setUpExecutionHelper(String[] args, Set<DataSetMetadata> metadata, List<MethodConfiguration> methods, int numberOfRuns, boolean skipTrain, boolean skipTest,
            boolean shrinkFeatures, boolean balanceClasses, boolean includeEmpty, boolean removeOutliers, boolean saveArff, boolean saveModel, boolean saveSets) throws Exception
    {
        ExecutionHelper.metadata = metadata;
        ExecutionHelper.methods = methods;
        ExecutionHelper.numberOfRuns = numberOfRuns;
        ExecutionHelper.skipTrain = skipTrain;
        ExecutionHelper.skipTest = skipTest;
        ExecutionHelper.shrinkFeatures = shrinkFeatures;
        ExecutionHelper.balanceClasses = balanceClasses;
        ExecutionHelper.includeEmpty = includeEmpty;
        ExecutionHelper.removeOutliers = removeOutliers;
        ExecutionHelper.saveArff = saveArff;
        ExecutionHelper.saveModel = saveModel;
        ExecutionHelper.saveSets = saveSets;
    }
}
