package tarea3.tbd.repositories;
import java.util.List;
import tarea3.tbd.models.*;;

public interface RegionRepository {
    public int countRegions();
    public List<Region> getAllRegions();
    public Dog createRegion(Region region);
    public String getJson();
}
