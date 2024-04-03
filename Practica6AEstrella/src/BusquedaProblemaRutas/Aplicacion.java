/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusquedaProblemaRutas;

/**
 *
 * @author delta9
 */
public class Aplicacion {
    
    private MainWindow ventana;
    private RutaBFS ruta;
    private RutaUCS Ruta;
    private RutaAE rutaAE;

    public Aplicacion() {
        this.ruta = new RutaBFS(this);
        this.Ruta = new RutaUCS(this);
        this.rutaAE = new RutaAE(this);
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
    
    public void iniciaAE(){
        rutaAE.iniciaAE();
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
