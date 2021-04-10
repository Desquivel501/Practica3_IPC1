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
public class Players implements Serializable{
    private int codigo;
    private String nombre;
    private int punteo;

    public Players(int codigo, String nombre, int punteo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.punteo = punteo;
    }

    public int getPunteo() {
        return punteo;
    }

    public void setPunteo(int punteo) {
        this.punteo = punteo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
