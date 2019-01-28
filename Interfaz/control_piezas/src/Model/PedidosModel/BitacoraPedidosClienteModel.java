package Model.PedidosModel;

import Model.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BitacoraPedidosClienteModel {
    
    public ArrayList<Pedido> listaPedidos(String noOrdenCompra,Integer anio,Integer mes){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query;
        if("".equals(noOrdenCompra))
            query = " SELECT pd.id_orden_trabajo,pd.no_orden_compra,op.clave_producto,pd.fecha_entrega,pd.fecha_confirmacion_entrega, " +
                    " pd.fecha_recepcion,pd.desc_estado,pd.desc_contacto,pd.nombre_cliente,op.cantidad_cliente FROM todos_pedidos AS pd " +
                    " JOIN todas_ordenes_produccion AS op ON pd.id_orden_trabajo = op.id_orden_trabajo "+
                    " WHERE MONTH(fecha_recepcion) = "+mes+"  AND YEAR(fecha_recepcion) = "+anio+";";
    
        else 
            query = " SELECT pd.id_orden_trabajo,pd.no_orden_compra,op.clave_producto,pd.fecha_entrega,pd.fecha_confirmacion_entrega, " +
                    " pd.fecha_recepcion,pd.desc_estado,pd.desc_contacto,pd.nombre_cliente,op.cantidad_cliente FROM todos_pedidos AS pd " +
                    " JOIN todas_ordenes_produccion AS op ON pd.id_orden_trabajo = op.id_orden_trabajo "
                    + "WHERE no_orden_compra = '"+noOrdenCompra+"' AND MONTH(fecha_recepcion) = "+mes+"  AND YEAR(fecha_recepcion) = "+anio+";";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do{
                        pedidos.add(new Pedido(
                                rs.getInt(1),       //ordenTrabajo
                                rs.getString(2),    //ordenCompra
                                rs.getString(3),    //claveProducto
                                rs.getString(4),    //fechaEntrega
                                rs.getString(5),    //fechaConfirmacionEntrega
                                rs.getString(6),    //fechaRecepcion
                                rs.getString(7),    //estado
                                rs.getString(8),    //contacto
                                rs.getString(9),    //cliente
                                rs.getInt(10)));    //cantidad
                    }while(rs.next());
            } catch (Exception e) {
                System.err.println("error: class: nuevoPedidoClientes,"
                        + "method:listaPedidos"+e.getMessage());
            }
            
        return pedidos;
    }

}
