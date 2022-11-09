/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Classes;

import kmoyenne.MapReduce;
import kmoyenne.MapResult;
import publishsubscribe.Interfaces.IPublication;
import publishsubscribe.Interfaces.ISubscriber;
import publishsubscribe.Interfaces.ITopic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

/**
 * @author AP57630
 */
public class Subscriber extends Client implements ISubscriber {

    /** called when a publication is received */
    private Function<MapResult, Void> function;
    public Subscriber(long id, int port, int brokerPort) {
        super(id, port, brokerPort);
    }

    @Override
    public void subscribe(ITopic t) {
        try {
            connect();
            ObjectOutputStream oos = new ObjectOutputStream(getSocket().getOutputStream());
            oos.writeObject(RequestType.SUBSCRIBE);
            oos.flush();
            oos.writeObject(new Identity(getId(), getPort()));
            oos.flush();
            oos.writeObject(t);
            oos.flush();
            disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribe(ITopic t) {
        try {
            connect();
            ObjectOutputStream oos = new ObjectOutputStream(getSocket().getOutputStream());
            oos.writeObject(RequestType.UNSUBSCRIBE);
            oos.flush();
            oos.writeObject(new Identity(getId(), getPort()));
            oos.flush();
            oos.writeObject(t);
            oos.flush();
            disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listentoBroker() {
        try {
            ServerSocket serverSocket = new ServerSocket(getPort());
            for (;;) {
                Socket s = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Publication publication = (Publication) ois.readObject();
                function.apply(publication.getContent());
                s.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFunction(Function<MapResult, Void> function) {
        this.function = function;
    }
}
