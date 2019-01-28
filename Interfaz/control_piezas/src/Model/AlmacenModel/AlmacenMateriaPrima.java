/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.AlmacenModel;

import Model.RequisicionesModel.EntradaMaterial;

/**
 *
 * @author cesar
 */
public class AlmacenMateriaPrima extends EntradaMaterial{
    
    private int noAlmacenMateriaPrima;
    private int cantidadTotal;

    public int getNoAlmacenMateriaPrima() {
        return noAlmacenMateriaPrima;
    }

    public void setNoAlmacenMateriaPrima(int noAlmacenMateriaPrima) {
        this.noAlmacenMateriaPrima = noAlmacenMateriaPrima;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
    
}
