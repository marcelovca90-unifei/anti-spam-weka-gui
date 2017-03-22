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

import xyz.marcelo.common.DataSetMetadata;
import xyz.marcelo.common.MethodConfiguration;

public class CLIHelper
{
    private static final String OPTION_METADATA = "metadata";
    private static final String OPTION_METHOD = "method";
    private static final String OPTION_RUNS = "runs";
    private static final String OPTION_SKIP_TRAIN = "skipTrain";
    private static final String OPTION_SKIP_TEST = "skipTest";
    private static final String OPTION_TEST_EMPTY = "testEmpty";
    private static final String OPTION_SAVE_MODEL = "saveModel";

    private static CommandLine cmd;

    public static void initialize(String[] args) throws ParseException
    {
        // create Options object
        Options options = new Options();

        // add the command line options
        options.addOption(OPTION_METADATA, true, "path of the file containing data sets metadata (default: none)");
        options.addOption(OPTION_METHOD, true,
                "comma-separated list of methods to be used. Available methods: " + Arrays.toString(MethodConfiguration.values()) + " (default: none)");
        options.addOption(OPTION_RUNS, true, "number of repetitions to be performed (default: 1)");
        options.addOption(OPTION_SKIP_TRAIN, false, "perform training (learning) of the classifier(s) (default: false)");
        options.addOption(OPTION_SKIP_TEST, false, "perform testing (evaluation) of the classifier(s) (default: false)");
        options.addOption(OPTION_TEST_EMPTY, false, "include empty patterns while testing the classifier (default: false)");
        options.addOption(OPTION_SAVE_MODEL, false, "save the classifier to a .model file (default: false)");

        // instantiates the cli based on the provided arguments
        try
        {
            cmd = new DefaultParser().parse(options, args);

            if (!cmd.hasOption(OPTION_METADATA) || !cmd.hasOption(OPTION_METHOD))
            {
                throw new ParseException("Missing mandatory arguments");
            }
        }
        catch (ParseException e)
        {
            // automatically generate the help statement
            String mandatoryArgs = "METADATA METHOD";
            String optionalArgs = "[RUNS] [SKIP_TRAIN] [SKIP_TEST] [TEST_EMPTY] [SAVE_MODEL]";
            new HelpFormatter().printHelp("java -jar AntiSpamWeka.jar " + mandatoryArgs + " " + optionalArgs, options);
        }
    }

    public static Set<DataSetMetadata> getDataSetsMetadata() throws IOException
    {
        Set<DataSetMetadata> metadata = new LinkedHashSet<>();
        if (cmd.hasOption(OPTION_METADATA))
        {
            metadata.addAll(IOHelper.loadDataSetsMetadataFromFile(cmd.getOptionValue(OPTION_METADATA)));
        }
        return metadata;
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

    public static int getNumberOfRuns()
    {
        return cmd.hasOption(OPTION_RUNS) ? Integer.parseInt(cmd.getOptionValue(OPTION_RUNS)) : 1;
    }

    public static boolean shouldSkipTrain()
    {
        return cmd.hasOption(OPTION_SKIP_TRAIN);
    }

    public static boolean shouldSkipTest()
    {
        return cmd.hasOption(OPTION_SKIP_TEST);
    }

    public static boolean shouldIncludeEmptyInstances()
    {
        return cmd.hasOption(OPTION_TEST_EMPTY);
    }

    public static boolean shouldSaveModel()
    {
        return cmd.hasOption(OPTION_SAVE_MODEL);
    }

    public static void printConfiguration() throws IOException
    {
        System.out.println("---- CONFIGURATION ----");
        System.out.println(OPTION_METADATA + " : " + getDataSetsMetadata());
        System.out.println(OPTION_METHOD + " : " + getMethods());
        System.out.println(OPTION_RUNS + " : " + getNumberOfRuns());
        System.out.println(OPTION_SKIP_TRAIN + " : " + shouldSkipTrain());
        System.out.println(OPTION_SKIP_TEST + " : " + shouldSkipTest());
        System.out.println(OPTION_TEST_EMPTY + " : " + shouldIncludeEmptyInstances());
        System.out.println(OPTION_SAVE_MODEL + " : " + shouldSaveModel());
        System.out.println("-----------------------");
    }
}
