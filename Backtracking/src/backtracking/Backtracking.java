/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backtracking;

/**
 *
 * @author delta9
 */
public class Backtracking {
    
    public int[] backtracking(int[] variables,int[][]rango_variables, int[] optimo, int profundidad){
        int min = rango_variables[profundidad][0];
        int max = rango_variables[profundidad][1];
        for(int i=min; i<= max; i++){
            variables[profundidad] = i;
            if(profundidad < (variables.length-1)){
                if(es_completable(variables)){
                    optimo = backtracking(variables, rango_variables,optimo,profundidad+1);
                }
            }
            else{
                int sol = evalua_solucion(variables);
                if(sol >= evalua_solucion(optimo) && es_completable(variables) ){
                    optimo[0]=variables[0];
                    optimo[1]=variables[1];
                }
            }
        }
        variables[0] = optimo[0];
        variables[1] = optimo[0];
        return optimo;
    }

    private boolean es_completable(int[] variables){
        int x1 = variables[0];
        int x2 = variables[1];
        int val1 = 9*x1 +6*x2;
        int val2 = 8*x1 + 6*x2;
        return (val1 <=  450 && val2<= 320);
    }
    private int evalua_solucion(int[] variables){
        int x1 = variables[0];
        int x2 = variables[1];
        int val = (30-7)*x1 + (17-8)*x2;
        return val;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] variables ={0,0};
        int[][] rango_variables ={{0,50},{0,75}};
        int[] optimo={0,0};
        Backtracking bk = new Backtracking();
        int[] sol = bk.backtracking(variables, rango_variables, optimo, 0);
        System.out.println("Mejor solucion:");
        System.out.println(sol[0] + " Pantalones");
        System.out.println(sol[1]+ " Camisetas");
        System.out.println("f(x1,x2) = " +bk.evalua_solucion(sol) );
    }
    
}
