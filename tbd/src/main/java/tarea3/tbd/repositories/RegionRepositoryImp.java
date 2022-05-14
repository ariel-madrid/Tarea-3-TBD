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

    @Override
    public List<Region> getAllRegions() {
        try(Connection conn = sql2o.open()){
            final String query = "SELECT gid, nom_reg, nom_prov, nom_com, superficie FROM division_regional;";
            return conn.createQuery(query)
                    .executeAndFetch(Region.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Region createRegion(Region region) {
        // try(Connection conn = sql2o.open()){
        //     String query = "INSERT INTO DOG (name, location) " +
        //     "VALUES (:dogName, ST_GeomFromText(:point, 4326))";

        //     String point = "POINT("+dog.getLongitude()+" "+dog.getLatitude()+")";
        //     System.out.println("point: "+point);
            
        //     int insertedId = (int) conn.createQuery(query, true)
        //             .addParameter("dogName", dog.getName())
        //             .addParameter("point", point)
        //             .executeUpdate().getKey();
        //     dog.setId(insertedId);
        //     return dog;        
        // }catch(Exception e){
        //     System.out.println(e.getMessage());
        //     return null;
        // }
        return null;
    }

    @Override
    public String getJson() {
        // TODO Auto-generated method stub
        return null;
    }


}
