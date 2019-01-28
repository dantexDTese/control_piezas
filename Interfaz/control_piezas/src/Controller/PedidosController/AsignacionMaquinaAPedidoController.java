package Controller.PedidosController;
import Model.Constructores;
import Model.Estructuras;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.ProductoPendiente;
import Model.PedidosModel.Pedido;
import Model.ProductoMaquina;
import View.Pedidos.AsignarMaquinaAPedido;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;


public final class AsignacionMaquinaAPedidoController implements Constructores{

    /**
    * ATRIBUTOS
    */
    private final AsignarMaquinaAPedido vista;
    private final AsignacionMaquinaAPedidoModel model;
    
    
    private ArrayList<ProductoPendiente> pendientes;
    private final ArrayList<ProductoPendiente> agregados;
    private ProductoPendiente pendiente = null;                
    
    private String noOrdenTrabajo;
    private int pienzasTurnoProductoSeleccionado=0;
    /**
     * CONSTRUCTOR
     * @param vista
     * @param model
     */
    public AsignacionMaquinaAPedidoController(AsignarMaquinaAPedido vista,AsignacionMaquinaAPedidoModel model){     
        this.vista = vista;
        this.model = model;                
        agregados = new ArrayList<>();
        llenarComponentes();
        asignarEventos();
    }
    
    @Override
    public void llenarComponentes() {
        llenarTablaPedidosPendientes();        
        if(this.vista.getCbxMaquina().getSelectedItem() != null)
            Estructuras.obtenerCalendario(this.vista.getJpCalendario(),this.vista.getCbxMaquina().getSelectedItem().toString());
    }

    @Override
    public void asignarEventos() {
        vista.getJtOrdenesPendientes().addMouseListener(listenerSeleccionarPendiente);        
        vista.getBtnGuardar().addActionListener(listenerGuardarCambiosOrden);             
        vista.getBtnTerminar().addActionListener(listenerTerminarPendientes);
        vista.getCbxMaquina().addActionListener(listenerCbxMaquina);
        vista.getJdcFechaMontajeMolde().addPropertyChangeListener(listenerValidacionFecha);
        vista.getDcrFechaInicioProduccion().addPropertyChangeListener(listenerValidacionFecha);
        vista.getCheckOrdenCompleta().addActionListener(listenerCheckTodosDidas);
        vista.getCbxProducto().addActionListener(listenerCbxProducto);        
        vista.getSprPiecesByShift().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                obtenerNumDiasTrabajo();
            }
        });
    }
    
    /**
     * METODOS
     */
    private void llenarTablaPedidosPendientes(){
        
        ArrayList<Pedido> listaPendientes = model.listaPedidosPendientes();
        DefaultTableModel modelT = (DefaultTableModel)vista.getJtOrdenesPendientes().getModel();
        Estructuras.limpiarTabla(modelT);
        for(int i = 0;i<listaPendientes.size();i++){
            Pedido ped = listaPendientes.get(i);
            modelT.addRow(new Object[]{ped.getNoOrdenTrabajo(),ped.getNoOrdenCompra(),ped.getFechaRecepcion()});
        }
        
    }
    
    private void llenarListaProductos(){
        
        if(vista.getCbxProducto().getItemCount() >  0)
            vista.getCbxProducto().removeAllItems();
        
        for(int i = 0;i<pendientes.size();i++)
            vista.getCbxProducto().addItem(pendientes.get(i).getCodProducto());  
        
    }

    private void limpiar(){
    
        vista.getLbNoOrdenProduccion().setText("");
        vista.getLbCantidadCliente().setText("");
        vista.getSprCantidadProducir().setValue(0);
        vista.getSprPiecesByShift().setValue(0);
        vista.getJdcFechaMontajeMolde().setDate(null);
        vista.getDcrFechaInicioProduccion().setDate(null);
        vista.getSprDiasTrabajar().setValue(0);
    
    }
    
    private void obtenerNumDiasTrabajo(){
        if(vista.getCheckOrdenCompleta().isSelected()){        
            int dias =  (int)Math.ceil(Float.parseFloat(vista.getSprCantidadProducir().getValue().toString())/
                                    Float.parseFloat(vista.getSprPiecesByShift().getValue().toString()));            
            vista.getSprDiasTrabajar().setValue(dias);
        }    
    }
    
    
    /**
     * EVENTOS
     */
    
    private final ActionListener listenerCheckTodosDidas = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(vista.getCheckOrdenCompleta().isSelected()){        
                obtenerNumDiasTrabajo();
                vista.getSprDiasTrabajar().setEnabled(false);
            }else{
                vista.getSprDiasTrabajar().setEnabled(true);
            }
            
        }
    };
    
    private final ActionListener listenerCbxProducto = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {            
            if(pendientes != null && vista.getCbxProducto().getItemCount()>0){                
                limpiar();
                if((pendiente = obtenerAgregado(vista.getCbxProducto().getSelectedItem().toString())) == null)
                    inicializar();                   
                
                else if(JOptionPane.showConfirmDialog(null,"Modificacion",
                    "¿Deseas modificar este producto ya agregado?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 
                    JOptionPane.YES_OPTION)
                    obtenerAgregado();
            }
        }
             
        private void inicializar(){
            pendiente = obtenerPendiente(pendientes,vista.getCbxProducto().getSelectedItem().toString());
            pendiente.setProductosMaquina(model.obtenerListaProductoMaquina(pendiente.getCodProducto()));
            vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
            vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
            vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente());
            vista.getLbMaterial().setText(pendiente.getDescTipoMaterial());
            vista.getLbForma().setText(pendiente.getClaveForma());
            vista.getLbDimencion().setText(pendiente.getDescDimencion());
            
            if(vista.getCbxMaquina().getItemCount()>0)
                vista.getCbxMaquina().removeAllItems();
            
            for(int i = 0;i<pendiente.getProductosMaquina().size();i++)
                vista.getCbxMaquina().addItem(pendiente.getProductosMaquina().get(i).getDescMaquina());
            
        }
        
        private void obtenerAgregado(){
            vista.getSprCantidadProducir().setValue(pendiente.getCantidadTotal());
            if(pendiente.getWorker()==1)
               vista.getCbxWorker().setSelectedIndex(1);
            
            vista.getSprPiecesByShift().setValue(pendiente.getPiezasPorTurno());
            vista.getJdcFechaMontajeMolde().setDate(new Date(pendiente.getFechaMontaje()));
            vista.getDcrFechaInicioProduccion().setDate(new Date(pendiente.getFechaInicio()));
            vista.getCbxMaquina().setSelectedIndex(obtenerIndex(vista.getCbxMaquina(),pendiente.getDescMaquina()));
            vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
            vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
            vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente()); 
        }
        
        private ProductoPendiente obtenerPendiente(ArrayList<ProductoPendiente> lista,String claveProductos){
        for(int i = 0;i< lista.size();i++)
            if(claveProductos.equals( lista.get(i).getCodProducto()))
                return lista.get(i);
        return null;
    }
    
        private ProductoPendiente obtenerAgregado(String claveProducto) {
        ProductoPendiente pendiente=null;        
        for(int i = 0;i<agregados.size();i++)
            if(agregados.get(i).getCodProducto().equals(claveProducto)){
                pendiente = agregados.get(i);
            }
        return pendiente;
   
        }

        private int obtenerIndex(JComboBox lista, String material){
            for(int i=0;i<lista.getItemCount();i++)
                if(lista.getItemAt(i).equals(material))
                    return i;
            
            return 0;
        }
    };
    
    private final ActionListener listenerCbxMaquina = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vista.getCbxMaquina().getItemCount() > 0){
                Estructuras.obtenerCalendario(vista.getJpCalendario(),vista.getCbxMaquina().getSelectedItem().toString());
                
                ArrayList<ProductoMaquina> maquinasProducto = pendiente.getProductosMaquina();
                for(int i = 0;i<maquinasProducto.size();i++){
                   
                   if(maquinasProducto.get(i).getDescMaquina().equals(vista.getCbxMaquina().getSelectedItem().toString())){
                       vista.getSprPiecesByShift().setValue(maquinasProducto.get(i).getPiezasPorTurno());
                       pendiente.setProductoMaquinaSeleccionado(maquinasProducto.get(i));
                   }
                }
                
                
            }
        }
    };
    
    private final ActionListener listenerGuardarCambiosOrden = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(validarCampos()){
                
                Integer index;
                pendiente.setCantidadTotal(Integer.parseInt(vista.getSprCantidadProducir().getValue().toString()));
                pendiente.setPiezasPorTurno(Integer.parseInt(vista.getSprPiecesByShift().getValue().toString()));
                pendiente.setDescMaquina(vista.getCbxMaquina().getSelectedItem().toString());
                pendiente.setWorker(Float.parseFloat(vista.getCbxWorker().getSelectedItem().toString()));
                pendiente.setFechaMontaje(Estructuras.convertirFechaGuardar(vista.getJdcFechaMontajeMolde().getDate()));
                pendiente.setFechaInicio(Estructuras.convertirFechaGuardar(vista.getDcrFechaInicioProduccion().getDate()));        
                pendiente.setDiasTrabajo(vista.getSprDiasTrabajar().getValue());


                //SI EL PRODUCTO NO EXISTE EN LA LISTA AGREGADOS SE AGREGA DE LO CONTRARIO SE REMPLAZA
                if((index = obtenerIndexAgregado(pendiente.getCodProducto())) == null) agregados.add(pendiente);
                else agregados.set(index, pendiente);

                //ACTUALIZA LA TABLA 
                llenarTablaAgregados(agregados);            

                limpiar();
                
            }
            else JOptionPane.showMessageDialog(null, "POR FAVOR COMPLETE TODOS LOS CAMPOS","VALIDACION",JOptionPane.ERROR_MESSAGE);
        }
       
        private Integer obtenerIndexAgregado(String claveProducto){
            Integer index = null;

            for(int i = 0 ;i<agregados.size();i++)
                if(agregados.get(i).getCodProducto().equals(claveProducto))
                    index = i;

            return index;
        }
        
        private void llenarTablaAgregados(ArrayList<ProductoPendiente> pendientes){
            Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductosAgregados().getModel());
            DefaultTableModel model;
            model = (DefaultTableModel) vista.getJtbProductosAgregados().getModel();

            for(int i = 0;i<pendientes.size();i++){
                ProductoPendiente pendiente = pendientes.get(i);
                model.addRow(new Object[]{pendiente.getCodProducto(),pendiente.getFechaMontaje(),
                pendiente.getFechaInicio(),pendiente.getCantidadTotal(),pendiente.getDescMaquina(),pendiente.getPiezasPorTurno(),
                pendiente.getDescTipoMaterial()+" "+pendiente.getClaveForma()+" "+pendiente.getDescDimencion(),pendiente.getWorker(),pendiente.getDiasTrabajo()});
            }
        
        }
        
        private boolean validarCampos(){
            
            if(Integer.parseInt(vista.getSprCantidadProducir().getValue().toString()) >0 &&
                vista.getSprDiasTrabajar().getValue() > 0 && Integer.parseInt(vista.getSprPiecesByShift().getValue().toString()) > 0
                && vista.getJdcFechaMontajeMolde().getDate() != null && vista.getDcrFechaInicioProduccion().getDate() !=null
                    && !"".equals(vista.getLbNoOrdenProduccion().getText()))
                return true;
            
            return false;
        }
    };
    
    private final MouseListener listenerSeleccionarPendiente = new MouseAdapter() {
          
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); 
                if(agregados != null ){
                    seleccionarOrdenTrabajo(e.getPoint());
                    llenarListaProductos();
                }else
                    JOptionPane.showMessageDialog(null, "debe completar la planeacion para la actual orden de trabajo");
            }
            
            private void seleccionarOrdenTrabajo(Point pFila){
                int fila = vista.getJtOrdenesPendientes().rowAtPoint(pFila);
                noOrdenTrabajo = vista.getJtOrdenesPendientes().getValueAt(fila,0)+"";
                vista.getLbOrdenCompra().setText(vista.getJtOrdenesPendientes().getValueAt(fila,1).toString());
                pendientes = model.listaProductosPendientes(noOrdenTrabajo);
            }
    };
    
    private final PropertyChangeListener listenerValidacionFecha = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            
            if(evt.getSource() == vista.getJdcFechaMontajeMolde())
                validarFechaMontaje();
            
            else if(evt.getSource() == vista.getDcrFechaInicioProduccion())
                validarFechaInicio(vista.getJdcFechaMontajeMolde().getDate(),
                        vista.getDcrFechaInicioProduccion().getDate());
        }
        
        private void validarFechaInicio(Date fechaMontaje, Date fechaInicio) { 
                if(fechaMontaje == null && fechaInicio != null){
                    JOptionPane.showMessageDialog(null,"por favor antes seleccione una fecha de montaje");
                    vista.getDcrFechaInicioProduccion().setCalendar(null);
                }else if(fechaInicio != null && fechaMontaje!=null){
                    validarTodasFechas(vista.getJtOrdenesPendientes().getValueAt(obtenerFilaOrdenTrabajo(noOrdenTrabajo), 2).toString(),
                    Estructuras.convertirFechaGuardar(fechaMontaje),Estructuras.convertirFechaGuardar(fechaInicio));   
                }
        }
        
        private void validarTodasFechas(String fechaEntrega, String fechaMontaje, String fechaInicio) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");            
            try {
                Date fechaE = dateFormat.parse(fechaEntrega.replace('/', '-'));
                Date fechaM = dateFormat.parse(fechaMontaje.replace('/', '-'));
                Date fechaI = dateFormat.parse(fechaInicio.replace('/', '-'));    
                
                if(fechaM.compareTo(fechaI) >= 0 || fechaI.compareTo(fechaE) >= 0){
                    JOptionPane.showMessageDialog(null, "esta fecha no es valida");
                    vista.getDcrFechaInicioProduccion().setCalendar(null);
                }else if(!validarFecha(Integer.parseInt(vista.getSprPiecesByShift().getValue().toString()),
                                Integer.parseInt(vista.getSprCantidadProducir().getValue().toString()),fechaI,
                                vista.getJtOrdenesPendientes().getValueAt(obtenerFilaOrdenTrabajo(noOrdenTrabajo), 2).toString(),
                                Float.parseFloat(vista.getCbxWorker().getSelectedItem().toString())))
                        vista.getDcrFechaInicioProduccion().setCalendar(null);
                           
            } catch (ParseException ex) {
                Logger.getLogger(AsignacionMaquinaAPedidoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        private void validarFechaMontaje(){
            boolean validacion=true;
            try {
                if((int)vista.getSprPiecesByShift().getValue() > 0 && vista.getJdcFechaMontajeMolde().getDate() != null){
                        validacion = validarFecha(Integer.parseInt(vista.getSprPiecesByShift().getValue().toString()),
                                Integer.parseInt(vista.getSprCantidadProducir().getValue().toString()),
                                vista.getJdcFechaMontajeMolde().getDate(),
                                vista.getJtOrdenesPendientes().getValueAt(obtenerFilaOrdenTrabajo(noOrdenTrabajo), 2).toString(),
                                Float.parseFloat(vista.getCbxWorker().getSelectedItem().toString()));  
                    if(!validacion)
                        vista.getJdcFechaMontajeMolde().setCalendar(null);
                }
                
            } catch (NumberFormatException e) {
                System.err.println("error: class: AsignacionMaquinaAPedidoController,ListenerValidacionFecha "+e.getMessage());
            }            
        }
        
        private boolean validarFecha(int piezasTurno,int cantidadTotal,Date fechaSeleccionada,String fechaEntrega,float turnos){
            turnos = (turnos < 1)? 1:2;
            int diasNecesarios = (int)(Math.ceil((float)cantidadTotal/(float)piezasTurno) / turnos);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");            
            try {                
                Date dateEntrega = dateFormat.parse(fechaEntrega);
                int diasSeleccionado=(int) ((dateEntrega.getTime()-fechaSeleccionada.getTime())/86400000);
                
                if(diasSeleccionado < 0 || diasSeleccionado+1 < diasNecesarios ||
                        Estructuras.convertirFechaGuardar(dateEntrega).equals(Estructuras.convertirFechaGuardar(fechaSeleccionada))){
                     JOptionPane.showMessageDialog(null, "la fecha seleccionada no es valida","fecha no valida",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
            } catch (ParseException ex) {
                Logger.getLogger(AsignacionMaquinaAPedidoController.class.getName()).log(Level.SEVERE, null, ex);
            }            
            return true;                  
        }

        private int obtenerFilaOrdenTrabajo(String ordenTrabajo) {
            DefaultTableModel model = (DefaultTableModel) vista.getJtOrdenesPendientes().getModel();
            int fila = 0;
                while(!model.getValueAt(fila,0).toString().equals(ordenTrabajo)) fila++;
            return fila;
        }  
    };

    private final ActionListener listenerTerminarPendientes = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pendientes != null){
                for(int i = 0;i<pendientes.size();i++)
                    if(model.agregarProductoPendiente(pendientes.get(i),i+1)==null) break;                
              vista.dispose();
            }
                    
            else
                JOptionPane.showMessageDialog(null,"LE FALTA AGREGAR PRODUCTOS A LA PLANEACION");                       
        }
    };
    
}
