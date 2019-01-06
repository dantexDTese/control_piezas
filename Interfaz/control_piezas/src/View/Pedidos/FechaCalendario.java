/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Pedidos;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author cesar
 */
public class FechaCalendario extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if(value instanceof PanelFecha){
            
            PanelFecha panelFecha = (PanelFecha)value;
            return panelFecha;
            
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    
    
    
}
