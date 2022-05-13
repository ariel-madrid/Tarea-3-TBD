package cl.tbd.ejemplo1.repositories;
import java.util.List;
import cl.tbd.ejemplo1.models.Dog;

public interface RegionRepository {
    public int countRegions();
    public List<Region> getAllRegions();
    public Dog createRegion(Region region);
    public String getJson();
}
