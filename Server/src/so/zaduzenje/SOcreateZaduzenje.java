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
public class SOcreateZaduzenje extends GenericSO {

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Zaduzenje z = (Zaduzenje) odo;
        dbb.create(z);

        StavkaZaduzenja[] stavke = z.getStavkeZaduzenja();
        if (stavke == null || stavke.length == 0) {
            return;
        }

        int next = dbb.getNextStavkaIDForZaduzenje(z.getZaduzenjeID());
        for (StavkaZaduzenja s : stavke) {
            s.setZaduzenje(z);
            if (s.getStavkaID() <= 0) {
                s.setStavkaID(next++);
            }
            dbb.create(s);
        }
    }
}
