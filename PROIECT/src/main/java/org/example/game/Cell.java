package org.example.game;

public class Cell {
    private int x;
    private int y;
    private boolean visited;
    private CellEntityType type;

    public Cell(int x, int y, CellEntityType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        visited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellEntityType getType() {
        return type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + (x + 1) +
                ", y=" + (y + 1) +
                ", visited=" + visited +
                ", type=" + type +
                '}';
    }
}
