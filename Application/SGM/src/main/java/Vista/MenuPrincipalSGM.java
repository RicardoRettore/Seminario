/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.util.Scanner;
import java.util.ArrayList;
import Modelo.Mensaje;
import Controlador.ControladorMantenimiento;
import java.sql.SQLException;
/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class MenuPrincipalSGM {
    private ControladorMantenimiento gestor;
    
    public MenuPrincipalSGM() {
        gestor = new ControladorMantenimiento();
        
        int opcion = -1;
        do {
            mostrarMenu();
            try{
                opcion = Integer.parseInt(new Scanner(System.in).nextLine());
            } catch (Exception e){
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    agregarMantenimiento();
                    break;
                case 2:
                    programarMantenimiento();
                    break;
                case 3:
                    finalizarMantenimiento();
                    break;
                case 4:
                    mostrarMantenimientos();
                    break;
                case 5:
                    detallarMantenimiento();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elige una opción válida.");
            }
        } while (opcion != 0);

    }


    private void mostrarMenu() {
        System.out.println("\n----------------------------");
        System.out.println("\nSistema Mantenimiento: ");
        System.out.println("1. Agregar Nuevo Mantenimiento");
        System.out.println("2. Programar Mantenimiento");
        System.out.println("3. Finalizar Mantenimiento");
        System.out.println("4. Mostrar Mantenimientos");
        System.out.println("5. Detallar Mantenimiento");
        System.out.println("0. Salir");
        System.out.println("\n----------------------------");
        System.out.print("Elige una opción: ");
    }

    private void agregarMantenimiento() {
        // Implementa la lógica para agregar un nuevo mantenimiento
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Agregar Nuevo Mantenimiento");
        // ...
        Mensaje msg = new Mensaje();
        System.out.print("Ingrese el código del mantenimiento: ");
        String cod = new Scanner(System.in).nextLine();
        System.out.print("Ingrese el nombre del mantenimiento: ");
        String nombre = new Scanner(System.in).nextLine();
        System.out.print("Ingrese la descripción del mantenimiento: ");
        String descripcion = new Scanner(System.in).nextLine();
        System.out.print("Ingrese el tipo de mantenimiento: ");
        String tipo = new Scanner(System.in).nextLine();
        System.out.print("Ingrese la prioridad del mantenimiento (Alta - Media - Baja): ");
        String prioridad = new Scanner(System.in).nextLine();
        try {
            msg = gestor.agregarMantenimiento(cod, nombre, descripcion, tipo, prioridad);
        } catch (SQLException e) {
            System.out.println("Error al insertar información de la Base de Datos.");
            System.out.println(e.getMessage());
        }
        
        if (msg.getResultado()){
            System.out.println("SIIII -> " + msg.getMensaje());
        } else {
            System.out.println("NOOOO -> " + msg.getMensaje());
        }
    }

    private void programarMantenimiento() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Programar Mantenimiento");
        // ...
        Mensaje msg = new Mensaje();
        System.out.print("Ingrese el código del mantenimiento: ");
        String cod = new Scanner(System.in).nextLine();
        System.out.print("Ingrese la fecha de programación (dd/mm/aaaa): ");
        String fecha = new Scanner(System.in).nextLine();
        System.out.print("Ingrese las horas de traslado: ");
        String horas = new Scanner(System.in).nextLine();
        msg = gestor.programarMantenimiento(cod, fecha, horas);
        if (msg.getResultado()){
            System.out.println("SIIII -> " + msg.getMensaje());
        } else {
            System.out.println("NOOOO -> " + msg.getMensaje());
            System.out.println("No se pudo programar el mantenimiento. Por favor, verifique los datos ingresados.");
        }
    }

    private void finalizarMantenimiento() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Finalizar Mantenimiento");
        // ...
        Mensaje msg = new Mensaje();
        System.out.print("Ingrese el código del mantenimiento: ");
        String cod = new Scanner(System.in).nextLine();
        System.out.print("Ingrese el estado final (Realizado - A Reprogramar): ");
        String estado = new Scanner(System.in).nextLine();
        msg = gestor.finalizarMantenimiento(cod, estado);
        if (msg.getResultado()){
            System.out.println("SIIII -> " + msg.getMensaje());
        } else {
            System.out.println("NOOOO -> " + msg.getMensaje());
            System.out.println("No se pudo actualizar el mantenimiento. Por favor, verifique los datos ingresados.");
        }
    }

    
    private void mostrarMantenimientos(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Mantenimientos:");
        
        try {
            ArrayList<String> listaMantenimientos = gestor.getMantenimientosToString();
            for (String mantenimiento : listaMantenimientos) {
                System.out.println(mantenimiento);
            }
        }
        catch (SQLException e) {
            System.out.println("Error al recuperar información de la Base de Datos.");
            System.out.println(e.getMessage());
                }
    }

    
    private void detallarMantenimiento(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Detallar Mantenimiento");

        // ...
        
        System.out.print("Ingrese el código del mantenimiento: ");
        String cod = new Scanner(System.in).nextLine();
        System.out.println("\n----------------------------");
        try {
            System.out.println(gestor.detallarMantenimiento(cod));
        }
        catch (SQLException e) {
            System.out.println("Error al recuperar información de la Base de Datos.");
            System.out.println(e.getMessage());
        }
        System.out.println("----------------------------\n");

    }
}
