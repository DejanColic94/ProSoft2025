/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.Clan;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelClan extends AbstractTableModel {

    private final String[] columns = {"Ime", "Prezime", "Telefon", "Email", "Ima zaduženja"};
    private List<Clan> clanovi;
    private Set<Integer> aktivni;

    public TableModelClan(List<Clan> clanovi, Set<Integer> aktivni) {
        this.clanovi = clanovi;
        this.aktivni = aktivni == null ? Collections.emptySet() : aktivni;
    }

    public TableModelClan(List<Clan> clanovi) {
        this(clanovi, Collections.emptySet());
    }

    @Override
    public int getRowCount() {
        return clanovi == null ? 0 : clanovi.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Clan c = clanovi.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getIme();
            case 1:
                return c.getPrezime();
            case 2:
                return c.getTelefon() == null ? "" : c.getTelefon();
            case 3:
                return c.getEmail() == null ? "" : c.getEmail();
            case 4:
                return aktivni != null && aktivni.contains(c.getClanID()) ? "DA" : "NE";
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setClanovi(List<Clan> clanovi) {
        this.clanovi = clanovi;
        fireTableDataChanged();
    }

    public Clan getClanAt(int row) {
        return clanovi.get(row);
    }

    public void updateClan(Clan clan) {
        for (int i = 0; i < clanovi.size(); i++) {
            if (clanovi.get(i).getClanID() == clan.getClanID()) {
                clanovi.set(i, clan);
                fireTableRowsUpdated(i, i);
                return;
            }
        }
    }

    public void setAktivni(Set<Integer> aktivni) {
        this.aktivni = aktivni == null ? Collections.emptySet() : aktivni;
        fireTableDataChanged();
    }
}
