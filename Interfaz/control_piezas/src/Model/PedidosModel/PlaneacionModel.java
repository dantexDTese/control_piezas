
package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class PlaneacionModel {
 
    
    public ArrayList<String> listaMaquinas(){
        return Estructuras.obtenerlistaDatos("SELECT desc_maquina FROM maquinas");
    }
    
    public ArrayList<Pedido> listaPedidosPendientes(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM OrdenesPendientes");
                if(rs.first())
                    do {                        
                        Pedido pedido = new Pedido(rs.getString(1),rs.getString(2));
                        pedidos.add(pedido);
                    } while (rs.next());
               c.close(); 
            } catch (Exception e) {
                System.err.println("error: class: PlaneacionModel, Method:listaPedidosPendientes"+e.getMessage());
            }
        
        return pedidos;
    }
      
    public ArrayList<procedimientoTotal> listaProcedimientoMaquina(String nombreMaquina){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<procedimientoTotal> lista = new ArrayList<>();
        String query="SELECT * FROM procedimiento_total WHERE desc_maquina = '"+nombreMaquina+"'";
        if(c!=null)
            try {
               Statement st = c.createStatement();
               ResultSet rs = st.executeQuery(query);
               if(rs.first())
                    do {                        
                        lista.add(new procedimientoTotal(
                                rs.getString(1),rs.getInt(2),rs.getInt(3),
                                rs.getFloat(4),rs.getString(5),rs.getString(6),
                                rs.getString(7)));                       
                    } while (rs.next());
               c.close();
            } catch (Exception e) {
                System.err.println("error: class: PlaneacionModel, method:listaProcedimentosMaquina "+e.getMessage());
            }
        
        return lista;
    }
    
    public ProcesoPrincipal obtenerProcesoPrincipal(String descMaquina){
        ProcesoPrincipal principal = null;
        Connection c = Conexion.getInstance().getConexion();
        String query = "SELECT * FROM procesando_producto WHERE desc_maquina = '"+descMaquina+"'"
                + " GROUP BY id_orden_produccion;";
                
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    principal = new ProcesoPrincipal(rs.getInt(2),rs.getString(3),
                            rs.getString(7),rs.getString(9));
                c.close();
            } catch (Exception e) {
                System.err.println("error: class: PlaneacionModel method: obtenerProcesosPrincipal");
            }
        return principal;
    }
    
    public ArrayList<lotesProduccion> listaLotesProduccion(String descMaquina){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<lotesProduccion> listaLotes = new ArrayList<>();
        String query = "SELECT * FROM procesando_producto WHERE desc_maquina = '"+descMaquina+"';";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        listaLotes.add(new lotesProduccion(rs.getInt(8),rs.getString(10)));
                    } while (rs.next());
                c.close();
            } catch (Exception e) {
                System.err.println("error: PlaneacionModel, method:listaLotesProduccion "+e.getMessage());
            }
        return listaLotes;
    }
    
    
    
    
    
}
