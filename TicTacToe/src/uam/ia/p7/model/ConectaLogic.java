/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p7.model;

import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;
import uam.ia.p7.main.Conecta4;

/**
 *
 * @author delta9
 */
public class ConectaLogic {
    private Conecta4 control; 
    private Negamax negamax;

    public ConectaLogic(Conecta4 app) {
        this.control = app;
        this.negamax = new Negamax(this);
    }
/**
 * CPU ejecuta su movimiento
 * @param board el tablero actual
 * @param dificultad 
 */    
    public void nextTurn(JButton[][] board, int dificultad){
            int rand,col;
            if(!this.checkFull(control.getTablero())){

/*Si se descomenta el siguiente bloque y se comentan las lineas que invocan los algoritmos
  el juego funciona sin inteligencia y se puede probar que la evaluación funciona, igual que la verificacion de terminal.
  El problema radica en los algoritmos de busqueda el cual ya no pude resolver.
*/
                
                /*                
                if(dificultad == 1){
                    do{ //Si elige una posicion ocupada vuelve a generar otra
                    col = randomNum(board.length); //elige columna  
                }while(!"".equals(board[0][col].getText()));
                    int row = getCasillaLibre(board,col);              
                    board[row][col].setText("X"); //Pone mejor movimiento
                    board[row][col].setBackground(Color.BLUE);                    
                }
*/                
//                    int[] coordenada = negamax.getOptimo(board, dificultad); //Aqui se invoca a negamax
                      int[] coordenada = negamax.getOptimoAlfaBeta(board, dificultad); //aqui se invoca a negamax alfa beta poda
                    board[coordenada[0]][coordenada[1]].setText("X"); //Pone mejor movimiento
                    
                
                
                
                System.out.println("f(n):"+ negamax.f_n(board));
            }
            else
                control.finJuego("El juego termino, ha habido un empate");
            this.checkGanador(board); //Obtiene el tablero de botones como un string
        }
/**
 * Busca en la columna dada la primer casilla vacia que encuentra de abajo hacia arriba
 * @param tablero
 * @param columna
 * @return 
 */    
    public int getCasillaLibre(JButton[][] tablero, int columna){
        for(int i= tablero.length-1; i>=0;i--){
            if( "".equals(tablero[i][columna].getText()) ){
                return i;
            }   
        }
        return -1;
    }
        
    public int randomNum(int size){
        Random r = new Random();
        return r.nextInt(size);
    }
/**
 * Verifica si algun jugador gano o si es empate
 * @param buttons tablero
 */    
    public void checkGanador(JButton[][] buttons){
        int i,j;
        boolean win = false;        
        //Busca Renglones de 4
        for(i=6; i>=0;i--){
            for(j=0; j< 4; j++){
                if(!"".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i][j+1].getText()) && buttons[i][j].getText().equals(buttons[i][j+2].getText()) && buttons[i][j].getText().equals(buttons[i][j+3].getText()) ){
                    buttons[i][j].setBackground(Color.red);
                    buttons[i][j+1].setBackground(Color.red);
                    buttons[i][j+2].setBackground(Color.red);
                    buttons[i][j+3].setBackground(Color.red);
                    if(buttons[i][j].getText().equals("X")){
                        control.finJuego("El juego termino, gana CPU");                    
                }
                else
                    control.finJuego("El juego termino, ganaste");
                win=true;
                break;
                }
            }
        }
        //Busca Columnas de 4
        if(!win){
            for(j=0; j< buttons.length ;j++){
                for(i=0; i< 4; i++){
                    if(!"".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i+1][j].getText()) && buttons[i][j].getText().equals(buttons[i+2][j].getText()) && buttons[i][j].getText().equals(buttons[i+3][j].getText()) ){
                        buttons[i][j].setBackground(Color.red);
                        buttons[i+1][j].setBackground(Color.red);
                        buttons[i+2][j].setBackground(Color.red);
                        buttons[i+3][j].setBackground(Color.red);
                        if(buttons[i][j].getText().equals("X")){
                            control.finJuego("El juego termino, gana CPU");                        
                        }
                        else
                            control.finJuego("El juego termino, ganaste");
                        win=true;
                        break;
                    }
                }
            }
        }
        //Busca Diagonales de 4 con pendiente positiva (empieza del lado izquierdo del tablero y se va recorriendo hacia arriba)
        int m,n=1,p,q=3,r=0;
        if(!win){
                for(i=3;i<buttons.length;i++){ 
                    j=0; p=7;
                    for(m=i; m>(i-n) ;m--){ //Mitad superior
    //                    System.out.print("i:" +i+ " j:"+j +" m:" +m +" n:"+n +" i-n:" + (i-n) +" ");
                        if(!"".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m-1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m-2][j+2].getText()) && buttons[m][j].getText().equals(buttons[m-3][j+3].getText()) ){
                            buttons[m][j].setBackground(Color.red);
                            buttons[m-1][j+1].setBackground(Color.red);
                            buttons[m-2][j+2].setBackground(Color.red);
                            buttons[m-3][j+3].setBackground(Color.red);
                            if(buttons[i][j].getText().equals("X")){
                                control.finJuego("El juego termino, gana CPU");                        
                            }
                            else
                                control.finJuego("El juego termino, ganaste");
                            win=true;
                           break;
                        }
                       if(n<4){
                           p=p-1;
                           r=q+j;

                           if(!"".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p-1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p-2][r+2].getText()) && buttons[p][r].getText().equals(buttons[p-3][r+3].getText()) ){
                                buttons[p][r].setBackground(Color.red);
                                buttons[p-1][r+1].setBackground(Color.red);
                                buttons[p-2][r+2].setBackground(Color.red);
                                buttons[p-3][r+3].setBackground(Color.red);
                               if(buttons[i][j].getText().equals("X")){
                                    control.finJuego("El juego termino, gana CPU");                        
                                }
                               else
                                    control.finJuego("El juego termino, ganaste");
                               win=true;
                               break;
                            }
                        }
    //                    System.out.println(" p:" +p+ " q:" +q + " r:"+r);
                        j++;
                    }
                    n++;q--;
                }
        }       
        //Diagonales con pendiente negativa
        n=1;q=3;
        if(!win){
            for(i=3;i>=0;i--){ 
                j=0; p=0;
                for(m=i; m < i+n ;m++){ //Mitad superior
//                    System.out.print("i:" +i+ " j:"+j +" m:" +m +" n:"+n +" i-n:" + (i-n) +" ");
                    if(!"".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m+2][j+2].getText()) && buttons[m][j].getText().equals(buttons[m+3][j+3].getText()) ){
                        buttons[m][j].setBackground(Color.red);
                        buttons[m+1][j+1].setBackground(Color.red);
                        buttons[m+2][j+2].setBackground(Color.red);
                        buttons[m+3][j+3].setBackground(Color.red);
                        if(buttons[i][j].getText().equals("X")){
                            control.finJuego("El juego termino, gana CPU");                        
                        }
                       else
                            control.finJuego("El juego termino, ganaste");
                       break;
                    } 
                   if(n<4){  
                       r=q+j;
                       if(!"".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p+2][r+2].getText()) && buttons[p][r].getText().equals(buttons[p+3][r+3].getText()) ){
                            buttons[p][r].setBackground(Color.red);
                            buttons[p+1][r+1].setBackground(Color.red);
                            buttons[p+2][r+2].setBackground(Color.red);
                            buttons[p+3][r+3].setBackground(Color.red);
                           if(buttons[i][j].getText().equals("X")){
                                control.finJuego("El juego termino, gana CPU");                        
                            }
                           else
                                control.finJuego("El juego termino, ganaste");
                           break;
                        }
                    }
//                    System.out.println(" p:" +p+ " q:" +q + " r:"+r);
                    p++;
                    j++;
                }
                n++;
                q--;
            }
        }   
        System.out.println("");
    }
    /**
 * Revisa que ya no hay casillas libres
 * @param buttons
 * @return true si el tablero esta lleno
 */    
    public boolean checkFull(String[] buttons){
        for (String button : buttons) {
            if ("".equals(button)) {
                return false;
            }
        }
        return true;
    }
    public String[] getTabAsString(){
        return control.getTablero();
    }
    /**
     * Metodos para hacer pruebas
     * @param buttons 
     */
    public void testF_n(JButton[][] buttons){
        System.out.println(negamax.f_n(buttons));
    }
    public void testNegamax(JButton[][] board){
//        negamax.getOptimo(board, 7);
        negamax.getOptimoAlfaBeta(board, 7);
    }

}
