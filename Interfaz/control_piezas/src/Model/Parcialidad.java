
package Model;

import Model.ordenProduccion;


public class Parcialidad extends ordenProduccion{

    String fechaEntrega;
    int cantidadEntregada;

    public Parcialidad(String fechaEntrega, int cantidadEntregada,String codProducto,String codPedido) {
        super(codPedido,codProducto);
        
        this.fechaEntrega = fechaEntrega;
        this.cantidadEntregada = cantidadEntregada;
    }
    
    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public int getCantidadEntregada() {
        return cantidadEntregada;
    }
        
}
