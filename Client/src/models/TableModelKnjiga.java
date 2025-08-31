/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.Knjiga;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelKnjiga extends AbstractTableModel {

    private final List<Knjiga> knjige;
    private final String[] columns = {"Naziv", "Autor"};

    public TableModelKnjiga(List<Knjiga> knjige) {
        this.knjige = knjige;
    }

    @Override
    public int getRowCount() {
        return knjige == null ? 0 : knjige.size();
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
        Knjiga k = knjige.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return k.getNaziv();
            case 1:
                return k.getAutor();
            default:
                return null;
        }
    }

    public Knjiga getKnjigaAt(int row) {
        return knjige.get(row);
    }
}
