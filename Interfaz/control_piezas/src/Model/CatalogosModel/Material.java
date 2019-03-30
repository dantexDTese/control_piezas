
package Model.CatalogosModel;


public class Material {
    
    private int noMaterial;
    private String descTipoMaterial;
    private String descDimenciones;
    private String claveForma;
    private String descForma;
    private float longitudBarra;

    public Material() {
    
    }

    public int getNoMaterial() {
        return noMaterial;
    }

    public void setNoMaterial(int noMaterial) {
        this.noMaterial = noMaterial;
    }

    public String getDescTipoMaterial() {
        return descTipoMaterial;
    }

    public void setDescTipoMaterial(String descTipoMaterial) {
        this.descTipoMaterial = descTipoMaterial;
    }

    public String getDescDimenciones() {
        return descDimenciones;
    }

    public void setDescDimenciones(String descDimenciones) {
        this.descDimenciones = descDimenciones;
    }

    public String getClaveForma() {
        return claveForma;
    }

    public void setClaveForma(String claveForma) {
        this.claveForma = claveForma;
    }

    public String getDescForma() {
        return descForma;
    }

    public void setDescForma(String descForma) {
        this.descForma = descForma;
    }

    public float getLongitudBarra() {
        return longitudBarra;
    }

    public void setLongitudBarra(float longitudBarra) {
        this.longitudBarra = longitudBarra;
    }
    
}
