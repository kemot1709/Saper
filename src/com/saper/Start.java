package com.saper;

import javax.swing.*;

/**
 * Saper
 *
 * @author Tomasz Indeka
 */
public class Start {
    
    /**
     * Funkcja main
     *
     * @param args parametry maina
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        Visual visual = new Visual(controller);
        Model model = new Model(controller);
        controller.addMVC(visual, model);
    }
}
