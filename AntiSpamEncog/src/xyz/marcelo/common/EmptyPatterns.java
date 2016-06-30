package xyz.marcelo.common;

import java.util.HashMap;

/**
 * The Class EmptyPatterns.
 * 
 * @author marcelovca90
 */
public class EmptyPatterns
{
	/** The map. */
	private static HashMap<String, int[]> map = new HashMap<String, int[]>();

	/**
	 * Gets the the amount of empty hams and spams for a given data set folder.
	 * 
	 * @param folder
	 *            the data set folder
	 * @return the amount of [0] empty hams and [1] spams
	 */
	public static int[] get(String folder)
	{

		if (map.size() == 0)
		{

			map.put("/Ling_2016/CHI2/10", new int[] { 6563, 1407 });
			map.put("/Ling_2016/CHI2/20", new int[] { 6537, 1395 });
			map.put("/Ling_2016/CHI2/30", new int[] { 6518, 1383 });
			map.put("/Ling_2016/CHI2/40", new int[] { 6492, 1373 });
			map.put("/Ling_2016/CHI2/50", new int[] { 6469, 1365 });
			map.put("/Ling_2016/CHI2/60", new int[] { 6447, 1361 });
			map.put("/Ling_2016/CHI2/70", new int[] { 6418, 1353 });
			map.put("/Ling_2016/CHI2/80", new int[] { 6385, 1343 });
			map.put("/Ling_2016/CHI2/90", new int[] { 6357, 1333 });
			map.put("/Ling_2016/CHI2/100", new int[] { 6324, 1325 });
			map.put("/Ling_2016/FD/10", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/20", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/30", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/40", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/50", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/60", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/70", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/80", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/90", new int[] { 0, 0 });
			map.put("/Ling_2016/FD/100", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/10", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/20", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/30", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/40", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/50", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/60", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/70", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/80", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/90", new int[] { 0, 0 });
			map.put("/Ling_2016/MI/100", new int[] { 0, 0 });
			map.put("/SpamAssassin_2016/CHI2/10", new int[] { 177, 223 });
			map.put("/SpamAssassin_2016/CHI2/20", new int[] { 159, 197 });
			map.put("/SpamAssassin_2016/CHI2/30", new int[] { 146, 180 });
			map.put("/SpamAssassin_2016/CHI2/40", new int[] { 130, 171 });
			map.put("/SpamAssassin_2016/CHI2/50", new int[] { 119, 166 });
			map.put("/SpamAssassin_2016/CHI2/60", new int[] { 112, 164 });
			map.put("/SpamAssassin_2016/CHI2/70", new int[] { 105, 164 });
			map.put("/SpamAssassin_2016/CHI2/80", new int[] { 100, 157 });
			map.put("/SpamAssassin_2016/CHI2/90", new int[] { 95, 152 });
			map.put("/SpamAssassin_2016/CHI2/100", new int[] { 95, 150 });
			map.put("/SpamAssassin_2016/FD/10", new int[] { 0, 14 });
			map.put("/SpamAssassin_2016/FD/20", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/30", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/40", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/50", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/60", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/70", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/80", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/90", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/FD/100", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/10", new int[] { 0, 18 });
			map.put("/SpamAssassin_2016/MI/20", new int[] { 0, 17 });
			map.put("/SpamAssassin_2016/MI/30", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/40", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/50", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/60", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/70", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/80", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/90", new int[] { 0, 3 });
			map.put("/SpamAssassin_2016/MI/100", new int[] { 0, 3 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/10", new int[] { 79197, 124419 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/20", new int[] { 78073, 123752 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/30", new int[] { 77990, 123649 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/40", new int[] { 77909, 123545 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/50", new int[] { 77660, 122822 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/60", new int[] { 77525, 122787 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/70", new int[] { 77393, 122750 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/80", new int[] { 77054, 122433 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/90", new int[] { 77037, 122260 });
			map.put("/TREC-2005-2006-2007_2016/CHI2/100", new int[] { 77026, 122088 });
			map.put("/TREC-2005-2006-2007_2016/FD/10", new int[] { 1953, 6913 });
			map.put("/TREC-2005-2006-2007_2016/FD/20", new int[] { 1948, 5409 });
			map.put("/TREC-2005-2006-2007_2016/FD/30", new int[] { 1947, 5214 });
			map.put("/TREC-2005-2006-2007_2016/FD/40", new int[] { 1937, 5205 });
			map.put("/TREC-2005-2006-2007_2016/FD/50", new int[] { 1933, 5194 });
			map.put("/TREC-2005-2006-2007_2016/FD/60", new int[] { 1931, 5166 });
			map.put("/TREC-2005-2006-2007_2016/FD/70", new int[] { 1928, 5140 });
			map.put("/TREC-2005-2006-2007_2016/FD/80", new int[] { 1928, 5118 });
			map.put("/TREC-2005-2006-2007_2016/FD/90", new int[] { 1921, 5101 });
			map.put("/TREC-2005-2006-2007_2016/FD/100", new int[] { 1921, 5093 });
			map.put("/TREC-2005-2006-2007_2016/MI/10", new int[] { 2411, 9152 });
			map.put("/TREC-2005-2006-2007_2016/MI/20", new int[] { 2254, 6753 });
			map.put("/TREC-2005-2006-2007_2016/MI/30", new int[] { 2249, 6748 });
			map.put("/TREC-2005-2006-2007_2016/MI/40", new int[] { 1948, 6368 });
			map.put("/TREC-2005-2006-2007_2016/MI/50", new int[] { 1947, 6365 });
			map.put("/TREC-2005-2006-2007_2016/MI/60", new int[] { 1945, 6358 });
			map.put("/TREC-2005-2006-2007_2016/MI/70", new int[] { 1945, 6350 });
			map.put("/TREC-2005-2006-2007_2016/MI/80", new int[] { 1944, 6337 });
			map.put("/TREC-2005-2006-2007_2016/MI/90", new int[] { 1944, 6333 });
			map.put("/TREC-2005-2006-2007_2016/MI/100", new int[] { 1944, 6331 });
			map.put("/Unifei_2016/CHI2/10", new int[] { 3834, 14843 });
			map.put("/Unifei_2016/CHI2/20", new int[] { 3803, 14630 });
			map.put("/Unifei_2016/CHI2/30", new int[] { 3781, 14450 });
			map.put("/Unifei_2016/CHI2/40", new int[] { 3712, 14343 });
			map.put("/Unifei_2016/CHI2/50", new int[] { 3596, 14308 });
			map.put("/Unifei_2016/CHI2/60", new int[] { 3351, 13083 });
			map.put("/Unifei_2016/CHI2/70", new int[] { 3346, 12931 });
			map.put("/Unifei_2016/CHI2/80", new int[] { 3341, 12787 });
			map.put("/Unifei_2016/CHI2/90", new int[] { 3229, 12520 });
			map.put("/Unifei_2016/CHI2/100", new int[] { 3128, 12375 });
			map.put("/Unifei_2016/FD/10", new int[] { 17, 151 });
			map.put("/Unifei_2016/FD/20", new int[] { 17, 151 });
			map.put("/Unifei_2016/FD/30", new int[] { 16, 151 });
			map.put("/Unifei_2016/FD/40", new int[] { 16, 151 });
			map.put("/Unifei_2016/FD/50", new int[] { 16, 151 });
			map.put("/Unifei_2016/FD/60", new int[] { 16, 151 });
			map.put("/Unifei_2016/FD/70", new int[] { 16, 151 });
			map.put("/Unifei_2016/FD/80", new int[] { 16, 151 });
			map.put("/Unifei_2016/FD/90", new int[] { 16, 151 });
			map.put("/Unifei_2016/FD/100", new int[] { 16, 151 });
			map.put("/Unifei_2016/MI/10", new int[] { 26, 239 });
			map.put("/Unifei_2016/MI/20", new int[] { 21, 220 });
			map.put("/Unifei_2016/MI/30", new int[] { 21, 220 });
			map.put("/Unifei_2016/MI/40", new int[] { 16, 151 });
			map.put("/Unifei_2016/MI/50", new int[] { 16, 151 });
			map.put("/Unifei_2016/MI/60", new int[] { 16, 151 });
			map.put("/Unifei_2016/MI/70", new int[] { 16, 151 });
			map.put("/Unifei_2016/MI/80", new int[] { 16, 151 });
			map.put("/Unifei_2016/MI/90", new int[] { 16, 151 });
			map.put("/Unifei_2016/MI/100", new int[] { 16, 151 });
		}

		String folderSuffix = folder.replace(Folders.BASE_FOLDER, "");

		if (map.containsKey(folder))
			return map.get(folder);

		else if (map.containsKey(folderSuffix))
			return map.get(folderSuffix);

		else
			return null;
	}

}
