package Model.PedidosModel;

import Model.Conexion;
import Model.ordenProduccion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BitacoraPedidosClienteModel {
    
    public ArrayList<ordenProduccion> listaPedidos(String noOrdenCompra,Integer anio,Integer mes){
        ArrayList<ordenProduccion> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query;
        if("".equals(noOrdenCompra))
            query = " SELECT pd.id_orden_trabajo,pd.no_orden_compra,op.clave_producto,pd.fecha_entrega,pd.fecha_confirmacion_entrega, " +
                    " pd.fecha_recepcion,op.desc_estado,pd.desc_contacto,pd.nombre_cliente,op.cantidad_cliente FROM todos_pedidos AS pd " +
                    " JOIN todas_ordenes_produccion AS op ON pd.id_orden_trabajo = op.id_orden_trabajo "+
                    " WHERE MONTH(fecha_recepcion) = "+mes+"  AND YEAR(fecha_recepcion) = "+anio+";";
        else 
            query = " SELECT pd.id_orden_trabajo,pd.no_orden_compra,op.clave_producto,pd.fecha_entrega,pd.fecha_confirmacion_entrega, " +
                    " pd.fecha_recepcion,op.desc_estado,pd.desc_contacto,pd.nombre_cliente,op.cantidad_cliente FROM todos_pedidos AS pd " +
                    " JOIN todas_ordenes_produccion AS op ON pd.id_orden_trabajo = op.id_orden_trabajo "
                    + "WHERE no_orden_compra = '"+noOrdenCompra+"' AND MONTH(fecha_recepcion) = "+mes+"  AND YEAR(fecha_recepcion) = "+anio+";";
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        ordenProduccion op = new ordenProduccion();
                        op.setOrdenTrabajo(rs.getInt(1));
                        op.setNoOrdenCompra(rs.getString(2));
                        op.setCodProducto(rs.getString(3));
                        op.setFechaentrega(rs.getString(4));
                        op.setFechaConfirmacionEntrega(rs.getString(5));
                        op.setFechaRecepcion(rs.getString(6));
                        op.setDescEstadoOrdenProduccion(rs.getString(7));
                        op.setDescContacto(rs.getString(8));
                        op.setDescCliente(rs.getString(9));
                        op.setCantidadCliente(rs.getInt(10));
                        pedidos.add(op);    //cantidad
                    }while(rs.next());
            } catch (Exception e) {
                System.err.println("error: class: nuevoPedidoClientes,"
                        + "method:listaPedidos"+e.getMessage());
            }       
        return pedidos;
    }
}
