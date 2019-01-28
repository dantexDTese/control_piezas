package Model;

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
    
    public LotePlaneado(int noOrdenTrabajo,String codProducto,int noOrdenProduccion,int cantidadCliente,
    String descTipoMaterial,String descDimencion,String descForma,String claveForma,int noMaterial){
        super(noOrdenTrabajo, noOrdenProduccion, codProducto, cantidadCliente,noMaterial,descTipoMaterial,descDimencion,descForma,claveForma);
    }

    public void setDescMaquina(String descMaquina) {
        this.descMaquina = descMaquina;
    }
    
    public LotePlaneado(){
        
    }

    public void setNoLotePlaneado(int noLotePlaneado) {
        this.noLotePlaneado = noLotePlaneado;
    }

    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public void setCantidadPlaneada(int cantidadPlaneada) {
        this.cantidadPlaneada = cantidadPlaneada;
    }

    public void setFechaPlaneada(String fechaPlaneada) {
        this.fechaPlaneada = fechaPlaneada;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
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
