# CardPaymentsApi
Api para pagos de tarjeta para la compañia BankInc, realizada en spring boot con una base de datos H2

## Instrucciones para ejecutar la aplicación local en eclipse o Spring tools
1. Descargar el proyecto a la carpeta local deseada.
2. En Eclipse, seguir la siguiente ruta desde el menú: File -> Import -> Maven -> Existing Maven Projects -> Next
3. En la ventana emergente, click en el botón "Browse" y seleccionar la carpeta donde se desacargó el proyect.
4. Click derecho en en el proyecto en la pestaña "Project explorer" y seguir la siguiente ruta en el menú emergente Maven -> Update Project -> Ok
5. Click derecho en en el proyecto en la pestaña "Project explorer" y seguir la siguiente ruta en el menú emergente Run As -> Java Application -> Ok
6. En caso de salir la ventana para seleccionar la clase principal, elegir la clase "CardPaymentsApplication"

La aplicación se ejecutará en el puerto 8080 por defecto.

## Enlaces de interés en la aplicación
* Consola de H2 de la aplicación: http://localhost:8080/h2-console/
* Swagger: http://localhost:8080/swagger-ui.html

## Estructura general de la aplicación
![image](https://github.com/FerneyAMP/CardPaymentsApi/assets/32379263/fc6b4353-25e4-42ba-bc84-5746b84449d3)

## Estructura de la base de datos
![image](https://github.com/FerneyAMP/CardPaymentsApi/assets/32379263/55c0cd79-3f31-4237-8c55-33cfd9b9eb0b)

## Coverage de pruebas unitarias
![image](https://github.com/FerneyAMP/CardPaymentsApi/assets/32379263/82e9ae9e-e176-4414-ac4d-0306b8ff8cda)



  
