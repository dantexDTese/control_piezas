
package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.ImgTabla;
import Model.RequisicionesModel.AgregarNuevaRequisicionModel;
import Model.RequisicionesModel.AsignarMaterialRequisicionModel;
import Model.RequisicionesModel.MaterialesRequisicion;
import Model.RequisicionesModel.ControlEntregasModel;
import Model.RequisicionesModel.ParcialidadesRequisicion;
import View.Pedidos.ColorEstado;
import View.Requisiciones.AsignarMaterialRequisicion;
import View.Requisiciones.ControlEntregasView;
import View.Requisiciones.agregarNuevasRequisiciones;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
    private ArrayList<ParcialidadesRequisicion> parcialidades;
    private int noRequisicionSeleccionado;
    private String descMaterialSeleccionado;
    private ArrayList<MaterialesRequisicion> listaMaterialesRequisicion;
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
        entregasView.getBtnCerrar().setEnabled(false);
        this.entregasView.getJtEntregas().setDefaultRenderer(Object.class,new ImgTabla());
        Estructuras.modificarAnchoTabla(this.entregasView.getJtEntregas(),
                new Integer[]{110,160,130,80});
        
    }

    @Override
    public void asignarEventos() {
        
        this.entregasView.getBtnAgregarRequisiciones().addActionListener(listenerBotones);
        this.entregasView.getJtEntregas().addMouseListener(listenerTablas);
        this.entregasView.getJycAnioSeleccionado().addPropertyChangeListener(listenerFechas);
        this.entregasView.getJmcMesSeleccionado().addPropertyChangeListener(listenerFechas);
        this.entregasView.getBtnModificarObservaciones().addActionListener(listenerBotones);
        this.entregasView.getJtParcialidades().addMouseListener(listenerAsignarMaterial);
        this.entregasView.getBtnCerrar().addActionListener(listenerCerrar);
        
    }
    
    /**
     *METODOS 
     */
    public void llenarTablaRequisiciones(){
        listaMaterialesRequisicion = entregasModel.obtenerRequisiciones(entregasView.getJycAnioSeleccionado().getValue(),
                                                                                entregasView.getJmcMesSeleccionado().getMonth()+1);
        
        DefaultTableModel modelRequisiciones = (DefaultTableModel) entregasView.getJtEntregas().getModel();
        Estructuras.limpiarTabla(modelRequisiciones);
        
        for(int i = 0;i<listaMaterialesRequisicion.size();i++){
            MaterialesRequisicion requisicion = listaMaterialesRequisicion.get(i);
            
            ColorEstado estado = Estructuras.obtenerColorEstado(requisicion.getDescEstado());
            
            modelRequisiciones.addRow(new Object[]{
                requisicion.getNoRequisicion(),
                requisicion.getDescTipoMaterial() + " " + requisicion.getDescDimencion() + " " + requisicion.getClaveForma() ,
                requisicion.getBarrasNecesarias(),
                estado
            });
        }
            
    }
    
    /**
     * EVENTOS
     */
    
    private final PropertyChangeListener listenerFechas = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            entregasView.getLbAnioBitacora().setText(entregasView.getJycAnioSeleccionado().getValue()+"");
            llenarTablaRequisiciones();
            entregasView.getTxtObservaciones().setText("");
            Estructuras.limpiarTabla((DefaultTableModel) entregasView.getJtParcialidades().getModel());
            entregasView.getLbNoRequisicion().setText("");
        }
    };
    
    private final ActionListener listenerBotones   =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == entregasView.getBtnAgregarRequisiciones())
                    agregarNuevaRequisicion();
                
                else if(e.getSource() == entregasView.getBtnModificarObservaciones())
                   entregasModel.agregarComentarios(Integer.parseInt(entregasView.getLbNoRequisicion().getText()), entregasView.getTxtObservaciones().getText());
                
        }
        
        private void agregarNuevaRequisicion(){
            agregarNuevasRequisiciones vista = new agregarNuevasRequisiciones(entregasView.getPrincipal(), true);
            AgregarNuevaRequisicionController controller = new AgregarNuevaRequisicionController(vista,
            new AgregarNuevaRequisicionModel());
            vista.setVisible(true);
            vista.addWindowListener(new WindowAdapter() {
                
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
                    noRequisicionSeleccionado = Integer.parseInt(entregasView.getJtEntregas().getValueAt(fila, 0).toString());
                    descMaterialSeleccionado = entregasView.getJtEntregas().getValueAt(fila, 1).toString();
                    LlenarTablaParcialidades(noRequisicionSeleccionado,descMaterialSeleccionado);
                    validarCerrarRequisicion(fila);
                    entregasView.getLbNoRequisicion().setText(noRequisicionSeleccionado+"");
                    entregasView.getTxtObservaciones().setText(entregasModel.obtenerObservaciones(noRequisicionSeleccionado));
                    
                }
                    
            }
        }
        
        private void LlenarTablaParcialidades(int numRequisicion,String material){
            Estructuras.limpiarTabla((DefaultTableModel) entregasView.getJtParcialidades().getModel());
            parcialidades = entregasModel.listaParcialiades(numRequisicion, material);
            DefaultTableModel model = (DefaultTableModel) entregasView.getJtParcialidades().getModel();
            for(int i = 0;i<parcialidades.size();i++){
                ParcialidadesRequisicion parcialidad = parcialidades.get(i);
                model.addRow(new Object[]{
                    parcialidad.getParcialidad(),
                    parcialidad.getCantidad(),
                    parcialidad.getFechaSolicitud(),
                    parcialidad.getNoOrdenProduccion()
                });
            }
        }

        private void validarCerrarRequisicion(int fila) {
            int noRequisicion = Integer.parseInt(entregasView.getJtEntregas().getValueAt(fila, 0).toString());
            String descMaterial = entregasView.getJtEntregas().getValueAt(fila,1).toString();
            for(int i = 0;i<parcialidades.size();i++){
                MaterialesRequisicion rs = listaMaterialesRequisicion.get(i);
                if(noRequisicion==rs.getNoRequisicion()
                    && descMaterial.equals(rs.getDescTipoMaterial() + " " + rs.getDescDimencion() + " " + rs.getClaveForma()))
                    entregasView.getBtnCerrar().setEnabled(("ABIERTO".equals(rs.getDescEstado()))); 
            }            
        }
    };
    
    ActionListener listenerCerrar = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO DE CERRAR ESTA PARTE DE LA REQUISICION?","VALIDACION",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    System.err.println(noRequisicionSeleccionado+" "+descMaterialSeleccionado);
                    if(respuesta == JOptionPane.YES_OPTION){
                        entregasModel.cerrarParteRequisicion(noRequisicionSeleccionado,descMaterialSeleccionado);
                        entregasView.getBtnCerrar().setEnabled(false);
                        llenarTablaRequisiciones();
                    }
                }
            };
    
    private final MouseListener listenerAsignarMaterial = new MouseAdapter() {
       
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);    
            
            int fila = entregasView.getJtParcialidades().rowAtPoint(e.getPoint());
            
            ParcialidadesRequisicion parcialidad = obtenerParcialidad(
                    Integer.parseInt(entregasView.getJtParcialidades().getValueAt(fila, 0).toString()),
                    Integer.parseInt(entregasView.getJtParcialidades().getValueAt(fila, 3).toString()));
            
            AsignarMaterialRequisicion vista = new AsignarMaterialRequisicion(entregasView.getPrincipal(), true);
            AsignarMaterialRequisicionController controller = new AsignarMaterialRequisicionController(vista,
                    new AsignarMaterialRequisicionModel(),parcialidad);
            vista.setVisible(true);
            
            vista.addWindowListener(new WindowAdapter() {
                
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);   
                }
                
            });
        }

        private ParcialidadesRequisicion obtenerParcialidad(int noParcialidad, int noOp) {
            ParcialidadesRequisicion parcialidad = null;
            for(int i = 0;i<parcialidades.size();i++){
                parcialidad = parcialidades.get(i);
                if(parcialidad.getNoOrdenProduccion() == noOp && parcialidad.getParcialidad() == noParcialidad)
                    return parcialidad;   
            }
            return null;
        }
        
    };
    
}
