
package Model.RequisicionesModel;

/**
 *
 * @author cesar
 */
public class InspeccionDimencion extends EntradaMaterial{
    
    private int noInspeccionDimencion;
    private String descTipoInspeccion;
    private float resultadoInspeccion;

    public InspeccionDimencion() {

    }
    
    public int getNoInspeccionDimencion() {
        return noInspeccionDimencion;
    }

    public void setNoInspeccionDimencion(int noInspeccionDimencion) {
        this.noInspeccionDimencion = noInspeccionDimencion;
    }

    public String getDescTipoInspeccion() {
        return descTipoInspeccion;
    }

    public void setDescTipoInspeccion(String descTipoInspeccion) {
        this.descTipoInspeccion = descTipoInspeccion;
    }

    public float getResultadoInspeccion() {
        return resultadoInspeccion;
    }

    public void setResultadoInspeccion(float resultadoInspeccion) {
        this.resultadoInspeccion = resultadoInspeccion;
    }
    
    
    
}
