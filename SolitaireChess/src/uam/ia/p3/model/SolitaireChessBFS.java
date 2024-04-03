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
public class SolitaireChessBFS {
    private LinkedList<NodoBFS> Frontera; //FIFO
    private LinkedList<NodoBFS> visitados;
    private NodoBFS nodoInicial;
    private NodoBFS nodoActual;
    private String TableroSolucion;

    public SolitaireChessBFS() {
        this.Frontera = new LinkedList<>(); //crear cola
        this.visitados = new LinkedList<>(); //crear lista de nodos visitados
        this.nodoActual = new NodoBFS();
        this.nodoInicial = new NodoBFS();
        TableroSolucion = "............T...";
    }
    
    public void iniciarBFS(){
        //Asignar nodo inicial a estado inicial
        nodoInicial = crearNodoInicial(nodoInicial);
        //Crear cola y nodos visitados (ocurre en constructor)
        //Almacenar nodo inicial en frontera
        Frontera.addLast(nodoInicial); //cola
        while(Frontera.size()!=0){
             nodoActual = Frontera.removeFirst(); //extraer nodo de frontera
             if(checkSolucion(nodoActual)){
                System.out.println("Ya no hay piezas por comer, ganaste!");
                break;
             }
             //Introducir nodo actual en visitados
             visitados.add(nodoActual);
             //Por cada operador:
             
                moverTorre(nodoActual); //Aplica operador y crea al hijo si el movimiento es valido.
                moverReina(nodoActual);
                moverPeon(nodoActual);
                moverCaballo(nodoActual);
                for(NodoBFS n: nodoActual.getHijos()){
                    if(checkVisFron(Frontera,visitados,n)){
                        Frontera.addLast(n);    
                    }
                }
        }
        resultados(nodoActual);
       
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
   //Revisa si el nodo actual se encuentra en la frontera o en los nodos visitados 
    public boolean checkVisFron(LinkedList<NodoBFS> frontera, LinkedList<NodoBFS> visitados, NodoBFS nodo){
        boolean estaFrontera = false;
        boolean estaVisitado = false;
        if(!frontera.isEmpty()){ 
            for(NodoBFS n: frontera){        
                if(n.getTableroAsString().equals(nodo.getTableroAsString())){
                    estaFrontera = true;
                    break;
                }         
            }
        }
        if(!visitados.isEmpty()){         
            for(NodoBFS v: visitados){
                if(v.getTableroAsString() == nodo.getTableroAsString()){
                    estaVisitado = true;
                    break;
                } 
            }
        }
        if(estaFrontera || estaVisitado)
            return false;
        else
            return true;
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
