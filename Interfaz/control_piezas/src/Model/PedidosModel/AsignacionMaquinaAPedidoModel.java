
package Model.PedidosModel;

import Model.ProductoPendiente;
import Model.Conexion;
import Model.Estructuras;
import Model.ProductoMaquina;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class AsignacionMaquinaAPedidoModel {

    
    public final int LISTA_MAQUINAS = 1;
    public final int LISTA_MATERAILES = 2;
    
    
    public AsignacionMaquinaAPedidoModel() {
     
    }
    
    public JComboBox obtenerLista(JComboBox combo,int numLista){
        switch(numLista){
            case LISTA_MAQUINAS:
                return Estructuras.llenaCombo(combo,"SELECT desc_maquina FROM maquinas");
        } 
        return combo;
    }
    
    public ArrayList<Pedido> listaPedidosPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null) 
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(" SELECT pd.id_pedido,pd.no_orden_compra,pd.fecha_entrega FROM pedidos AS pd JOIN " +
                                                " ordenes_trabajo AS ot ON  pd.id_pedido = ot.id_pedido " +
                                                " JOIN (SELECT op.id_orden_trabajo,op.id_orden_produccion,op.clave_producto,op.cantidad_cliente " + 
                                                " FROM  todas_ordenes_produccion AS op " +
                                                " LEFT JOIN lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion WHERE " +
                                                " lp.id_lote_planeado IS NULL) AS opp ON ot.id_orden_trabajo = opp.id_orden_trabajo GROUP BY pd.id_pedido;");
                
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
    
    public ArrayList<ProductoPendiente> listaProductosPendientes(String noOrdenTrabajo){
        ArrayList<ProductoPendiente> listaProductos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {        
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT op.id_orden_trabajo,op.id_orden_produccion"
                                                + ",op.clave_producto,op.cantidad_cliente," +
                                                "desc_tipo_material,desc_dimencion,desc_forma,clave_forma,id_material FROM todas_ordenes_produccion AS op " +
                                                " LEFT JOIN lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion " +
                                                "WHERE (lp.id_lote_planeado IS NULL OR lp.id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'CERRADO')) " +
                                                "AND op.id_orden_trabajo ="+noOrdenTrabajo+";");
                if(rs.first())
                    do {                        
                        listaProductos.add(new ProductoPendiente(
                                rs.getInt(1), //id_orden_trabajo
                                rs.getString(3), //clave_producto
                                rs.getInt(2),  //id_orden_produccion
                                rs.getInt(4),   //cantidad_cliente
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getInt(9)
                        ));          
                    } while (rs.next());
                
                
                c.close();
            } catch (SQLException e) {
                System.err.println("mensaje: class:AsignacionMaquinaAPedido"
                        + "Method:listaProductosPendientes"+e.getMessage());
            }
        return listaProductos;        
    }
    
    public String agregarProductoPendiente(ProductoPendiente pendiente,int nOrden) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call agregar_orden_maquina(?,?,?,?,?,?,?,?,?,?,?,?)}";
        String res=null;
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, pendiente.getNoOrdenProduccion());
                cs.setString(2,pendiente.getCodProducto());
                cs.setFloat(3,pendiente.getWorker());
                cs.setInt(4,pendiente.getCantidadTotal());
                cs.setString(5,pendiente.getProductoMaquinaSeleccionado().getDescMaquina());
                cs.setInt(6, pendiente.getNoMaterial());
                cs.setString(7, pendiente.getFechaMontaje());
                cs.setString(8, pendiente.getFechaInicio());
                cs.setInt(9, pendiente.getPiezasPorTurno());
                cs.setInt(10, nOrden);
                cs.setInt(11, pendiente.getDiasTrabajo());
                cs.registerOutParameter(12, Types.VARCHAR);
                cs.execute();
                res = cs.getString(12);
                c.close();
            } catch (SQLException e) {
                System.err.println("error: class: AsignacionMaquinaAPedidoModel metohd:agregarProductoPendiente "+e.getMessage() );
            }
        return res;
    }

    public ArrayList<ProductoMaquina> obtenerListaProductoMaquina(String codProducto) {
        ArrayList<ProductoMaquina> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "SELECT piezas_por_turno,piezas_por_hora,desc_maquina,id_producto_maquina "
                        + "FROM ver_productos_maquinas WHERE clave_producto = '"+codProducto+"' "
                        + "AND desc_tipo_proceso = 'Maquinado';";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                       lista.add(new ProductoMaquina(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4)));
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                
            }
        return lista;
    }
    
    

    
}
