package pl.ppkwu.lab;


import pl.ppkwu.lab.api.ChecksumAlgorithm;
import pl.ppkwu.lab.api.EncryptionKey;
import pl.ppkwu.lab.api.EncryptionLibrary;
import pl.ppkwu.lab.api.HttpZipLibrary;
import pl.ppkwu.lab.impl.EncryptionLibraryImpl;
import pl.ppkwu.lab.impl.HttpZipLibraryImpl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class App {

    private static final String KEY = "ABCDEFghijkl1234";

    private static EncryptionLibrary encryptionLibrary;
    private static HttpZipLibrary httpZipLibrary;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        encryptionLibrary = new EncryptionLibraryImpl(new EncryptionKey(KEY));
        httpZipLibrary = new HttpZipLibraryImpl();

        do {
            System.out.println("Rodzaj operacji");
            System.out.println(menu());
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
                case "4":
                    handleZipFile();
                    break;
                case "5":
                    handleZipDirectory();
                    break;
                case "6":
                    handleUnzipFile();
                    break;
                case "7":
                    handleDownloadFile();
                    break;
                default:
                    System.out.println("Nieznana operacja");
            }
        } while (!input("Kontynuowac? [t/n]").equalsIgnoreCase("n"));
    }

    private static void handleDownloadFile() {
        System.out.println("Wybrano pobieranie pliku");
        String url = input("Adres pliku / strony");
        if (!url.toLowerCase().contains("http://")) {
            url = "http://" + url;
        }

        String outputFile = input("Plik wyjsciowy");

        URL urlAddress = null;
        try {
            urlAddress = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("Podano bledny url");
        }

        httpZipLibrary.downloadFile(urlAddress, new File(outputFile));

        System.out.println("Pobrano plik");
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

        System.out.println("Plik zostal odszyfrowany");
    }

    private static void handleEncryptFile() {
        System.out.println("Wybrano szyfrowanie pliku");
        String inputFile = input("Plik wejsciowy");
        String outputFile = input("Plik wyjsciowy");
        encryptionLibrary.encryptFile(new File(inputFile), new File(outputFile));

        System.out.println("Plik zostal zaszyfrowany");
    }

    private static void handleZipDirectory() {
        System.out.println("Wybrano kompresje folderu");
        String inputDirectory = input("Folder do skompresowania");
        String outputFile = input("Plik wyjsciowy (.zip)");

        httpZipLibrary.unzipFile(new File(inputDirectory), new File(outputFile));

        System.out.println("Folder zostal skompresowany");
    }

    private static void handleZipFile() {
        System.out.println("Wybrano kompresje pliku");
        String inputFile = input("Plik do skompresowania");
        String outputFile = input("Plik wyjsciowy (.zip)");

        httpZipLibrary.zipFile(new File(inputFile), new File(outputFile));

        System.out.println("Plik zostal skompresowany");
    }

    private static void handleUnzipFile() {
        System.out.println("Wybrano dekompresje pliku");
        String inputFile = input("Plik wejsciowy");
        String outputDirectory = input("Folder wyjsciowy");

        httpZipLibrary.unzipFile(new File(inputFile), new File(outputDirectory));

        System.out.println("Plik zostal odkompresowany");
    }

    private static String input(String name) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("(" + name + "):: ");
        String input = scanner.nextLine();
        return input;
    }

    private static String menu() {
        return  "1 - Szyfrowanie\n" +
                "2 - Deszyfrowanie\n" +
                "3 - Suma kontrolna\n" +
                "4 - Kompresja pliku\n" +
                "5 - Kompresja folderu\n" +
                "6 - Dekompresja pliku\n" +
                "7 - Pobranie pliku (http)";
    }
}
