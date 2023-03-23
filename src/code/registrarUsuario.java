package code;

import com.mysql.cj.xdevapi.Statement;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import settings.Key;
import settings.conexionBD;
import java.sql.SQLException;
import java.sql.ResultSet;

public class registrarUsuario extends javax.swing.JFrame {
        
    //Mover ventana
    int xMouse, yMouse;
    
    //Incersiones
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet result;
    String idUsuario = "";
    
    //Validar caracteres
    boolean validarCaracter=false;

    public registrarUsuario() {
        initComponents();
        conexion=new conexionBD();
        setIconImage(getIconImage());
        this.setLocationRelativeTo(this);
        Ingresar i=new Ingresar(); i.setImageIn(lblLogo, "src/sources/logo.png");
        desactivarComponentes();
    }
    
        //Logo del JFrame
    @Override
    public Image getIconImage(){
        Image retValue=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icon_32px.png"));
        return retValue;
    }
    
    public void desactivarComponentes(){
        btnGuardar.setEnabled(false); txtNombre.setEnabled(false); cmbCargo.setEnabled(false); txtPass.setEnabled(false); txtNick.setEnabled(false);
        txtPaterno.setEnabled(false); txtMaterno.setEnabled(false); txtConfirmar.setEnabled(false); txtBoleta.setEnabled(false);
    }
    
    public void activarComponentes(){
        btnGuardar.setEnabled(true); txtNombre.setEnabled(true); cmbCargo.setEnabled(true); txtPass.setEnabled(true); txtNick.setEnabled(true);
        txtPaterno.setEnabled(true); txtMaterno.setEnabled(true); txtConfirmar.setEnabled(true); txtBoleta.setEnabled(true);
    }
    
    public void limpiarComponentes(){
        txtNombre.setText(""); txtPaterno.setText(""); txtMaterno.setText(""); txtBoleta.setText("");
        txtNick.setText(""); txtPass.setText(""); txtConfirmar.setText(""); cmbCargo.setSelectedIndex(0);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnClose = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNick = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        txtConfirmar = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        cmbCargo = new javax.swing.JComboBox<>();
        lblLogo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPaterno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaterno = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBoleta = new javax.swing.JTextField();
        lblBarraMov = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Nuevo usuario");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jToolBar1.setBackground(new java.awt.Color(51, 51, 51));
        jToolBar1.setFloatable(false);
        jToolBar1.setForeground(new java.awt.Color(51, 51, 51));
        jToolBar1.setRollover(true);
        jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnClose.setBackground(new java.awt.Color(51, 51, 51));
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/home32_px.png"))); // NOI18N
        btnClose.setBorder(null);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClose);

        btnNuevo.setBackground(new java.awt.Color(51, 51, 51));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/newUser_32px.png"))); // NOI18N
        btnNuevo.setFocusable(false);
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNuevo);

        btnGuardar.setBackground(new java.awt.Color(51, 51, 51));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/cloud_32px.png"))); // NOI18N
        btnGuardar.setFocusable(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardar);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 12))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel5.setText("Nickname");

        txtNick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNickActionPerformed(evt);
            }
        });
        txtNick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNickKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel7.setText("Contraseña");

        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel1.setText("Confirmar contraseña");

        txtConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConfirmarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConfirmarKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel8.setText("Cargo");

        cmbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Invitado", "Servicio Social", "Encargado de almacén" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtConfirmar)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1)
                                    .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtPass))
                        .addGap(38, 38, 38))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Nombre");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel3.setText("Apellido Paterno");

        txtPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaternoKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel4.setText("Apellido Materno");

        txtMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaternoKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel6.setText("Numero de trabajador o boleta");

        txtBoleta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBoletaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(txtBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 85, Short.MAX_VALUE))
                    .addComponent(txtMaterno)
                    .addComponent(txtNombre)
                    .addComponent(txtPaterno))
                .addGap(38, 38, 38))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        lblBarraMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        lblBarraMov.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBarraMov.setText("Registrar usuario");
        lblBarraMov.setPreferredSize(new java.awt.Dimension(135, 30));
        lblBarraMov.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblBarraMovMouseDragged(evt);
            }
        });
        lblBarraMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBarraMovMousePressed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(bgLayout.createSequentialGroup()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblBarraMov, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addComponent(lblBarraMov, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        Ingresar f=new Ingresar();
        f.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNickActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNickActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        btnNuevo.setEnabled(false);
        activarComponentes();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void lblBarraMovMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBarraMovMouseDragged
        int x=evt.getXOnScreen();
        int y=evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);

    }//GEN-LAST:event_lblBarraMovMouseDragged

    private void lblBarraMovMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBarraMovMousePressed
        xMouse=evt.getX();
        yMouse=evt.getY();
    }//GEN-LAST:event_lblBarraMovMousePressed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
        Ingresar f=new Ingresar();
        f.setVisible(true);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(txtNombre.getText().isEmpty() || txtPaterno.getText().isEmpty() || txtMaterno.getText().isEmpty() || txtBoleta.getText().isEmpty() ||
        txtNick.getText().isEmpty() || txtPass.getText().isEmpty() || txtConfirmar.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Llene todos lo campos para continuar.");
        }else{
            if(!"Ingrese su nombre de usuario".equals(txtNick.getText()) || !"ingrese su nombre de usuario".equals(txtNick.getText())){
                if(!"**********".equals(txtPass.getText())){
                    if(txtPass.getPassword().length>3){
                        if(!(txtPass.getText().matches(txtConfirmar.getText()))){
                            JOptionPane.showMessageDialog(rootPane, "La contraseña no coincide.");
                        }else{
                            try{
                                //Verifica si el nickname ya esta en uso
                                String consulta="SELECT nickname FROM usuarios WHERE nickname LIKE ? ";
                                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                                cmd.setString(1, txtNick.getText());
                                result=cmd.executeQuery();
                                if(result.next()){
                                    JOptionPane.showMessageDialog(rootPane, "El nickname ya está en uso.");
                                    txtNick.setText("");
                                    txtNick.grabFocus();
                                }else{
                                    try{
                                        //Verifica si la boleta se registro anteriormente
                                        String consulta2="SELECT boleta FROM usuarios WHERE boleta LIKE ? ";
                                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta2);
                                        cmd.setString(1, txtBoleta.getText());
                                        result=cmd.executeQuery();
                                        if(result.next()){
                                            JOptionPane.showMessageDialog(rootPane, "Esta persona ya fue registrada anteriormente.");
                                            txtBoleta.setText("");
                                            txtBoleta.grabFocus();
                                        }else{
                                            //Proceso de registro de usuarios
                                            try{
                                                //Asignar privilegios según el tipo de usuario
                                                String insertarUsuario=("INSERT INTO usuarios(nickname,nombre,paterno,materno,cargo,boleta,password) VALUES(?,?,?,?,?,?,?)");
                                                cmd=(PreparedStatement)conexion.conectar.prepareStatement(insertarUsuario);
                                                cmd.setString(1, txtNick.getText());
                                                cmd.setString(2, txtNombre.getText());
                                                cmd.setString(3, txtPaterno.getText());
                                                cmd.setString(4, txtMaterno.getText());
                                                String combo=cmbCargo.getSelectedItem().toString();
                                                cmd.setString(5, combo);
                                                cmd.setString(6, txtBoleta.getText());
                                                Key k=new Key();
                                                cmd.setString(7, k.getPassword(txtPass.getText()));
                                                cmd.executeUpdate();
                                                //Consultar id de usuarios
                                                try{
                                                    String IDconsulta="SELECT IDusuario FROM usuarios WHERE nickname = ? ";
                                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(IDconsulta);
                                                    cmd.setString(1, txtNick.getText());
                                                    ResultSet idresult=cmd.executeQuery();
                                                    if (idresult.next()) {
                                                        idUsuario = idresult.getString(1);
                                                    }else{
                                                        JOptionPane.showMessageDialog(rootPane, "Falló la consulta.");
                                                    }
                                                    //Insertar privilegios al id recien generado // Según el tipo de usuario
                                                    String insertarPrivilegios=("INSERT INTO privilegios (IDusuario,status,modBuscar,modInOut,modConsulta,modPerCod,modAlterUsuarios) VALUES (?,?,?,?,?,?,?)");
                                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(insertarPrivilegios);
                                                    cmd.setString(1, idUsuario);
                                                    if("Invitado".matches(cmbCargo.getSelectedItem().toString())){
                                                        cmd.setString(2, "S"); cmd.setString(3, "S"); cmd.setString(4, "N");
                                                        cmd.setString(5, "N"); cmd.setString(6, "N"); cmd.setString(7, "N");
                                                    }else{
                                                        if("Servicio Social".matches(cmbCargo.getSelectedItem().toString())){
                                                            cmd.setString(2, "S"); cmd.setString(3, "S"); cmd.setString(4, "S");
                                                            cmd.setString(5, "S"); cmd.setString(6, "N"); cmd.setString(7, "N");
                                                        }else{
                                                            if("Encargado de almacén".matches(cmbCargo.getSelectedItem().toString())){
                                                                cmd.setString(2, "S"); cmd.setString(3, "S"); cmd.setString(4, "S");
                                                                cmd.setString(5, "S"); cmd.setString(6, "S"); cmd.setString(7, "N");
                                                            }
                                                        }
                                                    }
                                                    cmd.executeUpdate();
                                                    JOptionPane.showMessageDialog(rootPane, "Usuario registrado.");
                                                    limpiarComponentes();
                                                    desactivarComponentes();
                                                    btnNuevo.setEnabled(true);
                                                }catch(SQLException e){
                                                    JOptionPane.showMessageDialog(rootPane, "Usuario generado con éxito.\nError: No se pudieron asignar privilegios.");
                                                }
                                            }catch(SQLException e){
                                                JOptionPane.showMessageDialog(rootPane, "Error al guardar.");
                                            }
                                            
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
                        JOptionPane.showMessageDialog(rootPane, "La contraseña debe tener al menos 4 caracteres.");
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane, "No puedes usar esta contraseña.");
                    txtPass.setText("");
                    txtConfirmar.setText("");
                    txtPass.grabFocus();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "No puedes usar este nickname.");
                txtNick.setText("");
                txtNick.grabFocus();
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        
    }//GEN-LAST:event_txtPassActionPerformed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            txtPaterno.grabFocus();
        }
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtPaternoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaternoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            txtMaterno.grabFocus();
        }
    }//GEN-LAST:event_txtPaternoKeyPressed

    private void txtMaternoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaternoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            txtBoleta.grabFocus();
        }
    }//GEN-LAST:event_txtMaternoKeyPressed

    private void txtBoletaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBoletaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            txtNick.grabFocus();
        }
    }//GEN-LAST:event_txtBoletaKeyPressed

    private void txtNickKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNickKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == 10){
            txtPass.grabFocus();
        }
    }//GEN-LAST:event_txtNickKeyPressed

    private void txtPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            txtConfirmar.grabFocus();
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_txtPassKeyPressed

    private void txtConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmarKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_txtConfirmarKeyPressed

    private void txtPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyTyped
        //Limita el uso de caracteres para escribir la contraseña y en caso de no estar permitido borra el ultimo escrito
        StringBuilder cadena=new StringBuilder(txtPass.getText());
        if(validarCaracter==false)
            if( (evt.isShiftDown()==true || evt.isShiftDown()==false) && (evt.getKeyChar()<'A' || evt.getKeyChar()>'Z') && 
                (evt.getKeyChar()<'a' || evt.getKeyChar()>'z') && (evt.getKeyChar()<'0' || evt.getKeyChar()>'9') &&
                (evt.getKeyChar()!='$' && evt.getKeyChar()!='%' && evt.getKeyChar()!='#' && evt.getKeyChar()!='*' && evt.getKeyChar()!='+'
                && evt.getKeyChar()!='_' && evt.getKeyChar()!='-' && evt.getKeyChar()!='.' && evt.getKeyChar()!='ñ' && evt.getKeyChar()!='Ñ')
            ){
                JOptionPane.showMessageDialog(rootPane, "No puedes usar ' " + evt.getKeyChar() + " '.");
                cadena.deleteCharAt(cadena.length()-1);
                txtPass.setText(cadena.toString());
                cadena=null;
            }
        validarCaracter=false;
    }//GEN-LAST:event_txtPassKeyTyped

    private void txtConfirmarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmarKeyTyped
        //Limita el uso de caracteres para escribir la contraseña y en caso de no estar permitido borra el ultimo escrito
        StringBuilder cadena=new StringBuilder(txtPass.getText());
        if(validarCaracter==false)
            if( (evt.isShiftDown()==true || evt.isShiftDown()==false) && (evt.getKeyChar()<'A' || evt.getKeyChar()>'Z') && 
                (evt.getKeyChar()<'a' || evt.getKeyChar()>'z') && (evt.getKeyChar()<'0' || evt.getKeyChar()>'9') &&
                (evt.getKeyChar()!='$' && evt.getKeyChar()!='%' && evt.getKeyChar()!='#' && evt.getKeyChar()!='*' && evt.getKeyChar()!='+'
                && evt.getKeyChar()!='_' && evt.getKeyChar()!='-' && evt.getKeyChar()!='.' && evt.getKeyChar()!='ñ' && evt.getKeyChar()!='Ñ')
            ){
                JOptionPane.showMessageDialog(rootPane, "No puedes usar ' " + evt.getKeyChar() + " '.");
                cadena.deleteCharAt(cadena.length()-1);
                txtPass.setText(cadena.toString());
                cadena=null;
            }
        validarCaracter=false;
    }//GEN-LAST:event_txtConfirmarKeyTyped

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
            java.util.logging.Logger.getLogger(registrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registrarUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblBarraMov;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JTextField txtBoleta;
    private javax.swing.JPasswordField txtConfirmar;
    private javax.swing.JTextField txtMaterno;
    private javax.swing.JTextField txtNick;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPaterno;
    // End of variables declaration//GEN-END:variables
}
