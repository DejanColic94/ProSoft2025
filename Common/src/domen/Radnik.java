/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class Radnik extends OpstiDomenskiObjekat{
    private int radnikID;
    private String ime;
    private String prezime;
    private String username;
    private String password;

    public Radnik() {
    }

    public Radnik(int radnikID, String ime, String prezime, String username, String password) {
        this.radnikID = radnikID;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRadnikID() {
        return radnikID;
    }

    public void setRadnikID(int radnikID) {
        this.radnikID = radnikID;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.radnikID;
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
        final Radnik other = (Radnik) obj;
        return this.radnikID == other.radnikID;
    }

    @Override
    public String toString() {
        return ""+ime+" "+prezime;
    }

    @Override
    public String getImeTabele() {
       return "radnik";
    }

    @Override
    public String getParametre() {
         return String.format("'%s', '%s', '%s', '%s', '%s'", radnikID, ime, prezime, username, password);
    }

    @Override
    public String getPK() {
        return "radnikID";
    }

    @Override
    public int getVrednostPK() {
       return radnikID;
    }

    @Override
    public List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs) {
        List<OpstiDomenskiObjekat> listRadnik = new ArrayList<>();
        try {
            while (rs.next()) {
                int radnikID = rs.getInt("radnikID");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String username = rs.getString("username");
                String password = rs.getString("password");

                Radnik radnik = new Radnik(radnikID, ime, prezime, username, password);
                listRadnik.add(radnik);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greska kod ResultSetIntoTable za Radnika");
        }
        return listRadnik;
    }

    @Override
    public String getUpdate() {
        return String.format("ime='%s', prezime='%s',username='%s', password='%s'", ime,prezime,username,password);
    }

    @Override
    public void setVrednostPK(int pk) {
        this.radnikID = pk;
    }
    
    
}
