/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p7.main;

import javax.swing.JButton;
import uam.ia.p7.model.ConectaLogic;
import uam.ia.p7.view.JFrameConecta;

/**
 *
 * @author delta9
 */
public class Conecta4 {
    private JFrameConecta ventana;
    private ConectaLogic conecta;

    public Conecta4() {
        this.conecta = new ConectaLogic(this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conecta4 conecta = new Conecta4();
        conecta.inicia();
    
    }
    private void inicia(){
        System.out.println("Conecta 4");
        ventana = new JFrameConecta(this);
        ventana.setVisible(true);
        /**
         * Al descomentar la siguiente linea se puede observar en la ventana como el algoritmo esta probando todos los casos
         * me di cuenta que esta buscando el optimo global y no el local
         * no supe como meter una restricción para que solo evaluara hasta cierto numero y no se la pasara todo el tiempo probando casos
         */
        conecta.testNegamax(ventana.testNegamax()); //Con esta linea pruebo un movimiento inicial
    }

    public void finJuego(String mensaje){
        ventana.finJuego(mensaje);
    }
    public String[] getTablero(){
        return ventana.getButtonsAsString();
    }    

    public void nextTurn(JButton[][] buttons, int dificultad) {
        conecta.nextTurn(buttons, dificultad);
    }
    public void checkGanador(JButton[][] buttons){
        conecta.checkGanador(buttons);
    }
}
