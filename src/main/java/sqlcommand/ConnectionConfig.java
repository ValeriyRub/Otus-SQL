package sqlcommand;

public class ConnectionConfig {

    public static String getUrl() {
        String url = System.getenv("url");
        if (url == null) {
            throw new RuntimeException("System property 'url' не задана");
        }
        return url;
    }

    public static String getUsername() {
        String username = System.getenv("username");
        if (username == null) {
            throw new RuntimeException("System property 'username' не задана");
        }
        return username;
    }

    public static String getPassword() {
        String password = System.getenv("password");
        if (password == null) {
            throw new RuntimeException("System property 'password' не задана");
        }
        return password;
    }
}

