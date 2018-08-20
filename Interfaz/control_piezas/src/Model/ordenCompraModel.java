
package Model;

import ds.desktop.notify.DesktopNotify;
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
        final String llamadaProcedimiento = "{Call agregar_orden_trabajo (?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(llamadaProcedimiento);
                cs.setString(1, descOrdenCopra);
                cs.setString(2, nomCliente);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                DesktopNotify.showDesktopMessage("orden completada", cs.getString(3),DesktopNotify.SUCCESS,120);
                c.close();
            } catch (Exception e) {
                System.err.println("error:"+e.getMessage());
            }        
    }
    
    public void insertarOrdenProducto(ordenProducto nuevaOrden,String descOrdenTrabajo){
        Connection c = Conexion.getInstance().getConexion();
        final String llamaProcudimiento = "{Call agregar_ordenes_produccion(?,?,?,?,?,?,?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(llamaProcudimiento);
                cs.setString(1,descOrdenTrabajo);
                cs.setString(2, nuevaOrden.getCodProducto());
                cs.setInt(3, nuevaOrden.getCantidadProducir());
                cs.setString(4, nuevaOrden.getDescMaquina());
                cs.setString(5, nuevaOrden.getDescMateria());
                cs.setInt(6,nuevaOrden.getCantidadSolicitada());
                cs.setInt(7,nuevaOrden.getCantidadPorTurno());
                
                if(nuevaOrden.getFecha().equals(""))
                    cs.setDate("fecha_montaje",null);
                else
                cs.setString(8,nuevaOrden.getFecha());
                
                cs.setInt(9,nuevaOrden.getBarrasNecesarias());
                cs.execute();               
                cs.close();
            } catch (Exception e) {
                System.err.println("error clase: ordenCompraModel, metodo: insertarOrdenProducto ->"+e.getMessage());
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
        final String query = String.format("SELECT desc_orden_trabajo FROM ordenes_trabajo " +
        "JOIN clientes ON clientes.id_cliente = ordenes_trabajo.id_cliente WHERE nombre_cliente = '%s'"
        ,nomCliente);  
        selector.removeAllItems();
        selector = Estructuras.llenaCombo(selector, query);
        return selector;
    }    
    
    
        
}
