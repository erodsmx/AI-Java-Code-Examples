/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p4.ucs.model;

import java.util.HashMap;
import java.util.LinkedList;
import uam.ia.p4.ucs.main.Aplicacion;

/**
 *
 * @author delta9
 */
public class RutaBFS {
    private Aplicacion control;
    private LinkedList<NodoBFS> Frontera; //FIFO
    private LinkedList<NodoBFS> visitados;
    private NodoBFS nodoInicial;
    private NodoBFS nodoActual;
    private HashMap<String,String[]> conexiones;
    private int contador;
    private long startTime;
    private long endTime;


    public RutaBFS(Aplicacion app) {
        this.control = app;
        this.Frontera = new LinkedList<>(); //crear cola
        this.visitados = new LinkedList<>(); //crear lista de nodos visitados
        this.nodoActual = new NodoBFS();
        this.nodoInicial = new NodoBFS();
        this.conexiones = new HashMap<>();
        this.contador = 0;
        this.startTime = this.endTime = 0;
        initConexiones();
    }
    public void iniciaBFS(){
        clearAll();
        startTime = System.nanoTime();

        //Asignar nodo inicial a estado inicial
        nodoInicial = crearNodoInicial(nodoInicial);
        //Crear cola y nodos visitados (ocurre en constructor)
        //Almacenar nodo inicial en frontera
        Frontera.addLast(nodoInicial); //cola
        while(Frontera.size()!=0){
             nodoActual = Frontera.removeFirst(); //extraer nodo de frontera
             if(checkSolucion(nodoActual)){
                System.out.println("Has llegado a tu destino!");
                break;
             }
             //Introducir nodo actual en visitados
             visitados.add(nodoActual);
            contador++;
             //Por cada operador:
             visitarCiudad(nodoActual);
                for(NodoBFS n: nodoActual.getHijos()){
                    if(checkVisFron(Frontera,visitados,n)){
                        Frontera.addLast(n);    
                    }
                }
        }
        endTime = System.nanoTime();
        resultados(nodoActual);
    }
    public void visitarCiudad(NodoBFS actual){
        String ciudad = actual.getDatos();
        String[] temp = new String[conexiones.get(ciudad).length];
        int i=0;
        for(String str: conexiones.get(ciudad)){
            temp[i] = str;
            i++;
        }
//        System.out.println("\nTe encuentras en: " + ciudad);
        for(String s: temp){
//            System.out.println("Estado generado: " + s );
            NodoBFS hijo = new NodoBFS();
            hijo.setDatos(s);
            hijo.setPadre(actual);
            actual.addHijo(hijo);
        }
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
                toWindow = toWindow.concat(resultados.get(i).concat(" -> "));

            }
            else{
                System.out.println(resultados.get(i)); 
                toWindow = toWindow.concat(resultados.get(i));                
            }
        }
        System.out.println("");
        control.setResultados(endTime-startTime,0,contador, toWindow);
        
    }
    //Compara el estado actual contra la solución para saber si se ha llegado a la solución
    public boolean checkSolucion(NodoBFS currentNode){
        if(currentNode.getDatos().equals(control.getCiudadFinal())){
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
                if(v.getDatos() == nodo.getDatos()){
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
        //Metodo que se utiliza para escribir los valores del estado inicial
    public NodoBFS crearNodoInicial(NodoBFS inicial){
        inicial.setDatos(control.getCiudadInicial());
        return inicial;
    }
    
    private void initConexiones(){
        String[] acapulco = {"Toluca","CDMX","Cuernavaca"};
        String[] toluca = {"Querétaro","CDMX","Acapulco"};
        String[] cuernavaca = {"Acapulco","CDMX"};
        String[] queretaro = {"Toluca","CDMX"};
        String[] cdmx = {"Querétaro","Toluca","Acapulco","Cuernavaca","Huatulco","Puebla","Veracruz","Poza Rica","Pachuca"};
        String[] puebla = {"CDMX","Huatulco","Veracruz"};
        String[] huatulco = {"CDMX","Puebla"};
        String[] pachuca = {"CDMX","Poza Rica"};
        String[] veracruz = {"Puebla","CDMX"};
        String[] pozaRica = {"CDMX","Pachuca"};
        conexiones.put("Acapulco", acapulco);
        conexiones.put("Toluca",toluca);
        conexiones.put("Cuernavaca",cuernavaca);
        conexiones.put("Querétaro",queretaro);
        conexiones.put("CDMX",cdmx );
        conexiones.put("Puebla",puebla );
        conexiones.put("Huatulco",huatulco );
        conexiones.put("Pachuca",pachuca );
        conexiones.put("Veracruz",veracruz );
        conexiones.put("Poza Rica",pozaRica );
    }
    private void clearAll(){
        contador =0;
        Frontera.clear();
        visitados.clear();
        nodoActual.setDatos("");
        nodoActual.getHijos().clear();
        nodoActual.setPadre(null);
        nodoInicial.setDatos("");
        nodoInicial.getHijos().clear();
        nodoInicial.setPadre(null);
    }
}
