/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */

import java.util.ArrayList;
import Modelo.Cuadrilla;
import Modelo.Mensaje;
import java.sql.SQLException;
import ConexionBD.ModeloRegistradorCuadrillas;

public class ControladorCuadrillasMto {

    /*************** ATRIBUTOS *****************/
    
    private ArrayList<Cuadrilla> cuadrillasDisponibles;
    private ArrayList<Cuadrilla> cuadrillasMto;

    /**
     * Constructor de la clase GestionadorCuadrilla.
     * @param empresa El nombre de la empresa.
     */
    public ControladorCuadrillasMto(ArrayList<Cuadrilla> cuadrillasMto) {
        cuadrillasDisponibles = new ArrayList<>();
        this.cuadrillasMto = cuadrillasMto;

    }


    /**
     * Método para agregar una cuadrilla a una lista de cuadrillas.
     * @param idCuad El id de la cuadrilla a agregar.
     * @retrun Mensaje Un mensaje de éxito o error del tipo Mensaje.
     */
    public Mensaje agregarCuadrillaMto(String idCuad){
        Mensaje msg = new Mensaje();
        try {
            // Buscar código ingresado dentro de las cuadrillas disponibles
            Cuadrilla cuad = buscarCuadrillaDisponible(idCuad);
            // Validar que la cuadrilla exista como disponible
            if (cuad != null){
                // Verificar si la cuadrilla ha sido asignada al mantenimiento
                if (!cuadrillasMto.contains(cuad)){
                    // Agregar cuadrilla
                    cuadrillasMto.add(cuad);
                    msg.SetMensaje(true,"Cuadrilla asignada exitosamente.");
                }
                else {
                    // setear mensaje de cuadrilla ya asignada
                    msg.SetMensaje(false,"Cuadrilla ya asignada.");
                }
            } else {
                // Lanzar excepción de cuadrilla no encontrada
                throw new IllegalArgumentException("Error: El código de cuadrilla no existe.");
            }
        } catch (IllegalArgumentException e){
            // Capturar error por cuadrilla no encontrada y setear mensaje
            msg.SetMensaje(false, e.getMessage());
        } catch (Exception e){
            // Capturar cualquier otra excepción
            msg.SetMensaje(false,"Error: No se pudo asignar cuadrillal.");
        }
        return msg;
    }

    /**
     * Método para buscar una cuadrilla de una lista de cuadrillas disponibles.
     * @param idCuad El id de la cuadrilla a buscar.
     * @retrun Cuadrilla Un objeto cuadrilla.
     */
    public Cuadrilla buscarCuadrillaDisponible(String idCuad){
        Cuadrilla cuad = null;
        for (Cuadrilla cuadrilla : cuadrillasDisponibles) {
            if (cuadrilla.getIdCuad().equals(idCuad)) {
                cuad = cuadrilla;
                break;
            }
        }
        return cuad;
    }

    /**
     * Método para eliminar una cuadrilla a una lista de cuadrillas.
     * @param idCuad El id de la cuadrilla a eliminar.
     * @retrun Mensaje Un mensaje de éxito o error del tipo Mensaje.
     */
    public Mensaje eliminarCuadrillaMto(String idCuad){
        Mensaje msg = new Mensaje();
        try {
            // Buscar código ingresado dentro de las cuadrillas asignadas al mantenimiento
            Cuadrilla cuad = buscarCuadrillaMto(idCuad);
            // Validar que la cuadrilla exista como asignada al mantenimiento
            if (cuad != null){
                // Eliminar cuadrilla
                cuadrillasMto.remove(cuad);
                msg.SetMensaje(true,"Cuadrilla eliminada exitosamente.");
            } else {
                // Lanzar excepción de cuadrilla no asignada
                throw new IllegalArgumentException("Error: Cuadrilla no asignada.");
            }
        } catch (IllegalArgumentException e) {
            // Capturar excepción por cuadrilla no encontrada y setear mensaje
            msg.SetMensaje(false, e.getMessage());
        } catch (Exception e) {
            // Capturar cualquier otra excepción
            msg.SetMensaje(false,"Error: No se pudo asignar cuadrilla.");
        }
        return msg;
    }

    /**
     * Método para buscar una cuadrilla dentro de ls asignadas del mantenimiento.
     * @param idCuad El id de la cuadrilla a buscar.
     * @retrun Cuadrilla Un objeto cuadrilla.
     */
    public Cuadrilla buscarCuadrillaMto(String idCuad){
        Cuadrilla cuad = null;
        for (Cuadrilla cuadrilla : cuadrillasMto) {
            if (cuadrilla.getIdCuad().equals(idCuad)) {
                cuad = cuadrilla;
                break;
            }
        }
        return cuad;
    }

    /**
     * Método para listar las cuadrillas existentes en la lista de cuadrillas disponibles
     * @retrun String Una cadena con la lista.
     */
    public String listarCuadrillasDisponibles() throws SQLException {
        String listaCuadrillas = "";
        try {
            cargarCuadrillasExistentes();
            for (Cuadrilla cuadrilla : cuadrillasDisponibles) {
                listaCuadrillas += cuadrilla.getIdCuad() + " - " + cuadrilla.getDescripcion() + "\n";
                }
            }
         finally {
                 
                 }
        return listaCuadrillas;
    }

    /**
     * Método para listar las cuadrillas asignadas al mantenimiento
     * @retrun String Una cadena con la lista.
     */
    public String listarCuadrillasMto(){
        String listaCuadrillas = "";
        for (Cuadrilla cuadrilla : cuadrillasMto) {
            listaCuadrillas += cuadrilla.getIdCuad() + " - " + cuadrilla.getDescripcion() + "\n";
        }
        return listaCuadrillas;
    }

    
    /**
     * Método para inicializar las cuadrillas disponibles
     * @retrun void
     */
    private void cargarCuadrillasExistentes() throws SQLException {
        // objeto para ooncetar a BD
        ModeloRegistradorCuadrillas bd = new ModeloRegistradorCuadrillas();
        try {
            cuadrillasDisponibles = bd.ConsultarCuadrillas();
        }
        finally {
            
        }

    }    
}
