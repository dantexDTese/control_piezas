
package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class ordenCompraModel {
        
    public static final String QUERY_COMBO_CLIENTE = "SELECT nombre_cliente FROM clientes";
    
    
    public ordenCompraModel(){        
    
     
        
    }
    
    
    public void insertarOrdenCompra(String descOrdenCopra,String nomCliente){
        Connection c = Conexion.getInstance().getConexion();
        final String llamadaProcedimiento = "{Call agregar_orden_compra (?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(llamadaProcedimiento);
                cs.setString(1, descOrdenCopra);
                cs.setString(2, nomCliente);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));                
                c.close();
            } catch (Exception e) {
                System.err.println("error:"+e.getMessage());
            }        
    }
    
    
    public void insertarCliente(String nombreCliente){
        Connection c = Conexion.getInstance().getConexion();
        final String llamadaProcedimiento = "{Call agregar_cliente (?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(llamadaProcedimiento);
                cs.setString(1, nombreCliente);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(2));                
                c.close();
            } catch (Exception e) {
                System.err.println("error:"+e.getMessage());
            }        
    }
    
    public JComboBox llenarComboOrdenCompra(JComboBox selector,String nomCliente){
        final String query = String.format("SELECT desc_orden_compra FROM ordenes_compra " +
        "JOIN clientes ON clientes.id_cliente = ordenes_compra.id_cliente WHERE nombre_cliente = '%s'",nomCliente);
        selector.removeAllItems();
        selector = Estructuras.llenaCombo(selector, query);
        return selector;
    }    
    
    
        
}
