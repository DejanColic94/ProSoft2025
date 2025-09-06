/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import domain.StavkaZaduzenja;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Colic
 */
public class TableModelStavkaZaduzenja extends AbstractTableModel {

    private List<StavkaZaduzenja> lista;
    private final String[] kolone = {"PrimerakID", "Izdavač", "Godina", "Datum razduženja", "Napomena"};
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public TableModelStavkaZaduzenja(List<StavkaZaduzenja> lista) {
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
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaZaduzenja s = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return s.getPrimerak() == null ? "" : s.getPrimerak().getPrimerakID();
            case 1:
                return s.getPrimerak() == null ? "" : s.getPrimerak().getIzdavac();
            case 2:
                return s.getPrimerak() == null ? "" : s.getPrimerak().getGodinaIzdanja();
            case 3:
                return s.getDatumRazduzenja() == null ? "" : sdf.format(s.getDatumRazduzenja());
            case 4:
                return s.getNapomena() == null ? "" : s.getNapomena();
            default:
                return "";
        }
    }

    public StavkaZaduzenja getAt(int row) {
        return lista.get(row);
    }

    public void setLista(List<StavkaZaduzenja> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
}
