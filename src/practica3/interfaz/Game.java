/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.interfaz;

import com.google.gson.Gson;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import practica3.sprites.*;
import practica3.sprites.pewPew;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import practica3.Save.*;


/**
 *
 * @author Derek
 */
public class Game extends JFrame{
    
    private naveJugador jugador;
    private ArrayList<naveAlien> naveAzul;
    private ArrayList<naveAlien> naveVerde;
    private ArrayList<naveAlien> naveNaranja;
    
    private JLabel background;
    private boolean arriba = false;
    private boolean abajo = false;
    private boolean espacio = false;
    private ArrayList<pewPew> disparos;
    private ArrayList<powerUp> powers;
    private pewPew pew;
    private int contDisparo, contAzul, contNaranja, contVerde;
    private JPanel statusPane;
    private int aX,aY;
    private int bX,bY;
    private int cX,cY;
    int currentLaser = 0;
//    int currentPower = 0;
    private Font customFont;
    JLabel timerLabel, punteoLabel, velocidadLabel;
    int timerInt, punteo;
    double speedMultiplier;
    boolean outBlue, outGreen;
    MediaPlayer backgroundMusic;
    powerUp power;
    int powerCount;
    boolean pause, freezeBool;
//    pauseScreen pauseOverlay;
//    gameOverScreen gameOver;
//    wonScreen wonOverlay;
    Thread timer;
    boolean done;
    JButton continuarBtn,salirBtn;
    MediaPlayer win;
    JTextField nombreTxt;
    boolean gameFinished;
//     = new ArrayList<>();
    serializar save = new serializar();
    List<Pair<String, Integer>> jugadores = new ArrayList<>();
    Pair<String, Integer> jugadorActual;
    Players[] player = new Players[300];
    int contPlayers;
    pauseOverlay pauseOverlay;
    gameOverOverlay gameOver;
    winOverlay wonOverlay;
    
    public static boolean shipHit = false;
    public static boolean exit = false;
    
    boolean power1,power2,power3,power4,power5,power0;
    int time,frec,shipType,speed, initialSpeed;
    config settings;
    
    public Game() throws IOException, AWTException, FileNotFoundException, ClassNotFoundException{
        playWinSound();
        customFont();
        playBackgroundMusic();
        deserializar();
        deserializarConfig();
        gameFinished = false;
        pauseOverlay = new pauseOverlay();
        done = false;
        pause = false;
        outBlue = false;
        outGreen = false;
        contDisparo = 0;
        powerCount = 0;
        punteo = 0;
        contAzul = 0;
        contNaranja = 0;
        contVerde = 0;
        freezeBool = false;
        aX = 670;
        aY = 10;
        bX = 735;
        bY = 10;
        cX = 865;
        cY = 10;
        disparos = new ArrayList<>();
        powers = new ArrayList<>(); 
        shipHit = false;
        exit = false;
        initialSpeed = (int) speedMultiplier;
        
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageIcon("Resorces\\cursor.png").getImage(),
        new Point(0,0),"custom cursor"));
        
        this.setSize(1000,720);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.setUndecorated(true);
        this.setLayout(null);
        this.setTitle("Invasores del Espacio");

        background = new JLabel(new ImageIcon("Resorces\\background3.gif"));
        background.setBounds(0,50,1200,830);
//        background.setOpaque(true);
        this.add(background);
        
        statusPane = new JPanel();
        statusPane.setBounds(0, 0, 1000, 50);
        statusPane.setBackground(Color.red);
        statusPane.setLayout(new GridLayout(0,3));
        this.add(statusPane);
        
        punteoLabel = new JLabel("Punteo: 0");
        punteoLabel.setSize(400,50);
        punteoLabel.setFont(customFont.deriveFont(22f));
        punteoLabel.setForeground(Color.BLACK);
        punteoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusPane.add(punteoLabel);
        
        velocidadLabel = new JLabel("Velocidad: " + speedMultiplier);
        velocidadLabel.setSize(400,50); 
        velocidadLabel.setFont(customFont.deriveFont(22f));
        velocidadLabel.setForeground(Color.BLACK);
        velocidadLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusPane.add(velocidadLabel);

        timerLabel = new JLabel();
        timerLabel.setSize(400,50);
        timerLabel.setFont(customFont.deriveFont(22f));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusPane.add(timerLabel);
        
        nombreTxt = new JTextField();
        nombreTxt.setBackground(new Color(60,63,65));
        nombreTxt.setFont(customFont.deriveFont(50f));
        nombreTxt.setForeground(Color.black);
        nombreTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreTxt.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        nombreTxt.setBounds(250, 450, 700, 100);
        
        timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (timerInt>=0) {
                        setTimer();
                        if (timerInt == 0 && !pause) {
                            timerLabel.setText("Tiempo: 0" );
                            Thread.sleep(500);
                            gameLost();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.start();

        jugador = new naveJugador(20,305,shipType, speedMultiplier);
        background.add(jugador);
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) espacio = false;
                if (e.getKeyCode() == KeyEvent.VK_UP) arriba = false;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) abajo = false;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)espacio = true;
                if (e.getKeyCode() == KeyEvent.VK_UP) arriba = true;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) abajo = true;
                if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
//                    if (!pause) {
                        try {
                            pause();
                            pause = true;
                            espacio = false;
                            arriba = false;
                            abajo = false;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
            }
        });

        naveAzul = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            naveAzul.add(new naveAlien(aX,aY,0,speedMultiplier));
            background.add(naveAzul.get(i));
            aY = aY + 78;
        }
        
        int colVerde = 1;
        naveVerde = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            naveVerde.add(new naveAlien(bX,bY,colVerde,speedMultiplier));
            background.add(naveVerde.get(i));
            bY = bY + 78;
            if(i==7){
                bX = bX+65;
                bY = 10;
                colVerde = 2;
            }
        }
        
        int colNaranja = 3;
        naveNaranja = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            naveNaranja.add(new naveAlien(cX,cY,colNaranja,speedMultiplier));
            background.add(naveNaranja.get(i));
            cY = cY + 78;
            if(i==7){
                cX = cX+65;
                cY = 10;
                colNaranja = 4;
            }
        }

        //Main thread, controls ship movement and power ups
        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    backgroundMusic.play();
                    while (!gameFinished) { 
                        
                        if(!pause){
                            move();
                            hit();
                            getPower();
                            playerHit();
                            if (naveAzul.isEmpty() && !outBlue) {
                                for (int i = 0; i < naveVerde.size(); i++) {
                                    naveVerde.get(i).setSpeed(speedMultiplier*1.5);
                                }
                                for (int i = 0; i < naveNaranja.size(); i++) {
                                    naveNaranja.get(i).setSpeed(speedMultiplier*1.5);
                                }
                                blueOut();
                            }
                            if (naveVerde.isEmpty() && !outGreen) {
                                for (int i = 0; i < naveNaranja.size(); i++) {
                                    naveNaranja.get(i).setSpeed(speedMultiplier*2);
                                }
                                greenOut();
                            }
                            if (naveAzul.isEmpty() && naveVerde.isEmpty() && naveNaranja.isEmpty()) {
                                Thread.sleep(300);
                                win.play();
                                gameWin();
                            }
                            Thread.sleep(30);
                        }else{
                            Thread.sleep(30);
                        }
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainThread.start();
        
        //Thread that controls the movement of the laser
        Thread laser = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!gameFinished) { 
                        if (!pause) {
                            if(espacio){
                                shoot();
                            }
                            Thread.sleep(100);
                        }else{
                            Thread.sleep(100);
                        }
                }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        laser.start();
        
        pauseOverlay.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!exit) {
                    resume();
                    pause = false;
                }else{
                    backgroundMusic.stop();
                    backgroundMusic.dispose();
                    close();
                }
            }
     });
        
    }
    
    public void shoot(){
        if (!freezeBool) {
            playSoundLaser();
            disparos.add(new pewPew(55, (jugador.getLocation().y + 24),initialSpeed));
            background.add(disparos.get(contDisparo));
            disparos.get(contDisparo).moverDisparo();
            contDisparo++;
        }
    }
    
    public void hit(){
        boolean shipHit = false;
        for (int i = currentLaser; i < disparos.size(); i++) {
            for (int j = 0; j < naveAzul.size(); j++) {
                if (disparos.get(i).getY() >= naveAzul.get(j).getY() && (disparos.get(i).getY()+5) <= (naveAzul.get(j).getY()+60) && (disparos.get(i).getX()+37) >= naveAzul.get(j).getX()) {

                    naveAzul.get(j).hit();

                    if (naveAzul.get(j).muerto()) {
                        naveAzul.get(j).stopWave();
                        naveAzul.remove(j);
                        addPunteo(10);
                    }
                    
                    background.remove(disparos.get(i));
                    disparos.get(i).hit();
                    currentLaser++;
                    shipHit = true;
                    playSoundHit();
                }
            }
            if (shipHit) break;
            for (int j = 0; j < naveVerde.size(); j++) {
                if (disparos.get(i).getY() >= naveVerde.get(j).getY() && (disparos.get(i).getY()+5) <= (naveVerde.get(j).getY()+60) && (disparos.get(i).getX()+37) >=  naveVerde.get(j).getX()) {

                    naveVerde.get(j).hit();

                    if (naveVerde.get(j).muerto()) {
                        naveVerde.get(j).stopWave();
                        naveVerde.remove(j);
                        addPunteo(20);
                    }

                    background.remove(disparos.get(i));
                    disparos.get(i).hit();
                    shipHit = true;
                    currentLaser++;
                    playSoundHit();
                }
            }
            if (shipHit) break;
            for (int j = 0; j < naveNaranja.size(); j++) {
                if (disparos.get(i).getY() >= naveNaranja.get(j).getY() && (disparos.get(i).getY()+5) <= (naveNaranja.get(j).getY()+60) && (disparos.get(i).getX()+37) >=  naveNaranja.get(j).getX()) {

                    naveNaranja.get(j).hit();

                    if (naveNaranja.get(j).muerto()) {
                        naveNaranja.get(j).stopWave();
                        naveNaranja.remove(j);
                        addPunteo(30);
                    }

                    background.remove(disparos.get(i));
                    disparos.get(i).hit();
                    shipHit = true;
                    currentLaser++;
                    playSoundHit();
                }
            }
            if (shipHit) break;
            if(disparos.get(i).outOfBounds()){
                background.remove(disparos.get(i));
            }
        }
    }
    
    public void move(){
        if (espacio && arriba) {
            jugador.moveUp();
        }else if(espacio && abajo){
            jugador.moveDown();
        }else if(arriba){
            jugador.moveUp();
        }else if(abajo){
            jugador.moveDown();
        }else if(espacio){
        }
    }
    
    private void customFont(){
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resorces\\kenvector_future.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    private void addPunteo(int extra){
        punteo = punteo + extra;
        punteoLabel.setText("Punteo: " + punteo);
    }
    
    public void blueOut(){
        outBlue = true;
        speedMultiplier = speedMultiplier + 0.5;
        velocidadLabel.setText("Velocidad: " + speedMultiplier);
    }
     
    public void greenOut(){
        outGreen = true;
        speedMultiplier = speedMultiplier + 0.5;
        velocidadLabel.setText("Velocidad: " + speedMultiplier);
    }
    
    public void playSoundLaser(){
        try{
            String bip = "Resorces\\laserSound.wav";
            Media hit = new Media(new File(bip).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        }catch(ConcurrentModificationException ce){
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void playBackgroundMusic(){
        String back = "Resorces\\backgroundMusic.mp3";
        Media hit1 = new Media(new File(back).toURI().toString());
        backgroundMusic = new MediaPlayer(hit1);
        backgroundMusic.setVolume(0.1);
    }
    
    public void getPower(){
        boolean powerTaken = false;
        for (int i = 0; i < powers.size(); i++) {
                if(!powers.get(i).taken() && !powers.get(i).outOfBounds()){
                    if (powers.get(i).getY() >= jugador.getY() && powers.get(i).getY()+5 <= jugador.getY()+70 && powers.get(i).getX() <= 52 && powers.get(i).getX()+34 >= 20) {
                    powerEffect(powers.get(i).getType());
                    powers.get(i).take();
                    background.remove(powers.get(i));
                    }
                }
                if(powers.get(i).outOfBounds()){
                background.remove(powers.get(i));
            }
        }
    }
    
    public void powerEffect(int type){
        switch(type){
            case 0:
                timerInt = timerInt+10;
                playSoundGoodEffect();
                break;
            case 1:
                 timerInt = timerInt-10;
                  playSoundBadEffect();
                break;
            case 2:
                 addPunteo(10);
                 playSoundGoodEffect();
                break;
            case 3:
                  addPunteo(-10);
                  playSoundBadEffect();
                break;
            case 4:
                playSoundGoodEffect();
                Thread speedUp = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jugador.speedUp();
                            Thread.sleep(10000);
                            jugador.speedDown();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                speedUp.start();
                break;
            case 5:
                playSoundBadEffect();
                Thread freeze = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jugador.freeze();
                            freezeBool = true;
                            Thread.sleep(5000);
                            jugador.unFreeze();
                            freezeBool = false;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                freeze.start();
                
                break;
        }
    }

    public void gameLost(){
        playLoseSound();
        pause = true;
        backgroundMusic.stop();
        backgroundMusic.dispose();
        for (int i = 0; i < naveAzul.size(); i++) {
            naveAzul.get(i).pause();
        }
        for (int i = 0; i < naveVerde.size(); i++) {
            naveVerde.get(i).pause();
        }
        for (int i = 0; i < naveNaranja.size(); i++) {
            naveNaranja.get(i).pause();
        }
        for (int i = 0; i < disparos.size(); i++) {
            disparos.get(i).pause();
        }
        for (int i = 0; i < powers.size(); i++) {
            powers.get(i).pause();
        }
        gameOver = new gameOverOverlay(punteo);
        gameOver.setVisible(true);
        gameOver.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                close();
            }
        });
        
        this.setEnabled(false);
    }
    
    public void gameWin(){
        pause = true;
        backgroundMusic.stop();
        backgroundMusic.dispose();
        wonOverlay = new winOverlay(punteo);
        
        for (int i = 0; i < disparos.size(); i++) {
            disparos.get(i).pause();
            disparos.get(i).setVisible(false);
        }
        for (int i = 0; i < powers.size(); i++) {
            powers.get(i).pause();
            powers.get(i).setVisible(false);
        }

        wonOverlay.setVisible(true);
        wonOverlay.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                 close();
            }
        });
        this.setEnabled(false);
    }
    
    public void pause() throws InterruptedException{
        for (int i = 0; i < naveAzul.size(); i++) {
            naveAzul.get(i).pause();
//            naveAzul.get(i).setVisible(false);
        }
        for (int i = 0; i < naveVerde.size(); i++) {
            naveVerde.get(i).pause();
//            naveVerde.get(i).setVisible(false);
        }
        for (int i = 0; i < naveNaranja.size(); i++) {
            naveNaranja.get(i).pause();
//            naveNaranja.get(i).setVisible(false);
        }
        for (int i = 0; i < disparos.size(); i++) {
            disparos.get(i).pause();
//            disparos.get(i).setVisible(false);
        }
        for (int i = 0; i < powers.size(); i++) {
            powers.get(i).pause();
//            powers.get(i).setVisible(false);
        }
        
        pauseOverlay.setVisible(true);
    }
    
    public void resume(){
        for (int i = 0; i < naveAzul.size(); i++) {
            naveAzul.get(i).resume();
            naveAzul.get(i).setVisible(true);
        }
        for (int i = 0; i < naveVerde.size(); i++) {
            naveVerde.get(i).resume();
            naveVerde.get(i).setVisible(true);
        }
        for (int i = 0; i < naveNaranja.size(); i++) {
            naveNaranja.get(i).resume();
            naveNaranja.get(i).setVisible(true);
        }
        for (int i = 0; i < disparos.size(); i++) {
            disparos.get(i).resume();
            disparos.get(i).setVisible(true);
        }
        for (int i = 0; i < powers.size(); i++) {
            powers.get(i).resume();
            powers.get(i).setVisible(true);
        }
        
        jugador.setVisible(true);
        pauseOverlay.setVisible(false);
        
        background.remove(pauseOverlay);
        backgroundMusic.play();
    }
    
    public boolean getPaused(){
        return pause;
    }
    
    public void setTimer() throws InterruptedException{
        if(!pause){
            timerLabel.setText("Tiempo: " + timerInt);
            if (timerInt % frec == 0 && timerInt != settings.getTiempo()) {
                powers.add(new powerUp(power0,power1,power2,power3,power4,power5));
                background.add(powers.get(powerCount));
                powerCount++;
            }
            Thread.sleep(1000);
            timerInt--;
        }else{
            Thread.sleep(1000);
        }
    }
    
    public void playSoundGoodEffect(){
        String bip = "Resorces\\goodEffect.wav";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
    }
    
    public void playSoundBadEffect(){
        String bip = "Resorces\\badEffect.wav";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
    }
    
    public void playWinSound(){
        String bip = "Resorces\\win.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        win = new MediaPlayer(hit);
        win.setVolume(0.3);
        
    }
    
    public void playLoseSound(){
        String bip = "Resorces\\lose.wav";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
    }
    
    public void playSoundHit(){
        try{
            String bip = "Resorces\\hit.mp3";
            Media hit = new Media(new File(bip).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(0.1);
            mediaPlayer.play();
        }catch(ConcurrentModificationException ce){
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void deserializar(){
        try
        {
            ObjectInputStream profLoad = new ObjectInputStream(new FileInputStream("Saves\\leaderboard"));
            this.player = (Players[]) profLoad.readObject();
            for (Players pl : this.player) {
                if (pl == null) {break;}
                contPlayers++;
            }
        } 
        catch (IOException ioe) 
        {
//            ioe.printStackTrace();
        } 
        catch (ClassNotFoundException c) 
        {
//            c.printStackTrace();
        }
    }
    
    public void playerHit(){
        if (shipHit) {
            gameLost();
        }
    }
    
    public void close(){
        gameFinished = true;
        naveNaranja.clear();
        naveAzul.clear();
        naveVerde.clear();
        shipHit = false;
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    public void deserializarConfig() throws FileNotFoundException, IOException, ClassNotFoundException{
        try{
            Gson gson = new Gson();
            settings = gson.fromJson(new FileReader("Saves\\config.json"), config.class);
            
            int frecAux = 0;
            int speedAux = 0;
            
            power0 = settings.isPower0();
            power1 = settings.isPower1();
            power2 = settings.isPower2();
            power3 = settings.isPower3();
            power4 = settings.isPower4();
            power5 = settings.isPower5();
            frecAux = settings.getFrecuencia();
            speedAux = settings.getVelocidad();
            timerInt = settings.getTiempo();
            shipType = settings.getshipType();
            
            switch (frecAux) {
                case 0:
                    frec = 15;
                    break;
                case 1:
                    frec = 10;
                    break;
                case 2:
                    frec = 5;
                    break;
                default:
                    frec = 10;
                    break;
            }
            
            switch (speedAux) {
                case 0:
                    speedMultiplier = 1;
                    break;
                case 1:
                    speedMultiplier = 2;
                    break;
                default:
                    speedMultiplier = 1;
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
