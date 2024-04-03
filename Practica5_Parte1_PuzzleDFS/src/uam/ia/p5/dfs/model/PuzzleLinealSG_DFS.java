/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p5.dfs.model;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class PuzzleLinealSG_DFS {
    //aqui va el metodo que recorre el recorrido o construccion del arbol
    //Utilizar una cola (FIFO) donde el primero en entrar es el primer en salir.
    
    private LinkedList<NodoDFS> visitados;
    private NodoDFS nodoInicial;
    private NodoDFS nodoSolucion;
    private NodoDFS S;

    
    public PuzzleLinealSG_DFS(){
        this.visitados = new LinkedList<>();
        this.nodoInicial = new NodoDFS();
        this.nodoSolucion = new NodoDFS();
        this.S = new NodoDFS();
    }
    
    public void iniciarDFS(){
        crearNodoInicial();
        setSolucion();
        S = DFSRecursivo(nodoInicial,nodoSolucion,visitados);
        resultados(S);
    }
    public NodoDFS DFSRecursivo(NodoDFS nodo,NodoDFS solucion,LinkedList<NodoDFS> Visitados){
        Visitados.add(nodo);
        if(checkSolucion(nodo)){
            System.out.println("Se ha llegado a la solución!");
            return nodo;
         }
        else{
            //Aplicar operadores
            operadorIzq(nodo);
            operadorDer(nodo);
            operadorCen(nodo);
            for(NodoDFS n: nodo.getHijos()){
                 if(!checkVisitados(Visitados,n) && funcionCoste(n) ){
                     S = DFSRecursivo(n, solucion, Visitados);
                     if(S != null){
                         return S;
                     }
                 }
            }
        }
        return null;     
    }
    public boolean funcionCoste(NodoDFS nodo){
        String[] hijo =  new String[4];
        String[] padre =  new String[4];
        hijo = nodo.getDato();
        padre = nodo.getPadre().getDato();
        int h_nHijo =0;
        int h_nPadre=0;
        h_nHijo = calcularCoste(hijo);
        h_nPadre = calcularCoste(padre);
        
        if(h_nHijo >= h_nPadre )
            return true;
        return false;
    }
    
    public int calcularCoste(String[] dato){
        int coste=0;
        for(int i=0; i<3;i++){
            if(Integer.parseInt(dato[i]) < Integer.parseInt(dato[i+1])){
                coste++;
            }
        }
        return coste;
    }

    //Compara el estado actual contra la solución para saber si se ha llegado a la solución
    public boolean checkSolucion(NodoDFS currentNode){
        if(Arrays.toString(currentNode.getDato()).equals(Arrays.toString(nodoSolucion.getDato()))){
            return true;
        }
        return false;
    }    
    
    //Revisa si el nodo actual se encuentra en la frontera o en los nodos visitados 
    public boolean checkVisitados(LinkedList<NodoDFS> visitados, NodoDFS nodo){
        if(!visitados.isEmpty()){         
            for(NodoDFS v: visitados){
                if(Arrays.toString(v.getDato()).equals(Arrays.toString(nodo.getDato()))){
                    return true;
                } 
            }
        }
        return false;
    }
   
    public void setSolucion(){
        String[] edoFinal = {"1","2", "3", "4"};
        this.nodoSolucion.setDato(edoFinal);
        this.nodoSolucion.setHijos(null);
        this.nodoSolucion.setPadre(null);              
    }
    
    public void operadorIzq(NodoDFS nodo){
        NodoDFS hijo = new NodoDFS();
        String[] valor =  new String[4];
        for(int i=0; i<4; i++){
            valor[i] = nodo.getDato()[i];
        }        
        String temp = valor[0];
        valor[0] = valor[1];
        valor[1] = temp;
        hijo.setDato(valor);
        hijo.setPadre(nodo);
        nodo.getHijos().addLast(hijo);

    }
    
    public void operadorCen(NodoDFS nodo){
        NodoDFS hijo = new NodoDFS();
        String[] valor =  new String[4];
        for(int i=0; i<4; i++){
            valor[i] = nodo.getDato()[i];
        }        
        String temp = new String(valor[1]);
        valor[1] = valor[2];
        valor[2] = temp;
        hijo.setDato(valor);
        hijo.setPadre(nodo);
        nodo.addHijo(hijo);        
    }

    public void operadorDer(NodoDFS nodo){
        NodoDFS hijo = new NodoDFS();
        String[] valor =  new String[4];
        for(int i=0; i<4; i++){
            valor[i] = nodo.getDato()[i];
        }        
        String temp = new String(valor[2]);
        valor[2] = valor[3];
        valor[3] = temp;
        hijo.setDato(valor);
        hijo.setPadre(nodo);
        nodo.addHijo(hijo);
    }


    
    public NodoDFS crearNodo(String[] dato) {
        NodoDFS nodo = new NodoDFS();
        nodo.setDato(dato);
        
        
        return nodo;
    }
    
    public void crearNodoInicial(){
        String[] edoInicial = {"4","2", "3", "1"};
        nodoInicial.setPadre(null);
        nodoInicial.setDato(edoInicial);
        
    }
    
    
    public void resultados(NodoDFS nodo){
        NodoDFS nodofinal = nodo;
        LinkedList<String> resultados = new LinkedList<>();
        while(nodofinal.getPadre() != null){
            resultados.addFirst(nodofinal.toString());
            nodofinal = nodofinal.getPadre();   
        }
        resultados.addFirst(nodoInicial.toString());
        for(int i=0; i< resultados.size();i++){
            if(i!= resultados.size()-1){
                System.out.print(resultados.get(i)+ " -> ");
            }
            else
                System.out.println(resultados.get(i)); 
        }
        System.out.println("");
    }
   public void resetAll(){
       visitados.clear();
       nodoInicial=null;
       
   } 

}
