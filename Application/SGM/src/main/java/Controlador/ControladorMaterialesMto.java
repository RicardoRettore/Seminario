/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.util.ArrayList;
import Modelo.Material;
import Modelo.Mensaje;
/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class ControladorMaterialesMto {


    /*************** ATRIBUTOS *****************/
    private ArrayList<Material> materiales;
    private ArrayList<Material> stockMateriales;

    /**
     * Constructor de la clase GestionadorMaterialesMto.
     * @param materiales La lista de materiales del mantenimiento.
     */
    public ControladorMaterialesMto(ArrayList<Material> materiales) {
        this.materiales = materiales;
        stockMateriales = new ArrayList<>();
        inicializarMaterialesStock();    
    }

    /**
     * Método para agregar materiales al mantenimiento.
     * @param idMat Id del materiale a agregar.
     * @param cantidad Cantidad a agregar.
     * @return Un mensaje de éxito o error.
     */
    public Mensaje agregarMaterialMto(String idMat, String cantidad) {
        Mensaje msg = new Mensaje();
        try {
            // Validar que la cantidad se un número entero positivo
            Integer.valueOf(cantidad);
            // Buscar el material en el stok
            Material material = buscarMaterialStock(idMat);
            // Validar que el material exista en stock
            if (material != null){
                // Validar que la cantidad a agregar sea menor que la disponible en stock
                if (material.getCantidad()>=Integer.parseInt(cantidad)){
                    // Buscar el material en la lista de materiales del manteniemiento
                    Material materialMto = buscarMateiralMto(idMat);
                    // Validar si el material ya está en la lista de materiales del mantenimiento
                    if (materialMto != null){
                            // Sumar la cantidad de material al mantenimiento
                            materialMto.setCantidad(materialMto.getCantidad() + Integer.parseInt(cantidad));
                            // Actualizar el stock del material
                            material.setCantidad(material.getCantidad() - Integer.parseInt(cantidad));
                            msg.SetMensaje(true,"Material agregado exitosamente.");
                    }
                    else {
                        // Agregar el material al mantenimiento
                        materialMto = new Material(idMat, material.getDescripcion(), material.getTipo(), material.getUnidad(), Integer.parseInt(cantidad));
                        materiales.add(materialMto);
                        // Actualizar el stock del material
                        material.setCantidad(material.getCantidad() - Integer.parseInt(cantidad));
                        msg.SetMensaje(true,"Material agregado exitosamente.");
                    }
                } else {
                    // setear mensaje por falta de stock del material
                    msg.SetMensaje(false,"No hay suficiente material en stock para la cantidad ingresada.");
                }
            } else {
                // Lanzar excepción por código de material no encontrado
                throw new IllegalArgumentException("Error: El código de Material ingresado no existe.");
            }
        } catch (NumberFormatException e){
            // capturar excepción por cantidad no válida
            msg.SetMensaje(false,"Error: La cantidad ingresada debe ser un número.");
        } catch (IllegalArgumentException e){
            // capturar excepción por código de material no encontrado
            msg.SetMensaje(false, e.getMessage());
        } catch (Exception e){
            // capturar excepción por otro error
            msg.SetMensaje(false,"Error: No se pudo agregar el material.");
        }
        return msg;
    }

    /**
     * Método para eliminar un material del mantenimiento.
     * @param idMat Id del material a eliminar.
     * @param cantidad Cantidad a eliminar.
     * @return Un mensaje de éxito o error.
     */
    public Mensaje eliminarMaterialMto(String idMat, String cantidad) {
         Mensaje msg = new Mensaje();
        try {
            // Validar que la cantidad se un número entero positivo
            Integer.valueOf(cantidad);
            // Buscar el material en la lista de materiales del mantenimiento
            Material materialMto = buscarMateiralMto(idMat);
            // Validar que el material exista en la lista de materiales del mantenimiento    
            if (materialMto != null){
                // Validar que la cantidad a eliminar sea menor que la cantidad en mantenimiento    
                if (Integer.parseInt(cantidad)<materialMto.getCantidad()){
                    // Actualizar la cantidad de material en el mantenimiento
                    materialMto.setCantidad(materialMto.getCantidad() - Integer.parseInt(cantidad));
                    // Actualizar el stock del material
                    Material material = buscarMaterialStock(idMat);
                    material.setCantidad(material.getCantidad()+Integer.parseInt(cantidad));
                    msg.SetMensaje(true,"Material eliminado exitosamente.");
                } else {
                    // Eliminar el material de la lista de mantenimiento
                    materiales.remove(materialMto);
                    // Actualizar el stock del material
                    Material material = buscarMaterialStock(idMat);
                    material.setCantidad(material.getCantidad() + Integer.parseInt(cantidad));
                    msg.SetMensaje(true,"Material eliminado exitosamente.");
                   }
                }
            else {
                // Lanzar excepción por código de material no encontrado
                throw new IllegalArgumentException("Error: El código de Material ingresado no existe.");
            }
        } catch (NumberFormatException e){
            // capturar excepción por cantidad no válida
            msg.SetMensaje(false,"Error: La cantidad ingresada debe ser un número.");
        } catch (IllegalArgumentException e){
            // capturar excepción por código de material no encontrado
            msg.SetMensaje(false, e.getMessage());
        } catch (Exception e){
            // capturar excepción por otro error
            msg.SetMensaje(false,"Error: No se pudo eliminar el material.");
        }
    return msg;
    }

    /**
     * Método para listar el stock de materiales.
     * @return Un String con la lista de materiales.
     */
    public String listarStockMateriales(){
        String stock = "";
        for (Material material : stockMateriales) {
            stock += material.toString() + "\n";
        }
        return stock;
    }

    public String listarMateriales(){
        String listaMateriales = "";
        for (Material material : materiales){
            listaMateriales += material.toString() + "\n";
        }
        return listaMateriales;
    }

    /**
     * Método para buscar un material en el stock de materiales.
     * @param idMat Id del material a buscar.
     * @return El material encontrado o null si no
     */
    public Material buscarMaterialStock(String idMat){
        Material materialstock = null;
        for (Material material : stockMateriales) {
            if (material.getId().equals(idMat)) {
                materialstock = material;
                break;
            }
        }
        return materialstock;
    }

    /**
     * Método para buscar un material en el mantenimiento.
     * @param idMat Id del material a buscar.
     * @return El material encontrado o null si no
     */
    public Material buscarMateiralMto(String idMat){
        Material materialMto = null;
        for (Material material : materiales) {
            if (material.getId().equals(idMat)) {
                materialMto = material;
                break;
            }
        }
        return materialMto;
    }
    
    /**
     * Método para inicializar el stock de materiales.
     */
    private void inicializarMaterialesStock(){
        stockMateriales.add(new Material("MS08", "Poste Madera 8 Metros", "Estructura", "Unidad", 50));
        stockMateriales.add(new Material("MS11", "Poste Madera 11 Metros", "Estructura", "Unidad", 50));
        stockMateriales.add(new Material("HS10", "Columna Hormigón 10 Metros", "Estructura", "Unidad", 50));
        stockMateriales.add(new Material("HS13", "Columna Hormigón 13 Metros", "Estructura", "Unidad", 50));
        stockMateriales.add(new Material("AI13", "Aislador 13,2 kV", "Aislador", "Unidad", 50));
        stockMateriales.add(new Material("AI33", "Aislador 33 kV", "Aislador", "Unidad", 50));
        stockMateriales.add(new Material("P095", "Cable preensamblado 95/50 Al/Al", "Cable", "Metros", 50));
        stockMateriales.add(new Material("P120", "Cable preensamblado 120/50 Al/Al", "Cable", "Metros", 50));
    }   
}
