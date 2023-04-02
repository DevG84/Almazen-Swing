package code;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

public class Inicio extends javax.swing.JFrame {
    
    //Modulos
    public boolean m1=false,m2=false,m3=false,m4=false,m5=false;

    public Inicio() {
        initComponents();
        setIconImage(getIconImage());
        iniciarInterfaz();
        this.setLocationRelativeTo(this);
        
    }

    public void iniciarInterfaz(){
        Login l=new Login();
        l.setImageIn(logo, "src/sources/logo.png");
        
        
        Pestañas.setEnabledAt(0, false);
        Pestañas.setEnabledAt(1, false);
        Pestañas.setEnabledAt(2, false);
        Pestañas.setEnabledAt(3, false);
        Pestañas.setEnabledAt(4, false);
        //
        Pestañas.setSelectedIndex(0);
        
    }
    
    public void habilitarModulos(){
        btnDisplayBuscar.setEnabled(m1);
        btnDisplayMov.setEnabled(m2);
        btnDisplayRegNew.setEnabled(m2);
        btnDisplayConsulta.setEnabled(m3);
        btnDisplayCodes.setEnabled(m4);
        btnDisplayAdmin.setEnabled(m5);
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
        jPanel3 = new javax.swing.JPanel();
        Pestañas = new javax.swing.JTabbedPane();
        panelInicio = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelBuscar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelMov = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelConsulta = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelCodes = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Almazen");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(23, 32, 42));

        logo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnDisplayMov.setBackground(new java.awt.Color(160, 64, 0));
        btnDisplayMov.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplayMov.setForeground(new java.awt.Color(255, 255, 255));
        btnDisplayMov.setText("Movimientos");
        btnDisplayMov.setBorder(null);
        btnDisplayMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayMovActionPerformed(evt);
            }
        });

        btnDisplayBuscar.setBackground(new java.awt.Color(160, 64, 0));
        btnDisplayBuscar.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplayBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnDisplayBuscar.setText("Buscar");
        btnDisplayBuscar.setBorder(null);
        btnDisplayBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayBuscarActionPerformed(evt);
            }
        });

        btnDisplayConsulta.setBackground(new java.awt.Color(160, 64, 0));
        btnDisplayConsulta.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplayConsulta.setForeground(new java.awt.Color(255, 255, 255));
        btnDisplayConsulta.setText("Consultar movimientos");
        btnDisplayConsulta.setBorder(null);
        btnDisplayConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayConsultaActionPerformed(evt);
            }
        });

        btnDisplayCodes.setBackground(new java.awt.Color(160, 64, 0));
        btnDisplayCodes.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplayCodes.setForeground(new java.awt.Color(255, 255, 255));
        btnDisplayCodes.setText("Códigos perzonalizados");
        btnDisplayCodes.setBorder(null);
        btnDisplayCodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayCodesActionPerformed(evt);
            }
        });

        btnDisplayAdmin.setBackground(new java.awt.Color(255, 255, 255));
        btnDisplayAdmin.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplayAdmin.setForeground(new java.awt.Color(0, 0, 0));
        btnDisplayAdmin.setText("Ajustes del Administrador");
        btnDisplayAdmin.setBorder(null);
        btnDisplayAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayAdminActionPerformed(evt);
            }
        });

        btnDisplayInicio.setBackground(new java.awt.Color(160, 64, 0));
        btnDisplayInicio.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplayInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnDisplayInicio.setText("Inicio");
        btnDisplayInicio.setBorder(null);
        btnDisplayInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayInicioActionPerformed(evt);
            }
        });

        btnDisplayRegNew.setBackground(new java.awt.Color(160, 64, 0));
        btnDisplayRegNew.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplayRegNew.setForeground(new java.awt.Color(255, 255, 255));
        btnDisplayRegNew.setText("Registrar nuevo material");
        btnDisplayRegNew.setBorder(null);
        btnDisplayRegNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayRegNewActionPerformed(evt);
            }
        });

        btnDisplaySettings.setBackground(new java.awt.Color(255, 255, 255));
        btnDisplaySettings.setFont(new java.awt.Font("Microsoft YaHei", 0, 13)); // NOI18N
        btnDisplaySettings.setForeground(new java.awt.Color(0, 0, 0));
        btnDisplaySettings.setText("Ajustes");
        btnDisplaySettings.setBorder(null);
        btnDisplaySettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplaySettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDisplayBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDisplayInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDisplayMov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDisplayConsulta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDisplayCodes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDisplayRegNew, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisplayAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisplaySettings, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDisplayAdmin, btnDisplayRegNew, btnDisplaySettings});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDisplayInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisplayBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisplayMov, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisplayConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisplayCodes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisplayRegNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDisplayAdmin)
                .addGap(18, 18, 18)
                .addComponent(btnDisplaySettings)
                .addGap(12, 12, 12))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDisplayAdmin, btnDisplayBuscar, btnDisplayCodes, btnDisplayConsulta, btnDisplayInicio, btnDisplayMov, btnDisplayRegNew, btnDisplaySettings});

        jPanel3.setBackground(new java.awt.Color(23, 32, 42));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        Pestañas.setBackground(new java.awt.Color(51, 51, 51));

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Inicio");

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio);
        panelInicio.setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(1145, Short.MAX_VALUE))
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(597, Short.MAX_VALUE))
        );

        Pestañas.addTab("Inicio", panelInicio);

        panelBuscar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Buscar");

        javax.swing.GroupLayout panelBuscarLayout = new javax.swing.GroupLayout(panelBuscar);
        panelBuscar.setLayout(panelBuscarLayout);
        panelBuscarLayout.setHorizontalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(1139, Short.MAX_VALUE))
        );
        panelBuscarLayout.setVerticalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(597, Short.MAX_VALUE))
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
                .addContainerGap(1104, Short.MAX_VALUE))
        );
        panelMovLayout.setVerticalGroup(
            panelMovLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(597, Short.MAX_VALUE))
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
                .addContainerGap(1127, Short.MAX_VALUE))
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(597, Short.MAX_VALUE))
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
                .addContainerGap(1130, Short.MAX_VALUE))
        );
        panelCodesLayout.setVerticalGroup(
            panelCodesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(597, Short.MAX_VALUE))
        );

        Pestañas.addTab("Códigos almazen", panelCodes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Pestañas)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Pestañas))
        );

        Pestañas.setTabPlacement(JTabbedPane.TOP);
        Pestañas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        Pestañas.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
                return 0;
            }
        });

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
        
    }//GEN-LAST:event_btnDisplayMovActionPerformed

    private void btnDisplayBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayBuscarActionPerformed
        Pestañas.setSelectedIndex(1);
        
    }//GEN-LAST:event_btnDisplayBuscarActionPerformed

    private void btnDisplayConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayConsultaActionPerformed
        Pestañas.setSelectedIndex(3);
        
    }//GEN-LAST:event_btnDisplayConsultaActionPerformed

    private void btnDisplayAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDisplayAdminActionPerformed

    private void btnDisplayInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayInicioActionPerformed
        Pestañas.setSelectedIndex(0);
        
    }//GEN-LAST:event_btnDisplayInicioActionPerformed

    private void btnDisplaySettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplaySettingsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDisplaySettingsActionPerformed

    private void btnDisplayRegNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayRegNewActionPerformed
        
        
    }//GEN-LAST:event_btnDisplayRegNewActionPerformed

    private void btnDisplayCodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayCodesActionPerformed
        Pestañas.setSelectedIndex(4);
        
    }//GEN-LAST:event_btnDisplayCodesActionPerformed
    
    
    
    public static void main(String args[]) {
        /* Look and Feel */
        FlatArcIJTheme.setup();

        // create UI here...

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTabbedPane Pestañas;
    private javax.swing.JButton btnDisplayAdmin;
    private javax.swing.JButton btnDisplayBuscar;
    private javax.swing.JButton btnDisplayCodes;
    private javax.swing.JButton btnDisplayConsulta;
    private javax.swing.JButton btnDisplayInicio;
    private javax.swing.JButton btnDisplayMov;
    private javax.swing.JButton btnDisplayRegNew;
    private javax.swing.JButton btnDisplaySettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panelBuscar;
    private javax.swing.JPanel panelCodes;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelMov;
    // End of variables declaration//GEN-END:variables
}
