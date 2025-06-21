/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

// *******************************************************************
// *                         - PAGINAS-                              *
// *   Clase para almacenar en formato String los html generales     *
// *   necesarios para el funcionamiento del servidor.               *
// *   @author: Ana Rodríguez González Fecha: 14/05/2025             *
// *   @version 2.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class Paginas {

    //Linea inicial
    public static final String linea_OK = "HTTP/1.1 200 OK";
    //Si la peticion no es la esperada
    public static final String linea_404 = "HTTP/1.1 404 Not Found";
    //Tipo
    public static final String cabecera = "Content-Type: text/html; charset=UTF-8";
    //Página de inicio
    public static final String login = "";

    //Diseño de la página inicio con enlaces a la página de cada juego (Se ha incluido un botón de cerrar sesión)
    public static final String html_inicio = """
                                            <!DOCTYPE html>
                                             <html>
                                               <head>
                                                 <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                 <title>Servidor de Juegos</title>
                                                 <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                 <style>
                                                   @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                             
                                                   body {
                                                     font-family: 'Press Start 2P', cursive;
                                                     text-align: center;
                                                     background-color: black;
                                                     color: #00FF00;
                                                     margin: 0;
                                                     padding: 0;
                                                   }
                                             
                                                   .container {                                                      
                                                     width: 50%;
                                                     margin: 50px auto;
                                                     padding: 20px;
                                                     background: #111;
                                                     border: 4px solid #00FF00;
                                                     border-radius: 10px;
                                                     box-shadow: 0 0 15px #00FF00;
                                                   }
                                             
                                                   h1 {
                                                     font-size: 40px;
                                                     color: #00FF00;
                                                     text-shadow: 3px 3px 0px #000, 6px 6px 5px rgba(0, 255, 0, 0.5);
                                                   }
                                             
                                                   .routes {
                                                     text-align: left;
                                                     margin-top: 20px;
                                                     padding: 15px;
                                                     border: 2px solid #FF1493;
                                                     background: #222;
                                                     border-radius: 5px;
                                                     box-shadow: 0 0 10px #FF4500;
                                                   }
                                             
                                                   .routes ul {
                                                     list-style-type: decimal;
                                                     text-align: left;
                                                     padding-left: 90px;
                                                     font-size: 15px;
                                                   }
                                             
                                                   .routes li {
                                                     font-size: 15px;
                                                     padding: 15px;
                                                   }
                                             
                                                   .routes p {
                                                     font-size: 15px;
                                                     color: #FFD700;
                                                     line-height: 1.5;
                                                   }
                                             
                                                   .links {
                                                     display: flex;
                                                     flex-direction: column;
                                                     align-items: center;
                                                     gap: 15px;
                                                     margin-top: 20px;
                                                   }
                                             
                                                   .links a {
                                                     display: inline-block; /* Para que se comporte como un boton */
                                                     font-family: 'Press Start 2P', cursive;
                                                     font-size: 20px;
                                                     padding: 25px;
                                                     width: 95%;
                                                     text-align: center;
                                                     color: white;
                                                     border-radius: 5px;
                                                     cursor: pointer;
                                                     transition: 0.3s;
                                                     box-shadow: 0 0 10px rgba(255, 255, 255, 0.5);
                                                     text-decoration: none;
                                                   }
                                             
                                                   .adivina {
                                                     background: #00FF00;
                                                     color: black;
                                                   }
                                             
                                                   .adivina:hover {
                                                     background: #00CC00;
                                                     box-shadow: 0 0 20px #00FF00;                                                     
                                                   }
                                             
                                                   .dados {
                                                     background: #9933FF;
                                                     color: black;
                                                   }
                                             
                                                   .dados:hover {
                                                     background: #7722CC;
                                                     box-shadow: 0 0 20px #9933FF;
                                                   }
                                             
                                                   .ppt {
                                                     background: #FF1493;
                                                     color: black;
                                                   }
                                             
                                                   .ppt:hover {
                                                     background: #CC1177;
                                                     box-shadow: 0 0 20px #FF1493;
                                                   }
                                             
                                                   ol {
                                                     list-style-type: decimal;
                                                     padding-left: 20px;
                                                   }
                                             
                                                   .adivina-text {
                                                     color: #00FF00;
                                                   }
                                             
                                                   .dados-text {
                                                     color: #9933FF;
                                                   }
                                             
                                                   .ppt-text {
                                                     color: #FF1493;
                                                   }
                                             
                                                   .logout-btn {
                                                        font-family: 'Press Start 2P', cursive;
                                                        font-size: 20px;
                                                        padding: 12px 20px;
                                                        background-color: #FF0000; /* Rojo neón */
                                                        color: black;
                                                        border: 2px solid #FF0000;
                                                        border-radius: 5px;
                                                        cursor: pointer;
                                                        box-shadow: 0 0 10px #FF0000, 0 0 20px #FF1493;
                                                        transition: 0.3s;
                                                    }
                                                   .logout-btn:hover {
                                                        background-color: #CC0000;
                                                        box-shadow : 0 0 20px #FF1493, 0 0 30px #FF1493;
                                                    }
                                                 </style>
                                               </head>
                                               <body>
                                                 <div class="container"> 
                                                   <h1>Servidor de Juegos</h1>
                                             
                                                   <div class="routes">
                                                     <p>El servidor responderá a diferentes rutas según la petición del usuario:</p>
                                                     <ul>
                                                       <li class="adivina-text"><strong>/adivina</strong> Juego "Adivina el Numero".</li>
                                                       <li class="dados-text"><strong>/dados</strong> Juego "Lanza Dados".</li>
                                                       <li class="ppt-text"><strong>/ppt</strong> Juego "Piedra, Papel o Tijera".</li>
                                                     </ul>
                                                     <p>Cualquier otra ruta mostrará un mensaje de error <strong>404 - Página No Encontrada</strong>.</p>
                                                   </div>
                                             
                                                   <div class='links'>
                                                     <a href="adivina" class="adivina">Adivina el Número</a>
                                                     <a href="dados" class="dados">Lanzar Dado</a>
                                                     <a href="ppt" class="ppt">Piedra, Papel o Tijera</a>
                                                   </div>
                                                 </div>
                                                    <form action="/logout" method="POST">
                                                    <button type="submit" class="logout-btn">Cerrar sesión</button>
                                                    </form>
                                               </body>
                                             </html>""";

    //Diseño de la página que muestra que ha habido un error de tipo Not_found
    public static final String html_404 = """
                                          <!DOCTYPE html>
                                          <html>
                                            <head>
                                              <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                              <title>Error 404</title>
                                              <link rel="icon" href="data:," />
                                              <style>
                                                @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                          
                                                body {
                                                  background-color: black;
                                                  color: #FFD700; /* Amarillo neón */
                                                  font-family: 'Press Start 2P', cursive;
                                                  text-align: center;
                                                  padding: 50px;
                                                }
                                          
                                                h1 {
                                                  font-size: 28px;
                                                  text-shadow: 2px 2px 8px #FFD700;
                                                }
                                          
                                                .error-container {
                                                  border: 3px solid #FFD700;
                                                  display: inline-block;
                                                  padding: 20px;
                                                  border-radius: 10px;
                                                  box-shadow: 0px 0px 15px #FFD700;
                                                  margin-top: 20px;
                                                }
                                          
                                                .error-message {
                                                  font-size: 20px;
                                                  margin: 15px 0;
                                                }
                                          
                                                .error-code {
                                                  font-size: 32px;
                                                  font-weight: bold;
                                                  text-shadow: 3px 3px 12px #FFD700;
                                                }
                                          
                                                a {
                                                  display: inline-block;
                                                  margin-top: 20px;
                                                  color: #FFD700;
                                                  text-decoration: none;
                                                  font-size: 16px;
                                                  border: 2px solid #FFD700;
                                                  padding: 10px 20px;
                                                  border-radius: 5px;
                                                  box-shadow: 0px 0px 10px #FFD700;
                                                  transition: 0.3s;
                                                }
                                          
                                                a:hover {
                                                  background-color: #FFD700;
                                                  color: black;
                                                  box-shadow: 0px 0px 20px #FFD700;
                                                }
                                              </style>
                                            </head>
                                            <body>
                                              <h1>¡Página No Encontrada!</h1>
                                              <div class="error-container">
                                                <p class="error-message">Lo sentimos, la página que buscas no existe.</p>
                                                <p class="error-code">Error 404</p>
                                              </div>
                                            </body>
                                          </html>""";
}
