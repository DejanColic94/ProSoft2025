/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.clan;


import domain.OpstiDomenskiObjekat;
import java.util.List;
import so.GenericSO;

/**
 *
 * @author Dejan Colic
 */
public class SOgetAllClan extends GenericSO {

    private List<OpstiDomenskiObjekat> result;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        result = dbb.getAll(odo);
    }

    public List<OpstiDomenskiObjekat> getResult() {
        return result;
    }
}
