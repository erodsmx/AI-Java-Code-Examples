/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p3.model;

import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class Rook {
    
    private String[][] tablero;
    private int[] posicion; //La posicion siempre es la del estado actual (nodoActual)


    public Rook(String[][] tab) {
        this.tablero = new String[4][4];
        this.initTab(tab);
        posicion = new int[2];
        this.posicion = getPosicion("T");

    }
    public void desplazar(NodoBFS nodoActual){
        if(tablero[posicion[0]][posicion[1]]=="T"){       
        moverArriba(nodoActual.buscarPieza("T"),nodoActual);
        moverAbajo(nodoActual.buscarPieza("T"),nodoActual);
        moverIzq(nodoActual.buscarPieza("T"),nodoActual);
        moverDer(nodoActual.buscarPieza("T"),nodoActual);
        }
    }
    
    public int[] getPosicion(String pieza){ // "T" = torre,
        int[] posicion = new int[2];
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
               if(tablero[i][j] == pieza){
                   posicion[0] = i;
                   posicion[1] = j;
               }
            }
        }
//        System.out.println("La posicion de la torre es: " + posicion[0] + " " +posicion[1]);
        return posicion;
    }
    public void generarHijo(NodoBFS nodoActual){
        NodoBFS hijo = new NodoBFS();
        hijo.setDato(tablero.clone());
        hijo.setPadre(nodoActual);
        nodoActual.addHijo(hijo);
    }
    public void moverArriba(int[] pos,NodoBFS nodoActual){
        try {
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-i][pos[1]]!="" && i!=0){
                    System.out.println("Torre se come a: " + tablero[pos[0]-i][pos[1]]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-i][pos[1]] = "T";
                    posicion[0] = pos[0]-i;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
//         System.out.println("Movimiento invalido, fuera de tablero");
      }
        
    }
    public void moverAbajo(int[] pos,NodoBFS nodoActual){
        try {
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]+i][pos[1]]!="" && i!=0){
                    System.out.println("Torre se come a: " + tablero[pos[0]+i][pos[1]]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]+i][pos[1]] = "T";
                    posicion[0] = pos[0]+i;
                    generarHijo(nodoActual);
                    break;
                }       
            }   
        } catch(ArrayIndexOutOfBoundsException e) {            
            }
        
    }
    
    public void moverIzq(int[] pos,NodoBFS nodoActual){
        try {
            
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]][pos[1]-i]!="" && i!=0){
                    System.out.println("Torre se come a: " + tablero[pos[0]][pos[1]-i]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]][pos[1]-i] = "T";
                    posicion[1] = pos[1]-i;
                    generarHijo(nodoActual);
                    break;
                }       
            }
      } catch(ArrayIndexOutOfBoundsException e) {
      }
    }
    
    public void moverDer(int[] pos,NodoBFS nodoActual){
        try {
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]][pos[1]+i]!="" && i!=0){
                    System.out.println("Torre se come a: " + tablero[pos[0]][pos[1]+i]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]][pos[1]+i] = "T";
                    posicion[1] = pos[1]+i;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
      }                
    }    
    
     @Override
    public String toString() {
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                if(tablero[i][j] !="")
                System.out.print("| " + tablero[i][j]+" ");
                else
                    System.out.print("| " +"  ");
                
            }
            System.out.print("|");
            System.out.println();
        }
        
        return "";
    }   
    
    private void initTab(String[][] tab){
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                tablero[i][j] = tab[i][j];
            }
        }   
        
    }
    
    
}
