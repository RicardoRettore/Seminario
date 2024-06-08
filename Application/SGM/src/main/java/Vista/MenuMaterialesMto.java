/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Mantenimiento;
import Modelo.Mensaje;
import Controlador.ControladorMaterialesMto;
import java.util.Scanner;

/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class MenuMaterialesMto {

  private Mantenimiento mto;
  private ControladorMaterialesMto gestor;


  public MenuMaterialesMto(Mantenimiento mto) {
    this.mto = mto;
    gestor = new ControladorMaterialesMto(mto.getMaterialesMto());
  }

  public void ejecutarMenu(){
    
    int opcion = -1;
    do {
      mostrarMenu();
      try {
            opcion = Integer.parseInt(new Scanner(System.in).nextLine());
      } catch (Exception e){
        opcion = -1;
      }

      switch (opcion) {
        case 1:
          agregarMaterial();
          break;
        case 2:
          eliminarMaterial();
          break;
        case 0:
          System.out.println("Saliendo de gestión de Materiales...\n\n");
          System.out.print("\033[H\033[2J");
          System.out.flush();
          break;
        default:
          System.out.println("Opción inválida. Por favor, elige una opción válida.");
      }
    } while (opcion != 0);
    
    
  }

  private void mostrarMenu() {
        System.out.println("\n----------------------------");
        System.out.println("\nGestionar Materiales del Mantenimiento " + String.valueOf(mto.getId()) + ":");
        System.out.println("1. Agregar Material");
        System.out.println("2. Eliminar Material");
        System.out.println("0. Salir");
        System.out.println("\n----------------------------");
        System.out.print("Elige una opción: ");
    }

  private void agregarMaterial(){
    Mensaje msg = new Mensaje();
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.println("Agregar Material");
    // ...
    System.out.println("Materiales existentes:\n");
    System.out.println("------------------------");
    System.out.println(gestor.listarStockMateriales());
    System.out.println("------------------------\n");

    System.out.println("Ingrese el ID del material a agregar:");
    String id = new Scanner(System.in).nextLine();
    System.out.println("Ingrese la cantidad a agregar:");
    String cantidad = new Scanner(System.in).nextLine();


    msg = gestor.agregarMaterialMto(id,cantidad);
    if (msg.getResultado()){
      System.out.println("SIIII -> " + msg.getMensaje());
    } else {
      System.out.println("NOOOO -> " + msg.getMensaje());
    }
    //System.out.println("Mantenimiento agregado exitosamente.");

    System.out.println("\n----------------------------");
    System.out.println("Materiales del Mantenimiento\n");
    System.out.println(gestor.listarMateriales());
    
  }

  private void eliminarMaterial(){
    Mensaje msg = new Mensaje();
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.println("Eliminar Material");
    // ...
    System.out.println("Materiales existentes:\n");
    System.out.println("------------------------");
    System.out.println(gestor.listarMateriales());
    System.out.println("------------------------\n");

    System.out.println("Ingrese el ID del material a eliminar:");
    String id = new Scanner(System.in).nextLine();
    System.out.println("Ingrese la cantidad a eliminar:");
    String cantidad = new Scanner(System.in).nextLine();

    msg = gestor.eliminarMaterialMto(id,cantidad);
    if (msg.getResultado()){
      System.out.println("SIIII -> " + msg.getMensaje());
    } else {
      System.out.println("NOOOO -> " + msg.getMensaje());
    }
    //System.out.println("Mantenimiento agregado exitosamente.");

    System.out.println("\n----------------------------");
    System.out.println("Materiales del Mantenimiento\n");
    System.out.println(gestor.listarMateriales());
  }
}
