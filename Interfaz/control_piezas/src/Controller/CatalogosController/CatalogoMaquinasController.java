/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.CatalogosController;

import Model.CatalogosModel.CatalogoMaquinasModel;
import Model.Constructores;
import Model.Estructuras;
import View.Catalogos.CatalogoMaquinas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public final class CatalogoMaquinasController implements Constructores{

    private final CatalogoMaquinas view;
    private final CatalogoMaquinasModel model;
    
    public CatalogoMaquinasController(CatalogoMaquinas view, CatalogoMaquinasModel model) {
       this.view = view;
       this.model = model;
       llenarComponentes();
       asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarTablaMaquinas();
    }

    @Override
    public void asignarEventos() {
        view.getBtnGuardar().addActionListener(listenerAgregarMaquina);
    }
    
    private void llenarTablaMaquinas(){
        ArrayList<String> listaMaquinas = model.obtenerLIstaMaquinas();
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbMaquinas().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaMaquinas.size();i++)
            modeloTabla.addRow(new Object[]{i+1,listaMaquinas.get(i)});   
    }
    
    
    private final ActionListener listenerAgregarMaquina = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(!"".equals(view.getTxtDescripcion().getText()) ){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿SEGURO DE AGREGAR ESTE MATERIAL A LA LISTA?",
                        "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                     
                    model.agregarMaquina(view.getTxtDescripcion().getText());
                    view.getTxtDescripcion().setText("");
                    llenarTablaMaquinas();
                    
                }
            }else JOptionPane.showMessageDialog(null, "NO HA ESCRITO NINGUNA DESCRIPCION PARA LA MAQUINA,"
                    + "POR FAVOR COMPLETE EL CAMPO","VALIDACION",JOptionPane.INFORMATION_MESSAGE);
            
            
        }
    };
}
