/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import communication.Request;
import communication.Response;
import constants.Operations;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class ClientServiceThread extends Thread {

    private Socket socket;
    private List<ClientServiceThread> clientList;
    private boolean end = false;

    public ClientServiceThread(Socket socket, List<ClientServiceThread> clientList) {
        this.socket = socket;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        while (!end) {
            Request request = acceptRequest();
            Response response = new Response();

            switch (request.getOperacija()) {
                case Operations.LOGIN:

                    break;
                default:
                    throw new AssertionError();
            }
            sendResponse(response);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public List<ClientServiceThread> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientServiceThread> clientList) {
        this.clientList = clientList;
    }

    public void sendResponse(Response response) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(response);
        } catch (IOException ex) {
            System.out.println("Error in sending out a Response object.");
        }
    }

    private Request acceptRequest() {
        ObjectInputStream ois = null;
        Request request = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            request = (Request) ois.readObject();
        } catch (Exception ex) {
            System.out.println("Error in accepting Request object.");
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                System.out.println("Error in closing input stream.");
            }
        }
        return request;
    }

}
