/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.AlmacenModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public class RegistroInventarioModel {

    
    public ArrayList<String> listaProductos() {
        return Estructuras.obtenerlistaDatos("SELECT clave_producto FROM productos;");
    }

    public int iniciarInventario(String responsable) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "CALL agregar_inventario(?,?)";
        int respuesta = 0;
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, responsable);
                cs.registerOutParameter(2, Types.INTEGER);
                cs.execute();
                respuesta = cs.getInt(2);
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, Class:RegistroInventarioModel, metodo:listaProductos "+e.getMessage());

            }
        return respuesta;
    }

    public void agregarProductoInventario(String claveProducto, int cantidad,int noInventario) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL agregar_producto_inventario(?,?,?)}";
        if(c!= null)
            try {
               
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noInventario);
                cs.setString(2, claveProducto);
                cs.setInt(3, cantidad);
                cs.execute();
                
            } catch (SQLException e) {
                System.err.println("error: paquete:AlmacenModel, Class:RegistroInventarioModel, metodo:agregarProductoInventario "+e.getMessage());
            }
    }

    
    
}
