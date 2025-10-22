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
public class AutenDaoJdbc implements AutenticacionDAO {
@Override
    public Optional<UserSession> login(String username, String password) {
        final String sql =
            "SELECT u.id, u.nombre_usuario, u.contrasena_hash, u.institucion_id, r.codigo " +
            "FROM usuario u " +
            "JOIN rol r ON r.id = u.rol_id " +
            "WHERE u.nombre_usuario=? AND u.activo=1";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                String stored = rs.getString("contrasena_hash");

                // PROTOTIPO: comparación directa (texto plano).
 
                if (stored == null || !stored.equals(password)) return Optional.empty();

                /*
                    Long instId = (Long) rs.getObject("institucion_id");
                */
                Object instObj = rs.getObject("institucion_id");
                Long instId = (instObj == null) ? null : ((Number) instObj).longValue(); // Nota: casteo a Number, NO a Long

                
                return Optional.of(new UserSession(
                        rs.getLong("id"),
                        rs.getString("nombre_usuario"),
                        rs.getString("codigo"),   // "DEP" o "INST"
                        instId                    // null si DEP
                ));
            }

        } catch (SQLException e) {
            throw new PersistenciaExcepciones("Error en login", e);
        }
    }
}
