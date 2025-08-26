/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import communication.Request;
import communication.Response;
import constants.Operations;
import controller.Controller;
import domain.OpstiDomenskiObjekat;
import domain.Radnik;
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
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Radnik ulogovaniRadnik;

    public ClientServiceThread(Socket socket, List<ClientServiceThread> clientList) {
        this.socket = socket;
        this.clientList = clientList;
        try {

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error initializing streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (!end) {
            Request request = acceptRequest();
            Response response = new Response();

            switch (request.getOperacija()) {
                case Operations.LOGIN:
                    try {
                        OpstiDomenskiObjekat param = (OpstiDomenskiObjekat) request.getParam();
                        OpstiDomenskiObjekat result = Controller.getInstance().login(param);

                        this.ulogovaniRadnik = (Radnik) result;
                        Controller.getInstance().addUlogovanogRadnika((Radnik) result);

                        response.setParams(result);
                        response.setSuccess(true);
                        response.setMessage("Uspesno ste se prijavili na sistem.");
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greska prilikom prijave: " + e.getMessage());
                    }
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
            oos.writeObject(response);
            oos.flush();
        } catch (IOException ex) {
            System.out.println("Error in sending out a Response object: " + ex.getMessage());
        }
    }

    public Request acceptRequest() {
        try {
            return (Request) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error in accepting Request object: " + ex.getMessage());
            return null;
        }
    }

}
