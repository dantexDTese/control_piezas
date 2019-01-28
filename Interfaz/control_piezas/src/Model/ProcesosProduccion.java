
package Model;

import Model.ProduccionModel.LoteProduccion;
import ds.desktop.notify.DesktopNotify;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

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
        
        
        
        mostrarAvisos(listaProcesando.get(procesoActual));
            
        //para avanzar normal
        listaProcesando.get(procesoActual).getTiempoTranscurridoR().avanzar(new Date());
        
        //para avanzar el tiempo muerto
        if(!listaProcesando.get(procesoActual).isActivacion()){
            listaProcesando.get(procesoActual).getTiempoMuertoR().avanzar(new Date());  
        }
                  
        
        procesoActual++;
    }
    
    public static LoteProduccion obtenerProceso(int ordenProduccion){
        
        for(int i = 0;i<listaProcesando.size();i++){
            
            if(ordenProduccion == listaProcesando.get(i).getNoOrdenProduccion())
                return listaProcesando.get(i);
            
        }
        
        return null;
        
    }
    
    public static void terminarLote(LoteProduccion loteProduccion) {
        for(int i = 0;i<listaProcesando.size();i++){
            if(loteProduccion.getNoOrdenProduccion() == listaProcesando.get(i).getNoOrdenProduccion())
                listaProcesando.remove(i);
        }
    }

    private void mostrarAvisos(LoteProduccion loteSeleccionado) {
        
        if(loteSeleccionado.getCantidadProducidaR() >= 
                (loteSeleccionado.getCantidadPlaneada()*80)/100 && !loteSeleccionado.isAviso1()){
            
            DesktopNotify.showDesktopMessage("AVISO DE PRODUCUCION", "SE CALCULA QUE LA PRODUCCION DE"
                    + loteSeleccionado.getDescLote() +" A LLEGADO "
                    + "HA UN 80%",DesktopNotify.INFORMATION);
            loteSeleccionado.setAviso1(true);
            
        }else if(loteSeleccionado.getCantidadProducidaR() >= 
                (loteSeleccionado.getCantidadPlaneada()*95)/100 && !loteSeleccionado.isAviso2()){
            DesktopNotify.showDesktopMessage("AVISO DE PRODUCUCION","SE CALCULA QUE LA PRODUCCION DE"
                    + loteSeleccionado.getDescLote() +"HA LLEGADO A UN 98%, POR FAVOR "
                            + "ESTE AL PENDIENTE DE SU CONCLUCION EN LA VENTENA DE SEGUIMIENTO DE "
                            + "PRODUCCION",DesktopNotify.SUCCESS);
            loteSeleccionado.setAviso2(true);
            
        }else if(loteSeleccionado.getCantidadProducidaR() > 
                loteSeleccionado.getCantidadPlaneada() && !loteSeleccionado.isAviso3()){
            
            DesktopNotify.showDesktopMessage("AVISO DE PRODUCUCION","SE CALCULA QUE LA PRODUCCION DE"
                    + loteSeleccionado.getDescLote() +"HA LLEGADO A UN 100%, POR FAVOR "
                            +"INGRESE A LA VENTANA DE SEGUIMIENTO PARA CONCLUIR SU PRODUCCION "
                            + "ANTES DE QUE SE CALCULEN PIEZAS DE MAS",DesktopNotify.WARNING);    
            loteSeleccionado.setAviso3(true);
        }
        
        
        
    }

    
}

