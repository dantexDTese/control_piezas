
package Model;


public class ProductoMaquina {
    
    private int noProductoMaquina;
    private int claveProducto;
    private int piezasPorTurno;
    private int piezasPorBarra;
    private int piezasPorHora;
    private String descMaquina;
    private String claveTipoProceso;
    private String descTipoProceso;

    public ProductoMaquina(){
        
    }
    
    public ProductoMaquina(int noProductoMaquina, int claveProducto, int piezasPorTurno, int piezasPorBarra, int piezasPorHora, String descMaquina, String claveTipoProceso, String descTipoProceso) {
        this.noProductoMaquina = noProductoMaquina;
        this.claveProducto = claveProducto;
        this.piezasPorTurno = piezasPorTurno;
        this.piezasPorBarra = piezasPorBarra;
        this.piezasPorHora = piezasPorHora;
        this.descMaquina = descMaquina;
        this.claveTipoProceso = claveTipoProceso;
        this.descTipoProceso = descTipoProceso;
    }

    public ProductoMaquina(int piezasPorTurno, int piezasPorHora, String descMaquina,int noProductoMaquina) {
        this.noProductoMaquina = noProductoMaquina;
        this.piezasPorTurno = piezasPorTurno;
        this.piezasPorHora = piezasPorHora;
        this.descMaquina = descMaquina;
    }

    
    public int getNoProductoMaquina() {
        return noProductoMaquina;
    }

    public void setNoProductoMaquina(int noProductoMaquina) {
        this.noProductoMaquina = noProductoMaquina;
    }

    public int getClaveProducto() {
        return claveProducto;
    }

    public void setClaveProducto(int claveProducto) {
        this.claveProducto = claveProducto;
    }

    public int getPiezasPorTurno() {
        return piezasPorTurno;
    }

    public void setPiezasPorTurno(int piezasPorTurno) {
        this.piezasPorTurno = piezasPorTurno;
    }

    public int getPiezasPorBarra() {
        return piezasPorBarra;
    }

    public void setPiezasPorBarra(int piezasPorBarra) {
        this.piezasPorBarra = piezasPorBarra;
    }

    public int getPiezasPorHora() {
        return piezasPorHora;
    }

    public void setPiezasPorHora(int piezasPorHora) {
        this.piezasPorHora = piezasPorHora;
    }

    public String getDescMaquina() {
        return descMaquina;
    }

    public void setDescMaquina(String descMaquina) {
        this.descMaquina = descMaquina;
    }

    public String getClaveTipoProceso() {
        return claveTipoProceso;
    }

    public void setClaveTipoProceso(String claveTipoProceso) {
        this.claveTipoProceso = claveTipoProceso;
    }

    public String getDescTipoProceso() {
        return descTipoProceso;
    }

    public void setDescTipoProceso(String descTipoProceso) {
        this.descTipoProceso = descTipoProceso;
    }
    
    
    
}
