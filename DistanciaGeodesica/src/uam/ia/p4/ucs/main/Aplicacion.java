/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p4.ucs.main;

import uam.ia.p4.ucs.model.*;
import uam.ia.p4.ucs.view.Geodesico;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    private Geodesico ventanageo;
    private Distancia distancia;

    public Aplicacion() {
        this.distancia = new Distancia();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();
    }
    public void inicia(){
        ventanageo = new Geodesico(this);
        ventanageo.setVisible(true);
    }
    public Double iniciaGeo(Double lat1, Double lon1, Double lat2, Double lon2){
        return distancia.distGeodesica(lat1, lon1, lat2, lon2);
    }

    
}
