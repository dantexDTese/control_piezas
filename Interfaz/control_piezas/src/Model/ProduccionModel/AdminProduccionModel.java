
package Model.ProduccionModel;

import Model.Conexion;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class AdminProduccionModel {
               
    public ArrayList<OrdenTrabajo> listaOrdenesTrabajo(){
         ArrayList<OrdenTrabajo> lista = new ArrayList<>();
         Connection c = Conexion.getInstance().getConexion();
         String query = "SELECT ot.id_orden_trabajo,ts.desc_estados " +
                        "FROM ordenes_trabajo AS ot JOIN procedimiento_total " +
                        "AS pt ON ot.id_orden_trabajo = pt.id_orden_trabajo " +
                        "JOIN todos_los_estados  AS ts ON ot.id_estado = ts.id_estado " + 
                        "GROUP BY ot.id_orden_trabajo;";
         if(c!=null)
             try {                 
                 Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery(query);
                 if(rs.first())
                     do{
                         lista.add(new OrdenTrabajo(rs.getInt(1),rs.getString(2)));
                     }while(rs.next());
                 c.close();
             } catch (SQLException e) {
                 System.err.println("error: clase AdminProduccionModel "
                         + "method: llstaOrdenesTrabajo "+e.getMessage());
             }
         return lista;
     }       
          
    public ArrayList<OrdenProduccion> listaOrdenesProduccion(int noOrdenTrabajo){
         ArrayList<OrdenProduccion> lista = new ArrayList<>();
         Connection c = Conexion.getInstance().getConexion();
         String query = "SELECT pt.id_orden_produccion,pt.clave_producto " +
                        "FROM ordenes_trabajo AS ot JOIN procedimiento_total " +
                        "AS pt ON ot.id_orden_trabajo = pt.id_orden_trabajo " +
                        "JOIN todos_los_estados  AS ts ON ot.id_estado = ts.id_estado "
                        +"WHERE ot.id_orden_trabajo = "+noOrdenTrabajo+" ;";
         if(c!=null)
             try {                 
                 Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery(query);
                 if(rs.first())
                     do{
                         lista.add(new OrdenProduccion(rs.getInt(1),rs.getString(2)));
                     }while(rs.next());
                 c.close();
             } catch (SQLException e) {
                 System.err.println("error: clase AdminProduccionModel "
                         + "method: llstaOrdenesProduccion "+e.getMessage());
             }
         
         return lista;         
     }
    
    public OrdenProduccionGuardada obtenerOrdenProduccion(int noOrdenProduccion){
        OrdenProduccionGuardada orden = null;
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM ver_ordenes_produccion WHERE id_orden_produccion = "+noOrdenProduccion+";";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                       
                        orden = new OrdenProduccionGuardada(
                                rs.getInt(1),rs.getString(2),rs.getString(3),
                                rs.getString(4),rs.getString(5),rs.getString(6),
                                rs.getString(7),rs.getInt(8),rs.getString(9),
                                rs.getInt(10),rs.getInt(11),rs.getString(12),
                                rs.getString(13),rs.getInt(14),rs.getInt(15),
                                rs.getString(16),rs.getString(17),rs.getString(18),
                                rs.getString(19),rs.getString(20),rs.getString(21));
                        
                    } while (rs.next());
                
                c.close();
            } catch (SQLException e) {
                
            }
        
        
        return orden;
    }

    

    public void modificarBarrasNecesarias(int noOrden,int barras) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call modificar_barras_necesarias(?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1,noOrden);
                cs.setInt(2, barras);
                cs.registerOutParameter(3,Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
            } catch (SQLException e) {
                System.err.println("Error: Class: AdminProduccionModel Method:modificarBarrasNecesarias "+e.getMessage());
            }
    }
     
    public final class OrdenTrabajo extends Pedido{
        
        
        public OrdenTrabajo(int noOrdenTrabajo, String estado) {
            super(noOrdenTrabajo, estado);
        }    
        
    }
        
    public final class OrdenProduccion extends ProductosPendientes{        
        public OrdenProduccion(int noOrdenProduccion, String claveProducto) {
            super(noOrdenProduccion, claveProducto);
        }               
    }
        
}
