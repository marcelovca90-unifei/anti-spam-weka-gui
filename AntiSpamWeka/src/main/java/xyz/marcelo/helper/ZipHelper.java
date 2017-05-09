package xyz.marcelo.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

public final class ZipHelper
{
    public static boolean isZipped(String filename) throws IOException
    {
        // try to create a zip input stream for the given filename
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);

        // if there is an entry, then it is a zip file
        boolean isZipped = (zipInputStream.getNextEntry() != null);

        // close the streams
        zipInputStream.close();
        fileInputStream.close();

        return isZipped;
    }

    public static void compress(String filename) throws IOException, ArchiveException
    {
        // create the output zip file
        File outputFile = new File(filename + ".zip");

        // create the stream to the zip file and add
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        ArchiveOutputStream archiveOutputStream = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, fileOutputStream);

        // create the zip entry and it it to the zip file
        File inputFile = new File(filename);
        ZipArchiveEntry entry = new ZipArchiveEntry(inputFile.getName());
        archiveOutputStream.putArchiveEntry(entry);

        // copy the input file content to the zip entry
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(inputFile));
        IOUtils.copy(inputStream, archiveOutputStream);

        // close the streams and finish the zip file
        inputStream.close();
        archiveOutputStream.closeArchiveEntry();
        archiveOutputStream.finish();
        fileOutputStream.close();

        // delete the input file so that only the zip will remain
        inputFile.delete();
    }

    public static void extract(String filename) throws ZipException, IOException
    {
        // opens the zip file for the given filename
        File inputFile = new File(filename);
        ZipFile zipFile = new ZipFile(inputFile);
        ZipArchiveEntry archiveEntry = zipFile.getEntries().nextElement();
        InputStream inputStream = zipFile.getInputStream(archiveEntry);
        byte[] content = IOUtils.toByteArray(inputStream);
        zipFile.close();

        // reads the zip file's first entry's content and write it to the buffer
        FileUtils.writeByteArrayToFile(new File(filename.replace("\\.zip", "")), content);

        // delete the input file so that only the zip will remain
        inputFile.delete();
    }
}
