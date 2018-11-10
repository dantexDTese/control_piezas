
package Model.RequisicionesModel;

import Model.Conexion;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AgregarRequisicionesModel {
 
    
    public ArrayList<Pedido> listaOrdenesPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "select id_orden_trabajo,no_orden_compra "
                + "from requisicion_ordenes WHERE desc_estados = 'ABIERTO' "
                + "GROUP BY id_orden_trabajo;";        
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
                System.err.println("error class:AgregarRequisicionesModel method:listaOrdenesPendientes "+e.getMessage());
            }
        return pedidos;       
    }
    
    public ArrayList<ProductosPendientes> listaProductosPendientes(int noOrdenTrabajo){
        ArrayList<ProductosPendientes> productosPendientes = new ArrayList<>();
        String query = "select clave_producto,cantidad_total,desc_material FROM"
                        + " requisicion_ordenes AS ro WHERE ro.id_orden_trabajo = "+noOrdenTrabajo+";";
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        productosPendientes.add(new ProductosPendientes(rs.getString(1),rs.getInt(2),rs.getString(3))); 
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: AgregarRequisicionesModel method:listaProductosPendientes "+e.getMessage());
                
            }
        return productosPendientes;
    }  
    
    public ArrayList<MaterialesRequisicion> listaMaterialesRequeridos(int noOrdenTrabajo){
        ArrayList<MaterialesRequisicion> materialesRequeridos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "select cantidad_total,desc_material " +
                        "FROM materiales_requeridos AS mr JOIN materiales AS mt ON mt.id_material = mr.id_material " +
                        "WHERE id_requisicion = (SELECT id_requisicion FROM requisiciones WHERE id_orden_trabajo = "+noOrdenTrabajo+");";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        materialesRequeridos.add(new MaterialesRequisicion(rs.getInt(1),rs.getString(2)));
                    } while (rs.next());

              c.close();
            } catch (SQLException e) {
                System.err.println("error: class: AgregarRequisicionesModel method:listaMaterialesRequeridos "+e.getMessage());            
            }
        
        return materialesRequeridos;
    }
    
    public ArrayList<Proveedores> listaProveedores(){
        ArrayList<Proveedores> proveedores = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM proveedores";
        if(c!= null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        proveedores.add(new Proveedores(rs.getInt(1),rs.getString(2),rs.getString(3)));
                    } while (rs.next());
                    c.close();
            } catch (SQLException e) {
                System.err.println("error: class:AgregarRequisicionesModel method: listaProveedores "+e.getMessage());
            }
                
            
            return proveedores;
    }

    public int obtenerParcialidad(String material,int noOrdenSeleccionada) {
        int parcialidad=0;
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT num_parcialidad from parcialidades_orden_requerida as por WHERE " +
                        "por.id_material_requerido = (SELECT cantidad_total FROM materiales_requeridos AS mr " +
                        "WHERE mr.id_requisicion = (SELECT id_requisicion FROM requisiciones AS rq WHERE rq.id_orden_trabajo = "+noOrdenSeleccionada+")" +
                        "AND mr.id_material = (SELECT id_material FROM materiales AS mt WHERE mt.desc_material = '"+material+"'));";
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    parcialidad = rs.getInt(1);
            } catch (SQLException e) {
                System.err.println("error: class:AgregarRequisicionModel method:obtenerParcialidad "+e.getMessage());
            }
        return parcialidad;
    }
    
}
