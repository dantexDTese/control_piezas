package Controller.ProduccionController;

import Model.Constructores;
import Model.Estructuras;
import Model.ImgTabla;
import Model.LotePlaneado;
import Model.OrdenTrabajo;
import Model.ProduccionModel.AdminProduccionModel;
import Model.ProduccionModel.SeguimientoProduccionModel;
import Model.ordenProduccion;
import View.Produccion.AdminProduccionView;
import View.Produccion.SeguimientoProduccionDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class AdminProduccionController implements Constructores{
    
    /**
     * ATRIBUTOS
     */
    private final AdminProduccionView vista;
    private final AdminProduccionModel model;
    private LotePlaneado ordenSeleccionada;
    
    /**
     * CONSTRUCTOR
     * @param vista
     * @param model
     */
    public AdminProduccionController(AdminProduccionView vista, AdminProduccionModel model) {
        this.vista = vista;
        this.model = model;
        llenarComponentes();
        asignarEventos();
    }
    
    @Override
    public void llenarComponentes() {
        llenarTablaOrdenesTrabajo();
        vista.getJtbOrdenesTrabajo().setDefaultRenderer(Object.class,new ImgTabla());
        if(!vista.getPrincipal().getSesionIniciada().getDescUsuario().equals("PRODUCCION")){
            vista.getBtnSeguimientoProduccion().setEnabled(false);
        } 
    }

    @Override
    public void asignarEventos() {
        this.vista.getJtbOrdenesTrabajo().addMouseListener(listenerSeleccionOrdenTrabajo);
        this.vista.getJtbOrdenesProduccion().addMouseListener(listenerSeleccionOrdenProduccion);
        this.vista.getBtnModificar().addActionListener(listenerBotones);
        this.vista.getBtnGuardarModificacion().addActionListener(listenerBotones);
        this.vista.getBtnSeguimientoProduccion().addActionListener(listenerBotones);
        PropertyChangeListener listenerFechas = (PropertyChangeEvent evt) -> {llenarTablaOrdenesTrabajo();};
        this.vista.getJdcMes().addPropertyChangeListener(listenerFechas);
        this.vista.getJdcAnio().addPropertyChangeListener(listenerFechas);
        this.vista.getBtnBuscar().addActionListener(listenerBotones);
        this.vista.getBtnVerTodo().addActionListener(listenerBotones);
        this.vista.getBtnCancelarModificaciones().addActionListener(listenerBotones);
        this.vista.getBtnCerrar().addActionListener(listenerBotones);
    }
     
    /**
     * METODOS
     */
    private void llenarTablaOrdenesTrabajo(){    
        ArrayList<OrdenTrabajo> listaOrdenesTrabajo =  model.listaOrdenesTrabajo(
                vista.getTxtOrdenCompra().getText(),
                vista.getJdcAnio().getValue(),vista.getJdcMes().getMonth()+1);
        
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbOrdenesTrabajo().getModel());
        DefaultTableModel modelTabla = (DefaultTableModel) vista.getJtbOrdenesTrabajo().getModel();
        
        for(int i = 0;i<listaOrdenesTrabajo.size();i++){ 
            modelTabla.addRow(new Object[]{
                listaOrdenesTrabajo.get(i).getNoPedido(),
                listaOrdenesTrabajo.get(i).getNoOrdenCompra()
            });
        }
    }
    /**
     * EVENTOS
     */
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == vista.getBtnSeguimientoProduccion())
                mostrarSeguimientoProduccion();
            
            else if(e.getSource() == vista.getBtnBuscar())
                llenarTablaOrdenesTrabajo();

            else if(e.getSource() == vista.getBtnVerTodo()){
                vista.getTxtOrdenCompra().setText("");
                llenarTablaOrdenesTrabajo();
            }
            else if(e.getSource() == vista.getBtnModificar()){
                
                if(ordenSeleccionada != null)
                    activarCancelarModificacion(true);
                else
                    JOptionPane.showMessageDialog(null, "POR FAVOR PRIMERO SELECCIONE UNA ORDEN","VALIDACION",JOptionPane.WARNING_MESSAGE);
            }
            
            else if(e.getSource() == vista.getBtnGuardarModificacion())
                guardarModificacion();
            
            else if(e.getSource() == vista.getBtnCancelarModificaciones())
                activarCancelarModificacion(false);
            
            
            else if(e.getSource() == vista.getBtnCerrar()){
                int respuesta = JOptionPane.showConfirmDialog(null,"¿SEGURO QUE DESEA CERRAR ESTA ORDEN?","VALIDACION",JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                
                if(respuesta == JOptionPane.YES_OPTION){
                    model.cerrarOrdenProduccion(ordenSeleccionada.getNoOrdenProduccion());
                    activarCancelarModificacion(false);
                    vista.getBtnModificar().setEnabled(false);
                }
            }
        }     
        
        private void mostrarSeguimientoProduccion(){
            if(ordenSeleccionada != null){
                SeguimientoProduccionDialogView vistaSeguimiento = 
                        new SeguimientoProduccionDialogView(vista.getPrincipal(), true);
                SeguimientoProduccionController controllerSeguimiento = 
                        new SeguimientoProduccionController(vistaSeguimiento,new SeguimientoProduccionModel(),ordenSeleccionada);
                vistaSeguimiento.setVisible(true);
            
            }else JOptionPane.showMessageDialog(null, "POR FAVOR PRIMER SELECCIONE UNA ORDEN","VALIDACION",JOptionPane.WARNING_MESSAGE);                
        }
        
        
        private void guardarModificacion(){
            ordenSeleccionada.setDescEmpaque(vista.getTxtEmpaque().getText());
            if(vista.getJdcDesmontajeMolde().getDate() != null)
                ordenSeleccionada.setFechaDesmontaje(Estructuras.convertirFechaGuardar(vista.getJdcDesmontajeMolde().getDate()));
            ordenSeleccionada.setValidacion_compras(vista.getCheckAprobacionCompras().isSelected());
            ordenSeleccionada.setValidacion_produccion(vista.getCheckAprobacionProduccion().isSelected());
            ordenSeleccionada.setValidacion_matenimiento(vista.getCheckAprobacionMatenimiento().isSelected());
            ordenSeleccionada.setValidacion_calidad(vista.getCheckAprobacionCalidad().isSelected());
            model.guardarModificaciones(ordenSeleccionada);
            activarCancelarModificacion(false);
        }
        
        private void activarCancelarModificacion(boolean valor){
            vista.getTxtEmpaque().setEnabled(valor);
            vista.getJdcDesmontajeMolde().setEnabled(valor);
            vista.getCheckAprobacionCalidad().setEnabled(valor);
            vista.getCheckAprobacionCompras().setEnabled(valor);
            vista.getCheckAprobacionMatenimiento().setEnabled(valor);
            vista.getCheckAprobacionProduccion().setEnabled(valor);
            vista.getBtnGuardarModificacion().setEnabled(valor);
            vista.getBtnCancelarModificaciones().setEnabled(valor);
            vista.getBtnModificar().setEnabled(!valor);
            
            if(ordenSeleccionada.getDescEstadoOrdenProduccion().equals("ABIERTO")){
                vista.getBtnCerrar().setEnabled(valor);
                
            }else
                vista.getBtnModificar().setEnabled(false);   
        }      
    };
    
    private final MouseListener listenerSeleccionOrdenTrabajo = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e){
            super.mousePressed(e);
                int fila = vista.getJtbOrdenesTrabajo().rowAtPoint(e.getPoint());
                llenarTablaOrdenesProduccion(Integer.parseInt(vista.getJtbOrdenesTrabajo().getValueAt(fila, 0).toString()));
        }
    
        private void llenarTablaOrdenesProduccion(int ordenTrabajo){
            ArrayList<ordenProduccion> listaOrdenesProduccion = model.listaOrdenesProduccion(ordenTrabajo);
            Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbOrdenesProduccion().getModel());
            DefaultTableModel modelTabla = (DefaultTableModel) vista.getJtbOrdenesProduccion().getModel();
            
            for(int i = 0;i<listaOrdenesProduccion.size();i++)
                modelTabla.addRow(new Object[]{
                    listaOrdenesProduccion.get(i).getNoOrdenProduccion(),
                    listaOrdenesProduccion.get(i).getCodProducto(),
                });            
        }
    
    };        
        
    private final MouseListener listenerSeleccionOrdenProduccion = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);            
            int filaSeleccionada = vista.getJtbOrdenesProduccion().rowAtPoint(e.getPoint());
            llenarOrdenProduccion((Integer)vista.getJtbOrdenesProduccion().getValueAt(filaSeleccionada,0));
        }
        
        private void llenarOrdenProduccion(Integer noOrdenProduccion){
            ordenSeleccionada = model.obtenerOrdenProduccion(noOrdenProduccion);
            if(ordenSeleccionada!= null){
                vista.getLbNoOP().setText(ordenSeleccionada.getNoOrdenProduccion()+"");
                vista.getLbProducto().setText(ordenSeleccionada.getCodProducto());
                vista.getLbCliente().setText(ordenSeleccionada.getDescCliente());
                vista.getLbOrdenCompra().setText(ordenSeleccionada.getNoOrdenCompra());
                vista.getLbCantidad().setText(ordenSeleccionada.getCantidadTotal()+"");
                vista.getLbPiezasPorTurno().setText(ordenSeleccionada.getPiezasPorTurno()+"");
                vista.getLbMaterial().setText(ordenSeleccionada.getDescTipoMaterial()+" "+ordenSeleccionada.getClaveForma() + " "+ ordenSeleccionada.getDescDimencion() );
                vista.getLbMaquina().setText(ordenSeleccionada.getDescMaquina());
                vista.getLbCantidadProduccir().setText(ordenSeleccionada.getCantidadTotal()+"");
                vista.getLbBarrasNecesarias().setText(Math.ceil(ordenSeleccionada.getBarrasNecesarias())+"");
                vista.getLbFechaMontajeMolde().setText(ordenSeleccionada.getFechaMontaje());
                vista.getLbFechaArranqueProceso().setText(ordenSeleccionada.getFechaInicio());
                vista.getLbInicioProduccion().setText(ordenSeleccionada.getFechaInicio());
                vista.getLbFechaEntrega().setText(ordenSeleccionada.getFechaentrega());
                vista.getLbTurnosNecesarios().setText(ordenSeleccionada.getTurnosNecesarios()+"");
                vista.getLbFechaCerrada().setText(ordenSeleccionada.getFechaFin());
                vista.getJdcDesmontajeMolde().setDate(Estructuras.formateFecha(ordenSeleccionada.getFechaDesmontaje()));
                vista.getCheckAprobacionCompras().setSelected(ordenSeleccionada.isValidacion_compras());
                vista.getCheckAprobacionProduccion().setSelected(ordenSeleccionada.isValidacion_produccion());
                vista.getCheckAprobacionMatenimiento().setSelected(ordenSeleccionada.isValidacion_matenimiento());
                vista.getCheckAprobacionCalidad().setSelected(ordenSeleccionada.isValidacion_calidad());
                vista.getTxtEmpaque().setText(ordenSeleccionada.getDescEmpaque());
                vista.getLbFechaCerrada().setText(ordenSeleccionada.getFechaFin());
                
                if("CERRADO".equals(ordenSeleccionada.getDescEstadoOrdenProduccion()))
                    vista.getBtnModificar().setEnabled(false);
                
            }
            else
                JOptionPane.showMessageDialog(null,"error");       
        }
    };
}
