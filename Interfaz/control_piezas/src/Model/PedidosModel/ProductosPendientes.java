
package Model.PedidosModel;


public class ProductosPendientes {

    String noOrdenCompra;
    int noOrdenProduccion;
    String claveProducto;
    int cantidadCliente;

    public ProductosPendientes(String noOrdenCompra, int noOrdenProduccion, String claveProducto, int cantidadCliente) {
        this.noOrdenCompra = noOrdenCompra;
        this.noOrdenProduccion = noOrdenProduccion;
        this.claveProducto = claveProducto;
        this.cantidadCliente = cantidadCliente;
    }
        
    public String getNoOrdenCompra() {
        return noOrdenCompra;
    }

    public int getNoOrdenProduccion() {
        return noOrdenProduccion;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public int getCantidadCliente() {
        return cantidadCliente;
    }
    
    
    
    
}
