
package Model;


public class AlmacenProductoTerminado{
    
    private int noAlmacenProductoTerminado;
    private String codProducto;
    private int total;

    public AlmacenProductoTerminado(){    }
    
    public AlmacenProductoTerminado(int noAlmacenProductoTerminado, int total,String codProducto) {
        this.noAlmacenProductoTerminado = noAlmacenProductoTerminado;
        this.codProducto = codProducto;
        this.total = total;
    }

    public AlmacenProductoTerminado(int noAlmacenProductoTerminado) {
        this.noAlmacenProductoTerminado = noAlmacenProductoTerminado;
    }
   

    public String getCodProducto() {
        return codProducto;
    }
    
    public int getNoAlmacenProductoTerminado() {
        return noAlmacenProductoTerminado;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
