
package Model.ProduccionModel;

import Model.Conexion;
import Model.Estructuras;
import Model.TiempoMuertoLote;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class CompletarRegistroProduccionModel {

    public final int LISTA_DEFECTOS_PRODUCCION = 1;
    
    public final int LISTA_TIEMPOS_MUERTOS = 2;
    
    public void modificarLoteProduccion(LoteProduccion loteProduccion) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL modificar_lote_produccion(?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, loteProduccion.getDescLote());
                cs.setInt(2, loteProduccion.getCantidadOperados());
                cs.setInt(3, loteProduccion.getCantidadAdmin());
                cs.setInt(4, loteProduccion.getScrapOperador());
                cs.setInt(5, loteProduccion.getScrapAdmin());
                cs.setFloat(6, loteProduccion.getMerma());
                cs.setInt(7, loteProduccion.getRechazo());
                cs.setInt(8, loteProduccion.getCantidadRechazoLiberado());
                cs.setInt(9, loteProduccion.getScrapAjustable());
                cs.setFloat(10, loteProduccion.getBarrasUtilizadas());
                cs.registerOutParameter(11, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(11));
                
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:ProduccionModel, Class:CompletarRegistroProduccionModel, metodo:modificarLoteProduccion "+e.getMessage());
            }
    }
    
    public JComboBox llenarCombo(JComboBox combo,int tipoLista){
        
        switch(tipoLista){
            
            case LISTA_DEFECTOS_PRODUCCION:
                    combo = Estructuras.llenaCombo(combo, "SELECT desc_defecto_producto FROM defectos_producto;");
                    break;
            case LISTA_TIEMPOS_MUERTOS:
                    combo = Estructuras.llenaCombo(combo,"SELECT desc_tiempo_muerto FROM tipos_tiempo_muerto;");
                    break;
                    
        }
        
        return combo;    
    }
    
    public ArrayList<DefectoLote> listaDefectosLotes(String descLote){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<DefectoLote> lista = new ArrayList<>();
        if(c!=null)
            try {
                String query = "SELECT id_defecto_lote,dl.id_lote_produccion,"
                        + "cantidad,desc_defecto_producto FROM defectos_lotes AS dl " +
                        "INNER JOIN defectos_producto AS dp ON dl.id_defecto_producto = dp.id_defecto_producto " +
                        "WHERE dl.id_lote_produccion = (SELECT id_lote_produccion "
                        + "FROM lotes_produccion WHERE desc_lote = '"+descLote+"');";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        DefectoLote lote = new DefectoLote();
                        lote.setNoDefectoLote(rs.getInt(1));
                        lote.setNoLote(rs.getInt(2));
                        lote.setCantidad(rs.getInt(3));
                        lote.setDescDefectoProduccion(rs.getString(4));
                        lista.add(lote);
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, "
                        + "Class:CompletarRegistroProduccionModel, metodo:listaDefectosLotes "+e.getMessage());
            }
     
        return lista;
    }

    public ArrayList<TiempoMuertoLote> listaTiemposMuertosLote(String descLote){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<TiempoMuertoLote> listaTiemposMuertos = new ArrayList<>();
        
        if(c!=null)
            try {
                String query = "SELECT lp.desc_lote,tml.id_tiempo_muerto_lote,ttm.desc_tiempo_muerto FROM lotes_produccion AS lp " +
                    "INNER JOIN tiempos_muertos_lote AS tml ON lp.id_lote_produccion = tml.id_lote_produccion " +
                    "INNER JOIN tipos_tiempo_muerto AS ttm ON ttm.id_tipo_tiempo_muerto = tml.id_tipo_tiempo_muerto "
                        + "WHERE lp.desc_lote = '"+descLote+"';";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        TiempoMuertoLote tiempoLote = new TiempoMuertoLote();
                        tiempoLote.setDescLote(rs.getString(1));
                        tiempoLote.setNoTiempoMuertoLote(rs.getInt(1));
                        tiempoLote.setDescTiempoMuerto(rs.getString(3));
                        listaTiemposMuertos.add(tiempoLote);
                    } while (rs.next());
            
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, "
                        + "Class:CompletarRegistroProduccionModel, metodo:listaTiemposMuertosLote "+e.getMessage());
            }
        
        return listaTiemposMuertos;
    }
    
    public void agregarTiempoMuerto(TiempoMuertoLote tiempoMuerto){
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_tiempos_muertos(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, tiempoMuerto.getDescLote());
                cs.setString(2, tiempoMuerto.getDescTiempoMuerto());
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:ProduccionModel, "
                        + "Class:CompletarRegistroProduccionModel, metodo:agregarTiempoMuerto "+e.getMessage());
            }
    }
    
    public void agregarDefecto(DefectoLote loteBuscado) {
        Connection c = Conexion.getInstance().getConexion();
        if(c != null)
            try {
                String query = "{CALL agregar_defecto_produccion(?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, loteBuscado.getDescLote());
                cs.setString(2, loteBuscado.getDescDefectoProduccion());
                cs.setInt(3,loteBuscado.getCantidad());
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(4));
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: paquete:ProduccionModel, "
                        + "Class:CompletarRegistroProduccionModel, metodo:agregarDefecto "+e.getMessage());
            }
    }

    public void actualizarDefecto(DefectoLote loteBuscado) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                
                String query = "{CALL modificar_defecto_produccion(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, loteBuscado.getNoDefectoLote());
                cs.setInt(2, loteBuscado.getCantidad());
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                
                JOptionPane.showMessageDialog(null, cs.getString(3));
            } catch (SQLException e) {
                System.err.println("error: paquete:ProduccionModel, "
                        + "Class:CompletarRegistroProduccionModel, metodo:actualizarDefecto "+e.getMessage());
            }
    }

    public void eliminarDefecto(DefectoLote lote) {
       Connection c = Conexion.getInstance().getConexion();
       if(c!=null)
           try {
               
               String query = "{CALL eliminar_defecto_lote(?,?,?)}";
               CallableStatement cs = c.prepareCall(query);
               cs.setString(1, lote.getDescLote());
               cs.setString(2, lote.getDescDefectoProduccion());
               cs.registerOutParameter(3, Types.VARCHAR);
               cs.execute();
               JOptionPane.showMessageDialog(null, cs.getString(3));
           } catch (HeadlessException | SQLException e) {
               System.err.println("error: paquete:ProduccionModel, "
                        + "Class:CompletarRegistroProduccionModel, metodo:EliminarDefecto "+e.getMessage());
           }
        
        
    }

    public void eliminarTiempoMuerto(TiempoMuertoLote tiempoLote) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL eliminar_tiempo_muerto_lote(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, tiempoLote.getDescLote());
                cs.setString(2, tiempoLote.getDescTiempoMuerto());
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
            } catch (HeadlessException | SQLException e) {
               System.err.println("error: paquete:ProduccionModel, "
                        + "Class:CompletarRegistroProduccionModel, metodo:EliminarTiempoMuerto "+e.getMessage()); 
            }
    }

}
