/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.Primerak;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelPrimerakTemp extends AbstractTableModel {

    private final List<Primerak> primerci = new ArrayList<>();
    private final String[] columns = {"Izdavač", "Godina izdanja"};

    @Override
    public int getRowCount() {
        return primerci.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Primerak p = primerci.get(row);
        switch (col) {
            case 0:
                return p.getIzdavac();
            case 1:
                return p.getGodinaIzdanja();
            default:
                return null;
        }
    }

    public void addPrimerak(Primerak p) {
        primerci.add(p);
        fireTableRowsInserted(primerci.size() - 1, primerci.size() - 1);
    }

    public void removePrimerak(int row) {
        primerci.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public List<Primerak> getPrimerci() {
        return primerci;
    }

    public Primerak getPrimerakAt(int row) {
        if (row >= 0 && row < primerci.size()) {
            return primerci.get(row);
        }
        return null;
    }

    public void updatePrimerak(int row, Primerak primerak) {
        if (row >= 0 && row < primerci.size()) {
            primerci.set(row, primerak);
            fireTableRowsUpdated(row, row);
        }
    }
}
