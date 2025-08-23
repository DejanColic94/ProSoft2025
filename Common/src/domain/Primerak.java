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
public class Primerak extends OpstiDomenskiObjekat {

    private int primerakID;
    private String izdavac;
    private int godinaIzdanja;
    private Knjiga knjiga;

    public Primerak() {
    }

    public Primerak(int primerakID, String izdavac, int godinaIzdanja, Knjiga knjiga) {
        this.primerakID = primerakID;
        this.izdavac = izdavac;
        this.godinaIzdanja = godinaIzdanja;
        this.knjiga = knjiga;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public int getPrimerakID() {
        return primerakID;
    }

    public void setPrimerakID(int primerakID) {
        this.primerakID = primerakID;
    }

    public String getIzdavac() {
        return izdavac;
    }

    public void setIzdavac(String izdavac) {
        this.izdavac = izdavac;
    }

    public int getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public void setGodinaIzdanja(int godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.primerakID;
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
        final Primerak other = (Primerak) obj;
        return this.primerakID == other.primerakID;
    }

    @Override
    public String getImeTabele() {
        return "primerak";
    }

    @Override
    public String getParametre() {
        return String.format("'%s', '%s','%s', '%s'", primerakID, izdavac, godinaIzdanja, knjiga.getPK());
    }

    @Override
    public String getPK() {
        return "primerakID";
    }

    @Override
    public int getVrednostPK() {
        return primerakID;
    }

    @Override
    public List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs) {
        List<OpstiDomenskiObjekat> listPrimerak = new ArrayList<>();
        try {
            while (rs.next()) {
                int primerakID = rs.getInt(("primerakID"));
                String izdavac = rs.getString("izdavac");
                int godinaIzdanja = rs.getInt("godinaIzdanja");

                Knjiga knjiga = new Knjiga(rs.getInt("knjigaID"), null, null);

                Primerak primerak = new Primerak(primerakID, izdavac, godinaIzdanja, knjiga);
                listPrimerak.add(primerak);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greska kod ResultSetIntoTable za Primerke");
        }
        return listPrimerak;
    }

    @Override
    public String getUpdate() {
        return String.format("izdavac='%s',godinaIzdanja='%s',knjigaID='%s'", izdavac,godinaIzdanja,knjiga.getVrednostPK());
    }

    @Override
    public void setVrednostPK(int pk) {
        this.primerakID = pk;
    }

}
