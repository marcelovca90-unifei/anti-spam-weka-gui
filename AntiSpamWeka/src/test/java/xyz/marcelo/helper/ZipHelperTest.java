/*******************************************************************************
 * Copyright (C) 2017 Marcelo Vinícius Cysneiros Aragão
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package xyz.marcelo.helper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZipHelperTest
{
    private File srcFile;
    private File destFile;

    @Before
    public void setUp() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        srcFile = new File(classLoader.getResource("iris.arff").getFile());
        destFile = new File(srcFile.getAbsolutePath() + ".copy");
        FileUtils.copyFile(srcFile, destFile);
    }

    @Test
    public void constructor_shouldReturnNotNullInstance()
    {
        assertThat(new ZipHelper(), notNullValue());
    }

    @Test
    public void isZipped_notZippedFile_shouldReturnFalse() throws IOException
    {
        Assert.assertThat(destFile.exists(), CoreMatchers.equalTo(Boolean.TRUE));
        Assert.assertThat(ZipHelper.isZipped(destFile.getAbsolutePath()), CoreMatchers.equalTo(Boolean.FALSE));
    }

    @Test
    public void compress_shouldCompressAndDeleteUncompressedFile() throws IOException, ArchiveException
    {
        File zippedFile = ZipHelper.compress(destFile.getAbsolutePath());

        Assert.assertThat(zippedFile.exists(), CoreMatchers.equalTo(Boolean.TRUE));
        Assert.assertThat(ZipHelper.isZipped(zippedFile), CoreMatchers.equalTo(Boolean.TRUE));
        Assert.assertThat(destFile.exists(), CoreMatchers.equalTo(Boolean.FALSE));
    }

    @Test
    public void extract_shouldExtractAndDeleteCompressedFile() throws IOException, ArchiveException
    {
        File zippedFile = ZipHelper.compress(destFile);
        File extractedFile = ZipHelper.extract(zippedFile);

        // TODO investigate the assertions below

        Assert.assertThat(zippedFile.exists(), CoreMatchers.equalTo(Boolean.FALSE));
        // Assert.assertThat(extractedFile.exists(), CoreMatchers.equalTo(Boolean.TRUE));
        // Assert.assertThat(ZipHelper.isZipped(extractedFile), CoreMatchers.equalTo(Boolean.FALSE));
        // Assert.assertThat(destFile.exists(), CoreMatchers.equalTo(Boolean.TRUE));
    }
}
