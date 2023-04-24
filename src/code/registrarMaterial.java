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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import settings.Key;

public class registrarMaterial extends javax.swing.JFrame {
    
    //Para acciones en la base de datos
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet marca_res, present_res,confirma, result;
        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        java.sql.Date fecha = java.sql.Date.valueOf(currentDate);
    
    //Autorizaciones
    boolean perCodAutorizar=false;
    
    //
    String idMerca="";
    
    //
    Inicio i;
    
    public registrarMaterial(Inicio inicio) {
        initComponents();
        i=inicio;
        setIconImage(getIconImage());
        this.setLocationRelativeTo(null);
        conexion=new conexionBD();
        setImageIn(logo, "src/sources/logo.png");
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
        txtArt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cual1 = new javax.swing.JLabel();
        cual2 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtPresent = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblNota = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        btnClean = new javax.swing.JButton();
        logo = new javax.swing.JLabel();

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
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información del Material", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 13))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel5.setText("Marca:");

        cmbMarca.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMarcaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel6.setText("Presentación:");

        cmbPresent.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbPresent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPresentActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel7.setText("Cantidad Inicial:");

        spinCant.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtArt.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel3.setText("Nombre del artículo:");

        txtCodigo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel2.setText("Código de barras:");

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel4.setText("Descripción:");

        cual1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual1.setText("¿Cuál?");

        cual2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual2.setText("¿Cuál?");

        txtMarca.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtPresent.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtPresent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPresentActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnRegistrar.setText("Registrar Material");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
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

        txtDesc.setColumns(20);
        txtDesc.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtDesc.setLineWrap(true);
        txtDesc.setRows(3);
        txtDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtDesc);

        btnClean.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnClean.setText("Limpiar");
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClean)
                        .addGap(18, 18, 18)
                        .addComponent(btnRegistrar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cual1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cual2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(spinCant, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClean, btnRegistrar});

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
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnClean))
                .addContainerGap())
        );

        logo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        logo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAboutUs, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
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
        cmbMarca.removeAllItems();
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
        cmbPresent.removeAllItems();
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

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        boolean camposLlenos=true;
        //Verifica que los campos estén llenos
        if(txtCodigo.getText().isEmpty() || txtArt.getText().isEmpty() || txtDesc.getText().isEmpty() || cmbMarca.getSelectedItem()==null || cmbPresent.getSelectedItem()==null){
            camposLlenos=false;
        }else{
            if(cmbMarca.getSelectedItem().equals("Otra")){  if(txtMarca.getText().isEmpty()){             camposLlenos=false;}
            }else{  if(cmbPresent.getSelectedItem().equals("Otra")){if(txtPresent.getText().isEmpty()){   camposLlenos=false;}}}
        }
        if(camposLlenos==false){
            JOptionPane.showMessageDialog(null,"Llene todos los campos para continuar.");
        }else{
            //Verifica que el codigo del material no se halla registrado antes
            try{
                //Verifica si el nickname ya esta en uso
                String consulta="SELECT codigo FROM mercancia WHERE codigo LIKE ? ";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtCodigo.getText());
                confirma=cmd.executeQuery();
                if(confirma.next()){
                    JOptionPane.showMessageDialog(rootPane, "El código de este material ya ha sido registrado.");
                }else{
                    //Inicia registro
                    try{
                        String registrar=("INSERT INTO mercancia(codigo,articulo,descripcion,marca,presentacion,existencia) VALUES(?,?,?,?,?,?)");
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(registrar);
                        cmd.setString(1, txtCodigo.getText());
                        cmd.setString(2, txtArt.getText());
                        cmd.setString(3, txtDesc.getText());
                        if(cmbMarca.getSelectedItem().toString().equals("Otra")){    cmd.setString(4, txtMarca.getText());
                        }else{                          cmd.setString(4, cmbMarca.getSelectedItem().toString());}
                        if(cmbPresent.getSelectedItem().toString().equals("Otra")){  cmd.setString(5, txtPresent.getText());
                        }else{                          cmd.setString(5, cmbPresent.getSelectedItem().toString());}
                        cmd.setString(6, spinCant.getValue().toString());
                        cmd.executeUpdate();
                        //Consultar id del material recien registrado
                        try{
                            String IDconsulta="SELECT IDmercancia FROM mercancia WHERE codigo=? ";
                            cmd=(PreparedStatement)conexion.conectar.prepareStatement(IDconsulta);
                            cmd.setString(1, txtCodigo.getText());
                            ResultSet idresult=cmd.executeQuery();
                            if (idresult.next()) {
                                idMerca = idresult.getString(1);
                            }else{
                                JOptionPane.showMessageDialog(rootPane, "Falló la consulta.");
                            }
                            //Registrar movimiento
                            String registrarMovimiento=("INSERT INTO movimiento(IDusuario,IDmercancia,tipo,asunto,fecha,cantidad) VALUES (?,?,?,?,?,?)");
                            cmd=(PreparedStatement)conexion.conectar.prepareStatement(registrarMovimiento);
                            cmd.setString(1, i.id);
                            cmd.setString(2, idMerca);
                            cmd.setString(3, "E");
                            cmd.setString(4, "Nuevo registro de material");
                            cmd.setDate(5, fecha);
                            cmd.setString(6, spinCant.getValue().toString());
                            cmd.executeUpdate();
                            JOptionPane.showMessageDialog(null,"Registro exitoso.");
                            vaciarCampos();
                            llenarMarca();
                            llenarPresentación();
                            try{
                                String materiales="SELECT * FROM mercancia";
                                cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                result=cmd.executeQuery();
                                i.llenarTablaBuscar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
                            }
                        }catch(SQLException e){
                            JOptionPane.showMessageDialog(rootPane, "Error al guardar datos de movimiento.");
                        }
                    }catch(SQLException e){
                        JOptionPane.showMessageDialog(null,"Error 007: Error al registrar material.");
                    }
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error 008: Error al consultar codigo de material.");
            }
        }
        
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoMouseClicked
        
    }//GEN-LAST:event_logoMouseClicked

    private void txtDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescKeyTyped
        StringBuilder cadena=new StringBuilder(txtDesc.getText());
        if(txtDesc.getText().length() >=300){
            JOptionPane.showMessageDialog(null, "Limite maximo de 300 caracteres.");
            cadena.deleteCharAt(cadena.length()-1);
            txtDesc.setText(cadena.toString());
            cadena=null;
        }
    }//GEN-LAST:event_txtDescKeyTyped

    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
        vaciarCampos();
    }//GEN-LAST:event_btnCleanActionPerformed

    public void setImageIn(JLabel a,String route){
        ImageIcon img; Icon icono;
        img=new ImageIcon(route);
        icono=new ImageIcon(img.getImage().getScaledInstance(a.getWidth(), a.getHeight(), Image.SCALE_DEFAULT));
        a.setIcon(icono);
        this.repaint();
    }
    
    private void vaciarCampos(){
        txtCodigo.setText(""); txtArt.setText(""); txtDesc.setText(""); cmbMarca.setSelectedItem(null); cmbPresent.setSelectedItem(null);
        txtMarca.setText(""); txtPresent.setText(""); spinCant.setValue(0); cmbTipo.setSelectedItem("Código del articulo");
    }
    
    private void aplicarPerCod(){
        cmbTipo.setSelectedItem("Código personalizado");
        txtCodigo.setEditable(false);
        //Asignar código personalizado
        try{
            String consulta="SELECT MAX(codigo) FROM mercancia WHERE codigo LIKE 'ALMZ-%'";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            result=cmd.executeQuery();
            if(result.next()){
                String maxCode=result.getString(1);
                if(maxCode != null){
                    int valor = Integer.parseInt(maxCode.substring(5));
                    txtCodigo.setText("ALMZ-" + (valor+1));
                }else{
                    txtCodigo.setText("ALMZ-1");
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 009: Falló la consulta de los códigos personalizados.");
        }
        
        
    }
        
    private void autorizar(){
        
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
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbMarca;
    private javax.swing.JComboBox<String> cmbPresent;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel cual1;
    private javax.swing.JLabel cual2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel logo;
    private javax.swing.JSpinner spinCant;
    private javax.swing.JTextField txtArt;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtPresent;
    // End of variables declaration//GEN-END:variables
}
