package mazerunner.engine;

import java.io.Serializable;

public class Point implements Serializable {
    private int x;
    private int y;

    public Point(int xValue, int yValue) {
        x = xValue;
        y = yValue;
    }

    public void setX(int xValue) { x = xValue; }

    public void setY(int yValue) { y = yValue; }

    public void increment(char coord, int value) {
        switch (coord) {
            case 'x':
                x += value;
                break;
            case 'y':
                y += value;
                break;
        }
    }

    public int x() { return x; }

    public int y() { return y; }

    @Override
    public String toString() { return String.format("(%d, %d)", x, y); }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object instanceof Point)
        {
            sameSame = (this.x == ((Point) object).x && this.y == ((Point) object).y);
        }

        return sameSame;
    }
}
