
package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.RequisicionesModel.AgregarNuevaRequisicionModel;
import Model.RequisicionesModel.MaterialesRequisicion;
import Model.RequisicionesModel.ControlEntregasModel;
import View.Requisiciones.ControlEntregasView;
import View.Requisiciones.agregarNuevasRequisiciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author cesar
 */
public final class ControlEntregasController implements Constructores{
    
    /**
     * ATRIBUTOS
     */
    private ControlEntregasView entregasView;
    private final ControlEntregasModel entregasModel;
    
    /**
     * CONSTRUCTOR
     * @param entragasView
     * @param entregasModel
     */
    public ControlEntregasController(ControlEntregasView entragasView, ControlEntregasModel entregasModel) {
        this.entregasView = entragasView;
        this.entregasModel = entregasModel;
        llenarComponentes();
        asignarEventos(); 
    }
    
    @Override
    public void llenarComponentes() {
        llenarTablaRequisiciones();
    }

    @Override
    public void asignarEventos() {
        this.entregasView.getBtnAgregarRequisiciones().addActionListener(listenerBotones);
        this.entregasView.getJtEntregas().addMouseListener(listenerTablas);
    }
    
    /**
     *METODOS 
     */
    public void llenarTablaRequisiciones(){
        ArrayList<MaterialesRequisicion> listaMaterialesRequisicion = entregasModel.obtenerRequisiciones();
        DefaultTableModel modelRequisiciones = (DefaultTableModel) entregasView.getJtEntregas().getModel();

        for(int i = 0;i<listaMaterialesRequisicion.size();i++){
            MaterialesRequisicion requisicion = listaMaterialesRequisicion.get(i);
            modelRequisiciones.addRow(new Object[]{
                requisicion.getNoRequisicion(),
                requisicion.getMaterial(),
                requisicion.getBarrasNecesarias(),
                requisicion.getDescEstado()
            });
        }
            
    }
    
    /**
     * EVENTOS
     */
    private final ActionListener listenerBotones   =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == entregasView.getBtnAgregarRequisiciones())
                    agregarNuevaRequisicion();
        }
        
        private void agregarNuevaRequisicion(){
            agregarNuevasRequisiciones vista = new agregarNuevasRequisiciones(entregasView.getPrincipal(), true);
            AgregarNuevaRequisicionController controller = new AgregarNuevaRequisicionController(vista,
            new AgregarNuevaRequisicionModel());
            vista.setVisible(true);
            vista.addWindowStateListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    llenarTablaRequisiciones();
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    llenarTablaRequisiciones();
                }
                
                
            });
        }
    };

    private final MouseListener listenerTablas = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
               
                if(e.getSource() == entregasView.getJtEntregas()){
                    int fila = entregasView.getJtEntregas().rowAtPoint(e.getPoint());
                    LlenarTablaParcialidades(Integer.parseInt(entregasView.getJtEntregas().getValueAt(fila, 0).toString()),entregasView.getJtEntregas().getValueAt(fila, 1).toString());
                }
                    
            }
        }
        
        private void LlenarTablaParcialidades(int numRequisicion,String material){
            Estructuras.limpiarTabla((DefaultTableModel) entregasView.getJtParcialidades().getModel());
            ArrayList<ControlEntregasModel.ParcialidadesRequisicion> parcialidades = entregasModel.listaParcialiades(numRequisicion, material);
            DefaultTableModel model = (DefaultTableModel) entregasView.getJtParcialidades().getModel();
            for(int i = 0;i<parcialidades.size();i++){
                ControlEntregasModel.ParcialidadesRequisicion parcialidad = parcialidades.get(i);
                model.addRow(new Object[]{
                    parcialidad.getParcialidad(),
                    parcialidad.getCantidad(),
                    parcialidad.getFechaSolicitud(),
                    parcialidad.getFechaEntrega(),
                    parcialidad.getNoOrdenProduccion()
                });
            }
        }
 
    };
    
    
    
    
    
}
