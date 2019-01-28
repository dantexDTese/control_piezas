
package Model;

import Model.LotePlaneado;
import Model.ProductoMaquina;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public class ProductoPendiente extends LotePlaneado{

       private int diasTrabajo;
       private ProductoMaquina productoMaquinaSeleccionado;
       private ArrayList<ProductoMaquina> productosMaquina;

       
    public ProductoPendiente() {
        
    }

    
    public ProductoPendiente(int noOrdenTrabajo, String codProducto, int noOrdenProduccion, int cantidadCliente,    
            String descTipoMaterial,String descDimencion,String descForma,String claveForma,int noMaterial) {

        super(noOrdenTrabajo, codProducto, noOrdenProduccion, cantidadCliente,descTipoMaterial,descDimencion,descForma,claveForma,noMaterial);
        
        
        
    }

    
    
    public ProductoMaquina getProductoMaquinaSeleccionado() {

        return productoMaquinaSeleccionado;
        
    }

    
    
    public void setProductoMaquinaSeleccionado(ProductoMaquina productoMaquinaSeleccionado) {

        this.productoMaquinaSeleccionado = productoMaquinaSeleccionado;
        
    }

    
    
    public ArrayList<ProductoMaquina> getProductosMaquina() {

        return productosMaquina;
        
    }

    
    
    public void setProductosMaquina(ArrayList<ProductoMaquina> productosMaquina) {

        this.productosMaquina = productosMaquina;
        
    }

    
    
    public int getDiasTrabajo() {

        return diasTrabajo;
        
    }

    
    
    public void setDiasTrabajo(int diasTrabajo) {

        this.diasTrabajo = diasTrabajo;
        
    }        

}