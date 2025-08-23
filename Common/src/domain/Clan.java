/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class Clan extends OpstiDomenskiObjekat {

    private int clanID;
    private String ime;
    private String prezime;
    private String telefon;
    private String email;

    public Clan() {
    }

    public Clan(int clanID, String ime, String prezime, String telefon, String email) {
        this.clanID = clanID;
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getClanID() {
        return clanID;
    }

    public void setClanID(int clanID) {
        this.clanID = clanID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.clanID;
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
        final Clan other = (Clan) obj;
        return this.clanID == other.clanID;
    }

    @Override
    public String toString() {
        return "" + ime + " " + prezime;
    }

    @Override
    public String getImeTabele() {
        return "clan";
    }

    @Override
    public String getParametre() {
        return String.format("'%s', '%s', '%s', '%s', '%s'", clanID, ime, prezime, telefon, email);
    }

    @Override
    public String getPK() {
        return "clanID";
    }

    @Override
    public int getVrednostPK() {
        return clanID;
    }

    @Override
    public List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs) {
        List<OpstiDomenskiObjekat> listClanovi = new ArrayList<>();
        try {
            while (rs.next()) {
                int clanID = rs.getInt("clanID");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String telefon = rs.getString("telefon");
                String email = rs.getString("email");

                Clan clan = new Clan(clanID, ime, prezime, telefon, email);
                listClanovi.add(clan);
            }
        } catch (SQLException ex) {
            System.out.println("Greska kod  ResultSetIntoTable za clanove");
            ex.printStackTrace();

        }
        return listClanovi;
    }

    @Override
    public String getUpdate() {
        return String.format("ime='%s', prezime='%s', telefon='%s', email='%s'", ime, prezime, telefon, email);
    }

    @Override
    public void setVrednostPK(int pk) {
        this.clanID = pk;
    }

}
