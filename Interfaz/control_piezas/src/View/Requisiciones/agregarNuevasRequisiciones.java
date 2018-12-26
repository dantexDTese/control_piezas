
package View.Requisiciones;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class agregarNuevasRequisiciones extends javax.swing.JDialog {

    public agregarNuevasRequisiciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JLabel getLbOrdenProduccion() {
        return lbOrdenProduccion;
    }

    public JTextArea getTxtUsoMaterial() {
        return txtUsoMaterial;
    }
    
    public JButton getBtnAgregarListaOP() {
        return btnAgregarListaOP;
    }

    public JButton getBtnAgregarMaterialesRequeridos() {
        return btnAgregarMaterialesRequeridos;
    }

    public JTextField getTxtNombreSolicitando() {
        return txtNombreSolicitando;
    }

    public JButton getBtnAgregar() {
        return btnAgregarMaterialesRequeridos;
    }    
    
    public JSpinner getSprCantidad() {
        return sprCantidad;
    }
    
    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    public JComboBox<String> getCbxCuentaCargo() {
        return cbxCuentaCargo;
    }

    public JDateChooser getJdcFechaSolicitada() {
        return jdcFechaSolicitada;
    }

    public JSpinner getSpParcialidad() {
        return spParcialidad;
    }
    
    public JTable getJtbListaMateriales() {
        return jtbListaMateriales;
    }

    public JTable getJtbPendientes() {
        return jtbPendientes;
    }

    public JTable getJtbProductos() {
        return jtbProductos;
    }

    public JLabel getLbMaterial() {
        return lbMaterial;
    }

    public JLabel getLbNoPartida() {
        return lbNoPartida;
    }


    public JLabel getLbUnidad() {
        return lbUnidad;
    }

    public JTable getJtMaterialOrdenesSeleccionado() {
        return jtMaterialOrdenesSeleccionado;
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbPendientes = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbProductos = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtbListaMateriales = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtNombreSolicitando = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtMaterialOrdenesSeleccionado = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lbNoPartida = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbMaterial = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        sprCantidad = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        lbUnidad = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jdcFechaSolicitada = new com.toedter.calendar.JDateChooser();
        spParcialidad = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        cbxCuentaCargo = new javax.swing.JComboBox<>();
        btnAgregarMaterialesRequeridos = new javax.swing.JButton();
        btnAgregarListaOP = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lbOrdenProduccion = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtUsoMaterial = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(15, 144, 138));

        jPanel3.setBackground(new java.awt.Color(15, 144, 138));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ORDENES PENDIENTES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Impact", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);

        jtbPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. ORDEN", "ORDEN COMPRA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbPendientes.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jtbPendientes);
        if (jtbPendientes.getColumnModel().getColumnCount() > 0) {
            jtbPendientes.getColumnModel().getColumn(0).setResizable(false);
            jtbPendientes.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PRODUCTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Impact", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setOpaque(false);

        jtbProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. OP", "PRODUCTO", "CANT. PRODUCTO", "MATERIAL", "BARRAS NECESARIAS", "SIG. PRODUCCION"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtbProductos);
        if (jtbProductos.getColumnModel().getColumnCount() > 0) {
            jtbProductos.getColumnModel().getColumn(0).setResizable(false);
            jtbProductos.getColumnModel().getColumn(1).setResizable(false);
            jtbProductos.getColumnModel().getColumn(2).setResizable(false);
            jtbProductos.getColumnModel().getColumn(3).setResizable(false);
            jtbProductos.getColumnModel().getColumn(4).setResizable(false);
            jtbProductos.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LISTA DE MATERIALES REQUERIDOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Impact", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel7.setOpaque(false);

        jtbListaMateriales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NUM. PARTIDA", "MATERIAL", "CANTIDAD", "UNIDAD", "PARCIALIDAD", "FECHA SOLICITADA", "CUENTA CARGO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbListaMateriales.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jtbListaMateriales);
        if (jtbListaMateriales.getColumnModel().getColumnCount() > 0) {
            jtbListaMateriales.getColumnModel().getColumn(0).setResizable(false);
            jtbListaMateriales.getColumnModel().getColumn(1).setResizable(false);
            jtbListaMateriales.getColumnModel().getColumn(2).setResizable(false);
            jtbListaMateriales.getColumnModel().getColumn(3).setResizable(false);
            jtbListaMateriales.getColumnModel().getColumn(4).setResizable(false);
            jtbListaMateriales.getColumnModel().getColumn(5).setResizable(false);
            jtbListaMateriales.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NOMBRE DEL SOLICITANTE SOLICITANTE");

        txtNombreSolicitando.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnEnviar.setText("ENVIAR");

        jLabel3.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("AGREGAR NUEVAS REQUISICIONES");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel10.setOpaque(false);
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtMaterialOrdenesSeleccionado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "OP", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtMaterialOrdenesSeleccionado.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jtMaterialOrdenesSeleccionado);
        if (jtMaterialOrdenesSeleccionado.getColumnModel().getColumnCount() > 0) {
            jtMaterialOrdenesSeleccionado.getColumnModel().getColumn(0).setResizable(false);
            jtMaterialOrdenesSeleccionado.getColumnModel().getColumn(1).setResizable(false);
            jtMaterialOrdenesSeleccionado.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel10.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 88, 369, 150));

        jLabel2.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("NO. PARTIDA");
        jPanel10.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 112, -1));

        lbNoPartida.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        lbNoPartida.setForeground(new java.awt.Color(255, 255, 255));
        lbNoPartida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNoPartida.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.add(lbNoPartida, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 100, 17));

        jLabel4.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("MATERIAL");
        jPanel10.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 70, 20));

        lbMaterial.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        lbMaterial.setForeground(new java.awt.Color(255, 255, 255));
        lbMaterial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMaterial.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.add(lbMaterial, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 100, 17));

        jLabel9.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CANTIDAD");
        jPanel10.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 70, -1));
        jPanel10.add(sprCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 100, -1));

        jLabel10.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("UNIDADA");
        jPanel10.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 74, -1));

        lbUnidad.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        lbUnidad.setForeground(new java.awt.Color(255, 255, 255));
        lbUnidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUnidad.setText("PZ");
        lbUnidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.add(lbUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 74, -1));

        jLabel6.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("PARCIALIDAD");
        jPanel10.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, -1, -1));

        jLabel12.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("FECHA SOLICITADA");
        jPanel10.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));
        jPanel10.add(jdcFechaSolicitada, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 100, -1));
        jPanel10.add(spParcialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 70, -1));

        jLabel8.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CUENTA A CARGO");
        jPanel10.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 104, -1));

        cbxCuentaCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GMC", "GSM" }));
        jPanel10.add(cbxCuentaCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, 100, -1));

        btnAgregarMaterialesRequeridos.setText("AGREGAR MATERIALES REQUERIDOS");
        jPanel10.add(btnAgregarMaterialesRequeridos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 370, -1));

        btnAgregarListaOP.setText("AGREGAR LISTA OP");
        jPanel10.add(btnAgregarListaOP, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 160, -1));

        jLabel5.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("OP");
        jPanel10.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 60, 20));

        lbOrdenProduccion.setFont(new java.awt.Font("Impact", 0, 13)); // NOI18N
        lbOrdenProduccion.setForeground(new java.awt.Color(255, 255, 255));
        lbOrdenProduccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOrdenProduccion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.add(lbOrdenProduccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 100, 20));

        jLabel7.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("USO DEL MATERIAL:");

        txtUsoMaterial.setColumns(20);
        txtUsoMaterial.setRows(5);
        txtUsoMaterial.setEnabled(false);
        jScrollPane3.setViewportView(txtUsoMaterial);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombreSolicitando, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 6, Short.MAX_VALUE)))))
                .addGap(38, 38, 38))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(296, 296, 296)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNombreSolicitando, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(agregarNuevasRequisiciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(agregarNuevasRequisiciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(agregarNuevasRequisiciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(agregarNuevasRequisiciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                agregarNuevasRequisiciones dialog = new agregarNuevasRequisiciones(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregarListaOP;
    private javax.swing.JButton btnAgregarMaterialesRequeridos;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JComboBox<String> cbxCuentaCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private com.toedter.calendar.JDateChooser jdcFechaSolicitada;
    private javax.swing.JTable jtMaterialOrdenesSeleccionado;
    private javax.swing.JTable jtbListaMateriales;
    private javax.swing.JTable jtbPendientes;
    private javax.swing.JTable jtbProductos;
    private javax.swing.JLabel lbMaterial;
    private javax.swing.JLabel lbNoPartida;
    private javax.swing.JLabel lbOrdenProduccion;
    private javax.swing.JLabel lbUnidad;
    private javax.swing.JSpinner spParcialidad;
    private javax.swing.JSpinner sprCantidad;
    private javax.swing.JTextField txtNombreSolicitando;
    private javax.swing.JTextArea txtUsoMaterial;
    // End of variables declaration//GEN-END:variables
}
