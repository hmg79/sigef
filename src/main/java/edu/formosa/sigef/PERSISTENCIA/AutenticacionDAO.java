/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/*Interface de autenticación en la etapa de persistencia
*/
package edu.formosa.sigef.PERSISTENCIA;

/**
 *
 * @author Héctor Marciano Galli <hectormgalli@gmail.com>
 */
import edu.formosa.sigef.LOGICA.UserSession;
import java.util.Optional;
public interface AutenticacionDAO {
    Optional<UserSession> login (String username, String password);
}
