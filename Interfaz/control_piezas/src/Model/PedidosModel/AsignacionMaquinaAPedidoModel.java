
package Model.PedidosModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class AsignacionMaquinaAPedidoModel {





    public AsignacionMaquinaAPedidoModel() {
            

    }
    
    public ArrayList<String> listaMaquinas(){
        return Estructuras.obtenerlistaDatos("SELECT desc_maquina FROM maquinas");
    }
    
    public ArrayList<ProductosPendientes> listaProductosPendientes(String noOrden){
        ArrayList<ProductosPendientes> listaProductos = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        
        if(c!=null)
            try {
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM productosEnEspera WHERE no_orden_compra = '"+noOrden+"'");
                if(rs.first())
                    do {                        
                        listaProductos.add(new ProductosPendientes(rs.getString(1),rs.getInt(2), rs.getString(3), rs.getInt(4)));
                    } while (rs.next());
                
            } catch (Exception e) {
                System.err.println("mensaje: class:AsignacionMaquinaAPedido"
                        + "Method:listaProductosPendientes"+e.getMessage());
            }
        
        return listaProductos;
        
    }
    
    public ArrayList<String> listaMateriales(){
        return Estructuras.obtenerlistaDatos("SELECT desc_material FROM materiales");       
    }
    
    
    
    


    
}
