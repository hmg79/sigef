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

public class TestBCrypt {

    public static void main(String[] args) {
        String plain = "1234";

     
        String hashFromDB = "$2a$12$QO2faU.9Z7TJGLrhyU4WMevEEgzPzm4ujtjkY/SpYXS5eGqEzCN/K";

        System.out.println("Contraseña ingresada: " + plain);
        System.out.println("Hash desde la base:   " + hashFromDB);

        boolean coincide = BCrypt.checkpw(plain, hashFromDB);

        System.out.println("\n¿Coinciden?: " + coincide);
    }
}
