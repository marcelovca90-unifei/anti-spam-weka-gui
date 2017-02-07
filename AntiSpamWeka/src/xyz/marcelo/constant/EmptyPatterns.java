package xyz.marcelo.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

public class EmptyPatterns
{
    private static Map<String, Pair<Integer, Integer>> hashMap = initialize();

    private static Map<String, Pair<Integer, Integer>> initialize()
    {
        Map<String, Pair<Integer, Integer>> map = new HashMap<>();

        map.put("LING_SPAM/CHI2/8", Pair.of(6567, 1409));
        map.put("LING_SPAM/CHI2/16", Pair.of(6551, 1399));
        map.put("LING_SPAM/CHI2/32", Pair.of(6510, 1381));
        map.put("LING_SPAM/CHI2/64", Pair.of(6432, 1359));
        map.put("LING_SPAM/CHI2/128", Pair.of(6239, 1325));
        map.put("LING_SPAM/CHI2/256", Pair.of(5850, 1325));
        map.put("LING_SPAM/CHI2/512", Pair.of(5247, 1325));
        map.put("LING_SPAM/FD/8", Pair.of(0, 0));
        map.put("LING_SPAM/FD/16", Pair.of(0, 0));
        map.put("LING_SPAM/FD/32", Pair.of(0, 0));
        map.put("LING_SPAM/FD/64", Pair.of(0, 0));
        map.put("LING_SPAM/FD/128", Pair.of(0, 0));
        map.put("LING_SPAM/FD/256", Pair.of(0, 0));
        map.put("LING_SPAM/FD/512", Pair.of(0, 0));
        map.put("LING_SPAM/MI/8", Pair.of(0, 0));
        map.put("LING_SPAM/MI/16", Pair.of(0, 0));
        map.put("LING_SPAM/MI/32", Pair.of(0, 0));
        map.put("LING_SPAM/MI/64", Pair.of(0, 0));
        map.put("LING_SPAM/MI/128", Pair.of(0, 0));
        map.put("LING_SPAM/MI/256", Pair.of(0, 0));
        map.put("LING_SPAM/MI/512", Pair.of(0, 0));
        map.put("SPAM_ASSASSIN/CHI2/8", Pair.of(178, 218));
        map.put("SPAM_ASSASSIN/CHI2/16", Pair.of(163, 186));
        map.put("SPAM_ASSASSIN/CHI2/32", Pair.of(145, 171));
        map.put("SPAM_ASSASSIN/CHI2/64", Pair.of(110, 159));
        map.put("SPAM_ASSASSIN/CHI2/128", Pair.of(83, 122));
        map.put("SPAM_ASSASSIN/CHI2/256", Pair.of(24, 12));
        map.put("SPAM_ASSASSIN/CHI2/512", Pair.of(24, 12));
        map.put("SPAM_ASSASSIN/FD/8", Pair.of(0, 14));
        map.put("SPAM_ASSASSIN/FD/16", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/FD/32", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/FD/64", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/FD/128", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/FD/256", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/FD/512", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/MI/8", Pair.of(0, 19));
        map.put("SPAM_ASSASSIN/MI/16", Pair.of(0, 17));
        map.put("SPAM_ASSASSIN/MI/32", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/MI/64", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/MI/128", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/MI/256", Pair.of(0, 3));
        map.put("SPAM_ASSASSIN/MI/512", Pair.of(0, 3));
        map.put("TREC/CHI2/8", Pair.of(124549, 79403));
        map.put("TREC/CHI2/16", Pair.of(123891, 78219));
        map.put("TREC/CHI2/32", Pair.of(123634, 78036));
        map.put("TREC/CHI2/64", Pair.of(122781, 77532));
        map.put("TREC/CHI2/128", Pair.of(121511, 75894));
        map.put("TREC/CHI2/256", Pair.of(117642, 73723));
        map.put("TREC/CHI2/512", Pair.of(102781, 62899));
        map.put("TREC/FD/8", Pair.of(5520, 1951));
        map.put("TREC/FD/16", Pair.of(5414, 1948));
        map.put("TREC/FD/32", Pair.of(5207, 1937));
        map.put("TREC/FD/64", Pair.of(5145, 1929));
        map.put("TREC/FD/128", Pair.of(5056, 1919));
        map.put("TREC/FD/256", Pair.of(4998, 1888));
        map.put("TREC/FD/512", Pair.of(4934, 1875));
        map.put("TREC/MI/8", Pair.of(9690, 2411));
        map.put("TREC/MI/16", Pair.of(7348, 2400));
        map.put("TREC/MI/32", Pair.of(5500, 2248));
        map.put("TREC/MI/64", Pair.of(5363, 1943));
        map.put("TREC/MI/128", Pair.of(5328, 1941));
        map.put("TREC/MI/256", Pair.of(5301, 1920));
        map.put("TREC/MI/512", Pair.of(5214, 1887));
        map.put("UNIFEI_2017/CHI2/8", Pair.of(352209, 508321));
        map.put("UNIFEI_2017/CHI2/16", Pair.of(348045, 503329));
        map.put("UNIFEI_2017/CHI2/32", Pair.of(347591, 502947));
        map.put("UNIFEI_2017/CHI2/64", Pair.of(346991, 502862));
        map.put("UNIFEI_2017/CHI2/128", Pair.of(345507, 502563));
        map.put("UNIFEI_2017/CHI2/256", Pair.of(337363, 498522));
        map.put("UNIFEI_2017/CHI2/512", Pair.of(304883, 449219));
        map.put("UNIFEI_2017/FD/8", Pair.of(2892, 10485));
        map.put("UNIFEI_2017/FD/16", Pair.of(2846, 10455));
        map.put("UNIFEI_2017/FD/32", Pair.of(2835, 10455));
        map.put("UNIFEI_2017/FD/64", Pair.of(2814, 10441));
        map.put("UNIFEI_2017/FD/128", Pair.of(2807, 10436));
        map.put("UNIFEI_2017/FD/256", Pair.of(2754, 10436));
        map.put("UNIFEI_2017/FD/512", Pair.of(2673, 10408));
        map.put("UNIFEI_2017/MI/8", Pair.of(53238, 75774));
        map.put("UNIFEI_2017/MI/16", Pair.of(16589, 34168));
        map.put("UNIFEI_2017/MI/32", Pair.of(16500, 34077));
        map.put("UNIFEI_2017/MI/64", Pair.of(2867, 10465));
        map.put("UNIFEI_2017/MI/128", Pair.of(2835, 10465));
        map.put("UNIFEI_2017/MI/256", Pair.of(2795, 10436));
        map.put("UNIFEI_2017/MI/512", Pair.of(2760, 10436));

        return map;
    }

    public static Pair<Integer, Integer> get(String folder)
    {
        String key = folder;

        for (Entry<String, Pair<Integer, Integer>> entry : hashMap.entrySet())
            if (key.contains(entry.getKey()))
                return entry.getValue();

        return null;
    }

    public static Pair<Integer, Integer> get(String dataSet, String statMethod, Integer featureAmount)
    {
        String key = dataSet + "/" + statMethod + "/" + featureAmount;

        for (Entry<String, Pair<Integer, Integer>> entry : hashMap.entrySet())
            if (key.contains(entry.getKey()))
                return entry.getValue();

        return null;
    }
}
