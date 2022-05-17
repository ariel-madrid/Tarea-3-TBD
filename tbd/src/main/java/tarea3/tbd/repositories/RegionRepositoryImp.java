package tarea3.tbd.repositories;

import tarea3.tbd.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class RegionRepositoryImp implements RegionRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public int countRegions() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM division_regional").executeScalar(Integer.class);
        }
        return total;
    }

    /**
     * Se devuelve una lista con los atributos más importantes de una región.
     * En este caso, como existe una columna especial de geometría, para 
     * añadir esta región al mapa se pide que se utilice el método getJson().
     */
    @Override
    public List<Region> getAllRegions() {
        try(Connection conn = sql2o.open()){
            final String query = "SELECT nom_reg, cod_regi FROM division_regional GROUP BY nom_reg, cod_regi;";
            return conn.createQuery(query)
                    .executeAndFetch(Region.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Region createRegion(Region region) {
        // Creo que no es necesario crear la región
        return null;
    }

    /**
     * Obtiene un geoJSON de la tabla especial 'division_regional'
     * georeferenciada desde el campo 'geom' que es del tipo 'Multipolygon'
     * utilizando funciones de postGIS.
     */
    @Override
    public String getJson() {
        final String query = "SELECT json_build_object("+
            "'type', 'FeatureCollection',"+
            "'features', json_agg(ST_AsGeoJSON(t.geom)::json)"+
            ")"+
        "FROM division_regional AS t;";

        try(Connection conn = sql2o.open()){
            
            return conn.createQuery(query)
                    .executeAndFetch(String.class)
                    .get(0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
        
    }

    @Override
    public List<Dog> intersect(Dog dog) {
        try(Connection conn = sql2o.open()){
            
            String point = "POINT("+dog.getLongitude()+" "+dog.getLatitude()+")";
           
            String query = "SELECT d.id, d.name, st_x(st_astext( location)) AS longitude, st_y(st_astext(location)) AS latitude "+
                            "FROM division_regional as dr, dog as d  "+
                            "WHERE dr.cod_regi = "+dog.getCodRegi()+" and st_intersects(geom, ST_GeomFromText('"+point+"')) and d.id ="+dog.getId()+";";
            System.out.println(query);
            return conn.createQuery(query).executeAndFetch(Dog.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
