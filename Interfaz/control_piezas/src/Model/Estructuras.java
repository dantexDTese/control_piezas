/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class Estructuras {

    
    public static JComboBox llenaCombo(JComboBox selector,String query){
        Connection conexion = Conexion.getInstance().getConexion();       
        selector.removeAll();
        if(conexion != null){
            try {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);               
                
                if(rs.first()){
                    do{
                        selector.addItem(rs.getString(1));
                    }while(rs.next());
                }
                    
                conexion.close();
            } catch (Exception e) {
                System.err.println("error:"+e.getMessage());
            }
        }
        return selector;
    }
    
    
    public static ArrayList obtenerlistaDatos(String query){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList lista=new ArrayList();
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        lista.add(rs.getString(1));
                    }while(rs.next());
                c.close();
            } catch (Exception e) {
                System.err.println("error clase ordenProduccionModel,metodo listaProductos:"+e.getMessage());
            }
        return lista;
    }
    
    public static boolean existeEntidad(String query){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    return true;
            } catch (Exception e) {
                System.err.println("class:Estructuras,"
                                + "method: existeEntidad,error:"+e.getMessage());
            }
        else{
            System.err.println("no fue posible establecer una conexion");
            return false;
        }
        return true;
    }
    
    public static DefaultTableModel limpiarTabla(DefaultTableModel model){
        while(model.getRowCount()>0)
            model.removeRow(0);
    
         return model;
    }
    
    
    
    
}
