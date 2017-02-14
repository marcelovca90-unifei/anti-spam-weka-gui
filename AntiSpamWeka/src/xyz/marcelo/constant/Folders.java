package xyz.marcelo.constant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Folders
{
    public static String SEPARATOR = File.separator;

    private static String[] dataSets = { "LING_SPAM", "SPAM_ASSASSIN", "TREC", "UNIFEI_2017" };

    private static String[] statMethods = { "CHI2", "FD", "MI" };

    private static Integer[] featureAmounts = { 8, 16, 32, 64, 128, 256, 512 };

    public static List<String> getFolders(String baseFolderPath) throws IOException
    {
        File baseFolder = new File(baseFolderPath);
        if (!baseFolder.exists() || !baseFolder.isDirectory()) throw new IOException();

        List<String> folders = new ArrayList<>();
        for (String dataSet : dataSets)
            for (String statMethod : statMethods)
                for (Integer featureAmount : featureAmounts)
                    folders.add(baseFolderPath + SEPARATOR + dataSet + SEPARATOR + statMethod + SEPARATOR + featureAmount);

        return folders;
    }

    public static String shortenFolderName(String baseFolder, String folder)
    {
        return shortenFolderName(baseFolder, folder, 3);
    }

    public static String shortenFolderName(String baseFolder, String folder, int maxLength)
    {
        String[] parts = baseFolder.split("\\" + SEPARATOR);
        for (String part : parts)
        {
            if (folder.contains(part))
            {
                folder = folder.replaceAll(part, part.length() > maxLength ? part.substring(0, maxLength) + "~" : part);
            }
        }
        return folder;
    }
}
