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
public class CatalogoClientesModel {

    public ArrayList<Cliente> obtenerListaClientes() {
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<Cliente> clientes = new ArrayList<>();
        if(c!=null)
            try {
                String query = "SELECT * FROM clientes";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Cliente cl = new Cliente();
                        cl.setNoCliente(rs.getInt(1));
                        cl.setNombreCliente(rs.getString(2));
                        cl.setDescCliente(rs.getString(3));
                        clientes.add(cl);
                    } while (rs.next());
            } catch (SQLException e) {
                System.err.println("error: paquete:CatalogosModel, class:CatalogoClientesModel, metodo:obtenerListaClientes "+e.getMessage());
            }
        return clientes;
    }

    public void guardarModificarCliente(String descCliente, String nomCliente) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_modificar_cliente(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, nomCliente);
                cs.setString(2, descCliente);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3),"CONFIRMACION",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException | SQLException e) {
                 System.err.println("error: paquete:CatalogosModel, class:CatalogoClientesModel, metodo:guardarModificarCliente "+e.getMessage());
            }
               
    }
    
    

    
    
}
