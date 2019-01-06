
package Model;

import Model.ProduccionModel.LoteProduccion;
import java.util.ArrayList;
import java.util.Date;

public class ProcesosProduccion extends Thread{

    public static ArrayList<LoteProduccion> listaProcesando = new ArrayList<>();
    
   public static LoteProduccion loteMostrado;
   
    public int procesoActual;

    public ProcesosProduccion(int procesoActual) {
        
        this.procesoActual = procesoActual;
        
    }
    
    public ProcesosProduccion(){
        
        this.procesoActual = 0;
        
    }
    
    
    @Override
    public void run() {
        super.run();
        while(true){
            try {
                if(listaProcesando.size()>0){

                    if(loteMostrado != null){
                        if(loteMostrado.getNoOrdenProduccion() != listaProcesando.get(procesoActual).getNoOrdenProduccion())
                            avanzar();                        
                        
                    }else avanzar();

                    if(procesoActual == listaProcesando.size())
                        procesoActual=0;
                }
                sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        
    }
    
    private void avanzar(){
        listaProcesando.get(procesoActual).getTiempoTranscurridoR().avanzar(new Date());
        
        if(!listaProcesando.get(procesoActual).isActivacion())
            listaProcesando.get(procesoActual).getTiempoMuertoR().avanzar(new Date());
        
        procesoActual++;
    }
    
    public static LoteProduccion obtenerProceso(int ordenProduccion){
        
        for(int i = 0;i<listaProcesando.size();i++){
            
            if(ordenProduccion == listaProcesando.get(i).getNoOrdenProduccion())
                return listaProcesando.get(i);
            
        }
        
        return null;
        
    }

    
}

