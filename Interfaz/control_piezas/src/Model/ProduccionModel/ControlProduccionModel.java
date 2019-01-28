package Model.ProduccionModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;


public class ControlProduccionModel {

    
    
    public ArrayList<LoteProduccion> obtenerListaLotes(int noOrdenProduccion) {
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<LoteProduccion> listaLotesProduccion = new ArrayList<>();
        
        if(c!=null)
            try {
                String query = "SELECT lpr.rechazo,lpr.desc_lote,op.clave_producto,lpr.barras_utilizadas,"
                        + "lpr.merma,lpr.cantidad_administrador,lpr.scrap_ajustable,lpr.scrap_administrador,"
                        + "lp.desc_tipo_proceso,cantidad_rechazo_liberado,cantidad_operador,scrap_operador"
                        + ",lp.cantidad_planeada,lpr.cantidad_registrada,lpr.tiempo_muerto FROM todos_lotes_planeados AS lp " +
                          " INNER JOIN lotes_produccion AS lpr ON lp.id_lote_planeado = lpr.id_lote_planeado " +
                          " INNER JOIN todas_ordenes_produccion AS op ON op.id_orden_produccion = lp.id_orden_produccion " +
                          " WHERE op.id_orden_produccion = "+noOrdenProduccion+" AND lp.desc_estado = 'CERRADO';";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        
                        LoteProduccion loteProducido = new LoteProduccion();
                        loteProducido.setRechazo(rs.getInt(1));
                        loteProducido.setDescLote(rs.getString(2));
                        loteProducido.setCodProducto(rs.getString(3));
                        loteProducido.setBarrasUtilizadas(rs.getFloat(4));
                        loteProducido.setMerma(rs.getFloat(5));
                        loteProducido.setCantidadAdmin(rs.getInt(6));
                        loteProducido.setScrapAjustable(rs.getInt(7));
                        loteProducido.setScrapAdmin(rs.getInt(8));
                        loteProducido.setTipoProceso(rs.getString(9));
                        loteProducido.setCantidadRechazoLiberado(rs.getInt(10));
                        loteProducido.setCantidadOperados(rs.getInt(11));
                        loteProducido.setScrapOperador(rs.getInt(12));
                        loteProducido.setCantidadPlaneada(rs.getInt(13));
                        loteProducido.setCantidadProducidaR(rs.getInt(14));
                        loteProducido.setTiempoMuerto(rs.getString(15));
                        listaLotesProduccion.add(loteProducido);
                        
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: Paquete:ProduccionModel, Class:ControlProduccionModel, metodo:obtenerListaLotes "+e.getMessage());
            }
        
        return listaLotesProduccion;
    }
    
    
}
