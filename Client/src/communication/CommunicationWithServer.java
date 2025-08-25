/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 *
 * @author Dejan Colic
 */
public class CommunicationWithServer {

    private static CommunicationWithServer instance;
    private Socket socket;

    private CommunicationWithServer() {
        try {
            this.socket = new Socket("localhost", 8189);
        } catch (IOException ex) {
            System.out.println("Error in connecting with server socket!");
            ex.printStackTrace();
        }
    }

    public CommunicationWithServer getInstance() {
        if (instance == null) {
            instance = new CommunicationWithServer();
        }

        return instance;
    }

    public Response receiveResponse() {
        Response response = new Response();
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            response = (Response) ois.readObject();
        } catch (IOException ex) {
            System.out.println("Error in receiveResponse");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error in receiveResponse");
            ex.printStackTrace();
        }
        return response;
    }

    public void sendRequest(Request request) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
        } catch (IOException ex) {
            System.out.println("Error in sendRequest");
            ex.printStackTrace();
        }
    }
}
