
package Model;


public class AlmacenProductoTerminado extends ProductoCliente{
    
    private int noAlmacenProductoTerminado;
    private int total;

    public AlmacenProductoTerminado(){    }
    
    public AlmacenProductoTerminado(int noAlmacenProductoTerminado, int total,int noProductoCliente) {
        super(noProductoCliente);
        this.noAlmacenProductoTerminado = noAlmacenProductoTerminado;
        this.total = total;
    }
    
     public AlmacenProductoTerminado(int noAlmacenProductoTerminado, int total,int noMaterialCliente, String descCliente, String claveProducto) {
        super(noMaterialCliente, descCliente, claveProducto);
        this.noAlmacenProductoTerminado = noAlmacenProductoTerminado;
        this.total = total;
    }
     
    public AlmacenProductoTerminado(int noAlmacenProductoTerminado,int noMaterialCliente, String descCliente, String claveProducto) {
        super(noMaterialCliente, descCliente, claveProducto);
        this.noAlmacenProductoTerminado = noAlmacenProductoTerminado;
    }
    
    public AlmacenProductoTerminado(int noAlmacenProductoTerminado) {
        super();
        this.noAlmacenProductoTerminado = noAlmacenProductoTerminado;
    }
    
    public int getNoAlmacenProductoTerminado() {
        return noAlmacenProductoTerminado;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
