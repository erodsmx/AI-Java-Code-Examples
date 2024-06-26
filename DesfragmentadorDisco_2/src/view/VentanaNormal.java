/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.Aplicacion;

/**
 *
 * @author delta9
 */
public class VentanaNormal extends javax.swing.JFrame {
    private Aplicacion control;
    private  int arraySize = 50;
    private JButton[] buttons = new JButton[arraySize];

    //Allocate the size of the array
    /**
     * Creates new form VentanaNormal
     */
    public VentanaNormal(Aplicacion App) {
        this.control = App;
        initComponents(); //componentes por default
        initButtons();// inicia array de botones
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonReiniciar = new javax.swing.JButton();
        jButtonComenzar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Normal"));
        jPanel1.setLayout(new java.awt.GridLayout(5, 10));

        jButtonReiniciar.setText("Nuevo Desfragmentado");
        jButtonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReiniciarActionPerformed(evt);
            }
        });

        jButtonComenzar.setText("Comenzar");
        jButtonComenzar.setOpaque(true);
        jButtonComenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComenzarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonComenzar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonReiniciar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReiniciar)
                    .addComponent(jButtonComenzar))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReiniciarActionPerformed
        clearButtons();
        control.asignarCelda(buttons,arraySize); //Rellena de manera aleatoria    
    }//GEN-LAST:event_jButtonReiniciarActionPerformed

    private void jButtonComenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComenzarActionPerformed
        control.desfragmentarUnidad(buttons, arraySize);

    }//GEN-LAST:event_jButtonComenzarActionPerformed

    //Crea los botones y los agrega al panel
private void initButtons(){
    int i;
    for(i=0;i<arraySize;i++){
        buttons[i] = new JButton("");
        buttons[i].setOpaque(true); //Para poder mostrar el color del boton
        buttons[i].setBackground(Color.WHITE); //inicializa todos blancos
        jPanel1.add(buttons[i]);
    }

}
    private void clearButtons(){
        int i;
        for(i=0;i<arraySize;i++){
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setText("");
        }
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonComenzar;
    private javax.swing.JButton jButtonReiniciar;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
