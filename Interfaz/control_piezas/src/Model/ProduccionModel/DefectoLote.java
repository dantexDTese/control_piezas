
package Model.ProduccionModel;

import Model.ProduccionModel.LoteProduccion;



public class DefectoLote extends LoteProduccion{
    
    
    private int noDefectoLote;
    private String descDefectoProduccion;
    private int cantidad;
    
    public DefectoLote(){
        
        
    }

    public int getNoDefectoLote() {
        return noDefectoLote;
    }

    public void setNoDefectoLote(int noDefectoLote) {
        this.noDefectoLote = noDefectoLote;
    }

    public String getDescDefectoProduccion() {
        return descDefectoProduccion;
    }

    public void setDescDefectoProduccion(String descDefectoProduccion) {
        this.descDefectoProduccion = descDefectoProduccion;
    }

    

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    
}
