/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p10.wang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    private WangLogic wang;

    public Aplicacion() {
        wang = new WangLogic(this);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }

    private void inicia() {
        System.out.println("Reglas para la entrada de datos:");
        System.out.println("\t Escribir premisas e inferencias separadas por comas: premisa1, premisa2,... => inferencia1, inferencia2,...");
        System.out.println("\t No usar parentesis en premisas simples como: (q), (!p) , (avc), (a->c), solo usarlos si lleva negacion");
        System.out.println("\t Escribir todo en mayusculas o todo en minusculas");
        System.out.println("\t Operacion AND: ^ , Operación OR: v , Negacion: ! , Implicacion: ->");
        System.out.println("\t Ejemplos de como escribir premisas: !p, !(qvr), s^t, u->w => !(a^b), cvd, e->f, g\n");
        System.out.println("\t Ejemplos a probar de las diapositivas: \n\t P->Q,P => Q \n\t !P -> !Q, !P => !Q \n\t avb, !(b^c) => c->a \n\t p->q, q->r, !r =>!p");
        
        String respuesta = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
        do{ 
            wang.clear();
            System.out.println("\nIngresa la expresión completa:");            
            wang.inputData();
            System.out.println("");
            if(wang.ejecutarWang(wang.getSubpremisas(), wang.getSubinferencias())){
                System.out.println("El teorema quedo demostrado");
            }
            else
                System.out.println("EL teorema no quedo demostrado");


            System.out.println("\nContinuar? (Y/N)");            
            try { respuesta = reader.readLine();} //Capturar Teorema //Capturar Teorema
            catch (IOException ex) {}
                respuesta= respuesta.toUpperCase();
        }while("Y".equals(respuesta));

    }

}
