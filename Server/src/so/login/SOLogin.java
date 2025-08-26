/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.login;

import so.GenericSO;
import domain.OpstiDomenskiObjekat;
import domain.Radnik;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class SOLogin extends GenericSO {

    private Radnik ulogovaniRadnik;

    @Override
    protected void executeSO(OpstiDomenskiObjekat odo) throws Exception {
        List<OpstiDomenskiObjekat> listaRadnika = dbb.getAll(odo);

        Radnik inputRadnik = (Radnik) odo;

        for (OpstiDomenskiObjekat o : listaRadnika) {
            Radnik r = (Radnik) o;
            if (r.getUsername().equals(inputRadnik.getUsername())
                    && r.getPassword().equals(inputRadnik.getPassword())) {
                ulogovaniRadnik = r;
                return;
            }
        }

        throw new Exception("Radnik not found. Invalid credentials.");
    }

    public Radnik getResult() {
        return ulogovaniRadnik;
    }
}
