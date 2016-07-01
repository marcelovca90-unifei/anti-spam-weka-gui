/**
 * 
 */
package xyz.marcelo.constant;

import java.io.File;

/**
 * The Class Folders.
 * 
 * @author marcelovca90
 */
public class Folders
{
	/** The path separator (OS dependent). */
	public static final String SLASH = File.separator;
	
	/** The parent folder for the data set folders. */
	public static final String BASE_FOLDER = "/home/marcelo/Mestrado/Vectors";

	/** The warm up folder tag. */
	public static final String WARMUP_TAG = "WarmUp";

	/** WarmUp data set folders. */
	public static final String[] FOLDERS_WARMUP = new String[] {
			BASE_FOLDER + "" + SLASH + "WarmUp" + SLASH + "Ling_2016_CHI2_10",
			BASE_FOLDER + "" + SLASH + "WarmUp" + SLASH + "SpamAssassin_2016_CHI2_10",
			BASE_FOLDER + "" + SLASH + "WarmUp" + SLASH + "TREC-2005-2006-2007_2016_CHI2_10",
			BASE_FOLDER + "" + SLASH + "WarmUp" + SLASH + "Unifei_2016_CHI2_10" };

	/** LingSpam data set folders. */
	public static final String[] FOLDERS_LING = new String[] {
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "100" };

	/** SpamAssassin data set folders. */
	public static final String[] FOLDERS_SPAMASSASSIN = new String[] {
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "100" };

	/** Trec data set folders. */
	public static final String[] FOLDERS_TREC = new String[] {
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "100" };

	/** Unifei data set folders. */
	public static final String[] FOLDERS_UNIFEI = new String[] {
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "100" };

	public static final String[] FOLDERS_CHI2 = new String[] {

			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "CHI2" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "CHI2" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "CHI2" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "CHI2" + SLASH + "100" };

	public static final String[] FOLDERS_FD = new String[] {

			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "FD" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "FD" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "FD" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "FD" + SLASH + "100" };

	public static final String[] FOLDERS_MI = new String[] {

			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Ling_2016" + SLASH + "MI" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "SpamAssassin_2016" + SLASH + "MI" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "TREC-2005-2006-2007_2016" + SLASH + "MI" + SLASH + "100",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "10",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "20",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "30",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "40",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "50",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "60",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "70",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "80",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "90",
			BASE_FOLDER + "" + SLASH + "Unifei_2016" + SLASH + "MI" + SLASH + "100" };

}