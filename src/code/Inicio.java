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
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
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
    //Para realizar movimientos
    private Map<String, Integer> tabla2Data = new HashMap<>();
    

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
        llenarMarca(cmbBuscarMarca);
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
    
    private void iniciarMovimientos(){
        llenarMarca(cmbBuscarMarcaMov);
        cmbTipoMov.addItem(null);
        cmbTipoMov.addItem("Entrada");
        cmbTipoMov.addItem("Salida");
        try{
            String materiales="SELECT * FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
            result=cmd.executeQuery();
            llenarTablaSeleccionar(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
        }
    }
    
    //Funciones utiles
    public void llenarTablaBuscar(ResultSet registros){
        String encabezado[]={"Código","Artículo","Descripción","Marca","Presentación","Existencia","Almacén","Anaquel","Repisa"};
        DefaultTableModel modeloBuscar = new DefaultTableModel();
        for (String columnas : encabezado) {
            modeloBuscar.addColumn(columnas);
        }
        try{
            while(registros.next()){
                Object[] row = new Object[9];
                row[0] = registros.getString(2);
                row[1] = registros.getString(3);
                row[2] = registros.getString(4);
                row[3] = registros.getString(5);
                row[4] = registros.getString(6);
                row[5] = registros.getString(7);
                row[6] = registros.getString(8);
                row[7] = registros.getString(9);
                row[8] = registros.getString(10);
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
        int[] anchos = {80, 150, 300, 100, 80, 10, 120, 10, 10};
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
    
    private void llenarMarca(JComboBox cmbBuscarMarca){
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
        jLabel8 = new javax.swing.JLabel();
        cmbAlmacen = new javax.swing.JComboBox<>();
        panelMov = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSeleccionarMaterial = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMoverMaterial = new javax.swing.JTable();
        btnMover = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtBuscarCodigoMov = new javax.swing.JTextField();
        cmbAlmacenMov = new javax.swing.JComboBox<>();
        txtBuscarArticuloMov = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbBuscarTipoMov = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cmbBuscarMarcaMov = new javax.swing.JComboBox<>();
        btnBorrarMov = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnAddMov = new javax.swing.JButton();
        btnDeleteMov = new javax.swing.JButton();
        btnRestMov = new javax.swing.JButton();
        spinCantRest = new javax.swing.JSpinner();
        spinCantAdd = new javax.swing.JSpinner();
        cmbTipoMov = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        btnSumMov = new javax.swing.JButton();
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
                        .addGap(0, 986, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBienvenida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 669, Short.MAX_VALUE)
                .addComponent(btnAboutUs)
                .addContainerGap())
        );

        Pestañas.addTab("Inicio", panelInicio);

        panelBuscar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Código:");

        txtBuscarCodigo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBuscarCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarCodigoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtBuscarCodigoMousePressed(evt);
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
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
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
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
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

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

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

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Almacén ubicado:");

        cmbAlmacen.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbAlmacen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Zona 100 (Principal)", "Zona 100 (Bodega)", "Edificio A" }));
        cmbAlmacen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAlmacenItemStateChanged(evt);
            }
        });
        cmbAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAlmacenActionPerformed(evt);
            }
        });

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
                                .addComponent(txtBuscarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBuscarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBuscarLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrar)
                    .addComponent(jLabel8)
                    .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addContainerGap())
        );

        Pestañas.addTab("Buscar", panelBuscar);

        panelMov.setBackground(new java.awt.Color(255, 255, 255));
        panelMov.setForeground(new java.awt.Color(51, 51, 51));

        tblSeleccionarMaterial.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        tblSeleccionarMaterial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Artículo", "Descripción", "Marca", "Existencia", "Presentación"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblSeleccionarMaterial);
        if (tblSeleccionarMaterial.getColumnModel().getColumnCount() > 0) {
            tblSeleccionarMaterial.getColumnModel().getColumn(0).setResizable(false);
            tblSeleccionarMaterial.getColumnModel().getColumn(1).setResizable(false);
            tblSeleccionarMaterial.getColumnModel().getColumn(2).setResizable(false);
            tblSeleccionarMaterial.getColumnModel().getColumn(3).setResizable(false);
            tblSeleccionarMaterial.getColumnModel().getColumn(4).setResizable(false);
            tblSeleccionarMaterial.getColumnModel().getColumn(5).setResizable(false);
        }

        tblMoverMaterial.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        tblMoverMaterial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Artículo", "Descripción", "Marca", "Presentación", "Cantidad", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblMoverMaterial);
        if (tblMoverMaterial.getColumnModel().getColumnCount() > 0) {
            tblMoverMaterial.getColumnModel().getColumn(0).setResizable(false);
            tblMoverMaterial.getColumnModel().getColumn(1).setResizable(false);
            tblMoverMaterial.getColumnModel().getColumn(2).setResizable(false);
            tblMoverMaterial.getColumnModel().getColumn(3).setResizable(false);
            tblMoverMaterial.getColumnModel().getColumn(4).setResizable(false);
            tblMoverMaterial.getColumnModel().getColumn(5).setResizable(false);
            tblMoverMaterial.getColumnModel().getColumn(6).setResizable(false);
        }

        btnMover.setText("Realizar Movimiento");
        btnMover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoverActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Código:");

        txtBuscarCodigoMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBuscarCodigoMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarCodigoMovMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtBuscarCodigoMovMousePressed(evt);
            }
        });
        txtBuscarCodigoMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCodigoMovActionPerformed(evt);
            }
        });
        txtBuscarCodigoMov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCodigoMovKeyReleased(evt);
            }
        });

        cmbAlmacenMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbAlmacenMov.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zona 100 (Principal)", "Zona 100 (Bodega)", "Edificio A" }));
        cmbAlmacenMov.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAlmacenMovItemStateChanged(evt);
            }
        });
        cmbAlmacenMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAlmacenMovActionPerformed(evt);
            }
        });

        txtBuscarArticuloMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtBuscarArticuloMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarArticuloMovMouseClicked(evt);
            }
        });
        txtBuscarArticuloMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarArticuloMovActionPerformed(evt);
            }
        });
        txtBuscarArticuloMov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarArticuloMovKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Artículo:");

        cmbBuscarTipoMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbBuscarTipoMov.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Descripción", "Por Nombre" }));
        cmbBuscarTipoMov.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBuscarTipoMovItemStateChanged(evt);
            }
        });
        cmbBuscarTipoMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarTipoMovActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Marca:");

        cmbBuscarMarcaMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbBuscarMarcaMovMouseClicked(evt);
            }
        });
        cmbBuscarMarcaMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarMarcaMovActionPerformed(evt);
            }
        });

        btnBorrarMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnBorrarMov.setText("Borrar selección");
        btnBorrarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarMovActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Almacén ubicado:");

        jLabel12.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Material Seleccionado");

        jLabel13.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Material disponible");

        btnAddMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnAddMov.setText("Añadir");
        btnAddMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMovActionPerformed(evt);
            }
        });

        btnDeleteMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDeleteMov.setText("Eliminar");
        btnDeleteMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMovActionPerformed(evt);
            }
        });

        btnRestMov.setText("Disminuir");
        btnRestMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestMovActionPerformed(evt);
            }
        });

        spinCantRest.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        spinCantRest.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        spinCantAdd.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        spinCantAdd.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        cmbTipoMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbTipoMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMovActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Tipo:");

        btnSumMov.setText("Aumentar");
        btnSumMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumMovActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMovLayout = new javax.swing.GroupLayout(panelMov);
        panelMov.setLayout(panelMovLayout);
        panelMovLayout.setHorizontalGroup(
            panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovLayout.createSequentialGroup()
                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelMovLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMovLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarCodigoMov, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarArticuloMov, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBuscarTipoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBuscarMarcaMov, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelMovLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbAlmacenMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBorrarMov))
                            .addGroup(panelMovLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelMovLayout.createSequentialGroup()
                                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1052, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1052, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelMovLayout.createSequentialGroup()
                                        .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelMovLayout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(panelMovLayout.createSequentialGroup()
                                                        .addComponent(jLabel14)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cmbTipoMov, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addComponent(spinCantAdd)
                                                    .addComponent(btnAddMov, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(btnSumMov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(spinCantRest)
                                                .addComponent(btnRestMov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnDeleteMov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 44, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnMover)))))))
                .addContainerGap())
        );
        panelMovLayout.setVerticalGroup(
            panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscarCodigoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtBuscarArticuloMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBuscarTipoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cmbBuscarMarcaMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cmbAlmacenMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrarMov))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMovLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(panelMovLayout.createSequentialGroup()
                        .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinCantAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddMov)))
                .addGap(12, 12, 12)
                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(panelMovLayout.createSequentialGroup()
                        .addComponent(spinCantRest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSumMov)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRestMov)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteMov)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMover)))
                .addContainerGap())
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
                .addContainerGap(1199, Short.MAX_VALUE))
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(864, Short.MAX_VALUE))
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
                .addContainerGap(1202, Short.MAX_VALUE))
        );
        panelCodesLayout.setVerticalGroup(
            panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(864, Short.MAX_VALUE))
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
        iniciarMovimientos();
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
        cmbAlmacen.setSelectedItem("Todos");
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void txtBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoActionPerformed

    private void txtBuscarCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCodigoKeyReleased
        String consulta;
        try{
            if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ?";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtBuscarCodigo.getText() + "%");
            }else{
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ?";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtBuscarCodigo.getText() + "%");
                cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
            }
            result=cmd.executeQuery();
            llenarTablaBuscar(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
        }
    }//GEN-LAST:event_txtBuscarCodigoKeyReleased

    private void txtBuscarArticuloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarArticuloKeyReleased
        String consulta;
        switch(cmbBuscarTipo.getSelectedItem().toString()){
            case "Por Descripción":
                try{
                    if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                        cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
            case "Por Nombre":
                try{
                    if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                        cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
        }
    }//GEN-LAST:event_txtBuscarArticuloKeyReleased

    private void cmbBuscarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarTipoActionPerformed
        String consulta;
        switch(cmbBuscarTipo.getSelectedItem().toString()){
            case "Por Descripción":
                try{
                    if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                        cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
            case "Por Nombre":
                try{
                    if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                        cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
        }
    }//GEN-LAST:event_cmbBuscarTipoActionPerformed

    private void cmbBuscarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarMarcaActionPerformed
        String consulta;
        if(cmbBuscarMarca.getSelectedItem() == null){
            try{
                if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                    consulta="SELECT * FROM mercancia";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                }else{
                    consulta="SELECT * FROM mercancia WHERE almacen LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbAlmacen.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaBuscar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }else{
            try{
                if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbBuscarMarca.getSelectedItem().toString());
                }else{
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbBuscarMarca.getSelectedItem().toString());
                    cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaBuscar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }
    }//GEN-LAST:event_cmbBuscarMarcaActionPerformed

    private void txtBuscarCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCodigoMouseClicked
        txtBuscarCodigo.setText(""); txtBuscarArticulo.setText(""); cmbBuscarTipo.setSelectedItem("Por Descripción"); cmbBuscarMarca.setSelectedItem(null);
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

    private void txtBuscarCodigoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCodigoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoMousePressed

    private void cmbAlmacenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAlmacenItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAlmacenItemStateChanged

    private void cmbAlmacenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAlmacenActionPerformed
        String materiales;
        //Si todo está vacio
        if(txtBuscarCodigo.getText().isEmpty() && txtBuscarArticulo.getText().isEmpty() && cmbBuscarMarca.getSelectedItem()==null){
            try{
                if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                    materiales="SELECT * FROM mercancia";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                }else{
                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                    cmd.setString(1, cmbAlmacen.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaBuscar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane, "Error al consultar..");
            }
        }else{
            //Si solo el código está escrito
            if(txtBuscarArticulo.getText().isEmpty() && cmbBuscarMarca.getSelectedItem()==null){
                try{
                    if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtBuscarCodigo.getText() + "%");
                    }else{
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtBuscarCodigo.getText() + "%");
                        cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaBuscar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            }else{
                //Si solo el articulo está escrito
                if(txtBuscarCodigo.getText().isEmpty() && cmbBuscarMarca.getSelectedItem()==null){
                    switch(cmbBuscarTipo.getSelectedItem().toString()){
                        case "Por Descripción":
                            try{
                                if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                                    cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaBuscar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        break;
                        case "Por Nombre":
                            try{
                                if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                                    cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaBuscar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        break;
                    }
                }else{
                    //Si solo está seleccionada la marca
                    if(txtBuscarCodigo.getText().isEmpty() && txtBuscarArticulo.getText().isEmpty()){
                        if(cmbBuscarMarca.getSelectedItem() == null){
                            try{
                                if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbAlmacen.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaBuscar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        }else{
                            try{
                                if(cmbAlmacen.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbBuscarMarca.getSelectedItem().toString());
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbBuscarMarca.getSelectedItem().toString());
                                    cmd.setString(2, cmbAlmacen.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaBuscar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_cmbAlmacenActionPerformed

    //Buscar Material  SELECT (codigo, articulo, descripcion, marca, existencia, presentacion) FROM mercancia
    
    public void llenarTablaSeleccionar(ResultSet r){
        String encabezado[]={"Código","Artículo","Descripción","Marca","Existencia","Presentación"};
        DefaultTableModel modeloBuscar = new DefaultTableModel();
        for (String columnas : encabezado) {
            modeloBuscar.addColumn(columnas);
        }
        try{
            while(r.next()){
                Object[] row = new Object[6];
                row[0] = r.getString(2);
                row[1] = r.getString(3);
                row[2] = r.getString(4);
                row[3] = r.getString(5);
                row[4] = r.getString(7);
                row[5] = r.getString(6);
                modeloBuscar.addRow(row);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 008: Error al consultar materiales registrados en la base de datos.");
        }
        tblSeleccionarMaterial.setModel(modeloBuscar);
        
        // Establecer ancho de columnas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        tblSeleccionarMaterial.setDefaultRenderer(Object.class, renderer);
        int[] anchos = {80, 90, 190, 50, 8, 30};
        for (int i=0; i<tblSeleccionarMaterial.getColumnCount(); i++) {
            TableColumn columna = tblSeleccionarMaterial.getColumnModel().getColumn(i);
            int ancho=0;
            // Obtener ancho máximo de columna
            for (int j=0; j<tblSeleccionarMaterial.getRowCount(); j++) {
                TableCellRenderer cellRenderer=tblSeleccionarMaterial.getCellRenderer(j, i);
                Object valor=tblSeleccionarMaterial.getValueAt(j, i);
                Component componente=cellRenderer.getTableCellRendererComponent(tblSeleccionarMaterial, valor, false, false, j, i);
                ancho=Math.max(ancho, componente.getPreferredSize().width);
            }
            // Establecer ancho de columna
            if (ancho>0 && ancho>anchos[i]) {
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
     
    private void txtBuscarCodigoMovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCodigoMovMouseClicked
        txtBuscarCodigoMov.setText(""); txtBuscarArticuloMov.setText(""); cmbBuscarTipoMov.setSelectedItem("Por Descripción"); cmbBuscarMarcaMov.setSelectedItem(null);
    }//GEN-LAST:event_txtBuscarCodigoMovMouseClicked

    private void txtBuscarCodigoMovMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCodigoMovMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoMovMousePressed

    private void txtBuscarCodigoMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCodigoMovActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoMovActionPerformed

    private void txtBuscarCodigoMovKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCodigoMovKeyReleased
        String consulta;
        try{
            if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ?";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtBuscarCodigoMov.getText() + "%");
            }else{
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ?";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtBuscarCodigoMov.getText() + "%");
                cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
            }
            result=cmd.executeQuery();
            llenarTablaSeleccionar(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
        }
    }//GEN-LAST:event_txtBuscarCodigoMovKeyReleased

    private void cmbAlmacenMovItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAlmacenMovItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAlmacenMovItemStateChanged

    private void cmbAlmacenMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAlmacenMovActionPerformed
        String materiales;
        //Si todo está vacio
        if(txtBuscarCodigoMov.getText().isEmpty() && txtBuscarArticuloMov.getText().isEmpty() && cmbBuscarMarcaMov.getSelectedItem()==null){
            try{
                if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                    materiales="SELECT * FROM mercancia";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                }else{
                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                    cmd.setString(1, cmbAlmacenMov.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaSeleccionar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane, "Error al consultar..");
            }
        }else{
            //Si solo el código está escrito
            if(txtBuscarArticuloMov.getText().isEmpty() && cmbBuscarMarcaMov.getSelectedItem()==null){
                try{
                    if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtBuscarCodigoMov.getText() + "%");
                    }else{
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtBuscarCodigoMov.getText() + "%");
                        cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaSeleccionar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            }else{
                //Si solo el articulo está escrito
                if(txtBuscarCodigoMov.getText().isEmpty() && cmbBuscarMarcaMov.getSelectedItem()==null){
                    switch(cmbBuscarTipoMov.getSelectedItem().toString()){
                        case "Por Descripción":
                            try{
                                if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                                    cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaSeleccionar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        break;
                        case "Por Nombre":
                            try{
                                if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                                    cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaSeleccionar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        break;
                    }
                }else{
                    //Si solo está seleccionada la marca
                    if(txtBuscarCodigoMov.getText().isEmpty() && txtBuscarArticuloMov.getText().isEmpty()){
                        if(cmbBuscarMarcaMov.getSelectedItem() == null){
                            try{
                                if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbAlmacenMov.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaSeleccionar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        }else{
                            try{
                                if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbBuscarMarcaMov.getSelectedItem().toString());
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ?";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbBuscarMarcaMov.getSelectedItem().toString());
                                    cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaSeleccionar(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_cmbAlmacenMovActionPerformed

    private void txtBuscarArticuloMovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarArticuloMovMouseClicked
        txtBuscarCodigoMov.setText(""); cmbBuscarMarcaMov.setSelectedItem(null); txtBuscarArticuloMov.setText("");
    }//GEN-LAST:event_txtBuscarArticuloMovMouseClicked

    private void txtBuscarArticuloMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarArticuloMovActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarArticuloMovActionPerformed

    private void txtBuscarArticuloMovKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarArticuloMovKeyReleased
        String consulta;
        switch(cmbBuscarTipoMov.getSelectedItem().toString()){
            case "Por Descripción":
                try{
                    if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                        cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaSeleccionar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
            case "Por Nombre":
                try{
                    if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                        cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaSeleccionar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
        }
    }//GEN-LAST:event_txtBuscarArticuloMovKeyReleased

    private void cmbBuscarTipoMovItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBuscarTipoMovItemStateChanged
        txtBuscarArticuloMov.grabFocus();
    }//GEN-LAST:event_cmbBuscarTipoMovItemStateChanged

    private void cmbBuscarTipoMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarTipoMovActionPerformed
        String consulta;
        switch(cmbBuscarTipoMov.getSelectedItem().toString()){
            case "Por Descripción":
                try{
                    if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                        cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaSeleccionar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
            case "Por Nombre":
                try{
                    if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ?";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                        cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaSeleccionar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
        }
    }//GEN-LAST:event_cmbBuscarTipoMovActionPerformed

    private void cmbBuscarMarcaMovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbBuscarMarcaMovMouseClicked
        txtBuscarCodigoMov.setText(""); txtBuscarArticuloMov.setText("");
    }//GEN-LAST:event_cmbBuscarMarcaMovMouseClicked

    private void cmbBuscarMarcaMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarMarcaMovActionPerformed
        String consulta;
        if(cmbBuscarMarcaMov.getSelectedItem() == null){
            try{
                if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                    consulta="SELECT * FROM mercancia";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                }else{
                    consulta="SELECT * FROM mercancia WHERE almacen LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbAlmacenMov.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaSeleccionar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }else{
            try{
                if(cmbAlmacenMov.getSelectedItem().toString().equals("Todos")){
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbBuscarMarcaMov.getSelectedItem().toString());
                }else{
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbBuscarMarcaMov.getSelectedItem().toString());
                    cmd.setString(2, cmbAlmacenMov.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaSeleccionar(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }
    }//GEN-LAST:event_cmbBuscarMarcaMovActionPerformed

    private void btnBorrarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarMovActionPerformed
        txtBuscarCodigoMov.setText(""); txtBuscarArticuloMov.setText(""); cmbBuscarTipoMov.setSelectedItem("Por Descripción"); cmbBuscarMarcaMov.setSelectedItem(null);
        cmbAlmacenMov.setSelectedItem("Todos");
    }//GEN-LAST:event_btnBorrarMovActionPerformed

    private void btnAddMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMovActionPerformed
        if (cmbTipoMov.getSelectedItem() != null) {
            TableModel tabla1 = tblSeleccionarMaterial.getModel();
            int filas[] = tblSeleccionarMaterial.getSelectedRows();
            tblMoverMaterial.getSelectionModel().clearSelection(); // Deseleccionar todas las filas de la segunda tabla

            for (int a = 0; a < filas.length; a++) {
                Object[] row = new Object[7];
                int cantidad = (int)spinCantAdd.getValue();
                row[0] = tabla1.getValueAt(filas[a], 0); // Código
                row[1] = tabla1.getValueAt(filas[a], 1); // Artículo
                row[2] = tabla1.getValueAt(filas[a], 2); // Descripción
                row[3] = tabla1.getValueAt(filas[a], 3); // Marca
                row[4] = tabla1.getValueAt(filas[a], 5); // Presentación
                row[5] = cantidad; // Existencia
                row[6] = cmbTipoMov.getSelectedItem().toString();

                //
                String strcant = (String) tabla1.getValueAt(filas[a], 4);
                int cantidadExistente = Integer.parseInt(strcant);
                
                //
                
                // Verificar si el elemento ya está en la tabla2
                if (tabla2Data.containsKey(row[0])) {
                    int cantidadActual = tabla2Data.get(row[0].toString());
                    int nuevaCantidad = cantidadActual + cantidad;
                    // Si el elemento ya está en la tabla2, aumentar su cantidad en la cantidad del spinner
                    if (tblMoverMaterial.getValueAt(a, 6).toString().equals("Salida")) {
                        if(nuevaCantidad > cantidadExistente){
                            nuevaCantidad = cantidadActual;
                            JOptionPane.showMessageDialog(null, "La cantidad de salida supera la existencia del material.");
                        }else{
                            cantidadActual = tabla2Data.get(row[0].toString());
                            nuevaCantidad = cantidadActual + cantidad;
                            tabla2Data.put(row[0].toString(), nuevaCantidad);
                        }
                    }else{    
                        cantidadActual = tabla2Data.get(row[0].toString());
                        nuevaCantidad = cantidadActual + cantidad;
                        tabla2Data.put(row[0].toString(), nuevaCantidad);
                    }
                    // Actualizar la cantidad en la fila correspondiente en la tabla2
                    for (int i = 0; i < tblMoverMaterial.getRowCount(); i++) {
                        if (tblMoverMaterial.getValueAt(i, 0).toString().equals(row[0])) {
                            tblMoverMaterial.setValueAt(nuevaCantidad, i, 5);
                            
                            tblMoverMaterial.getSelectionModel().addSelectionInterval(i, i); // Selección de fila
                            break;
                        }
                    }

                } else {
                    // Si el elemento no está en la tabla2, agregar una nueva fila con la cantidad seleccionada en el spin
                    if (row[6].equals("Salida") && cantidad > cantidadExistente) {
                        JOptionPane.showMessageDialog(null, "La cantidad de seleccionada supera la existencia del material disponible.");
                        continue;
                    }
                    
                    tabla2Data.put(row[0].toString(), cantidad);
                    row[5] = cantidad; // Establecer el valor actual de la cantidad
                    ((DefaultTableModel) tblMoverMaterial.getModel()).addRow(row);

                    // Ajustar el ancho de las columnas
                    TableColumnModel columnModel = tblMoverMaterial.getColumnModel();
                    for (int i = 0; i < columnModel.getColumnCount(); i++) {
                        TableColumn column = columnModel.getColumn(i);
                        int preferredWidth = column.getMinWidth();
                        int maxWidth = column.getMaxWidth();

                        for (int e = 0; e < tblMoverMaterial.getRowCount(); e++) {
                            TableCellRenderer cellRenderer = tblMoverMaterial.getCellRenderer(e, i);
                            Component c = tblMoverMaterial.prepareRenderer(cellRenderer, e, i);
                            int width = c.getPreferredSize().width + tblMoverMaterial.getIntercellSpacing().width;
                            preferredWidth = Math.max(preferredWidth, width);

                            // Ajustar la anchura de la columna en función del contenido más ancho de una celda
                            if (preferredWidth >= maxWidth) {
                                preferredWidth = maxWidth;
                                break;
                            }
                        }
                        column.setPreferredWidth(preferredWidth);
                    }
                    // Actualizar la cantidad en la fila correspondiente en la tabla2
                    for (int i = 0; i < tblMoverMaterial.getRowCount(); i++) {
                        if (tblMoverMaterial.getValueAt(i, 0).toString().equals(row[0])) {
                            tblMoverMaterial.getSelectionModel().addSelectionInterval(i, i); // Selección de fila
                            break;
                        }
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione el tipo de movimiento.");
        }
        spinCantAdd.setValue(1);  cmbTipoMov.setSelectedItem(null);
    }//GEN-LAST:event_btnAddMovActionPerformed

    private void btnDeleteMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMovActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblMoverMaterial.getModel();
        int[] selectedRows = tblMoverMaterial.getSelectedRows();

        // Recorre las filas seleccionadas en orden inverso
        for (int i=selectedRows.length-1; i >= 0; i--) {
            // Obtiene el código del material a eliminar
            String codigo = (String) model.getValueAt(selectedRows[i], 0);
            model.removeRow(selectedRows[i]);
            tabla2Data.remove(codigo);
        }
    }//GEN-LAST:event_btnDeleteMovActionPerformed

    private void btnMoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoverActionPerformed

    private void btnRestMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestMovActionPerformed
        int cantidadActual = 0;
        int[] selectedRows = tblMoverMaterial.getSelectedRows();

        // Primera pasada: restar 1 a cada fila
        for (int row : selectedRows) {
            cantidadActual = (int) tblMoverMaterial.getValueAt(row, 5);
            if (cantidadActual > 0) {
                tblMoverMaterial.setValueAt(cantidadActual - (int)spinCantRest.getValue(), row, 5);
            }
        }

        // Segunda pasada: eliminar las filas que llegaron a 0
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int row = selectedRows[i];
            cantidadActual = (int) tblMoverMaterial.getValueAt(row, 5);
            if (cantidadActual <= 0) {
                ((DefaultTableModel) tblMoverMaterial.getModel()).removeRow(row);
            }
        }
        spinCantRest.setValue(1);
    }//GEN-LAST:event_btnRestMovActionPerformed

    private void btnSumMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumMovActionPerformed
        int[] selectedRows = tblMoverMaterial.getSelectedRows();
        
        for (int row : selectedRows) {
            int cantidadActual = Integer.parseInt(tblMoverMaterial.getValueAt(row, 5).toString());
            int nuevaCantidad = cantidadActual + (int) spinCantRest.getValue();
            tblMoverMaterial.setValueAt(nuevaCantidad, row, 5);
        }
        spinCantRest.setValue(1);

    }//GEN-LAST:event_btnSumMovActionPerformed

    private void cmbTipoMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMovActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoMovActionPerformed
    
    public void setImageIn(JLabel a,String route){
        ImageIcon img; Icon icono;
        img=new ImageIcon(route);
        icono=new ImageIcon(img.getImage().getScaledInstance(a.getWidth(), a.getHeight(), Image.SCALE_DEFAULT));
        a.setIcon(icono);
        this.repaint();
    }
    
    //Realizar movimientos
    
    
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
    private javax.swing.JButton btnAddMov;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBorrarMov;
    private javax.swing.JButton btnDeleteMov;
    private javax.swing.JButton btnDisplayAdmin;
    private javax.swing.JButton btnDisplayBuscar;
    private javax.swing.JButton btnDisplayCodes;
    private javax.swing.JButton btnDisplayConsulta;
    private javax.swing.JButton btnDisplayInicio;
    private javax.swing.JButton btnDisplayMov;
    private javax.swing.JButton btnDisplayRegNew;
    private javax.swing.JButton btnDisplaySettings;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMover;
    private javax.swing.JButton btnRestMov;
    private javax.swing.JButton btnSumMov;
    private javax.swing.JComboBox<String> cmbAlmacen;
    private javax.swing.JComboBox<String> cmbAlmacenMov;
    private javax.swing.JComboBox<String> cmbBuscarMarca;
    private javax.swing.JComboBox<String> cmbBuscarMarcaMov;
    private javax.swing.JComboBox<String> cmbBuscarTipo;
    private javax.swing.JComboBox<String> cmbBuscarTipoMov;
    private javax.swing.JComboBox<String> cmbTipoMov;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panelBuscar;
    private javax.swing.JPanel panelCodes;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelMov;
    private javax.swing.JSpinner spinCantAdd;
    private javax.swing.JSpinner spinCantRest;
    private javax.swing.JTable tblBuscar;
    private javax.swing.JTable tblMoverMaterial;
    private javax.swing.JTable tblSeleccionarMaterial;
    private javax.swing.JTextField txtBuscarArticulo;
    private javax.swing.JTextField txtBuscarArticuloMov;
    private javax.swing.JTextField txtBuscarCodigo;
    private javax.swing.JTextField txtBuscarCodigoMov;
    // End of variables declaration//GEN-END:variables
}
