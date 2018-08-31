
package Controller.PedidosController;

import Model.PedidosModel.BitacoraPedidosClienteModel;
import Model.PedidosModel.nuevoPedidoClienteModel;
import View.Pedidos.BitacoraPedidosClienteView;
import View.Pedidos.NuevoPedidoCliente;
import ds.desktop.notify.DesktopNotify;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BitacoraPedidosClienteController implements ActionListener{

    BitacoraPedidosClienteView vista;
    BitacoraPedidosClienteModel model;
    
    public BitacoraPedidosClienteController(BitacoraPedidosClienteView vista, BitacoraPedidosClienteModel model) {
        this.vista = vista;
        this.model = model;       
        
        vista.getBtnNuevaOrden().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBtnNuevaOrden())
            agregaNuevaOrden();
    }
    
    private void agregaNuevaOrden(){
        nuevoPedidoClienteModel modelNuevoPedido = new nuevoPedidoClienteModel();
        NuevoPedidoCliente vistaNuevoPedido = new NuevoPedidoCliente(vista.getPrincipal(), true);
        nuevoPedidoClienteController controllerNuevoPedido = new nuevoPedidoClienteController(vistaNuevoPedido,modelNuevoPedido);
        
        vistaNuevoPedido.setVisible(true);       
    }
    
    
    
}
