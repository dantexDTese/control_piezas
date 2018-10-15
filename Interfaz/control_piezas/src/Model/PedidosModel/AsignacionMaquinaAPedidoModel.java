
package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class AsignacionMaquinaAPedidoModel {

    public AsignacionMaquinaAPedidoModel() {
     
    }
    
    public ArrayList<String> listaMaquinas(){
        return Estructuras.obtenerlistaDatos("SELECT desc_maquina FROM maquinas");
    }
    
    public ArrayList<Pedido> listaPedidosPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM PedidosPendientes");
                if(rs.first())
                    do {                        
                        Pedido pedido = new Pedido(rs.getString(1),rs.getInt(2),rs.getString(3));
                        pedidos.add(pedido);
                    } while (rs.next());
               c.close(); 
            } catch (Exception e) {
                System.err.println("error: class: PlaneacionModel, Method:listaPedidosPendientes"+e.getMessage());
            }
        
        return pedidos;
    }
    
    
    public ArrayList<ProductosPendientes> listaProductosPendientes(String noOrdenTrabajo){
        ArrayList<ProductosPendientes> listaProductos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {       
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM productosEnEspera "
                        + "WHERE id_orden_trabajo = '"+noOrdenTrabajo+"'");
                if(rs.first())
                    do {                        
                        listaProductos.add(new ProductosPendientes(rs.getString(1),
                                rs.getInt(2), rs.getString(3), rs.getInt(4)));                        
                    } while (rs.next());
                
            } catch (Exception e) {
                System.err.println("mensaje: class:AsignacionMaquinaAPedido"
                        + "Method:listaProductosPendientes"+e.getMessage());
            }
        return listaProductos;        
    }
    
    public ArrayList<String> listaMateriales(){
        return Estructuras.obtenerlistaDatos("SELECT desc_material FROM materiales");
    }
    
    
    public void agregarOrdenMaquina(ordenPlaneada ordenModificada){
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call agregar_orden_maquina(?,?,?,?,?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1,ordenModificada.getNoOrdenProduccion());
                cs.setString(2,ordenModificada.getDescProducto());
                cs.setFloat(3,ordenModificada.getNuevo_worker());
                cs.setInt(4,ordenModificada.getNueva_cantidad_total());
                cs.setString(5,ordenModificada.getDesc_maquina());
                cs.setString(6,ordenModificada.getDesc_material());
                cs.registerOutParameter(7,Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(7));
                
            } catch (Exception e) {
                System.err.println("error : class: AsignacionMaquinaAPedidoModel, Method: agregarOrdenMaquina "
                +e.getMessage());                
            }
        
    }

}
