
package View;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;


public class OrdenesProduccion extends javax.swing.JDialog {

    /**
     * Creates new form OrdenesProduccion
     * @param parent
     * @param modal
     */
    public OrdenesProduccion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JButton getAgregarProducto() {
        return AgregarProducto;
    }

    public void setAgregarProducto(JButton AgregarProducto) {
        this.AgregarProducto = AgregarProducto;
    }
   
    public JPanel getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(JPanel listaProductos) {
        this.listaProductos = listaProductos;
    }

    public JPanel getListaMaquina() {
        return listaMaquina;
    }

    public void setListaMaquina(JPanel listaMaquina) {
        this.listaMaquina = listaMaquina;
    }

    public JPanel getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(JPanel listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
    
    
    /**Cajas de texto*/
    
    public JTextField getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(JTextField productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }
    
    public JSpinner getCantidadPorTurno() {
        return cantidadPorTurno;
    }

    public void setCantidadPorTurno(JSpinner cantidadPorTurno) {
        this.cantidadPorTurno = cantidadPorTurno;
    }

    public JSpinner getCantidadProducir() {
        return cantidadProducir;
    }

    public void setCantidadProducir(JSpinner cantidadProducir) {
        this.cantidadProducir = cantidadProducir;
    }

    
    /**spinners*/
    
    
    public JSpinner getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(JSpinner cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public JDateChooser getFechaMontaje() {
        return fechaMontaje;
    }

    public void setFechaMontaje(JDateChooser fechaMontaje) {
        this.fechaMontaje = fechaMontaje;
    }

    public JTextField getMaquinaSeleccionada() {
        return maquinaSeleccionada;
    }

    public void setMaquinaSeleccionada(JTextField maquinaSeleccionada) {
        this.maquinaSeleccionada = maquinaSeleccionada;
    }

    public JTextField getMaterialSeleccionado() {
        return materialSeleccionado;
    }

    public void setMaterialSeleccionado(JTextField materialSeleccionado) {
        this.materialSeleccionado = materialSeleccionado;
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        AgregarProducto = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ContenedorLisProductos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        productoSeleccionado = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        listaProductos = new javax.swing.JPanel();
        contenedorLisOtros = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        maquinaSeleccionada = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaMaquina = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaMateriales = new javax.swing.JPanel();
        materialSeleccionado = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cantidadSolicitada = new javax.swing.JSpinner();
        cantidadProducir = new javax.swing.JSpinner();
        cantidadPorTurno = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        fechaMontaje = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        AgregarProducto.setText("AGREGAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        ContenedorLisProductos.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("PRODUCTO");

        listaProductos.setMinimumSize(new java.awt.Dimension(256, 164));

        javax.swing.GroupLayout listaProductosLayout = new javax.swing.GroupLayout(listaProductos);
        listaProductos.setLayout(listaProductosLayout);
        listaProductosLayout.setHorizontalGroup(
            listaProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
        );
        listaProductosLayout.setVerticalGroup(
            listaProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 164, Short.MAX_VALUE)
        );

        jScrollPane6.setViewportView(listaProductos);

        javax.swing.GroupLayout ContenedorLisProductosLayout = new javax.swing.GroupLayout(ContenedorLisProductos);
        ContenedorLisProductos.setLayout(ContenedorLisProductosLayout);
        ContenedorLisProductosLayout.setHorizontalGroup(
            ContenedorLisProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContenedorLisProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContenedorLisProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(productoSeleccionado)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ContenedorLisProductosLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
                .addContainerGap())
        );
        ContenedorLisProductosLayout.setVerticalGroup(
            ContenedorLisProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContenedorLisProductosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(productoSeleccionado)
                .addContainerGap())
        );

        contenedorLisOtros.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("MAQUINA");

        javax.swing.GroupLayout listaMaquinaLayout = new javax.swing.GroupLayout(listaMaquina);
        listaMaquina.setLayout(listaMaquinaLayout);
        listaMaquinaLayout.setHorizontalGroup(
            listaMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );
        listaMaquinaLayout.setVerticalGroup(
            listaMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(listaMaquina);

        javax.swing.GroupLayout contenedorLisOtrosLayout = new javax.swing.GroupLayout(contenedorLisOtros);
        contenedorLisOtros.setLayout(contenedorLisOtrosLayout);
        contenedorLisOtrosLayout.setHorizontalGroup(
            contenedorLisOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLisOtrosLayout.createSequentialGroup()
                .addGroup(contenedorLisOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorLisOtrosLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel3)
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorLisOtrosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(contenedorLisOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(maquinaSeleccionada))))
                .addContainerGap())
        );
        contenedorLisOtrosLayout.setVerticalGroup(
            contenedorLisOtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLisOtrosLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 27, Short.MAX_VALUE)
                .addComponent(maquinaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout listaMaterialesLayout = new javax.swing.GroupLayout(listaMateriales);
        listaMateriales.setLayout(listaMaterialesLayout);
        listaMaterialesLayout.setHorizontalGroup(
            listaMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
        );
        listaMaterialesLayout.setVerticalGroup(
            listaMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(listaMateriales);

        jLabel4.setText("MATERIAL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(materialSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(materialSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ContenedorLisProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(contenedorLisOtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contenedorLisOtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ContenedorLisProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel5.setText("CANTIDAD POR TURNO");

        jLabel2.setText("CANTIDAD SOLICITADA");

        jLabel6.setText("CANTIDAD A PRODUCIR");

        jLabel7.setText("AGREGAR FECHA DE MONTAJE");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cantidadSolicitada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cantidadProducir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(121, 121, 121))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(cantidadPorTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaMontaje, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(58, 58, 58))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(fechaMontaje, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(cantidadSolicitada)
                    .addComponent(cantidadPorTurno)
                    .addComponent(cantidadProducir))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrdenesProduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenesProduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenesProduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenesProduccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OrdenesProduccion dialog = new OrdenesProduccion(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarProducto;
    private javax.swing.JPanel ContenedorLisProductos;
    private javax.swing.JSpinner cantidadPorTurno;
    private javax.swing.JSpinner cantidadProducir;
    private javax.swing.JSpinner cantidadSolicitada;
    private javax.swing.JPanel contenedorLisOtros;
    private com.toedter.calendar.JDateChooser fechaMontaje;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel listaMaquina;
    private javax.swing.JPanel listaMateriales;
    private javax.swing.JPanel listaProductos;
    private javax.swing.JTextField maquinaSeleccionada;
    private javax.swing.JTextField materialSeleccionado;
    private javax.swing.JTextField productoSeleccionado;
    // End of variables declaration//GEN-END:variables
}
