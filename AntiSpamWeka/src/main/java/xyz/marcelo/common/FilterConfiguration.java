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
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.CorrelationAttributeEval;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.PrincipalComponents;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.attributeSelection.SVMAttributeEval;
import weka.attributeSelection.SymmetricalUncertAttributeEval;
import weka.attributeSelection.WrapperSubsetEval;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.ClassBalancer;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.SpreadSubsample;
import weka.filters.supervised.instance.StratifiedRemoveFolds;

public class FilterConfiguration
{
    public enum AttributeFilter
    {
        CfsSubsetEval_GreedyStepwise(CfsSubsetEval.class, "-P 1 -E 1", GreedyStepwise.class, "-T -1.7976931348623157E308 -N -1 -num-slots 1"),
        CorrelationAttributeEval_Ranker(CorrelationAttributeEval.class, "", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        GainRatioAttributeEval_Ranker(GainRatioAttributeEval.class, "", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        InfoGainAttributeEval_Ranker(InfoGainAttributeEval.class, "", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        OneRAttributeEval_Ranker(OneRAttributeEval.class, "-S 1 -F 10 -B 6", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        PrincipalComponents_Ranker(PrincipalComponents.class, "-R 0.95 -A 5", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        ReliefFAttributeEval_Ranker(ReliefFAttributeEval.class, "-M -1 -D 1 -K 10", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        SVMAttributeEval_Ranker(SVMAttributeEval.class, "-X 1 -Y 0 -Z 0 -P 1.0E-25 -T 1.0E-10 -C 1.0 -N 0", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        SymmetricalUncertAttributeEval_Ranker(SymmetricalUncertAttributeEval.class, "", Ranker.class, "-T -1.7976931348623157E308 -N -1"),
        WrapperSubsetEval_GreedyStepwise(WrapperSubsetEval.class, "-B weka.classifiers.rules.ZeroR -F 5 -T 0.01 -R 1 -E DEFAULT --", GreedyStepwise.class, "-T -1.7976931348623157E308 -N -1 -num-slots 1");

        private final Class<? extends ASEvaluation> evalClazz;
        private final String evalConfig;
        private final Class<? extends ASSearch> searchClazz;
        private final String searchConfig;

        private AttributeFilter(Class<? extends ASEvaluation> evalClazz, String evalConfig, Class<? extends ASSearch> searchClazz, String searchConfig)
        {
            this.evalClazz = evalClazz;
            this.evalConfig = evalConfig;
            this.searchClazz = searchClazz;
            this.searchConfig = searchConfig;
        }

        public Class<? extends ASEvaluation> getEvalClazz()
        {
            return evalClazz;
        }

        public String getEvalConfig()
        {
            return evalConfig;
        }

        public Class<? extends ASSearch> getSearchClazz()
        {
            return searchClazz;
        }

        public String getSearchConfig()
        {
            return searchConfig;
        }

        public String getDescription()
        {
            return String.format("AttributeFilter [evalClass=%s, evalConfig=\"%s\", searchClass=%s, searchConfig=\"%s\"]", evalClazz.getSimpleName(), evalConfig, searchClazz.getSimpleName(), searchConfig);
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
            Logger.debug("Applying {} to the data set (numAttributes: {})", filter.getDescription(), dataSet.numAttributes());
            filteredDataSet = Filter.useFilter(dataSet, buildAttributeFilterFor(filter, dataSet));
            Logger.debug("Applied {} to the data set (numAttributes: {})", filter.getDescription(), filteredDataSet.numAttributes());
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }

        return filteredDataSet;
    }

    public enum InstanceFilter
    {
        ClassBalancer(ClassBalancer.class, "-num-intervals 10"),
        Resample(Resample.class, "-B 0.0 -S 1 -Z 100.0"),
        SpreadSubsample(SpreadSubsample.class, "-M 0.0 -X 0.0 -S 1"),
        StratifiedRemoveFolds(StratifiedRemoveFolds.class, "-S 0 -N 10 -F 1");

        private InstanceFilter(Class<? extends Filter> clazz, String config)
        {
            this.clazz = clazz;
            this.config = config;
        }

        private final Class<? extends Filter> clazz;
        private final String config;

        public Class<? extends Filter> getClazz()
        {
            return clazz;
        }

        public String getConfig()
        {
            return config;
        }

        public String getDescription()
        {
            return String.format("InstanceFilter [class=%s, config=%s]", clazz.getSimpleName(), config);
        }
    };

    // remove less relevant instances from the given data set
    private static Filter buildInstanceFilterFor(InstanceFilter instanceFilter, Instances dataSet)
    {
        Filter filter = null;

        try
        {
            filter = instanceFilter.getClazz().newInstance();
            filter.setInputFormat(dataSet);
            filter.setOptions(Utils.splitOptions(instanceFilter.getConfig()));
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
            Logger.debug("Applying {} to the data set (size: {})", filter.getDescription(), dataSet.size());
            filteredDataSet = Filter.useFilter(dataSet, buildInstanceFilterFor(filter, dataSet));
            Logger.debug("Applied {} to the data set (size: {})", filter.getDescription(), filteredDataSet.size());
        }
        catch (Exception e)
        {
            Logger.error(Constants.UNEXPECTED_EXCEPTION_MASK, e);
        }

        return filteredDataSet;
    }
}
