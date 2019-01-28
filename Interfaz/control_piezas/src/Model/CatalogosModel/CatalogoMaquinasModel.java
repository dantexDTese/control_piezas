/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.CatalogosModel;

import Model.Conexion;
import Model.Estructuras;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author cesar
 */
public class CatalogoMaquinasModel {

    public ArrayList<String> obtenerLIstaMaquinas() {
        return Estructuras.obtenerlistaDatos("SELECT desc_maquina FROM maquinas;");
    }

    public void agregarMaquina(String descMaquina) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_maquina(?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, descMaquina);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(2),"CONFIRMACION",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException | SQLException e) {
                 System.err.println("error: paquete:CatalogosModel, class:CatalogoMaquinasModel, metodo:agregarMaquina "+e.getMessage());
            }
    }
    
}
