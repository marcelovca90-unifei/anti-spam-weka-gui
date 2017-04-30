package xyz.marcelo.helper;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.EvolutionarySearch;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.ClassBalancer;

public class FilterHelper
{
    // remove less relevant attributes from the given data set
    public static Instances applyAttributeFilter(Instances dataSet) throws Exception
    {
        CfsSubsetEval cfsSubsetEval = new CfsSubsetEval();
        cfsSubsetEval.setOptions(Utils.splitOptions("-M -Z -P 1 -E 1"));

        EvolutionarySearch evolutionarySearch = new EvolutionarySearch();
        evolutionarySearch.setOptions(Utils.splitOptions(
                "-population-size 20 -generations 20 -init-op 0 -selection-op 1 -crossover-op 0 -crossover-probability 0.6 -mutation-op 0 -mutation-probability 0.1 -replacement-op 0 -seed 1 -report-frequency 20"));

        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setInputFormat(dataSet);
        attributeSelection.setEvaluator(cfsSubsetEval);
        attributeSelection.setSearch(evolutionarySearch);

        return Filter.useFilter(dataSet, attributeSelection);
    }

    // remove less relevant instances from the given data set
    public static Instances applyInstanceFilter(Instances dataSet) throws Exception
    {
        ClassBalancer classBalancer = new ClassBalancer();
        classBalancer.setInputFormat(dataSet);
        classBalancer.setOptions(Utils.splitOptions("-num-intervals 10"));
        classBalancer.setDebug(true);

        return Filter.useFilter(dataSet, classBalancer);
    }
}
