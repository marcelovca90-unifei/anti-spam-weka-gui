package xyz.marcelo.helpers;

import weka.classifiers.Evaluation;
import xyz.marcelo.enums.Method;

public class FormatHelper {

	private static String folder;
	private static Method method;
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

	public static void debug() {
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

	public static String formatLine(String line) {
		return line.replaceAll("\\s+", "\t").trim();
	}

	public static void printHeader() {
		System.out.println("FOLDER\tMETHOD\tHP\tSP\tHR\tSR\tTrT\tTeT");
	}

	public static void printResults(Evaluation evaluation) throws Exception {

		String summary = evaluation.toSummaryString();

		String confusionMatrix = evaluation.toMatrixString();

		String classDetail = evaluation.toClassDetailsString();

		String lines[] = (summary + System.lineSeparator() + confusionMatrix + System.lineSeparator() + classDetail)
				.split("\\r?\\n");

		for (String line : lines) {

			String cleanLine = formatLine(line);

			String[] parts = cleanLine.split("\\t");

			if (line.contains("Correctly Classified Instances")) {
				totalCorrect = Integer.parseInt(parts[3]);
				totalCorrectPercent = Double.parseDouble(parts[4]);
			}

			else if (line.contains("Incorrectly Classified Instances")) {
				totalIncorrect = Integer.parseInt(parts[3]);
				totalIncorrectPercent = Double.parseDouble(parts[4]);
			}

			else if (line.contains("|") && line.contains("ham")) {
				hamCorrect = Integer.parseInt(parts[0]);
				hamIncorrect = Integer.parseInt(parts[1]);
			}

			else if (line.contains("|") && line.contains("spam")) {
				spamCorrect = Integer.parseInt(parts[0]);
				spamIncorrect = Integer.parseInt(parts[1]);
			}

			else if (line.contains("0,") && line.contains("ham")) {
				hamPrecision = 100 * Double.parseDouble(parts[4].replace(',', '.'));
				hamRecall = 100 * Double.parseDouble(parts[5].replace(',', '.'));
			}

			else if (line.contains("0,") && line.contains("spam")) {
				spamPrecision = 100 * Double.parseDouble(parts[4].replace(',', '.'));
				spamRecall = 100 * Double.parseDouble(parts[5].replace(',', '.'));
			}

		}

		System.out.println(String.format("%s\t%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.1f\t%.1f", folder, method, hamPrecision,
				spamPrecision, hamRecall, spamRecall, trainTime, testTime));

	}

	public static void setFolder(String folder) {
		FormatHelper.folder = folder;
	}

	public static void setMethod(Method method) {
		FormatHelper.method = method;
	}

	public static void setTestTime(double testTime) {
		FormatHelper.testTime = testTime;
	}

	public static void setTrainTime(double trainTime) {
		FormatHelper.trainTime = trainTime;
	}

}
