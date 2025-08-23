/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Dejan Colic
 */
public class StavkaZaduzenja extends OpstiDomenskiObjekat{
    private Zaduzenje zaduzenje;
    private int stavkaID;
    private Date datumRazduzenja;
    private String napomena;
    private Primerak primerak;

    public StavkaZaduzenja() {
    }

    public StavkaZaduzenja(Zaduzenje zaduzenje, int stavkaID, Date datumRazduzenja, String napomena, Primerak primerak) {
        this.zaduzenje = zaduzenje;
        this.stavkaID = stavkaID;
        this.datumRazduzenja = datumRazduzenja;
        this.napomena = napomena;
        this.primerak = primerak;
    }

    public Primerak getPrimerak() {
        return primerak;
    }

    public void setPrimerak(Primerak primerak) {
        this.primerak = primerak;
    }

    public Zaduzenje getZaduzenje() {
        return zaduzenje;
    }

    public void setZaduzenje(Zaduzenje zaduzenje) {
        this.zaduzenje = zaduzenje;
    }

    public int getStavkaID() {
        return stavkaID;
    }

    public void setStavkaID(int stavkaID) {
        this.stavkaID = stavkaID;
    }

    public Date getDatumRazduzenja() {
        return datumRazduzenja;
    }

    public void setDatumRazduzenja(Date datumRazduzenja) {
        this.datumRazduzenja = datumRazduzenja;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.zaduzenje);
        hash = 11 * hash + this.stavkaID;
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
        final StavkaZaduzenja other = (StavkaZaduzenja) obj;
        if (this.stavkaID != other.stavkaID) {
            return false;
        }
        return Objects.equals(this.zaduzenje, other.zaduzenje);
    }

    @Override
    public String getImeTabele() {
        return "stavkaZaduzenja";
    }

    @Override
    public String getParametre() {
         return String.format("'%s', '%s', '%s', '%s','%s'", zaduzenje.getVrednostPK(),stavkaID,datumRazduzenja,napomena,primerak.getVrednostPK());
    }

    @Override
    public String getPK() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String getCompositePK() {
        return "zaduzenjeID, stavkaID";
    }

    @Override
    public int getVrednostPK() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public int[] getVrednostCompositePK() {
       return new int[] { zaduzenje.getVrednostPK(), stavkaID };
    }

    @Override
    public List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs) {
        List<OpstiDomenskiObjekat> listStavki = new ArrayList<>();
    try {
        while (rs.next()) {
            int zaduzenjeID = rs.getInt("zaduzenjeID");
            int stavkaID = rs.getInt("stavkaID");
            Date datumRazduzenja = null;
            java.sql.Date sqlDatum = rs.getDate("datumRazduzenja");
            if (sqlDatum != null) {
                datumRazduzenja = new Date(sqlDatum.getTime());
            }
            String napomena = rs.getString("napomena");
            int primerakID = rs.getInt("primerakID");

            Zaduzenje zaduzenje = new Zaduzenje();
            zaduzenje.setZaduzenjeID(zaduzenjeID);

            Primerak primerak = new Primerak();
            primerak.setPrimerakID(primerakID);

            StavkaZaduzenja sz = new StavkaZaduzenja(zaduzenje, stavkaID, datumRazduzenja, napomena, primerak);
            listStavki.add(sz);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("Greska kod ResultSetIntoTable za StavkaZaduzenja");
    }
    return listStavki;
    }

    @Override
    public String getUpdate() {
       return String.format("datumRazduzenja='%s', napomena='%s', primerakID='%s'",
            new java.sql.Date(datumRazduzenja.getTime()), napomena, primerak.getVrednostPK());
    }

    @Override
    public void setVrednostPK(int pk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
