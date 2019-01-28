package Model.RequisicionesModel;


    
public class MaterialesRequisicion{
    
        private int noRequisicion;
        private int noMaterial;
        private String descTipoMaterial;
        private String descDimencion;
        private String claveForma;
        private int barrasNecesarias;
        private String descEstado;
        private String descMaterial;

    public MaterialesRequisicion() {
        
    }

    public String getDescMaterial() {
        return descMaterial;
    }

    public void setDescMaterial(String descMaterial) {
        this.descMaterial = descMaterial;
    }

    public int getNoMaterial() {
        return noMaterial;
    }

    public void setNoMaterial(int noMaterial) {
        this.noMaterial = noMaterial;
    }
       
    public int getNoRequisicion() {
        return noRequisicion;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public String getClaveForma() {
        return claveForma;
    }

    public void setClaveForma(String claveForma) {
        this.claveForma = claveForma;
    }
   

    public void setNoRequisicion(int noRequisicion) {
        this.noRequisicion = noRequisicion;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    
    public String getDescTipoMaterial() {
        return descTipoMaterial;
    }

    public void setDescTipoMaterial(String descTipoMaterial) {
        this.descTipoMaterial = descTipoMaterial;
    }

    public String getDescDimencion() {
        return descDimencion;
    }

    public void setDescDimencion(String descDimencion) {
        this.descDimencion = descDimencion;
    }

    public int getBarrasNecesarias() {
        return barrasNecesarias;
    }

    public void setBarrasNecesarias(int barrasNecesarias) {
        this.barrasNecesarias = barrasNecesarias;
    }
    
    
    public String materialToString(){
        return descTipoMaterial + " " +descDimencion + " " + claveForma;
                
    }
 
    
              
 }
    
