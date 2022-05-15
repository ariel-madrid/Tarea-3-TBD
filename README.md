# Tarea-3-TBD
# IMPORTANTE
# Para poder utilizar la aplicación para la tarea 3 correctamente, se debe tener:

- postGIS y postgreSQL (misma versión para ambos) 
- correr el script que crea las tablas necesarias
- correr el siguiente comando en postgreSQL luego de correr el script de las tablas: <code>
SELECT UpdateGeometrySRID('division_regional', 'geom', 3857);</code> de forma que el SRID de Leaflet y la tabla dentro de la base de datos sea coherente.
