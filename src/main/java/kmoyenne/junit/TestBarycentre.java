package kmoyenne.junit;

import kmoyenne.Barycentre;
import kmoyenne.utils.CsvUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBarycentre {
    @Test
    public void testBarycentre() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();

        assertEquals("[#1 --> largeur : 50 prix : 100, #2 --> largeur : 100 prix : 150, #3 --> largeur : 150 prix : 200, #4 --> largeur : 200 prix : 250, #5 --> largeur : 250 prix : 300]",barycentres.toString());
    }

    @Test
    public void testToString() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        assertEquals("#1 --> largeur : 50 prix : 100",barycentres.get(0).toString());

    }

    @Test
    public void getId() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();

        assertEquals("#1",barycentres.get(0).getId());
    }

    @Test
    public void getLargeur() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();

        assertEquals(50,barycentres.get(0).getLargeur());
    }

    @Test
    public void getPrix() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();

        assertEquals(100,barycentres.get(0).getPrix());
    }

    @Test
    public void setLargeur() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        barycentres.get(0).setLargeur(5);

        assertEquals(5,barycentres.get(0).getLargeur());
    }

    @Test
    public void setPrix() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        barycentres.get(0).setPrix(5);

        assertEquals(5,barycentres.get(0).getPrix());
    }

    @Test
    public void testClone() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        Barycentre testB = barycentres.get(0).clone();

        assertEquals(testB,barycentres.get(0));
    }

    @Test
    public void testEquals() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        Barycentre testB = barycentres.get(0);

        assertTrue(testB.equals(testB));
    }

    @Test
    public void testHashCode() {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        Barycentre testB = barycentres.get(0);

        assertEquals(1134,testB.hashCode());
    }
}
