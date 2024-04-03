/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p7.model;

import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import uam.ia.p7.main.Aplicacion;

/**
 *
 * @author delta9
 */
public class TicTacLogic {
    private Aplicacion control;
    private int rand;
    private Minimax minmax;

        public TicTacLogic(Aplicacion app) {
        this.control = app;
        this.rand =0;
        this.minmax = new Minimax();
    }

/**
 * Cuando es el turno del CPU se ejecuta el método colocando una X en la casilla elegida
 * @param board 
 */
    public void nextTurn(JButton[] board, int dificultad){
            if(!minmax.checkFull(control.getTablero())){
                int coordenada = minmax.getOptimo(control.getTablero(), dificultad);
                board[coordenada].setText("X"); //Pone mejor movimientp
/*
                do{ //Si elige la misma posicion vuelve a generar otra para no pintar sobre la misma
                    rand = randomNum(board.length);                
                }while(!"".equals(board[rand].getText()));
                board[rand].setText("X"); //CPU marca con X
*/
            }
            else{
                control.finJuego("El juego termino, ha habido un empate");
            }
            this.checkGanador(control.getTablero()); //Obtiene el tablero de botones como un string
        }

            /*Genera numero aleatorio*/
    public int randomNum(int size){
        Random r = new Random();
        return r.nextInt(size);
    }
/*  /**
     * En el turno del jugador se invoca checkSolucion pasa saber si se ha ganado.
     * @param tablero 
     */
    public void checkGanador(String[] tablero){ //Cada jugador le pasa el tablero en su estado actual
        int i;
        boolean win = false;
        for(i=0; i < 7; i+=3){//Renglones
            if(tablero[i].equals(tablero[i+1]) && tablero[i].equals(tablero[i+2]) && !"".equals(tablero[i]) ){
                if(tablero[i].equals("X"))
                    control.finJuego("El juego termino, gana CPU");
                else
                    control.finJuego("El juego termino, ganaste");
                win = true;
                break;
            }
        }
        if(!win){ //Si un renglon ganó ya no revisa el resto
            for(i=0; i< 3; i++){ //Columnas
                if(tablero[i].equals(tablero[i+3]) && tablero[i].equals(tablero[i+6]) && !"".equals(tablero[i])){
                    if(tablero[i].equals("X")) //CPU
                        control.finJuego("El juego termino, gana CPU");
                    else
                        control.finJuego("El juego termino, ganaste");
                    win=true;
                    break;
                }
            }  
        }
        if(!win){ //Si un renglon o una columna ganó ya no revisa diagonales
            if(tablero[0].equals(tablero[4]) && tablero[0].equals(tablero[8]) && !"".equals(tablero[0])){               
                if(tablero[0].equals("X")) //CPU
                    control.finJuego("El juego termino, gana CPU");
                else
                    control.finJuego("El juego termino, ganaste");           
            }
            if(tablero[6].equals(tablero[4]) && tablero[6].equals(tablero[2]) && !"".equals(tablero[6])){               
                if(tablero[6].equals("X")) //CPU
                    control.finJuego("El juego termino, gana CPU");
                else
                    control.finJuego("El juego termino, ganaste");           
            }
        }     
    }
    
    public void copyBoardSon(String[] tableroPadre, String[] tableroHijo){
        for(int i= 0; i<tableroPadre.length; i++){
                tableroHijo[i]= tableroPadre[i];
            
        }
    }

}
