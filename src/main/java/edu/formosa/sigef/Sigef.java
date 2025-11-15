/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/*
Lanzador principal de la aplicación
*/
package edu.formosa.sigef;

/**
 *
 * @author Héctor Marciano Galli <hectormgalli@gmail.com>
 */
import edu.formosa.sigef.GUI.LoginDialogo;
import edu.formosa.sigef.GUI.SplashSIGEF;
import edu.formosa.sigef.GUI.mainWindows;
import edu.formosa.sigef.LOGICA.UserSession;
import javax.swing.*;
public class Sigef {
public static void main(String[] args){
    //Mostrar splash animado antes del login
    SplashSIGEF splash=new SplashSIGEF();
    splash.mostrar();
    
    //Lanzar el login
    SwingUtilities.invokeLater(() -> {
            LoginDialogo dlg = new LoginDialogo(null);
            dlg.setVisible(true);
            UserSession s = dlg.getSession();
            if (s == null) System.exit(0);
            new mainWindows(s).setVisible(true);
        });
    }
}
