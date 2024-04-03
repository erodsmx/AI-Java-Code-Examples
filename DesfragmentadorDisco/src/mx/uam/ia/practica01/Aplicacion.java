/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.uam.ia.practica01;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import mx.uam.ia.practica01.presentacion.*;
/**
 *
 * @author delta9
 */
public class Aplicacion {

    private VentanaDificultad ventana;
    private VentanaNormal ventanaNormal;
    private VentanaIntermedio ventanaIntermedio;
    private VentanaExtendido ventanaExtendido;
    private Color[] colors;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.inicia();//En la ventana principal se elige la dificultad
    }

    public Aplicacion() {
        this.colors = new Color[6];  
    }
    public void inicia(){
        initColor();
        abrirMenu();
    }
    /*Carga la ventana principal*/
    public void abrirMenu(){
        ventana = new VentanaDificultad(this);
        ventana.setVisible(true);
        
    }
    /*Muestra la ventana principal y limpia la dificultad elegida*/
    public void mostrarMenu(){
        ventana.setVisible(true);
        ventana.clearRadio();

    }
    /*Metodos para abrir la ventana de acuerdo a la dificultad*/
    public void abrirNormal(){
        ventanaNormal = new VentanaNormal(this);
        ventanaNormal.setVisible(true);
    }   
    public void abrirIntermedio(){
        ventanaIntermedio = new VentanaIntermedio(this);
        ventanaIntermedio.setVisible(true);
        
    }
    public void abrirExtendido(){
        ventanaExtendido = new VentanaExtendido(this);
        ventanaExtendido.setVisible(true);
        
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
    /*Genera numero aleatorio*/
    public int randomNum(int size){
    Random r = new Random();
    return r.nextInt(size);
}
    /*Rellena de manera aleatoria las celdas.*/
    public void asignarCelda(JButton[] buttons, int arraySize){
        int i,aleatorio,k;
        aleatorio = randomNum(arraySize); //Elige una posicion al azar de todas las celdas posibles
        int numCeldasPorColor; //Elige cuantas celdas de cada color se van a pintar       
        for(i=1;i<colors.length;i++){ //Por cada iteracion va rellenando cada uno de los colores
            numCeldasPorColor = randomNum((arraySize/5)-1)+1;//a cada color le toca un maximo de (num total de botones entre 5 colores)
            k=1; //Se usa para enumerar cada celda que se va pintando
            for(int j=0; j<numCeldasPorColor;j++ ){ //Colorea el numero de celdas que eligio previamente
                if(buttons[aleatorio].getBackground()==Color.WHITE){ //Si la celda es vacia la pinta
                   buttons[aleatorio].setBackground(colors[i]);
                   buttons[aleatorio].setText(String.valueOf(k));
                   k++;
                }
                do{ //Si elige la misma posicion vuelve a generar otra para no pintar sobre la misma
                    aleatorio = randomNum(arraySize);
                }while(buttons[aleatorio].getText()!="");                 
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
            if(buttons[i].getBackground()== Color.WHITE)
                continue;
//Crea nuevos botones con su propia memoria para evitar hacer referencia a los botones del arreglo            
            JButton sortbtn =new JButton("");
            sortbtn.setOpaque(true); //Para poder mostrar el color del boton
            sortbtn.setBackground(buttons[i].getBackground()); //inicializa color
            sortbtn.setText(buttons[i].getText());
            
            if(buttons[i].getBackground().getRGB()== colors[1].getRGB()){        
                temporal.addFragment(sortbtn, 0); //Amarillo
            }
            else if(buttons[i].getBackground().getRGB()== colors[2].getRGB()){
                temporal.addFragment(sortbtn, 1); //Rojo
            }
            else if(buttons[i].getBackground().getRGB()== colors[3].getRGB()){
                temporal.addFragment(sortbtn, 2); //Naranja
            }
            else if(buttons[i].getBackground().getRGB()== colors[4].getRGB()){
                temporal.addFragment(sortbtn, 3); //Gris
            }
            else if(buttons[i].getBackground().getRGB()== colors[5].getRGB()){
                temporal.addFragment(sortbtn, 4); //Azul
            }    
        }
        for(LinkedList<JButton> b: temporal.getDisco()){ //Ordena cada una de las listas de colores
            insertionSort(b);
        }

        for(LinkedList<JButton> b: temporal.getDisco()){ //Cada lista esta en orden ascendente y de un solo color
            for(JButton butt: b){ //Revisa el boton con los demas del arreglo para intercambiar posiciones si es necesario                                
                for(j=k;j<arraySize;j++){ //Recorre el grid de botones                    
/*Si el elemento ordenado es igual al elemento en la posicion K no hay que mover nada, boton K esta en su posicion correcta*/
                    if(butt.getBackground() == buttons[k].getBackground() && butt.getText().equals(buttons[k].getText()) ){
                        k++;
                        break;
                    }
/*Si el elemento ordenado es igual al boton en la posicion J y J!=k el elemento debe de ser movido*/
                    if(butt.getBackground() == buttons[j].getBackground() && butt.getText().equals(buttons[j].getText()) && j!=k ){
                    //intercambia elementos
                        tempbtn.setBackground(buttons[k].getBackground());
                        tempbtn.setText(buttons[k].getText());
                       buttons[k].setBackground(butt.getBackground());
                       buttons[k].setText(butt.getText());
                        buttons[j].setBackground(tempbtn.getBackground());
                        buttons[j].setText(tempbtn.getText());
                        k++;
                        break;                                
                    }//end if
                }//end for intercambia elemento
pausa();
            }
        }
       
    }

public void pausa(){
    
    try{
        Thread.sleep(300);
    }
    catch(InterruptedException ex){
        Thread.currentThread().interrupt();
    }
}    
     
    
    /*metodo de ordenamiento por inserción,de datos, como la lista de elementos no es grande no es necesario aplicar metodos como quicksort o método Shell*/
    public static void insertionSort(LinkedList<JButton> blist) {
    for (int i = 1; i < blist.size(); i++) {
        JButton current = blist.get(i);        
        int j = i - 1;
        while(j >= 0 && Integer.parseInt(current.getText()) < Integer.parseInt(blist.get(j).getText())) {
           
            blist.set(j+1, blist.get(j));            
            j--;
        }
        blist.set(j+1, current);
    }
}

    
}
