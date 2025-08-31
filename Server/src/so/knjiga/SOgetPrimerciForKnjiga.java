/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.knjiga;

import so.GenericSO;
import domain.Primerak;
import domain.OpstiDomenskiObjekat;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class SOgetPrimerciForKnjiga extends GenericSO {

    private List<OpstiDomenskiObjekat> result;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        Primerak primerak = (Primerak) odo;
        String cond = "knjigaID=" + primerak.getKnjiga().getKnjigaID();
        result = dbb.getWithCondition(new Primerak(), cond);
        for (OpstiDomenskiObjekat o : result) {
            Primerak p = (Primerak) o;
            p.setDostupan(dbb.isPrimerakAvailable(p.getPrimerakID()));
        }
    }

    public List<OpstiDomenskiObjekat> getResult() {
        return result;
    }
}
