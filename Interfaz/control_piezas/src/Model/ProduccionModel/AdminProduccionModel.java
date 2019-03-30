package Model.ProduccionModel;

import Model.Conexion;
import Model.LotePlaneado;
import Model.OrdenTrabajo;
import Model.ordenProduccion;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AdminProduccionModel {
               
    public ArrayList<OrdenTrabajo> listaOrdenesTrabajo(String descOrden,int anio,int mes){
         ArrayList<OrdenTrabajo> lista = new ArrayList<>();
         Connection c = Conexion.getInstance().getConexion();
         String query;
         if("".equals(descOrden))
            query = "SELECT id_pedido,no_orden_compra,desc_estado FROM "
                    + "todos_pedidos WHERE MONTH(fecha_recepcion) = "+mes+"  AND YEAR(fecha_recepcion) = "+anio+";";
         else
             query = "SELECT id_pedido,no_orden_compra,desc_estado FROM todos_pedidos "
                     +"WHERE no_orden_compra = '"+descOrden+"' AND "
                     + "MONTH(fecha_recepcion) = "+mes+"  AND YEAR(fecha_recepcion) = "+anio+";;";
         
         if(c!=null)
             try {                 
                 Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery(query);
                 if(rs.first())
                     do{
                         OrdenTrabajo pedido = new OrdenTrabajo();
                         pedido.setNoPedido(rs.getInt(1));
                         pedido.setNoOrdenCompra(rs.getString(2));
                         pedido.setDescEstadoPedido(rs.getString(3));
                         
                         lista.add(pedido);
                     }while(rs.next());
                 c.close();
             } catch (SQLException e) {
                 System.err.println("error: clase AdminProduccionModel "
                         + "method: llstaOrdenesTrabajo "+e.getMessage());
             }
         return lista;
     }       
          
    public ArrayList<ordenProduccion> listaOrdenesProduccion(int noOrdenTrabajo){
         ArrayList<ordenProduccion> lista = new ArrayList<>();
         Connection c = Conexion.getInstance().getConexion();
         String query = "SELECT id_orden_produccion,clave_producto FROM todas_ordenes_produccion "
                        +"WHERE id_orden_trabajo = "+noOrdenTrabajo+" GROUP BY id_orden_produccion ;";
         if(c!=null)
             try {                 
                 Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery(query);
                 if(rs.first())
                     do{
                         ordenProduccion orden = new ordenProduccion();
                         orden.setNoOrdenProduccion(rs.getInt(1));
                         orden.setCodProducto(rs.getString(2));
                         lista.add(orden);
                     }while(rs.next());
                 c.close();
             } catch (SQLException e) {
                 System.err.println("error: clase AdminProduccionModel "
                         + "method: llstaOrdenesProduccion "+e.getMessage());
             }
         
         return lista;         
     }
    
    public LotePlaneado obtenerOrdenProduccion(int noOrdenProduccion){
        LotePlaneado orden = null;
        Connection c = Conexion.getInstance().getConexion();
        String query = " SELECT pd.id_orden_trabajo,pd.no_orden_compra,pd.fecha_entrega AS fecha_entrega_pedido, "
                + " pd.fecha_confirmacion_entrega AS fecha_confirmacion_entrega_pedido, " +
                " pd.fecha_recepcion AS fecha_recepcion_pedido,pd.desc_contacto,pd.nombre_cliente,op.clave_producto, "
                + " op.cantidad_total,op.id_orden_produccion,op.desc_tipo_material,op.desc_dimencion, " +
                " op.clave_forma,op.piezas_turno_registro,op.turnos_necesarios,op.fecha_registro AS fecha_registro_op, "
                + " op.fecha_montaje,op.fecha_desmontaje,op.fecha_inicio AS fecha_inicio_op, " +
                " op.fecha_fin AS fecha_fin_op,lp.desc_maquina,mo.barras_necesarias,"
                + "validacion_compras,validacion_produccion,validacion_matenimiento,validacion_calidad,op.desc_estado,"
                + "desc_empaque " +
                " FROM todos_pedidos AS pd " +
                " INNER JOIN todas_ordenes_produccion AS op ON pd.id_orden_trabajo = op.id_orden_trabajo " +
                " INNER JOIN todos_lotes_planeados AS lp ON lp.id_orden_produccion = op.id_orden_produccion " +
                " INNER JOIN materiales_orden AS mo ON mo.id_orden_produccion = op.id_orden_produccion "
                + "WHERE op.id_orden_produccion = " + noOrdenProduccion +
                " GROUP BY op.id_orden_produccion; ";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first()){
                        
                       orden = new LotePlaneado();
                       
                       orden.setOrdenTrabajo(rs.getInt(1));
                       orden.setNoOrdenCompra(rs.getString(2));
                       orden.setFechaentrega(rs.getString(3));
                       orden.setFechaConfirmacionEntrega(rs.getString(4));
                       orden.setFechaRecepcion(rs.getString(5));
                       orden.setDescContacto(rs.getString(6));
                       orden.setDescCliente(rs.getString(7));
                       orden.setCodProducto(rs.getString(8));
                       orden.setCantidadTotal(rs.getInt(9));
                       orden.setNoOrdenProduccion(rs.getInt(10));
                       orden.setDescTipoMaterial(rs.getString(11));
                       orden.setDescDimencion(rs.getString(12));
                       orden.setClaveForma(rs.getString(13));
                       orden.setPiezasPorTurno(rs.getInt(14));
                       orden.setTurnosNecesarios(rs.getInt(15));
                       orden.setFechaRegistro(rs.getString(16));
                       orden.setFechaMontaje(rs.getString(17));
                       orden.setFechaDesmontaje(rs.getString(18));
                       orden.setFechaInicio(rs.getString(19));
                       orden.setFechaFin(rs.getString(20));
                       orden.setDescMaquina(rs.getString(21));
                       orden.setBarrasNecesarias(rs.getFloat(22));
                       orden.setValidacion_compras(rs.getBoolean(23));
                       orden.setValidacion_produccion(rs.getBoolean(24));
                       orden.setValidacion_matenimiento(rs.getBoolean(25));
                       orden.setValidacion_calidad(rs.getBoolean(26));
                       orden.setDescEstadoOrdenProduccion(rs.getString(27));
                       orden.setDescEmpaque(rs.getString(28));
                }
 
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel class: AdminProduccionModel Method:obtenerOrdenProduccion "+e.getMessage());
            }
        return orden;
    }

    public void cerrarOrdenProduccion(int noOrdenProduccion){
        Connection c = Conexion.getInstance().getConexion();
        if(c!= null)
            try {
                String query = "{CALL cerrar_orden_produccion(?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noOrdenProduccion);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(2));
                c.close();
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:ProduccionModel class: AdminProduccionModel Method:cerrarOrdenProduccion "+e.getMessage());
            }
    }

    public void guardarModificaciones(LotePlaneado ordenSeleccionada){
        
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL hacer_modificaciones_op(?,?,?,?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, ordenSeleccionada.getNoOrdenProduccion());
                cs.setString(2, ordenSeleccionada.getDescEmpaque());
                cs.setString(3, ordenSeleccionada.getFechaDesmontaje());
                cs.setBoolean(4, ordenSeleccionada.isValidacion_compras());
                cs.setBoolean(5, ordenSeleccionada.isValidacion_produccion());
                cs.setBoolean(6, ordenSeleccionada.isValidacion_matenimiento());
                cs.setBoolean(7,ordenSeleccionada.isValidacion_calidad());
                cs.registerOutParameter(8, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(8),"RESPUETA",JOptionPane.INFORMATION_MESSAGE);
                
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:ProduccionModel class: AdminProduccionModel "
                        + "Method:guardarModificaciones "+e.getMessage());
            }
        
    }

        
}
