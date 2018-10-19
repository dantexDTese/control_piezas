/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AlmacenController.MateriaPrimaController;
import Controller.AlmacenController.ProductoTerminadoController;
import Controller.RequisicionesController.controlEntregasController;
import Controller.OrdenCompraController;
import Controller.PedidosController.BitacoraPedidosClienteController;
import Controller.PedidosController.PlaneacionController;
import Controller.ProduccionController.BitacoraOrdenesTrabajoController;
import Controller.RequisicionesController.AgregarRequisicinesController;
import Controller.SeguimientoOrdenesController;
import Controller.SeguimientoProductosController;
import Model.AlmacenModel.MateriaPrimaModel;
import Model.AlmacenModel.ProductoTerminadoModel;
import Model.RequisicionesModel.controlEntregasModel;
import Model.PedidosModel.BitacoraPedidosClienteModel;
import Model.PedidosModel.PlaneacionModel;
import Model.ProduccionModel.BitacoraOrdenesTrabajoModel;
import Model.RequisicionesModel.AgregarRequisicionesModel;
import Model.SeguimientoOrdenesModel;
import Model.SeguimientoProductosModel;
import Model.ordenCompraModel;
import View.Requisiciones.ControlEntregasView;
import View.Pedidos.BitacoraPedidosClienteView;
import View.Pedidos.PlaneacionView;
import View.Produccion.BitacoraOrdenesTrabajoView;
import View.Requisiciones.AgregarRequisiciones;
import View.almacenView.MateriaPrimaView;
import View.almacenView.ProductoTerminadoView;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.DesktopNotifyDriver;
import ds.desktop.notify.NotifyTheme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author cesar
 */
public class Principal extends javax.swing.JFrame {

        
    Dimension screenSize;
    
    public Principal() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH); 
        Toolkit t = Toolkit.getDefaultToolkit();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       
                
        
    }
    
    private class fondo extends JPanel{
        
        private Image imagen;

        private fondo() {
            
        }
        public void paintComponents(Graphics g){
            super.paintComponents(g);
            
            File file = new File("src/img/fondo.png");
            try {
                imagen = ImageIO.read(file);
                g.drawImage(imagen,500,500, null);
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }


    private void IntroducirPanle(JInternalFrame pnNuevo,JDesktopPane escritorio,Point punto) { 
            Dimension d = pnNuevo.getPreferredSize();
            pnNuevo.setSize(d);
            pnNuevo.setLocation(punto.x-pnNuevo.getWidth()/2,
                    punto.y-pnNuevo.getHeight()/2);
            escritorio.add(pnNuevo);
            escritorio.revalidate();
            escritorio.repaint();
            pnNuevo.show();
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        Escritorio = new javax.swing.JDesktopPane();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuItemBitacoraOrdenesTrabajo = new javax.swing.JMenuItem();
        subMenuOrdenesProduccion = new javax.swing.JMenu();
        itemMenuNuevaOrden = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuItemMateriaPrima = new javax.swing.JMenuItem();
        menuItemProductoTerminado = new javax.swing.JMenuItem();
        menuPedidos = new javax.swing.JMenu();
        itemMenuBitacoraPedidosCliente = new javax.swing.JMenuItem();
        itemMenuPlaneacion = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        menuItemNuevasRequisiciones = new javax.swing.JMenuItem();
        menuItemControlEntregaMP = new javax.swing.JMenuItem();
        menuItemAdminRequisiciones = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu6.setText("jMenu6");

        jMenu8.setText("jMenu8");

        jMenu9.setText("jMenu9");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        Escritorio.setBackground(new java.awt.Color(86, 9, 12));

        javax.swing.GroupLayout EscritorioLayout = new javax.swing.GroupLayout(Escritorio);
        Escritorio.setLayout(EscritorioLayout);
        EscritorioLayout.setHorizontalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 888, Short.MAX_VALUE)
        );
        EscritorioLayout.setVerticalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 732, Short.MAX_VALUE)
        );

        jMenu3.setText("Archivo                    ");

        jMenuItem1.setText("Salir");
        jMenu3.add(jMenuItem1);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Producciòn                             ");

        menuItemBitacoraOrdenesTrabajo.setText("Bitacora de ordenes de trabajo");
        menuItemBitacoraOrdenesTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBitacoraOrdenesTrabajoActionPerformed(evt);
            }
        });
        jMenu4.add(menuItemBitacoraOrdenesTrabajo);

        subMenuOrdenesProduccion.setText("Ordenes de produccion");

        itemMenuNuevaOrden.setText("Nueva orden");
        itemMenuNuevaOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuNuevaOrdenActionPerformed(evt);
            }
        });
        subMenuOrdenesProduccion.add(itemMenuNuevaOrden);

        jMenuItem2.setText("Administrar ordenes");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        subMenuOrdenesProduccion.add(jMenuItem2);

        jMenu4.add(subMenuOrdenesProduccion);

        jMenu7.setText("Seguimiento de produccion");

        jMenuItem3.setText("Seguimiento diario");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem3);

        jMenu4.add(jMenu7);

        jMenuBar2.add(jMenu4);

        jMenu5.setText("Almacen                               ");

        menuItemMateriaPrima.setText("MATERIA PRIMA");
        menuItemMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMateriaPrimaActionPerformed(evt);
            }
        });
        jMenu5.add(menuItemMateriaPrima);

        menuItemProductoTerminado.setText("PRODUCTO TERMINADO");
        menuItemProductoTerminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProductoTerminadoActionPerformed(evt);
            }
        });
        jMenu5.add(menuItemProductoTerminado);

        jMenuBar2.add(jMenu5);

        menuPedidos.setText("Pedidos                        ");

        itemMenuBitacoraPedidosCliente.setText("bitacora de pedidos clientes");
        itemMenuBitacoraPedidosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuBitacoraPedidosClienteActionPerformed(evt);
            }
        });
        menuPedidos.add(itemMenuBitacoraPedidosCliente);

        itemMenuPlaneacion.setText("Planeacion");
        itemMenuPlaneacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuPlaneacionActionPerformed(evt);
            }
        });
        menuPedidos.add(itemMenuPlaneacion);

        jMenuBar2.add(menuPedidos);

        jMenu10.setText("Requisicion de materiales            ");

        menuItemNuevasRequisiciones.setText("Nuevas requisiciones");
        menuItemNuevasRequisiciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevasRequisicionesActionPerformed(evt);
            }
        });
        jMenu10.add(menuItemNuevasRequisiciones);

        menuItemControlEntregaMP.setText("Control de entrega de MP");
        menuItemControlEntregaMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemControlEntregaMPActionPerformed(evt);
            }
        });
        jMenu10.add(menuItemControlEntregaMP);

        menuItemAdminRequisiciones.setText("Admin. Requisiciones");
        jMenu10.add(menuItemAdminRequisiciones);

        jMenuBar2.add(jMenu10);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Escritorio)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public JDesktopPane getEscritorio() {
        return Escritorio;
    }
    
    private void itemMenuNuevaOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuNuevaOrdenActionPerformed
        Escritorio.removeAll();
        ordenCompraModel modelo = new ordenCompraModel();
        OrdenCompra vista = new OrdenCompra(this);
        OrdenCompraController control = new OrdenCompraController(vista,modelo);
        IntroducirPanle(vista, Escritorio,new Point( screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_itemMenuNuevaOrdenActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Escritorio.removeAll();
        IntroducirPanle(new AdminProduccion(), Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Escritorio.removeAll();
        
        SeguimientoProductosModel modeloOrdenesProductos = new SeguimientoProductosModel();
        SeguimientoProductos vistaOrdenesProductos = new SeguimientoProductos();
        SeguimientoProductosController controllerOrdenesProductos = new SeguimientoProductosController(vistaOrdenesProductos,modeloOrdenesProductos);
        
        SeguimientoOrdenesModel modeloOrdenesTrabajo = new SeguimientoOrdenesModel();
        SeguimientoOrdenes vistaOrdenesTrabajo = new SeguimientoOrdenes();      
        SeguimientoOrdenesController controlloerOrdenesTrabajo = new SeguimientoOrdenesController(vistaOrdenesTrabajo
                ,modeloOrdenesTrabajo
                ,controllerOrdenesProductos);
        
        IntroducirPanle(vistaOrdenesTrabajo, Escritorio,new Point(screenSize.width/7,screenSize.height/2));
        IntroducirPanle(vistaOrdenesProductos, Escritorio,new Point(screenSize.width/2+200,screenSize.height/2));
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void itemMenuBitacoraPedidosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuBitacoraPedidosClienteActionPerformed
        BitacoraPedidosClienteView vista = new BitacoraPedidosClienteView(this);
        BitacoraPedidosClienteModel model = new BitacoraPedidosClienteModel();
        BitacoraPedidosClienteController controler = new BitacoraPedidosClienteController(vista,model);
        Escritorio.removeAll();
        IntroducirPanle(vista, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_itemMenuBitacoraPedidosClienteActionPerformed

    private void itemMenuPlaneacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuPlaneacionActionPerformed
        PlaneacionView vista = new PlaneacionView(this);
        PlaneacionModel model = new PlaneacionModel();
        PlaneacionController controller = new PlaneacionController(vista,model);
        Escritorio.removeAll();
        IntroducirPanle(vista, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_itemMenuPlaneacionActionPerformed

    private void menuItemBitacoraOrdenesTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBitacoraOrdenesTrabajoActionPerformed
        BitacoraOrdenesTrabajoView bitacoraTrabajosView = new BitacoraOrdenesTrabajoView(this);
        BitacoraOrdenesTrabajoModel bitacoratrabajosModel = new BitacoraOrdenesTrabajoModel();
        BitacoraOrdenesTrabajoController bitacoraTrabajosController = new BitacoraOrdenesTrabajoController(bitacoraTrabajosView,bitacoratrabajosModel);
        Escritorio.removeAll();
        IntroducirPanle(bitacoraTrabajosView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
        
    }//GEN-LAST:event_menuItemBitacoraOrdenesTrabajoActionPerformed

    private void menuItemMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMateriaPrimaActionPerformed
        MateriaPrimaView mPrimaView  = new MateriaPrimaView(this);
        MateriaPrimaModel mPrimaModel = new MateriaPrimaModel();
        MateriaPrimaController mPrimaController = new MateriaPrimaController(mPrimaView,mPrimaModel);
        Escritorio.removeAll();
        IntroducirPanle(mPrimaView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemMateriaPrimaActionPerformed

    private void menuItemProductoTerminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProductoTerminadoActionPerformed
        ProductoTerminadoView pTerminadoView = new ProductoTerminadoView(this);
        ProductoTerminadoModel pTerminadoModel = new ProductoTerminadoModel();
        ProductoTerminadoController pTerminadoController = new ProductoTerminadoController(pTerminadoView,pTerminadoModel);
        Escritorio.removeAll();
        IntroducirPanle(pTerminadoView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemProductoTerminadoActionPerformed

    private void menuItemNuevasRequisicionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevasRequisicionesActionPerformed
        AgregarRequisiciones AgregarRequisicionView = new AgregarRequisiciones(this);
        AgregarRequisicinesController controller = new AgregarRequisicinesController(AgregarRequisicionView,new AgregarRequisicionesModel());
        Escritorio.removeAll();
        IntroducirPanle(AgregarRequisicionView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemNuevasRequisicionesActionPerformed

    private void menuItemControlEntregaMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemControlEntregaMPActionPerformed
        ControlEntregasView controlEntrasView = new ControlEntregasView(this);
        controlEntregasController entregasController = new controlEntregasController(controlEntrasView,new controlEntregasModel());
        Escritorio.removeAll();
        IntroducirPanle(controlEntrasView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
            
    }//GEN-LAST:event_menuItemControlEntregaMPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {         
                Principal p = new Principal();
                p.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane Escritorio;
    private javax.swing.JMenuItem itemMenuBitacoraPedidosCliente;
    private javax.swing.JMenuItem itemMenuNuevaOrden;
    private javax.swing.JMenuItem itemMenuPlaneacion;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem menuItemAdminRequisiciones;
    private javax.swing.JMenuItem menuItemBitacoraOrdenesTrabajo;
    private javax.swing.JMenuItem menuItemControlEntregaMP;
    private javax.swing.JMenuItem menuItemMateriaPrima;
    private javax.swing.JMenuItem menuItemNuevasRequisiciones;
    private javax.swing.JMenuItem menuItemProductoTerminado;
    private javax.swing.JMenu menuPedidos;
    private javax.swing.JMenu subMenuOrdenesProduccion;
    // End of variables declaration//GEN-END:variables
}
