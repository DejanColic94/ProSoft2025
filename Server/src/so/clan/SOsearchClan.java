/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.clan;

import domain.Clan;
import domain.OpstiDomenskiObjekat;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOsearchClan extends GenericSO {

    private List<OpstiDomenskiObjekat> result;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Clan c = (Clan) odo;
        String term = c.getIme();
        if (term == null) {
            term = "";
        }

        String condition = "ime LIKE '%" + term + "%' OR prezime LIKE '%" + term + "%'";
        result = dbb.getWithCondition(new Clan(), condition);
    }

    public List<OpstiDomenskiObjekat> getResult() {
        return result;
    }
}
