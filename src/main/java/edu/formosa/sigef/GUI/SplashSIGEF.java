/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package edu.formosa.sigef.GUI;

/**
 *
 * @author HÉCTOR M GALLI
 */
import javax.swing.*;
import java.awt.*;
/**
 * Splash animado estilo Tajima DG
 * Muestra logo, barra y texto dinámico con efecto de fade-in / fade-out.
 */ 
 public class SplashSIGEF  extends JWindow{
     
    private final JLabel lblTexto = new JLabel("Cargando módulos", SwingConstants.CENTER);

    public SplashSIGEF() {
        // Panel principal
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        // Imagen central (colocar tu imagen en resources/formosa.png)
        JLabel logo = new JLabel(new ImageIcon("src/main/resources/formosa.png"));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        // Texto de carga
        lblTexto.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblTexto.setForeground(new Color(60, 60, 60));

        // Layout general
        content.setLayout(new BorderLayout(10, 10));
        content.add(logo, BorderLayout.CENTER);
        content.add(lblTexto, BorderLayout.SOUTH);

        setSize(420, 300);
        setLocationRelativeTo(null);
        setOpacity(0f); // arranca transparente
    }

    public void mostrar() {
        setVisible(true);
        animarFadeIn();

        // Animación de texto en segundo plano
        Thread texto = new Thread(this::animarTexto);
        texto.start();

        // Duración total visible
        dormir(3500);

        animarFadeOut();
        setVisible(false);
        dispose();
    }

    private void animarTexto() {
        String base = "Cargando módulos";
        try {
            for (int i = 0; i < 12; i++) {
                lblTexto.setText(base + ".".repeat(i % 4));
                Thread.sleep(350);
            }
        } catch (InterruptedException ignored) {}
    }

    private void animarFadeIn() {
        for (float i = 0; i <= 1.0f; i += 0.05f) {
            setOpacity(i);
            dormir(40);
        }
    }

    private void animarFadeOut() {
        for (float i = 1.0f; i >= 0; i -= 0.05f) {
            setOpacity(i);
            dormir(40);
        }
    }

    private void dormir(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
