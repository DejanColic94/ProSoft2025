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
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private CommunicationWithServer() {
        try {
            this.socket = new Socket("localhost", 8189);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error in connecting with server socket!");
            ex.printStackTrace();
        }
    }

    public static CommunicationWithServer getInstance() {
        if (instance == null) {
            instance = new CommunicationWithServer();
        }

        return instance;
    }

    public void sendRequest(Request request) throws IOException {
        oos.writeObject(request);
        oos.flush();
    }

    public Response receiveResponse() throws IOException, ClassNotFoundException {
        return (Response) ois.readObject();
    }

    public void close() {
        try {
            if (ois != null) {
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (oos != null) {
                oos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
