import mazerunner.engine.GameEngine;
import mazerunner.engine.Cell;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameEngine {
    @Test
    void testGetSize() {
        GameEngine engine = new GameEngine(10);

        assertEquals(10, engine.getSize());
    }

    @Test
    void testDefaultDifficulty() {
        GameEngine engine = new GameEngine(10);

        assertEquals(5, engine.getDifficulty());
    }
    @Test
    void testSetDifficulty() {
        GameEngine engine = new GameEngine(10);

        engine.setDifficulty(10);
        assertEquals(10, engine.getDifficulty());
    }
    @Test
    void testSetDifficulty_OutOfRange() {
        GameEngine engine = new GameEngine(10);

        engine.setDifficulty(11);
        assertEquals(5, engine.getDifficulty());
        engine.setDifficulty(-1);
        assertEquals(5, engine.getDifficulty());
    }

    @Test
    void testGenerateMap() {
        GameEngine engine = new GameEngine(10);
        engine.generateMap();

        Map<Cell.types, AtomicInteger> numEachCell = new HashMap<>();

        for(int x=0; x<engine.getSize(); x++) {
            for(int y=0; y<engine.getSize(); y++) {
                Cell.types cell = engine.getCell(x, y).getType();
                if (!numEachCell.containsKey(cell)) {
                    numEachCell.put(cell, new AtomicInteger(1));
                } else {
                    numEachCell.get(cell).incrementAndGet();
                }
            }
        }
        assertEquals(1, numEachCell.get(Cell.types.ENTRY).intValue());
        assertEquals(5, numEachCell.get(Cell.types.TRAP).intValue());
        assertEquals(5, numEachCell.get(Cell.types.APPLE).intValue());
        assertEquals(5, numEachCell.get(Cell.types.COIN).intValue());
        assertEquals(83, numEachCell.get(Cell.types.EMPTY).intValue());
        assertEquals(1, numEachCell.get(Cell.types.EXIT).intValue());
    }
    @Test
    void testGenerateMap_NonDefaultDifficulty() {
        GameEngine engine = new GameEngine(10);
        engine.setDifficulty(8);
        engine.generateMap();

        Map<Cell.types, AtomicInteger> numEachCell = new HashMap<>();

        for(int x=0; x<engine.getSize(); x++) {
            for(int y=0; y<engine.getSize(); y++) {
                Cell.types cell = engine.getCell(x, y).getType();
                if (!numEachCell.containsKey(cell)) {
                    numEachCell.put(cell, new AtomicInteger(1));
                } else {
                    numEachCell.get(cell).incrementAndGet();
                }
            }
        }
        assertEquals(1, numEachCell.get(Cell.types.ENTRY).intValue());
        assertEquals(8, numEachCell.get(Cell.types.TRAP).intValue());
        assertEquals(2, numEachCell.get(Cell.types.APPLE).intValue());
        assertEquals(5, numEachCell.get(Cell.types.COIN).intValue());
        assertEquals(83, numEachCell.get(Cell.types.EMPTY).intValue());
        assertEquals(1, numEachCell.get(Cell.types.EXIT).intValue());
    }

    @Test
    void testCheckInteraction_Entry() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.ENTRY);

        assertEquals("No action taken.", engine.checkInteraction());
        assertEquals("\nStamina: 12\nCoins: 0\n", engine.getPlayerStats());
    }
    @Test
    void testCheckInteraction_Exit() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.EXIT);

        assertEquals("You exited the maze!", engine.checkInteraction());
        assertEquals("\nStamina: 12\nCoins: 0\n", engine.getPlayerStats());
    }
    @Test
    void testCheckInteraction_Apple() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.APPLE);

        assertEquals("You ate a apple!", engine.checkInteraction());
        assertEquals("\nStamina: 15\nCoins: 0\n", engine.getPlayerStats());
    }
    @Test
    void testCheckInteraction_Trap() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.TRAP);

        assertEquals("You got caught in a trap! Had to pay 1 coin...", engine.checkInteraction());
        assertEquals("\nStamina: 12\nCoins: -1\n", engine.getPlayerStats());
    }
    @Test
    void testCheckInteraction_Coin() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.COIN);

        assertEquals("You picked up a coin!", engine.checkInteraction());
        assertEquals("\nStamina: 12\nCoins: 1\n", engine.getPlayerStats());
    }

    @Test
    void testCheckGameState_Running() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.EMPTY);
        engine.setPlayerStats(12, 0);
        assertTrue(engine.checkGameState());
    }
    @Test
    void testCheckGameState_TrapWithNoCoin() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.TRAP);
        engine.setPlayerStats(12, -1);
        assertFalse(engine.checkGameState());
    }
    @Test
    void testCheckGameState_Exit() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.EXIT);
        engine.setPlayerStats(12, 0);
        assertFalse(engine.checkGameState());
    }
    @Test
    void testCheckGameState_NoStamina() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.EMPTY);
        engine.setPlayerStats(0, 0);
        assertFalse(engine.checkGameState());
    }
    @Test
    void testCheckGameState_NoStaminaOnApple() {
        GameEngine engine = new GameEngine(1);
        engine.generateTestMap(Cell.types.APPLE);
        engine.setPlayerStats(0, 0);
        assertTrue(engine.checkGameState());
    }

    @Test
    void testResetPlayer() {
        GameEngine engine = new GameEngine(10);
        engine.generateTestMap(Cell.types.APPLE);
        engine.checkInteraction();
        engine.resetPlayer();
        assertEquals("\nStamina: 12\nCoins: 0\n", engine.getPlayerStats());
    }
    @Test
    void testMovePlayer() {
        GameEngine engine = new GameEngine(2);
        engine.generateTestMap(Cell.types.EMPTY);

        assertArrayEquals(new int[]{0,0}, engine.getPlayerPos());
        assertEquals("Player moved up!", engine.movePlayer('w'));
        assertArrayEquals(new int[]{0,1}, engine.getPlayerPos());
        assertEquals("Player moved right!", engine.movePlayer('d'));
        assertArrayEquals(new int[]{1,1}, engine.getPlayerPos());
        assertEquals("Player moved down!", engine.movePlayer('s'));
        assertArrayEquals(new int[]{1,0}, engine.getPlayerPos());
        assertEquals("Player moved left!", engine.movePlayer('a'));
        assertArrayEquals(new int[]{0,0}, engine.getPlayerPos());
    }

    @Test
    void testGetGameResults_Win() {
        GameEngine engine = new GameEngine(1);

        engine.setScore(5);
        assertEquals("Congrats, you won! Your score was 5!", engine.getGameResults());
    }
    @Test
    void testGetGameResults_Lose() {
        GameEngine engine = new GameEngine(1);

        engine.setScore(-1);
        assertEquals("You lost...", engine.getGameResults());
    }
}
