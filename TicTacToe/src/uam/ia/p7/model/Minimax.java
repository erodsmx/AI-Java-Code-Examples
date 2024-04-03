
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p7.model;

public class Minimax 
{   

    public Minimax() {
    }

    private int minimax(String board[], Boolean isMaximizer) { 
        if(isTerminal(board)){ //Si es nodo terminal
            int score = getScore(board); //Obtiene su valor
            if (score == 1 || score == -1) 
                return score; //Si alguien gano
            return 0; //empate                 
        }
        if (isMaximizer){ 
            int maxEval = Integer.MIN_VALUE; 
            for(int i=0; i<9;i++){
                if("".equals(board[i])){//Si es vacia
                    board[i]= "X"; //realiza movimiento
                    maxEval = Math.max(maxEval, minimax(board, !isMaximizer)); //Le toca a minimizer               
                    board[i]= ""; //undo 
                }
            } 
            return maxEval; 
        }  
        else{ //Minimizer 
            int minEval = Integer.MAX_VALUE; 
            for(int i=0; i<9;i++){
                if("".equals(board[i])){//Si es vacia
                    board[i]= "O"; //realiza movimiento                
                    int eval = minimax(board, !isMaximizer);//Le toca a maximizer
                    minEval = Math.min(minEval,eval); 
                    board[i]= ""; //undo 
                }
            }  
            return minEval; 
        } 
    } 
/**
 * Verifica si el tablero que se analiza es terminal
 * @param tablero
 * @return true si alguien gano o es empate
 */    
    public boolean isTerminal(String[] tablero){
        int i;      
        for(i=0; i < 7; i+=3){//Renglones
            if(tablero[i].equals(tablero[i+1]) && tablero[i].equals(tablero[i+2]) && !"".equals(tablero[i]) )
                return true;           
        }
        for(i=0; i< 3; i++){ //Columnas
            if(tablero[i].equals(tablero[i+3]) && tablero[i].equals(tablero[i+6]) && !"".equals(tablero[i]))
                return true;     
            }  
            //Diagonales
            if(tablero[0].equals(tablero[4]) && tablero[0].equals(tablero[8]) && !"".equals(tablero[0]))            
                return true;
            if(tablero[6].equals(tablero[4]) && tablero[6].equals(tablero[2]) && !"".equals(tablero[6]))             
                return true;
            if(checkFull(tablero))
                return true;
        return false;
    }
/**
 * Cuando es terminal se le asigna un valor en base al ganador
 * @param tablero
 * @return 1 si gana CPU, -1 si gana humano, 0 si nadie gana
 */
    public int getScore(String[] tablero){
        int i;      
        for(i=0; i < 7; i+=3){//Renglones
            if(tablero[i].equals(tablero[i+1]) && tablero[i].equals(tablero[i+2]) && !"".equals(tablero[i]) ){
                if(tablero[i].equals("X"))
                    return 1; //Gana Maximizer
                else
                   return -1; //Gana Minimizer                
            }
        }
            for(i=0; i< 3; i++){ //Columnas
                if(tablero[i].equals(tablero[i+3]) && tablero[i].equals(tablero[i+6]) && !"".equals(tablero[i])){
                    if(tablero[i].equals("X")) 
                        return 1;
                    else
                        return -1;                    
                }
            }  
            //Diagonales
            if(tablero[0].equals(tablero[4]) && tablero[0].equals(tablero[8]) && !"".equals(tablero[0])){               
                if(tablero[0].equals("X")) 
                    return 1;
                else
                    return -1;                
            }
            if(tablero[6].equals(tablero[4]) && tablero[6].equals(tablero[2]) && !"".equals(tablero[6])){               
                if(tablero[6].equals("X")) 
                    return 1;
                else
                    return -1;              
            }

     return 0; //nadie gana
    }
/**
 * Revisa que ya no hay casillas libres
 * @param buttons
 * @return true si el tablero esta lleno
 */    
    public boolean checkFull(String[] buttons){
        for (String button : buttons) {
            if ("".equals(button)) {
                return false;
            }
        }
        return true;
    }    
/**
 * Primero realiza el movimiento posible e invoca a minimax para conocer su valor
 * si es optimo guarda la coordenada donde se hizo el movimiento
 * @param board
 * @return coordenada donde va el movimiento optimo
 */    
    public int getOptimo(String board[], int dificultad){ 
        int bestVal = Integer.MIN_VALUE; //Como es caso de max este valor debe ser muy pequeño
        int coordenada =-1;   
        for(int i=0; i<9;i++){
            if("".equals(board[i])){//Si es vacia
                board[i]= "X"; //realiza movimiento Max (X)
                int eval = minimax(board, false); //Ahora toca turno min
                if(eval > bestVal){
                    coordenada= i;
                    bestVal = eval;                     
                }               
                board[i]= ""; //undo 
                if(i==dificultad){
                    System.out.println("El valor del tablero: "+bestVal);
                    return coordenada;
                }
            }
        } 
        System.out.println("El valor del tablero: "+bestVal);  
        return coordenada; 
    }
    
    // Testing 
//public static void main(String[] args) 
//{ 
//    Minimax minmax = new Minimax();
//    minmax.inicia();
//} 
//
//public void inicia(){
//    String[] tab ={"O","X","", "O","","X", "X","","O"}; 
//    int coord = getOptimo(tab, 9);  
//    System.out.printf("posicion: %d\n\n",  
//               coord); 
//}
    
} 