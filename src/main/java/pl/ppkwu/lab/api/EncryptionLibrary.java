package pl.ppkwu.lab.api;

import java.io.File;

public interface EncryptionLibrary {

    void enryptFile(File inputFile, File outputFile);

    void decryptFile(File inputFile, File outputFile);

    String fileChecksum(File file, ChecksumAlgorithm checksumAlgorithm);

    void setEnryptionKey(EncryptionKey encryptionKey);
}
