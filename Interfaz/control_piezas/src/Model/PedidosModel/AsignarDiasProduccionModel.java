package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import Model.Pedido;
import Model.ProductoMaquina;
import Model.ProductoPendiente;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class AsignarDiasProduccionModel {

    public final int LISTA_MAQUINAS =  1;
    public final int LISTA_PROCESOS = 2;
    
    
    public ArrayList<Pedido> listaPedidosPendientes(){
        
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        Connection c = Conexion.getInstance().getConexion();
        
        String query = "SELECT pd.id_pedido,pd.no_orden_compra,pd.fecha_entrega FROM todos_pedidos AS pd " +
                "JOIN (SELECT op.id_orden_trabajo,op.id_orden_produccion,op.clave_producto,op.cantidad_cliente " +
                "FROM  todas_ordenes_produccion AS op " +
                "LEFT JOIN todos_lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion WHERE " +
                "(todos_lotes_cerrados(op.id_orden_produccion) AND op.desc_estado = 'ABIERTO') OR obtener_piezas_procesar(op.id_orden_produccion,lp.desc_tipo_proceso) > 0) "
                + "AS opp ON pd.id_orden_trabajo = opp.id_orden_trabajo " +
                "WHERE pd.desc_estado = 'ABIERTO' GROUP BY pd.id_pedido;";
        
            if(c!=null)
                try {
                    Statement st = c.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    if(rs.first())
                        do {                        
                            Pedido pedido = new Model.Pedido();
                            pedido.setNoPedido(rs.getInt(1));
                            pedido.setNoOrdenCompra(rs.getString(2));
                            pedidos.add(pedido);
                        } while (rs.next());
                   c.close(); 
                } catch (SQLException e) {
                    System.err.println("error: class: AsignacionMaquinaAPedidoModel, Method:listaPedidosPendientes "+e.getMessage());
                }        
            return pedidos;
    }
    
    public final int SELECCION_FALTAMTES_PROCESAR = 0;
    public final int SELECCION_SIGUIENTE_PROCESO = 1;
    
    public ArrayList<OrdenProduccionNuevaPlaneacion> listaOrdenesNuevaPlaneacion(String ordenCompra,int tipoSeleccion){
        ArrayList<OrdenProduccionNuevaPlaneacion> lista= new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query;
        if(c!=null)
            try {
                Statement st = c.createStatement();
                
                if(tipoSeleccion == SELECCION_FALTAMTES_PROCESAR){
                    query = "SELECT op.id_orden_produccion,op.clave_producto,op.cantidad_total,obtener_piezas_procesar(op.id_orden_produccion,lp.desc_tipo_proceso) AS " +
                            "piezas_faltantes,op.worker FROM (SELECT * FROM todos_pedidos WHERE desc_estado = 'ABIERTO' AND no_orden_compra = '"+ordenCompra+"') AS pd " +
                            "INNER JOIN (SELECT * FROM todas_ordenes_produccion WHERE desc_estado = 'ABIERTO') AS op ON pd.id_orden_trabajo = op.id_orden_trabajo " +
                            "INNER JOIN todos_lotes_planeados AS lp ON lp.id_orden_produccion = op.id_orden_produccion " +
                            "WHERE obtener_piezas_procesar(op.id_orden_produccion,lp.desc_tipo_proceso) > 0 " +
                            "GROUP BY op.id_orden_produccion;";
                    ResultSet rs = st.executeQuery(query);
                    if(rs.first())
                    do {          
                        OrdenProduccionNuevaPlaneacion nuevo = new OrdenProduccionNuevaPlaneacion();    
                        nuevo.setNoOrdenProduccion(rs.getInt(1));
                        nuevo.setCodProducto(rs.getString(2));
                        nuevo.setCantidadTotal(rs.getInt(3));
                        nuevo.setPiezasParaProcesar(rs.getInt(4));
                        nuevo.setWorker(rs.getFloat(5));
                        
                        lista.add(nuevo); 
                    } while (rs.next());
                    
                }else if(tipoSeleccion == SELECCION_SIGUIENTE_PROCESO){
                    
                    query = " SELECT op.id_orden_produccion,op.clave_producto,op.cantidad_total AS piezas_listas " +
                            " FROM (SELECT * FROM todos_pedidos WHERE desc_estado = 'ABIERTO' AND no_orden_compra = '"+ordenCompra+"') AS pd " +
                            " INNER JOIN (SELECT * FROM todas_ordenes_produccion WHERE desc_estado = 'ABIERTO') AS op ON pd.id_orden_trabajo = op.id_orden_trabajo " +
                            " INNER JOIN (SELECT * FROM  todos_lotes_planeados WHERE desc_estado = 'CERRADO' ) "
                            + "AS lp ON lp.id_orden_produccion = op.id_orden_produccion "+
                            " GROUP BY op.id_orden_produccion; ";
                    
                    ResultSet rs = st.executeQuery(query);
                    if(rs.first())
                    do {
                        
                        OrdenProduccionNuevaPlaneacion nuevo = new OrdenProduccionNuevaPlaneacion();
                        nuevo.setNoOrdenProduccion(rs.getInt(1));
                        nuevo.setCodProducto(rs.getString(2));
                        nuevo.setCantidadTotal(rs.getInt(3));
                        //nuevo.setPiezasListas(rs.getInt(4));
                        lista.add(nuevo); 
                        
                    } while (rs.next());
                    
                    
                }
                c.close();
            } catch (SQLException e) {
                System.err.println("error: class: AsignarDiasMaquinaAPedidoModel, Method:listaOrdenesNuevaPlaneacion "+e.getMessage());
            }
        
            return lista;
    }
    
    public JComboBox llenarCombo(JComboBox combo,int tipoLista,String codProducto){
        
        switch (tipoLista) {
            case LISTA_MAQUINAS:
                    return  Estructuras.llenaCombo(combo, "SELECT desc_maquina FROM maquinas;");
            case LISTA_PROCESOS:
                    return Estructuras.llenaCombo(combo,"SELECT desc_tipo_proceso FROM ver_productos_maquinas WHERE clave_producto = '"+codProducto+"'"
                            + " AND desc_maquina IS NOT NULL AND desc_tipo_proceso <> 'Maquinado' GROUP BY desc_tipo_proceso;");       
            default:
                return combo;
        }
        
    }

    public ProductoMaquina obtenerMaquinaUtilizada(String codProducto) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "SELECT pm.piezas_por_turno,pm.desc_maquina,id_producto_maquina,pm.desc_tipo_proceso " +
                               "FROM (SELECT * FROM todos_lotes_planeados GROUP BY id_orden_produccion) AS lp " +
                               "INNER JOIN ver_productos_maquinas AS pm ON pm.desc_maquina = lp.desc_maquina " +
                               "WHERE pm.clave_producto = '"+codProducto+"' AND lp.desc_tipo_proceso = pm.desc_tipo_proceso GROUP BY lp.desc_maquina;";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first()){
                    ProductoMaquina productoMaquina = new ProductoMaquina();
                    productoMaquina.setPiezasPorTurno(rs.getInt(1));
                    productoMaquina.setDescMaquina(rs.getString(2));
                    productoMaquina.setNoProductoMaquina(rs.getInt(3));
                    productoMaquina.setDescTipoProceso(rs.getString(4));
                    return productoMaquina;
                }
                c.close();
            } catch (SQLException e) {
                System.err.println("error: class: AsignarDiasMaquinaAPedidoModel, Method:obtenerMaquinaUtilizar "+e.getMessage());
            }
        
        
        return null;
    }

    public ArrayList<ProductoMaquina> obtenerMaquinasProducto(String codProducto,String tipoProceso) {
        ArrayList<ProductoMaquina> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "SELECT piezas_por_turno,desc_maquina,id_producto_maquina,desc_tipo_proceso "
                        + "FROM ver_productos_maquinas WHERE clave_producto = '"+codProducto+"'"
                        + " AND desc_tipo_proceso = '"+tipoProceso+"' ;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        ProductoMaquina productoMaquina = new ProductoMaquina();
                        productoMaquina.setPiezasPorTurno(rs.getInt(1));
                        productoMaquina.setDescMaquina(rs.getString(2));
                        productoMaquina.setNoProductoMaquina(rs.getInt(3));
                        productoMaquina.setDescTipoProceso(tipoProceso);
                       lista.add(productoMaquina);
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {    
                System.err.println("error: class: AsignarDiasMaquinaAPedidoModel, Method:obtenerMaquinasProducto "+e.getMessage());
            }
        return lista;
    }

    public void registrarNuevaPlaneacion(OrdenProduccionNuevaPlaneacion nuevaPlaneacion) {
        Connection c = Conexion.getInstance().getConexion();
        if( c != null)
            try {
                
                String query = "{CALL registrar_nueva_planeacion(?,?,?,?,?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, nuevaPlaneacion.getNoOrdenProduccion());
                cs.setInt(2,nuevaPlaneacion.getCantidadPlaneada());
                cs.setInt(3,nuevaPlaneacion.getPiezasPorTurno());
                cs.setString(4, nuevaPlaneacion.getFechaPlaneada());
                cs.setFloat(5, nuevaPlaneacion.getWorker());
                cs.setString(6, nuevaPlaneacion.getProductoMaquinaSeleccionado().getDescMaquina());
                cs.setString(7, nuevaPlaneacion.getProductoMaquinaSeleccionado().getDescTipoProceso());
                cs.setInt(8, nuevaPlaneacion.getDiasTrabajo());
                cs.registerOutParameter(9, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(9));
               
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: class: AsignarDiasMaquinaAPedidoModel, Method:registrarNuevaPlaneacion "+e.getMessage());
            }
    }

    public int obtenerPiezasListas(int noOrdenProduccion, String tipoProceso) {
        Connection c = Conexion.getInstance().getConexion();
        int resultado=0;
        if(c!=null)
            try {
                
                String query = "SELECT obtener_piezas_procesar("+noOrdenProduccion+",'"+tipoProceso+"');";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    resultado = rs.getInt(1);
            } catch (SQLException e) {
                System.err.println("error: class: AsignarDiasMaquinaAPedidoModel, Method:obtenerPiezasLista "+e.getMessage());

            }
        return resultado;
    }
    
    public class OrdenProduccionNuevaPlaneacion extends ProductoPendiente{
     
        private int piezasParaProcesar;
        private int piezasListas;
        

        private OrdenProduccionNuevaPlaneacion() {
            
            
        }

        public int getPiezasListas() {
            return piezasListas;
        }

        public void setPiezasListas(int piezasListas) {
            this.piezasListas = piezasListas;
        }

        public void setPiezasParaProcesar(int piezasParaProcesar) {
            this.piezasParaProcesar = piezasParaProcesar;
        }

        public int getPiezasParaProcesar() {
            return piezasParaProcesar;
        }
        
    }
    
}
