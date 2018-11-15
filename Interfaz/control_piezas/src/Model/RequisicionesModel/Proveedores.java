
package Model.RequisicionesModel;


public class Proveedores {

    private final int noProveedor;
    private final String descProveedor;
    private final String direccion;
    private final float IVA;

    public Proveedores(int noProveedor, String descProveedor, String direccion,float IVA) {
        this.noProveedor = noProveedor;
        this.descProveedor = descProveedor;
        this.direccion = direccion;
        this.IVA = IVA;
    }

    public float getIVA() {
        return IVA;
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
