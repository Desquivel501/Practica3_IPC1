package practica3.sprites;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import practica3.interfaz.Game;


public class naveAlien extends JLabel{
    public static int width = 52;
    public static int height = 70;
    private int x;
    private int y;
    private int type;
    boolean waveBol = true;
    private int vida;
    Thread wave;
    private double speed;
    boolean paused;
    
    
    public naveAlien(int x, int y, int type, double speedMultiplier) throws IOException{
        paused = false;
        this.type = type;
        this.x = x;
        this.y = y;
        this.speed = 800/speedMultiplier;
        this.setSize(width,height);
        switch(type){
            case 0:
                BufferedImage buffAzul = ImageIO.read(new File("Resorces\\enemyBlue.png"));
                Image dimAzul = buffAzul.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(dimAzul));
                this.setLocation(x,y);
                vida = 1;
                break;
            case 1:
                BufferedImage buffVerde = ImageIO.read(new File("Resorces\\enemyGreen.png"));
                Image dimVerde = buffVerde.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(dimVerde));
                this.setLocation(x,y);
                vida = 2;
                break;
            case 2:
                BufferedImage buffVerde2 = ImageIO.read(new File("Resorces\\enemyGreen.png"));
                Image dimVerde2 = buffVerde2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(dimVerde2));
                this.setLocation(x,y);
                vida = 2;
                break;
            case 3:
                BufferedImage buffNaranja = ImageIO.read(new File("Resorces\\enemyOrange.png"));
                Image dimNaranja = buffNaranja.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(dimNaranja));
                this.setLocation(x,y);
                vida = 3;
                break;
            case 4:
                BufferedImage buffNaranja2 = ImageIO.read(new File("Resorces\\enemyOrange.png"));
                Image dimNaranja2 = buffNaranja2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(dimNaranja2));
                this.setLocation(x,y);
                vida = 3;
                break;
        }
        
        wave = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (waveBol) {
                        switch(type){
                            case 0:
                                waveDown();
                                Thread.sleep((long) speed);
                                waveUp();
                                Thread.sleep((long) speed);
                                break;
                            case 1:
                                Thread.sleep((long) speed);
                                waveDown();
                                Thread.sleep((long) speed);
                                waveUp();
                                break;
                            case 2:
                                waveDown();
                                Thread.sleep((long) speed);
                                waveUp();
                                Thread.sleep((long) speed);
                                break;
                            case 3:
                                Thread.sleep((long) speed);
                                waveDown();
                                Thread.sleep((long) speed);
                                waveUp();
                                break;
                            case 4:
                                waveDown();
                                Thread.sleep((long) speed);
                                waveUp();
                                Thread.sleep((long) speed);
                                break;
                        }
                        forward();
                        playerHit();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        wave.start();
       
    }
    public void waveDown(){
        if (!paused){
            this.setLocation(x,y-5);
        }
    }
    
    public void waveUp(){
        if (!paused){
            this.setLocation(x,y+5);
        }
    }
    
    public void forward(){
        if (!paused) {
            this.x = x-8;
            this.setLocation(x,y);
        }   
    }

    public void stopWave(){
        waveBol = false;
    }
    
    public int getShipX(){
        return x;
    }
    
    public int getShipY(){
        return y;
    }
    
    public void hit(){
        Thread explotion = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    restarVida();
                    if (vida < 0) {
                        hitIcon();
                        Thread.sleep(500);
                        delete();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        explotion.start();
    }
    
    public void hitIcon() throws IOException{
        this.setIcon(new ImageIcon("Resorces\\explosion3.gif"));
        playSoundExplosion();
    }
    
    public void delete(){
        waveBol = false;
        this.setEnabled(false);
        this.setVisible(false);
    }
    
    public void restarVida(){
        vida--;
    }
    
    public boolean muerto(){
        if (vida == 0) {
            return true;
        }else{
            return false;
        }
    }
    
    public void setSpeed(double newSpeed){
        this.speed = 800/newSpeed;
    }
    
    public void playSoundExplosion(){
        try{
            String bip = "Resorces\\explosion.wav";
            Media hit = new Media(new File(bip).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        }catch(ConcurrentModificationException ce){
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void pause(){
        this.paused = true;
    }
    
    public void resume(){
        this.paused = false;
    }
    
    public void playerHit(){
        if (x<=75) {
            Game.shipHit = true;
            delete();
        }
    }
}
