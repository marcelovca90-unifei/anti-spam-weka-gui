/**
 * 
 */
package xyz.marcelovca90.common;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author marcelovca90
 * 
 */
public class Folders {

	protected static final String ROOT = "/home/marcelovca90/Mestrado/Vectors";

	public static final String[] LINGSPAM = new String[] {
			ROOT + "/Ling_2016/CHI2/10", ROOT + "/Ling_2016/CHI2/20",
			ROOT + "/Ling_2016/CHI2/30", ROOT + "/Ling_2016/CHI2/40",
			ROOT + "/Ling_2016/CHI2/50", ROOT + "/Ling_2016/CHI2/60",
			ROOT + "/Ling_2016/CHI2/70", ROOT + "/Ling_2016/CHI2/80",
			ROOT + "/Ling_2016/CHI2/90", ROOT + "/Ling_2016/CHI2/100",
			ROOT + "/Ling_2016/FD/10", ROOT + "/Ling_2016/FD/20",
			ROOT + "/Ling_2016/FD/30", ROOT + "/Ling_2016/FD/40",
			ROOT + "/Ling_2016/FD/50", ROOT + "/Ling_2016/FD/60",
			ROOT + "/Ling_2016/FD/70", ROOT + "/Ling_2016/FD/80",
			ROOT + "/Ling_2016/FD/90", ROOT + "/Ling_2016/FD/100",
			ROOT + "/Ling_2016/MI/10", ROOT + "/Ling_2016/MI/20",
			ROOT + "/Ling_2016/MI/30", ROOT + "/Ling_2016/MI/40",
			ROOT + "/Ling_2016/MI/50", ROOT + "/Ling_2016/MI/60",
			ROOT + "/Ling_2016/MI/70", ROOT + "/Ling_2016/MI/80",
			ROOT + "/Ling_2016/MI/90", ROOT + "/Ling_2016/MI/100" };

	public static final String[] SPAMASSASSIN = new String[] {
			ROOT + "/SpamAssassin_2016/CHI2/10",
			ROOT + "/SpamAssassin_2016/CHI2/20",
			ROOT + "/SpamAssassin_2016/CHI2/30",
			ROOT + "/SpamAssassin_2016/CHI2/40",
			ROOT + "/SpamAssassin_2016/CHI2/50",
			ROOT + "/SpamAssassin_2016/CHI2/60",
			ROOT + "/SpamAssassin_2016/CHI2/70",
			ROOT + "/SpamAssassin_2016/CHI2/80",
			ROOT + "/SpamAssassin_2016/CHI2/90",
			ROOT + "/SpamAssassin_2016/CHI2/100",
			ROOT + "/SpamAssassin_2016/FD/10",
			ROOT + "/SpamAssassin_2016/FD/20",
			ROOT + "/SpamAssassin_2016/FD/30",
			ROOT + "/SpamAssassin_2016/FD/40",
			ROOT + "/SpamAssassin_2016/FD/50",
			ROOT + "/SpamAssassin_2016/FD/60",
			ROOT + "/SpamAssassin_2016/FD/70",
			ROOT + "/SpamAssassin_2016/FD/80",
			ROOT + "/SpamAssassin_2016/FD/90",
			ROOT + "/SpamAssassin_2016/FD/100",
			ROOT + "/SpamAssassin_2016/MI/10",
			ROOT + "/SpamAssassin_2016/MI/20",
			ROOT + "/SpamAssassin_2016/MI/30",
			ROOT + "/SpamAssassin_2016/MI/40",
			ROOT + "/SpamAssassin_2016/MI/50",
			ROOT + "/SpamAssassin_2016/MI/60",
			ROOT + "/SpamAssassin_2016/MI/70",
			ROOT + "/SpamAssassin_2016/MI/80",
			ROOT + "/SpamAssassin_2016/MI/90",
			ROOT + "/SpamAssassin_2016/MI/100" };

	public static final String[] TREC = new String[] {
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/10",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/20",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/30",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/40",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/50",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/60",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/70",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/80",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/90",
			ROOT + "/TREC-2005-2006-2007_2016/CHI2/100",
			ROOT + "/TREC-2005-2006-2007_2016/FD/10",
			ROOT + "/TREC-2005-2006-2007_2016/FD/20",
			ROOT + "/TREC-2005-2006-2007_2016/FD/30",
			ROOT + "/TREC-2005-2006-2007_2016/FD/40",
			ROOT + "/TREC-2005-2006-2007_2016/FD/50",
			ROOT + "/TREC-2005-2006-2007_2016/FD/60",
			ROOT + "/TREC-2005-2006-2007_2016/FD/70",
			ROOT + "/TREC-2005-2006-2007_2016/FD/80",
			ROOT + "/TREC-2005-2006-2007_2016/FD/90",
			ROOT + "/TREC-2005-2006-2007_2016/FD/100",
			ROOT + "/TREC-2005-2006-2007_2016/MI/10",
			ROOT + "/TREC-2005-2006-2007_2016/MI/20",
			ROOT + "/TREC-2005-2006-2007_2016/MI/30",
			ROOT + "/TREC-2005-2006-2007_2016/MI/40",
			ROOT + "/TREC-2005-2006-2007_2016/MI/50",
			ROOT + "/TREC-2005-2006-2007_2016/MI/60",
			ROOT + "/TREC-2005-2006-2007_2016/MI/70",
			ROOT + "/TREC-2005-2006-2007_2016/MI/80",
			ROOT + "/TREC-2005-2006-2007_2016/MI/90",
			ROOT + "/TREC-2005-2006-2007_2016/MI/100" };

	public static final String[] UNIFEI = new String[] {
			ROOT + "/Unifei_2016/CHI2/10", ROOT + "/Unifei_2016/CHI2/20",
			ROOT + "/Unifei_2016/CHI2/30", ROOT + "/Unifei_2016/CHI2/40",
			ROOT + "/Unifei_2016/CHI2/50", ROOT + "/Unifei_2016/CHI2/60",
			ROOT + "/Unifei_2016/CHI2/70", ROOT + "/Unifei_2016/CHI2/80",
			ROOT + "/Unifei_2016/CHI2/90", ROOT + "/Unifei_2016/CHI2/100",
			ROOT + "/Unifei_2016/FD/10", ROOT + "/Unifei_2016/FD/20",
			ROOT + "/Unifei_2016/FD/30", ROOT + "/Unifei_2016/FD/40",
			ROOT + "/Unifei_2016/FD/50", ROOT + "/Unifei_2016/FD/60",
			ROOT + "/Unifei_2016/FD/70", ROOT + "/Unifei_2016/FD/80",
			ROOT + "/Unifei_2016/FD/90", ROOT + "/Unifei_2016/FD/100",
			ROOT + "/Unifei_2016/MI/10", ROOT + "/Unifei_2016/MI/20",
			ROOT + "/Unifei_2016/MI/30", ROOT + "/Unifei_2016/MI/40",
			ROOT + "/Unifei_2016/MI/50", ROOT + "/Unifei_2016/MI/60",
			ROOT + "/Unifei_2016/MI/70", ROOT + "/Unifei_2016/MI/80",
			ROOT + "/Unifei_2016/MI/90", ROOT + "/Unifei_2016/MI/100" };

	public static final String[] ALL = (String[]) ArrayUtils.addAll(
			ArrayUtils.addAll(Folders.LINGSPAM, Folders.SPAMASSASSIN),
			ArrayUtils.addAll(Folders.TREC, Folders.UNIFEI));

}
