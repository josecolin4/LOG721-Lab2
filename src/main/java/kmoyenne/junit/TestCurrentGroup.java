package kmoyenne.junit;

import kmoyenne.Barycentre;
import kmoyenne.CurrentGroup;
import kmoyenne.MapResult;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCurrentGroup {

    static List<Barycentre> barycentres;
    static MapResult mapResult;
    static CurrentGroup currentGroup;
    @BeforeClass
    public static void beforeClass() throws Exception {
        barycentres = CsvUtils.extractBarycentres();
        mapResult = new MapResult(barycentres.get(0),new Point(1,1));
        currentGroup = new CurrentGroup();
    }


    @Test
    public void getCurrentLargeur() {
        assertEquals(0,currentGroup.getCurrentLargeur());
    }

    @Test
    public void getCurrentPrix() {
        assertEquals(0,currentGroup.getCurrentPrix());
    }
}
