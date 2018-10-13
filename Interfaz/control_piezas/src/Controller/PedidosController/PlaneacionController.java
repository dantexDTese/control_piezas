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

    private PlaneacionView vista;
    private PlaneacionModel model;
    private ProcesoPrincipal procesoPrincipal; 
    private ArrayList<lotesProduccion> listaLotes = new ArrayList<>();
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
    private final PropertyChangeListener listenerFechas = (PropertyChangeEvent evt) -> {llenarTabla();};
    private AsignarMaquinaAPedido vistaMaquinaPedido;
    public PlaneacionController(PlaneacionView vista, PlaneacionModel model) {
        
        this.vista = vista;
        this.model = model;
        llenarListaMaquinas();
        if(this.vista.getCbxListaMaquinas() != null) {
            if (!"".equals(this.vista.getCbxListaMaquinas().getSelectedItem().toString())) {
                llenarTablaMaquinas(this.vista.getCbxListaMaquinas().getSelectedItem().toString());                
            }
        }
           
        this.vista.getCbxListaMaquinas().addItemListener(maquinaSeleccionada);
        this.vista.getJtCalendario().setDefaultRenderer(Object.class,new FechaCalendario());              
        obtenerProcesoPrincipal(vista.getCbxListaMaquinas().getSelectedItem().toString());        
        this.vista.getJycAnioCalendario().addPropertyChangeListener(listenerFechas);
        this.vista.getJmtMesCalendario().addPropertyChangeListener(listenerFechas);   
        this.vista.getBtnAgregarOrdenesPendientes().addActionListener((ActionEvent e) -> {agregarOrdenesPendientes();});
        
        this.vistaMaquinaPedido = new AsignarMaquinaAPedido(this.vista.getPrincpial(), false);
        
    }
    
    
    
    private boolean  estadoVistaMaquinaPedido=false;
    
    private void agregarOrdenesPendientes() {

        if(this.estadoVistaMaquinaPedido == false){
            estadoVistaMaquinaPedido = true;
            AsignacionMaquinaAPedidoController controllerMaquinaPedido = new AsignacionMaquinaAPedidoController(vistaMaquinaPedido
                    , new AsignacionMaquinaAPedidoModel());
            
            vistaMaquinaPedido.setVisible(estadoVistaMaquinaPedido);
        }
        
    }
    
    private void agregarCalendario(PanelFecha [] fechas){        
        
        DefaultTableModel model = (DefaultTableModel) vista.getJtCalendario().getModel();
        vista.getJtCalendario().setRowHeight(50);        
        model.addRow(fechas);        
    }
   
    private void llenarTabla(){
        Estructuras.limpiarTabla((DefaultTableModel) vista.getJtCalendario().getModel());
        int anio = vista.getJycAnioCalendario().getValue();
        int mes = vista.getJmtMesCalendario().getMonth()+1;
        int dias = diasMes(anio, mes);
        int zeller = obtenerZeller(anio, mes);
        int diaSemana = 0;
        PanelFecha [] fechas = new PanelFecha[7];
        
        for(int i = 0;i<zeller;i++,diaSemana++)
            fechas[i] = null;
        
        for(int i = 0;i<dias;i++){
            fechas[diaSemana] = new PanelFecha(i+1);
            if(diaSemana==6){
                agregarCalendario(fechas);
                diaSemana = 0;
                fechas = new PanelFecha[7];
            }else diaSemana++;

        }
        if(fechas[0] != null)
            agregarCalendario(fechas);
                
    }
    
    private int obtenerZeller(int anio, int mes){
        int a = (14-mes)/12;
        int y = anio - a;
        int m = mes + 12  * a - 2;
        int dia = 1;
        return  (dia +  y + y / 4 - y / 100 + y / 400  + (31 * m ) / 12 ) % 7;
    }
    
    private int diasMes(int anio, int mes){
        if(mes == 1|| mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12)
            return 31;
        else if(mes == 2)
            return (esBiciesto(anio))? 29:28;
        return 30;
    }
    
    private boolean esBiciesto(int anio){
        if(anio %4 == 0 && anio %100 == 0 && anio%400 == 0)
            return true;
          else
            return false;
        
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