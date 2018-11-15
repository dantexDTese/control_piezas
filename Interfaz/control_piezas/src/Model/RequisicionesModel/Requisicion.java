/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.RequisicionesModel;

/**
 *
 * @author cesar
 */
public class Requisicion {

    /**
     * ATRIBUTOS
     */
    private final String descProveedor;
    private final int noOrdenTrabajo;
    private final String solicitante;
    private final String terminos;
    private final String lugarEntrega;
    private final String comentarios;
    private final float subTotal;
    private final float IVA;
    private final float total;

    /**
     * CONSTRUCTOR
     * @param descProveedor
     * @param noOrdenTrabajo
     * @param solicitante
     * @param terminos
     * @param lugarEntrega
     * @param comentarios
     * @param subTotal
     * @param IVA
     * @param total
     */
      
    public Requisicion(String descProveedor, int noOrdenTrabajo, String solicitante, String terminos, String lugarEntrega, String comentarios, float subTotal, float IVA, float total) {
        this.descProveedor = descProveedor;
        this.noOrdenTrabajo = noOrdenTrabajo;
        this.solicitante = solicitante;
        this.terminos = terminos;
        this.lugarEntrega = lugarEntrega;
        this.comentarios = comentarios;
        this.subTotal = subTotal;
        this.IVA = IVA;
        this.total = total;
    }
    
    
    /**
     * PROPIEDADES
     */
    
    

    public String getDescProveedor() {
        return descProveedor;
    }

    public int getNoOrdenTrabajo() {
        return noOrdenTrabajo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getTerminos() {
        return terminos;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public String getComentarios() {
        return comentarios;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public float getIVA() {
        return IVA;
    }

    public float getTotal() {
        return total;
    }

    
    
}
