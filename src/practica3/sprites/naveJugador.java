/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Derek
 */
public class naveJugador extends JLabel{
    public static int width = 52;
    public static int height = 70;
    private int x;
    private double y;
    double speedMultiplier;
    boolean freeze;
    
    public naveJugador(int x, int y, int type, double speed) throws IOException{
        this.freeze = false;
        this.speedMultiplier = speed;
        this.x = x;
        this.y = y;
        this.setSize(width,height);
        this.setLocation(x,y);
        
        BufferedImage buff = ImageIO.read(new File("Resorces\\jugador"+ type +".png"));
        Image dim = buff.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(dim));
        this.setLocation(x,y);

    }
    
    public void moveUp(){
        if(!freeze){
            if(y>10){
            this.y = (this.y)-(8*speedMultiplier);
            this.setLocation(x, (int) y);
            }
        }
    }
    public void moveDown(){
        if(!freeze){
            if(y<560){
            this.y = (this.y)+(8*speedMultiplier);
            this.setLocation(x, (int) y);
            }
        }
    }
    
    public int getY(){
        return (int)this.y;
    }
    
    public int getX(){
        return this.x;
    }
    
    public void speedUp(){
        speedMultiplier = speedMultiplier + 1;
    }
    
    public void speedDown(){
        speedMultiplier = speedMultiplier - 1;
    }
    
    public void freeze(){
        this.freeze = true;
    }
    
    public void unFreeze(){
        this.freeze = false;
    }
}
