
package Model;

import java.util.Date;


public class ordenProducto {

    private String codProducto;
    private String descMaquina;
    private String descMateria;
    private int cantidadSolicitada;
    private int cantidadProducir;
    private int cantidadPorTurno;
    private String fecha;
    private int barrasNecesarias;
    
    public ordenProducto(String codProducto,int cantidadSolicitada){
        this.codProducto = codProducto;
        this.cantidadSolicitada = cantidadSolicitada;
    }
    
    
    public ordenProducto(String codProducto, String descMaquina, String descMaterial, 
            int cantidadSolicitada, int cantidadProducir, int cantidadPorTurno, String fecha,
            int barrasNecesarias) {
        this.codProducto = codProducto;
        this.descMaquina = descMaquina;
        this.descMateria = descMaterial;
        this.cantidadSolicitada = cantidadSolicitada;
        this.cantidadProducir = cantidadProducir;
        this.cantidadPorTurno = cantidadPorTurno;
        this.fecha = fecha;   
        this.barrasNecesarias = barrasNecesarias;
    }

    

    public int getBarrasNecesarias() {
        return barrasNecesarias;
    }
    

    public String getCodProducto() {
        return codProducto;
    }

    public String getDescMaquina() {
        return descMaquina;
    }

    public String getDescMateria() {
        return descMateria;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public int getCantidadProducir() {
        return cantidadProducir;
    }

    public int getCantidadPorTurno() {
        return cantidadPorTurno;
    }

    public String getFecha() {
        return fecha;
    }
    
    
}
