
package Model.RequisicionesModel;

import Model.Conexion;
import Model.Estructuras;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class RegistrarNuevaEntradaMaterialModel {
    public  final int LISTA_PROVEEDORES = 1;
    public  final int LISTA_MATERIALES = 2;
    
    public JComboBox llenarCombo(JComboBox combo, int numLista){
        String query = "";
        
        switch(numLista){
            case LISTA_PROVEEDORES:
                query = "SELECT desc_proveedor FROM proveedores;";
                break;
            case LISTA_MATERIALES:
                query = "SELECT CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) AS desc_material FROM ver_materiales;";
                break;
            default:
                return combo;
        }   
        return Estructuras.llenaCombo(combo, query);
    }
    
    public void agregarEntradaMaterial(EntradaMaterial entradaMaterial){
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL agregar_entrada_material(?,?,?,?,?,?,?,?)}";
        if(c!=null)
            try {
                CallableStatement cs = c.prepareCall(query);
                cs.setString(1,entradaMaterial.getDescProveedor());
                cs.setString(2,entradaMaterial.getDescMaterial());
                cs.setInt(3,entradaMaterial.getCantidad());
                cs.setString(4, entradaMaterial.getCodigo());
                cs.setString(5, entradaMaterial.getCertificado());
                cs.setString(6,entradaMaterial.getOrdenCompra());
                cs.setString(7, entradaMaterial.getInspector());
                cs.registerOutParameter(8,Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(8));
            } catch (SQLException e){
                System.err.println("error: pacquete:RequisicionesController, class RegistrarNuevaEntradaMaterialModel, metodo: agregarEntradaMaterialModel "+e.getMessage());
            }        
    }
    
}
