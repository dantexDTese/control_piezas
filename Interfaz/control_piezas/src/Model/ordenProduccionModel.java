
package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


public class ordenProduccionModel {
    
    
    public final int EXISTE_PRODUCTO = 1;
    public final int EXISTE_MATERIAL = 2;
    public final int EXISTE_MAQUINA = 3;
    
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
        
    public boolean existeEntidad(int opcion,String propiedad){
          Pattern p = Pattern.compile("^([a-zA-Z]|[1-9]|\\\\|\\-|\\_)+");
      Matcher m = p.matcher(propiedad);
      String query;
      if (m.find()){
              switch (opcion) {
                  case 1:
                      query =String.format("select '%s' from productos",propiedad);
                      break;
                  case 2:
                      query =String.format("select '%s' from materiales",propiedad);
                      break;
                  default:
                      query =String.format("select '%s' from maquinas",propiedad);
                      break;
              }
              return Estructuras.existeEntidad(query);
      }
      else{
         JOptionPane.showMessageDialog(null,"el valor no es correcto");
         return false;
      }
    }
    
    
    
}
