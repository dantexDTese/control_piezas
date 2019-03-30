
package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.Requisicion;
import Model.RequisicionesModel.ParcialidadMaterial;
import Model.RequisicionesModel.RecepcionRequisicionModel;
import View.Requisiciones.RecepcionRequisiciones;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class RecepcionRequisicionController implements Constructores{

    private final  RecepcionRequisiciones view;
    private final RecepcionRequisicionModel model;
    private ArrayList<ParcialidadMaterial> listaMaterialesRequisicion;
    private ArrayList<ParcialidadMaterial> listaMaterialesAgregados;
    
    public RecepcionRequisicionController(RecepcionRequisiciones view, RecepcionRequisicionModel model) {
        
        this.view = view;
        this.model = model;
        this.listaMaterialesAgregados = new ArrayList<>();
        llenarComponentes();
        asignarEventos();
        
    }

    @Override
    public void llenarComponentes() {
        llenarTablaRequisicionesPendientes();
        Estructuras.modificarAnchoTabla(view.getJtbSeleccionMateriales(),
                new Integer[]{90,120,100,115,185});
    }

    @Override
    public void asignarEventos() {
        view.getJtbRequisiciones().addMouseListener(listenerSeleccionarRequisicion);
        view.getJtbMaterialesFaltantes().addMouseListener(listenerSeleccionarMaterial);
        view.getJtbSeleccionMateriales().addMouseListener(listenerQuitarMaterial);
        view.getBtnGuardar().addActionListener(listenerGuardarCancelar);
        view.getBtnCancelar().addActionListener(listenerGuardarCancelar);
    }
    
    private void llenarTablaRequisicionesPendientes(){
        ArrayList<Requisicion> listaPendientes = model.listaRequisicionesPendientes();
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbRequisiciones().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaPendientes.size();i++)
            modeloTabla.addRow(new Object[]{
                listaPendientes.get(i).getNoRequisicion(),
                listaPendientes.get(i).getFechaCreacion()
            });
        
        
    }
    
    private void llenarTablaMaterialesSolicitados(){
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbMaterialesFaltantes().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        ParcialidadMaterial pMaterial;
        for(int i = 0;i<listaMaterialesRequisicion.size();i++){
            pMaterial = listaMaterialesRequisicion.get(i);
            modeloTabla.addRow(new Object[]{
                pMaterial.getNoPartida(),
                pMaterial.getDescMaterial(),
                pMaterial.getNoParcialidad(),
                pMaterial.getFechaSolicitadaParcialidadMaterial(),
                pMaterial.getCantidad(),
                pMaterial.getCantidadRestante()
            });
        }
    }
    
    private void llenarTablaMaterialesAgregados(){
       DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbSeleccionMateriales().getModel();
       Estructuras.limpiarTabla(modeloTabla);
       
       for(int i = 0;i<listaMaterialesAgregados.size();i++){
           ParcialidadMaterial pMaterial = listaMaterialesAgregados.get(i);
           
           modeloTabla.addRow(new Object[]{
               i+1,pMaterial.getDescMaterial(),pMaterial.getCantidadSeleccionada(),
               pMaterial.getNoRequisicion(),pMaterial.getNoPartida()
           });
       }   
    }
    
    private final MouseListener listenerSeleccionarRequisicion = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            
            if(e.getClickCount() == 2){
                if(JOptionPane.showConfirmDialog(null,"¿SELECCIONAR REQUISICION?","VALIDACION",JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    int fila = view.getJtbRequisiciones().rowAtPoint(e.getPoint());
                    
                    listaMaterialesRequisicion = model.listaMaterialesRequisicion(
                    Integer.parseInt(view.getJtbRequisiciones().getValueAt(fila, 0).toString()));
                    llenarTablaMaterialesSolicitados();
                }   
            }
        }

    };
    
    private final MouseListener listenerSeleccionarMaterial = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            
            if(e.getClickCount() == 2)
                if(JOptionPane.showConfirmDialog(null, "¿AGREGAR ESTE MATERIAL?","VALIDACION",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 
                        JOptionPane.YES_OPTION){
                    
                    int fila = view.getJtbMaterialesFaltantes().rowAtPoint(e.getPoint());
                    ParcialidadMaterial seleccionado = seleccionarMaterial(
                    Integer.parseInt(view.getJtbMaterialesFaltantes().getValueAt(fila,0).toString()));
                    
                    int cantidadAgregar = EscribirCantidad(seleccionado.getCantidadRestante());
                    
                    if(cantidadAgregar > 0){
                        seleccionado.setCantidadSeleccionada(cantidadAgregar);
                        Integer index = 0;
                        if( (index = buscarMaterialAgregado(seleccionado)) != null)
                           listaMaterialesAgregados.set(index, seleccionado);
                        else
                           listaMaterialesAgregados.add(seleccionado);
                        
                        llenarTablaMaterialesAgregados();
                    }
                }
        }

        private ParcialidadMaterial seleccionarMaterial(int noPartida) {
            for(int i = 0;i<listaMaterialesRequisicion.size();i++)
                if(listaMaterialesRequisicion.get(i).getNoPartida() == noPartida)
                    return listaMaterialesRequisicion.get(i);
            return null;
        }

        private int EscribirCantidad(int cantidadRestante) {
            try {
                int respuesta = Integer.parseInt(JOptionPane.showInputDialog("AGREGAR CANTIDAD"));
                 if(respuesta > cantidadRestante){
                     JOptionPane.showMessageDialog(null,"CANTIDAD NO VALIDA, INTENTE DE NUEVO","VALIDACION",
                             JOptionPane.WARNING_MESSAGE);
                     return 0;
                 }else{
                     JOptionPane.showMessageDialog(null,"MATERIAL AGREGADO CORRECTAMENTE","VALIDACION",
                             JOptionPane.INFORMATION_MESSAGE);
                     return respuesta;
                 }
            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PARAMETRO NO VALIDO");
                return 0;
            }
        }

        private Integer buscarMaterialAgregado(ParcialidadMaterial seleccionado) {
           
            for(int i = 0;i<listaMaterialesAgregados.size();i++)
                if(listaMaterialesAgregados.get(i).getNoRequisicion() == 
                        seleccionado.getNoRequisicion() &&
                        listaMaterialesAgregados.get(i).getNoPartida() == seleccionado.getNoPartida()){
                    
                    return i;
                }
            
            return null;
        }

    };
    
    private final MouseListener listenerQuitarMaterial = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            
            if(e.getClickCount() == 2)
                if(JOptionPane.showConfirmDialog(null,"¿ELIMINAR MATERIAL?","VALIDACION",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    int fila = view.getJtbSeleccionMateriales().rowAtPoint(e.getPoint());
                    listaMaterialesAgregados.remove(fila);
                    llenarTablaMaterialesAgregados();
                }
            
        }
    };
            
    private final ActionListener listenerGuardarCancelar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource() == view.getBtnGuardar())
                guardarMateriales();
            else if(e.getSource() == view.getBtnCancelar())
                view.dispose();
                
            
        }

        private void guardarMateriales() {
            
            if(listaMaterialesAgregados.size() > 0){
                int noRegistro =  model.registrarValidacionMateriales();

                if(noRegistro > 0){
                    for(int i = 0;i<listaMaterialesAgregados.size();i++)
                        model.guardarMateriales(listaMaterialesAgregados.get(i).getNoMaterialSolicitado(),
                                noRegistro,listaMaterialesAgregados.get(i).getCantidadSeleccionada());
                    
                    JOptionPane.showMessageDialog(null, "EL MATERIAL SE HA GUARDADO DE FORMA CORRECTA");
                    listaMaterialesAgregados.clear();
                    llenarTablaMaterialesAgregados();
                    listaMaterialesRequisicion.clear();
                    llenarTablaMaterialesSolicitados();
                }
                
            }else
                JOptionPane.showMessageDialog(null, "LA LISTA ESTA VACIA, POR FAVOR AGREGA MATERIALES O CANCELE LA OPERACION",
                        "VALIDACION",JOptionPane.INFORMATION_MESSAGE);
        }
        
    };
    
    
}
