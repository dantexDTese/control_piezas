
package Model.AlmacenModel;

import Model.AlmacenProductoTerminado;
import Model.Conexion;
import Model.Estructuras;
import Model.ProductoCliente;
import Model.RegistroEntradaSalida;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;





public class ProductoTerminadoModel {

    public final int LISTA_CLIENTES=1;
    public final int LISTA_PRODUCTOS = 2;
    
    public JComboBox llenarCombo(JComboBox combo,String nombreCliente, int numLista){
        
        combo.removeAllItems();
        combo.addItem("cualquiera");
        
        switch(numLista){
            case LISTA_CLIENTES:
                combo = Estructuras.llenaCombo(combo, " SELECT nombre_cliente FROM productos_clientes GROUP BY nombre_cliente");
                break;
            case LISTA_PRODUCTOS:
                String query = "SELECT clave_producto FROM productos_clientes ";
                if(!"cualquiera".equals(nombreCliente))
                    query += "WHERE nombre_cliente = '"+nombreCliente+"'";
                combo = Estructuras.llenaCombo(combo,query);
                break;
        }
       
       return combo;
    }
        
    public ArrayList<AlmacenProductoTerminado> listaMaterialesClientes(String nomCliente,String descMaterial){
        ArrayList<AlmacenProductoTerminado> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = queryMaterialesCliente(nomCliente, descMaterial);
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);           
                if(rs.first())
                    do {                        
                        lista.add(new AlmacenProductoTerminado(rs.getInt(4),rs.getInt(1), rs.getString(2), rs.getString(3)));
                    } while (rs.next());            
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, Class:ProductoTerminadoModel, metodo:listaMaterialesClientes "+e.getMessage());
            }
        return lista;
    }
    
    private String queryMaterialesCliente(String nomCliente,String claveProducto){
        String query = "SELECT * FROM productos_clientes ";
        
        if(!"cualquiera".equals(nomCliente) && "cualquiera".equals(claveProducto)){
            query += "WHERE nombre_cliente = '" +nomCliente + "'";
        }
        
        else if("cualquiera".equals(nomCliente) && !"cualquiera".equals(claveProducto)){
            query += "WHERE clave_producto = '" + claveProducto + "'";
        }
        
        else if(!"cualquiera".equals(nomCliente) && !"cualquiera".equals(claveProducto)){
            query += "WHERE nombre_cliente = '" +nomCliente + "' AND clave_producto = '"+claveProducto+"'";
        }
        
        return query;
    }
 
    public ArrayList<RegistroEntradaSalida> listaRegistrosEntradasSalidas(int noAlmacenProductoTerminado) {
        ArrayList<RegistroEntradaSalida> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM entradas_salidas WHERE id_almacen_producto_terminado = "+noAlmacenProductoTerminado+";";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next())
                    do {                        
                      lista.add(new RegistroEntradaSalida(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6)));
                    } while (rs.next());
               
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, Class:ProductoTerminadoModel, metodo:listaRegistroEntrdasSalidas "+e.getMessage());

            }
        
        return lista;
    }

    public void registrarEntrada(int cantidadRegistrar, int noAlmacenProductoTerminado) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL registrar_entrada_almacen(?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, cantidadRegistrar);
                cs.setInt(2,noAlmacenProductoTerminado);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
            } catch (HeadlessException | SQLException e) {
                    System.err.println("error: paquete:AlmacenModel, Class:ProductoTerminadoModel, metodo:registrarEntrada "+e.getMessage());
            }
    }
    
}
