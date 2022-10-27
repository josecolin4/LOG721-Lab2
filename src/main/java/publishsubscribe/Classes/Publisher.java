/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Classes;

import publishsubscribe.Interfaces.IPublication;
import publishsubscribe.Interfaces.IPublisher;
import publishsubscribe.Interfaces.ITopic;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author AP57630
 */
public class Publisher extends Client implements IPublisher {

    public Publisher(long id, int port, int brokerPort) {
        super(id, port, brokerPort);
    }

    @Override
    public void advertise(ITopic t, IPublication.Format format) {
        try {
            connect();
            ObjectOutputStream oos = new ObjectOutputStream(getSocket().getOutputStream());
            oos.writeObject(RequestType.ADVERTISE);
            oos.flush();
            Identity id = new Identity(getId(), getPort(), format);
            oos.writeObject(id);
            oos.flush();
            oos.writeObject(t);
            oos.flush();
            disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publish(ITopic t, Publication p) {
        try {
            connect();
            ObjectOutputStream oos = new ObjectOutputStream(getSocket().getOutputStream());
            oos.writeObject(RequestType.PUBLISH);
            oos.flush();
            oos.writeObject(new Identity(getId(), getPort()));
            oos.flush();
            oos.writeObject(t);
            oos.flush();
            oos.writeObject(p);
            oos.close();
            disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void unadvertise(ITopic t, IPublication.Format format) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }
}
