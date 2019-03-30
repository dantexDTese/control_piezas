
package Controller.CatalogosController;

import Model.CatalogosModel.CatalogoProductosModel;
import Model.CatalogosModel.Producto;
import Model.Constructores;
import Model.Estructuras;
import Model.ProductoMaquina;
import View.Catalogos.AgregarMaquinaProducto;
import View.Catalogos.CatalogoProductos;
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


public final class CatalogoProductosController implements Constructores{

    private final CatalogoProductos view;
    private final CatalogoProductosModel model;
    private ArrayList<Producto> listaProductos;
    private Producto productoSeleccionado;
    private ArrayList<ProductoMaquina> listaProductosMaquina;
    
    public CatalogoProductosController(CatalogoProductos view, CatalogoProductosModel model) {
    
        this.view = view;
        this.model = model;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarListaProductos();
        reiniciar();
        Estructuras.modificarAnchoTabla(view.getJtbListaProductos(), new Integer[]{50,100,330,200});
        view.setCbxMaterial(model.llenarComboMateriales(view.getCbxMaterial()));
        
        
    }

    @Override
    public void asignarEventos() {
       view.getJtbListaProductos().addMouseListener(listenerProductosMaquina);
       view.getBtnGuardar().addActionListener(listenerGuardarModificarProducto);
       view.getBtnAgregarMaquina().addActionListener(listenerAgregarMaquina);
    }
    
    private void llenarListaProductos(){
        listaProductos = model.listaProductos();
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbListaProductos().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaProductos.size();i++)
            modeloTabla.addRow(new Object[]{
                listaProductos.get(i).getNoProducto(),
                listaProductos.get(i).getClaveProducto(),
                listaProductos.get(i).getDescProducto(),
                listaProductos.get(i).getMaterial()
                
            });
        
    }
    
    private final MouseListener listenerProductosMaquina = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            int fila = view.getJtbListaProductos().rowAtPoint(e.getPoint());
            productoSeleccionado = obtenerProducto(
                            Integer.parseInt(view.getJtbListaProductos().getValueAt(fila,0).toString()));
            
            if(e.getClickCount() == 2){
                
                if(JOptionPane.showConfirmDialog(null, "¿QUIERES MODIFICAR ESTE PRODUCTO?","VALIDACION",JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    llenarCamposProducto(productoSeleccionado);   
                }
            }else if(e.getClickCount() == 1)
                reiniciar();
            
            if(productoSeleccionado != null)
                llenarTablaMaquinas(productoSeleccionado.getNoProducto());
        }

        private Producto obtenerProducto(int noProducto) {
            
            for(int i = 0;i<listaProductos.size();i++)
                if(listaProductos.get(i).getNoProducto() == noProducto)
                    return listaProductos.get(i);
            return null;
        }

        private void llenarCamposProducto(Producto productoSeleccionado) {
            view.getJpnNoProducto().setVisible(true);
            view.getLbNoProducto().setText(productoSeleccionado.getNoProducto()+"");
            view.getTxtClaveProducto().setText(productoSeleccionado.getClaveProducto());
            view.getTxtDescripcion().setText(productoSeleccionado.getDescProducto());
            view.getCbxMaterial().setSelectedItem(productoSeleccionado.getMaterial());
        }

    };
    
    private final ActionListener listenerGuardarModificarProducto = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(!"".equals(view.getTxtClaveProducto().getText()) && !"".equals(view.getTxtDescripcion().getText())
                    && view.getCbxMaterial().getSelectedItem() != null){
                
                if(JOptionPane.showConfirmDialog(null,"¿DESEA CONTINUAR CON ESTA OPERACION?","VERIFICACION",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                
                    Producto p = new Producto();
                    p.setClaveProducto(view.getTxtClaveProducto().getText());
                    p.setDescProducto(view.getTxtDescripcion().getText());
                    p.setMaterial(view.getCbxMaterial().getSelectedItem().toString());

                    if(productoSeleccionado == null)
                        model.agregarNuevoProducto(p);   
                    else 
                        model.modificarProducto(productoSeleccionado.getNoProducto(), p);
                    
                    reiniciar();
                    llenarListaProductos();
                }
            }
        }
    };
    
    private void llenarTablaMaquinas(int noProducto) {
        listaProductosMaquina = model.obtenerMaquinasProducto(noProducto);
        DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbMaquinasProducto().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaProductosMaquina.size();i++)
            modeloTabla.addRow(new Object[]{
                listaProductosMaquina.get(i).getNoProductoMaquina(),
                listaProductosMaquina.get(i).getDescMaquina(),
                listaProductosMaquina.get(i).getPiezasPorTurno(),
                listaProductosMaquina.get(i).getPiezasPorBarra(),
                listaProductosMaquina.get(i).getPiezasPorHora(),
                listaProductosMaquina.get(i).getDescTipoProceso()
            });

    }
    
    private void reiniciar(){
        
        view.getJpnNoProducto().setVisible(false);
        view.getTxtClaveProducto().setText("");
        view.getTxtDescripcion().setText("");
        
    }
    
    private final ActionListener listenerAgregarMaquina = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
         
            if(productoSeleccionado != null){
                AgregarMaquinaProducto viewAgregarMaquina = new AgregarMaquinaProducto(view.getPrincipal(), true, productoSeleccionado);
                viewAgregarMaquina.setVisible(true);   
                viewAgregarMaquina.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e); 
                        
                        if(viewAgregarMaquina.getpMaquina() != null)
                            model.agregarMaquinaProducto(productoSeleccionado.getClaveProducto()
                                    ,viewAgregarMaquina.getpMaquina());
                        
                        llenarTablaMaquinas(productoSeleccionado.getNoProducto());

                    }
                    
                    

                });
                
                
            }    
        }
    };

    
    
    
}
