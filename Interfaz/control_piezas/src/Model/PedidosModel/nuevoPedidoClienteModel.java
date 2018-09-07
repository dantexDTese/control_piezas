
package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import Model.productoModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class nuevoPedidoClienteModel {
    
    
    
    public ArrayList listaClientes(){
        return Estructuras.obtenerlistaDatos("SELECT nombre_cliente FROM clientes");
    }
    
    
    public ArrayList listaContacto(String nombreCliente){
        Connection c = Conexion.getInstance().getConexion();
        int idCliente;
        ArrayList listaContactos = new ArrayList();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(
                        String.format("select id_cliente FROM clientes "
                                + "WHERE nombre_cliente = '%s'", nombreCliente));
                
                if(rs.first()){
                    idCliente = rs.getInt(1);
                    listaContactos =  Estructuras.obtenerlistaDatos(
                        String.format("SELECT desc_contacto FROM contactos WHERE "
                                + "id_cliente = %s", idCliente));
                }
                
                c.close();
            } catch (Exception e) {
                System.err.println("error class:nuevoPedidoClienteModel,"
                        + "method:listaContacto->"+e.getMessage());
            }
        
        return listaContactos;
    }
    
    public ArrayList<productoModel> listaProductos(){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<productoModel> productos = new ArrayList<>();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT clave_producto FROM productos");
                if(rs.first())
                    do{
                        productoModel producto = new productoModel(rs.getString(1));
                        productos.add(producto);
                    }while(rs.next());
                
                c.close();
            } catch (Exception e) {
                System.err.print("Error class:nuevoPedidoClienteModel,"
                        + "method: listaProductos "+e.getMessage());
            }
        
        return productos;
    }
    
    public int agregarPedido(String descOrdenCompra, String descCliente,
                                String descContacto,String fechaEntrega){
        
        Connection c = Conexion.getInstance().getConexion();
        String query = "{Call agregar_pedido (?,?,?,?,?,?,?,?)";
        int res=0;
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(descCliente);
                cs.setString(1,descOrdenCompra);
                cs.setString(2,descCliente);
                cs.setString(3,descContacto);
                cs.setString(4,fechaEntrega);
                cs.registerOutParameter(5,Types.VARCHAR);
                cs.registerOutParameter(6,Types.INTEGER);
                cs.execute();
                JOptionPane.showMessageDialog(null,cs.getString(5));
                res = cs.getInt(6);
            } catch (Exception e) {
                System.err.println("error clase:NuevoPedidoClienteModel method:agregarPedido :"+e.getMessage());
            }
                
        return res;
    }
    
}
