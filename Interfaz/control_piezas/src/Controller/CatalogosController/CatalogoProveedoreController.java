/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.CatalogosController;

import Model.CatalogosModel.CatalogoProveedoresModel;

import Model.CatalogosModel.Proveedor;
import Model.Constructores;
import Model.Estructuras;
import View.Catalogos.CatalogoProveedores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public final class CatalogoProveedoreController implements Constructores{

    private final CatalogoProveedores view;
    private final CatalogoProveedoresModel model;
    private ArrayList<Proveedor> listaProveedores;
    
    
    public CatalogoProveedoreController(CatalogoProveedores view, CatalogoProveedoresModel model) {
        this.view = view;
        this.model = model;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarTablaProveedores();
    }

    @Override
    public void asignarEventos() {
        view.getJtbProveedores().addMouseListener(listenerTablaProveedores);
        view.getBtnGuardar().addActionListener(listenerGuardarModificar);
    }
    
    private void llenarTablaProveedores(){
        listaProveedores = model.obtenerListaProveedores();
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbProveedores().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        
        for(int i = 0;i<listaProveedores.size();i++)
            modeloTabla.addRow(new Object[]{
                listaProveedores.get(i).getNoProveedor(),
                listaProveedores.get(i).getDescProveedor()
            });
    }
    
    private final MouseListener listenerTablaProveedores = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            
                int fila = view.getJtbProveedores().rowAtPoint(e.getPoint());
            Proveedor c = buscarProveedor(Integer.parseInt(view.getJtbProveedores().getValueAt(fila,0).toString()));
            if(e.getClickCount() == 2){
                int res = JOptionPane.showConfirmDialog(null, "¿DESEA MODIFICAR ESTE PROVEEDOR?","VALIDACION",JOptionPane.YES_NO_OPTION
                ,JOptionPane.QUESTION_MESSAGE);
                if(res == JOptionPane.YES_OPTION){
                    view.getTxtDescProveedorGuardar().setText(c.getDescProveedor());
                    view.getTxtInformacionAdicionalGuardar().setText(c.getDirProveedor());
                }
            }else if(e.getClickCount() == 1){
                limpiar();
                view.getTxtInformacionAdicional().setText(c.getDirProveedor());
            }    
            
            
            
        }
        
        private Proveedor buscarProveedor(int noProveedor){
            
            for(int i = 0;i<listaProveedores.size();i++)
                if(noProveedor == listaProveedores.get(i).getNoProveedor())
                    return listaProveedores.get(i);
            
            return null;
        }

    };
    
    private final ActionListener listenerGuardarModificar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
             if(!"".equals(view.getTxtDescProveedorGuardar().getText()) &&
                    !"".equals(view.getTxtInformacionAdicionalGuardar().getText())){
                
                model.guardarModificarProveedor(view.getTxtDescProveedorGuardar().getText()
                        ,view.getTxtInformacionAdicionalGuardar().getText());
                
                limpiar();
                llenarTablaProveedores();
            }else JOptionPane.showMessageDialog(null, "COMPLETE LOS CAMPOS PARA REALIZAR LA OPERACION","VALIDACION",
                    JOptionPane.WARNING_MESSAGE);
            
            
        }
    };
    
    private void limpiar(){
        view.getTxtDescProveedorGuardar().setText("");
        view.getTxtInformacionAdicionalGuardar().setText("");
    }
    
}
