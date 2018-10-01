/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.ProduccionModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BitacoraOrdenesTrabajoModel {
    
    
    public ArrayList<RegistroOrdenTrabajo> listaOrdenesTrabajo(){
        ArrayList<RegistroOrdenTrabajo> listaOrdenes = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "select * from bitacora_ordenes_trabajo;";
       if(c!=null) 
           try {
               Statement st = c.createStatement();
               ResultSet rs = st.executeQuery(query);
               if(rs.first())
                   do {                                             
                       listaOrdenes.add(new RegistroOrdenTrabajo(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(4),rs.getInt(5),
                               rs.getString(6),rs.getString(7),rs.getString(8)));
                   } while (rs.next());
               c.close();
           } catch (Exception e) {
               System.err.println("error: class:BitacorardenesTrabajoModel, method:listaOrdenesTrabajo "+e.getMessage());
           }
       return listaOrdenes;
    }
    
}
