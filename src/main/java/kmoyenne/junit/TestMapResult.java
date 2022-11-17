package kmoyenne.junit;

import kmoyenne.Barycentre;
import kmoyenne.MapResult;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMapResult {

    static List<Barycentre> barycentres;
    static List<Point> data;
    static MapResult mapResult;
    @BeforeClass
    public static void beforeClass() throws Exception {
        barycentres = CsvUtils.extractBarycentres();
         data = CsvUtils.extractData();
         mapResult = new MapResult(barycentres.get(0),new Point(1,1));
    }

    @Test
    public void getBarycentre() throws Exception {

        assertEquals("#1 --> largeur : 50 prix : 100",mapResult.getBarycentre().toString());

    }
    @Test
    public void getPoint() throws Exception {

        assertEquals("largeur : 1 prix : 1",mapResult.getPoint().toString());

    }

    @Test
    public void toStringTest() throws Exception {

        assertEquals("barycentre : #1 --> largeur : 50 prix : 100 point : largeur : 1 prix : 1",mapResult.toString());

    }
}
