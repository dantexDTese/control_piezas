
package Model.PedidosModel;


public class procedimientoTotal {

   private String noOrdenCompra;
   private int idOrdenProduccion;
   private int cantidadTotal;
   private float worker;
   private String descMaterial;
   private String claveProducto;
   private String descTipoProceso;

    public procedimientoTotal(
            String noOrdenCompra
            ,int idOrdenProduccion, 
            int cantidadTotal,
            float worker,
            String descMaterial,
            String claveProducto,
            String descTipoProceso) {
        this.noOrdenCompra = noOrdenCompra;
        this.idOrdenProduccion = idOrdenProduccion;
        this.cantidadTotal = cantidadTotal;
        this.worker = worker;
        this.descMaterial = descMaterial;
        this.claveProducto = claveProducto;
        this.descTipoProceso = descTipoProceso;
    }

    public String getNoOrdenCompra() {
        return noOrdenCompra;
    }

    public int getIdOrdenProduccion() {
        return idOrdenProduccion;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public float getWorker() {
        return worker;
    }

    public String getDescMaterial() {
        return descMaterial;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public String getDescTipoProceso() {
        return descTipoProceso;
    }
    
        
}
