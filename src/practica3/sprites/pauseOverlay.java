
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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import practica3.Save.Players;
import practica3.interfaz.Game;
import practica3.interfaz.Menu;

/**
 *
 * @author Derek
 */
public class pauseOverlay extends JFrame{
    
    private final int HEIGHT = 880;
    private final int WIDTH = 1200;
    Font customFont;
    JButton  reanudarBtn, salirBtn;
    JLabel titulo;
    JPanel background;
    boolean closed;
    
    public pauseOverlay(){
        closed = false;
        customFont();
        this.setSize(WIDTH,HEIGHT);
        this.setLocation(0,0);
        this.setSize(1000,720);
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

        titulo = new JLabel();
        titulo.setText("PAUSA");
        titulo.setForeground(new Color(235,0,139));
        titulo.setFont(customFont.deriveFont(100f));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setBounds(25, 50, 750, 100);
        background.add(titulo);
        
        reanudarBtn = new JButton();
        reanudarBtn.setText("REANUDAR");
        reanudarBtn.setFont(customFont.deriveFont(40f));
        reanudarBtn.setForeground(new Color(41,180,115));
        reanudarBtn.setContentAreaFilled(false);
        reanudarBtn.setBorderPainted(false);
        reanudarBtn.setOpaque(false);
        reanudarBtn.setBounds(250, 210, 300, 50);
        background.add(reanudarBtn);
        
        reanudarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close();
            }
        });

        salirBtn = new JButton();
        salirBtn.setText("REGRESAR AL MENU");
        salirBtn.setFont(customFont.deriveFont(40f));
        salirBtn.setForeground(new Color(41,180,115));
        salirBtn.setContentAreaFilled(false);
        salirBtn.setBorderPainted(false);
        salirBtn.setOpaque(false);
        salirBtn.setBounds(100, 300, 600, 50);
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

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
                    close();
                }
            }
        });
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
        Game.exit = true;
        close();
    }
    
}
