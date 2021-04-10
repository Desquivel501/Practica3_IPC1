/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.sprites;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Derek
 */
public class powerUp extends JLabel{
    private static int HEIGHT = 33;
    private static int WIDTH = 34;
    private int x;
    private int y;
    private int finalY;
    private int type;
    private boolean timeUp, timeDown, extraPoints, lessPoints, speedUp, freeze;
    private boolean reRun;
    private Thread move;
    boolean finalPosition, stop, taken, paused;

    public powerUp(boolean timeUp, boolean timeDown, boolean extraPoints, boolean lessPoints, boolean speedUp, boolean freeze) {
        this.timeUp = timeUp; //type 0
        this.timeDown = timeDown;//type 1
        this.extraPoints = extraPoints;//type 2
        this.lessPoints = lessPoints;//type 3
        this.speedUp = speedUp;//type 4
        this.freeze = freeze;//type 5
        this.reRun = true;
        finalPosition = false;
        stop = false;
        taken = false;
        paused = false;
        
        this.x = ThreadLocalRandom.current().nextInt(120, 900 + 1);
        this.y = 0;
        this.finalY = ThreadLocalRandom.current().nextInt(20, 620 + 1);
        this.setSize(WIDTH, HEIGHT);
        this.setLocation(x,y);
        
        
        this.type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
        while(reRun){
            if (type == 0 && timeUp == false) {
                this.type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            }else if (type == 1 && timeDown == false) {
                this.type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            }else if (type == 2 && extraPoints == false) {
                this.type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            }else if (type == 3 && lessPoints == false) {
                this.type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            }else if (type == 4 && speedUp == false) {
                this.type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            }else if (type == 5 && freeze == false) {
                this.type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            }else{
                this.reRun = false;
            }
        }
        
        switch(type){
            case 0:
                this.setIcon(new ImageIcon("Resorces\\timeUp.png"));
                this.setLocation(x,y);
                break;
            case 1:
                this.setIcon(new ImageIcon("Resorces\\timeDown.png"));
                this.setLocation(x,y);
                break;
            case 2:
                this.setIcon(new ImageIcon("Resorces\\extraPoints.png"));
                this.setLocation(x,y);
                break;
            case 3:
                this.setIcon(new ImageIcon("Resorces\\losePoints.png"));
                this.setLocation(x,y);
                break;
            case 4:
                this.setIcon(new ImageIcon("Resorces\\speedUp.png"));
                this.setLocation(x,y);
                break;
            case 5:
                this.setIcon(new ImageIcon("Resorces\\paralysis.png"));
                this.setLocation(x,y);
                break;
        }
        
        move = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!stop) {
                        if (!finalPosition) {
                            moveDown();
                        }else{
                            forward();
                        }
                        Thread.sleep(80);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        move.start();
    }
    
    public void moveDown(){
        if (!paused) {
            if(y<finalY){
             this.y = this.y + 10;
            this.setLocation(x,y);                   
            }else{
                this.finalPosition = true;
            }
        }
        
    }
    
    public void forward(){
        if (!paused) {
            if (x>0) {
                this.x = this.x - 10;
                this.setLocation(x,y);
            }else{
                stop = true;
                this.setVisible(false);
            }
        }
    }
    
    public boolean outOfBounds(){
        return stop;
    }
    
    public boolean hit(){
        return stop;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getType(){
        return this.type;
    }
    
    public boolean taken(){
        return this.taken;
    }
    
    public void take(){
        this.taken = true;
        this.setVisible(false);
    }
    
    public void pause(){
        paused = true;
    }
    
    public void resume(){
        paused = false;
    }
}
