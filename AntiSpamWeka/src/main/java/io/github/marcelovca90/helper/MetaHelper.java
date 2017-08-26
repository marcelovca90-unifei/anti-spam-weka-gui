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
package io.github.marcelovca90.helper;

public class MetaHelper
{
    private static CommandLineHelper commandLineHelper;
    private static ExperimentHelper experimentHelper;
    private static InputOutputHelper inputOutputHelper;
    private static RandomHelper randomHelper;

    public static CommandLineHelper getCommandLineHelper()
    {
        if (commandLineHelper == null)
            commandLineHelper = new CommandLineHelper();
        return commandLineHelper;
    }

    public static ExperimentHelper getExperimentHelper()
    {
        if (experimentHelper == null)
            experimentHelper = new ExperimentHelper();
        return experimentHelper;
    }

    public static InputOutputHelper getInputOutputHelper()
    {
        if (inputOutputHelper == null)
            inputOutputHelper = new InputOutputHelper();
        return inputOutputHelper;
    }

    public static RandomHelper getRandomHelper()
    {
        if (randomHelper == null)
            randomHelper = new RandomHelper();
        return randomHelper;
    }

    public static void initialize(CommandLineHelper _commandLineHelper, ExperimentHelper _experimentHelper, InputOutputHelper _inputOutputHelper, RandomHelper _randomHelper)
    {
        commandLineHelper = _commandLineHelper;
        experimentHelper = _experimentHelper;
        inputOutputHelper = _inputOutputHelper;
        randomHelper = _randomHelper;
    }

    public static void reset()
    {
        commandLineHelper = null;
        experimentHelper = null;
        inputOutputHelper = null;
        randomHelper = null;
    }

    // used to suppress the default public constructor
    private MetaHelper()
    {
    }
}
