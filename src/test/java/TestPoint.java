import mazerunner.engine.GameEngine;
import mazerunner.engine.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPoint {
    @Test
    void testInitPoint() {
        Point point = new Point(0,0);

        assertEquals(0, point.x());
        assertEquals(0, point.y());
    }
    @Test
    void testSetPoint() {
        Point point = new Point(0,0);
        point.setX(1);
        assertEquals(1, point.x());
        point.setY(1);
        assertEquals(1, point.y());
    }
    @Test
    void testIncrementPoint() {
        Point point = new Point(0,0);

        for(int i=0; i<3; i++) {
            point.increment('x', 1);
        }
        assertEquals(3, point.x());

        for(int i=0; i<3; i++) {
            point.increment('y', 1);
        }
        assertEquals(3, point.y());
    }

    @Test
    void testToString() {
        Point point = new Point(0,0);

        assertEquals("(0, 0)", point.toString());
    }
    @Test
    void testEquals() {
        Point point = new Point(0,0);
        Point equalPoint = new Point(0,0);
        Point nonEqualPoint = new Point(1,1);

        assertEquals(equalPoint, point);
        assertNotEquals(point, nonEqualPoint);
    }
}
