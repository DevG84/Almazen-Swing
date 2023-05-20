package settings;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class customPane {
    public static void showMessageDialogWithTimeout(String message, String title, int messageType, int timeout) {
        FlatArcIJTheme.setup();
        
        final JOptionPane optionPane = new JOptionPane(message, messageType);

        // Carga la imagen de icono desde los recursos del proyecto
        ImageIcon icon = new ImageIcon(customPane.class.getResource("/sources/progress_32px.gif"));
        optionPane.setIcon(icon);

        final JDialog dialog = optionPane.createDialog(title);

        Timer timer = new Timer(timeout, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo después del tiempo especificado
            }
        });
        timer.setRepeats(false);
        timer.start();

        // Establecer el icono de la aplicación personalizado
        try {
            ImageIcon icon2 = new ImageIcon(customPane.class.getResource("/icon_32px.png"));
            dialog.setIconImage(icon2.getImage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        dialog.setVisible(true);
    }
}

