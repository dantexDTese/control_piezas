
package Model.EtiquetasModel;


public class Etiqueta {
    
    private int noEtiqueta;
    private String codigoEtiqueta;
    private String fecha;
    private String folio;
    private int piezasPorBolsa;
    private int piezasTotales;

    public Etiqueta(int noEtiqueta){
        
    }

    public Etiqueta(){
        
    }
    
    public int getNoEtiqueta() {
        return noEtiqueta;
    }

    public void setNoEtiqueta(int noEtiqueta) {
        this.noEtiqueta = noEtiqueta;
    }

    public String getCodigoEtiqueta() {
        return codigoEtiqueta;
    }

    public void setCodigoEtiqueta(String codigoEtiqueta) {
        this.codigoEtiqueta = codigoEtiqueta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getPiezasPorBolsa() {
        return piezasPorBolsa;
    }

    public void setPiezasPorBolsa(int piezasPorBolsa) {
        this.piezasPorBolsa = piezasPorBolsa;
    }

    public int getPiezasTotales() {
        return piezasTotales;
    }

    public void setPiezasTotales(int piezasTotales) {
        this.piezasTotales = piezasTotales;
    }
    
    
    
}
