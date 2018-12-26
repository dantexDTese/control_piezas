
package Model.PedidosModel;


public class Pedido {

    /**
     * Atributos
     */
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
    

    /**
     * Constructor 1
     */
    public Pedido(){
        
    }
    
    /**
     * Constructor 2
     * @param noOrdenTrabajo
     * @param estado
     */
    public Pedido(int noOrdenTrabajo, String estado) {
        this.noOrdenTrabajo = noOrdenTrabajo;
        this.estado = estado;
    }
    
    /**
     * Constructor 3
     * @param noOrdenCompra
     * @param noOrdenTrabajo
     */
    public Pedido(String noOrdenCompra,int noOrdenTrabajo){
        this.noOrdenTrabajo = noOrdenTrabajo;
        this.noOrdenCompra = noOrdenCompra;
    }
    
    /**
     * Constructor 4
     * @param noOrdenTrabajo
     * @param noOrdenCompra
     * @param claveProducto
     * @param fechaEntrega
     * @param fechaConfirmacionEntrega
     * @param fechaRecepcion
     * @param estado
     * @param descContacto
     * @param nombreCliente
     * @param cantidadCliente
     */
    public Pedido(int noOrdenTrabajo,String noOrdenCompra,String claveProducto,String fechaEntrega,String fechaConfirmacionEntrega,String fechaRecepcion,String estado,String descContacto,String nombreCliente,int cantidadCliente) {
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
    
    /**
     * Constructor 5
     */
    Pedido(int noOrdenTrabajo,String noOrdenCompra, String fechaRecepcion) {
        this.noOrdenCompra = noOrdenCompra;
        this.fechaRecepcion = fechaRecepcion;
        this.noOrdenTrabajo = noOrdenTrabajo;
    }
    
    /**
     * Propiedades
     */
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
