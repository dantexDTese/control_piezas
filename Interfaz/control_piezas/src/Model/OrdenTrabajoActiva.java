
package Model;


public class OrdenTrabajoActiva {

    private String descOrdenTrabajo;
    private String Cliente;

    public OrdenTrabajoActiva(String descOrdenTrabajo, String Cliente) {
        this.descOrdenTrabajo = descOrdenTrabajo;
        this.Cliente = Cliente;
    }

    public String getDescOrdenTrabajo() {
        return descOrdenTrabajo;
    }

    public String getCliente() {
        return Cliente;
    }
    
}
