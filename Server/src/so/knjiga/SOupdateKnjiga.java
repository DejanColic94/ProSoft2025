/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.knjiga;

import domain.Knjiga;
import domain.OpstiDomenskiObjekat;
import domain.Primerak;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOupdateKnjiga extends GenericSO {

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Knjiga k = (Knjiga) odo;
        dbb.update(k);

        List<OpstiDomenskiObjekat> rows = dbb.getWithCondition(new Primerak(), "knjigaID=" + k.getKnjigaID());
        List<Primerak> existing = new ArrayList<>();
        for (OpstiDomenskiObjekat o : rows) {
            existing.add((Primerak) o);
        }

        List<Primerak> incoming = k.getListaPrimeraka();
        Map<Integer, Primerak> incomingById = new HashMap<>();
        for (Primerak p : incoming) {
            incomingById.put(p.getPrimerakID(), p);
        }

        for (Primerak e : existing) {
            if (!incomingById.containsKey(e.getPrimerakID())) {
                if (!dbb.isPrimerakAvailable(e.getPrimerakID())) {
                    throw new Exception("Primerak nije moguće obrisati jer je zadužen");
                }
                dbb.delete(e);
            }
        }

        for (Primerak p : incoming) {
            p.setKnjiga(k);
            if (p.getPrimerakID() > 0) {
                dbb.update(p);
            } else {
                dbb.create(p);
            }
        }
    }
}
