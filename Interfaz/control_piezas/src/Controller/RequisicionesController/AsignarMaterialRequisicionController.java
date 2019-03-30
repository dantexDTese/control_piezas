package Controller.RequisicionesController;

import Model.AlmacenModel.AlmacenMateriaPrima;
import Model.Constructores;
import Model.Estructuras;
import Model.RequisicionesModel.AsignarMaterialRequisicionModel;
import Model.RequisicionesModel.ParcialidadesRequisicion;
import View.Requisiciones.AsignarMaterialRequisicion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class AsignarMaterialRequisicionController implements Constructores{

    private final AsignarMaterialRequisicion vista;
    private final AsignarMaterialRequisicionModel modelo;
    private final ParcialidadesRequisicion parcialidad;
    private ArrayList<AlmacenMateriaPrima> lotes;
    private AlmacenMateriaPrima loteSeleccionado;
    
    AsignarMaterialRequisicionController(AsignarMaterialRequisicion vista, AsignarMaterialRequisicionModel modelo,
            ParcialidadesRequisicion parcialidad){
        this.vista = vista;
        this.modelo = modelo;
        this.parcialidad = parcialidad;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarTablaLotesMateria();
        llenarTablaMaterialesAsignados();
        int faltantes = modelo.obtenerFaltantes(parcialidad.getNoOrdenProduccion());
        vista.getLbFaltantes().setText(faltantes+"");
        if(faltantes == 0)
            vista.getBtnGuardar().setEnabled(false);
        vista.getLbDescMaterial().setText(parcialidad.getDescMaterial());
        vista.getMaterialesPorAsignar().addMouseListener(listenerTablaLotes);
        vista.getLbOp().setText(parcialidad.getNoOrdenProduccion()+"");
    }

    @Override
    public void asignarEventos() {
        vista.getBtnGuardar().addActionListener(listenerGuardar);
        vista.getMaterialesPorAsignar().addMouseListener(listenerTablaLotes);
        
    }
    
    private void llenarTablaMaterialesAsignados(){
        ArrayList<AsignarMaterialRequisicionModel.MaterialEntregado> listaLotes = modelo.lotesMaterialesAsignados(parcialidad.getNoOrdenProduccion());
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbMaterialesAsignados().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaLotes.size();i++)
            modeloTabla.addRow(new Object[]{
                i+1,listaLotes.get(i).getDescLote(),
                listaLotes.get(i).getCantidadAsignada(),
                listaLotes.get(i).getFecha()
            });
        
    }
    
    private void llenarTablaLotesMateria(){
        System.err.println(parcialidad.getDescMaterial());
        lotes = modelo.listaLotesMaterial(parcialidad.getDescMaterial());
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getMaterialesPorAsignar().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        
        for(int i = 0;i<lotes.size();i++){
            AlmacenMateriaPrima material = lotes.get(i);;
            modeloTabla.addRow(new Object[]{
                i+1,
                material.getDescLote(),
                material.getCantidadTotal(),
                material.getFechaRegistro()
            });
        }
            
        
    }
    
    private final MouseListener listenerTablaLotes = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int fila = vista.getMaterialesPorAsignar().rowAtPoint(e.getPoint());
            loteSeleccionado = obtenerLoteSeleccionado(vista.getMaterialesPorAsignar().getValueAt(fila, 1).toString());
            vista.getLbDescLote().setText(loteSeleccionado.getDescLote());
        }

        private AlmacenMateriaPrima obtenerLoteSeleccionado(String descLote) {
            
            for(int i = 0;i<lotes.size();i++)
                if(lotes.get(i).getDescLote().equals(descLote))
                    return lotes.get(i);
            
            return null;
        }
         
        
    };
    
    private final ActionListener listenerGuardar = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(loteSeleccionado != null){
 
                int respuesta = JOptionPane.showConfirmDialog(null, "¿SEGURO QUE DESEA ASIGNAR ESTA CANTIDAD?",
                        "CONFIRMACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
           
                if(respuesta == JOptionPane.YES_OPTION){
                    int cantidadGuardar = Integer.parseInt(vista.getSprCantidad().getValue().toString());     

                    if(cantidadGuardar > 0){

                        modelo.asignarMaterial(parcialidad.getNoOrdenProduccion(),loteSeleccionado.getDescLote(),cantidadGuardar);
                        vista.dispose();
                    }else
                        JOptionPane.showMessageDialog(null, "LA CANDIDAD NO ES VALIDA","VALIDACION",JOptionPane.ERROR_MESSAGE);

                }
            }else
                JOptionPane.showMessageDialog(null, "POR FAVOR PRIMERO SELECCIONE UN LOTE DE MATERIAL","VALIDACION",JOptionPane.WARNING_MESSAGE);
            
        }
    };
}


