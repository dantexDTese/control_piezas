
package Model.RequisicionesModel;


public class Proveedores {

    private final int noProveedor;
    private final String descProveedor;
    private final String direccion;

    public Proveedores(int noProveedor, String descProveedor, String direccion) {
        this.noProveedor = noProveedor;
        this.descProveedor = descProveedor;
        this.direccion = direccion;
    }

    public int getNoProveedor() {
        return noProveedor;
    }

    public String getDescProveedor() {
        return descProveedor;
    }

    public String getDireccion() {
        return direccion;
    }
    
}
