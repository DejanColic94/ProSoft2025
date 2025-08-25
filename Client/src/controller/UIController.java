/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dejan Colic
 */
public class UIController {

    private static UIController instance;

    private UIController() {
    }

    public static UIController getInstance() {
        if (instance == null) {
            instance = new UIController();
        }

        return instance;
    }
}
