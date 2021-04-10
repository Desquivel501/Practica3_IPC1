/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.reverseOrder;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import practica3.Save.Players;
import practica3.Save.serializar;
import practica3.sprites.pauseOverlay;

/**
 *
 * @author Derek
 */
public class leaderboard extends JFrame{
    List<Pair<String, Integer>> jugadores = new ArrayList<>();
    serializar save = new serializar();
    Font customFont;
    JLabel titulo;
//    JLabel[] ranking = new JLabel[5];
    int startY, cont;
    JLabel ranking, name, points, medal;
    JButton salirBtn;
    Players[] player = new Players[300];
    int contPlayers;
    int[][] temp = new int[300][2];
    MediaPlayer mediaPlayer;
    Thread song;
    boolean playSong;
    
    public leaderboard() throws IOException, FileNotFoundException, ClassNotFoundException{
        playSong = true;
        playSoundBack();
        deserializar();
        sort();
        customFont();
        startY = 200;
        this.setSize(1000,720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.setUndecorated(true);
        this.setLayout(null);
        this.setVisible(true);
        this.setTitle("Posiciones");
        
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageIcon("Resorces\\cursor.png").getImage(),
        new Point(0,0),"custom cursor"));
        
        BufferedImage img = ImageIO.read(new File("Resorces\\background2.jpg"));
        JLabel background = new JLabel(new ImageIcon(img.getScaledInstance(1000, 750, Image.SCALE_SMOOTH)));
        background.setBounds(0,0,1000,720);
        this.add(background);
        
        titulo = new JLabel();
        titulo.setText("TOP JUGADORES");
        titulo.setForeground(new Color(235,0,139));
        titulo.setFont(customFont.deriveFont(80f));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setBounds(50, 50, 900, 100);
        background.add(titulo);
        
        Comparator<Pair<String, Integer>> c = reverseOrder(comparing(Pair::getValue));
        Collections.sort(jugadores, c);
        
        if (contPlayers > 5 ) {
            cont = 5;
        }else{
            cont = contPlayers;
        }
        
        ranking = new JLabel();
        ranking.setBounds(50, startY, 250, 50);
        ranking.setText("Ranking");
        ranking.setForeground(new Color(235,0,139));
        ranking.setFont(customFont.deriveFont(40f));
        background.add(ranking);

        name = new JLabel();
        name.setBounds(330, startY, 300, 50);
        name.setText("NOMBRE");
        name.setForeground(new Color(235,0,139));
        name.setFont(customFont.deriveFont(40f));
        background.add(name);

        points = new JLabel();
        points.setBounds(640, startY, 320, 50);
        points.setText("PUNTUACION");
        points.setForeground(new Color(235,0,139));
        points.setFont(customFont.deriveFont(40f));
        background.add(points); 
           
        startY = startY + 60;

        for (int i = 0; i < cont; i++) {
            int index = 0;
            for (int k = 0; k < contPlayers; k++) {
                if ( temp[i][0] == this.player[k].getCodigo()) {
                    index = k;
                    break;
                }
            }
                
           ranking = new JLabel();
           ranking.setBounds(110, startY, 50, 50);
           ranking.setText(Integer.toString(i+1) + ".");
           ranking.setForeground(Color.white);
           ranking.setFont(customFont.deriveFont(40f));
           background.add(ranking);
           
           name = new JLabel();
           name.setBounds(330, startY, 300, 50);
           name.setText(player[index].getNombre());
           name.setForeground(Color.white);
           name.setFont(customFont.deriveFont(40f));
           background.add(name);
           
           points = new JLabel();
           points.setBounds(640, startY, 330, 50);
           points.setText(Integer.toString(player[index].getPunteo()));
           points.setForeground(Color.white);
           points.setFont(customFont.deriveFont(40f));
           background.add(points);
           
            if (i<3) {
                String medalName = "medal" + i;
                BufferedImage prize = ImageIO.read(new File("Resorces\\"+medalName+".png"));
                JLabel medal = new JLabel(new ImageIcon(prize.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                medal.setBounds(50,startY+10,30,30);
                background.add(medal);
            }
           
           startY = startY + 60;
        }
        
        salirBtn = new JButton();
        salirBtn.setText("REGRESAR AL MENU");
        salirBtn.setFont(customFont.deriveFont(40f));
        salirBtn.setForeground(new Color(41,180,115));
        salirBtn.setContentAreaFilled(false);
        salirBtn.setBorderPainted(false);
        salirBtn.setOpaque(false);
        salirBtn.setBounds(200, 600, 600, 50);
        salirBtn.setFocusable(false);
        background.add(salirBtn);
        salirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    salirActionBtn(evt);
                } catch (IOException ex) {
                    Logger.getLogger(pauseOverlay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                salir();
            }
        });
    }

    public void deserializar() throws FileNotFoundException, IOException, ClassNotFoundException{
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
//            System.out.println("Class not found");
//            c.printStackTrace();
        }
    }
    
    private void customFont(){
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resorces\\kenvector_future.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException e) {
//            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    private void sort(){
        for (int i = 0; i < contPlayers; i++) {
            temp[i][0] = this.player[i].getCodigo();
            temp[i][1] = this.player[i].getPunteo();
        }
        
        int aux,aux2;
        for (int i = 0; i < contPlayers - 1; i++) {
            for (int j = 0; j < contPlayers - i - 1; j++) {
                if ( temp[j + 1][1] > temp[j][1]) {
                    aux = temp[j + 1][1];
                    aux2 = temp[j + 1][0];
                    temp[j + 1][1] = temp[j][1];
                    temp[j + 1][0] = temp[j][0];
                    temp[j][1] = aux;
                    temp[j][0] = aux2;
                }
            }
        }
    }
    
    private void salirActionBtn(java.awt.event.ActionEvent evt) throws IOException {
       playSoundSelect();
       salir();
       this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }  
    
    public void playSoundBack(){
        String bip = "Resorces\\optionsMusic.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        song = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (playSong) {
                       Thread.sleep(300);
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
    
    public void playSoundSelect(){
        String bip = "Resorces\\select.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
    }
    
     public void salir(){
        this.salirBtn.setEnabled(false);
        mediaPlayer.dispose();
        playSong = false;
    }
     
    
}
