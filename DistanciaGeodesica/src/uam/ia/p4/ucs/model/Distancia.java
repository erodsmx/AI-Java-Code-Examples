/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p4.ucs.model;

/**
 *
 * @author delta9
 */
public class Distancia {

    public Distancia() {
    }
    
    public Double distGeodesica(Double lat1, Double lon1, Double lat2, Double lon2){
        Double grad_rad = 0.01745329;
        Double rad_grad = 57.29577951;
        Double longitud = Math.abs(lon1-lon2);
        Double valor = (Math.sin(lat1*grad_rad) * Math.sin(lat2*grad_rad))
                        + (Math.cos(lat1 * grad_rad) * Math.cos(lat2 * grad_rad) * Math.cos(longitud*grad_rad));
        return (Math.acos(valor)*rad_grad)*111.32;
    }    
}
