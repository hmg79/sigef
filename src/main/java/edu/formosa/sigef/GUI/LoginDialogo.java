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
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginDialogo extends JDialog {
  private final JTextField user = new JTextField(15);
    private final JPasswordField pass = new JPasswordField(15);
    private final JButton btn = new JButton("Ingresar");
    private UserSession session;
    private final AuthServicio auth = new AuthServicio(new AutenDaoJdbc());

    public LoginDialogo(Frame parent){
        super(parent, "Acceso | SIGEF", true);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx=0;c.gridy=0; add(new JLabel("Usuario:"), c);
        c.gridx=1; add(user, c);
        c.gridx=0;c.gridy=1; add(new JLabel("Clave:"), c);
        c.gridx=1; add(pass, c);
        c.gridx=0;c.gridy=2;c.gridwidth=2; add(btn, c);

        btn.addActionListener(e -> doLogin());
        getRootPane().setDefaultButton(btn);
        pack();
        setLocationRelativeTo(parent);
    }

    private void doLogin(){
        Optional<UserSession> s = auth.login(user.getText().trim(), new String(pass.getPassword()));
        if (s.isPresent()){
            session = s.get();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas o usuario inactivo", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public UserSession getSession(){ return session; }
}
