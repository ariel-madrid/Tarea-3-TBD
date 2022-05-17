package tarea3.tbd.repositories;
import java.util.List;
import tarea3.tbd.models.*;;

public interface DogRepository {
    public int countDogs();
    public List<Dog> getAllDogs();
    public Dog createDog(Dog dog);
    public String getJson();

    public List<Dog> getNNearDogs(int dogId, int n);
    public List<Dog> getRNearDogs(Dog dog, int r);
}
