
package practica3.sprites;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class pewPew extends JLabel {
    private static int HEIGHT = 10;
    private static int WIDTH = 37;
    private int x;
    private int y;
    private boolean stop;
    boolean paused;
    private int speed;
    
    public pewPew(int x, int y, int speed){
        this.x = x;
        this.y = y;
        paused = false;
        this.speed = speed;
        
        this.setSize(WIDTH,HEIGHT);
        this.setLocation(x,y);
        this.setIcon(new ImageIcon("Resorces\\laser.png"));
    }
    
    public void moverDisparo(){
        stop = false;
        
        Thread move = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!stop) {  
                       if(x<1000){
                            move();
                            Thread.sleep(30);
                       }else{
                           stop();
                       }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        move.start();
    }
    
    public void move(){
        if (!paused) {
            this.x = this.x + (10*speed);
            this.setLocation(x,y);
        }
    }
    
    public void stop(){
        stop = true;
    }
    
    public boolean outOfBounds(){
        return stop;
    }
    
    public boolean hit(){
        return stop;
    }
    
    public ArrayList<naveAlien> getNave(ArrayList<naveAlien> nave) {
        return nave;
    }
    
    public int getPewX(){
        return x;
    }
    
    public int getPewY(){
        return y;
    }
    
    public void pause(){
        paused = true;
    }
    
    public void resume(){
        paused = false;
    }
}
