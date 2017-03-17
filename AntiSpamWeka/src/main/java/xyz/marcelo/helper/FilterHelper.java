package xyz.marcelo.helper;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.ClassBalancer;

public class FilterHelper
{
    public static Boolean shouldApplyAttributeFilter(String folder)
    {
        /*
         * if (folder.contains(File.separator + "CHI2" + File.separator)) return false;
         * if (folder.contains(File.separator + "FD" + File.separator)) return true;
         * if (folder.contains(File.separator + "MI" + File.separator)) return true;
         */
        return true;
    }

    // remove less relevant attributes of the given data set
    private static Instances applyAttributeFilter(Instances dataSet) throws Exception
    {
        CfsSubsetEval cfsSubsetEval = new CfsSubsetEval();
        cfsSubsetEval.setOptions(Utils.splitOptions("-Z -P 1 -E 1"));

        BestFirst bestFirst = new BestFirst();
        bestFirst.setOptions(Utils.splitOptions("-D 2 -N 5"));

        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setInputFormat(dataSet);
        attributeSelection.setEvaluator(cfsSubsetEval);
        attributeSelection.setSearch(bestFirst);

        return Filter.useFilter(dataSet, attributeSelection);
    }

    public static Boolean shouldApplyInstanceFilter(String folder)
    {
        /*
         * if (folder.contains(File.separator + "CHI2" + File.separator)) return false;
         * if (folder.contains(File.separator + "FD" + File.separator)) return true;
         * if (folder.contains(File.separator + "MI" + File.separator)) return true;
         */
        return true;
    }

    // remove less relevant instances of the given data set
    private static Instances applyInstanceFilter(Instances dataSet) throws Exception
    {
        ClassBalancer classBalancer = new ClassBalancer();
        classBalancer.setInputFormat(dataSet);
        classBalancer.setOptions(Utils.splitOptions("-num-intervals 10"));
        classBalancer.setDebug(true);

        return Filter.useFilter(dataSet, classBalancer);
    }

    public static Instances applyFilters(Instances dataSet, boolean attributeFilter, boolean instanceFilter) throws Exception
    {
        if (attributeFilter) dataSet = applyAttributeFilter(dataSet);
        if (instanceFilter) dataSet = applyInstanceFilter(dataSet);
        return dataSet;
    }
}
