
package Model;

public class Pedido {
    
    private int noPedido;
    private String descEstado;
    private String descContacto;
    private String descCliente;
    private String fechaRecepcion;
    private String fechaentrega;
    private String noOrdenCompra;
    private String fechaConfirmacionEntrega;

    public Pedido() {
    }

    
    
    public Pedido(int noPedido, String noOrdenCompra,String descCliente) {
        this.noPedido = noPedido;
        this.noOrdenCompra = noOrdenCompra;
        this.descCliente = descCliente;
    }

    public int getNoPedido() {
        return noPedido;
    }

    public void setNoPedido(int noPedido) {
        this.noPedido = noPedido;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public String getDescContacto() {
        return descContacto;
    }

    public void setDescContacto(String descContacto) {
        this.descContacto = descContacto;
    }

    public String getDescCliente() {
        return descCliente;
    }

    public void setDescCliente(String descCliente) {
        this.descCliente = descCliente;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(String fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public String getNoOrdenCompra() {
        return noOrdenCompra;
    }

    public void setNoOrdenCompra(String noOrdenCompra) {
        this.noOrdenCompra = noOrdenCompra;
    }

    public String getFechaConfirmacionEntrega() {
        return fechaConfirmacionEntrega;
    }

    public void setFechaConfirmacionEntrega(String fechaConfirmacionEntrega) {
        this.fechaConfirmacionEntrega = fechaConfirmacionEntrega;
    }
    
    
    
    
    
    
}
