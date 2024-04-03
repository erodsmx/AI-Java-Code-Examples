/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaAE8Puzzle;




/**
 *
 * @author delta9
 */
public class Aplicacion {
    
    private PuzzleAE puzzle;


    public Aplicacion() {
        puzzle = new PuzzleAE(this);
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }
    
    public void inicia(){
        puzzle.iniciarAE();
    }
    
}
