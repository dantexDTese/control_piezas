/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.ProduccionModel;

import Model.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BitacoraOrdenesTrabajoModel {
    
    
    public ArrayList<RegistroOrdenTrabajo> listaOrdenesTrabajo(int fecha){
        ArrayList<RegistroOrdenTrabajo> listaOrdenes = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = String.format("SELECT * FROM bitacora_ordenes_trabajo WHERE YEAR(fecha_registro) = %d;",fecha) ;
       if(c!=null) 
           try {
               Statement st = c.createStatement();
               ResultSet rs = st.executeQuery(query);
               if(rs.first())
                   do {                                             
                       listaOrdenes.add(new RegistroOrdenTrabajo(rs.getInt(1),rs.getString(2),rs.getString(3)
                               ,rs.getInt(4),rs.getString(5),rs.getString(6),rs.getInt(7),
                               rs.getString(8),rs.getString(9),rs.getString(10)));
                   } while (rs.next());
               c.close();
           } catch (Exception e) {
               System.err.println("error: class:BitacorardenesTrabajoModel, method:listaOrdenesTrabajo "+e.getMessage());
           }
       
       return listaOrdenes;
    }
    
    public String obtenerObservacion (int noOrden){
        String observacion="";    
        Connection c = Conexion.getInstance().getConexion();
        String query = String.format("SELECT observaciones FROM ordenes_produccion WHERE id_orden_produccion = %d", noOrden);
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    observacion = rs.getString(1);
                c.close();
            } catch (Exception e) {
                System.err.println("error: class:BitacoraOrdenesTrabajoModel method: obtenerObservacion");
            }
        
        return observacion;
    }

    public void guardarObservacion(String observacion,int noOrden) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall("{Call guardar_observacion(?,?,?)}");
                cs.setString(1, observacion);
                cs.setInt(2 ,noOrden);
                cs.registerOutParameter(3,Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
                c.close();
            } catch (Exception e) {
                System.err.println("error: class:BitacoraOrdenesTrabajoModel, Method:guardarObservaciones "+e.getMessage());
            }
    }
    
}
//