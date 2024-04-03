/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p5.dfs.main;


import uam.ia.p5.dfs.model.PuzzleLinealSG_DFS;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    
    private PuzzleLinealSG_DFS puzzle;


    public Aplicacion() {
        puzzle = new PuzzleLinealSG_DFS();
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }
    
    public void inicia(){
        puzzle.iniciarDFS();
    }
    
}
