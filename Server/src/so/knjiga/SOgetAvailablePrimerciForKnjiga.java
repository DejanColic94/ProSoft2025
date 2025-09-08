/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.knjiga;

import domain.Knjiga;
import domain.OpstiDomenskiObjekat;
import domain.Primerak;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOgetAvailablePrimerciForKnjiga extends GenericSO {

    private List<Primerak> result;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Knjiga k = (Knjiga) odo;
        result = dbb.getAvailablePrimerciForKnjiga(k.getKnjigaID());
        for (Primerak p : result) {
            p.setKnjiga(k);
        }
    }

    public List<Primerak> getResult() {
        return result;
    }
}
