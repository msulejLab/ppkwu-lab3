package pl.ppkwu.lab.impl;

import pl.ppkwu.lab.api.HttpZipLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class HttpZipLibraryImpl implements HttpZipLibrary {

    @Override
    public void downloadFile(URL url, File outFile) {
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(outFile.getPath());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            throw new RuntimeException("Error while downloading file");
        }
    }

    @Override
    public void zipFile(File unzippedFile, File zippedFile) {

    }

    @Override
    public void zipDirectory(File unzippedDirectory, File zippedDirectory) {

    }

    @Override
    public void unzipFile(File zippedFile, File unzippedFile) {

    }
}
