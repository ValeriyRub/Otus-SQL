package sqlcommand;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class AnimalService {
    // Получение животных из базы
    public static void displayAnimalsFromDB() {
        Properties prop = new Properties();
        try (InputStream stream = ClassLoader.getSystemResourceAsStream("myProperty.properties")) {
            if (stream == null) {
                System.out.println("Файл конфигурации не найден.");
                return;
            }

            prop.load(stream);
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            String sqlQuery = "SELECT * FROM animal";

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(sqlQuery);

                System.out.printf("%-5s %-15s %-10s %-10s %-15s%n", "id", "name", "age", "weight", "color");
                System.out.println("--------------------------------------------------------");

                while (result.next()) {
                    System.out.printf("%-5s %-15s %-10s %-10s %-15s%n",
                            result.getString("id"),
                            result.getString("name"),
                            result.getString("age"),
                            result.getString("weight"),
                            result.getString("color"));
                }

            } catch (SQLException ex) {
                System.out.println("Ошибка при подключении к базе: " + ex.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла свойств: " + e.getMessage());
        }
    }
    // Добавление животного
    public static void saveAnimalToDB(String name, int age, int weight, String color, String type) {
        Properties prop = new Properties();
        try (InputStream stream = ClassLoader.getSystemResourceAsStream("myProperty.properties")) {
            if (stream == null) {
                System.out.println("Файл конфигурации не найден.");
                return;
            }

            prop.load(stream);
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            String insertQuery = "INSERT INTO animal (name, age, weight, color, type) VALUES (?, ?, ?, ?, ?)";

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement stmt = con.prepareStatement(insertQuery)) {
                    stmt.setString(1, name);
                    stmt.setInt(2, age);
                    stmt.setInt(3, weight);
                    stmt.setString(4, color);
                    stmt.setString(5, type);
                    stmt.executeUpdate();
                }

            } catch (SQLException ex) {
                System.out.println("Ошибка при добавлении животного: " + ex.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла свойств: " + e.getMessage());
        }
    }
    //Редактирование животного
    public static void updateAnimalInDB(int id, String name, int age, int weight, String color) {
        Properties prop = new Properties();
        try (InputStream stream = ClassLoader.getSystemResourceAsStream("myProperty.properties")) {
            if (stream == null) {
                System.out.println("Файл конфигурации не найден.");
                return;
            }

            prop.load(stream);
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            String updateQuery = "UPDATE animal SET name = ?, age = ?, weight = ?, color = ? WHERE id = ?";

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
                    stmt.setString(1, name);
                    stmt.setInt(2, age);
                    stmt.setInt(3, weight);
                    stmt.setString(4, color);
                    stmt.setInt(5, id);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Животное успешно обновлено.");
                    } else {
                        System.out.println("Животное с таким ID не найдено.");
                    }
                }

            } catch (SQLException ex) {
                System.out.println("Ошибка при обновлении животного: " + ex.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла свойств: " + e.getMessage());
        }
    }
    // Фильтр животных
    public static void displayAnimalsByType(String type) {
        Properties prop = new Properties();
        try (InputStream stream = ClassLoader.getSystemResourceAsStream("myProperty.properties")) {
            if (stream == null) {
                System.out.println("Файл конфигурации не найден.");
                return;
            }

            prop.load(stream);
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            String sqlQuery = "SELECT * FROM animal WHERE type = ?";

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement stmt = con.prepareStatement(sqlQuery)) {
                    stmt.setString(1, type);
                    ResultSet result = stmt.executeQuery();

                    System.out.printf("%-5s %-15s %-10s %-10s %-15s %-10s%n", "id", "name", "age", "weight", "color", "type");
                    System.out.println("---------------------------------------------------------------------");

                    boolean found = false;
                    while (result.next()) {
                        found = true;
                        System.out.printf("%-5s %-15s %-10s %-10s %-15s %-10s%n",
                                result.getString("id"),
                                result.getString("name"),
                                result.getString("age"),
                                result.getString("weight"),
                                result.getString("color"),
                                result.getString("type"));
                    }

                    if (!found) {
                        System.out.println("Животные этого типа не найдены.");
                    }
                }

            } catch (SQLException ex) {
                System.out.println("Ошибка при подключении к базе: " + ex.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла свойств: " + e.getMessage());
        }
    }

}

