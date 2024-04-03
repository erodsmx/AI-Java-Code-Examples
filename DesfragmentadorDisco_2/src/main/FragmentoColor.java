/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JButton;

/**
 *
 * @author delta9
 */
public class FragmentoColor {
        private LinkedList<JButton> fragAmarillo;
        private LinkedList<JButton> fragRojo;
        private LinkedList<JButton> fragNaranja;      
        private LinkedList<JButton> fragGris;        
        private LinkedList<JButton> fragAzul;
        private ArrayList <LinkedList> disco;

        //Constructor
    public FragmentoColor() {
        fragAmarillo= new LinkedList<>();
        fragRojo= new LinkedList<>();
        fragNaranja= new LinkedList<>();
        fragGris= new LinkedList<>();
        fragAzul = new LinkedList<>();
        disco = new ArrayList<>();
        initDisc();
    }

    private void initDisc(){
        disco.add(fragAmarillo);
        disco.add(fragRojo);
        disco.add(fragNaranja);
        disco.add(fragGris);
        disco.add(fragAzul);
        
    }
    public void addFragment(JButton button,int index){
        disco.get(index).add(button);
        
    }

    public ArrayList<LinkedList> getDisco() {
        return disco;
    }
     
    
}
