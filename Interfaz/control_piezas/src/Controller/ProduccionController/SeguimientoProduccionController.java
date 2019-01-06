package Controller.ProduccionController;

import Model.Constructores;
import Model.Estructuras;
import Model.ProcesosProduccion;
import Model.ProduccionModel.OrdenProduccionGuardada;
import Model.ProduccionModel.SeguimientoProduccionModel;
import Model.ProduccionModel.LoteProduccion;
import View.Produccion.SeguimientoProduccionDialogView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public final class SeguimientoProduccionController implements Constructores{

    
    private final SeguimientoProduccionDialogView vistaSeguimiento;
    private final SeguimientoProduccionModel seguimientoProduccionModel;
    private final OrdenProduccionGuardada ordenSeleccionada;     
    private LoteProduccion loteProduccion;
    private Timer tiempo;
    
    SeguimientoProduccionController(SeguimientoProduccionDialogView vistaSeguimiento, SeguimientoProduccionModel seguimientoProduccionModel,OrdenProduccionGuardada ordenSeleccionada) {
        
        //INICIALIZAR
        this.vistaSeguimiento = vistaSeguimiento;
        this.seguimientoProduccionModel = seguimientoProduccionModel;
        this.ordenSeleccionada = ordenSeleccionada;
        
        llenarComponentes();
        asignarEventos();
        
    }
    
    @Override
    public void llenarComponentes(){
        
        vistaSeguimiento.getLbCliente().setText(ordenSeleccionada.getNombreCliente());
        vistaSeguimiento.getLbParte().setText(ordenSeleccionada.getClaveProducto());
        vistaSeguimiento.getLbCantidadTotal().setText(ordenSeleccionada.getCantidadTotal()+"");
        vistaSeguimiento.getLbMaquina().setText(ordenSeleccionada.getDescMaquina());   
        
        tiempo = new Timer(1000, (ActionEvent e) -> {
            
            loteProduccion.getTiempoTranscurridoR().avanzar();
            
            if(!loteProduccion.isActivacion()){
                loteProduccion.getTiempoMuertoR().avanzar();
            }
                
            
            llenarCampos();
            if(!vistaSeguimiento.isVisible()){
                tiempo.stop();   
                ProcesosProduccion.loteMostrado = null;
            }
        });
        
        ArrayList <String> procesosProduccion = seguimientoProduccionModel.obtenerProcesosProduccion(ordenSeleccionada.getOrdenProduccion());
        
        for(int i = 0;i<procesosProduccion.size();i++)
           vistaSeguimiento.getCbxProcesosProduccion().addItem(procesosProduccion.get(i));
        
        if((loteProduccion = ProcesosProduccion.obtenerProceso(ordenSeleccionada.getOrdenProduccion())) == null){

            loteProduccion = seguimientoProduccionModel.obtenerOrdenPlaneada(ordenSeleccionada.getOrdenProduccion());
            
            if(loteProduccion!=null){
                
                loteProduccion.setPiezasPorTurno(ordenSeleccionada.getPiezasPorTurno());
                loteProduccion.setPiezasSegundoR(loteProduccion.calcularPiezasSegundo(loteProduccion.getPiezasPorTurno()));
                vistaSeguimiento.getPrbProgresoLote().setMaximum(loteProduccion.getCantidadPlaneada());
                
            }
                
            
        }else{
            
            ProcesosProduccion.loteMostrado = loteProduccion;
            
            vistaSeguimiento.getBtnIniciarSiguienteLote().setEnabled(false);
            if (loteProduccion.isActivacion())
                vistaSeguimiento.getBtnPausar().setText("PAUSAR");
            else
                vistaSeguimiento.getBtnPausar().setText("CONTINUAR");
                    
                    
            vistaSeguimiento.getBtnPausar().setEnabled(true);
            vistaSeguimiento.getBtnDetener().setEnabled(true);
            
            tiempo.start();     
            
        }
        
        
    }
    
    @Override
    public void asignarEventos() {   
        
        this.vistaSeguimiento.getCbxProcesosProduccion().addActionListener(listenerSeleccionProceso);
        this.vistaSeguimiento.getBtnIniciarSiguienteLote().addActionListener(listenerInizializarProceso);   
        this.vistaSeguimiento.getBtnPausar().addActionListener(listenerPauseDetener);
        this.vistaSeguimiento.getBtnDetener().addActionListener(listenerPauseDetener);
    }

    public SeguimientoProduccionModel getSeguimientoProduccionModel() {
        return seguimientoProduccionModel;
    }
    
   
    //EVENTOS
    private final ActionListener listenerInizializarProceso = new ActionListener() {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(loteProduccion!= null){                      
                iniciarLoteProduccion();
                llenarCampos();
                vistaSeguimiento.getBtnIniciarSiguienteLote().setEnabled(false);
                vistaSeguimiento.getBtnDetener().setEnabled(true);
                vistaSeguimiento.getBtnPausar().setEnabled(true);
                ProcesosProduccion.loteMostrado = loteProduccion;
                ProcesosProduccion.listaProcesando.add(loteProduccion);
                tiempo.start();
                
            }else JOptionPane.showMessageDialog(null, "ESTA ORDEN NO ESTA PLANEADA PARA HOY","PLANEACION",JOptionPane.INFORMATION_MESSAGE);
            
        }
        
        private void iniciarLoteProduccion(){
            loteProduccion.setTiempoMuerto("00:00:00");
            loteProduccion.setCantidadProducidaR(0);
            vistaSeguimiento.getPrbProgresoLote().setMaximum(loteProduccion.getCantidadPlaneada());
            
            loteProduccion.setDescLote(obtenerDescripcionLote());
        }
         
        private String obtenerDescripcionLote(){
            String descLote="";
            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMYY");
            descLote = sdf.format(fecha);
            descLote+=(fecha.getHours()>15)? "V":"M";
            descLote+=ordenSeleccionada.getDescMaquina();
            return descLote;
        }
        
    };
    
    private void llenarCampos(){
     
        vistaSeguimiento.getLbObjetivoPlaneado().setText(loteProduccion.getCantidadPlaneada()+"");
        
        vistaSeguimiento.getLbTiempoTranscurrido().setText(loteProduccion.getTiempoTranscurridoR().toString());

        vistaSeguimiento.getLbTiempoMuerto().setText(loteProduccion.getTiempoMuertoR().toString());
        
        vistaSeguimiento.getLbCantidadProducida().setText(((int)Math.floor(loteProduccion.getCantidadProducidaR()))+"");

        vistaSeguimiento.getPrbProgresoLote().setValue((int)loteProduccion.getCantidadProducidaR());
        
        vistaSeguimiento.getLbLote().setText(loteProduccion.getDescLote());
    }
    
    private final ActionListener listenerSeleccionProceso = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<LoteProduccion> listaLotes = obtenerLotesProcesados(ordenSeleccionada.getOrdenProduccion(),vistaSeguimiento.getCbxProcesosProduccion().getSelectedItem().toString());
            llenarTotalesLotes(listaLotes);
        }
        
        private ArrayList<LoteProduccion> obtenerLotesProcesados(int noOrdenProduccion,String procesoSeleccionado){
            ArrayList<LoteProduccion> listaLotesProduccion = 
                    seguimientoProduccionModel.listaLotesProduccion(noOrdenProduccion, procesoSeleccionado);
            
            DefaultTableModel modelLotesProduccion = (DefaultTableModel) vistaSeguimiento.getJtbLotesProduccion().getModel();
            Estructuras.limpiarTabla((DefaultTableModel) vistaSeguimiento.getJtbLotesProduccion().getModel());
            
            for(int i = 0;i<listaLotesProduccion.size();i++){
                LoteProduccion lote = listaLotesProduccion.get(i);
                modelLotesProduccion.addRow(new Object[]{
                    i,lote.getDescLote(),lote.getCantidadOperados(),lote.getScrapOperador(),
                    lote.getMerma(),lote.getTiempoMuerto(),lote.getRechazo(),
                    lote.getCantidadAdmin(),lote.getScrapAdmin()});
            }
            
            return listaLotesProduccion;
        }

        private void llenarTotalesLotes(ArrayList<LoteProduccion> listaLotes){
            LoteProduccion p = new LoteProduccion();

            for(int i = 0;i<listaLotes.size();i++){
                LoteProduccion lote = listaLotes.get(i);
                p.setCantidadOperados( p.getCantidadOperados() + lote.getCantidadOperados());
                p.setScrapOperador(p.getScrapOperador() + lote.getScrapOperador());
                p.setMerma(p.getMerma() + lote.getMerma());   
                p.setTiempoMuerto(sumarTiempo(p.getTiempoMuerto(),lote.getTiempoMuerto()));
                p.setRechazo(p.getRechazo() + lote.getRechazo());
                p.setCantidadAdmin(p.getCantidadAdmin() + lote.getCantidadAdmin());
                p.setScrapAdmin(p.getScrapAdmin() + lote.getScrapAdmin() );
            }

        }
        
        private String sumarTiempo(String tiempo1,String tiempo2){
            String tiempoResultado = new String();
            
                
                
            return tiempoResultado;
        }
    };
    
    private final ActionListener listenerPauseDetener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource() == vistaSeguimiento.getBtnPausar())
                pausarProceso();
            else
                detenerProceso();
            
        }
        
        private void pausarProceso(){
            if(vistaSeguimiento.getBtnPausar().getText().equals("PAUSAR")){
                vistaSeguimiento.getBtnPausar().setText("CONTINUAR");
                loteProduccion.setActivacion(false);
            }else{
                vistaSeguimiento.getBtnPausar().setText("PAUSAR");
                loteProduccion.setActivacion(true);
                
            }
        }
        
        private void detenerProceso(){
            
            
            
        }
        
    };
}
