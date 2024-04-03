/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p4.ucs.model;

import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class NodoUCS {
    private String datos;
    private LinkedList<NodoUCS> hijos;
    private NodoUCS padre;
    private int coste;

    public NodoUCS() {
        this.datos = "";
        this.padre = null;
        this.coste = 0;
        this.hijos = new LinkedList<>();        
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String dato) {
        this.datos = dato;
    }

    public LinkedList<NodoUCS> getHijos() {
        return hijos;
    }

    public void setHijos(LinkedList<NodoUCS> hijos) {
        this.hijos = hijos;
    }

    public NodoUCS getPadre() {
        return padre;
    }

    public void setPadre(NodoUCS padre) {
        this.padre = padre;
    }
    public void addHijo(NodoUCS nodo){
        this.hijos.add(nodo);
        
    }
    public int getCoste() {
        return coste;
    }
    public void setCoste(int coste) {
        this.coste = coste;
    }
    
    @Override
    public String toString() {
        return datos;
    }

    
}
