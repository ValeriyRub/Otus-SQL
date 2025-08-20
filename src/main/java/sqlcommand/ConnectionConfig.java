package sqlcommand;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionConfig {
    private static final String PROPERTIES_FILE = "myProperty.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream stream = ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE)) {
            if (stream == null) {
                throw new RuntimeException("Файл конфигурации не найден: " + PROPERTIES_FILE);
            }
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла свойств: " + e.getMessage(), e);
        }
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
}

