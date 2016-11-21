package pl.ppkwu.lab.impl;

import pl.ppkwu.lab.api.HttpZipLibrary;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(zippedFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            if (unzippedFile.isFile()) {
                String folder = zippedFile.getName().substring(0, zippedFile.getName().indexOf(".zip"));
                zipFileInDirectory(unzippedFile, folder, zipOutputStream);
            }

            zipOutputStream.closeEntry();
            zipOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void zipDirectory(File unzippedDirectory, File zippedDirectory) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zippedDirectory);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            File[] contents = unzippedDirectory.listFiles();
            for (File f : contents) {
                if (f.isFile()) {
                    zipFileInDirectory(f, unzippedDirectory.getName(), zipOutputStream);
                }
            }

            zipOutputStream.closeEntry();
            zipOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unzipFile(File zippedFile, File unzippedFile) {

    }

    private void zipFileInDirectory(File inputFile, String parentName, ZipOutputStream zipOutputStream) {
        try {
            ZipEntry zipEntry = new ZipEntry(parentName + "\\" + inputFile.getName());
            zipOutputStream.putNextEntry(zipEntry);


            FileInputStream fileInputStream = new FileInputStream(inputFile);
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, bytesRead);
            }

            zipOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
