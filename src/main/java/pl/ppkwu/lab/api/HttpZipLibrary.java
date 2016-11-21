package pl.ppkwu.lab.api;

import java.io.File;
import java.net.URL;

public interface HttpZipLibrary {

    void downloadFile(URL url, File outFile);

    void zipFile(File inFile, File outFile);

    void unzipFile(File zippedFile, File unzippedFile);
}
