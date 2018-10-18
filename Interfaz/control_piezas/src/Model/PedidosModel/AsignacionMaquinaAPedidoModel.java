
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
    
    

    public String agregarProductoPendiente(ProductosPendientes pendiente) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call agregar_orden_maquina(?,?,?,?,?,?,?,?,?,?)}";
        String res=null;
        if(c!=null)
            try {
                JOptionPane.showMessageDialog(null, pendiente.getPiecesByShift());
                
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
                cs.registerOutParameter(10, Types.VARCHAR);
                cs.execute();
                res = cs.getString(10);
                c.close();
            } catch (Exception e) {
                System.err.println("error: class: AsignacionMaquinaAPedidoModel metohd:agregarProductoPendiente "+e.getMessage() );
            }
        return res;
    }

}
