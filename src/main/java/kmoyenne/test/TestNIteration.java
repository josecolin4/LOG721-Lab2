package kmoyenne.test;

import kmoyenne.Barycentre;
import kmoyenne.MapReduce;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;
import publishsubscribe.Classes.Broker;
import publishsubscribe.Classes.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestNIteration {

    public static void main(String[] args) {
        // récupération des données
        List<Barycentre> barycentres = CsvUtils.extractBarycentres();
        List<Point> data = CsvUtils.extractData();

        MapReduce mapReduce = new MapReduce(barycentres);

        // affichage des barycentre à l'itération 0
        System.out.println("barycentres à l'itération 0");
        mapReduce.printBaryCentres();

        // publish subscribe
        int portBorker = 4000;
        Broker broker = new Broker(0, portBorker);
        new Thread() {
            public void run() {
                try {
                    broker.listenToNetwork();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

        while(broker.isBrokerListening()) {}

        // mappers
        for (int i = 0; i < Test1Iteration.NB_MAPPERS; i++) {
            mapReduce.addMapper(new Publisher(100 + i, 5000 + i, portBorker));
        }

        // un Reducer par barycentre
        mapReduce.addReducerForEachBarycentre(portBorker);

        // pour identifier la convergence
        List<List<Barycentre>> historique = new ArrayList<>();
        historique.add(mapReduce.getBarycentres());

        boolean convergence = false;
        boolean userEnded = false;
        Scanner scanner = new Scanner(System.in);
        int iteration = 1;
        while (!convergence && !userEnded) {

            System.out.println("ajouté un point ? [y/n]");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "y":
                    System.out.println("entré le point avec le format largeur;prix");
                    String newPoint = scanner.nextLine();
                    Scanner pointScanner = new Scanner(newPoint);
                    pointScanner.useDelimiter(";");
                    if (pointScanner.hasNext()) {
                        int largeur = Integer.parseInt(pointScanner.next());
                        int prix = Integer.parseInt(pointScanner.next());
                        data.add(new Point(largeur, prix));
                        System.out.println("point ajouté : (" + largeur + ", " + prix + ")");
                    }
            }

            // map (puis en parrallèle, shuffle et reduce)
            mapReduce.splitAndMap(data);
            mapReduce.waitForReduceToFinish();

            mapReduce.updateBarycentre();

            // affichage des barycentres aprés la première itération
            System.out.println("barycentres à l'itération " + iteration);
            mapReduce.printBaryCentres();

            historique.add(mapReduce.getBarycentres());

            // check convergence
            if (historique.size() >= 3) {
                if (barycentresEquals(historique.get(historique.size() - 1), historique.get(historique.size() - 2))
                        &&
                        barycentresEquals(historique.get(historique.size() - 2), historique.get(historique.size() - 3))) {
                    convergence = true;
                }
            }

            System.out.println("end ? [y/n]");
            userInput = scanner.nextLine();
            switch (userInput) {
                case "y":
                    userEnded = true;
            }
            iteration++;
        }

        if (convergence) {
            System.out.println("l'algorithme à convergé");
        }

        if (userEnded) {
            System.out.println("fin du programme");
        }
    }

    private static boolean barycentresEquals(List<Barycentre> barycentres1, List<Barycentre> barycentres2) {
        if (barycentres1.size() != barycentres2.size()) {
            return false;
        }

        for (int i = 0; i < barycentres1.size(); i++) {
            if (barycentres1.get(i).getPrix() != barycentres2.get(i).getPrix()
                || barycentres1.get(i).getLargeur() != barycentres2.get(i).getLargeur()) {
                return false;
            }
        }

        return true;
    }
}
