/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p3.model;

import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class SolitaireChessDFS {
    private LinkedList<NodoBFS> visitados;
    private NodoBFS nodoInicial;
    private NodoBFS nodoSolucion;
    private NodoBFS S;
    
    private String TableroSolucion;

    public SolitaireChessDFS() {
        this.visitados = new LinkedList<>(); //crear lista de nodos visitados
        this.nodoInicial = new NodoBFS();
        this.nodoSolucion = new NodoBFS();
        this.S = new NodoBFS();
        TableroSolucion = "............T...";
    }
    
    public void iniciarDFS(){
        //Asignar nodo inicial a estado inicial
        nodoInicial = crearNodoInicial(nodoInicial);
        setSolucion();
        S = DFSRecursivo(nodoInicial,nodoSolucion,visitados);
       resultados(S);
    }
    
    public NodoBFS DFSRecursivo(NodoBFS nodo,NodoBFS solucion,LinkedList<NodoBFS> Visitados){
        visitados.add(nodo);
        if(checkSolucion(nodo)){
            System.out.println("Ya no hay piezas por comer, ganaste!");
            return nodo;
         }
        else{
            moverTorre(nodo); //Aplica operador y crea al hijo si el movimiento es valido.
            moverReina(nodo);
            moverPeon(nodo);
            moverCaballo(nodo);
            for(NodoBFS n: nodo.getHijos()){
                 if(!checkVisitados(Visitados,n)){
                     S = DFSRecursivo(n, solucion, Visitados);
                     if(S != null){
                         return S;
                     }
                 }
            }
        }
        return null;     
    }

    public void resultados(NodoBFS nodo){
        NodoBFS nodofinal = nodo;
        LinkedList<String> resultados = new LinkedList<>();
        while(nodofinal.getPadre() != null){
            resultados.addFirst(nodofinal.getTableroAsString());
            nodofinal = nodofinal.getPadre();   
        }
        resultados.addFirst(nodoInicial.getTableroAsString());
        for(int i=0; i< resultados.size();i++){
            if(i!= resultados.size()-1){
                System.out.print(resultados.get(i)+ " -> ");
            }
            else
                System.out.println(resultados.get(i)); 
        }
        System.out.println("");
    }
    
    public void setSolucion(){
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                if(i==3 && j== 0)
                   nodoSolucion.getDato()[i][j]="T";
                else
                    nodoSolucion.getDato()[i][j]="";
            }
        }
        nodoSolucion.setDato(initArray());
        nodoSolucion.getDato()[3][0] = "T";
    }
    
    
    /*Metodos correspondientes a los operadores, se crean las piezas de ajedrez para despues invocar sus métodos de movimiento*/
    public void moverTorre(NodoBFS nodoActual){
        Rook torre = new Rook(nodoActual.getDato());
        torre.desplazar(nodoActual);
    }
    public void moverReina(NodoBFS nodoActual){
        Queen reina = new Queen(nodoActual.getDato());
        reina.desplazar(nodoActual);  
    }
    public void moverPeon(NodoBFS nodoActual){
        Pawn peon = new Pawn(nodoActual.getDato());
        peon.desplazar(nodoActual);
        
    }
    public void moverCaballo(NodoBFS nodoActual){
        Knight caballo = new Knight(nodoActual.getDato());
        caballo.desplazar(nodoActual);
        
    }
   //Revisa si el nodo actual se encuentra en la frontera o en los nodos visitados 
    public boolean checkVisitados(LinkedList<NodoBFS> visitados, NodoBFS nodo){
        if(!visitados.isEmpty()){         
            for(NodoBFS v: visitados){
                if(v.getTableroAsString() == nodo.getTableroAsString()){
                    return true;
                } 
            }
        }
        return false;
    }
    
    //Compara el estado actual contra la solución para saber si se ha llegado a la solución
    public boolean checkSolucion(NodoBFS currentNode){
        if(currentNode.getTableroAsString().equals(TableroSolucion)){
            return true;
        }
        return false;
    }
    
    //Metodo que se utiliza para escribir los valores del estado inicial
    public NodoBFS crearNodoInicial(NodoBFS nodoInicial){
        String[][] dato = initArray();
        dato[0][0] = "Q";
        dato[0][2] = "T";
        dato[2][1] = "K";
        dato[3][0] = "P";
        
        nodoInicial.setDato(dato.clone()); //Estado inicial
        
        return nodoInicial;
    }
    //Metodo que pone todas las casillas del tablero en blanco
    private String[][] initArray(){
        String[][] dato = new String[4][4];
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                dato[i][j]="";
            }
        }
        return dato;
    }
    
}
