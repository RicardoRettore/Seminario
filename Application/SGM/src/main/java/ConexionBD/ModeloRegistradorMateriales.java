/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import Modelo.Material;
import Modelo.Mensaje;
/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class ModeloRegistradorMateriales {
    
  private final String url = "jdbc:mysql://localhost:3306/sgm?zeroDateTimeBehavior=CONVERT_TO_NULL";
  private final String user = "Administrador";
  private final String password = "@dminS212024";
  private Connection connection;


  public ModeloRegistradorMateriales(){
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
  
  
  public ArrayList<Material> ConsultarMateriales() throws SQLException {
    ArrayList <Material> listaMateriales = new ArrayList<>(); 
    
    java.sql.Statement sqlMat = null;
    
    try {
      this.conectar();
        
        // Cargar los materiales del stock
        String sqlmat = "select * from materiales";
        
        sqlMat = connection.createStatement();
        java.sql.ResultSet Mat = sqlMat.executeQuery(sqlmat);
        
        while (Mat.next()) {
            listaMateriales.add(new Material(Mat.getString("IDMat"), Mat.getString("Descripcion"), Mat.getString("TipoMaterial"), Mat.getString("Unidad"), Mat.getInt("Cantidad")));
        }
      
      this.desconectar();
      
    } finally {
        if (sqlMat != null) {
                sqlMat.close();
            }
        }
    return listaMateriales;
  }
  
  public Mensaje ActualizarMaterialStock(Material material) throws SQLException {
    Mensaje msg = new Mensaje();
    java.sql.PreparedStatement sqlMat = null;
      
    try {
        this.conectar();
        // Cargar los materiales del stock
        String sqlmat = """
                      UPDATE materiales 
                      SET Cantidad = ? 
                      WHERE IDMat = ?""";
        
        sqlMat = connection.prepareStatement(sqlmat);
        sqlMat.setInt(1, material.getCantidad());
        sqlMat.setString(2, material.getId());
       
        sqlMat.executeUpdate();
        connection.commit();
        this.desconectar();
        msg.SetMensaje(true, "Material actualizado con éxito.");
    }
    catch (SQLException e) {
        msg.SetMensaje(false, "Error al actualizar stock. " + e.getLocalizedMessage());
    }
    catch (Exception e){
        msg.SetMensaje(false, e.getMessage());
    }
    finally {
        if (sqlMat != null) {
              sqlMat.close();
        }
    }

    return msg;
  }
}
