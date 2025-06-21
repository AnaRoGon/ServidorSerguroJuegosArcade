# Servidor Seguro de Juegos Arcade
Proyecto Java que implementa un servidor HTTPS seguro, donde los usuarios pueden iniciar sesión o registrarse para acceder a una pequeña colección de juegos arcade. 
El control de sesión se realiza mediante cookies. El almacenamiento y comprobación de la contraseña se realiza mediante cifrado BCrypt.
Para mayor seguridad, los datos introducidos por el usuario se almacena en un fichero con algoritmo de cifrado AES. 

# Características principales

## Pantalla de Inicio de Sesión


## Tres juegos disponibles:

### Lanzar Dados

### Piedra, Papel o Tijera

### Adivina el número


# Tecnologías utilizadas

Para el desarrollo de esta aplicación se destaca: 
* javax.crypto.* para cifrado de datos con AES.
* org.mindrot.jbcrypt.BCrypt para cifrado de contraseña. 
* SSLServerSocket, SSLSocket para sockets seguros. 
* ConcurrentHashMap para almacenar la sesión de los juegos por usuario.
* Multihilo con Thread para gestionar múltiples clientes.
* HTML simple embebido como respuesta en Java.
* Cookies HTTP para mantener sesiones entre peticiones.*
* java.util.logging para manejar el fichero log de errores.
* Control de concurrencia de hilos y acceso a ficheros mediante una clase Semáforo.
* Inicio de sesión y registro seguros con control de sesión mediante cookies.
* Persistencia de usuarios almacenados en un archivo local encriptado.

# Instrucciones de uso.

Para ejecutar la app copia el enlace del repositorio en GitHub: <https://github.com/AnaRoGon/ServidorSerguroJuegosArcade.git>

1. Abre NetBeans y ve al menú:
2. Team -> Git -> Clone.
3. Pega la URL del repositorio y sigue las instrucciones para clonar el proyecto localmente.
4. Una vez clonado, NetBeans detectará el proyecto y podrás abrirlo y ejecutarlo directamente desde el IDE.

