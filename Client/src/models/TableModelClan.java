/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.Clan;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelClan extends AbstractTableModel {

    private final String[] columns = {"ID", "Ime", "Prezime", "Telefon", "Email"};
    private List<Clan> clanovi;

    public TableModelClan(List<Clan> clanovi) {
        this.clanovi = clanovi;
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
    public Object getValueAt(int row, int col) {
        Clan c = clanovi.get(row);
        switch (col) {
            case 0:
                return c.getClanID();
            case 1:
                return c.getIme();
            case 2:
                return c.getPrezime();
            case 3:
                return c.getTelefon();
            case 4:
                return c.getEmail();
            default:
                return null;
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
}
