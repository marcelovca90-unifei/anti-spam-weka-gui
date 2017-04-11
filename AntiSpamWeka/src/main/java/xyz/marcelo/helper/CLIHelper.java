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
    private static final String OPTION_SHRINK_FEATURES = "shrinkFeatures";
    private static final String OPTION_BALANCE_CLASSES = "balanceClasses";
    private static final String OPTION_TEST_EMPTY = "testEmpty";
    private static final String OPTION_SAVE_MODEL = "saveModel";

    private static CommandLine cmd;

    public static void initialize(String[] args) throws ParseException
    {
        // create Options object
        Options opts = new Options();

        // add the command line options
        opts.addOption(OPTION_METADATA, true, "path of the file containing data sets metadata (default: none)");
        opts.addOption(OPTION_METHOD, true, "CSV list of methods. Available methods: " + Arrays.toString(MethodConfiguration.values()) + " (default: none)");
        opts.addOption(OPTION_RUNS, true, "number of repetitions to be performed (default: 1)");
        opts.addOption(OPTION_SKIP_TRAIN, false, "perform training (learning) of the classifier(s) (default: false)");
        opts.addOption(OPTION_SKIP_TEST, false, "perform testing (evaluation) of the classifier(s) (default: false)");
        opts.addOption(OPTION_TEST_EMPTY, false, "include empty patterns while testing the classifier (default: false)");
        opts.addOption(OPTION_SAVE_MODEL, false, "save the classifier to a .model file (default: false)");
        opts.addOption(OPTION_SHRINK_FEATURES, false, "reduce the feature space using an evolutionary search");
        opts.addOption(OPTION_BALANCE_CLASSES, false, "equalize the number of instances for each class");

        // instantiates the cli based on the provided arguments
        try
        {
            cmd = new DefaultParser().parse(opts, args);

            if (!cmd.hasOption(OPTION_METADATA) || !cmd.hasOption(OPTION_METHOD))
            {
                throw new ParseException("Missing mandatory arguments");
            }
        }
        catch (ParseException e)
        {
            // automatically generate the help statement
            String mandatoryArgs = "METADATA METHOD";
            String optionalArgs = "[RUNS] [SKIP_TRAIN] [SKIP_TEST] [TEST_EMPTY] [SAVE_MODEL] [SHRINK_FEATURES] [BALANCE_CLASSES]";
            new HelpFormatter().printHelp("java -jar AntiSpamWeka.jar " + mandatoryArgs + " " + optionalArgs, opts);

            // exit with status code 1, indicating abnormal termination
            System.exit(1);
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

    public static boolean skipTrain()
    {
        return cmd.hasOption(OPTION_SKIP_TRAIN);
    }

    public static boolean skipTest()
    {
        return cmd.hasOption(OPTION_SKIP_TEST);
    }

    public static boolean includeEmptyInstances()
    {
        return cmd.hasOption(OPTION_TEST_EMPTY);
    }

    public static boolean saveModel()
    {
        return cmd.hasOption(OPTION_SAVE_MODEL);
    }

    public static boolean shrinkFeatures()
    {
        return cmd.hasOption(OPTION_SHRINK_FEATURES);
    }

    public static boolean balanceClasses()
    {
        return cmd.hasOption(OPTION_BALANCE_CLASSES);
    }

    public static void printConfiguration() throws IOException
    {
        System.out.println("---- CONFIGURATION ----");
        System.out.println(OPTION_METADATA + " : " + getDataSetsMetadata());
        System.out.println(OPTION_METHOD + " : " + getMethods());
        System.out.println(OPTION_RUNS + " : " + getNumberOfRuns());
        System.out.println(OPTION_SKIP_TRAIN + " : " + skipTrain());
        System.out.println(OPTION_SKIP_TEST + " : " + skipTest());
        System.out.println(OPTION_TEST_EMPTY + " : " + includeEmptyInstances());
        System.out.println(OPTION_SAVE_MODEL + " : " + saveModel());
        System.out.println(OPTION_SHRINK_FEATURES + " : " + shrinkFeatures());
        System.out.println(OPTION_BALANCE_CLASSES + " : " + balanceClasses());
        System.out.println("-----------------------");
    }
}
