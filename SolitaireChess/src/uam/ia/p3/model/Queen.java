/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p3.model;

/**
 *
 * @author delta9
 */
public class Queen {
    private String[][] tablero;
    private int[] posicion; //La posicion siempre es la del estado actual (nodoActual)

    public Queen(String[][] tab) {
        this.tablero = new String[4][4];
        this.initTab(tab);
        posicion = new int[2];
        this.posicion = getPosicion("Q");

        
    }
    
    public void desplazar(NodoBFS nodoActual){       
        if(tablero[posicion[0]][posicion[1]]=="Q"){
        moverArriba(nodoActual.buscarPieza("Q"),nodoActual);
        moverAbajo(nodoActual.buscarPieza("Q"),nodoActual);
        moverIzq(nodoActual.buscarPieza("Q"),nodoActual);
        moverDer(nodoActual.buscarPieza("Q"),nodoActual);
        }
    }
    
        public void moverArriba(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-i][pos[1]]!="" && i!=0){
                    System.out.println("Reina se come a: " + tablero[pos[0]-i][pos[1]]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-i][pos[1]] = "Q";
                    posicion[0] = pos[0]-i;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
//         System.out.println("Movimiento invalido, fuera de tablero");
      }
        
    }
    public void moverAbajo(int[] pos,NodoBFS nodoActual){
        try { 
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]+i][pos[1]]!="" && i!=0){
                    System.out.println("Reina se come a: " + nodoActual.getDato()[pos[0]+i][pos[1]]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]+i][pos[1]] = "Q";
                    posicion[0] = pos[0]+i;
                    generarHijo(nodoActual);
                    break;
                }       
            }   
        } catch(ArrayIndexOutOfBoundsException e) {            
            }
        
    }
    
    public void moverIzq(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]][pos[1]-i]!="" && i!=0){
                    System.out.println("Reina se come a: " + tablero[pos[0]][pos[1]-i]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]][pos[1]-i] = "Q";
                    posicion[1] = pos[1]-i;
                    generarHijo(nodoActual);
                    break;
                }       
            }
      } catch(ArrayIndexOutOfBoundsException e) {
      }        
    }
    
    public void moverDer(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]][pos[1]+i]!="" && i!=0){
                    System.out.println("Reina se come a: " + nodoActual.getDato()[pos[0]][pos[1]+i]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]][pos[1]+i] = "Q";
                    posicion[1] = pos[1]+i;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
      }                
    }
    
    public void moverArrDer(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-i][pos[1]+i]!="" && i!=0){
                    System.out.println("Reina se come a: " + nodoActual.getDato()[pos[0]-i][pos[1]+i]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-i][pos[1]+i] = "Q";
                    posicion[0] = pos[0]+i;
                    posicion[1] = pos[1]+i;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
      }                        
    }
    
        public void moverArrIzq(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-i][pos[1]-i]!="" && i!=0){
                    System.out.println("Reina se come a: " + tablero[pos[0]-i][pos[1]-i]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-i][pos[1]-i] = "Q";
                    posicion[0] = pos[0]-i;
                    posicion[1] = pos[1]-i;
                    generarHijo(nodoActual);
                    break;
                }       
            }
      } catch(ArrayIndexOutOfBoundsException e) {
      }                         
    }
    
    public void generarHijo(NodoBFS nodoActual){
        NodoBFS hijo = new NodoBFS();
        String[][] tab = new String[4][4];
        copyTab(tablero, tab);
        hijo.setDato(tab);
        hijo.setPadre(nodoActual);
        nodoActual.getHijos().addLast(hijo);
    }
        public int[] getPosicion(String pieza){ // "T" = torre,
        int[] posicion = new int[2];
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
               if(tablero[i][j] == pieza){
                   posicion[0] = i;
                   posicion[1] = j;
               }
            }
        }
        return posicion;
    }
     @Override
    public String toString() {
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                if(tablero[i][j] !="")
                System.out.print("| " + tablero[i][j]+" ");
                else
                    System.out.print("| " +"  ");
                
            }
            System.out.print("|");
            System.out.println();
        }
        
        return "";
    }
    private void initTab(String[][] tab){
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                tablero[i][j] = tab[i][j];
            }
        }   
    }    
        private void copyTab(String[][] Tablero, String[][] Tab){
        for(int i=0; i<4; i++ ){
            for(int j =0; j<4; j++){
                Tab[i][j] = Tablero[i][j];
            }
        }   
    }    
}
