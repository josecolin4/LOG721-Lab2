package kmoyenne;

import java.io.Serializable;

public class Barycentre  implements Serializable {

    private String id;

    private int largeur, prix;

    public Barycentre(String id, int x, int y) {
        this.id = id;
        this.largeur = x;
        this.prix = y;
    }

    public String toString() {
        return id + " --> largeur : " + largeur + " prix : " + prix;
    }

    public String getId() {
        return id;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getPrix() {
        return prix;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public Barycentre clone() {
        return new Barycentre(id, largeur, prix);
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Barycentre)) {
            return false;
        }
        return this.id.equals(((Barycentre) other).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
