
package Controller;

import Model.ordenProduccionModel;
import Model.ordenProducto;
import View.OrdenesProduccion;
import View.plantillaOpcionLista;
import View.plantillaOrdenProduccion;
import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ordenProduccionController {
    
    
    OrdenesProduccion vista;
    ordenProduccionModel modelo;
    OrdenCompraController ordenesCompra;
    
    ordenProduccionController(OrdenesProduccion vistaOrdenesProduccion, ordenProduccionModel modelo, OrdenCompraController ordesCompra) {
        this.vista = vistaOrdenesProduccion;
        this.modelo = modelo;
        llenarListas();
        this.vista.getCantidadSolicitada().addChangeListener( new change());
        this.vista.getCantidadProducir().addChangeListener( new change());
        this.vista.getCantidadPorTurno().addChangeListener( new change());
        this.ordenesCompra = ordesCompra;
        
        this.vista.getFechaMontaje().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {                
                validaFecha();
            }
        });       
       
        this.vista.getAgregarProducto().addActionListener(new action());     
    }
    
    
    private void validaFecha(){        
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        
        if(vista.getFechaMontaje().getDate()!=null){
            if(! sdf.format(vista.getFechaMontaje().getDate()).equals(sdf.format(fecha))
               && vista.getFechaMontaje().getDate().before(fecha)){
                JOptionPane.showMessageDialog(null,"La fecha no puede ser menor que "
                        + "la del dia actual");
                vista.getFechaMontaje().setDate(fecha);
            }
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
    
    private class action implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){               
            if(validaCantidades(Integer.parseInt(vista.getCantidadProducir().getValue().toString()),
               Integer.parseInt(vista.getCantidadSolicitada().getValue().toString()))  &&
                    modelo.existeEntidad(modelo.EXISTE_PRODUCTO,vista.getProductoSeleccionado().getText())  &&
                    modelo.existeEntidad(modelo.EXISTE_MATERIAL, vista.getMaterialSeleccionado().getText())  &&
                    modelo.existeEntidad(modelo.EXISTE_MAQUINA,vista.getMaquinaSeleccionada().getText())){
            
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");            
                Date date = vista.getFechaMontaje().getDate();
                String sDate="";
                if(date !=null)
                    sDate = sdf.format(date);
                ordenProducto producto = new ordenProducto(vista.getProductoSeleccionado().getText(),
                vista.getMaquinaSeleccionada().getText(),vista.getMaterialSeleccionado().getText(),
                Integer.parseInt(vista.getCantidadSolicitada().getValue().toString()),
                Integer.parseInt(vista.getCantidadProducir().getValue().toString()),
                Integer.parseInt(vista.getCantidadPorTurno().getValue().toString()),
                sDate);
                ordenesCompra.agregarProducto(producto);           
                vista.dispose();
                
            }
        }
    }
    
    private boolean validaCantidades(Integer cantidadProducir, Integer cantidadSolicitada){                     
        if(cantidadSolicitada == 0){
            JOptionPane.showMessageDialog(null,"La cantidad solicitada no puede ser 0");
            return false;
        }else if(cantidadProducir<cantidadSolicitada){
            JOptionPane.showMessageDialog(null,"La cantidad a producir no puede ser menor que la solicitada");
            return false;
        }                
        return true;
    }
    
    private boolean validaFecha(Date fecha){
        return true;
    }
    
    private boolean validaCajaTexto(JTextField cajaTexto){
        return true;
    }
    
    
    
    
    
    private class change implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            
            if(e.getSource() == vista.getCantidadProducir() || 
               e.getSource() == vista.getCantidadSolicitada() ||
                e.getSource() == vista.getCantidadPorTurno()){
                validaPositivos(e);
            }
           
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
