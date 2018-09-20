
package Model.PedidosModel;


public class ordenPlaneada {
    
    private int noOrdenProduccion;
    private String descProducto;
    private float nuevo_worker;
    private int nueva_cantidad_total;
    private String desc_maquina;
    private String desc_material;

    public int getNoOrdenProduccion() {
        return noOrdenProduccion;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public float getNuevo_worker() {
        return nuevo_worker;
    }

    public int getNueva_cantidad_total() {
        return nueva_cantidad_total;
    }

    public String getDesc_maquina() {
        return desc_maquina;
    }

    public String getDesc_material() {
        return desc_material;
    }

    public ordenPlaneada(int noOrdenProduccion, String descProducto, float nuevo_worker, int nueva_cantidad_total, String desc_maquina, String desc_material) {
        this.noOrdenProduccion = noOrdenProduccion;
        this.descProducto = descProducto;
        this.nuevo_worker = nuevo_worker;
        this.nueva_cantidad_total = nueva_cantidad_total;
        this.desc_maquina = desc_maquina;
        this.desc_material = desc_material;
    }

    
       
}
