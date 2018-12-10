
package Model.PedidosModel;


public class ProductosPendientes {

    
    /**
     * ATRIBUTOS
     */
    private String noOrdenCompra;
    private int noOrdenProduccion;
    private final String claveProducto;
    private  int cantidadCliente;
    private int qty;
    private int piecesByShift;
    private String maquina;
    private String material;
    private float worker;
    private String fechaMontaje;
    private String fechaInicio;
    private float barrasNecesarias;
    private int barrasSeleccionadas;
    private String descTipoProceso;
    /**
     * CONSTRUCTOR
     * @param noOrdenProduccion
     * @param claveProducto
     */
    
    public ProductosPendientes(int noOrdenProduccion, String claveProducto) {
        this.noOrdenProduccion = noOrdenProduccion;
        this.claveProducto = claveProducto;
    }
    
    
    /**
     * CONSTRUCTUR
     * @param noOrdenCompra
     * @param noOrdenProduccion
     * @param claveProducto
     * @param cantidadCliente
     */
    

    public ProductosPendientes(String noOrdenCompra, int noOrdenProduccion, String claveProducto, int cantidadCliente){
        this.noOrdenCompra = noOrdenCompra;
        this.noOrdenProduccion = noOrdenProduccion;
        this.claveProducto = claveProducto;
        this.cantidadCliente = cantidadCliente;
    }
    
    /**
     * CONSTRUCTOR
     * @param claveProducto
     * @param qty
     * @param noOrdenCompra
     * @param noOrdenProduccion
     * @param piecesByShift
     * @param material
     * @param worker
     */
  
    public ProductosPendientes(String claveProducto,int qty,String noOrdenCompra, int noOrdenProduccion,int piecesByShift,
     String material,float worker) {
         this.noOrdenCompra = noOrdenCompra;
        this.noOrdenProduccion = noOrdenProduccion;
        this.claveProducto = claveProducto;
        this.qty = qty;
        this.material = material;
        this.worker = worker;
        this.piecesByShift = piecesByShift;
    }
    
    /**
     * CONSTRUCTOR
     * @param noOrdenProduccion
     * @param claveProducto
     * @param qty
     * @param material
     * @param barrasNecesarias
     * @param fecha_inicio
     */

    public ProductosPendientes(int noOrdenProduccion,String claveProducto,int qty,String material, float barrasNecesarias, String fecha_inicio) {
        this.noOrdenProduccion = noOrdenProduccion;
        this.claveProducto = claveProducto;
        this.qty = qty;
        this.material = material;
        this.barrasNecesarias = barrasNecesarias;
        this.fechaInicio = fecha_inicio;
    }

    public String getDescTipoProceso() {
        return descTipoProceso;
    }

    public void setDescTipoProceso(String descTipoProceso) {
        this.descTipoProceso = descTipoProceso;
    }
    
    public void setBarrasSeleccionadas(int barrasSeleccionadas) {
        this.barrasSeleccionadas = barrasSeleccionadas;
    }

    public int getBarrasSeleccionadas() {
        return barrasSeleccionadas;
    }

    public float getBarrasNecesarias() {
        return barrasNecesarias;
    }
        
    public String getNoOrdenCompra() {
        return noOrdenCompra;
    }

    public int getNoOrdenProduccion() {
        return noOrdenProduccion;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public int getCantidadCliente() {
        return cantidadCliente;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPiecesByShift() {
        return piecesByShift;
    }

    public void setPiecesByShift(int piecesByShift) {
        this.piecesByShift = piecesByShift;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public float getWorker() {
        return worker;
    }

    public void setWorker(float worker) {
        this.worker = worker;
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
    
}
