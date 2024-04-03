/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.hanoi.model;

import java.util.LinkedList;
import uam.ia.hanoi.main.Aplicacion;

/**
 *
 * @author delta9
 */
public class HanoiBFS {
    private LinkedList<NodoBFS> Frontera; //FIFO
    private LinkedList<NodoBFS> visitados;
    private NodoBFS nodoInicial;
    private NodoBFS nodoActual;
    private NodoBFS S;
    private NodoBFS nodoSolucion;

    private Aplicacion control;
    public HanoiBFS(Aplicacion app) {
        this.control = app;
        this.Frontera = new LinkedList<>(); //crear cola
        this.visitados = new LinkedList<>(); //crear lista de nodos visitados
        this.nodoActual = new NodoBFS();
        this.nodoInicial = new NodoBFS();        
        this.S = new NodoBFS();
        this.nodoSolucion = new NodoBFS();
    }
    
    public void iniciaBFS(){
                //Asignar nodo inicial a estado inicial
        nodoInicial = crearNodoInicial(nodoInicial);
        //Crear cola y nodos visitados (ocurre en constructor)
        //Almacenar nodo inicial en frontera
        Frontera.addLast(nodoInicial); //cola
//        System.out.println("Inicial:" + nodoInicial.getDatos());
        while(Frontera.size()!=0){
             nodoActual = Frontera.removeFirst(); //extraer nodo de frontera
             if(nodoActual.getPadre()!=null)
//             System.out.println("Actual:" + nodoActual.getDatos()+ " Padre:" + nodoActual.getPadre().getDatos());
             if(checkSolucion(nodoActual)){
                System.out.println("Has terminado!");
                break;
             }
             //Introducir nodo actual en visitados
             visitados.add(nodoActual);
             //Por cada operador:
             moverDiscoVarillaA();
             moverDiscoVarillaB();
             moverDiscoVarillaC();

                for(NodoBFS n: nodoActual.getHijos()){
                    if(checkVisFron(Frontera,visitados,n)){
                        Frontera.addLast(n);    
                    }
                }

        }

        resultados(nodoActual);
        clearAll();
    }
    
    public void iniciaDFS(){
        NodoBFS hijotmp = new NodoBFS();
                //Asignar nodo inicial a estado inicial
        nodoInicial = crearNodoInicial(nodoInicial);
        //Crear cola y nodos visitados (ocurre en constructor)
        //Almacenar nodo inicial en frontera
        Frontera.addFirst(nodoInicial); //pila
//        System.out.println("Inicial:" + nodoInicial.getDatos());
        while(Frontera.size()!=0){
             nodoActual = Frontera.removeFirst(); //extraer nodo de frontera
             if(nodoActual.getPadre()!=null)
//             System.out.println("Actual:" + nodoActual.getDatos()+ " Padre:" + nodoActual.getPadre().getDatos());
             if(checkSolucion(nodoActual)){
                System.out.println("Has terminado!");
                break;
             }
             //Introducir nodo actual en visitados
             visitados.add(nodoActual);
             //Por cada operador:
             moverDiscoVarillaADFS(nodoActual);
             for(NodoBFS n :nodoActual.getHijos()){
                moverDiscoVarillaADFS(n);
                moverDiscoVarillaBDFS(n);
                moverDiscoVarillaCDFS(n);                 
             }
             moverDiscoVarillaBDFS(nodoActual);
             for(NodoBFS m :nodoActual.getHijos()){
                moverDiscoVarillaADFS(m);
                moverDiscoVarillaBDFS(m);
                moverDiscoVarillaCDFS(m);                 
             }
             moverDiscoVarillaCDFS(nodoActual);
             for(NodoBFS o :nodoActual.getHijos()){
                moverDiscoVarillaADFS(o);
                moverDiscoVarillaBDFS(o);
                moverDiscoVarillaCDFS(o);                 
             }

                for(NodoBFS n: nodoActual.getHijos()){
                    if(checkVisFron(Frontera,visitados,n)){
                        Frontera.addLast(n);    
                    }
                    for(NodoBFS m: n.getHijos()){
                        if(checkVisFron(Frontera,visitados,m)){
                            Frontera.addLast(m);    
                        }                        
                    }
                }

        }

        resultados(nodoActual);
        clearAll();
    }
    
    public void resultados(NodoBFS nodo){
        String toWindow = "";

        NodoBFS nodofinal = nodo;
        LinkedList<String> resultados = new LinkedList<>();
        while(nodofinal.getPadre() != null){
            resultados.addFirst(nodofinal.getDatos());
            nodofinal = nodofinal.getPadre();   
        }
        resultados.addFirst(nodoInicial.getDatos());
        for(int i=0; i< resultados.size();i++){
            if(i!= resultados.size()-1){
                System.out.print(resultados.get(i)+ " -> ");

            }
            else{
                System.out.println(resultados.get(i)); 
            }
        }
        System.out.println("");
        
    }
    public void moverDiscoVarillaA(){
         //Mover disco de B a A, si A no tiene nada se salta a la otra varilla
        if(!nodoActual.getVarillaB().isEmpty()){
            if(nodoActual.getVarillaA().isEmpty() || nodoActual.getVarillaA().getLast().getTamanio() > nodoActual.getVarillaB().getLast().getTamanio()){
                NodoBFS hijo = crearHijo();
                hijo.getVarillaA().addLast(hijo.getVarillaB().removeLast());
                hijo.getVarillaA().getLast().setPosicionVarilla("A");
            }

        }        
        //Mover disco de C a A
        if(!nodoActual.getVarillaC().isEmpty()){
            if(nodoActual.getVarillaA().isEmpty() || nodoActual.getVarillaA().getLast().getTamanio() > nodoActual.getVarillaC().getLast().getTamanio() ){
                NodoBFS hijo = crearHijo();
                hijo.getVarillaA().addLast(hijo.getVarillaC().removeLast());
                hijo.getVarillaA().getLast().setPosicionVarilla("A");
            }            
        }
        
    }   
    

    public void moverDiscoVarillaB(){
        //Mover disco de A a B, si A no tiene nada se salta a la otra varilla
        if(!nodoActual.getVarillaA().isEmpty()){
            if(nodoActual.getVarillaB().isEmpty() || nodoActual.getVarillaB().getLast().getTamanio() > nodoActual.getVarillaA().getLast().getTamanio()){
                NodoBFS hijo = crearHijo();
                hijo.getVarillaB().addLast(hijo.getVarillaA().removeLast());
                hijo.getVarillaB().getLast().setPosicionVarilla("B");
            }

        }        
        //Mover disco de C a B
        if(!nodoActual.getVarillaC().isEmpty()){
            if(nodoActual.getVarillaB().isEmpty() || nodoActual.getVarillaB().getLast().getTamanio() > nodoActual.getVarillaC().getLast().getTamanio() ){
                NodoBFS hijo = crearHijo();
                hijo.getVarillaB().addLast(hijo.getVarillaC().removeLast());
                hijo.getVarillaB().getLast().setPosicionVarilla("B");                
            }            
        }
    }
    public void moverDiscoVarillaC(){
        //Mover disco de A a C, si A no tiene nada se salta a la otra varilla
        if(!nodoActual.getVarillaA().isEmpty()){
            if(nodoActual.getVarillaC().isEmpty() || nodoActual.getVarillaC().getLast().getTamanio() > nodoActual.getVarillaA().getLast().getTamanio()){
                NodoBFS hijo = crearHijo();
                hijo.getVarillaC().addLast(hijo.getVarillaA().removeLast());
                hijo.getVarillaC().getLast().setPosicionVarilla("C");
            }

        }        
        //Mover disco de B a C
        if(!nodoActual.getVarillaB().isEmpty()){
            if(nodoActual.getVarillaC().isEmpty() || nodoActual.getVarillaC().getLast().getTamanio() > nodoActual.getVarillaB().getLast().getTamanio() ){
                NodoBFS hijo = crearHijo();
                hijo.getVarillaC().addLast(hijo.getVarillaB().removeLast());
                hijo.getVarillaC().getLast().setPosicionVarilla("C");
            }            
        }
    }
    public void moverDiscoVarillaADFS(NodoBFS nodo){
         //Mover disco de B a A, si A no tiene nada se salta a la otra varilla
        if(!nodo.getVarillaB().isEmpty()){
            if(nodo.getVarillaA().isEmpty() || nodo.getVarillaA().getLast().getTamanio() > nodo.getVarillaB().getLast().getTamanio()){
                NodoBFS hijo = crearHijoDFS(nodo);
                hijo.getVarillaA().addLast(hijo.getVarillaB().removeLast());
                hijo.getVarillaA().getLast().setPosicionVarilla("A");
            }

        }        
        //Mover disco de C a A
        if(!nodo.getVarillaC().isEmpty()){
            if(nodo.getVarillaA().isEmpty() || nodo.getVarillaA().getLast().getTamanio() > nodo.getVarillaC().getLast().getTamanio() ){
                NodoBFS hijo = crearHijoDFS(nodo);
                hijo.getVarillaA().addLast(hijo.getVarillaC().removeLast());
                hijo.getVarillaA().getLast().setPosicionVarilla("A");                
            }            
        }

    }   
    

    public void moverDiscoVarillaBDFS(NodoBFS nodo){
        //Mover disco de A a B, si A no tiene nada se salta a la otra varilla
        if(!nodo.getVarillaA().isEmpty()){
            if(nodo.getVarillaB().isEmpty() || nodo.getVarillaB().getLast().getTamanio() > nodo.getVarillaA().getLast().getTamanio()){
                NodoBFS hijo = crearHijoDFS(nodo);
                hijo.getVarillaB().addLast(hijo.getVarillaA().removeLast());
                hijo.getVarillaB().getLast().setPosicionVarilla("B");

            }

        }        
        //Mover disco de C a B
        if(!nodo.getVarillaC().isEmpty()){
            if(nodo.getVarillaB().isEmpty() || nodo.getVarillaB().getLast().getTamanio() > nodo.getVarillaC().getLast().getTamanio() ){
                NodoBFS hijo = crearHijoDFS(nodo);
                hijo.getVarillaB().addLast(hijo.getVarillaC().removeLast());
                hijo.getVarillaB().getLast().setPosicionVarilla("B");                
            }            
        }
    }
    public void moverDiscoVarillaCDFS(NodoBFS nodo){
        //Mover disco de A a C, si A no tiene nada se salta a la otra varilla
        if(!nodo.getVarillaA().isEmpty()){
            if(nodo.getVarillaC().isEmpty() || nodo.getVarillaC().getLast().getTamanio() > nodo.getVarillaA().getLast().getTamanio()){
                NodoBFS hijo = crearHijoDFS(nodo);
                hijo.getVarillaC().addLast(hijo.getVarillaA().removeLast());
                hijo.getVarillaC().getLast().setPosicionVarilla("C");
            }

        }        
        //Mover disco de B a C
        if(!nodo.getVarillaB().isEmpty()){
            if(nodo.getVarillaC().isEmpty() || nodo.getVarillaC().getLast().getTamanio() > nodo.getVarillaB().getLast().getTamanio() ){
                NodoBFS hijo = crearHijoDFS(nodo);
                hijo.getVarillaC().addLast(hijo.getVarillaB().removeLast());
                hijo.getVarillaC().getLast().setPosicionVarilla("C");
            }            
        }
    }

    public NodoBFS crearHijo(){
         NodoBFS hijo = new NodoBFS();
         LinkedList<Disco> tempA = new LinkedList<>();
         LinkedList<Disco> tempB = new LinkedList<>();
         LinkedList<Disco> tempC = new LinkedList<>();
         for(Disco d: nodoActual.getVarillaA()){
             tempA.addLast(new Disco(d.getTamanio(),d.getPosicionVarilla()));
         }
         for(Disco d: nodoActual.getVarillaB()){
             tempB.addLast(new Disco(d.getTamanio(),d.getPosicionVarilla()));
         }
         for(Disco d: nodoActual.getVarillaC()){
             tempC.addLast(new Disco(d.getTamanio(),d.getPosicionVarilla()));
         }         
         hijo.setVarillaA(tempA);
         hijo.setVarillaB(tempB);
         hijo.setVarillaC(tempC);
         hijo.setPadre(nodoActual);
         nodoActual.addHijo(hijo);
         return hijo;
        
    }
    
    public NodoBFS crearHijoDFS(NodoBFS nodo){
         NodoBFS hijo = new NodoBFS();
         LinkedList<Disco> tempA = new LinkedList<>();
         LinkedList<Disco> tempB = new LinkedList<>();
         LinkedList<Disco> tempC = new LinkedList<>();
         for(Disco d: nodo.getVarillaA()){
             tempA.addLast(new Disco(d.getTamanio(),d.getPosicionVarilla()));
         }
         for(Disco d: nodo.getVarillaB()){
             tempB.addLast(new Disco(d.getTamanio(),d.getPosicionVarilla()));
         }
         for(Disco d: nodo.getVarillaC()){
             tempC.addLast(new Disco(d.getTamanio(),d.getPosicionVarilla()));
         }         
         hijo.setVarillaA(tempA);
         hijo.setVarillaB(tempB);
         hijo.setVarillaC(tempC);
         hijo.setPadre(nodo);
         nodo.addHijo(hijo);
         return hijo;
        
    }

    //Metodo que se utiliza para escribir los valores del estado inicial
    public NodoBFS crearNodoInicial(NodoBFS inicial){
        Disco disco1 = new Disco(1);
        disco1.setPosicionVarilla("A");
        Disco disco2 = new Disco(2);
        disco2.setPosicionVarilla("A");
        Disco disco3 = new Disco(3);
        disco3.setPosicionVarilla("A");
        inicial.addA(disco3);
        inicial.addA(disco2);
        inicial.addA(disco1);
        return inicial;
    }
        //Compara el estado actual contra la solución para saber si se ha llegado a la solución
    public boolean checkSolucion(NodoBFS currentNode){
        String solucion = "3B2B1B";
//        System.out.println("Check sol: " + solucion + " " + currentNode.getDatos());
        if(solucion.equals(currentNode.getDatos())){
            return true;
        }
        return false;
    }
       //Revisa si el nodo actual se encuentra en la frontera o en los nodos visitados 
    public boolean checkVisFron(LinkedList<NodoBFS> frontera, LinkedList<NodoBFS> visitados, NodoBFS nodo){
        boolean estaFrontera = false;
        boolean estaVisitado = false;
        if(!frontera.isEmpty()){ 
            for(NodoBFS n: frontera){        
                if(n.getDatos().equals(nodo.getDatos())){
                    estaFrontera = true;
                    break;
                }         
            }
        }
        if(!visitados.isEmpty()){         
            for(NodoBFS v: visitados){
                if(v.getDatos().equals(nodo.getDatos())){
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
    public boolean checkVisitados(LinkedList<NodoBFS> visitados, NodoBFS nodo){
        if(!visitados.isEmpty()){         
            for(NodoBFS v: visitados){
                if(v.getDatos().equals(nodo.getDatos())){
                    return true;
                } 
            }
        }
        return false;
    }    

        private void clearAll(){
        Frontera.clear();
        visitados.clear();
        nodoActual.getVarillaA().clear();
        nodoActual.getVarillaB().clear();
        nodoActual.getVarillaC().clear();
        nodoActual.getHijos().clear();
        nodoActual.setPadre(null);
        nodoInicial.getVarillaA().clear();
        nodoInicial.getVarillaB().clear();
        nodoInicial.getVarillaC().clear();        
        nodoInicial.getHijos().clear();
        nodoInicial.setPadre(null);
    }
}
