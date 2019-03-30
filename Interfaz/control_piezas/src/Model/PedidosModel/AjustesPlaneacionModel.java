
package Model.PedidosModel;

import Model.Conexion;
import Model.LotePlaneado;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class AjustesPlaneacionModel {
    
    public ArrayList<LotePlaneado> listaLotesPlaneados(int noPedido,int noOrdenProduccion){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<LotePlaneado> lista = new ArrayList<>();
        String query = " SELECT "
                + " lp.desc_tipo_proceso,cantidad_planeada,fecha_planeada,op.id_orden_produccion FROM todos_lotes_planeados AS lp " +
                " INNER JOIN (SELECT id_orden_trabajo,id_orden_produccion FROM todas_ordenes_produccion) AS op "
                + " ON op.id_orden_produccion = lp.id_orden_produccion " +
                " INNER JOIN (SELECT id_pedido,id_orden_trabajo FROM todos_pedidos) "
                + " AS pd ON pd.id_pedido = op.id_orden_trabajo WHERE desc_estado = 'ABIERTO'"
                + " AND lp.id_orden_produccion = "+noOrdenProduccion+" AND pd.id_pedido = "+noPedido+";";
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {
                        
                        LotePlaneado lp = new LotePlaneado();
                        lp.setTipoProceso(rs.getString(1));
                        lp.setCantidadPlaneada(rs.getInt(2));
                        lp.setFechaPlaneada(rs.getString(3));
                        lp.setNoOrdenProduccion(rs.getInt(4));
                        lista.add(lp);
                        
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: paquete:PedidosModel, class:AjustesPlaneacionModel, metodo:listaLotesPlaneados");
            }
        
        return lista;
    }

    public int obtenerCantidadTotal(int noOrdenProduccion){
        
        Connection c = Conexion.getInstance().getConexion();
        int cantidadTotal=0;
        String query = "SELECT cantidad_total FROM ordenes_produccion WHERE id_orden_produccion = "+noOrdenProduccion+";";
        
        if( c != null )
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first()) cantidadTotal = rs.getInt(1);
                
            } catch (SQLException e) {
                System.err.println("error: paquete:PedidosModel, class:AjustesPlaneacionModel, metodo:obtenerCantidadTotal "+
                        e.getMessage());
            }
        
        return cantidadTotal;
    }

    public void modificarLote(LotePlaneado loteModificar) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL modificar_lote(?,?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, loteModificar.getNoOrdenProduccion());
                cs.setInt(2, loteModificar.getCantidadPlaneada());
                cs.setString(3,loteModificar.getFechaPlaneada());
                cs.registerOutParameter(4,Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(4));
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:PedidosModel, class:AjustesPlaneacionModel, metodo:modificarLote "+
                        e.getMessage());
            }
    }

    public void eliminarLote(LotePlaneado loteModificar) {
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL eliminar_lote(?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, loteModificar.getNoOrdenProduccion());
                cs.setString(2, loteModificar.getFechaPlaneada());
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
            } catch (SQLException e) {
                System.err.println("error: paquete:PedidosModel, class:AjustesPlaneacionModel, metodo:modificarLote "+
                        e.getMessage());
            }
    }
    
    
    
    
}
