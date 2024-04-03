/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaAE8Puzzle;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class NodoAE {
    private String[][] dato;
    private LinkedList<NodoAE> hijos;
    private NodoAE padre;
    private int h1_n; //Tejas mal acomodadas
    private int h2_n; //Distancia Manhattan
    private int h3_n; //h2(n) + 3*S(n)
    private int f_n;
    private int g_n;

    public NodoAE() {
            this.dato = new String[3][3];
            this.padre = null;
            this.hijos = new LinkedList<>();
            this.h1_n = 0;
            this.h2_n = 0;
            this.h3_n = 0;
            this.f_n = 0;
            this.g_n = 0;            
    }

    public int getG_n() {
        return g_n;
    }

    public void setG_n(int g_n) {
        this.g_n = g_n;
    }

    public String[][] getDato() {
        return dato;
    }

    public void setDatos(String[][] dato) {
        this.dato = dato;
    }

    public LinkedList<NodoAE> getHijos() {
        return hijos;
    }

    public void setHijos(LinkedList<NodoAE> hijos) {
        this.hijos = hijos;
    }

    public void addHijo(NodoAE nodo){
        this.hijos.add(nodo);
        
    }
    public NodoAE getPadre() {
        return padre;
    }

    public void setPadre(NodoAE padre) {
        this.padre = padre;
    }

    public int getH1_n() {
        return h1_n;
    }

    public void setH1_n(int h1_n) {
        this.h1_n = h1_n;
    }

    public int getH2_n() {
        return h2_n;
    }

    public void setH2_n(int h2_n) {
        this.h2_n = h2_n;
    }

    public int getH3_n() {
        return h3_n;
    }

    public void setH3_n(int h3_n) {
        this.h3_n = h3_n;
    }

    public int getF_n() {
        return f_n;
    }

    public void setF_n(int f_n) {
        this.f_n = f_n;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for(int i=0; i<3; i++ ){
            for(int j=0; j<3; j++){
                sb.append(dato[i][j]);
            }
        }

        return sb.toString();
        }

    
}
