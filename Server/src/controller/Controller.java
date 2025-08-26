/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.OpstiDomenskiObjekat;
import so.GenericSO;
import so.login.SOLogin;


/**
 *
 * @author Dejan Colic
 */
public class Controller {
    private static Controller instance;

   private Controller() {
    }
    
    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        
        return instance;
    }
    
     public OpstiDomenskiObjekat login(OpstiDomenskiObjekat odo) throws Exception {
        SOLogin so = new SOLogin();
        so.execute(odo);
        return so.getResult();
    }
}
