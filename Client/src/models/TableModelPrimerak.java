/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.Primerak;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelPrimerak extends AbstractTableModel {

    private final List<Primerak> primerci;
    private final String[] columns = {"Izdavač", "Godina izdanja"};

    public TableModelPrimerak(List<Primerak> primerci) {
        this.primerci = primerci;
    }

    @Override
    public int getRowCount() {
        return primerci == null ? 0 : primerci.size();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Primerak p = primerci.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getIzdavac();
            case 1:
                return p.getGodinaIzdanja();
            default:
                return null;
        }
    }

    public Primerak getPrimerakAt(int row) {
        return primerci.get(row);
    }
}
