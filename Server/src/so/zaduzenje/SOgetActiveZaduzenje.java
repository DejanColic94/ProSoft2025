/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.zaduzenje;

import domain.OpstiDomenskiObjekat;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOgetActiveZaduzenje extends GenericSO {

    private List<Integer> ids;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        ids = dbb.getActiveClanIds();
    }

    public List<Integer> getIds() {
        return ids;
    }

}
