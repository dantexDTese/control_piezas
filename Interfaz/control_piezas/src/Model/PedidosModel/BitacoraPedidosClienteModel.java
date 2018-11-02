
package Model.PedidosModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ComboBoxModel;


public class BitacoraPedidosClienteModel {
    
    /*todo bien*/
    
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
                        pedidos.add(new Pedido(rs.getInt(1), rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9),
                        rs.getString(10)));
                    }while(rs.next());
            } catch (Exception e) {
                System.err.println("error: class: nuevoPedidoClientes,"
                        + "method:listaPedidos"+e.getMessage());
            }
            
        return pedidos;
    }

}
