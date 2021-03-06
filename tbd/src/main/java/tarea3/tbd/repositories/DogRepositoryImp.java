package tarea3.tbd.repositories;

import tarea3.tbd.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DogRepositoryImp implements DogRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public int countDogs() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM dog").executeScalar(Integer.class);
        }
        return total;
    }

    @Override
    public List<Dog> getAllDogs() {
        try(Connection conn = sql2o.open()){
            final String query = "SELECT id, name, st_x(st_astext( location)) AS longitude, st_y(st_astext(location)) AS latitude FROM dog;";
            return conn.createQuery(query)
                    .executeAndFetch(Dog.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Dog createDog(Dog dog) {
        try(Connection conn = sql2o.open()){
            String query = "INSERT INTO dog (id, name, location, latitude, longitude, cod_regi) " +
            "VALUES (:id, :dogName, ST_GeomFromText(:point, 4326), :latitude, :longitude, :cod_regi)";

            String point = "POINT("+dog.getLongitude()+" "+dog.getLatitude()+")";
            int insertedId = (int) conn.createQuery(query, true)
                    .addParameter("id", dog.getId())
                    .addParameter("dogName", dog.getName())
                    .addParameter("point", point)
                    .addParameter("latitude", dog.getLatitude())
                    .addParameter("longitude", dog.getLongitude())
                    .addParameter("cod_regi", dog.getCodRegi())
                    .executeUpdate().getKey();
            dog.setId(insertedId);
            return dog;    
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @Override
    public String getJson() {
        // TODO Auto-generated method stub
        final String query = "SELECT json_build_object("+
            "'type', 'FeatureCollection',"+
            "'features', json_agg(ST_AsGeoJSON(t.geom)::json)"+
            ")"+
        "FROM division_regional_4326 AS t WHERE t.gid = 5;";
        return null;
    }

    @Override
    public List<Dog> getNNearDogs(int dogId, int n) {
        final String dogQuery = "(SELECT * FROM dog WHERE id = " + dogId + ") as requested_dog ";
        final String query = "SELECT dog.id, dog.name, ST_Distance(requested_dog.location::geography, dog.location::geography) as distance " +
                             "FROM " + dogQuery + ", dog " +
                             "WHERE dog.id != requested_dog.id " +
                             "ORDER BY distance " + 
                             "LIMIT " + n + ";";

        try(Connection conn = sql2o.open()){
            return conn.createQuery(query)
                    .executeAndFetch(Dog.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Dog> getRNearDogs(Dog dog){
        
        try(Connection conn = sql2o.open()){
            
            String dogQuery = " (SELECT id, name, latitude, longitude, radio FROM dog) as tabla";

            String point = "("+dog.getLongitude()+", "+dog.getLatitude()+")";

            String query = "SELECT * FROM" + dogQuery + " "+
                                    " WHERE ST_DWithin("+ 
                                    " ST_MakePoint(longitude, latitude)::geography,"+
                                    " ST_MakePoint"+ point + "::geography,"+
                                    dog.getRadio() + 
                                    ") AND id != " + dog.getId() + ";";
            
            System.out.println(query);

            return conn.createQuery(query).executeAndFetch(Dog.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
