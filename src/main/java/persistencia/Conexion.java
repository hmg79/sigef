/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author HÉCTOR M GALLI
 */
public class Conexion {
    public Connection getConnection(){
        Connection con=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sigef", "root","");
        } catch (Exception e) {
            e.printStackTrace(); //Muestra error si falla
        }
        return con;
    
    }
    //Verificar si se establece la conexión a la base de datos
    public static void main(String[] args) {
        Conexion con=new Conexion();
        if(con.getConnection()!=null) {
            System.out.println("Conexión Exitosa a la base de datos");
        } else {
            System.out.println("Error de conexión");
        }
    }
}
 