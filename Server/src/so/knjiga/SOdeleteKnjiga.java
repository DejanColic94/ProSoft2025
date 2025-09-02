/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.knjiga;

import so.GenericSO;
import domain.OpstiDomenskiObjekat;

/**
 *
 * @author Dejan Colic
 */
public class SOdeleteKnjiga extends GenericSO {

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        dbb.delete(odo);
    }
}
