package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite {
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) { // set treasureCount = 0 and numLives = 2
        super(x, y);
        win = false;
        treasureCount = 0;
        numLives = 2;
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    public int getLives() {
        return numLives;
    }

    public boolean getWin() {
        return win;
    }

    @Override
    public String getRowCol(int size) {
        return "Player:" + super.getRowCol(size); // get the [row][col] coords
    }

    @Override
    public String getCoords() {
        return "Player:" + super.getCoords(); // get the Cartesian plane coords
    }

    // move method should override parent class, sprite
    @Override
    public void move(String direction) { // move the (x,y) coordinates of the player
        // reads a, s, w, and d to determine left, down, up, and right movement respectively
        if (direction.equals("a")) {
            setX(getX() - 1);
        } else if (direction.equals("s")) {
            setY(getY() - 1);
        } else if (direction.equals("w")) {
            setY(getY() + 1);
        } else if (direction.equals("d")) {
            setX(getX() + 1);
        }
    }

    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to
        // numTreasures is the total treasures at the beginning of the game
        if (obj instanceof Treasure && !(obj instanceof Trophy)) { // make sure that you can't interact with the trophy and increase treasure count
            treasureCount++;
        } else if (obj instanceof Enemy) {
            numLives--;
        } else if (obj instanceof Trophy && numTreasures == treasureCount) { /// prevents getting trophy before collecting all treasures
            win = true;
        }

    }

    // checks that x and y are in grid, prevents out of bounds error
    public boolean isValid(int size, String direction) { // check grid boundaries
        if (direction.equals("w")) {
            if (super.getY() + 1 > size - 1) {
                return false;
            }
        } else if (direction.equals("a")) {
            if (super.getX() - 1 < 0) {
                return false;
            }
        } else if (direction.equals("s")) {
            if (super.getY() - 1 < 0) {
                return false;
            }
        } else if (direction.equals("d")) {
            if (super.getX() + 1 > size - 1) {
                return false;
            }
        }
        return true;
    }

}
