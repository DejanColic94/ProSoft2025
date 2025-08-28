/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.clan;

import domain.OpstiDomenskiObjekat;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOupdateClan extends GenericSO {

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        dbb.update(odo);
    }
}
