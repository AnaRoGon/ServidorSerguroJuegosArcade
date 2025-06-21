/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacion_segura;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

// *******************************************************************
// *                           - LOG-                                *
// *   Clase para almacenar la información de error en un archivo    *
// *   log definido. Se formatea la salida para que quede según      *
// *   @author: Ana Rodríguez González                               *
// *   @version 1.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class Log {

    //Método para configurar el log
    public static void configurarLog(FileHandler fh, Logger logger) throws SecurityException {

        //Se sobreescribe este método para definir el formato de salida según lo indicado
        fh.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
                return String.format("%s %s%n", fechaHora, record.getMessage());
            }
        });

        //Asignamos el manejador al logger
        logger.addHandler(fh);
        //Desactivamos los mensaje en consola
        logger.setUseParentHandlers(true);
        //Establecemos el nivel de registro (ALL para registrar todo)
        logger.setLevel(Level.ALL);

        //Hook para cerrar el FileHandles al finalizar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (fh != null) {
                fh.close();
            }
        }));
    }

    //Este método nos va a ayudar a almacenar cada error en el archivo de error del log
    public static void guardarError(Logger logger, String juego, String linea, String valorRecibido) throws IOException {
        //Si recibimos una cadena vacía sustituimos el valorRecibido para que se entienda el error
        if (valorRecibido.equals("")) {
            valorRecibido = "Cadena vacía";
        }
        //Mensaje de error
        logger.log(Level.INFO, "Error juego " + juego + " en la línea " + linea + ": El valor introducido no es correcto. Valor recibido:" + valorRecibido);
    }
}
