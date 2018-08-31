/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import Model.productoModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


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
                
            } catch (Exception e) {
                System.err.print("Error class:nuevoPedidoClienteModel,"
                        + "method: listaProductos "+e.getMessage());
            }
        
        return productos;
    }
    
}
