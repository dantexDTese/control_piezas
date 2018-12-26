
package Controller.PedidosController;

import Model.Estructuras;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.AsignarDiasProduccionModel;
import Model.PedidosModel.PlaneacionModel;
import Model.PedidosModel.ProcesoPrincipal;
import Model.PedidosModel.lotesProduccion;
import Model.PedidosModel.procedimientoTotal;
import View.Pedidos.AsignarDiasProduccion;
import View.Pedidos.AsignarMaquinaAPedido;
import View.Pedidos.PlaneacionView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;



public class PlaneacionController  {

    /**
     * Atributos
     */
    private final PlaneacionView vista;
    private final PlaneacionModel model;
    private ProcesoPrincipal procesoPrincipal; 
    private final ArrayList<lotesProduccion> listaLotes;
    
    /**
     * Constructor
     * @param vista
     * @param model
     */
    
    public PlaneacionController(PlaneacionView vista, PlaneacionModel model) {
        
        //INICIALIZAR
        this.listaLotes = new ArrayList<>();
        this.vista = vista;
        this.model = model;
        llenarListaMaquinas();
        this.vista.getJpCalendar().setSize(800,350);        
        if(vista.getCbxListaMaquinas().getSelectedItem() != null){
            llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
            Estructuras.obtenerCalendario(this.vista.getJpCalendar(),this.vista.getCbxListaMaquinas().getSelectedItem().toString());        
        }        
        
        //EVENTOS
        this.vista.getCbxListaMaquinas().addItemListener(maquinaSeleccionada);
        this.vista.getBtnAgregarOrdenesPendientes().addActionListener(listenerBotones);               
        this.vista.getBtnAgregarOrdenesMaquinas().addActionListener(listenerBotones);
        
        
    }
    
    //EVENTOS
    private final ItemListener maquinaSeleccionada = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(vista.getCbxListaMaquinas().getSelectedItem() != null) {
                if (!"".equals(vista.getCbxListaMaquinas().getSelectedItem().toString())) {
                llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
                Estructuras.obtenerCalendario(vista.getJpCalendar(),vista.getCbxListaMaquinas().getSelectedItem().toString());        
                //obtenerProcesoPrincipal(vista.getCbxListaMaquinas().getSelectedItem().toString());
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
                }                
            });
            vistaMaquinaPedido.setVisible(true); 
    }
        
        private void agregarOrdenesMaquinas(){
            AsignarDiasProduccion diasProduccionView = new AsignarDiasProduccion(vista.getPrincpial(), true);
            AsignarDiasProduccionController diasProduccionController = new AsignarDiasProduccionController(diasProduccionView,new AsignarDiasProduccionModel());
            diasProduccionView.setVisible(true);
        }
        
    };
    
    //METHODOS
   
    private void limbiarCampos(){
        vista.getLbProductoEnProceso().setText("");
            vista.getLbCantidadTotal().setText("");      
            vista.getLbProcesoActual().setText("");
            vista.getLbCantidadProcesada().setText("");
            vista.getLbCantidadRestante().setText("");
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
        ArrayList<procedimientoTotal> procedimientos = model.listaProcedimientoMaquina(nombreMaquina);
        DefaultTableModel modelMaquinas = (DefaultTableModel) vista.getTbLIstaPedidosMaquina().getModel();
        
        
            for(int i = 0;i<procedimientos.size();i++){
                procedimientoTotal procedimiento = procedimientos.get(i);
                modelMaquinas.addRow(new Object[]{procedimiento.getNoOrdenTrabajo(),
                procedimiento.getClaveProducto(),procedimiento.getQty(),procedimiento.getNoOrdenCompra(),
                procedimiento.getNoOrdenProduccion(),procedimiento.getPiecesByShift(),procedimiento.getMaterial(),procedimiento.getWorker(),
                procedimiento.getProcesoActual()});
            }
        }
        
        
    }
}