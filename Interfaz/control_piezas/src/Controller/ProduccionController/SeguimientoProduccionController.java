package Controller.ProduccionController;

import Model.Constructores;
import Model.Estructuras;
import Model.ImgTabla;
import Model.LotePlaneado;
import Model.ProcesosProduccion;
import Model.ProduccionModel.CerrarLotesModel;
import Model.ProduccionModel.CompletarRegistroProduccionModel;
import Model.ProduccionModel.SeguimientoProduccionModel;
import Model.ProduccionModel.LoteProduccion;
import View.Pedidos.ColorEstado;
import View.Produccion.CerrarLotes;
import View.Produccion.CompletarRegistroProduccionView;
import View.Produccion.SeguimientoProduccionDialogView;
import View.Produccion.TurnoOperador;
import ds.desktop.notify.DesktopNotify;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private ArrayList<LoteProduccion> listaLotes;
    
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
        vistaSeguimiento.getJtbLotesProduccion().setDefaultRenderer(Object.class,new ImgTabla());
        Estructuras.modificarAnchoTabla(vistaSeguimiento.getJtbLotesProduccion(),new Integer[]{50,150,100,100,100,100,100,100,100,100});
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
            }            
        }else{
            ProcesosProduccion.loteMostrado = loteProduccion;
            vistaSeguimiento.getBtnIniciarSiguienteLote().setEnabled(false);
            vistaSeguimiento.getPrbProgresoLote().setMaximum(loteProduccion.getCantidadPlaneada());
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
        this.vistaSeguimiento.getJtbLotesProduccion().addMouseListener(listenerModificarLote);
        this.vistaSeguimiento.getBtnCerrarLotes().addActionListener(listenerCerrarLotes);
    }

    public SeguimientoProduccionModel getSeguimientoProduccionModel(){
        return seguimientoProduccionModel;
    }
    
    private void llenarCampos(){
        vistaSeguimiento.getLbObjetivoPlaneado().setText(loteProduccion.getCantidadPlaneada()+"");
        vistaSeguimiento.getLbTiempoTranscurrido().setText(loteProduccion.getTiempoTranscurridoR().toString());
        vistaSeguimiento.getLbTiempoMuerto().setText(loteProduccion.getTiempoMuertoR().toString());
        vistaSeguimiento.getLbCantidadProducida().setText(((int)Math.floor(loteProduccion.getCantidadProducidaR()))+"");
        vistaSeguimiento.getPrbProgresoLote().setValue((int)loteProduccion.getCantidadProducidaR());
        vistaSeguimiento.getLbLote().setText(loteProduccion.getDescLote());
    }
    
    private void llenarTablaLotes(){
            listaLotes = obtenerLotesProcesados(ordenSeleccionada.getNoOrdenProduccion(),
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
            ColorEstado estado = Estructuras.obtenerColorEstado(lote.getDescEstado());
            
            modelLotesProduccion.addRow(new Object[]{
                i+1,
                lote.getDescLote(),
                lote.getCantidadOperados(),
                lote.getScrapOperador(),
                lote.getMerma(),
                lote.getTiempoMuerto(),
                lote.getRechazo(),
                lote.getCantidadAdmin(),                
                lote.getScrapAdmin(),
                estado});
            
        }
        
        return listaLotesProduccion;
    }

    private void agregarValores(LoteProduccion loteProduccion){
        if(JOptionPane.showConfirmDialog(null,"¿DESEA AGREGAR LOS REGISTROS DE PRODUCCION?","VALIDACION",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            CompletarRegistroProduccionView viewCompletarRegistro =
                    new CompletarRegistroProduccionView(vistaSeguimiento.getPrincipal(), true);
            CompletarRegistroProduccionController viewController = 
                    new CompletarRegistroProduccionController(viewCompletarRegistro,             
                    new CompletarRegistroProduccionModel(), loteProduccion);
            viewCompletarRegistro.setVisible(true);
            viewCompletarRegistro.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e){
                    super.windowClosed(e);
                    llenarTablaLotes();
                }
            });
        }
    }
    
    //llena la tabla de los lotes ya producidos
    private void llenarTotalesLotes(ArrayList<LoteProduccion> listaLotes){
            LoteProduccion p = new LoteProduccion();
            for(int i = 0;i<listaLotes.size();i++){
                LoteProduccion lote = listaLotes.get(i);
                if( lote.getDescEstado().equals("APROBADA") ){
                    p.setCantidadOperados( p.getCantidadOperados() + lote.getCantidadOperados());
                    p.setScrapOperador(p.getScrapOperador() + lote.getScrapOperador());
                    p.setMerma(p.getMerma() + lote.getMerma());
                    p.setTiempoMuerto(sumarTiempo(p.getTiempoMuerto(),lote.getTiempoMuerto()));
                    p.setRechazo(p.getRechazo() + lote.getRechazo());
                    p.setCantidadAdmin(p.getCantidadAdmin() + lote.getCantidadAdmin());
                    p.setScrapAdmin(p.getScrapAdmin()+lote.getScrapAdmin());
                }
            }
            
            DefaultTableModel modeloTabla = (DefaultTableModel) vistaSeguimiento.getJtbTotalesLotesProduccion().getModel();
            Estructuras.limpiarTabla(modeloTabla);
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
        
    //funcion para sumar 2 tiempo y regresa el resultado de la suma
    private String sumarTiempo(String tiempo1,String tiempo2){
        if(tiempo1 != null)
            return Estructuras.sumarHoras(tiempo1, tiempo2);        
        return tiempo2;
    }
    
    //evento para los botones de pausar y detener proceso
    private final ActionListener listenerPauseDetener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == vistaSeguimiento.getBtnPausar())
                pausarProceso();
        }
        
        //cuando se pausa el proceso la etiqueta del boton cambia de PAUSAR a DETENER
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
      
    //cuando se detiene el proceso se dejan de guardar datos en la PC y se envian al servidor
    //este evento se asigna al boton detener.
    private final ActionListener listenerDetenerProceso = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            int respuesta = JOptionPane.showConfirmDialog(null, "SI DETENIENE EL PROCESO NO PODRA REPETIRLO, ¿CONTINUAR? "
                    ,"VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(respuesta == JOptionPane.YES_OPTION){
                tiempo.stop();
                if(seguimientoProduccionModel.guardarProduccion(loteProduccion)){
                    ProcesosProduccion.terminarLote(loteProduccion);
                    agregarValores(loteProduccion);
                    vistaSeguimiento.dispose();
                }
            }
        }
    };
    
    //Este evento se asigna a la tabla de lotes producidos 
    private final MouseListener listenerModificarLote = new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                int fila = vistaSeguimiento.getJtbLotesProduccion().rowAtPoint(e.getPoint());
                String descLote = vistaSeguimiento.getJtbLotesProduccion().getValueAt(fila, 1).toString();
                
                int seleccion = JOptionPane.showOptionDialog(null,"SELECCIONE UNA OPCION", "SELECTOR DE OPCIONES",JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,null, new Object[]{"MODIFICAR REGISTROS","APROBAR/RECHAZAR"}, "PLAN DE INSPECCION");
                
                LoteProduccion lp = seguimientoProduccionModel.obtenerLoteProducido(descLote);
                
                if(seleccion == 0){
                    if(lp != null && lp.getDescEstado().equals("PROCESANDO")) agregarValores(lp);
                    else JOptionPane.showMessageDialog(null, "EL ELEMENTO NO PUEDE SER MODIFICADO");
                }else if(seleccion ==1) aprobarRechazarLote(lp);   
            }
        }
    };
    
    private void aprobarRechazarLote(LoteProduccion loteSeleccionado) {
            if(loteSeleccionado.getDescEstado().equals("PROCESANDO")){
                JOptionPane.showMessageDialog(null,"UNA VEZ REALIZADA LA OPERACION NO PODRA SER MODIFICADA");
                int respuesta = JOptionPane.showOptionDialog(null,"SELECCIONE UNA OPCION", "SELECTOR DE OPCIONES",JOptionPane.YES_NO_CANCEL_OPTION,            
                        JOptionPane.QUESTION_MESSAGE,null, new Object[]{"APROBAR","RECHAZAR","CANCELAR"}, "PLAN DE INSPECCION"); 
                if(respuesta == 0)
                    seguimientoProduccionModel.terminarLoteProduccion(loteSeleccionado.getDescLote(),seguimientoProduccionModel.APROBAR_LOTE);
                else if(respuesta == 1)
                    seguimientoProduccionModel.terminarLoteProduccion(loteSeleccionado.getDescLote(),seguimientoProduccionModel.RECHAZAR_LOTE);
            }else
                JOptionPane.showMessageDialog(null," ESTE LOTE YA FUE EVALUADO ");
        }
    
    private final ActionListener listenerSeleccionProceso = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Estructuras.limpiarTabla((DefaultTableModel) vistaSeguimiento.getJtbLotesProduccion().getModel());
            llenarTablaLotes();
        } 
    };
    
    private final ActionListener listenerInizializarProceso = new ActionListener() {
    
        @Override
        public void actionPerformed(ActionEvent e){
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
            vistaSeguimiento.getPrbProgresoLote().setMaximum(loteProduccion.getCantidadPlaneada());
            
            String descLote = obtenerDescripcionLote();
            if(!buscarLote(listaLotes,descLote)){
                if(descLote != null){
                    loteProduccion.setDescLote(descLote);
                    return true;
                }   
            }else{
                JOptionPane.showMessageDialog(null,"LA DESCRIPCION DE ESTE LOTE YA EXISTE, INTENTE DE NUEVO",
                        "VALIDACION",JOptionPane.WARNING_MESSAGE);
            }
            return false;
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

        private int sumarCantidadesCalculadas(ArrayList<LoteProduccion> listaLotes){
            int sumaCantidadProducida = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            for(int i = 0;i<listaLotes.size();i++){
                if(listaLotes.get(i).getFechaTrabajo().equals(sdf.format(new Date())))
                   sumaCantidadProducida+=listaLotes.get(i).getCantidadAdmin();
            }
            if(sumaCantidadProducida > 0)
                DesktopNotify.showDesktopMessage(" LOTES ENCONTRADOS "," SE HAN ENCONTRADO OTROS LOTES PRODUCIDOS EN ESTE DIA ",
                    DesktopNotify.INFORMATION,5000);
            
            return sumaCantidadProducida;
        }

        private boolean buscarLote(ArrayList<LoteProduccion> listaLotes, String descLote){
            for(int i = 0;i<listaLotes.size();i++)
                if(listaLotes.get(i).getDescLote().equals(descLote))
                    return true;
            return false;
        }

    };
    
    private final ActionListener listenerCerrarLotes = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            CerrarLotes cerrarLotesView = new CerrarLotes(vistaSeguimiento.getPrincipal(), true);
            CerrarLotesController cerrarLotesController = new CerrarLotesController(
            cerrarLotesView,new CerrarLotesModel(),ordenSeleccionada);
            cerrarLotesView.setVisible(true);
        }
    };
    
}
