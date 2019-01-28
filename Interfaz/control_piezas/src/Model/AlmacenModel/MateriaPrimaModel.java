
package Model.AlmacenModel;

import Model.Conexion;
import Model.Estructuras;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class MateriaPrimaModel {
    
    
    public ArrayList<String> listaMateriasPrimas(){
        return Estructuras.obtenerlistaDatos("SELECT CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) FROM ver_materiales");
    }
    
    public ArrayList<AlmacenMateriaPrima> listaAlmacenMateriaPrima(String descMaterial,int anio,int mes){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<AlmacenMateriaPrima> listaAlmacen = new ArrayList<>();
        
        String query = "SELECT cantidad,cantidad_total,fecha_registro,desc_lote,id_almacen_materia_prima FROM ver_entradas_materiales WHERE desc_material = '"+descMaterial+"'"
                + " AND MONTH(fecha_registro) = "+mes+"  AND YEAR(fecha_registro) = "+anio+" AND id_almacen_materia_prima IS NOT NULL;";
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if(rs.first())
                    do {                        
                        AlmacenMateriaPrima material = new AlmacenMateriaPrima();
                        material.setCantidad(rs.getInt(1));
                        material.setCantidadTotal(rs.getInt(2));
                        material.setFechaRegistro(rs.getString(3));
                        material.setDescLote(rs.getString(4));
                        material.setNoAlmacenMateriaPrima(rs.getInt(5));
                        listaAlmacen.add(material);
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: clase:MateriaPrimaModel, metodo: listaMateriasPrimas "+e.getMessage());
            }
        
        return listaAlmacen;
    }

    public void registrarSalida(AlmacenMateriaPrima almacenLoteSeleccionado, int cantidad) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                 String query = "{CALL registrar_salida_material(?,?,?)}";
                 CallableStatement cs = c.prepareCall(query);
                 cs.setInt(1, almacenLoteSeleccionado.getNoAlmacenMateriaPrima());
                 cs.setInt(2, cantidad);
                 cs.registerOutParameter(3, Types.VARCHAR);
                 cs.execute();
                 JOptionPane.showMessageDialog(null, cs.getString(3));
            } catch (HeadlessException | SQLException e) {
                System.err.println("error: clase:MateriaPrimaModel, metodo: registrarSalida "+e.getMessage());
            }
    }
    
}
