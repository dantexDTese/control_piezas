/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.PedidosModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AsignarDiasProduccionModel {

    public ArrayList<Pedido> listaPedidosPendientes(){
            ArrayList<Pedido> pedidos = new ArrayList<>();
            Connection c = Conexion.getInstance().getConexion();
            if(c!=null)
                try {
                    Statement st = c.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM ordenes_por_planear;");

                    if(rs.first())
                        do {                        
                            Pedido pedido = new Pedido(rs.getInt(1),rs.getString(2),rs.getString(3));
                            pedidos.add(pedido);
                        } while (rs.next());
                   c.close(); 
                } catch (SQLException e) {
                    System.err.println("error: class: AsignacionMaquinaAPedidoModel, Method:listaPedidosPendientes "+e.getMessage());
                }        
            return pedidos;
    }    
}
