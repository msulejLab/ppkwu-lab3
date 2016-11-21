package pl.ppkwu.lab.api;

import java.io.File;

public interface EncryptionLibrary {

    void enryptFile(File inputFile, File outputFile);

    void decryptFile(File inputFile, File outputFile);

    void setEnryptionKey(EncryptionKey encryptionKey);
}
