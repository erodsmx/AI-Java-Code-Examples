/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaProblemaRutas;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author delta9
 */
public class RutaUCS {
    private Aplicacion control;
    private LinkedList<NodoAE> Frontera; //FIFO
    private LinkedList<NodoAE> visitados;
    private NodoAE nodoInicial;
    private NodoAE nodoActual;
    private HashMap<String, HashMap<String, Integer>> conexiones;
    private LinkedList<HashMap<String,Integer>> listaMapas;
    private int contador;
    private int distancia;
    private long startTime;
    private long endTime;
    

    public RutaUCS(Aplicacion app) {
        this.control = app;
        this.Frontera = new LinkedList<>(); //crear cola
        this.visitados = new LinkedList<>(); //crear lista de nodos visitados
        this.nodoActual = new NodoAE();
        this.nodoInicial = new NodoAE();
        this.conexiones = new HashMap<>();
        this.listaMapas = new LinkedList<>();
        this.contador = 0;
        this.distancia = 0;
        this.startTime = this.endTime = 0;
        initMaps();
    }
    public void iniciaUCS(){
        clearAll();
        startTime = System.nanoTime();
        //Asignar nodo inicial a estado inicial
        nodoInicial = crearNodoInicial(nodoInicial);
        Frontera.add(nodoInicial); //cola FIFO
        while(Frontera.size()!=0){
             nodoActual = Frontera.removeFirst(); //extraer nodo de frontera
             if(checkSolucion(nodoActual)){
                System.out.println("Has llegado a tu destino!");
                break;
             }
             //Introducir nodo actual en visitados
             visitados.add(nodoActual);
             contador++;
             visitarCiudad(nodoActual);
        }
        endTime = System.nanoTime();
        resultados(nodoActual);
    }
    public void visitarCiudad(NodoAE actual){
        int sumCostos = 0;
        String ciudad = actual.getDatos(); //Obtiene la ciudad donde se encuentra
        //Obtengo el segundo HashMap
        Set<String> subMapa = conexiones.get(ciudad).keySet();
        //Para cada una de las posibles ciudades a visitar
        for(String subCiudad: subMapa){             
            NodoAE hijo = new NodoAE();
            hijo.setDatos(subCiudad); //La ciudad del HashMap anidado
            hijo.setPadre(actual);
            sumCostos = conexiones.get(ciudad).get(subCiudad);
            hijo.setG_n(sumCostos + actual.getG_n());
            actual.addHijo(hijo);            
            if(checkVisitados(visitados,hijo)){ //Si nodo hijo no esta en visitados
                if(checkFrontera(Frontera,hijo)){ //Si nodo hijo esta en frontera
                    if(hijo.getG_n() < getNodoFrontera(hijo.getDatos()).getG_n()){
                        //Frontera.remove(getIndexFrontera(hijo.getDatos()));
                        //addValue(hijo);
                        Frontera.set(getIndexFrontera(hijo.getDatos()), hijo);
                    }       
                }
                else{
                        addValue(hijo);
                    }                
            } 
        }        
    }
    public NodoAE getNodoFrontera(String city){
        for(NodoAE n : Frontera){
            if(n.getDatos().equals(city))
                return n;
        }
        return null;
    }
    public int getIndexFrontera(String city){
        int i=0;
        for(NodoAE n : Frontera){
            if(n.getDatos().equals(city))
                return i;
            i++;
        }
        return -1;
    }
    
    //Compara el estado actual contra la solución para saber si se ha llegado a la solución
    public boolean checkSolucion(NodoAE currentNode){
        if(currentNode.getDatos().equals(control.getCiudadFinal())){
            return true;
        }
        return false;
    }

    //Metodo que se utiliza para escribir los valores del estado inicial
    public NodoAE crearNodoInicial(NodoAE inicial){
        inicial.setDatos(control.getCiudadInicial());
        return inicial;
    }
    private void initMaps(){
        int size=0;
        String[] ciudades = { "CDMX", "Toluca", "Cuernavaca", "Puebla", "Pachuca", "Querétaro", "Poza Rica", "Acapulco", "Veracruz", "Huatulco" };
        LinkedList<String[]> rutas = new LinkedList<>();
        LinkedList<int[]> cost = new LinkedList<>();
        String[] cdmx = {"Querétaro","Toluca","Acapulco","Cuernavaca","Huatulco","Puebla","Veracruz","Poza Rica","Pachuca"};
        String[] toluca = {"Querétaro","CDMX","Acapulco"};
        String[] cuernavaca = {"Acapulco","CDMX"};
        String[] puebla = {"CDMX","Huatulco","Veracruz"};
        String[] pachuca = {"CDMX","Poza Rica"};
        String[] queretaro = {"Toluca","CDMX"};
        String[] pozaRica = {"CDMX","Pachuca"};
        String[] acapulco = {"Toluca","CDMX","Cuernavaca"};
        String[] veracruz = {"Puebla","CDMX"};
        String[] huatulco = {"CDMX","Puebla"};
        int[] costoCDMX = {218,60,382,49,695,134,397,276,90};
        int[] costoToluca = {196,60,388};
        int[] costoCuerna = {291,49};
        int[] costoPuebla = {134,569,273};
        int[] costoPachuca = {90,183};
        int[] costoQueretaro = {196,218};
        int[] costoPoza = {276,183};
        int[] costoAca = {388,382,291};
        int[] costoVera = {273,397};
        int[] costoHuatulco = {695,569};
        rutas.addLast(cdmx);
        rutas.addLast(toluca);
        rutas.addLast(cuernavaca);
        rutas.addLast(puebla);
        rutas.addLast(pachuca);
        rutas.addLast(queretaro);
        rutas.addLast(pozaRica);
        rutas.addLast(acapulco);
        rutas.addLast(veracruz);
        rutas.addLast(huatulco);
        cost.addLast(costoCDMX);
        cost.addLast(costoToluca);
        cost.addLast(costoCuerna);
        cost.addLast(costoPuebla);
        cost.addLast(costoPachuca);
        cost.addLast(costoQueretaro);
        cost.addLast(costoPoza);
        cost.addLast(costoAca);
        cost.addLast(costoVera);
        cost.addLast(costoHuatulco);
        for(String s: ciudades){ //Para cada ciudad crear un HashMap
            HashMap<String,Integer> costos = new HashMap<>();
            size = rutas.getFirst().length;
            for(int i=0; i<size;i++){
                rutas.getFirst(); //regresa un arreglo de strings con ciudades
                costos.put(rutas.getFirst()[i],cost.getFirst()[i]);
            }
            rutas.removeFirst();
            cost.removeFirst();
            conexiones.put(s, costos);
        }        
    }
    private void clearAll(){
        distancia = 0;
        contador = 0;
        Frontera.clear();
        visitados.clear();
        nodoActual.setDatos("");
        nodoActual.getHijos().clear();
        nodoActual.setPadre(null);
        nodoInicial.setDatos("");
        nodoInicial.getHijos().clear();
        nodoInicial.setPadre(null);
    }
    
    private void addValue(NodoAE nodo) {

        if (Frontera.isEmpty()) {
            Frontera.add(nodo);
        } else if (Frontera.get(0).getG_n() > nodo.getG_n()) {
            Frontera.add(0, nodo);
        } else if (Frontera.get(Frontera.size() - 1).getG_n() < nodo.getG_n()) {
            Frontera.add(Frontera.size(), nodo);
        } else {
            int i = 0;
            while (Frontera.get(i).getG_n() < nodo.getG_n()) {
                i++;
            }
            Frontera.add(i, nodo);
        }

    }
    public boolean checkFrontera(LinkedList<NodoAE> frontera, NodoAE nodo){
        if(!frontera.isEmpty()){ 
            for(NodoAE n: frontera){        
                if(n.getDatos().equals(nodo.getDatos())){
                    return true;
                }         
            }
        }
            return false;
    }        
    public boolean checkVisitados(LinkedList<NodoAE> vis, NodoAE nodo){
        if(!vis.isEmpty()){ 
            for(NodoAE n: vis){        
                if(n.getDatos().equals(nodo.getDatos())){
                    return false;
                }         
            }
        }
            return true;
    }
    public void resultados(NodoAE nodo){
        String toWindow = "";

        NodoAE nodofinal = nodo;
        LinkedList<String> resultados = new LinkedList<>();
        distancia+= nodofinal.getG_n();
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
        control.setResultados(endTime-startTime,distancia, contador, toWindow);
        
    }    
}
