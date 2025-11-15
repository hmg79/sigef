/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Conexión con la base de datos para realizar la autenticación
*/
package edu.formosa.sigef.PERSISTENCIA;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.UserSession;
import java.sql.*;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
public class AutenDaoJdbc implements AutenticacionDAO {
@Override
    public Optional<UserSession> login(String username, String password) {
        final String sql = """
             SELECT u.id, u.nombre_usuario, u.contrasena_hash, u.institucion_id,
                       u.intentos_fallidos, u.bloqueado_hasta,
                       r.codigo
                FROM usuario u
                JOIN rol r ON r.id = u.rol_id
                WHERE u.nombre_usuario = ? AND u.activo = 1
            """;

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);
            // Prueba de verificación loggin
            //System.out.println("Intentando login con usuario: '" + username + "'");
            
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    // Prueba para ver si encuentra datos en la bd
                    //System.out.println("No se encontró el usuario en la BD.");
                    registrarAcceso(cn, null, username, false, "Usuario inexistente");
                    return Optional.empty();
                }

                long id = rs.getLong("id");
                String storedHash = rs.getString("contrasena_hash");
                int intentos = rs.getInt("intentos_fallidos");
                Timestamp bloqueadoHasta = rs.getTimestamp("bloqueado_hasta");

                // Si está bloqueado
                if (bloqueadoHasta != null && bloqueadoHasta.after(new Timestamp(System.currentTimeMillis()))) {
                    registrarAcceso(cn, id, username, false, "Cuenta bloqueada");
                    return Optional.empty();
                }

                // Verificar contraseña con BCrypt
                //Verificar funcionamiento correcto de la comparación con la base de datos, solo para verificación
                /*System.out.println("Hash desde BD: [" + storedHash + "]");
                System.out.println("Coincide BCrypt: " + BCrypt.checkpw(password, storedHash));
                boolean coincide = BCrypt.checkpw(password, storedHash);
                System.out.println("Coincide BCrypt: " + coincide);
                if (storedHash == null) {
                        System.out.println("El hash en BD es NULL");
                        registrarAcceso(cn, id, username, false, "Hash vacío");
                    return Optional.empty();
                }

                if (!coincide) {
                    System.out.println("Contraseña incorrecta (checkpw=false)");
                    intentos++; 
                    return Optional.empty();
                }

                System.out.println("Contraseña válida, preparando UserSession...");
               */
                
                if (storedHash == null || !BCrypt.checkpw(password, storedHash)) {
                    intentos++;
                    if (intentos >= 3) {
                        String upd = "UPDATE usuario SET intentos_fallidos=0, bloqueado_hasta=DATE_ADD(NOW(), INTERVAL 30 MINUTE) WHERE id=?";
                        try (PreparedStatement up = cn.prepareStatement(upd)) {
                            up.setLong(1, id);
                            up.executeUpdate();
                        }
                        registrarAcceso(cn, id, username, false, "Bloqueado por 3 intentos fallidos");
                    } else {
                        String upd = "UPDATE usuario SET intentos_fallidos=? WHERE id=?";
                        try (PreparedStatement up = cn.prepareStatement(upd)) {
                            up.setInt(1, intentos);
                            up.setLong(2, id);
                            up.executeUpdate();
                        }
                        registrarAcceso(cn, id, username, false, "Contraseña incorrecta");
                    }
                    return Optional.empty();
                }

                // Si la contraseña es correcta
                String updOk = "UPDATE usuario SET intentos_fallidos=0, bloqueado_hasta=NULL WHERE id=?";
                try (PreparedStatement up = cn.prepareStatement(updOk)) {
                    up.setLong(1, id);
                    up.executeUpdate();
                }

                registrarAcceso(cn, id, username, true, "Login exitoso");

                Object instObj = rs.getObject("institucion_id");
                Long instId = (instObj == null) ? null : ((Number) instObj).longValue();
                /*System.out.println("---- Verificación de login ----");
                System.out.println("Usuario leído: " + rs.getString("nombre_usuario"));
                System.out.println("Hash leído: " + rs.getString("contrasena_hash"));
                System.out.println("Institución ID: " + rs.getObject("institucion_id"));
                System.out.println("Rol código: " + rs.getString("codigo"));
                System.out.println("Comparación BCrypt: " + BCrypt.checkpw(password, rs.getString("contrasena_hash")));
                System.out.println("---------------------");
                */
                return Optional.of(new UserSession(
                        id,
                        rs.getString("nombre_usuario"),
                        rs.getString("codigo"), // “DEP” o “INST”
                        instId
                ));
            }

        } catch (SQLException e) {
            throw new PersistenciaExcepciones("Error en login", e);
        }
    }

    private void registrarAcceso(Connection cn, Long userId, String username, boolean exito, String msg) throws SQLException {
        String ins = "INSERT INTO sesion_acceso(usuario_id, ip, exito, mensaje, creado_en) VALUES (?, ?, ?, ?, NOW())";
            try (PreparedStatement ps = cn.prepareStatement(ins)) {
                ps.setObject(1, userId);
                ps.setString(2, "127.0.0.1"); // IP loopback local, debido a que serian necesarios servicios externos se utiliza esta de referencia
                ps.setBoolean(3, exito);
                ps.setString(4, msg);
            ps.executeUpdate();
    }
}

}
