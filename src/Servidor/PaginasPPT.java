/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

// *******************************************************************
// *                       - PAGINAS PPT-                            *
// *   Clase para almacenar en formato String los html               *
// *   necesarios para el funcionamiento del juego de PPT            *
// *   @author: Ana Rodríguez González                               *
// *   @version 2.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class PaginasPPT {

    //Diseño de la página inicio del juego
    public static final String html_ppt = """
                                        <!DOCTYPE html>
                                        <html>
                                          <head>
                                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                            <title>¡Juega a Piedra, Papel o Tijera!</title>
                                            <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                            <style>
                                              @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                        
                                              body {
                                                background-color: black;
                                                color: #FF66B2; 
                                                font-family: 'Press Start 2P', cursive;
                                                text-align: center;
                                                padding: 50px;
                                              }
                                        
                                              h1 {
                                                font-size: 30px;
                                                text-shadow: 2px 2px 8px #FF66B2;
                                              }
                                        
                                              .container {
                                                border: 3px solid #FF66B2;
                                                display: inline-block;
                                                padding: 20px;
                                                border-radius: 10px;
                                                box-shadow: 0px 0px 10px #FF66B2;
                                              }
                                        
                                              .instructions {
                                                font-size: 20px;
                                                margin: 15px 0;
                                              }
                                        
                                              button {
                                                background-color: #FF66B2;
                                                color: black;
                                                border: none;
                                                padding: 10px 20px;
                                                font-size: 20px;
                                                cursor: pointer;
                                                font-family: 'Press Start 2P', cursive;
                                                margin-top: 15px;
                                                border-radius: 5px;
                                                box-shadow: 0px 0px 10px #FF66B2;
                                              }
                                        
                                              button:hover {
                                                background-color: #ff3385;
                                                box-shadow: 0px 0px 15px #ff3385;
                                              }
                                        
                                              .button-group {
                                                display: flex;
                                                justify-content: space-around;
                                                margin-top: 20px;
                                              }
                                        
                                              .button-group button {
                                                margin: 0 10px;
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
                                            <h1>¡Juega a Piedra, Papel o Tijera!</h1>                                          
                                            <div class="container">
                                              <p class="instructions">Pulsa para jugar:</p>                                                  
                                        
                                              <div class="button-group">
                                                <form action='/jugarPPT' method='POST'>
                                                  <button name='jugarPiedra' value='1'>Piedra</button>  
                                                  <button name='jugarPapel' value='2'>Papel</button>  
                                                  <button name='jugarTijera' value='3'>Tijera</button>
                                                </form>
                                              </div>
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
                                                 <title>¡Juega a Piedra, Papel o Tijera!</title>
                                                 <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                 <style>
                                                   @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                             
                                                   body {
                                                     background-color: black;
                                                     color: #FF66B2; 
                                                     font-family: 'Press Start 2P', cursive;
                                                     text-align: center;
                                                     padding: 50px;
                                                   }
                                             
                                                   h1 {
                                                     font-size: 30px;
                                                     text-shadow: 2px 2px 8px #FF66B2;
                                                   }
                                             
                                                   .container {
                                                     border: 3px solid #FF66B2;
                                                     display: inline-block;
                                                     padding: 20px;
                                                     border-radius: 10px;
                                                     box-shadow: 0px 0px 10px #FF66B2;
                                                   }
                                             
                                                   .instructions {
                                                     font-size: 20px;
                                                     margin: 15px 0;
                                                   }
                                             
                                                   button {
                                                     background-color: #FF66B2;
                                                     color: black;
                                                     border: none;
                                                     padding: 10px 20px;
                                                     font-size: 20px;
                                                     cursor: pointer;
                                                     font-family: 'Press Start 2P', cursive;
                                                     margin-top: 15px;
                                                     border-radius: 5px;
                                                     box-shadow: 0px 0px 10px #FF66B2;
                                                   }
                                             
                                                   button:hover {
                                                     background-color: #ff3385;
                                                     box-shadow: 0px 0px 15px #ff3385;
                                                   }
                                             
                                                   .button-group {
                                                     display: flex;
                                                     justify-content: space-around;
                                                     margin-top: 20px;
                                                   }
                                             
                                                   .button-group button {
                                                     margin: 0 10px;
                                                   }                                             
                                                   .resultados, .rondas {
                                                     margin-top: 20px;
                                                   }
                                                 </style>
                                               </head>
                                               <body>                                          
                                                 <h1>¡Juega a Piedra, Papel o Tijera!</h1>                                          
                                                 <div class="container">
                                                   <p class="instructions">Pulsa para jugar:</p>                                                  
                                             
                                                   <div class="button-group">
                                                     <form action='/jugarPPT' method='POST'>
                                                       <button name='jugarPiedra' value='1'>%s</button>
                                                       <button name='jugarPapel' value='2'>%s</button>
                                                       <button name='jugarTijera' value='3'>%s</button>
                                                     </form>
                                                   </div>
                                             
                                                   <div class="resultados">
                                                     <p>Mano cliente: %s </p>
                                                     <p>Mano Servidor: %s </p>
                                                     <p><strong>%s</strong></p>
                                                   </div>                                             
                                                   <div class="rondas">
                                                     <p>Rondas: <strong>%d</strong></p>
                                                   </div>
                                                 </div>
                                               </body>
                                             </html>""";

    //Diseño con los resultados finales del juego
    public static final String html_resultados = """                                                 
                                                 <html>
                                                   <head>
                                                     <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                     <title>¡Juega a Piedra, Papel o Tijera!</title>
                                                     <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                     <style>
                                                       @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                 
                                                       body {
                                                         background-color: black;
                                                         color: #FF66B2; 
                                                         font-family: 'Press Start 2P', cursive;
                                                         text-align: center;
                                                         padding: 50px;
                                                       }
                                                 
                                                       h1 {
                                                         font-size: 30px;
                                                         text-shadow: 2px 2px 8px #FF66B2;
                                                       }
                                                 
                                                       .container {
                                                         border: 3px solid #FF66B2;
                                                         display: inline-block;
                                                         padding: 20px;
                                                         border-radius: 10px;
                                                         box-shadow: 0px 0px 10px #FF66B2;
                                                       }   
                                                 
                                                       .result {
                                                         font-size: 20px;
                                                         font-weight: bold;
                                                         margin-top: 20px;
                                                         color: #FFFF00;
                                                       }
                                                 
                                                       a {
                                                         color: #ff3385;
                                                         font-weight: bold;
                                                       }
                                                 
                                                       a:hover {
                                                         color: #ff1a66; 
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
                                                     <h1>¡Piedra, papel o tijera!</h1>
                                                     <div class="container">
                                                       <div class="result">
                                                       <h1>Resultados:</h1>
                                                        <p>%s</p> 
                                                        <p> ------------------------------------- </p>
                                                        <p>Rondas ganadas por el cliente: %d </p>
                                                        <p>Rondas ganadas por el servidor: %d </p> 
                                                       </div>      
                                                       <a href="ppt">Volver a jugar</a>
                                                     </div>
                                                     <div Style="margin-top: 30px;">
                                                       <a href="inicio" class="link-sala">Volver a la sala de juegos</a>
                                                      </div 
                                                   </body>
                                                 </html>""";
}
