
package Model.PedidosModel;


public class Pedido {

    private int id_pedido;
    private String no_orden_compra;
    private String fecha_entrega;
    private String fecha_confirmacion_entrega;
    private String fecha_recepcion;
    private String desc_contacto;
    private String nombre_cliente;
    private String clave_producto;
    private int cantidad_cliente;
    private String estado;
    

    public Pedido(int id_pedido,
            String no_orden_compra,
            String fecha_entrega,
            String fecha_confirmacion_entrega,
            String fecha_recepcion,
            String estado,
            String desc_contacto,
            String nombre_cliente,
            int cantidad_cliente,
            String clave_producto) {
        this.id_pedido = id_pedido;
        this.no_orden_compra = no_orden_compra;
        this.fecha_entrega = fecha_entrega;
        this.fecha_confirmacion_entrega = fecha_confirmacion_entrega;
        this.fecha_recepcion = fecha_recepcion;
        this.desc_contacto = desc_contacto;
        this.nombre_cliente = nombre_cliente;
        this.clave_producto = clave_producto;
        this.cantidad_cliente = cantidad_cliente;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
    
    public int getId_pedido() {
        return id_pedido;
    }

    public String getNo_orden_compra() {
        return no_orden_compra;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public String getFecha_confirmacion_entrega() {
        return fecha_confirmacion_entrega;
    }

    public String getFecha_recepcion() {
        return fecha_recepcion;
    }

    public String getDesc_contacto() {
        return desc_contacto;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public String getClave_producto() {
        return clave_producto;
    }

    public int getCantidad_cliente() {
        return cantidad_cliente;
    }

    
}
