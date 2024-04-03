/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p3.model;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class NodoBFS {
    String[][] dato;
    LinkedList<NodoBFS> hijos;
    NodoBFS padre;

    public NodoBFS() {
        this.dato = new String[4][4];
        this.initArray();
        this.padre = null;
        this.hijos = new LinkedList<>();
    }
            
    public NodoBFS getPadre() {
        return padre;
    }

    public void setPadre(NodoBFS padre) {
        this.padre = padre;
    }

    public String[][] getDato() {
        return dato;
    }

    public void setDato(String[][] dato) {
        this.dato = dato;
    }

    public void setHijos(LinkedList<NodoBFS> hijos) {
        this.hijos = hijos;
    }
    
    public void addHijo(NodoBFS hijo){
        hijos.add(hijo);
    }
    
    public LinkedList<NodoBFS> getHijos() {
        return hijos;
    }
    
    @Override
    public String toString() {
//        System.out.println("El tablero del nodo es:");
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                if(dato[i][j] !="")
                System.out.print("| " + dato[i][j]+" ");
                else
                    System.out.print("| " +"  ");
                
            }
            System.out.print("|");
            System.out.println();
        }
        
        return "";
    }
    
    public String getTableroAsString(){
        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                if(dato[i][j]== "")
                    strBuilder.append(".");
                else
                strBuilder.append(dato[i][j]);
            }
        }
        return strBuilder.toString();
    }
    
    private void initArray(){
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                dato[i][j]="";
            }
        }
    }
    
    public int[] buscarPieza(String pieza){
        int[] posicion = new int[2];
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                if(pieza.equals(dato[i][j])){
                    posicion[0] = i;
                    posicion[1] = j;
                    return posicion;
                }
            }
        }
        return posicion;        
    }

}
