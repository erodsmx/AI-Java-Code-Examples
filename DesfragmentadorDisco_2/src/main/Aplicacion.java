/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import view.VentanaNormal;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
/**
 *
 * @author delta9
 */
public class Aplicacion {

    private VentanaNormal ventanaNormal;
    private Color[] colors;
    private int elementosTotales;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();//En la ventana principal se elige la dificultad

    }

    public Aplicacion() {
        this.colors = new Color[6];
        this.elementosTotales=0;
        
    }
    public void inicia(){
        // Aplicacion le pasa su referencia a VentanaPrincipal para tener acceso a los metodos que llenan aleatoreamente.
        initColor();
        abrirNormal();
    }
    public void abrirNormal(){
        ventanaNormal = new VentanaNormal(this);
        ventanaNormal.setVisible(true);
    }

    /*La aplicacion inicializa los colores antes de utilizarlos*/
    private void initColor(){
    //Initialize the values of the array
    colors[0] = new Color(255,255,255); //blanco
    colors[1] = new Color(255,255,0); //amarillo
    colors[2] = new Color(255,0,0); //rojo
    colors[3] = new Color(255,200,0); //naranja
    colors[4] = new Color(192,192,192); //light gray
    colors[5] = new Color(0,0,255); //azul
    
    }
    
    /*Genera numeros aleatorios para elegir un color*/
    public Color randomColorNum(){
    Random r = new Random();
    return colors[r.nextInt(6)];
}
    public int randomNum(int size){
    Random r = new Random();
    return r.nextInt(size);
}
    //Rellena de manera aleatoria las celdas.
    public void asignarCelda(JButton[] buttons, int arraySize){
        int i,aleatorio,k;
        elementosTotales=0;
        aleatorio = randomNum(arraySize); //Elige una posicion al azar de todas las celdas posibles
        int numCeldasPorColor; //Elige cuantas celdas de cada color se van a pintar       
        for(i=1;i<colors.length;i++){ //Por cada iteracion va rellenando cada uno de los colores
            numCeldasPorColor = randomNum((arraySize/5)-1)+1;//num de espacios entre 5 colores, a cada color le toca un maximo de elementos
            elementosTotales+= numCeldasPorColor;
            k=1; //Numeracion para cada celda de color
            for(int j=0; j<numCeldasPorColor;j++ ){ //Colorea el numero de celdas que eligio previamente
                if(buttons[aleatorio].getBackground()==Color.WHITE){
                   buttons[aleatorio].setBackground(colors[i]);
                   buttons[aleatorio].setText(String.valueOf(k));
                   k++;
                }
                do{
                    aleatorio = randomNum(arraySize);
                }while(buttons[aleatorio].getText()!="");//Si elige la misma posicion vuelve a generar otra                
            }                
            
        }

    }

    public void desfragmentarUnidad(JButton[] buttons, int arraySize){
        int i,j,k=0;
        JButton tempbtn =new JButton("");
        tempbtn.setOpaque(true); //Para poder mostrar el color del boton
        tempbtn.setBackground(Color.WHITE); //inicializa blanco

        FragmentoColor temporal = new FragmentoColor();
        for(i=0;i< arraySize;i++){//Va separando cada fragmento de color en una lista
            if(buttons[i].getBackground().getRGB()== colors[1].getRGB()){
                temporal.addFragment(buttons[i], 0); //Amarillo
            }
            else if(buttons[i].getBackground().getRGB()== colors[2].getRGB()){
                temporal.addFragment(buttons[i], 1); //Rojo
            }
            else if(buttons[i].getBackground().getRGB()== colors[3].getRGB()){
                temporal.addFragment(buttons[i], 2); //Naranja
            }
            else if(buttons[i].getBackground().getRGB()== colors[4].getRGB()){
                temporal.addFragment(buttons[i], 3); //Gris
            }
            else if(buttons[i].getBackground().getRGB()== colors[5].getRGB()){
                temporal.addFragment(buttons[i], 4); //Azul
            }
            else if(buttons[i].getBackground()== Color.WHITE)
                continue;
        }
        for(LinkedList<JButton> b: temporal.getDisco()){ //Ordena cada una de las listas de colores
            insertionSort(b);
        }
        press();
        buttons[0].setBackground(Color.GREEN);
        buttons[0].revalidate();
        buttons[0].repaint();
        ventanaNormal.getjPanel1().revalidate();
        ventanaNormal.getjPanel1().repaint();
        press();
             
    }

public void press()
 { 
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
 }    
    
    //ordenamiento de datos
    public static void insertionSort(LinkedList<JButton> blist) {
    for (int i = 1; i < blist.size(); i++) {
        JButton current = blist.get(i);        
        int j = i - 1;
        while(j >= 0 && Integer.parseInt(current.getText()) < Integer.parseInt(blist.get(j).getText())) {
            //Collections.swap(blist, j+1, j);
            blist.set(j+1, blist.get(j));            
            j--;
        }
        // at this point we've exited, so j is either -1
        // or it's at the first element where current >= a[j]
        blist.set(j+1, current);
    }
}

    
}
