
package Model.RequisicionesModel;

import Model.Conexion;
import Model.Pedido;
import Model.ordenProduccion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public class AgregarNuevaRequisicionModel {
    
    public ArrayList<Pedido> listaOrdenesPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT 	pd.id_pedido,pd.no_orden_compra,op.id_orden_produccion FROM ordenes_produccion AS op " +
                        "JOIN ordenes_trabajo AS ot ON ot.id_orden_trabajo = op.id_orden_trabajo " +
                        "JOIN pedidos AS pd ON pd.id_pedido = ot.id_pedido " +
                        "JOIN materiales_orden AS mo ON op.id_orden_produccion = mo.id_orden_produccion " +
                        "JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido " +
                        "JOIN ver_materiales AS mt ON mt.id_material = mr.id_material " +
                        "WHERE mo.barras_necesarias > obtener_suma_materiales(op.id_orden_produccion) " +
                        "GROUP BY pd.id_pedido;";          
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        Pedido pedido = new Pedido();
                        pedido.setNoPedido(rs.getInt(1));
                        pedido.setNoOrdenCompra(rs.getString(2));
                       pedidos.add(pedido);
                    }while(rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error class:AgregarNuevaRequisicionesModel method:listaOrdenesPendientes "+e.getMessage());
            }
        return pedidos;       
    }
    
    public ArrayList<ordenProduccion> listaProductosPendientes(int noOrdenTrabajo){
        ArrayList<ordenProduccion> productosPendientes = new ArrayList<>();
        String query =  "select op.id_orden_produccion,op.clave_producto,op.cantidad_total,mt.desc_tipo_material,"
                + "mt.desc_dimencion,mt.clave_forma,mo.barras_necesarias,obtener_fecha_siguiente_lote_planeado(op.id_orden_produccion)"
                + ",mt.id_material,mo.barras_necesarias - obtener_suma_materiales(op.id_orden_produccion) AS piezas_faltantes "
              + "FROM todas_ordenes_produccion AS op " +
                "JOIN materiales_orden AS mo ON op.id_orden_produccion = mo.id_orden_produccion " +
                "JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido " +
                "JOIN ver_materiales AS mt ON mt.id_material = mr.id_material " +
                "WHERE mo.barras_necesarias > obtener_suma_materiales(op.id_orden_produccion) AND op.id_orden_trabajo = '"+noOrdenTrabajo+"' " +
                "GROUP BY id_orden_produccion;";
        
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {         
                        ordenProduccion orden = new ordenProduccion(rs.getInt(1));
                        orden.setCodProducto(rs.getString(2));
                        orden.setCantidadTotal(rs.getInt(3));
                        orden.setDescTipoMaterial(rs.getString(4));
                        orden.setDescDimencion(rs.getString(5));
                        orden.setClaveForma(rs.getString(6));
                        orden.setBarrasNecesarias(rs.getFloat(7));
                        orden.setFechaInicio(rs.getString(8));
                        orden.setNoMaterial(rs.getInt(9));
                        orden.setPiezasFaltantes(rs.getFloat(10));
                        productosPendientes.add(orden);
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: AgregarRequisicionesModel method:listaProductosPendientes "+e.getMessage());
            }
        return productosPendientes;
    }
    
    public int agregarRequisicion(String solicitante,String usoMaterial){
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL agregar_requisicion(?,?,?)}";
        int idRequisicion=0;
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1,solicitante);
                cs.setString(2, usoMaterial);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                idRequisicion = cs.getInt(3);
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete:requisicionesModel class:agregarNuevaRequisicion metodo:agregarRequisicion "+e.getMessage());
            }
        return idRequisicion;
    }

    public int agregarMaterialSolicitado(int idRequisicion,ParcialidadMaterial material){
        Connection c = Conexion.getInstance().getConexion();
        int idMaterialSolicitado=0;
        String query = "{CALL agregar_materiales_solicitados(?,?,?,?,?,?,?,?,?)}";
        if(c!=null)
            try {
                
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1,idRequisicion);
                cs.setInt(2, material.getNoPartida());
                cs.setInt(3,material.getNoMaterial());
                cs.setInt(4, material.getCantidad());
                cs.setString(5, material.getUnidad());
                cs.setInt(6,material.getNoParcialidad());
                cs.setString(7, material.getFechaSolicitadaParcialidadMaterial());
                cs.setString(8, material.getCuentaCargo());
                cs.registerOutParameter(9, Types.INTEGER);
                cs.execute();
                idMaterialSolicitado = cs.getInt(9);
                
            } catch (SQLException e) {
                System.err.println("error: paquete:requisicionesModel class:agregarNuevaRequisicion metodo:agregarMaterialSolicitado "+e.getMessage());
            }
        return idMaterialSolicitado;
    }
   
    public void agregarMaterialesOrdenRequisicion(int idMaterialSolicitado,int noOrdenProduccion,int cantidad){
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL agregar_materiales_orden_requisicion(?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, idMaterialSolicitado);
                cs.setInt(2, noOrdenProduccion);
                cs.setInt(3, cantidad);
                cs.execute();                
            } catch (SQLException e) {
                System.err.println("error: paquete:requisicionesModel class:agregarNuevaRequisicion metodo:agregarMaterialesOrdenRequisicion "+e.getMessage());
            }
    }
}
