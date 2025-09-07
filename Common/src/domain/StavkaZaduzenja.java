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
public class StavkaZaduzenja extends OpstiDomenskiObjekat {

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
        String datum = datumRazduzenja == null ? "NULL" : String.format("'%tF'", datumRazduzenja);
        String note = (napomena == null || napomena.isBlank()) ? "NULL" : "'" + napomena.replace("'", "''") + "'";
        return String.format("%d, %d, %s, %s, %d",
                zaduzenje.getZaduzenjeID(),
                stavkaID,
                datum,
                note,
                primerak.getPrimerakID());
    }

    @Override
    public String getPK() {
        return "zaduzenjeID,stavkaID";
    }

    public String getCompositePK() {
        return "zaduzenjeID, stavkaID";
    }

    @Override
    public int getVrednostPK() {
        return stavkaID;
    }

    public int[] getVrednostCompositePK() {
        return new int[]{zaduzenje.getVrednostPK(), stavkaID};
    }

    @Override
    public List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs) {
        List<OpstiDomenskiObjekat> list = new ArrayList<>();
        try {
            while (rs.next()) {
                StavkaZaduzenja s = new StavkaZaduzenja();
                Zaduzenje z = new Zaduzenje();
                z.setZaduzenjeID(rs.getInt("zaduzenjeID"));
                s.setZaduzenje(z);
                s.setStavkaID(rs.getInt("stavkaID"));
                java.sql.Date d = rs.getDate("datumRazduzenja");
                s.setDatumRazduzenja(d == null ? null : new Date(d.getTime()));
                s.setNapomena(rs.getString("napomena"));
                Primerak p = new Primerak();
                p.setPrimerakID(rs.getInt("primerakID"));
                s.setPrimerak(p);
                list.add(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public String getUpdate() {
        String datum = datumRazduzenja == null ? "NULL" : String.format("'%tF'", datumRazduzenja);
        String note = (napomena == null || napomena.isBlank()) ? "NULL" : "'" + napomena.replace("'", "''") + "'";
        return String.format("datumRazduzenja=%s, napomena=%s, primerakID=%d",
                datum, note, primerak.getPrimerakID());
    }

    @Override
    public void setVrednostPK(int pk) {
        this.stavkaID = pk;
    }

    @Override
    public String getInsertColumns() {
        return "zaduzenjeID, stavkaID, datumRazduzenja, napomena, primerakID";
    }
}
