package code;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import settings.Key;
import settings.conexionBD;

public class ajustesUsuario extends javax.swing.JFrame {

    Inicio i;
    //Para acciones en la base de datos
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet result;
    //
    boolean Autorizar=false;
    //Validar caracteres
    boolean validarCaracter=false;
    boolean validar=false;
    
    public ajustesUsuario(Inicio inicio) {
        i=inicio;
        initComponents();
        setIconImage(getIconImage());
        this.setLocationRelativeTo(null);
        conexion=new conexionBD();
        lblCargo.setText(i.cargo);
        txtNombre.setText(i.nombre);
        txtPa.setText(i.paterno);
        txtMa.setText(i.materno);
        txtBol.setText(i.boleta);
        txtNick.setText(i.nickname);
        lblID.setText("ID: " + i.id);
        deshabilitarInfo();
        deshabilitarPass();
    }
    
    private void deshabilitarInfo(){
        txtNombre.setEditable(false);txtPa.setEditable(false); txtMa.setEditable(false);
        txtBol.setEditable(false); txtNick.setEditable(false);
        btnGuardar.setVisible(false); btnCancelar.setVisible(false); btnEditar.setVisible(true);
    }
    
    private void habilitarInfo(){
        txtNombre.setEditable(true);txtPa.setEditable(true); txtMa.setEditable(true);
        txtBol.setEditable(true); txtNick.setEditable(true);
        btnGuardar.setVisible(true); btnCancelar.setVisible(true); btnEditar.setVisible(false);
    }
    
    private void deshabilitarPass(){
        oldPass.setVisible(false); oldPass.setEnabled(false);
        newPass.setVisible(false); newPass.setEnabled(false);
        newPassAgain.setVisible(false); newPassAgain.setEnabled(false);
        lbl1.setVisible(false); lbl2.setVisible(false); lbl3.setVisible(false);
        btnChangePass.setVisible(true); btnUpdatePass.setVisible(false); btnCancelarPass.setVisible(false);
        btnChangePass.setEnabled(true); btnUpdatePass.setEnabled(false); btnCancelarPass.setEnabled(false);
    }
    
    private void habilitarPass(){
        oldPass.setVisible(true); oldPass.setEnabled(true);
        newPass.setVisible(true); newPass.setEnabled(true);
        newPassAgain.setVisible(true); newPassAgain.setEnabled(true);
        lbl1.setVisible(true); lbl2.setVisible(true); lbl3.setVisible(true);
        btnChangePass.setVisible(false); btnUpdatePass.setVisible(true); btnCancelarPass.setVisible(true);
        btnChangePass.setEnabled(false); btnUpdatePass.setEnabled(true); btnCancelarPass.setEnabled(true);
    }
    
    private void autorizar(){
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        // Establecer el campo de texto del nombre de usuario como el primer campo en enfocarse
        Object[] fields = {"Password:", passwordField};
        JOptionPane pane = new JOptionPane(fields, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(null, "Confirmar");
        dialog.setModal(true);

        // Establecer el campo de texto del nombre de usuario como el primer campo en enfocarse
        dialog.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                passwordField.requestFocusInWindow();
            }
        });

        dialog.setVisible(true);
        int option = (Integer) pane.getValue();

        if (option == JOptionPane.OK_OPTION) {
            String pass = passwordField.getText();
            // Aquí puedes hacer lo que necesites con los datos ingresados
            if(!usernameField.getText().isEmpty() || !passwordField.getText().isEmpty()){
                try{
                    String consulta="SELECT password FROM usuarios WHERE IDusuario LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, i.id);
                    result=cmd.executeQuery();
                    if(result.next()){
                        Key k=new Key();
                        if(result.getString(1).matches(k.getPassword(pass))){
                            Autorizar=true;
                        }else{
                            JOptionPane.showMessageDialog(rootPane, "La contraseña es incorrecta.");
                            Autorizar=false;
                        }
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "La contraseña es incorrecta.");
                        Autorizar=false;
                    }
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane, "Error 006: No se pudo verificar la contraseña.");
                    Autorizar=false;
                }
            }
        }
        
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtPa = new javax.swing.JTextField();
        txtMa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBol = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNick = new javax.swing.JTextField();
        lbl1 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        oldPass = new javax.swing.JPasswordField();
        newPass = new javax.swing.JPasswordField();
        newPassAgain = new javax.swing.JPasswordField();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnChangePass = new javax.swing.JButton();
        btnUpdatePass = new javax.swing.JButton();
        btnCancelarPass = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ajustes");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Ajustes de Almazen");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "   Información del usuario   ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre");

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Apellido paterno");

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Apellido materno");

        txtNombre.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtPa.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtMa.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tu cargo es");

        txtBol.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBolActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Boleta");

        lblCargo.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        lblCargo.setForeground(new java.awt.Color(0, 0, 0));
        lblCargo.setText("CARGO");

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Nickname");

        txtNick.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtNick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNickActionPerformed(evt);
            }
        });

        lbl1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lbl1.setForeground(new java.awt.Color(0, 0, 0));
        lbl1.setText("Contraseña antigua");

        lbl2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lbl2.setForeground(new java.awt.Color(0, 0, 0));
        lbl2.setText("Nueva contraseña");

        lbl3.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lbl3.setForeground(new java.awt.Color(0, 0, 0));
        lbl3.setText("Confirmar nueva contraseña");

        lblID.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(0, 0, 0));
        lblID.setText("ID: ");

        oldPass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        oldPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oldPassKeyPressed(evt);
            }
        });

        newPass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        newPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newPassKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                newPassKeyTyped(evt);
            }
        });

        newPassAgain.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        newPassAgain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newPassAgainKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                newPassAgainKeyTyped(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnChangePass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnChangePass.setText("Cambiar contraseña");
        btnChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePassActionPerformed(evt);
            }
        });

        btnUpdatePass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnUpdatePass.setText("Actualizar contraseña");
        btnUpdatePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePassActionPerformed(evt);
            }
        });

        btnCancelarPass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnCancelarPass.setText("Cancelar");
        btnCancelarPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPassActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnChangePass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar)))
                        .addGap(593, 593, 593))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(txtPa, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtBol, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCargo))))
                            .addComponent(btnEditar)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnUpdatePass, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelarPass)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(oldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl3)
                                .addGap(71, 71, 71))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(newPass, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(newPassAgain, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBol, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(lblCargo)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addComponent(btnChangePass)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl1)
                    .addComponent(lbl2)
                    .addComponent(lbl3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPassAgain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdatePass)
                    .addComponent(btnCancelarPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtBolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBolActionPerformed

    private void txtNickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNickActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNickActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        habilitarInfo();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        deshabilitarInfo();
        txtNombre.setText(i.nombre);
        txtPa.setText(i.paterno);
        txtMa.setText(i.materno);
        txtBol.setText(i.boleta);
        txtNick.setText(i.nickname);
        lblID.setText("ID: " + i.id);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePassActionPerformed
        habilitarPass();
    }//GEN-LAST:event_btnChangePassActionPerformed

    private void btnCancelarPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPassActionPerformed
        deshabilitarPass(); oldPass.setText(""); newPass.setText(""); newPassAgain.setText("");
    }//GEN-LAST:event_btnCancelarPassActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(txtNombre.getText().isEmpty() || txtPa.getText().isEmpty() || txtMa.getText().isEmpty() || txtBol.getText().isEmpty() ||
        txtNick.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Llene todos lo campos para continuar.");
        }else{
            if(!"Escriba su nickname.".equalsIgnoreCase(txtNick.getText())){
                autorizar();
                if(Autorizar==true){
                    try{
                        //Verifica si el nickname ya esta en uso
                        String consulta="SELECT IDusuario,nickname FROM usuarios WHERE nickname LIKE ? ";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtNick.getText());
                        result=cmd.executeQuery();
                        if(result.next()){
                            String IDu=result.getString(1);
                            if(IDu.equals(i.id)){
                                //Verifica si la boleta se registro anteriormente
                                try{
                                    String consulta2="SELECT IDusuario,boleta FROM usuarios WHERE boleta LIKE ? ";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta2);
                                    cmd.setString(1, txtBol.getText());
                                    result=cmd.executeQuery();
                                    if(result.next()){
                                        String IDua=result.getString(1);
                                        if(IDua.equals(i.id)){
                                            Updateinfo();
                                        }else{
                                            JOptionPane.showMessageDialog(null,"Esta persona ya fué registrada.");
                                        }
                                    }else{
                                        Updateinfo();
                                    }
                                }catch(SQLException e){
                                    JOptionPane.showMessageDialog(rootPane, "Error al consultar.");
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Este nickname no está disponible.");
                            }
                        }else{
                            //Verifica si la boleta se registro anteriormente
                            try{
                                String consulta2="SELECT IDusuario,boleta FROM usuarios WHERE boleta LIKE ? ";
                                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta2);
                                cmd.setString(1, txtBol.getText());
                                result=cmd.executeQuery();
                                if(result.next()){
                                    String IDua=result.getString(1);
                                    if(IDua.equals(i.id)){
                                        Updateinfo();
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Esta persona ya fué registrada.");
                                    }
                                }else{
                                    Updateinfo();
                                }
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane, "Error al consultar.");
                            }
                        }
                    }catch(SQLException e){
                        JOptionPane.showMessageDialog(rootPane, "Error al consultar.");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "No puedes usar este nickname.");
                txtNick.setText("");
                txtNick.grabFocus();
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void Updateinfo(){
        try{
            String registrar=("UPDATE usuarios SET nickname=?,nombre=?,paterno=?,materno=?,boleta=? WHERE IDusuario = ?");
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(registrar);
            cmd.setString(6, i.id);
            cmd.setString(1, txtNick.getText());
            cmd.setString(2, txtNombre.getText());
            cmd.setString(3, txtPa.getText());
            cmd.setString(4, txtMa.getText());
            cmd.setString(5, txtBol.getText());
            cmd.executeUpdate();
            cmd.close();
            i.setUsuario(txtNick.getText());
            i.iniciarInterfaz();
            deshabilitarInfo();
            txtNombre.setText(i.nombre);
            txtPa.setText(i.paterno);
            txtMa.setText(i.materno);
            txtBol.setText(i.boleta);
            txtNick.setText(i.nickname);
            JOptionPane.showMessageDialog(null,"Actualización exitosa, vuelva a iniciar sesión.");
            Login l=new Login();
            this.dispose();
            i.dispose();
            l.setVisible(true);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error 007: Error al registrar material.");
        }
    }
    
    private void btnUpdatePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePassActionPerformed
        if(newPass.getText().isEmpty() || newPassAgain.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese su nueva contraseña.");
        }else{
            if(!"••••••••••".equals(newPass.getText())){
                if(newPass.getPassword().length>3 && newPass.getPassword().length<26){
                    if(!(newPass.getText().equals(newPassAgain.getText()))){
                        JOptionPane.showMessageDialog(null, "La contraseña no coincide.");
                    }else{
                        validarPass();
                        if(validar==true){
                            Key k=new Key();
                            try{
                                String actualizar=("UPDATE usuarios SET password=? WHERE IDusuario = ?");
                                cmd=(PreparedStatement)conexion.conectar.prepareStatement(actualizar);
                                cmd.setString(2, i.id);
                                cmd.setString(1, k.getPassword(newPass.getText()));
                                cmd.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Actualización exitosa, vuelva a iniciar sesión.");
                                Login l=new Login();
                                this.dispose();
                                i.dispose();
                                l.setVisible(true);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(null,"Error 007: Error al registrar material.");
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane, "La contraseña es incorrecta.");
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane, "La contraseña debe tener 4 a 25 caracteres.");
                }
            }
        }
    }//GEN-LAST:event_btnUpdatePassActionPerformed

    private void validarPass(){
        Key k=new Key();
        try{
            String consulta="SELECT password FROM usuarios WHERE IDusuario = ?";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            cmd.setString(1, i.id);
            result=cmd.executeQuery();
            if(result.next()){
                if(result.getString(1).matches(k.getPassword(oldPass.getText()))){
                    validar=true;
                }else{
                    JOptionPane.showMessageDialog(rootPane, "La contraseña es incorrecta.");
                    validar=false;
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "La contraseña es incorrecta.");
                validar=false;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error 006: No se pudo verificar la contraseña.");
        }
    }
    
    private void newPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPassKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            newPassAgain.grabFocus();
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_newPassKeyPressed

    private void newPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPassKeyTyped
        //Limita el uso de caracteres para escribir la contraseña y en caso de no estar permitido borra el ultimo escrito
        StringBuilder cadena=new StringBuilder(newPass.getText());
        if(validarCaracter==false)
            if( (evt.isShiftDown()==true || evt.isShiftDown()==false) && (evt.getKeyChar()<'A' || evt.getKeyChar()>'Z') && 
                (evt.getKeyChar()<'a' || evt.getKeyChar()>'z') && (evt.getKeyChar()<'0' || evt.getKeyChar()>'9') &&
                (evt.getKeyChar()!='$' && evt.getKeyChar()!='%' && evt.getKeyChar()!='#' && evt.getKeyChar()!='*' && evt.getKeyChar()!='+'
                && evt.getKeyChar()!='_' && evt.getKeyChar()!='-' && evt.getKeyChar()!='.' && evt.getKeyChar()!='ñ' && evt.getKeyChar()!='Ñ')
            ){
                JOptionPane.showMessageDialog(rootPane, "No puedes usar ' " + evt.getKeyChar() + " '.");
                cadena.deleteCharAt(cadena.length());
                newPass.setText(cadena.toString());
                cadena=null;
            }
        validarCaracter=false;
    }//GEN-LAST:event_newPassKeyTyped

    private void newPassAgainKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPassAgainKeyTyped
        //Limita el uso de caracteres para escribir la contraseña y en caso de no estar permitido borra el ultimo escrito
        StringBuilder cadena=new StringBuilder(newPassAgain.getText());
        if(validarCaracter==false)
            if( (evt.isShiftDown()==true || evt.isShiftDown()==false) && (evt.getKeyChar()<'A' || evt.getKeyChar()>'Z') && 
                (evt.getKeyChar()<'a' || evt.getKeyChar()>'z') && (evt.getKeyChar()<'0' || evt.getKeyChar()>'9') &&
                (evt.getKeyChar()!='$' && evt.getKeyChar()!='%' && evt.getKeyChar()!='#' && evt.getKeyChar()!='*' && evt.getKeyChar()!='+'
                && evt.getKeyChar()!='_' && evt.getKeyChar()!='-' && evt.getKeyChar()!='.' && evt.getKeyChar()!='ñ' && evt.getKeyChar()!='Ñ')
            ){
                JOptionPane.showMessageDialog(rootPane, "No puedes usar ' " + evt.getKeyChar() + " '.");
                cadena.deleteCharAt(cadena.length()-1);
                newPassAgain.setText(cadena.toString());
                cadena=null;
            }
        validarCaracter=false;
    }//GEN-LAST:event_newPassAgainKeyTyped

    private void newPassAgainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPassAgainKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            btnUpdatePass.grabFocus();
            btnUpdatePass.doClick();
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_newPassAgainKeyPressed

    private void oldPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oldPassKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            newPass.grabFocus();
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_oldPassKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setImageIn(JLabel a,String route){
        ImageIcon img; Icon icono;
        img=new ImageIcon(getClass().getResource(route));
        icono=new ImageIcon(img.getImage().getScaledInstance(a.getWidth(), a.getHeight(), Image.SCALE_DEFAULT));
        a.setIcon(icono);
        this.repaint();
    }
    
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
                new ajustesUsuario(inicio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarPass;
    private javax.swing.JButton btnChangePass;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnUpdatePass;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblID;
    private javax.swing.JPasswordField newPass;
    private javax.swing.JPasswordField newPassAgain;
    private javax.swing.JPasswordField oldPass;
    private javax.swing.JTextField txtBol;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtNick;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPa;
    // End of variables declaration//GEN-END:variables
}
