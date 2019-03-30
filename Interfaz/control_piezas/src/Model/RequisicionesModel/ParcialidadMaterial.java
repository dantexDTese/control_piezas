package Model.RequisicionesModel;

import Model.ordenProduccion;
import java.util.ArrayList;


public final class ParcialidadMaterial extends  MaterialesRequisicion{
        
        private int noPartida;
        private int noParcialidad;
        private String fechaSolicitadaParcialidadMaterial=null;
        private String cuentaCargo=null;
        private String unidad=null;
        private int cantidad;
        private int cantidadRestante;
        private int noMaterialSolicitado;
        private int cantidadSeleccionada;
        
        private ArrayList<ordenProduccion> listaOrdenesProduccion;
        
    public ParcialidadMaterial(int noPartida,int noParcialidad) {
            this.noParcialidad = noParcialidad;
            this.noPartida = noPartida;
    }

    ParcialidadMaterial() {
        
    }

    public ArrayList<ordenProduccion> getListaOrdenesProduccion() {
        return listaOrdenesProduccion;
    }

    public void setListaOrdenesProduccion(ArrayList<ordenProduccion> listaOrdenesProduccion) {
        this.listaOrdenesProduccion = listaOrdenesProduccion;
    }

    public void setFechaSolicitadaParcialidadMaterial(String fechaSolicitadaParcialidadMaterial) {
        this.fechaSolicitadaParcialidadMaterial = fechaSolicitadaParcialidadMaterial;
    }

    public int getNoMaterialSolicitado() {
        return noMaterialSolicitado;
    }

    public void setNoMaterialSolicitado(int noMaterialSolicitado) {
        this.noMaterialSolicitado = noMaterialSolicitado;
    }
    
    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(int cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
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

    public void setNoPartida(int noPartida) {
        this.noPartida = noPartida;
    }

    public void setNoParcialidad(int noParcialidad) {
        this.noParcialidad = noParcialidad;
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

    public int getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(int cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }
    
}
    
    
    
