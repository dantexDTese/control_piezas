package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.RequisicionesModel.EntradaMaterial;
import Model.RequisicionesModel.PlanInspeccionMaterialesModel;
import Model.RequisicionesModel.RegistroEntradaMaterialesModel;
import Model.RequisicionesModel.RegistrarNuevaEntradaMaterialModel;
import View.Requisiciones.PlanInspeccionMateriales;
import View.Requisiciones.RegistrarNuevaEntradaMaterial;
import View.Requisiciones.RegistroEntradaMateriales;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class RegistroEntradaMaterialesController implements Constructores{

    RegistroEntradaMateriales entradaMaterialesView;
    RegistroEntradaMaterialesModel entradaMaterialesModel;
    private ArrayList<EntradaMaterial> listaEntradaMateriales;
    public RegistroEntradaMaterialesController(RegistroEntradaMateriales entradaMaterialesView,
            RegistroEntradaMaterialesModel entradaMaterialesModel) {
        
        this.entradaMaterialesView = entradaMaterialesView;
        this.entradaMaterialesModel = entradaMaterialesModel;

        
        llenarComponentes();
        asignarEventos();
        
    }
    
    @Override
    public void llenarComponentes() {
        llenarTablaRegistroEntradaMateriales();
    }

    @Override
    public void asignarEventos() {
        entradaMaterialesView.getBtnRegistrarNuevaEntrada().addActionListener(listenerBotones);
        entradaMaterialesView.getJtbEntradaMateriales().addMouseListener(listenerTablaEntradas);
    }
    
    private void llenarTablaRegistroEntradaMateriales(){
        listaEntradaMateriales = entradaMaterialesModel.listaRegistroEntradaMateriales();
        Estructuras.limpiarTabla((DefaultTableModel) entradaMaterialesView.getJtbEntradaMateriales().getModel());
        DefaultTableModel modelTabla = (DefaultTableModel) entradaMaterialesView.getJtbEntradaMateriales().getModel();
        for(int i = 0;i<listaEntradaMateriales.size();i++){
            EntradaMaterial entradaMaterial = listaEntradaMateriales.get(i);
            modelTabla.addRow(new Object[]{
                entradaMaterial.getNoEntradaMaterial(),
                entradaMaterial.getFechaRegistro(),
                entradaMaterial.getDescMaterial(),
                entradaMaterial.getCodigo(),
                entradaMaterial.getDescProveedor(),
                entradaMaterial.getCantidad(),
                entradaMaterial.getOrdenCompra(),
                entradaMaterial.getInspector(),
                entradaMaterial.getCertificado(),
                entradaMaterial.getDescEstado()
            });
        }
            
    }
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            agregarNuevaEntradaMaterial();
        }
        
        private void agregarNuevaEntradaMaterial(){
            RegistrarNuevaEntradaMaterial viewNuevaEntrada = new RegistrarNuevaEntradaMaterial(entradaMaterialesView.getPrincipal(), true);
            RegistrarNuevaEntradaMaterialController nuevaEntradaController = new RegistrarNuevaEntradaMaterialController(
                    viewNuevaEntrada,new RegistrarNuevaEntradaMaterialModel());
            
            viewNuevaEntrada.setVisible(true);
            
            viewNuevaEntrada.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    llenarTablaRegistroEntradaMateriales();
                }
            });
            
        }
    };
    
    private final MouseListener listenerTablaEntradas = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                if( e.getSource() == entradaMaterialesView.getJtbEntradaMateriales()){
                    int fila = entradaMaterialesView.getJtbEntradaMateriales().rowAtPoint(e.getPoint());
                    mostrarPlanInspeccion(listaEntradaMateriales.get(fila));
                    
                }
            }
        }
        
        private void mostrarPlanInspeccion(EntradaMaterial materialSeleccionado){
            
            PlanInspeccionMateriales viewInspeccion = new PlanInspeccionMateriales(entradaMaterialesView.getPrincipal(), true);
            PlanInspeccionMaterialesController controllerInspeccion = new PlanInspeccionMaterialesController(viewInspeccion,new PlanInspeccionMaterialesModel(),
            materialSeleccionado);
            viewInspeccion.setVisible(true);
            
        }
        
    };
    
}
