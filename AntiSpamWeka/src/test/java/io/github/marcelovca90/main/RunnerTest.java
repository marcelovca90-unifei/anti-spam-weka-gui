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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.marcelovca90.common.DataSetMetadata;
import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.helper.CommandLineHelper;
import io.github.marcelovca90.helper.ExperimentHelper;
import io.github.marcelovca90.helper.InputOutputHelper;
import io.github.marcelovca90.helper.MetaHelper;

@RunWith(MockitoJUnitRunner.class)
public class RunnerTest
{
    private static String[] args;
    private static Set<DataSetMetadata> metadata;
    private static List<MethodConfiguration> methods;

    @Mock
    private CommandLineHelper commandLineHelper;

    private ExperimentHelper experimentHelper = new ExperimentHelper();

    private InputOutputHelper inputOutputHelper = new InputOutputHelper();

    @BeforeClass
    public static void setUp() throws IOException
    {
        args = new String[0];

        DataSetMetadata metadataMock = mock(DataSetMetadata.class);
        when(metadataMock.getEmptyHamCount()).thenReturn(6563);
        when(metadataMock.getEmptySpamCount()).thenReturn(1407);
        when(metadataMock.getFolder()).thenReturn("src/test/resources/data-sets-bin/10");

        metadata = new HashSet<>();
        metadata.add(metadataMock);

        methods = Arrays.asList(MethodConfiguration.PEGASOS);
    }

    @AfterClass
    public static void tearDown()
    {
        MetaHelper.reset();

        File folder = Paths.get("src/test/resources/data-sets-bin/10").toFile();

        Arrays.stream(folder.listFiles((f, p) -> p.endsWith(".csv") || p.endsWith(".model"))).forEach(File::delete);
    }

    @Test
    public void main_notPragmaticConfiguration_shouldReturnSucccess() throws Exception
    {
        commandLineHelper = buildCLIHelper(args, metadata, methods, 3, true, true, false, false, false, false, false);

        MetaHelper.initialize(commandLineHelper, experimentHelper, inputOutputHelper);

        Runner.main(args);
    }

    @Test
    public void main_pragmaticConfiguration_shouldReturnSucccess() throws Exception
    {
        commandLineHelper = buildCLIHelper(args, metadata, methods, 15, false, false, true, true, true, true, true);

        MetaHelper.initialize(commandLineHelper, experimentHelper, inputOutputHelper);

        Runner.main(args);
    }

    private CommandLineHelper buildCLIHelper(String[] args, Set<DataSetMetadata> metadata, List<MethodConfiguration> methods, int numberOfRuns, boolean skipTrain, boolean skipTest,
            boolean includeEmptyInstances, boolean saveModel, boolean saveSets, boolean shrinkFeatuers, boolean balanceClasses) throws Exception
    {
        CommandLineHelper helper = mock(CommandLineHelper.class);

        doNothing().when(helper).initialize(any(String[].class));
        when(helper.getDataSetsMetadata()).thenReturn(metadata);
        when(helper.getMethods()).thenReturn(methods);
        when(helper.getNumberOfRuns()).thenReturn(numberOfRuns);
        when(helper.skipTrain()).thenReturn(skipTrain);
        when(helper.skipTest()).thenReturn(skipTest);
        when(helper.includeEmptyInstances()).thenReturn(includeEmptyInstances);
        when(helper.saveModel()).thenReturn(saveModel);
        when(helper.saveSets()).thenReturn(saveSets);
        when(helper.shrinkFeatures()).thenReturn(shrinkFeatuers);
        when(helper.balanceClasses()).thenReturn(balanceClasses);
        doNothing().when(helper).printConfiguration();

        return helper;
    }
}
