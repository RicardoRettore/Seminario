/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import Modelo.Mantenimiento;
import Modelo.Material;
import Modelo.Cuadrilla;
import Modelo.Mensaje;


/**
 *
 * @author Ricardo Rettore <Ricardo.Rettore at seminario.org>
 */
public class ModeloRegistradorMantenimiento {

  private final String url = "jdbc:mysql://localhost:3306/sgm?zeroDateTimeBehavior=CONVERT_TO_NULL";
  private final String user = "Administrador";
  private final String password = "@dminS212024";
  private Connection connection;


  public ModeloRegistradorMantenimiento(){
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

  public ArrayList<Mantenimiento> ConsultarMantenimientos() throws SQLException {
    ArrayList <Mantenimiento> listaMantenimientos = new ArrayList<>(); 
    
    java.sql.Statement sqlMto = null;
    java.sql.PreparedStatement sqlMat = null;
    java.sql.PreparedStatement sqlCuad = null;
    
    try {
      this.conectar();

      String sql = "SELECT * FROM mantenimiento";
      sqlMto = connection.createStatement();
      java.sql.ResultSet Mtos = sqlMto.executeQuery(sql);
      while (Mtos.next()) {
        Mantenimiento mantenimiento = new Mantenimiento(Mtos.getInt("ID"), Mtos.getString("Nombre"), Mtos.getString("Descripcion"), Mtos.getString("TipoMantenimiento"), Mtos.getString("Prioridad"));
        mantenimiento.setEstado(Mtos.getString("Estado"));
        mantenimiento.setFechaProgramado(Mtos.getDate("FechaProgramado"));
        mantenimiento.setHorasTraslado(Mtos.getInt("HorasTraslado"));

        
        // Cargar los materiales del mantenimiento
        String sqlmat = """
                        select mat.ID_Mat , m.Descripcion, m.TipoMaterial , m.Unidad , mat.Cantidad
                        from materiales_mantenimiento as mat, mantenimiento as mto, materiales as m
                        where mto.ID = mat.ID_Mto
                        and mat.ID_Mat = m.IDMat
                        and mto.ID = ?""";
        
        sqlMat = connection.prepareStatement(sqlmat);
        sqlMat.setInt(1, Mtos.getInt("ID"));
        java.sql.ResultSet Mat = sqlMat.executeQuery();
        ArrayList<Material> materiales = new ArrayList<>();
        materiales = mantenimiento.getMaterialesMto();
        while (Mat.next()) {
            materiales.add(new Material(Mat.getString("ID_Mat"), Mat.getString("Descripcion"), Mat.getString("TipoMaterial"), Mat.getString("Unidad"), Mat.getInt("Cantidad")));
        }
        
        // Cargar las Cuadrillas asignadas al Mantenimiento
        String qrymat = """
                        select mcuad.ID_Cuad IDCuad, cuad.Descripcion Descripcion, cuad.Responsable Responsable
                        from mantenimiento mto, mantenimiento_cuadrillas mcuad, cuadrillas cuad
                        where mto.ID = mcuad.ID_Mant
                        and  mcuad.ID_Cuad = cuad.ID_Cuad
                        and  mto.ID = ?""";
        
        sqlCuad = connection.prepareStatement(qrymat);
        sqlCuad.setInt(1, Mtos.getInt("ID"));
        java.sql.ResultSet Cuad = sqlCuad.executeQuery();
        ArrayList<Cuadrilla> Cuadrillas = new ArrayList<>();
        Cuadrillas = mantenimiento.getCuadrillas();
        while (Cuad.next()) {
          Cuadrillas.add(new Cuadrilla (Cuad.getString("IDCuad"), Cuad.getString("Descripcion"), Cuad.getString("Responsable")));
        }

        // Agrego el mantenimiento con todos sus atributos al ArrayList
        listaMantenimientos.add(mantenimiento);
      }
      
      this.desconectar();
      
    } finally {
        if (sqlMto != null) {
                sqlMto.close();
            }
        if (sqlMat != null) {
                sqlMat.close();
            }
        if (sqlCuad != null) {
                sqlCuad.close();
            }
        }
    return listaMantenimientos;
  }
 
  
  public Mensaje IngresarMantenimiento (Mantenimiento mto) throws SQLException {
    Mensaje msg = new Mensaje();
    msg.SetMensaje(true, "Mantenimiento agregado!");
     
    java.sql.PreparedStatement sqlMto = null;
    java.sql.PreparedStatement sqlMat = null;


    try {
      this.conectar();
      // insertar mantenimiento
      String sqlmto = """
                      insert into mantenimiento (ID,Nombre,Descripcion,Estado,Prioridad,TipoMantenimiento) 
                      values (?,?,?,?,?,?)""";
      
      sqlMto = connection.prepareStatement(sqlmto);
      sqlMto.setInt(1,mto.getId());
      sqlMto.setString(2,mto.getNombre());
      sqlMto.setString(3,mto.getDescripcion());
      sqlMto.setString(4,mto.getEstado());
      sqlMto.setString(5,mto.getPrioridad());
      sqlMto.setString(6,mto.getTipo());
      sqlMto.executeUpdate();
      // insertar materiales
      String sqlmat = """
                      insert into materiales_mantenimiento (ID_Mto, ID_Mat, Cantidad)
                      values (?,?,?)""";

      sqlMat = connection.prepareStatement(sqlmat);
      sqlMat.setInt(1, mto.getId());
     
      for (Material material : mto.getMaterialesMto()) {
          sqlMat.setString(2, material.getId());
          sqlMat.setInt(3, material.getCantidad());
          sqlMat.addBatch();
      }
      
      sqlMat.executeBatch();
      
      connection.commit();
      this.desconectar();

      }  
      catch (SQLException e) {
          msg.SetMensaje(false, "Error: No fue posible agregar el mantenimiento. " + e.getMessage());
          connection.rollback();
      }  
      finally {
          if (sqlMto != null) {
                  sqlMto.close();
          }
          if (sqlMat != null) {
                  sqlMat.close();
          }
      }
    return msg;
  }
  
  
  public Mensaje ProgramarMantenimiento (Mantenimiento mto) throws SQLException {
    Mensaje msg = new Mensaje();
    msg.SetMensaje(true, "Mantenimiento Programado!");
     
    java.sql.PreparedStatement sqlMto = null;
    java.sql.PreparedStatement sqlCuad = null;


    try {
      this.conectar();
      

      // actualizar mantenimiento
      String sqlmto = """
                      UPDATE mantenimiento 
                      SET FechaProgramado = ?, HorasTraslado = ?, Estado = ? 
                      WHERE ID = ?""";
      
      sqlMto = connection.prepareStatement(sqlmto);
      sqlMto.setDate(1, new java.sql.Date(mto.getFechaProgramado().getTime()));
      sqlMto.setInt(2,mto.getHorasTraslado());
      sqlMto.setString(3,mto.getEstado());
      sqlMto.setInt(4,mto.getId());

      sqlMto.executeUpdate();
     

      // insertar cuadrillas
      String sqlcuad = """
                      insert into mantenimiento_cuadrillas (ID_Mant, ID_Cuad)
                      values (?,?)""";

      sqlCuad = connection.prepareStatement(sqlcuad);
      sqlCuad.setInt(1, mto.getId());
     
      for (Cuadrilla cuadrilla : mto.getCuadrillas()) {
          sqlCuad.setString(2, cuadrilla.getIdCuad());
          sqlCuad.addBatch();
      }
      
      sqlCuad.executeBatch();
      
      connection.commit();
      this.desconectar();

      }  
      catch (SQLException e) {
          msg.SetMensaje(false, "Error: No fue posible Programar el mantenimiento. " + e.getMessage());
          connection.rollback();
      }  
      finally {
          if (sqlMto != null) {
                  sqlMto.close();
          }
          if (sqlCuad != null) {
                  sqlCuad.close();
          }
      }
    return msg;
  }

  public Mensaje ReprogramarMantenimiento (Mantenimiento mto) throws SQLException {
    Mensaje msg = new Mensaje();
    msg.SetMensaje(true, "Mantenimiento Actualizado!");
     
    java.sql.PreparedStatement sqlMto = null;
    java.sql.PreparedStatement sqlCuad = null;


    try {
      this.conectar();
      

      // actualizar mantenimiento
      String sqlmto = """
                      UPDATE mantenimiento 
                      SET FechaProgramado = null, HorasTraslado = ?, Estado = ? 
                      WHERE ID = ?""";
      
      sqlMto = connection.prepareStatement(sqlmto);
      sqlMto.setInt(1, mto.getHorasTraslado());
      sqlMto.setString(2,mto.getEstado());
      sqlMto.setInt(3,mto.getId());

      sqlMto.executeUpdate();
     

      // borrar cuadrillas
      String sqlcuad = """
                      delete from mantenimiento_cuadrillas
                      where ID_Mant = ?""";

      sqlCuad = connection.prepareStatement(sqlcuad);
      sqlCuad.setInt(1, mto.getId());
     
      
      sqlCuad.executeUpdate();
      
      connection.commit();
      this.desconectar();

      }  
      catch (SQLException e) {
          msg.SetMensaje(false, "Error: No fue posible Actualizar el mantenimiento. " + e.getMessage());
          connection.rollback();
      }  
      finally {
          if (sqlMto != null) {
                  sqlMto.close();
          }
          if (sqlCuad != null) {
                  sqlCuad.close();
          }
      }
    return msg;
  }

  public Mensaje FinalizarMantenimiento (Mantenimiento mto) throws SQLException {
    Mensaje msg = new Mensaje();
    msg.SetMensaje(true, "Mantenimiento Actualizado!");
     
    java.sql.PreparedStatement sqlMto = null;


    try {
      this.conectar();
      

      // actualizar mantenimiento
      String sqlmto = """
                      UPDATE mantenimiento 
                      SET Estado = ? 
                      WHERE ID = ?""";
      
      sqlMto = connection.prepareStatement(sqlmto);
      sqlMto.setString(1,mto.getEstado());
      sqlMto.setInt(2,mto.getId());

      sqlMto.executeUpdate();
      
      connection.commit();
      this.desconectar();

      }  
      catch (SQLException e) {
          msg.SetMensaje(false, "Error: No fue posible Actualizar el mantenimiento. " + e.getMessage());
          connection.rollback();
      }  
      finally {
          if (sqlMto != null) {
                  sqlMto.close();
          }
      }
    return msg;
  }
  
}

