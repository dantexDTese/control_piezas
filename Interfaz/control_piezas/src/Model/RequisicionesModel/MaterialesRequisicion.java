package Model.RequisicionesModel;


    
public class MaterialesRequisicion{
        private final String material;
        private int barrasNecesarias;

        public MaterialesRequisicion(int barrasNecesarias,String material) {
            this.material = material;
            this.barrasNecesarias = barrasNecesarias;
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
    
