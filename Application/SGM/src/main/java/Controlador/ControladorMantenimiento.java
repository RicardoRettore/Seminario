/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Modelo.Mantenimiento;
import Modelo.Mensaje;
import Vista.MenuMaterialesMto;
import Vista.MenuCuadrillaMto;



/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class ControladorMantenimiento {
    private ArrayList<Mantenimiento> mantenimientos;
    private String empresa;
    

    public ControladorMantenimiento(String empresa) {
        this.empresa = empresa;
        this.mantenimientos = new ArrayList<>();
    }

    public String getEmpresa() {
        return this.empresa;
    }

    
    /**
     * Agrega un nuevo mantenimiento a la empresa.
     * 
     * @param cod        El código del mantenimiento.
     * @param nombre     El nombre del mantenimiento.
     * @param descripcion La descripción del mantenimiento.
     * @param tipo       El tipo de mantenimiento.
     * @param prioridad  La prioridad del mantenimiento.
     * @return Un mensaje de éxito o error.
     */
    public Mensaje agregarMantenimiento(String cod, String nombre, String descripcion, String tipo, String prioridad) {
        Mensaje msg = new Mensaje();
        try {
            // Validar que el código del mantenimiento sea un número entero positivo
            Integer.valueOf(cod);
            // Valida que la prioridad esté dentro de los valores esperados
            if (prioridad.equals("Alta")||prioridad.equals("Media")||prioridad.equals("Baja")) {
                // Valida que el manteimientos no exista previamente
                if (!mantenimientos.contains(new Mantenimiento(Integer.parseInt(cod), nombre, descripcion, tipo, prioridad))) {
                    // Agrega el mantenimiento a la lista
                    mantenimientos.add(new Mantenimiento(Integer.parseInt(cod), nombre, descripcion, tipo, prioridad));
                    msg.SetMensaje(true, "Mantenimiento agregado exitosamente.");
                    // Invoca a la interfaz para agregar materiales
                    MenuMaterialesMto menu = new MenuMaterialesMto(mantenimientos.get(mantenimientos.size()-1));
                    menu.ejecutarMenu();
                } else {
                    // Captura excepción si el mantenimiento ya existe
                    throw new IllegalArgumentException("Error: Ya existe un mantenimiento con este código.");
                }
            } else {
                // Setea mensaje de error si la prioridad no es válida
                msg.SetMensaje(false, "La prioridad debe ser 'Alta', 'Media' o 'Baja'.");
            }
        } catch (NumberFormatException e) {
            // Captura el error si el código del mantenimiento no es un número entero positivo
            msg.SetMensaje(false,"Error: El código del mantenimiento debe ser un número.");
        } catch (IllegalArgumentException e) {
            // Captura el error si el mantenimiento ya existe
            msg.SetMensaje(false, e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otro error
            msg.SetMensaje(false, "Error: No se pudo agregar el mantenimiento.");
        }
        return msg;
    }

    /**
     * Programar un mantenimiento.
     * @param cod El código del mantenimiento.
     * @param fecha La fecha de programación.
     * @param horas Las horas de traslado.
     * @return Un mensaje de éxito o error.
     */
    public Mensaje programarMantenimiento(String idMto, String fecha, String horas) {
        Mensaje msg = new Mensaje();
        try {
            // Valida que el código del mantenimiento sea un número entero positivo
            Integer.valueOf(idMto);
            // Válida que la fecha sea válida
            SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
            Date fechaProgramada = formato.parse(fecha);
            // Valida que las horas sean números enteros positivos
            Integer.valueOf(horas);
            // Buscar el mantenimiento en la lista
            Mantenimiento mto = buscarMantenimiento(Integer.parseInt(idMto));
            // Valida que el mantenimiento exista
            if (mto != null) {
                // Valida que el estado del mantenimiento sea valido para Programar
                if (mto.getEstado().equals("Pendiente")||mto.getEstado().equals("A Reprogramar")) {
                    // Actualiza fecha de programación y horas de traslado
                    mto.setFechaProgramado(fechaProgramada);
                    mto.setHorasTraslado(Integer.parseInt(horas));
                    // Invoca a la interfaz para asignar cuadrilla
                    MenuCuadrillaMto menuCuad = new MenuCuadrillaMto(mto);
                    menuCuad.ejecutarMenu();
                   // Valida que se haya agregado al menos una cuadrilla
                    if (!mto.getCuadrillas().isEmpty()){
                        // Actualiza el estado del mantenimiento a "Programado" 
                        mto.setEstado("Programado");
                        msg.SetMensaje(true, "Mantenimiento programado exitosamente.");
                    } else {
                        // Elimina actualización por no contar con cuadrilla agregada
                        mto.setFechaProgramado(null);
                        mto.setHorasTraslado(0);
                        msg.SetMensaje(false, "Mantenimiento NO programado. No se asiganó ninguna cuadrilla.");
                    }
                } else {
                    // Setea mensaje de error si el estado del mantenimiento no es válido
                    msg.SetMensaje(false, "Para Programar un Mantenimiento debe estar en Estado 'Pendiente' o 'A Reprogramar'.");
                }
            } else {
                // lanza excepción si el mantenimiento no existe
                throw new IllegalArgumentException("Error: No existe un mantenimiento con este código.");
            }
        } catch (NumberFormatException e) {
            // Captura excepción si el código del mantenimiento y las horas no es un número entero positivo
            msg.SetMensaje(false, "Error: El código del mantenimiento y la hora deben ser números.");
        } catch (ParseException e) {
            // Captura excepción si la fecha no es válida 
            msg.SetMensaje(false, "Error: Formato de fecha no válida.");
        } catch (IllegalArgumentException e) {
            // Captura excepción si el mantenimiento no existe
            msg.SetMensaje(false, e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otro error
            msg.SetMensaje(false, "Error: No se pudo programar el mantenimiento.");
        }
        return msg;
    }

    /**
     * Finalizar un mantenimiento.
     * @param cod El código del mantenimiento.
     * @param estado El estado final del mantenimiento.
     * @return Un mensaje de éxito o error.
     */
    public Mensaje finalizarMantenimiento(String idMto, String estadoFinal) {
        Mensaje msg = new Mensaje();
        try {
            // Valida que el código del mantenimiento sea un número entero positivo
            Integer.valueOf(idMto);
            // Valida que el estado final sea válido
            if (estadoFinal.equals("Realizado")||estadoFinal.equals("A Reprogramar")) {
                // Busca el mantenimiento en la lista
                Mantenimiento mto = buscarMantenimiento(Integer.parseInt(idMto));
                // Valida que el mantenimiento exista
                if (mto != null) {
                    // Valida que el estado del mantenimiento sea valido para Finalizar
                    if (mto.getEstado().equals("Programado")) {
                        if (estadoFinal.equals("Realizado")) {
                            // Actualiza el estado del mantenimiento a "Realizado"
                            mto.setEstado(estadoFinal);
                            msg.SetMensaje(true, "El Mantenimiento ha sido actualizado.");
                        } else {
                            // Elimina fecha programado y horas de traslado a los mantenimientos que sean reprogramados
                            mto.setFechaProgramado(null);
                            mto.setHorasTraslado(0);
                            mto.setEstado(estadoFinal);
                            // Elimina las cuadrillas asignadas al mantenimiento
                            mto.getCuadrillas().clear();
                            msg.SetMensaje(true, "El Mantenimiento ha sido actualizado.");
                        }
                    } else {
                        // Setea mensaje de error si el estado del mantenimiento no es válido para finalizar
                        msg.SetMensaje(false, "Para Finalizar un Mantenimiento debe estar en Estado 'Programado'.");
                    }
                } else {
                    // lanza excepción si el mantenimiento no existe
                    throw new IllegalArgumentException("Error: No existe un mantenimiento con este código.");
                }
            } else {
                // Setea mensaje de error si el estado final no es válido
                msg.SetMensaje(false, "El estado ingresado debe ser 'A Reprogramar' o 'Realizado'.");
            }
        } catch (NumberFormatException e) {
            // Captura excepción si el código del mantenimiento no es un número entero positivo
            msg.SetMensaje(false, "Error: El código del mantenimiento y la hora deben ser números.");
        } catch (IllegalArgumentException e) {
            // Captura excepción si el mantenimiento no existe
            msg.SetMensaje(false, e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otro error
            msg.SetMensaje(false, "Error: No se pudo programar el mantenimiento.");
        }

        return msg;
    }

    /**
     * Retorna la lista de mantenimientos.
     * @return Un ArrayList strings con el resumen de cada mantenimientos.
     */
    public ArrayList<String> getMantenimientosToString() {
        ArrayList<String> listaMantenimientos = new ArrayList<>();
        for (Mantenimiento mantenimiento : mantenimientos) {
            listaMantenimientos.add(mantenimiento.toString());
        }
        return listaMantenimientos;
    }

    /**
     * Detalla el mantenimiento con el código ingresado.
     * @param cod El código del mantenimiento.
     * @return Un smtring con la lista de mantenimientos.
     */
    public String detallarMantenimiento(String cod){
        Mantenimiento mto = null;
        try {
            for (Mantenimiento mantenimiento : mantenimientos) {
                if (mantenimiento.getId() == Integer.parseInt(cod)){
                    mto = mantenimiento;
                    break;
                }
            }
            return mto.detallarMantenimiento();
        } catch (NumberFormatException e) {
            return "Error: El código del mantenimiento debe ser un número.";    
        } catch (Exception e){
            return "El Mantenimiento ID " + cod + " no existe.";
        }
    }

    /**
     * Busca un mantenimiento por su código.
     * @param cod El código del mantenimiento.
     * @return Un mantenimiento.
     */
    private Mantenimiento buscarMantenimiento(int id){
        Mantenimiento mto = null;
        for (Mantenimiento mantenimiento : mantenimientos) {
            if (mantenimiento.getId() == id){
                mto = mantenimiento;
                break;
            }
        }
        return mto;
    }
}
