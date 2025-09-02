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
import domain.Knjiga;
import domain.Primerak;
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

    public void deleteClan(Clan clan) throws Exception {
        Request req = new Request(Operations.DELETE_CLAN, clan);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();

        if (!res.isSuccess()) {
            throw new Exception(res.getMessage());
        }
    }

    public List<Clan> searchClan(String term) throws Exception {
        Request req = new Request(Operations.SEARCH_CLAN, term);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();

        if (res.isSuccess()) {
            return (List<Clan>) res.getParams();
        } else {
            throw new Exception(res.getMessage());
        }
    }

    public void createClan(Clan clan) throws Exception {
        Request req = new Request(Operations.CREATE_CLAN, clan);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();
        if (!res.isSuccess()) {
            throw new Exception(res.getMessage());
        }
    }

    public void updateClan(Clan clan) throws Exception {
        Request req = new Request(Operations.UPDATE_CLAN, clan);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();
        if (!res.isSuccess()) {
            throw new Exception(res.getMessage());
        }
    }

    public List<Knjiga> getAllKnjiga() throws Exception {
        Request req = new Request(Operations.GET_ALL_KNJIGA, null);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();

        if (res.isSuccess()) {
            return (List<Knjiga>) res.getParams();
        } else {
            throw new Exception(res.getMessage());
        }
    }

    public List<Primerak> getPrimerciForKnjiga(Knjiga knjiga) throws Exception {
        Request req = new Request(Operations.GET_PRIMERCI_FOR_KNJIGA, knjiga);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();

        if (res.isSuccess()) {
            return (List<Primerak>) (List<?>) res.getParams();
        } else {
            throw new Exception(res.getMessage());
        }
    }

    public int countPrimerci(int knjigaID) throws Exception {
        Request req = new Request(Operations.COUNT_PRIMERCI, knjigaID);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();
        if (res.isSuccess()) {
            return (int) res.getParams();
        }
        throw new Exception(res.getMessage());
    }

    public int countAvailablePrimerci(int knjigaID) throws Exception {
        Request req = new Request(Operations.COUNT_AVAILABLE_PRIMERCI, knjigaID);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();
        if (res.isSuccess()) {
            return (int) res.getParams();
        }
        throw new Exception(res.getMessage());
    }

    public List<Knjiga> searchKnjiga(String term) throws Exception {
        Request req = new Request(Operations.SEARCH_KNJIGA, term);
        CommunicationWithServer.getInstance().sendRequest(req);
        Response res = CommunicationWithServer.getInstance().receiveResponse();

        if (res.isSuccess()) {
            return (List<Knjiga>) (List<?>) res.getParams();
        } else {
            throw new Exception(res.getMessage());
        }
    }
}
