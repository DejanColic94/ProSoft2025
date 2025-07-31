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
public class Knjiga extends OpstiDomenskiObjekat{
    private int knjigaID;
    private String naziv;
    private String autor;

    public Knjiga() {
    }

    public Knjiga(int knjigaID, String naziv, String autor) {
        this.knjigaID = knjigaID;
        this.naziv = naziv;
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getKnjigaID() {
        return knjigaID;
    }

    public void setKnjigaID(int knjigaID) {
        this.knjigaID = knjigaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.knjigaID;
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
        final Knjiga other = (Knjiga) obj;
        return this.knjigaID == other.knjigaID;
    }

    @Override
    public String toString() {
        return "naziv= " + naziv;
    }

    @Override
    public String getImeTabele() {
       return "knjiga";
    }

    @Override
    public String getParametre() {
         return String.format(" '%s', '%s','%s'", knjigaID, naziv, autor);
    }

    @Override
    public String getPK() {
        return "knjigaID";
    }

    @Override
    public int getVrednostPK() {
        return knjigaID;
    }

    @Override
    public List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs) {
       List<OpstiDomenskiObjekat> listKnjiga = new ArrayList<>();
        try {
            while (rs.next()) {
                int knjigaID = rs.getInt("knjigaID");
                String naziv = rs.getString("naziv");
                String autor = rs.getString("autor");
                
                Knjiga knjiga = new Knjiga(knjigaID, naziv, autor);
                listKnjiga.add(knjiga);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greska kod ResultSetIntoTable za Knjige");
        }
        return listKnjiga;
    }

    @Override
    public String getUpdate() {
        return String.format("naziv='%s', autor='%s'", naziv,autor);
    }

    @Override
    public void setVrednostPK(int pk) {
       this.knjigaID = pk;
    }
    
}
