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
package xyz.marcelo.common;

import org.pmw.tinylog.Logger;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.SimpleBatchFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.ClassBalancer;

public class FilterConfiguration
{
    public enum AttributeFilter
    {
        BASIC("CfsSubsetEval", "-P 1 -E 1", CfsSubsetEval.class, "BestFirst", "-D 1 -N 5", BestFirst.class);

        private final String evalName;
        private final String evalConfig;
        private final Class<? extends ASEvaluation> evalClazz;
        private final String searchName;
        private final String searchConfig;
        private final Class<? extends ASSearch> searchClazz;

        private AttributeFilter(String evalName, String evalConfig, Class<? extends ASEvaluation> evalClazz, String searchName, String searchConfig, Class<? extends ASSearch> searchClazz)
        {
            this.evalName = evalName;
            this.evalConfig = evalConfig;
            this.evalClazz = evalClazz;
            this.searchName = searchName;
            this.searchConfig = searchConfig;
            this.searchClazz = searchClazz;
        }

        public String getEvalName()
        {
            return evalName;
        }

        public String getEvalConfig()
        {
            return evalConfig;
        }

        public Class<? extends ASEvaluation> getEvalClazz()
        {
            return evalClazz;
        }

        public String getSearchName()
        {
            return searchName;
        }

        public String getSearchConfig()
        {
            return searchConfig;
        }

        public Class<? extends ASSearch> getSearchClazz()
        {
            return searchClazz;
        }

        public String getDescription()
        {
            return String.format("AttributeFilter [evalName=%s, evalConfig=%s, searchName=%s, searchConfig=%s]", evalName, evalConfig, searchName, searchConfig);
        }
    };

    // remove less relevant attributes from the given data set
    private static Filter buildAttributeFilterFor(AttributeFilter attributeFilter, Instances dataSet)
    {
        Filter filter = null;

        try
        {
            ASEvaluation evaluator = attributeFilter.getEvalClazz().newInstance();
            ((OptionHandler) evaluator).setOptions(Utils.splitOptions(attributeFilter.getEvalConfig()));

            ASSearch search = attributeFilter.getSearchClazz().newInstance();
            ((OptionHandler) search).setOptions(Utils.splitOptions(attributeFilter.getSearchConfig()));

            filter = new AttributeSelection();
            filter.setInputFormat(dataSet);
            ((AttributeSelection) filter).setEvaluator(evaluator);
            ((AttributeSelection) filter).setSearch(search);

            return filter;
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }

        return filter;
    }

    public static Instances buildAndApply(Instances dataSet, AttributeFilter filter)
    {
        Instances filteredDataSet = null;

        try
        {
            Logger.debug("Applying {} to the data set", filter.getDescription());
            filteredDataSet = Filter.useFilter(dataSet, buildAttributeFilterFor(filter, dataSet));
            Logger.debug("Applied {} to the data set", filter.getDescription());
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }

        return filteredDataSet;
    }

    public enum InstanceFilter
    {
        BASIC("ClassBalancer", "-num-intervals 10", ClassBalancer.class);

        private InstanceFilter(String batchName, String batchConfig, Class<? extends SimpleBatchFilter> batchClazz)
        {
            this.batchName = batchName;
            this.batchConfig = batchConfig;
            this.batchClazz = batchClazz;
        }

        private final String batchName;
        private final String batchConfig;
        private final Class<? extends SimpleBatchFilter> batchClazz;

        public String getBatchName()
        {
            return batchName;
        }

        public String getBatchConfig()
        {
            return batchConfig;
        }

        public Class<? extends SimpleBatchFilter> getBatchClazz()
        {
            return batchClazz;
        }

        public String getDescription()
        {
            return String.format("InstanceFilter [batchName=%s, batchConfig=%s]", batchName, batchConfig);
        }
    };

    // remove less relevant instances from the given data set
    private static Filter buildInstanceFilterFor(InstanceFilter instanceFilter, Instances dataSet)
    {
        Filter filter = null;

        try
        {
            filter = instanceFilter.getBatchClazz().newInstance();
            filter.setInputFormat(dataSet);
            filter.setOptions(Utils.splitOptions(instanceFilter.getBatchConfig()));
            filter.setDebug(true);
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }

        return filter;
    }

    public static Instances buildAndApply(Instances dataSet, InstanceFilter filter)
    {
        Instances filteredDataSet = null;

        try
        {
            Logger.debug("Applying {} to the data set", filter.getDescription());
            filteredDataSet = Filter.useFilter(dataSet, buildInstanceFilterFor(filter, dataSet));
            Logger.debug("Applied {} to the data set", filter.getDescription());
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }

        return filteredDataSet;
    }
}
