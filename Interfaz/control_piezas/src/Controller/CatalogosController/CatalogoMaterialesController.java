
package Controller.CatalogosController;

import Model.CatalogosModel.CatalogoMaterialesModel;
import Model.CatalogosModel.Material;
import Model.Constructores;
import Model.Estructuras;
import View.Catalogos.CatalogoMateriales;
import View.Catalogos.agregarNuevaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public final class CatalogoMaterialesController implements Constructores{

    
    private final CatalogoMateriales view;
    private final CatalogoMaterialesModel model;
    private ArrayList<Material> listaMateriales;
    private Material materialSeleccionado;

    public CatalogoMaterialesController(CatalogoMateriales view, CatalogoMaterialesModel model) {
        
        this.view = view;
        this.model = model;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarTablaMateriales();
        view.getJpnVerNoOrden().setVisible(false);
        view.setCbxTiposMaterial(model.llenarCombo(view.getCbxTiposMaterial(),model.LISTA_TIPOS_MATERIAL));
        view.setCbxDimencionesMateriales(model.llenarCombo(view.getCbxDimencionesMateriales(), model.LISTA_DIMENCIONES_MATERIAL));
        view.setCbxFormasMateriales(model.llenarCombo(view.getCbxFormasMateriales(), model.LISTA_FORMAS_MATERIAL));
    }

    @Override
    public void asignarEventos() {
        view.getJtbListaMateriales().addMouseListener(listenerTabla);
        view.getBtnGuardar().addActionListener(listenerGuardarModificar);
        view.getCbxTiposMaterial().addActionListener(listenerAgregarDatosCombos);
        view.getCbxDimencionesMateriales().addActionListener(listenerAgregarDatosCombos);
        view.getCbxFormasMateriales().addActionListener(listenerAgregarDatosCombos);
        
        
    }
    
    private void llenarTablaMateriales(){
        listaMateriales = model.obtenerListaMateriales();        
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbListaMateriales().getModel();
        
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaMateriales.size();i++){
            Material material = listaMateriales.get(i);
            
            modeloTabla.addRow(new Object[]{
                material.getNoMaterial(),
                material.getDescTipoMaterial(),
                material.getDescDimenciones(),
                material.getDescForma(),
                material.getLongitudBarra()
            });
        }
           
    }

    private final MouseListener listenerTabla = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                if(JOptionPane.showConfirmDialog(null, "¿QUIERES MODIFICAR ESTE MATERIAL?","VALIDACION",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)
                        == JOptionPane.YES_OPTION){

                    int fila = view.getJtbListaMateriales().rowAtPoint(e.getPoint());

                    materialSeleccionado = buscarMaterial(Integer.
                            parseInt(view.getJtbListaMateriales().getValueAt(fila,0).toString()));
                    
                    llenarDatosMaterialSeleccionado();
                    view.getJpnVerNoOrden().setVisible(true);
                }
            }else if(e.getClickCount() == 1)
                reiniciar();
            
        }
        
        private void llenarDatosMaterialSeleccionado(){
            view.getLbNoMaterial().setText(materialSeleccionado.getNoMaterial()+"");
            view.getCbxTiposMaterial().setSelectedItem(materialSeleccionado.getDescTipoMaterial());
            view.getCbxDimencionesMateriales().setSelectedItem(materialSeleccionado.getDescDimenciones());
            view.getCbxFormasMateriales().setSelectedItem(materialSeleccionado.getDescForma());
            view.getTxtLongitudBarra().setText(materialSeleccionado.getLongitudBarra()+"");
            view.getLbOperacion().setText("MODIFICAR");
        }

        private Material buscarMaterial(int noMaterial) {
            for(int i = 0;i<listaMateriales.size();i++)
                if(noMaterial == listaMateriales.get(i).getNoMaterial())
                    return listaMateriales.get(i);
            
            return null;
        }
    };
    
    private final ActionListener listenerGuardarModificar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(JOptionPane.showConfirmDialog(null, "ESTAS SEGURO DE HACER ESTOS CAMBIOS",
                    "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) ==
                    JOptionPane.YES_OPTION){
                Material materialOPeracion = hacerMaterialNuevo();

                if(materialSeleccionado != null)
                    model.modificarMaterial(materialOPeracion,materialSeleccionado.getNoMaterial());
                else
                    model.guardarNuevoMaterial(materialOPeracion);

                llenarTablaMateriales();
                reiniciar();
                
            }
        }
        
        

        private Material hacerMaterialNuevo() {
            Material materialOPeracion = new Material();
            materialOPeracion.setDescTipoMaterial(view.getCbxTiposMaterial().getSelectedItem().toString());
            materialOPeracion.setDescDimenciones(view.getCbxDimencionesMateriales().getSelectedItem().toString());
            materialOPeracion.setDescForma(view.getCbxFormasMateriales().getSelectedItem().toString());
            materialOPeracion.setLongitudBarra(Float.parseFloat(view.getTxtLongitudBarra().getText()));
            return materialOPeracion;
        }
    };
    
    private void reiniciar(){
        view.getJpnVerNoOrden().setVisible(false);
        view.getTxtLongitudBarra().setText("");
        materialSeleccionado = null;
        view.getLbOperacion().setText("AGREGAR");
    }
    
    private final ActionListener listenerAgregarDatosCombos = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            

                if(e.getSource() == view.getCbxTiposMaterial() &&
                        "AGREGAR...".equals(view.getCbxTiposMaterial().getSelectedItem().toString()))
                    agregarNuevoTipoMaterial();

                else if(e.getSource() == view.getCbxDimencionesMateriales()  &&
                        "AGREGAR...".equals(view.getCbxDimencionesMateriales().getSelectedItem().toString()))
                    agregarNuevaDimenciMaterial();

                else if(e.getSource() == view.getCbxFormasMateriales()  &&
                        view.getCbxFormasMateriales().getSelectedItem() != null)
                    if("AGREGAR...".equals(view.getCbxFormasMateriales().getSelectedItem().toString()))
                    agregarNuevaFormaMaterial();
            
        
        }

        private void agregarNuevoTipoMaterial() {
            if(JOptionPane.showConfirmDialog(null, "¿SEGURO DE AGREGAR UN NUEVO REGISTRO?","VALIDACION",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 
                    JOptionPane.YES_OPTION){
                String respuesta = JOptionPane.showInputDialog(null,"DESCRIPCION DEL MATERIAL",
                        "AGREGAR NUEVO TIPO MATERIAL",JOptionPane.QUESTION_MESSAGE);

                if(respuesta != null && !"".equals(respuesta)){
                    model.agregarNuevoTipoMaterial(respuesta);
                    view.setCbxTiposMaterial(model.llenarCombo(view.getCbxTiposMaterial(),model.LISTA_TIPOS_MATERIAL));
                }                
            }
        }

        private void agregarNuevaDimenciMaterial() {
            if(JOptionPane.showConfirmDialog(null, "¿SEGURO DE AGREGAR UN NUEVO REGISTRO?","VALIDACION",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 
                    JOptionPane.YES_OPTION){
                
                String respuesta = JOptionPane.showInputDialog(null,"ESCRIBE LA DIMENCION DEL MATERIAL",
                        "AGREGAR NUEVA DIMENCION",JOptionPane.QUESTION_MESSAGE);

                if(respuesta != null && !"".equals(respuesta)){
                    if(validarDimencion(respuesta)){
                        model.agregarNuevaDimencion(respuesta);
                        view.setCbxDimencionesMateriales(model.llenarCombo(view.getCbxDimencionesMateriales(), model.LISTA_DIMENCIONES_MATERIAL));                
                    }else
                        JOptionPane.showMessageDialog(null, "LA ENTRADA NO ES CORRECTA, POR FAVOR INTENTE DE NUEVO");
                
                }   
            }    
        }
        

        private void agregarNuevaFormaMaterial() {
            if(JOptionPane.showConfirmDialog(null, "¿SEGURO DE AGREGAR UN NUEVO REGISTRO?","VALIDACION",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 
                    JOptionPane.YES_OPTION){
                
            
                agregarNuevaForma nuevaForma = new agregarNuevaForma(view.getPrincipal(), true);
                nuevaForma.setVisible(true);
                nuevaForma.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        
                        if(nuevaForma.getDescForma() != null && nuevaForma.getClaveForma() != null){
                            if(!"".equals(nuevaForma.getDescForma())
                                 && !"".equals(nuevaForma.getClaveForma())){
                                model.agregarNuevaForma(nuevaForma.getDescForma(),nuevaForma.getClaveForma());
                            }else
                                JOptionPane.showMessageDialog(null, "LOS CAMPOS NO FUERES LLENADOS, POR FAVOR INTENTE DE NUEVO");
                        }
                        view.setCbxFormasMateriales(model.llenarCombo(view.getCbxFormasMateriales(), model.LISTA_FORMAS_MATERIAL));
                    }
                });
            
            }
        }  

        private boolean validarDimencion(String respuesta) {
            Pattern exRegular = Pattern.compile("[1-9]*\\s{0,1}\\/{0,1}");
            Matcher validar = exRegular.matcher(respuesta);
            return (validar.find())? true:false;
        }
    };
}
