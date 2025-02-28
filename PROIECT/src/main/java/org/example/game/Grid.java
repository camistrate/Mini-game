package org.example.game;

import org.example.characters.Characterrr;
import org.example.Game;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private static int m;  //numar linii
    private static int n;  // numar coloane

    public Characterrr character;
    private static Cell currentCell;
    private static CellEntityType oldCellType;

    private static Grid grid;

    private Grid(int m, int n, Characterrr character) {
        this.m = m;
        this.n = n;
        this.character = character;
    }

    public static Grid createGrid(int m, int n, Characterrr character) {
        grid = new Grid(m, n, character);
        //creare matrice
        for (int i = 0; i < 5; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }

        grid.get(0).set(0, new Cell(0, 0, CellEntityType.PLAYER));
        grid.get(0).set(0, new Cell(0, 0, CellEntityType.PLAYER));
        grid.get(0).set(3, new Cell(0, 3, CellEntityType.SANCTUARY));
        grid.get(1).set(3, new Cell(1, 3, CellEntityType.SANCTUARY));
        grid.get(2).set(0, new Cell(2, 0, CellEntityType.SANCTUARY));
        grid.get(3).set(4, new Cell(3, 4, CellEntityType.ENEMY));
        grid.get(4).set(3, new Cell(4, 3, CellEntityType.SANCTUARY));
        grid.get(4).set(4, new Cell(4, 4, CellEntityType.PORTAL));

        currentCell = grid.getCell(0, 0);

        return grid;
    }

    public static Grid createRandomGrid(int m, int n, Characterrr character) {
        grid = new Grid(m, n, character);
        generateGrid();
        return grid;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public Characterrr getCharacter() {
        return character;
    }

    public int currentX() {
        return currentCell.getX();
    }

    public int currentY() {
        return currentCell.getY();
    }

    public static Cell getCell(int x, int y) {
        if (grid.isEmpty()) {
            throw new IllegalStateException("Grid is not initialized.");
        }
        return grid.get(x).get(y);
    }

    public CellEntityType getOldType(){
        return oldCellType;
    }

    public void setOldType(){
        oldCellType = CellEntityType.PLAYER;
    }

    private static void generateGrid() {
        //creare matrice
        for (int i = 0; i < m; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }

        //initializarea campurilor esentiale din matrice
        generateCellType(CellEntityType.PLAYER, 1);
        generateCellType(CellEntityType.SANCTUARY, 2);
        generateCellType(CellEntityType.ENEMY, 4);
        generateCellType(CellEntityType.PORTAL, 1);

        //initializarea celorlalte campuri din matrice
        Random rand = new Random();
        int x = rand.nextInt(5);
        int y = rand.nextInt(5);

        //mai adaugam x elemente de type SANCTUARY
        generateCellType(CellEntityType.SANCTUARY, x);

        //mai adaugam y elemente de type ENEMY
        generateCellType(CellEntityType.ENEMY, y);
    }

    //metoda care genereaza nr celule de tip type
    private static void generateCellType(CellEntityType type, int nr) {
        Random rand = new Random();
        int x = rand.nextInt(m);
        int y = rand.nextInt(n);

        //generam nr celule random goale
        for(int i = 0; i < nr; i++) {
            if(hasVoid() == true) {
                while (grid.get(x).get(y).getType() != CellEntityType.VOID) {
                    x = rand.nextInt(m);
                    y = rand.nextInt(n);
                }

                // setam celula la type-ul dat
                grid.get(x).set(y, new Cell(x, y, type));

                if (type == CellEntityType.PLAYER) {
                    currentCell = getCell(x, y);
                }
            }
            else {  //altfel oprim for-ul
                i = nr;
            }
        }
    }

    //metoda care verifica daca mai exista celule void
    private static boolean hasVoid() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j).getType() == CellEntityType.VOID) {
                    return true;
                }
            }
        }
        return false;
    }

    public void makeMove(String move) {
        if(move.equals("A")) {
            goWest();
        } else if (move.equals("D")) {
            goEast();
        } else if (move.equals("S")) {
            goSouth();
        } else if (move.equals("W")) {
            goNorth();
        } else if (move.equals("Q")) {
            System.out.println("***  YOU END THE GAME  ***");
            Game.getCharacter();
        }
    }

    private void goNorth() {
        move(-1, 0);
    }

    private void goSouth() {
        move(1, 0);
    }

    private void goWest() {
        move(0, -1);
    }

    private void goEast() {
        move(0, 1);
    }

    //metoda care muta jucatorul
    private void move(int x, int y)  {
        int calculateX = currentCell.getX() + x;
        int calculateY = currentCell.getY() + y;

        //actualizam celula in care ne-am aflat
        currentCell.setVisited(true);
        currentCell.setType(CellEntityType.VOID);

        //actualizam pozitia curenta
        currentCell = grid.get(calculateX).get(calculateY);
        //retinem tipul celuluei si aplicam evenimentul
        CellEntityType type = currentCell.getType();
        Events event = new Events(character);
        event.doEvent(type);

        oldCellType = currentCell.getType();
        currentCell.setType(CellEntityType.PLAYER);
    }

   public void showGrid () {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                CellEntityType type = grid.get(i).get(j).getType();
                if(!grid.get(i).get(j).isVisited() && type != CellEntityType.PLAYER) {
                    System.out.print("N  ");
                } else {
                    if (type == CellEntityType.PLAYER) {
                        System.out.print("P  ");
                    } else if (type == CellEntityType.SANCTUARY) {
                        System.out.print("S  ");
                    } else if (type == CellEntityType.VOID) {
                        System.out.print("V  ");
                    } else if (type == CellEntityType.PORTAL) {
                        System.out.print("F  ");
                    } else if (type == CellEntityType.ENEMY) {
                        System.out.print("E  ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
   }
}
