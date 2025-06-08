/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;
import java.sql.*;
import logica.Usuario;
/**
 *
 * @author HÉCTOR M GALLI
 */
public class UsuarioDAO {

    Conexion conexion = new Conexion();
    public Usuario validar(String user, String pass) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE usuario = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date hoy = new Date(System.currentTimeMillis());
                Date bloqueado = rs.getDate("bloqueadohasta");

                // Verificamos si está bloqueado
                if (bloqueado != null && bloqueado.after(hoy)) {
                    System.out.println("Usuario bloqueado hasta: " + bloqueado);
                    return null;
                }

                String passBD = rs.getString("contraseña");
                if (pass.equals(passBD)) {
                    // Contraseña correcta
                    u = new Usuario();
                    u.setUsuario(rs.getString("usuario"));
                    u.setContraseña(rs.getString("contraseña"));
                    u.setRol(rs.getString("rol"));
                    u.setId_usuario(rs.getInt("id_usuario"));
                    u.setEmail(rs.getString("email"));
                    u.setId_institución(rs.getInt("id_institución"));
                    // Reiniciar intentos fallidos
                    PreparedStatement reset = con.prepareStatement(
                        "UPDATE usuario SET intentosfallidos = 0, bloqueadohasta = NULL WHERE usuario = ?");
                    reset.setString(1, user);
                    reset.executeUpdate();
                    
                } else {
                    // Contraseña incorrecta → aumentar intentos
                    int intentos = rs.getInt("intentosfallidos") + 1;
                    PreparedStatement update;

                    if (intentos >= 3) {
                        // Bloquear por 1 día
                        update = con.prepareStatement(
                            "UPDATE usuario SET intentosfallidos = ?, bloqueadohasta = CURDATE() + INTERVAL 1 DAY WHERE usuario = ?");
                    } else {
                        update = con.prepareStatement(
                            "UPDATE usuario SET intentosfallidos = ? WHERE usuario = ?");
                    }

                    update.setInt(1, intentos);
                    update.setString(2, user);
                    update.executeUpdate();

                    System.out.println("Intentos fallidos: " + intentos);
                }
            }
            con.close();
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }
public Usuario buscarPorEmail(String email) {
    Usuario u = null;
    String sql = "SELECT * FROM usuario WHERE email=?";
    try (Connection con = conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            u = new Usuario();
            u.setUsuario(rs.getString("usuario"));
            u.setContraseña(rs.getString("contraseña"));
            u.setRol(rs.getString("rol"));
            u.setEmail(email);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return u;
}

}

