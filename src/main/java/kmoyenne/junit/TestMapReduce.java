package kmoyenne.junit;

import kmoyenne.Barycentre;
import kmoyenne.MapReduce;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMapReduce {

    static List<Barycentre> barycentres;
    static List<Point> data;
    @BeforeClass
    public static void beforeClass() {
        barycentres = CsvUtils.extractBarycentres();
         data = CsvUtils.extractData();
    }

    @Test
    public void reduce() {
        MapReduce mapReduce = new MapReduce(barycentres);

        assertEquals("[#1 --> largeur : 50 prix : 100, #2 --> largeur : 100 prix : 150, #3 --> largeur : 150 prix : 200, #4 --> largeur : 200 prix : 250, #5 --> largeur : 250 prix : 300]",mapReduce.getBarycentres().toString());

    }
}
