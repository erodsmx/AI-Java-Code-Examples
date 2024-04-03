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
public class NodoAE {
    private String datos;
    private LinkedList<NodoAE> hijos;
    private NodoAE padre;
    private int g_n;
    private int f_n;

    public NodoAE() {
        this.datos = "";
        this.padre = null;
        this.g_n = 0;
        this.f_n = 0;
        this.hijos = new LinkedList<>();        
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String dato) {
        this.datos = dato;
    }

    public LinkedList<NodoAE> getHijos() {
        return hijos;
    }

    public void setHijos(LinkedList<NodoAE> hijos) {
        this.hijos = hijos;
    }

    public NodoAE getPadre() {
        return padre;
    }

    public void setPadre(NodoAE padre) {
        this.padre = padre;
    }
    public void addHijo(NodoAE nodo){
        this.hijos.add(nodo);
        
    }
    public int getG_n() {
        return g_n;
    }
    public void setG_n(int g_n) {
        this.g_n = g_n;
    }

    public int getF_n() {
        return f_n;
    }

    public void setF_n(int f_n) {
        this.f_n = f_n;
    }
    
    @Override
    public String toString() {
        return datos;
    }

    
}
