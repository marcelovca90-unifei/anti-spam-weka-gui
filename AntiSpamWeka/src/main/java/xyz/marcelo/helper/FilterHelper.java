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

public class FilterHelper
{
    // used to suppress the default public constructor
    private FilterHelper()
    {
    }

    private static final FilterHelper INSTANCE = new FilterHelper();

    public static final FilterHelper getInstance()
    {
        return INSTANCE;
    }

    // remove less relevant attributes from the given data set
    public Instances applyAttributeFilter(Instances dataSet) throws Exception
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
    public Instances applyInstanceFilter(Instances dataSet) throws Exception
    {
        SimpleBatchFilter batchFilter = new ClassBalancer();
        batchFilter.setInputFormat(dataSet);
        batchFilter.setOptions(Utils.splitOptions("-num-intervals 10"));
        batchFilter.setDebug(true);

        return Filter.useFilter(dataSet, batchFilter);
    }
}
