/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import Modelo.Cuadrilla;

/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class ModeloRegistradorCuadrillas {
    
  private final String url = "jdbc:mysql://localhost:3306/sgm?zeroDateTimeBehavior=CONVERT_TO_NULL";
  private final String user = "Administrador";
  private final String password = "@dminS212024";
  private Connection connection;


  public ModeloRegistradorCuadrillas(){
  }

  // Método para conectar a la BD
  public void conectar() throws SQLException {    
    try {
      // Establecer la conexión

      connection = DriverManager.getConnection(url, user, password);
      connection.setAutoCommit(false);

    } finally {
        
    }
  }
 

  public void desconectar() throws SQLException {

    try {
        // Cerrar la conexión
        connection.setAutoCommit(true);
        connection.close();
    } 
    finally {
        
    }
  }  
  
  
  public ArrayList<Cuadrilla> ConsultarCuadrillas() throws SQLException {
    ArrayList <Cuadrilla> listaCuadrillas = new ArrayList<>(); 
    
    java.sql.Statement sqlCuad = null;
    
    try {
      this.conectar();
        
        // Cargar los materiales del stock
        String sqlcuad = "select * from cuadrillas";
        
        sqlCuad = connection.createStatement();
        java.sql.ResultSet Cuad = sqlCuad.executeQuery(sqlcuad);
        
        while (Cuad.next()) {
            listaCuadrillas.add(new Cuadrilla(Cuad.getString("ID_Cuad"), Cuad.getString("Descripcion"), Cuad.getString("Responsable")));
        }
      
      this.desconectar();
      
    } finally {
        if (sqlCuad != null) {
                sqlCuad.close();
            }
        }
    return listaCuadrillas;
  }
  
}
