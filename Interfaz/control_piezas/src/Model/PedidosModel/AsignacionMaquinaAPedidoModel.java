
package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JComboBox;


public class AsignacionMaquinaAPedidoModel {

    
    public final int LISTA_MAQUINAS = 1;
    public final int LISTA_MATERAILES = 2;
    
    
    public AsignacionMaquinaAPedidoModel() {
     
    }
    
    public JComboBox obtenerLista(JComboBox combo,int numLista){
        switch(numLista){
            case LISTA_MAQUINAS:
                return Estructuras.llenaCombo(combo,"SELECT desc_maquina FROM maquinas");
            case LISTA_MATERAILES:
                return Estructuras.llenaCombo(combo,"SELECT desc_material FROM materiales");
        } 
        return combo;
    }
    
    public ArrayList<Pedido> listaPedidosPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null) 
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT pd.id_pedido,pd.no_orden_compra,pd.fecha_entrega FROM pedidos AS pd JOIN " +
                                                "ordenes_trabajo AS ot ON  pd.id_pedido = ot.id_pedido " +
                                                "JOIN ordenes_por_planear AS opp ON ot.id_orden_trabajo = opp.id_orden_trabajo GROUP BY pd.id_pedido;");
                
                if(rs.first())
                    do {                         
                        Pedido pedido = new Pedido(rs.getInt(1),rs.getString(2),rs.getString(3));
                        pedidos.add(pedido);
                    } while (rs.next());
               c.close(); 
            } catch (SQLException e) {
                System.err.println("error: class: AsignacionMaquinaAPedidoModel, Method:listaPedidosPendientes "+e.getMessage());
            }        
        return pedidos;
    }
    
    public ArrayList<ProductosPendientes> listaProductosPendientes(String noOrdenTrabajo){
        ArrayList<ProductosPendientes> listaProductos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {        
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT op.id_orden_trabajo,op.id_orden_produccion"
                                                + ",op.clave_producto,op.cantidad_cliente FROM ordenes_produccion_abiertas AS op " +
                                                " LEFT JOIN lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion " +
                                                "WHERE (lp.id_lote_planeado IS NULL OR lp.id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'CERRADO')) " +
                                                "AND op.id_orden_trabajo ="+noOrdenTrabajo+";");
                if(rs.first())
                    do {                        
                        listaProductos.add(new ProductosPendientes(
                                rs.getString(1), //id_orden_trabajo
                                rs.getInt(2),  //id_orden_produccion
                                rs.getString(3), //clave_producto
                                rs.getInt(4)));  //cantidad_cliente                      
                    } while (rs.next());
            } catch (SQLException e) {
                System.err.println("mensaje: class:AsignacionMaquinaAPedido"
                        + "Method:listaProductosPendientes"+e.getMessage());
            }
        return listaProductos;        
    }
    
    public String agregarProductoPendiente(ProductosPendientes pendiente,int nOrden) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call agregar_orden_maquina(?,?,?,?,?,?,?,?,?,?,?,?)}";
        String res=null;
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                
                cs.setInt(1, pendiente.getNoOrdenProduccion());
                cs.setString(2,pendiente.getClaveProducto());
                cs.setFloat(3,pendiente.getWorker());
                cs.setInt(4,pendiente.getQty());
                cs.setString(5,pendiente.getMaquina());
                cs.setString(6, pendiente.getMaterial());
                cs.setString(7, pendiente.getFechaMontaje());
                cs.setString(8, pendiente.getFechaInicio());
                cs.setInt(9, pendiente.getPiecesByShift());
                cs.setInt(10, nOrden);
                cs.setInt(11, pendiente.getDiasTrabajar());
                cs.registerOutParameter(12, Types.VARCHAR);
                cs.execute();
                res = cs.getString(12);
                c.close();
            } catch (SQLException e) {
                System.err.println("error: class: AsignacionMaquinaAPedidoModel metohd:agregarProductoPendiente "+e.getMessage() );
            }
        return res;
    }

    public Integer obtenerPiezasTurno(String claveProducto, String descMaterial) {        
        Connection c = Conexion.getInstance().getConexion();        
        String query = "SELECT piezas_por_turno FROM productos_material WHERE " +
                       "id_material = (SELECT id_material FROM materiales WHERE desc_material = '"+descMaterial+"') " +
                       "AND id_producto = (SELECT id_producto FROM productos WHERE clave_producto = '"+claveProducto+"');";        
        
        Integer piezasTurno = null;
        
        if(c!=null)
            try {                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    piezasTurno = rs.getInt(1);                    
            } catch (SQLException e) {
                System.err.println("error: class:AsignacionMaquinaAPedidoModel method:obtenerPiezasTurno "+e.getMessage());                
            }
        
        return piezasTurno;
    }

}
