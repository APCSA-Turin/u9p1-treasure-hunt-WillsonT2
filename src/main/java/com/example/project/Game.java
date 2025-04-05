package com.example.project;

import java.util.Scanner;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 

    public Game(int size){ //the constructor should call initialize() and play()
        this.size = size; 
        grid = new Grid(size); 
        initialize(); // creates and places all the sprites
        play(); // runs the game and implements the rules of the game
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){ //write your game logic here
        boolean keepPlaying = true; // used to end game when over
        while(!player.getWin() && keepPlaying){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop
            grid.display();
            System.out.println("Player:" + player.getCoords());
            System.out.println("Player:" + player.getRowCol(size));
            System.out.println("Treasure Collected: " + player.getTreasureCount());
            System.out.println("Lives remaining: " + player.getLives());
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a direction (w, a, s, d) or 'q' to exit: ");
            String move = scanner.nextLine();
            if (move.equals("q")){ // ends game when q is entered
                keepPlaying = false;
            }else if (player.isValid(size, move)){ // check to see if the direction moved is valid
                int interractObjX = player.getX();  
                int interractObjY = player.getY();
                if (move.equals("a")){ // find coords of the object that is trying to be interracted with
                    interractObjX = player.getX() - 1;
                }else if (move.equals("s")){
                    interractObjY = player.getY() - 1;
                }else if (move.equals("w")){
                    interractObjY = player.getY() + 1;
                }else if (move.equals("d")){
                    interractObjX = player.getX() + 1;
                }
                Sprite sprite = grid.getSprite(interractObjX, interractObjY); // find what type of Sprite object player is interactng with
                // prevents movement if trying to walk into trophy without all treasure or walking into mazewall
                if ((!(sprite instanceof Trophy) || player.getTreasureCount() == treasures.length) && !(sprite instanceof Mazewall)){ 
                    player.interact(size, move, treasures.length, sprite); // spriite variable being interacted wtih 
                    player.move(move);
                    grid.placeSprite(player, move);
                }
                if (player.getWin()){ // display the grid after win or loss, changes all sprite but player to Conffeti if win and Skull if lose
                    grid.win();
                    grid.display();
                }else if (player.getLives() == 0){
                    grid.gameover();
                    grid.display();
                    keepPlaying = false;
                }
            }
            }
    }

    public void initialize(){
        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        Player player = new Player(0, 0);
        this.player = player; // declare global variables so they can be used in play()
        Trophy trophy = new Trophy(0, 9);
        this.trophy = trophy;
        Treasure treasure1 = new Treasure(2, 2);
        Treasure treasure2 = new Treasure(9, 9);
        treasures = new Treasure[2];
        treasures[0] = treasure1;
        treasures[1] = treasure2; 
        Enemy enemy1 = new Enemy(2, 8);
        Enemy enemy2 = new Enemy(3, 2);
        Enemy enemy3 = new Enemy(5, 7);
        enemies = new Enemy[3];
        enemies[0] = enemy1;
        enemies[1] = enemy2;
        enemies[2] = enemy3;
        grid.placeSprite(player);
        grid.placeSprite(trophy);
        grid.placeSprite(treasure1);
        grid.placeSprite(treasure2);
        grid.placeSprite(enemy1);
        grid.placeSprite(enemy2);
        grid.placeSprite(enemy3);
    }

    // used to intialze the maze walls to simulate the maze game
    public void initializeMaze(){
        Player player = new Player(0, 0);
        this.player = player; // declare global variables so they can be used in play() 
        Trophy trophy = new Trophy(0, 8);
        this.trophy = trophy;
        this.treasures = new Treasure[0]; // no treasure in the maze game
        grid.placeSprite(trophy);
        grid.placeSprite(player);
        // placing of all the mazewall sprites to create the maze
        grid.placeSprite(new Mazewall(0, 1));
        grid.placeSprite(new Mazewall(1, 1));
        grid.placeSprite(new Mazewall(2, 1));
        grid.placeSprite(new Mazewall(3, 1));
        grid.placeSprite(new Mazewall(5, 0));
        grid.placeSprite(new Mazewall(5, 1));
        grid.placeSprite(new Mazewall(6, 0));
        grid.placeSprite(new Mazewall(6, 1));
        grid.placeSprite(new Mazewall(8, 2));
        grid.placeSprite(new Mazewall(8, 1));
        grid.placeSprite(new Mazewall(0, 7));
        grid.placeSprite(new Mazewall(0, 6));
        grid.placeSprite(new Mazewall(0, 5));
        grid.placeSprite(new Mazewall(1, 8));
        grid.placeSprite(new Mazewall(2, 8));
        grid.placeSprite(new Mazewall(3, 8));
        grid.placeSprite(new Mazewall(4, 8));
        grid.placeSprite(new Mazewall(5, 8));
        grid.placeSprite(new Mazewall(6, 8));
        grid.placeSprite(new Mazewall(7, 8));
        grid.placeSprite(new Mazewall(7, 6));
        grid.placeSprite(new Mazewall(3, 7));
        grid.placeSprite(new Mazewall(5, 6));
        grid.placeSprite(new Mazewall(9, 9));
        grid.placeSprite(new Mazewall(9, 8));
        grid.placeSprite(new Mazewall(9, 7));
        grid.placeSprite(new Mazewall(9, 6));
        // creating lines of walls
        for (int i = size - 1; i >= 2; i--){
            grid.placeSprite(new Mazewall(i, 5));
        }
        for (int i = 0; i < size - 1; i++){
            grid.placeSprite(new Mazewall(i, 3));
        }
    }

    public static void main(String[] args) {
        Game game = new Game(10);
        
    }
}