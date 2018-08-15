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
import javax.swing.JComboBox;

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
    
    
}
