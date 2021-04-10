/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.interfaz;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


/**
 *
 * @author Derek
 */
public class Menu extends JFrame{
    JLabel titulo;
    JButton exitBtn, playBtn, optionBtn, topBton;
    private Font customFont;
    Thread song;
    MediaPlayer mediaPlayer;
    MediaPlayer back;
    boolean playSong;
    int cont = 0;
    
    public Menu() throws IOException{
        JFXPanel fxPanel = new JFXPanel();
        playSong = true;
        customFont();
        playSoundBack();
        this.setSize(1000,720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("Invasores del Espacio");
        
//        this.setUndecorated(true);
        
        BufferedImage img = ImageIO.read(new File("Resorces\\background2.jpg"));
        JLabel background = new JLabel(new ImageIcon(img.getScaledInstance(1000, 750, Image.SCALE_SMOOTH)));
        background.setBounds(0,0,1000,750);
        this.add(background);
        
        titulo = new JLabel();
        titulo.setText("<html><body> &nbsp; Invasores <br> del Espacio </body></html>");
        titulo.setBounds(60, 20, 900, 300);
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setFont(customFont.deriveFont(80f));
        titulo.setForeground(new Color(235,0,139));
        background.add(titulo);
        
        playBtn = new JButton();
        playBtn.setText("Jugar");
        playBtn.setBounds(400,360,200,50);
        playBtn.setForeground(new Color(41,180,115));
        playBtn.setContentAreaFilled(false);
        playBtn.setBorderPainted(false);
        playBtn.setFont(customFont.deriveFont(40f));
        background.add(playBtn);
        playBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    playBtnActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AWTException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        optionBtn = new JButton();
        optionBtn.setText("Configuración");
        optionBtn.setBounds(290,435,430,50);
        optionBtn.setForeground(new Color(41,180,115));
        optionBtn.setContentAreaFilled(false);
        optionBtn.setBorderPainted(false);
        optionBtn.setFont(customFont.deriveFont(40f));
        background.add(optionBtn);
        optionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configBtnActionPerformed(evt);
            }
        });
        
        topBton = new JButton();
        topBton.setText("Puntuaciones");
        topBton.setBounds(300,510,410,50);
        topBton.setForeground(new Color(41,180,115));
        topBton.setContentAreaFilled(false);
        topBton.setBorderPainted(false);
        topBton.setFont(customFont.deriveFont(40f));
        background.add(topBton);
        topBton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    topBtnActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        exitBtn = new JButton();
        exitBtn.setText("Salir");
        exitBtn.setBounds(400,585,200,50);
        exitBtn.setForeground(new Color(41,180,115));
        exitBtn.setContentAreaFilled(false);
        exitBtn.setBorderPainted(false);
        exitBtn.setFont(customFont.deriveFont(40f));
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    exitBtnActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        background.add(exitBtn); 
        
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageIcon("Resorces\\cursor.png").getImage(),
        new Point(0,0),"custom cursor"));
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                salir();
            }
        });
        
        
    }
    
    private void playBtnActionPerformed(java.awt.event.ActionEvent evt) throws IOException, AWTException, FileNotFoundException, ClassNotFoundException {
        playBtn.setEnabled(false);
        playSoundSelect();
        playSong = false;
        mediaPlayer.stop();
        mediaPlayer.dispose();
        Game newGame = new Game();
        Game.exit = false;
        Game.shipHit = false;
        newGame.setVisible(true);
        newGame.setResizable(false);
        this.setVisible(false);
        
        newGame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                enable();
            }
        });
    }  
    
    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        playSoundSelect();
        playSong = false;
        mediaPlayer.stop();
        mediaPlayer.dispose();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }  
    
    private void topBtnActionPerformed(java.awt.event.ActionEvent evt) throws IOException, FileNotFoundException, ClassNotFoundException {
//        System.out.println(cont);
        topBton.setEnabled(false);
        playSoundSelect();
        playSong = false;
        mediaPlayer.stop();
        mediaPlayer.dispose();
        leaderboard top = new leaderboard();
        top.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                enable();
            }
        });
//        top.printLeaders();
//        cont++;
    }
    
    private void configBtnActionPerformed(java.awt.event.ActionEvent evt){
        playSoundSelect();
        try {
            optionBtn.setEnabled(false);
            playSong = false;
            mediaPlayer.stop();
            mediaPlayer.dispose();
            
            options op = new options();
            
            op.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    enable();
                }
            });
            
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void playSoundSelect(){
        String bip = "Resorces\\select.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
    }
    
    public void playSoundBack(){
        String bip = "Resorces\\titleScreenMusic.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        song = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (playSong) {
                       Thread.sleep(500);
                       mediaPlayer.setVolume(0.1);
                       mediaPlayer.play();
                       Thread.sleep(100);
                    }
                } catch (Exception ex) {
//                    ex.printStackTrace();
                }
            }
        });
        song.start();
    }
    
    public void enable(){
        playBtn.setEnabled(true);
        optionBtn.setEnabled(true);
        topBton.setEnabled(true);
        this.setVisible(true);
        playSong = true;
        playSoundBack();
    }
    
    public void salir(){
        playSong = false;
        mediaPlayer.dispose();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
//    public void deserializar(){
}
