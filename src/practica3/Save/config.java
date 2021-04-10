/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3.Save;

import java.io.Serializable;

/**
 *
 * @author Derek
 */
public class config implements Serializable{
    private boolean power0;
    private boolean power1;
    private boolean power2;
    private boolean power3;
    private boolean power4;
    private boolean power5;
    private int frecuencia;
    private int velocidad;
    private int tiempo;
    private int shipType;
    

    public config(boolean power0, boolean power1, boolean power2, boolean power3, boolean power4, boolean power5, int frecuencia, int velocidad, int tiempo, int shipType) {
        this.power0 = power0;
        this.power1 = power1;
        this.power2 = power2;
        this.power3 = power3;
        this.power4 = power4;
        this.power5 = power5;
        this.frecuencia = frecuencia;
        this.velocidad = velocidad;
        this.tiempo = tiempo;
        this.shipType = shipType;
    }

    public int getshipType() {
        return shipType;
    }

    public void setshipType(int shipType) {
        this.shipType = shipType;
    }
    
    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isPower0() {
        return power0;
    }

    public void setPower0(boolean power0) {
        this.power0 = power0;
    }

    public boolean isPower1() {
        return power1;
    }

    public void setPower1(boolean power1) {
        this.power1 = power1;
    }

    public boolean isPower2() {
        return power2;
    }

    public void setPower2(boolean power2) {
        this.power2 = power2;
    }

    public boolean isPower3() {
        return power3;
    }

    public void setPower3(boolean power3) {
        this.power3 = power3;
    }

    public boolean isPower4() {
        return power4;
    }

    public void setPower4(boolean power4) {
        this.power4 = power4;
    }

    public boolean isPower5() {
        return power5;
    }

    public void setPower5(boolean power5) {
        this.power5 = power5;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    
    
}
