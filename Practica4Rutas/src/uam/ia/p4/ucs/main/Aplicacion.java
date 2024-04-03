/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p4.ucs.main;

import uam.ia.p4.ucs.model.*;
import uam.ia.p4.ucs.view.MainWindow;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    
    private MainWindow ventana;
    private RutaBFS ruta;
    private RutaUCS Ruta;

    public Aplicacion() {
        this.ruta = new RutaBFS(this);
        this.Ruta = new RutaUCS(this);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }
    public void inicia(){
        ventana = new MainWindow(this);
        ventana.setVisible(true);
    }
    public void iniciaBFS(){
        ruta.iniciaBFS();
    }
    
    public void iniciaUCS(){
        Ruta.iniciaUCS();
    }
    public String getCiudadInicial(){
        return ventana.getCiudadInicial();
    }
    public String getCiudadFinal(){
        return ventana.getCiudadFinal();
    }
    public void setResultados(long time, int dist,int contador, String ruta){
        ventana.setResults(time,dist,contador, ruta);
    }
    
}
