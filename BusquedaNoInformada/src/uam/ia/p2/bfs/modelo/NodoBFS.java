/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p2.bfs.modelo;

import java.util.Arrays;

/**
 *
 * @author delta9
 */
public class NodoBFS {
    String[] dato;
    NodoBFS[] hijos;
    NodoBFS padre;

        public NodoBFS() {
            this.dato = new String[4];
            this.padre = null;
            this.hijos = new NodoBFS[3];
    }

    public String[] getDato() {
        return dato;
    }

    public void setDato(String[] dato) {
        this.dato = dato;
    }

    public NodoBFS[] getHijos() {
        return hijos;
    }

    public void setHijos(NodoBFS[] hijos) {
        this.hijos = hijos;
    }
    
    public void setHijoI(NodoBFS nodo){
        this.hijos[0] = nodo;
    }
    public void setHijoC(NodoBFS nodo){
        this.hijos[1] = nodo;
    }
    public void setHijoD(NodoBFS nodo){
        this.hijos[2] = nodo;
    }
    
    
    public NodoBFS getPadre() {
        return padre;
    }

    public void setPadre(NodoBFS padre) {
        this.padre = padre;
    }

    @Override
    public String toString() {
        return dato[0] + dato[1] + dato[2] + dato[3];
    }
    
    

}
