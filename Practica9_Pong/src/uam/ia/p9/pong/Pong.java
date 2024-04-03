/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uam.ia.p9.pong;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 * @author delta9
 */
public class Pong extends JFrame {
    int x = 0;
    int y=580;
    int ballx;
    int bally = 0;
    int pts=0;
    int level=0;
    int p1score=0,p2score=0;
    JLabel points,lvl,p1,p2;
    DrawPanel drawPanel;
    boolean colision = false, direccion=false, angulo=false, colisionCPU=false;
    int radio = 10;
    int speedY = 2;
    int speedX = 2;
    int contadorClips=1, contadorimagen=1;
    int randomAngle=0;
    Timer timer;
    URL[] urls = new URL[21]; //11 archivos en total
    AudioInputStream[] audioInput = new AudioInputStream[12];
    Clip[] clips = new Clip[12];
    Image icons[] = new Image[9];
    BackgroundPanel background;
    CPUPlayer cpu;
    realPlayer jugador;
    
    public Pong(){
  //      getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));
        this.setPreferredSize(new Dimension(400,622));
        setImage();
        cpu = new CPUPlayer(this);
        jugador = new realPlayer();        
        drawPanel = new DrawPanel();
        points = new JLabel("Pts:"+pts+"\t\t",JLabel.LEFT);
        lvl = new JLabel("Lvl:"+level+"\t\t", JLabel.RIGHT);
        points.setFont(new Font("Serif", Font.BOLD, 25));
        lvl.setFont(new Font("Serif", Font.BOLD, 25));
        p1 =new JLabel("P1:"+p1score+"\t", JLabel.CENTER);
        p1.setFont(new Font("Serif", Font.BOLD, 25));
        p2 =new JLabel("P2:"+p2score, JLabel.CENTER);
        p2.setFont(new Font("Serif", Font.BOLD, 25));
        points.setForeground(Color.GRAY);
        lvl.setForeground(Color.GRAY);
        p1.setForeground(Color.GRAY);
        p2.setForeground(Color.GRAY);
        drawPanel.add(points);
        drawPanel.add(lvl);
        drawPanel.add(p1);
        drawPanel.add(p2);
        background.add(drawPanel);
//        add(drawPanel);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        ballx = this.getWidth()/2-10;
        JOptionPane.showMessageDialog(null, "Eres el jugador verde");        
        setVisible(true);
    }
    public void restart(){
        ballx = this.getWidth()/2-10;
        bally = 0;
        x=0;
        y=580;
        cpu.setCpux(340);
        cpu.setCpuy(580);
        timer.restart();
        pts=0;
        level=0;
        speedY = 2;
        speedX = 2;
        points.setText("Pts:"+pts+"\t\t");
        lvl.setText("Lvl:"+level);
        contadorClips=0;        
        clips[contadorClips].start();
        clips[contadorClips].loop(Clip.LOOP_CONTINUOUSLY);
        contadorClips++;
        background.setImage(icons[0]);
        background.setStyle(0);//escalado == 0
        contadorimagen=1; 
        colisionCPU=false;
        cpu.setActivo(false);
    }
    public void run(){
        cpu.start();
        jugador.start();

        loadMusic();
        System.out.println("Thread:" +cpu.getName() + " id:" + cpu.getId());
        System.out.println("Thread:" +jugador.getName() + " id:" + jugador.getId());        
        //Movimiento de la bola
           timer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    //La bola se mueve dando speedX pasos en X y speedY pasos en Y;
                    bally += speedY;
                    ballx += speedX; 
                    //Limites para Y
                    if (bally > 580) {
                        bally= 580; //Le quitas el pedacito que se paso
                        clips[contadorClips-1].stop();
                        clips[10].setFramePosition(0);
                        clips[10].start();
                        timer.stop();                       
                        handleQuit();                     

                    } else if (bally < 0) { //Rebota en el limite superior
                        if(angulo){
                            if(speedX<0)
                                speedX+=randomAngle;
                            else 
                                speedX-=randomAngle;
                            angulo=false;
                        }                        
                        bally = 0;
                        speedY *= -1;
                        if(colision)
                            cpu.setActivo(true);
                        colision= false; //Si rebota en alguna pared la colision ya paso.
                        colisionCPU= false;
                    }
                    //Limites para X
                    if (ballx + (radio * 2) > getWidth()) {
                        ballx = getWidth() - (radio * 2);
                        speedX *= -1;
                    } else if (ballx < 0) {
                        ballx = 0;
                        speedX *= -1;
                    }
                    repaint();
                    checkColision();
                }
            });
            timer.start();

    
    }
    protected class realPlayer extends Thread{

        public realPlayer() {
            this.setName("Player 1");
        }
        
        @Override
        public void run(){
//Movimientos de la barra de acuerdo a a tecla presionada            
            Action rightAction = new AbstractAction(){
                public void actionPerformed(ActionEvent e) {//Movimiento hacia la derecha
                    if(x<340)
                        x +=40;
                    if(x>340)
                        x=340;
                    drawPanel.repaint();
                }
            };
            Action leftAction = new AbstractAction(){
                public void actionPerformed(ActionEvent e) { //Movimiento hacia la izquierda
                    if(x>0)
                        x -=40;
                    if(x<0)
                        x=0;
                    drawPanel.repaint();
                }
            };
            Action upAction = new AbstractAction(){ //Movimiento hacia arriba
                public void actionPerformed(ActionEvent e) {
                    if(y>0){
                        if( !((y-bally < 21 && y-bally > 0) && (ballx > (x-19) && ballx < (x+60))) )//Para que no se pueda pasar por encima de la pelota
                        y -=40;
                    }
                    if(y<0)
                        y=0;
                    drawPanel.repaint();
                }
            };
            Action downAction = new AbstractAction(){
                public void actionPerformed(ActionEvent e) {
                    if(y<580)
                        y +=50;
                    if(y>580)
                        y=580;
                    drawPanel.repaint();
                }
            };         
            InputMap inputMap = drawPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = drawPanel.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
            actionMap.put("rightAction", rightAction);
            inputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
            actionMap.put("leftAction", leftAction);       
            inputMap.put(KeyStroke.getKeyStroke("UP"), "upAction");
            actionMap.put("upAction", upAction);
            inputMap.put(KeyStroke.getKeyStroke("DOWN"), "downAction");
            actionMap.put("downAction", downAction);            
        }
    }
    protected class DrawPanel extends JPanel {   
        public DrawPanel() {
            this.setSize(this.getPreferredSize());
        }
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            g.fillRoundRect(x, y, 60, 20, 10, 10);
            g.setColor(Color.WHITE);
            g.fillRoundRect(cpu.getCpux(),cpu.getCpuy(), 60, 20, 10, 10);
            g.setColor(Color.RED);
            g.fillOval(ballx, bally, 20, 20); //radio 10

        }

        public Dimension getPreferredSize() {
            return new Dimension(400, 600);
        }
    }
    
        public void checkColision(){
            //Si pega en la parte de arriba
            if( (bally > (y-22)) && (ballx > (x-19) && ballx < (x+60) ) && !colision && bally<y && !cpu.isActivo() && !colisionCPU){
                clips[11].setFramePosition(0);
                clips[11].start();
                colision=true; 
                setBall();
            }
            else if( (bally > (cpu.getCpuy()-22)) && (ballx > (cpu.getCpux()-19) && ballx < (cpu.getCpux()+60) ) && !colisionCPU && bally<cpu.getCpuy() && cpu.isActivo()){
                colisionCPU=true;
                cpu.setActivo(false);
                clips[11].setFramePosition(0);
                clips[11].start();
                setBall();
            }
           
        }
    public void setBall(){
                speedY*=-1; //Cambia la direccion en Y
                direccion= !direccion;
                if(direccion && colision) //Va alternando la direccion en X,una vez a la izq otra a la derecha    
                    speedX*=-1;
                if(!direccion && colisionCPU)
                    speedX*=-1;
                pts+=10;
                points.setText("Pts:"+pts+"\t\t");
                if(level<4)
                    this.randomAngle = randomNum(5);
                else if(level <7)
                    this.randomAngle = randomNum(10);
                else
                    this.randomAngle = randomNum(15);
                if( ballx < x+20 ){ //Si pega en la esquina izquierda de la barra simula un golpe en angulo
                    angulo=true;
                     if(speedX<0)
                        speedX-=randomAngle;
                    else 
                        speedX+=randomAngle;
                     speedX*=-1;//Cambia de nuevo la direccion para hacerlo mas aleatorio
                }
                else if( ballx > x+40 ){ //Si pega en la esquina derecha de la barra simula un golpe en angulo
                    angulo=true;
                     if(speedX<0)
                        speedX-=randomAngle;
                    else 
                        speedX+=randomAngle;
                     speedX*=-1;
                }
 
                if(pts%100==0){ //En cada nivel aumenta la velocidad
                this.changeBck();
                level++;
                lvl.setText("Lvl:"+level);
                speedY-=2;
                    if(speedX<0)
                        speedX-=1;
                    else 
                        speedX+=1;
                    if(contadorClips<=9){
                        clips[contadorClips-1].stop();
                        clips[contadorClips].loop(Clip.LOOP_CONTINUOUSLY);                        
                        contadorClips++;
                    }
                    if(level==6)
                        cpu.setFast(5);
                    if(level==7)
                        cpu.setFast(2);
                    if(level==8)
                        cpu.setFast(1);
                }        
    }    
    public int randomNum(int size){
        Random r = new Random();
        return r.nextInt(size - (size-1)) + (size-1);
//r.nextInt(max - min) + min;
    }
 
    public void setImage(){
//Imagenes
    loadImage();
    background= new BackgroundPanel(icons[0],0,400,600);
    background.setSize(new Dimension(400,600));
    background.setOpaque(false);
    getContentPane().add(background);              
    }
    
    public void loadMusic(){
       try {
         urls[0] = this.getClass().getClassLoader().getResource("UAM/ia/p9/pong/Intro.wav");
         audioInput[0]= AudioSystem.getAudioInputStream(urls[0]);
         clips[0] = AudioSystem.getClip();
         
         urls[10] = this.getClass().getClassLoader().getResource("UAM/ia/p9/pong/Loose.wav");
         audioInput[10]= AudioSystem.getAudioInputStream(urls[10]);
         clips[10] = AudioSystem.getClip(); 

         urls[11] = this.getClass().getClassLoader().getResource("UAM/ia/p9/pong/hit.wav");
         audioInput[11]= AudioSystem.getAudioInputStream(urls[11]);
         clips[11] = AudioSystem.getClip(); 
         
          for(int i=1 ;i<10; i++){
              String url = "UAM/ia/p9/pong/Part" + i + ".wav";
              urls[i] =this.getClass().getClassLoader().getResource(url);
              audioInput[i] = AudioSystem.getAudioInputStream(urls[i]);
              clips[i] = AudioSystem.getClip();
              clips[i].open(audioInput[i]);
          }
         clips[0].open(audioInput[0]);
         clips[10].open(audioInput[10]);
         clips[11].open(audioInput[11]);
         clips[0].loop(Clip.LOOP_CONTINUOUSLY); 
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
    }
    public void loadImage(){
        //Imagenes
         int j=0;
         for(int i=12 ;i<urls.length; i++,j++){
              String url = "UAM/ia/p9/pong/Level" + (j+1) + ".gif";
              urls[i] =this.getClass().getClassLoader().getResource(url);
              icons[j] = new ImageIcon(urls[i]).getImage();  
         }
    }
    public void changeBck(){
        if(contadorimagen<9){            
            background.setImage(icons[contadorimagen]);
            background.setStyle(0);//escalado == 0
            contadorimagen++;
        }
    }
    
    public void handleQuit()
    {
          if(cpu.isActivo()) //Si pierde CPU
            p1score+=pts;
          else
            p2score+=pts;

        // display the showOptionDialog
      int choice = JOptionPane.showOptionDialog(null, 
          "Quieres seguir jugando?", 
          "Salir?", 
          JOptionPane.YES_NO_OPTION, 
          JOptionPane.QUESTION_MESSAGE, 
          null, null, null);
      if (choice == JOptionPane.YES_OPTION)
      {
          if(cpu.isActivo()){ //Si pierde CPU
            p1.setText("P1:"+p1score);
          }
          else{
            p2.setText("P2:"+p2score);
          }
        restart();
      }
      // interpret the user's choice
      if (choice == JOptionPane.NO_OPTION)
      {
          if(p1score > p2score)
            JOptionPane.showMessageDialog(null, "Ganaste con " + p1score + " puntos.");
          else
            JOptionPane.showMessageDialog(null, "Perdiste.");

          System.exit(0);
      }
    }

    public int getBallx() {
        return ballx;
    }

    public int getBally() {
        return bally;
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                Pong ventana = new Pong();
                    ventana.run();
            }
        });
    }
}