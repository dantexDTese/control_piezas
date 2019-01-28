
package Controller.ProduccionController;

import Model.Constructores;
import Model.LotePlaneado;
import Model.ProduccionModel.CompletarRegistroProduccionModel;
import Model.ProduccionModel.ControlProduccionModel;
import Model.ProduccionModel.LoteProduccion;
import View.Produccion.CompletarRegistroProduccionView;
import View.Produccion.ControlProduccionDialogView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;



public final class ControlProduccionController implements Constructores{

    private final ControlProduccionDialogView controlProduccionView;
    private final ControlProduccionModel controlProduccionModel;
    private final LotePlaneado ordenSeleccionada;
    ArrayList<LoteProduccion> lotesProducidos;
    private LoteProduccion loteSeleccionado;
    
    public ControlProduccionController(ControlProduccionDialogView controlProduccionView,
            ControlProduccionModel controlProduccionModel,LotePlaneado ordenSeleccionada) {
        
        this.controlProduccionView = controlProduccionView;
        this.controlProduccionModel = controlProduccionModel;
        this.ordenSeleccionada = ordenSeleccionada;
        llenarComponentes();
        asignarEventos();
        
    }
    
    
    @Override
    public void llenarComponentes() {
        llenarDescripcionLote();
    }

    @Override
    public void asignarEventos() {
        controlProduccionView.getJtbConntrolProduccion().addMouseListener(listenerTablaLotes);
        
    }
     
     private void llenarDescripcionLote(){
         lotesProducidos = controlProduccionModel.obtenerListaLotes(ordenSeleccionada.getNoOrdenProduccion());
         DefaultTableModel modeloTabla = (DefaultTableModel) controlProduccionView.getJtbConntrolProduccion().getModel();
         
         for(int i = 0;i<lotesProducidos.size();i++){
             LoteProduccion lote = lotesProducidos.get(i);
             modeloTabla.addRow(new Object[]{
                 lote.getRechazo(),
                 lote.getDescLote(),
                 lote.getCodProducto(),
                 lote.getTipoProceso(),
                 lote.getBarrasUtilizadas(),
                 lote.getMerma(),
                 lote.getCantidadAdmin(),
                 lote.getCantidadRechazoLiberado(),
                 lote.getScrapAjustable(),
                 lote.getScrapAdmin(),
                 lote.getTiempoMuerto()
             });
         
         }
         
     }

    private final MouseListener listenerTablaLotes = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            int fila = controlProduccionView.getJtbConntrolProduccion().rowAtPoint(e.getPoint());
            loteSeleccionado = obtenerLoteSeleccionado(fila);
            CompletarRegistroProduccionView vista = new CompletarRegistroProduccionView(controlProduccionView.getParent(), true);
            CompletarRegistroProduccionController controller = new CompletarRegistroProduccionController(
            vista,new CompletarRegistroProduccionModel(),loteSeleccionado);
            vista.setVisible(true);
        }

        private LoteProduccion obtenerLoteSeleccionado(int fila) {
            String descLote = controlProduccionView.getJtbConntrolProduccion().getValueAt(fila, 1).toString();
            for(int i = 0;i<lotesProducidos.size();i++)
                if(descLote.equals(lotesProducidos.get(i).getDescLote()))
                    return lotesProducidos.get(i);
            
            return null;
        }
        
    };
    
}
