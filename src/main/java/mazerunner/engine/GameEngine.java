package mazerunner.engine;

import java.io.Serializable;
import java.util.*;

/**
 * The main game engine.
 */
public class GameEngine implements Serializable {

    private Cell[][] map;
    private Player player;
    private int mapDifficulty = 5;
    private int score;

    //Initialize each cell object for the map.
    private final Cell ENTRY_CELL = new Cell(Cell.types.ENTRY);
    private final Cell EMPTY_CELL = new Cell(Cell.types.EMPTY);
    private final Cell EXIT_CELL = new Cell(Cell.types.EXIT);
    private final Cell APPLE_CELL = new Cell(Cell.types.APPLE);
    private final Cell COIN_CELL = new Cell(Cell.types.COIN);
    private final Cell TRAP_CELL = new Cell(Cell.types.TRAP);

    /**
     * Creates the game environment.
     */
    public GameEngine(int size) {
        map = new Cell[size][size];
        player = new Player();
    }

    /**
     * The size of the current game map.
     * @return this is both the width and the height.
     */
    public int getSize() { return map.length; }

    /**
     * Randomly generates a map. Places entrance, exit, trap, apple, and coin cells on the map.
     */
    public void generateMap() {
        //Fill every value in the map with the EmptyCell object.
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[x][y] = EMPTY_CELL;
            }
        }

        //Store the type of cell and the number of each cell in a list for looping.
        LinkedHashMap<Cell, Integer> numOfType = new LinkedHashMap<>();
        numOfType.put(EXIT_CELL, 1);
        numOfType.put(COIN_CELL, 5);
        numOfType.put(TRAP_CELL, mapDifficulty);
        numOfType.put(APPLE_CELL, 10 - mapDifficulty);

        //Stores the values already in use to prevent cell overlap.
        ArrayList<Point> usedCells = new ArrayList<>();

        //Add the EntranceCell object.
        map[0][0] = ENTRY_CELL;
        usedCells.add(new Point(0,0));

        //Randomly place trap, apple, coin and exit tile/s.
        Random rdm = new Random();
        for (Cell cellObj : numOfType.keySet()) {
            int cellCount = numOfType.get(cellObj);

            while (cellCount > 0) {
                Point point = new Point(rdm.nextInt(map.length), rdm.nextInt(map.length));

                if (!usedCells.contains(point)) {
                    map[point.x()][point.y()] = cellObj;
                    usedCells.add(point);
                    cellCount--;
                }
            }
        }
    }

    /**
     * Generates a unique map for testing.
     * @param type the desired cell type to fill the map.
     */
    public void generateTestMap(Cell.types type) {
        //Fill every value in the map with the desired object.
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[x][y] = new Cell(type);
            }
        }
    }

    /**
     * Sets the difficulty of the map.
     * @param difficulty value between 0-10.
     */
    public boolean setDifficulty(int difficulty) {
        if (difficulty <= 10 && difficulty >= 0) {
            mapDifficulty = difficulty;
            return true;
        } else {
            System.out.print("\nInvalid value.");
            mapDifficulty = 5;
            return false;
        }
    }

    /**
     * Returns the difficulty of the map.
     * @return The map difficulty;
     */
    public int getDifficulty() { return mapDifficulty; }

    /**
     * Sets the score.
     * @param value the desired score.
     */
    public void setScore(int value) {
        score = value;
    }


    /**
     * Resets the player object.
     */
    public void resetPlayer() {
        player = new Player();
    }

    /**
     * Gets the players position.
     * @return an array of the players x and y values in the format [x, y].
     */
    public int[] getPlayerPos() {
        return new int[]{player.getPos().x(), player.getPos().y()};
    }

    /**
     * Sets the player's stats.
     * @param stamina the desired stamina value.
     * @param coins the desired coin value.
     */
    public void setPlayerStats(int stamina, int coins) {
        player.setStamina(stamina);
        player.setCoinCount(coins);
    }

    /**
     * Gets the player's stats.
     * @return the player's stamina and coin count.
     */
    public String getPlayerStats() {
        return "\nStamina: " + player.getStamina() + "\nCoins: " + player.getCoinCount() + "\n";
    }

    /**
     * Moves the players position depending on the value input.
     * @return the result of the players movement.
     */
    public String movePlayer(char moveChar) {
        String result = "";
        switch (Character.toLowerCase(moveChar)) {
            case 'a':
                if (player.getPos().x() == 0) {
                    result = "Error. Player can't move left!";
                } else {
                    player.moveLeft();
                    result = "Player moved left!";
                }
                break;
            case 'w':
                if (player.getPos().y() == map.length-1) {
                    result = "Error. Player can't move up!";
                } else {
                    player.moveUp();
                    result = "Player moved up!";
                }
                break;
            case 'd':
                if (player.getPos().x() == map.length-1) {
                    result = "Error. Player can't move right!";
                } else {
                    player.moveRight();
                    result = "Player moved right!";
                }
                break;
            case 's':
                if (player.getPos().y() == 0) {
                    result = "Error. Player can't move down!";
                } else {
                    player.moveDown();
                    result = "Player moved down!";
                }
                break;
        }
        return result;
    }

    /**
     * Gets the cell type that the player is standing on and calls the appropriate player action.
     * @return The result of the interaction - what the player did.
     */
    public String checkInteraction() {
        int posY = player.getPos().y();
        int posX = player.getPos().x();
        String interactionResult = "No action taken.";

        switch (map[posX][posY].getType()) {
            case COIN:
                player.pickupCoin();
                map[posX][posY] = EMPTY_CELL;
                interactionResult = "You picked up a coin!";
                break;
            case APPLE:
                player.eatApple();
                map[posX][posY] = EMPTY_CELL;
                interactionResult = "You ate a apple!";
                break;
            case TRAP:
                player.payCoin();
                interactionResult = "You got caught in a trap! Had to pay 1 coin...";
                break;
            case EXIT:
                interactionResult = "You exited the maze!";
                break;
        }
        return interactionResult;
    }

    /**
     * Checks the state of the game against the win conditions and the lose conditions.
     * Updates the score if the game is over.
     * @return boolean representing whether the game is over or not.
     * Returns false if the game is over otherwise it returns true.
     */
    public boolean checkGameState() {
        if (map[player.getPos().x()][player.getPos().y()].getType() == Cell.types.EXIT) {
            score = player.getCoinCount();
            return false;
        } else if (player.getStamina() == 0 &&
                map[player.getPos().x()][player.getPos().y()].getType() != Cell.types.APPLE) {
            score = -1;
            return false;
        } else if (player.getCoinCount() == -1 &&
                map[player.getPos().x()][player.getPos().y()].getType() == Cell.types.TRAP) {
            score = -1;
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets the game results.
     * @return win or lose message.
     */
    public String getGameResults() {
        if (score < 0) {
            return "You lost...";
        } else {
            return "Congrats, you won! Your score was " + score + "!";
        }
    }

    /**
     * Gets the required cell from positional values.
     * @param x the x value of the desired cell.
     * @param y the y value of the desired cell.
     * @return the desired cell.
     */
    public Cell getCell(int x, int y) { return map[x][y]; }

    /**
     * Prints the map out using symbols.
     */
    public void printMap() {
        char symbol = '-';
        System.out.print("\n");
        Point playerPos = player.getPos();
        for (int y=map.length - 1; y >= 0; y--) {
            System.out.print("|");
            for (int x=0; x < map.length; x++) {
                switch (map[x][y].getType()) {
                    case COIN:
                        symbol = '0';
                        break;
                    case APPLE:
                        symbol = 'A';
                        break;
                    case TRAP:
                        symbol = '#';
                        break;
                    case ENTRY:
                    case EMPTY:
                        symbol = '-';
                        break;
                    case EXIT:
                        symbol = 'X';
                        break;
                }
                if (x == playerPos.x() && y == playerPos.y()) {
                    symbol = '!';
                }
                System.out.printf("%c|", symbol);
            }
            System.out.print("\n");
        }
    }

    /**
     * Runs the text-based UI of the game.
     */
    public static void main(String[] args) {
        GameEngine engine = new GameEngine(10);
        Scanner sc = new Scanner(System.in);
        Set<Character> moveValues = new HashSet<>(Arrays.asList('w','a','s','d'));
        engine.setDifficulty(11);

        engine.generateMap();

        System.out.print("\n-------Maze Game------\n");
        System.out.print("\nGame character: !\nTrap: #\nApple: A\nCoin: 0\nExit: X\n");
        engine.printMap();

        // Main game loop
        while (engine.checkGameState()) {
            boolean move = false;
            char moveInput = 0;

            while (!move) {
                System.out.print("\nMove Player (w -> up | a -> left | s -> down | d -> right): ");
                moveInput = sc.next().charAt(0);
                if (moveValues.contains(moveInput)) {
                    move = true;
                } else {
                    System.out.print("Error, input invalid.");
                }
            }
            String result = engine.movePlayer(moveInput);
            engine.printMap();

            System.out.print("\nMove: " + result + "\nAction: " + engine.checkInteraction());
            System.out.print(engine.getPlayerStats());
        }
        System.out.print("\n" + engine.getGameResults() + "\n");
    }
}
