
package Model.RequisicionesModel;

import Model.Conexion;
import Model.Requisicion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AsignacionMaterialModel {
    
   public ArrayList<Requisicion> obtenerListaRequisiciones(){
       ArrayList<Requisicion> listaRequisiciones = new ArrayList<>();
       Connection c = Conexion.getInstance().getConexion();
       String query =   " SELECT rq.id_requisicion,rq.fecha_creacion " +
                        " FROM materiales_solicitud_compras AS msc " +
                        " INNER JOIN materiales_solicitados AS ms " +
                        " ON msc.id_material_solicitado = ms.id_material_solicitado " +
                        " INNER JOIN requisiciones AS rq ON rq.id_requisicion = ms.id_requisicion " +
                        " GROUP BY rq.id_requisicion; ";
       if(c!=null)
           try {
               Statement st = c.createStatement();
               ResultSet rs = st.executeQuery(query);
               if(rs.next())
                   do {                       
                       Requisicion rq = new Requisicion();
                       rq.setNoRequisicion(rs.getInt(1));
                       rq.setFechaCreacion(rs.getString(2));
                       listaRequisiciones.add(rq);
                   } while (rs.next());
           } catch (SQLException e) {
               System.err.println(" paquete:RequisicionesController class:AsignacionMaterialModel "
                       + " metodo:obtenerListaRequisiciones ");
           }
       return listaRequisiciones;
   }
    
}
