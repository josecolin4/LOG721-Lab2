package kmoyenne.junit;

import kmoyenne.Barycentre;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCSV {

    @Test
    public void extractBarycentres() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();

        assertEquals(5,barycentres.size());
    }
    @Test
    public void extractData() {

        List<Point> data = CsvUtils.extractData();
        assertEquals("[largeur : 10 prix : 10, largeur : 200 prix : 200, largeur : 300 prix : 300, largeur : 400 prix : 400, largeur : 100 prix : 100, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10, largeur : 10 prix : 10]",data.toString());
    }
}