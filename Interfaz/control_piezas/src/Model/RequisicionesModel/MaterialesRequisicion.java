package Model.RequisicionesModel;


    
public class MaterialesRequisicion{
        private int noRequisicion;
        private final String material;
        private int barrasNecesarias;
        private String descEstado;
        
        public MaterialesRequisicion(int barrasNecesarias,String material) {
            this.material = material;
            this.barrasNecesarias = barrasNecesarias;
        }

    public MaterialesRequisicion(int noRequisicion, String material, int barrasNecesarias, String descEstado) {
        this.noRequisicion = noRequisicion;
        this.material = material;
        this.barrasNecesarias = barrasNecesarias;
        this.descEstado = descEstado;
    }

    public int getNoRequisicion() {
        return noRequisicion;
    }

    public String getDescEstado() {
        return descEstado;
    }
        
    
        

        public MaterialesRequisicion(String material) {
            this.material = material;
        }
        
        public String getMaterial() {
            return material;
        }

        public int getBarrasNecesarias() {
            return barrasNecesarias;
        }
              
    }
    
