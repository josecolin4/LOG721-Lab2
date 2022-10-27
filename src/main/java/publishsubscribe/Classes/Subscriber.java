/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Classes;

import publishsubscribe.Interfaces.IPublication;
import publishsubscribe.Interfaces.ISubscriber;
import publishsubscribe.Interfaces.ITopic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author AP57630
 */
public class Subscriber extends Client implements ISubscriber {


    public Subscriber(long id, int port, int brokerPort) {
        super(id, port, brokerPort);
    }

    @Override
    public void subscribe(ITopic t, IPublication.Format format) {
        try {
            connect();
            ObjectOutputStream oos = new ObjectOutputStream(getSocket().getOutputStream());
            oos.writeObject(RequestType.SUBSCRIBE);
            oos.flush();
            oos.writeObject(new Identity(getId(), getPort(), format));
            oos.flush();
            oos.writeObject(t);
            oos.flush();
            disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribe(ITopic t, IPublication.Format format) {
        try {
            connect();
            ObjectOutputStream oos = new ObjectOutputStream(getSocket().getOutputStream());
            oos.writeObject(RequestType.UNSUBSCRIBE);
            oos.flush();
            oos.writeObject(new Identity(getId(), getPort(), format));
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
                System.out.println(getId() + " : " + getPort() + " à reçu " + publication.getContent());
                s.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
