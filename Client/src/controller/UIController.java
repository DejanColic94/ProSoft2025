/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import communication.CommunicationWithServer;
import communication.Request;
import communication.Response;
import constants.Operations;
import domain.Clan;
import domain.Radnik;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class UIController {

    private static UIController instance;

    private UIController() {
    }

    public static UIController getInstance() {
        if (instance == null) {
            instance = new UIController();
        }

        return instance;
    }

    public Radnik login(Radnik radnik) throws Exception {
        Request request = new Request(Operations.LOGIN, radnik);
        CommunicationWithServer.getInstance().sendRequest(request);

        Response response = CommunicationWithServer.getInstance().receiveResponse();

        if (response.isSuccess()) {
            return (Radnik) response.getParams();
        } else {
            throw new Exception("Login failed: " + response.getMessage());
        }
    }

    public void logout(Radnik radnik) throws Exception {
        Request request = new Request(Operations.LOGOUT, radnik);
        CommunicationWithServer.getInstance().sendRequest(request);
        Response response = CommunicationWithServer.getInstance().receiveResponse();

        if (!response.isSuccess()) {
            throw new Exception("Logout failed: " + response.getMessage());
        }

        CommunicationWithServer.getInstance().close();
    }

    public List<Clan> getAllClan() throws Exception {
        Request req = new Request(Operations.GET_ALL_CLAN, new Clan());
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();

        if (res.isSuccess()) {
            return (List<Clan>) res.getParams();
        } else {
            throw new Exception(res.getMessage());
        }
    }
}
