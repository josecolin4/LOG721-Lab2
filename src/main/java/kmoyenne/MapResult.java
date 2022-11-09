package kmoyenne;

import java.io.Serializable;

public class MapResult implements Serializable {

    private Barycentre barycentre;
    private Point point;

    public MapResult(Barycentre barycentre, Point point) {
        this.barycentre = barycentre;
        this.point = point;
    }

    public Barycentre getBarycentre() {
        return barycentre;
    }

    public Point getPoint() {
        return point;
    }
}
