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
import domain.Primerak;
import domain.Radnik;
import domain.StavkaZaduzenja;
import domain.Zaduzenje;
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
    private Radnik loggedRadnik;

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
                        Radnik r = (Radnik) result;

                        if (Controller.getInstance().isRadnikLoggedIn(r)) {
                            response.setSuccess(false);
                            response.setMessage("Radnik je već ulogovan.");
                            break;
                        }

                        this.loggedRadnik = r;
                        Controller.getInstance().addUlogovanogRadnika(r);
                        ServerLogger.getInstance().logAction(this.loggedRadnik, "Login successful");

                        response.setParams(result);
                        response.setSuccess(true);
                        response.setMessage("Uspešno ste se prijavili na sistem.");
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom prijave: " + e.getMessage());
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Login failed", e);
                    }
                    break;

                case Operations.LOGOUT:
                    try {
                        Radnik toRemove = this.loggedRadnik;
                        if (toRemove == null && request.getParam() instanceof Radnik) {
                            toRemove = (Radnik) request.getParam();
                        }
                        if (toRemove != null) {
                            Controller.getInstance().removeLoggedRadnik(toRemove);
                            this.loggedRadnik = null;
                        }

                        ServerLogger.getInstance().logAction(this.loggedRadnik, "Logout completed");
                        response.setSuccess(true);
                        response.setMessage("Odjava uspesna.");

                        end = true;
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greska pri odjavi: " + e.getMessage());
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Logout failed", e);
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

                        ServerLogger.getInstance().logAction(this.loggedRadnik,
                                "Obrisan clan: ID=" + clan.getClanID()
                                + ", " + clan.getIme() + " " + clan.getPrezime()
                        );

                    } catch (java.sql.SQLIntegrityConstraintViolationException fkEx) {
                        response.setSuccess(false);
                        response.setMessage("Član ima zaduženja i ne može biti obrisan.");

                        ServerLogger.getInstance().logError(this.loggedRadnik,
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
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Search clan failed", e);
                    }
                    break;
                case Operations.CREATE_CLAN:
                    try {
                        Clan clan = (Clan) request.getParam();
                        Controller.getInstance().createClan(clan);
                        response.setSuccess(true);
                        response.setMessage("Član je uspešno kreiran.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik,
                                "Created clan: " + clan.getIme() + " " + clan.getPrezime()
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom kreiranja člana.");
                        ServerLogger.getInstance().logError(this.loggedRadnik,
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
                        ServerLogger.getInstance().logAction(this.loggedRadnik,
                                "Updated clan: ID=" + clan.getClanID() + ", " + clan.getIme() + " " + clan.getPrezime()
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom izmene člana.");
                        ServerLogger.getInstance().logError(this.loggedRadnik,
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
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Get all knjiga failed", e);
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
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Get primerci for knjiga failed", e);
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
                case Operations.SEARCH_KNJIGA:
                    try {
                        String term = (String) request.getParam();
                        List<OpstiDomenskiObjekat> knjige = Controller.getInstance().searchKnjiga(term);
                        response.setParams(knjige);
                        response.setSuccess(true);
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom pretrage knjiga.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Search knjiga failed", e);
                    }
                    break;
                case Operations.DELETE_KNJIGA:
                    try {
                        Knjiga knjiga = (Knjiga) request.getParam();
                        Controller.getInstance().deleteKnjiga(knjiga);
                        response.setSuccess(true);
                        response.setMessage("Knjiga je uspešno obrisana.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik,
                                "Deleted knjiga: ID=" + knjiga.getKnjigaID() + ", " + knjiga.getNaziv()
                        );
                    } catch (java.sql.SQLIntegrityConstraintViolationException fkEx) {
                        response.setSuccess(false);
                        response.setMessage("Knjiga ima zaduženja i ne može biti obrisana.");
                        ServerLogger.getInstance().logError(this.loggedRadnik,
                                "Delete knjiga failed: book has loans",
                                fkEx
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom brisanja knjige.");
                        ServerLogger.getInstance().logError(this.loggedRadnik,
                                "Delete knjiga failed (unexpected error)",
                                e
                        );
                    }
                    break;
                case Operations.CREATE_KNJIGA:
                    try {
                        Knjiga knjiga = (Knjiga) request.getParam();
                        Controller.getInstance().createKnjiga(knjiga);
                        response.setSuccess(true);
                        response.setMessage("Knjiga je uspešno sačuvana.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik,
                                "Created knjiga: " + knjiga.getNaziv() + " (" + knjiga.getAutor() + ")"
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom čuvanja knjige.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Create knjiga failed", e);
                    }
                    break;
                case Operations.UPDATE_KNJIGA:
                    try {
                        Knjiga knjiga = (Knjiga) request.getParam();
                        Controller.getInstance().updateKnjiga(knjiga);
                        response.setSuccess(true);
                        response.setMessage("Knjiga je uspešno izmenjena.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik,
                                "Updated knjiga: " + knjiga.getNaziv()
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom izmene knjige.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Update knjiga failed", e);
                    }
                    break;
                case Operations.GET_ALL_ZADUZENJE:
                    try {
                        List<Zaduzenje> lista = Controller.getInstance().getAllZaduzenje();
                        response.setSuccess(true);
                        response.setParams(lista);
                        response.setMessage("Zaduženja uspešno učitana.");
                        ServerLogger.getInstance().logAction(
                                this.loggedRadnik,
                                "Pregled svih zaduženja"
                        );
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom učitavanja zaduženja.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Get all zaduzenje failed", e);
                    }
                    break;
                case Operations.GET_STAVKE_FOR_ZADUZENJE:
                    try {
                        Zaduzenje z = (Zaduzenje) request.getParam();
                        List<StavkaZaduzenja> lista = Controller.getInstance().getStavkeForZaduzenje(z);
                        response.setSuccess(true);
                        response.setParams(lista);
                        response.setMessage("Stavke učitane.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik, "Pregled stavki za zaduženje " + z.getZaduzenjeID());
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom učitavanja stavki.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Get stavke failed", e);
                    }
                    break;
                case Operations.CREATE_ZADUZENJE:
                    try {
                        Zaduzenje z = (Zaduzenje) request.getParam();
                        Controller.getInstance().createZaduzenje(z);
                        response.setSuccess(true);
                        response.setMessage("Zaduženje je sačuvano.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik, "Create zaduzenje");
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom čuvanja zaduženja.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Create zaduzenje failed", e);
                    }
                    break;
                case Operations.DELETE_ZADUZENJE:
                    try {
                        Zaduzenje z = (Zaduzenje) request.getParam();
                        Controller.getInstance().deleteZaduzenje(z);
                        response.setSuccess(true);
                        response.setMessage("Zaduženje je obrisano.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik, "Delete zaduzenje ID=" + z.getZaduzenjeID());
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom brisanja zaduženja.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Delete zaduzenje failed", e);
                    }
                    break;
                case Operations.UPDATE_ZADUZENJE:
                    try {
                        Zaduzenje z = (Zaduzenje) request.getParam();
                        Controller.getInstance().updateZaduzenje(z);
                        response.setSuccess(true);
                        response.setMessage("Zaduženje je izmenjeno.");
                        ServerLogger.getInstance().logAction(this.loggedRadnik, "Update zaduzenje ID=" + z.getZaduzenjeID());
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška prilikom izmene zaduženja.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Update zaduzenje failed", e);
                    }
                    break;
                case Operations.GET_AVAILABLE_PRIMERCI_FOR_KNJIGA:
                    try {
                        Knjiga k = (Knjiga) request.getParam();
                        List<Primerak> list = Controller.getInstance().getAvailablePrimerciForKnjiga(k);
                        response.setSuccess(true);
                        response.setParams(list);
                        ServerLogger.getInstance().logAction(this.loggedRadnik,
                                "Get available primerci for knjigaID=" + k.getKnjigaID());
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška pri učitavanju dostupnih primeraka.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Get available primerci failed", e);
                    }
                    break;
                case Operations.GET_ACTIVE_CLAN_IDS:
                    try {
                        List<Integer> ids = Controller.getInstance().getActiveClanIds();
                        response.setParams(ids);
                        response.setSuccess(true);
                        ServerLogger.getInstance().logAction(this.loggedRadnik, "Fetch active clan IDs");
                    } catch (Exception e) {
                        response.setSuccess(false);
                        response.setMessage("Greška pri dohvatanju aktivnih zaduženja.");
                        ServerLogger.getInstance().logError(this.loggedRadnik, "Get active clan IDs failed", e);
                    }
                    break;
                default:
                    response.setSuccess(false);
                    response.setMessage("Nepoznata operacija: " + request.getOperacija());
                    break;
            }
            sendResponse(response);
        }
        if (loggedRadnik != null) {
            Controller.getInstance().removeLoggedRadnik(loggedRadnik);
            loggedRadnik = null;
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
