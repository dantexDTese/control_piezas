
package Model.PedidosModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BitacoraPedidosClienteModel {
    
    public ArrayList<Pedido> listaPedidos(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM bitacoraPedidos";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do{
                        pedidos.add(new Pedido(
                                rs.getInt(1),       //ordenTrabajo
                                rs.getString(2),    //ordenCompra
                                rs.getString(3),    //claveProducto
                                rs.getString(4),    //fechaEntrega
                                rs.getString(5),    //fechaConfirmacionEntrega
                                rs.getString(6),    //fechaRecepcion
                                rs.getString(7),    //estado
                                rs.getString(8),    //contacto
                                rs.getString(9),    //cliente
                                rs.getInt(10)));    //cantidad
                    }while(rs.next());
            } catch (Exception e) {
                System.err.println("error: class: nuevoPedidoClientes,"
                        + "method:listaPedidos"+e.getMessage());
            }
            
        return pedidos;
    }

}
