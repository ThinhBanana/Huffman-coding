import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tien Thinh
 */
public class Menu {

    Scanner sc = new Scanner(System.in);
    HuffmanCoding huffman = new HuffmanCoding();
    Validate valid = new Validate();

    public String loadDataToEncode(String fileName) throws IOException {
        String str = "";
        Path filePath = Paths.get(fileName);
        Charset charset = StandardCharsets.UTF_8;
        long count = Files.lines(filePath).count();
        while (true) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
                String line;
                for (int i = 0; i < count; i++) {
                    line = bufferedReader.readLine();
                    if (i == count - 1) {
                        str = str + line;
                    } else {
                        str = str + line + "\n";
                    }
                }
                System.out.print("Original file size: " + String.valueOf(FileSize(fileName)) + " bytes\n");
                return str;
            } catch (IOException ex) {
                System.err.format("I/O error: %s%n", ex);
                System.out.print("Enter again:");
                fileName = valid.checkFileName();
                filePath = Paths.get(fileName);
                count = Files.lines(filePath).count();
            }
        }

    }

    public String loadDataToDecode(String fileName) throws IOException {
        String str = "";
        //String fileName = valid.checkFileName();
        Path filePath = Paths.get(fileName);
        //Charset charset = StandardCharsets.UTF_8;
        while (true) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    str = str + line;
                }
                System.out.print("Original file size: " + String.valueOf(FileSize(fileName)) + " bytes\n");
                return str;
            } catch (IOException ex) {
                System.err.format("I/O error: %s%n", ex);
                System.out.print("Enter again: ");
                fileName = valid.checkFileName();
                filePath = Paths.get(fileName);
            }
        }

    }

    public String CreateFile() throws IOException {
//        System.out.print("Enter file name: ");
        String filename = valid.checkFileName();
        while (true) {
            try {
                File newFile = new File(filename);
                if (newFile.createNewFile()) {
                    System.out.println("\nFile created: " + newFile.getName());
                    return filename;
                } else {
                    System.err.println("File already exists.");
                    System.out.print("Enter again: ");
                    filename = valid.checkFileName();
                }
            } catch (IOException e) {
                System.err.println("An error occurred.");
            }
        }
    }

    public void writeFileDecode(String fileName, String decode_Content) throws IOException {
        File newFile = new File(fileName);
        decode_Content = decode_Content.replaceAll("\\n", "\r\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(decode_Content);
            System.out.println("Successfully wrote to the file.");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    
    public void writeFileEncode(String fileName, String decode_Content) throws IOException {
        File newFile = new File(fileName);
        decode_Content = decode_Content.replaceAll("\\n", "\r\n");
        long lct = Long.parseLong(decode_Content, 2); 
        int ict = (int) lct;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(ict);
            System.out.println("Successfully wrote to the file.");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public double FileSize(String fileName) {
        File file = new File(fileName);
        return file.length();
    }

    public void Menu() throws IOException {
        String choice;
        do {
            System.out.println("WELLCOME TO HUFFMAN CODING");
            System.out.println("1. Huffman encoding");
            System.out.println("2. Huffman decoding");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = valid.checkChoice();
            switch (choice) {
                case "1": {
                    System.out.print("Enter the file name want to encoded: ");
                    String fileName = valid.checkFileInput();
                    String data = loadDataToEncode(fileName);

                    System.out.println("--- Encode succeed ---");
                    String encodeData = huffman.encode(data);

                    System.out.print("\nEnter the file new file name: ");
                    String newFile = CreateFile();
                    writeFileEncode(newFile, encodeData);

                    System.out.println("New file size: " + String.valueOf(FileSize(newFile)) + " bytes\n");
                    break;
                }
                case "2": {
                    System.out.print("Enter the file name want to decoded: ");
                    String fileName, data, decodeData;
                    decodeData = "";
                    do {
                        fileName = valid.checkFileInput();
                        data = loadDataToDecode(fileName);
                        try {
                            decodeData = huffman.decode(data);
                        } catch (Exception e) {
                            System.out.println("Can not decode this file");
                            System.out.println("Enter again: ");
                        }
                    } while (decodeData.isEmpty());
                    System.out.println(decodeData);

                    System.out.print("----------------------\nEnter the file new file name: ");
                    String newFile = CreateFile();
                    writeFileDecode(newFile, decodeData);

                    System.out.println("New file size: " + String.valueOf(FileSize(newFile)) + " bytes\n");
                    break;
                }
                default: {
                    System.out.println("Goodbye. See you again !!!");
                }
            }

        } while (!"3".equals(choice));
    }

}
