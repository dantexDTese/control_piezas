
package Model.ProduccionModel;


public class OrdenProduccionGuardada {

    private final int ordenTrabajo;
    private final  String noOrdenCompra;
    private final  String fechaEntregaPedido;
    private final  String fechaConfirmacionEntregaPedido;
    private final  String fechaRecepcionPedido;
    private final  String descContacto;
    private final  String nombreCliente;
    private final  int cantidadCliente;
    private final  String claveProducto;
    private final  int cantidadTotal;
    private final  int ordenProduccion;
    private final  String descMaterial;
    private final  String descMaquina;
    private final  int barrasNecesarias;
    private final  int piezasPorTurno;
    private final  String fechaRegistroOP;
    private final  String fechaMontaje;
    private final  String fechaDesmontaje;
    private final  String fechaInicioOP;
    private final  String fechaFinOP;
    private final  String observacionesOP;

    public OrdenProduccionGuardada(int ordenTrabajo, String noOrdenCompra, 
            String fechaEntregaPedido, String fechaConfirmacionEntregaPedido,
            String fechaRecepcionPedido, String descContacto, String nombreCliente,
            int cantidadCliente, String claveProducto, int cantidadTotal,
            int ordenProduccion, String descMaterial, String descMaquina, 
            int barrasNecesarias, int piezasPorTurno, String fechaRegistroOP,
            String fechaMontaje,String fechaInicioOP, String fechaDesmontaje,
            String fechaFinOP, String observacionesOP) {
        
        this.ordenTrabajo = ordenTrabajo;
        this.noOrdenCompra = noOrdenCompra;
        this.fechaEntregaPedido = fechaEntregaPedido;
        this.fechaConfirmacionEntregaPedido = fechaConfirmacionEntregaPedido;
        this.fechaRecepcionPedido = fechaRecepcionPedido;
        this.descContacto = descContacto;
        this.nombreCliente = nombreCliente;
        this.cantidadCliente = cantidadCliente;
        this.claveProducto = claveProducto;
        this.cantidadTotal = cantidadTotal;
        this.ordenProduccion = ordenProduccion;
        this.descMaterial = descMaterial;
        this.descMaquina = descMaquina;
        this.barrasNecesarias = barrasNecesarias;
        this.piezasPorTurno = piezasPorTurno;
        this.fechaRegistroOP = fechaRegistroOP;
        this.fechaMontaje = fechaMontaje;
        this.fechaDesmontaje = fechaDesmontaje;
        this.fechaInicioOP = fechaInicioOP;
        this.fechaFinOP = fechaFinOP;
        this.observacionesOP = observacionesOP;
    }

    

    
    
    
    public int getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public String getNoOrdenCompra() {
        return noOrdenCompra;
    }

    public String getFechaEntregaPedido() {
        return fechaEntregaPedido;
    }

    public String getFechaConfirmacionEntregaPedido() {
        return fechaConfirmacionEntregaPedido;
    }

    public String getFechaRecepcionPedido() {
        return fechaRecepcionPedido;
    }

    public String getDescContacto() {
        return descContacto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getCantidadCliente() {
        return cantidadCliente;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public int getOrdenProduccion() {
        return ordenProduccion;
    }

    public String getDescMaterial() {
        return descMaterial;
    }

    public String getDescMaquina() {
        return descMaquina;
    }

    public int getBarrasNecesarias() {
        return barrasNecesarias;
    }

    public int getPiezasPorTurno() {
        return piezasPorTurno;
    }

    public String getFechaRegistroOP() {
        return fechaRegistroOP;
    }

    public String getFechaMontaje() {
        return fechaMontaje;
    }

    public String getFechaDesmontaje() {
        return fechaDesmontaje;
    }

    public String getFechaInicioOP() {
        return fechaInicioOP;
    }

    public String getFechaFinOP() {
        return fechaFinOP;
    }

    public String getObservacionesOP() {
        return observacionesOP;
    }
    
    
    
}
