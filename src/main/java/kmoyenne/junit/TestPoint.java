package kmoyenne.junit;

import kmoyenne.Barycentre;
import kmoyenne.Point;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPoint {


    static Point point;

    @BeforeClass
    public static void beforeClass() throws Exception {
        point = new Point(1,1);
    }

    @Test
    public void  testToString() {

        assertEquals("largeur : 1 prix : 1",point.toString());
    }

    @Test
    public void  distance() {
        assertEquals("0.0",Double.toString(point.distance(new Barycentre("1",1,1))));
    }

    @Test
    public void  getLargeur() {
        assertEquals(1,point.getLargeur());
    }

    @Test
    public void  getPrix() {
        assertEquals(1,point.getPrix());
    }
}
