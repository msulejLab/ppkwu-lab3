package pl.ppkwu.lab.impl;

import pl.ppkwu.lab.api.ChecksumAlgorithm;
import pl.ppkwu.lab.api.EncryptionKey;
import pl.ppkwu.lab.api.EncryptionLibrary;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionLibraryImpl implements EncryptionLibrary {

    private EncryptionKey encryptionKey;

    public EncryptionLibraryImpl() {
        this(new EncryptionKey("ABCDEFghijkl1234"));
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
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(checksumAlgorithm.toString().toUpperCase());
            md.update(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting file checksum");
        }
    }

    @Override
    public void setEncryptionKey(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    private void doCrypto(int cipherMode, File inputFile, File outputFile) {
        try {
            Key secretKey = new SecretKeySpec(encryptionKey.getValue().getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
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
