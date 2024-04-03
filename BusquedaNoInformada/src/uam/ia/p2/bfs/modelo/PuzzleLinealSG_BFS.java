/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p2.bfs.modelo;

/**
 *
 * @author delta9
 */
public class PuzzleLinealSG_BFS {
    //aqui va el metodo que recorre el recorrido o construccion del arbol
    //Utilizar una cola (FIFO) donde el primero en entrar es el primer en salir.
    
    public PuzzleLinealSG_BFS(){
    }

    
    public NodoBFS operadorIzq(NodoBFS nodo){
        NodoBFS hijo = new NodoBFS();
        String[] valor = nodo.getDato().clone();
        String temp = valor[0];
        valor[0] = valor[1];
        valor[1] = temp;
        hijo.setDato(valor);
        hijo.setPadre(nodo);        
        return hijo;
    }
    
    public NodoBFS operadorCen(NodoBFS nodo){
        NodoBFS hijo = new NodoBFS();
        String[] valor = nodo.getDato().clone();
        String temp = valor[1];
        valor[1] = valor[2];
        valor[2] = temp;
        hijo.setDato(valor);
        hijo.setPadre(nodo); 
        return hijo;
    }

    public NodoBFS operadorDer(NodoBFS nodo){
        NodoBFS hijo = new NodoBFS();
        String[] valor = nodo.getDato().clone();
        String temp = valor[2];
        valor[2] = valor[3];
        valor[3] = temp;
        hijo.setDato(valor);
        hijo.setPadre(nodo); 
        return hijo;
    }

    //este metodo se usa en profundidad, cuando un nodo crea su primer hijo el método le crea los tres hijos a ese primer hijo.
    public NodoBFS[] expandirHijos(NodoBFS hijo){
        NodoBFS[] hijostmp = new NodoBFS[3];
        hijostmp[0] = operadorIzq(hijo);
        hijostmp[1] = operadorCen(hijo);
        hijostmp[2] = operadorDer(hijo);
        return hijostmp;
    }
    
    public NodoBFS crearNodo(String[] dato) {
        NodoBFS nodo = new NodoBFS();
        nodo.setDato(dato);
        
        
        return nodo;
    }
    
    public NodoBFS crearNodoInicial(String[] dato){
        NodoBFS nodo = new NodoBFS();
        nodo.setPadre(null);
        nodo.setHijos(null);
        nodo.setDato(dato);
        return nodo;
        
    }
    
    
}
