
package Model.PedidosModel;


public class ProductosPendientes {

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

    public ProductosPendientes(int noOrdenProduccion, String claveProducto) {
        this.noOrdenProduccion = noOrdenProduccion;
        this.claveProducto = claveProducto;
    }
    
    

    public ProductosPendientes(String noOrdenCompra, int noOrdenProduccion, String claveProducto, int cantidadCliente){
        this.noOrdenCompra = noOrdenCompra;
        this.noOrdenProduccion = noOrdenProduccion;
        this.claveProducto = claveProducto;
        this.cantidadCliente = cantidadCliente;
    }

  
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

    public ProductosPendientes(String claveProducto,int qty,String material ,String fecha_inicio){
        this.claveProducto = claveProducto;
        this.qty = qty;
        this.material = material;
        this.fechaInicio = fecha_inicio;
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
