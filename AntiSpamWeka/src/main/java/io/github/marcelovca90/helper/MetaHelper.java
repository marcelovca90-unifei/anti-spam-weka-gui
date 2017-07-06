package io.github.marcelovca90.helper;

public class MetaHelper
{
    private static CLIHelper cliHelper;
    private static FormatHelper formatHelper;
    private static IOHelper ioHelper;
    private static ResultHelper resultHelper;

    public static CLIHelper getCliHelper()
    {
        if (cliHelper == null)
            cliHelper = new CLIHelper();
        return cliHelper;
    }

    public static FormatHelper getFormatHelper()
    {
        if (formatHelper == null)
            formatHelper = new FormatHelper();
        return formatHelper;
    }

    public static IOHelper getIoHelper()
    {
        if (ioHelper == null)
            ioHelper = new IOHelper();
        return ioHelper;
    }

    public static ResultHelper getResultHelper()
    {
        if (resultHelper == null)
            resultHelper = new ResultHelper();
        return resultHelper;
    }

    public static void initialize(CLIHelper _cliHelper, FormatHelper _formatHelper, IOHelper _ioHelper, ResultHelper _resultHelper)
    {
        cliHelper = _cliHelper;
        formatHelper = _formatHelper;
        ioHelper = _ioHelper;
        resultHelper = _resultHelper;
    }
}
