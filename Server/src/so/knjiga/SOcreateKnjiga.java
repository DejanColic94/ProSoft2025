/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.knjiga;

import domain.Knjiga;
import domain.Primerak;
import domain.OpstiDomenskiObjekat;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOcreateKnjiga extends GenericSO {

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Knjiga knjiga = (Knjiga) odo;
        dbb.create(knjiga);

        List<Primerak> primerci = knjiga.getListaPrimeraka();
        for (Primerak p : primerci) {
            p.setKnjiga(knjiga);
            dbb.create(p);
        }
    }
}
