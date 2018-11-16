package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    
    private Connection nuevaConexion;
    private final String driver = "com.mysql.jdbc.Driver";
    /**
     * CONEXION REMOTA
     *
     * 
    */
    private final String host = "69.175.92.67:3306/";
    private final String user = "tlpnowlq_gmcmmt";
    private final String password = ",2004conyla";
    private final String dataBaseName = "tlpnowlq_control_piezas";
    
    /**
    * CONEXION REMOTA LOCAL
    private static final String host = "localhost/";
    private static final String user = "root";
    private static final String password = "sasa";
    private static final String dataBaseName = "control_piezas_2";
    */
    
    private final String url="jdbc:mysql://"+host+dataBaseName;
    
    private Conexion() {
        nuevaConexion = null;
        try {
            Class.forName(driver);
            nuevaConexion = DriverManager.getConnection(url+"?useSSL=false&noAccessToProcedureBodies=true",user,password);
        } catch (Exception e) {
            System.err.println("error:"+e.getMessage());
        }
    }
    
    public static Conexion getInstance(){
        Conexion conexion = new Conexion();
        return conexion;
    }
    
    public Connection getConexion(){
        return nuevaConexion;
    }
    
    public void desconectar(){
        try {
            nuevaConexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
