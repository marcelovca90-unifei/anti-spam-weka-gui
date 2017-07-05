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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.pmw.tinylog.Logger;

import io.github.marcelovca90.common.Constants.CLIOption;
import io.github.marcelovca90.common.DataSetMetadata;
import io.github.marcelovca90.common.MethodConfiguration;

public class CLIHelper
{
    // used to suppress the default public constructor
    private CLIHelper()
    {
    }

    private static final CLIHelper INSTANCE = new CLIHelper();

    public static final CLIHelper getInstance()
    {
        return INSTANCE;
    }

    private static final String OPTION_VALUE_MASK = "{} : {}";

    private CommandLine commandLine;

    private Options options;

    public void initialize(String[] args) throws ParseException
    {
        // create Options object
        options = new Options();

        // add the command line options
        addOption(CLIOption.METADATA, true, "path of the file containing data sets metadata (default: none)");
        addOption(CLIOption.METHOD, true, "CSV list of methods. Available methods: " + Arrays.toString(MethodConfiguration.values()) + " (default: none)");
        addOption(CLIOption.RUNS, true, "number of repetitions to be performed (default: 1)");
        addOption(CLIOption.SKIP_TRAIN, false, "perform training (learning) of the classifier(s) (default: false)");
        addOption(CLIOption.SKIP_TEST, false, "perform testing (evaluation) of the classifier(s) (default: false)");
        addOption(CLIOption.TEST_EMPTY, false, "include empty patterns while testing the classifier (default: false)");
        addOption(CLIOption.SAVE_MODEL, false, "save the classifier to a .model file (default: false)");
        addOption(CLIOption.SAVE_SETS, false, "save the training and testing data sets to a .csv file (default: false)");
        addOption(CLIOption.SHRINK_FEATURES, false, "reduce the feature space using an evolutionary search");
        addOption(CLIOption.BALANCE_CLASSES, false, "equalize the number of instances for each class");

        // instantiates the cli based on the provided arguments
        try
        {
            commandLine = new DefaultParser().parse(options, args);

            if (!hasOption(CLIOption.METADATA) || !hasOption(CLIOption.METHOD))
                throw new ParseException("Missing mandatory arguments");
        }
        catch (ParseException e)
        {
            // automatically generate the help statement
            String mandatoryArgs = "METADATA METHOD";
            String optionalArgs = "[RUNS] [SKIP_TRAIN] [SKIP_TEST] [TEST_EMPTY] [SAVE_MODEL] [SAVE_SETS] [SHRINK_FEATURES] [BALANCE_CLASSES]";
            new HelpFormatter().printHelp("java -jar AntiSpamWeka.jar " + mandatoryArgs + " " + optionalArgs, options);

            // rethrow the exception
            throw e;
        }
    }

    public Set<DataSetMetadata> getDataSetsMetadata() throws IOException
    {
        Set<DataSetMetadata> metadata = new LinkedHashSet<>();
        metadata.addAll(IOHelper.getInstance().loadDataSetsMetadataFromFile(getOptionValue(CLIOption.METADATA)));
        return metadata;
    }

    public List<MethodConfiguration> getMethods()
    {
        List<MethodConfiguration> methods = new ArrayList<>();
        Arrays
            .stream(getOptionValue(CLIOption.METHOD).split(","))
            .forEach(m -> methods.add(MethodConfiguration.valueOf(m)));
        return methods;
    }

    public int getNumberOfRuns()
    {
        return hasOption(CLIOption.RUNS) ? Integer.parseInt(getOptionValue(CLIOption.RUNS)) : 1;
    }

    public boolean skipTrain()
    {
        return hasOption(CLIOption.SKIP_TRAIN);
    }

    public boolean skipTest()
    {
        return hasOption(CLIOption.SKIP_TEST);
    }

    public boolean includeEmptyInstances()
    {
        return hasOption(CLIOption.TEST_EMPTY);
    }

    public boolean saveModel()
    {
        return hasOption(CLIOption.SAVE_MODEL);
    }

    public boolean saveSets()
    {
        return hasOption(CLIOption.SAVE_SETS);
    }

    public boolean shrinkFeatures()
    {
        return hasOption(CLIOption.SHRINK_FEATURES);
    }

    public boolean balanceClasses()
    {
        return hasOption(CLIOption.BALANCE_CLASSES);
    }

    public void printConfiguration() throws IOException
    {
        Logger.debug("---- CONFIGURATION ----");
        Logger.debug(OPTION_VALUE_MASK, CLIOption.METADATA, getDataSetsMetadata());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.METHOD, getMethods());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.RUNS, getNumberOfRuns());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.SKIP_TRAIN, skipTrain());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.SKIP_TEST, skipTest());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.TEST_EMPTY, includeEmptyInstances());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.SAVE_MODEL, saveModel());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.SAVE_SETS, saveSets());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.SHRINK_FEATURES, shrinkFeatures());
        Logger.debug(OPTION_VALUE_MASK, CLIOption.BALANCE_CLASSES, balanceClasses());
        Logger.debug("-----------------------");
    }

    private String camelCaseOption(CLIOption opt)
    {
        return StringUtils.remove(WordUtils.capitalizeFully(opt.name(), '_'), "_");
    }

    private void addOption(CLIOption opt, boolean hasArg, String description)
    {
        options.addOption(camelCaseOption(opt), hasArg, description);
    }

    private String getOptionValue(CLIOption opt)
    {
        return commandLine.getOptionValue(camelCaseOption(opt));
    }

    private boolean hasOption(CLIOption opt)
    {
        return commandLine.hasOption(camelCaseOption(opt));
    }
}
