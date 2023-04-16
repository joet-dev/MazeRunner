import mazerunner.engine.Player;
import mazerunner.engine.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    void testGetStamina() {
        Player player = new Player();

        assertEquals(12, player.getStamina());
    }
    @Test
    void testSetStamina() {
        Player player = new Player();
        player.setStamina(5);

        assertEquals(5, player.getStamina());
    }
    @Test
    void testGetCoinCount() {
        Player player = new Player();

        assertEquals(0, player.getCoinCount());
    }
    @Test
    void testSetCoinCount() {
        Player player = new Player();
        player.setCoinCount(5);

        assertEquals(5, player.getCoinCount());
    }

    @Test
    void testEatApple() {
        Player player = new Player();
        player.eatApple();

        assertEquals(15, player.getStamina());
    }
    @Test
    void testPickupCoin() {
        Player player = new Player();
        player.pickupCoin();

        assertEquals(1, player.getCoinCount());
    }
    @Test
    void testPayCoin() {
        Player player = new Player();
        player.payCoin();

        assertEquals(-1, player.getCoinCount());
    }

    @Test
    void testMoveUp() {
        Player player = new Player();
        player.moveUp();

        assertEquals(11, player.getStamina());
        assertEquals(new Point(0, 1), player.getPos());
    }
    @Test
    void testMoveDown() {
        Player player = new Player();
        player.moveDown();

        assertEquals(11, player.getStamina());
        assertEquals(new Point(0, -1), player.getPos());
    }
    @Test
    void testMoveLeft() {
        Player player = new Player();
        player.moveLeft();

        assertEquals(11, player.getStamina());
        assertEquals(new Point(-1, 0), player.getPos());
    }
    @Test
    void testMoveRight() {
        Player player = new Player();
        player.moveRight();

        assertEquals(11, player.getStamina());
        assertEquals(new Point(1, 0), player.getPos());
    }
}
