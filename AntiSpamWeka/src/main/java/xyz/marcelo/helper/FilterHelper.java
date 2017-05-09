package xyz.marcelo.helper;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.MultiObjectiveEvolutionarySearch;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.SimpleBatchFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.ClassBalancer;

public final class FilterHelper
{
    // remove less relevant attributes from the given data set
    public static Instances applyAttributeFilter(Instances dataSet) throws Exception
    {
        ASEvaluation evaluator = new CfsSubsetEval();
        ((OptionHandler) evaluator).setOptions(Utils.splitOptions("-P 1 -E 1"));

        ASSearch search = new MultiObjectiveEvolutionarySearch();
        ((OptionHandler) search).setOptions(Utils.splitOptions("-generations 10 -population-size 100 -seed 1 -a 0"));

        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setInputFormat(dataSet);
        attributeSelection.setEvaluator(evaluator);
        attributeSelection.setSearch(search);

        return Filter.useFilter(dataSet, attributeSelection);
    }

    // remove less relevant instances from the given data set
    public static Instances applyInstanceFilter(Instances dataSet) throws Exception
    {
        SimpleBatchFilter batchFilter = new ClassBalancer();
        batchFilter.setInputFormat(dataSet);
        batchFilter.setOptions(Utils.splitOptions("-num-intervals 10"));
        batchFilter.setDebug(true);

        return Filter.useFilter(dataSet, batchFilter);
    }
}
