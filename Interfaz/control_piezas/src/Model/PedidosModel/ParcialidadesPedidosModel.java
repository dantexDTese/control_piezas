
package Model.PedidosModel;

import Model.Parcialidad;
import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


public class ParcialidadesPedidosModel {



    public ArrayList<Parcialidad> listaParcialidades(String noOrden,String codProducto){
        ArrayList<Parcialidad> parcialidades = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT pd.fecha_entrega,res.cantidad FROM parcialidades_entrega AS pe " +
                       "INNER JOIN (SELECT * FROM parcialidades_pedido AS pp WHERE pp.id_pedido = "
                       + "(SELECT id_pedido FROM pedidos AS pd WHERE pd.no_orden_compra = '"+noOrden+"')) AS pd " +
                       "ON pe.id_parcialidad_pedido = pd.id_parcialidad_pedido " +
                       "INNER JOIN (SELECT * FROM todas_ordenes_produccion WHERE clave_producto = '"+codProducto+"') "
                       + "AS op ON pe.id_orden_produccion = op.id_orden_produccion " +
                       "INNER JOIN registros_entradas_salidas AS res ON pe.id_registro_entrada_salida = res.id_registro_entrada_salida;";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        parcialidades.add(new Parcialidad(rs.getString(1), rs.getInt(2), codProducto, noOrden));
                    } while (rs.next());
                c.close();
            } catch (SQLException e) {
                System.err.println("error: Class ParcialiadesPedidosModel,Method: listaParcialiades"+e.getMessage());
            }
        
        return parcialidades;
    }

}
