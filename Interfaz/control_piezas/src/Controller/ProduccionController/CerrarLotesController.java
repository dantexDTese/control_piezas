
package Controller.ProduccionController;

import Model.Constructores;
import Model.Estructuras;
import Model.LotePlaneado;
import Model.ProduccionModel.CerrarLotesModel;
import Model.ProduccionModel.LoteProduccion;
import View.Produccion.CerrarLotes;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class CerrarLotesController implements Constructores{    
    private CerrarLotes vista;
    private CerrarLotesModel modelo;
    private LotePlaneado ordenSeleccionada;
    private ArrayList<LoteProduccion> listaLOtes;
    CerrarLotesController(CerrarLotes vista, CerrarLotesModel modelo, LotePlaneado ordenSeleccionada) {
        this.vista = vista;
        this.modelo = modelo;
        this.ordenSeleccionada = ordenSeleccionada;
        llenarComponentes();
        asignarEventos();
    }

    @Override
    public void llenarComponentes() {
        llenarTablaLotes();
        
    }

    @Override
    public void asignarEventos(){
        this.vista.getJtbLotesSinCerrar().addMouseListener(listenerCerrarLote);
    }
    
    private void llenarTablaLotes(){
       listaLOtes = modelo.listaLotesNoCerrados(ordenSeleccionada.getNoOrdenProduccion());
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getJtbLotesSinCerrar().getModel();
        Estructuras.limpiarTabla(modeloTabla);
        for(int i = 0;i<listaLOtes.size();i++)
            modeloTabla.addRow(new Object[]{
                i+1,
                listaLOtes.get(i).getFechaPlaneada(),
                listaLOtes.get(i).getDescLote()
            });
    }
    
    private final MouseListener listenerCerrarLote = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e){
            super.mousePressed(e);
            if(e.getClickCount() == 2){
                if(JOptionPane.showConfirmDialog(null, "¿QUIERES CERRAR ESTE LOTE DE PRODUCCION?",
                    "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    int fila = vista.getJtbLotesSinCerrar().rowAtPoint(e.getPoint());
                    int lotePlaneado = obtenerNoLOtePlaneado(vista.getJtbLotesSinCerrar().getValueAt(fila, 2).toString());
                    modelo.cerrarLote(lotePlaneado);
                    llenarTablaLotes();
                }
            }
        }

        private int obtenerNoLOtePlaneado(String descLote){
            
            for(int i = 0;i<listaLOtes.size();i++)
                if(listaLOtes.get(i).getDescLote().equals(descLote))
                    return listaLOtes.get(i).getNoLotePlaneado();
            
            return 0;
        }
    };
    
}
