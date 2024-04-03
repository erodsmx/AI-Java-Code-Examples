/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.hanoi.model;

import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class NodoBFS {
    private LinkedList<Disco> VarillaA;
    private LinkedList<Disco> VarillaC;
    private LinkedList<Disco> VarillaB;
    private NodoBFS padre;
    private LinkedList<NodoBFS> hijos;

    public NodoBFS() {
        VarillaA = new LinkedList<>();
        VarillaB = new LinkedList<>();
        VarillaC = new LinkedList<>();
        this.padre = null;
        this.hijos = new LinkedList<>();
    }
    public void addA(Disco e){
        this.VarillaA.add(e);
    }
    public void addB(Disco e){
        this.VarillaB.add(e);
    }
    public void addC(Disco e){
        this.VarillaC.add(e);
    }    
    public LinkedList<Disco> getVarillaA() {
        return VarillaA;
    }

    public void setVarillaA(LinkedList<Disco> VarillaA) {
        this.VarillaA = VarillaA;
    }

    public LinkedList<Disco> getVarillaC() {
        return VarillaC;
    }

    public void setVarillaC(LinkedList<Disco> VarillaC) {
        this.VarillaC = VarillaC;
    }

    public LinkedList<Disco> getVarillaB() {
        return VarillaB;
    }

    public void setVarillaB(LinkedList<Disco> VarillaB) {
        this.VarillaB = VarillaB;
    }

    public NodoBFS getPadre() {
        return padre;
    }

    public void setPadre(NodoBFS padre) {
        this.padre = padre;
    }

    public LinkedList<NodoBFS> getHijos() {
        return hijos;
    }

    public void addHijo(NodoBFS n){
        this.hijos.add(n);
        
    }

    @Override
    public String toString() {
        return "VarillaA: " + VarillaA + "\nVarillaB:" + VarillaB + "\nVarillaC=" + VarillaC;
    }
    public String getDatos(){
        StringBuilder sb = new StringBuilder("");
        for(Disco d: VarillaA){
            sb.append(d.getValues());
        }
        for(Disco e: VarillaB){
            sb.append(e.getValues());
        }
        for(Disco f: VarillaC){
            sb.append(f.getValues());
        }
        return sb.toString();
                
    }
    
}
