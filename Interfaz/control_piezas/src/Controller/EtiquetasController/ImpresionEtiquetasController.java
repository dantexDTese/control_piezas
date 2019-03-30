package Controller.EtiquetasController;

import Model.Constructores;
import Model.Estructuras;
import Model.EtiquetasModel.CreacionPDF;
import Model.EtiquetasModel.GenerateBarcode;
import Model.EtiquetasModel.ImpresionEtiquetasModel;
import Model.Pedido;
import Model.ProduccionModel.LoteProduccion;
import View.Etiquetas.ImpresionEtiquetasView;
import com.itextpdf.text.DocumentException;
import ds.desktop.notify.DesktopNotify;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;


public final class ImpresionEtiquetasController  implements Constructores{

    private final ImpresionEtiquetasView vista;
    private final ImpresionEtiquetasModel modelo;
    private String codOrdenCompra;
    private ArrayList<LoteProduccion> listaLotes;
    private ArrayList<LoteProduccion> listaLotesSeleccionados;
    private final GenerateBarcode barras;
    
    public ImpresionEtiquetasController(ImpresionEtiquetasView vista,
            ImpresionEtiquetasModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        barras = new GenerateBarcode();
        listaLotesSeleccionados = new ArrayList<>();
        llenarComponentes();
        asignarEventos();
    }
    
    @Override
    public void llenarComponentes() {
        llenarTablaPedidosPendientes();

    }

    @Override
    public void asignarEventos() {
        vista.getJtbPedidosPendientes().addMouseListener(listenerTablaPendientes);
        vista.getJtbLotesListos().addMouseListener(listenerTablaSeleccionaLote);
        vista.getSprPiezasPorBolsa().addChangeListener(listenerCalcularNumBolsas);
        vista.getCbxProductos().addActionListener((ActionEvent e) -> {
            if(vista.getCbxProductos().getItemCount()>0)
                llenarTablaLotes(codOrdenCompra, vista.getCbxProductos().getSelectedItem().toString());
        });
        vista.getJtbLotesSeleccionados().addMouseListener(listenerQuitarLote);
        vista.getBtnGuardar().addActionListener(listenerImprimir);
        
    }
    
    private void llenarTablaPedidosPendientes(){
        ArrayList<Pedido> listaPedidos = modelo.pedidosPendientes();
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbPedidosPendientes().getModel();
        
        Estructuras.limpiarTabla(modeloTabla);
        
        for(int i = 0;i<listaPedidos.size();i++){
            Pedido ped = listaPedidos.get(i);
            
            modeloTabla.addRow(new Object[]{
                ped.getNoPedido(),
                ped.getNoOrdenCompra(),
                ped.getDescCliente()
            });
        }   
    }
    
    private final MouseListener listenerTablaPendientes = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            int fila = vista.getJtbPedidosPendientes().rowAtPoint(e.getPoint());
            codOrdenCompra = vista.getJtbPedidosPendientes().getValueAt(fila, 1).toString();
            vista.setCbxProductos(modelo.listaProductos(vista.getCbxProductos(), codOrdenCompra));
        } 
    };
    
    private final MouseListener listenerTablaSeleccionaLote = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿AGREGAR?","VALIDACION",JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                    int fila = vista.getJtbLotesListos().rowAtPoint(e.getPoint());
                    LoteProduccion lote = listaLotes.get(fila);
                    agregarLoteEtiquetas(lote);
                    llenarTablaLotesAgregados();
                    actualizarDescripcionEtiqueta();
                    vista.getSprTotalPiezas().setValue(sumarCantidadesLotes());
                }
            }
        }
    };
    
    private final ChangeListener listenerCalcularNumBolsas = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(Integer.parseInt(vista.getSprTotalPiezas().getValue().toString()) >= 0 &&
                    Integer.parseInt(vista.getSprPiezasPorBolsa().getValue().toString()) >= 0
                    && Integer.parseInt(vista.getSprTotalPiezas().getValue().toString()) >=
                    Integer.parseInt(vista.getSprPiezasPorBolsa().getValue().toString())){
                
                int numBolsas = (int) Math.ceil(Float.parseFloat(vista.getSprTotalPiezas().getValue().toString() )
                    / Float.parseFloat(vista.getSprPiezasPorBolsa().getValue().toString()));
                
                vista.getLbNumEtiquetas().setText(numBolsas+"");

            }else{
                JOptionPane.showMessageDialog(null, "NUMERO DE PIEZAS POR BOLSA NO VALIDO, POR FAVOR INTENTE DE NUEVO"
                    ,"VALIDACION",JOptionPane.ERROR_MESSAGE);
                vista.getSprPiezasPorBolsa().setValue(0);
                vista.getLbNumEtiquetas().setText(0+"");
            }
            
            
        }
    };
    
    private final MouseListener listenerQuitarLote = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                int respueta = JOptionPane.showConfirmDialog(null, "¿SEGURO DE QUITAR ESTE LOTE?","VALIDACION",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                if(respueta == JOptionPane.YES_OPTION){
                    int fila = vista.getJtbLotesSeleccionados().rowAtPoint(e.getPoint());
                    String descLote = vista.getJtbLotesSeleccionados().getValueAt(fila, 1).toString();
                    listaLotesSeleccionados.remove(fila);
                    llenarTablaLotesAgregados();
                    actualizarDescripcionEtiqueta();
                    vista.getSprTotalPiezas().setValue(sumarCantidadesLotes());
                    if(listaLotesSeleccionados.isEmpty()){
                        vista.getLbCodigoEtiqueta().setText("");
                        vista.getLbNumEtiquetas().setText("");
                    }
                } 
            }
        }
    };
    
    private void llenarTablaLotes(String codOrdenCompra,String claveProducto) {
            listaLotes = modelo.listaLotesProduccion(codOrdenCompra,claveProducto);
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbLotesListos().getModel();
            Estructuras.limpiarTabla(modeloTabla);
            for(int i = 0;i<listaLotes.size();i++){
                LoteProduccion lote = listaLotes.get(i);
                modeloTabla.addRow(new Object[]{
                    lote.getNumLote(),
                    lote.getDescLote(),
                    lote.getCantidadAdmin()
                });
            }
        }
    
    private void agregarLoteEtiquetas(LoteProduccion lote) {
            
            for(int i = 0;i<listaLotesSeleccionados.size();i++){
                if(lote.getDescLote().equals(listaLotesSeleccionados.get(i).getDescLote())){
                    JOptionPane.showMessageDialog(null, "ESTE LOTE YA FUE AGREGADO, SELECCIONE OTRO POR FAVOR");
                    return;
                }
            }
            listaLotesSeleccionados.add(lote);
            
        }

        
    private void llenarTablaLotesAgregados() {
            
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbLotesSeleccionados().getModel();
            Estructuras.limpiarTabla(modeloTabla);
            for(int i = 0;i<listaLotesSeleccionados.size();i++)
                modeloTabla.addRow(new Object[]{
                    listaLotesSeleccionados.get(i).getNumLote(),
                    listaLotesSeleccionados.get(i).getDescLote(),
                    listaLotesSeleccionados.get(i).getCantidadAdmin()
                });
            
        }

    
    private void actualizarDescripcionEtiqueta() {
        String descLote="";
        if(listaLotesSeleccionados.size() > 0){
            if(listaLotesSeleccionados.size() > 1)
                descLote = listaLotesSeleccionados.get(0).getNumLote()+"-"+listaLotesSeleccionados.get(
                    listaLotesSeleccionados.size()-1).getNumLote();
            else descLote = listaLotesSeleccionados.get(0).getNumLote()+"-";
            descLote += new SimpleDateFormat("ddMMYY").format(new Date());
            descLote+=listaLotesSeleccionados.get(0).getDescMaquina();
                vista.getLbCodigoEtiqueta().setText(descLote);   
        }
    }

    
    
    private int sumarCantidadesLotes() {
        int suma = 0;
        for(int i = 0;i<listaLotesSeleccionados.size();i++)
            suma += listaLotesSeleccionados.get(i).getCantidadAdmin();
        return suma;    
    }  
    
    private final ActionListener listenerImprimir = new ActionListener() {
       
        
        @Override
        public void actionPerformed(ActionEvent e) {  
            r.run();
        }
        
        
        private final Runnable r = new Runnable() {
            @Override
            public void run() {
                    try {
                        
                    if(Integer.parseInt(vista.getSprPiezasPorBolsa().getValue().toString()) > 0){
                            int respusta = JOptionPane.showConfirmDialog(null, "¿SEGURO DE IMRPIMIR ESTAS ETIQUETAS?","VALIDACION",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                            
                    if(respusta == JOptionPane.YES_OPTION){
                            int numEtiqueta = 1;
                            DesktopNotify.showDesktopMessage("PROCESANDO", "LAS ETIQUETAS ESTAN EN PROCESO, ESPERE POR FAVOR", 
                                    DesktopNotify.SUCCESS, 10000);
                            
                            numEtiqueta = modelo.guardarEtiqueta(vista.getTxtFolio().getText(),
                                    Integer.parseInt(vista.getSprTotalPiezas().getValue().toString()),
                                    Integer.parseInt(vista.getSprPiezasPorBolsa().getValue().toString()),
                                    vista.getLbCodigoEtiqueta().getText());
                            
                            if(numEtiqueta > 0){
                                try {
                                    
                                    for(int i = 0;i<listaLotesSeleccionados.size();i++)
                                    modelo.agregarEtiquetaLoteProduccion(numEtiqueta, listaLotesSeleccionados.get(i).getDescLote() );
                                    registrarEtiqueta(Integer.parseInt(vista.getSprPiezasPorBolsa().getValue().toString()),
                                    Integer.parseInt(vista.getSprTotalPiezas().getValue().toString()),
                                    numEtiqueta);
                                   
                                    generarEtiquetas(Integer.parseInt(vista.getSprPiezasPorBolsa().getValue().toString()),
                                            Integer.parseInt(vista.getSprTotalPiezas().getValue().toString()),vista.getTxtFolio().getText(),vista.getLbCodigoEtiqueta().getText(),
                                            vista.getCbxLogo().getSelectedItem().toString(),listaLotesSeleccionados.get(0).getDescProducto(),
                                            listaLotesSeleccionados.get(0).getCodProducto(),listaLotesSeleccionados.get(0).getDescCliente(),listaLotesSeleccionados.get(0).getNoOrdenCompra());
                                    
                                } catch (IOException | DocumentException ex) {
                                    Logger.getLogger(ImpresionEtiquetasController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }

                    }else JOptionPane.showMessageDialog(null,"NO SE HA PODIDO IMPRIMIR LAS ETIQUETAS DEBIDO A QUE HAY UN ERROR EN SUS PARAMETROS,"
                            + "cOMPRUEBE SUS CAMPOS POR FAVOR");   
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"NO SE HA PODIDO IMPRIMIR LAS ETIQUETAS DEBIDO A QUE HAY UN ERROR EN SUS PARAMETROS,"
                            + "cOMPRUEBE SUS CAMPOS POR FAVOR");   
                }
            }
        };

        private void registrarEtiqueta(int piezasPorBolsa, int piezasTotales,int noEtiqueta) {
            int num = 1;
            while(piezasTotales > piezasPorBolsa){
                modelo.registrarEtiquetaLote(noEtiqueta,num, piezasPorBolsa);
                piezasTotales -= piezasPorBolsa;
                num ++;
            }
            
            if(piezasTotales > 0)
                modelo.registrarEtiquetaLote(noEtiqueta,num, piezasTotales);
            
            JOptionPane.showMessageDialog(null, " LAS ETIQUETAS SE HAN REGISTRADO CORRECTAMENTE,"
                                + " AHORA PASARAN A SU IMPRESION","CONFIRMACION",JOptionPane.INFORMATION_MESSAGE);
        }

        private void generarEtiquetas(int piezasPorBolsa, int piezasTotales, String descFolio
                , String codEtiquetas, String descLote,String descProducto,String codProducto,String descCliente,
                String noOrdenCompra) throws IOException, DocumentException {
            
            CreacionPDF etiquetas = new CreacionPDF(CreacionPDF.DOS_ETIQUETAS_PAPEL_15X10,
                    CreacionPDF.ESCALA_ETIQUETA_15X10);
            
            int  logoSeleccionado = (vista.getCbxLogo().getSelectedItem().equals("MMT"))? CreacionPDF.LOGO_MMT:CreacionPDF.LOGO_GMC;
            
            while(piezasTotales > piezasPorBolsa){
                
                etiquetas.agregarEtiqueta(descCliente, descProducto+"",
                        piezasPorBolsa+"",codProducto,descFolio,codEtiquetas,noOrdenCompra,logoSeleccionado);
                System.err.println(piezasTotales);
                piezasTotales -= piezasPorBolsa;
                
            }
            
            if(piezasTotales > 0)
                etiquetas.agregarEtiqueta(descCliente, descProducto+"",
                        piezasTotales+"",codProducto,descFolio,codEtiquetas,noOrdenCompra,logoSeleccionado);
            
            etiquetas.terminarPDF();
            etiquetas.imprimirEtiquetas();
        }        
        
    };
   
}
