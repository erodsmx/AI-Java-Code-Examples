/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p7.main;

import javax.swing.JButton;
import uam.ia.p7.model.TicTacLogic;
import uam.ia.p7.view.JFrameToe;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    private JFrameToe ventana;
    private TicTacLogic gato;

    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();//En la ventana principal se elige la dificultad
    }

    public Aplicacion() {
        gato = new TicTacLogic(this);

    }
    public void inicia(){
        abrirMenu();
    }
    /*Carga la ventana principal*/
    public void abrirMenu(){
        ventana = new JFrameToe(this);
        ventana.setVisible(true);
        
    }
    public void nextTurn(JButton[] buttons, int dificultad){
        gato.nextTurn(buttons, dificultad);
    }
    
    public String[] getTablero(){
        return ventana.getButtonsAsString();
    }

    public void finJuego(String mensaje){
        ventana.finJuego(mensaje);
    }
    public void checkSol(){
        gato.checkGanador(this.getTablero());
    }
}
