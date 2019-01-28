
package Model;

import Model.ProduccionModel.LoteProduccion;


public class TiempoMuertoLote extends LoteProduccion{
    
    private int noTiempoMuertoLote;
    private String descTiempoMuerto;

    public TiempoMuertoLote() {
    
    }

    public int getNoTiempoMuertoLote() {
        return noTiempoMuertoLote;
    }

    public void setNoTiempoMuertoLote(int noTiempoMuertoLote) {
        this.noTiempoMuertoLote = noTiempoMuertoLote;
    }

    public String getDescTiempoMuerto() {
        return descTiempoMuerto;
    }

    public void setDescTiempoMuerto(String descTiempoMuerto) {
        this.descTiempoMuerto = descTiempoMuerto;
    }
    
}
