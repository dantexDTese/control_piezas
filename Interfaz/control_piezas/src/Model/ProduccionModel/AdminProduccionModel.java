
package Model.ProduccionModel;

import Model.Conexion;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class AdminProduccionModel {
               
    public ArrayList<OrdenTrabajo> listaOrdenesTrabajo(){
         ArrayList<OrdenTrabajo> lista = new ArrayList<>();
         Connection c = Conexion.getInstance().getConexion();
         String query = "SELECT ot.id_orden_trabajo,ts.desc_estado " +
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
                        +"WHERE ot.id_orden_trabajo = "+noOrdenTrabajo+" GROUP BY pt.id_orden_produccion ;";
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
                                rs.getInt(1),       //ordenTrabajo
                                rs.getString(2),    //ordenCompra
                                rs.getString(3),    //fechaEntregaPedido
                                rs.getString(4),    //fechaConfirmacionEntrega
                                rs.getString(5),    //fechaRecepcion
                                rs.getString(6),    //descContacto
                                rs.getString(7),    //descCliente
                                rs.getInt(8),       //claveProducto
                                rs.getString(9),    //cantidadTotal
                                rs.getInt(10),      //ordenProduccion
                                rs.getInt(11),      //descMaterial
                                rs.getString(12),   //descMaquina
                                rs.getString(13),   //barrasNecesarias
                                rs.getInt(14),      //piezasPorTurno
                                rs.getInt(15),      //turnosNecesarios
                                rs.getString(16),   //fechaRegistroOrp
                                rs.getString(17),   //fechaMontaje
                                rs.getString(18),   //fechaDesmontaje
                                rs.getString(19),   //fechaInicioProduccion
                                rs.getString(20),   //fechaFin
                                rs.getString(21));  //observaciones
                    } while (rs.next());
                
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel class: AdminProduccionModel Method:obtenerOrdenProduccion "+e.getMessage());
            }
        
        
        return orden;
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
