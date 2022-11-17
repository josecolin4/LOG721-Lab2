package kmoyenne;

import com.google.common.collect.Lists;
import publishsubscribe.Classes.Publication;
import publishsubscribe.Classes.Publisher;
import publishsubscribe.Classes.Subscriber;
import publishsubscribe.Classes.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MapReduce {

    private List<Barycentre> barycentres;

    private CountDownLatch compteur;

    /**
     * Association de chaque point avec un barycentre (shuffle).
     * A chaque fois qu'un point est ajouté, on met à jour la copie du barycentre (reduce)
     * en prenant en compte les coordonnées du nouveau point.
     * Quand tout les points ont été ajouté, on met à jour le vrai barycentre avec la valeur de
     * la copie.
     */
    private Map<Barycentre, CurrentGroup> currentGroups;

    private List<Publisher> mappers;
    private List<Subscriber> reducers;

    public MapReduce(List<Barycentre> barycentres) {
        this.barycentres = barycentres;
        mappers = new ArrayList<>();
        reducers = new ArrayList<>();
        currentGroups = new HashMap<>();
    }

    public void addMapper(Publisher mapper) {
        mappers.add(mapper);
    }

    public void addReducerForEachBarycentre(int portBorker) {
        for (int i = 0; i < barycentres.size(); i++) {
            Topic topic = new Topic(barycentres.get(i).getId());
            addReducer(new Subscriber(200 + i, 6000 + i, portBorker), topic);
            Barycentre barycentreCopy = barycentres.get(i).clone();
            currentGroups.put(barycentreCopy, new CurrentGroup());
        }
    }
    public void addReducer(Subscriber reducer, Topic topic) {
        reducers.add(reducer);
        reducer.setFunction(this::suffleAndReduce);
        new Thread() {
            public void run() {
                reducer.subscribe(topic);
                reducer.listentoBroker();
            }
        }.start();
    }

    public void splitAndMap(List<Point> points) {
        compteur = new CountDownLatch(points.size());
        // distribue les tâches sur les mappers
        List<List<Point>> subdivisions = new ArrayList<>();
        for (int i = 0; i < mappers.size(); i++) {
            subdivisions.add(new ArrayList<>());
        }
        int i = 0;
        for (Point point : points) {
            subdivisions.get(i).add(point);
            i++;
            if (i >= subdivisions.size()) {
                i = 0;
            }
        }

        i = 0;
        for (List<Point> subdivision : subdivisions) {
            Publisher reducer = mappers.get(i);
            Thread thread = new Thread() {
                public void run() {
                    for (Point point : subdivision) {
                        MapResult result = map(point);
                        Topic topic = new Topic(result.getBarycentre().getId());
                        reducer.advertise(topic);
                        reducer.publish(topic, new Publication(topic, result));
                    }
                }
            };
            thread.start();
            i++;
        }
    }

    public void waitForReduceToFinish() {
        try {
            compteur.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public MapResult map(Point point) {
        Barycentre bestBarycentre = barycentres.get(0);
        double bestDistance = point.distance(bestBarycentre);
        for (int i = 1; i < barycentres.size(); i++) {
            double distance = point.distance(barycentres.get(i));
            if (distance < bestDistance) {
                bestBarycentre = barycentres.get(i);
                bestDistance = distance;
            }
        }


        return new MapResult(bestBarycentre, point);
    }

    public Void suffleAndReduce(MapResult mapResult) {
        CurrentGroup currentGroup = currentGroups.get(mapResult.getBarycentre());
        currentGroup.reduce(mapResult);
        compteur.countDown();
        return null;
    }

    public void updateBarycentre() {
        for (Barycentre barycentre : barycentres) {
            barycentre.setLargeur(currentGroups.get(barycentre).getCurrentLargeur());
            barycentre.setPrix(currentGroups.get(barycentre).getCurrentPrix());
        }

        // reset for next iteration
        currentGroups.clear();
        for (int i = 0; i < barycentres.size(); i++) {
            Barycentre barycentreCopy = barycentres.get(i).clone();
            currentGroups.put(barycentreCopy, new CurrentGroup());
        }
    }

    public void printBaryCentres() {
        for (Barycentre barycentre : barycentres) {
            System.out.println("barycentre " + barycentre.getId() + " --> largeur : " + barycentre.getLargeur()
                    + ", prix : " + barycentre.getPrix());
        }
    }

    public List<Barycentre> getBarycentres() {
        List<Barycentre> cloneBarycentres = new ArrayList<>();
        for(Barycentre barycentre : barycentres) {
            cloneBarycentres.add(barycentre.clone());
        }
        return cloneBarycentres;
    }
}
