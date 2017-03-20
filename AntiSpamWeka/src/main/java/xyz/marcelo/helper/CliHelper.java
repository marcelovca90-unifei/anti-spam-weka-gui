package xyz.marcelo.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import xyz.marcelo.common.MethodConfiguration;

public class CliHelper
{
    private static final String OPTION_FOLDER = "folder";
    private static final String OPTION_METHOD = "method";
    private static final String OPTIONS_SKIP_TRAIN = "skipTrain";
    private static final String OPTIONS_SKIP_TEST = "skipTest";
    private static final String OPTIONS_RUNS = "runs";
    private static final String OPTIONS_EMPTY = "empty";

    private static CommandLine cmd;

    public static void initialize(String[] args) throws ParseException
    {
        // create Options object
        Options options = new Options();

        // add the command line options
        options.addOption(OPTION_FOLDER, true, "root folder of the data sets (default: none)");
        options.addOption(OPTION_METHOD, true,
                "comma-separated list of methods to be used. Available methods: " + Arrays.toString(MethodConfiguration.values()) + " (default: none)");
        options.addOption(OPTIONS_SKIP_TRAIN, false, "perform training (learning) of the classifier(s) (default: false)");
        options.addOption(OPTIONS_SKIP_TEST, false, "perform testing (evaluation) of the classifier(s) (default: false)");
        options.addOption(OPTIONS_RUNS, true, "number of repetitions to be performed (default: 1)");
        options.addOption(OPTIONS_EMPTY, false, "include empty patterns while testing the classifier (default: false)");

        // instantiates the cli based on the provided arguments
        try
        {
            cmd = new DefaultParser().parse(options, args);

            if (!cmd.hasOption(OPTION_FOLDER) || !cmd.hasOption(OPTION_METHOD))
            {
                throw new ParseException("Missing mandatory arguments");
            }
        }
        catch (ParseException e)
        {
            // automatically generate the help statement
            new HelpFormatter().printHelp("AntiSpamWeka", options);
        }
    }

    public static List<String> getFolders() throws IOException
    {
        List<String> folders = new ArrayList<>();
        if (cmd.hasOption(OPTION_FOLDER))
        {
            folders.addAll(DataSetHelper.getFolders(cmd.getOptionValue(OPTION_FOLDER)));
        }
        return folders;
    }

    public static List<MethodConfiguration> getMethods()
    {
        List<MethodConfiguration> methods = new ArrayList<>();
        if (cmd.hasOption(OPTION_METHOD))
        {
            Arrays.stream(cmd.getOptionValue(OPTION_METHOD).split(",")).forEach(m -> methods.add(MethodConfiguration.valueOf(m)));
        }
        return methods;
    }

    public static boolean shouldSkipTrain()
    {
        return cmd.hasOption(OPTIONS_SKIP_TRAIN);
    }

    public static boolean shouldSkipTest()
    {
        return cmd.hasOption(OPTIONS_SKIP_TEST);
    }

    public static int getNumberOfRuns()
    {
        return cmd.hasOption(OPTIONS_RUNS) ? Integer.parseInt(cmd.getOptionValue(OPTIONS_RUNS)) : 1;
    }

    public static boolean shouldIncludeEmptyInstances()
    {
        return cmd.hasOption(OPTIONS_EMPTY);
    }
}
