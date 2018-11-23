package Controller.PedidosController;
import Model.Estructuras;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import View.Pedidos.AsignarMaquinaAPedido;
import com.toedter.calendar.JDateChooser;
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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class AsignacionMaquinaAPedidoController {

    /**
     * ATRIBUTOS
     */
    private final AsignarMaquinaAPedido vista;
    private final AsignacionMaquinaAPedidoModel model;
    private ArrayList<ProductosPendientes> pendientes;
    private final ArrayList<ProductosPendientes> agregados;
    private ProductosPendientes pendiente=null;                
    private String ordenTrabajo;
    
    /**
     * CONSTRUCTOR
     * @param vista
     * @param model
     */
    
    public AsignacionMaquinaAPedidoController(AsignarMaquinaAPedido vista,AsignacionMaquinaAPedidoModel model){     
        this.vista = vista;
        this.model = model;                
        llenarListaMaquinas();
        llenarListaMateriales();        
        this.vista.getJtOrdenesPendientes().addMouseListener(listenerSeleccionarPendiente);        
        this.vista.getBtnGuardar().addActionListener(listenerGuardarCambiosOrden);           
        this.vista.getCbxMateriales().addActionListener(listenerObtenerPiezasTurno);
        llenarTablaPedidosPendientes();        
        
        agregados = new ArrayList<>();
        if(this.vista.getCbxMaquina().getSelectedItem() != null)
            Estructuras.obtenerCalendario(this.vista.getJpCalendario(),this.vista.getCbxMaquina().getSelectedItem().toString());
        
        this.vista.getBtnTerminar().addActionListener(listenerTerminarPendientes);
        
        this.vista.getCbxMaquina().addActionListener((ActionEvent e) -> {
            Estructuras.obtenerCalendario(vista.getJpCalendario(),vista.getCbxMaquina().getSelectedItem().toString());
        });
        
        this.vista.getJdcFechaMontajeMolde().addPropertyChangeListener(listenerValidacionFecha);
        this.vista.getDcrFechaInicioProduccion().addPropertyChangeListener(listenerValidacionFecha);
    }
    
    /**
     * METODOS
     */
     
    
   
    private void llenarTablaPedidosPendientes(){        
        ArrayList<Pedido> listaPendientes = model.listaPedidosPendientes();
        DefaultTableModel modelT = (DefaultTableModel) vista.getJtOrdenesPendientes().getModel();
        Estructuras.limpiarTabla(modelT);
        for(int i = 0;i<listaPendientes.size();i++){
            Pedido ped = listaPendientes.get(i);
            modelT.addRow(new Object[]{ped.getNoOrdenCompra(),
                ped.getNoOrdenTrabajo(),ped.getFechaRecepcion()});
        }
    }
    
    private void llenarListaMaquinas(){
        ArrayList<String> maquinas = model.listaMaquinas();
        for(int i = 0;i<maquinas.size();i++)
            vista.getCbxMaquina().addItem(maquinas.get(i));
    }
    
    private void llenarListaMateriales(){
        ArrayList<String> materiales = model.listaMateriales();
        for(int i = 0;i<materiales.size();i++)
            vista.getCbxMateriales().addItem(materiales.get(i));
    }
    
    private void llenarListaProductos(){
        for(int i = 0;i<pendientes.size();i++)
            vista.getCbxProducto().addItem(pendientes.get(i).getClaveProducto());  
        vista.getCbxProducto().addActionListener(listenerCbxProducto);        
    }

    private void limpiar(){
    
        vista.getLbNoOrdenProduccion().setText("");
        vista.getLbCantidadCliente().setText("");
        vista.getSprCantidadProducir().setValue(0);
        vista.getSprPiecesByShift().setValue(0);
        vista.getJdcFechaMontajeMolde().setDate(null);
        vista.getDcrFechaInicioProduccion().setDate(null);
    
    }
    
    /**
     * EVENTOS
     */
        
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
            vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
            vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
            vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente());
        }
        
        private void obtenerAgregado(){
            vista.getSprCantidadProducir().setValue(pendiente.getQty());
            if(pendiente.getWorker()==1)
               vista.getCbxWorker().setSelectedIndex(1);
            vista.getSprPiecesByShift().setValue(pendiente.getPiecesByShift());
            vista.getCbxMateriales().setSelectedIndex(obtenerIndex(vista.getCbxMateriales(),pendiente.getMaterial()));
            vista.getJdcFechaMontajeMolde().setDate(new Date(pendiente.getFechaMontaje()));
            vista.getDcrFechaInicioProduccion().setDate(new Date(pendiente.getFechaInicio()));
            vista.getCbxMaquina().setSelectedIndex(obtenerIndex(vista.getCbxMaquina(),pendiente.getMaquina()));
            vista.getLbNoOrdenProduccion().setText(pendiente.getNoOrdenProduccion()+"");
            vista.getLbCantidadCliente().setText(pendiente.getCantidadCliente()+"");
            vista.getSprCantidadProducir().setValue(pendiente.getCantidadCliente());
        }
        
        private ProductosPendientes obtenerPendiente(ArrayList<ProductosPendientes> lista,String claveProductos){
        for(int i = 0;i< lista.size();i++)
            if(claveProductos.equals( lista.get(i).getClaveProducto()))
                return lista.get(i);
        return null;
    }
    
         private ProductosPendientes obtenerAgregado(String claveProducto) {
        ProductosPendientes pendiente=null;        
        for(int i = 0;i<agregados.size();i++)
            if(agregados.get(i).getClaveProducto().equals(claveProducto))
                pendiente = agregados.get(i);
        
        return pendiente;
    }

        private int obtenerIndex(JComboBox lista, String material){
            for(int i=0;i<lista.getItemCount();i++)
                if(lista.getItemAt(i).equals(material))
                    return i;
            
            return 0;
        }
    };
    
    private final ActionListener listenerGuardarCambiosOrden = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AgregarPendiente();
            limpiar();            
        }
        
        private void AgregarPendiente(){        
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");            
            Integer index;
            
            pendiente.setQty(Integer.parseInt(vista.getSprCantidadProducir().getValue().toString()));
            pendiente.setPiecesByShift(Integer.parseInt(vista.getSprPiecesByShift().getValue().toString()));
            pendiente.setMaquina(vista.getCbxMaquina().getSelectedItem().toString());
            pendiente.setMaterial(vista.getCbxMateriales().getSelectedItem().toString());
            pendiente.setWorker(Float.parseFloat(vista.getCbxWorker().getSelectedItem().toString()));
            pendiente.setFechaMontaje(sdf.format(vista.getJdcFechaMontajeMolde().getDate()));
            pendiente.setFechaInicio(sdf.format(vista.getDcrFechaInicioProduccion().getDate()));        
                                    
            if((index = obtenerIndexAgregado(pendiente.getClaveProducto())) == null)
                agregados.add(pendiente);
            else 
                agregados.set(index, pendiente);
            
            llenarTablaAgregados(agregados);            
        }
        
        
        private Integer obtenerIndexAgregado(String claveProducto){
            Integer index = null;

            for(int i = 0 ;i<agregados.size();i++)
                if(agregados.get(i).getClaveProducto().equals(claveProducto))
                    index = i;

            return index;
        }
        
        private void llenarTablaAgregados(ArrayList<ProductosPendientes> pendientes){
            Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductosAgregados().getModel());
            DefaultTableModel model;
            model = (DefaultTableModel) vista.getJtbProductosAgregados().getModel();

            for(int i = 0;i<pendientes.size();i++){
                ProductosPendientes pendiente = pendientes.get(i);
                model.addRow(new Object[]{pendiente.getClaveProducto(),pendiente.getFechaMontaje(),
                pendiente.getFechaInicio(),pendiente.getQty(),pendiente.getMaquina(),pendiente.getPiecesByShift(),
                pendiente.getMaterial(),pendiente.getWorker()});
            }
        
        }
    };
    
    private final MouseListener listenerSeleccionarPendiente = new MouseAdapter() {
          
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); 
                if(pendientes == null){
                    seleccionarOrdenTrabajo(e.getPoint());
                    llenarListaProductos();
                }else
                    JOptionPane.showMessageDialog(null, "debe completar la planeacion para la actual orden de trabajo");
            }
            
            private void seleccionarOrdenTrabajo(Point pFila){
                int fila = vista.getJtOrdenesPendientes().rowAtPoint(pFila);
                ordenTrabajo = vista.getJtOrdenesPendientes().getValueAt(fila,1)+"";
                vista.getLbOrdenCompra().setText(vista.getJtOrdenesPendientes().getValueAt(fila,0).toString());
                pendientes = model.listaProductosPendientes(ordenTrabajo);
            }
    };
    
    private final ActionListener listenerTerminarPendientes = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pendientes != null){
                for(int i = 0;i<pendientes.size();i++)
                    if(model.agregarProductoPendiente(pendientes.get(i),i+1)==null)
                        break;                
              llenarTablaPedidosPendientes();  
              Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductosAgregados().getModel());     
              vista.getCbxProducto().removeAllItems();
            }
                    
            else
                JOptionPane.showMessageDialog(null,"la tabla de productos pendientes esta vacia");                       
        }
    };

    private final ActionListener listenerObtenerPiezasTurno = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vista.getCbxProducto().getSelectedItem() != ""){
                String producto = vista.getCbxProducto().getSelectedItem().toString();
                if(!"".equals(producto))
                    obtenerPiezasTurno(vista.getCbxProducto().getSelectedItem().toString()
                            ,vista.getCbxMateriales().getSelectedItem().toString());
            }               
        }
        
        private void obtenerPiezasTurno(String claveProducto, String descMaterial) {
            Integer piezasTurno = model.obtenerPiezasTurno(claveProducto,descMaterial);
            if(piezasTurno != null)
                vista.getSprPiecesByShift().setValue(piezasTurno);
        }
    };
    
    PropertyChangeListener listenerValidacionFecha = new PropertyChangeListener() {
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
                    validarTodasFechas(vista.getJtOrdenesPendientes().getValueAt(obtenerFilaOrdenTrabajo(ordenTrabajo), 2).toString(),
                    Estructuras.convertirFecha(fechaMontaje),Estructuras.convertirFecha(fechaInicio));   
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
                                vista.getJtOrdenesPendientes().getValueAt(obtenerFilaOrdenTrabajo(ordenTrabajo), 2).toString(),
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
                                vista.getJtOrdenesPendientes().getValueAt(obtenerFilaOrdenTrabajo(ordenTrabajo), 2).toString(),
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
                        Estructuras.convertirFecha(dateEntrega).equals(Estructuras.convertirFecha(fechaSeleccionada))){
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
                while(!model.getValueAt(fila,1).toString().equals(ordenTrabajo)) fila++;
            return fila;
        }

        

        
    };
}
