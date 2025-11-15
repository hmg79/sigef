/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package TEST;

/**
 *
 * @author HÉCTOR M GALLI
 */

import org.mindrot.jbcrypt.BCrypt;

public class GenerarClaves {
    public static void main(String[] args) {
        String plain = "1234"; // tu contraseña original
        String hash = BCrypt.hashpw(plain, BCrypt.gensalt(12));
        System.out.println("Hash generado para '" + plain + "':");
        System.out.println(hash);
    }
}
