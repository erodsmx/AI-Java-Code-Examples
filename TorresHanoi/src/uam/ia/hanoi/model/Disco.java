/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.hanoi.model;

/**
 *
 * @author delta9
 */
public class Disco {
    private int tamanio;
    private String posicionVarilla;

    public Disco(int size) {
        this.tamanio = size;
    }
    public Disco(int size, String pos){
        this.tamanio = size;
        this.posicionVarilla = pos;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public String getPosicionVarilla() {
        return posicionVarilla;
    }

    public void setPosicionVarilla(String posicionVarilla) {
        this.posicionVarilla = posicionVarilla;
    }

    @Override
    public String toString() {
        return "( " + tamanio + ", \"" + posicionVarilla+"\")";
    }
    public String getValues(){
        return tamanio + posicionVarilla;
    }
    
    
}
