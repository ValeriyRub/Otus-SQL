package animals;

import java.util.Set;

public abstract class Animal {

    private String name;
    private int age;
    private int weight;
    private String color;


    public Animal(String name, int age, int weight, String color) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.color = color;
    }

    public void say() {
        System.out.println("Я говорю");
    }

    public void go() {
        System.out.println("Я иду");
    }

    public void Drink() {
        System.out.println("Я пью");
    }

    public void Eat() {
        System.out.println("Я ем");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return String.format("Привет! меня зовут " + name + ", мне " + age + " " + years() + ", я вешу - " + weight + " кг, мой цвет - " + color);
    }

    private String years() {
        if (age >= 11 && age <= 14) {
            return "лет";
        }
        int ostatok = age % 10;
        if(ostatok == 0) {
            return "лет";
        }
        if(ostatok == 1) {
            return "год";
        }

        if(ostatok >= 2 && ostatok <= 4){
            return "года";
        }

        return "лет";
    }
}