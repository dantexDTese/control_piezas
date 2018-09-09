
package Model.PedidosModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;


public class ParcialidadesPedidosModel {



    public ArrayList<Parcialidad> listaParcialidades(String noOrden){
        ArrayList<Parcialidad> parcialidades = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT pr.fecha_entrega,pr.cantidad_entregada "
                        + "FROM bitacoraPedidos bp "
                        + "JOIN parcialidades pr ON bp.id_pedido = pr.id_pedido " +
                          " WHERE bp.no_orden_compra = '"+noOrden+"'");
                
                if(rs.first())
                    do {                        
                        parcialidades.add(new Parcialidad(noOrden, rs.getString(1),rs.getInt(2)));
                    } while (rs.next());
                
            } catch (Exception e) {
                System.err.println("error: Class ParcialiadesPedidosModel,"
                        + "Method: listaParcialiades"+e.getMessage());
            }
        
        return parcialidades;
    }
}
