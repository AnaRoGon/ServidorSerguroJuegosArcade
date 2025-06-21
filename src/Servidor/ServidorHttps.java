/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import programacion_segura.CifradoAES;
import programacion_segura.CifradoBCrypt;
import programacion_segura.Log;
import programacion_segura.Semaforo;

// *******************************************************************
// *                      - SERVIDOR HTTP-                           *
// *   Clase que nos ayuda a sincronizar los hilos clientes          *
// *   y ejecutar un servidor http con distintos juegos              *
// *   @author: Ana Rodríguez González                               *
// *   @version 2.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class ServidorHttps {

    //Para añadir las cookies creamos un ConcurrentHashMap que almacenará un String que representará las cookies
    //Y un objeto SesionJuego en el que están almacenadas nuestras variables con el estado de cada juego
    private static final ConcurrentHashMap<String, SesionJuego> sesiones = new ConcurrentHashMap<>();
    //Para que el log no se repita creamos las variables de clase Logger y FileHandles como estáticas
    private static final Logger logger = Logger.getLogger("logErrores");
    private static FileHandler fh;
    //Creamos la variable para controlar la lectura y escritura sincronizada en el fichero de usuarios
    private static Semaforo semaforo = new Semaforo();

    public static void main(String[] args) throws Exception {
        final int PUERTO = 8066;

        //Se intenta establecer la conexión al puerto indicado que se mantiene abierta para recibir a los clientes
        try {

            //**************************      Configuramos el servidor para que sea seguro       *************************//
            //Cargamos el almacen de claves
            KeyStore keyStore = KeyStore.getInstance("JKS");
            try (FileInputStream keyFile = new FileInputStream("AlmacenSSL")) {
                keyStore.load(keyFile, "123456".toCharArray());
            }

            //Inicializamos el gestor de claves con keystore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("sunX509");
            keyManagerFactory.init(keyStore, "123456".toCharArray()); //Usa la misma contraseña

            //Inicializamos el contexto SSL con el gestor de claves
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            //Declaramos un objeto de tipo Factory para crear socket SSL servidor
            SSLServerSocketFactory factory = sslContext.getServerSocketFactory();

            //Creamos el socket servidor seguro
            SSLServerSocket socketServidorSsl = (SSLServerSocket) factory.createServerSocket(PUERTO);
            System.out.println("Servidor escuchando en el puerto: " + PUERTO);
            System.out.println("Conectar desde: " + "https://localhost:" + PUERTO);

            //Creamos el fichero de usuarios.txt si no existe
            File fichero = new File("usuarios.txt");
            if (fichero.exists() == false) {
                try {
                    fichero.createNewFile();

                } catch (IOException ex) {
                    Logger.getLogger(ServidorHttps.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Si es la primera vez que se crea se avisa
                System.out.println("El fichero \"usuarios.txt\" se ha creado correctamente");
            } else {
                //Si ya existía también se avisa de esto
                System.out.println("El fichero \"usuarios.txt\" ya estaba creado");
            }

            //*******************      Configuramos el log para almacenar los errores que necesitemos       *****************//
            //Se crea y configura el manejador de archivos
            fh = new FileHandler("logErrores.txt", true);

            //Utilizamos el método de configuración de la clase Log
            Log.configurarLog(fh, logger);

            while (true) {
                SSLSocket socketSsl = (SSLSocket) socketServidorSsl.accept();
                Thread hiloServidor = new HiloServidor(socketSsl, semaforo);
                hiloServidor.start();
            }
        } catch (IOException ioe) { //Se capturan las posibles excepciones
            System.out.println(ioe.getMessage());
        }
    }

    // ***********************************************************************
    // *                        - HiloServidor -                             * 
    // *       Clase privada para crear una conexión con cada hilo Cliente   * 
    // ***********************************************************************
    //Clase que implementa extiende de Thread para poder crear varios hilos concurrentes
    //También contiene los métodos que redirigirán a cada cliente al juego solicitado
    static class HiloServidor extends Thread {

        private final Socket socCliente;
        private final Semaforo semaforo;

        //Constructor de clase
        public HiloServidor(Socket socCliente, Semaforo semaforo) {
            this.socCliente = socCliente;
            this.semaforo = semaforo;
        }

        //En el método run gestionaremos el redireccionamiento a cada página según 
        //el tipo de petición que nos llegue (POST, GET)
        @Override
        public void run() {

            //Se crea un Buffer para leer las peticiones de entrada del cliente
            //Y un PrintWriter para manejar las salidas,
            //es decir, cada página html que queramos enviar como respuesta
            try {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socCliente.getOutputStream(), true); //No olvidar el true!!!

                // *********************************************************************************************** //               
                //********************************      Recogemos la petición       *******************************//
                // *********************************************************************************************** //
                //Leemos la línea inicial
                String peticion = entrada.readLine();
                //Si no se dan las condiciones que necesitamos
                //Así tenemos un mejor control de peticiones inesperadas. 
                if (peticion == null || (!peticion.startsWith("GET") && !peticion.startsWith("POST"))) {
                    return; // Ignora la petición si no es GET o POST.
                }
                System.out.println("Peticion: " + peticion);
                String ruta = peticion.split(" ")[1]; // Extrae la ruta solicitada.
                //Leer encabezados HTTP. Determina la sesionID y el tamaño del cuerpo
                String[] metadatos = new String[2];
                metadatos = obtenerMetadatos(entrada);

                //Obtenemos la sesion, es decir, la cookie que se ha generado para esta sesión
                String sessionId = metadatos[0];
                //Comprueba si existe este sessionID pero si no existe crea una nueva intancia de sesión de juego
                SesionJuego sesion = sesiones.computeIfAbsent(sessionId, k -> new SesionJuego());

                //Leemos todos los metadados
                int contentLength = Integer.parseInt(metadatos[1]); //Obtenemos el length

                //Simulamos la línea vacía
                System.out.println("Linea vacía");

                //Si tenemos petición POST leemos el cuerpo
                StringBuilder cuerpo = new StringBuilder();
                if (peticion.startsWith("POST") && contentLength > 0) {
                    char[] buffer = new char[contentLength]; //Va leyendo caracter a caracter
                    entrada.read(buffer, 0, contentLength);  //El cuerpo no tiene salto de línea, hay que hacer un read
                    cuerpo.append(buffer);
                }

                //Se muestra la información que hemos almacenado del cuerpo  
                System.out.println("Cuerpo: " + cuerpo);

                // *********************************************************************************************** //               
                //********************************      Mandamos la respuesta       *******************************//
                // *********************************************************************************************** //
                /////////////////////////////////        PETICIONES GET       ///////////////////////////////////////
                if (peticion.startsWith("GET")) {
                    peticion = peticion.substring(3, peticion.lastIndexOf("HTTP")).trim(); //Eliminamos los espacios
                    //Si la petición corresponde con "/" significará que es la página de logeo del servidor
                    if (peticion.length() == 0 || peticion.equals("/")) {
                        //Se manda a la página de inicio de sesión
                        redireccionarA(salida, PaginaLogin.html_login, sessionId);
                    } else if (peticion.equals("/inicio")) {
                        //Si el usuario se ha logeado se manda a la página de inicio de juegos (sala de juegos)
                        if (sesion.logeado) {
                            redireccionarA(salida, Paginas.html_inicio, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else if (peticion.equals("/adivina")) {
                        if (sesion.logeado) {
                            //Si el usuario se ha logeado se manda a la página del juego adivina número  
                            redireccionarA(salida, PaginasAdivina.html_adivina, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else if (peticion.equals("/dados")) {
                        if (sesion.logeado) {
                            //Si el usuario se ha logeado se manda a la página del juego dados
                            redireccionarA(salida, PaginasDados.html_dados, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else if (peticion.equals("/ppt")) {
                        if (sesion.logeado) {
                            //Si el usuario se ha logeado se manda a la página del juego ppt
                            redireccionarA(salida, PaginasPPT.html_ppt, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else if (peticion.equals("/login")) {
                        redireccionarA(salida, PaginaLogin.html_login, sessionId);
                    } else if (peticion.equals("/registro")) {
                        redireccionarA(salida, PaginaLogin.html_newUser, sessionId);
                    } else if (peticion.equals("/logout")) {
                        //Si el usuario se ha logeado se puede cerrar sesión
                        if (sesion.logeado) {
                            cerrarSesion(salida, sesion, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else { //Si no corresponde con ninguna de nuestras páginas se manda a una página que indica el error not_found
                        salida.println(Paginas.linea_404);
                        salida.println(Paginas.cabecera);
                        salida.println("Content-Length: " + Paginas.html_404.length());
                        salida.println("\n");
                        salida.println(Paginas.html_404);
                    }
                }

                /////////////////////////////////        PETICIONES POST      ///////////////////////////////////////                
                if (peticion != null && peticion.startsWith("POST")) {
                    peticion = peticion.substring(4, peticion.lastIndexOf("HTTP")).trim(); //Eliminamos los espacios
                    if (peticion.equals("/lanzarDado")) {
                        //Si se está logeado
                        if (sesion.logeado) {
                            //Se inicia el juego de los dados
                            juegoDados(salida, sesion, sessionId);
                        } else {
                            //Sino se redirecciona al login
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else if (peticion.equals("/jugarPPT")) {
                        if (sesion.logeado) {
                            //Se inicia el juego de ppt
                            juegoPPT(salida, cuerpo, sesion, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else if (peticion.equals("/jugarAdivina")) {
                        if (sesion.logeado) {
                            //Se inicia el juego Adivina
                            juegoAdivina(salida, cuerpo, sesion, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    } else if (peticion.equals("/login")) {
                        inicioSesion(salida, cuerpo, sessionId, sesion);
                    } else if (peticion.equals("/registro")) {
                        registroNuevoUsuario(salida, cuerpo, sessionId);
                    } else if (peticion.equals("/logout")) {
                        if (sesion.logeado) {
                            cerrarSesion(salida, sesion, sessionId);
                        } else {
                            redireccionarA(salida, PaginaLogin.html_login, sessionId);
                        }
                    }
                }

                //D.E.P HiloServidor
            } catch (IOException ioe) { //Se capturan las posibles excepciones
                System.out.println(ioe.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(ServidorHttps.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // ************************************************************************************************** //
        // *                                   - MÉTODOS DE CADA JUEGO -                                    * // 
        // *               Métodos que permiten al servidor y a los clientes comunicarse                    * //
        // *               según el juego al que hayan sido redirigidos.                                    * //
        // *               Estos métodos reciben en su constructor el PrintWriter                           * //
        // *               que irá mandando la respuesta a cada página según el caso de                     * //
        // *               cada juego.                                                                      * //
        // ************************************************************************************************** // 
        //Método que implementa la lógica del juego Dados del Servidor
        private void juegoDados(PrintWriter salida, SesionJuego sesion, String sessionId) {
            //Variable para formatear el diseño de la página html según los resultados de cada ronda
            String html_formateada;
            //Variable para cambiar el texto del botón del juego
            String txtBoton;
            //Valor aleatorio entre 1 y 6 del dado del cliente
            int numeroCliente = (int) (Math.random() * 6) + 1;
            //Valor aleatorio entre 1 y 6 del dado del Servidor
            int numeroServidor = (int) (Math.random() * 6) + 1;
            //Se juegan 5 rondas
            if (sesion.rondaDados < 5) {
                if (numeroCliente > numeroServidor) {
                    //Se suma la ronda
                    sesion.rondaDados++;
                    //Se suman la puntuación al cliente
                    sesion.puntosClienteDados++;
                    //Para actualizar el texto del botón según la ronda
                    txtBoton = (sesion.rondaDados == 5) ? "Resultados" : "Lanzar dado"; //Si se está en la última ronda pasará a ser "Resultados"
                    //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                    html_formateada = String.format(PaginasDados.html_rondas, txtBoton, numeroCliente, numeroServidor, "GANADOR: cliente", sesion.rondaDados);
                    redireccionarA(salida, html_formateada, sessionId);
                } else if (numeroServidor > numeroCliente) {
                    //Se suma la ronda
                    sesion.rondaDados++;
                    //Se suma la puntuación al servidor
                    sesion.puntosServidorDados++;
                    txtBoton = (sesion.rondaDados == 5) ? "Resultados" : "Lanzar dado";
                    html_formateada = String.format(PaginasDados.html_rondas, txtBoton, numeroCliente, numeroServidor, "GANADOR: servidor", sesion.rondaDados);
                    redireccionarA(salida, html_formateada, sessionId);
                } else {
                    //Si hay empate no se suma la ronda
                    txtBoton = (sesion.rondaDados == 5) ? "Resultados" : "Lanzar dado";
                    html_formateada = String.format(PaginasDados.html_rondas, txtBoton, numeroCliente, numeroServidor, "Empate. Se repite la ronda", sesion.rondaDados);
                    redireccionarA(salida, html_formateada, sessionId);
                }
            } else {
                //Se manda la página con los resultados finales donde también se da la opción de jugar otra vez 
                if (sesion.puntosServidorDados > sesion.puntosClienteDados) {
                    html_formateada = String.format(PaginasDados.html_resultados, "Ha ganado el SERVIDOR", sesion.puntosClienteDados, sesion.puntosServidorDados);
                    redireccionarA(salida, html_formateada, sessionId);
                } else if (sesion.puntosClienteDados > sesion.puntosServidorDados) {
                    html_formateada = String.format(PaginasDados.html_resultados, "Ha ganado el CLIENTE", sesion.puntosClienteDados, sesion.puntosServidorDados);
                    redireccionarA(salida, html_formateada, sessionId);
                }
                //Se resetea el juego
                sesion.rondaDados = 0;
                sesion.puntosServidorDados = 0;
                sesion.puntosClienteDados = 0;
            }
        }

        //Método que implementa la lógica del juego PPT del Servidor
        private void juegoPPT(PrintWriter salida, StringBuilder cuerpo, SesionJuego sesion, String sessionId) throws IOException {
            //Variable para formatear el diseño de la página html según los resultados de cada ronda
            String html_formateada;
            //Array de String para almacenar el texto de los botones según la ronda
            String[] botones;
            //Generamos la mano del servidor de manera aleatoria
            int manoServidor = (int) (Math.random() * 3) + 1;
            int manoCliente = 0;
            //Obtenemos la mano del cliente según la información almacenada en el cuerpo
            if (cuerpo.toString().endsWith("1")) {
                manoCliente = 1;
            } else if (cuerpo.toString().endsWith("2")) {
                manoCliente = 2;
            } else if (cuerpo.toString().endsWith("3")) {
                manoCliente = 3;
            } else {
                /////////////////////////////////        SE MANDA EL ERROR      /////////////////////////////////////// 
                //Obtenemos la línea del código en el que saltó el error
                int linea = Thread.currentThread().getStackTrace()[1].getLineNumber();
                //Valor no válido, mandar a la clase Log para escribir la información del error en el archivo
                Log.guardarError(logger, "PPT", Integer.toString(linea), cuerpo.toString());
            }

            //Se juegan 5 rondas
            if (sesion.rondaPPT < 5) {
                //En base a la mano del cliente se establece la puntuación y los resultados de cada ronda
                switch (manoCliente) {
                    case 1: //Cliente saca piedra
                        if (manoServidor == 1) { //Si sacan piedra ambos
                            //Empate
                            //Se actualiza el texto de los botones según la ronda
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "piedra", "piedra", "EMPATE: Se repite la ronda", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);

                        } else if (manoServidor == 2) { //Si el servidor ha sacado papel
                            sesion.rondaPPT++; //Se suma la ronda
                            sesion.puntosServidorPPT++; //servidor suma puntos
                            //Se actualiza el texto de los botones según la ronda
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "piedra", "papel", "GANADOR: servidor", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);

                        } else if (manoServidor == 3) { //Si el servidor ha sacado tijera
                            sesion.rondaPPT++; //Se suma la ronda
                            sesion.puntosClientePPT++; //El cliente suma puntos
                            //Se actualiza el texto de los botones según la ronda
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "piedra", "tijera", "GANADOR: cliente", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);
                        }

                        break;

                    case 2: //Cliente saca papel
                        if (manoServidor == 2) { //Si sacan papel ambos                            
                            //Empate                            
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "papel", "papel", "EMPATE: Se repite la ronda", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);
                        } else if (manoServidor == 1) { //Si el servidor saca piedra
                            sesion.rondaPPT++;
                            sesion.puntosClientePPT++;
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "papel", "piedra", "GANADOR: cliente", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);
                        } else if (manoServidor == 3) { //Si el servidor saca tijera                            
                            sesion.rondaPPT++;
                            sesion.puntosServidorPPT++;
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "papel", "tijera", "GANADOR: servidor", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);
                        }

                        break;

                    case 3: //Cliente saca tijera
                        if (manoServidor == 3) { //Si ambos sacan tijera
                            //empate                         
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "tijera", "tijera", "EMPATE: Se repite la ronda", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);
                        } else if (manoServidor == 2) { //Si el servidor sacca papel
                            sesion.puntosClientePPT++;
                            sesion.rondaPPT++;
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "tijera", "papel", "GANADOR: cliente", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);

                        } else if (manoServidor == 1) { //Si el servidor saca piedra                           
                            sesion.puntosServidorPPT++;
                            sesion.rondaPPT++;
                            botones = obtenerTxtBotonesPPT(sesion, sessionId);
                            //Se formatea el html y se redirecciona a la página que muestra los resultados de la ronda
                            html_formateada = String.format(PaginasPPT.html_rondas, botones[0], botones[1], botones[2], "tijera", "piedra", "GANADOR: servidor", sesion.rondaPPT);
                            redireccionarA(salida, html_formateada, sessionId);
                        }

                        break;
                }

            } else {
                //Se manda la página formateada con los resultados finales donde se da la opción de jugar otra vez
                if (sesion.puntosServidorPPT > sesion.puntosClientePPT) {
                    html_formateada = String.format(PaginasPPT.html_resultados, "Ha ganado el SERVIDOR", sesion.puntosClientePPT, sesion.puntosServidorPPT);
                    redireccionarA(salida, html_formateada, sessionId);
                } else if (sesion.puntosClientePPT > sesion.puntosServidorPPT) {
                    html_formateada = String.format(PaginasPPT.html_resultados, "Ha ganado el CLIENTE", sesion.puntosClientePPT, sesion.puntosServidorPPT);
                    redireccionarA(salida, html_formateada, sessionId);
                }
                //Se resetea el juego
                sesion.rondaPPT = 0;
                sesion.puntosClientePPT = 0;
                sesion.puntosServidorPPT = 0;
            }

        }

        //Método para implementar la lógica del juego Adivina del Servidor
        private void juegoAdivina(PrintWriter salida, StringBuilder cuerpo, SesionJuego sesion, String sessionId) throws IOException {
            //Variable para formatear el diseño de la página html según los resultados de cada ronda
            String html_formateada;
            //Obtenemos la posición del signo de igual en el cuerpo
            int posicionSignoIgual = cuerpo.lastIndexOf("=");
            //Obtenemos todo el texto que siga después del igual
            String obtenerNumero = cuerpo.substring(posicionSignoIgual + 1);

            try {
                //Se intenta parsear el número mandado por el cliente y si no es un número se lanza el catch y controlamos el error
                int numCliente = Integer.parseInt(obtenerNumero);
                //Se tienen 10 intentos en este juego
                if (sesion.intentosAdivina < 10) {
                    //Se toma el número aleatorio generado en la variable estática del servidor y se compara con el del cliente
                    if (numCliente == sesion.numAleatorioAdivina) {
                        //Se formatea el html y se redirecciona a la página que muestra que muestra los resultados
                        html_formateada = String.format(PaginasAdivina.html_resultados, "acertado", sesion.numAleatorioAdivina);
                        redireccionarA(salida, html_formateada, sessionId);
                        //Se resetea el juego
                        sesion.numAleatorioAdivina = (int) (Math.random() * 100) + 1;
                        sesion.intentosAdivina = 1;
                    } else {
                        //Siempre que el número esté dentro de los valores indicados
                        if (!(numCliente > 100 || numCliente < 1)) {
                            if (numCliente > sesion.numAleatorioAdivina) { //Si el número es más bajo
                                //Se formatea el html y se redirecciona a la página que la pista del número
                                html_formateada = String.format(PaginasAdivina.html_rondas, "es más bajo", (10 - sesion.intentosAdivina));
                                redireccionarA(salida, html_formateada, sessionId);
                                //Se suma el intento después para que al llegar a los 10 intentos
                                //nos mande a la página de resultados al volver a entrar en las condiciones
                                sesion.intentosAdivina++;

                            } else if (numCliente < sesion.numAleatorioAdivina) { //El número es más alto                            
                                //Se formatea el html y se redirecciona a la página que la pista del número
                                html_formateada = String.format(PaginasAdivina.html_rondas, "es más alto", (10 - sesion.intentosAdivina));
                                redireccionarA(salida, html_formateada, sessionId);
                                //Se suma el intento
                                sesion.intentosAdivina++;
                            }
                        } else { //Si el número no está entre los valores indicados
                            //Se formatea el html y se redirecciona a la página que muestra el aviso de que el número debe de ser entre 1 y 100
                            html_formateada = String.format(PaginasAdivina.html_rondas, "debe estar entre 1 y 100", (10 - sesion.intentosAdivina + 1));
                            redireccionarA(salida, html_formateada, sessionId);
                            //No se resta el intento
                        }
                    }
                } else {
                    //Si el usuario ha consumido todos los intentos        
                    //Se formatea el html y se redirecciona a la página que muestra que muestra los resultados
                    html_formateada = String.format(PaginasAdivina.html_resultados, "fallado", sesion.numAleatorioAdivina);
                    redireccionarA(salida, html_formateada, sessionId);
                    //Se resetea el juego
                    sesion.numAleatorioAdivina = (int) (Math.random() * 100) + 1;
                    sesion.intentosAdivina = 1;
                }
                //Por si queremos controlar los resultados del juego desde la salida de NetBeans
                System.out.println("El numero mandado por el cliente es " + numCliente);
                System.out.println("EL numero del servidor es: " + sesion.numAleatorioAdivina);
                //Capturamos el error en caso de que no se introduzca nada en el formulario y se pulse enviar o no sea un valor numérico
            } catch (NumberFormatException nfe) {
                //Se avisa al usuario formateando la página e indicándo el error y que no se pierde el intento
                html_formateada = String.format(PaginasAdivina.html_rondas, "debe ser válido", (10 - sesion.intentosAdivina + 1));
                redireccionarA(salida, html_formateada, sessionId);
                System.out.println("El usuario no ha introducido ningún número: " + nfe.getMessage());
                /////////////////////////////////        SE MANDA EL ERROR      /////////////////////////////////////// 
                //Obtenemos la línea del código en el que saltó el error
                int linea = Thread.currentThread().getStackTrace()[1].getLineNumber();
                //Valor no válido, mandar a la clase Log para escribir el error en el archivo
                Log.guardarError(logger, "Adivina", Integer.toString(linea), obtenerNumero);
            }
        }

        // **************************************************************************************************
        // *                                   - MÉTODOS AUXILIARES -                                       *                                                                     
        // **************************************************************************************************  
        //Método para simplificar el direccionamiento a cada página
        public void redireccionarA(PrintWriter salida, String pagina, String sessionId) {
            //Mandamos la línea inicial           
            salida.println(Paginas.linea_OK);
            //MAndamos los metadatos 
            salida.println(Paginas.cabecera);
            salida.println("Content-Length: " + pagina.length());
            salida.println("Set-Cookie: sessionId=" + sessionId + "; Path=/;"); //Desde aqui se responde si hay o no coockie
            //Mandamos la línea en blanco
            salida.println("\n");
            //Mandamos el cuerpo de página
            salida.println(pagina);
        }

        //Método para complementar el juego PPT y controlar el contenido de los botones según la ronda en el juego PPT
        public String[] obtenerTxtBotonesPPT(SesionJuego sesion, String sessionId) {
            //Array de String que contendrá los nombres de los botones
            String[] textosBotones = new String[3];
            // Si se ha llegado a la ronda 5 se cambia el contenido de los botones para que el usuario entienda
            //que va a acceder a los resultados en caso contrario se mantiene los nombres "Piedra, papel, tijera"
            String txtBotonPiedra = (sesion.rondaPPT == 5) ? "" : "Piedra";
            String txtBotonPapel = (sesion.rondaPPT == 5) ? "Resultados" : "Papel";
            String txtBotonTijera = (sesion.rondaPPT == 5) ? "" : "Tijera";
            //Se almacenan en el vector 
            textosBotones[0] = txtBotonPiedra;
            textosBotones[1] = txtBotonPapel;
            textosBotones[2] = txtBotonTijera;
            //Se devuelve el array con los nombres actualizados
            return textosBotones;
        }

        //Método para manejar el uso de cookies en el servidor por sesión
        private String[] obtenerMetadatos(BufferedReader entrada) throws IOException {
            String linea;
            String[] metadatos = new String[2];
            String sessionId = null;
            String contentLength = "0";

            while (!(linea = entrada.readLine()).isBlank()) {
                System.out.println("Metadato: " + linea);
                if (linea.startsWith("Cookie: ")) {
                    String[] cookies = linea.substring(8).split("; ");
                    for (String cookie : cookies) {
                        if (cookie.startsWith("sessionId=")) {
                            sessionId = cookie.substring(10);
                        }
                    }
                } else if (linea.startsWith("Content-Length: ")) {
                    contentLength = linea.substring(16);
                }
            }
            //Si no hay cookie se genera una nueva coockie
            if (sessionId == null) {
                sessionId = UUID.randomUUID().toString();
            }
            System.out.println("COOKIE: " + sessionId);

            metadatos[0] = sessionId;
            metadatos[1] = contentLength;
            return metadatos;
        }

        // **************************************************************************************************
        // *          - MÉTODOS DE CONTROL DE INICIO DE SESIÓN, REGISTRO Y CIERRE SESIÓN -                  *                                                                     
        // ************************************************************************************************** 
        /* Método que recibe los datos de usuario y contraseña, desencripta el archivo de usuarios para comprobar 
         * que el usuario existe y comprueba que la contraseña indicada sea correcta (la contraseña se compara con la cifrada en BCrypt). 
         * Si el usuario no está almacenado se redirecciona para que el usuario se registre */
        private void inicioSesion(PrintWriter salida, StringBuilder cuerpo, String sessionId, SesionJuego sesion) throws Exception {
            //Obtenemos la posición del signo de igual en el cuerpo
            int posicionPrimerIgual = cuerpo.indexOf("=");
            int posicionAnd = cuerpo.indexOf("&");
            int posicionSegundoIgual = cuerpo.lastIndexOf("=");
            boolean usuarioEncontrado = false;
            boolean loginCorrecto = false;
            File txt = new File("usuarios.txt");
            String html_formateada;

            //Obtenemos los datos haciendo uso de las posiciones obtenidas
            String correoObtenido = cuerpo.substring((posicionPrimerIgual + 1), (posicionAnd));
            String passwordObtenida = cuerpo.substring(posicionSegundoIgual + 1);
            String correoFormateado = correoObtenido.replace("%40", "@"); //Reemplazamos el valor codificado por un arroba
            //Verificamos que los datos correspondan con los patrones 
            System.out.println("obtenerCorreo: " + correoFormateado);
            System.out.println("obtenerPassword: " + passwordObtenida);
            semaforo.accesoLeer();
            try {
                //Desciframos los datos del fichero y los almacenamos en una variable
                byte[] datosCifradosFichero = Files.readAllBytes(Paths.get("usuarios.txt"));
                String datosDescifrados = CifradoAES.descifrarDatos(datosCifradosFichero);
                //Obtenemos cada línea
                String[] lineas = datosDescifrados.split("\n");
                //Recuperamos los datos
                for (String linea : lineas) {
                    String lineaSinEspacios = linea.trim();
                    String[] partes = lineaSinEspacios.split(":");
                    try {
                        //Obtenemos el correo por un lado y la contraseña por otro
                        String correo = partes[0];
                        String hashPassword = partes[1];
                        System.out.println("Correo obtenido: " + correo);
                        System.out.println("Password obtenida: " + hashPassword);
                        //Si el correo ya está almacenado en nuestro fichero
                        if (correo.equals(correoFormateado)) {
                            System.out.println("Correo encontrado!");
                            usuarioEncontrado = true;
                            //Hay que desencriptar la contraseña para compararla

                            if (CifradoBCrypt.comprobarPassword(passwordObtenida, hashPassword)) { //Desciframos el hash para poder compararlo con el método de la clase Bcrypt
                                System.out.println("Contraseña correcta!");
                                loginCorrecto = true;
                                break; //salimos del bucle! 
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        //Si salta esta excepcion es que no se ha podido separar el correo y la password en el archivo porque no hay nada almacenado en el fichero usuarios.txt
                        System.out.println("Aún no hay ningún usuario almacenado");
                        //Se formatea el html y se redirecciona a la página que muestra que muestra los resultados
                        html_formateada = String.format(PaginaLogin.html_password_login_incorrecto, "Aún no tenemos ningún usuario registrado", "¡Sé el primero en registrarte! ");
                        redireccionarA(salida, html_formateada, sessionId);
                        break; //Salimos
                    }
                }
            } catch (InterruptedException ex) {
                System.out.println(ex);
            } finally {
                semaforo.lecturaFinalizada();
            }

            //Salimos del bucle y decidimos qué hacer según los resultados obtenidos
            if (loginCorrecto) {
                sesion.logeado = true; //Cambiamos el valor de la variable de la sesión para indicar que se ha logeado
                redireccionarA(salida, Paginas.html_inicio, sessionId);
            } else if (usuarioEncontrado) { //Se ha encontrado un usuario con el correo indicado pero no ha puesto la contraseña bien, debe intentarlo nuevamente
                System.out.println("La contraseña no es correcta. Inténtalo de nuevo");
                //Se formatea el html y se redirecciona a la página que muestra que muestra los mensajes informando al usuario
                html_formateada = String.format(PaginaLogin.html_password_login_incorrecto, "La contraseña no es correcta", "Inténtalo de nuevo");
                redireccionarA(salida, html_formateada, sessionId);
            } else { //Si no se manda a registrarse ya que no tenemos datos de este usuario             
                //Si no existe un usuario almacenado con esos datos se le indica al usuario que debe crear una cuenta para jugar
                html_formateada = String.format(PaginaLogin.html_password_login_incorrecto, "No hay ningún correo almacenado con estos datos.", "Puedes registrarte desde aquí: ");
                redireccionarA(salida, html_formateada, sessionId);
            }
        }

        /* Método que permite a un usuario registrarse en el servidor con un correo y contraseña. Verifica que los datos recibidos sigan un patrón
         * establecido y que encajen (contraseña de al menos 6 caracteres alfanuméricos y formato de correo estándar "user@correo.com" 
         * Si los datos introducidos cumplen los requerimientos y no hay otro usuario usando el mismo correo se cifra la contraseña con Bcrypt. 
         * Acto seguido se cifra nuevamente la contraseña junto al correo con el patrón de cifrado AES y se redirecciona a la sala de inicio de juegos */
        private void registroNuevoUsuario(PrintWriter salida, StringBuilder cuerpo, String sessionId) throws Exception {
            String correo;
            String password;
            String correoAlmacenado;
            String html_formateada;
            boolean usuarioEncontrado = false;
            int posicionPrimerIgual = cuerpo.indexOf("=");
            int posicionAnd = cuerpo.indexOf("&");
            int posicionSegundoIgual = cuerpo.lastIndexOf("=");

            //Obtenemos los datos en base a las posiciones obtenidas
            String correoRecibido = cuerpo.substring((posicionPrimerIgual + 1), (posicionAnd));
            correo = correoRecibido.replace("%40", "@"); //Reemplazamos el valor por un arroba            
            password = cuerpo.substring(posicionSegundoIgual + 1);

            //Vamos a comprobar que se introducen datos que corresponden con las condiciones de correo y contraseña
            Pattern patCorreo = Pattern.compile("^[a-zA-z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$");
            Matcher matcherCorreo = patCorreo.matcher(correo);
            //Sólo letras mayúsculas, minusculas o numeros de una longitud de 6 como mínimo
            Pattern patPassword = Pattern.compile("^[a-zA-Z0-9]{6,}$");
            Matcher matcherPassword = patPassword.matcher(password);

            //Si tanto el correo como la contraseña cumplen con las restricciones
            if (matcherCorreo.matches() && matcherPassword.matches()) {
                //Leemos el archivo encriptado para comprobar que no existe ya un usuario con el correo indicado
                semaforo.accesoLeer(); //Se intenta leer
                try {
                    byte[] datosCifradosFichero = Files.readAllBytes(Paths.get("usuarios.txt"));
                    //Desciframos los datos y los almacenamos en una variable
                    String datosDescifrados = CifradoAES.descifrarDatos(datosCifradosFichero);

                    System.out.println("Datos descifrados: " + new String(datosDescifrados)); //mostramos los datos por salida (prueba, esta línea se puede comentar)

                    //Obtenemos cada línea de los datos descifrados
                    String[] lineas = datosDescifrados.split("\n");
                    //Recuperamos los correos almacenados y vemos si ya está registrado el usuario
                    int contador = 0;
                    for (String linea : lineas) {
                        contador++;
                        String lineaSinEspacios = linea.trim();
                        String[] partes = lineaSinEspacios.split(":");
                        correoAlmacenado = partes[0];
                        System.out.println("Correo almacenado" + contador + " : " + correoAlmacenado);
                        //Si el correo ya está almacenado
                        if (correo.equals(correoAlmacenado)) {
                            usuarioEncontrado = true;
                            System.out.println("Este usuario ya está almacenado en la base de datos");
                            //Si ya existe un usuario con ese mail se le indica al usuario que inicie sesión
                            html_formateada = String.format(PaginaLogin.html_existingUser, "Ya existe un usuario con estos datos", "Inicia sesión con tu usuario desde aquí: ");
                            redireccionarA(salida, html_formateada, sessionId);
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                } finally {
                    semaforo.lecturaFinalizada(); //termina la lectura del fichero
                }

                //Si no se ha encontrado usuario
                if (!usuarioEncontrado) {
                    //Se encripta la contraseña
                    String passwordCifrada = CifradoBCrypt.cifrarPassword(password);
                    //Se cifran los datos
                    String datos = correo + ":" + passwordCifrada + System.lineSeparator();
                    byte[] datosCifrados = CifradoAES.cifrarDatos(datos);

                    //Se escriben en el fichero de texto al nuevo usuario
                    //Actuando el fichero así como una BD encriptada del servidor
                    semaforo.accesoEscribir(); //Se intenta escribir
                    try (FileOutputStream fos = new FileOutputStream("usuarios.txt", true)) {
                        fos.write(datosCifrados); //Estamos escribiendo una línea de datos cifrados
                    }
                    semaforo.escrituraFinalizada(); //Se avisa de que se ha terminado de escribir en el fichero

                    //Se redirecciona a la página de juegos
                    redireccionarA(salida, Paginas.html_inicio, sessionId);
                    System.out.println("El correo y la contraseña son válidos. Usuario registrado con éxito");
                }

            } else {
                //Si la contraseña o el correo no son válidos se avisa en el html
                html_formateada = String.format(PaginaLogin.html_newUser_incorrect, "El correo o la contraseña no son válidos.", "Recuerda que la contraseña debe ser de al menos 6 caracteres alfanuméricos (letras y números).");
                redireccionarA(salida, html_formateada, sessionId);
            }
        }

        //Método para cerrar la sesión actual
        private void cerrarSesion(PrintWriter salida, SesionJuego sesion, String sessionId) {
            //Se cambia el valor del logeo a False para salir
            sesion.logeado = false;
            //Se resetean todos los juegos
            sesion.rondaDados = 0;
            sesion.rondaPPT = 0;
            sesion.puntosServidorDados = 0;
            sesion.puntosClienteDados = 0;
            sesion.puntosServidorPPT = 0;
            sesion.puntosClientePPT = 0;
            sesion.intentosAdivina = 1;
            sesion.numAleatorioAdivina = (int) (Math.random() * 100) + 1;
            //redireccionamos al login
            redireccionarA(salida, PaginaLogin.html_login, sessionId);
        }

    }
}
