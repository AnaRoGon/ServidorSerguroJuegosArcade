/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

// *******************************************************************
// *                       - PAGINAS DADOS-                          *
// *   Clase para almacenar en formato String los html               *
// *   necesarios para el funcionamiento del juego de dados          *
// *   @author: Ana Rodríguez González                               *
// *   @version 2.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class PaginasDados {

    //Diseño de la página inicio del juego
    public static final String html_dados = """
                                           <!DOCTYPE html>
                                            <html>
                                              <head>
                                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                <title>¡Lanza Dados!</title>
                                                <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                <style>
                                                  @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                            
                                                  body {
                                                    background-color: black;
                                                    color: #9B4DFF; 
                                                    font-family: 'Press Start 2P', cursive;
                                                    text-align: center;
                                                    padding: 50px;
                                                  }
                                            
                                                  h1 {
                                                    font-size: 30px;
                                                    text-shadow: 2px 2px 8px #9B4DFF;
                                                  }
                                            
                                                  .container {
                                                    border: 3px solid #9B4DFF;
                                                    display: inline-block;
                                                    padding: 20px;
                                                    border-radius: 10px;
                                                    box-shadow: 0px 0px 10px #9B4DFF;
                                                  }
                                            
                                                  .instructions {
                                                    font-size: 20px;
                                                    margin: 15px 0;
                                                  }
                                            
                                                  button {
                                                    background-color: #9B4DFF;
                                                    color: black;
                                                    border: none;
                                                    padding: 10px 20px;
                                                    font-size: 20px;
                                                    cursor: pointer;
                                                    font-family: 'Press Start 2P', cursive;
                                                    margin-top: 15px;
                                                    border-radius: 5px;
                                                    box-shadow: 0px 0px 10px #9B4DFF;
                                                  }
                                            
                                                  button:hover {
                                                    background-color: #7a33cc;
                                                    box-shadow: 0px 0px 15px #7a33cc;
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
                                                <h1>¡Lanza Dados!</h1>
                                                <div class="container">
                                                  <p class="instructions">Pulsa para lanzar los dados:</p>
                                                  <form action='/lanzarDado' method='POST'>
                                                    <button name='lanzarDado' value='0'>Lanzar Dado</button>
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
                                                 <title>¡Lanza Dados!</title>
                                                 <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                 <style>
                                                     @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                             
                                                     body {
                                                         background-color: black;
                                                         color: #9B4DFF;
                                                         font-family: 'Press Start 2P', cursive;
                                                         text-align: center;
                                                         padding: 50px;
                                                     }
                                             
                                                     h1 {
                                                         font-size: 30px;
                                                         text-shadow: 2px 2px 8px #9B4DFF;
                                                     }
                                             
                                                     .container {
                                                         border: 3px solid #9B4DFF;
                                                         display: inline-block;
                                                         padding: 20px;
                                                         border-radius: 10px;
                                                         box-shadow: 0px 0px 10px #9B4DFF;
                                                     }
                                             
                                                     .instructions {
                                                         font-size: 20px;
                                                         margin: 15px 0;
                                                     }
                                             
                                                     button {
                                                         background-color: #9B4DFF;
                                                         color: black;
                                                         border: none;
                                                         padding: 10px 20px;
                                                         font-size: 20px;
                                                         cursor: pointer;
                                                         font-family: 'Press Start 2P', cursive;
                                                         margin-top: 15px;
                                                         border-radius: 5px;
                                                         box-shadow: 0px 0px 10px #9B4DFF;
                                                     }
                                             
                                                     button:hover {
                                                         background-color: #7a33cc;
                                                         box-shadow: 0px 0px 15px #7a33cc;
                                                     }
                                             
                                                     .resultados, .rondas {
                                                         margin-top: 20px;
                                                     }
                                                 </style>
                                             </head>
                                             <body>
                                                 <h1>¡Lanza Dados!</h1>
                                                 <div class="container">
                                                     <p class="instructions">Pulsa para lanzar los dados:</p>
                                                     <form action='/lanzarDado' method='POST'>
                                                         <button name='lanzarDado' value='0'>%s</button>
                                                     </form>
                                                     <div class="resultados">
                                                         <p>Dado cliente: %d </p>
                                                         <p>Dado Servidor: %d </p>
                                                         <br>
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
                                                     <title>¡Lanza Dados!</title>
                                                     <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                                     <style>
                                                       @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                 
                                                       body {
                                                         background-color: black;
                                                         color: #9B4DFF; 
                                                         font-family: 'Press Start 2P', cursive;
                                                         text-align: center;
                                                         padding: 50px;
                                                       }
                                                 
                                                       h1 {
                                                         font-size: 30px;
                                                         text-shadow: 2px 2px 8px #9B4DFF;
                                                       }
                                                 
                                                       .container {
                                                         border: 3px solid #9B4DFF;
                                                         display: inline-block;
                                                         padding: 20px;
                                                         border-radius: 10px;
                                                         box-shadow: 0px 0px 10px #9B4DFF;
                                                       }   
                                                 
                                                       .result {
                                                         font-size: 20px;
                                                         font-weight: bold;
                                                         margin-top: 20px;
                                                         color: #FFFF00;
                                                       }
                                                       
                                                       a {
                                                        color: #872AEF;
                                                        font-weight: bold;
                                                       }
                                                                                                  
                                                       a:hover {
                                                         color: #751DCB; 
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
                                                     <h1>¡Lanza Dados!</h1>
                                                     <div class="container">
                                                       <div class="result">
                                                         <h1>Resultados:</h1>
                                                         <p>%s</p> 
                                                         <p> ------------------------------------- </p>
                                                         <p>Rondas ganadas por el cliente: %d </p>
                                                         <p>Rondas ganadas por el servidor: %d </p>                                                 
                                                       </div>   
                                                       <br>
                                                       <a href="dados">Volver a jugar</a>                                                    
                                                     </div>
                                                     <div Style="margin-top: 30px;">
                                                       <a href="inicio" class="link-sala">Volver a la sala de juegos</a>
                                                     </div 
                                                   </body>
                                                 </html>""";

}
