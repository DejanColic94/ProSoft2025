/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

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
}
