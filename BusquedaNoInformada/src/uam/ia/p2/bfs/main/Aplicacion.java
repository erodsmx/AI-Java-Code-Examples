/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p2.bfs.main;

import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import uam.ia.p2.bfs.modelo.NodoBFS;
import uam.ia.p2.bfs.modelo.PuzzleLinealSG_BFS;
import uam.ia.p2.bfs.view.MainView;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    
    private MainView Ventana;
    private LinkedList<String> listnum = new LinkedList<>();
    private PuzzleLinealSG_BFS puzzle;
    private NodoBFS nodoInicial;
    private NodoBFS nodoActual;
    private NodoBFS hijo;
    private LinkedList<NodoBFS> nodosFrontera; //FIFO
    private LinkedList<NodoBFS> visitados;

    public Aplicacion() {
        this.initList();
        puzzle = new PuzzleLinealSG_BFS();
        this.nodoActual = new NodoBFS();
        this.hijo = new NodoBFS();
        this.nodosFrontera = new LinkedList<>();
        this.visitados = new LinkedList<>();
        nodosFrontera.clear();
        visitados.clear();
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }
    
    public void inicia(){
        Ventana = new MainView(this);
        Ventana.setVisible(true);
    }
  
    
    //Implementación del pseudocódigo de la diapositiva 57 del video Semana2
    public void iniciarBusquedaBFS(){
        resetAll();
        NodoBFS[] hijostmp = new NodoBFS[3];
        nodoInicial = puzzle.crearNodoInicial(Ventana.extraerDatos());
        nodosFrontera.addLast(nodoInicial);
        while(nodosFrontera.size()!=0){
            nodoActual = nodosFrontera.removeFirst(); //extraer nodo de frontera
            if("1234".equals(nodoActual.toString())){
                System.out.println("Se ha encontrado la solucion en el nodo actual, puzzle: "+ nodoActual.toString());
                break;
            }
            visitados.add(nodoActual);
          hijostmp[0] = puzzle.operadorIzq(nodoActual); //Aplica operador izq
          hijostmp[1] = puzzle.operadorCen(nodoActual); //Aplica central y devuelve nodo con nuevo dato
          hijostmp[2] = puzzle.operadorDer(nodoActual); //Aplica derecho
          nodoActual.setHijos(hijostmp); //Al nodo actual le guarda los hijos.

            for(int i=0;i< 3; i++){
                if(checkVisFron(nodosFrontera,visitados,nodoActual.getHijos()[i])){ //Si no esta en frontera y visitados
                    nodosFrontera.addLast(nodoActual.getHijos()[i]); //agrega hijo en nodos frontera               
                }   
            }

        } //end while
        System.out.println("frontera:"+nodosFrontera.size() + " visitados:"+visitados.size());
        resultados(nodoActual);
        
        
    }
    
    public void iniciarBusquedaDFS(){
        resetAll();
        NodoBFS[] hijostmp = new NodoBFS[3];
        NodoBFS[] subhijos = new NodoBFS[3];
        nodoInicial = puzzle.crearNodoInicial(Ventana.extraerDatos());
        nodosFrontera.addFirst(nodoInicial); //Ahora agrega al inicio (LIFO), para que el ultimo dato que entra sea el primero que salga
        while(nodosFrontera.size()!=0){
            nodoActual = nodosFrontera.removeFirst(); //extraer nodo de frontera
            if("1234".equals(nodoActual.toString())){
                System.out.println("Se ha encontrado la solucion en el nodo actual, puzzle: "+ nodoActual.toString());
                break;
            }
            visitados.add(nodoActual);                     
          hijostmp[0] = puzzle.operadorIzq(nodoActual); //Aplica operador izq  
          subhijos = puzzle.expandirHijos(hijostmp[0]); //expande hijos
          hijostmp[0].setHijos(subhijos.clone());

          hijostmp[1] = puzzle.operadorCen(nodoActual); //Aplica operador cen 
          subhijos = puzzle.expandirHijos(hijostmp[1]);
          hijostmp[1].setHijos(subhijos.clone());

          hijostmp[2] = puzzle.operadorDer(nodoActual); //Aplica operador der  
          subhijos = puzzle.expandirHijos(hijostmp[2]);          
          hijostmp[2].setHijos(subhijos.clone());

          nodoActual.setHijos(hijostmp); //Al nodo actual le guarda los hijos.
            for(int i=0;i< 3; i++){
                if(checkVisFron(nodosFrontera,visitados,nodoActual.getHijos()[i])){ //Si no esta en frontera y visitados
                    nodosFrontera.addFirst(nodoActual.getHijos()[i]); //agrega hijo en nodos frontera               
                }
                /*
                for(NodoBFS n: nodoActual.getHijos()[i].getHijos()){                  
                    if(checkVisFron(nodosFrontera,visitados,n)){ //Si no esta en frontera y visitados
                        nodosFrontera.addFirst(n); //agrega hijo en nodos frontera               
                    }
                }
                  */  
               
            }
        } //end while
        System.out.println("frontera:"+nodosFrontera.size() + " visitados:"+visitados.size() );
        resultados(nodoActual);
    }

    public void resultados(NodoBFS nodo){
        NodoBFS nodofinal = nodo;
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
       nodosFrontera.clear();
       visitados.clear();
       nodoInicial=null;
       nodoActual=null;
       
   } 
    
    public boolean checkVisFron(LinkedList<NodoBFS> frontera, LinkedList<NodoBFS> visitados, NodoBFS nodo){
        boolean estaFrontera = false;
        boolean estaVisitado = false;
        for(NodoBFS n: frontera){        
            if(n.toString() == nodo.toString()){
                estaFrontera = true;
                break;
            }
                
        }
        for(NodoBFS v: visitados){
            if(v.toString() == nodo.toString()){
                estaVisitado = true;
                break;
            } 
        }
        if(estaFrontera || estaVisitado)
            return false;
        else
            return true;
    }
    
    
       /*Rellena de manera aleatoria las celdas.*/
    public void asignarCelda(JButton[] buttons, int arraySize){
        int i,aleatorio=0;
        this.initList();
        aleatorio = randomNum(listnum.size()); 

        for(i=0;i<arraySize;i++){
            buttons[i].setText(listnum.get(aleatorio)); //Escribe un numero aleatorio
            listnum.remove(aleatorio);
            if(listnum.size()!=0)
            aleatorio = randomNum(listnum.size());
        }
    }

    private void initList(){
        listnum.clear();
        listnum.add("1");
        listnum.add("2");
        listnum.add("3");
        listnum.add("4");

        
    }
    
        /*Genera numero aleatorio*/
    public int randomNum(int size){

        Random r = new Random();
        return r.nextInt(size);
    }
    
    
    
}
