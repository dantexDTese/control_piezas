
package Model.AlmacenModel;

import Model.AlmacenProductoTerminado;
import Model.Conexion;
import Model.Estructuras;
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


    public JComboBox llenarCombo(JComboBox combo){
        combo.removeAllItems();
        String query = "SELECT clave_producto FROM productos_clientes ";
        combo = Estructuras.llenaCombo(combo,query);
       return combo;
    }
        
    public ArrayList<AlmacenProductoTerminado> listaMaterialesClientes(){
        ArrayList<AlmacenProductoTerminado> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT clave_producto,id_almacen_producto_terminado,total FROM productos_almacen ";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);           
                if(rs.first())
                    do {                        
                        lista.add(new AlmacenProductoTerminado(rs.getInt(2),rs.getInt(3), rs.getString(1)));
                    } while (rs.next());            
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, Class:ProductoTerminadoModel, metodo:listaMaterialesClientes "+e.getMessage());
            }
        return lista;   
    }
    
 
    public ArrayList<RegistroEntradaSalida> listaRegistrosEntradasSalidas(int noAlmacenProductoTerminado,int anio,int mes) {
        ArrayList<RegistroEntradaSalida> lista = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM entradas_salidas WHERE id_almacen_producto_terminado = "+noAlmacenProductoTerminado+""
                + " AND MONTH(fecha_registro) = "+mes+"  AND YEAR(fecha_registro) = "+anio+";";
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
    
    public ArrayList<String> listaInventarios(int anio, int mes){
        return Estructuras.obtenerlistaDatos("SELECT fecha_inventario FROM inventarios WHERE MONTH(fecha_inventario) = "+mes+"  AND YEAR(fecha_inventario) = "+anio+";");
    }

    public ArrayList<ProductoInventario> obtenerProductosInventario(int noInventario, String fecha) {
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<ProductoInventario> listaProductosInventario = new ArrayList<>();
        
        if(c!=null)
            try {
                String query = "SELECT pi.id_inventario,pr.clave_producto,pi.cantidad FROM productos_inventario AS pi " +
                                " INNER JOIN inventarios AS inv ON pi.id_inventario = inv.id_inventario " +
                                " INNER JOIN productos AS pr ON pr.id_producto = pi.id_inventario " +
                                " WHERE pi.id_inventario = "+noInventario+";";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        ProductoInventario productoInventario = new ProductoInventario();
                        productoInventario.setNoInventario(rs.getInt(1));
                        productoInventario.setCodProducto(rs.getString(2));
                        productoInventario.setCantidad(rs.getInt(3));
                        listaProductosInventario.add(productoInventario);
                    } while (rs.next());
                
            } catch (SQLException e) {
                 System.err.println("error: paquete:AlmacenModel, Class:ProductoTerminadoModel, metodo:obtenerProductosInventario "+e.getMessage());
            }
        
        return listaProductosInventario;
    }
}
