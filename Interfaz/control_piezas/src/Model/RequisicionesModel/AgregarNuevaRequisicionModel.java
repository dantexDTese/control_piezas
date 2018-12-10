
package Model.RequisicionesModel;

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

/**
 *
 * @author cesar
 */
public class AgregarNuevaRequisicionModel {
    
    
    
    public ArrayList<Pedido> listaOrdenesPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "select id_orden_trabajo AS num_pedido,no_orden_compra"
                + " FROM requisicion_ordenes WHERE desc_estado = 'REQUISICION'";  
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                       pedidos.add(new Pedido(rs.getString(2),rs.getInt(1)));
                    }while(rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error class:AgregarNuevaRequisicionesModel method:listaOrdenesPendientes "+e.getMessage());
            }
        return pedidos;       
    }
    
    public ArrayList<ProductosPendientes> listaProductosPendientes(int noOrdenTrabajo){
        ArrayList<ProductosPendientes> productosPendientes = new ArrayList<>();
        String query =  "select ro.id_orden_produccion,ro.clave_producto,ro.cantidad_total,ro.desc_material,"
                + "ro.barras_necesarias,op.fecha_inicio FROM requisicion_ordenes AS ro " +
                "JOIN ordenes_produccion AS op ON op.id_orden_produccion = ro.id_orden_produccion "
                + "WHERE ro.id_orden_trabajo = "+noOrdenTrabajo+";";
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        productosPendientes.add(new ProductosPendientes(
                                rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getFloat(5),rs.getString(6)));
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
                cs.setString(3,material.getMaterial());
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
