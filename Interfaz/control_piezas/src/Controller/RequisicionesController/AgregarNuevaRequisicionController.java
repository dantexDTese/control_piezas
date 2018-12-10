package Controller.RequisicionesController;

import Model.Estructuras;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.ProductosPendientes;
import Model.RequisicionesModel.AgregarNuevaRequisicionModel;
import Model.RequisicionesModel.ParcialidadMaterial;
import View.Requisiciones.agregarNuevasRequisiciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AgregarNuevaRequisicionController {
    
    /**
     * ATRIBUTOS
     */
    private final agregarNuevasRequisiciones vista;
    private final AgregarNuevaRequisicionModel model;
    private final ArrayList<ParcialidadMaterial> listaParcialidad;
    private ArrayList<ProductosPendientes> listaOrdenesProduccion;
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
        llenarListaPendientes();
        listaParcialidad = new ArrayList<>();
        listaOrdenesProduccion = new ArrayList<>();
        usoMaterial = new ArrayList<>();
        vista.getSpParcialidad().setValue(parcialidadMaxima);
        
        //ASIGNACION DE EVENTOS
        this.vista.getJtbPendientes().addMouseListener(listenerOrdenesPendientes);
        this.vista.getBtnAgregar().addActionListener(listenerBotones);
        this.vista.getJtbProductos().addMouseListener(listenerSeleccionarOrdenTrabajo);
        this.vista.getBtnEnviar().addActionListener(listenerBotones);
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
            listaPendientes.get(i).getNoOrdenTrabajo(),
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
            ArrayList<ProductosPendientes> productosPendientes = model.listaProductosPendientes(noOrdenTrabajo);
            Estructuras.limpiarTabla((DefaultTableModel) vista.getJtbProductos().getModel());
            DefaultTableModel model = (DefaultTableModel) vista.getJtbProductos().getModel();
            for(int i = 0;i<productosPendientes.size();i++)
                model.addRow(new Object[]{
                    productosPendientes.get(i).getNoOrdenProduccion(),
                    productosPendientes.get(i).getClaveProducto(),
                    productosPendientes.get(i).getQty(),
                    productosPendientes.get(i).getMaterial(),
                    productosPendientes.get(i).getBarrasNecesarias(),
                    productosPendientes.get(i).getFechaInicio()
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
                ProductosPendientes op = iniciarPartida(filaSeleccionada,vista.getJtbProductos());
                
                if("".equals(vista.getLbMaterial().getText())){
                    if(0==JOptionPane.showConfirmDialog(null, "¿esta seguro de comenzar con el material de esta orden?"
                            ,"SELECCION DE MATERIAL",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)){
                        agregarPartida(op, (DefaultTableModel) vista.getJtMaterialOrdenesSeleccionado().getModel());       
                    }
                }
               else if(op.getMaterial().equals(vista.getLbMaterial().getText()))
                        agregarPartida(op, (DefaultTableModel) vista.getJtMaterialOrdenesSeleccionado().getModel());       
                else
                    JOptionPane.showMessageDialog(null, "EL MATERIAL DE ESTA OP NO CONINCIDE CON LA ANTERIOR","SIN COINCIDENCIA",JOptionPane.WARNING_MESSAGE);
            }
        }
        
        private void agregarPartida(ProductosPendientes op,DefaultTableModel model){
            listaOrdenesProduccion.add(op);
            int cantidad = (int)Math.ceil(op.getBarrasNecesarias());
            vista.getLbOrdenProduccion().setText(op.getNoOrdenProduccion()+"");  
            vista.getLbNoPartida().setText(noPartida+"");
            vista.getLbMaterial().setText(op.getMaterial());
            vista.getSpParcialidad().setValue(parcialidadMaxima);
            vista.getSprCantidad().setValue((int)Math.ceil(cantidad));
            ActionListener listenerBoton = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    op.setBarrasSeleccionadas((int) vista.getSprCantidad().getValue());
                    model.addRow(new Object[]{listaOrdenesProduccion.size(),op.getNoOrdenProduccion(),op.getBarrasSeleccionadas()});
                    vista.getBtnAgregarListaOP().removeActionListener(this);
                }
            };
            vista.getBtnAgregarListaOP().addActionListener(listenerBoton);
        }
               
        private ProductosPendientes iniciarPartida(int filaSeleccionada,JTable OrdenesProduccion){
            return new ProductosPendientes(
                    Integer.parseInt(OrdenesProduccion.getValueAt(filaSeleccionada, 0).toString()),
                    OrdenesProduccion.getValueAt(filaSeleccionada, 1).toString(),
                    Integer.parseInt(OrdenesProduccion.getValueAt(filaSeleccionada, 2).toString()),
                    OrdenesProduccion.getValueAt(filaSeleccionada, 3).toString(),
                    Float.parseFloat(OrdenesProduccion.getValueAt(filaSeleccionada, 4).toString()),
                    OrdenesProduccion.getValueAt(filaSeleccionada, 5).toString());
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
            
            if(!"".equals(vista.getTxtNombreSolicitando().getText())&& !"".equals(vista.getTxtUsoMaterial().getText()))
                noRequisicion = model.agregarRequisicion(vista.getTxtNombreSolicitando().getText(), vista.getTxtUsoMaterial().getText());
            
            if(noRequisicion > 0)
                for(int i = 0;i<listaParcialidad.size();i++){                    
                    ParcialidadMaterial parcialidadMaterial = listaParcialidad.get(i);
                    noMaterialSolicitado = model.agregarMaterialSolicitado(noRequisicion, parcialidadMaterial);                    
                    ArrayList<ProductosPendientes> listaOrdenesProduccion = parcialidadMaterial.getListaOrdenesProduccion();
                    
                    
                    for(int j = 0;j < listaOrdenesProduccion.size();j++){    
                        ProductosPendientes pendiente = listaOrdenesProduccion.get(j);
                        model.agregarMaterialesOrdenRequisicion(noMaterialSolicitado, pendiente.getNoOrdenProduccion(), pendiente.getBarrasSeleccionadas());                                                
                    }                    
                }    
        }                        
        
        private boolean agregarMaterial(int numBarras){
            
        int parcialidadSeleccionada = Integer.parseInt(vista.getSpParcialidad().getValue().toString());  
        
        if(numBarras>0 && vista.getJdcFechaSolicitada().getDate()!=null && parcialidadSeleccionada != 0){            
            if(parcialidadSeleccionada <= parcialidadMaxima){                
                
                ParcialidadMaterial materialSeleccionado = new ParcialidadMaterial(Integer.parseInt(vista.getLbNoPartida().getText()),
                        Integer.parseInt(vista.getSpParcialidad().getValue().toString()),vista.getLbMaterial().getText());
                        
                materialSeleccionado.setCantidad(numBarras);
                materialSeleccionado.setFechaSolicitadaParcialidadMaterial(Estructuras.convertirFecha(vista.getJdcFechaSolicitada().getDate()));
                materialSeleccionado.setCuentaCargo(vista.getCbxCuentaCargo().getSelectedItem().toString());
                materialSeleccionado.setUnidad(vista.getLbUnidad().getText());        
                materialSeleccionado.setListaOrdenesProduccion(listaOrdenesProduccion);            
                
                
                listaParcialidad.add(materialSeleccionado);
                
                DefaultTableModel modelListaParcialidad = (DefaultTableModel) vista.getJtbListaMateriales().getModel();
                modelListaParcialidad.addRow(new Object[]{
                    materialSeleccionado.getNoPartida(),
                    materialSeleccionado.getMaterial(),
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
