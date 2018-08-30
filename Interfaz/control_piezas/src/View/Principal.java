/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.OrdenCompraController;
import Controller.PedidosController.BitacoraPedidosClienteController;
import Controller.SeguimientoOrdenesController;
import Controller.SeguimientoProductosController;
import Model.PedidosModel.BitacoraPedidosClienteModel;
import Model.SeguimientoOrdenesModel;
import Model.SeguimientoProductosModel;
import Model.ordenCompraModel;
import View.Pedidos.BitacoraPedidosClienteView;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.DesktopNotifyDriver;
import ds.desktop.notify.NotifyTheme;
import java.awt.Color;
import java.awt.Dimension;
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
        Escritorio = new javax.swing.JDesktopPane();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        subMenuOrdenesProduccion = new javax.swing.JMenu();
        itemMenuNuevaOrden = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuPedidos = new javax.swing.JMenu();
        itemMenuBitacoraPedidosCliente = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        Escritorio.setBackground(new java.awt.Color(0, 0, 51));

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
        jMenuBar2.add(jMenu5);

        menuPedidos.setText("Pedidos");

        itemMenuBitacoraPedidosCliente.setText("bitacora de pedidos clientes");
        itemMenuBitacoraPedidosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuBitacoraPedidosClienteActionPerformed(evt);
            }
        });
        menuPedidos.add(itemMenuBitacoraPedidosCliente);

        jMenuBar2.add(menuPedidos);

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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenu menuPedidos;
    private javax.swing.JMenu subMenuOrdenesProduccion;
    // End of variables declaration//GEN-END:variables
}
