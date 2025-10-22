/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Interface Principal
*/
package edu.formosa.sigef.GUI;

/**
 *
 * @author HÉCTOR M GALLI
 */
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

    public mainWindows(UserSession session){
        super("SIGEF – Informes");
        this.session = session;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(header(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        // Panel de filtros (izquierda)
        PanelFiltros filtros = new PanelFiltros(session, reportes, table, status);
        add(filtros, BorderLayout.WEST);
    }

    private JPanel header(){
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        JLabel title = new JLabel("Módulo de Informes", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        p.add(title, BorderLayout.WEST);

        String rolLegible = (session.esInstitucion() || "INSTITUCION".equalsIgnoreCase(session.getRolCodigo())) ?
                "Institución" : (session.esDepartamento() || "DEPARTAMENTO".equalsIgnoreCase(session.getRolCodigo())) ?
                "Departamento" : session.getRolCodigo();

        JLabel userInfo = new JLabel("Usuario: " + session.getUsername() + "   |   Tipo: " + rolLegible);
        userInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        p.add(userInfo, BorderLayout.EAST);
        return p;
    }
}
