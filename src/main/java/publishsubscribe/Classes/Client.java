/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscribe.Classes;

import publishsubscribe.Interfaces.IClient;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author AP57630
 */
public class Client implements IClient {

    private long id;
    private int port, brokerPort;
    private Socket socket;

    public Client(long id, int port, int brokerPort) {
        this.id = id;
        this.port = port;
        this.brokerPort = brokerPort;
    }

    @Override
    public void connect() throws IOException {
        try {
            socket = new Socket("localhost", brokerPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            socket.close();
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
}
