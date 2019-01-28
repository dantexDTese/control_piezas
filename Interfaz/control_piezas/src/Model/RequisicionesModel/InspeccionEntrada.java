package Model.RequisicionesModel;

/**
 *
 * @author cesar
 */
public class InspeccionEntrada extends EntradaMaterial{
    
    private int noInspeccionEntrada;
    private String descTipoInspeccion;
    private String descResultadoInspeccion;
    
    public InspeccionEntrada() {
    }

    
    public int getNoInspeccionEntrada() {
        return noInspeccionEntrada;
    
    }

    public void setNoInspeccionEntrada(int noInspeccionEntrada) {
        this.noInspeccionEntrada = noInspeccionEntrada;
    
    }

    public String getDescTipoInspeccion() {
        return descTipoInspeccion;
    
    }

    public void setDescTipoInspeccion(String descTipoInspeccion) {
        this.descTipoInspeccion = descTipoInspeccion;
    
    }

    public String getDescResultadoInspeccion() {
        return descResultadoInspeccion;
    
    }

    public void setDescResultadoInspeccion(String descResultadoInspeccion) {
        this.descResultadoInspeccion = descResultadoInspeccion;
    
    }
    
}
