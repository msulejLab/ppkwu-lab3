package pl.ppkwu.lab;


import pl.ppkwu.lab.api.ChecksumAlgorithm;
import pl.ppkwu.lab.api.EncryptionKey;
import pl.ppkwu.lab.api.EncryptionLibrary;
import pl.ppkwu.lab.api.HttpZipLibrary;
import pl.ppkwu.lab.impl.EncryptionLibraryImpl;
import pl.ppkwu.lab.impl.HttpZipLibraryImpl;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class App {

    private static final String KEY = "ABCDEFghijkl1234";
    private static final String MENU =   "1 - Szyfrowanie\n2 - Deszyfrowanie\n3 - Suma kontrolna";

    private static EncryptionLibrary encryptionLibrary;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        encryptionLibrary = new EncryptionLibraryImpl(new EncryptionKey(KEY));

        do {
            System.out.println("Rodzaj operacji");
            System.out.println(MENU);
            String operation = input("");
            
            switch (operation) {
                case "1":
                    handleEncryptFile();
                    break;
                case "2":
                    handleDecryptFile();
                    break;
                case "3":
                    handleChecksumFile();
                    break;
                default:
                    System.out.println("Nieznana operacja");
            }
        } while (!input("Kontynuowac? [t/n]").equalsIgnoreCase("n"));
    }

    private static void handleChecksumFile() {
        System.out.println("Obliczanie sumy kontrolnej");
        String file = input("Nazwa pliku");
        String algorithmInput = input("Algorytm [MD5/SHA]");
        ChecksumAlgorithm algorithm = algorithmInput.equalsIgnoreCase("SHA")?
            ChecksumAlgorithm.SHA1 : ChecksumAlgorithm.MD5;
        String checksum = encryptionLibrary.fileChecksum(new File(file), algorithm);
        System.out.println("Suma kontrolna pliku: " + checksum);
    }

    private static void handleDecryptFile() {
        System.out.println("Wybrano odszyfrowanie pliku");
        String inputFile = input("Plik wejsciowy");
        String outputFile = input("Plik wyjsciowy");
        encryptionLibrary.decryptFile(new File(inputFile), new File(outputFile));
    }

    private static void handleEncryptFile() {
        System.out.println("Wybrano szyfrowanie pliku");
        String inputFile = input("Plik wejsciowy");
        String outputFile = input("Plik wyjsciowy");
        encryptionLibrary.encryptFile(new File(inputFile), new File(outputFile));
    }

    private static String input(String name) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("(" + name + "):: ");
        String input = scanner.nextLine();
        return input;
    }
}
