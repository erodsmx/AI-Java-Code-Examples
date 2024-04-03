/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaAE8Puzzle;

import java.util.LinkedList;

/**
 *
 * @author delta9
 */
public class PuzzleAE {
    private Aplicacion control;
    private LinkedList<NodoAE> Frontera; //FIFO
    private LinkedList<NodoAE> visitados;
    private NodoAE nodoInicial;
    private NodoAE nodoActual;
    private NodoAE nodoSolucion;
    private int contador;
    private long startTime;
    private long endTime;
    private String solucion; 


    public PuzzleAE(Aplicacion app) {
        this.control = app;
        this.Frontera = new LinkedList<>(); //crear cola
        this.visitados = new LinkedList<>(); //crear lista de nodos visitados
        this.nodoActual = new NodoAE();
        this.nodoInicial = new NodoAE();
        this.nodoSolucion = new NodoAE();
        this.contador = 0;
        this.startTime = this.endTime = 0;
        this.solucion = "123456780";
        initSol();
    }    
/**
 * Implementación del algoritmo A*
 */    
    public void iniciarAE(){
        startTime = System.currentTimeMillis();
        //Asignar nodo inicial a estado inicial
        crearNodoInicial();
        Frontera.add(nodoInicial); //cola FIFO, agregar nodo inicia a frontera
        while(Frontera.size()!=0){ //Mientras contenga elementos
             nodoActual = Frontera.removeFirst(); //extraer primer nodo de frontera
             if(checkSolucion(nodoActual)){ //Checar solucion
                System.out.println("Se ha resuelto el puzzle!");
                break;
             }
             //Introducir nodo actual en visitados
             visitados.add(nodoActual);
             contador++; //Contador para numero de pasos
             aplicarOperador();
             for(NodoAE n: nodoActual.getHijos()){
                if(checkVisitados(visitados,n)){ //Si nodo hijo no esta en visitados
                    if(checkFrontera(Frontera,n)){ //Si nodo hijo esta en frontera
                        if(n.getF_n() < getNodoFrontera(n.toString()).getF_n()){
                            Frontera.remove(getIndexFrontera(n.toString()));
                            addValue(n); //Inserta ordenado
                        }       
                    }
                    else{
                        addValue(n);
                        }                
                }
             }
        }
        endTime = System.currentTimeMillis();
        resultados(nodoActual); //Imprime resultados en consola
        System.out.println("El tiempo de ejecucion es: " + (endTime-startTime));
        
    }
/**
 * Se aplican todos los operadores en un solo metodo, en cada movimiento
 * se valida si es un movimiento valido o no.
 */    
    public void aplicarOperador(){
        moverDerecha(nodoActual);
        moverIzquierda(nodoActual);
        moverArriba(nodoActual);
        moverAbajo(nodoActual);
        
        
    }
/**
 * Funcion heuristica 1 cuenta numero de tejas mal acomodadas
 * @param tablero la configuración actual del puzzle en forma de string concatenando renglon por renglon
 * @return el valor de la función heurística
 */   
    private int getH1_n(String tablero){ //Tejas mal acomodadas
        int h1_n=0;
        for(int i=0; i< tablero.length(); i++){ //Si la casilla tiene distintos elementos
            if(Character.compare(tablero.charAt(i), solucion.charAt(i)) !=0 ){ //Compare regresa 0 si X == Y
                h1_n++;
            }
        }
        return h1_n;
    }
/**
 * Funcion heuristica 2 Calcula el numero de pasos que hay que dar de la posicion actual a la posicion de la solucion.
 * @param nodo el nodo del estado con que se seta trabajando
 * @return 
 */    
    private int getH2_n(NodoAE nodo){ //Distancia Manhattan
        int h2_n=0;
        int[] posicionActual={0,0};
        int[] posicionSolucion={0,0};
        for(int i=0;i< 9; i++){
            posicionActual = getPosicion(nodo,String.valueOf(i));
            posicionSolucion = getPosicion(nodoSolucion,String.valueOf(i));
            if( posicionActual[0] != posicionSolucion[0] || posicionActual[1] != posicionSolucion[1]){
                h2_n+= Math.abs(posicionSolucion[1] - posicionActual[1]) + Math.abs(posicionSolucion[0] - posicionActual[0] );
            }
        }
        return h2_n;
    }
// Funcion heuristica 3
    private int getH3_n(int h2, String[][] tab){ //h2(n) + 3*S(n)
        int s_n=0, contador=0;
//checa posicion central
        if(!tab[1][1].equals(nodoSolucion.getDato()[1][1])){
            s_n++;  
        }
        /* Despues de evalua casilla central, se va por las dos primeras columnas,
           el caso especial es que al 8 le sigue el cero
        */
        for(int i=0;i<3; i++){ //Ciclo para renglones
            for(int j=0; j<3;j++){//Ciclo para columnas
                if(i==1 && j==1){ //Se salta casilla central
                    continue;
                }
                if("0".equals(tab[i][j])){ //Si es la casilla con num cero se salta.
                    continue;
                }
             //Primero analiza las primeras dos columnas   
                if(j!=2){ //Si casilla no se encuentra en la ultima columna
                    if("8".equals(tab[i][j])){ //Caso especial, num ocho le sigue el cero
                        if(!"0".equals(tab[i][j+1])){
                            s_n+=2;
                        }
                    }
                    else{ //Si no es el numero ocho
                        if(Integer.parseInt(tab[i][j+1]) - Integer.parseInt(tab[i][j+1]) != 1 ){
                           s_n+=2; 
                        }
                    }
                }
             //ahora se analiza la ultima columna
                else{ //Si j==2
                    //Caso para ultima casilla
                    if(i==2){
                        if(Integer.parseInt(tab[0][0]) - Integer.parseInt(tab[i][j]) != 1 ){
                            s_n+=2;
                        }
                    }
                    else{ //Casillas renglon 0 columna 2 y  renglon 1 columna 2
                        if(Integer.parseInt(tab[i+1][1]) - Integer.parseInt(tab[i][j]) != 1 ){
                            s_n+=2;
                        }
                    }
                }
            }
        }
        return h2 + 3*s_n;
    }
//Funcion de evaluacion    
    private int setF_n(NodoAE nodo){
        return nodo.getG_n() + nodo.getH1_n();
//        return nodo.getG_n() + nodo.getH2_n();
//        return nodo.getG_n() + nodo.getH3_n();
//        return nodo.getH1_n() + nodo.getH2_n();
//        return nodo.getH1_n() + nodo.getH3_n();

    }
    //Calcula la suma de las distancias que tienen las casillas en la posicion actual y en la solucion
    private int getG_n(String config){
        int posInicial=0;
        int posFinal=0;
        int g_n=0;
        for(int i=0; i<9; i++){ //Si la casilla tiene distintos elementos
            posInicial = getPosChar(config,Character.forDigit(i, 10));
            posFinal = getPosChar(solucion,Character.forDigit(i, 10));
            g_n+= Math.abs(posInicial - posFinal);
        }        
        return g_n;
    }

 /**
  * La casilla vacia se desplaza hacia la derecha
  * @param actual recibe el nodo actual para poder extraer el tablero y posteriormente asignarlo como padre del hijo generado
  */   
    private void moverDerecha(NodoAE actual) {
        int[] posicion = getPosicion(actual,"0");
        String elementoTemp = "";
        if(posicion[1] != 2){ //Si no se encuentra en la esquina derecha
            String [][] tempPuzzle = new String[3][3];
            copyConfig(actual.getDato(),tempPuzzle);
            elementoTemp = tempPuzzle[posicion[0]][posicion[1]+1];
            tempPuzzle[posicion[0]][posicion[1]+1] = tempPuzzle[posicion[0]][posicion[1]]; //intercambia elementos
            tempPuzzle[posicion[0]][posicion[1]] = elementoTemp;
            crearHijo(tempPuzzle,actual);
        }
    }
/**
 * La casilla vacia se desplaza hacia la izquierda
 * @param actual nodo que se extrajo de frontera
 */
    private void moverIzquierda(NodoAE actual) {
        int[] posicion = getPosicion(actual,"0");
        String elementoTemp = "";
        if(posicion[1] != 0){ //Si no se encuentra en la esquina izquierda
            String [][] tempPuzzle = new String[3][3];
            copyConfig(actual.getDato(),tempPuzzle);
            elementoTemp = tempPuzzle[posicion[0]][posicion[1]-1];
            tempPuzzle[posicion[0]][posicion[1]-1] = tempPuzzle[posicion[0]][posicion[1]]; //intercambia elementos
            tempPuzzle[posicion[0]][posicion[1]] = elementoTemp;
            crearHijo(tempPuzzle,actual);
        }
    }
/**
 * La casilla vacia se desplaza hacia arriba
 * @param actual nodo que se extrajo de frontera
 */
    private void moverArriba(NodoAE actual) {
        int[] posicion = getPosicion(actual,"0");
        String elementoTemp = "";
        if(getPosicion(actual,"0")[0] != 0){ //Si no se encuentra en la primera fila
            String [][] tempPuzzle = new String[3][3];
            copyConfig(actual.getDato(),tempPuzzle);
            elementoTemp = tempPuzzle[posicion[0]-1][posicion[1]];
            tempPuzzle[posicion[0]-1][posicion[1]] = tempPuzzle[posicion[0]][posicion[1]]; //intercambia elementos
            tempPuzzle[posicion[0]][posicion[1]] = elementoTemp;
            crearHijo(tempPuzzle,actual);
        }
    }
/**
 * La casilla vacia se desplaza hacia abajo
 * @param actual nodo que se extrajo de frontera
 */
    private void moverAbajo(NodoAE actual) {
        int[] posicion = getPosicion(actual,"0");
        String elementoTemp = "";
        if(getPosicion(actual,"0")[0] != 2){ //Si no se encuentra en la ultima fila
            String [][] tempPuzzle = new String[3][3];
            copyConfig(actual.getDato(),tempPuzzle);
            elementoTemp = tempPuzzle[posicion[0]+1][posicion[1]];
            tempPuzzle[posicion[0]+1][posicion[1]] = tempPuzzle[posicion[0]][posicion[1]]; //intercambia elementos
            tempPuzzle[posicion[0]][posicion[1]] = elementoTemp;
            crearHijo(tempPuzzle,actual);
        }
    }
/**
 * crear hijo genera un nuevo nodo y calcula todas sus funciones heuristicas y la función de evaluación
 * @param tempPuzzle la configuracion actual del puzzle con el movimiento hecho
 * @param actual el nodo actual con el que se trabaja, se usa para la referencia del padre
 */    
    private void crearHijo(String [][] tempPuzzle, NodoAE actual){
            NodoAE hijo = new NodoAE();
            hijo.setDatos(tempPuzzle); // Tablero con movimiento realizado
            hijo.setH1_n(getH1_n(hijo.toString())); //le pasa el tablero como string para comparar char by char
            hijo.setH2_n(getH2_n(hijo)); //Calcula h2
            hijo.setH3_n(getH3_n(hijo.getH2_n(), hijo.getDato())); //calcula h3
            hijo.setG_n(getG_n(hijo.toString())); //Calcula g(n)
            hijo.setF_n(setF_n(hijo)); //Calcula f(n)
            hijo.setPadre(actual);
            actual.addHijo(hijo);        
    }
    
    /*Insercion ordenada en frontera*/
    private void addValue(NodoAE nodo) {

        if (Frontera.isEmpty()) {
            Frontera.add(nodo);
        } else if (Frontera.get(0).getF_n() > nodo.getF_n()) {
            Frontera.add(0, nodo);
        } else if (Frontera.get(Frontera.size() - 1).getF_n() < nodo.getF_n()) {
            Frontera.add(Frontera.size(), nodo);
        } else {
            int i = 0;
            while (Frontera.get(i).getF_n() < nodo.getF_n()) {
                i++;
            }
            Frontera.add(i, nodo);
        }

    }

/**
 * Revisa si el nodo a buscar se encuentra en la frontera
 * @param frontera
 * @param nodo
 * @return true si el nodo se encuentra en la lista
 */    
    public boolean checkFrontera(LinkedList<NodoAE> frontera, NodoAE nodo){
        if(!frontera.isEmpty()){ 
            for(NodoAE n: frontera){        
                if(n.toString().equals(nodo.toString())){
                    return true;
                }         
            }
        }
            return false;
    }
/**
 * Revisa que el nodo no se encuentre en visitados
 * @param vis lista de nodos visitados
 * @param nodo elemento a buscar
 * @return true si nodo hijo no esta en visitados
 */    
    public boolean checkVisitados(LinkedList<NodoAE> vis, NodoAE nodo){
        if(!vis.isEmpty()){ 
            for(NodoAE n: vis){        
                if(n.toString().equals(nodo.toString())){
                    return false;
                }         
            }
        }
            return true;
    }
    //Obtiene la posición en el tablero de la casilla dada, se usa para saber que movimientos se pueden hacer.
    public int[] getPosicion(NodoAE nodo, String num){
        int[] posicion ={0,0};
        String[][] puzzle;
        puzzle = nodo.getDato();
        for(int i=0 ; i<3;i++){
            for(int j=0; j<3; j++){
                if(puzzle[i][j].equals(num)){
                    posicion[0]=i; //Renglon
                    posicion[1]=j; //Columna
                }
            }
        }

        return posicion;
    }
    
    //Dada la configuracion del tablero busca en frontera el nodo que tenga los mismos datos
    public NodoAE getNodoFrontera(String config){
        for(NodoAE n : Frontera){
            if(n.toString().equals(config))
                return n;
        }
        return null;
    } 
/**
 * Obtiene la posición del nodo que contiene la configuración a buscar
 * @param config La configuración del tablero que se va a buscar
 * @return posición del elemento en la lista frontera
 */    
    public int getIndexFrontera(String config){
        int i=0;
        for(NodoAE n : Frontera){
            if(n.toString().equals(config))
                return i;
            i++;
        }
        return -1;
    }
/**
 * 
 * @param config Contiene la posicion de todas las casillas en un solo string
 * @param caracter El elemento a buscar
 * Todas las casillas forman un string, el método busca la posición del elemento dado,
 * este metodo se utiliza al calcular g(n)
 * @return la posición del elemento en el string que concatena los elementos renglon por renglon
 */
    private int getPosChar(String config, char caracter){
        for(int i=0; i< config.length(); i++){ //Si la casilla tiene distintos elementos
            if(Character.compare(config.charAt(i), caracter) !=0 ){ //Compare regresa 0 si X == Y
                
            }
        }  
        return 0;
    }
/**
 * 
 * @param config, arreglo con los elementos del tablero a copiar
 * @param temp arreglo destino
 */
    private void copyConfig(String[][] config, String[][] temp){
        for(int i= 0; i<3; i++){
            for(int j=0; j<3; j++){
                temp[i][j]= config[i][j];
            }
        }
    }
    
    //inicializa nodo con el estado inicial
    public void crearNodoInicial(){
        nodoInicial.getDato()[0][0] = "8";
        nodoInicial.getDato()[0][1] = "7";
        nodoInicial.getDato()[0][2] = "6";
        nodoInicial.getDato()[1][0] = "1";
        nodoInicial.getDato()[1][1] = "5";
        nodoInicial.getDato()[1][2] = "4";
        nodoInicial.getDato()[2][0] = "3";
        nodoInicial.getDato()[2][1] = "0";
        nodoInicial.getDato()[2][2] = "2";
    }
/**
 * Imprime resultados en consola
 * @param nodo La solucion del problema
 */    
    public void resultados(NodoAE nodo){
        NodoAE nodofinal = nodo;
        LinkedList<String> resultados = new LinkedList<>();
        while(nodofinal.getPadre() != null){
            resultados.addFirst(nodofinal.toString());
            nodofinal = nodofinal.getPadre();   
        }
        resultados.addFirst(nodoInicial.toString());
        System.out.println("Se generaron " + contador +" pasos para encontrar la solución\n");
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
    //Compara el estado actual contra la solución para saber si se ha llegado a la solución
    public boolean checkSolucion(NodoAE currentNode){
        if(currentNode.toString().equals(solucion)){
            return true;
        }
        return false;
    }
/**
 * Inicializa el nodo solucion
 */    
    private void initSol(){
        String[][] tabSolucion;
        tabSolucion = new String[3][3];
        int k=1;
        for(int i=0; i<3; i++ ){
            for(int j=0; j<3; j++){
                tabSolucion[i][j] = String.valueOf(k);
                k++;
            }
        }
        tabSolucion[2][2] = "0";
        nodoSolucion.setDatos(tabSolucion);
    }
}
