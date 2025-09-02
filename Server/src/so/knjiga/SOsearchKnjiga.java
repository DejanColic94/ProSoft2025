/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.knjiga;

import domain.Knjiga;
import domain.OpstiDomenskiObjekat;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOsearchKnjiga extends GenericSO {

    private List<OpstiDomenskiObjekat> result;
    private String term;

    public SOsearchKnjiga(String term) {
        this.term = term;
    }

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        String cond = "naziv LIKE '%" + term + "%' OR autor LIKE '%" + term + "%'";
        result = dbb.getWithCondition(new Knjiga(), cond);
    }

    public List<OpstiDomenskiObjekat> getResult() {
        return result;
    }
}
