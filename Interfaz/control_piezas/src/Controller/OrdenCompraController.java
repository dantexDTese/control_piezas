/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Estructuras;
import Model.ordenCompraModel;
import Model.ordenProduccionModel;
import Model.ordenProducto;
import View.OrdenCompra;
import View.OrdenesProduccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class OrdenCompraController implements ActionListener{
    
    
    private OrdenCompra vista;
    private ordenCompraModel modelo;
    private OrdenesProduccion vistaOrdenesProduccion;
    private ArrayList <ordenProducto> productos;
    
    public OrdenCompraController(OrdenCompra vista,ordenCompraModel modelo){
        this.vista = vista;
        this.modelo = modelo;
        llenarCombos();
        this.vista.getSelecCliente().addActionListener(this);
        this.vista.getAddCliente().addActionListener(this);
        this.vista.getAddOrdenCompra().addActionListener(this);
        this.vista.getAddProducto().addActionListener(this);
        this.productos = new ArrayList<>();
    }
    
    private void llenarCombos(){
        this.vista.setSelecCliente(Estructuras.llenaCombo(this.vista.getSelecCliente(),ordenCompraModel.QUERY_COMBO_CLIENTE));
    }
    
    public void agregarProducto(ordenProducto producto){
        productos.add(producto);
        
        
        DefaultTableModel model = (DefaultTableModel) vista.getTablaProductos().getModel();
        model.addRow(new Object[]{producto.getCodProducto(),producto.getDescMaquina(),producto.getDescMateria(),producto.getFecha(),
        producto.getCantidadSolicitada(),producto.getCantidadProducir(),producto.getCantidadPorTurno()});
        vista.getTablaProductos().setModel(model);        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == vista.getSelecCliente())
            vista.setSelecOrdenCompra(modelo.llenarComboOrdenCompra(vista.getSelecOrdenCompra()
                    ,vista.getSelecCliente().getSelectedItem().toString()));
        
        else if(e.getSource() == vista.getAddCliente())
            agregarCliente();
        
        else if(e.getSource() == vista.getAddOrdenCompra())
            agregarOrdenCompra(vista.getSelecCliente().getSelectedItem().toString());
        
        else if(e.getSource() == vista.getAddProducto())
            agregarProducto();   
    }
    
    private void agregarProducto(){
           vistaOrdenesProduccion = new OrdenesProduccion(this.vista.getPrincipal(), true);
           ordenProduccionModel modelo = new ordenProduccionModel();
           ordenProduccionController controller = new ordenProduccionController(vistaOrdenesProduccion, modelo,this);
           vistaOrdenesProduccion.setVisible(true);
    }
    
    
    private void agregarCliente(){
        String nombreCliente = JOptionPane.
                showInputDialog("¿Cual es el nombre de tu cliente?");
        
        if(nombreCliente != null && !"".equals(nombreCliente)){
            modelo.insertarCliente(nombreCliente);
            llenarCombos();
        }
        else
            JOptionPane.showMessageDialog(null, "complete el campo para agregar");
        
        
    }
    
    private void agregarOrdenCompra(String nombreCliente){
           String descOrdenCompra = JOptionPane.
                showInputDialog("¿una descripcion de la orden de compra?");
        if(nombreCliente != null && !"".equals(nombreCliente)){
            if(descOrdenCompra != null && !"".equals(descOrdenCompra)){
                modelo.insertarOrdenCompra(descOrdenCompra,nombreCliente);
                vista.setSelecOrdenCompra(modelo.llenarComboOrdenCompra(vista.getSelecOrdenCompra()
                    ,vista.getSelecCliente().getSelectedItem().toString()));
            }
            else
                JOptionPane.showMessageDialog(null, "complete el campo para agregar");
        }
        else
                JOptionPane.showMessageDialog(null, "seleccione uno de sus clientes");
        
    }
    
    
}
