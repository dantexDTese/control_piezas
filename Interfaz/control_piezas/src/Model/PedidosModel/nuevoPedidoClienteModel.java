
package Model.PedidosModel;


import Model.Conexion;
import Model.Estructuras;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class nuevoPedidoClienteModel {
    
    
    
    public ArrayList listaClientes(){
        
        return Estructuras.obtenerlistaDatos("SELECT nombre_cliente FROM clientes");
        
    }
    
    
    public ArrayList<String> listaContactosCliente(String nombreCliente){
        return Estructuras.obtenerlistaDatos(" SELECT desc_contacto FROM clientes AS cl INNER JOIN contactos "
                + " AS cn ON cl.id_cliente = cn.id_cliente WHERE cl.nombre_cliente =  '"+nombreCliente+"'; ");
    }
    
    
    
    public ArrayList<String> listaProductos(){
        return Estructuras.obtenerlistaDatos(" SELECT clave_producto FROM productos AS pr INNER JOIN productos_maquinas AS pm " +
                                           " ON pm.id_producto = pr.id_producto WHERE pm.id_maquina IS NOT NULL " +
                                           " AND pm.id_tipo_proceso = "
                                         + " (SELECT id_tipo_proceso FROM tipos_proceso WHERE desc_tipo_proceso = 'Maquinado') " +
                                           " GROUP BY clave_producto; ");
    }
    
    public int agregarPedido(String descOrdenCompra, String descCliente,String descContacto,String fechaEntrega){   
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call agregar_pedido(?,?,?,?,?,?)}";
        int res=0;
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1,descOrdenCompra);
                cs.setString(2,descCliente);
                cs.setString(3,descContacto);
                cs.setString(4,fechaEntrega);
                cs.registerOutParameter(5,Types.VARCHAR);
                cs.registerOutParameter(6,Types.INTEGER);
                cs.execute();
                JOptionPane.showMessageDialog(null,cs.getString(5));
                res = cs.getInt(6);
                c.close();
                        
            } catch (HeadlessException | SQLException e) {
                System.err.println("error clase:NuevoPedidoClienteModel method:agregarPedido :"+e.getMessage());
            }
                
        return res;
    }
    
    
    
    
    public void agregarOrdenProduccion(int idPedido,String descProducto,int cantidadCliente){
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call agregar_orden_produccion(?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, idPedido);
                cs.setString(2, descProducto);
                cs.setInt(3, cantidadCliente);
                cs.execute();
                cs.close();               
            } catch (SQLException e) {
                System.err.println("error clase: nuevoPedidoClienteModel,"
                        + " method: agregarOrdenProduccion:"+e.getMessage());
            }
    }
    
}
