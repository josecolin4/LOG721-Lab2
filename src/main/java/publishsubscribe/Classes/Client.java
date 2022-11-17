/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Classes;

import publishsubscribe.Interfaces.IClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author AP57630
 */
public class Client implements IClient {

    private long id;
    private int port, brokerPort;
    private Socket socket;

    private ObjectOutputStream oos;

    public Client(long id, int port, int brokerPort) {
        this.id = id;
        this.port = port;
        this.brokerPort = brokerPort;
    }

    @Override
    public void connect() throws IOException {
        try {
            if (socket == null) {
                socket = new Socket("localhost", brokerPort);
                oos = new ObjectOutputStream(getSocket().getOutputStream());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public int getPort() {
        return port;
    }

    public long getId() {
        return id;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }
}
