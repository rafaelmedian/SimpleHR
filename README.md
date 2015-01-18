GestorEmpleados
===============

A single page desktop app for storage of your employees

## Características
![Imagen 3](/images/HR_3.png)
- __Opensource__. Cualquiera puede extender nuestro código para implementar o modificar funciones en sus negocios, puede ser un excelente punto de partida para negocios que por su tamaño o especialización requieren una solución propia. Ésto siempre que respeten los términos de nuestra licencia :).
- __Gratuito__. Excelente para pequeños y medianos negocios que requieran un sistema de para llevar control de sus empleados. Además las actualizaciones seguirá siendo gratuitas para siempre.
- __Diseño__ moderno que da una buena presentación a los clientes y oscuro que reduce el cansancio visual de los usuarios
- __Touchscreen y touchless (no touch)__: No solo su diseño es perfectamente apto para terminales touchscreen, además presenta una navegación por teclado, finalmente cuidada para terminales que no tienen touchscreen, ésto es útil en negocios de mucho tráfico.
- __Seguridad__ Con login para usuarios y limitaciones para crear usuaios.



## Instalación
### Requisitos mínimos
- Windows XP y superiores: Si bien la mayor parte del programa es multiplataforma, exíste un componente que solo funciona con windows, estoy trabajando en quitar ese componente (OneTouch SDK).
- Java JRE 7u6
- Lector de huella DigitalPersona u.are.u 4000 o 4500

### Instalación de la base de datos
1. Descargar MySQL Server, desde la página oficial. [Página oficial de MySQL Server](http://dev.mysql.com/downloads/mysql/)
2. [Descargar MySQL Workbench](http://dev.mysql.com/downloads/tools/workbench/)
3. Instalar MySQL con los parámetros deseados, para un negocio pequeño los parámetros que vienen por default en el instalador serán más que suficientes. Es importante recordar el password que pongamos durante la instalación.
4. Instalar MySQL Workbench, éste software facilita las operaciones con la base de datos y será de gran utilidad para administrarla.
5. Abrir workbench, la interfaz está dividida en 3 columnas: "SQL Development", "Data Modeling" y "Server Administration".
6. Cargar la base de datos de HRSImple, en la columna "Server Administration", después haga doble click en "mysql@localhost", ahora vaya a "Data export and restore", seleccione la pestaña "Import from disk", seleccione "Import from self-contained file", haga click en "..." y busque el archivo "schema.sql" en la carpeta de instalación de HRSimple. Por último haga click en "Select all tables" y "Start import".
7. ¡Felicidades! Si los pasos anteriores se han seguido con éxito, la base de datos estará lista para ser utilizada.

### Capturas de la aplicación
![Imagen 1](/images/HR_1.png)
![Imagen 6](/images/HR_6.png)
![Imagen 7](/images/HR_7.png)
![Imagen 8](/images/HR_8.png)
![Imagen 9](/images/HR_9.png)



#### Notas
- Es posible conectarse a cualquier otro tipo de instalación de MySQL y resultaría útil para configuraciones excentricas, por ejemplo usando LAMP, TurnKey Linux, WAMP, etc.



Instrumentación
===============
Para los preocupados por el lado técnico, HRSimple utiliza las siguientes tecnologías libres:
- Java
- MySQL
- JavaFX 
- JavaFX Dialogs 
- FXML y CSS


Licencia
========

Licenciado bajo GNUv2. Para más detalles ver el archivo LICENSE adjunto al código en éste repositorio.

