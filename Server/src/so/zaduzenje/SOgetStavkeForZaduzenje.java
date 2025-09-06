/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.zaduzenje;

import domain.OpstiDomenskiObjekat;
import domain.Primerak;
import domain.StavkaZaduzenja;
import domain.Zaduzenje;
import java.util.ArrayList;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOgetStavkeForZaduzenje extends GenericSO {

    private List<OpstiDomenskiObjekat> list;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Zaduzenje z = (Zaduzenje) odo;
        List<OpstiDomenskiObjekat> rows = dbb.getWithCondition(new StavkaZaduzenja(), "zaduzenjeID=" + z.getZaduzenjeID());
        
        list = new ArrayList<>();
        
        for (OpstiDomenskiObjekat o : rows) {
            StavkaZaduzenja s = (StavkaZaduzenja) o;
            int pid = s.getPrimerak().getPrimerakID();
            List<OpstiDomenskiObjekat> primerakRows = dbb.getWithCondition(new Primerak(), "primerakID=" + pid);
            if (!primerakRows.isEmpty()) {
                s.setPrimerak((Primerak) primerakRows.get(0));
            }
            s.setZaduzenje(z);
            list.add(s);
        }
        
    }

    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }
}
