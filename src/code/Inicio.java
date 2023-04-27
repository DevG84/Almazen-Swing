package code;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import settings.conexionBD;

public class Inicio extends javax.swing.JFrame {
    
    //Para acciones en la base de datos
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet result, resultData, marca_res;
    
    //Modulos
    public boolean m1=false,m2=false,m3=false,m4=false,m5=false;
    private final Color unused,used,selected;
    
    //Bienvenida
    public String welcomeUser="";
    
    //Datos del usuario
    public String user="";
    public String id="",nickname="",nombre="",paterno="",materno="",cargo="",boleta="";

    public Inicio(String usuario){
        this.user = usuario;
        unused=new Color(255, 255, 255);
        used=new Color(234,237,237);
        selected=new Color(234,237,237);
        conexion=new conexionBD();
        initComponents();
        setExtendedState(this.MAXIMIZED_BOTH);
        setUsuario(user);
        iniciarInterfaz();
        setIconImage(getIconImage());
        this.setLocationRelativeTo(null);
        
        
        
    }

    //Interfaces
    public void iniciarInterfaz(){
        setImageIn(logo, "src/sources/logo.png");
        //
        Pestañas.setEnabledAt(0, false);
        Pestañas.setEnabledAt(1, false);
        Pestañas.setEnabledAt(2, false);
        Pestañas.setEnabledAt(3, false);
        Pestañas.setEnabledAt(4, false);
        Pestañas.setSelectedIndex(0);
        changeButtonColor();
        //Inicio
        lblBienvenida.setText("Hola " + nombre + ", bienvenido/a.");
        
        //Buscar
        
    }
    
    private void iniciarBuscar(){
        llenarMarca();
        //Llena la tabla con todo el material de la base de datos
        try{
            String materiales="SELECT * FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
            result=cmd.executeQuery();
            llenarTablaBuscar(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
        }
    }
    
    //Funciones utiles
    public void llenarTablaBuscar(ResultSet registros){
        String encabezado[]={"Código","Artículo","Descripción","Marca","Presentación","Existencia","Anaquel","Repisa"};
        DefaultTableModel modeloBuscar = new DefaultTableModel();
        for (String columnas : encabezado) {
            modeloBuscar.addColumn(columnas);
        }
        try{
            while(registros.next()){
                Object[] row = new Object[8];
                row[0] = registros.getString(2);
                row[1] = registros.getString(3);
                row[2] = registros.getString(4);
                row[3] = registros.getString(5);
                row[4] = registros.getString(6);
                row[5] = registros.getString(7);
                row[6] = registros.getString(8);
                row[7] = registros.getString(9);
                modeloBuscar.addRow(row);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 008: Error al consultar materiales registrados en la base de datos.");
        }
        tblBuscar.setModel(modeloBuscar);
        
        // Establecer ancho de columnas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        tblBuscar.setDefaultRenderer(Object.class, renderer);
        int[] anchos = {80, 150, 300, 100, 120, 70, 70, 60};
        for (int i = 0; i < tblBuscar.getColumnCount(); i++) {
            TableColumn columna = tblBuscar.getColumnModel().getColumn(i);
            int ancho = 0;
            // Obtener ancho máximo de columna
            for (int j = 0; j < tblBuscar.getRowCount(); j++) {
                TableCellRenderer cellRenderer = tblBuscar.getCellRenderer(j, i);
                Object valor = tblBuscar.getValueAt(j, i);
                Component componente = cellRenderer.getTableCellRendererComponent(tblBuscar, valor, false, false, j, i);
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
        //
    }
    
    private void llenarMarca(){
        cmbBuscarMarca.removeAllItems();
        try{
            String consulta="SELECT DISTINCT marca FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            marca_res=cmd.executeQuery();
            //Recorrer los resultados y agregar cada valor único de "marca" al JComboBox
            cmbBuscarMarca.addItem(null);
            while (marca_res.next()) {
                cmbBuscarMarca.addItem(marca_res.getString(1));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 004: Error al obtener las marcas registradas en la base de datos.");
        }
    }
    
    //
    public void setUsuario(String usuario) {
        this.user = usuario;
        try{
            String obtener="SELECT IDusuario,nickname,nombre,paterno,materno,cargo,boleta FROM usuarios WHERE nickname LIKE ? ";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(obtener);
            cmd.setString(1, user);
            resultData=cmd.executeQuery();
            while (resultData.next()) {
                //Procesa los datos de la consulta
                id=resultData.getString(1);
                nickname=resultData.getString(2);
                nombre=resultData.getString(3);
                paterno=resultData.getString(4);
                materno=resultData.getString(5);
                cargo=resultData.getString(6);
                boleta=resultData.getString(7);
            }
            cmd.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error: 002");
        }
    }
    
    private void resetButtonColor(){
        btnDisplayInicio.setBackground(unused);
        btnDisplayBuscar.setBackground(unused);
        btnDisplayMov.setBackground(unused);
        btnDisplayConsulta.setBackground(unused);
        btnDisplayCodes.setBackground(unused);
    }
    
    private void changeButtonColor(){
        if(Pestañas.getSelectedIndex()==0){resetButtonColor(); btnDisplayInicio.setBackground(used);}
        if(Pestañas.getSelectedIndex()==1){resetButtonColor(); btnDisplayBuscar.setBackground(used);}
        if(Pestañas.getSelectedIndex()==2){resetButtonColor(); btnDisplayMov.setBackground(used);}
        if(Pestañas.getSelectedIndex()==3){resetButtonColor(); btnDisplayConsulta.setBackground(used);}
        if(Pestañas.getSelectedIndex()==4){resetButtonColor(); btnDisplayCodes.setBackground(used);}
    }
    
    public void habilitarModulos(){
        btnDisplayBuscar.setEnabled(m1);
        btnDisplayMov.setEnabled(m2);
        btnDisplayRegNew.setEnabled(m2);
        btnDisplayConsulta.setEnabled(m3);
        btnDisplayCodes.setEnabled(m4);
        btnDisplayAdmin.setEnabled(m5);
        btnDisplayAdmin.setVisible(m5);
    }
    
    
    //Logo del JFrame
    @Override
    public Image getIconImage(){
        Image retValue=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icon_32px.png"));
        return retValue;
    }
    //

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        btnDisplayMov = new javax.swing.JButton();
        btnDisplayBuscar = new javax.swing.JButton();
        btnDisplayConsulta = new javax.swing.JButton();
        btnDisplayCodes = new javax.swing.JButton();
        btnDisplayAdmin = new javax.swing.JButton();
        btnDisplayInicio = new javax.swing.JButton();
        btnDisplayRegNew = new javax.swing.JButton();
        btnDisplaySettings = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Pestañas = new javax.swing.JTabbedPane();
        panelInicio = new javax.swing.JPanel();
        lblBienvenida = new javax.swing.JLabel();
        btnAboutUs = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        panelBuscar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarCodigo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBuscarArticulo = new javax.swing.JTextField();
        cmbBuscarTipo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbBuscarMarca = new javax.swing.JComboBox<>();
        btnBorrar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuscar = new javax.swing.JTable();
        panelMov = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelConsulta = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelCodes = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Almazen");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        logo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        logo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoMouseClicked(evt);
            }
        });

        btnDisplayMov.setBackground(new java.awt.Color(234, 237, 237));
        btnDisplayMov.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplayMov.setText("Realizar movimientos");
        btnDisplayMov.setBorder(null);
        btnDisplayMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplayMov.setRolloverEnabled(false);
        btnDisplayMov.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnDisplayMovMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnDisplayMovMouseMoved(evt);
            }
        });
        btnDisplayMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisplayMovMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisplayMovMouseExited(evt);
            }
        });
        btnDisplayMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayMovActionPerformed(evt);
            }
        });

        btnDisplayBuscar.setBackground(new java.awt.Color(234, 237, 237));
        btnDisplayBuscar.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplayBuscar.setText("Buscar material");
        btnDisplayBuscar.setBorder(null);
        btnDisplayBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplayBuscar.setRolloverEnabled(false);
        btnDisplayBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisplayBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisplayBuscarMouseExited(evt);
            }
        });
        btnDisplayBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayBuscarActionPerformed(evt);
            }
        });

        btnDisplayConsulta.setBackground(new java.awt.Color(234, 237, 237));
        btnDisplayConsulta.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplayConsulta.setText("Consultar movimientos");
        btnDisplayConsulta.setBorder(null);
        btnDisplayConsulta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplayConsulta.setRolloverEnabled(false);
        btnDisplayConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisplayConsultaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisplayConsultaMouseExited(evt);
            }
        });
        btnDisplayConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayConsultaActionPerformed(evt);
            }
        });

        btnDisplayCodes.setBackground(new java.awt.Color(234, 237, 237));
        btnDisplayCodes.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplayCodes.setText("Códigos perzonalizados");
        btnDisplayCodes.setBorder(null);
        btnDisplayCodes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplayCodes.setRolloverEnabled(false);
        btnDisplayCodes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisplayCodesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisplayCodesMouseExited(evt);
            }
        });
        btnDisplayCodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayCodesActionPerformed(evt);
            }
        });

        btnDisplayAdmin.setBackground(new java.awt.Color(119, 156, 214));
        btnDisplayAdmin.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplayAdmin.setForeground(new java.awt.Color(255, 255, 255));
        btnDisplayAdmin.setText("Ajustes del Administrador");
        btnDisplayAdmin.setBorder(null);
        btnDisplayAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplayAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayAdminActionPerformed(evt);
            }
        });

        btnDisplayInicio.setBackground(new java.awt.Color(234, 237, 237));
        btnDisplayInicio.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplayInicio.setText("Inicio");
        btnDisplayInicio.setBorder(null);
        btnDisplayInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplayInicio.setRolloverEnabled(false);
        btnDisplayInicio.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnDisplayInicioMouseMoved(evt);
            }
        });
        btnDisplayInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisplayInicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisplayInicioMouseExited(evt);
            }
        });
        btnDisplayInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayInicioActionPerformed(evt);
            }
        });

        btnDisplayRegNew.setBackground(new java.awt.Color(255, 234, 200));
        btnDisplayRegNew.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplayRegNew.setText("Registrar nuevo material");
        btnDisplayRegNew.setBorder(null);
        btnDisplayRegNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplayRegNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayRegNewActionPerformed(evt);
            }
        });

        btnDisplaySettings.setBackground(new java.awt.Color(255, 234, 200));
        btnDisplaySettings.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnDisplaySettings.setText("Ajustes");
        btnDisplaySettings.setBorder(null);
        btnDisplaySettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisplaySettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplaySettingsActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(255, 90, 78));
        btnLogout.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Cerrar sesión");
        btnLogout.setBorder(null);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDisplayInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDisplayBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDisplayMov, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDisplayConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDisplayCodes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDisplayRegNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDisplayAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
            .addComponent(btnDisplaySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnDisplayInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDisplayBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDisplayMov, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDisplayConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDisplayCodes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDisplayRegNew, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDisplayAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDisplaySettings, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        Pestañas.setBackground(new java.awt.Color(51, 51, 51));

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));

        lblBienvenida.setFont(new java.awt.Font("Microsoft YaHei", 0, 24)); // NOI18N
        lblBienvenida.setText("Bienvenido a Almazen");

        btnAboutUs.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        btnAboutUs.setText("Acerca de Almazen");
        btnAboutUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutUsActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/progress.gif"))); // NOI18N

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio);
        panelInicio.setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInicioLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAboutUs))
                    .addGroup(panelInicioLayout.createSequentialGroup()
                        .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInicioLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5))
                            .addComponent(lblBienvenida))
                        .addGap(0, 911, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBienvenida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 474, Short.MAX_VALUE)
                .addComponent(btnAboutUs)
                .addContainerGap())
        );

        Pestañas.addTab("Inicio", panelInicio);

        panelBuscar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel1.setText("Código:");

        txtBuscarCodigo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBuscarCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarCodigoMouseClicked(evt);
            }
        });
        txtBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCodigoActionPerformed(evt);
            }
        });
        txtBuscarCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCodigoKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel6.setText("Artículo:");

        txtBuscarArticulo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBuscarArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarArticuloMouseClicked(evt);
            }
        });
        txtBuscarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarArticuloActionPerformed(evt);
            }
        });
        txtBuscarArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarArticuloKeyReleased(evt);
            }
        });

        cmbBuscarTipo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbBuscarTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Descripción", "Por Nombre" }));
        cmbBuscarTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBuscarTipoItemStateChanged(evt);
            }
        });
        cmbBuscarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarTipoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel7.setText("Marca:");

        cmbBuscarMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbBuscarMarcaMouseClicked(evt);
            }
        });
        cmbBuscarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarMarcaActionPerformed(evt);
            }
        });

        btnBorrar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnBorrar.setText("Borrar selección");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        tblBuscar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        tblBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBuscar);

        javax.swing.GroupLayout panelBuscarLayout = new javax.swing.GroupLayout(panelBuscar);
        panelBuscar.setLayout(panelBuscarLayout);
        panelBuscarLayout.setHorizontalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBuscarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBuscarLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBuscarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBuscarLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelBuscarLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        panelBuscarLayout.setVerticalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtBuscarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cmbBuscarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addContainerGap())
        );

        Pestañas.addTab("Buscar", panelBuscar);

        panelMov.setBackground(new java.awt.Color(255, 255, 255));
        panelMov.setForeground(new java.awt.Color(51, 51, 51));

        jLabel2.setText("Movimientos");

        javax.swing.GroupLayout panelMovLayout = new javax.swing.GroupLayout(panelMov);
        panelMov.setLayout(panelMovLayout);
        panelMovLayout.setHorizontalGroup(
            panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(1101, Short.MAX_VALUE))
        );
        panelMovLayout.setVerticalGroup(
            panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(669, Short.MAX_VALUE))
        );

        Pestañas.addTab("Movimientos", panelMov);

        panelConsulta.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Consulta");

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(1124, Short.MAX_VALUE))
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(669, Short.MAX_VALUE))
        );

        Pestañas.addTab("Consulta", panelConsulta);

        panelCodes.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Códigos");

        javax.swing.GroupLayout panelCodesLayout = new javax.swing.GroupLayout(panelCodes);
        panelCodes.setLayout(panelCodesLayout);
        panelCodesLayout.setHorizontalGroup(
            panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(1127, Short.MAX_VALUE))
        );
        panelCodesLayout.setVerticalGroup(
            panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(669, Short.MAX_VALUE))
        );

        Pestañas.addTab("Códigos almazen", panelCodes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(Pestañas))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(Pestañas))
        );

        /*
        Pestañas.setTabPlacement(JTabbedPane.TOP);
        Pestañas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        Pestañas.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
                return 0;
            }
        });
        */

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

    private void btnDisplayMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayMovActionPerformed
        Pestañas.setSelectedIndex(2);
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayMovActionPerformed

    private void btnDisplayBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayBuscarActionPerformed
        Pestañas.setSelectedIndex(1);
        changeButtonColor();
        iniciarBuscar();
    }//GEN-LAST:event_btnDisplayBuscarActionPerformed

    private void btnDisplayConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayConsultaActionPerformed
        Pestañas.setSelectedIndex(3);
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayConsultaActionPerformed

    private void btnDisplayAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayAdminActionPerformed
        ajustesAdmin admin=new ajustesAdmin();
        admin.setVisible(true);
    }//GEN-LAST:event_btnDisplayAdminActionPerformed

    private void btnDisplayInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayInicioActionPerformed
        Pestañas.setSelectedIndex(0);
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayInicioActionPerformed

    private void btnDisplaySettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplaySettingsActionPerformed
        ajustesUsuario userSettings=new ajustesUsuario();
        userSettings.setVisible(true);
    }//GEN-LAST:event_btnDisplaySettingsActionPerformed

    private void btnDisplayRegNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayRegNewActionPerformed
        registrarMaterial m=new registrarMaterial(this);
        m.setVisible(true);
    }//GEN-LAST:event_btnDisplayRegNewActionPerformed

    private void btnDisplayCodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayCodesActionPerformed
        Pestañas.setSelectedIndex(4);
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayCodesActionPerformed

    private void btnDisplayInicioMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayInicioMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDisplayInicioMouseMoved

    private void btnDisplayMovMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayMovMouseDragged
        
    }//GEN-LAST:event_btnDisplayMovMouseDragged

    private void btnDisplayMovMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayMovMouseMoved
        
    }//GEN-LAST:event_btnDisplayMovMouseMoved

    private void btnDisplayMovMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayMovMouseEntered
        btnDisplayMov.setBackground(selected);
    }//GEN-LAST:event_btnDisplayMovMouseEntered

    private void btnDisplayMovMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayMovMouseExited
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayMovMouseExited

    private void btnDisplayInicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayInicioMouseEntered
        btnDisplayInicio.setBackground(selected);
    }//GEN-LAST:event_btnDisplayInicioMouseEntered

    private void btnDisplayBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayBuscarMouseEntered
        btnDisplayBuscar.setBackground(selected);
    }//GEN-LAST:event_btnDisplayBuscarMouseEntered

    private void btnDisplayConsultaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayConsultaMouseEntered
        btnDisplayConsulta.setBackground(selected);
    }//GEN-LAST:event_btnDisplayConsultaMouseEntered

    private void btnDisplayCodesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayCodesMouseEntered
        btnDisplayCodes.setBackground(selected);
    }//GEN-LAST:event_btnDisplayCodesMouseEntered

    private void btnDisplayInicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayInicioMouseExited
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayInicioMouseExited

    private void btnDisplayBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayBuscarMouseExited
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayBuscarMouseExited

    private void btnDisplayConsultaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayConsultaMouseExited
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayConsultaMouseExited

    private void btnDisplayCodesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisplayCodesMouseExited
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayCodesMouseExited

    private void logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoMouseClicked
        Pestañas.setSelectedIndex(0);
        changeButtonColor();
    }//GEN-LAST:event_logoMouseClicked

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        Object[] opciones={"Si","No"};
        ImageIcon Icono=new ImageIcon("src/sources/icons/questionCube.png");
        int respuesta=JOptionPane.showOptionDialog(rootPane, "¿Quiere cerrar sesión?","Cerrar sesión",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,Icono,opciones,opciones[0]);
        if(respuesta==0){
            Login l=new Login();
            this.dispose();
            l.setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnAboutUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutUsActionPerformed
        About a=new About();
        a.setVisible(true);
    }//GEN-LAST:event_btnAboutUsActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        txtBuscarCodigo.setText(""); txtBuscarArticulo.setText(""); cmbBuscarTipo.setSelectedItem("Por Descripción"); cmbBuscarMarca.setSelectedItem(null);
        
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void txtBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoActionPerformed

    private void txtBuscarCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCodigoKeyReleased
        try{
            String consulta="SELECT * FROM mercancia WHERE codigo LIKE ?";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            cmd.setString(1, txtBuscarCodigo.getText() + "%");
            result=cmd.executeQuery();
            llenarTablaBuscar(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
        }
    }//GEN-LAST:event_txtBuscarCodigoKeyReleased

    private void txtBuscarArticuloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarArticuloKeyReleased
        switch(cmbBuscarTipo.getSelectedItem().toString()){
            case "Por Descripción":
                try{
                    String consulta="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
            case "Por Nombre":
                try{
                    String consulta="SELECT * FROM mercancia WHERE articulo LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
        }
    }//GEN-LAST:event_txtBuscarArticuloKeyReleased

    private void cmbBuscarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarTipoActionPerformed
        switch(cmbBuscarTipo.getSelectedItem().toString()){
            case "Por Descripción" -> {
                try{
                    String consulta="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, "%" + txtBuscarArticulo.getText() + "%");
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            }
            case "Por Nombre" -> {
                try{
                    String consulta="SELECT * FROM mercancia WHERE articulo LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, "%" + txtBuscarArticulo.getText() + "%");
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            }
        }
    }//GEN-LAST:event_cmbBuscarTipoActionPerformed

    private void cmbBuscarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarMarcaActionPerformed
        if(cmbBuscarMarca.getSelectedItem() == null){
            try{
                String consulta="SELECT * FROM mercancia";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                result=cmd.executeQuery();
                llenarTablaBuscar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }else{
            try{
                String consulta="SELECT * FROM mercancia WHERE marca LIKE ?";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, "%" + cmbBuscarMarca.getSelectedItem().toString() + "%");
                result=cmd.executeQuery();
                llenarTablaBuscar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }
    }//GEN-LAST:event_cmbBuscarMarcaActionPerformed

    private void txtBuscarCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCodigoMouseClicked
        txtBuscarArticulo.setText(""); cmbBuscarTipo.setSelectedItem("Por Descripción"); cmbBuscarMarca.setSelectedItem(null);
    }//GEN-LAST:event_txtBuscarCodigoMouseClicked

    private void txtBuscarArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarArticuloMouseClicked
        txtBuscarCodigo.setText(""); cmbBuscarMarca.setSelectedItem(null); txtBuscarArticulo.setText("");
    }//GEN-LAST:event_txtBuscarArticuloMouseClicked

    private void cmbBuscarMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbBuscarMarcaMouseClicked
        txtBuscarCodigo.setText(""); txtBuscarArticulo.setText("");
    }//GEN-LAST:event_cmbBuscarMarcaMouseClicked

    private void cmbBuscarTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBuscarTipoItemStateChanged
        txtBuscarArticulo.grabFocus();
    }//GEN-LAST:event_cmbBuscarTipoItemStateChanged

    private void txtBuscarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarArticuloActionPerformed
    
    public void setImageIn(JLabel a,String route){
        ImageIcon img; Icon icono;
        img=new ImageIcon(route);
        icono=new ImageIcon(img.getImage().getScaledInstance(a.getWidth(), a.getHeight(), Image.SCALE_DEFAULT));
        a.setIcon(icono);
        this.repaint();
    }
    
    public static void main(String args[]) {
        String usuario="";
        /* Look and Feel */
        FlatArcIJTheme.setup();

        // create UI here...

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Inicio(usuario).setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTabbedPane Pestañas;
    private javax.swing.JButton btnAboutUs;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnDisplayAdmin;
    private javax.swing.JButton btnDisplayBuscar;
    private javax.swing.JButton btnDisplayCodes;
    private javax.swing.JButton btnDisplayConsulta;
    private javax.swing.JButton btnDisplayInicio;
    private javax.swing.JButton btnDisplayMov;
    private javax.swing.JButton btnDisplayRegNew;
    private javax.swing.JButton btnDisplaySettings;
    private javax.swing.JButton btnLogout;
    private javax.swing.JComboBox<String> cmbBuscarMarca;
    private javax.swing.JComboBox<String> cmbBuscarTipo;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panelBuscar;
    private javax.swing.JPanel panelCodes;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelMov;
    private javax.swing.JTable tblBuscar;
    private javax.swing.JTextField txtBuscarArticulo;
    private javax.swing.JTextField txtBuscarCodigo;
    // End of variables declaration//GEN-END:variables
}
