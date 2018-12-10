
package View.Pedidos;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;


public class BitacoraPedidosClienteView extends javax.swing.JInternalFrame {

    
    private final JFrame principal;
    public BitacoraPedidosClienteView(JFrame principal) {
        initComponents();
        this.principal = principal;
        
    }
    
    
    public JFrame getPrincipal() {
        return principal;
    }

    public JButton getBtnBuscarOrden() {
        return btnBuscarOrden;
    }

    public JButton getBtnVertodo() {
        return btnVertodo;
    }

    public JMonthChooser getJmcMes() {
        return jmcMes;
    }

    public JYearChooser getJycAnio() {
        return jycAnio;
    }

    public JTextField getTxtOrdenCompra() {
        return txtOrdenCompra;
    }
    
    
    

    public JButton getBtnNuevaOrden() {
        return btnNuevaOrden;
    }
   

    public JTable getTbPedidosClientes() {
        return tbPedidosClientes;
    }

        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jmcMes = new com.toedter.calendar.JMonthChooser();
        jycAnio = new com.toedter.calendar.JYearChooser();
        btnNuevaOrden = new javax.swing.JButton();
        txtOrdenCompra = new javax.swing.JTextField();
        btnBuscarOrden = new javax.swing.JButton();
        btnVertodo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPedidosClientes = new javax.swing.JTable();

        setBorder(null);
        setVisible(true);

        jPanel1.setBackground(new java.awt.Color(15, 144, 138));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BITACORA DE PEDIDOS DE CLIENTE, AÑO ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, 41));

        jLabel2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 79, 41));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SELECCIONAR AÑO");

        btnNuevaOrden.setText("NUEVA ORDEN");

        btnBuscarOrden.setText("BUSCAR");

        btnVertodo.setText("VER TODO");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jycAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jmcMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVertodo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNuevaOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBuscarOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jycAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jmcMes, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnVertodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setOpaque(false);

        tbPedidosClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ESTADO", "NO. PEDIDO", "ORDEN COMPRA", "PRODUCTO", "CANTIDAD", "FECHA DE ENTREGA", "CONFIRMACION ENTREGA", "FECHA RECEPCION", "CONTACTO", "CLIENTE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPedidosClientes.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbPedidosClientes);
        if (tbPedidosClientes.getColumnModel().getColumnCount() > 0) {
            tbPedidosClientes.getColumnModel().getColumn(0).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(1).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(2).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(3).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(4).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(5).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(6).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(7).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(8).setResizable(false);
            tbPedidosClientes.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarOrden;
    private javax.swing.JButton btnNuevaOrden;
    private javax.swing.JButton btnVertodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JMonthChooser jmcMes;
    private com.toedter.calendar.JYearChooser jycAnio;
    private javax.swing.JTable tbPedidosClientes;
    private javax.swing.JTextField txtOrdenCompra;
    // End of variables declaration//GEN-END:variables
}
