package mazerunner.engine;

import java.io.Serializable;

public class Player implements Serializable {

    private int stamina;
    private int coinCount;
    private Point position;


    /**
     * Initializes the player object.
     */
    public Player() {
        stamina = 12;
        coinCount = 0;
        position = new Point(0, 0);
    }

    /**
     * Returns the position of the player object.
     * @return an array with values of x and y.
     */
    public Point getPos() {
        return position;
    }

    /**
     * Returns the stamina level of the player object.
     * @return an integer value of the stamina.
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * Returns the coin value the player object has.
     * @return an integer value of coins.
     */
    public int getCoinCount() {
        return coinCount;
    }

    /**
     * Set the value of the player object's stamina.
     * @param value an integer of the desired stamina.
     */
    public void setStamina(int value) {
        stamina = value;
    }

    /**
     * Set the value of the player object's coins.
     * @param value an integer of the desired coin count.
     */
    public void setCoinCount(int value) {
        coinCount = value;
    }

    /**
     * Player eats apple. +3 to stamina.
     */
    public void eatApple() {
        stamina += 3;
    }

    /**
     * Player picks up coin. +1 to coinCount.
     */
    public void pickupCoin() {
        coinCount += 1;
    }

    /**
     * If the player has no coins, false is returned. If the player has coins, 1 is removed and true is returned.
     */
    public void payCoin() {
        coinCount -= 1;
    }

    /**
     * Moves the position of the player object up.
     */
    public void moveUp() {
        stamina -= 1;
        position.increment('y', 1);
    }

    /**
     * Moves the position of the player object down.
     */
    public void moveDown() {
        stamina -= 1;
        position.increment('y', -1);
    }

    /**
     * Moves the position of the player object left.
     */
    public void moveLeft() {
        stamina -= 1;
        position.increment('x', -1);
    }

    /**
     * Moves the position of the player object right.
     */
    public void moveRight() {
        stamina -= 1;
        position.increment('x', 1);
    }
}
