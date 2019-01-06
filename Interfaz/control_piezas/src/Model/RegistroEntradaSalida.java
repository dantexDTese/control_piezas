
package Model;

public class RegistroEntradaSalida extends AlmacenProductoTerminado{
    
    private final int noEntradaSalida;
    private final String fechaRegistro;
    private final int cantidad;
    private final String descTipoOperacionAlmacen;
    private final int totalRegistrado;

    public RegistroEntradaSalida(
    int noEntradaSalida,int noAlmacenProductoTerminado,String descTipoOperacionAlmacen,String fechaRegistro,int cantidad,int totalRegistrado ) {
        super(noAlmacenProductoTerminado);
        this.noEntradaSalida = noEntradaSalida;
        this.fechaRegistro = fechaRegistro;
        this.cantidad = cantidad;
        this.descTipoOperacionAlmacen = descTipoOperacionAlmacen;
        this.totalRegistrado = totalRegistrado;
    }

    public int getTotalRegistrado() {
        return totalRegistrado;
    }

    public int getNoEntradaSalida() {
        return noEntradaSalida;
    }
    
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getDescTipoOperacionAlmacen() {
        return descTipoOperacionAlmacen;
    }
    
    
}
