/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class ordenCompraSeguimientoDiario {
    
   
    private String claveProducto;
    private String descCliente;
    private int cantidadTotal;
    private String procesoActual;
    private String maquina;
    private String operador;

    public ordenCompraSeguimientoDiario(String claveProducto, String descCliente, int cantidadTotal, String procesoActual, String maquina, String operador) {
        this.claveProducto = claveProducto;
        this.descCliente = descCliente;
        this.cantidadTotal = cantidadTotal;
        this.procesoActual = procesoActual;
        this.maquina = maquina;
        this.operador = operador;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public String getDescCliente() {
        return descCliente;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public String getProcesoActual() {
        return procesoActual;
    }

    public String getMaquina() {
        return maquina;
    }

    public String getOperador() {
        return operador;
    }
        
}
