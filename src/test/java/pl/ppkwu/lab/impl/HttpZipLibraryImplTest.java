package pl.ppkwu.lab.impl;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

public class HttpZipLibraryImplTest {

    HttpZipLibraryImpl httpZipLibrary;

    @Before
    public void setUp() throws Exception {
        httpZipLibrary = new HttpZipLibraryImpl();
    }

    @Test
    public void shouldDownloadFile() throws Exception {
        File file = new File("output/downloaded.txt");
        file.createNewFile();
        URL url = new URL("http://google.com");
        long i = file.length();
        httpZipLibrary.downloadFile(url,file);
        assertEquals("plik nie sciagniety",sizeofFileBiggerThanZero(file), true);
    }

    @Test
    public void shouldZipFile() throws Exception {
        File normalFile = new File("src/test/resources/normalfile.txt");
        File zipFile = new File("output/zip.zip");
        httpZipLibrary.zipFile(normalFile,zipFile);

        assertEquals("nie stworzono pliku",true,zipFile.exists());
    }

    @Test
    public void shouldZipDirectory() throws Exception {
        File filesDirectory = new File("src/test/resources/files");
        File zipFile = new File("output/35.zip");
        httpZipLibrary.zipDirectory(filesDirectory,zipFile);
        assertEquals("nie skompresowano",zipFile.exists(),true);
    }

    @Test
    public void shouldUnzipFile() throws Exception {
        File zippedFile = new File("src/test/resources/hello.zip");
        File unzippedFile = new File("hello");
        httpZipLibrary.unzipFile(zippedFile,unzippedFile);
        assertEquals("nie skompresowano",unzippedFile.exists(),true);
    }

    private boolean sizeofFileBiggerThanZero(File file){
        return file.length() > 0;
    }

    @AfterClass
    public static void clean() {
        File directory = new File("output");

        for(File file: directory.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }
}