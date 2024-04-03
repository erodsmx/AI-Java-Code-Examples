/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p9.pong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author delta9
 */
public class CPUPlayer extends Thread{
    private Timer timer;
    private boolean activo =false;
    private Pong app;
    private int bolax,bolay;
    private int cpux=340;
    private int cpuy=580;
    private int fast=15;    
    public CPUPlayer(Pong panel) {
        this.app = panel;
        this.setName("CPU Player");
    }
    @Override
    public void run(){
         timer = new Timer(fast, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    //La bola se mueve dando speedX pasos en X y speedY pasos en Y;
                    if(activo){
                        aplicarOperadores(app.getBallx(),app.getBally());                        
                        app.drawPanel.repaint();
                    }
                    if(app.colisionCPU){
                        retroceder();
                    }
                }
            });
         timer.start();
    }
    public void aplicarOperadores(int bolax, int bolay){
        double distPadre=0,distHijo=0;
        int[] coordenadas = {cpux,cpuy};
        distPadre = distanciaEuclideana(bolax,bolay,cpux,cpuy);
        //Movimiento a la izq
        if(!(bolax == 0 || cpux<10)){
            distHijo= distanciaEuclideana(bolax,bolay,cpux-10,cpuy);
            if (distHijo < distPadre){
                coordenadas[0]=cpux-10;
                coordenadas[1]=cpuy;
            }
                
        }         
        //Movimiento a la der
        if((cpux < 330) ){            
            distHijo= distanciaEuclideana(bolax,bolay,cpux+10,cpuy);
            if (distHijo < distPadre){
                coordenadas[0]=cpux+10;
                coordenadas[1]=cpuy;
            }
        } 
        //Movimiento arriba
        if(bolay < cpuy && cpuy> bolay+30){
            distHijo= distanciaEuclideana(bolax,bolay,cpux,cpuy-10);
            if (distHijo < distPadre && cpuy > bolay){
                coordenadas[0]=cpux;
                coordenadas[1]=cpuy-10;
            }            
        }
        //Movimiento abajo
        if(cpuy<555){
            distHijo= distanciaEuclideana(bolax,bolay,cpux,cpuy+25);
            if (distHijo < distPadre){
                coordenadas[0]=cpux;
                coordenadas[1]=cpuy+25;
            }            
        }        
        cpux= coordenadas[0];
        cpuy= coordenadas[1];
    }
    public double distanciaEuclideana(int bolax, int bolay, int cpx, int cpy){
       return Math.sqrt( Math.pow( (bolax+10) - (cpx+30),2) + Math.pow( (bolay+50) - (cpy+30) , 2) );
    }
    public boolean isActivo() {
        return activo;
    }
    public void retroceder(){
        if(cpuy<560)
            cpuy+=2*(app.level);
        app.drawPanel.repaint();
    }
    public int getCpux() {
        return cpux;
    }

    public void setCpux(int cpux) {
        this.cpux = cpux;
    }

    public int getCpuy() {
        return cpuy;
    }

    public void setCpuy(int cpuy) {
        this.cpuy = cpuy;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getFast() {
        return fast;
    }

    public void setFast(int fast) {
        this.fast = fast;
    }
    
}
