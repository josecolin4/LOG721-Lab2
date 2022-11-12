package kmoyenne.test;

import kmoyenne.Barycentre;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCSV {

    @Test
    public void testCSV() throws Exception {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        System.out.println(barycentres.toString());

        List<Point> data = CsvUtils.extractData();
        assertEquals("[largeur : 40 prix : 60, largeur : 30 prix : 60, largeur : 20 prix : 60, largeur : 10 prix : 60, largeur : 0 prix : 60]",data.toString());
    }
}
