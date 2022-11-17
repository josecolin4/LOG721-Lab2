package kmoyenne.test;

import kmoyenne.Barycentre;
import kmoyenne.MapReduce;
import kmoyenne.Point;
import kmoyenne.utils.CsvUtils;
import publishsubscribe.Classes.Broker;
import publishsubscribe.Classes.Publisher;

import java.io.IOException;
import java.util.List;

public class Test1Iteration {

    public static final int NB_MAPPERS = 4;

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
        for (int i = 0; i < NB_MAPPERS; i++) {
            mapReduce.addMapper(new Publisher(100 + i, 5000 + i, portBorker));
        }

        // un Reducer par barycentre
        mapReduce.addReducerForEachBarycentre(portBorker);

        // map (puis en parrallèle, shuffle et reduce)
        mapReduce.splitAndMap(data);
        mapReduce.waitForReduceToFinish();

        mapReduce.updateBarycentre();

        // affichage des barycentres aprés la première itération
        System.out.println("barycentres à l'itération 1");
        mapReduce.printBaryCentres();
    }
}
