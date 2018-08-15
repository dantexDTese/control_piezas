/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Estructuras;
import Model.ordenCompraModel;
import View.OrdenCompra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author cesar
 */
public class OrdenCompraController implements ActionListener{
    
    
    private OrdenCompra vista;
    
    private ordenCompraModel modelo;
    
    
    public OrdenCompraController(OrdenCompra vista,ordenCompraModel modelo){
        this.vista = vista;
        this.modelo = modelo;
        llenarCombos();
        this.vista.getSelecCliente().addActionListener(this);
        this.vista.getAddCliente().addActionListener(this);
        this.vista.getAddOrdenCompra().addActionListener(this);
        this.vista.getAddProducto().addActionListener(this);
    }
    
    private void llenarCombos(){
        this.vista.setSelecCliente(Estructuras.llenaCombo(this.vista.getSelecCliente(),ordenCompraModel.QUERY_COMBO_CLIENTE));
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
