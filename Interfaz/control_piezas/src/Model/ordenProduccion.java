package Model;

public class ordenProduccion extends OrdenTrabajo{
    
    private int noOrdenProduccion;
    private String codProducto;
    private String descProducto;
    private String descEstadoOrdenProduccion;
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
    private float barrasNecesarias;
    private int noMaterial;
    private String descTipoMaterial;
    private String descDimencion;
    private String descForma;
    private String claveForma;
    private String fechaDesmontaje;
    private int barrasSelecciondas;
    private boolean validacion_compras;
    private boolean validacion_produccion;
    private boolean validacion_matenimiento;
    private boolean validacion_calidad;
    private float piezasFaltantes;
    
    public ordenProduccion(){
        
    }

    public float getPiezasFaltantes() {
        return piezasFaltantes;
    }

    public void setPiezasFaltantes(float piezasFaltantes) {
        this.piezasFaltantes = piezasFaltantes;
    }
    
    
    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public boolean isValidacion_compras() {
        return validacion_compras;
    }

    public void setValidacion_compras(boolean validacion_compras) {
        this.validacion_compras = validacion_compras;
    }

    public boolean isValidacion_produccion() {
        return validacion_produccion;
    }

    public void setValidacion_produccion(boolean validacion_produccion) {
        this.validacion_produccion = validacion_produccion;
    }

    public boolean isValidacion_matenimiento() {
        return validacion_matenimiento;
    }

    public void setValidacion_matenimiento(boolean validacion_matenimiento) {
        this.validacion_matenimiento = validacion_matenimiento;
    }

    public boolean isValidacion_calidad() {
        return validacion_calidad;
    }

    public void setValidacion_calidad(boolean validacion_calidad) {
        this.validacion_calidad = validacion_calidad;
    }

    public int getBarrasSelecciondas() {
        return barrasSelecciondas;
    }

    public void setBarrasSelecciondas(int barrasSelecciondas) {
        this.barrasSelecciondas = barrasSelecciondas;
    }
    
    public ordenProduccion(String  codPedido,String codProducto){
        super(codPedido);
        this.codProducto = codProducto;
    }
    
    public ordenProduccion(int noOrdenProduccio,String claveProducto,int cantidadCliente){
        this.noOrdenProduccion = noOrdenProduccio;
        this.codProducto = claveProducto;
        this.cantidadCliente = cantidadCliente;
    }
    
    public ordenProduccion(int noOrdenProduccio,int cantidadTotal,String claveProducto){
        this.noOrdenProduccion = noOrdenProduccio;
        this.codProducto = claveProducto;
        this.cantidadTotal = cantidadTotal;
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
        this.cantidadCliente = cantidadCliente;
        this.cantidadTotal = cantidadTotal;
        this.piezasPorTurno = piezasPorTurno;
        this.fechaRegistro = fechaRegistro;   
        this.barrasNecesarias = barrasNecesarias;
        
    }

    public ordenProduccion(int noOrdenTrabajo, int noOrdenProduccion, String codProducto, int cantidadCliente,
            int noMaterial,String descTipoMaterial,String descDimencion,String descForma,String claveForma) {
         super(noOrdenTrabajo);
         
         this.noOrdenProduccion = noOrdenProduccion;
         this.codProducto = codProducto;
         this.cantidadCliente = cantidadCliente;
         this.descTipoMaterial = descTipoMaterial;
         this.descDimencion = descDimencion;
         this.descForma = descForma;
         this.claveForma = claveForma;
         this.noMaterial = noMaterial;
    
    }

    public float getBarrasNecesarias() {
        return barrasNecesarias;
    }

    public void setBarrasNecesarias(float barrasNecesarias) {
        this.barrasNecesarias = barrasNecesarias;
    }

    public void setNoOrdenProduccion(int noOrdenProduccion) {
        this.noOrdenProduccion = noOrdenProduccion;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public void setBarrasNecesarias(int barrasNecesarias) {
        this.barrasNecesarias = barrasNecesarias;
    }

    public String getFechaDesmontaje() {
        return fechaDesmontaje;
    }

    public void setFechaDesmontaje(String fechaDesmontaje) {
        this.fechaDesmontaje = fechaDesmontaje;
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

    public String getDescDimencion() {
        return descDimencion;
    }

    public void setDescDimencion(String descDimencion) {
        this.descDimencion = descDimencion;
    }

    public String getDescForma() {
        return descForma;
    }

    public void setDescForma(String descForma) {
        this.descForma = descForma;
    }

    public String getClaveForma() {
        return claveForma;
    }

    public void setClaveForma(String claveForma) {
        this.claveForma = claveForma;
    }
    
    

    
    public int getNoOrdenProduccion() {
        return noOrdenProduccion;
    }
    
  
    public String getCodProducto() {
        return codProducto;
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
    
    public String getDescEstadoOrdenProduccion() {
        return descEstadoOrdenProduccion;
    }

    public void setDescEstadoOrdenProduccion(String descEstadoOrdenProduccion) {
        this.descEstadoOrdenProduccion = descEstadoOrdenProduccion;
    }
       
    public String materialToString(){
        return descTipoMaterial + " " + descDimencion + " " + claveForma; 
    }
    
}
