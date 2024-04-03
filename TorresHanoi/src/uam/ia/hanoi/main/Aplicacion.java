/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.hanoi.main;

import uam.ia.hanoi.model.HanoiBFS;
import uam.ia.hanoi.view.Ventana;

/**
 *
 * @author delta9
 */
public class Aplicacion {

    private static HanoiBFS torre;
    private Ventana ventana;

    public Aplicacion() {
        this.torre = new HanoiBFS(this);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }
    
    public void inicia(){
//        ventana = new Ventana(this);
//        ventana.setVisible(true);
System.out.println("Algoritmo BFS:");
        torre.iniciaBFS();
        System.out.println("\nAhora el algoritmo DFS:");
        torre.iniciaDFS();
        System.out.println("\nComo leer la cadena de caracteres:\n\tEl string de cada estado se forma concatenando los valores de cada disco\n\tLee cada varilla desde el disco inferior hasta el superior"
                + "\n\tEj: 3A2A1A, Imprime elementos de varilla A, primero imprime el disco 3=3A=Tamaño+Posicion, luego imprime 2A y por último 1A\n\t    Se pasa a la siguiente lista (varilla B), es vacia no imprime nada, luego la lista C tambien vacia no imprime nada\n\t"
                + "3A2A1B: Imprime dos discos de la varilla A, luego imprime un disco de la varilla B y como varilla C es vacia no imprime nada\n\tPara dibujar los estados: 3A2A1A Disco 3 en varilla A, Disco 2 en varilla A, Disco 1 en varilla A"
                + "\n\t3B2C1C: Disco 3 en varilla B, Disco 2 en varilla C, Disco 1 en Varilla C, los discos siempre estan ordenados en tamaño de izq a derecha");
        
    }
    
}
