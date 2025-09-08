/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.zaduzenje;

import domain.OpstiDomenskiObjekat;
import domain.StavkaZaduzenja;
import domain.Zaduzenje;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOupdateZaduzenje extends GenericSO {

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Zaduzenje z = (Zaduzenje) odo;
        dbb.update(z);
        dbb.deleteStavkeForZaduzenje(z.getZaduzenjeID());
        StavkaZaduzenja[] stavke = z.getStavkeZaduzenja();
        if (stavke == null || stavke.length == 0) {
            return;
        }
        int counter = 1;
        for (StavkaZaduzenja s : stavke) {
            s.setZaduzenje(z);
            s.setStavkaID(counter++);
            dbb.create(s);
        }
    }
}
