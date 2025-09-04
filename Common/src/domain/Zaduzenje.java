/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class Zaduzenje extends OpstiDomenskiObjekat {

    private int zaduzenjeID;
    private Date datumZaduzenja;
    private Clan clan;
    private Radnik radnik;
    private StavkaZaduzenja[] stavkeZaduzenja;

    public Zaduzenje(int zaduzenjeID, Date datumZaduzenja, Clan clan, Radnik radnik, StavkaZaduzenja[] stavkeZaduzenja) {
        this.zaduzenjeID = zaduzenjeID;
        this.datumZaduzenja = datumZaduzenja;
        this.clan = clan;
        this.radnik = radnik;
        this.stavkeZaduzenja = stavkeZaduzenja;
    }

    public Zaduzenje() {
    }

    public Zaduzenje(int zaduzenjeID, Date datumZaduzenja, Clan clan, Radnik radnik) {
        this.zaduzenjeID = zaduzenjeID;
        this.datumZaduzenja = datumZaduzenja;
        this.clan = clan;
        this.radnik = radnik;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    public int getZaduzenjeID() {
        return zaduzenjeID;
    }

    public void setZaduzenjeID(int zaduzenjeID) {
        this.zaduzenjeID = zaduzenjeID;
    }

    public Date getDatumZaduzenja() {
        return datumZaduzenja;
    }

    public void setDatumZaduzenja(Date datumZaduzenja) {
        this.datumZaduzenja = datumZaduzenja;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.zaduzenjeID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zaduzenje other = (Zaduzenje) obj;
        return this.zaduzenjeID == other.zaduzenjeID;
    }

    @Override
    public String getImeTabele() {
        return "zaduzenje";
    }

    @Override
    public String getParametre() {
        return String.format("'%s','%s','%s'",
                new java.sql.Date(datumZaduzenja.getTime()),
                radnik.getRadnikID(),
                clan.getClanID());
    }

    @Override
    public String getPK() {
        return "zaduzenjeID";
    }

    @Override
    public int getVrednostPK() {
        return zaduzenjeID;
    }

    @Override
    public List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs) {
        List<OpstiDomenskiObjekat> listZaduzenje = new ArrayList<>();
        try {
            while (rs.next()) {
                int zaduzenjeID = rs.getInt("zaduzenjeID");
                Date datumZaduzenja = new Date(rs.getDate("datumZaduzenja").getTime());
                Radnik radnik = new Radnik();
                radnik.setRadnikID(rs.getInt("radnikID"));
                Clan clan = new Clan();
                clan.setClanID(rs.getInt("clanID"));

                Zaduzenje zaduzenje = new Zaduzenje(zaduzenjeID, datumZaduzenja, clan, radnik);
                listZaduzenje.add(zaduzenje);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greska kod ResultSetIntoTable za Zaduzenje");
        }
        return listZaduzenje;
    }

    @Override
    public String getUpdate() {
        return String.format("datumZaduzenja='%tF', radnikID='%s', clanID='%s'",
                new java.sql.Date(datumZaduzenja.getTime()),
                radnik.getVrednostPK(),
                clan.getVrednostPK()
        );
    }

    @Override
    public void setVrednostPK(int pk) {
        this.zaduzenjeID = pk;
    }

    public StavkaZaduzenja[] getStavkeZaduzenja() {
        return stavkeZaduzenja;
    }

    public void setStavkeZaduzenja(StavkaZaduzenja[] stavkeZaduzenja) {
        this.stavkeZaduzenja = stavkeZaduzenja;
    }

    @Override
    public String getInsertColumns() {
        return "datumZaduzenja, radnikID, clanID";
    }

}
