/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author cesar
 */
public class Requisicion {

    /**
     * ATRIBUTOS
     */
    private int noRequisicion;
    private String descProveedor;
    private int noOrdenTrabajo;
    private String fechaCreacion;
    private String fechaCerrada;
    private String solicitante;
    private String terminos;
    private String lugarEntrega;
    private String comentarios;
    private String usoMaterial;
    private float subTotal;
    private float IVA;
    private float total;

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
    
    
    public Requisicion(){
        
    }

    
    
    
    
     

    public void setNoRequisicion(int noRequisicion) {
        this.noRequisicion = noRequisicion;
    }

    /**
     * PROPIEDADES
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

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

    public int getNoRequisicion() {
        return noRequisicion;
    }

    public String getFechaCerrada() {
        return fechaCerrada;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    
    
    
}
