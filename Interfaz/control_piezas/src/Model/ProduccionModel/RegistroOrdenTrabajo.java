package Model.ProduccionModel;

public class RegistroOrdenTrabajo {

    private final int noOrdenProduccion;
    private final String fechaRegistro;
    private final String claveProducto;
    private final int cantidadCliente;
    private final String fechaInicio;
    private final String fechaFin;
    private final int noPedido;
    private final String fechaEntrega;
    private final String descEstados;
    private final String observaciones;

    public RegistroOrdenTrabajo(int noOrdenProduccion, String fechaRegistro,String claveProducto,
            int cantidadCliente, String fechaInicio, String fechaFin, int noPedido, String fechaEntrega, String descEstados, String observaciones) {
        this.noOrdenProduccion = noOrdenProduccion;
        this.fechaRegistro = fechaRegistro;
        this.cantidadCliente = cantidadCliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.noPedido = noPedido;
        this.fechaEntrega = fechaEntrega;
        this.descEstados = descEstados;
        this.observaciones = observaciones;
        this.claveProducto = claveProducto;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    
    
    public int getNoOrdenProduccion() {
        return noOrdenProduccion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public int getCantidadCliente() {
        return cantidadCliente;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public int getNoPedido() {
        return noPedido;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public String getDescEstados() {
        return descEstados;
    }

    public String getObservaciones() {
        return observaciones;
    }
    
}
