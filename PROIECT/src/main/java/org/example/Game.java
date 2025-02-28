package org.example;

import org.example.characters.Characterrr;
import org.example.exceptions.InvalidCommandException;
import org.example.exceptions.InvalidMoveException;
import org.example.game.Grid;
import org.example.player.Account;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private ArrayList<Account> accounts;
    private static Grid board;
    private static Account currentAccount;
    private static Characterrr character;
    private static int level;

    // instanta unica
    private static Game instance;

    private Game() {
    }

    // metoda pt a obtine instanta unica
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Grid getBoard() {
        return board;
    }

    public int getLevel() {
        return level;
    }

    public static Characterrr returnCharacter() {
        return character;
    }

    private static String showMoves() {
        System.out.println("Available moves: ");
        if(canMoveUp())
            System.out.print("W (UP)   ");
        if(canMoveDown())
            System.out.print("S (DOWN)   ");
        if(canMoveLeft())
            System.out.print("A (LEFT)   ");
        if(canMoveRight())
            System.out.print("D (RIGHT)");
        System.out.println();
        System.out.println("Q (QUIT)");

        System.out.println();

        Scanner scanner = new Scanner(System.in);
        String move;

        // daca nu e un input corect sau daca e o mutare invalida
        while(true) {
            try {
                System.out.println("Enter your move: ");
                move = scanner.nextLine().toUpperCase();
                if ((!move.equals("Q") && !move.equals("W") && !move.equals("A") && !move.equals("S") && !move.equals("D")) ||
                        ((move.equals("W") && !canMoveUp()) || (move.equals("S") && !canMoveDown()) ||
                                (move.equals("A") && !canMoveLeft()) || (move.equals("D") && !canMoveRight()))) {
                    throw new InvalidMoveException("Invalid move.");
                }
                break;
            } catch (InvalidMoveException e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println("Invalid input type.");
                scanner.nextLine().toUpperCase();
            }
        }
        return move;
    }

    private static boolean canMoveUp () {
        return (board.currentX() > 0);
    }

    private static boolean canMoveDown () {
        return (board.currentX() < board.getM() - 1);
    }

    private static boolean canMoveLeft () {
        return (board.currentY() > 0);
    }

    private static boolean canMoveRight () {
        return (board.currentY() < board.getN() - 1);
    }

    public void run() {
        accounts = JsonInput.deserializeAccounts();

        for(Account account : accounts) {
            System.out.println(account.getInformation());
        }

        Scanner input = new Scanner(System.in);
        boolean emailExists = false;

        while (!emailExists) {
            System.out.print("Enter email: ");
            String email = input.nextLine();

            for (Account account : accounts) {
                if (account.getInformation().getEmail().equals(email)) {
                    emailExists = true;
                    currentAccount = account;
                }
            }

            if (!emailExists) {
                System.out.print("Email does not exist. Try again!");
            }
        }

        boolean correctPassword = false;
        while (!correctPassword) {
            System.out.print("Enter password: ");
            String password = input.nextLine();

            if(currentAccount.getInformation().getPassword().equals(password)) {
                correctPassword = true;
            }

            if (!correctPassword) {
                System.out.print("Incorrect password. Try again!");
            }
        }
        System.out.println("Autentification done.");
        getCharacter();
    }

    // alegerea caracterului
    public static void getCharacter() {
        Scanner input = new Scanner(System.in);
        System.out.println("Characters: ");
        for (int i = 1; i <= currentAccount.getNumberOfCharacters(); i++) {
            System.out.println(i + ") " + currentAccount.getCharacterName(i - 1));
        }

        String stringNumber;
        int number = -1;

        while (true) {
            try {
                System.out.print("Enter the number of the character or press Q to exit: ");
                stringNumber = input.nextLine().toUpperCase();
                if (stringNumber.equals("Q")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {
                    try {
                        number = Integer.parseInt(stringNumber);
                        if(number > currentAccount.getNumberOfCharacters() || number <= 0)
                            throw new InvalidCommandException("Invalid input.");
                        break;
                    } catch (InvalidCommandException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e){
                        System.out.println("Invalid input.");
                        input.nextLine();
                    }
                }
            } catch (Exception e){
                System.out.println("Invalid input.");
                input.nextLine();
            }
        }

        character = currentAccount.getCharacter(number - 1);
        System.out.println();
        System.out.println("Your character is: " + character.getName());
        System.out.println();
        System.out.println("***  WELCOME TO THE GAME!  ***");
        level = 1;
        play();
    }

    private static void play() {
        //PENTRU GENERARE HARTA HARDCODATA:
        //board = Grid.createGrid(5, 5, character);

        //PENTRU GENERARE HARTA RANDOM:
        Random r = new Random();
        board = Grid.createRandomGrid(4 + r.nextInt(6), 4 + r.nextInt(6), character);

        System.out.println("*** LEVEL: " + level + "  ***");
        System.out.println("Use W (UP), S (DOWN), A (LEFT), D (RIGHT) to move or Q to quit.");
        System.out.println();
        String move = "A";
        while (!move.equals("Q")) {
            board.showGrid();
            move = showMoves();
            board.makeMove(move);
        }
    }

    public static void finishedLevel(String answer) {
        //crestem numarul de jocuri jucate de player
        currentAccount.increasePlayedGames();
        //crestem level-ul
        level++;

        if(answer.equals("N")) {
            getCharacter();
        } else {
            play();
        }
    }
}
