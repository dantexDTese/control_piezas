
package Controller.EtiquetasController;

import Model.Constructores;
import Model.Estructuras;
import Model.EtiquetasModel.Etiqueta;
import Model.EtiquetasModel.EtiquetaLote;
import Model.EtiquetasModel.SeguimientoLotesModel;
import Model.ordenProduccion;
import View.Etiquetas.SeguimientoLotes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public final class SeguimientoLotesController implements Constructores{

    private final SeguimientoLotes view;
    private final SeguimientoLotesModel model;
    
    
    public SeguimientoLotesController(SeguimientoLotes view, SeguimientoLotesModel model) {
        this.view = view;
        this.model = model;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        
    }

    @Override
    public void asignarEventos() {
        view.getBtnBuscar().addActionListener(listenerBuscar);
    }
    
    
    private final ActionListener listenerBuscar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            ordenProduccion pedidoLote;
            Etiqueta etiquetaPedido;
            ArrayList<EtiquetaLote> listaEtiquetas;
            if((pedidoLote = model.pedidoLote(view.getTxtCodigoLote().getText()) ) != null){
                view.getLbCodigoEtiqueta().setText(view.getTxtCodigoLote().getText());
                llenarComponentesPedido(pedidoLote);
                etiquetaPedido = model.etiquetaLote(view.getTxtCodigoLote().getText());
                llenarComponentesEtiqueta(etiquetaPedido);   
                listaEtiquetas = model.listaEtiquetasLote(view.getTxtCodigoLote().getText());
                llenarTablaEtiquetas(listaEtiquetas);  
                view.getLbNumeroBolsas().setText(listaEtiquetas.size()+"");
                view.getTxtCodigoLote().setText("");
                
            }
            
        }

        private void llenarComponentesPedido(ordenProduccion pedidoLote){
            view.getLbNumeroPedido().setText(pedidoLote.getNoPedido()+"");
            view.getLbOrdenCompra().setText(pedidoLote.getNoOrdenCompra());
            view.getLbFechaRecepcion().setText(pedidoLote.getFechaRecepcion());
            view.getLbFechaEntrega().setText(pedidoLote.getFechaentrega());
            view.getLbCliente().setText(pedidoLote.getDescCliente());
            view.getLbContacto().setText(pedidoLote.getDescContacto());
            view.getLbClaveProducto().setText(pedidoLote.getCodProducto());
            view.getLbDescripcionProducto().setText(pedidoLote.getDescProducto());
        }

        private void llenarComponentesEtiqueta(Etiqueta etiquetaPedido) {
            view.getLbFolio().setText(etiquetaPedido.getFolio());
            view.getLbPiezasPorBolsa().setText(etiquetaPedido.getPiezasPorBolsa()+"");
            view.getLbTotalPiezas().setText(etiquetaPedido.getPiezasTotales()+"");
        }

        private void llenarTablaEtiquetas(ArrayList<EtiquetaLote> listaEtiquetas) {
            DefaultTableModel modeloTabla = (DefaultTableModel) view.getJtbLotesProduccion().getModel();
            Estructuras.limpiarTabla(modeloTabla);
            for(int i = 0;i<listaEtiquetas.size();i++){
                modeloTabla.addRow(new Object[]{
                    i+1,
                    listaEtiquetas.get(i).getCantidad()
                });
            }
        }
        
        
        
    };
    
}
