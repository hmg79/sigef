/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEST;

/**
 *
 * @author hmg79
 */
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class TestLogin {

    public static void main(String[] args) throws Exception {

        String username = "depadmin";  
        String password = "$2a$12$QO2faU.9Z7TJGLrhyU4WMevEEgzPzm4ujtjkY/SpYXS5eGqEzCN/K";   

        String url  = "jdbc:mysql://localhost:3306/sigef";  //acceso base de datos
        String user = "root";                                // Usuario MySQL
        String pass = "cisco213";                                // Password MySQL

        Connection cn = DriverManager.getConnection(url, user, pass);

        String sql = """
            SELECT nombre_usuario, contrasena_hash, intentos_fallidos, bloqueado_hasta
            FROM usuario
            WHERE nombre_usuario = ? AND activo = 1
        """;

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            System.out.println("❌ Usuario no encontrado o inactivo.");
            return;
        }

        String hash = rs.getString("contrasena_hash");

        System.out.println("=====================================");
        System.out.println("USUARIO: " + username);
        System.out.println("HASH EN BD: [" + hash + "]");
        System.out.println("PROBANDO BCRYPT...");
        System.out.println("=====================================");

        boolean ok = BCrypt.checkpw(password, hash);

        if (ok) {
            System.out.println("✔ CONTRASEÑA CORRECTA (BCrypt OK)");
        } else {
            System.out.println("❌ CONTRASEÑA INCORRECTA (BCrypt FAIL)");
        }

        System.out.println("=====================================");

        cn.close();
    }
}
