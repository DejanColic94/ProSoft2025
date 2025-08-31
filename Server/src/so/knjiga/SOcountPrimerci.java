/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.knjiga;

import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOcountPrimerci extends GenericSO {

    private int count;
    private final int knjigaID;

    public SOcountPrimerci(int knjigaID) {
        this.knjigaID = knjigaID;
    }

    @Override
    protected void executeSO(domain.OpstiDomenskiObjekat odo) throws Exception {
        count = dbb.countPrimerciForKnjiga(knjigaID);
    }

    public int getCount() {
        return count;
    }
}
