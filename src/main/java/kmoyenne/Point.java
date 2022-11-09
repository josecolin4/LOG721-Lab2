package kmoyenne;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Point implements Serializable {

    int largeur;

    int prix;

    public Point(int largeur, int prix) {
        this.largeur = largeur;
        this.prix = prix;
    }

    public String toString() {
        return "largeur : " + largeur + " prix : " + prix;
    }

    public double distance(Barycentre barycentre) {
        return Point2D.distance(largeur, prix, barycentre.getLargeur(), barycentre.getPrix());
    }

    public int getLargeur() {
        return largeur;
    }

    public int getPrix() {
        return prix;
    }
}
