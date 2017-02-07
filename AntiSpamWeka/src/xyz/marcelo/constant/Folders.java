/**
 *
 */
package xyz.marcelo.constant;

import java.util.ArrayList;
import java.util.List;

public class Folders
{
    public static String baseFolder = "/Users/marcelocysneiros/Downloads/Vetores-2017-Fev";

    private static String[] dataSets = { "LING_SPAM", "SPAM_ASSASSIN", "TREC", "UNIFEI_2017" };

    private static String[] statMethods = { "CHI2", "FD", "MI" };

    private static Integer[] featureAmounts = { 8, 16, 32, 64, 128, 256, 512 };

    public static List<String> getFolders()
    {
        List<String> folders = new ArrayList<>();
        for (String dataSet : dataSets)
            for (String statMethod : statMethods)
                for (Integer featureAmount : featureAmounts)
                    folders.add(baseFolder + "/" + dataSet + "/" + statMethod + "/" + featureAmount);
        return folders;
    }

    public static String shortenFolderName(String folder, int maxLength)
    {
        String[] parts = baseFolder.split("\\/");
        for (String part : parts)
            if (folder.contains(part))
                folder = folder.replaceAll(part, part.length() > maxLength ? part.substring(0, maxLength) + "..." : part);
        return folder;
    }
}
