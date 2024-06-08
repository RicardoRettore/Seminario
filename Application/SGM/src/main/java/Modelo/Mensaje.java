/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class Mensaje {
    private boolean resultado;
    private String mensaje;

    public Mensaje() {
        this.resultado = false;
        this.mensaje = "";
    }

    public void SetMensaje (boolean r, String m){
        this.resultado = r;
        this.mensaje = m;
    }
    public boolean getResultado(){
        return this.resultado;
    }
    public String getMensaje(){
        return this.mensaje;
    }
}    
