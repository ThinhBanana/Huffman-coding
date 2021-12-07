
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
public class Validate {

    Scanner sc = new Scanner(System.in);

    public String checkInputString() {
        while (true) {
            String result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.err.println("Not empty");
                System.out.print("Enter again: ");
            } else {
                return result;
            }
        }
    }

    public String checkChoice() {
        while (true) {
            String result = checkInputString();
            if (result.equalsIgnoreCase("1")
                    || result.equalsIgnoreCase("2")
                    || result.equalsIgnoreCase("3")) {
                return result;
            }
            System.err.println("Please choose between 1,2 or 3");
            System.out.print("Enter again: ");
        }
    }

    public String checkFileName() {
        while (true) {
            String result = checkInputString();
            if (result.contains(".txt")) {
                return result;
            }
            System.err.println("File name must have '.txt' format");
            System.out.print("Enter again: ");
        }
    }

    public String checkFileInput() {
        while (true) {
            String result = checkInputString();
            if (result.contains(".txt")) {
                return result;
            }
            System.err.println("File name must have '.txt' format");
            System.out.print("Enter again: ");
        }
    }
}
