
package Model;




public class OrdenProductoActivo extends ordenProducto{
    
    private String procesoProduccion;
    private String noOrdenProducto;
    private String lote;
    private String noOperador;
    
    
    public OrdenProductoActivo(String codProducto, String descMaquina,
            String descMaterial, int cantidadSolicitada, int cantidadProducir,
            int cantidadPorTurno, String fecha, int barrasNecesarias,
            String procesoProduccion,String noOrdenProducto,String noOperador) {
        
        super(codProducto, descMaquina, descMaterial, cantidadSolicitada,
                cantidadProducir, cantidadPorTurno, fecha, barrasNecesarias);
        
        this.procesoProduccion = procesoProduccion;
        this.noOrdenProducto = noOrdenProducto;
        this.noOperador = noOperador;
    }

    public String getProcesoProduccion() {
        return procesoProduccion;
    }

    public String getNoOrdenProducto() {
        return noOrdenProducto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNoOperador() {
        return noOperador;
    }
    
}
