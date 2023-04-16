import mazerunner.engine.GameEngine;
import mazerunner.engine.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCell {
    @Test
    void testGetType() {
        Cell cell = new Cell(Cell.types.EMPTY);

        assertEquals(Cell.types.EMPTY, cell.getType());
    }
    @Test
    void testToString() {
        Cell cell = new Cell(Cell.types.EMPTY);

        assertEquals("EMPTY", cell.toString());
    }
    @Test
    void testEquals() {
        Cell cell = new Cell(Cell.types.EMPTY);
        Cell equalCell = new Cell(Cell.types.EMPTY);
        Cell nonEqualCell = new Cell(Cell.types.ENTRY);

        assertEquals(equalCell, cell);
        assertNotEquals(nonEqualCell, cell);
    }
    @Test
    void testHashCode() {
        Cell entry_cell = new Cell(Cell.types.ENTRY);
        Cell exit_cell = new Cell(Cell.types.EXIT);
        Cell empty_cell = new Cell(Cell.types.EMPTY);
        Cell trap_cell = new Cell(Cell.types.TRAP);
        Cell apple_cell = new Cell(Cell.types.APPLE);
        Cell coin_cell = new Cell(Cell.types.COIN);

        assertEquals(0, entry_cell.hashCode());
        assertEquals(1, exit_cell.hashCode());
        assertEquals(2, empty_cell.hashCode());
        assertEquals(3, trap_cell.hashCode());
        assertEquals(4, apple_cell.hashCode());
        assertEquals(5, coin_cell.hashCode());
    }
}
