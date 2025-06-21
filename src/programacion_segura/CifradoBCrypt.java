/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacion_segura;

import org.mindrot.jbcrypt.BCrypt;

// *******************************************************************
// *                     - CIFRADO BCRYPT-                           *
// *   Clase que implementa una serie de métodos para el encriptado  *
// *   y comprobación de contraseñas utilizando el algoritmo BCrypt  *
// *   @author: Ana Rodríguez González                               *
// *   @version 1.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class CifradoBCrypt {
    //Cifra una contraseña utilizando BCrypt
    public static String cifrarPassword(String password) {
        System.out.println("Estamos en el método para cifrar contraseña de la clase CifradoBCrypt y esta es la contraseña recibida: ");
        System.out.println(password);
        //Hacemos el hash para cifrar la contraseña
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        System.out.println("Contraseña cifrada: " + hash);
        return hash;
    }
    
    //Verifica si una contraseña coincide con su hash cifrado
    public static boolean comprobarPassword(String password, String hash) {
        boolean coincide = BCrypt.checkpw(password, hash);
        return coincide;
    }

}
