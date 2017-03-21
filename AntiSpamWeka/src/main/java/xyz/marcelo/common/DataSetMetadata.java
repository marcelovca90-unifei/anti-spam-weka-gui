package xyz.marcelo.common;

public class DataSetMetadata
{
    private String folder;
    private int emptyHamCount;
    private int emptySpamCount;

    public String getFolder()
    {
        return folder;
    }

    public int getEmptyHamCount()
    {
        return emptyHamCount;
    }

    public int getEmptySpamCount()
    {
        return emptySpamCount;
    }

    public DataSetMetadata(String folder, int emptyHamCount, int emptySpamCount)
    {
        super();
        this.folder = folder;
        this.emptyHamCount = emptyHamCount;
        this.emptySpamCount = emptySpamCount;
    }

    @Override
    public String toString()
    {
        return "DataSetMetadata [folder=" + folder + ", emptyHamCount=" + emptyHamCount + ", emptySpamCount=" + emptySpamCount + "]";
    }
}
