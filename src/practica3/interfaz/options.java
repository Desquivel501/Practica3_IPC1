/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.interfaz;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import practica3.Save.config;
import practica3.Save.serializar;

/**
 *
 * @author Derek
 */
public class options extends JFrame{
    JPanel itemPanel, speedPanel, timePanel, shipPanel;
    Font customFont;
    JComboBox frecCB, levelCB;
    JCheckBox power1ChB,power2ChB,power3ChB,power4ChB,power5ChB,power0ChB;
    boolean power1,power2,power3,power4,power5,power0;
    int time,frec,shipType,speed;
    config settings;
    JLabel frecLbl, timeLbl;
    JSpinner timeSp;
    JLabel shipLbl, backLbl, nextLbl;
    JButton saveBtn, backBtn;
    serializar save = new serializar();
    MediaPlayer mediaPlayer;
    Thread song;
    boolean playSong;
    
    public options() throws IOException, FileNotFoundException, ClassNotFoundException{
        playSong = true;
        playSoundBack();
        customFont();
        this.setSize(1000,720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.setUndecorated(true);
        this.setLayout(null);
        this.setVisible(true);
        this.setTitle("Opciones");
        
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageIcon("Resorces\\cursor.png").getImage(),
        new Point(0,0),"custom cursor"));
        
        BufferedImage img = ImageIO.read(new File("Resorces\\background2.jpg"));
        JLabel background = new JLabel(new ImageIcon(img.getScaledInstance(1000, 720, Image.SCALE_SMOOTH)));
        background.setBounds(0,0,1000,720);
        this.add(background);
        
        itemPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        
        itemPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE), "ITEMS", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, customFont.deriveFont(30f), Color.WHITE));
        itemPanel.setBounds(50, 30, 480, 620);
        itemPanel.setOpaque(false);
        itemPanel.setBackground(new Color(255, 255, 255, 0));
        itemPanel.setLayout(null);
        background.add(itemPanel);
        
        power0ChB = new JCheckBox("AUMENTO DE TIEMPO");
        power0ChB.setBounds(50, 80, 380, 30);
        power0ChB.setOpaque(false);
        power0ChB.setFocusable(false);
        power0ChB.setFont(customFont.deriveFont(25f));
        power0ChB.setForeground(Color.white);
        itemPanel.add(power0ChB);
        
        power1ChB = new JCheckBox("PUNTOS EXTRA");
        power1ChB.setBounds(50, 150, 380, 30);
        power1ChB.setOpaque(false);
        power1ChB.setFocusable(false);
        power1ChB.setFont(customFont.deriveFont(25f));
        power1ChB.setForeground(Color.white);
        itemPanel.add(power1ChB);
        
        power2ChB = new JCheckBox("AUMENTO DE VELOCIDAD");
        power2ChB.setBounds(50, 220, 380, 30);
        power2ChB.setOpaque(false);
        power2ChB.setFocusable(false);
        power2ChB.setFont(customFont.deriveFont(23f));
        power2ChB.setForeground(Color.white);
        itemPanel.add(power2ChB);
        
        power3ChB = new JCheckBox("DISMINUCIÓN DE TIEMPO");
        power3ChB.setBounds(50, 290, 380, 30);
        power3ChB.setOpaque(false);
        power3ChB.setFocusable(false);
        power3ChB.setFont(customFont.deriveFont(23f));
        power3ChB.setForeground(Color.white);
        itemPanel.add(power3ChB);
        
        power4ChB = new JCheckBox("PENALIZACION");
        power4ChB.setBounds(50, 360, 380, 30);
        power4ChB.setOpaque(false);
        power4ChB.setFocusable(false);
        power4ChB.setFont(customFont.deriveFont(25f));
        power4ChB.setForeground(Color.white);
        itemPanel.add(power4ChB);
        
        power5ChB = new JCheckBox("PARALISIS");
        power5ChB.setBounds(50, 430, 380, 30);
        power5ChB.setOpaque(false);
        power5ChB.setFocusable(false);
        power5ChB.setFont(customFont.deriveFont(25f));
        power5ChB.setForeground(Color.white);
        itemPanel.add(power5ChB);
        
        frecLbl = new JLabel("FRECUENCIA");
        frecLbl.setBounds(50, 530, 200, 30);
        frecLbl.setFont(customFont.deriveFont(23f));
        frecLbl.setForeground(Color.white);
        itemPanel.add(frecLbl);
        
        frecCB = new JComboBox();
        frecCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BAJA", "NORMAL" , "ALTA"}));
        frecCB.setBounds(250, 530, 160, 30);
        frecCB.setFont(customFont.deriveFont(20f));
        itemPanel.add(frecCB);
        
        
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        
        speedPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        speedPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE), "VELOCIDAD", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, customFont.deriveFont(30f), Color.WHITE));
        speedPanel.setBounds(580, 30, 375, 150);
        speedPanel.setOpaque(false);
        speedPanel.setBackground(new Color(255,255,255,0));
        speedPanel.setLayout(null);
        background.add(speedPanel);
        
        levelCB = new JComboBox();
        levelCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NORMAL" , "RAPIDA"}));
        levelCB.setBounds(50, 75, 275, 30);
        levelCB.setFont(customFont.deriveFont(20f));
        
        speedPanel.add(levelCB);
        
        
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        
        timePanel = new JPanel();
        timePanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE), "TIEMPO", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, customFont.deriveFont(30f), Color.WHITE));
        timePanel.setBounds(580, 210, 375, 150);
        timePanel.setBackground(new Color(255,255,255,0));
        timePanel.setLayout(null);
        background.add(timePanel);
        
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel();
        modeloSpinner.setMaximum(600);
        modeloSpinner.setMinimum(0);
        modeloSpinner.setStepSize(5);
        
        timeSp = new JSpinner();
        timeSp.setBounds(50, 70, 145, 40);
        timeSp.setFont(customFont.deriveFont(20f));
        timeSp.setModel(modeloSpinner);
        timePanel.add(timeSp);
        
        timeLbl = new JLabel("SEGUNDOS");
        timeLbl.setBounds(210, 70, 175, 40);
        timeLbl.setFont(customFont.deriveFont(20f));
        timeLbl.setForeground(Color.white);
        timePanel.add(timeLbl);
        
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        
        shipPanel = new JPanel(){
            protected void paintComponent(Graphics g){
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        shipPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE), "NAVE", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, customFont.deriveFont(30f), Color.WHITE));
        shipPanel.setBounds(580, 390, 375, 175);
        shipPanel.setOpaque(false);
        shipPanel.setBackground(new Color(255,255,255,0));
        shipPanel.setLayout(null);
        background.add(shipPanel);
        
        shipLbl = new JLabel();
        shipLbl.setBounds(140,50,130,100);
        shipPanel.add(shipLbl);
        
        backLbl = new JLabel(new ImageIcon("Resorces\\backward.png"));
        backLbl.setBounds(75,80,50,50);
        shipPanel.add(backLbl);
        backLbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                back();
            }
        });
        
        nextLbl = new JLabel(new ImageIcon("Resorces\\forward.png"));
        nextLbl.setBounds(255,80,50,50);
        shipPanel.add(nextLbl);
        nextLbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                next();
            }
        });
        
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------------------------------------------------------
        
        saveBtn = new JButton("GUARDAR");
        saveBtn.setBounds(580, 600, 175, 40);
        saveBtn.setFont(customFont.deriveFont(20f));
        background.add(saveBtn);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    saveBtnActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        backBtn = new JButton("SALIR");
        backBtn.setBounds(785, 600, 175, 40);
        backBtn.setFont(customFont.deriveFont(20f));
        background.add(backBtn);
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    backBtnActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                salir();
            }
        });
        
        deserializar();

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
    
    public void customShip(){
        switch(shipType){
            case 0:
                shipLbl.setIcon(new ImageIcon("Resorces\\ship0_pr.png"));
                break;
            case 1:
                shipLbl.setIcon(new ImageIcon("Resorces\\ship1_pr.png"));
                break;
            case 2:
                shipLbl.setIcon(new ImageIcon("Resorces\\ship2_pr.png"));
                break;
            default:
                shipLbl.setIcon(new ImageIcon("Resorces\\ship0_pr.png"));
                break;
        }
    }
    
    public void next(){
        if (shipType == 2) {
            shipType = 0;
        }else{
            shipType++;
        }
        customShip();
    }
    
    public void back(){
        if (shipType == 0) {
            shipType = 2;
        }else{
            shipType--;
        }
        customShip();
    }
    
    public void deserializar() throws FileNotFoundException, IOException, ClassNotFoundException{
        try{
            Gson gson = new Gson();
            settings = gson.fromJson(new FileReader("Saves\\config.json"), config.class);
            
            power0ChB.setSelected(settings.isPower0());
            power1ChB.setSelected(settings.isPower2());
            power2ChB.setSelected(settings.isPower4());
            power3ChB.setSelected(settings.isPower1());
            power4ChB.setSelected(settings.isPower3());
            power5ChB.setSelected(settings.isPower5());
            
            frecCB.setSelectedIndex(settings.getFrecuencia());
            levelCB.setSelectedIndex(settings.getVelocidad());
            timeSp.setValue(settings.getTiempo());
            shipType = settings.getshipType();
            customShip();
            
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public void serializar(){
        settings.setPower0(power0ChB.isSelected());
        settings.setPower1(power3ChB.isSelected());
        settings.setPower2(power1ChB.isSelected());
        settings.setPower3(power4ChB.isSelected());
        settings.setPower4(power2ChB.isSelected());
        settings.setPower5(power5ChB.isSelected());
        
        settings.setFrecuencia(frecCB.getSelectedIndex());
        settings.setshipType(shipType);
        
        if ((int)timeSp.getValue() > 0) {
            settings.setTiempo((int)timeSp.getValue());
        }else{
            settings.setTiempo(90);
        }
       
        settings.setVelocidad(levelCB.getSelectedIndex());
        
        save.saveConfig(settings);
    }
    
    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        playSoundSelect();
        try{
             serializar();
            JOptionPane.showMessageDialog(this,
                "Se ha guardado la configuración.",
                "Guardado",
                JOptionPane.PLAIN_MESSAGE);
        }catch(Exception e){
            e.printStackTrace();
        }
       
    } 
    
    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        playSoundSelect();
        salir();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    } 
    
    public void playSoundSelect(){
        String bip = "Resorces\\select.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
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
    
    public void salir(){
        backBtn.setEnabled(false);
//        mediaPlayer.pause();
        mediaPlayer.dispose();
        playSong = false;
        
    }
    
}
