package kmoyenne.test;

import kmoyenne.Barycentre;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;

import java.util.List;

public class TestCSV {

    public static void main(String[] args) {
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        System.out.println(barycentres.toString());

        List<Point> data = CsvUtils.extractData();
        System.out.println(data.toString());
    }
}
