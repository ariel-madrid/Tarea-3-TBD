# Tarea-3-TBD
# IMPORTANTE
# Para poder utilizar la aplicación para la tarea 3 correctamente, se debe tener:

- postGIS y postgreSQL (misma versión para ambos) 
- correr el script que crea las tablas necesarias
- correr el siguiente comando en postgreSQL luego de correr el script de las tablas: <code>
SELECT UpdateGeometrySRID('division_regional', 'geom', 3857);</code> de forma que el SRID de Leaflet y la tabla dentro de la base de datos sea coherente.

Integrantes:

Ariel Argomedo
Israel Guzman
Ramon Parra
Nicolas Henriquez
Marcelo Guzman
Bryan Salgado
Sistema Operativo: Windows 10 PRO Editor de codigo: Visual studio code

Pasos para utilizar la App Web:
<ol>
    <li>En la carpeta del repositorio llamada Tarea-3-TBD se encontraran dos carpetas llamadas tarea3 y tbd. La carpeta tarea3 corresponde al front desarrollo mediante vue y tbd al back desarrollado utilizando JAVA, es por esto que para que ambas cosas funcionen se debe tener instalado JDK version 11 para JAVA y Node para el front</li>
    <li>En la carpeta del respositorio Tarea-3-TBD se ingresa a la carpeta tarea3 y en consola se digita el siguiente comando:<strong>npm install</strong>. Una vez ejecutado el comando anterior, se levanta el front con el comando<strong>npm run dev</strong>en el puerto http://localhost:3000/ y se podra utilizar el front de la entrega.
En la carpeta del respositorio labTBD-v2 se ingresa a la carpeta labTBD y en consola se digita el siguiente comando:
./gradlew bootRun
para que se levante el backend en el puerto http://localhost:8080/ que hace uso de la base de datos.
Con estos pasos quedaria listo para poder interactuar con la App Web.
Los commits asociados a cada integrante del grupo se pueden observar en la presentacion entregada. Se crea este nuevo respositorio por un tema de formalidad mas que nada y mantener un orden en las entregas.
</ol>
