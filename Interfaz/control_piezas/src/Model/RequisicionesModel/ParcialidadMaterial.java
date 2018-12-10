package Model.RequisicionesModel;

import Model.PedidosModel.ProductosPendientes;
import java.util.ArrayList;


public final class ParcialidadMaterial extends  MaterialesRequisicion{
        
        private final int noPartida;
        private final int noParcialidad;
        private String fechaSolicitadaParcialidadMaterial=null;
        private String cuentaCargo=null;
        private String unidad=null;
        private int cantidad;
        private float precioUnitario;
        private float precioTotal;
        private String proveedor;
        private ArrayList<ProductosPendientes> listaOrdenesProduccion;
        

    public ParcialidadMaterial(int noPartida,int noParcialidad,String material) {
        super(material);
            this.noParcialidad = noParcialidad;
            this.noPartida = noPartida;
    }

    public ArrayList<ProductosPendientes> getListaOrdenesProduccion() {
        return listaOrdenesProduccion;
    }

    public void setListaOrdenesProduccion(ArrayList<ProductosPendientes> listaOrdenesProduccion) {
        this.listaOrdenesProduccion = listaOrdenesProduccion;
    }
    
    

    public void setFechaSolicitadaParcialidadMaterial(String fechaSolicitadaParcialidadMaterial) {
        this.fechaSolicitadaParcialidadMaterial = fechaSolicitadaParcialidadMaterial;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public void setCuentaCargo(String cuentaCargo) {
        this.cuentaCargo = cuentaCargo;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
        
    public String getProveedor() {
        return proveedor;
    }
        
       
    public int getNoPartida() {
            return noPartida;
    }

    public int getNoParcialidad() {
            return noParcialidad;
    }

    public String getFechaSolicitadaParcialidadMaterial() {
            return fechaSolicitadaParcialidadMaterial;
    }

    public String getCuentaCargo() {
            return cuentaCargo;
    }

    public String getUnidad() {
            return unidad;
    }

    public int getCantidad() {
            return cantidad;
    }

    public float getPrecioUnitario() {
            return precioUnitario;
    }

    public float getPrecioTotal() {
            return precioTotal;
    }
}
    
    
    
