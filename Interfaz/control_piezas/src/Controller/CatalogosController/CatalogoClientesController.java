/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.CatalogosController;

import Model.CatalogosModel.CatalogoClientesModel;
import Model.CatalogosModel.Cliente;
import Model.Constructores;
import Model.Estructuras;
import View.Catalogos.CatalogoClientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class CatalogoClientesController implements Constructores{

    private ArrayList<Cliente> listaClientes;
    private CatalogoClientes view;
    private CatalogoClientesModel model;
    
    
    public CatalogoClientesController(CatalogoClientes view, CatalogoClientesModel model) {
        this.view = view;
        this.model = model;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarTablaClientes();
    }

    @Override
    public void asignarEventos() {
        view.getJtbTablaClientes().addMouseListener(listenerTablaClientes);
        view.getBtnGuardar().addActionListener(listenerGuardarModificar);
        
    }
    
    private void llenarTablaClientes(){
        listaClientes = model.obtenerListaClientes();
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbTablaClientes().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaClientes.size();i++)
            modeloTabla.addRow(new Object[]{
                listaClientes.get(i).getNoCliente(),
                listaClientes.get(i).getNombreCliente()
            });
    }
    
    private final MouseListener listenerTablaClientes = new MouseAdapter() {
        
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int fila = view.getJtbTablaClientes().rowAtPoint(e.getPoint());
            Cliente c = buscarCliente(Integer.parseInt(view.getJtbTablaClientes().getValueAt(fila,0).toString()));
            if(e.getClickCount() == 2){
                int res = JOptionPane.showConfirmDialog(null, "¿DESEA MODIFICAR ESTE CLIENTE?","VALIDACION",JOptionPane.YES_NO_OPTION
                ,JOptionPane.QUESTION_MESSAGE);
                if(res == JOptionPane.YES_OPTION){
                    view.getTxtNombreGuardar().setText(c.getNombreCliente());
                    view.getTxtDescripcionGuardar().setText(c.getDescCliente());
                }
            }else if(e.getClickCount() == 1){
                limpiar();
                view.getTxtDescripcion().setText(c.getDescCliente());
            }    
        }
        
        private Cliente buscarCliente(int noCliente){
            for(int i = 0;i<listaClientes.size();i++)
                if(noCliente == listaClientes.get(i).getNoCliente())
                    return listaClientes.get(i);
            
            return null;
        }
        
        
    
    };
    
    private final ActionListener listenerGuardarModificar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!"".equals(view.getTxtDescripcionGuardar().getText()) &&
                    !"".equals(view.getTxtNombreGuardar().getText())){
                
                model.guardarModificarCliente(view.getTxtDescripcionGuardar().getText()
                        ,view.getTxtNombreGuardar().getText());
                
                limpiar();
                llenarTablaClientes();
            }else JOptionPane.showMessageDialog(null, "COMPLETE LOS CAMPOS PARA REALIZAR LA OPERACION","VALIDACION",
                    JOptionPane.WARNING_MESSAGE);
        }
    };
    
    private void limpiar(){
            view.getTxtNombreGuardar().setText("");
            view.getTxtDescripcionGuardar().setText("");
            
        }
    
}
