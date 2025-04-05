package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    public Grid(int size) { //initialize and create a grid with all DOT objects
        this.size = size;
        grid = new Sprite[size][size];
        for (int i = 0; i < grid.length; i++){ // fills entire grid with DOT objects
            for (int j = 0; j < grid[i].length; j++){
                grid[i][j] = new Dot(Math.abs(i - size + 1), j); // Converting from Cartesian plane to [row][col] to initilize DOT objects
            }
        }
    }

 
    public Sprite[][] getGrid(){return grid;}


    public Sprite getSprite(int x, int y){
        return grid[(size - y - 1)][x]; // Convert from Cartesiian plane to [row][col]
    }

    public void placeSprite(Sprite s){ //place sprite in new spot
        grid[Math.abs(size - s.getY() - 1)][s.getX()] = s; // Convert froom Cartesian plane to [row][col]
    }

    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        // need to get old coords before movement to replace it with DOT sprite
        int prevX = s.getX(); 
        int prevY = s.getY();
        if (direction.equals("s")){
            prevY++;
        }else if (direction.equals("a")){
            prevX++;
        }else if (direction.equals("d")){
            prevX--;
        }else if (direction.equals("w")){
            prevY--;
        }
        placeSprite(new Dot(prevX, prevY)); // removes the old sprite and replaces with DOT sprite
        placeSprite(s);
    }


    public void display() { //print out the current grid to the screen 
        // looks at what class each object is to print corresponding emoji
        for (Sprite[] row : grid){
            for (Sprite sprite : row){
                if (sprite instanceof Dot){
                    System.out.print("⬜");
                }else if (sprite instanceof Player){
                    System.out.print("🦄");
                }else if (sprite instanceof Trophy){
                    System.out.print("🏆");
                }else if (sprite instanceof Treasure){
                    System.out.print("🌈");
                }else if (sprite instanceof Enemy){
                    System.out.print("🦂");
                }else if (sprite instanceof Skull){ // used to symbolize loss
                    System.out.print("💀");
                }else if (sprite instanceof Confetti){ // used to symbolize win
                    System.out.print("🎉");
                }else if (sprite instanceof Mazewall){ // used in intializeMaze() to block movement
                    System.out.print("🧱");
                }
            }
            System.out.println();
        } 
    }
    
    public void gameover(){ //use this method to display a loss
        // replaces all the sprites with Skull sprite except for player to signify loss
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!(grid[i][j] instanceof Player)){
                    grid[i][j] = new Skull(i, j); 
                }
            }
        }
    }

    public void win(){ //use this method to display a win 
        // replaces all the sprites with Confetti sprite except for player to signify win
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!(grid[i][j] instanceof Player)){
                    grid[i][j] = new Confetti(i, j);
                }
            }
        }
    }


}