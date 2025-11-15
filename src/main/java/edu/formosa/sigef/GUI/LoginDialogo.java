/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Interface de Ingreso al sistema
*/
package edu.formosa.sigef.GUI;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.UserSession;
import edu.formosa.sigef.LOGICA.AuthServicio;
import edu.formosa.sigef.PERSISTENCIA.AutenDaoJdbc;
import edu.formosa.sigef.PERSISTENCIA.PersistenciaExcepciones;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginDialogo extends JDialog {
    private final JTextField user = new JTextField(15);
    private final JPasswordField pass = new JPasswordField(15);
    private final JButton btn = new JButton("Ingresar");
    private UserSession session;
    private final AuthServicio auth = new AuthServicio(new AutenDaoJdbc());

    public LoginDialogo(Frame parent) {
        super(parent, "Acceso | SIGEF", true);
        setIconImage(new ImageIcon(getClass().getResource("/icono.png")).getImage());
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);

        c.gridx = 0; c.gridy = 0; add(new JLabel("Usuario:"), c);
        c.gridx = 1; add(user, c);
        c.gridx = 0; c.gridy = 1; add(new JLabel("Clave:"), c);
        c.gridx = 1; add(pass, c);
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2; add(btn, c);

        btn.addActionListener(e -> doLogin());
        getRootPane().setDefaultButton(btn);
        pack();
        setLocationRelativeTo(parent);

        // === Mensaje con efecto fade-out al cerrar ===
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                mostrarDespedidaAnimada();
            }
        });
    }

    /** Muestra un mensaje de despedida con efecto de fade-out */
    private void mostrarDespedidaAnimada() {
        JDialog despedida = new JDialog(this, false);
        despedida.setUndecorated(true);
        despedida.setSize(320, 120);
        despedida.setLocationRelativeTo(this);

        JLabel lbl = new JLabel("<html><center>Gracias por utilizar SIGEF.<br>Hasta pronto.</center></html>", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        despedida.add(lbl, BorderLayout.CENTER);

        despedida.setOpacity(0f);
        despedida.setVisible(true);

        // Pequeña animación de aparición y desvanecimiento
        new Thread(() -> {
            try {
                for (float i = 0f; i <= 1f; i += 0.1f) {
                    despedida.setOpacity(i);
                    Thread.sleep(40);
                }
                Thread.sleep(900); // tiempo visible
                for (float i = 1f; i >= 0f; i -= 0.1f) {
                    despedida.setOpacity(i);
                    Thread.sleep(40);
                }
            } catch (InterruptedException ignored) {}
            SwingUtilities.invokeLater(() -> {
                despedida.dispose();
                dispose(); // cierra el login
            });
        }).start();
    }

    private void doLogin() {
        try {
            Optional<UserSession> s = auth.login(
                    user.getText().trim(),
                    new String(pass.getPassword())
            );

            if (s.isPresent()) {
                session = s.get();
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos.",
                        "Acceso denegado",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (PersistenciaExcepciones ex) {
            JOptionPane.showMessageDialog(
                    this, ex.getMessage(),
                    "Aviso", JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error inesperado durante la autenticación:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE
            );
        } finally {
            pass.setText("");
        }
    }

    public UserSession getSession() {
        return session;
    }
}
