/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.Zaduzenje;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelZaduzenje extends AbstractTableModel {

    private List<Zaduzenje> lista;
    private String[] kolone = {"Član", "Radnik", "Datum"};

    public TableModelZaduzenje(List<Zaduzenje> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista == null ? 0 : lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zaduzenje z = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return z.getClan().getIme() + " " + z.getClan().getPrezime();
            case 1:
                return z.getRadnik().getIme() + " " + z.getRadnik().getPrezime();
            case 2:
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
                return sdf.format(z.getDatumZaduzenja());
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public Zaduzenje getZaduzenjeAt(int row) {
        return lista.get(row);
    }

    public void setLista(List<Zaduzenje> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
}
