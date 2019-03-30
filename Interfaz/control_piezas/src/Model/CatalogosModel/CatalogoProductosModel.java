/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.CatalogosModel;

import Model.Conexion;
import Model.Estructuras;
import Model.ProductoMaquina;
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

/**
 *
 * @author cesar
 */
public class CatalogoProductosModel {
    
    
    public ArrayList<Producto> listaProductos(){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<Producto> listaProductos = new ArrayList<>();
        
        if(c!=null)
            try {
                String query = " SELECT id_producto,clave_producto,desc_producto, "
                        + " CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) AS material FROM productos AS pr " +
                          " INNER JOIN ver_materiales AS vm ON pr.id_material = vm.id_material "
                        + " ORDER BY id_producto;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Producto p = new Producto();
                        p.setNoProducto(rs.getInt(1));
                        p.setClaveProducto(rs.getString(2));
                        p.setDescProducto(rs.getString(3));
                        p.setMaterial(rs.getString(4));
                        listaProductos.add(p);
                    }while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: paquete:CatalogosModel, class:CatalogoProductosModel,metodo:listaProductos "+
                        e.getMessage());
            }
        
        return listaProductos;
    }

    public JComboBox<String> llenarComboMateriales(JComboBox<String> cbxMaterial) {
       return Estructuras.llenaCombo(cbxMaterial,"select CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) AS material from ver_materiales;");
    }

    public ArrayList<ProductoMaquina> obtenerMaquinasProducto(int noProducto) {
       Connection c = Conexion.getInstance().getConexion();
       ArrayList<ProductoMaquina> listaMaquinasProducto = new ArrayList<>();
       if(c!=null)
           try {
               String query = " SELECT desc_maquina,piezas_por_turno,piezas_por_barra,"
                       + "piezas_por_hora,desc_tipo_proceso,id_producto_maquina FROM ver_productos_maquinas WHERE id_producto = "+noProducto+";";
               Statement st = c.createStatement();
               ResultSet rs = st.executeQuery(query);
               if(rs.first())
                   do {                       
                       ProductoMaquina pMaquina = new ProductoMaquina();
                       pMaquina.setDescMaquina(rs.getString(1));
                       pMaquina.setPiezasPorTurno(rs.getInt(2));
                       pMaquina.setPiezasPorBarra(rs.getInt(3));
                       pMaquina.setPiezasPorHora(rs.getInt(4));
                       pMaquina.setDescTipoProceso(rs.getString(5));
                       pMaquina.setNoProductoMaquina(rs.getInt(6));
                       listaMaquinasProducto.add(pMaquina);
                   } while (rs.next());
               
           } catch (SQLException e) {
                 System.err.println("error: paquete:CatalogosModel, class:CatalogoProductosModel,metodo:obtenerMaquinasProducto "+
                        e.getMessage());
           }
       return listaMaquinasProducto;
    }

    public void agregarNuevoProducto(Producto p) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_nuevo_producto(?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, p.getClaveProducto());
                cs.setString(2, p.getDescProducto());
                cs.setString(3, p.getMaterial());
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(4));
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:CatalogosModel, class:CatalogoProductosModel,metodo:agregarNuevoProducto "+
                        e.getMessage());
            }
    }
    
    public void modificarProducto(int noProductoAnterior,Producto p){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL modificar_producto(?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noProductoAnterior);
                cs.setString(2, p.getClaveProducto());
                cs.setString(3, p.getDescProducto());
                cs.setString(4, p.getMaterial());
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(5));
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:CatalogosModel, class:CatalogoProductosModel,metodo:modificarProducto "+
                        e.getMessage());
            }
        
    }

    public void agregarMaquinaProducto(String claveProducto, ProductoMaquina pMaquina) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_producto_material(?,?,?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1,claveProducto);
                cs.setString(2, pMaquina.getDescTipoProceso());
                cs.setInt(3, pMaquina.getPiezasPorHora());
                cs.setInt(4, pMaquina.getPiezasPorTurno());
                cs.setInt(5, pMaquina.getPiezasPorBarra());
                cs.setString(6, pMaquina.getDescMaquina());
                cs.registerOutParameter(7, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(7));
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:CatalogosModel, class:CatalogoProductosModel,metodo:agregarMaquinaProducto "+
                        e.getMessage());
            }
    }

}
