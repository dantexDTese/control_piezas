/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PedidosController;

import Model.Estructuras;
import Model.PedidosModel.CalendarioModel;
import View.Pedidos.Calendario;
import View.Pedidos.FechaCalendario;
import View.Pedidos.PanelFecha;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class CalendarioController {

    private Calendario vista;
    private CalendarioModel model;
    
    public CalendarioController(Calendario vista,CalendarioModel model) {           
     this.vista = vista;
     this.model = model;
     this.vista.getJtCalendario().setDefaultRenderer(Object.class,new FechaCalendario());   
     this.vista.getJycAnioCalendario().addPropertyChangeListener(listenerFechas);
     this.vista.getJmtMesCalendario().addPropertyChangeListener(listenerFechas);  
    }
        
    private final PropertyChangeListener listenerFechas = (PropertyChangeEvent evt) -> {llenarTabla();};    
    
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
    
}
