
package Controller.CatalogosController;

import Model.CatalogosModel.Cliente;
import Model.CatalogosModel.Contacto;
import Model.CatalogosModel.ContactosClienteModel;
import Model.Constructores;
import Model.Estructuras;
import View.Catalogos.ContactosCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class ContactosClienteController implements Constructores{

    private ContactosCliente view; 
    private ContactosClienteModel model;
    private Cliente clienteSeleccionado;
    private ArrayList<Contacto> listaContactos;
    private Contacto contactoSeleccionado;
    
    ContactosClienteController(ContactosCliente view, 
            ContactosClienteModel model, Cliente clienteSeleccionado){
        this.view = view;
        this.model = model;
        this.clienteSeleccionado = clienteSeleccionado;
        this.listaContactos = new ArrayList<>();
        
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarTablaContactos(clienteSeleccionado.getNombreCliente());
    }

    @Override
    public void asignarEventos(){
        view.getJtbContactoCLiente().addMouseListener(listenerSeleccionContacto);
        view.getBtnGuardar().addActionListener(listenerGuardarModificar);
    }
    
    private void llenarTablaContactos(String nombreCLiente){
        listaContactos = model.listaContactoCliente(nombreCLiente);
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbContactoCLiente().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        
        for(int i = 0;i<listaContactos.size();i++){
            Contacto cn = listaContactos.get(i);
            modeloTabla.addRow(new Object[]{
                i+1,
                cn.getDescContacto()
            });
        }    
    }
    
    private final MouseListener listenerSeleccionContacto = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            if(e.getClickCount() == 2){
                if(JOptionPane.YES_OPTION ==
                        JOptionPane.showConfirmDialog(null, "¿MODIFICAR CONTACTO?",
                        "VALIDACION", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)){
                    int fila = view.getJtbContactoCLiente().rowAtPoint(e.getPoint());
                    String descContacto = view.getJtbContactoCLiente().getValueAt(fila, 1).toString();
                    contactoSeleccionado = buscarContacto(descContacto);
                    if(contactoSeleccionado != null)
                        llenarCamposContacto(contactoSeleccionado,fila+1);           
                }
            }else limpiar();
            
        }

        private Contacto buscarContacto(String descContacto) {       
            for(int i = 0;i<listaContactos.size();i++)
                if(listaContactos.get(i).getDescContacto().equals(descContacto))
                    return listaContactos.get(i);
            return null;
        }
    };
    
    private void llenarCamposContacto(Contacto contacto,int noContacto) {
        view.getLbNoContacto().setText(noContacto+"");
        view.getLbNombre().setText(contacto.getDescContacto());
        view.getLbDepartamento().setText(contacto.getDescDepartamento());
        view.getLbTelefono().setText(contacto.getTelefono());
        view.getLbExtencion().setText(contacto.getExtencion());
        view.getLbCelular().setText(contacto.getCelular());
        view.getLbCorreo().setText(contacto.getCorreo());
    }
    
    private void limpiar(){
        view.getLbNoContacto().setText("");
        view.getLbNombre().setText("");
        view.getLbDepartamento().setText("");
        view.getLbTelefono().setText("");
        view.getLbExtencion().setText("");
        view.getLbCelular().setText("");
        view.getLbCorreo().setText("");
    }
    
    private final ActionListener listenerGuardarModificar = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(contactoSeleccionado == null){
                Contacto contacto = llenarDatosContacto(new Contacto());
                contacto.setNoCliente(clienteSeleccionado.getNoCliente());
                model.guardarContacto(contacto);
                
            }
            else{
                contactoSeleccionado = llenarDatosContacto(contactoSeleccionado);
                model.modificarContacto(contactoSeleccionado);
            }
            contactoSeleccionado = null;
            limpiar();
            llenarTablaContactos(clienteSeleccionado.getNombreCliente());
            
        }
        
        private Contacto llenarDatosContacto(Contacto contacto){
            contacto.setDescContacto(view.getLbNombre().getText());
            contacto.setDescDepartamento(view.getLbDepartamento().getText());
            contacto.setTelefono(view.getLbTelefono().getText());
            contacto.setExtencion(view.getLbExtencion().getText());
            contacto.setCelular(view.getLbCelular().getText());
            contacto.setCorreo(view.getLbCorreo().getText());
            return contacto;
        }
    };
    
}
