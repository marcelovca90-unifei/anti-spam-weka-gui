package xyz.marcelo.helper;

import static xyz.marcelo.helper.DataSetHelper.SEPARATOR;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

public class EmptyInstanceHelper
{
    private static Map<String, Pair<Integer, Integer>> hashMap = populateHashMap();

    // initializes the hashmap that contains the empty amounts of hams and spams (respectively) in each folder
    private static Map<String, Pair<Integer, Integer>> populateHashMap()
    {
        Map<String, Pair<Integer, Integer>> map = new HashMap<>();

        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "CHI2" + SEPARATOR + "8", Pair.of(6567, 1409));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "CHI2" + SEPARATOR + "16", Pair.of(6551, 1399));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "CHI2" + SEPARATOR + "32", Pair.of(6510, 1381));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "CHI2" + SEPARATOR + "64", Pair.of(6432, 1359));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "CHI2" + SEPARATOR + "128", Pair.of(6239, 1325));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "CHI2" + SEPARATOR + "256", Pair.of(5850, 1325));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "CHI2" + SEPARATOR + "512", Pair.of(5247, 1325));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "FD" + SEPARATOR + "8", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "FD" + SEPARATOR + "16", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "FD" + SEPARATOR + "32", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "FD" + SEPARATOR + "64", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "FD" + SEPARATOR + "128", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "FD" + SEPARATOR + "256", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "FD" + SEPARATOR + "512", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "MI" + SEPARATOR + "8", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "MI" + SEPARATOR + "16", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "MI" + SEPARATOR + "32", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "MI" + SEPARATOR + "64", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "MI" + SEPARATOR + "128", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "MI" + SEPARATOR + "256", Pair.of(0, 0));
        map.put("2017_BASE2_LING_SPAM" + SEPARATOR + "MI" + SEPARATOR + "512", Pair.of(0, 0));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "CHI2" + SEPARATOR + "8", Pair.of(178, 218));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "CHI2" + SEPARATOR + "16", Pair.of(163, 186));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "CHI2" + SEPARATOR + "32", Pair.of(145, 171));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "CHI2" + SEPARATOR + "64", Pair.of(110, 159));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "CHI2" + SEPARATOR + "128", Pair.of(83, 122));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "CHI2" + SEPARATOR + "256", Pair.of(24, 12));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "CHI2" + SEPARATOR + "512", Pair.of(24, 12));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "FD" + SEPARATOR + "8", Pair.of(0, 14));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "FD" + SEPARATOR + "16", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "FD" + SEPARATOR + "32", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "FD" + SEPARATOR + "64", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "FD" + SEPARATOR + "128", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "FD" + SEPARATOR + "256", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "FD" + SEPARATOR + "512", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "MI" + SEPARATOR + "8", Pair.of(0, 19));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "MI" + SEPARATOR + "16", Pair.of(0, 17));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "MI" + SEPARATOR + "32", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "MI" + SEPARATOR + "64", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "MI" + SEPARATOR + "128", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "MI" + SEPARATOR + "256", Pair.of(0, 3));
        map.put("2017_BASE2_SPAM_ASSASSIN" + SEPARATOR + "MI" + SEPARATOR + "512", Pair.of(0, 3));
        map.put("2017_BASE2_TREC" + SEPARATOR + "CHI2" + SEPARATOR + "8", Pair.of(124549, 79403));
        map.put("2017_BASE2_TREC" + SEPARATOR + "CHI2" + SEPARATOR + "16", Pair.of(123891, 78219));
        map.put("2017_BASE2_TREC" + SEPARATOR + "CHI2" + SEPARATOR + "32", Pair.of(123634, 78036));
        map.put("2017_BASE2_TREC" + SEPARATOR + "CHI2" + SEPARATOR + "64", Pair.of(122781, 77532));
        map.put("2017_BASE2_TREC" + SEPARATOR + "CHI2" + SEPARATOR + "128", Pair.of(121511, 75894));
        map.put("2017_BASE2_TREC" + SEPARATOR + "CHI2" + SEPARATOR + "256", Pair.of(117642, 73723));
        map.put("2017_BASE2_TREC" + SEPARATOR + "CHI2" + SEPARATOR + "512", Pair.of(102781, 62899));
        map.put("2017_BASE2_TREC" + SEPARATOR + "FD" + SEPARATOR + "8", Pair.of(5520, 1951));
        map.put("2017_BASE2_TREC" + SEPARATOR + "FD" + SEPARATOR + "16", Pair.of(5414, 1948));
        map.put("2017_BASE2_TREC" + SEPARATOR + "FD" + SEPARATOR + "32", Pair.of(5207, 1937));
        map.put("2017_BASE2_TREC" + SEPARATOR + "FD" + SEPARATOR + "64", Pair.of(5145, 1929));
        map.put("2017_BASE2_TREC" + SEPARATOR + "FD" + SEPARATOR + "128", Pair.of(5056, 1919));
        map.put("2017_BASE2_TREC" + SEPARATOR + "FD" + SEPARATOR + "256", Pair.of(4998, 1888));
        map.put("2017_BASE2_TREC" + SEPARATOR + "FD" + SEPARATOR + "512", Pair.of(4934, 1875));
        map.put("2017_BASE2_TREC" + SEPARATOR + "MI" + SEPARATOR + "8", Pair.of(9690, 2411));
        map.put("2017_BASE2_TREC" + SEPARATOR + "MI" + SEPARATOR + "16", Pair.of(7348, 2400));
        map.put("2017_BASE2_TREC" + SEPARATOR + "MI" + SEPARATOR + "32", Pair.of(5500, 2248));
        map.put("2017_BASE2_TREC" + SEPARATOR + "MI" + SEPARATOR + "64", Pair.of(5363, 1943));
        map.put("2017_BASE2_TREC" + SEPARATOR + "MI" + SEPARATOR + "128", Pair.of(5328, 1941));
        map.put("2017_BASE2_TREC" + SEPARATOR + "MI" + SEPARATOR + "256", Pair.of(5301, 1920));
        map.put("2017_BASE2_TREC" + SEPARATOR + "MI" + SEPARATOR + "512", Pair.of(5214, 1887));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "CHI2" + SEPARATOR + "8", Pair.of(352209, 508321));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "CHI2" + SEPARATOR + "16", Pair.of(348045, 503329));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "CHI2" + SEPARATOR + "32", Pair.of(347591, 502947));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "CHI2" + SEPARATOR + "64", Pair.of(346991, 502862));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "CHI2" + SEPARATOR + "128", Pair.of(345507, 502563));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "CHI2" + SEPARATOR + "256", Pair.of(337363, 498522));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "CHI2" + SEPARATOR + "512", Pair.of(304883, 449219));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "FD" + SEPARATOR + "8", Pair.of(2892, 10485));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "FD" + SEPARATOR + "16", Pair.of(2846, 10455));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "FD" + SEPARATOR + "32", Pair.of(2835, 10455));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "FD" + SEPARATOR + "64", Pair.of(2814, 10441));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "FD" + SEPARATOR + "128", Pair.of(2807, 10436));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "FD" + SEPARATOR + "256", Pair.of(2754, 10436));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "FD" + SEPARATOR + "512", Pair.of(2673, 10408));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "MI" + SEPARATOR + "8", Pair.of(53238, 75774));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "MI" + SEPARATOR + "16", Pair.of(16589, 34168));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "MI" + SEPARATOR + "32", Pair.of(16500, 34077));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "MI" + SEPARATOR + "64", Pair.of(2867, 10465));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "MI" + SEPARATOR + "128", Pair.of(2835, 10465));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "MI" + SEPARATOR + "256", Pair.of(2795, 10436));
        map.put("2017_BASE2_UNIFEI" + SEPARATOR + "MI" + SEPARATOR + "512", Pair.of(2760, 10436));

        return map;
    }

    // returns a pair containing the empty amounts of hams and spams (respectively) for a given folder
    public static Pair<Integer, Integer> getEmptyInstancesCountByFolder(String folder)
    {
        String key = folder;

        for (Entry<String, Pair<Integer, Integer>> entry : hashMap.entrySet())
            if (key.contains(entry.getKey())) return entry.getValue();

        return null;
    }
}
