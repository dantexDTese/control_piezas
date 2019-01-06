package Model;

public class OrdenTrabajo extends Pedido{
    
    private int ordenTrabajo;
    private String descEstado;
    private String fechaInicio;
    private String fechaFin;
    private String observaciones;

    public OrdenTrabajo() {
        
    }

    
    
    public OrdenTrabajo(int noPedido,int ordenTrabajo, String noOrdenCompra,String nombreCliente) {
        super(noPedido, noOrdenCompra,nombreCliente);
        this.ordenTrabajo = ordenTrabajo;
    }
    

    public int getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(int ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
