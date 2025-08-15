package fabric;

import animals.Animal;
import birds.Duck;
import pets.Cat;
import pets.Dog;


public class AnimalFabric {
    public static Animal createAnimal (String type, String name, int age, int weight, String color){
        return switch (type){
            case "cat" -> new Cat(name, age, weight, color);
            case "dog" -> new Dog(name, age, weight, color);
            case "duck" -> new Duck(name, age, weight, color);
            default -> null;
        };
    }

}

