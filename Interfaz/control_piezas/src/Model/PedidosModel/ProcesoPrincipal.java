
package Model.PedidosModel;


public class ProcesoPrincipal {
    
    
    private final int cantidadTotal;
    private final String claveProducto;
    private final String descProcesoActual;
    private final String descMaterial;
    private int cantidadProcesada;
    public ProcesoPrincipal(int cantidadTotal, String claveProducto, String descProcesoActual, String descMaterial) {
        this.cantidadTotal = cantidadTotal;
        this.claveProducto = claveProducto;
        this.descProcesoActual = descProcesoActual;
        this.descMaterial = descMaterial;
        cantidadProcesada=0;
    }

    public int getCantidadProcesada() {
        return cantidadProcesada;
    }

    public void setCantidadProcesada(int cantidadProcesada) {
        this.cantidadProcesada = cantidadProcesada;
    }
    
    

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public String getDescProcesoActual() {
        return descProcesoActual;
    }

    public String getDescMaterial() {
        return descMaterial;
    }
    
}
