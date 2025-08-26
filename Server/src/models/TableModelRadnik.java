/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.Radnik;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelRadnik extends AbstractTableModel {

    private final String[] columns = {"ID", "Ime", "Prezime", "Username"};
    private List<Radnik> radnici;

    public TableModelRadnik(List<Radnik> radnici) {
        this.radnici = radnici;
    }

    public TableModelRadnik() {
    }
    
    

    @Override
    public int getRowCount() {
        return radnici.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Radnik r = radnici.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return r.getRadnikID();
            case 1:
                return r.getIme();
            case 2:
                return r.getPrezime();
            case 3:
                return r.getUsername();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void addRadnika(Radnik r) {
        radnici.add(r);
        fireTableDataChanged();
    }

    public void removeRadnika(Radnik r) {
        radnici.remove(r);
        fireTableDataChanged();
    }

    public List<Radnik> getRadnici() {
        return radnici;
    }

    public void setRadnici(List<Radnik> radnici) {
        this.radnici = radnici;
        fireTableDataChanged();
    }
}
