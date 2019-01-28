
package Controller.CatalogosController;

import Model.CatalogosModel.CatalogoOperadoresModel;
import Model.CatalogosModel.Operador;
import Model.Constructores;
import Model.Estructuras;
import View.Catalogos.CatalogoOperadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class CatalogoOperadoresController implements Constructores{

    
    private final CatalogoOperadores view;
    private final CatalogoOperadoresModel model;
    private ArrayList<Operador> listaOperadores;
    public CatalogoOperadoresController(CatalogoOperadores view, CatalogoOperadoresModel model) {
        this.view = view;
        this.model = model;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
       llenarTablaOperadores();
    }

    @Override
    public void asignarEventos() {
        view.getJtbOperadores().addMouseListener(listenerSeleccionarOperador);
        view.getBtnGuardarOperador().addActionListener(listenerGuardarModificarOperador);
    }
    
    private void llenarTablaOperadores(){
        
        listaOperadores = model.obtejerLIstaOperadores();
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbOperadores().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        
        for(int i = 0;i<listaOperadores.size();i++)
            modeloTabla.addRow(new Object[]{
                listaOperadores.get(i).getCodOperador(),
                listaOperadores.get(i).getNombreOperador()
            }); 
    }
    
   private final MouseListener listenerSeleccionarOperador = new MouseAdapter() {
        
       @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            
            if(e.getClickCount() == 2){
                
                int respuesta = JOptionPane.showConfirmDialog(null,"¿SEGURO QUE QUIERES MODIFICAR ESTE USUARIO?",
                        "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                
                if(respuesta == JOptionPane.YES_OPTION){
                    
                    int fila = view.getJtbOperadores().rowAtPoint(e.getPoint());
                    
                    Operador op = buscarOperador(view.getJtbOperadores().getValueAt(fila,0).toString());
                    if(op!= null){
                        view.getTxtCodigoOperador().setText(op.getCodOperador());
                        view.getTxtNombreOperador().setText(op.getNombreOperador());
                    }
                }
            }
        }

        private Operador buscarOperador(String codOperador) {
            
            for(int i = 0;i<listaOperadores.size();i++)
                if(codOperador.equals(listaOperadores.get(i).getCodOperador()))
                    return listaOperadores.get(i);
            
            return null;
        }
   };
   
   private final ActionListener listenerGuardarModificarOperador = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(!"".equals(view.getTxtCodigoOperador().getText()) && 
                    !"".equals(view.getTxtNombreOperador().getText())){
            
                int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE REALLIZAR OPERACION?","VALIDACION",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                if(respuesta == JOptionPane.YES_OPTION){
                    model.guardarModificarOperadores(view.getTxtCodigoOperador().getText(),
                            view.getTxtNombreOperador().getText());
                    llenarTablaOperadores();
                    limpiar();
                }
                   
            }else JOptionPane.showMessageDialog(null,"LOS CAMPOS NO HAN SIDO COMPLETADOS POR FAVOR LLENE LO NECESARIO",
                    "VALIDACION",JOptionPane.INFORMATION_MESSAGE);
        }
        
        private void limpiar(){
            view.getTxtCodigoOperador().setText("");
            view.getTxtNombreOperador().setText("");
        }
    };
}
