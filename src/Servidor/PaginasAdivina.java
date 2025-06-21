/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

// *******************************************************************
// *                    - PAGINAS ADIVINA-                           *
// *   Clase para almacenar en formato String los html necesarios    *
// *   para el funcionamiento del juego de adivina.                  *
// *   @author: Ana Rodríguez González                               *
// *   @version 2.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class PaginasAdivina {

    //Diseño de la página inicio del juego
    public static final String html_adivina = """                                              
                                              <html>
                                              <head>
                                                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                  <title>¡Adivina el Número!</title>
                                                  <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                  <style>
                                                      @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                              
                                                      body {
                                                          background-color: black;
                                                          color: #00FF00; 
                                                          font-family: 'Press Start 2P', cursive;
                                                          text-align: center;
                                                          padding: 50px;
                                                      }
                                              
                                                      h1 {
                                                          font-size: 30px;
                                                          text-shadow: 2px 2px 8px #00FF00;
                                                      }
                                              
                                                      .container {
                                                          border: 3px solid #00FF00;
                                                          display: inline-block;
                                                          padding: 20px;
                                                          border-radius: 10px;
                                                          box-shadow: 0px 0px 10px #00FF00;
                                                      }
                                              
                                                      label {
                                                          font-size: 20px;
                                                          display: block;
                                                          margin: 15px 0;
                                                      }
                                              
                                                      input {
                                                          background-color: black;
                                                          border: 2px solid #00FF00;
                                                          color: #00FF00;
                                                          font-size: 20px;
                                                          text-align: center;
                                                          padding: 5px;
                                                          width: 80px;
                                                          font-family: 'Press Start 2P', cursive;
                                                      }
                                              
                                                      button {
                                                          background-color: #00FF00;
                                                          color: black;
                                                          border: none;
                                                          padding: 10px 20px;
                                                          font-size: 20px;
                                                          cursor: pointer;
                                                          font-family: 'Press Start 2P', cursive;
                                                          margin-top: 20px;
                                                          border-radius: 5px;
                                                          box-shadow: 0px 0px 10px #00FF00;
                                                      }
                                              
                                                      button:hover {
                                                          background-color: #00cc00;
                                                          box-shadow: 0px 0px 15px #00cc00;
                                                      }
                                              
                                                      .link-sala {
                                                        color: #FFFFFF; 
                                                        font-weight: bold;
                                                        text-decoration: none;
                                                        font-size: 15px;
                                                        font-family: 'Press Start 2P', cursive;
                                                      }
                                                      
                                                      .link-sala:hover {
                                                        color: #FFA500; 
                                                        text-shadow: 0px 0px 5px #FFA500;
                                                      } 
                                              
                                                  </style>
                                              </head>
                                              <body>
                                                  <h1>¡Adivina el Número!</h1>
                                                  <div class="container">
                                                      
                                                      <form action="/jugarAdivina" method="POST">
                                                          <label for="numero">Introduce un número del 1 al 100:</label>
                                                          <input type="text" name="numero" id="numero" value="">      
                                                          <br>                                                    
                                                          <button>Enviar</button>
                                                      </form>                                  
                                                  </div>  
                                                  <div Style="margin-top: 30px;">
                                                    <a href="inicio" class="link-sala">Volver a la sala de juegos</a>
                                                  </div
                                              </body>                                              
                                              </html>""";

    //Diseño que permite ir mostrando los resultados de cada ronda y seguir jugando
    public static final String html_rondas = """                                              
                                              <html>
                                              <head>
                                                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                  <title>¡Adivina el Número!</title>
                                                  <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                  <style>
                                                      @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                              
                                                      body {
                                                          background-color: black;
                                                          color: #00FF00; 
                                                          font-family: 'Press Start 2P', cursive;
                                                          text-align: center;
                                                          padding: 50px;
                                                      }
                                              
                                                      h1 {
                                                          font-size: 30px;
                                                          text-shadow: 2px 2px 8px #00FF00;
                                                      }
                                              
                                                      .container {
                                                          border: 3px solid #00FF00;
                                                          display: inline-block;
                                                          padding: 20px;
                                                          border-radius: 10px;
                                                          box-shadow: 0px 0px 10px #00FF00;
                                                      }
                                              
                                                      label {
                                                          font-size: 20px;
                                                          display: block;
                                                          margin: 15px 0;
                                                      }
                                              
                                                      input {
                                                          background-color: black;
                                                          border: 2px solid #00FF00;
                                                          color: #00FF00;
                                                          font-size: 20px;
                                                          text-align: center;
                                                          padding: 5px;
                                                          width: 50px;
                                                          font-family: 'Press Start 2P', cursive;
                                                      }
                                              
                                                      button {
                                                          background-color: #00FF00;
                                                          color: black;
                                                          border: none;
                                                          padding: 10px 20px;
                                                          font-size: 20px;
                                                          cursor: pointer;
                                                          font-family: 'Press Start 2P', cursive;
                                                          margin-top: 15px;
                                                          border-radius: 5px;
                                                          box-shadow: 0px 0px 10px #00FF00;
                                                      }
                                              
                                                      button:hover {
                                                          background-color: #00cc00;
                                                          box-shadow: 0px 0px 15px #00cc00;
                                                      }
                                                      .resultados, .rondas {
                                                          margin-top: 20px;
                                                      }
                                                  </style>
                                              </head>
                                              <body>
                                                  <h1>¡Adivina el Número!</h1>
                                                  <div class="container">
                                                      
                                                      <form action="/jugarAdivina" method="POST">
                                                          <label for="numero">Introduce un número del 1 al 100:</label>
                                                          <input type="text" name="numero" id="numero" value="">      
                                                          <br>                                                    
                                                          <button>Enviar</button>
                                                      </form>
                                                      <div class="resultados">
                                                        <p>El numero <strong>%s</strong></p>                                             
                                                       </div>                                             
                                                       <div class="rondas">
                                                        <p>Te quedan <strong>%d</strong> intentos</p>
                                                       </div> 
                                                  </div>                                                
                                              </body>
                                              </html>""";

    //Diseño con los resultados finales del juego
    public static final String html_resultados = """ 
                                                 <html>
                                                     <head>
                                                         <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                         <title>¡Adivina el Número!</title>
                                                         <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                         <style>
                                                             @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                 
                                                             body {
                                                                 background-color: black;
                                                                 color: #00FF00;
                                                                 font-family: 'Press Start 2P', cursive;
                                                                 text-align: center;
                                                                 padding: 55px;
                                                             }
                                                 
                                                             h1 {
                                                                 font-size: 30px;
                                                                 text-shadow: 2px 2px 8px #00FF00;
                                                             }
                                                 
                                                             .container {
                                                                 border: 3px solid #00FF00;
                                                                 display: inline-block;
                                                                 padding: 20px;
                                                                 border-radius: 15px;
                                                                 box-shadow: 0px 0px 10px #00FF00;
                                                             }
                                                 
                                                             .result {
                                                                 font-size: 20px;
                                                                 font-weight: bold;
                                                                 margin-top: 20px;
                                                                 color: #FFFF00;
                                                             }
                                                 
                                                             a {
                                                                 color: #00CC00;
                                                                 font-weight: bold;
                                                             }
                                                 
                                                             a:hover {
                                                                 color: #009900;
                                                             }
                                                 
                                                             .link-sala {
                                                               color: #FFFFFF;
                                                               font-weight: bold;
                                                               text-decoration: none;
                                                               font-size: 15px;
                                                               font-family: 'Press Start 2P', cursive;
                                                             }
                                                             
                                                             .link-sala:hover {
                                                               color: #FFA500; 
                                                               text-shadow: 0px 0px 5px #FFA500;
                                                             }
                                                         </style>
                                                     </head>
                                                     <body>
                                                         <h1>¡Adivina el número!</h1>
                                                         <div class="container">
                                                             <div class="result">
                                                                 <h1>Resultados:</h1>
                                                                 <p>Has %s, el número correcto es %d</p>
                                                             </div>
                                                             <br>
                                                             <a href="adivina">Volver a jugar</a>
                                                         </div> 
                                                         <div Style="margin-top: 30px;">
                                                            <a href="inicio" class="link-sala">Volver a la sala de juegos</a>
                                                         </div                                                      
                                                     </body>                                                     
                                                 </html>""";

}
