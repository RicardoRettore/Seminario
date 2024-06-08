/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Objects;

/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class Material {
       
    /*
     * Declaración de atributos encapsulados con modificador private
     */
    
    private final String id;
    private String descripcion;
    private String tipo;
    private String unidad;
    private int cantidad;

    /**
     * Constructor de la clase Material.
     * @param id El ID del material.
     * @param descripcion La descripción del material.
     * @param tipo El tipo de material.
     * @param unidad La unidad de medida del material.
     * @param cantidad La cantidad del material.
     */
    public Material(String id, String descripcion, String tipo, String unidad, int cantidad) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }

    /**
     * Setters y Getters
     * @return 
     **/
    
    public String getId() {
        return id;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return tipo;
    }
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    public String getUnidad() {
        return unidad;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    /**
     * Redefinición del método toString con resumen del material.
     * @return Un Strings.
     */
    @Override
    public String toString(){
        return "ID Material: " + getId() + " - " + getDescripcion() + " - Cantidad: " + cantidad;    
    }

    /**
     * Redefinición del método equals para comparar dos objetos de la clase Material.
     * @return Un 1 o 0 si los Mantenimientos son iguales en su ID.
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Material other = (Material) obj;
        return id.equals(other.id);
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
}
