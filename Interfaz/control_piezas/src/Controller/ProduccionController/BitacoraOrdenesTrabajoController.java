/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ProduccionController;

import Model.Estructuras;
import Model.ProduccionModel.BitacoraOrdenesTrabajoModel;
import Model.ProduccionModel.RegistroOrdenTrabajo;
import View.Produccion.BitacoraOrdenesTrabajoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cesar
 */
public class BitacoraOrdenesTrabajoController {

    private final BitacoraOrdenesTrabajoView bitacoraTrabajosView;
    private final BitacoraOrdenesTrabajoModel bitacoraTrabajosModel;
    private ArrayList<RegistroOrdenTrabajo> ordenesTrabajo;
    int noOrden=0;
    private final MouseAdapter clickTabla = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            int fila  = bitacoraTrabajosView.getTbOrdenesTrabajo().rowAtPoint(e.getPoint());
            noOrden = (int)bitacoraTrabajosView.getTbOrdenesTrabajo().getValueAt(fila, 0);
            bitacoraTrabajosView.getLbNoOrdenTrabajo().setText(noOrden+"");
            bitacoraTrabajosView.getTxtaObservacionGuardada().setText(obtenerObservacion(noOrden));
            bitacoraTrabajosView.getAtxtObservacion().setText(obtenerObservacion(noOrden));
            
        }
    };
    
    private final ActionListener botonGuardar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {       
            if(noOrden != 0){
                bitacoraTrabajosModel.guardarObservacion(bitacoraTrabajosView.getAtxtObservacion().getText(),noOrden);
                bitacoraTrabajosView.getAtxtObservacion().setText("");
            }
            else
                JOptionPane.showMessageDialog(null,"SELECCIONA UNA ORDEN DE TRABAJO");
            
            llenarTablaOrdenesTrabajo(bitacoraTrabajosView.getYcrAnio().getValue());
        }
        
        
    };
    
    private final ActionListener botonBuscar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
             bitacoraTrabajosView.getLbAnio().setText(bitacoraTrabajosView.getYcrAnio().getValue()+"");
             llenarTablaOrdenesTrabajo(bitacoraTrabajosView.getYcrAnio().getValue());
        }
    };
    
    private String obtenerObservacion(int noOrden){
        String observacion = bitacoraTrabajosModel.obtenerObservacion(noOrden);
        return observacion;
    }
    
    public BitacoraOrdenesTrabajoController(BitacoraOrdenesTrabajoView bitacoraTrabajosView, BitacoraOrdenesTrabajoModel bitacoratrabajosModel) {
        this.bitacoraTrabajosView = bitacoraTrabajosView;
        this.bitacoraTrabajosModel = bitacoratrabajosModel;
        llenarTablaOrdenesTrabajo(this.bitacoraTrabajosView.getYcrAnio().getValue());
        this.bitacoraTrabajosView.getTbOrdenesTrabajo().addMouseListener(clickTabla);
        this.bitacoraTrabajosView.getBtnGuardarObservacion().addActionListener(botonGuardar);
        this.bitacoraTrabajosView.getLbAnio().setText(bitacoraTrabajosView.getYcrAnio().getValue()+"");
        this.bitacoraTrabajosView.getBtnBuscar().addActionListener(botonBuscar);
    }
    
    private void llenarTablaOrdenesTrabajo(int anio){
            ordenesTrabajo = bitacoraTrabajosModel.listaOrdenesTrabajo(anio);
            DefaultTableModel modelOrdenesTrabajo = (DefaultTableModel) bitacoraTrabajosView.getTbOrdenesTrabajo().getModel();        
            Estructuras.limpiarTabla(modelOrdenesTrabajo);
            if(ordenesTrabajo.size()>0)
                for(int i = 0;i<ordenesTrabajo.size();i++){
                    RegistroOrdenTrabajo ordenTrabajo = ordenesTrabajo.get(i);
                    modelOrdenesTrabajo.addRow(new Object[]{
                        ordenTrabajo.getNoOrdenProduccion(),
                        ordenTrabajo.getFechaRegistro(),
                        ordenTrabajo.getClaveProducto(),
                        ordenTrabajo.getCantidadCliente(),
                        ordenTrabajo.getFechaInicio(),
                        ordenTrabajo.getFechaFin(),
                        ordenTrabajo.getNoPedido(),
                        ordenTrabajo.getFechaEntrega(),
                        ordenTrabajo.getDescEstados()
                    });
                }
    }   
}
