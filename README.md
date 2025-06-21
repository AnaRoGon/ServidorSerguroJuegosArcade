# Servidor Seguro de Juegos Arcade
Proyecto Java que implementa un servidor HTTPS seguro, donde los usuarios pueden iniciar sesión o registrarse para acceder a una pequeña colección de juegos arcade. 
El control de sesión se realiza mediante cookies. El almacenamiento y comprobación de la contraseña se realiza mediante cifrado BCrypt.
Para mayor seguridad, los datos introducidos por el usuario se almacena en un fichero con algoritmo de cifrado AES. 

# Características principales

## Pantalla de Inicio de Sesión

![00](https://github.com/user-attachments/assets/0dee0bc2-b7e4-4661-a17c-863d40bbfac2)

Si se detecta que el usuario no está registrado se indica con un mensaje

![02](https://github.com/user-attachments/assets/c176a3f1-7623-4f42-9f12-fc9f2a028454)

![03](https://github.com/user-attachments/assets/a9b4b7e6-e222-41f0-b430-5857ba80b15e)

Nota: En caso de que el usuario estuviese tratando de logearse y ya existiese una cuenta con ese nombre
también se indicaría al usuario. 

## Tres juegos disponibles:

Pantalla de inicio de los juegos: 

![04](https://github.com/user-attachments/assets/019f11c0-40ec-46c8-9e67-8f477a74be96)

### Lanzar Dados

![07](https://github.com/user-attachments/assets/31b75488-59e8-4434-b52f-b7dab4a11d30)

![08](https://github.com/user-attachments/assets/d7a136c8-4688-4d01-a3c6-c28437edf315)

### Piedra, Papel o Tijera

![09](https://github.com/user-attachments/assets/1b4783bd-a7fd-4d36-88c6-6c7465daf13e)

![10](https://github.com/user-attachments/assets/a95b1aa2-054e-4ce8-ab35-ac1a9dcc97c0)

### Adivina el número

![05](https://github.com/user-attachments/assets/afe374ec-016c-4dc7-955e-398fa5da2d14)

![06](https://github.com/user-attachments/assets/db65b2ce-2912-40c1-8b98-ef0e1a884b39)

# Tecnologías utilizadas

Para el desarrollo de esta aplicación se destaca: 
* javax.crypto.* para cifrado de datos con AES.
* org.mindrot.jbcrypt.BCrypt para cifrado de contraseña. 
* SSLServerSocket, SSLSocket para sockets seguros. 
* ConcurrentHashMap para almacenar la sesión de los juegos por usuario.
* Multihilo con Thread para gestionar múltiples clientes.
* HTML simple embebido como respuesta en Java.
* Cookies HTTP para mantener sesiones entre peticiones.
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

