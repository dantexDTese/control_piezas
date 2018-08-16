
package Controller;

import Model.ordenProduccionModel;
import View.OrdenesProduccion;
import View.plantillaOpcionLista;
import View.plantillaOrdenProduccion;
import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ordenProduccionController {
    
    
    OrdenesProduccion vista;
    ordenProduccionModel modelo;
    
    public ordenProduccionController(OrdenesProduccion vista,ordenProduccionModel modelo){
      
        this.vista = vista;
        this.modelo = modelo;
        llenarListas();
        this.vista.getCantidadSolicitada().addChangeListener( new change());
        this.vista.getCantidadProducir().addChangeListener( new change());
        this.vista.getCantidadPorTurno().addChangeListener( new change());
        this.vista.getFechaMontaje().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                validaFecha();
            }
        });
    }
    
    
    private void validaFecha(){
        
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            
        if(! sdf.format(vista.getFechaMontaje().getDate()).equals(sdf.format(fecha))
           && vista.getFechaMontaje().getDate().before(fecha)){
            JOptionPane.showMessageDialog(null,"La fecha no puede ser menor que "
                    + "la del dia actual");
            vista.getFechaMontaje().setDate(fecha);
        }
            
                
           
        
        
    }
    
    
    
    
    
    private void llenarListas(){       
        this.vista.setListaProductos(
                llenaLista(this.modelo.obtenerListaProductos(),
                this.vista.getListaProductos(),vista.getProductoSeleccionado()));       
        
        
        this.vista.setListaMaquina(
                llenaLista(this.modelo.obtenerListaMaquinas(),
                this.vista.getListaMaquina(),vista.getMaquinaSeleccionada()));       
        
        
        this.vista.setListaMateriales(
                llenaLista(this.modelo.obtenerListaMateriales(),
                this.vista.getListaMateriales(),vista.getMaterialSeleccionado()));             
    }
    
    private JPanel llenaLista(ArrayList lista,JPanel panelContenedor,JTextField cajaTexto){
        
        GridLayout gridLayout = new GridLayout(lista.size(), 1);
        panelContenedor.setLayout(gridLayout);
                
        for(int i = 0;i<lista.size();i++){
            plantillaOpcionLista opcion = new plantillaOpcionLista(lista.get(i).toString());            
            opcion.addMouseListener(new click(cajaTexto,opcion.getLbElemento().getText()));
            panelContenedor.add(opcion);
        }
        
        return panelContenedor;
    }
    
    
    
    
    private class change implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            
            if(e.getSource() == vista.getCantidadProducir() || 
               e.getSource() == vista.getCantidadSolicitada() ||
                e.getSource() == vista.getCantidadPorTurno())
                validaPositivos(e);
           
            if(e.getSource() == vista.getFechaMontaje())
                System.err.println("fecha");
            
        }
        
        
        
        
        
        private void validaPositivos(ChangeEvent e){
            JSpinner s =  (JSpinner) e.getSource();
            if(Integer.parseInt(s.getValue().toString()) < 0){
                JOptionPane.showMessageDialog(null,"no puede asignar valores menores de 0");
                s.setValue(0);
            }
        }
        
        
        
    }
    
    
    
    private class click implements MouseListener{

        String textoElemento;
        JTextField cajaTexto;
        public click(JTextField cajaTexto,String textoElemento){
            this.textoElemento = textoElemento;
            this.cajaTexto = cajaTexto;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            cajaTexto.setText(textoElemento);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
        
    }
    
    
    
    
}
