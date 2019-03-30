
package Model;


public class Sesion {
    
    private boolean estado;
    private String descUsuario;
    public Sesion(){
        
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setDescSecion(String descUsuario) {
        this.descUsuario = descUsuario;
    }

    public String getDescUsuario() {
        return descUsuario;
    }
    
    
        
}
