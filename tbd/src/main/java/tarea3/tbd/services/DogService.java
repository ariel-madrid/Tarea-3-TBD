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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DogService {

    private DogRepository dogRepository;

    DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        try {
            List<Dog> dogs = dogRepository.getAllDogs();
            for (Dog d : dogs) {
                /*PGgeometry geom = d.getLocation();
                Geometry geometry = geom.getGeometry();
                
                System.out.println("Point: "+ geometry.getValue());*/
            }
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

    /* @PostMapping("/nuevodog")
    @ResponseBody
    public Dog createDog(@RequestBody Dog dog){
        Dog result = dogRepository.createDog(dog);
        return result;
    } */

     //Crear una nueva habilidad (Metodo POST)
     @RequestMapping(value = "/nuevodog", method=RequestMethod.POST)
     public Dog createDog(@RequestBody Dog dog) 
     {  
        System.out.println(dog.getName());
        return dogRepository.createDog(dog);  
     }

    /**
     * Obtener los N perros más cercanos a un perro seleccionado.
     * @return
     */
    @GetMapping("/dogs/nneardogs")
    public List<Dog> getNNearDogs(@RequestParam("dogId") int dogId, 
                                  @RequestParam("n") int n){
        
        return dogRepository.getNNearDogs(dogId, n);
    }

    /**
     * Obtener los N perros más cercanos a un perro seleccionado.
     * @return
     */
    @PostMapping(value = "/dogs/rneardogs")
    @ResponseBody
    public List<Dog> getRNearDogs(@RequestBody Dog dog){
        
        try {
            List<Dog> value = dogRepository.getRNearDogs(dog);
            return value;
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
            return null;
        }
    }
        
                                    
}
