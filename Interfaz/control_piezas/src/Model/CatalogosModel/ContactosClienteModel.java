
package Model.CatalogosModel;

import Model.Conexion;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ContactosClienteModel {
    
    public ArrayList<Contacto> listaContactoCliente(String descCliente){
        Connection c = Conexion.getInstance().getConexion();
        ArrayList<Contacto> lista = new ArrayList<>();
        if(c!=null)
            try {
                String query = " SELECT * FROM contactos AS cn " +
                " WHERE cn.id_cliente = (SELECT id_cliente FROM clientes AS cl "
              + " WHERE cl.nombre_cliente = '"+descCliente+"'); ";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                        
                        Contacto contacto = new Contacto();
                        contacto.setNoContacto(rs.getInt(1));
                        contacto.setNoCliente(rs.getInt(2));
                        contacto.setDescContacto(rs.getString(3));
                        contacto.setDescDepartamento(rs.getString(4));
                        contacto.setTelefono(rs.getString(5));
                        contacto.setExtencion(rs.getString(6));
                        contacto.setCelular(rs.getString(7));
                        contacto.setCorreo(rs.getString(8));
                        lista.add(contacto);
                    } while (rs.next());
            } catch(SQLException e){
                System.err.println(" error:paquete:CatalogosModel, clase:COntactosClienteModel, "
                        + " metodo:listaContactoCliente ");
            }
        return lista;
    }

    public void guardarContacto(Contacto contacto) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL guardar_contacto(?,?,?,?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, contacto.getNoCliente());
                cs.setString(2, contacto.getDescContacto());
                cs.setString(3, contacto.getDescDepartamento());
                cs.setString(4, contacto.getTelefono());
                cs.setString(5, contacto.getExtencion());
                cs.setString(6, contacto.getCelular());
                cs.setString(7, contacto.getCorreo());
                cs.registerOutParameter(8, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(8));
            } catch (HeadlessException | SQLException e) {
                System.err.println(" error:paquete:CatalogosModel, clase:COntactosClienteModel, "
                        + " metodo:guardarContacto ");
            }     
    }

    public void modificarContacto(Contacto contacto) {
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "{CALL modificar_contacto(?,?,?,?,?,?,?,?)}";
                CallableStatement cs = c.prepareCall(query);
                cs.setInt(1, contacto.getNoContacto());
                cs.setString(2, contacto.getDescContacto());
                cs.setString(3, contacto.getDescDepartamento());
                cs.setString(4, contacto.getTelefono());
                cs.setString(5, contacto.getExtencion());
                cs.setString(6, contacto.getCelular());
                cs.setString(7, contacto.getCorreo());
                cs.registerOutParameter(8, Types.VARCHAR);
                cs.execute();
                JOptionPane.showMessageDialog(null, cs.getString(8));
            } catch (HeadlessException | SQLException e) {
                System.err.println(" error:paquete:CatalogosModel, clase:COntactosClienteModel, "
                        + " metodo:ModificarContacto ");
            }     
    }
    
}
