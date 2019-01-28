/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Produccion;

import Model.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author cesar
 */
public class TurnoOperador extends javax.swing.JDialog {

    /**
     * Creates new form TurnoOperador
     */
    
    private TurnoOperador(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    private static  ArrayList<operador> operadores = new ArrayList<>();
    private static String[] turnoYoperador = new String[2];
    private static TurnoOperador ventana;
    private static boolean guardar=false;
    
    public static String[] seleccionarOperadorTurno(java.awt.Frame parent){
        ventana = new TurnoOperador(parent, true);
        
        
        operadores = optenerOperadores();
        for(int i = 0;i<operadores.size();i++)
            ventana.getCbxCodOperador().addItem(operadores.get(i).getNoOperador()+"");
        
        
       ventana.getCbxCodOperador().addActionListener(listenerBotones);
        
                
        ventana.getBtnAceptar().addActionListener(listenerBotones);
        ventana.getBtnCancelar().addActionListener(listenerBotones);
        
        ventana.addWindowListener(listenerCerrando);
        ventana.setVisible(true);
        return turnoYoperador;
    }
    
    private static final WindowListener listenerCerrando = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e); 
            if(!guardar)
                turnoYoperador = null;
        }
    
         
    
    };
    
    
    private static final ActionListener listenerBotones = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == ventana.getCbxCodOperador()){
                for(int i = 0;i<operadores.size();i++)
                    if(operadores.get(i).getNoOperador().equals(ventana.getCbxCodOperador().getSelectedItem().toString())){
                        ventana.lbOperador.setText(operadores.get(i).nombreOperador);
                        turnoYoperador[1] = operadores.get(i).nombreOperador;
                    }           
            }
            else if(e.getSource() == ventana.getBtnAceptar()){
                turnoYoperador[0] = ventana.getCbxTurno().getSelectedItem().toString();
                turnoYoperador[1] = ventana.getCbxCodOperador().getSelectedItem().toString();
                guardar = true;
                ventana.dispose();
                
            }
            else if(e.getSource() == ventana.getBtnCancelar()){
                turnoYoperador = null;
                ventana.dispose();
            }
        }
        
        
    };
    
    private static ArrayList<operador> optenerOperadores(){
        ArrayList<operador> listaOperadores = new ArrayList<>();
        Connection c = Conexion.getInstance().getConexion();
        if(c!=null)
            try {
                String query = "SELECT no_operador,nombre_operador FROM operadores;";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.first())
                    do {                  
                     listaOperadores.add(new operador(rs.getString(1),rs.getString(2)));
                    } while (rs.next());
                
            } catch (SQLException e) {
                System.err.println("error: "+e.getMessage());
            }
        
        return listaOperadores;
    }
    
    private static class operador{
        
        String noOperador;
        String nombreOperador;

        public operador(String noOperador, String nombreOperador) {
            this.noOperador = noOperador;
            this.nombreOperador = nombreOperador;
        }

        public String getNoOperador() {
            return noOperador;
        }

        public void setNoOperador(String noOperador) {
            this.noOperador = noOperador;
        }

        public String getNombreOperador() {
            return nombreOperador;
        }

        public void setNombreOperador(String nombreOperador) {
            this.nombreOperador = nombreOperador;
        }

        

        
        
    }

    public void setCbxCodOperador(JComboBox<String> cbxCodOperador) {
        this.cbxCodOperador = cbxCodOperador;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JComboBox<String> getCbxCodOperador() {
        return cbxCodOperador;
    }

    public JComboBox<String> getCbxTurno() {
        return cbxTurno;
    }

    public JLabel getLbOperador() {
        return lbOperador;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxTurno = new javax.swing.JComboBox<>();
        cbxCodOperador = new javax.swing.JComboBox<>();
        lbOperador = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jLabel1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel1.setText("SELECCIONA EL TURNO");

        jLabel2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel2.setText("SELECCIONA EL OPERADOR");

        cbxTurno.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        cbxTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "V" }));

        lbOperador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAceptar.setText("ACEPTAR");

        btnCancelar.setText("CANCELAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addGap(10, 10, 10)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbxCodOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbOperador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnCancelar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxTurno)
                    .addComponent(cbxCodOperador)
                    .addComponent(lbOperador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(TurnoOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TurnoOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TurnoOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TurnoOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TurnoOperador dialog = new TurnoOperador(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> cbxCodOperador;
    private javax.swing.JComboBox<String> cbxTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbOperador;
    // End of variables declaration//GEN-END:variables
}
