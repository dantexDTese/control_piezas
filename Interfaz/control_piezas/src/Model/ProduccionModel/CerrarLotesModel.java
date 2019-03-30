
package Model.ProduccionModel;

import Model.Conexion;
import Model.PedidosModel.lotesProduccion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class CerrarLotesModel {
    
    
    public ArrayList<LoteProduccion> listaLotesNoCerrados(int noOrdenProduccion){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<LoteProduccion> lista = new ArrayList<>();
        String query = " SELECT lp.id_lote_planeado,lp.fecha_planeada,lpr.desc_lote FROM todos_lotes_planeados AS lp " +
                       " INNER JOIN lotes_produccion AS lpr ON lp.id_lote_planeado = lpr.id_lote_planeado " +
                       " WHERE lp.desc_estado = 'ABIERTO' AND lp.id_orden_produccion = "+noOrdenProduccion+" GROUP BY lp.id_lote_planeado;";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        LoteProduccion lp = new LoteProduccion();
                        lp.setNoLotePlaneado(rs.getInt(1));
                        lp.setFechaPlaneada(rs.getString(2));
                        lp.setDescLote(rs.getString(3));
                        lista.add(lp);
                    } while (rs.next());
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel,"
                        + " class:CerrarLotesModel, metodo:listaLotesNoCerrados "+e.getMessage());
            }
        return lista;
    }

    public void cerrarLote(int lotePlaneado) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL cerrar_lote_planeado(?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, lotePlaneado);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(2));
            } catch (SQLException e) {
                System.out.println("error: paquete:ProduccionModel, class:CerrarLotesModel, metodo:listaLotesNoCerrados "+
                        e.getMessage());
            }
    }
}
