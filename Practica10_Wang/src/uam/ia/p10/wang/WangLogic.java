/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p10.wang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

// !p, !(qvr), s^t, u->w => !(a^b), cvd, e->f, g
// avb, !(b^c) => c->a
// p->q, q->r, !r =>!p
/**
 *
 * @author delta9
 */
public class WangLogic {
    private String teorema;
    private Aplicacion control;
    private String[] partes;
    private LinkedList<String> subprem;
    private LinkedList<String> subinfer;
    private boolean teoremaFalso =false;
    private boolean teoremah1Falso =false;
    private boolean teoremah2Falso =false;
    private boolean teosub =false;
    
    public WangLogic(Aplicacion app) {
        this.control = app;
        this.teorema = "";
    }
    
    /**
     * El usuario ingresa el teorema y el programa lo divide en premisas e inferencias
     */
    public void inputData(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] premisas;
        String[] inferencias;        
        try {teorema = reader.readLine();} //Capturar Teorema //Capturar Teorema
         catch (IOException ex) {}
        teorema = teorema.replaceAll(" ", "");
        partes = teorema.split("=>"); //premisas a la izquierda, inferencia a la derecha
        premisas =  partes[0].split(",");
        inferencias = partes[1].split(","); 
        subprem = new LinkedList(Arrays.asList(premisas));
        subinfer = new LinkedList(Arrays.asList(inferencias));              
    }
    
    public boolean ejecutarWang(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        //Paso 1 se ejecuta en la entrada de datos
        //Paso 2, eliminar negaciones
        eliminarNegacion(subpremisas, subinferencias);
        //Paso 3 reemplazar conectivas ^ a la izquierda, v a la derecha
        reemplazarConectiva(subpremisas, subinferencias);
        //Paso 4 generar hilos
        if(generarHilos(subpremisas, subinferencias)) //V a la izq o ^ a la derecha.
            return true;
        if(teoremaFalso)
            return false;
        //Paso 5 eliminar implicaciones
        eliminarImplicaciones(subpremisas, subinferencias);
        //Paso 6 Teorema demostrado?
        if(!contieneConectivas(subpremisas, subinferencias)){ //Si ya no hay conectivas
            if(isProven(subpremisas, subinferencias)){ //Si el teorema quedo demostrado
                teosub = true;
                return true; //tiene el mismo # de elementos y los elementos son iguales
            }
            //Paso 7
            else
                return false; // algun elemento es distinto o tiene numero de elementos distintos a la izq y der
        }
        return ejecutarWang(subpremisas, subinferencias);
/*        
        for(String s: subpremisas){
        System.out.print(s+",");        
        
        }
        System.out.print(" => ");
        for(String s: subinferencias){
        System.out.print(s+",");        
        
        }
        */
    }
    public void eliminarNegacion(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        String temp;
/*Busca negacion en premisas*/        
        for(int i=0;i<subpremisas.size();i++){
            temp = subpremisas.get(i);
            if(temp.length() == 2){ //Si es negacion sencilla !p, !q
                if(temp.charAt(0) == '!'){
                    subinferencias.add(temp.charAt(1)+"");
                    subpremisas.remove(i);
                    i--;
                    System.out.print("Eliminando Negacion en premisa: ");       
                    printTeorema(subpremisas,subinferencias);
                }
            } 
            else if(temp.length() == 6){ // caso !(pvq) y !(p^q)
                if(temp.charAt(0) == '!' && temp.charAt(1)== '(' ){
                    subinferencias.add(temp.substring(2,5));
                    subpremisas.remove(i);
                    System.out.print("Eliminando Negacion en premisa: ");       
                    printTeorema(subpremisas,subinferencias);                    
                }
                
            }            
        }
        for(int i=0;i<subinferencias.size();i++){
            temp = subinferencias.get(i);
            if(temp.length() == 2){ //Si es negacion sencilla en inferencia
                if(temp.charAt(0) == '!'){
                    subpremisas.add(temp.charAt(1)+"");
                    subinferencias.remove(i);
                    i--;
                    System.out.print("Eliminando Negacion en inferencia: ");       
                    printTeorema(subpremisas,subinferencias);
                }
            } 
            else if(temp.length() == 6){ // caso !(pvq) y !(p^q)
                if(temp.charAt(0) == '!' && temp.charAt(1)== '('){
                    subpremisas.add(temp.substring(2,5));
                    subinferencias.remove(i);                    
                    System.out.print("Eliminando Negacion en inferencia: ");       
                    printTeorema(subpremisas,subinferencias);
                }
            }
        }
    }
    
    public void reemplazarConectiva(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        String temp;
        for(int i=0; i< subpremisas.size(); i++){
            temp = subpremisas.get(i);                    
            if(temp.length() == 3){ //caso (p ^ q) a la izquierda
               if(temp.charAt(1) == '^'){
                   subpremisas.set(i, temp.charAt(0)+"" );
                   subpremisas.add(i+1, temp.charAt(2)+"");
                    System.out.print("Reemplazando conectiva en premisa: ");       
                    printTeorema(subpremisas,subinferencias);                   
               }   
            }
        }
        for(int i=0; i< subinferencias.size(); i++){
            temp = subinferencias.get(i);                    
            if(temp.length() == 3){ //caso (r v p) a la derecha
               if(temp.charAt(1) == 'v'){
                   subinferencias.set(i, temp.charAt(0)+"" );
                   subinferencias.add(i+1, temp.charAt(2)+"");
                    System.out.print("Reemplazando conectiva en inferencia: ");       
                    printTeorema(subpremisas,subinferencias);  
               }   
            }
        }
        
        
        for(int i=0; i< subpremisas.size(); i++){
            temp = subpremisas.get(i);                    
            if(temp.length() == 4 && temp.charAt(0) == '!' ){ //caso (!p ^ q) a la izquierda
               if(temp.charAt(2) == '^'){
                   subpremisas.set(i,"!"+ temp.charAt(1)+"" );
                   subpremisas.add(i+1, temp.charAt(3)+"");
                    System.out.print("Reemplazando conectiva en premisa: ");       
                    printTeorema(subpremisas,subinferencias);                   
               }   
            }
        }
        for(int i=0; i< subinferencias.size(); i++){
            temp = subinferencias.get(i);                    
            if(temp.length() == 4 && temp.charAt(0) == '!' ){ //caso (!r v p) a la derecha
               if(temp.charAt(2) == 'v'){
                   subinferencias.set(i,"!"+ temp.charAt(1)+"" );
                   subinferencias.add(i+1, temp.charAt(3)+"");
                    System.out.print("Reemplazando conectiva en inferencia: ");       
                    printTeorema(subpremisas,subinferencias);  
               }   
            }
        }        
    }
    
    public boolean generarHilos(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        String temp;
        LinkedList<String> premisasHilo1, subinfh1;
        LinkedList<String> premisasHilo2, subinfh2;
        LinkedList<String> inferenciasHilo1, subpreh1;
        LinkedList<String> inferenciasHilo2, subpreh2;
        boolean prh1=false, prh2=false, infh1=false, infh2=false;
        for(int i=0; i<subpremisas.size(); i++){
            temp = subpremisas.get(i);
            if(temp.length() == 3){ //caso pvq
                if(temp.charAt(1)== 'v'){ // OR a la izquierda
                    premisasHilo1 = new LinkedList<>();
                    copyList(subpremisas,premisasHilo1);
                    premisasHilo1.set(i, temp.charAt(0)+"");
                    System.out.print("Hilo 1: ");
                    printTeorema(premisasHilo1,subinferencias);   
                    prh1= ejecutarWang(premisasHilo1,subinferencias);
                    if(!contieneConectivas(premisasHilo1,subinferencias) || teosub){
                        if(!prh1)
                            teoremah1Falso = true;
                        else return true;
                    }
                    
                    premisasHilo2 = new LinkedList<>(); //Segundo hilo
                    copyList(subpremisas,premisasHilo2);
                    premisasHilo2.set(i, temp.charAt(2)+"");
                    System.out.print("Hilo 2: ");
                    printTeorema(premisasHilo2,subinferencias);
                    prh2= ejecutarWang(premisasHilo2,subinferencias);
                    if(!contieneConectivas(premisasHilo2,subinferencias)){
                        if(!prh2)
                            teoremah2Falso = true;
                        else return true;
                    }
                }
                
            }
            else if(temp.length() == 4 && temp.charAt(0) == '!'){ //caso !pvq
                    premisasHilo1 = new LinkedList<>();  //Primer hilo
                    copyList(subpremisas,premisasHilo1);
                    subinfh1 = new LinkedList<>();
                    copyList(subinferencias,subinfh1);
                    premisasHilo1.set(i,"!"+ temp.charAt(1)+"");
                    System.out.print("Hilo 1: ");
                    printTeorema(premisasHilo1,subinfh1);   
                    prh1= ejecutarWang(premisasHilo1,subinfh1);             
                    if(!contieneConectivas(premisasHilo1,subinfh1) || teosub){
                        if(!prh1)
                            teoremah1Falso = true;
                        else return true;
                    }
                    premisasHilo2 = new LinkedList<>(); //Segundo hilo
                    copyList(subpremisas,premisasHilo2);
                    subinfh2 = new LinkedList<>();
                    copyList(subinferencias,subinfh2);
                    premisasHilo2.set(i, temp.charAt(3)+"");
                    System.out.print("Hilo 2: ");
                    printTeorema(premisasHilo2,subinfh2);
                    prh2= ejecutarWang(premisasHilo2,subinfh2); 
                    if(!contieneConectivas(premisasHilo2,subinfh2) ){
                        if(!prh2)
                            teoremah2Falso = true;
                        else return true;
                    }                    
            }

//Otro caso            
            else if(temp.length() == 4 && temp.charAt(2) == '!'){ //caso pv!q
                    premisasHilo1 = new LinkedList<>();  //Primer hilo
                    copyList(subpremisas,premisasHilo1);
                    subinfh1 = new LinkedList<>();
                    copyList(subinferencias,subinfh1);
                    premisasHilo1.set(i,temp.charAt(0)+"");
                    System.out.print("Hilo 1: ");
                    printTeorema(premisasHilo1,subinfh1);   
                    prh1= ejecutarWang(premisasHilo1,subinfh1);             
                    if(!contieneConectivas(premisasHilo1,subinfh1) || teosub){
                        if(!prh1)
                            teoremah1Falso = true;
                        else return true;
                    }
                    premisasHilo2 = new LinkedList<>(); //Segundo hilo
                    copyList(subpremisas,premisasHilo2);
                    subinfh2 = new LinkedList<>();
                    copyList(subinferencias,subinfh2);
                    premisasHilo2.set(i, "!"+temp.charAt(3)+"");
                    System.out.print("Hilo 2: ");
                    printTeorema(premisasHilo2,subinfh2);
                    prh2= ejecutarWang(premisasHilo2,subinfh2); 
                    if(!contieneConectivas(premisasHilo2,subinfh2) ){
                        if(!prh2)
                            teoremah2Falso = true;
                        else return true;
                    }                    
            }            

//Fin otro caso            
        }
//EL procedimiento es el mismo para las premisas y las subinferencias.
                for(int i=0; i<subinferencias.size(); i++){
            temp = subinferencias.get(i);
            if(temp.length() == 3){
                if(temp.charAt(1)== '^'){ // AND a la derecha
                    inferenciasHilo1 = new LinkedList<>();
                    copyList(subinferencias,inferenciasHilo1);
                    subpreh1 = new LinkedList<>();
                    copyList(subpremisas,subpreh1);
                    inferenciasHilo1.set(i, temp.charAt(0)+"");
                    System.out.print("Hilo 1: ");
                    printTeorema(subpremisas,inferenciasHilo1);   
                    infh1= ejecutarWang(subpreh1,inferenciasHilo1);
                    if(!contieneConectivas(subpreh1,inferenciasHilo1)){
                        if(!infh1)
                            teoremah1Falso = true;
                        else return true;
                    }  
                    
                    inferenciasHilo2 = new LinkedList<>();
                    copyList(subinferencias,inferenciasHilo2);
                    subpreh2 = new LinkedList<>();
                    copyList(subpremisas,subpreh2);
                    inferenciasHilo2.set(i, temp.charAt(2)+"");
                    System.out.print("Hilo 2: ");
                    printTeorema(subpreh2,inferenciasHilo2);   
                    infh2= ejecutarWang(subpreh2,inferenciasHilo2);
                    if(!contieneConectivas(subpreh2,inferenciasHilo2)){
                        if(!infh2)
                            teoremah1Falso = true;
                        else return true;
                    } 
                }
            }
            else if(temp.length() == 4 && temp.charAt(0) == '!'){ //caso !p^q
                    inferenciasHilo1 = new LinkedList<>();  //Primer hilo
                    copyList(subinferencias,inferenciasHilo1);
                    inferenciasHilo1.set(i,"!"+ temp.charAt(1)+"");
                    subpreh1 = new LinkedList<>();
                    copyList(subpremisas,subpreh1);
                    System.out.print("Hilo 1: ");
                    printTeorema(subpreh1,inferenciasHilo1);   
                    infh1= ejecutarWang(subpreh1,inferenciasHilo1);
                    if(!contieneConectivas(subpreh1,inferenciasHilo1)){
                        if(!infh1)
                            teoremah1Falso = true;
                        else return true;
                    }
                    inferenciasHilo2 = new LinkedList<>(); //Segundo hilo
                    copyList(subinferencias,inferenciasHilo2);
                    subpreh2 = new LinkedList<>();
                    copyList(subpremisas,subpreh2);
                    inferenciasHilo2.set(i, temp.charAt(3)+"");
                    System.out.print("Hilo 2: ");
                    printTeorema(subpreh2,inferenciasHilo2);   
                    infh2= ejecutarWang(subpreh2,inferenciasHilo2);
                    if(!contieneConectivas(subpreh2,inferenciasHilo2)){
                        if(!infh2)
                            teoremah2Falso = true;
                        else return true;
                    }                    
            }            
        }
                if(teoremah1Falso && teoremah2Falso)
                    teoremaFalso = true;
        if(prh1 || prh2 || infh1 || infh2)
            return true;
        return false;
    }

    public void eliminarImplicaciones(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        String temp;
        for(int i=0; i< subpremisas.size(); i++){
            temp= subpremisas.get(i);
            if(temp.contains("->")){
                if(temp.length() == 4){ //Caso p -> q <-> !p v q
                    subpremisas.set(i,"!" + temp.charAt(0) + "v" + temp.charAt(3) );                   
                }
                else if(temp.substring(0, temp.indexOf("-")).length() ==3 ){ //Caso pvq -> r
                   subpremisas.set(i,"!" +"("+ temp.substring(0, temp.indexOf("-")) +")" + "v" + temp.charAt(temp.length()-1) ); 
                }                
                else if(temp.substring(0 , temp.indexOf("-")).length() > 4 ){ //Caso !(pvq) -> r
                       subpremisas.set(i,temp.substring(2, temp.indexOf("-")-1) + "v" + temp.charAt(temp.length()-1) );     
                }
                else if(temp.length() == 5 && temp.charAt(0) == '!'){ //Caso !p -> q <-> p v q
                    subpremisas.set(i,temp.charAt(1) + "v" + temp.charAt(4) );                    
                }
                else if(temp.length() == 6 && temp.charAt(0) == '!' && temp.charAt(4) == '!'){ //Caso !p -> !q <-> p v q
                    subpremisas.set(i,temp.charAt(1) + "v" + "!"+temp.charAt(5) );                    
                }                
                System.out.print("Eliminando implicacion en premisa: ");       
                printTeorema(subpremisas,subinferencias);                 
            }
        }
        for(int i=0; i< subinferencias.size(); i++){
            temp= subinferencias.get(i);
            if(temp.contains("->")){
                if(temp.length() == 4){ //Caso p -> q <-> !p v q
                    subinferencias.set(i,"!" + temp.charAt(0) + "v" + temp.charAt(3) );                     
                }
                else if(temp.substring(0, temp.indexOf("-")).length() ==3 ){ //Caso pvq -> r
                   subinferencias.set(i,"!" +"("+ temp.substring(0, temp.indexOf("-")) +")" + "v" + temp.charAt(temp.length()-1) );
                }                
                else if(temp.substring(0 , temp.indexOf("-")).length() > 4 ){ //Caso !(pvq) -> r
                       subinferencias.set(i,temp.substring(2, temp.indexOf("-")-1) + "v" + temp.charAt(temp.length()-1) );                               
                }
                else if(temp.length() == 5 && temp.charAt(0) == '!'){ //Caso !p -> q <-> p v q
                    subinferencias.set(i,temp.charAt(1) + "v" + temp.charAt(4) );                    
                }
                System.out.print("Eliminando implicacion en inferencia: "); 
                    printTeorema(subpremisas,subinferencias);                    
            }
        }
    }
    
    public boolean isProven(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        int contador = subpremisas.size();
        if(subpremisas.size() == subinferencias.size()){//Si tienen el mismo numero de elementos
            for(int i=0; i< subpremisas.size();i++){
                for(int j=0; j<subinferencias.size();j++){
                    if(subpremisas.get(i).equals(subinferencias.get(j)) ){
                        contador--;
                        break;
                    }
                }
            }
            if(contador==0)
                return true; //Se demostro el teorema
        }
        return false;        
    }
    
    public boolean contieneConectivas(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        for(String s: subpremisas){
            if(s.contains("^") || s.contains("v") || s.contains("->") || s.contains("!")){
                return true;
            }
        }
        for(String s: subinferencias){
            if(s.contains("^") || s.contains("v")|| s.contains("->")|| s.contains("!")){
                return true;
            }
        }

        return false;
    }
    
    public void printTeorema(LinkedList<String> subpremisas, LinkedList<String> subinferencias){
        for(int i =0; i<subpremisas.size(); i++){
            if(i!=subpremisas.size()-1)
                System.out.print(subpremisas.get(i)+", ");
            else
                System.out.print(subpremisas.get(i));
        }
        System.out.print(" => ");
        for(int i =0; i<subinferencias.size(); i++){
            if(i!=subinferencias.size()-1)
                System.out.print(subinferencias.get(i)+", ");
            else
                System.out.print(subinferencias.get(i)+"\n");
        }        
    }
    
    public LinkedList<String> getSubpremisas() {
        return subprem;
    }

    public LinkedList<String> getSubinferencias() {
        return subinfer;
    }
    private void copyList(LinkedList<String> subpremisas, LinkedList<String> copia){
        for(String s: subpremisas){
            copia.add(s);
        }
    }

    void clear() {
        this.teoremaFalso =false;
        this.teoremah1Falso =false;
        this.teoremah2Falso =false;
        this.teosub = false;
    }
    
}
