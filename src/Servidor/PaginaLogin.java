/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

// *******************************************************************
// *                      - PAGINA LOGIN-                            *
// *   Clase para almacenar en formato String los html relacionados  *
// *   con el logeo y el registro en el servidor                     *
// *   @author: Ana Rodríguez González Fecha: 14/05/2025             *
// *   @version 1.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class PaginaLogin {
    
    //Página principal para el logeo del usuario donde se da la opción de redirigir a la página de registro
    public static final String html_login = """
                                            <!DOCTYPE html>
                                            <html>
                                            <head>
                                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                            <title>Login del servidor</title>
                                            <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                            <style>
                                                @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                
                                                body {
                                                    background-color: #000;
                                                    color: #0ff;
                                                    font-family: 'Press Start 2P', cursive;
                                                    text-align: center;
                                                    padding: 50px;
                                                    }
                                            
                                                    h1 {
                                                        font-size: 50px;
                                                        color: #0ff;
                                                        text-shadow: 0 0 10px #0ff;
                                                        margin-bottom: 15px;
                                                    }
                                            
                                                    h2 {
                                                        font-size: 25px;
                                                        color: #0ff;
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                            
                                                    .container {
                                                        display: inline-block;
                                                        padding: 10px 200px 50px 200px;
                                                        border: 2px solid #0ff;
                                                        border-radius: 10px;
                                                        background-color: rgba(0, 255, 255, 0.1);
                                                        box-shadow: 0 0 20px #0ff;
                                                        text-align: center;
                                                    }
                                            
                                                    label {
                                                        font-size: 20px;
                                                        display: block;
                                                        margin-top: 20px;
                                                        padding: 20px;
                                                        color: #0ff;
                                                    }
                                            
                                                    input {
                                                        display: block; 
                                                        background-color: #000;
                                                        border: 2px solid #0ff;
                                                        color: #0ff;
                                                        width: 400px;
                                                        height: 50px; 
                                                        padding: 5px;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 5px;
                                                        box-sizing: border-box;
                                                    }
                                            
                                                    button {
                                                        background-color: #0ff;
                                                        color: #000;
                                                        border: none;
                                                        padding: 20px 20px;
                                                        font-size: 20px;
                                                        cursor: pointer;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 55px; 
                                                        border-radius: 5px;
                                                        box-shadow: 0 0 15px #0ff;
                                                    }
                                            
                                                    button:hover {
                                                        background-color: black;
                                                        color: #00ffff;
                                                        box-shadow: 0 0 25px #00ffff;
                                                    }
                                                </style>
                                            </head>
                                            <body>
                                                <h1>¡Bienvenido!</h1>
                                                <h2>Inicia sesión para jugar</h2>
                                                <div class="container">
                                                    <form action="/login" method="POST">
                                                        <label for="email">Email:</label>
                                                        <input type="text" name="email" id="email" required>                                                         
                                                        <label for="password">Contraseña:</label>
                                                        <input type="password" id="password" name="password" required>                                                           
                                                        <button type="submit">Iniciar sesión</button>
                                                    </form>
                                                </div>
                                                <h2>Si no tienes una cuenta puedes registrarte desde aquí</h2>
                                                <a href="registro" class="registro">Crear una nueva cuenta</a>
                                            </body>
                                            </html>""";
    
    //Pagina que se muestra cuando el logeo ha sido incorrecto con la opción de añadir distintas frases según el tipo de fallo
    public static final String html_password_login_incorrecto = """
                                            <!DOCTYPE html>
                                            <html>
                                            <head>
                                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                            <title>Login del servidor</title>
                                            <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                            <style>
                                                @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                
                                                body {
                                                    background-color: #000;
                                                    color: #0ff;
                                                    font-family: 'Press Start 2P', cursive;
                                                    text-align: center;
                                                    padding: 50px;
                                                    }
                                            
                                                    h1 {
                                                        font-size: 50px;
                                                        color: #0ff;
                                                        text-shadow: 0 0 10px #0ff;
                                                        margin-bottom: 15px;
                                                    }
                                            
                                                    h2 {
                                                        font-size: 25px;
                                                        color: #0ff;
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                                    
                                                    p {
                                                        font-size: 20px;
                                                        color: #f00; 
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                                                                           
                                                        }
                                            
                                                    .container {
                                                        display: inline-block;
                                                        padding: 10px 200px 50px 200px;
                                                        border: 2px solid #0ff;
                                                        border-radius: 10px;
                                                        background-color: rgba(0, 255, 255, 0.1);
                                                        box-shadow: 0 0 20px #0ff;
                                                        text-align: center;
                                                    }
                                            
                                                    label {
                                                        font-size: 20px;
                                                        display: block;
                                                        margin-top: 20px;
                                                        padding: 20px;
                                                        color: #0ff;
                                                    }
                                            
                                                    input {
                                                        display: block; 
                                                        background-color: #000;
                                                        border: 2px solid #0ff;
                                                        color: #0ff;
                                                        width: 400px;
                                                        height: 50px; 
                                                        padding: 5px;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 5px;
                                                        box-sizing: border-box;
                                                    }
                                            
                                                    button {
                                                        background-color: #0ff;
                                                        color: #000;
                                                        border: none;
                                                        padding: 20px 20px;
                                                        font-size: 20px;
                                                        cursor: pointer;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 55px; 
                                                        border-radius: 5px;
                                                        box-shadow: 0 0 15px #0ff;
                                                    }
                                            
                                                    button:hover {
                                                        background-color: black;
                                                        color: #00ffff;
                                                        box-shadow: 0 0 25px #00ffff;
                                                    }
                                                </style>
                                            </head>
                                            <body>
                                                <h1>¡Bienvenido!</h1>
                                                <h2>Inicia tu sesión en el servidor</h2>                                                  
                                                <div class="container">
                                                    <form action="/login" method="POST">
                                                        <label for="email">Email:</label>
                                                        <input type="text" name="email" id="email" required>                                                         
                                                        <label for="password">Contraseña:</label>
                                                        <input type="password" id="password" name="password" required>                                                           
                                                        <button type="submit">Iniciar sesion</button>
                                                    </form>
                                                </div>
                                                <p>%s</p>
                                                <p>%s</p> 
                                                <a href="registro" class="registro">Crear una nueva cuenta</a>             
                                            </body>
                                            </html>""";
    
    //Página principal para el registro del usuario
    public static final String html_newUser = """
                                            <!DOCTYPE html>
                                            <html>
                                            <head>
                                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                            <title>Nuevo usuario</title>
                                            <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                            <style>
                                                @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                
                                                body {
                                                    background-color: #000;
                                                    color: #0ff;
                                                    font-family: 'Press Start 2P', cursive;
                                                    text-align: center;
                                                    padding: 50px;
                                                    }
                                            
                                                    h1 {
                                                        font-size: 50px;
                                                        color: #0ff;
                                                        text-shadow: 0 0 10px #0ff;
                                                        margin-bottom: 15px;
                                                    }
                                            
                                                    h2 {
                                                        font-size: 25px;
                                                        color: #0ff;
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                                 
                                                    p {
                                                        font-size: 20px;
                                                        color: #f00; 
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                            
                                                    .container {
                                                        display: inline-block;
                                                        padding: 10px 200px 50px 200px;
                                                        border: 2px solid #0ff;
                                                        border-radius: 10px;
                                                        background-color: rgba(0, 255, 255, 0.1);
                                                        box-shadow: 0 0 20px #0ff;
                                                        text-align: center;
                                                    }
                                            
                                                    label {
                                                        font-size: 20px;
                                                        display: block;
                                                        margin-top: 20px;
                                                        padding: 20px;
                                                        color: #0ff;
                                                    }
                                            
                                                    input {
                                                        display: block; 
                                                        background-color: #000;
                                                        border: 2px solid #0ff;
                                                        color: #0ff;
                                                        width: 400px;
                                                        height: 50px; 
                                                        padding: 5px;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 5px;
                                                        box-sizing: border-box;
                                                    }
                                            
                                                    button {
                                                        background-color: #0ff;
                                                        color: #000;
                                                        border: none;
                                                        padding: 20px 20px;
                                                        font-size: 20px;
                                                        cursor: pointer;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 55px; 
                                                        border-radius: 5px;
                                                        box-shadow: 0 0 15px #0ff;
                                                    }
                                            
                                                    button:hover {
                                                        background-color: black;
                                                        color: #00ffff;
                                                        box-shadow: 0 0 25px #00ffff;
                                                    }
                                                </style>
                                            </head>
                                            <body>                                                
                                                <h1>¡Registrate!</h1>
                                                <p>Para poder jugar debes estar registrado<p>
                                                <h2>Introduce tus datos</h2>
                                                <div class="container">
                                                    <form action="/registro" method="POST">
                                                        <label for="email">Email:</label>
                                                        <input type="text" name="email" id="email" required>                                                         
                                                        <label for="password">Contraseña:</label>
                                                        <input type="password" id="password" name="password" required>                                                           
                                                        <button type="submit">Registrarse</button>
                                                    </form>
                                                </div>
                                            </body>
                                            </html>""";
    
    //Página para indicar cuando el registro ha fallado con la opción de formatear la salida de los mensajes
    public static final String html_newUser_incorrect = """
                                            <!DOCTYPE html>
                                            <html>
                                            <head>
                                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                            <title>Nuevo usuario</title>
                                            <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                            <style>
                                                @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                
                                                body {
                                                    background-color: #000;
                                                    color: #0ff;
                                                    font-family: 'Press Start 2P', cursive;
                                                    text-align: center;
                                                    padding: 50px;
                                                    }
                                            
                                                    h1 {
                                                        font-size: 50px;
                                                        color: #0ff;
                                                        text-shadow: 0 0 10px #0ff;
                                                        margin-bottom: 15px;
                                                    }
                                            
                                                    h2 {
                                                        font-size: 25px;
                                                        color: #0ff;
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                                 
                                                    p {
                                                        font-size: 20px;
                                                        color: #f00; 
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                            
                                                    .container {
                                                        display: inline-block;
                                                        padding: 10px 200px 50px 200px;
                                                        border: 2px solid #0ff;
                                                        border-radius: 10px;
                                                        background-color: rgba(0, 255, 255, 0.1);
                                                        box-shadow: 0 0 20px #0ff;
                                                        text-align: center;
                                                    }
                                            
                                                    label {
                                                        font-size: 20px;
                                                        display: block;
                                                        margin-top: 20px;
                                                        padding: 20px;
                                                        color: #0ff;
                                                    }
                                            
                                                    input {
                                                        display: block; 
                                                        background-color: #000;
                                                        border: 2px solid #0ff;
                                                        color: #0ff;
                                                        width: 400px;
                                                        height: 50px; 
                                                        padding: 5px;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 5px;
                                                        box-sizing: border-box;
                                                    }
                                            
                                                    button {
                                                        background-color: #0ff;
                                                        color: #000;
                                                        border: none;
                                                        padding: 20px 20px;
                                                        font-size: 20px;
                                                        cursor: pointer;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 55px; 
                                                        border-radius: 5px;
                                                        box-shadow: 0 0 15px #0ff;
                                                    }
                                            
                                                    button:hover {
                                                        background-color: black;
                                                        color: #00ffff;
                                                        box-shadow: 0 0 25px #00ffff;
                                                    }
                                                </style>
                                            </head>
                                            <body>                                                
                                                <h1>¡Registrate!</h1>
                                                <p>Para poder jugar debes estar registrado<p>
                                                <h2>Introduce tus datos</h2>
                                                <div class="container">
                                                    <form action="/registro" method="POST">
                                                        <label for="email">Email:</label>
                                                        <input type="text" name="email" id="email" required>                                                         
                                                        <label for="password">Contraseña:</label>
                                                        <input type="password" id="password" name="password" required>                                                           
                                                        <button type="submit">Registrarse</button>
                                                    </form>
                                                </div>
                                                <p>%s</p> 
                                                <p>%s</p> 
                                            </body>
                                            </html>""";
    
    
    //Página para cuando se intenta registrar un usuario que ya existía con enlace a la página de login
    public static final String html_existingUser = """
                                            <!DOCTYPE html>
                                            <html>
                                            <head>
                                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                            <title>Nuevo usuario</title>
                                            <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/5752/5752090.png">
                                            <style>
                                                @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                                                
                                                body {
                                                    background-color: #000;
                                                    color: #0ff;
                                                    font-family: 'Press Start 2P', cursive;
                                                    text-align: center;
                                                    padding: 50px;
                                                    }
                                            
                                                    h1 {
                                                        font-size: 50px;
                                                        color: #0ff;
                                                        text-shadow: 0 0 10px #0ff;
                                                        margin-bottom: 15px;
                                                    }
                                            
                                                    h2 {
                                                        font-size: 25px;
                                                        color: #0ff;
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                                 
                                                    p {
                                                        font-size: 20px;
                                                        color: #f00; 
                                                        margin-top: 50px;
                                                        margin-bottom: 30px;
                                                    }
                                            
                                                    .container {
                                                        display: inline-block;
                                                        padding: 10px 200px 50px 200px;
                                                        border: 2px solid #0ff;
                                                        border-radius: 10px;
                                                        background-color: rgba(0, 255, 255, 0.1);
                                                        box-shadow: 0 0 20px #0ff;
                                                        text-align: center;
                                                    }
                                            
                                                    label {
                                                        font-size: 20px;
                                                        display: block;
                                                        margin-top: 20px;
                                                        padding: 20px;
                                                        color: #0ff;
                                                    }
                                            
                                                    input {
                                                        display: block; 
                                                        background-color: #000;
                                                        border: 2px solid #0ff;
                                                        color: #0ff;
                                                        width: 400px;
                                                        height: 50px; 
                                                        padding: 5px;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 5px;
                                                        box-sizing: border-box;
                                                    }
                                            
                                                    button {
                                                        background-color: #0ff;
                                                        color: #000;
                                                        border: none;
                                                        padding: 20px 20px;
                                                        font-size: 20px;
                                                        cursor: pointer;
                                                        font-family: 'Press Start 2P', cursive;
                                                        margin-top: 55px; 
                                                        border-radius: 5px;
                                                        box-shadow: 0 0 15px #0ff;
                                                    }
                                            
                                                    button:hover {
                                                        background-color: black;
                                                        color: #00ffff;
                                                        box-shadow: 0 0 25px #00ffff;
                                                    }
                                                </style>
                                            </head>
                                            <body>                                                
                                                <h1>¡Registrate!</h1>
                                                <p>Para poder jugar debes estar registrado<p>
                                                <h2>Introduce tus datos</h2>
                                                <div class="container">
                                                    <form action="/registro" method="POST">
                                                        <label for="email">Email:</label>
                                                        <input type="text" name="email" id="email" required>                                                         
                                                        <label for="password">Contraseña:</label>
                                                        <input type="password" id="password" name="password" required>                                                           
                                                        <button type="submit">Registrarse</button>
                                                    </form>
                                                </div>
                                                <p>%s </p> 
                                                <p>%s </p> 
                                                <a href="login" class="registro">Iniciar sesión</a>   
                                            </body>
                                            </html>""";

}
