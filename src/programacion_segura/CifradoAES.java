/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacion_segura;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

// *******************************************************************
// *                       - CIFRADO AES-                            *
// *   Clase que nos permite cifrar y descifrar datos utilizando     *
// *   el algoritmo de cifrado AES.                                  *
// *   @author: Ana Rodríguez González Fecha: 14/05/2025             *
// *   @version 1.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class CifradoAES {

    private static final String ALGORITMO = "AES";
    private static final byte[] CLAVE_AES = "1234567890123456".getBytes();

    //Método para cifrar un String utilizando AES
    //Devuele un array de bytes cifrados. 
    public static byte[] cifrarDatos(String datos) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        SecretKey clave = new SecretKeySpec(CLAVE_AES, ALGORITMO);

        cipher.init(Cipher.ENCRYPT_MODE, clave);
        return cipher.doFinal(datos.getBytes());
    }
    //Método para descifrar un array de bytes utilizando AES
    //Devuelve un string con los datos descifrados
    public static String descifrarDatos(byte[] datosCifrados) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        SecretKey clave = new SecretKeySpec(CLAVE_AES, ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, clave);
        return new String(cipher.doFinal(datosCifrados));
    }

}
