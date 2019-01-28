
package Model.RequisicionesModel;

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


public class PlanInspeccionMaterialesModel {

    
    public final String RESULTADO_INSPECCION_CUMPLE = "C";
    public final String RESULTADO_INSPECCION_RECHAZO = "R";
    
    public final String TIPO_INSPECCION_CERTIFICADO_DE_MATERIAL = "Certificado de material";
    public final String TIPO_INSPECCION_IDENTIFICACION_DE_MATERIA_PRIMA = "Identificación de materia prima";
    public final String TIPO_INSPECCION_APARIENCIA = "Apariencia";
    public final String TIPO_INSPECCION_EMPAQUE = "Empaque";
    public final String TIPO_INSPECCION_DIMENCION_1 = "DIMENCION1";
    public final String TIPO_INSPECCION_DIMENCION_2 = "DIMENCION2";
    
    public final String [] LISTA_TIPOS_INSPECCION = {TIPO_INSPECCION_CERTIFICADO_DE_MATERIAL,TIPO_INSPECCION_IDENTIFICACION_DE_MATERIA_PRIMA
                                            ,TIPO_INSPECCION_APARIENCIA, TIPO_INSPECCION_EMPAQUE};
    
    public final String [] LISTA_TIPOS_INSPECCION_DIMENCIONES = {TIPO_INSPECCION_DIMENCION_1,TIPO_INSPECCION_DIMENCION_2};
    
    public final int TIPO_LISTA_INSPECCION_PROPIEDADES = 1;
    public final int TIPO_LISTA_INSPECCION_DIMENCIONES = 2;
    /**
     * 
     * @param materialSeleccionado
     * 
     */
    
    public void actualizarInformacion(EntradaMaterial materialSeleccionado) {
          
        Connection c = Conexion.getInstance().getConexion();
        String query = "{CALL actualizar_entrada_material(?,?,?,?,?,?,?)}";
        try {
            
            CallableStatement cs = c.prepareCall(query);
            
            cs.setInt(1, materialSeleccionado.getNoEntradaMaterial());
            cs.setString(2, materialSeleccionado.getNoParte());
            cs.setString(3, materialSeleccionado.getFactura());
            cs.setString(4, materialSeleccionado.getComentarios());
            cs.setString(5, materialSeleccionado.getDescEstado());
            cs.setString(6, materialSeleccionado.getDescLote());
            cs.registerOutParameter(7, Types.VARCHAR);
            cs.execute();
            JOptionPane.showMessageDialog(null, cs.getString(7));
            
        } catch (HeadlessException | SQLException e) {
            System.err.println("error: paquete: RequisicionModel, Class: PlanInspeccionModel Metodo:ActualizarInformacion " + e.getMessage());
        }
          
    }
    
    public String[] obtenerDimencionesMaterial(String descMaterial){
        Connection c = Conexion.getInstance().getConexion();
        String[] valorDimenciones = new String[2];
       
        if(c!=null)
            try {
                String sql = "SELECT desc_dimencion,longitud_barra FROM ver_materiales WHERE "
                        + "CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) = '"+descMaterial+"';";
                
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs.first()){
                    valorDimenciones[0] = rs.getString(1);
                    valorDimenciones[1] = rs.getString(2);
                }
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete: RequisicionModel, Class: PlanInspeccionModel Metodo:PlanInspeccionMaterialesModel " + e.getMessage());
            }
        
        return valorDimenciones;
    }

    public void registrarInspeccionEntrada(InspeccionEntrada[][] listaInspeccionEntradas) {
        Connection c = Conexion.getInstance().getConexion();
        int numColumnas = 6;
        if(c!=null)
            try {
                
                String query = "{CALL registrar_inspeccion_propiedades(?,?,?)}";
                for(int i = 0;i<LISTA_TIPOS_INSPECCION.length;i++)
                    for(int j = 0;j<numColumnas;j++){
                        InspeccionEntrada entrada = listaInspeccionEntradas[i][j];
                        CallableStatement cs = c.prepareCall(query);
                        cs.setInt(1, entrada.getNoEntradaMaterial());
                        cs.setString(2, entrada.getDescTipoInspeccion());
                        cs.setString(3, entrada.getDescResultadoInspeccion());
                        cs.execute();
                    }
                
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete: RequisicionModel, Class: PlanInspeccionModel Metodo:registrarInspeccionEntrada " + e.getMessage());
            }
    }

    public void registrarInspeccionDimenciones(InspeccionDimencion[][] listaInspeccionDimenciones) {
        Connection c = Conexion.getInstance().getConexion();
        int numColumnas = 6;
        if(c!=null)
            try {
                String query = "{CALL registrar_inspeccion_dimenciones(?,?,?)}";
                for(int i = 0;i<LISTA_TIPOS_INSPECCION_DIMENCIONES.length;i++)
                    for(int j = 0;j<numColumnas;j++){
                        InspeccionDimencion entrada = listaInspeccionDimenciones[i][j];
                        CallableStatement cs = c.prepareCall(query);
                        cs.setInt(1, entrada.getNoEntradaMaterial());
                        cs.setString(2, entrada.getDescTipoInspeccion());
                        cs.setFloat(3, entrada.getResultadoInspeccion());
                        cs.execute();
                    }
                c.close();
            } catch (SQLException e) {
                System.err.println("error: paquete: RequisicionModel, Class: PlanInspeccionModel Metodo:registrarInspeccionEntrada " + e.getMessage());
            }
    }

    public ArrayList<Object> obtenerListasResultados(int noMaterialRegistrado,int tipoLista){
        ArrayList<Object> lista = new ArrayList<>();
        String query = "";
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                Statement st = c.createStatement();
                ResultSet rs;
                switch(tipoLista){
                    case TIPO_LISTA_INSPECCION_PROPIEDADES:
                        query = "SELECT desc_resultado_inspeccion,desc_tipo_inspeccion FROM resultados_inspeccion_propieades WHERE id_entrada_material = "+noMaterialRegistrado+"";
                        rs = st.executeQuery(query);
                        if(rs.first())
                            do {                                
                                InspeccionEntrada entrada = new InspeccionEntrada();
                                entrada.setDescResultadoInspeccion(rs.getString(1));
                                entrada.setDescTipoInspeccion(rs.getString(2));
                                lista.add(entrada);
                            } while (rs.next());
                        break;
                    case TIPO_LISTA_INSPECCION_DIMENCIONES:
                        query = "SELECT resultado_inspeccion,desc_tipo_inspeccion FROM resultados_inspeccion_dimenciones WHERE id_entrada_material = "+noMaterialRegistrado+"";
                        rs = st.executeQuery(query);
                        if(rs.first())
                            do {                                
                                InspeccionDimencion dimencion = new InspeccionDimencion();
                                dimencion.setResultadoInspeccion(rs.getFloat(1));
                                dimencion.setDescTipoInspeccion(rs.getString(2));
                                lista.add(dimencion);
                            } while (rs.next());
                        break;
                }    
            
            } catch (SQLException e) {
                System.err.println("error: paquete: RequisicionModel, Class: PlanInspeccionModel Metodo:obtenerListaResultados " + e.getMessage());
            }
            
        return lista;
    }
    
}
