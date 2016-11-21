package pl.ppkwu.lab.impl;

import pl.ppkwu.lab.api.ChecksumAlgorithm;
import pl.ppkwu.lab.api.EncryptionKey;
import pl.ppkwu.lab.api.EncryptionLibrary;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class EncryptionLibraryImpl implements EncryptionLibrary {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private EncryptionKey encryptionKey;

    public EncryptionLibraryImpl() {
        this.encryptionKey = new EncryptionKey("empty");
    }

    public EncryptionLibraryImpl(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Override
    public void encryptFile(File inputFile, File outputFile) {
        doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    @Override
    public void decryptFile(File inputFile, File outputFile) {
        doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    @Override
    public String fileChecksum(File file, ChecksumAlgorithm checksumAlgorithm) {
        return null;
    }

    @Override
    public void setEncryptionKey(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    private void doCrypto(int cipherMode, File inputFile, File outputFile) {
        try {
            Key secretKey = new SecretKeySpec(encryptionKey.getValue().getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting/decrypting file", e);
        }
    }
}
