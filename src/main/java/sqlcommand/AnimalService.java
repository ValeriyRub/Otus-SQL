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

            String sqlQuery = "SELECT * FROM Animal";

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(sqlQuery);

                System.out.printf("%-5s %-15s %-10s %-10s %-15s%n", "id", "name", "age", "weight", "color");
                System.out.println("--------------------------------------------------------------");

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
    public static void saveAnimalToDB(String name, int age, int weight, String color) {
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

            String insertQuery = "INSERT INTO Animal (name, age, weight, color) VALUES (?, ?, ?, ?)";

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement stmt = con.prepareStatement(insertQuery)) {
                    stmt.setString(1, name);
                    stmt.setInt(2, age);
                    stmt.setInt(3, weight);
                    stmt.setString(4, color);
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

            String updateQuery = "UPDATE Animal SET name = ?, age = ?, weight = ?, color = ? WHERE id = ?";

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
}

