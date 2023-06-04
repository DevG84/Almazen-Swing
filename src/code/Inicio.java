package code;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ActionMap;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import settings.Key;
import settings.conexionBD;

public class Inicio extends javax.swing.JFrame {
    
    //Para acciones en la base de datos
    conexionBD conexion=null;
    PreparedStatement cmd;
    ResultSet result, resultData, marca_res, confirma;
    // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        java.sql.Date fecha = java.sql.Date.valueOf(currentDate);
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
    //Para modificar información
    String staticIdMerc="";
    boolean perCodAutorizar=false;

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
        setImageIn(logo, "/sources/logo.png");
        //
        Pestañas.setEnabledAt(0, false);
        Pestañas.setEnabledAt(1, false);
        Pestañas.setEnabledAt(2, false);
        Pestañas.setEnabledAt(3, false);
        Pestañas.setEnabledAt(4, false);
        Pestañas.setSelectedIndex(0);
        changeButtonColor();
        //Inicio
        lblBienvenida.setText("!Saludos " + nombre + "¡ Almazen está listo para acompañarte en tu día.");
        setImageIn(logo2, "/package_512px.png");
        setImageIn(lblImg2, "/sources/hugMenu.png");
        setImageIn(imglat1, "/sources/imglat1.jpeg");
        setImageIn(imglat2, "/sources/imglat2.jpeg");
        //Buscar
        
    }
    
    public void iniciarBuscar(){
        llenarMarca(cmbBuscarMarca);
        //Llena la tabla con todo el material de la base de datos
        try{
            String materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
            result=cmd.executeQuery();
            llenarTablaBuscar(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
        }
    }
    
    public void iniciarMovimientos(){
        llenarMarca(cmbBuscarMarcaMov);
        cmbTipoMov.removeAllItems();
        cmbTipoMov.addItem(null);
        cmbTipoMov.addItem("Entrada");
        cmbTipoMov.addItem("Salida");
        //Llena la tabla con todo el material de la base de datos
        try{
            String materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
            result=cmd.executeQuery();
            llenarTablaSeleccionar(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
        }
        
    }
    
    public void iniciarConsulta(){
        llenarMarcaConsult(cmbConsultMarca);
        llenarNick();
        cmbConsultAlmacen.setSelectedItem("Todos"); cmbConsultMov.setSelectedItem("Entrada y salida"); cmbConsultNick.setSelectedItem(null);
        dateConsultDe.setDate(null); dateConsultA.setDate(null);
        btnFiltrarConsult.setEnabled(false);
        //Llena la tabla con la información de la base de datos
        try{
            String materiales=("SELECT mov.asunto, merc.codigo, merc.articulo, merc.descripcion, merc.marca, merc.almacen, mov.tipo, mov.cantidad, u.nickname, mov.fecha FROM movimiento AS mov\n" +
            "JOIN mercancia AS merc ON mov.IDmercancia = merc.IDmercancia\n" +
            "JOIN usuarios AS u ON mov.IDusuario = u.IDusuario ORDER BY mov.fecha DESC, mov.IDmovimiento DESC");
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
            result=cmd.executeQuery();
            llenarTablaConsult(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
        }
    }
    
    public void iniciarPerCod(){
        //
        txtEditCod.setText(""); txtEditArt.setText(""); spinEditCant.setValue(0);
        txtEditPresent.setText(""); txtEditAnaquel.setText(""); txtEditRepisa.setText(""); txtEditMarca.setText("");
        editDesc.setText("\n                                              SELECCIONE UN ELEMENTO DE LA TABLA PARA VER SU INFORMACIÓN");
        desactivarEdicion();
        //
        tblPerCod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Establece que solo se puede seleccionar una fila de la tabla
        llenarMarca(cmbMarcaPerCod);
        llenarMarca(cmbEditMarca);
        cmbEditMarca.addItem("Otra");
        //LLena la presentación
        cmbEditPresent.removeAllItems();
        try{
            String consulta="SELECT DISTINCT presentacion FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            marca_res=cmd.executeQuery();
            //Recorrer los resultados y agregar cada valor único de "presentacion" al JComboBox
            cmbEditPresent.addItem(null);
            while (marca_res.next()) {
                cmbEditPresent.addItem(marca_res.getString(1));
            }
            cmbEditPresent.addItem("Otra");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 005: Error al obtener las presentaciones registradas en la base de datos.");
        }
        //Llena ubicación
        //Anaquel
        cmbEditAnaquel.removeAllItems();
        try{
            String consulta="SELECT DISTINCT anaquel FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            marca_res=cmd.executeQuery();
            //Recorrer los resultados y agregar cada valor único de "anaquel" al JComboBox
            cmbEditAnaquel.addItem(null);
            while (marca_res.next()) {
                cmbEditAnaquel.addItem(marca_res.getString(1));
            }
            cmbEditAnaquel.addItem("Otra");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 005: Error al obtener las presentaciones registradas en la base de datos.");
        }
        
        //Repisa
        cmbEditRepisa.removeAllItems();
        try{
            String consulta="SELECT DISTINCT repisa FROM mercancia";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            marca_res=cmd.executeQuery();
            //Recorrer los resultados y agregar cada valor único de "repisa" al JComboBox
            cmbEditRepisa.addItem(null);
            while (marca_res.next()) {
                cmbEditRepisa.addItem(marca_res.getString(1));
            }
            cmbEditRepisa.addItem("Otra");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 005: Error al obtener las presentaciones registradas en la base de datos.");
        }
        
        //Almacén
        cmbEditAlmacen.removeAllItems();
        cmbEditAlmacen.addItem(null); cmbEditAlmacen.addItem("Zona 100 (Principal)"); cmbEditAlmacen.addItem("Zona 100 (Bodega)");
        
        //Llena la tabla
        try{
            String materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
            result=cmd.executeQuery();
            llenarTablaPerCod(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
        }
    }
    
    private void desactivarEdicion(){
        txtEditCod.setEnabled(false); txtEditArt.setEnabled(false); spinEditCant.setEnabled(false);
        editDesc.setEnabled(false); cmbEditMarca.setEnabled(false); cmbEditPresent.setEnabled(false);
        cmbEditAlmacen.setEnabled(false); cmbEditAnaquel.setEnabled(false); cmbEditRepisa.setEnabled(false);
        btnUpdate.setEnabled(false); btnUpdate.setVisible(false); btnEditar.setEnabled(true); 
        btnCancelar.setVisible(false); btnCancelar.setEnabled(false);
    }
    
    private void activarEdicion(){
        txtEditCod.setEnabled(true); txtEditArt.setEnabled(true); spinEditCant.setEnabled(true);
        editDesc.setEnabled(true); cmbEditMarca.setEnabled(true); cmbEditPresent.setEnabled(true);
        cmbEditAlmacen.setEnabled(true); cmbEditAnaquel.setEnabled(true); cmbEditRepisa.setEnabled(true);
        btnUpdate.setEnabled(true); btnUpdate.setVisible(true); btnEditar.setEnabled(false); 
        btnCancelar.setVisible(true); btnCancelar.setEnabled(true);
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
    
    public void llenarTablaPerCod(ResultSet registros){
        String encabezado[]={"Código","Artículo","Descripción","Marca","Presentación","Existencia","Almacén"};
        DefaultTableModel modeloPercod = new DefaultTableModel();
        for (String columnas : encabezado) {
            modeloPercod.addColumn(columnas);
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
                modeloPercod.addRow(row);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 008: Error al consultar materiales registrados en la base de datos.");
        }
        tblPerCod.setModel(modeloPercod);
        
        // Establecer ancho de columnas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        tblPerCod.setDefaultRenderer(Object.class, renderer);
        int[] anchos = {10, 10, 10, 10, 10, 10, 10};
        for (int i = 0; i < tblPerCod.getColumnCount(); i++) {
            TableColumn columna = tblPerCod.getColumnModel().getColumn(i);
            int ancho = 0;
            // Obtener ancho máximo de columna
            for (int j = 0; j < tblPerCod.getRowCount(); j++) {
                TableCellRenderer cellRenderer = tblPerCod.getCellRenderer(j, i);
                Object valor = tblPerCod.getValueAt(j, i);
                Component componente = cellRenderer.getTableCellRendererComponent(tblPerCod, valor, false, false, j, i);
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
            String consulta="SELECT DISTINCT marca FROM mercancia ORDER BY marca ASC";
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
    
    private void llenarMarcaConsult(JComboBox cmbBuscarMarca){
        cmbBuscarMarca.removeAllItems();
        try{
            String consulta="SELECT DISTINCT mercancia.marca FROM movimiento JOIN mercancia ON movimiento.IDmercancia = mercancia.IDmercancia";
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
    
    private void llenarNick(){
        cmbConsultNick.removeAllItems();
        try{
            String consulta="SELECT DISTINCT usuarios.nickname FROM movimiento JOIN usuarios ON movimiento.IDusuario = usuarios.IDusuario";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            result=cmd.executeQuery();
            //Recorrer los resultados y agregar cada valor único de "marca" al JComboBox
            cmbConsultNick.addItem(null);
            while (result.next()) {
                cmbConsultNick.addItem(result.getString(1));
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
    
    private void autorizarEdicion(){
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
        logo2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblImg2 = new javax.swing.JLabel();
        gitLogo = new javax.swing.JLabel();
        imglat1 = new javax.swing.JLabel();
        imglat2 = new javax.swing.JLabel();
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
        jLabel15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAsunto = new javax.swing.JTextArea();
        panelConsulta = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        cmbConsultAlmacen = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtConsultCodigo = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtConsultArticulo = new javax.swing.JTextField();
        cmbConsultTipo = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbConsultMarca = new javax.swing.JComboBox<>();
        btnBorrarConsult = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        cmbConsultMov = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cmbConsultNick = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblConsulta = new javax.swing.JTable();
        dateConsultDe = new com.toedter.calendar.JDateChooser();
        dateConsultA = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btnFiltrarConsult = new javax.swing.JButton();
        panelCodes = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cmbAlmacenPerCod = new javax.swing.JComboBox<>();
        txtCodigoPerCod = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtArticuloPerCod = new javax.swing.JTextField();
        cmbTipoPerCod = new javax.swing.JComboBox<>();
        cmbMarcaPerCod = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        btnBorrarPerCod = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPerCod = new javax.swing.JTable();
        panelEditar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtEditCod = new javax.swing.JTextField();
        txtEditArt = new javax.swing.JTextField();
        cmbEditMarca = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        editDesc = new javax.swing.JTextArea();
        cmbEditPresent = new javax.swing.JComboBox<>();
        spinEditCant = new javax.swing.JSpinner();
        cmbEditAlmacen = new javax.swing.JComboBox<>();
        cmbEditAnaquel = new javax.swing.JComboBox<>();
        cmbEditRepisa = new javax.swing.JComboBox<>();
        txtEditPresent = new javax.swing.JTextField();
        cual1 = new javax.swing.JLabel();
        txtEditMarca = new javax.swing.JTextField();
        cual2 = new javax.swing.JLabel();
        cual3 = new javax.swing.JLabel();
        txtEditAnaquel = new javax.swing.JTextField();
        txtEditRepisa = new javax.swing.JTextField();
        cual4 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

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
        btnDisplayCodes.setText("Modificar información");
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
        btnDisplaySettings.setIconTextGap(-90);
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
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btnDisplayAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDisplaySettings, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            .addGap(0, 35, Short.MAX_VALUE)
        );

        Pestañas.setBackground(new java.awt.Color(51, 51, 51));

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));

        lblBienvenida.setFont(new java.awt.Font("Microsoft YaHei", 0, 24)); // NOI18N
        lblBienvenida.setText("Bienvenido a Almazen");

        btnAboutUs.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        btnAboutUs.setText("Acerca de");
        btnAboutUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutUsActionPerformed(evt);
            }
        });

        logo2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logo2MouseClicked(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
        jTextArea1.setRows(5);
        jTextArea1.setText("Almazen fue creado y configurado para uso exclusivo del CECyT 14 \"Luis Enrique Erro\".\n\nLas funciones con las que cuenta Almazen son:\n\n    Buscar material: Es un apartado de consulta de material, te permite saber que materiales hay actualmente en el almacén.  \n         Cuenta con un filtro de búsqueda que te permite encontrar los materiales escribiendo su nombre, su código de barras o eligiendo su marca.\n\n    Registrar nuevo material: Registra un material del almacén, en caso de la llegada de un material que no habías registrado esta función te ayudará.\n        Puedes registrar un material de dos maneras, con un código de barras propio del articulo o con un código personalizado de nuestro sistema.\n         • El código del artículo se encuentra en el empaque donde viene, es ideal para no generar nuevos códigos.\n         • Un código personalizado se compone por el prefijo \"ALMZ-\" y un número generado automáticamente.\n            Debido a que es una opción delicada necesita ser autorizada por un Administrador o un Encargado de Almacén. \n\n    Realizar movimientos: Registra una salida o entrada de material de los productos que elijas.\n        Elije un material de la tabla \"Materiales disponibles\", selecciona el tipo de movimiento (\"Entrada\" o \"Salida\"), selecciona la cantidad y añádelos a\n        la tabla \"Material seleccionado\", escribe un asunto, por ejemplo \"Vale de salida 17\", y da clic en \"Mover\" para actualizar las existencias de los \n        materiales.\n        Cuenta con un filtro de búsqueda para encontrar los materiales y botones en la tabla \"Material seleccionado\" que permiten añadir, disminuir las \n        cantidades de los materiales o eliminar de la tabla un material no contemplado en el movimiento.\n\n    Consultar movimientos: Revisa los movimientos realizados en ciertas situaciones.\n        Tienes disponible una tabla con toda la información sobre el movimiento realizado por alguien.\n        Cuenta con filtros que te permiten conocer información específica como el responsable de que el movimiento se hiciera efectivo o la fecha en\n        que se realizó.\n\n    Modificar información: Corrige errores ocurridos al registrar un material.\n        Puedes editar la información de un material que selecciones, ten cuidado no modifiques la información sin tener en cuenta a los demás materiales,\n        debido a que esta función es delicada solo podrá ser autorizada por un Administrador o Encargado de Almacén.\n");
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setEnabled(false);
        jTextArea1.setFocusable(false);
        jTextArea1.setRequestFocusEnabled(false);
        jScrollPane8.setViewportView(jTextArea1);

        lblImg2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImg2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImg2MouseClicked(evt);
            }
        });

        gitLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/github.png"))); // NOI18N
        gitLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gitLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gitLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio);
        panelInicio.setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInicioLayout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 985, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInicioLayout.createSequentialGroup()
                                .addGap(0, 265, Short.MAX_VALUE)
                                .addComponent(btnAboutUs))
                            .addGroup(panelInicioLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(imglat2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(imglat1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(panelInicioLayout.createSequentialGroup()
                        .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBienvenida)
                            .addGroup(panelInicioLayout.createSequentialGroup()
                                .addComponent(gitLogo)
                                .addGap(18, 18, 18)
                                .addComponent(logo2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblImg2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblBienvenida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelInicioLayout.createSequentialGroup()
                        .addComponent(imglat2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(imglat1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImg2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gitLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
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
        cmbBuscarTipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        cmbBuscarMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/trash2_24px.png"))); // NOI18N
        btnBorrar.setText("Borrar selección");
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        tblBuscar.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblBuscar);

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel8.setText("Almacén:");

        cmbAlmacen.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbAlmacen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Zona 100 (Principal)", "Zona 100 (Bodega)" }));
        cmbAlmacen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBuscarLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbAlmacen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBuscarLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelBuscarLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscarArticulo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbBuscarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbBuscarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBuscarLayout.createSequentialGroup()
                                        .addGap(893, 893, 893)
                                        .addComponent(btnBorrar))))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrar)
                    .addComponent(jLabel8)
                    .addComponent(cmbAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
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
        tblSeleccionarMaterial.getTableHeader().setReorderingAllowed(false);
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
        tblMoverMaterial.getTableHeader().setReorderingAllowed(false);
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

        btnMover.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnMover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/move_48px.png"))); // NOI18N
        btnMover.setText("Mover");
        btnMover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMover.setIconTextGap(-107);
        btnMover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoverActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
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
        cmbAlmacenMov.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zona 100 (Principal)", "Zona 100 (Bodega)" }));
        cmbAlmacenMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jLabel9.setText("Artículo:");

        cmbBuscarTipoMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbBuscarTipoMov.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Descripción", "Por Nombre" }));
        cmbBuscarTipoMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jLabel10.setText("Marca:");

        cmbBuscarMarcaMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnBorrarMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/trash2_24px.png"))); // NOI18N
        btnBorrarMov.setText("Borrar selección");
        btnBorrarMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarMovActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel11.setText("Almacén:");

        jLabel12.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        jLabel12.setText("Material Seleccionado");

        jLabel13.setFont(new java.awt.Font("Microsoft YaHei", 1, 13)); // NOI18N
        jLabel13.setText("Material disponible");

        btnAddMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnAddMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/add_32px.png"))); // NOI18N
        btnAddMov.setText("Añadir");
        btnAddMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddMov.setIconTextGap(-90);
        btnAddMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMovActionPerformed(evt);
            }
        });

        btnDeleteMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDeleteMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/packageDelete2_32px.png"))); // NOI18N
        btnDeleteMov.setText("Eliminar");
        btnDeleteMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteMov.setIconTextGap(-90);
        btnDeleteMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMovActionPerformed(evt);
            }
        });

        btnRestMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnRestMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/OutBox_32px.png"))); // NOI18N
        btnRestMov.setText("Disminuir");
        btnRestMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRestMov.setIconTextGap(-100);
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
        cmbTipoMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTipoMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMovActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel14.setText("Tipo:");

        btnSumMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnSumMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/InBox_32px.png"))); // NOI18N
        btnSumMov.setText("Aumentar");
        btnSumMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSumMov.setIconTextGap(-100);
        btnSumMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumMovActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel15.setText("¿Por qué se está moviendo el material?");

        txtAsunto.setColumns(20);
        txtAsunto.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtAsunto.setLineWrap(true);
        txtAsunto.setRows(5);
        txtAsunto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAsuntoKeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(txtAsunto);

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
                                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelMovLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbAlmacenMov, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelMovLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscarCodigoMov, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelMovLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscarArticuloMov)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbBuscarTipoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbBuscarMarcaMov, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovLayout.createSequentialGroup()
                                        .addGap(893, 893, 893)
                                        .addComponent(btnBorrarMov))))
                            .addGroup(panelMovLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelMovLayout.createSequentialGroup()
                                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelMovLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelMovLayout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(panelMovLayout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cmbTipoMov, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(btnAddMov, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                            .addComponent(spinCantAdd)))
                                    .addGroup(panelMovLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(btnDeleteMov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnRestMov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSumMov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(spinCantRest, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(btnMover))))))
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                    .addGroup(panelMovLayout.createSequentialGroup()
                        .addGroup(panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinCantAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddMov)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                        .addGap(36, 36, 36)
                        .addComponent(spinCantRest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSumMov)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRestMov)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteMov)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(btnMover)))
                .addContainerGap())
        );

        Pestañas.addTab("Movimientos", panelMov);

        panelConsulta.setBackground(new java.awt.Color(255, 255, 255));

        cmbConsultAlmacen.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbConsultAlmacen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Zona 100 (Principal)", "Zona 100 (Bodega)" }));
        cmbConsultAlmacen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbConsultAlmacen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbConsultAlmacenItemStateChanged(evt);
            }
        });
        cmbConsultAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConsultAlmacenActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel17.setText("Almacén:");

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel3.setText("Código:");

        txtConsultCodigo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtConsultCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtConsultCodigoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtConsultCodigoMousePressed(evt);
            }
        });
        txtConsultCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConsultCodigoActionPerformed(evt);
            }
        });
        txtConsultCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtConsultCodigoKeyReleased(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel18.setText("Artículo:");

        txtConsultArticulo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtConsultArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtConsultArticuloMouseClicked(evt);
            }
        });
        txtConsultArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConsultArticuloActionPerformed(evt);
            }
        });
        txtConsultArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtConsultArticuloKeyReleased(evt);
            }
        });

        cmbConsultTipo.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbConsultTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Descripción", "Por Nombre" }));
        cmbConsultTipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbConsultTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbConsultTipoItemStateChanged(evt);
            }
        });
        cmbConsultTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConsultTipoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel19.setText("Marca:");

        cmbConsultMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbConsultMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbConsultMarcaMouseClicked(evt);
            }
        });
        cmbConsultMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConsultMarcaActionPerformed(evt);
            }
        });

        btnBorrarConsult.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnBorrarConsult.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/trash2_24px.png"))); // NOI18N
        btnBorrarConsult.setText("Borrar selección");
        btnBorrarConsult.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarConsult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarConsultActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel20.setText("Tipo:");

        cmbConsultMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbConsultMov.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Entrada", "Salida", "Actualización" }));
        cmbConsultMov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbConsultMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConsultMovActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel21.setText("Nickname:");

        cmbConsultNick.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbConsultNick.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbConsultNick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConsultNickActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel22.setText("Fecha:");

        tblConsulta.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        tblConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Asunto", "Código", "Artículo", "Descripción", "Marca", "Tipo", "Cantidad", "Nickname", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblConsulta.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblConsulta);
        if (tblConsulta.getColumnModel().getColumnCount() > 0) {
            tblConsulta.getColumnModel().getColumn(0).setResizable(false);
            tblConsulta.getColumnModel().getColumn(2).setResizable(false);
            tblConsulta.getColumnModel().getColumn(3).setResizable(false);
            tblConsulta.getColumnModel().getColumn(4).setResizable(false);
        }

        dateConsultDe.setDateFormatString("dd MMM yyyy"); // NOI18N
        dateConsultDe.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        dateConsultDe.setMaxSelectableDate(fecha);
        dateConsultDe.setPreferredSize(new java.awt.Dimension(85, 24));
        dateConsultDe.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateConsultDePropertyChange(evt);
            }
        });
        dateConsultDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dateConsultDeKeyReleased(evt);
            }
        });

        dateConsultA.setDateFormatString("dd MMM yyyy"); // NOI18N
        dateConsultA.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        dateConsultA.setMaxSelectableDate(fecha);
        dateConsultA.setPreferredSize(new java.awt.Dimension(85, 24));
        dateConsultA.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateConsultAPropertyChange(evt);
            }
        });
        dateConsultA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dateConsultAKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel16.setText("a");

        jLabel23.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel23.setText("De");

        btnFiltrarConsult.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnFiltrarConsult.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/search_24px.png"))); // NOI18N
        btnFiltrarConsult.setText("Filtrar");
        btnFiltrarConsult.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrarConsult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarConsultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConsultCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConsultArticulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbConsultTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addGap(6, 6, 6)
                        .addComponent(cmbConsultMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbConsultAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addGap(6, 6, 6)
                        .addComponent(cmbConsultMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addGap(6, 6, 6)
                        .addComponent(cmbConsultNick, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel23)
                        .addGap(5, 5, 5)
                        .addComponent(dateConsultDe, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(7, 7, 7)
                        .addComponent(dateConsultA, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiltrarConsult)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(btnBorrarConsult))
                    .addComponent(jSeparator3)
                    .addComponent(jScrollPane4))
                .addGap(6, 6, 6))
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtConsultCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtConsultArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbConsultTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbConsultMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))))
                .addGap(6, 6, 6)
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbConsultAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel20))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(cmbConsultMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel21))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(cmbConsultNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(dateConsultDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel16))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(dateConsultA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBorrarConsult)
                        .addComponent(btnFiltrarConsult)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        Pestañas.addTab("Consulta", panelConsulta);

        panelCodes.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel4.setText("Código:");

        jLabel24.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel24.setText("Almacén:");

        cmbAlmacenPerCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbAlmacenPerCod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Zona 100 (Principal)", "Zona 100 (Bodega)" }));
        cmbAlmacenPerCod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbAlmacenPerCod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAlmacenPerCodItemStateChanged(evt);
            }
        });
        cmbAlmacenPerCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAlmacenPerCodActionPerformed(evt);
            }
        });

        txtCodigoPerCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtCodigoPerCod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodigoPerCodMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCodigoPerCodMousePressed(evt);
            }
        });
        txtCodigoPerCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoPerCodActionPerformed(evt);
            }
        });
        txtCodigoPerCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoPerCodKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel25.setText("Artículo:");

        txtArticuloPerCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        txtArticuloPerCod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtArticuloPerCodMouseClicked(evt);
            }
        });
        txtArticuloPerCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArticuloPerCodActionPerformed(evt);
            }
        });
        txtArticuloPerCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtArticuloPerCodKeyReleased(evt);
            }
        });

        cmbTipoPerCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbTipoPerCod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Descripción", "Por Nombre" }));
        cmbTipoPerCod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTipoPerCod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoPerCodItemStateChanged(evt);
            }
        });
        cmbTipoPerCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoPerCodActionPerformed(evt);
            }
        });

        cmbMarcaPerCod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbMarcaPerCod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbMarcaPerCodMouseClicked(evt);
            }
        });
        cmbMarcaPerCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMarcaPerCodActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel26.setText("Marca:");

        btnBorrarPerCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnBorrarPerCod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/trash2_24px.png"))); // NOI18N
        btnBorrarPerCod.setText("Borrar selección");
        btnBorrarPerCod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarPerCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarPerCodActionPerformed(evt);
            }
        });

        tblPerCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        tblPerCod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Artículo", "Descripción", "Marca", "Presentación", "Existencia", "Almacén"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPerCod.getTableHeader().setReorderingAllowed(false);
        tblPerCod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPerCodMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblPerCod);
        if (tblPerCod.getColumnModel().getColumnCount() > 0) {
            tblPerCod.getColumnModel().getColumn(0).setResizable(false);
            tblPerCod.getColumnModel().getColumn(1).setResizable(false);
            tblPerCod.getColumnModel().getColumn(2).setResizable(false);
            tblPerCod.getColumnModel().getColumn(3).setResizable(false);
            tblPerCod.getColumnModel().getColumn(4).setResizable(false);
            tblPerCod.getColumnModel().getColumn(5).setResizable(false);
            tblPerCod.getColumnModel().getColumn(6).setResizable(false);
        }

        panelEditar.setBackground(new java.awt.Color(255, 255, 255));
        panelEditar.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Código:");

        jLabel27.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Artículo:");

        jLabel28.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Descripción:");

        jLabel29.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Marca:");

        jLabel30.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Presentación:");

        jLabel31.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Existencia:");

        jLabel32.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Almacén:");

        jLabel33.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Anaquel:");

        jLabel34.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Repisa:");

        txtEditCod.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtEditArt.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        cmbEditMarca.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbEditMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEditMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEditMarcaActionPerformed(evt);
            }
        });

        editDesc.setColumns(20);
        editDesc.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        editDesc.setLineWrap(true);
        editDesc.setRows(3);
        editDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                editDescKeyTyped(evt);
            }
        });
        jScrollPane7.setViewportView(editDesc);

        cmbEditPresent.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbEditPresent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEditPresent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEditPresentActionPerformed(evt);
            }
        });

        spinEditCant.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        cmbEditAlmacen.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbEditAlmacen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbEditAnaquel.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbEditAnaquel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEditAnaquel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEditAnaquelActionPerformed(evt);
            }
        });

        cmbEditRepisa.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cmbEditRepisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEditRepisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEditRepisaActionPerformed(evt);
            }
        });

        txtEditPresent.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        cual1.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual1.setForeground(new java.awt.Color(0, 0, 0));
        cual1.setText("¿Cuál?");

        txtEditMarca.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        cual2.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual2.setForeground(new java.awt.Color(0, 0, 0));
        cual2.setText("¿Cuál?");

        cual3.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual3.setForeground(new java.awt.Color(0, 0, 0));
        cual3.setText("¿Cuál?");

        txtEditAnaquel.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        txtEditRepisa.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N

        cual4.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        cual4.setForeground(new java.awt.Color(0, 0, 0));
        cual4.setText("¿Cuál?");

        javax.swing.GroupLayout panelEditarLayout = new javax.swing.GroupLayout(panelEditar);
        panelEditar.setLayout(panelEditarLayout);
        panelEditarLayout.setHorizontalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarLayout.createSequentialGroup()
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30)
                        .addGap(12, 12, 12)
                        .addComponent(cmbEditPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cual1)
                        .addGap(18, 18, 18)
                        .addComponent(txtEditPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelEditarLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel32)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEditAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelEditarLayout.createSequentialGroup()
                                .addComponent(cmbEditAnaquel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cual3)
                                .addGap(18, 18, 18)
                                .addComponent(txtEditAnaquel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelEditarLayout.createSequentialGroup()
                                .addComponent(cmbEditRepisa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cual4)
                                .addGap(18, 18, 18)
                                .addComponent(txtEditRepisa, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinEditCant, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarLayout.createSequentialGroup()
                                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cual2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEditMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbEditMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarLayout.createSequentialGroup()
                        .addGap(0, 18, Short.MAX_VALUE)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEditCod, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEditArt, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        panelEditarLayout.setVerticalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEditCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(txtEditArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(18, 18, 18)
                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditarLayout.createSequentialGroup()
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel30)
                                .addComponent(cmbEditPresent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEditPresent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cual1)))
                        .addGap(18, 18, 18)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelEditarLayout.createSequentialGroup()
                                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbEditAlmacen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32))
                                .addGap(18, 18, 18)
                                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbEditAnaquel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel33))
                                    .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtEditAnaquel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cual3)))
                                .addGap(18, 18, 18)
                                .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbEditRepisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34)))
                            .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEditRepisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cual4)))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(panelEditarLayout.createSequentialGroup()
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbEditMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEditMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cual2))
                        .addGap(18, 18, 18)
                        .addGroup(panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(spinEditCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        btnEditar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/edit_32px.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setIconTextGap(-100);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/update_32px.png"))); // NOI18N
        btnUpdate.setText("Actualizar");
        btnUpdate.setIconTextGap(-100);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sources/icons/cancel_32px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setIconTextGap(-100);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCodesLayout = new javax.swing.GroupLayout(panelCodes);
        panelCodes.setLayout(panelCodesLayout);
        panelCodesLayout.setHorizontalGroup(
            panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodesLayout.createSequentialGroup()
                .addGroup(panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCodesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator4)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCodesLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodigoPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtArticuloPerCod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTipoPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbMarcaPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCodesLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbAlmacenPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBorrarPerCod))))
                    .addGroup(panelCodesLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6)
                            .addGroup(panelCodesLayout.createSequentialGroup()
                                .addComponent(panelEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEditar)
                                    .addComponent(btnUpdate)
                                    .addComponent(btnCancelar))
                                .addGap(0, 236, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        panelCodesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnEditar, btnUpdate});

        panelCodesLayout.setVerticalGroup(
            panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigoPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txtArticuloPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(cmbMarcaPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarPerCod)
                    .addComponent(jLabel24)
                    .addComponent(cmbAlmacenPerCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCodesLayout.createSequentialGroup()
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(Pestañas, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        iniciarConsulta();
    }//GEN-LAST:event_btnDisplayConsultaActionPerformed

    private void btnDisplayAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayAdminActionPerformed
        ajustesAdmin admin=new ajustesAdmin(this);
        admin.setVisible(true);
    }//GEN-LAST:event_btnDisplayAdminActionPerformed

    private void btnDisplayInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayInicioActionPerformed
        Pestañas.setSelectedIndex(0);
        changeButtonColor();
    }//GEN-LAST:event_btnDisplayInicioActionPerformed

    private void btnDisplaySettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplaySettingsActionPerformed
        ajustesUsuario userSettings=new ajustesUsuario(this);
        userSettings.setVisible(true);
    }//GEN-LAST:event_btnDisplaySettingsActionPerformed

    private void btnDisplayRegNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayRegNewActionPerformed
        registrarMaterial m=new registrarMaterial(this);
        m.setVisible(true);
    }//GEN-LAST:event_btnDisplayRegNewActionPerformed

    private void btnDisplayCodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayCodesActionPerformed
        Pestañas.setSelectedIndex(4);
        changeButtonColor();
        iniciarPerCod();
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
        ImageIcon Icono=new ImageIcon(getClass().getResource("/sources/icons/questionCube.png"));
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

    //Buscar materiales
    
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
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? ORDER BY articulo ASC";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtBuscarCodigo.getText() + "%");
            }else{
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticulo.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                    consulta="SELECT * FROM mercancia ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                }else{
                    consulta="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
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
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbBuscarMarca.getSelectedItem().toString());
                }else{
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                    materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                }else{
                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
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
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtBuscarCodigo.getText() + "%");
                    }else{
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticulo.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbBuscarMarca.getSelectedItem().toString());
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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

    //Realizar Movimiento SELECT (codigo, articulo, descripcion, marca, existencia, presentacion) FROM mercancia
    
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
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? ORDER BY articulo ASC";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtBuscarCodigoMov.getText() + "%");
            }else{
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                    materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                }else{
                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
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
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtBuscarCodigoMov.getText() + "%");
                    }else{
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
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
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbBuscarMarcaMov.getSelectedItem().toString());
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtBuscarArticuloMov.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
                    consulta="SELECT * FROM mercancia ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                }else{
                    consulta="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
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
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbBuscarMarcaMov.getSelectedItem().toString());
                }else{
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
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
        cmbAlmacenMov.setSelectedItem("Zona 100 (Principal)");
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
                    int cantidadActual = 0;
                    cantidadActual =  tabla2Data.get(row[0].toString());
                    int nuevaCantidad = cantidadActual + cantidad;
                    // Si el elemento ya está en la tabla2, aumentar su cantidad en la cantidad del spinner
                    if (tblMoverMaterial.getValueAt(a, 6).toString().equals("Salida")) {
                        if(nuevaCantidad > cantidadExistente){
                            nuevaCantidad = cantidadActual;
                            JOptionPane.showMessageDialog(null, "La cantidad de seleccionada supera la existencia del material disponible.");
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
            if(tblMoverMaterial.getRowCount()>0){
                cmbAlmacenMov.setEnabled(false);
            }else{
                cmbAlmacenMov.setEnabled(true);
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
        
        if(tblMoverMaterial.getRowCount()>0){
            cmbAlmacenMov.setEnabled(false);
        }else{
            cmbAlmacenMov.setEnabled(true);
        }
    }//GEN-LAST:event_btnDeleteMovActionPerformed

    private void btnMoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoverActionPerformed
        String asunto = txtAsunto.getText().trim();
        boolean contenido=false;
        if (!asunto.isEmpty()) {
            contenido=true;
        }
        if(tblMoverMaterial.getRowCount()>0 && contenido==true){
        Object[] opciones={"Si","No"};
        ImageIcon Icono=new ImageIcon(getClass().getResource("/sources/icons/questionCube.png"));
        int respuesta=JOptionPane.showOptionDialog(rootPane, "Está a punto de realizar un movimiento de material.\n¿Quiere continuar?","Realizar Movimiento",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,Icono,opciones,opciones[0]);
        if(respuesta==0){
            for (int i = 0; i < tblMoverMaterial.getRowCount(); i++) {
                String idMerca="", codigo="", tipoMov="";
                int cantidadTbl=0, cantidadBD=0, nuevaCant=0;
                
                codigo = tblMoverMaterial.getValueAt(i, 0).toString();
                cantidadTbl = Integer.parseInt(tblMoverMaterial.getValueAt(i, 5).toString());
                tipoMov = tblMoverMaterial.getValueAt(i, 6).toString();
                
                //Consultar a la base de datos el material y obtener su cantidad
                try{
                    String consulta="SELECT IDmercancia,existencia FROM mercancia WHERE codigo = ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, codigo);
                    result=cmd.executeQuery();
                    while(result.next()){
                        idMerca = result.getString(1);
                        cantidadBD = Integer.parseInt(result.getString(2));
                        if(tipoMov=="Salida"){
                            nuevaCant=cantidadBD-cantidadTbl;
                        }else{
                            nuevaCant=cantidadBD+cantidadTbl;
                        }
                    }
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "Falló la consulta.");
                }
                
                //Actualizar la cantidad del material en la base de datos según el tipo de movimiento
                try{
                    String update="UPDATE mercancia SET existencia = ? WHERE codigo = ?";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(update);
                    cmd.setInt(1, nuevaCant);
                    cmd.setString(2, codigo);
                    cmd.executeUpdate();
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al actualizar las cantidades.");
                }
                
                //Hacer incersión a la tabla movimientos
                try{
                    String registrarMovimiento=("INSERT INTO movimiento(IDusuario,IDmercancia,tipo,asunto,fecha,cantidad) VALUES (?,?,?,?,?,?)");
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(registrarMovimiento);
                    cmd.setString(1, id);
                    cmd.setString(2, idMerca);
                    if(tipoMov=="Salida"){  cmd.setString(3, "S");
                    }else{                  cmd.setString(3, "E");}
                    cmd.setString(4, asunto);
                    cmd.setDate(5, fecha);
                    cmd.setInt(6, cantidadTbl);
                    cmd.executeUpdate();
                }catch(SQLException exz){
                    JOptionPane.showMessageDialog(null,"Error al registrar el movimiento.");
                }
                
                try{
                    String materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                    result=cmd.executeQuery();
                    llenarTablaSeleccionar(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane, "Error al llenar tabla.");
                }
            }
            txtAsunto.setText("");
            //Vaciar tabla
            DefaultTableModel model = (DefaultTableModel) tblMoverMaterial.getModel();
            int numRows = model.getRowCount();
            int delete= numRows - 1;
            for (int i=delete; i >= 0; i--) {
                // Obtiene el código del material a eliminar
                String codigo = (String) model.getValueAt(i, 0);
                model.removeRow(i);
                tabla2Data.remove(codigo);
            }
        }
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un material y especificar el asunto para realizar un movimiento.");
        }
        //Habilitar ubicación si la tabla está vacia
        if(tblMoverMaterial.getRowCount()>0){
            cmbAlmacenMov.setEnabled(false);
        }else{
            cmbAlmacenMov.setEnabled(true);
        }
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
                DefaultTableModel model = (DefaultTableModel) tblMoverMaterial.getModel();
                // Obtiene el código del material a eliminar
                String codigo = (String) model.getValueAt(selectedRows[i], 0);
                model.removeRow(selectedRows[i]);
                tabla2Data.remove(codigo);
            }
        }
        spinCantRest.setValue(1);
        if(tblMoverMaterial.getRowCount()>0){
            cmbAlmacenMov.setEnabled(false);
        }else{
            cmbAlmacenMov.setEnabled(true);
        }
    }//GEN-LAST:event_btnRestMovActionPerformed

    private void btnSumMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumMovActionPerformed
        int[] selectedRows = tblMoverMaterial.getSelectedRows();
        
        for (int row : selectedRows) {
            int cantidadActual = Integer.parseInt(tblMoverMaterial.getValueAt(row, 5).toString());
            int nuevaCantidad = 0;
            int limite=0;
            if(tblMoverMaterial.getValueAt(row, 6).equals("Salida")){
                String identificador = tblMoverMaterial.getValueAt(row, 0).toString();
                for (int i = 0; i < tblSeleccionarMaterial.getRowCount(); i++) {
                    if (identificador.equals(tblSeleccionarMaterial.getValueAt(i, 0).toString())) {
                        limite = Integer.parseInt(tblSeleccionarMaterial.getValueAt(i, 4).toString());
                        break;
                    }
                }
                nuevaCantidad = cantidadActual + (int) spinCantRest.getValue();
                if(nuevaCantidad>limite){
                    nuevaCantidad=limite;
                    tblMoverMaterial.setValueAt(nuevaCantidad, row, 5);
                    JOptionPane.showMessageDialog(null, "La cantidad de seleccionada supera la existencia del material disponible.");
                }else{
                    tblMoverMaterial.setValueAt(nuevaCantidad, row, 5);
                }
            }else{
                nuevaCantidad = cantidadActual + (int) spinCantRest.getValue();
                tblMoverMaterial.setValueAt(nuevaCantidad, row, 5);
            }
        }
        spinCantRest.setValue(1);

    }//GEN-LAST:event_btnSumMovActionPerformed

    private void cmbTipoMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMovActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoMovActionPerformed

    private void txtAsuntoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAsuntoKeyTyped
        StringBuilder cadena=new StringBuilder(txtAsunto.getText());
        if(txtAsunto.getText().length() >=255){
            JOptionPane.showMessageDialog(null, "Limite maximo de 255 caracteres.");
            cadena.deleteCharAt(cadena.length()-1);
            txtAsunto.setText(cadena.toString());
            cadena=null;
        }
    }//GEN-LAST:event_txtAsuntoKeyTyped

    //Consultar Movimientos
    
    public void llenarTablaConsult(ResultSet r){
        String encabezado[]={"Asunto","Código","Artículo","Descripción","Marca","Almacén","Tipo","Cantidad","Responsable","Fecha"};
        String tipoMovimiento="";
        
        DefaultTableModel modeloConsulta = new DefaultTableModel();
        for (String columnas : encabezado) {
            modeloConsulta.addColumn(columnas);
        }
        try{
            while(r.next()){
                Object[] row = new Object[10];
                row[0] = r.getString(1);
                row[1] = r.getString(2);
                row[2] = r.getString(3);
                row[3] = r.getString(4);
                row[4] = r.getString(5);
                row[5] = r.getString(6);
                tipoMovimiento=r.getString(7);
                if(tipoMovimiento.equals("E")){
                    row[6]="Entrada";
                }else{
                    if(tipoMovimiento.equals("S")){
                        row[6]="Salida";
                    }else{
                        if(tipoMovimiento.equals("U")){
                            row[6]="Actualización";
                        }
                    }
                }
                row[7] = r.getString(8);
                row[8] = r.getString(9);
                row[9] = r.getString(10);
                modeloConsulta.addRow(row);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error 008: Error al consultar materiales registrados en la base de datos.");
        }
        tblConsulta.setModel(modeloConsulta);
        
        // Ajustar el ancho de las columnas
        TableColumnModel columnModel = tblConsulta.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            int preferredWidth = column.getMinWidth();
            int maxWidth = column.getMaxWidth();

            for (int e = 0; e < tblConsulta.getRowCount(); e++) {
                TableCellRenderer cellRenderer = tblConsulta.getCellRenderer(e, i);
                Component c = tblConsulta.prepareRenderer(cellRenderer, e, i);
                int width = c.getPreferredSize().width + tblConsulta.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                // Ajustar la anchura de la columna en función del contenido más ancho de una celda
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            column.setPreferredWidth(preferredWidth);
        }
        //
    }
    
    private void consultFiltrosDefault(String columna, String campo){
        String consulta;
        try{
            consulta=("""
            SELECT mov.asunto, merc.codigo, merc.articulo, merc.descripcion, merc.marca, merc.almacen, mov.tipo, mov.cantidad, u.nickname, mov.fecha FROM movimiento AS mov
            JOIN mercancia AS merc ON mov.IDmercancia = merc.IDmercancia
            JOIN usuarios AS u ON mov.IDusuario = u.IDusuario
            WHERE merc.""" + columna +" LIKE ?\n" +
            "ORDER BY mov.fecha DESC, mov.IDmovimiento DESC");
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            cmd.setString(1, campo + "%");
            result=cmd.executeQuery();
            llenarTablaConsult(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
        }
    }
    
    private void consultFiltrosActivos(String almacen, String tipo, String nickname, String fechaDe, String fechaA){
        //Buscar por código, articulo o marca
        String buscar="";
        String modo="";
        if(txtConsultCodigo.getText()!=null && txtConsultArticulo.getText().isEmpty() && cmbConsultMarca.getSelectedItem()==null){
           buscar=txtConsultCodigo.getText();
           modo="merc.codigo LIKE ?";
        }else{
            if(txtConsultCodigo.getText().isEmpty() && txtConsultArticulo.getText()!=null && cmbConsultMarca.getSelectedItem()==null){
                buscar=txtConsultArticulo.getText(); 
                if(cmbConsultTipo.getSelectedItem().toString().equals("Por Nombre")){   
                    modo="merc.articulo LIKE ?";
                }else{  
                    if(cmbConsultTipo.getSelectedItem().toString().equals("Por Descripción")){   
                        modo="merc.descripcion LIKE ?";
                    }
                }
            }else{
                if(txtConsultCodigo.getText().isEmpty() && txtConsultArticulo.getText().isEmpty() && cmbConsultMarca.getSelectedItem()!=null){
                    buscar=cmbConsultMarca.getSelectedItem().toString(); 
                    modo="merc.marca LIKE ?";
                }
            }
        }
        if("Entrada".equals(tipo)){
            tipo="E";
        }else{
            if("Salida".equals(tipo)){
                tipo="S";
            }else{
                if("Actualización".equals(tipo)){
                    tipo="U";
                }
            }
        }
        //Buscar por 
        if(tipo=="Todos") tipo=null;
        if(almacen=="Todos") almacen=null;
        if(nickname=="") nickname=null;
        if(fechaDe=="") fechaDe=null;
        if(fechaA=="") fechaA=null;
        String filtro="";
        int casofiltro=0;  //8 casos
        if(almacen==null && tipo==null && nickname==null && (fechaDe!=null && fechaA!=null)){ casofiltro=1; filtro=""; }else{    //solo DE fechas
        if(almacen!=null && tipo==null && nickname==null && (fechaDe!=null && fechaA!=null)){ casofiltro=2; filtro="AND merc.almacen LIKE ?"; }else{    //A y DE
        if(almacen==null && tipo!=null && nickname==null && (fechaDe!=null && fechaA!=null)){ casofiltro=3; filtro="AND mov.tipo LIKE ?"; }else{    //B y DE
        if(almacen==null && tipo==null && nickname!=null && (fechaDe!=null && fechaA!=null)){ casofiltro=4; filtro="AND u.nickname LIKE ?"; }else{    //C y DE
        if(almacen!=null && tipo!=null && nickname==null && (fechaDe!=null && fechaA!=null)){ casofiltro=5; filtro="AND merc.almacen LIKE ? AND mov.tipo LIKE ?"; }else{    //A, B y DE
        if(almacen!=null && tipo==null && nickname!=null && (fechaDe!=null && fechaA!=null)){ casofiltro=6; filtro="AND merc.almacen LIKE ? AND u.nickname LIKE ?"; }else{    //A, C y DE
        if(almacen==null && tipo!=null && nickname!=null && (fechaDe!=null && fechaA!=null)){ casofiltro=7; filtro="AND mov.tipo LIKE ? AND u.nickname LIKE ?"; }else{    //B, C y DE
        if(almacen!=null && tipo!=null && nickname!=null && (fechaDe!=null && fechaA!=null))  casofiltro=8; filtro="AND merc.almacen LIKE ? AND mov.tipo LIKE ? AND u.nickname LIKE ?"; }}}}}}}   // A, B, C y DE
        
        String consulta;
        try {
            consulta = ("""
            SELECT mov.asunto, merc.codigo, merc.articulo, merc.descripcion, merc.marca, merc.almacen, mov.tipo, mov.cantidad, u.nickname, mov.fecha FROM movimiento AS mov
            JOIN mercancia AS merc ON mov.IDmercancia = merc.IDmercancia
            JOIN usuarios AS u ON mov.IDusuario = u.IDusuario
            WHERE """ + " " + modo + " " + filtro + " AND mov.fecha BETWEEN '" + fechaDe + "' AND '" + fechaA + "' " +
            "ORDER BY mov.fecha DESC, mov.IDmovimiento DESC");
            cmd = (PreparedStatement) conexion.conectar.prepareStatement(consulta);
            cmd.setString(1, buscar + "%");
            if (casofiltro == 1){ }else{
            if (casofiltro == 2){ cmd.setString(2, almacen); }else{
            if (casofiltro == 3){ cmd.setString(2, tipo); }else{
            if (casofiltro == 4){ cmd.setString(2, nickname); }else{ 
            if (casofiltro == 5){ cmd.setString(2, almacen); cmd.setString(3, tipo); }else{
            if (casofiltro == 6){ cmd.setString(2, almacen); cmd.setString(3, nickname); }else{
            if (casofiltro == 7){ cmd.setString(2, tipo); cmd.setString(3, nickname); }else{
            if (casofiltro == 8){ cmd.setString(2, almacen); cmd.setString(3, tipo); cmd.setString(4, nickname);}}}}}}}}
            result = cmd.executeQuery();
            llenarTablaConsult(result);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Error al consultar.");
        }
    }
    
    private void cmbConsultAlmacenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbConsultAlmacenItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbConsultAlmacenItemStateChanged

    private void cmbConsultAlmacenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConsultAlmacenActionPerformed
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            btnFiltrarConsult.setEnabled(false);   
        }else{
            btnFiltrarConsult.setEnabled(true);
        }
    }//GEN-LAST:event_cmbConsultAlmacenActionPerformed

    private void txtConsultCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConsultCodigoMouseClicked
        txtConsultCodigo.setText(""); txtConsultArticulo.setText(""); cmbConsultTipo.setSelectedItem("Por Descripción"); cmbConsultMarca.setSelectedItem(null);
    }//GEN-LAST:event_txtConsultCodigoMouseClicked

    private void txtConsultCodigoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConsultCodigoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConsultCodigoMousePressed

    private void txtConsultCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConsultCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConsultCodigoActionPerformed

    private void txtConsultCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConsultCodigoKeyReleased
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            consultFiltrosDefault("codigo",txtConsultCodigo.getText());
        }   
    }//GEN-LAST:event_txtConsultCodigoKeyReleased

    private void txtConsultArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConsultArticuloMouseClicked
        txtConsultCodigo.setText(""); cmbConsultMarca.setSelectedItem(null); txtConsultArticulo.setText("");
    }//GEN-LAST:event_txtConsultArticuloMouseClicked

    private void txtConsultArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConsultArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConsultArticuloActionPerformed

    private void txtConsultArticuloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConsultArticuloKeyReleased
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            if (cmbConsultTipo.getSelectedItem().toString().equals("Por Descripción")){
                consultFiltrosDefault("descripcion",txtConsultArticulo.getText());
            }else{
                consultFiltrosDefault("articulo",txtConsultArticulo.getText());
            }
        }
    }//GEN-LAST:event_txtConsultArticuloKeyReleased

    private void cmbConsultTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbConsultTipoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbConsultTipoItemStateChanged

    private void cmbConsultTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConsultTipoActionPerformed
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            if (cmbConsultTipo.getSelectedItem().toString().equals("Por Descripción")){
                consultFiltrosDefault("descripcion",txtConsultArticulo.getText());
            }else{
                consultFiltrosDefault("articulo",txtConsultArticulo.getText());
            }
        }
    }//GEN-LAST:event_cmbConsultTipoActionPerformed

    private void cmbConsultMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbConsultMarcaMouseClicked
        txtConsultCodigo.setText(""); txtConsultArticulo.setText("");
    }//GEN-LAST:event_cmbConsultMarcaMouseClicked

    private void cmbConsultMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConsultMarcaActionPerformed
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            if(cmbConsultMarca.getSelectedItem()==null){
                try{
                    String consulta=("""
                    SELECT mov.asunto, merc.codigo, merc.articulo, merc.descripcion, merc.marca, merc.almacen, mov.tipo, mov.cantidad, u.nickname, mov.fecha FROM movimiento AS mov
                    JOIN mercancia AS merc ON mov.IDmercancia = merc.IDmercancia
                    JOIN usuarios AS u ON mov.IDusuario = u.IDusuario
                    ORDER BY mov.fecha DESC, mov.IDmovimiento DESC""");
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    result=cmd.executeQuery();
                    llenarTablaConsult(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            }else{
                consultFiltrosDefault("marca",cmbConsultMarca.getSelectedItem().toString());
            }
        }
    }//GEN-LAST:event_cmbConsultMarcaActionPerformed

    private void btnBorrarConsultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarConsultActionPerformed
        cmbConsultAlmacen.setSelectedItem("Todos"); cmbConsultMov.setSelectedItem("Todos"); cmbConsultNick.setSelectedItem(null);
        dateConsultDe.setDate(null); dateConsultA.setDate(null);
        txtConsultCodigo.setText(""); txtConsultArticulo.setText(""); cmbConsultTipo.setSelectedItem("Por Descripción"); cmbConsultMarca.setSelectedItem(null);
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            btnFiltrarConsult.setEnabled(false);   
        }else{
            btnFiltrarConsult.setEnabled(true);
        }
    }//GEN-LAST:event_btnBorrarConsultActionPerformed

    private void btnFiltrarConsultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarConsultActionPerformed
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormat1 = ""; String fechaFormat2 = ""; String nickTo="";
        if((dateConsultDe.getDate()==null && dateConsultA.getDate()!=null) || (dateConsultDe.getDate()!=null && dateConsultA.getDate()==null)){
            JOptionPane.showMessageDialog(null, "Ingrese una fecha válida.");
        }else{
            int resultadoComparacion=0;
            if(dateConsultDe.getDate()==null && dateConsultA.getDate()==null){
                try{
                    String getDBFecha="SELECT MIN(fecha) FROM movimiento";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(getDBFecha);
                    result=cmd.executeQuery();
                    while(result.next()){
                        fechaFormat1=result.getString(1);
                    }
                }catch(SQLException ex) {
                    JOptionPane.showMessageDialog(null, result);
                }
                fechaFormat2=fecha.toString();
            }else{
                resultadoComparacion = dateConsultDe.getDate().compareTo(dateConsultA.getDate());
                if (resultadoComparacion < 0) {
                    //Fecha 2 es mayor a fecha1 (Orden normal)
                    fechaFormat1 = formato.format(dateConsultDe.getDate());
                    fechaFormat2 = formato.format(dateConsultA.getDate());
                } else if (resultadoComparacion == 0) {
                    //Las fechas son iguales
                    fechaFormat1 = formato.format(dateConsultDe.getDate());
                    fechaFormat2 = formato.format(dateConsultA.getDate());
                } else {
                    //Fecha 1 es mayor que fecha2 (Orden inverso)
                    fechaFormat2 = formato.format(dateConsultDe.getDate());
                    fechaFormat1 = formato.format(dateConsultA.getDate());
                }
            }
            if (cmbConsultNick.getSelectedItem()==null) {nickTo="";}else{nickTo=cmbConsultNick.getSelectedItem().toString();}
            if(txtConsultCodigo.getText()==null && txtConsultArticulo.getText()==null && cmbConsultMarca.getSelectedItem()==null){}else{
                //Buscar articulo con filtro
                consultFiltrosActivos(cmbConsultAlmacen.getSelectedItem().toString(), cmbConsultMov.getSelectedItem().toString(),nickTo, fechaFormat1, fechaFormat2);
            }
            //
            if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
             && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
                btnFiltrarConsult.setEnabled(false);
            }else{
                btnFiltrarConsult.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnFiltrarConsultActionPerformed

    private void cmbConsultMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConsultMovActionPerformed
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            btnFiltrarConsult.setEnabled(false);   
        }else{
            btnFiltrarConsult.setEnabled(true);
        }
    }//GEN-LAST:event_cmbConsultMovActionPerformed

    private void cmbConsultNickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConsultNickActionPerformed
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            btnFiltrarConsult.setEnabled(false);   
        }else{
            btnFiltrarConsult.setEnabled(true);
        }
    }//GEN-LAST:event_cmbConsultNickActionPerformed

    private void dateConsultDePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateConsultDePropertyChange
        if(dateConsultA.getDate()==null){
            dateConsultA.setDate(fecha);
        }
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            btnFiltrarConsult.setEnabled(false);   
        }else{
            btnFiltrarConsult.setEnabled(true);
        }
    }//GEN-LAST:event_dateConsultDePropertyChange

    private void dateConsultAPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateConsultAPropertyChange
        if(cmbConsultAlmacen.getSelectedItem().toString().equals("Todos") && cmbConsultMov.getSelectedItem().equals("Todos") && cmbConsultNick.getSelectedItem()==null
         && dateConsultDe.getDate()==null && dateConsultA.getDate()==null    ){
            btnFiltrarConsult.setEnabled(false);   
        }else{
            btnFiltrarConsult.setEnabled(true);
        }
    }//GEN-LAST:event_dateConsultAPropertyChange

    private void dateConsultDeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateConsultDeKeyReleased
        
    }//GEN-LAST:event_dateConsultDeKeyReleased

    private void dateConsultAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateConsultAKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_dateConsultAKeyReleased

    //Códigos perzonalizados
    
    private void btnBorrarPerCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarPerCodActionPerformed
        txtCodigoPerCod.setText(""); txtArticuloPerCod.setText(""); cmbTipoPerCod.setSelectedItem("Por Descripción"); cmbMarcaPerCod.setSelectedItem(null);
        cmbAlmacenPerCod.setSelectedItem("Todos");
    }//GEN-LAST:event_btnBorrarPerCodActionPerformed

    private void cmbMarcaPerCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMarcaPerCodActionPerformed
        String consulta;
        if(cmbMarcaPerCod.getSelectedItem() == null){
            try{
                if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                    consulta="SELECT * FROM mercancia ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                }else{
                    consulta="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbAlmacenPerCod.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaPerCod(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }else{
            try{
                if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbMarcaPerCod.getSelectedItem().toString());
                }else{
                    consulta="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                    cmd.setString(1, cmbMarcaPerCod.getSelectedItem().toString());
                    cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaPerCod(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
            }
        }
    }//GEN-LAST:event_cmbMarcaPerCodActionPerformed

    private void cmbMarcaPerCodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbMarcaPerCodMouseClicked
        txtCodigoPerCod.setText(""); txtArticuloPerCod.setText("");
    }//GEN-LAST:event_cmbMarcaPerCodMouseClicked

    private void cmbTipoPerCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoPerCodActionPerformed
        String consulta;
        switch(cmbTipoPerCod.getSelectedItem().toString()){
            case "Por Descripción":
                try{
                    if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                        cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaPerCod(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
            case "Por Nombre":
                try{
                    if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                        cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaPerCod(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
        }
    }//GEN-LAST:event_cmbTipoPerCodActionPerformed

    private void cmbTipoPerCodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoPerCodItemStateChanged
        txtArticuloPerCod.grabFocus();
    }//GEN-LAST:event_cmbTipoPerCodItemStateChanged

    private void txtArticuloPerCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloPerCodKeyReleased
        String consulta;
        switch(cmbTipoPerCod.getSelectedItem().toString()){
            case "Por Descripción":
                try{
                    if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                        cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaPerCod(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
            case "Por Nombre":
                try{
                    if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                    }else{
                        consulta="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                        cmd.setString(1, txtArticuloPerCod.getText() + "%");
                        cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaPerCod(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
                }
            break;
        }
    }//GEN-LAST:event_txtArticuloPerCodKeyReleased

    private void txtArticuloPerCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArticuloPerCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArticuloPerCodActionPerformed

    private void txtArticuloPerCodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtArticuloPerCodMouseClicked
        txtCodigoPerCod.setText(""); cmbMarcaPerCod.setSelectedItem(null); txtArticuloPerCod.setText("");
    }//GEN-LAST:event_txtArticuloPerCodMouseClicked

    private void txtCodigoPerCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPerCodKeyReleased
        String consulta;
        try{
            if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? ORDER BY articulo ASC";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtCodigoPerCod.getText() + "%");
            }else{
                consulta="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1, txtCodigoPerCod.getText() + "%");
                cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
            }
            result=cmd.executeQuery();
            llenarTablaPerCod(result);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane,"Error al consultar.");
        }
    }//GEN-LAST:event_txtCodigoPerCodKeyReleased

    private void txtCodigoPerCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoPerCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoPerCodActionPerformed

    private void txtCodigoPerCodMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoPerCodMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoPerCodMousePressed

    private void txtCodigoPerCodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoPerCodMouseClicked
        txtCodigoPerCod.setText(""); txtArticuloPerCod.setText(""); cmbTipoPerCod.setSelectedItem("Por Descripción"); cmbMarcaPerCod.setSelectedItem(null);
    }//GEN-LAST:event_txtCodigoPerCodMouseClicked

    private void cmbAlmacenPerCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAlmacenPerCodActionPerformed
        String materiales;
        //Si todo está vacio
        if(txtCodigoPerCod.getText().isEmpty() && txtArticuloPerCod.getText().isEmpty() && cmbMarcaPerCod.getSelectedItem()==null){
            try{
                if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                    materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                }else{
                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                    cmd.setString(1, cmbAlmacenPerCod.getSelectedItem().toString());
                }
                result=cmd.executeQuery();
                llenarTablaPerCod(result);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al consultar.");
            }
        }else{
            //Si solo el código está escrito
            if(txtArticuloPerCod.getText().isEmpty() && cmbMarcaPerCod.getSelectedItem()==null){
                try{
                    if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtCodigoPerCod.getText() + "%");
                    }else{
                        materiales="SELECT * FROM mercancia WHERE codigo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                        cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                        cmd.setString(1, txtCodigoPerCod.getText() + "%");
                        cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                    }
                    result=cmd.executeQuery();
                    llenarTablaPerCod(result);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null,"Error al consultar.");
                }
            }else{
                //Si solo el articulo está escrito
                if(txtCodigoPerCod.getText().isEmpty() && cmbMarcaPerCod.getSelectedItem()==null){
                    switch(cmbTipoPerCod.getSelectedItem().toString()){
                        case "Por Descripción":
                            try{
                                if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtArticuloPerCod.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE descripcion LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtArticuloPerCod.getText() + "%");
                                    cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaPerCod(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(null,"Error al consultar.");
                            }
                        break;
                        case "Por Nombre":
                            try{
                                if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtArticuloPerCod.getText() + "%");
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE articulo LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, txtArticuloPerCod.getText() + "%");
                                    cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaPerCod(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(null,"Error al consultar.");
                            }
                        break;
                    }
                }else{
                    //Si solo está seleccionada la marca
                    if(txtCodigoPerCod.getText().isEmpty() && txtArticuloPerCod.getText().isEmpty()){
                        if(cmbMarcaPerCod.getSelectedItem() == null){
                            try{
                                if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE almacen LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbAlmacenPerCod.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaPerCod(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(null,"Error al consultar.");
                            }
                        }else{
                            try{
                                if(cmbAlmacenPerCod.getSelectedItem().toString().equals("Todos")){
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbMarcaPerCod.getSelectedItem().toString());
                                }else{
                                    materiales="SELECT * FROM mercancia WHERE marca LIKE ? AND almacen LIKE ? ORDER BY articulo ASC";
                                    cmd=(PreparedStatement)conexion.conectar.prepareStatement(materiales);
                                    cmd.setString(1, cmbMarcaPerCod.getSelectedItem().toString());
                                    cmd.setString(2, cmbAlmacenPerCod.getSelectedItem().toString());
                                }
                                result=cmd.executeQuery();
                                llenarTablaPerCod(result);
                            }catch(SQLException e){
                                JOptionPane.showMessageDialog(null,"Error al consultar.");
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_cmbAlmacenPerCodActionPerformed

    private void cmbAlmacenPerCodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAlmacenPerCodItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAlmacenPerCodItemStateChanged

    private void cmbEditRepisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEditRepisaActionPerformed
        if ("Otra".equals(cmbEditRepisa.getSelectedItem())) {
            cual4.setVisible(true);
            txtEditRepisa.setVisible(true);
            txtEditRepisa.setEnabled(true);
        }else{
            cual4.setVisible(false);
            txtEditRepisa.setVisible(false);
            txtEditRepisa.setEnabled(false);
        }
    }//GEN-LAST:event_cmbEditRepisaActionPerformed

    private void cmbEditAnaquelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEditAnaquelActionPerformed
        if ("Otra".equals(cmbEditAnaquel.getSelectedItem())) {
            cual3.setVisible(true);
            txtEditAnaquel.setVisible(true);
            txtEditAnaquel.setEnabled(true);
        }else{
            cual3.setVisible(false);
            txtEditAnaquel.setVisible(false);
            txtEditAnaquel.setEnabled(false);
        }
    }//GEN-LAST:event_cmbEditAnaquelActionPerformed

    private void cmbEditPresentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEditPresentActionPerformed
        if ("Otra".equals(cmbEditPresent.getSelectedItem())) {
            cual1.setVisible(true);
            txtEditPresent.setVisible(true);
            txtEditPresent.setEnabled(true);
        }else{
            cual1.setVisible(false);
            txtEditPresent.setVisible(false);
            txtEditPresent.setEnabled(false);
        }
    }//GEN-LAST:event_cmbEditPresentActionPerformed

    private void editDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editDescKeyTyped
        StringBuilder cadena=new StringBuilder(editDesc.getText());
        if(editDesc.getText().length() >=300){
            JOptionPane.showMessageDialog(null, "Limite maximo de 300 caracteres.");
            cadena.deleteCharAt(cadena.length()-1);
            editDesc.setText(cadena.toString());
            cadena=null;
        }
    }//GEN-LAST:event_editDescKeyTyped

    private void cmbEditMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEditMarcaActionPerformed
        if ("Otra".equals(cmbEditMarca.getSelectedItem())) {
            cual2.setVisible(true);
            txtEditMarca.setVisible(true);
            txtEditMarca.setEnabled(true);
        }else{
            cual2.setVisible(false);
            txtEditMarca.setVisible(false);
            txtEditMarca.setEnabled(false);
        }
    }//GEN-LAST:event_cmbEditMarcaActionPerformed

        //Realizar movimientos
    
    private void tblPerCodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPerCodMouseClicked
        int fila=tblPerCod.getSelectedRow();
        try{
            String idPerCodMer=tblPerCod.getValueAt(fila, 0).toString();
            String consulta="SELECT * FROM mercancia WHERE codigo=?";
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
            cmd.setString(1,idPerCodMer);
            result=cmd.executeQuery();
            if(result.next()){
                staticIdMerc=result.getString(1);
                txtEditCod.setText(result.getString(2));
                txtEditArt.setText(result.getString(3));
                editDesc.setText(result.getString(4));
                cmbEditMarca.setSelectedItem(result.getString(5));
                cmbEditPresent.setSelectedItem(result.getString(6));
                spinEditCant.setValue(Integer.valueOf(result.getString(7)));
                cmbEditAlmacen.setSelectedItem(result.getString(8));
                cmbEditAnaquel.setSelectedItem(result.getString(9));
                cmbEditRepisa.setSelectedItem(result.getString(10));
            }
            cmd.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Error en consulta");
        }
    }//GEN-LAST:event_tblPerCodMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        perCodAutorizar=false;
        autorizarEdicion();
        if(perCodAutorizar==true){
            activarEdicion();
        }else{
            desactivarEdicion();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        txtEditCod.setText(""); txtEditArt.setText(""); spinEditCant.setValue(0);
        txtEditPresent.setText(""); txtEditAnaquel.setText(""); txtEditRepisa.setText(""); txtEditMarca.setText("");
        editDesc.setText("\n                                              SELECCIONE UN ELEMENTO DE LA TABLA PARA VER SU INFORMACIÓN");
        cmbEditMarca.setSelectedItem(null);
        cmbEditPresent.setSelectedItem(null); cmbEditAlmacen.setSelectedItem(null);
        cmbEditAnaquel.setSelectedItem(null); cmbEditRepisa.setSelectedItem(null);
        desactivarEdicion();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        boolean camposLlenos=true;
        //Verifica que los campos estén llenos
        if(txtEditCod.getText().isEmpty() || txtEditArt.getText().isEmpty() || editDesc.getText().isEmpty() || cmbEditMarca.getSelectedItem()==null || cmbEditPresent.getSelectedItem()==null || cmbEditAlmacen.getSelectedItem()==null
        || cmbEditAnaquel.getSelectedItem()==null || cmbEditRepisa.getSelectedItem()==null    ){
            camposLlenos=false;
        }else{
            //Si otro esta seleccionado y el campo está vacio
                //Marca y presentación
            if(cmbEditMarca.getSelectedItem().equals("Otra")){  if(txtEditMarca.getText().isEmpty()){             camposLlenos=false;}
            }else{  if(cmbEditPresent.getSelectedItem().equals("Otra")){if(txtEditPresent.getText().isEmpty()){   camposLlenos=false;}}}
                //Ubicación
            if(cmbEditAnaquel.getSelectedItem().equals("Otra")){  if(txtEditAnaquel.getText().isEmpty()){             camposLlenos=false;}
            }else{  if(cmbEditRepisa.getSelectedItem().equals("Otra")){if(txtEditRepisa.getText().isEmpty()){   camposLlenos=false;}}}
        }
        if(camposLlenos==false){
            JOptionPane.showMessageDialog(null,"Llene todos los campos para continuar.");
        }else{
            //Actualiza información    staticIdMerc
            //Verificar duplicidad
            try{
                String consulta="SELECT IDmercancia FROM mercancia WHERE codigo=?";
                cmd=(PreparedStatement)conexion.conectar.prepareStatement(consulta);
                cmd.setString(1,txtEditCod.getText());
                result=cmd.executeQuery();
                if(result.next()){
                    String res_cod=result.getString(1);
                    if(res_cod.equals(staticIdMerc)){
                        actualizarInfo();
                    }else{
                        JOptionPane.showMessageDialog(null,"El código de este material ya fué registrado.");
                    }
                }else{
                    actualizarInfo();
                }
                iniciarPerCod();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al comprobar duplicidad.");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void gitLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gitLogoMouseClicked
        // Redirecciona a una pagina de GitHub
        if(java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop= java.awt.Desktop.getDesktop();
            if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try{
                    java.net.URI url= new java.net.URI("https://github.com/Giomine325/Almazen");
                    desktop.browse(url);
                }catch(URISyntaxException | IOException ex){}
            }
        }
    }//GEN-LAST:event_gitLogoMouseClicked

    private void logo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logo2MouseClicked
        // Redirecciona a una pagina de GitHub
        if(java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop= java.awt.Desktop.getDesktop();
            if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try{
                    java.net.URI url= new java.net.URI("https://github.com/Giomine325/Almazen/releases");
                    desktop.browse(url);
                }catch(URISyntaxException | IOException ex){}
            }
        }
    }//GEN-LAST:event_logo2MouseClicked

    private void lblImg2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImg2MouseClicked
        // Redirecciona a una pagina de GitHub
        if(java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop= java.awt.Desktop.getDesktop();
            if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try{
                    java.net.URI url= new java.net.URI("https://giomine325.github.io/Almazen/");
                    desktop.browse(url);
                }catch(URISyntaxException | IOException ex){}
            }
        }
    }//GEN-LAST:event_lblImg2MouseClicked

    private void actualizarInfo(){
        try{
            String registrar=("UPDATE mercancia SET codigo=?,articulo=?,descripcion=?,marca=?,presentacion=?,existencia=?,almacen=?,anaquel=?,repisa=? WHERE IDmercancia = ?");
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(registrar);
            cmd.setString(10, staticIdMerc);
            cmd.setString(1, txtEditCod.getText());
            cmd.setString(2, txtEditArt.getText());
            cmd.setString(3, editDesc.getText());
            if(cmbEditMarca.getSelectedItem().toString().equals("Otra")){    cmd.setString(4, txtEditMarca.getText());
            }else{                          cmd.setString(4, cmbEditMarca.getSelectedItem().toString());}
            if(cmbEditPresent.getSelectedItem().toString().equals("Otra")){  cmd.setString(5, txtEditPresent.getText());
            }else{                          cmd.setString(5, cmbEditPresent.getSelectedItem().toString());}
            cmd.setString(6, spinEditCant.getValue().toString());
            cmd.setString(7, cmbEditAlmacen.getSelectedItem().toString());
            if(cmbEditAnaquel.getSelectedItem().toString().equals("Otra")){    cmd.setString(8, txtEditAnaquel.getText());
            }else{                          cmd.setString(8, cmbEditAnaquel.getSelectedItem().toString());}
            if(cmbEditRepisa.getSelectedItem().toString().equals("Otra")){  cmd.setString(9, txtEditRepisa.getText());
            }else{                          cmd.setString(9, cmbEditRepisa.getSelectedItem().toString());}
            cmd.executeUpdate();
            cmd.close();
            //Registrar movimiento
            String registrarMovimiento=("INSERT INTO movimiento(IDusuario,IDmercancia,tipo,asunto,fecha,cantidad) VALUES (?,?,?,?,?,?)");
            cmd=(PreparedStatement)conexion.conectar.prepareStatement(registrarMovimiento);
            cmd.setString(1, id);
            cmd.setString(2, staticIdMerc);
            cmd.setString(3, "U");
            cmd.setString(4, "Actualización de datos del material.");
            cmd.setDate(5, fecha);
            cmd.setString(6, spinEditCant.getValue().toString());
            cmd.executeUpdate();
            cmd.close();
            JOptionPane.showMessageDialog(null,"Actualización exitosa.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error 007: Error al registrar material.");
        }
    }
    
    public void setImageIn(JLabel a,String route){
        ImageIcon img; Icon icono;
        img = new ImageIcon(getClass().getResource(route));
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
        java.awt.EventQueue.invokeLater(() -> {
            new Inicio(usuario).setVisible(true);
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTabbedPane Pestañas;
    private javax.swing.JButton btnAboutUs;
    private javax.swing.JButton btnAddMov;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBorrarConsult;
    private javax.swing.JButton btnBorrarMov;
    private javax.swing.JButton btnBorrarPerCod;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDeleteMov;
    private javax.swing.JButton btnDisplayAdmin;
    private javax.swing.JButton btnDisplayBuscar;
    private javax.swing.JButton btnDisplayCodes;
    private javax.swing.JButton btnDisplayConsulta;
    private javax.swing.JButton btnDisplayInicio;
    private javax.swing.JButton btnDisplayMov;
    private javax.swing.JButton btnDisplayRegNew;
    private javax.swing.JButton btnDisplaySettings;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFiltrarConsult;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMover;
    private javax.swing.JButton btnRestMov;
    private javax.swing.JButton btnSumMov;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbAlmacen;
    private javax.swing.JComboBox<String> cmbAlmacenMov;
    private javax.swing.JComboBox<String> cmbAlmacenPerCod;
    private javax.swing.JComboBox<String> cmbBuscarMarca;
    private javax.swing.JComboBox<String> cmbBuscarMarcaMov;
    private javax.swing.JComboBox<String> cmbBuscarTipo;
    private javax.swing.JComboBox<String> cmbBuscarTipoMov;
    private javax.swing.JComboBox<String> cmbConsultAlmacen;
    private javax.swing.JComboBox<String> cmbConsultMarca;
    private javax.swing.JComboBox<String> cmbConsultMov;
    private javax.swing.JComboBox<String> cmbConsultNick;
    private javax.swing.JComboBox<String> cmbConsultTipo;
    private javax.swing.JComboBox<String> cmbEditAlmacen;
    private javax.swing.JComboBox<String> cmbEditAnaquel;
    private javax.swing.JComboBox<String> cmbEditMarca;
    private javax.swing.JComboBox<String> cmbEditPresent;
    private javax.swing.JComboBox<String> cmbEditRepisa;
    private javax.swing.JComboBox<String> cmbMarcaPerCod;
    private javax.swing.JComboBox<String> cmbTipoMov;
    private javax.swing.JComboBox<String> cmbTipoPerCod;
    private javax.swing.JLabel cual1;
    private javax.swing.JLabel cual2;
    private javax.swing.JLabel cual3;
    private javax.swing.JLabel cual4;
    private com.toedter.calendar.JDateChooser dateConsultA;
    private com.toedter.calendar.JDateChooser dateConsultDe;
    private javax.swing.JTextArea editDesc;
    private javax.swing.JLabel gitLogo;
    private javax.swing.JLabel imglat1;
    private javax.swing.JLabel imglat2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextArea jTextArea1;
    public javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblImg2;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel logo2;
    private javax.swing.JPanel panelBuscar;
    private javax.swing.JPanel panelCodes;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JPanel panelEditar;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelMov;
    private javax.swing.JSpinner spinCantAdd;
    private javax.swing.JSpinner spinCantRest;
    private javax.swing.JSpinner spinEditCant;
    private javax.swing.JTable tblBuscar;
    private javax.swing.JTable tblConsulta;
    private javax.swing.JTable tblMoverMaterial;
    private javax.swing.JTable tblPerCod;
    private javax.swing.JTable tblSeleccionarMaterial;
    private javax.swing.JTextField txtArticuloPerCod;
    private javax.swing.JTextArea txtAsunto;
    private javax.swing.JTextField txtBuscarArticulo;
    private javax.swing.JTextField txtBuscarArticuloMov;
    private javax.swing.JTextField txtBuscarCodigo;
    private javax.swing.JTextField txtBuscarCodigoMov;
    private javax.swing.JTextField txtCodigoPerCod;
    private javax.swing.JTextField txtConsultArticulo;
    private javax.swing.JTextField txtConsultCodigo;
    private javax.swing.JTextField txtEditAnaquel;
    private javax.swing.JTextField txtEditArt;
    private javax.swing.JTextField txtEditCod;
    private javax.swing.JTextField txtEditMarca;
    private javax.swing.JTextField txtEditPresent;
    private javax.swing.JTextField txtEditRepisa;
    // End of variables declaration//GEN-END:variables
}
