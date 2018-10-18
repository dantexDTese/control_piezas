
package Model.PedidosModel;


public class Pedido {

    private int noOrdenTrabajo;
    private String noOrdenCompra;
    private String fechaEntrega;
    private String fechaConfirmacionEntrega;
    private String fechaRecepcion;
    private String descContacto;
    private String nombreCliente;
    private String claveProducto;
    private int cantidadCliente;
    
    private String estado;
    

    public Pedido(){
        
    }
    
    

    public Pedido(int noOrdenTrabajo, String noOrdenCompra, String fechaEntrega, String fechaConfirmacionEntrega, String fechaRecepcion, String descContacto, String nombreCliente, String claveProducto, int cantidadCliente, String estado) {
        this.noOrdenTrabajo = noOrdenTrabajo;
        this.noOrdenCompra = noOrdenCompra;
        this.fechaEntrega = fechaEntrega;
        this.fechaConfirmacionEntrega = fechaConfirmacionEntrega;
        this.fechaRecepcion = fechaRecepcion;
        this.descContacto = descContacto;
        this.nombreCliente = nombreCliente;
        this.claveProducto = claveProducto;
        this.cantidadCliente = cantidadCliente;
        this.estado = estado;
    }
    
    
    Pedido(String noOrdenCompra,int noOrdenTrabajo, String fechaRecepcion) {
        this.noOrdenCompra = noOrdenCompra;
        this.fechaRecepcion = fechaRecepcion;
        this.noOrdenTrabajo = noOrdenTrabajo;
    }

    public String getEstado() {
        return estado;
    }

    public int getNoOrdenTrabajo() {
        return noOrdenTrabajo;
    }

    public String getNoOrdenCompra() {
        return noOrdenCompra;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public String getFechaConfirmacionEntrega() {
        return fechaConfirmacionEntrega;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public String getDescContacto() {
        return descContacto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public int getCantidadCliente() {
        return cantidadCliente;
    }
    
    
}
