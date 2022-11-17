package kmoyenne.test;

import kmoyenne.Barycentre;
import kmoyenne.Point;

public class TestDistance {

    public static void main(String[] args) {
        Barycentre barycentre1 = new Barycentre("#1", 50, 100);
        Barycentre barycentre2 = new Barycentre("#2", 100, 150);
        Point point = new Point(10, 10);

        if (point.distance(barycentre1) > point.distance(barycentre2)) {
            System.out.println("test ko");
        } else {
            System.out.println("test ok");
        }
    }
}
