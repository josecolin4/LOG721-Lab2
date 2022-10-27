package publishsubscribe.Classes;

import publishsubscribe.Interfaces.IBroker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author AP57630
 */
public class Broker implements IBroker {

    private long id;
    private int port;
    private List<Topic> topics;
    private ServerSocket socketServer;

    private List<Map.Entry<Identity, Topic>> waitingList;

    public Broker(long id, int port) {
        this.id = id;
        this.port = port;
        topics = new ArrayList<>();
        waitingList = new ArrayList<>();
    }

    @Override
    public void listenToNetwork() throws IOException {
        socketServer = new ServerSocket(port);
        for (;;) {
            Socket s = socketServer.accept();
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            try {
                RequestType requestType = (RequestType) ois.readObject();
                if (requestType == RequestType.ADVERTISE) {
                    // DEBUG
                    System.out.println("addAdvertiser");
                    addAdvertiser(ois);
                } else if (requestType == RequestType.PUBLISH) {
                    // DEBUG
                    System.out.println("publish");
                    publish(ois);

                } else if (requestType == RequestType.UNSUBSCRIBE) {
                    // DEBUG
                    System.out.println("unsubscribe");
                    unsubscribe(ois);
                } else {
                    // DEBUG
                    System.out.println("addSubscriber");
                    addSubsriber(ois);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            s.close();
        }
    }

    public void addAdvertiser(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Identity identity = (Identity) ois.readObject();
        Topic topicToAdd = (Topic) ois.readObject();
        if (!topics.contains(topicToAdd)) {
            topics.add(topicToAdd);
            topicToAdd.addPublisher(identity);
        } else {
            topicToAdd = topics.get(topics.indexOf(topicToAdd));
            topicToAdd.addPublisher(identity);
        }

        for (Map.Entry<Identity, Topic> waiting : waitingList) {
            if (topicToAdd.isCompatible(waiting.getValue().getName())) {
                topicToAdd.addSubscriber(waiting.getKey());
            }
        }
    }

    public void addSubsriber(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Identity identity = (Identity) ois.readObject();
        Topic topicToSubscribe = (Topic) ois.readObject();

        // check if topicToSubscribe match any of the current publisher's topic
        for (Topic topic : topics) {
            if (topic.isCompatible(topicToSubscribe.getName())) {
                topic.addSubscriber(identity);
                return;
            }
        }

        // if not match, put them in the waiting list, until it match a new publisher's topic
        waitingList.add(new AbstractMap.SimpleEntry<>(identity, topicToSubscribe));
    }

    public void publish(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Identity identity = (Identity) ois.readObject();
        Topic topic = (Topic) ois.readObject();

        // check si le publisher à utilisé la méthode advertise pour ce topic
        Topic topicToPublish = topics.get(topics.indexOf(topic));
        if (topicToPublish != null && topicToPublish.getPub().contains(identity)) {

            Publication publication = (Publication) ois.readObject();

            for (Identity sub : topicToPublish.getSub()) {
                Socket socket = new Socket("localhost", sub.getPort());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(publication);
                oos.flush();
                oos.close();
                socket.close();
            }
        } else {
            // pas de transmition de la publication
            // TODO error message to publisher
            ois.readObject();
        }
    }

    public void unsubscribe(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Identity identity = (Identity) ois.readObject();
        Topic topicToUnsubscribe = (Topic) ois.readObject();

        for (Topic topic : topics) {
            if (topic.isCompatible(topicToUnsubscribe.getName())) {
                topic.getSub().remove(identity);
                return;
            }
        }
    }
}
