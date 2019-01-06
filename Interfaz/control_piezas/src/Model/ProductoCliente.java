
package Model;


public class ProductoCliente {
    
    private int noProductoCliente;
    private String descCliente;
    private String claveProducto;

    public ProductoCliente(int noMaterialCliente, String descCliente, String claveProducto) {
        this.noProductoCliente = noMaterialCliente;
        this.descCliente = descCliente;
        this.claveProducto = claveProducto;
    }
    
    public ProductoCliente(int noProductoCliente) {
        this.noProductoCliente = noProductoCliente;
        
    }

    public ProductoCliente() {
        
    }
    
    

    public int getNoProductoCliente() {
        return noProductoCliente;
    }

    public void setNoProductoCliente(int noProductoCliente) {
        this.noProductoCliente = noProductoCliente;
    }

    public String getDescCliente() {
        return descCliente;
    }

    public void setDescCliente(String descCliente) {
        this.descCliente = descCliente;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        this.claveProducto = claveProducto;
    }
    
    



    
}
