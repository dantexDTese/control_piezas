
package Model.PedidosModel;


public class lotesProduccion {

    private final String fechaTrabaho;
    private final int CantidadTrabajada;

    public lotesProduccion( int CantidadTrabajada, String fechaTrabaho) {
        this.fechaTrabaho = fechaTrabaho;
        this.CantidadTrabajada = CantidadTrabajada;
    }

    public lotesProduccion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFechaTrabaho() {
        return fechaTrabaho;
    }

    public int getCantidadTrabajada() {
        return CantidadTrabajada;
    }
    
}
