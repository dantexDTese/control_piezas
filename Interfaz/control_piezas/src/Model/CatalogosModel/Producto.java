
package Model.CatalogosModel;


public class Producto {
    
    private int noProducto;
    private String claveProducto;
    private String descProducto;
    private String material;

    public Producto() {
    }

    public int getNoProducto() {
        return noProducto;
    }

    public void setNoProducto(int noProducto) {
        this.noProducto = noProducto;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        this.claveProducto = claveProducto;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

}
