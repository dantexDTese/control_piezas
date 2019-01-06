package Model.PedidosModel;

import Model.ordenProduccion;

public class LotePlaneado extends ordenProduccion{
    
    private int     noLotePlaneado;
    private String  tipoProceso;
    private int     cantidadPlaneada;
    private String  fechaPlaneada;
    private String  descMaquina;
    private String  descEstado;
    
    public LotePlaneado(int noLotePlaneado,int noOrdenProduccion,String  tipoProceso,int cantidadPlaneada,String  fechaPlaneada,String  descMaquina,String  descEstado){
        super(noOrdenProduccion);
        this.noLotePlaneado = noLotePlaneado;
        this.tipoProceso = tipoProceso;
        this.cantidadPlaneada = cantidadPlaneada;
        this.fechaPlaneada = fechaPlaneada;
        this.descMaquina = descMaquina;
        this.descEstado = descEstado;
    }

    public LotePlaneado(int noLotePlaneado, int cantidadPlaneada, String descEstado,int noOrdenProduccion) {
        super(noOrdenProduccion);
        this.noLotePlaneado = noLotePlaneado;
        this.cantidadPlaneada = cantidadPlaneada;
        this.descEstado = descEstado;
    }
    
    
    public LotePlaneado(){
        
    }

    public int getNoLotePlaneado() {
        return noLotePlaneado;
    }

    public String getTipoProceso() {
        return tipoProceso;
    }

    public int getCantidadPlaneada() {
        return cantidadPlaneada;
    }

    public String getFechaPlaneada() {
        return fechaPlaneada;
    }

    public String getDescMaquina() {
        return descMaquina;
    }

    public String getDescEstado() {
        return descEstado;
    }
       
}
