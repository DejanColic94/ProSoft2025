/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.zaduzenje;

import domain.OpstiDomenskiObjekat;
import domain.Zaduzenje;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOgetAllZaduzenje extends GenericSO {

    private List<OpstiDomenskiObjekat> list;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        String select = "z.zaduzenjeID, z.datumZaduzenja, "
                + "r.radnikID, r.ime AS imeRadnik, r.prezime AS prezimeRadnik, "
                + "c.clanID,   c.ime AS imeClan,   c.prezime AS prezimeClan";
        
        String join = "z JOIN clan c ON z.clanID = c.clanID "
                + "JOIN radnik r ON z.radnikID = r.radnikID";
        list = dbb.getAllWithJoin(new Zaduzenje(), select, join);
    }

    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }
}
