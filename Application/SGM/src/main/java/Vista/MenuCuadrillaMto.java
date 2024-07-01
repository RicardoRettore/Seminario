/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.util.Scanner;
import Modelo.Mantenimiento;
import Modelo.Mensaje;
import Controlador.ControladorCuadrillasMto;
import java.sql.SQLException;
/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class MenuCuadrillaMto {
    private Mantenimiento mto;
    private ControladorCuadrillasMto gestor;

    public MenuCuadrillaMto (Mantenimiento mto) {
        this.mto = mto;
        gestor = new ControladorCuadrillasMto(mto.getCuadrillas());

    }
        
        
    public void ejecutarMenu(){
        int opcion;
        do {
            mostrarMenu();
           
            try{
                opcion = Integer.parseInt(new Scanner(System.in).nextLine());
            } catch (Exception e) {
                opcion = -1;
            }
            
            switch (opcion) {
                case 1:
                    agregarCuadrilla();
                    break;
                case 2:
                    eliminarCuadrilla();
                    break;
                case 0:
                    System.out.println("Saliendo de gestión de cuadrillas...");
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu(){
        System.out.println("");
        System.out.println("\nMenú de cuadrillas: ");
        System.out.println("1. Agregar cuadrilla");
        System.out.println("2. Eliminar cuadrilla");
        System.out.println("0. Salir");
        System.out.println("\n----------------------------");
        System.out.print("Elige una opción: ");
    }

    private void agregarCuadrilla(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Agregar cuadrilla");
        System.out.println("Cuadrillas existentes:\n");
        System.out.println("------------------------");
        try {
            System.out.println(gestor.listarCuadrillasDisponibles());
            System.out.println("------------------------\n");
            System.out.print("Ingrese el ID de la cuadrilla: ");
            String idCuad = new Scanner(System.in).nextLine();
            Mensaje msg = gestor.agregarCuadrillaMto(idCuad);
            if (msg.getResultado()){
                System.out.println("SIIII -> " + msg.getMensaje());
            } else {
                System.out.println("NOOOO -> " + msg.getMensaje());
            }
        }
        catch (SQLException e) {
            System.out.println("Error al cargar las cuadrillas disponibles. " + e.getMessage());
        }
    }

    private void eliminarCuadrilla(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Eliminar cuadrilla");
        System.out.println("------------------------");
        System.out.println(gestor.listarCuadrillasMto());
        System.out.println("------------------------\n");
        System.out.print("Ingrese el ID de la cuadrilla a eliminar: ");
        String idCuad = new Scanner(System.in).nextLine();
        Mensaje msg = gestor.eliminarCuadrillaMto(idCuad);
        if (msg.getResultado()){
            System.out.println("SIIII -> " + msg.getMensaje());
        } else {
            System.out.println("NOOOO -> " + msg.getMensaje());
        }
    }    
}
