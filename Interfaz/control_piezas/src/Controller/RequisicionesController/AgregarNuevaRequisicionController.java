package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.Pedido;
import Model.RequisicionesModel.AgregarNuevaRequisicionModel;
import Model.RequisicionesModel.ParcialidadMaterial;
import Model.ordenProduccion;
import View.Requisiciones.agregarNuevasRequisiciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class AgregarNuevaRequisicionController implements Constructores{
    
    /**
     * ATRIBUTOS
     */
    private final agregarNuevasRequisiciones vista;
    private final AgregarNuevaRequisicionModel model;
    private final ArrayList<ParcialidadMaterial> listaParcialidad;
    private ArrayList<ordenProduccion> listaOrdenesProduccion;
    private ArrayList<ordenProduccion> productosPendientes;
    private ordenProduccion opSeleccionada;
    private Integer noOrdenSeleccionada;
    private int parcialidadMaxima=1;
    private int noPartida = 1;
    private final ArrayList<String> usoMaterial;
    
    /**
     * CONSTRUCTOR
     */
    AgregarNuevaRequisicionController(agregarNuevasRequisiciones vista,AgregarNuevaRequisicionModel model) { 
        //INICIALIZACION
        this.vista = vista;
        this.model = model;
        llenarComponentes();
        listaParcialidad = new ArrayList<>();
        listaOrdenesProduccion = new ArrayList<>();
        usoMaterial = new ArrayList<>();
        vista.getSpParcialidad().setValue(parcialidadMaxima);
        asignarEventos();
    }
    
    
    @Override
    public void llenarComponentes() {
        llenarListaPendientes();
    }

    @Override
    public void asignarEventos() {
        //ASIGNACION DE EVENTOS
        this.vista.getJtbPendientes().addMouseListener(listenerOrdenesPendientes);
        this.vista.getBtnAgregar().addActionListener(listenerBotones);
        this.vista.getJtbProductos().addMouseListener(listenerSeleccionarOrdenTrabajo);
        this.vista.getBtnEnviar().addActionListener(listenerBotones);
        ChangeListener listenerValidarCantidad = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(opSeleccionada != null){
                    if((int)Math.ceil(opSeleccionada.getPiezasFaltantes()) < Integer.parseInt(vista.getSprCantidad().getValue().toString())){
                        JOptionPane.showMessageDialog(null, "LA CANTIDAD QUE ESTA INTENTEDO ES MAYOR A LA NECESARIA, "
                                + "POR FAVOR INTENTE DE NUEVO");
                        vista.getSprCantidad().setValue((int)Math.ceil(opSeleccionada.getPiezasFaltantes()));
                    }
                        
                }
                    
            }
        };
        this.vista.getSprCantidad().addChangeListener(listenerValidarCantidad);
    }
    
    /**
     * METODOS
     */
    private void llenarListaPendientes(){
        ArrayList<Pedido> listaPendientes = model.listaOrdenesPendientes();
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbPendientes().getModel());
        DefaultTableModel model = (DefaultTableModel) vista.getJtbPendientes().getModel();
        for(int i = 0;i<listaPendientes.size();i++)
        model.addRow(new Object[]{
            listaPendientes.get(i).getNoPedido(),
            listaPendientes.get(i).getNoOrdenCompra()
        });
    }
    
    private void limpiar(){
        vista.getTxtNombreSolicitando().setText("");
        vista.getLbMaterial().setText("");
        vista.getLbNoPartida().setText("");
        vista.getSprCantidad().setValue(0);
        vista.getLbOrdenProduccion().setText("");
        listaOrdenesProduccion = new ArrayList<>();
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtMaterialOrdenesSeleccionado().getModel());
    }
    
    /**
     * EVENTOS
     */
    private final MouseAdapter listenerOrdenesPendientes = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int fila = vista.getJtbPendientes().rowAtPoint(e.getPoint());
            noOrdenSeleccionada = (int) vista.getJtbPendientes().getValueAt(fila,0);
            agregarUsoMaterial(vista.getJtbPendientes().getValueAt(fila,1).toString());
            llenarListaProductos(noOrdenSeleccionada);                        
        }
        
       private void llenarListaProductos(int noOrdenTrabajo){
            productosPendientes = model.listaProductosPendientes(noOrdenTrabajo);
            Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductos().getModel());
            DefaultTableModel model = (DefaultTableModel) vista.getJtbProductos().getModel();
            for(int i = 0;i<productosPendientes.size();i++)
                model.addRow(new Object[]{
                    productosPendientes.get(i).getNoOrdenProduccion(),
                    productosPendientes.get(i).getCodProducto(),
                    productosPendientes.get(i).getCantidadTotal(),
                    productosPendientes.get(i).materialToString(),
                    productosPendientes.get(i).getBarrasNecesarias(),
                    productosPendientes.get(i).getFechaInicio(),
                    productosPendientes.get(i).getPiezasFaltantes()
                });
        }
       
       private void agregarUsoMaterial(String ordenCompra){           
            vista.getTxtUsoMaterial().setText("");        
            if(usoMaterial.isEmpty())
                usoMaterial.add(ordenCompra);               
            else for(int i = 0;i<usoMaterial.size();i++){
                if(ordenCompra.equals(usoMaterial.get(i)))
                    break;
                else if(i == usoMaterial.size()-1){
                    usoMaterial.add(ordenCompra);
                    break;
                }
            }                    
           for(int i = 0;i<usoMaterial.size();i++)               
               vista.getTxtUsoMaterial().setText( vista.getTxtUsoMaterial().getText()+","+usoMaterial.get(i) );                                                       
       }
       
    };
    
    
    
    private final MouseAdapter listenerSeleccionarOrdenTrabajo = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                int filaSeleccionada = vista.getJtbProductos().rowAtPoint(e.getPoint());
                
                opSeleccionada = iniciarPartida(filaSeleccionada);
                
                if("".equals(vista.getLbMaterial().getText())){
                    if(0==JOptionPane.showConfirmDialog(null, "¿esta seguro de comenzar con el material de esta orden?"
                            ,"SELECCION DE MATERIAL",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)){
                        agregarPartida(opSeleccionada, (DefaultTableModel) vista.getJtMaterialOrdenesSeleccionado().getModel());       
                    }
                }
               else if(opSeleccionada.materialToString().equals(vista.getLbMaterial().getText()))
                        agregarPartida(opSeleccionada, (DefaultTableModel) vista.getJtMaterialOrdenesSeleccionado().getModel());       
                else
                    JOptionPane.showMessageDialog(null, "EL MATERIAL DE ESTA OP NO CONINCIDE CON LA ANTERIOR","SIN COINCIDENCIA",JOptionPane.WARNING_MESSAGE);
            }
        }
        
         private void agregarPartida(ordenProduccion op,DefaultTableModel model){
            listaOrdenesProduccion.add(op);
            int cantidad = (int)Math.ceil(op.getPiezasFaltantes());
            vista.getLbOrdenProduccion().setText(op.getNoOrdenProduccion()+"");  
            vista.getLbNoPartida().setText(noPartida+"");
            vista.getLbMaterial().setText(op.materialToString());
            vista.getSpParcialidad().setValue(parcialidadMaxima);
            vista.getSprCantidad().setValue((int)Math.ceil(cantidad));
            ActionListener listenerBoton = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    op.setBarrasSelecciondas((int) vista.getSprCantidad().getValue());
                    model.addRow(new Object[]{listaOrdenesProduccion.size(),op.getNoOrdenProduccion(),op.getBarrasSelecciondas()});
                    vista.getBtnAgregarListaOP().removeActionListener(this);
                }
            };
            vista.getBtnAgregarListaOP().addActionListener(listenerBoton);
        }
               
        private ordenProduccion iniciarPartida(int filaSeleccionada){
            
            int noOrden = Integer.parseInt(vista.getJtbProductos().getValueAt(filaSeleccionada, 0).toString());
            for(int i = 0;i<productosPendientes.size();i++)
                if(productosPendientes.get(i).getNoOrdenProduccion() == noOrden)
                    return productosPendientes.get(i);
            
            
            return null;
        }
        
    };

    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == vista.getBtnAgregar()){
                if(agregarMaterial(obtenerCantidadBarrasOrdenes()))
                    limpiar();
            
            }else if(e.getSource() == vista.getBtnEnviar()){
                EnviarRequisicion();
                vista.dispose();
            }
        }
        
        private void EnviarRequisicion(){
            int noRequisicion = 0;
            int noMaterialSolicitado=0;
            if(!listaParcialidad.isEmpty()){
                if(!"".equals(vista.getTxtNombreSolicitando().getText())&& !"".equals(vista.getTxtUsoMaterial().getText()))
                    noRequisicion = model.agregarRequisicion(vista.getTxtNombreSolicitando().getText(), vista.getTxtUsoMaterial().getText());

                if(noRequisicion > 0)
                    for(int i = 0;i<listaParcialidad.size();i++){                    
                        ParcialidadMaterial parcialidadMaterial = listaParcialidad.get(i);
                        noMaterialSolicitado = model.agregarMaterialSolicitado(noRequisicion, parcialidadMaterial);                    
                        ArrayList<ordenProduccion> listaOrdenesProduccion = parcialidadMaterial.getListaOrdenesProduccion();


                        for(int j = 0;j < listaOrdenesProduccion.size();j++){    
                            ordenProduccion pendiente = listaOrdenesProduccion.get(j);
                            model.agregarMaterialesOrdenRequisicion(noMaterialSolicitado, pendiente.getNoOrdenProduccion(), pendiente.getBarrasSelecciondas());                                                
                        }                    
                    }    
            }else JOptionPane.showMessageDialog(null, "LA LISTA DE MATERIALES ESTA VACIA, POR FAVOR AGREGUE MATERIALES A LA LISTA");
        }                        
        
        private boolean agregarMaterial(int numBarras){
        ParcialidadMaterial materialSeleccionado;
        int parcialidadSeleccionada = Integer.parseInt(vista.getSpParcialidad().getValue().toString());  
        
        if(numBarras>0 && vista.getJdcFechaSolicitada().getDate()!=null && parcialidadSeleccionada != 0){            
            if(parcialidadSeleccionada <= parcialidadMaxima){                
                
                materialSeleccionado = new ParcialidadMaterial(Integer.parseInt(vista.getLbNoPartida().getText()),
                        Integer.parseInt(vista.getSpParcialidad().getValue().toString()));
                        
                materialSeleccionado.setCantidad(numBarras);
                materialSeleccionado.setFechaSolicitadaParcialidadMaterial(Estructuras.convertirFechaGuardar(vista.getJdcFechaSolicitada().getDate()));
                materialSeleccionado.setCuentaCargo(vista.getCbxCuentaCargo().getSelectedItem().toString());
                materialSeleccionado.setUnidad(vista.getLbUnidad().getText());    
                materialSeleccionado.setDescTipoMaterial(listaOrdenesProduccion.get(0).getDescTipoMaterial());
                materialSeleccionado.setDescDimencion(listaOrdenesProduccion.get(0).getDescDimencion());
                materialSeleccionado.setClaveForma(listaOrdenesProduccion.get(0).getClaveForma());
                materialSeleccionado.setNoMaterial(listaOrdenesProduccion.get(0).getNoMaterial());
                materialSeleccionado.setListaOrdenesProduccion(listaOrdenesProduccion);            
                
                listaParcialidad.add(materialSeleccionado);
                
                DefaultTableModel modelListaParcialidad = (DefaultTableModel) vista.getJtbListaMateriales().getModel();
                modelListaParcialidad.addRow(new Object[]{
                    materialSeleccionado.getNoPartida(),
                    materialSeleccionado.materialToString(),
                    materialSeleccionado.getCantidad(),
                    materialSeleccionado.getUnidad(),
                    materialSeleccionado.getNoParcialidad(),
                    materialSeleccionado.getFechaSolicitadaParcialidadMaterial(),
                    materialSeleccionado.getCuentaCargo()
                });
                
                parcialidadMaxima++;
                noPartida++;
                return true;
            
            }else 
                JOptionPane.showMessageDialog(null, "el numero de parcialidad es incorrecto", "parcialidad incorrecta", JOptionPane.WARNING_MESSAGE);
        }        
        return false;
    }
        
    
        private int obtenerCantidadBarrasOrdenes(){
            int cantidadTotal = 0;
            DefaultTableModel modelOP = (DefaultTableModel) vista.getJtMaterialOrdenesSeleccionado().getModel();
            for(int i = 0;i<modelOP.getRowCount();i++)
                cantidadTotal += Integer.parseInt(modelOP.getValueAt(i, 2).toString());                
            return cantidadTotal;
        } 
            
    };

    
    
    
}
