/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Clan;
import domain.Knjiga;
import domain.OpstiDomenskiObjekat;
import domain.Primerak;
import domain.Radnik;
import domain.Zaduzenje;
import java.util.ArrayList;
import java.util.List;
import models.TableModelRadnik;
import so.clan.SOcreateClan;
import so.clan.SOdeleteClan;
import so.clan.SOgetAllClan;
import so.clan.SOsearchClan;
import so.clan.SOupdateClan;
import so.knjiga.SOcountAvailablePrimerci;
import so.knjiga.SOcountPrimerci;
import so.knjiga.SOcreateKnjiga;
import so.knjiga.SOdeleteKnjiga;
import so.knjiga.SOgetAllKnjiga;
import so.knjiga.SOgetPrimerciForKnjiga;
import so.knjiga.SOsearchKnjiga;
import so.knjiga.SOupdateKnjiga;
import so.login.SOLogin;
import so.zaduzenje.SOgetAllZaduzenje;

/**
 *
 * @author Dejan Colic
 */
public class Controller {

    private static Controller instance;
    private List<Radnik> ulogovaniRadnici;
    private TableModelRadnik modelRadnik;

    private Controller() {
        ulogovaniRadnici = new ArrayList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public List<Radnik> getUlogovaniRadnici() {
        return ulogovaniRadnici;
    }

    public void setUlogovaniRadnici(List<Radnik> radnici) {
        this.ulogovaniRadnici = radnici;
    }

    public void addUlogovanogRadnika(Radnik r) {
        ulogovaniRadnici.add(r);
        if (modelRadnik != null) {
            modelRadnik.fireTableDataChanged();
        }
    }

    public void removeLoggedRadnik(Radnik r) {
        ulogovaniRadnici.remove(r);
        if (modelRadnik != null) {
            modelRadnik.fireTableDataChanged();
        }
    }

    public void setModelRadnik(TableModelRadnik model) {
        this.modelRadnik = model;
    }

    public OpstiDomenskiObjekat login(OpstiDomenskiObjekat odo) throws Exception {
        SOLogin so = new SOLogin();
        so.execute(odo);
        return so.getResult();
    }

    public List<OpstiDomenskiObjekat> getAllClan() throws Exception {
        SOgetAllClan so = new SOgetAllClan();
        so.execute(new Clan());
        return so.getResult();
    }

    public void deleteClan(OpstiDomenskiObjekat clan) throws Exception {
        SOdeleteClan so = new SOdeleteClan();
        so.execute(clan);
    }

    public List<OpstiDomenskiObjekat> searchClan(String term) throws Exception {
        Clan c = new Clan();
        c.setIme(term);
        c.setPrezime(term);
        SOsearchClan so = new SOsearchClan();
        so.execute(c);
        return so.getResult();
    }

    public void createClan(OpstiDomenskiObjekat clan) throws Exception {
        SOcreateClan so = new SOcreateClan();
        so.execute(clan);
    }

    public void updateClan(OpstiDomenskiObjekat clan) throws Exception {
        SOupdateClan so = new SOupdateClan();
        so.execute(clan);
    }

    public List<OpstiDomenskiObjekat> getAllKnjiga() throws Exception {
        SOgetAllKnjiga so = new SOgetAllKnjiga();
        so.execute(new Knjiga());
        return so.getResult();
    }

    public List<OpstiDomenskiObjekat> getPrimerciForKnjiga(OpstiDomenskiObjekat knjiga) throws Exception {
        Primerak p = new Primerak();
        p.setKnjiga((Knjiga) knjiga);
        SOgetPrimerciForKnjiga so = new SOgetPrimerciForKnjiga();
        so.execute(p);
        return so.getResult();
    }

    public int countPrimerci(int knjigaID) throws Exception {
        SOcountPrimerci so = new SOcountPrimerci(knjigaID);
        so.execute(new domain.Knjiga());
        return so.getCount();
    }

    public int countAvailablePrimerci(int knjigaID) throws Exception {
        SOcountAvailablePrimerci so = new SOcountAvailablePrimerci(knjigaID);
        so.execute(new domain.Knjiga());
        return so.getCount();
    }

    public List<OpstiDomenskiObjekat> searchKnjiga(String term) throws Exception {
        SOsearchKnjiga so = new SOsearchKnjiga(term);
        so.execute(new Knjiga());
        return so.getResult();
    }

    public void deleteKnjiga(OpstiDomenskiObjekat knjiga) throws Exception {
        SOdeleteKnjiga so = new SOdeleteKnjiga();
        so.execute(knjiga);
    }

    public void createKnjiga(Knjiga knjiga) throws Exception {
        SOcreateKnjiga so = new SOcreateKnjiga();
        so.execute(knjiga);
    }

    public void updateKnjiga(Knjiga knjiga) throws Exception {
        SOupdateKnjiga so = new SOupdateKnjiga();
        so.execute(knjiga);
    }

    public List<Zaduzenje> getAllZaduzenje() throws Exception {
        SOgetAllZaduzenje so = new SOgetAllZaduzenje();
        so.execute(new Zaduzenje());
        List<OpstiDomenskiObjekat> result = so.getList();
        List<Zaduzenje> lista = new ArrayList<>();
        for (OpstiDomenskiObjekat o : result) {
            lista.add((Zaduzenje) o);
        }
        return lista;
    }
}
