
package Model.PedidosModel;


public class procedimientoTotal extends ProductosPendientes{

    private final  String procesoActual;
    private final  int noOrdenTrabajo;

    public procedimientoTotal(
            int noOrdenTrabajo, 
            String claveProducto,
            int qty, 
            String noOrdenCompra,
            int noOrdenProduccion,
            int piecesByShift,
            String material
            , float worker,String procesoActual) {
        super(claveProducto, qty, noOrdenCompra, noOrdenProduccion, piecesByShift, material, worker);
        this.procesoActual = procesoActual;
        this.noOrdenTrabajo = noOrdenTrabajo;
    }

    

    public String getProcesoActual() {
        return procesoActual;
    }

    public int getNoOrdenTrabajo() {
        return noOrdenTrabajo;
    }

}
