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

public class Constants
{
    public static final String UNEXPECTED_EXCEPTION_MASK = "Unexpected exception: {}";

    public enum CLIOption
    {
        METADATA,
        METHOD,
        RUNS,
        SKIP_TRAIN,
        SKIP_TEST,
        SHRINK_FEATURES,
        BALANCE_CLASSES,
        TEST_EMPTY,
        SAVE_MODEL,
        SAVE_SETS;
    }

    public enum Metric
    {
        HAM_PRECISION,
        SPAM_PRECISION,
        HAM_RECALL,
        SPAM_RECALL,
        HAM_AREA_UNDER_PRC,
        SPAM_AREA_UNDER_PRC,
        HAM_AREA_UNDER_ROC,
        SPAM_AREA_UNDER_ROC,
        TRAIN_TIME,
        TEST_TIME
    }

    public enum MessageType
    {
        HAM,
        SPAM
    }
}
