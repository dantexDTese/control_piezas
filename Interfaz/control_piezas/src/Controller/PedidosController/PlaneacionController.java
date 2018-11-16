/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PedidosController;

import Model.Estructuras;
import Model.PedidosModel.AsignacionMaquinaAPedidoModel;
import Model.PedidosModel.Pedido;
import Model.PedidosModel.PlaneacionModel;
import Model.PedidosModel.ProcesoPrincipal;
import Model.PedidosModel.lotesProduccion;
import Model.PedidosModel.procedimientoTotal;
import View.Pedidos.AsignarMaquinaAPedido;
import View.Pedidos.FechaCalendario;
import View.Pedidos.PanelFecha;
import View.Pedidos.PlaneacionView;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class PlaneacionController  {

    private final PlaneacionView vista;
    private final PlaneacionModel model;
    private ProcesoPrincipal procesoPrincipal; 
    private ArrayList<lotesProduccion> listaLotes = new ArrayList<>();
    
    
    private AsignarMaquinaAPedido vistaMaquinaPedido;
    
    public PlaneacionController(PlaneacionView vista, PlaneacionModel model) {
    
        this.vista = vista;
        this.model = model;
       
        
        llenarListaMaquinas();
        
        this.vista.getCbxListaMaquinas().addItemListener(maquinaSeleccionada);
        this.vista.getBtnAgregarOrdenesPendientes().addActionListener((ActionEvent e) -> {agregarOrdenesPendientes();});               

        this.vista.getJpCalendar().setSize(800,350);
        
        if(vista.getCbxListaMaquinas().getSelectedItem() != null){
            llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
            Estructuras.obtenerCalendario(this.vista.getJpCalendar(),this.vista.getCbxListaMaquinas().getSelectedItem().toString());        
        }
       
        
    }
    
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
    
    //EVENTOS
    
    
    
    private void agregarOrdenesPendientes() {
            this.vistaMaquinaPedido = new AsignarMaquinaAPedido(this.vista.getPrincpial(), true); 
            AsignacionMaquinaAPedidoController controllerMaquinaPedido = new AsignacionMaquinaAPedidoController(vistaMaquinaPedido
                    , new AsignacionMaquinaAPedidoModel());            
            vistaMaquinaPedido.setVisible(true); 
    }
    
    
    
    private void obtenerProcesoPrincipal(String nombreMaquina){    
        procesoPrincipal = model.obtenerProcesoPrincipal(nombreMaquina);
        limbiarCampos();
        if(procesoPrincipal!=null)
        {
            vista.getLbProductoEnProceso().setText(procesoPrincipal.getClaveProducto());
            vista.getLbCantidadTotal().setText(procesoPrincipal.getCantidadTotal()+"");      
            vista.getLbProcesoActual().setText(procesoPrincipal.getDescProcesoActual());
        }
    }
    
    private void limbiarCampos(){
        vista.getLbProductoEnProceso().setText("");
            vista.getLbCantidadTotal().setText("");      
            vista.getLbProcesoActual().setText("");
            vista.getLbCantidadProcesada().setText("");
            vista.getLbCantidadRestante().setText("");
    }
   
 
    
    private void obtenerCantidadesRestantes(){
        if(listaLotes.size()>0){
            for(int i = 0;i<listaLotes.size();i++)
                procesoPrincipal.setCantidadProcesada(procesoPrincipal.getCantidadProcesada()+listaLotes.get(i).getCantidadTrabajada());
            
            vista.getLbCantidadProcesada().setText(procesoPrincipal.getCantidadProcesada()+"");
            vista.getLbCantidadRestante().setText(procesoPrincipal.getCantidadTotal()-procesoPrincipal.getCantidadProcesada()+"");
            
        }
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