package kmoyenne;

import java.util.ArrayList;
import java.util.List;

public class CurrentGroup {

    private int nbPoints;

    private int currentLargeur;
    private int currentPrix;

    public CurrentGroup() {
        nbPoints = 0;
        this.currentLargeur = 0;
        this.currentPrix = 0;
    }

    public void reduce(MapResult mapResult) {
        // update du barycentre en recalculant la moyenne
        currentLargeur = (currentLargeur * nbPoints + mapResult.getPoint().getLargeur())
                / (nbPoints + 1);
        currentPrix = (currentPrix * nbPoints + mapResult.getPoint().getPrix())
                / (nbPoints + 1);
        nbPoints++;
    }

    public int getCurrentLargeur() {
        return currentLargeur;
    }

    public int getCurrentPrix() {
        return currentPrix;
    }
}
