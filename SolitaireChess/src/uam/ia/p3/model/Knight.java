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
public class Knight {
    
    private String[][] tablero;
    private int[] posicion; //La posicion siempre es la del estado actual (nodoActual)


    public Knight(String[][] tab) {
        this.tablero = new String[4][4];
        this.initTab(tab);
        posicion = new int[2];
        this.posicion = getPosicion("K");

    }
    public void desplazar(NodoBFS nodoActual){
        if(tablero[posicion[0]][posicion[1]]=="K"){       
        moverArribaIzq(nodoActual.buscarPieza("K"),nodoActual);
        moverArribaDer(nodoActual.buscarPieza("K"),nodoActual);
        moverAbajoIzq(nodoActual.buscarPieza("K"),nodoActual);
        moverAbajoDer(nodoActual.buscarPieza("K"),nodoActual);
        moverIzqArr(nodoActual.buscarPieza("K"),nodoActual);
        moverIzqAba(nodoActual.buscarPieza("K"),nodoActual);
        moverDerArr(nodoActual.buscarPieza("K"),nodoActual);
        moverDerAba(nodoActual.buscarPieza("K"),nodoActual);
        }
    }
    
    public int[] getPosicion(String pieza){
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
    public void generarHijo(NodoBFS nodoActual){
        NodoBFS hijo = new NodoBFS();
        String[][] tab = new String[4][4];
        copyTab(tablero, tab);
        hijo.setDato(tab);
        hijo.setPadre(nodoActual);
        nodoActual.addHijo(hijo);
    }
    
    public void moverArribaIzq(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-2][pos[1]-1]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]-2][pos[1]-1]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-2][pos[1]-1] = "K";
                    posicion[0] = pos[0]-2;
                    posicion[1] = pos[1]-1;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
      }      
    }
        public void moverArribaDer(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-2][pos[1]+1]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]-2][pos[1]+1]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-2][pos[1]+1] = "K";
                    posicion[0] = pos[0]-2;
                    posicion[1] = pos[1]+1;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
      }      
    }

    public void moverAbajoIzq(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]+2][pos[1]-1]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]+2][pos[1]-1]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]+2][pos[1]-1] = "K";
                    posicion[0] = pos[0]+2;
                    posicion[1] = pos[1]-1;
                    System.out.println(nodoActual.toString());
                    generarHijo(nodoActual);
                    break;
                }       
            }   
        } catch(ArrayIndexOutOfBoundsException e) {            
            }  
    }
    
        public void moverAbajoDer(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]+2][pos[1]+1]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]+2][pos[1]+1]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]+2][pos[1]+1] = "K";
                    posicion[0] = pos[0]+2;
                    posicion[1] = pos[1]+1;
                    System.out.println(nodoActual.toString());
                    generarHijo(nodoActual);
                    break;
                }       
            }   
        } catch(ArrayIndexOutOfBoundsException e) {            
            }  
    }
    
    public void moverIzqArr(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-1][pos[1]-2]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]-1][pos[1]-2]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-1][pos[1]-2] = "K";
                    posicion[0] = pos[0]-1;
                    posicion[1] = pos[1]-2;
                    System.out.println(nodoActual.toString());
                    generarHijo(nodoActual);
                    break;
                }       
            }
      } catch(ArrayIndexOutOfBoundsException e) {
      }
    }
    
    public void moverIzqAba(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]+1][pos[1]-2]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]+1][pos[1]-2]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]+1][pos[1]-2] = "K";
                    posicion[0] = pos[0]+1;
                    posicion[1] = pos[1]-2;
                    System.out.println(nodoActual.toString());
                    generarHijo(nodoActual);
                    break;
                }       
            }
      } catch(ArrayIndexOutOfBoundsException e) {
      }
    }
    
    public void moverDerArr(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]-1][pos[1]+2]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]-1][pos[1]+2]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]-1][pos[1]+2] = "K";
                    posicion[0] = pos[0]-1;
                    posicion[1] = pos[1]+2;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
      }                
    }

    public void moverDerAba(int[] pos,NodoBFS nodoActual){
        try {
            initTab(nodoActual.getDato());
            for(int i=0; i<4;i++ ){
                if (tablero[pos[0]+1][pos[1]+2]!="" && i!=0){
                    System.out.println("Caballo se come a: " + tablero[pos[0]+1][pos[1]+2]);
                    tablero[pos[0]][pos[1]] = "";
                    tablero[pos[0]+1][pos[1]+2] = "K";
                    posicion[0] = pos[0]+1;
                    posicion[1] = pos[1]+2;
                    generarHijo(nodoActual);
                    break;
                }       
            }      
      } catch(ArrayIndexOutOfBoundsException e) {
      }                
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
