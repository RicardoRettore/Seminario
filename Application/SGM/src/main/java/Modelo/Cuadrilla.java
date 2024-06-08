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
public class Cuadrilla {
  
  /*
   * Declaración de atributos encapsulados con modificador private
   */
  private final String idCuad;
  private String descripcion;
  private String responsable;

  
  /**
   * Constructor de la clase Cuadrilla.
   * @param id El ID de la cuadrilla.
   * @param descripcion El nombre de la cuadrilla.
   * @param responsable El responsable de la cuadrilla.
   */
  public Cuadrilla(String idCuad, String descripcion, String responsable){
    this.idCuad = idCuad;
    this.descripcion = descripcion;
    this.responsable = responsable;
  }

  /**
  / Setters y Getters
  **/
  public String getIdCuad(){
    return this.idCuad;
  }
  
  public String getDescripcion(){
    return this.descripcion;
  }
  
  public String getResponsable(){
    return this.responsable;
  }

  public void setDescripcion(String descripcion){
    this.descripcion = descripcion;
  }
  public void setResponsable(String responsable){
    this.responsable = responsable;
  }

  /**
   * Redefinición del método toString con resumen de la cuadrilla.
   * @return Un Strings.
   */
  @Override
  public String toString(){
    return "Cuadrilla: " + this.idCuad + " - " + " - " + this.descripcion + " - " + this.responsable;
  }

  /**
   * Redefinición del método equals para comparar dos objetos de la clase Cuadrilla.
   * @return Un 1 o 0 si los Mantenimientos son iguales en su ID.
   */
  @Override
  public boolean equals(Object obj){
    if (obj == null) return false;
    if (obj == this) return true;
    if (obj.getClass() != this.getClass()) return false;
    Cuadrilla cuad = (Cuadrilla) obj;
    return this.idCuad.equals(cuad.getIdCuad());
  }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.idCuad);
        return hash;
    }
}
