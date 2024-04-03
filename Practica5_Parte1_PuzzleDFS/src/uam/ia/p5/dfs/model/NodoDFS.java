/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p5.dfs.model;

import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class NodoDFS {
    String[] dato;
    LinkedList<NodoDFS> hijos;
    NodoDFS padre;

        public NodoDFS() {
            this.dato = new String[4];
            this.padre = null;
            this.hijos = new LinkedList<>();
    }

    public String[] getDato() {
        return dato;
    }

    public void setDato(String[] dato) {
        this.dato = dato;
    }

    public LinkedList<NodoDFS> getHijos() {
        return hijos;
    }

    public void setHijos(LinkedList<NodoDFS> hijos) {
        this.hijos = hijos;
    }

    public void addHijo(NodoDFS nodo){
        this.hijos.addLast(nodo);
    }
    
    public NodoDFS getPadre() {
        return padre;
    }

    public void setPadre(NodoDFS padre) {
        this.padre = padre;
    }

    @Override
    public String toString() {
        return dato[0] + dato[1] + dato[2] + dato[3];
    }
    
    

}
