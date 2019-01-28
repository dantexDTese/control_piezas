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
    /**
    * CONEXION REMOTA LOCAL
    * 
    */
    
    
    /*private static final String host = "localhost/";
    private static final String user = "root";
    private static final String password = "";
    private static final String dataBaseName = "control_piezas";*/
    //private static final String host = "gmcsys.ddns.net/";
    
    private static final String host = "192.168.1.100/";
    private static final String user = "sistema";
    private static final String password = "sistema";
    private static final String dataBaseName = "control_piezas";
    
    
    private final String url="jdbc:mysql://"+host+dataBaseName;
    
    private Conexion() {
        nuevaConexion = null;
        try {
            Class.forName(driver);
            nuevaConexion = DriverManager.getConnection(url+"?useSSL=false&noAccessToProcedureBodies=true",user,password);
        } catch (ClassNotFoundException | SQLException e) {
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
