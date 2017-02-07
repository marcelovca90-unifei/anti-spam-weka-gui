package xyz.marcelo.helper;

import java.util.HashMap;
import java.util.LinkedList;

import xyz.marcelo.method.MethodConfiguration;
import xyz.marcelo.method.MethodEvaluation;

public class FormatHelper
{
    private static String folder;
    private static MethodConfiguration methodConfiguration;
    private static double trainTime;
    private static double testTime;
    private static int totalCorrect = 0;
    private static int totalIncorrect = 0;
    private static double totalCorrectPercent = 0;
    private static double totalIncorrectPercent = 0;
    private static int hamCorrect = 0;
    private static int hamIncorrect = 0;
    private static int spamCorrect = 0;
    private static int spamIncorrect = 0;
    private static double hamPrecision = 0;
    private static double hamRecall = 0;
    private static double spamPrecision = 0;
    private static double spamRecall = 0;

    private static HashMap<String, LinkedList<Double[]>> keeper = new HashMap<>();

    public static void aggregateResult(MethodEvaluation methodEvaluation, boolean printPartialResult) throws Exception
    {
        folder = methodEvaluation.getFolder();

        methodConfiguration = methodEvaluation.getMethodConfiguration();

        trainTime = (methodEvaluation.getTrainEnd() - methodEvaluation.getTrainStart());

        testTime = (methodEvaluation.getTestEnd() - methodEvaluation.getTestStart());

        String summary = methodEvaluation.getEvaluation().toSummaryString();

        String confusionMatrix = methodEvaluation.getEvaluation().toMatrixString();

        String classDetail = methodEvaluation.getEvaluation().toClassDetailsString();

        String lines[] = (summary + System.lineSeparator() + confusionMatrix + System.lineSeparator() + classDetail).split("\\r?\\n");

        for (String line : lines)
        {
            String cleanLine = line.replaceAll("\\s+", "\t").trim();

            String[] parts = cleanLine.split("\\t");

            if (line.contains("Correctly Classified Instances"))
            {
                totalCorrect = Integer.parseInt(parts[3]);
                try
                {
                    totalCorrectPercent = Double.parseDouble(parts[4]);
                }
                catch (NumberFormatException ex)
                {
                    totalCorrectPercent = Double.parseDouble(parts[4].replace(',', '.'));
                }
            }

            else if (line.contains("Incorrectly Classified Instances"))
            {
                totalIncorrect = Integer.parseInt(parts[3]);
                try
                {
                    totalIncorrectPercent = Double.parseDouble(parts[4]);
                }
                catch (NumberFormatException ex)
                {
                    totalIncorrectPercent = Double.parseDouble(parts[4].replace(',', '.'));
                }
            }

            else if (line.contains("|") && line.contains("ham"))
            {
                hamCorrect = Integer.parseInt(parts[0]);
                hamIncorrect = Integer.parseInt(parts[1]);
            }

            else if (line.contains("|") && line.contains("spam"))
            {
                spamCorrect = Integer.parseInt(parts[0]);
                spamIncorrect = Integer.parseInt(parts[1]);
            }

            else if ((line.contains("0.") || line.contains("0,")) && line.contains("ham"))
            {
                try
                {
                    hamPrecision = 100 * Double.parseDouble(parts[4]);
                    hamRecall = 100 * Double.parseDouble(parts[5]);
                }
                catch (NumberFormatException ex)
                {
                    hamPrecision = 100 * Double.parseDouble(parts[4].replace(',', '.'));
                    hamRecall = 100 * Double.parseDouble(parts[5].replace(',', '.'));
                }
            }

            else if ((line.contains("0.") || line.contains("0,")) && line.contains("spam"))
            {
                try
                {
                    spamPrecision = 100 * Double.parseDouble(parts[4]);
                    spamRecall = 100 * Double.parseDouble(parts[5]);
                }
                catch (NumberFormatException ex)
                {
                    spamPrecision = 100 * Double.parseDouble(parts[4].replace(',', '.'));
                    spamRecall = 100 * Double.parseDouble(parts[5].replace(',', '.'));
                }
            }
        }

        String key = folder + "\t" + methodConfiguration.getPseudoHashCode();
        Double[] value = new Double[] { hamPrecision, spamPrecision, hamRecall, spamRecall, trainTime, testTime };
        if (!keeper.containsKey(key))
            keeper.put(key, new LinkedList<Double[]>());

        // TODO: try to detect outlier; if not, then add

        keeper.get(key).add(value);

        if (printPartialResult)
            System.out.println(String.format("%s\t%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f", folder, methodConfiguration.name(), hamPrecision, spamPrecision,
                    hamRecall, spamRecall, trainTime, testTime));
    }

    public static void debug()
    {
        System.out.println("totalCorrect = " + totalCorrect);
        System.out.println("totalIncorrect = " + totalIncorrect);
        System.out.println("totalCorrectPercent = " + totalCorrectPercent);
        System.out.println("totalIncorrectPercent = " + totalIncorrectPercent);
        System.out.println("hamCorrect = " + hamCorrect);
        System.out.println("hamIncorrect = " + hamIncorrect);
        System.out.println("spamCorrect = " + spamCorrect);
        System.out.println("spamIncorrect = " + spamIncorrect);
        System.out.println("hamPrecision = " + hamPrecision);
        System.out.println("hamRecall = " + hamRecall);
        System.out.println("spamPrecision = " + spamPrecision);
        System.out.println("spamRecall = " + spamRecall);
    }

    public static void printFooter()
    {
        System.out.println("--------------------------------" + System.lineSeparator());
    }

    public static void printHeader()
    {
        System.out.println("PATH\tMTHD\tHP\tSP\tHR\tSR\tTrTi\tTeTi");
    }

    public static void printResults()
    {
        String key = folder + "\t" + methodConfiguration.getPseudoHashCode();
        LinkedList<Double[]> values = keeper.get(key);

        LinkedList<Double> hamPrecisionValues = new LinkedList<>();
        LinkedList<Double> spamPrecisionValues = new LinkedList<>();
        LinkedList<Double> hamRecallValues = new LinkedList<>();
        LinkedList<Double> spamRecallValues = new LinkedList<>();
        LinkedList<Double> trainTimeValues = new LinkedList<>();
        LinkedList<Double> testTimeValues = new LinkedList<>();

        for (Double[] value : values)
        {
            hamPrecisionValues.add(value[0]);
            spamPrecisionValues.add(value[1]);
            hamRecallValues.add(value[2]);
            spamRecallValues.add(value[3]);
            trainTimeValues.add(value[4]);
            testTimeValues.add(value[5]);
        }

        double hamPrecisionAvg = StatHelper.mean(hamPrecisionValues);
        double hamPrecisionStdDev = StatHelper.standardDeviation(hamPrecisionValues);

        double spamPrecisionAvg = StatHelper.mean(spamPrecisionValues);
        double spamPrecisionStdDev = StatHelper.standardDeviation(spamPrecisionValues);

        double hamRecallAvg = StatHelper.mean(hamRecallValues);
        double hamRecallStdDev = StatHelper.standardDeviation(hamRecallValues);

        double spamRecallAvg = StatHelper.mean(spamRecallValues);
        double spamRecallStdDev = StatHelper.standardDeviation(spamRecallValues);

        double trainTimeAvg = StatHelper.mean(trainTimeValues);
        double trainTimeStdDev = StatHelper.standardDeviation(trainTimeValues);

        double testTimeAvg = StatHelper.mean(testTimeValues);
        double testTimeStdDev = StatHelper.standardDeviation(testTimeValues);

        System.out.println(String.format("%s\t%s\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f\t%.2f ± %.2f", folder,
                methodConfiguration.name(), hamPrecisionAvg, hamPrecisionStdDev, spamPrecisionAvg, spamPrecisionStdDev, hamRecallAvg, hamRecallStdDev,
                spamRecallAvg, spamRecallStdDev, trainTimeAvg, trainTimeStdDev, testTimeAvg, testTimeStdDev));

        keeper.clear();
    }

    public static void setFolder(String folder)
    {
        FormatHelper.folder = folder;
    }

    public static void setMethodConfiguration(MethodConfiguration methodConfiguration)
    {
        FormatHelper.methodConfiguration = methodConfiguration;
    }

    public static void setTestTime(double testTime)
    {
        FormatHelper.testTime = testTime;
    }

    public static void setTrainTime(double trainTime)
    {
        FormatHelper.trainTime = trainTime;
    }
}
