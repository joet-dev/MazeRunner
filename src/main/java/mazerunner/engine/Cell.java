package mazerunner.engine;

import java.io.Serializable;

public class Cell implements Serializable {
    public enum types { ENTRY, EXIT, EMPTY, TRAP, APPLE, COIN }
    private final types CELL_TYPE;

    public Cell(types type) {
        CELL_TYPE = type;
    }

    public types getType() {
        return CELL_TYPE;
    }

    @Override
    public String toString() {
        return CELL_TYPE.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Cell) {
            return this.CELL_TYPE == ((Cell) object).CELL_TYPE;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return types.valueOf(this.toString()).ordinal();
    }
}
