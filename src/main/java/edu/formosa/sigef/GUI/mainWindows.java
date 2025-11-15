/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Interface Principal
*/
package edu.formosa.sigef.GUI;

/*
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.UserSession;
import edu.formosa.sigef.LOGICA.ReporteServicio;
import edu.formosa.sigef.PERSISTENCIA.ReporteDaoJdbc;

import javax.swing.*;
import java.awt.*;



import edu.formosa.sigef.LOGICA.UserSession;
import edu.formosa.sigef.LOGICA.ReporteServicio;
import edu.formosa.sigef.PERSISTENCIA.ReporteDaoJdbc;

import javax.swing.*;
import java.awt.*;

public class mainWindows extends JFrame {
private final UserSession session;
    private final ReporteServicio reportes = new ReporteServicio(new ReporteDaoJdbc());
    private final JTable table = new JTable();
    private final JLabel status = new JLabel("Listo.");

    public mainWindows(UserSession session) {
        super("SIGEF – Informes");
        this.session = session;
        setIconImage(new ImageIcon(getClass().getResource("/icono.png")).getImage());

        // 👇 Evita cierre directo, para controlar evento de "X"
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                cerrarSesion(); // reutiliza el mismo método
            }
        });

        setSize(1200, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(header(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        // Panel de filtros
        PanelFiltros filtros = new PanelFiltros(session, reportes, table, status);
        add(filtros, BorderLayout.WEST);
    }

    private JPanel header() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JLabel title = new JLabel("Módulo de Informes", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        p.add(title, BorderLayout.WEST);

        // Panel derecho con info de usuario y botón de cierre
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        String rolLegible = (session.esInstitucion() || "INSTITUCION".equalsIgnoreCase(session.getRolCodigo())) ?
                "Institución" : (session.esDepartamento() || "DEPARTAMENTO".equalsIgnoreCase(session.getRolCodigo())) ?
                "Departamento" : session.getRolCodigo();

        JLabel userInfo = new JLabel("Usuario: " + session.getUsername() + "   |   Tipo: " + rolLegible);
        JButton btnLogout = new JButton("Cerrar sesión");
        btnLogout.addActionListener(e -> cerrarSesion());

        right.add(userInfo);
        right.add(btnLogout);
        p.add(right, BorderLayout.EAST);

        return p;
    }

    private void cerrarSesion() {
        // Ícono institucional para el diálogo
        ImageIcon icon = new ImageIcon(getClass().getResource("/icono.png"));

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que desea cerrar la sesión actual y volver al inicio?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon
        );

        if (opcion == JOptionPane.YES_OPTION) {
            registrarCierreSesion();
            dispose();

            // Mensaje de despedida
            JOptionPane.showMessageDialog(
                    null,
                    "Gracias por utilizar SIGEF.\nSesión finalizada correctamente.",
                    "Hasta pronto",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon
            );

            // Regresar al login
            SwingUtilities.invokeLater(() -> {
                LoginDialogo login = new LoginDialogo(null);
                login.setVisible(true);
                UserSession nuevaSesion = login.getSession();

                if (nuevaSesion != null) {
                    new mainWindows(nuevaSesion).setVisible(true);
                }
            });
        }
    }

    private void registrarCierreSesion() {
        try (var cn = edu.formosa.sigef.PERSISTENCIA.DB.getConnection()) {
            String sql = """
                INSERT INTO sesion_acceso(usuario_id, ip, exito, mensaje, creado_en)
                VALUES (?, ?, ?, ?, NOW())
            """;
            try (var ps = cn.prepareStatement(sql)) {
                ps.setObject(1, session.getUserId());
                ps.setString(2, obtenerIPLocal());
                ps.setBoolean(3, true);
                ps.setString(4, "Cierre de sesión exitoso");
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("⚠️ No se pudo registrar cierre de sesión: " + e.getMessage());
        }
    }

    private String obtenerIPLocal() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }
}