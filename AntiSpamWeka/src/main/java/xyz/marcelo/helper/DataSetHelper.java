package xyz.marcelo.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSetHelper
{
    public static String SEPARATOR = File.separator;

    private static String[] dataSets = { "2017_BASE2_LING_SPAM", "2017_BASE2_SPAM_ASSASSIN", "2017_BASE2_TREC", "2017_BASE2_UNIFEI" };

    private static String[] statMethods = { "CHI2", "FD", "MI" };

    private static Integer[] featureAmounts = { 8, 16, 32, 64, 128, 256, 512 };

    // combinates the dataSets, statMethods and featureAmounts to generate the complete folder paths
    public static List<String> getFolders(String baseFolderPath) throws IOException
    {
        File baseFolder = new File(baseFolderPath);
        if (!baseFolder.exists() || (baseFolder.exists() && !baseFolder.isDirectory())) throw new IOException();

        List<String> folders = new ArrayList<>();
        for (String dataSet : dataSets)
            for (String statMethod : statMethods)
                for (Integer featureAmount : featureAmounts)
                    folders.add(baseFolderPath + SEPARATOR + dataSet + SEPARATOR + statMethod + SEPARATOR + featureAmount);

        return folders;
    }

    // returns the last portion, i.e. the data set name, of the given folder
    public static String getDataSetNameFromFolder(String folder)
    {
        for (String dataSet : dataSets)
        {
            if (folder.contains(dataSet))
            {
                return folder.substring(folder.indexOf(dataSet));
            }
        }
        return folder;
    }
}
