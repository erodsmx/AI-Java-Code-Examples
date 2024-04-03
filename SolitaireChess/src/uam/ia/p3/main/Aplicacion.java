/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p3.main;

import uam.ia.p3.model.*;
import uam.ia.p3.view.MainWindow;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    private MainWindow Ventana;
    private SolitaireChessBFS chess;
    private SolitaireChessDFS chessDFS;
    

    public Aplicacion() {
        this.chess = new SolitaireChessBFS();
        this.chessDFS = new SolitaireChessDFS();
    }
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }
    public void inicia(){
        Ventana = new MainWindow(this);
        Ventana.setVisible(true);
    }
    public void iniciarBFS(){
        chess.iniciarBFS();   
    }
    public void iniciarDFS(){
        chessDFS.iniciarDFS();   
    }

    
}
