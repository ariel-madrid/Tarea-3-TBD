package tarea3.tbd.services;

import tarea3.tbd.repositories.DogRepository;
import tarea3.tbd.repositories.RegionRepository;
import tarea3.tbd.models.*;

import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RegionService {

    private RegionRepository regionRepository;
    private DogRepository dogRepository;
    RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @RequestMapping(value = "/regions", method = RequestMethod.GET)
    public List<Region> getAllRegions() {
        System.out.println("getAllRegions()");
        try {
            List<Region> regions = regionRepository.getAllRegions();
            //System.out.println("1");
            //for (Region r : regions) {
            //    System.out.println("ID: " + r.getId());
            //}
            //System.out.println("2");
            return regions;
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
            return null;
        }

    }

    @GetMapping("/regions/count")
    public String countRegions() {
        int total = regionRepository.countRegions();
        return String.format("Existen %s regiones", total);
    }

    @PostMapping("/regions/create")
    @ResponseBody
    public Region createRegion(@RequestBody Region region) {
        Region result = regionRepository.createRegion(region);
        return result;
    }


    @PostMapping(value = "/intersection")
    @ResponseBody
    public List<Dog> getIntersection(@RequestBody Dog dog){
        try {
            List<Dog> value = regionRepository.intersect(dog);
            return value;
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
            return null;
        }

    }

}