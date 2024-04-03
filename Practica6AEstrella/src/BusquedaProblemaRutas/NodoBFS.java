/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaProblemaRutas;

import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class NodoBFS {
    private String datos;
    private LinkedList<NodoBFS> hijos;
    private NodoBFS padre;

    public NodoBFS() {
        this.datos = "";
        this.padre = null;
        this.hijos = new LinkedList<>();        
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String dato) {
        this.datos = dato;
    }

    public LinkedList<NodoBFS> getHijos() {
        return hijos;
    }

    public void setHijos(LinkedList<NodoBFS> hijos) {
        this.hijos = hijos;
    }

    public NodoBFS getPadre() {
        return padre;
    }

    public void setPadre(NodoBFS padre) {
        this.padre = padre;
    }
    public void addHijo(NodoBFS nodo){
        this.hijos.add(nodo);
        
    }
    @Override
    public String toString() {
        return "Te encuentras en: " + datos;
    }

    
}
