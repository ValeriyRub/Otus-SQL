import animals.Animal;
import birds.Flying;
import fabric.AnimalFabric;
import sqlcommand.AnimalService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Main {
   public static void main(String[] args) throws SQLException, IOException {
      Scanner scan = new Scanner(System.in);

      while (true) {
         System.out.println("Выберите команду:");
         System.out.println("add/update/list/filter/exit");
         String input = scan.next().toUpperCase().trim();

         CommandData command = CommandData.of(input);
         switch (command) {
            case ADD -> {
               String type;
               while (true) {
                  System.out.println("Введите животное из списка: cat/dog/duck");
                  type = scan.next().toLowerCase().trim();

                  if (!type.equals("cat") && !type.equals("dog") && !type.equals("duck")) {
                     System.out.println("Такого животного не существует. Повторите попытку.");
                  } else {
                     break;
                  }
               }

               String name;
               int age;
               int weight;
               String color;

               System.out.println("Введите имя животного:");
               name = scan.next();

               while (true) {
                  System.out.println("Введите возраст животного:");
                  try {
                     age = Integer.parseInt(scan.next());
                     if (age <= 0) {
                        System.out.println("Возраст должен быть положительным числом.");
                     } else break;
                  } catch (NumberFormatException e) {
                     System.out.println("Ошибка: возраст должен быть числом.");
                  }
               }

               while (true) {
                  System.out.println("Введите вес животного:");
                  try {
                     weight = Integer.parseInt(scan.next());
                     if (weight <= 0) {
                        System.out.println("Вес должен быть положительным числом.");
                     } else break;
                  } catch (NumberFormatException e) {
                     System.out.println("Ошибка: вес должен быть числом.");
                  }
               }

               System.out.println("Введите цвет животного:");
               color = scan.next();

               Animal animals = AnimalFabric.createAnimal(type, name, age, weight, color);
               if (animals == null) {
                  System.out.println("Ошибка создания животного.");
                  break;
               }

               animals.say();
               if (animals instanceof Flying flyingAnimal) {
                  flyingAnimal.fly();
               }

               // Сохраняем животное в базу данных
               AnimalService.saveAnimalToDB(name, age, weight, color, type);
               System.out.println("Животное добавлено в базу данных.");
            }

            case LIST -> AnimalService.displayAnimalsFromDB();
            case UPDATE ->
            {
               System.out.println("Введите ID животного, которое хотите обновить:");
               int id;
               while (true) {
                  try {
                     id = Integer.parseInt(scan.next());
                     break;
                  } catch (NumberFormatException e) {
                     System.out.println("Ошибка: ID должен быть числом.");
                  }
               }

               System.out.println("Введите новое имя:");
               String name = scan.next();

               int age;
               while (true) {
                  System.out.println("Введите новый возраст:");
                  try {
                     age = Integer.parseInt(scan.next());
                     if (age > 0) break;
                     else System.out.println("Возраст должен быть положительным.");
                  } catch (NumberFormatException e) {
                     System.out.println("Ошибка: возраст должен быть числом.");
                  }
               }

               int weight;
               while (true) {
                  System.out.println("Введите новый вес:");
                  try {
                     weight = Integer.parseInt(scan.next());
                     if (weight > 0) break;
                     else System.out.println("Вес должен быть положительным.");
                  } catch (NumberFormatException e) {
                     System.out.println("Ошибка: вес должен быть числом.");
                  }
               }

               System.out.println("Введите новый цвет:");
               String color = scan.next();

               AnimalService.updateAnimalInDB(id, name, age, weight, color);
            }

            case FILTER -> {
               System.out.println("Введите тип животного для фильтрации (cat/dog/duck):");
               String filterType = scan.next().toLowerCase().trim();

               if (!filterType.equals("cat") && !filterType.equals("dog") && !filterType.equals("duck")) {
                  System.out.println("Неверный тип животного.");
               } else {
                  AnimalService.displayAnimalsByType(filterType);
               }
            }

            case EXIT -> {
               System.out.println("Завершение программы.");
               System.exit(0);
            }

            default -> System.out.println("Неизвестная команда.");
         }
      }
   }
}
