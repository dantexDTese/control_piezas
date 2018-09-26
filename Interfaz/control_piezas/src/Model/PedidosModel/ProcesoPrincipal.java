
package Model.PedidosModel;


public class ProcesoPrincipal {
    
    private String claveProducto;
    private int cantidadProcesada;
    private int cantidadTotal;
    private int cantidadRestante;

    public ProcesoPrincipal(String claveProducto, int cantidadProcesada, int cantidadTotal, int cantidadRestante) {
        this.claveProducto = claveProducto;
        this.cantidadProcesada = cantidadProcesada;
        this.cantidadTotal = cantidadTotal;
        this.cantidadRestante = cantidadRestante;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public int getCantidadProcesada() {
        return cantidadProcesada;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public int getCantidadRestante() {
        return cantidadRestante;
    }

}
