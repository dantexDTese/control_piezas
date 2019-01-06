/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.RequisicionesModel;

public class EntradaMaterial {
    
            private int noEntradaMaterial;
            private String fechaRegistro;
            private String descProveedor;
            private String descMaterial;
            private int cantidad;
            private String  codigo;
            private String  certificado;
            private String ordenCompra;
            private String inspector;
            private String descEstado;
            private String comentarios;
            private String factura;
            private String noParte;
            
            
            
            public EntradaMaterial() {
                
            }


        public EntradaMaterial(int noEntradaMaterial,String descMaterial,String descProveedor,  int cantidad, String codigo, String certificado, String ordenCompra, String inspector) {
            this.descProveedor = descProveedor;
            this.descMaterial = descMaterial;
            this.cantidad = cantidad;
            this.codigo = codigo;
            this.certificado = certificado;
            this.ordenCompra = ordenCompra;
            this.inspector = inspector;
            this.noEntradaMaterial = noEntradaMaterial;
        }

    public EntradaMaterial(int noEntradaMaterial,String fechaRegistro, String descMaterial, String descProveedor, int cantidad, String codigo, String certificado, String ordenCompra, String inspector,String descEstado
            ,String comentarios,String factura,String noParte) {
        this.fechaRegistro = fechaRegistro;
        this.descProveedor = descProveedor;
        this.descMaterial = descMaterial;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.certificado = certificado;
        this.ordenCompra = ordenCompra;
        this.inspector = inspector;
        this.descEstado = descEstado;
        this.noEntradaMaterial = noEntradaMaterial;
        this.comentarios = comentarios;
        this.factura = factura;
        this.noParte = noParte;
    }
    
    
    
    public EntradaMaterial(String descMaterial,String descProveedor,  int cantidad, String codigo, String certificado, String ordenCompra, String inspector) {
        this.descProveedor = descProveedor;
        this.descMaterial = descMaterial;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.certificado = certificado;
        this.ordenCompra = ordenCompra;
        this.inspector = inspector;
    }

    public int getNoEntradaMaterial() {
        return noEntradaMaterial;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setDescProveedor(String descProveedor) {
        this.descProveedor = descProveedor;
    }

    public void setDescMaterial(String descMaterial) {
        this.descMaterial = descMaterial;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public void setOrdenCompra(String ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public void setNoParte(String noParte) {
        this.noParte = noParte;
    }
    
    

    public String getComentarios() {
        return comentarios;
    }

    public String getFactura() {
        return factura;
    }

    public String getNoParte() {
        return noParte;
    }
    
    

    public String getDescEstado() {
        return descEstado;
    }

    
    public String getFechaRegistro() {
        return fechaRegistro;
    }
                    

    public String getDescProveedor() {
        return descProveedor;
    }

    public String getDescMaterial() {
        return descMaterial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCertificado() {
                return certificado;
            }

    public String getOrdenCompra() {
                return ordenCompra;
            }

            public String getInspector() {
                return inspector;
            }
}
