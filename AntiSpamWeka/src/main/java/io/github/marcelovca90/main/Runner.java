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

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogManager;

import io.github.marcelovca90.common.Constants.MessageType;
import io.github.marcelovca90.common.DataSetMetadata;
import io.github.marcelovca90.common.FilterConfiguration;
import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.common.MethodEvaluation;
import io.github.marcelovca90.helper.MetaHelper;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class Runner
{
    public static void main(String[] args) throws Exception
    {
        new Runner().run(args);
    }

    private void run(String[] args) throws Exception
    {
        // change global setting for Logger instances to WARNING level
        Arrays
            .stream(LogManager.getLogManager().getLogger("").getHandlers())
            .forEach(h -> h.setLevel(Level.WARNING));

        // initialize the CLI helper with the provided arguments
        MetaHelper.getCommandLineHelper().initialize(args);

        // print the parsed, args-provided parameters
        MetaHelper.getCommandLineHelper().printConfiguration();

        // objects that will hold all kinds of data sets
        Instances dataSet = null;
        Instances trainingSet = null;
        Instances testingSet = null;
        Instances emptySet = null;

        for (MethodConfiguration method : MetaHelper.getCommandLineHelper().getMethods())
        {
            MetaHelper.getExperimentHelper().printHeader();

            for (DataSetMetadata metadata : MetaHelper.getCommandLineHelper().getDataSetsMetadata())
            {
                String folder = metadata.getFolder();
                int numberOfTotalFeatures;
                int numberOfActualFeatures;

                // initialize random number generator
                MetaHelper.getRandomHelper().reset();

                String arffFilePath = folder + File.separator + "data.arff";
                if (Paths.get(arffFilePath).toFile().exists())
                {
                    // count the number of total features by looking at the file name
                    dataSet = MetaHelper.getInputOutputHelper().loadInstancesFromArffFile(arffFilePath);

                    // count the number of total features by looking at the data set
                    numberOfTotalFeatures = Integer.valueOf(folder.substring(folder.lastIndexOf(folder.contains("\\") ? "\\" : "/") + 1));
                }
                else
                {
                    // import data sets for each class
                    String hamFilePath = folder + File.separator + MessageType.HAM.name().toLowerCase();
                    Instances hamDataSet = MetaHelper.getInputOutputHelper().loadInstancesFromRawFile(hamFilePath, MessageType.HAM);
                    String spamFilePath = folder + File.separator + MessageType.SPAM.name().toLowerCase();
                    Instances spamDataSet = MetaHelper.getInputOutputHelper().loadInstancesFromRawFile(spamFilePath, MessageType.SPAM);

                    // match class cardinalities so data set becomes balanced
                    MetaHelper.getInputOutputHelper().matchCardinalities(hamDataSet, spamDataSet);

                    // merge ham and spam data sets
                    dataSet = MetaHelper.getInputOutputHelper().mergeInstances(hamDataSet, spamDataSet);

                    // count the number of total features by looking at the data set
                    numberOfTotalFeatures = dataSet.numAttributes() - 1;
                }

                // apply attribute and instance filters to the data set, if specified
                if (MetaHelper.getCommandLineHelper().shrinkFeatures())
                    dataSet = FilterConfiguration.buildAndApply(dataSet, FilterConfiguration.AttributeFilter.CfsSubsetEval_MultiObjectiveEvolutionarySearch);
                if (MetaHelper.getCommandLineHelper().balanceClasses())
                    dataSet = FilterConfiguration.buildAndApply(dataSet, FilterConfiguration.InstanceFilter.ClassBalancer);

                // count the number of actual features by looking at the data set
                numberOfActualFeatures = dataSet.numAttributes() - 1;

                // save whole set to .arff file, if specified
                if (MetaHelper.getCommandLineHelper().saveArff())
                    MetaHelper.getInputOutputHelper().saveInstancesToArffFile(dataSet, folder + File.separator + "data.arff");

                // build empty patterns set, if specified
                if (MetaHelper.getCommandLineHelper().includeEmpty())
                    emptySet = MetaHelper.getInputOutputHelper().createEmptyInstances(dataSet.numAttributes() - 1, metadata.getEmptyHamCount(), metadata.getEmptySpamCount());

                // build the classifier for the given configuration
                Classifier baseClassifier = MethodConfiguration.buildClassifierFor(method);

                // create the object that will hold the overall evaluations result
                MethodEvaluation baseEvaluation = new MethodEvaluation(folder, method);

                // reset run results keeper
                MetaHelper.getExperimentHelper().clearResultHistory();

                int numberOfRuns = MetaHelper.getCommandLineHelper().getNumberOfRuns();
                for (int run = 0; run < numberOfRuns; run++)
                {
                    // set random number generator's seed
                    MetaHelper.getRandomHelper().update();

                    // randomize the data set to assure balance and avoid biasing
                    dataSet.randomize(MetaHelper.getRandomHelper().getRandom());

                    // build train and test sets
                    double splitPercent = method.getSplitPercent();
                    int trainingSetSize = (int) Math.round(dataSet.numInstances() * splitPercent);
                    int testingSetSize = dataSet.numInstances() - trainingSetSize;
                    trainingSet = new Instances(dataSet, 0, trainingSetSize);
                    testingSet = new Instances(dataSet, trainingSetSize, testingSetSize);

                    // add empty patterns to test set
                    if (MetaHelper.getCommandLineHelper().includeEmpty())
                        testingSet.addAll(emptySet);

                    // save the data sets to .csv files, if specified
                    if (MetaHelper.getCommandLineHelper().saveSets())
                    {
                        MetaHelper.getInputOutputHelper().saveInstancesToArffFile(trainingSet, folder + File.separator + "training.arff");
                        MetaHelper.getInputOutputHelper().saveInstancesToArffFile(testingSet, folder + File.separator + "testing.arff");
                    }

                    // if the training should be skipped, then read the classifier from the filesystem; else, clone and train the base classifier
                    Classifier classifier = AbstractClassifier.makeCopy(baseClassifier);

                    // create the object that will hold the single evaluation result
                    Evaluation evaluation = new Evaluation(testingSet);

                    // setup the classifier evaluation
                    baseEvaluation.setClassifier(classifier);
                    baseEvaluation.setEvaluation(evaluation);
                    baseEvaluation.setNumberOfTotalFeatures(numberOfTotalFeatures);
                    baseEvaluation.setNumberOfActualFeatures(numberOfActualFeatures);

                    // if the classifier could not be loaded from the filesystem, then train it
                    if (!MetaHelper.getCommandLineHelper().skipTrain())
                        baseEvaluation.train(trainingSet);

                    // if the testing should not be skipped
                    if (!MetaHelper.getCommandLineHelper().skipTest())
                    {
                        // evaluate the classifier
                        baseEvaluation.test(testingSet);

                        // compute and log the partial results for this configuration
                        MetaHelper.getExperimentHelper().computeSingleRunResults(baseEvaluation);
                        MetaHelper.getExperimentHelper().summarizeResults(baseEvaluation, false, true);

                        // if at the end of last run, detect and remove outliers (if specified); this may lead to additional runs
                        if (MetaHelper.getCommandLineHelper().removeOutliers() && run == (numberOfRuns - 1))
                            run -= MetaHelper.getExperimentHelper().detectAndRemoveOutliers();
                    }

                    // persist the classifier, if specified in args
                    if (MetaHelper.getCommandLineHelper().saveModel())
                    {
                        String classifierFilename = MetaHelper.getInputOutputHelper().buildClassifierFilename(folder, method, splitPercent);
                        MetaHelper.getInputOutputHelper().saveModelToFile(classifierFilename, classifier);
                    }
                }

                // log the final results for this configuration
                if (MetaHelper.getCommandLineHelper().getNumberOfRuns() > 0 && !MetaHelper.getCommandLineHelper().skipTest())
                    MetaHelper.getExperimentHelper().summarizeResults(baseEvaluation, true, true);
            }
        }
    }
}
