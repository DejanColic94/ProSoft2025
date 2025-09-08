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
        StavkaZaduzenja[] stavke = z.getStavkeZaduzenja();
        if (stavke != null) {
            for (StavkaZaduzenja s : stavke) {
                if (!dbb.isPrimerakAvailable(s.getPrimerak().getPrimerakID())) {
                    throw new Exception("Primerak " + s.getPrimerak().getPrimerakID() + " nije dostupan.");
                }
            }
        }
        dbb.create(z);
        if (stavke != null && stavke.length > 0) {
            int ordinal = 1;
            for (StavkaZaduzenja s : stavke) {
                s.setZaduzenje(z);
                s.setStavkaID(ordinal++);
                dbb.create(s);
            }
        }
    }
}
