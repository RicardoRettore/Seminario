/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class Mantenimiento {
    
    /*
     * Declaración de atributos encapsulados con modificador private
     */
    
    private final int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String tipo;
    private String prioridad;
    private int horasTraslado;
    private Date fechaProgramado;
    private ArrayList <Material> materiales;
    private ArrayList <Cuadrilla> cuadrillas;

    /**
     * Constructor de la clase Mantenimiento.
     * @param id El ID del mantenimiento.
     * @param nombre El nombre del mantenimiento.
     * @param descripcion La descripción del mantenimiento.
     * @param tipo El tipo de mantenimiento.
     * @param prioridad La prioridad del mantenimiento.
     */
    public Mantenimiento(int id, String nombre, String descripcion, String tipo, String prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.estado = "Pendiente";
        this.horasTraslado = 0;
        this.materiales = new ArrayList<>();
        this.cuadrillas = new ArrayList<>();
    }
    
    /**
    / Setters y Getters
    **/
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getId() {
        return id;
    }
    public String getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    public int getHorasTraslado() {
        return horasTraslado;
    }
    public void setHorasTraslado(int horasTraslado){
        this.horasTraslado = horasTraslado;
    }
    public Date getFechaProgramado() {
        return fechaProgramado;
    }
    public void setFechaProgramado(Date fechaProgramado) {
        this.fechaProgramado = fechaProgramado;
    }

    /**
     * Redefinición del método toString con resumen del mantenimiento.
     * @return Un Strings.
     */
    @Override
    public String toString() {
        return "id: " + id + ", Prioridad: " + prioridad + ", Estado: " + estado + ", Nombre: " + nombre + ", Descripción: " + descripcion;
    }

    /**
     * Redefinición del método equals para comparar dos objetos de la clase Mantenimiento.
     * @return Un 1 o 0 si los Mantenimientos son iguales en su ID.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mantenimiento other = (Mantenimiento) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        return hash;
    }
    
    /**
     * Obtiene la lista de materiales del mantenimiento.
     * @return Una lista de Strings con los nombres de los materiales.
     */
    public ArrayList<String> getListaMeteriales(){
        ArrayList<String> listaManteriales = new ArrayList<>();
        for (Material material : materiales) {
            listaManteriales.add(material.toString());
        }
        return listaManteriales;
    }

    
     /* Obtiene el detalle completo del mantenimiento.
     * @return Un Strings con todos los datos del mantenimiento.
     */
    public String detallarMantenimiento(){
        String detalle;
        String horas;
        if (horasTraslado == 0){
            horas = "-";
        } else {
            horas = String.valueOf(horasTraslado);
        }
        detalle = "id: " + id + "\nPrioridad: " + prioridad + "\nEstado: " + estado + "\nNombre: " + nombre + "\nDescripción: ";
        detalle += descripcion + "\nTipo: " + tipo + "\nHoras de traslado: " + horas + "\nFecha programada: " + fechaProgramado; 
        detalle += "\n\t\tMateriales: ";
        for (String material : getListaMeteriales()){
            detalle += "\n\t\t\t\t" + material;
        }
        detalle += "\n\t\tCuadrillas Asignadas: ";
        for (Cuadrilla cuadrilla : cuadrillas){
            detalle += "\n\t\t\t\t" + cuadrilla.toString();
        }
        return detalle;
    }

    /* Obtiene la lista de Materiales que contiene el mantenimiento.
    * @return Un ArrayList<Material> con todos los materiales del mantenimiento.
    */
    public ArrayList<Material> getMaterialesMto() {
        return materiales;
    }

    /* Obtiene la lista de Cuadrillas que está asignadas al mantenimiento.
    * @return Un ArrayList<Cuadrilla> con todos los materiales del mantenimiento.
    */
    public ArrayList<Cuadrilla> getCuadrillas() {
        return cuadrillas;
    }

}