
package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class ordenProduccionModel {
    
    public ordenProduccionModel(){
        
    }
    
    
    public ArrayList obtenerListaProductos(){
        return Estructuras.obtenerlistaDatos("SELECT clave_producto FROM productos");
    }
    
    public ArrayList obtenerListaMaquinas(){
        return Estructuras.obtenerlistaDatos("SELECT desc_maquina FROM maquinas");
    }
    
    public ArrayList obtenerListaMateriales(){
        return Estructuras.obtenerlistaDatos("SELECT desc_material FROM materiales");
    }
    
    
}
