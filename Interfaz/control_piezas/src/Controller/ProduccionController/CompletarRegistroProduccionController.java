
package Controller.ProduccionController;

import Model.Constructores;
import Model.Estructuras;
import Model.ProduccionModel.CompletarRegistroProduccionModel;
import Model.ProduccionModel.LoteProduccion;
import Model.TiempoMuertoLote;
import View.Produccion.CompletarRegistroProduccionView;
import Model.ProduccionModel.DefectoLote;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class CompletarRegistroProduccionController implements Constructores{

    
    private final CompletarRegistroProduccionView vista;
    private final CompletarRegistroProduccionModel model;
    private final LoteProduccion loteProduccion;
    private ArrayList<DefectoLote> listaDefectosLotes;
    private ArrayList<TiempoMuertoLote> listaTiemposMuertos;
    
    CompletarRegistroProduccionController(CompletarRegistroProduccionView vista,
            CompletarRegistroProduccionModel model,LoteProduccion loteProduccion){
        this.vista = vista;
        this.model = model;
        this.loteProduccion = loteProduccion;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        if(loteProduccion != null){
            llenarRegistros();

            listaDefectosLotes = model.listaDefectosLotes(loteProduccion.getDescLote());
            llenarListaDefectos();

            listaTiemposMuertos = model.listaTiemposMuertosLote(loteProduccion.getDescLote());
            llenarTablaTiemposMuertos();
        }
    }
    
    private void llenarRegistros(){
            vista.getSprCantidadAdministrador().setValue(loteProduccion.getCantidadAdmin());
            vista.getSprCantidadOperador().setValue(loteProduccion.getCantidadOperados());
            vista.getSprCantidadRechazadaLiberada().setValue(loteProduccion.getCantidadRechazoLiberado());
            vista.getSprMerma().setText(loteProduccion.getMerma()+"");
            vista.getSprScrapAjustado().setValue(loteProduccion.getScrapAjustable());
            vista.getSprScrapOperador().setValue(loteProduccion.getScrapOperador());
            vista.getSprScrapAdministrador().setValue(loteProduccion.getScrapAdmin());
            vista.getSprRechazo().setValue(loteProduccion.getRechazo());
            vista.getSprBarrasUtilizadas().setText(loteProduccion.getBarrasUtilizadas()+"");  
            vista.setCbxDefectos(model.llenarCombo(vista.getCbxDefectos(), model.LISTA_DEFECTOS_PRODUCCION));
            vista.setCbxTiempoMuerto(model.llenarCombo(vista.getCbxTiempoMuerto(), model.LISTA_TIEMPOS_MUERTOS));
            vista.getLbCantidadCalculada().setText(loteProduccion.getCantidadProducidaR()+"");
            vista.getLbObjetivoPlaneacion().setText(loteProduccion.getCantidadPlaneada()+"");
    }

    @Override
    public void asignarEventos() {
        vista.getBtnModificarRegistro().addActionListener(listenerModificar);
        vista.getBtnAgregarDefecto().addActionListener(listenerAgregarDefecto);
        vista.getBtnAgregarTiempoMuerto().addActionListener(listenerAgregarTiempoMuerto);
        vista.getJtbDefecto().addMouseListener(listenerQuitar);
        vista.getJtbTiemposMuertos().addMouseListener(listenerQuitar);
    }
    
    private final MouseListener listenerQuitar = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            int res = JOptionPane.showConfirmDialog(null, "¿SEGURO DE ELIMINAR ESTE REGISTRO?","VALIDACION",JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            
            if( res == JOptionPane.YES_OPTION){
                if(e.getSource() == vista.getJtbDefecto()){
                    int fila = vista.getJtbDefecto().rowAtPoint(e.getPoint());
                    DefectoLote lote = buscarDefectosLista(vista.getJtbDefecto().getValueAt(fila, 1).toString());
                    if(lote!=null){
                        lote.setDescLote(loteProduccion.getDescLote());
                        model.eliminarDefecto(lote);
                        listaDefectosLotes = model.listaDefectosLotes(loteProduccion.getDescLote());
                        llenarListaDefectos();
                    }
                }else if(e.getSource() == vista.getJtbTiemposMuertos()){
                    int fila = vista.getJtbTiemposMuertos().rowAtPoint(e.getPoint());
                    TiempoMuertoLote tiempoLote = buscarTiempoMuerto(vista.getJtbTiemposMuertos().getValueAt(fila, 1).toString());

                    if(tiempoLote != null){
                        tiempoLote.setDescLote(loteProduccion.getDescLote());
                        model.eliminarTiempoMuerto(tiempoLote);
                        listaTiemposMuertos = model.listaTiemposMuertosLote(loteProduccion.getDescLote());
                        llenarTablaTiemposMuertos();
                    }
                }
            }
            
        }
        
    };
    
    private final ActionListener listenerModificar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           
           if(e.getSource() == vista.getBtnModificarRegistro()){ 
                if(modificarLote())
                    model.modificarLoteProduccion(loteProduccion);
                
           }
           
        }
    
        private boolean modificarLote(){
            try {
                loteProduccion.setCantidadAdmin(Integer.parseInt(vista.getSprCantidadAdministrador().getValue().toString()));
                loteProduccion.setCantidadOperados(Integer.parseInt(vista.getSprCantidadOperador().getValue().toString()));
                loteProduccion.setCantidadRechazoLiberado(Integer.parseInt(vista.getSprCantidadRechazadaLiberada().getValue().toString()));
                loteProduccion.setMerma(Float.parseFloat(vista.getSprMerma().getText()));
                loteProduccion.setScrapAjustable(Integer.parseInt(vista.getSprScrapAjustado().getValue().toString()));
                loteProduccion.setScrapOperador(Integer.parseInt(vista.getSprScrapOperador().getValue().toString()));
                loteProduccion.setScrapAdmin(Integer.parseInt(vista.getSprScrapAdministrador().getValue().toString()));
                loteProduccion.setRechazo(Integer.parseInt(vista.getSprRechazo().getValue().toString()));
                loteProduccion.setBarrasUtilizadas(Float.parseFloat(vista.getSprBarrasUtilizadas().getText()));
                return true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CAMPOS NO VALIDOS POR FAVOR COMPLETE LOS DE FORMA CORRECTA");
                llenarRegistros();
            }
            return false;
        }
        
    };
    
    private final ActionListener listenerAgregarDefecto = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            DefectoLote loteBuscado;   
            if(Integer.parseInt(vista.getSprCantidadDefectuosa().getValue().toString()) > 0
                    && !"".equals(vista.getCbxDefectos().getSelectedItem().toString())){
                    
                int res = JOptionPane.showConfirmDialog(null, "¿SEGURO DE AGREGAR ESTE DEFECTO A LA LISTA?",
                        "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                
                if(res == JOptionPane.YES_OPTION){
                    
                    if((loteBuscado = buscarDefectosLista(vista.getCbxDefectos().getSelectedItem().toString())) != null){
                        res = JOptionPane.showConfirmDialog(null, "ESTE DEFECTO YA ESTA EN LA LISTA"
                                + ",¿CONTINUAR?","VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(res == JOptionPane.YES_OPTION){
                            loteBuscado.setCantidad(loteBuscado.getCantidad()+
                                    Integer.parseInt(vista.getSprCantidadDefectuosa().getValue().toString()));
                            actualizarLoteLista(loteBuscado); 
                            model.actualizarDefecto(loteBuscado);
                            llenarListaDefectos();
                        }      
                    
                    }else{
                        
                        loteBuscado = new DefectoLote();
                        loteBuscado.setDescLote(loteProduccion.getDescLote());
                        loteBuscado.setDescDefectoProduccion(vista.getCbxDefectos().getSelectedItem().toString());                        
                        loteBuscado.setCantidad(Integer.parseInt(vista.getSprCantidadDefectuosa().getValue().toString()));
                        listaDefectosLotes.add(loteBuscado);
                        model.agregarDefecto(loteBuscado);
                        listaDefectosLotes = model.listaDefectosLotes(loteProduccion.getDescLote());
                        llenarListaDefectos();         
                        
                    }
                    
                    vista.getSprCantidadDefectuosa().setValue(0);
                }
            }         
        }

        private void actualizarLoteLista(DefectoLote loteBuscado) {
            for(int i = 0;i<listaDefectosLotes.size();i++)
                if(listaDefectosLotes.get(i).getDescDefectoProduccion().equals(loteBuscado.getDescDefectoProduccion()))
                    listaDefectosLotes.set(i, loteBuscado);
        }
    };
 
     private final ActionListener listenerAgregarTiempoMuerto = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            TiempoMuertoLote tiempoSeleccionado;
            
            int res = JOptionPane.showConfirmDialog(null, "¿SEGURO DE AGREGAR ESTE TIEMPO MUERTO A LA LISTA?",
                        "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                
                if(res == JOptionPane.YES_OPTION){
                    if((tiempoSeleccionado = buscarTiempoMuerto(vista.getCbxTiempoMuerto().getSelectedItem().toString())) == null){
                        tiempoSeleccionado = new TiempoMuertoLote();
                        tiempoSeleccionado.setDescLote(loteProduccion.getDescLote());
                        tiempoSeleccionado.setDescTiempoMuerto(vista.getCbxTiempoMuerto().getSelectedItem().toString());
                        model.agregarTiempoMuerto(tiempoSeleccionado);
                        listaTiemposMuertos = model.listaTiemposMuertosLote(loteProduccion.getDescLote());
                        llenarTablaTiemposMuertos();   
                    }else JOptionPane.showMessageDialog(null, "ESTA DESCRIPCION YA FUE AGREGADA");
                }
            
        }

    };
    
    private void llenarListaDefectos(){
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbDefecto().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaDefectosLotes.size();i++){
            DefectoLote lote = listaDefectosLotes.get(i);
            modeloTabla.addRow(new Object[]{
                i+1,lote.getDescDefectoProduccion(),
                lote.getCantidad()
            });
        }
    }

    private void llenarTablaTiemposMuertos() {
        DefaultTableModel modeloTableModel = (DefaultTableModel) vista.getJtbTiemposMuertos().getModel();
        Estructuras.limpiarTabla(modeloTableModel);
        for(int i = 0;i<listaTiemposMuertos.size();i++)
            modeloTableModel.addRow(new Object[]{i+1,listaTiemposMuertos.get(i).getDescTiempoMuerto()});
    }


    private DefectoLote buscarDefectosLista(String descDefecto) {
            for(int i = 0;i<listaDefectosLotes.size();i++)
                if(listaDefectosLotes.get(i).getDescDefectoProduccion().equals(descDefecto))
                    return listaDefectosLotes.get(i);
            return null;    
    }
    
    
    private TiempoMuertoLote buscarTiempoMuerto(String descTiempoMuerto){
            for(int i = 0;i<listaTiemposMuertos.size();i++)
                if(listaTiemposMuertos.get(i).getDescTiempoMuerto().equals(descTiempoMuerto))
                    return listaTiemposMuertos.get(i);
            
            return null;   
    }
    
    
    
        
    
}
