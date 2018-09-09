
package Model.PedidosModel;


public class Parcialidad {


    
    String noOrden;
    String fecha_entrega;
    int cantidad;

    public Parcialidad(String noOrden, String fecha_entrega, int cantidad) {
        this.noOrden = noOrden;
        this.fecha_entrega = fecha_entrega;
        this.cantidad = cantidad;
    }

    public String getNoOrden() {
        return noOrden;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    
    
}
