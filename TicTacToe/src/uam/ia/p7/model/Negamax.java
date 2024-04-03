
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p7.model;

import java.util.LinkedList;
import javax.swing.JButton;

public class Negamax 
{   
    private ConectaLogic control;
    public Negamax(ConectaLogic app) {
        this.control = app;
    }

    private int negamax(JButton board[][], Boolean isMaximizer) {
//        print(board);
        if(checkTerminal(board)){ //Si es nodo terminal
            int score = f_n(board); //Obtiene su valor
                return score; //Si alguien gano               
        }
        int maxEval = Integer.MIN_VALUE; 
            for(int j=0; j<board.length;j++){//Columnas
                    int row = control.getCasillaLibre(board, j); //Obtiene casilla vacia de abajo hacia arriba
                if(row!=-1){// -1 significa que en esa columna no hay casillas libres
                    if(isMaximizer == true)
                        board[row][j].setText("X"); //realiza movimiento
                    else
                        board[row][j].setText("O");
                    maxEval = Math.max(maxEval, -negamax(board, !isMaximizer)); //Le toca al otro jugador              
                    board[row][j].setText(""); //undo 
                }
            }
 
        return maxEval; 
    } 
//En la invocacion inicial poner alfa=-infinito y beta=+infinito
    public int negamaxAlfaBetaPoda(JButton board[][], Boolean isMaximizer,int alfa, int beta){
        if(checkTerminal(board)){ //Si es nodo terminal
            int score = f_n(board); //Obtiene su valor
                return score;                
        }
        int score = Integer.MIN_VALUE; 
            for(int j=0; j<board.length;j++){//Columnas
                    int row = control.getCasillaLibre(board, j); //Obtiene casilla vacia de abajo hacia arriba
                if(row!=-1){// -1 significa que en esa columna no hay casillas libres
                    if(isMaximizer == true)
                        board[row][j].setText("X"); //realiza movimiento
                    else
                        board[row][j].setText("O");
                    int cur = -negamaxAlfaBetaPoda(board, !isMaximizer, -alfa,-beta); //Le toca al otro jugador
                    if(cur> score)
                        score = cur;
                    if(score> alfa)
                        alfa=score;
                    board[row][j].setText(""); //undo
                    if(alfa>= beta)
                        return alfa; //poda
                }
            }
        return score;         
    }

//Metodo de prueba, se elige una sola casillas aleatoria, este método no cicla infinitamente como negamaxAlfaBeta y negamax
    public int negamaxAlfaBetaPodaRandom(JButton board[][], Boolean isMaximizer,int alfa, int beta){
        if(checkTerminal(board)){ //Si es nodo terminal
            int score = f_n(board); //Obtiene su valor
                return score;                
        }
        int score = Integer.MIN_VALUE;
        int col,row;
//        LinkedList<Integer[]> posiciones = new LinkedList<>();
                    do{ //Si elige una posicion ocupada vuelve a generar otra
                    col = control.randomNum(board.length); //elige columna  
                }while(!"".equals(board[0][col].getText()));
                    row = control.getCasillaLibre(board, col); //Obtiene casilla vacia de abajo hacia arriba

                    if(isMaximizer == true)
                        board[row][col].setText("X"); //realiza movimiento
                    else
                        board[row][col].setText("O");
                    int cur = -negamaxAlfaBetaPoda(board, !isMaximizer, -alfa,-beta); //Le toca al otro jugador
                    if(cur> score)
                        score = cur;
                    if(score> alfa)
                        alfa=score;
                    board[row][col].setText(""); //undo
                    if(alfa>= beta)
                        return alfa; //poda
                
            
        return score;         
    }

//Imprime en consola cada que se invoca el algoritmo    
    public void print(JButton board[][]){
        for(int i=0;i<7;i++){
            for(int j=0;j<7; j++){
                if(j==0){
                    if(board[i][j].getText().equals(""))
                        System.out.print("_ ");
                    else
                        System.out.print(board[i][j].getText()+" ");
                }
                else if(j%6 != 0){
                    if(board[i][j].getText().equals(""))
                        System.out.print("_ ");
                    else
                        System.out.print(board[i][j].getText()+" ");                    
                }
                else if(j%6 == 0){
                    if(board[i][j].getText().equals(""))
                        System.out.println("_ ");
                    else
                        System.out.println(board[i][j].getText()+" ");                    
                }
            }
        }
        System.out.println("");
    }
/**
 * Revisa si el tablero es terminal
 * @param buttons
 * @return true si alguien gana o es empate (tablero lleno)
 */
    public boolean checkTerminal(JButton[][] buttons){
        int i,j;      
        //Renglones de 4
        for(i=6; i>=0;i--){
            for(j=0; j< 4; j++){
                if(!"".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i][j+1].getText()) && buttons[i][j].getText().equals(buttons[i][j+2].getText()) && buttons[i][j].getText().equals(buttons[i][j+3].getText()) ){
                    return true;                    
                }
            }
        }
        //Columnas de 4
            for(j=0; j< buttons.length ;j++){
                for(i=0; i< 4; i++){
                    if(!"".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i+1][j].getText()) && buttons[i][j].getText().equals(buttons[i+2][j].getText()) && buttons[i][j].getText().equals(buttons[i+3][j].getText()) ){
                        return true;                        
                    }
                }
            }       
        //Diagonales con pendiente positiva
        int m,n=1,p,q=3,r=0;
                for(i=3;i<buttons.length;i++){ 
                    j=0; p=7;
                    for(m=i; m>(i-n) ;m--){ //Mitad superior
                        if(!"".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m-1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m-2][j+2].getText()) && buttons[m][j].getText().equals(buttons[m-3][j+3].getText()) ){
                            return true;
                        }
                       if(n<4){
                           p=p-1;
                           r=q+j;

                           if(!"".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p-1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p-2][r+2].getText()) && buttons[p][r].getText().equals(buttons[p-3][r+3].getText()) ){
                               return true;
                            }
                        }
                        j++;
                    }
                    n++;q--;
                }              
        //Diagonales con pendiente negativa
        n=1;q=3;
            for(i=3;i>=0;i--){ 
                j=0; p=0;
                for(m=i; m < i+n ;m++){ //Mitad superior
                    if(!"".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m+2][j+2].getText()) && buttons[m][j].getText().equals(buttons[m+3][j+3].getText()) ){
                        return true;
                    } 
                   if(n<4){  
                       r=q+j;
                       if(!"".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p+2][r+2].getText()) && buttons[p][r].getText().equals(buttons[p+3][r+3].getText()) ){
                           return true;
                       }
                    }
                    p++;
                    j++;
                }
                n++;
                q--;
            }
            if(control.checkFull(control.getTabAsString()))
                    return true;
        return false;
    }  

/**
 * Funcion de evaluacion
 * @param buttons
 * @return (lineas de 2)*4 +9*(lineas de 3) +1000*(lineas de 4)
 */    
    public int f_n(JButton[][] buttons){
        //buscar lineas horizontales
        //En cada renglon recorre y primero busca lineas de 4, despues lineas de 3 y al ultimo de 2
        int i,j,k,cont4=0, cont3=0,cont2=0, u=0,u2=0;
        boolean hay4=false, hay3up =false, hay3down=false;
        for(i=6,k=0; i>=0;i--, k=0){
            for(j=0; j< 4; j++){ //linea de 4                
                if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i][j+1].getText()) && buttons[i][j].getText().equals(buttons[i][j+2].getText()) && buttons[i][j].getText().equals(buttons[i][j+3].getText()) ){
                    cont4++;
                    k= j;
                    hay4=true;
                    break;
                }
            }
            if(!hay4){ //Si encontro una linea de 4 ya no busca de 3
                for(j=0;j<5;j++){ //lineas de 3
                    if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i][j+1].getText()) && buttons[i][j].getText().equals(buttons[i][j+2].getText()) ){
                        cont3++;
                        j+=2;                
                    }                
                }
            }
            for(j=0;j<6;j++){ //lineas de 2
                if( (hay4 && (k==1 || k==2)) ){  //Si la linea de 4 empieza en segunda o tercer casilla no hay posibilidad para linea de dos
                    continue;
                }
                if(j==0){ //Si se encuentra al inicio
                   if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i][j+1].getText()) && !buttons[i][j].getText().equals(buttons[i][j+2].getText()) ){
                       cont2++;
                    }
                }
                else if(j==5){ //Si se encuentra al final
                   if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i][j+1].getText()) && !buttons[i][j].getText().equals(buttons[i][j-1].getText()) ){
                       cont2++;
                    }              
                }
                else{ //Si esta en medio
                   if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i][j+1].getText()) && !buttons[i][j].getText().equals(buttons[i][j+2].getText()) && !buttons[i][j].getText().equals(buttons[i][j-1].getText()) ){
                       cont2++;
                    }                            
                }                   
            }
            
        }
        //Busca lineas verticales, recorre columna a columna en busca de lineas de 4, de 3 y al ultimo de 2
        hay4=false;
        for(j=0,k=-1; j<buttons.length;j++){
            for(i=0; i< 4; i++){ //linea de 4
                if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i+1][j].getText()) && buttons[i][j].getText().equals(buttons[i+2][j].getText()) && buttons[i][j].getText().equals(buttons[i+3][j].getText()) ){
                    cont4++;
                    k= i;
                    hay4=true;
                    break;
                }
            }
            if(!hay4){
                for(i=0;i<5;i++){ //lineas de 3
                    if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i+1][j].getText()) && buttons[i][j].getText().equals(buttons[i+2][j].getText()) ){
                        cont3++;
                        i+=2;                   
                    }                
                }
            }
            //Lineas de 2
            for(i=0;i<6;i++){ //lineas de 2
                if( (hay4 && (k==1 || k==2)) ){  //Si la linea de 4 empieza en segunda o tercer casilla no hay posibilidad para linea de dos
                    continue;
                }
                if(i==0){ //Si se encuentra al inicio
                   if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i+1][j].getText()) && !buttons[i][j].getText().equals(buttons[i+2][j].getText()) ){
                       cont2++;
                    }
                }
                else if(i==5){ //Si se encuentra al final
                   if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i+1][j].getText()) && !buttons[i][j].getText().equals(buttons[i-1][j].getText()) ){
                       cont2++;
                    }              
                }
                else{ //Si esta en medio
                   if("X".equals(buttons[i][j].getText()) && buttons[i][j].getText().equals(buttons[i+1][j].getText()) && !buttons[i][j].getText().equals(buttons[i+2][j].getText()) && !buttons[i][j].getText().equals(buttons[i-1][j].getText()) ){
                       cont2++;
                    }                            
                }
            } //Fin loop lineas de dos 
        } //Fin loop lineas verticales
//Busca lineas diagonales con pendiente positiva
/*Diagonales de 4*/        
        hay4=false;
        int m,n=1,p,q=3,r=0,z=0; k=0;

                for(i=3;i<buttons.length;i++){ 
                    j=0; p=7;
                    for(m=i; m>(i-n) ;m--){ //linea de 4
                        if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m-1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m-2][j+2].getText()) && buttons[m][j].getText().equals(buttons[m-3][j+3].getText()) ){
                            cont4++;
                            k=m; 
                            hay4=true;
                        }
                       if(n<4){
                           p=p-1;
                           r=q+j;

                           if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p-1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p-2][r+2].getText()) && buttons[p][r].getText().equals(buttons[p-3][r+3].getText()) ){
                               cont4++;
                               k=r; //checar este dato
                               hay4=true;                               
                            }
                        }
                        j++;
                      
                        if(hay4 && z==0){
                            z=i; //si hay una de 4 en el pivote Z ya no busca en esa diagonal en linea de 3
                            break;//Solo puede haber una linea de 4, no hay espacio para 2 lineas de 4, no es necesario seguir la iteracion
                        }
                    }
                    n++;q--;
                }
/*Diagonales de 3*/
        n=1;q=3;r=0;
                for(i=3;i<buttons.length;i++){ 
                    j=0; p=7;
                        for(m=i; m>(i-n)-1 ;m--){ //linea de 3 
                                if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m-1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m-2][j+2].getText()) ){
                                    if(!(i==z)){ //si hay una linea de 4 en la diagonal Z ya no busca las de 3 y se salta a la siguiente diagonal
                                       if( !(hay4 && i==k) ){ //Si hay una de 4 se salta
                                           cont3++; 
                                           u=m;
                                       }
                                    }
                                }                               
                           if(n<4){
                               p=p-1;
                               r=q+j;
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p-1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p-2][r+2].getText()) ){
                                    if(!(i==z)){ //si hay una linea de 4 en la diagonal Z ya no busca las de 3 y se salta a la siguiente diagonal
                                        if(!(hay4 && i==k) ){ //Si hay una de 4 se salta
                                            cont3++;
                                            u2=r;
                                        }
                                    }
                                                                      
                                }
                            }
                            j++;
                        } 
                    n++;q--;
                }                
//Comprueba las ultimas lineas de 3 que no se contemplan en el ciclo
//El ciclo solo recorre el espacio de renglon 3 columna cero hasta la renglon 6 columna 3
//Lo que no revisa son los picos superior izquierdo e inferior derecho que se validan con los siguientes ifs
if("X".equals(buttons[2][0].getText()) && buttons[2][0].getText().equals(buttons[1][1].getText()) && buttons[2][0].getText().equals(buttons[0][2].getText()) ){
    cont3++;
    hay3up=true;
}    
if("X".equals(buttons[6][4].getText()) && buttons[6][4].getText().equals(buttons[5][5].getText()) && buttons[6][4].getText().equals(buttons[4][6].getText()) ){
    cont3++;
    hay3down=true;
}
 /*Diagonales de 2*/           
        n=1;q=3;r=0;
                for(i=3;i<buttons.length;i++){
                   j=0; p=7;
                  for(m=i; m>(i-n)-2 ;m--){ //Mitad superior                                         
                        if(m==i){ //Si se encuentra al inicio
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m-1][j+1].getText()) && !buttons[m][j].getText().equals(buttons[m-2][j+2].getText()) ){
                               cont2++;
                            }
                        }
                        else if(m==1){ //Si se encuentra al final
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m-1][j+1].getText()) && !buttons[m][j].getText().equals(buttons[m+1][j-1].getText()) ){
                               cont2++;
                            }              
                        }
                        else{ //Si esta en medio
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m-1][j+1].getText()) && !buttons[m][j].getText().equals(buttons[m-2][j+2].getText()) && !buttons[m][j].getText().equals(buttons[m+1][j-1].getText()) ){
                               cont2++;
                            }                            
                        }                        
                       if(n<4){
                           p=p-1;
                           r=q+j;
                            if(p==6){ //Si se encuentra al inicio
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p-1][r+1].getText()) && !buttons[p][r].getText().equals(buttons[p-2][r+2].getText()) ){
                                   cont2++;
                                }
                            }
                            else if(r==5){ //Si se encuentra al final
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p-1][r+1].getText()) && !buttons[p][r].getText().equals(buttons[p+1][r-1].getText()) ){
                                   cont2++;
                                }              
                            }
                            else{ //Si esta en medio
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p-1][r+1].getText()) && !buttons[p][r].getText().equals(buttons[p-2][r+2].getText()) && !buttons[m][j].getText().equals(buttons[p+1][r-1].getText()) ){
                                   cont2++;
                                }                            
                            }    
                        }
                        j++;
                    } 
                        n++;q--;      
                }                 

//Ultimo caso para lineas de dos no consideradas en el ciclo
if("X".equals(buttons[1][0].getText()) && buttons[1][0].getText().equals(buttons[0][1].getText())  ){
    cont2++;
}
if("X".equals(buttons[6][5].getText()) && buttons[6][5].getText().equals(buttons[5][6].getText())  ){
    cont2++;
}
//Penultima diagonal no considerada en ciclo
if(!hay3up){ //Si hay linea de 3 ya no revisa
    if("X".equals(buttons[2][0].getText()) && buttons[2][0].getText().equals(buttons[1][1].getText())  )
        cont2++;   
    if("X".equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[0][2].getText())  )
        cont2++;   
}
if(!hay3down){ //Si hay linea de 3 ya no revisa
    if("X".equals(buttons[6][4].getText()) && buttons[6][4].getText().equals(buttons[5][5].getText())  )
        cont2++;   
    if("X".equals(buttons[5][5].getText()) && buttons[5][5].getText().equals(buttons[4][6].getText())  )
        cont2++;       
}                
                
        //Diagonales con pendiente negativa
/*Lineas de 4*/
        n=1;q=3; k=0; z=0;
            for(i=3;i>=0;i--){ 
                j=0; p=0;
                for(m=i; m < i+n ;m++){ //Mitad superior
                    if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m+2][j+2].getText()) && buttons[m][j].getText().equals(buttons[m+3][j+3].getText()) ){
                        cont4++;
                        k=m;                      
                    } 
                   if(n<4){  //la otra mitad
                       r=q+j;
                       if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p+2][r+2].getText()) && buttons[p][r].getText().equals(buttons[p+3][r+3].getText()) ){
                           cont4++;
                           k=r;                      
                       }
                    }
                    p++;
                    j++;
                }
                n++;
                q--;
            }
//Lineas de 3
        n=1;q=4; k=0; z=0;
            for(i=4;i>=0;i--){ 
                j=0; p=0;
                for(m=i; m < i+n ;m++){ //Mitad superior
                        if(j==0 && m==4){
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m+2][j+2].getText())  ){
                               cont3++;
                            }
                        }  
                        else if(j==0){ //Si se encuentra al inicio
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m+2][j+2].getText()) && !buttons[m][j].getText().equals(buttons[m+3][j+3].getText()) ){
                               cont3++;
                            }
                        }
                        else if(m==4){ //Si se encuentra al final
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && !buttons[m][j].getText().equals(buttons[m-1][j-1].getText()) ){
                               cont3++;
                            }              
                        }
                        else{ //Si esta en medio
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && buttons[m][j].getText().equals(buttons[m+2][j+2].getText()) && !buttons[m][j].getText().equals(buttons[m-1][j-1].getText()) && !buttons[m][j].getText().equals(buttons[m+3][j+3].getText()) ){
                               cont3++;
                            }                            
                        }                        
   
                   if(n<5){  
                       r=q+j;
                            if(p==0 && r==4){
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p+2][r+2].getText())  ){
                                   cont3++;
                                }
                            }  
                            else if(p==0){ //Si se encuentra al inicio
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p+2][r+2].getText()) && !buttons[m][j].getText().equals(buttons[p+3][r+3].getText()) ){
                                   cont3++;
                                }
                            }
                            else if(r==4){ //Si se encuentra al final
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && buttons[p][r].getText().equals(buttons[p+2][r+2].getText()) && !buttons[p][r].getText().equals(buttons[p-1][r-1].getText()) ){
                                   cont3++;
                                }              
                            }
                            else{ //Si esta en medio
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && buttons[p][r].getText().equals(buttons[m+2][j+2].getText()) && !buttons[m][j].getText().equals(buttons[m-1][j-1].getText()) && !buttons[m][j].getText().equals(buttons[m+3][j+3].getText()) ){
                                   cont3++;
                                }                            
                            }                                      
                    }
                    p++;
                    j++;
                }
                n++;
                q--;
            }
//Lineas de 2 
         n=1;q=5; k=0; z=0;
            for(i=5;i>=0;i--){ 
                j=0; p=0;
                for(m=i; m < i+n ;m++){ //Mitad superior
                        if(j==0 && m==5){
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText())  ){
                               cont2++;
                            }
                        }  
                        else if(j==0){ //Si se encuentra al inicio
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && !buttons[m][j].getText().equals(buttons[m+2][j+2].getText()) ){
                               cont2++;
                            }
                        }
                        else if(m==5){ //Si se encuentra al final
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && !buttons[m][j].getText().equals(buttons[m-1][j-1].getText()) ){
                               cont2++;
                            }              
                        }
                        else{ //Si esta en medio
                           if("X".equals(buttons[m][j].getText()) && buttons[m][j].getText().equals(buttons[m+1][j+1].getText()) && !buttons[m][j].getText().equals(buttons[m-1][j-1].getText()) && !buttons[m][j].getText().equals(buttons[m+2][j+2].getText()) ){
                               cont2++;
                            }                            
                        }                        
   
                   if(n<6){  
                       r=q+j;
                            if(p==0 && r==5){
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText())  ){
                                   cont2++;
                                }
                            }  
                            else if(p==0){ //Si se encuentra al inicio
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && !buttons[p][r].getText().equals(buttons[p+2][r+2].getText()) ){
                                   cont2++;
                                }
                            }
                            else if(r==5){ //Si se encuentra al final
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText())  && !buttons[p][r].getText().equals(buttons[p-1][r-1].getText()) ){
                                   cont2++;
                                }              
                            }
                            else{ //Si esta en medio
                               if("X".equals(buttons[p][r].getText()) && buttons[p][r].getText().equals(buttons[p+1][r+1].getText()) && !buttons[p][r].getText().equals(buttons[m+2][j+2].getText()) && !buttons[m][j].getText().equals(buttons[m-1][j-1].getText())){
                                   cont3++;
                                }                            
                            }                                      
                    }
                    p++;
                    j++;
                }
                n++;
                q--;
            }               
//        System.out.println("Lineas de 2: "+ cont2+ ", Lineas de 3: "+ cont3 + ", Lineas de 4: " + cont4);        
        return (cont2*4) + (cont3*9) + (cont4*1000);
    }
    
/**
 * Primero realiza el movimiento posible e invoca a negamax para conocer su valor
 * si es optimo guarda la coordenada donde se hizo el movimiento
 * @param board
 * @return coordenada donde va el movimiento optimo
 */    
    public int[] getOptimo(JButton board[][], int dificultad){ 
        int bestVal = Integer.MIN_VALUE; //Como es caso de max este valor debe ser muy pequeño
        int[] coordenada = {0,0};   

             for(int j=0; j<board.length;j++){
                    int row = control.getCasillaLibre(board, j);
                    if(row !=-1){
                        board[row][j].setText("X"); //realiza movimiento Max (X)
                        int eval = negamax(board, false); //Ahora toca turno min
                        if(eval > bestVal){
                            coordenada[0]= row;
                            coordenada[1]=j;
                            bestVal = eval;                     
                        }               
                        board[row][j].setText(""); //undo 
                        if(j==dificultad){
                            System.out.println("El valor del tablero: "+bestVal); //Recorta recorrido de acuerdo a la dificultad
                            return coordenada;
                        }
                    }
                
            }
 
        System.out.println("El valor del tablero: "+bestVal);  
        return coordenada; 
    }

    //Metodo similar para obtener optimo pero aqui se invoca alfa beta poda
    public int[] getOptimoAlfaBeta(JButton board[][], int dificultad){ 
        int bestVal = Integer.MIN_VALUE; //Como es caso de max este valor debe ser muy pequeño
        int[] coordenada = {0,0};   

             for(int j=0; j<board.length;j++){
                    int row = control.getCasillaLibre(board, j);
                    if(row !=-1){
                        board[row][j].setText("X"); //realiza movimiento Max (X)
//                        int eval = negamaxAlfaBetaPoda(board, false,Integer.MIN_VALUE, Integer.MAX_VALUE); 
                        int eval = negamaxAlfaBetaPoda(board, false,Integer.MIN_VALUE, Integer.MAX_VALUE); 
                        if(eval > bestVal){
                            coordenada[0]= row;
                            coordenada[1]=j;
                            bestVal = eval;                     
                        }               
                        board[row][j].setText(""); //undo 
                        if(j==dificultad){
                            System.out.println("El valor del tablero: "+bestVal); //Recorta recorrido de acuerdo a la dificultad
                            return coordenada;
                        }
                    }
                
            }
 
        System.out.println("El valor del tablero: "+bestVal);  
        return coordenada; 
    }

} 