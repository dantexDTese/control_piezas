
package Controller.RequisicionesController;

import Model.Constructores;
import Model.Estructuras;
import Model.RequisicionesModel.EntradaMaterial;
import Model.RequisicionesModel.InspeccionDimencion;
import Model.RequisicionesModel.InspeccionEntrada;
import Model.RequisicionesModel.PlanInspeccionMaterialesModel;
import View.Requisiciones.PlanInspeccionMateriales;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public final class PlanInspeccionMaterialesController implements Constructores{

    private final PlanInspeccionMateriales vista;
    private final PlanInspeccionMaterialesModel model;
    private final EntradaMaterial materialSeleccionado;
    private final InspeccionEntrada[][] listaInspeccionEntradas;
    private final InspeccionDimencion[][] listaInspeccionDimenciones;
    
    PlanInspeccionMaterialesController(PlanInspeccionMateriales viewInspeccion, PlanInspeccionMaterialesModel planInspeccionMaterialesModel,EntradaMaterial materialSeleccionado) {
       this.vista = viewInspeccion;
       this.model = planInspeccionMaterialesModel;
       this.materialSeleccionado = materialSeleccionado;
       listaInspeccionEntradas = new InspeccionEntrada[4][6];
       listaInspeccionDimenciones = new InspeccionDimencion[2][6];
       llenarComponentes();
       asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        
        vista.getLbProveedor().setText(materialSeleccionado.getDescProveedor());
        vista.getLbDisponibilidad().setText(materialSeleccionado.getDescEstado());
        vista.getLbAprobacion().setText(materialSeleccionado.getInspector());
        vista.getLbDescripcion().setText(materialSeleccionado.getDescMaterial());
        vista.getLbOrdenCompra().setText(materialSeleccionado.getOrdenCompra());
        vista.getLbFecha().setText(Estructuras.convertirFechaGuardar(new Date()));
        vista.getLbCantidad().setText(materialSeleccionado.getCantidad()+"");
        vista.getJtbInspeccion().setRowHeight(50);
        vista.getTxtComentarios().setText(materialSeleccionado.getComentarios());
        vista.getTxtFactura().setText(materialSeleccionado.getFactura());
        vista.getLbLote().setText(materialSeleccionado.getDescLote());
        
        Estructuras.modificarAnchoTabla(vista.getJtbInspeccion(), new Integer[]{250,50,50,50,50,50,50});
        
        if(materialSeleccionado.getDescEstado().equals("APROBADA") || materialSeleccionado.getDescEstado().equals("RECHAZADA")){
            vista.getBtnGuardar().setEnabled(false);
            vista.getTxtFactura().setEditable(false);
            vista.getLbLote().setEditable(false);
            vista.getTxtComentarios().setEditable(false);
            obtenerResultados();
        }
        
        String[] dimenciones = model.obtenerDimencionesMaterial(materialSeleccionado.getDescMaterial());
        vista.getJtbInspeccion().setValueAt("DIMENCIONES "+dimenciones[0]+" "+
                convertirFraccion(dimenciones[0])+"m de largo", 0, 0);
        vista.getJtbInspeccion().setValueAt("DIMENCIONES "+dimenciones[1]+"m de largo", 1, 0);
        
    }
    
    private String convertirFraccion(String franccion){
        float resultado=0;
        int i = 0;
        for(;i<franccion.length();i++)
            if(!"/".equals(franccion.substring(i, i+1)))
                resultado += Float.parseFloat(franccion.substring(i, i+1))*Math.pow(10, i);
            else break;
        
        
         return (resultado / Float.parseFloat(franccion.substring(i+1, franccion.length())))+"";

    }

    @Override
    public void asignarEventos() {
        vista.getBtnGuardar().addActionListener(listenerBotones);
    }
    
    private final ActionListener listenerBotones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!"".equals(vista.getTxtFactura().getText()) && 
                    (vista.getRbtAprobar().isSelected() || vista.getRbtRechazar().isSelected())){
                
                if(JOptionPane.showConfirmDialog(null, "SEGURO DE GUARDAR LOS CAMBIOS"
                        ,"GUARDAR CAMBIOS",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    if(completarCampos()){
                        model.actualizarInformacion(materialSeleccionado);
                        model.registrarInspeccionEntrada(listaInspeccionEntradas);
                        model.registrarInspeccionDimenciones(listaInspeccionDimenciones);
                    }   
                    vista.dispose();
                }
            }else JOptionPane.showMessageDialog(null,"POR FAVOR COMPLETA LOS CAMPOS");
        }
        
        
        private boolean completarCampos(){
            if(llenarListaInspeccionEntrada() && llenarListaInspeccionDimenciones()){
                materialSeleccionado.setFactura(vista.getTxtFactura().getText());
                materialSeleccionado.setComentarios(vista.getTxtComentarios().getText());
                materialSeleccionado.setDescEstado( (vista.getRbtAprobar().isSelected())? "APROBADA":"RECHAZADA" );
                materialSeleccionado.setDescLote(vista.getLbLote().getText());
                return true;
            }
            return false;   
        }

        private boolean llenarListaInspeccionEntrada() {
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbInspeccion().getModel();
            for(int i=2;i<modeloTabla.getRowCount();i++)
                for(int j = 1;j<modeloTabla.getColumnCount();j++){
                    String resultadoInspeccion = modeloTabla.getValueAt(i, j).toString().toUpperCase();
                    if(resultadoInspeccion.equals("C") ||resultadoInspeccion.equals("R") ){
                        InspeccionEntrada entrada = new InspeccionEntrada();
                        entrada.setNoEntradaMaterial(materialSeleccionado.getNoEntradaMaterial());
                        entrada.setDescResultadoInspeccion(resultadoInspeccion);
                        entrada.setDescTipoInspeccion(model.LISTA_TIPOS_INSPECCION[i-2]);
                        listaInspeccionEntradas[i-2][j-1] = entrada;
                    }else{
                        JOptionPane.showMessageDialog(null, "LOS VALORES DE LA INSPECCION NO SON VALIDOS"
                                ,"VALIDACION",JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            
            return true;
        }

        private boolean llenarListaInspeccionDimenciones() {
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbInspeccion().getModel();
            for(int i=0;i<2;i++)
                for(int j = 1;j<modeloTabla.getColumnCount();j++){
                    try {
                        float resultadoInspeccion = Float.parseFloat(vista.getJtbInspeccion().getValueAt(i, j).toString());
                        InspeccionDimencion entrada = new InspeccionDimencion();
                        entrada.setNoEntradaMaterial(materialSeleccionado.getNoEntradaMaterial());
                        entrada.setResultadoInspeccion(resultadoInspeccion);
                        entrada.setDescTipoInspeccion(model.LISTA_TIPOS_INSPECCION_DIMENCIONES[i]);
                        listaInspeccionDimenciones[i][j-1] = entrada;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "LOS VALORES DE LA INSPECCION NO SON VALIDOS"
                                ,"VALIDACION",JOptionPane.ERROR_MESSAGE);
                        return false;
                    }          
                }
            
            return true;
        }
        
    };

    private void obtenerResultados() {
        ArrayList<Object> listaResultadosPropiedades = model.obtenerListasResultados(materialSeleccionado.getNoEntradaMaterial(), model.TIPO_LISTA_INSPECCION_PROPIEDADES);
        ArrayList<Object> listaResultadosDimenciones = model.obtenerListasResultados(materialSeleccionado.getNoEntradaMaterial(), model.TIPO_LISTA_INSPECCION_DIMENCIONES);
        
        
        for(int i = 0;i<model.LISTA_TIPOS_INSPECCION.length;i++)
            llenarFilaResultadoPropiedades(listaResultadosPropiedades,model.LISTA_TIPOS_INSPECCION[i]);
        
        for(int i = 0;i<model.LISTA_TIPOS_INSPECCION_DIMENCIONES.length;i++)
            llenarFilaResultadoDimenciones(listaResultadosDimenciones,model.LISTA_TIPOS_INSPECCION_DIMENCIONES[i]);
            
    }
    
    private void llenarFilaResultadoDimenciones(ArrayList<Object> listaResultados,String tipoProceso){
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbInspeccion().getModel();
        
        for(int i = 0,cuenta = 0;i<listaResultados.size();i++,cuenta++){
            if(cuenta == 6)
                break;
            InspeccionDimencion dimencion = (InspeccionDimencion) listaResultados.get(i);
            if(tipoProceso.equals(model.TIPO_INSPECCION_DIMENCION_1))
                    modeloTabla.setValueAt(dimencion.getResultadoInspeccion(), 0,cuenta+1);
            
            else if(tipoProceso.equals(model.TIPO_INSPECCION_DIMENCION_2))
                    modeloTabla.setValueAt(dimencion.getResultadoInspeccion(), 1,cuenta+1);
            
        }
            
        
    }
    
    private void llenarFilaResultadoPropiedades(ArrayList<Object> listaResultados,String tipoProceso){
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbInspeccion().getModel();
        
        for(int i = 0,cuenta = 0;i<listaResultados.size();i++,cuenta++){
            if(cuenta == 6)
                break;
            InspeccionEntrada entrada = (InspeccionEntrada) listaResultados.get(i);
            if(tipoProceso.equals(model.TIPO_INSPECCION_CERTIFICADO_DE_MATERIAL))
                    modeloTabla.setValueAt(entrada.getDescResultadoInspeccion(), 2,cuenta+1);
            
            else if(tipoProceso.equals(model.TIPO_INSPECCION_IDENTIFICACION_DE_MATERIA_PRIMA))
                    modeloTabla.setValueAt(entrada.getDescResultadoInspeccion(), 3,cuenta+1);
            
            else if(tipoProceso.equals(model.TIPO_INSPECCION_APARIENCIA))
                    modeloTabla.setValueAt(entrada.getDescResultadoInspeccion(), 4,cuenta+1);
            
            else if(tipoProceso.equals(model.TIPO_INSPECCION_EMPAQUE))
                    modeloTabla.setValueAt(entrada.getDescResultadoInspeccion(), 5,cuenta+1);
            
        }
            
        
    }
    
    
    
    
}
