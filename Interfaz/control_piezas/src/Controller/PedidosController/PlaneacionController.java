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
import View.Pedidos.PlaneacionView;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author cesar
 */
public class PlaneacionController  {

    private PlaneacionView vista;
    private PlaneacionModel model;
    ProcesoPrincipal procesoPrincipal;
    
    ArrayList<lotesProduccion> listaLotes = new ArrayList<>();
    private final MouseAdapter moueAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if(e.getClickCount() >= 2){
                int fila = vista.getTbListaPedidosPendientes().rowAtPoint(e.getPoint());
                AsignarMaquinaAPedido vistaSelecMaquina = new AsignarMaquinaAPedido(vista.getPrincpial(), true);
                AsignacionMaquinaAPedidoController controller = new AsignacionMaquinaAPedidoController(vistaSelecMaquina,
                new AsignacionMaquinaAPedidoModel(),
                vista.getTbListaPedidosPendientes().getValueAt(fila, 0).toString());
                vistaSelecMaquina.setVisible(true);
                
                vistaSelecMaquina.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e); 
                        llenarListaMaquinas();
                        llenarListaPedidosPendientes();
                        if(vista.getCbxListaMaquinas() != null )
                         llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
                        obtenerProcesoPrincipal(vista.getCbxListaMaquinas().getSelectedItem().toString());
                            
                    }
                });
                
            }      
        }                   
     };
    
    private final ItemListener maquinaSeleccionada = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(vista.getCbxListaMaquinas().getSelectedItem() != null) {
                if (!"".equals(vista.getCbxListaMaquinas().getSelectedItem().toString())) {
                llenarTablaMaquinas(vista.getCbxListaMaquinas().getSelectedItem().toString());
                obtenerProcesoPrincipal(vista.getCbxListaMaquinas().getSelectedItem().toString());            
                }
            }
        }
    };
    
    public PlaneacionController(PlaneacionView vista, PlaneacionModel model) {
        this.vista = vista;
        this.model = model;
        
        llenarListaMaquinas();
        llenarListaPedidosPendientes();
        if(this.vista.getCbxListaMaquinas() != null) {
            if (!"".equals(this.vista.getCbxListaMaquinas().getSelectedItem().toString())) {
                llenarTablaMaquinas(this.vista.getCbxListaMaquinas().getSelectedItem().toString());
                
            }
        }
        
        this.vista.getTbListaPedidosPendientes().addMouseListener(moueAdapter);
        this.vista.getCbxListaMaquinas().addItemListener(maquinaSeleccionada);
        obtenerProcesoPrincipal(vista.getCbxListaMaquinas().getSelectedItem().toString());
        
    }
    
    private void obtenerProcesoPrincipal(String nombreMaquina){    
        procesoPrincipal = model.obtenerProcesoPrincipal(nombreMaquina);
        Estructuras.limpiarTabla((DefaultTableModel) vista.getTbBitacoraProducto().getModel());
        limbiarCampos();
        if(procesoPrincipal!=null)
        {
            vista.getLbProductoEnProceso().setText(procesoPrincipal.getClaveProducto());
            vista.getLbCantidadTotal().setText(procesoPrincipal.getCantidadTotal()+"");      
            vista.getLbProcesoActual().setText(procesoPrincipal.getDescProcesoActual());
            llenarTablaLotes(nombreMaquina);
        }
    }
    
    private void limbiarCampos(){
        vista.getLbProductoEnProceso().setText("");
            vista.getLbCantidadTotal().setText("");      
            vista.getLbProcesoActual().setText("");
            vista.getLbCantidadProcesada().setText("");
            vista.getLbCantidadRestante().setText("");
    }
   
    private void llenarTablaLotes(String nombreMaquina){
        listaLotes = model.listaLotesProduccion(nombreMaquina);
        DefaultTableModel modelTabla = (DefaultTableModel) vista.getTbBitacoraProducto().getModel();
        for(int i = 0;i<listaLotes.size();i++)
            modelTabla.addRow(new Object[]{listaLotes.get(i).getFechaTrabaho(),listaLotes.get(i).getCantidadTrabajada()});
              
        obtenerCantidadesRestantes();
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
    
    private void llenarListaPedidosPendientes(){
     ArrayList<Pedido> pedidos = model.listaPedidosPendientes();
     Estructuras.limpiarTabla((DefaultTableModel) vista.getTbListaPedidosPendientes().getModel());
     if(pedidos.size()>0){
        DefaultTableModel tableModel = (DefaultTableModel) vista.getTbListaPedidosPendientes().getModel();
         for(int i = 0;i<pedidos.size();i++)
             tableModel.addRow(new Object[]{pedidos.get(i).getNo_orden_compra(),pedidos.get(i).getFecha_recepcion()});         
     }             
    }
    
    private void llenarTablaMaquinas(String nombreTabla){
        vista.getTbLIstaPedidosMaquina().setModel(Estructuras.limpiarTabla((DefaultTableModel) vista.getTbLIstaPedidosMaquina().getModel()));
        ArrayList<procedimientoTotal> procedimientos = model.listaProcedimientoMaquina(nombreTabla);
        
        DefaultTableModel modelMaquinas = (DefaultTableModel) vista.getTbLIstaPedidosMaquina().getModel();
        
        for(int i = 0;i<procedimientos.size();i++){
            procedimientoTotal unProcedimiento = procedimientos.get(i);            
            modelMaquinas.addRow(new Object[]{unProcedimiento.getNoOrdenCompra(),
            unProcedimiento.getIdOrdenProduccion(),unProcedimiento.getClaveProducto(),
            0,unProcedimiento.getDescMaterial(),unProcedimiento.getWorker(),
            0,unProcedimiento.getDescTipoProceso()});
        }
        
    }
}