/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public abstract class OpstiDomenskiObjekat implements Serializable{
    
    public abstract String getImeTabele();
    public abstract String getParametre();
    public abstract String getPK();
    public abstract int getVrednostPK();
    public abstract List<OpstiDomenskiObjekat> ResultSetIntoTable(ResultSet rs);
    public abstract String getUpdate();
    public abstract void setVrednostPK(int pk);
}
