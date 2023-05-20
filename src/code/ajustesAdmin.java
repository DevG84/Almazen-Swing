package code;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import settings.conexionBD;
import java.sql.SQLException;
import java.sql.ResultSet;
import settings.Key;
import settings.conexionBD;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class ajustesAdmin extends javax.swing.JFrame {

    Inicio i;
    //Para acciones en la base de datos
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet result;
    
    //Validar caracteres
    boolean validarCaracter=false;
    boolean autorizar=false;
    
    public ajustesAdmin(Inicio inicio) {
        initComponents();
        setIconImage(getIconImage());
        this.setLocationRelativeTo(null);
        conexion=new conexionBD();
        cambiarCargoOff();
        cambiarPassOff();
        cmbBuscarCargo.setVisible(false);
    }

    private void cambiarCargoOff(){
        cmbEditCargo.setVisible(false);
        btnUpdateCargo.setVisible(false);
        btnEditCargo.setVisible(true);
    }
    
    private void cambiarCargoOn(){
        cmbEditCargo.setVisible(true);
        btnUpdateCargo.setVisible(true);
        btnEditCargo.setVisible(false);
    }
    
    private void cambiarPassOff(){
        lblPass1.setVisible(false); lblPass2.setVisible(false);
        txtPass.setVisible(false); txtConfirmar.setVisible(false);
        btnUpdatePass.setVisible(false); btnChangePass.setVisible(true);
    }
    
    private void cambiarPassOn(){
        lblPass1.setVisible(true); lblPass2.setVisible(true);
        txtPass.setVisible(true); txtConfirmar.setVisible(true);
        btnUpdatePass.setVisible(true); btnChangePass.setVisible(false);
    }
    
    //Logo del JFrame
    @Override
    public Image getIconImage(){
        Image retValue=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("iconSettings_32px.png"));
        return retValue;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        cmbBuscarTipo = new javax.swing.JComboBox<>();
        cmbBuscarCargo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        lblCargo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblPrivilegios = new javax.swing.JLabel();
        cmbEditCargo = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        lblPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        lblPass1 = new javax.swing.JLabel();
        lblPass2 = new javax.swing.JLabel();
        txtConfirmar = new javax.swing.JTextField();
        btnUpdatePass = new javax.swing.JButton();
        btnEditCargo = new javax.swing.JButton();
        btnChangePass = new javax.swing.JButton();
        btnUpdateCargo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrador");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Buscar:");

        txtBuscar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        cmbBuscarTipo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbBuscarTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nickname", "Nombre", "Apellido Paterno", "Apellido Materno", "Cargo", "Boleta", " ", " " }));
        cmbBuscarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarTipoActionPerformed(evt);
            }
        });

        cmbBuscarCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbBuscarCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Invitado", "Servicio Social", "Encargado de almacén", "Administrador" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nickname", "Nombre", "Apellido paterno", "Apellido materno", "Cargo", "Identificación"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jCheckBox1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox1.setText("Usuario activo");

        jCheckBox2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox2.setText("Buscar material");

        jCheckBox3.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox3.setText("Registrar y mover material");

        jCheckBox4.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox4.setText("Consultar movimientos");

        jCheckBox5.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jCheckBox5.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox5.setText("Modificar información");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        lblCargo.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        lblCargo.setForeground(new java.awt.Color(0, 0, 0));
        lblCargo.setText("Cargo");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N

        lblPrivilegios.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        lblPrivilegios.setForeground(new java.awt.Color(0, 0, 0));
        lblPrivilegios.setText("Privilegios");

        cmbEditCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbEditCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Invitado", "Servicio Social", "Encargado de almacén", "Administrador" }));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N

        lblPass.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        lblPass.setForeground(new java.awt.Color(0, 0, 0));
        lblPass.setText("Cambiar contraseña");

        txtPass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtPass.setToolTipText("");
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });

        lblPass1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lblPass1.setForeground(new java.awt.Color(0, 0, 0));
        lblPass1.setText("Nueva contraseña:");

        lblPass2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lblPass2.setForeground(new java.awt.Color(0, 0, 0));
        lblPass2.setText("Confirmar contraseña:");

        txtConfirmar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtConfirmar.setToolTipText("");
        txtConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConfirmarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConfirmarKeyTyped(evt);
            }
        });

        btnUpdatePass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnUpdatePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/update_16px.png"))); // NOI18N
        btnUpdatePass.setText("Actualizar contraseña");

        btnEditCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnEditCargo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/edit_16px.png"))); // NOI18N
        btnEditCargo.setText("Cambiar cargo");
        btnEditCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCargoActionPerformed(evt);
            }
        });

        btnChangePass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnChangePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/edit_16px.png"))); // NOI18N
        btnChangePass.setText("Cambiar contraseña");
        btnChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePassActionPerformed(evt);
            }
        });

        btnUpdateCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnUpdateCargo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/update_16px.png"))); // NOI18N
        btnUpdateCargo.setText("Actualizar cargo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPrivilegios, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox3)
                                .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblCargo)
                                .addComponent(cmbEditCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdateCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPass)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblPass2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(6, 6, 6)
                                    .addComponent(btnUpdatePass)))
                            .addContainerGap(266, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbBuscarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 407, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbBuscarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPass1)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPass2)
                            .addComponent(txtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnChangePass)
                            .addComponent(btnUpdatePass)))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPrivilegios)
                        .addGap(12, 12, 12)
                        .addComponent(jCheckBox1)
                        .addGap(6, 6, 6)
                        .addComponent(jCheckBox2)
                        .addGap(6, 6, 6)
                        .addComponent(jCheckBox3)
                        .addGap(6, 6, 6)
                        .addComponent(jCheckBox4)
                        .addGap(6, 6, 6)
                        .addComponent(jCheckBox5))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCargo)
                        .addGap(6, 6, 6)
                        .addComponent(cmbEditCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditCargo)
                        .addGap(6, 6, 6)
                        .addComponent(btnUpdateCargo)))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void txtPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            txtConfirmar.grabFocus();
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_txtPassKeyPressed

    private void txtPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyTyped
        //Limita el uso de caracteres para escribir la contraseña y en caso de no estar permitido borra el ultimo escrito
        StringBuilder cadena=new StringBuilder(txtPass.getText());
        if(validarCaracter==false)
            if( (evt.isShiftDown()==true || evt.isShiftDown()==false) && (evt.getKeyChar()<'A' || evt.getKeyChar()>'Z') && 
                (evt.getKeyChar()<'a' || evt.getKeyChar()>'z') && (evt.getKeyChar()<'0' || evt.getKeyChar()>'9') &&
                (evt.getKeyChar()!='$' && evt.getKeyChar()!='%' && evt.getKeyChar()!='#' && evt.getKeyChar()!='*' && evt.getKeyChar()!='+'
                && evt.getKeyChar()!='_' && evt.getKeyChar()!='-' && evt.getKeyChar()!='.' && evt.getKeyChar()!='ñ' && evt.getKeyChar()!='Ñ'))
            {
                JOptionPane.showMessageDialog(rootPane, "No puedes usar ' " + evt.getKeyChar() + " '.");
                cadena.deleteCharAt(cadena.length());
                txtPass.setText(cadena.toString());
                cadena=null;
            }
        validarCaracter=false;
    }//GEN-LAST:event_txtPassKeyTyped

    private void txtConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmarKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            //btnGuardar.grabFocus();
            //btnGuardar.doClick();
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_txtConfirmarKeyPressed

    private void txtConfirmarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmarKeyTyped
        //Limita el uso de caracteres para escribir la contraseña y en caso de no estar permitido borra el ultimo escrito
        StringBuilder cadena=new StringBuilder(txtPass.getText());
        if(validarCaracter==false)
            if( (evt.isShiftDown()==true || evt.isShiftDown()==false) && (evt.getKeyChar()<'A' || evt.getKeyChar()>'Z') && 
                (evt.getKeyChar()<'a' || evt.getKeyChar()>'z') && (evt.getKeyChar()<'0' || evt.getKeyChar()>'9') &&
                (evt.getKeyChar()!='$' && evt.getKeyChar()!='%' && evt.getKeyChar()!='#' && evt.getKeyChar()!='*' && evt.getKeyChar()!='+'
                && evt.getKeyChar()!='_' && evt.getKeyChar()!='-' && evt.getKeyChar()!='.' && evt.getKeyChar()!='ñ' && evt.getKeyChar()!='Ñ'))
            {
                JOptionPane.showMessageDialog(rootPane, "No puedes usar ' " + evt.getKeyChar() + " '.");
                cadena.deleteCharAt(cadena.length()-1);
                txtPass.setText(cadena.toString());
                cadena=null;
            }
        validarCaracter=false;
    }//GEN-LAST:event_txtConfirmarKeyTyped

    private void btnEditCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCargoActionPerformed
        cambiarCargoOn();
        
    }//GEN-LAST:event_btnEditCargoActionPerformed

    private void btnChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePassActionPerformed
        cambiarPassOn();
        
    }//GEN-LAST:event_btnChangePassActionPerformed

    private void cmbBuscarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarTipoActionPerformed
        if(cmbBuscarTipo.getSelectedItem().equals("Cargo")){
            cmbBuscarCargo.setVisible(true);
            txtBuscar.setVisible(false);
            //Buscar
            
        }else{
            cmbBuscarCargo.setVisible(false);
            txtBuscar.setVisible(true);
            //Buscar
            
        }
    }//GEN-LAST:event_cmbBuscarTipoActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Inicio inicio = null;
        /* Set the Nimbus look and feel */
        FlatArcIJTheme.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ajustesAdmin(inicio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePass;
    private javax.swing.JButton btnEditCargo;
    private javax.swing.JButton btnUpdateCargo;
    private javax.swing.JButton btnUpdatePass;
    private javax.swing.JComboBox<String> cmbBuscarCargo;
    private javax.swing.JComboBox<String> cmbBuscarTipo;
    private javax.swing.JComboBox<String> cmbEditCargo;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblPass1;
    private javax.swing.JLabel lblPass2;
    private javax.swing.JLabel lblPrivilegios;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtConfirmar;
    private javax.swing.JTextField txtPass;
    // End of variables declaration//GEN-END:variables
}
