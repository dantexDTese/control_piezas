package Model;

public class ordenProduccion extends OrdenTrabajo{
    
    private int noOrdenProduccion;
    private String codProducto;
    private String descEstado;
    private String descEmpaque;
    private int turnosReales;
    private int cantidadCliente;
    private int cantidadTotal;
    private String fechaRegistro;
    private float worker;
    private float turnosNecesarios;
    private String fechaMontaje;
    private String fechaInicio;
    private String fechaFin;
    private String observaciones;
    private int piezasPorTurno;
    private String descMateria;
    private int barrasNecesarias;
    
    
    
    public ordenProduccion(){
        
    }
    
    public ordenProduccion(int noOrdenProduccio,String claveProducto,int cantidadCliente){
        this.noOrdenProduccion = noOrdenProduccio;
        this.codProducto = claveProducto;
        this.cantidadCliente = cantidadCliente;
    }

    public ordenProduccion(int noOrdenProduccion){
        this.noOrdenProduccion = noOrdenProduccion;
    }
    
    
    public ordenProduccion(String codProducto,int cantidadCliente){
        this.codProducto = codProducto;
        this.cantidadCliente = cantidadCliente;
    }
    
    
    public ordenProduccion(String codProducto, String descMaterial, 
            int cantidadCliente, int cantidadTotal, int piezasPorTurno, String fechaRegistro
            ,int barrasNecesarias) {
        
        this.codProducto = codProducto;
        this.descMateria = descMaterial;
        this.cantidadCliente = cantidadCliente;
        this.cantidadTotal = cantidadTotal;
        this.piezasPorTurno = piezasPorTurno;
        this.fechaRegistro = fechaRegistro;   
        this.barrasNecesarias = barrasNecesarias;
        
    }

    public int getNoOrdenProduccion() {
        return noOrdenProduccion;
    }
    
    public int getBarrasNecesarias() {
        return barrasNecesarias;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public String getDescMateria() {
        return descMateria;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public String getDescEmpaque() {
        return descEmpaque;
    }

    public void setDescEmpaque(String descEmpaque) {
        this.descEmpaque = descEmpaque;
    }

    public int getTurnosReales() {
        return turnosReales;
    }

    public void setTurnosReales(int turnosReales) {
        this.turnosReales = turnosReales;
    }

    public int getCantidadCliente() {
        return cantidadCliente;
    }

    public void setCantidadCliente(int cantidadCliente) {
        this.cantidadCliente = cantidadCliente;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public float getWorker() {
        return worker;
    }

    public void setWorker(float worker) {
        this.worker = worker;
    }

    public float getTurnosNecesarios() {
        return turnosNecesarios;
    }

    public void setTurnosNecesarios(float turnosNecesarios) {
        this.turnosNecesarios = turnosNecesarios;
    }

    public String getFechaMontaje() {
        return fechaMontaje;
    }

    public void setFechaMontaje(String fechaMontaje) {
        this.fechaMontaje = fechaMontaje;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getPiezasPorTurno() {
        return piezasPorTurno;
    }

    public void setPiezasPorTurno(int piezasPorTurno) {
        this.piezasPorTurno = piezasPorTurno;
    }
    
    
    
}
