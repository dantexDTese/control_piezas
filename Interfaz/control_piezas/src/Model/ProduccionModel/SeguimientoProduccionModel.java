
package Model.ProduccionModel;

import Model.Conexion;
import Model.PedidosModel.lotesProduccion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class SeguimientoProduccionModel {

    
    public ArrayList<String> obtenerProcesosProduccion(int noOrdenProduccion) {
        ArrayList<String> listaProcesosProduccion = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT desc_tipo_proceso FROM tipos_proceso AS pc " +
                        "WHERE pc.id_tipo_proceso = (SELECT id_tipo_proceso FROM lotes_planeados "
                        + "WHERE id_orden_produccion = "+noOrdenProduccion+" GROUP BY id_tipo_proceso);";
        if(c!= null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do{
                        listaProcesosProduccion.add(rs.getString(1));
                    }while(rs.next());
                 
            } catch (SQLException e) {
                System.err.println("error: paquete:produccionModel, class:SeguimientoProduccionModel, method:obtenerProocesosProduccion "+e.getMessage());
            }
  
        return listaProcesosProduccion;
    }
    
    public ArrayList<lotesProduccion> listaLotesProduccion(){
        
    }
    
    public class  loteProduccion{
        
        private final int noLote;
        private final String descLote;
        private final int cantidadOperados;
        private final int scrapOperador;
        private final float merma;
        private final String tiempoMuerto;
        private final int rechazo;
        private final int cantidadAdmin;
        private final int scrapAdmin;

        public loteProduccion(int noLote, String descLote, int cantidadOperados, int scrapOperador, float merma, String tiempoMuerto, int rechazo, int cantidadAdmin, int scrapAdmin) {
            this.noLote = noLote;
            this.descLote = descLote;
            this.cantidadOperados = cantidadOperados;
            this.scrapOperador = scrapOperador;
            this.merma = merma;
            this.tiempoMuerto = tiempoMuerto;
            this.rechazo = rechazo;
            this.cantidadAdmin = cantidadAdmin;
            this.scrapAdmin = scrapAdmin;
        }
        
        
    }
    
}
