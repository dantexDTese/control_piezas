
package Model.CatalogosModel;

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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;



public class CatalogoMaterialesModel {
    
    public final int LISTA_TIPOS_MATERIAL = 1;
    public final int LISTA_DIMENCIONES_MATERIAL = 2;
    public final int LISTA_FORMAS_MATERIAL = 3;

    public JComboBox<String> llenarCombo(JComboBox<String> combo, int tipoLista) {
    
        if(combo.getItemCount() > 0)
            combo.removeAllItems();
        
        
        
        switch(tipoLista){  
            case LISTA_TIPOS_MATERIAL:
                combo =  Estructuras.llenaCombo(combo, "SELECT desc_tipo_material FROM tipos_material;");
                break;
            case LISTA_DIMENCIONES_MATERIAL:
                combo =  Estructuras.llenaCombo(combo, "SELECT desc_dimencion FROM dimenciones;");
                break;
            case LISTA_FORMAS_MATERIAL:
                combo =  Estructuras.llenaCombo(combo, "SELECT desc_forma FROM formas;");
                break;
        }
        
        combo.addItem("AGREGAR...");
        
        return combo;
    }

    public ArrayList<Material> obtenerListaMateriales() {
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<Material> listaMateriales = new ArrayList<>();
        if(c!=null)
            try {
                String query = "select * from ver_materiales;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Material materia = new Material();
                        materia.setNoMaterial(rs.getInt(1));
                        materia.setDescTipoMaterial(rs.getString(2));
                        materia.setDescDimenciones(rs.getString(3));
                        materia.setClaveForma(rs.getString(4));
                        materia.setDescForma(rs.getString(5));
                        materia.setLongitudBarra(rs.getFloat(6));
                        listaMateriales.add(materia);
                        
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error:paquete:CatalogosModel, Class:CatalogosMaterialesModel, metodo:obtenerListaMateriales");
            }
        return listaMateriales;
    }

    public void modificarMaterial(Material materialOPeracion, int noMaterial) {
         Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL modificar_material(?,?,?,?,?,?)};";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, noMaterial);
                cs.setString(2, materialOPeracion.getDescTipoMaterial());
                cs.setString(3, materialOPeracion.getDescDimenciones());
                cs.setString(4, materialOPeracion.getDescForma());
                cs.setFloat(5, materialOPeracion.getLongitudBarra());
                cs.registerOutParameter(6, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(6));
                c.close();
            } catch (SQLException e) {
                System.err.println("error:paquete:CatalogosModel, "
                        + "Class:CatalogosMaterialesModel, metodo:modificarMaterial "+e.getMessage());
            }
        
    }

    public void guardarNuevoMaterial(Material materialOPeracion) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                JOptionPane.showMessageDialog(null, materialOPeracion.getDescTipoMaterial()+" "+materialOPeracion.getDescDimenciones()
                +" "+materialOPeracion.getDescForma());
                String query = "{CALL agregar_material(?,?,?,?,?)};";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, materialOPeracion.getDescTipoMaterial());
                cs.setString(2, materialOPeracion.getDescDimenciones());
                cs.setString(3, materialOPeracion.getDescForma());
                cs.setFloat(4, materialOPeracion.getLongitudBarra());
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(5));
                c.close();
            } catch (SQLException e) {
                System.err.println("error:paquete:CatalogosModel,"
                        + " Class:CatalogosMaterialesModel, metodo:guardarNuevoMaterial "+e.getMessage());
            }
    }

    

    public void agregarNuevaDimencion(String dimencion) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_nueva_dimencion(?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, dimencion);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(2));
                c.close();
            } catch (HeadlessException | SQLException e) {
                System.err.println("error:paquete:CatalogosModel,"
                        + " Class:CatalogosMaterialesModel, metodo:guardarNuevaDimencion "+e.getMessage());
            }
    }

    public void agregarNuevoTipoMaterial(String tipoMaterial) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_nuevo_tipo_material(?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, tipoMaterial);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(2));
                c.close();
            } catch (HeadlessException | SQLException e) {
                System.err.println("error:paquete:CatalogosModel,"
                        + " Class:CatalogosMaterialesModel, metodo:guardarNuevTipoMaterial "+e.getMessage());
            }
    }

    public void agregarNuevaForma(String descForma, String claveForma) {
       Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL agregar_nueva_forma(?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1, claveForma);
                cs.setString(2, descForma);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(3));
                c.close();
            } catch (HeadlessException | SQLException e) {
                System.err.println("error:paquete:CatalogosModel,"
                        + " Class:CatalogosMaterialesModel, metodo:guardarNuevaForma "+e.getMessage());
            }
    }
    
    
    
}
