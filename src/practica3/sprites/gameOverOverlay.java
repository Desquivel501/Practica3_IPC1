/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import practica3.Save.Players;
import practica3.Save.serializar;
import practica3.interfaz.Game;

/**
 *
 * @author Derek
 */

public class gameOverOverlay extends JFrame{
    
    private final int HEIGHT = 720;
    private final int WIDTH = 1000;
    Font customFont;
    JButton  reanudarBtn, salirBtn;
    JLabel tituloLbl, nombreLbl, punteoLbl;
    JPanel background;
    boolean closed;
    int punteo;
    JTextField nombreTxt;
    Players[] player = new Players[300];
    int contPlayers;
    serializar save = new serializar();
    public gameOverOverlay(int punteo){
        
        closed = false;
        deserializar();
        customFont();
        this.punteo = punteo;
        this.setSize(WIDTH,HEIGHT);
        this.setLocation(0,0);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setUndecorated(true);
        this.setLayout(null);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
//        this.setBackground(new Color(60,63,65));

        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageIcon("Resorces\\cursor.png").getImage(),
        new Point(0,0),"custom cursor"));

        background = new JPanel();
        background.setBackground(new Color(60,63,65));
        background.setBounds(100, 150, 800, 480);
        background.setLayout(null);
        this.add(background);

        tituloLbl = new JLabel();
        tituloLbl.setText("FIN DEL JUEGO");
        tituloLbl.setForeground(new Color(235,0,139));
        tituloLbl.setFont(customFont.deriveFont(80f));
        tituloLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloLbl.setBounds(25, 50, 750, 50);
        background.add(tituloLbl);
        
        punteoLbl = new JLabel();
        punteoLbl.setText("PUNTEO: " + punteo);
        punteoLbl.setForeground(Color.WHITE);
        punteoLbl.setFont(customFont.deriveFont(40f));
        punteoLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        punteoLbl.setBounds(25, 160, 750, 50);
        background.add(punteoLbl);
        
        nombreLbl = new JLabel();
        nombreLbl.setText("INGRESE SU NOMBRE:");
        nombreLbl.setForeground(Color.WHITE);
        nombreLbl.setFont(customFont.deriveFont(40f));
        nombreLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreLbl.setBounds(25, 240, 750, 50);
        background.add(nombreLbl);
        
        nombreTxt = new JTextField();
        nombreTxt.setForeground(Color.black);
        nombreTxt.setFont(customFont.deriveFont(40f));
        nombreTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreTxt.setBounds(100, 300, 600, 50);
        nombreTxt.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        background.add(nombreTxt);

        salirBtn = new JButton();
        salirBtn.setText("REGRESAR AL MENU");
        salirBtn.setFont(customFont.deriveFont(40f));
        salirBtn.setForeground(new Color(41,180,115));
        salirBtn.setContentAreaFilled(false);
        salirBtn.setBorderPainted(false);
        salirBtn.setOpaque(false);
        salirBtn.setBounds(100, 390, 600, 50);
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

//        this.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                
//                if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
//                    close();
//                }
//            }
//        });
    }
    
    private void customFont(){
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resorces\\kenvector_future.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException e) {
//            e.printStackTrace();
        } catch(FontFormatException e) {
//            e.printStackTrace();
        }
    }
    
    public void close(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    private void salirActionBtn(java.awt.event.ActionEvent evt) throws IOException {
        if (!nombreTxt.getText().equals("")){
                player[contPlayers] = new Players(contPlayers,nombreTxt.getText().toUpperCase(), punteo);
                save.saveLeaderboard(player);
                contPlayers++;
                        
            }
        
        Game.exit = true;
        close();
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
//            System.out.println("Class not found");
//            c.printStackTrace();
        }
    }
}
