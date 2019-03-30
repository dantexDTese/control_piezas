package Controller.PedidosController;

import Model.Constructores;
import Model.Estructuras;
import Model.LotePlaneado;
import Model.PedidosModel.AjustesPlaneacionModel;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.AsignarDiasProduccionModel;
import Model.PedidosModel.PlaneacionModel;
import Model.PedidosModel.ProcesoPrincipal;
import View.Pedidos.AjustePlaneacion;
import View.Pedidos.AsignarDiasProduccion;
import View.Pedidos.AsignarMaquinaAPedido;
import View.Pedidos.PlaneacionView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class PlaneacionController implements Constructores{

    
    private final PlaneacionView vista;
    private final PlaneacionModel model;
    private ProcesoPrincipal procesoPrincipal; 
    private ArrayList<LotePlaneado> procedimientos;
    
    /**
     * Constructor
     * @param vista
     * @param model
     */
    
    public PlaneacionController(PlaneacionView vista, PlaneacionModel model) {
        
        //INICIALIZAR
        this.vista = vista;
        this.model = model;
        
        llenarComponentes();
        asignarEventos();
        
    }
    
    @Override
    public void llenarComponentes() {
        
        llenarListaMaquinas();
        this.vista.getJpCalendar().setSize(950,350);        
        
     if(vista.getCbxListaMaquinas().getSelectedItem() != null){
            llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
            Estructuras.obtenerCalendario(this.vista.getJpCalendar(),this.vista.getCbxListaMaquinas().getSelectedItem().toString());        
        } 
    }

    @Override
    public void asignarEventos() {
     
        this.vista.getCbxListaMaquinas().addItemListener(maquinaSeleccionada);
        this.vista.getBtnAgregarOrdenesPendientes().addActionListener(listenerBotones);               
        this.vista.getBtnAgregarOrdenesMaquinas().addActionListener(listenerBotones);
        this.vista.getJdcAnio().addPropertyChangeListener(listenerFecha);
        this.vista.getJdcMes().addPropertyChangeListener(listenerFecha);
        this.vista.getTbLIstaPedidosMaquina().addMouseListener(modificarLotesPlaneados);
    }
    
    private void llenarListaMaquinas(){
        ArrayList<String> maquinas = model.listaMaquinas();            
        if(maquinas.size()>0){
            vista.getCbxListaMaquinas().removeAllItems();
            for(int i = 0;i<maquinas.size();i++)
                vista.getCbxListaMaquinas().addItem(maquinas.get(i));
        }
    }
    
    private void llenarTablaMaquinas(String nombreMaquina){
        if(nombreMaquina != null){
            
            Estructuras.limpiarTabla((DefaultTableModel) vista.getTbLIstaPedidosMaquina().getModel());
            procedimientos = model.listaProcedimientoMaquina(nombreMaquina,vista.getJdcMes().getMonth()+1,vista.getJdcAnio().getValue());
            DefaultTableModel modelMaquinas = (DefaultTableModel) vista.getTbLIstaPedidosMaquina().getModel();

            for(int i = 0;i<procedimientos.size();i++){
                LotePlaneado procedimiento = procedimientos.get(i);

                modelMaquinas.addRow(new Object[]{procedimiento.getOrdenTrabajo(),
                    procedimiento.getCodProducto(),procedimiento.getCantidadTotal(),procedimiento.getNoOrdenCompra(),
                    procedimiento.getNoOrdenProduccion(),procedimiento.getPiezasPorTurno(),procedimiento.getDescTipoMaterial() +" "+
                    procedimiento.getClaveForma()+ " " +procedimiento.getDescDimencion(),procedimiento.getWorker(),procedimiento.getTipoProceso()});
            }
        }
    }
    
    
    private final MouseListener modificarLotesPlaneados = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            
            int fila = vista.getTbLIstaPedidosMaquina().rowAtPoint(e.getPoint());   
            
            LotePlaneado loteSeleccionado = buscarLote(
                    Integer.parseInt(vista.getTbLIstaPedidosMaquina().getValueAt(fila, 0).toString()),
                    Integer.parseInt(vista.getTbLIstaPedidosMaquina().getValueAt(fila, 4).toString()));
            
            if(loteSeleccionado != null)
                mostrarReplaneacion(loteSeleccionado);
            
        }

        private LotePlaneado buscarLote(int noPedido, int ordenProduccion) {
            
            for(int i = 0;i<procedimientos.size();i++){
                LotePlaneado lp = procedimientos.get(i);
                if(lp.getOrdenTrabajo() == noPedido && lp.getNoOrdenProduccion() == ordenProduccion)
                    return lp;
            }
            
            return null;
        }

        private void mostrarReplaneacion(LotePlaneado loteSeleccionado) {
            
            AjustePlaneacion vistaAjuste = new AjustePlaneacion(vista.getPrincpial(), true);
            AjustesPlaneacionController controllerAjuste = new AjustesPlaneacionController(
            vistaAjuste,new AjustesPlaneacionModel(),loteSeleccionado);
            vistaAjuste.setVisible(true);     
        }
        
    };
    
    private final ItemListener maquinaSeleccionada = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(vista.getCbxListaMaquinas().getSelectedItem() != null) {
                if (!"".equals(vista.getCbxListaMaquinas().getSelectedItem().toString())) {
                llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
                Estructuras.obtenerCalendario(vista.getJpCalendar(),vista.getCbxListaMaquinas().getSelectedItem().toString());        
                }
            }
        }
    };
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource() == vista.getBtnAgregarOrdenesPendientes())
                agregarOrdenesPendientes();
            else if(e.getSource() == vista.getBtnAgregarOrdenesMaquinas())
                agregarOrdenesMaquinas();
            
        }
        
         private void agregarOrdenesPendientes() {
            AsignarMaquinaAPedido vistaMaquinaPedido = new AsignarMaquinaAPedido(vista.getPrincpial(), true); 
            AsignacionMaquinaAPedidoController controllerMaquinaPedido = new AsignacionMaquinaAPedidoController(vistaMaquinaPedido
                    , new AsignacionMaquinaAPedidoModel());            
            vistaMaquinaPedido.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e); 
                    llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
                    Estructuras.obtenerCalendario(vista.getJpCalendar(),vista.getCbxListaMaquinas().getSelectedItem().toString());        
                }                
            });
            vistaMaquinaPedido.setVisible(true); 
    }
        
        private void agregarOrdenesMaquinas(){
            AsignarDiasProduccion diasProduccionView = new AsignarDiasProduccion(vista.getPrincpial(), true);
            AsignarDiasProduccionController diasProduccionController = new AsignarDiasProduccionController(diasProduccionView,new AsignarDiasProduccionModel());
            diasProduccionView.setVisible(true);
            diasProduccionView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
                    Estructuras.obtenerCalendario(vista.getJpCalendar(),vista.getCbxListaMaquinas().getSelectedItem().toString());        
                }
                
            });
        }
        
    };
    
    private final PropertyChangeListener listenerFecha = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
        }
    };
    
    
}