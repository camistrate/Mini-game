package org.example;

import org.example.graphicInterface.LoginPage;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        System.out.println("Select the type of game:");
        System.out.println("1. Command Line Game");
        System.out.println("2. Graphical User Interface Game");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.print("Invalid choice. Please enter 1 or 2: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }

        if (choice == 1) {
            Game game = Game.getInstance();
            game.run();
        } else {
            new LoginPage();
        }
    }
}
