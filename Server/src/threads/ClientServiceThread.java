/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import communication.Request;
import communication.Response;
import constants.Operations;
import controller.Controller;
import domain.Clan;
import domain.Knjiga;
import domain.OpstiDomenskiObjekat;
import domain.Radnik;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import util.ServerLogger;

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
            if (request == null) {
                break;
            }
            Response response = new Response();

            switch (request.getOperacija()) {
                case Operations.LOGIN:
                    try {
                        OpstiDomenskiObjekat param = (OpstiDomenskiObjekat) request.getParam();
                        OpstiDomenskiObjekat result = Controller.getInstance().login(param);

                        this.ulogovaniRadnik = (Radnik) result;
                        Controller.getInstance().addUlogovanogRadnika((Radnik) result);
                        ServerLogger.getInstance().logAction(this.ulogovaniRadnik, "Login successful");

                        response.setParams(result);
                        response.setSuccess(true);
                        response.setMessage("Uspesno ste se prijavili na sistem.");
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greska prilikom prijave: " + e.getMessage());
                        ServerLogger.getInstance().logError(this.ulogovaniRadnik, "Login failed", e);
                    }
                    break;
                case Operations.LOGOUT:
                    try {
                        Radnik toRemove = this.ulogovaniRadnik;
                        if (toRemove == null && request.getParam() instanceof Radnik) {
                            toRemove = (Radnik) request.getParam();
                        }
                        if (toRemove != null) {
                            Controller.getInstance().deleteUlogovanogRadnika(toRemove);
                            this.ulogovaniRadnik = null;
                        }

                        util.ServerLogger.getInstance().logAction(this.ulogovaniRadnik, "Logout completed");
                        response.setSuccess(true);
                        response.setMessage("Odjava uspesna.");

                        end = true;
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greska pri odjavi: " + e.getMessage());
                        ServerLogger.getInstance().logError(this.ulogovaniRadnik, "Logout failed", e);
                    }
                    break;
                case Operations.GET_ALL_CLAN:
                    try {
                        List<OpstiDomenskiObjekat> result = Controller.getInstance().getAllClan();
                        response.setParams(result);
                        response.setSuccess(true);
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greska prilikom ucitavanja clanova: " + e.getMessage());
                    }
                    break;
                case Operations.DELETE_CLAN:
                    try {
                        Controller.getInstance().deleteClan((OpstiDomenskiObjekat) request.getParam());
                        response.setSuccess(true);
                        response.setMessage("Clan uspesno obrisan.");

                        Clan clan = (Clan) request.getParam();

                        util.ServerLogger.getInstance().logAction(
                                this.ulogovaniRadnik,
                                "Obrisan clan: ID=" + clan.getClanID()
                                + ", " + clan.getIme() + " " + clan.getPrezime()
                        );

                    } catch (java.sql.SQLIntegrityConstraintViolationException fkEx) {
                        response.setSuccess(false);
                        response.setMessage("Član ima zaduženja i ne može biti obrisan.");

                        util.ServerLogger.getInstance().logError(
                                this.ulogovaniRadnik,
                                "Delete clan failed: member has existing loans",
                                fkEx
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greska prilikom brisanja clana: " + e.getMessage());
                    }
                    break;
                case Operations.SEARCH_CLAN:
                    try {
                        String term = (String) request.getParam();
                        List<OpstiDomenskiObjekat> result = Controller.getInstance().searchClan(term);
                        response.setParams(result);
                        response.setSuccess(true);
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom pretrage članova.");
                        util.ServerLogger.getInstance().logError(this.ulogovaniRadnik, "Search clan failed", e);
                    }
                    break;
                case Operations.CREATE_CLAN:
                    try {
                        Clan clan = (Clan) request.getParam();
                        Controller.getInstance().createClan(clan);
                        response.setSuccess(true);
                        response.setMessage("Član je uspešno kreiran.");
                        util.ServerLogger.getInstance().logAction(
                                this.ulogovaniRadnik,
                                "Created clan: " + clan.getIme() + " " + clan.getPrezime()
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom kreiranja člana.");
                        util.ServerLogger.getInstance().logError(
                                this.ulogovaniRadnik,
                                "Create clan failed",
                                e
                        );
                    }
                    break;
                case Operations.UPDATE_CLAN:
                    try {
                        Clan clan = (Clan) request.getParam();
                        Controller.getInstance().updateClan(clan);
                        response.setSuccess(true);
                        response.setMessage("Član je uspešno izmenjen.");
                        util.ServerLogger.getInstance().logAction(
                                this.ulogovaniRadnik,
                                "Updated clan: ID=" + clan.getClanID() + ", " + clan.getIme() + " " + clan.getPrezime()
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom izmene člana.");
                        util.ServerLogger.getInstance().logError(
                                this.ulogovaniRadnik,
                                "Update clan failed",
                                e
                        );
                    }
                    break;
                case Operations.GET_ALL_KNJIGA:
                    try {
                        List<OpstiDomenskiObjekat> knjige = Controller.getInstance().getAllKnjiga();
                        response.setParams(knjige);
                        response.setSuccess(true);
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom učitavanja knjiga.");
                        util.ServerLogger.getInstance().logError(this.ulogovaniRadnik, "Get all knjiga failed", e);
                    }
                    break;
                case Operations.GET_PRIMERCI_FOR_KNJIGA:
                    try {
                        Knjiga knjiga = (Knjiga) request.getParam();
                        List<OpstiDomenskiObjekat> primerci = Controller.getInstance().getPrimerciForKnjiga(knjiga);
                        response.setParams(primerci);
                        response.setSuccess(true);
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom učitavanja primeraka.");
                        util.ServerLogger.getInstance().logError(this.ulogovaniRadnik, "Get primerci for knjiga failed", e);
                    }
                    break;
                case Operations.COUNT_PRIMERCI:
                    try {
                        int knjigaID = (Integer) request.getParam();
                        int cnt = Controller.getInstance().countPrimerci(knjigaID);
                        response.setParams(cnt);
                        response.setSuccess(true);
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška pri brojanju primeraka.");
                    }
                    break;

                case Operations.COUNT_AVAILABLE_PRIMERCI:
                    try {
                        int knjigaID = (Integer) request.getParam();
                        int cnt = Controller.getInstance().countAvailablePrimerci(knjigaID);
                        response.setParams(cnt);
                        response.setSuccess(true);
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška pri brojanju dostupnih primeraka.");
                    }
                    break;
                default:
                    response.setSuccess(false);
                    response.setMessage("Nepoznata operacija: " + request.getOperacija());
                    break;
            }
            sendResponse(response);
        }
        if (ulogovaniRadnik != null) {
            Controller.getInstance().deleteUlogovanogRadnika(ulogovaniRadnik);
            ulogovaniRadnik = null;
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
