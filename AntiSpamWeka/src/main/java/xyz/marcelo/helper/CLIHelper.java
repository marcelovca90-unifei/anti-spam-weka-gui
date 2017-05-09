package xyz.marcelo.helper;

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

import xyz.marcelo.common.Constants.CLIOption;
import xyz.marcelo.common.DataSetMetadata;
import xyz.marcelo.common.MethodConfiguration;

public final class CLIHelper
{
    private static CommandLine commandLine;

    private static Options options;

    public static void initialize(String[] args) throws ParseException
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
            {
                throw new ParseException("Missing mandatory arguments");
            }
        }
        catch (ParseException e)
        {
            // automatically generate the help statement
            String mandatoryArgs = "METADATA METHOD";
            String optionalArgs = "[RUNS] [SKIP_TRAIN] [SKIP_TEST] [TEST_EMPTY] [SAVE_MODEL] [SAVE_SETS] [SHRINK_FEATURES] [BALANCE_CLASSES]";
            new HelpFormatter().printHelp("java -jar AntiSpamWeka.jar " + mandatoryArgs + " " + optionalArgs, options);

            // exit with status code 1, indicating abnormal termination
            System.exit(1);
        }
    }

    public static Set<DataSetMetadata> getDataSetsMetadata() throws IOException
    {
        Set<DataSetMetadata> metadata = new LinkedHashSet<>();
        if (hasOption(CLIOption.METADATA))
        {
            metadata.addAll(IOHelper.loadDataSetsMetadataFromFile(getOptionValue(CLIOption.METADATA)));
        }
        return metadata;
    }

    public static List<MethodConfiguration> getMethods()
    {
        List<MethodConfiguration> methods = new ArrayList<>();
        if (hasOption(CLIOption.METHOD))
        {
            Arrays.stream(getOptionValue(CLIOption.METHOD).split(",")).forEach(m -> methods.add(MethodConfiguration.valueOf(m)));
        }
        return methods;
    }

    public static int getNumberOfRuns()
    {
        return hasOption(CLIOption.RUNS) ? Integer.parseInt(getOptionValue(CLIOption.RUNS)) : 1;
    }

    public static boolean skipTrain()
    {
        return hasOption(CLIOption.SKIP_TRAIN);
    }

    public static boolean skipTest()
    {
        return hasOption(CLIOption.SKIP_TEST);
    }

    public static boolean includeEmptyInstances()
    {
        return hasOption(CLIOption.TEST_EMPTY);
    }

    public static boolean saveModel()
    {
        return hasOption(CLIOption.SAVE_MODEL);
    }

    public static boolean saveSets()
    {
        return hasOption(CLIOption.SAVE_SETS);
    }

    public static boolean shrinkFeatures()
    {
        return hasOption(CLIOption.SHRINK_FEATURES);
    }

    public static boolean balanceClasses()
    {
        return hasOption(CLIOption.BALANCE_CLASSES);
    }

    public static void printConfiguration() throws IOException
    {
        Logger.debug("---- CONFIGURATION ----");
        Logger.debug("{} : {}", CLIOption.METADATA, getDataSetsMetadata());
        Logger.debug("{} : {}", CLIOption.METHOD, getMethods());
        Logger.debug("{} : {}", CLIOption.RUNS, getNumberOfRuns());
        Logger.debug("{} : {}", CLIOption.SKIP_TRAIN, skipTrain());
        Logger.debug("{} : {}", CLIOption.SKIP_TEST, skipTest());
        Logger.debug("{} : {}", CLIOption.TEST_EMPTY, includeEmptyInstances());
        Logger.debug("{} : {}", CLIOption.SAVE_MODEL, saveModel());
        Logger.debug("{} : {}", CLIOption.SAVE_SETS, saveSets());
        Logger.debug("{} : {}", CLIOption.SHRINK_FEATURES, shrinkFeatures());
        Logger.debug("{} : {}", CLIOption.BALANCE_CLASSES, balanceClasses());
        Logger.debug("-----------------------");
    }

    private static String camelCaseOption(CLIOption opt)
    {
        return StringUtils.remove(WordUtils.capitalizeFully(opt.name(), '_'), "_");
    }

    private static void addOption(CLIOption opt, boolean hasArg, String description)
    {
        options.addOption(camelCaseOption(opt), hasArg, description);
    }

    private static String getOptionValue(CLIOption opt)
    {
        return commandLine.getOptionValue(camelCaseOption(opt));
    }

    private static boolean hasOption(CLIOption opt)
    {
        return commandLine.hasOption(camelCaseOption(opt));
    }
}
