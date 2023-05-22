package code;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import java.awt.Component;
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
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ajustesAdmin extends javax.swing.JFrame {

    Inicio i;
    //Para acciones en la base de datos
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet result;
    //Validar caracteres
    boolean validarCaracter=false;
    boolean autorizar=false;
    //
    String id; boolean existe=false;
    String cargoDef="";
    
    public ajustesAdmin(Inicio inicio) {
        initComponents();
        setIconImage(getIconImage());
        this.setLocationRelativeTo(null);
        conexion=new conexionBD();
        cambiarCargoOff();
        cambiarPassOff();
        cmbBuscarCargo.setVisible(false);
        lblID.setVisible(false);
        try{
            String consulta="SELECT * FROM usuarios";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            result=cmd.executeQuery();
            llenarTabla(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al llenar la tabla.");
        }
        btnUpdatePriv.setVisible(false);
    }

    public void llenarTabla(ResultSet registros){
        String encabezado[]={"ID","Nickname","Nombre","Apellido paterno","Apellido materno","Cargo","Identificación"};
        DefaultTableModel modeloBuscar = new DefaultTableModel();
        for (String columnas : encabezado) {
            modeloBuscar.addColumn(columnas);
        }
        try{
            while(registros.next()){
                Object[] row = new Object[7];
                row[0] = registros.getString(1);
                row[1] = registros.getString(2);
                row[2] = registros.getString(3);
                row[3] = registros.getString(4);
                row[4] = registros.getString(5);
                row[5] = registros.getString(6);
                row[6] = registros.getString(7);
                modeloBuscar.addRow(row);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 008: Error al consultar materiales registrados en la base de datos.");
        }
        tblUsuarios.setModel(modeloBuscar);
        
        // Establecer ancho de columnas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        tblUsuarios.setDefaultRenderer(Object.class, renderer);
        int[] anchos = {10, 10, 10, 10, 80, 10, 120, 10, 10};
        for (int i = 0; i < tblUsuarios.getColumnCount(); i++) {
            TableColumn columna = tblUsuarios.getColumnModel().getColumn(i);
            int ancho = 0;
            // Obtener ancho máximo de columna
            for (int j = 0; j < tblUsuarios.getRowCount(); j++) {
                TableCellRenderer cellRenderer = tblUsuarios.getCellRenderer(j, i);
                Object valor = tblUsuarios.getValueAt(j, i);
                Component componente = cellRenderer.getTableCellRendererComponent(tblUsuarios, valor, false, false, j, i);
                ancho = Math.max(ancho, componente.getPreferredSize().width);
            }
            // Establecer ancho de columna
            if (ancho > 0 && ancho > anchos[i]) {
                columna.setPreferredWidth(ancho);
            } else {
                columna.setPreferredWidth(anchos[i]);
            }
            // Establecer editor por defecto para evitar edición de celdas
            columna.setCellEditor(new DefaultCellEditor(new JTextField()) {
                @Override
                public boolean isCellEditable(EventObject e) {
                    return false;
                }
            });
        }
        if(tblUsuarios.getSelectedRow()!=-1){
            jPanel3.setVisible(true);
            btnClear.setVisible(true);
        }else{
            jPanel3.setVisible(false);
            lblSelect.setText("Seleccione un usuario.");
            lblID.setVisible(false);
            btnClear.setVisible(false);
        }
    }
    
    private void cambiarCargoOff(){
        cmbEditCargo.setVisible(false);
        btnUpdateCargo.setVisible(false);
        btnEditCargo.setVisible(true);
        btnCancelarCargo.setVisible(false);
    }
    
    private void cambiarCargoOn(){
        cmbEditCargo.setVisible(true);
        btnUpdateCargo.setVisible(true);
        btnEditCargo.setVisible(false);
        btnCancelarCargo.setVisible(true);
    }
    
    private void cambiarPassOff(){
        lblPass1.setVisible(false); lblPass2.setVisible(false);
        txtPass.setVisible(false); txtConfirmar.setVisible(false);
        btnUpdatePass.setVisible(false); btnChangePass.setVisible(true);
        btnCancelarPass.setVisible(false);
    }
    
    private void cambiarPassOn(){
        lblPass1.setVisible(true); lblPass2.setVisible(true);
        txtPass.setVisible(true); txtConfirmar.setVisible(true);
        btnUpdatePass.setVisible(true); btnChangePass.setVisible(false);
        btnCancelarPass.setVisible(true);
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
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        cmbBuscarCargo = new javax.swing.JComboBox<>();
        cmbBuscarTipo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lblCargo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lblPrivilegios = new javax.swing.JLabel();
        status = new javax.swing.JCheckBox();
        modBuscar = new javax.swing.JCheckBox();
        modInOut = new javax.swing.JCheckBox();
        modConsulta = new javax.swing.JCheckBox();
        modPerCod = new javax.swing.JCheckBox();
        modAlterUsuarios = new javax.swing.JCheckBox();
        btnUpdatePriv = new javax.swing.JButton();
        btnCancelarCargo = new javax.swing.JButton();
        btnUpdateCargo = new javax.swing.JButton();
        btnEditCargo = new javax.swing.JButton();
        cmbEditCargo = new javax.swing.JComboBox<>();
        lblPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        lblPass1 = new javax.swing.JLabel();
        lblPass2 = new javax.swing.JLabel();
        txtConfirmar = new javax.swing.JPasswordField();
        btnChangePass = new javax.swing.JButton();
        btnUpdatePass = new javax.swing.JButton();
        btnCancelarPass = new javax.swing.JButton();
        lblSelect = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrador");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Ajustes del Administrador");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "   Modificar los permisos de los usuarios   ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Buscar:");

        txtBuscar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        cmbBuscarCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbBuscarCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Invitado", "Servicio Social", "Encargado de almacén", "Administrador" }));
        cmbBuscarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarCargoActionPerformed(evt);
            }
        });

        cmbBuscarTipo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbBuscarTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nickname", "Nombre", "Apellido Paterno", "Apellido Materno", "Cargo", "Boleta", " ", " " }));
        cmbBuscarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarTipoActionPerformed(evt);
            }
        });

        tblUsuarios.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(0, 0, 0));

        lblCargo.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        lblCargo.setForeground(new java.awt.Color(0, 0, 0));
        lblCargo.setText("Cargo");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N

        lblPrivilegios.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        lblPrivilegios.setForeground(new java.awt.Color(0, 0, 0));
        lblPrivilegios.setText("Privilegios");

        status.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        status.setForeground(new java.awt.Color(0, 0, 0));
        status.setText("Usuario activo");

        modBuscar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        modBuscar.setForeground(new java.awt.Color(0, 0, 0));
        modBuscar.setText("Buscar material");

        modInOut.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        modInOut.setForeground(new java.awt.Color(0, 0, 0));
        modInOut.setText("Registrar y mover material");

        modConsulta.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        modConsulta.setForeground(new java.awt.Color(0, 0, 0));
        modConsulta.setText("Consultar movimientos");

        modPerCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        modPerCod.setForeground(new java.awt.Color(0, 0, 0));
        modPerCod.setText("Modificar información");
        modPerCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modPerCodActionPerformed(evt);
            }
        });

        modAlterUsuarios.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        modAlterUsuarios.setForeground(new java.awt.Color(0, 0, 0));
        modAlterUsuarios.setText("Ajustes de administrador");
        modAlterUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modAlterUsuariosActionPerformed(evt);
            }
        });

        btnUpdatePriv.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnUpdatePriv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/update_16px.png"))); // NOI18N
        btnUpdatePriv.setText("Actualizar privilegios");
        btnUpdatePriv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePrivActionPerformed(evt);
            }
        });

        btnCancelarCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnCancelarCargo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/close_24px.png"))); // NOI18N
        btnCancelarCargo.setText("Cancelar");
        btnCancelarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCargoActionPerformed(evt);
            }
        });

        btnUpdateCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnUpdateCargo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/update_16px.png"))); // NOI18N
        btnUpdateCargo.setText("Actualizar cargo");
        btnUpdateCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCargoActionPerformed(evt);
            }
        });

        btnEditCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnEditCargo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/edit_16px.png"))); // NOI18N
        btnEditCargo.setText("Cambiar cargo");
        btnEditCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCargoActionPerformed(evt);
            }
        });

        cmbEditCargo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbEditCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Invitado", "Servicio Social", "Encargado de almacén", "Administrador" }));

        lblPass.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        lblPass.setForeground(new java.awt.Color(0, 0, 0));
        lblPass.setText("Cambiar contraseña");

        txtPass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
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

        lblPass1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lblPass1.setForeground(new java.awt.Color(0, 0, 0));
        lblPass1.setText("Nueva contraseña:");

        lblPass2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lblPass2.setForeground(new java.awt.Color(0, 0, 0));
        lblPass2.setText("Confirmar contraseña:");

        txtConfirmar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConfirmarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConfirmarKeyTyped(evt);
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

        btnUpdatePass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnUpdatePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/update_16px.png"))); // NOI18N
        btnUpdatePass.setText("Actualizar contraseña");
        btnUpdatePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePassActionPerformed(evt);
            }
        });

        btnCancelarPass.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnCancelarPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/close_24px.png"))); // NOI18N
        btnCancelarPass.setText("Cancelar");
        btnCancelarPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrivilegios, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modInOut)
                    .addComponent(modConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modAlterUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdatePriv, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCargo)
                    .addComponent(cmbEditCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPass))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblPass2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConfirmar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPass)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnUpdatePass)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelarPass, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblCargo)
                        .addGap(6, 6, 6)
                        .addComponent(cmbEditCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditCargo)
                        .addGap(6, 6, 6)
                        .addComponent(btnUpdateCargo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarCargo))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblPrivilegios)
                        .addGap(12, 12, 12)
                        .addComponent(status)
                        .addGap(6, 6, 6)
                        .addComponent(modBuscar)
                        .addGap(6, 6, 6)
                        .addComponent(modInOut)
                        .addGap(6, 6, 6)
                        .addComponent(modConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modPerCod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modAlterUsuarios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdatePriv))
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblPass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPass1)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPass2)
                            .addComponent(txtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChangePass)
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdatePass)
                            .addComponent(btnCancelarPass))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbBuscarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbBuscarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblSelect.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        lblSelect.setForeground(new java.awt.Color(0, 0, 0));
        lblSelect.setText("Editando al usuario:");

        lblID.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        lblID.setForeground(new java.awt.Color(0, 0, 0));
        lblID.setText("ID");

        btnClear.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/close_24px.png"))); // NOI18N
        btnClear.setText("Quitar selección");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClear)
                        .addGap(18, 18, 18)
                        .addComponent(lblSelect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblID)
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSelect)
                        .addComponent(lblID)
                        .addComponent(btnClear)))
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

    private void btnCancelarPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPassActionPerformed
        cambiarPassOff(); txtPass.setText(""); txtConfirmar.setText("");
    }//GEN-LAST:event_btnCancelarPassActionPerformed

    private void btnCancelarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCargoActionPerformed
        cambiarCargoOff(); cmbEditCargo.setSelectedItem(cargoDef);
    }//GEN-LAST:event_btnCancelarCargoActionPerformed

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

    private void txtConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmarKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            btnUpdatePass.grabFocus();
            btnUpdatePass.doClick();
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
                && evt.getKeyChar()!='_' && evt.getKeyChar()!='-' && evt.getKeyChar()!='.' && evt.getKeyChar()!='ñ' && evt.getKeyChar()!='Ñ'))
        {
            JOptionPane.showMessageDialog(rootPane, "No puedes usar ' " + evt.getKeyChar() + " '.");
            cadena.deleteCharAt(cadena.length());
            txtPass.setText(cadena.toString());
            cadena=null;
        }
        validarCaracter=false;
    }//GEN-LAST:event_txtPassKeyTyped

    private void txtPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyPressed
        if(evt.getKeyCode()==10){
            validarCaracter=true;
            txtConfirmar.grabFocus();
        }
        if(evt.getKeyCode()==8 || evt.getKeyCode()==127){
            validarCaracter=true;
        }
    }//GEN-LAST:event_txtPassKeyPressed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void btnUpdatePrivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePrivActionPerformed
        try{
            String save="UPDATE privilegios SET status=?, modBuscar=?, modInOut=?, modConsulta=?, modPerCod=?, modAlterUsuarios=? WHERE IDusuario=?";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(save);

            if(status.isSelected())cmd.setString(1, "S"); else cmd.setString(1, "N");
            if(modBuscar.isSelected())cmd.setString(2, "S"); else cmd.setString(2, "N");
            if(modInOut.isSelected())cmd.setString(3, "S"); else cmd.setString(3, "N");
            if(modConsulta.isSelected())cmd.setString(4, "S"); else cmd.setString(4, "N");
            if(modPerCod.isSelected())cmd.setString(5, "S"); else cmd.setString(5, "N");
            if(modAlterUsuarios.isSelected())cmd.setString(6, "S"); else cmd.setString(6, "N");
            cmd.setString(7, id);
            cmd.executeUpdate();
            JOptionPane.showMessageDialog(rootPane,"Privilegios actualizados.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al actualizar.");
        }
    }//GEN-LAST:event_btnUpdatePrivActionPerformed

    private void modAlterUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modAlterUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modAlterUsuariosActionPerformed

    private void modPerCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modPerCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modPerCodActionPerformed

    private void btnUpdateCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCargoActionPerformed
        try{
            String save="UPDATE usuarios SET cargo=? WHERE IDusuario=?";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(save);
            cmd.setString(1, cmbEditCargo.getSelectedItem().toString());
            cmd.setString(2, id);
            cmd.executeUpdate();
            JOptionPane.showMessageDialog(rootPane,"Cargo actualizado.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al actualizar.");
        }
        cambiarCargoOff();
        try{
            String consulta="SELECT * FROM usuarios";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            result=cmd.executeQuery();
            llenarTabla(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al llenar la tabla.");
        }
    }//GEN-LAST:event_btnUpdateCargoActionPerformed

    private void btnEditCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCargoActionPerformed
        cambiarCargoOn();
        cargoDef=cmbEditCargo.getSelectedItem().toString();
    }//GEN-LAST:event_btnEditCargoActionPerformed

    private void btnUpdatePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePassActionPerformed
        if(txtPass.getText().isEmpty() || txtConfirmar.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese su nueva contraseña.");
        }else{
            if(!"••••••••••".equals(txtPass.getText())){
                if(txtPass.getPassword().length>3 && txtPass.getPassword().length<26){
                    if(!(txtPass.getText().equals(txtConfirmar.getText()))){
                        JOptionPane.showMessageDialog(null, "La contraseña no coincide.");
                    }else{
                        Object[] opciones={"Si","No"};
                        ImageIcon Icono=new ImageIcon(getClass().getResource("/sources/icons/questionCube.png"));
                        int respuesta=JOptionPane.showOptionDialog(rootPane, "Está a punto de modificar una contraseña.\n¿Desea continuar?","Cambiar contraseña",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,Icono,opciones,opciones[0]);
                        if(respuesta==0){
                            Key k=new Key();
                            try{
                                String actualizar=("UPDATE usuarios SET password=? WHERE IDusuario = ?");
                                cmd=(PreparedStatement)conexion.conectar.prepareStatement(actualizar);
                                cmd.setString(2, id);
                                cmd.setString(1, k.getPassword(txtPass.getText()));
                                cmd.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Se actualizó la contraseña del usuario.");
                                cambiarPassOff();
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(null,"Error 007: Error al registrar material.");
                            }
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane, "La contraseña debe tener 4 a 25 caracteres.");
                }
            }
        }
    }//GEN-LAST:event_btnUpdatePassActionPerformed

    private void btnChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePassActionPerformed
        cambiarPassOn();
    }//GEN-LAST:event_btnChangePassActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        int fila=tblUsuarios.getSelectedRow();
        try{
            id=tblUsuarios.getValueAt(fila, 0).toString();
            String consulta="""
            SELECT u.IDusuario, u.nombre, u.paterno, u.materno, u.cargo, p.status, p.modBuscar, p.modInOut, p.modConsulta, p.modPerCod, p.modAlterUsuarios
            FROM usuarios AS u
            JOIN privilegios AS p ON u.IDusuario = p.IDusuario
            WHERE u.IDusuario = ?
            """;
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            cmd.setString(1,id);
            result=cmd.executeQuery();
            while(result.next()){
                lblID.setVisible(true);
                btnUpdatePriv.setVisible(true);
                lblSelect.setText("Editando al usuario:"); lblID.setText(result.getString(1) + " / " + result.getString(2) +" " + result.getString(3) + " " + result.getString(4));
                cmbEditCargo.setSelectedItem(result.getString(5));
                if(result.getString(6).matches("S")) status.setSelected(true);
                else status.setSelected(false);
                if(result.getString(7).matches("S")) modBuscar.setSelected(true);
                else modBuscar.setSelected(false);
                if(result.getString(8).matches("S")) modInOut.setSelected(true);
                else modInOut.setSelected(false);
                if(result.getString(9).matches("S")) modConsulta.setSelected(true);
                else modConsulta.setSelected(false);
                if(result.getString(10).matches("S")) modPerCod.setSelected(true);
                else modPerCod.setSelected(false);
                if(result.getString(11).matches("S")) modAlterUsuarios.setSelected(true);
                else modAlterUsuarios.setSelected(false);
            }
            cmd.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error en consulta");
        }
        if(tblUsuarios.getSelectedRow()!=-1){
            jPanel3.setVisible(true);
            btnClear.setVisible(true);
        }else{
            jPanel3.setVisible(false);
            lblSelect.setText("Seleccione un usuario.");
            lblID.setVisible(false);
            btnClear.setVisible(false);
        }
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void cmbBuscarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarTipoActionPerformed
        if(cmbBuscarTipo.getSelectedItem().equals("Cargo")){
            cmbBuscarCargo.setVisible(true);
            txtBuscar.setVisible(false);
            //Buscar
            String cargo=cmbBuscarCargo.getSelectedItem().toString();
            if(cargo=="Todos"){cargo="";}
            try{
                String consulta=("SELECT * FROM usuarios WHERE cargo LIKE ?");
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, cargo + "%");
                result=cmd.executeQuery();
                llenarTabla(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }else{
            cmbBuscarCargo.setVisible(false);
            txtBuscar.setVisible(true);
            //Buscar
            String tipo = cmbBuscarTipo.getSelectedItem().toString();
            switch(tipo){
                case "Nickname":            tipo = "nickname";
                break;
                case "Nombre":              tipo = "nombre";
                break;
                case "Apellido Paterno":    tipo = "paterno";
                break;
                case "Apellido Materno":    tipo = "materno";
                break;
                case "Cargo":               tipo = "cargo";
                break;
                case "Boleta":              tipo = "boleta";
                break;
            }
            try{
                String consulta=("SELECT * FROM usuarios WHERE " + tipo + " LIKE ?");
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtBuscar.getText()+"%");
                result=cmd.executeQuery();
                llenarTabla(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }
    }//GEN-LAST:event_cmbBuscarTipoActionPerformed

    private void cmbBuscarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarCargoActionPerformed
        String cargo=cmbBuscarCargo.getSelectedItem().toString();
        if(cargo=="Todos"){cargo="";}
        try{
            String consulta=("SELECT * FROM usuarios WHERE cargo LIKE ?");
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            cmd.setString(1, cargo + "%");
            result=cmd.executeQuery();
            llenarTabla(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
        }
    }//GEN-LAST:event_cmbBuscarCargoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        String tipo = cmbBuscarTipo.getSelectedItem().toString();
        switch(tipo){
            case "Nickname":            tipo = "nickname";
            break;
            case "Nombre":              tipo = "nombre";
            break;
            case "Apellido Paterno":    tipo = "paterno";
            break;
            case "Apellido Materno":    tipo = "materno";
            break;
            case "Cargo":               tipo = "cargo";
            break;
            case "Boleta":              tipo = "boleta";
            break;
        }

        try{
            String consulta=("SELECT * FROM usuarios WHERE " + tipo + " LIKE ?");
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            cmd.setString(1, txtBuscar.getText()+"%");
            result=cmd.executeQuery();
            llenarTabla(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        tblUsuarios.clearSelection();
        if(tblUsuarios.getSelectedRow()!=-1){
            jPanel3.setVisible(true);
            btnClear.setVisible(true);
        }else{
            jPanel3.setVisible(false);
            lblSelect.setText("Seleccione un usuario.");
            lblID.setVisible(false);
            btnClear.setVisible(false);
        }
    }//GEN-LAST:event_btnClearActionPerformed

    
    
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
    private javax.swing.JButton btnCancelarCargo;
    private javax.swing.JButton btnCancelarPass;
    private javax.swing.JButton btnChangePass;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEditCargo;
    private javax.swing.JButton btnUpdateCargo;
    private javax.swing.JButton btnUpdatePass;
    private javax.swing.JButton btnUpdatePriv;
    private javax.swing.JComboBox<String> cmbBuscarCargo;
    private javax.swing.JComboBox<String> cmbBuscarTipo;
    private javax.swing.JComboBox<String> cmbEditCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblPass1;
    private javax.swing.JLabel lblPass2;
    private javax.swing.JLabel lblPrivilegios;
    private javax.swing.JLabel lblSelect;
    private javax.swing.JCheckBox modAlterUsuarios;
    private javax.swing.JCheckBox modBuscar;
    private javax.swing.JCheckBox modConsulta;
    private javax.swing.JCheckBox modInOut;
    private javax.swing.JCheckBox modPerCod;
    private javax.swing.JCheckBox status;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JPasswordField txtConfirmar;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
