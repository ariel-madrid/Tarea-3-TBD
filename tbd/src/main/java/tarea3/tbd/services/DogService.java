package tarea3.tbd.services;
import tarea3.tbd.repositories.DogRepository;
import tarea3.tbd.models.*;

import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class DogService {

    private DogRepository dogRepository;

    DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        System.out.println("getAllDogs()");
        try {
            List<Dog> dogs = dogRepository.getAllDogs();
            System.out.println("1");
            for (Dog d : dogs) {
                System.out.println("ID: " + d.getId());
                /*PGgeometry geom = d.getLocation();
                Geometry geometry = geom.getGeometry();
                
                System.out.println("Point: "+ geometry.getValue());*/
            }
            System.out.println("2");
            return dogs;
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
            return null;
        }
         
    }

    @GetMapping("/dogs/count")
    public String countDogs(){
        int total = dogRepository.countDogs();
        return String.format("Tienes %s perros!!", total);
    }

    @PostMapping("/dogs")
    @ResponseBody
    public Dog createDog(@RequestBody Dog dog){
        Dog result = dogRepository.createDog(dog);
        return result;
    }


    /**
     * Obtener los N perros más cercanos a un perro seleccionado.
     * @return
     */
    @GetMapping("/dogs/nneardogs")
    public List<Dog> getNNearDogs(@RequestParam int dogId, 
                                  @RequestParam int n){

        return dogRepository.getNNearDogs(dogId, n);
    }
}
