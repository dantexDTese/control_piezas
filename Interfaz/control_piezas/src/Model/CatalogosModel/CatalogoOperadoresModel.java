/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.CatalogosModel;

import Model.Conexion;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author cesar
 */
public class CatalogoOperadoresModel {

    public ArrayList<Operador> obtejerLIstaOperadores() {
         Connection c = Conexion.getInstance().getConexion();
        ArrayList<Operador> operadores = new ArrayList<>();
        if(c!=null)
            try {
                String query = "SELECT no_operador,nombre_operador FROM operadores;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Operador oper = new Operador();
                        
                        oper.setCodOperador(rs.getString(1));
                        oper.setNombreOperador(rs.getString(2));
                        operadores.add(oper);
                    } while (rs.next());
            } catch (SQLException e) {
                System.err.println("error: paquete:CatalogosModel, class:CatalogoOperadoresModel, metodo:obtenerListaOPeradores "+e.getMessage());
            }
        return operadores;
    }
    
    public void guardarModificarOperadores(String codPerador,String nombreOperador){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_modificar_operador(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, codPerador);
                cs.setString(2, nombreOperador);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3),"CONFIRMACION",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException | SQLException e) {
                 System.err.println("error: paquete:CatalogosModel, class:CatalogoOperadoresModel, metodo:guardarModificarOperdor "+e.getMessage());
            }
    }
    
}
