package Controller.ProduccionController;

import Model.Constructores;
import Model.Estructuras;
import Model.LotePlaneado;
import Model.ProcesosProduccion;
import Model.ProduccionModel.SeguimientoProduccionModel;
import Model.ProduccionModel.LoteProduccion;
import View.Produccion.SeguimientoProduccionDialogView;
import View.Produccion.TurnoOperador;
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
    private final LotePlaneado ordenSeleccionada;     
    private LoteProduccion loteProduccion;
    private Timer tiempo;
    
    SeguimientoProduccionController(SeguimientoProduccionDialogView vistaSeguimiento, SeguimientoProduccionModel seguimientoProduccionModel,LotePlaneado ordenSeleccionada) {
        
        //INICIALIZAR
        this.vistaSeguimiento = vistaSeguimiento;
        this.seguimientoProduccionModel = seguimientoProduccionModel;
        this.ordenSeleccionada = ordenSeleccionada;
        
        llenarComponentes();
        asignarEventos();
        
    }
    
    @Override
    public void llenarComponentes(){
        
        vistaSeguimiento.getLbCliente().setText(ordenSeleccionada.getDescCliente());
        vistaSeguimiento.getLbParte().setText(ordenSeleccionada.getCodProducto());
        vistaSeguimiento.getLbCantidadTotal().setText(ordenSeleccionada.getCantidadTotal()+"");
        vistaSeguimiento.getLbMaquina().setText(ordenSeleccionada.getDescMaquina());   
        vistaSeguimiento.getLbEstado().setText(ordenSeleccionada.getDescEstadoOrdenProduccion());
        
        ArrayList <String> procesosProduccion = seguimientoProduccionModel.obtenerProcesosProduccion(ordenSeleccionada.getNoOrdenProduccion());
        for(int i = 0;i<procesosProduccion.size();i++)
           vistaSeguimiento.getCbxProcesosProduccion().addItem(procesosProduccion.get(i));
        
        llenarTablaLotes();
        
        if(ordenSeleccionada.getDescEstadoOrdenProduccion().equals("CERRADO"))
            vistaSeguimiento.getBtnIniciarSiguienteLote().setEnabled(false);
        
        
        
        tiempo = new Timer(1000, (ActionEvent e) -> {
            
            loteProduccion.getTiempoTranscurridoR().avanzar();
            if(!loteProduccion.isActivacion())
                loteProduccion.getTiempoMuertoR().avanzar();    
            
            llenarCampos();
            if(!vistaSeguimiento.isVisible()){
                tiempo.stop();   
                ProcesosProduccion.loteMostrado = null;
            }
        });
        
        
        if((loteProduccion = ProcesosProduccion.obtenerProceso(ordenSeleccionada.getNoOrdenProduccion())) == null){
            loteProduccion = seguimientoProduccionModel.obtenerOrdenPlaneada(ordenSeleccionada.getNoOrdenProduccion());
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
        this.vistaSeguimiento.getBtnDetener().addActionListener(listenerDetenerProceso);
        
    }

    public SeguimientoProduccionModel getSeguimientoProduccionModel() {
        return seguimientoProduccionModel;
    }
    
   
    //EVENTOS
    private final ActionListener listenerInizializarProceso = new ActionListener() {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(loteProduccion != null){ 
                
                if(iniciarLoteProduccion()){
                    llenarCampos();
                    vistaSeguimiento.getBtnIniciarSiguienteLote().setEnabled(false);
                    vistaSeguimiento.getBtnDetener().setEnabled(true);
                    vistaSeguimiento.getBtnPausar().setEnabled(true);
                    ProcesosProduccion.loteMostrado = loteProduccion;
                    ProcesosProduccion.listaProcesando.add(loteProduccion);
                    tiempo.start();
                }else JOptionPane.showMessageDialog(null, "NO SE HA PODIDO INICIAR LA PRODUCCION PORQUE NO HA COMPLETADO LOS CAMPOS");
            }else JOptionPane.showMessageDialog(null, "ESTA ORDEN NO ESTA PLANEADA PARA HOY","PLANEACION",JOptionPane.INFORMATION_MESSAGE);
            
        }
        
        private boolean iniciarLoteProduccion(){
            loteProduccion.setTiempoMuerto("00:00:00");
            loteProduccion.setCantidadProducidaR(0);
            vistaSeguimiento.getPrbProgresoLote().setMaximum(loteProduccion.getCantidadPlaneada());
            
            String descLote = obtenerDescripcionLote();
            
            if(descLote != null){
                loteProduccion.setDescLote(descLote);
                return true;
            }
            else return false;
        }
         
        private String obtenerDescripcionLote(){
            String descLote="";
            
            String[] turnoOperador = TurnoOperador.seleccionarOperadorTurno(vistaSeguimiento.getPrincipal());
            
            if(turnoOperador != null){
           
                loteProduccion.setCodOperador(turnoOperador[1]);
                loteProduccion.setDescTurno(turnoOperador[0]);

                Date fecha = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMYY");
                descLote = sdf.format(fecha);
                descLote+=turnoOperador[0];
                descLote+=ordenSeleccionada.getDescMaquina();
                descLote+=turnoOperador[1];                
                
                return descLote;
            }
            
            return null;
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
            llenarTablaLotes();
        } 
        
    };
    
    private void llenarTablaLotes(){
            ArrayList<LoteProduccion> listaLotes = obtenerLotesProcesados(ordenSeleccionada.getNoOrdenProduccion(),
                    vistaSeguimiento.getCbxProcesosProduccion().getSelectedItem().toString());
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
                    i+1,
                    lote.getDescLote(),
                    lote.getCantidadOperados(),
                    lote.getScrapOperador(),
                    lote.getMerma(),
                    lote.getTiempoMuerto(),
                    lote.getRechazo(),
                    lote.getCantidadAdmin(),
                    lote.getScrapAdmin()});
            
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
            
            DefaultTableModel modeloTabla = (DefaultTableModel) vistaSeguimiento.getJtbTotalesLotesProduccion().getModel();
            modeloTabla.addRow(new Object[]{
                p.getCantidadOperados(),
                p.getScrapOperador(),
                p.getMerma(),
                p.getTiempoMuerto(),
                p.getRechazo(),
                p.getCantidadAdmin(),
                p.getScrapAdmin()
            });

        }
        
        private String sumarTiempo(String tiempo1,String tiempo2){
            if(tiempo1 != null)
                return Estructuras.sumarHoras(tiempo1, tiempo2);
            
            return tiempo2;
        }
    
    private final ActionListener listenerPauseDetener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource() == vistaSeguimiento.getBtnPausar())
                pausarProceso();
            
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
        
    };
    
    private final ActionListener listenerDetenerProceso = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int respuesta = JOptionPane.showConfirmDialog(null, "SI DETENIENE EL PROCESO NO PODRA REPETIRLO, ¿CONTINUAR? ","VALIDACION"
            ,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            
            if(respuesta == JOptionPane.YES_OPTION){
                tiempo.stop();
                if(seguimientoProduccionModel.guardarProduccion(loteProduccion)){
                    ProcesosProduccion.terminarLote(loteProduccion);
                    vistaSeguimiento.dispose();
                }
            }
        }
    };
}
