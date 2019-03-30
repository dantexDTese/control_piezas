package View;

import View.Produccion.AdminProduccionView;
import Controller.AlmacenController.MateriaPrimaController;
import Controller.AlmacenController.ProductoTerminadoController;
import Controller.CatalogosController.CatalogoClientesController;
import Controller.CatalogosController.CatalogoMaquinasController;
import Controller.CatalogosController.CatalogoMaterialesController;
import Controller.CatalogosController.CatalogoOperadoresController;
import Controller.CatalogosController.CatalogoProductosController;
import Controller.CatalogosController.CatalogoProveedoreController;
import Controller.EtiquetasController.ImpresionEtiquetasController;
import Controller.EtiquetasController.SeguimientoLotesController;
import Controller.RequisicionesController.ControlEntregasController;
import Controller.PedidosController.BitacoraPedidosClienteController;
import Controller.PedidosController.PlaneacionController;
import Controller.ProduccionController.AdminProduccionController;
import Controller.ProduccionController.BitacoraOrdenesTrabajoController;
import Controller.RequisicionesController.RecepcionRequisicionController;
import Controller.RequisicionesController.RegistroEntradaMaterialesController;
import Model.AlmacenModel.MateriaPrimaModel;
import Model.AlmacenModel.ProductoTerminadoModel;
import Model.CatalogosModel.CatalogoClientesModel;
import Model.CatalogosModel.CatalogoMaquinasModel;
import Model.CatalogosModel.CatalogoMaterialesModel;
import Model.CatalogosModel.CatalogoOperadoresModel;
import Model.CatalogosModel.CatalogoProductosModel;
import Model.CatalogosModel.CatalogoProveedoresModel;
import Model.EtiquetasModel.ImpresionEtiquetasModel;
import Model.EtiquetasModel.SeguimientoLotesModel;
import Model.RequisicionesModel.ControlEntregasModel;
import Model.PedidosModel.BitacoraPedidosClienteModel;
import Model.PedidosModel.PlaneacionModel;
import Model.ProcesosProduccion;
import Model.ProduccionModel.AdminProduccionModel;
import Model.ProduccionModel.BitacoraOrdenesTrabajoModel;
import Model.RequisicionesModel.RecepcionRequisicionModel;
import Model.RequisicionesModel.RegistroEntradaMaterialesModel;
import Model.Sesion;
import View.Catalogos.CatalogoClientes;
import View.Catalogos.CatalogoMaquinas;
import View.Catalogos.CatalogoMateriales;
import View.Catalogos.CatalogoOperadores;
import View.Catalogos.CatalogoProductos;
import View.Catalogos.CatalogoProveedores;
import View.Etiquetas.ImpresionEtiquetasView;
import View.Etiquetas.SeguimientoLotes;
import View.Requisiciones.ControlEntregasView;
import View.Pedidos.BitacoraPedidosClienteView;
import View.Pedidos.PlaneacionView;
import View.Produccion.BitacoraOrdenesTrabajoView;
import View.Requisiciones.RecepcionRequisiciones;
import View.Requisiciones.RegistroEntradaMateriales;
import View.almacenView.MateriaPrimaView;
import View.almacenView.ProductoTerminadoView;
import ds.desktop.notify.DesktopNotify;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author cesar
 */
public class Principal extends javax.swing.JFrame {
        
    Dimension screenSize;
    public static ProcesosProduccion procesos = new ProcesosProduccion();
    private final Sesion sesionIniciada = new Sesion();
    
 
    public Principal(){
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        Toolkit t = Toolkit.getDefaultToolkit();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        procesos.start();
        IntroducirPanle(new IniciarSesion(this),Escritorio,new Point(screenSize.width/2,screenSize.height/2-80));
        SesionCerrada();
        try {
            Image img = ImageIO.read(new File("src\\logo.png"));
            this.setIconImage(img);
        } catch (Exception e) {
            System.out.println("View.Principal.<init>()");
                    
        }
    }
    
    private void desabilitarSandra(){
        menuItemRecepcionRequisiciones.setEnabled(false);
    }
    
    private void desabilidarContabilidad(){
        menuControlProduccion.setEnabled(false);
        menuPedidos.setEnabled(false);
        menuItemControlEntregaMP.setEnabled(false);
        menuItemEntradasMateriales.setEnabled(false);
        menuSeguimientoLotes.setEnabled(false);
        menuCatalogos.setEnabled(false);
    }

    private void desabilitarProduccion(){
        menuPedidos.setEnabled(false);
        menuSeguimientoLotes.setEnabled(false);
        menuCatalogos.setEnabled(false);
        menuRequisicionMateriales.setEnabled(false);
        menuAlmacen.setEnabled(false);
    }
    


    private void IntroducirPanle(JInternalFrame pnNuevo,JDesktopPane escritorio,Point punto) { 
        Dimension d = pnNuevo.getPreferredSize();
        pnNuevo.setSize(d); 
        pnNuevo.setLocation(punto.x-pnNuevo.getWidth()/2,punto.y-pnNuevo.getHeight()/2);
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
        menuItemIniciarCerrarSesion = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuControlProduccion = new javax.swing.JMenu();
        menuItemBitacoraOrdenesTrabajo = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuAlmacen = new javax.swing.JMenu();
        menuItemMateriaPrima = new javax.swing.JMenuItem();
        menuItemProductoTerminado = new javax.swing.JMenuItem();
        menuPedidos = new javax.swing.JMenu();
        itemMenuBitacoraPedidosCliente = new javax.swing.JMenuItem();
        itemMenuPlaneacion = new javax.swing.JMenuItem();
        menuRequisicionMateriales = new javax.swing.JMenu();
        menuItemControlEntregaMP = new javax.swing.JMenuItem();
        menuItemEntradasMateriales = new javax.swing.JMenuItem();
        menuSeguimientoLotes = new javax.swing.JMenu();
        menuItemImprimirEtiquetas = new javax.swing.JMenuItem();
        menuItemSeguimientoLote = new javax.swing.JMenuItem();
        menuCatalogos = new javax.swing.JMenu();
        menuItemProductos = new javax.swing.JMenuItem();
        menuItemClientes = new javax.swing.JMenuItem();
        menuItemMaquinas = new javax.swing.JMenuItem();
        menuItemOperadores = new javax.swing.JMenuItem();
        menuItemProveedores = new javax.swing.JMenuItem();
        menuItemMateriales = new javax.swing.JMenuItem();
        menuCompras = new javax.swing.JMenu();
        menuItemRecepcionRequisiciones = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu6.setText("jMenu6");

        jMenu8.setText("jMenu8");

        jMenu9.setText("jMenu9");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        Escritorio.setBackground(new java.awt.Color(86, 9, 12));

        javax.swing.GroupLayout EscritorioLayout = new javax.swing.GroupLayout(Escritorio);
        Escritorio.setLayout(EscritorioLayout);
        EscritorioLayout.setHorizontalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1281, Short.MAX_VALUE)
        );
        EscritorioLayout.setVerticalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        jMenu3.setText("Archivo                    ");

        menuItemIniciarCerrarSesion.setText("Iniciar/cerrar sesión");
        menuItemIniciarCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemIniciarCerrarSesionActionPerformed(evt);
            }
        });
        jMenu3.add(menuItemIniciarCerrarSesion);

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar2.add(jMenu3);

        menuControlProduccion.setText("Producciòn                             ");

        menuItemBitacoraOrdenesTrabajo.setText("Bitacora de ordenes de trabajo");
        menuItemBitacoraOrdenesTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBitacoraOrdenesTrabajoActionPerformed(evt);
            }
        });
        menuControlProduccion.add(menuItemBitacoraOrdenesTrabajo);

        jMenuItem2.setText("Administrar ordenes");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuControlProduccion.add(jMenuItem2);

        jMenuBar2.add(menuControlProduccion);

        menuAlmacen.setText("Almacen                               ");

        menuItemMateriaPrima.setText("Materia prima");
        menuItemMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMateriaPrimaActionPerformed(evt);
            }
        });
        menuAlmacen.add(menuItemMateriaPrima);

        menuItemProductoTerminado.setText("Producto terminado");
        menuItemProductoTerminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProductoTerminadoActionPerformed(evt);
            }
        });
        menuAlmacen.add(menuItemProductoTerminado);

        jMenuBar2.add(menuAlmacen);

        menuPedidos.setText("Pedidos                        ");

        itemMenuBitacoraPedidosCliente.setText("bitacora de pedidos");
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

        menuRequisicionMateriales.setText("Requisicion de materiales            ");

        menuItemControlEntregaMP.setText("Control de requisiciones");
        menuItemControlEntregaMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemControlEntregaMPActionPerformed(evt);
            }
        });
        menuRequisicionMateriales.add(menuItemControlEntregaMP);

        menuItemEntradasMateriales.setText("Registro entrada materiales");
        menuItemEntradasMateriales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEntradasMaterialesActionPerformed(evt);
            }
        });
        menuRequisicionMateriales.add(menuItemEntradasMateriales);

        jMenuBar2.add(menuRequisicionMateriales);

        menuSeguimientoLotes.setText("Etiquetado               ");

        menuItemImprimirEtiquetas.setText("Imprimir etiquetas");
        menuItemImprimirEtiquetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemImprimirEtiquetasActionPerformed(evt);
            }
        });
        menuSeguimientoLotes.add(menuItemImprimirEtiquetas);

        menuItemSeguimientoLote.setText("Seguimiento de lotes");
        menuItemSeguimientoLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSeguimientoLoteActionPerformed(evt);
            }
        });
        menuSeguimientoLotes.add(menuItemSeguimientoLote);

        jMenuBar2.add(menuSeguimientoLotes);

        menuCatalogos.setText("Catalogos                        ");

        menuItemProductos.setText("PRODUCTOS");
        menuItemProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProductosActionPerformed(evt);
            }
        });
        menuCatalogos.add(menuItemProductos);

        menuItemClientes.setText("CLIENTES");
        menuItemClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemClientesActionPerformed(evt);
            }
        });
        menuCatalogos.add(menuItemClientes);

        menuItemMaquinas.setText("MAQUINAS");
        menuItemMaquinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMaquinasActionPerformed(evt);
            }
        });
        menuCatalogos.add(menuItemMaquinas);

        menuItemOperadores.setText("OPERADORES");
        menuItemOperadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOperadoresActionPerformed(evt);
            }
        });
        menuCatalogos.add(menuItemOperadores);

        menuItemProveedores.setText("PROVEEDORES");
        menuItemProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProveedoresActionPerformed(evt);
            }
        });
        menuCatalogos.add(menuItemProveedores);

        menuItemMateriales.setText("MATERIALES");
        menuItemMateriales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemMaterialesActionPerformed(evt);
            }
        });
        menuCatalogos.add(menuItemMateriales);

        jMenuBar2.add(menuCatalogos);

        menuCompras.setText("Compras                  ");

        menuItemRecepcionRequisiciones.setText("Recepcion de requisiciones");
        menuItemRecepcionRequisiciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRecepcionRequisicionesActionPerformed(evt);
            }
        });
        menuCompras.add(menuItemRecepcionRequisiciones);

        jMenuBar2.add(menuCompras);

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
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Escritorio.removeAll();
        AdminProduccionView adminProduccionView = new AdminProduccionView(this);
        AdminProduccionController adminProduccionController = new AdminProduccionController(adminProduccionView,
        new AdminProduccionModel());
        
        IntroducirPanle(adminProduccionView, Escritorio,new Point(screenSize.width/2,screenSize.height/2-80));
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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

    private void menuItemControlEntregaMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemControlEntregaMPActionPerformed
        ControlEntregasView controlEntrasView = new ControlEntregasView(this);
        ControlEntregasController entregasController = new ControlEntregasController(controlEntrasView,new ControlEntregasModel());
        Escritorio.removeAll();
        IntroducirPanle(controlEntrasView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
            
    }//GEN-LAST:event_menuItemControlEntregaMPActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
            dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuItemEntradasMaterialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEntradasMaterialesActionPerformed
        RegistroEntradaMateriales entradaMaterialesView = new RegistroEntradaMateriales(this);
        RegistroEntradaMaterialesController entradaMaterialController = 
                new RegistroEntradaMaterialesController(entradaMaterialesView,new RegistroEntradaMaterialesModel());
        Escritorio.removeAll();
        IntroducirPanle(entradaMaterialesView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
        
    }//GEN-LAST:event_menuItemEntradasMaterialesActionPerformed

    private void menuItemImprimirEtiquetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemImprimirEtiquetasActionPerformed
        ImpresionEtiquetasView impresionView = new ImpresionEtiquetasView(this);
        ImpresionEtiquetasController impresionCOntroller = new ImpresionEtiquetasController(impresionView, new ImpresionEtiquetasModel());
        Escritorio.removeAll();    
        IntroducirPanle(impresionView, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemImprimirEtiquetasActionPerformed

    private void menuItemProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProductosActionPerformed
        CatalogoProductos view = new CatalogoProductos(this);
        CatalogoProductosController controller = new CatalogoProductosController(view,new CatalogoProductosModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
        
    }//GEN-LAST:event_menuItemProductosActionPerformed

    private void menuItemClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemClientesActionPerformed
        CatalogoClientes view = new CatalogoClientes(this);
        CatalogoClientesController controller = new CatalogoClientesController(view,new CatalogoClientesModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemClientesActionPerformed

    private void menuItemMaquinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMaquinasActionPerformed
        CatalogoMaquinas view = new CatalogoMaquinas(this);
        CatalogoMaquinasController controller = new CatalogoMaquinasController(view,new CatalogoMaquinasModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemMaquinasActionPerformed

    private void menuItemOperadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOperadoresActionPerformed
        CatalogoOperadores view = new CatalogoOperadores(this);
        CatalogoOperadoresController controller = new CatalogoOperadoresController(view,new CatalogoOperadoresModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemOperadoresActionPerformed

    private void menuItemProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProveedoresActionPerformed
        CatalogoProveedores view = new CatalogoProveedores();
        CatalogoProveedoreController controller = new CatalogoProveedoreController(view,new CatalogoProveedoresModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemProveedoresActionPerformed

    private void menuItemMaterialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemMaterialesActionPerformed
        CatalogoMateriales view = new CatalogoMateriales(this);
        CatalogoMaterialesController controller = new CatalogoMaterialesController(view,
        new CatalogoMaterialesModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemMaterialesActionPerformed

    private void menuItemRecepcionRequisicionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRecepcionRequisicionesActionPerformed
        RecepcionRequisiciones view = new RecepcionRequisiciones();
        RecepcionRequisicionController controller = new RecepcionRequisicionController(view,new RecepcionRequisicionModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemRecepcionRequisicionesActionPerformed

    private void menuItemSeguimientoLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSeguimientoLoteActionPerformed
        SeguimientoLotes view = new SeguimientoLotes();
        SeguimientoLotesController controller = new SeguimientoLotesController(view,
        new SeguimientoLotesModel());
        Escritorio.removeAll();    
        IntroducirPanle(view, Escritorio,new Point(screenSize.width/2,screenSize.height/2));
    }//GEN-LAST:event_menuItemSeguimientoLoteActionPerformed

    public Sesion getSesionIniciada() {
        return sesionIniciada;
    }
    
    IniciarSesion viewSesion = new IniciarSesion(this);
    
    private void menuItemIniciarCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemIniciarCerrarSesionActionPerformed
        
        if(!sesionIniciada.isEstado())
            mostrarLogin();
            
        else if( JOptionPane.showConfirmDialog(null, "HAY UNA SESION ABIERTA, ¿SEGURO QUE QUIERE CERRAR LA SESION?",
                "VALIDACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ){
             
            if(sesionIniciada.getDescUsuario().equals("PRODUCCION") && procesos.procesoActual > 0){     
                sesionIniciada.setEstado(false);    
                DesktopNotify.showDesktopMessage(" CERRAR CERRADA "," HA CERRADO LA SESION DE FORMA CORRECTA, INICIE DE NUEVO "
                         + " SI ES NECESARIO ",DesktopNotify.SUCCESS,3000);
                 JOptionPane.showMessageDialog(null, "AUN HAY PROCESOS EN EJECUCION POR FAVOR TERMINE LOS PROCESOS ANTES DE "
                         + "CERRAR SU SESION");
             }else{
                mostrarLogin(); 
                SesionCerrada();
            }    
              
        }
    }//GEN-LAST:event_menuItemIniciarCerrarSesionActionPerformed

    public void SesionCerrada(){
        menuControlProduccion.setEnabled(false);
        menuAlmacen.setEnabled(false);
        menuPedidos.setEnabled(false);
        menuRequisicionMateriales.setEnabled(false);
        menuSeguimientoLotes.setEnabled(false);
        menuCatalogos.setEnabled(false);
        menuCompras.setEnabled(false);
    }
    
    public void SesionProduccionCalidad(){
        menuControlProduccion.setEnabled(true);
        menuAlmacen.setEnabled(true);
        menuPedidos.setEnabled(false);
        menuRequisicionMateriales.setEnabled(false);
        menuSeguimientoLotes.setEnabled(false);
        menuCatalogos.setEnabled(false);
        menuCompras.setEnabled(false);
    }
    
    void SesionCompras() {
        menuControlProduccion.setEnabled(false);
        menuAlmacen.setEnabled(false);
        menuPedidos.setEnabled(false);
        menuRequisicionMateriales.setEnabled(false);
        menuSeguimientoLotes.setEnabled(false);
        menuCatalogos.setEnabled(false);
        menuCompras.setEnabled(true);
    }
    
    
    void SesionPlaneacion() {
        menuControlProduccion.setEnabled(true);
        menuAlmacen.setEnabled(true);
        menuPedidos.setEnabled(true);
        menuRequisicionMateriales.setEnabled(true);
        menuSeguimientoLotes.setEnabled(true);
        menuCatalogos.setEnabled(true);
        menuCompras.setEnabled(false);
    }
    
    
    
    
    private void mostrarLogin(){
        Escritorio.removeAll();  
        IntroducirPanle(viewSesion, Escritorio,new Point(screenSize.width/2,screenSize.height/2-80));
    }
    
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
    private javax.swing.JMenuItem itemMenuPlaneacion;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenu menuAlmacen;
    private javax.swing.JMenu menuCatalogos;
    private javax.swing.JMenu menuCompras;
    private javax.swing.JMenu menuControlProduccion;
    private javax.swing.JMenuItem menuItemBitacoraOrdenesTrabajo;
    private javax.swing.JMenuItem menuItemClientes;
    private javax.swing.JMenuItem menuItemControlEntregaMP;
    private javax.swing.JMenuItem menuItemEntradasMateriales;
    private javax.swing.JMenuItem menuItemImprimirEtiquetas;
    private javax.swing.JMenuItem menuItemIniciarCerrarSesion;
    private javax.swing.JMenuItem menuItemMaquinas;
    private javax.swing.JMenuItem menuItemMateriaPrima;
    private javax.swing.JMenuItem menuItemMateriales;
    private javax.swing.JMenuItem menuItemOperadores;
    private javax.swing.JMenuItem menuItemProductoTerminado;
    private javax.swing.JMenuItem menuItemProductos;
    private javax.swing.JMenuItem menuItemProveedores;
    private javax.swing.JMenuItem menuItemRecepcionRequisiciones;
    private javax.swing.JMenuItem menuItemSeguimientoLote;
    private javax.swing.JMenu menuPedidos;
    private javax.swing.JMenu menuRequisicionMateriales;
    private javax.swing.JMenu menuSeguimientoLotes;
    // End of variables declaration//GEN-END:variables

    

    
}
