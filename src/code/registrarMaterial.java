package code;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import java.awt.AWTKeyStroke;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import settings.conexionBD;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import settings.Key;

public class registrarMaterial extends javax.swing.JFrame {
    
    //Para acciones en la base de datos
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet marca_res, present_res,confirma;
    
    //Autorizaciones
    boolean perCodAutorizar=false;
    
    //
    Inicio i;
    
    public registrarMaterial(Inicio inicio) {
        initComponents();
        i=inicio;
        setIconImage(getIconImage());
        this.setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        conexion=new conexionBD();
        //
        if("Administrador".equals(i.cargo) || "Encargado de almacén".equals(i.cargo)) lblNota.setText("");
        cual1.setVisible(false);
        txtMarca.setVisible(false);
        txtMarca.setEnabled(false);
        cual2.setVisible(false);
        txtPresent.setVisible(false);
        txtPresent.setEnabled(false);
        //
        llenarMarca();
        llenarPresentación();
        
    }

    //Logo del JFrame
    @Override
    public Image getIconImage(){
        Image retValue=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icon_32px.png"));
        return retValue;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAboutUs = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbMarca = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbPresent = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        spinCant = new javax.swing.JSpinner();
        txtDesc = new javax.swing.JTextField();
        txtArt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cual1 = new javax.swing.JLabel();
        cual2 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtPresent = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblNota = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar Material");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnAboutUs.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnAboutUs.setText("Cerrar");
        btnAboutUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutUsActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información del Material", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 13), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Marca:");

        cmbMarca.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMarcaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Presentación:");

        cmbPresent.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbPresent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPresentActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Cantidad Inicial:");

        spinCant.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtDesc.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtArt.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre del artículo:");

        txtCodigo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Código de barras:");

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Descripción:");

        cual1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual1.setForeground(new java.awt.Color(0, 0, 0));
        cual1.setText("¿Cuál?");

        cual2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual2.setForeground(new java.awt.Color(0, 0, 0));
        cual2.setText("¿Cuál?");

        txtMarca.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtPresent.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtPresent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPresentActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jButton1.setText("Registrar Material");

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Tipo de código:");

        cmbTipo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código del articulo", "Código personalizado" }));
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        lblNota.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        lblNota.setForeground(new java.awt.Color(153, 102, 0));
        lblNota.setText("Nota: El uso de códigos personalizados requiere ser autorizado.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12)
                        .addComponent(txtArt, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblNota))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cual1)
                        .addGap(18, 18, 18)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(cmbPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cual2)
                        .addGap(18, 18, 18)
                        .addComponent(txtPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(spinCant, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNota))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(cual1))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPresent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPresent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(cual2))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(spinCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jButton1)
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAboutUs, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                .addComponent(btnAboutUs)
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

    //Llenar información existente
    private void llenarMarca(){
        try{
            String consulta="SELECT DISTINCT marca FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            marca_res=cmd.executeQuery();
            //Recorrer los resultados y agregar cada valor único de "marca" al JComboBox
            cmbMarca.addItem(null);
            while (marca_res.next()) {
                cmbMarca.addItem(marca_res.getString(1));
            }
            cmbMarca.addItem("Otra");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 004: Error al obtener las marcas registradas en la base de datos.");
        }
    }
    
    private void llenarPresentación(){
        try{
            String consulta="SELECT DISTINCT presentacion FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            present_res=cmd.executeQuery();
            //Recorrer los resultados y agregar cada valor único de "presentacion" al JComboBox
            cmbPresent.addItem(null);
            while (present_res.next()) {
                cmbPresent.addItem(present_res.getString(1));
            }
            cmbPresent.addItem("Otra");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 005: Error al obtener las presentaciones registradas en la base de datos.");
        }
    }
    
    private void btnAboutUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutUsActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnAboutUsActionPerformed

    private void txtPresentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPresentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPresentActionPerformed

    private void cmbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMarcaActionPerformed
        if ("Otra".equals(cmbMarca.getSelectedItem())) {
            cual1.setVisible(true);
            txtMarca.setVisible(true);
            txtMarca.setEnabled(true);
        }else{
            cual1.setVisible(false);
            txtMarca.setVisible(false);
            txtMarca.setEnabled(false);
        }
    }//GEN-LAST:event_cmbMarcaActionPerformed

    private void cmbPresentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPresentActionPerformed
        if ("Otra".equals(cmbPresent.getSelectedItem())) {
            cual2.setVisible(true);
            txtPresent.setVisible(true);
            txtPresent.setEnabled(true);
        }else{
            cual2.setVisible(false);
            txtPresent.setVisible(false);
            txtPresent.setEnabled(false);
        }
    }//GEN-LAST:event_cmbPresentActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        if ("Código personalizado".equals(cmbTipo.getSelectedItem())) {
            if("Administrador".equals(i.cargo) || "Encargado de almacén".equals(i.cargo)){
                perCodAutorizar=true;
                lblNota.setVisible(true);
                lblNota.setText("Precaución: Asegurese de no duplicar ningún material al usar esta opción.");
            }else{
                autorizar();
            }
            if(perCodAutorizar==true){
                aplicarPerCod();
            }else{
                cmbTipo.setSelectedItem("Código del articulo");
            }
        }else{
            txtCodigo.setEditable(true);
            txtCodigo.setText("");
            if ("Código del articulo".equals(cmbTipo.getSelectedItem())) {
                if("Administrador".equals(i.cargo) || "Encargado de almacén".equals(i.cargo)){
                    lblNota.setText("");
                }else{
                    lblNota.setText("Nota: El uso de códigos personalizados requiere ser autorizado.");
                }
            }
        }
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void aplicarPerCod(){
        cmbTipo.setSelectedItem("Código personalizado");
        txtCodigo.setEditable(false);
        txtCodigo.setText("almz-XXX");
    }
        
    private void autorizar(){
        this.setAlwaysOnTop(false);
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        // Establecer el campo de texto del nombre de usuario como el primer campo en enfocarse
        Object[] fields = {"Nickname:", usernameField, "Password:", passwordField};
        JOptionPane pane = new JOptionPane(fields, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(null, "Autorizar");
        dialog.setModal(true);

        // Establecer el campo de texto del nombre de usuario como el primer campo en enfocarse
        dialog.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                usernameField.requestFocusInWindow();
            }
        });
        
        // Agregar una acción personalizada al evento VK_ENTER del campo de texto del nombre de usuario
        InputMap inputMap = usernameField.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = usernameField.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "moveToPasswordField");
        actionMap.put("moveToPasswordField", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                passwordField.requestFocusInWindow();
            }
        });

        dialog.setVisible(true);
        int option = (Integer) pane.getValue();

        if (option == JOptionPane.OK_OPTION) {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            // Aquí puedes hacer lo que necesites con los datos ingresados
            if(!usernameField.getText().isEmpty() || !passwordField.getText().isEmpty()){
                try{
                    String consulta="SELECT cargo,password,status FROM usuarios NATURAL JOIN privilegios WHERE nickname LIKE ? ";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, user);
                    confirma=cmd.executeQuery();
                    if(confirma.next()){
                        if(confirma.getString(1).equals("Administrador") || confirma.getString(1).equals("Encargado de almacén")){
                            if(confirma.getString(3).equals("S")){
                                Key k=new Key();
                                if(confirma.getString(2).matches(k.getPassword(pass))){
                                    perCodAutorizar=true;
                                    lblNota.setText("Precaución: Asegurese de no duplicar ningún material al usar esta opción.");
                                }else{
                                    JOptionPane.showMessageDialog(rootPane, "El usuario o la contraseña son incorrectos.");
                                    perCodAutorizar=false;
                                }
                            }else{
                                JOptionPane.showMessageDialog(rootPane, "El usuario no está activo en el sistema.");
                                perCodAutorizar=false;
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane, "El usuario ingresado no está autorizado.");
                            perCodAutorizar=false;
                        }
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "El usuario o la contraseña son incorrectos.");
                        perCodAutorizar=false;
                    }
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane, "Error: 006.");
                    perCodAutorizar=false;
                }
            }
        }
        this.setAlwaysOnTop(true);
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
                new registrarMaterial(inicio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAboutUs;
    private javax.swing.JComboBox<String> cmbMarca;
    private javax.swing.JComboBox<String> cmbPresent;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel cual1;
    private javax.swing.JLabel cual2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblNota;
    private javax.swing.JSpinner spinCant;
    private javax.swing.JTextField txtArt;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtPresent;
    // End of variables declaration//GEN-END:variables
}
