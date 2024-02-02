package io.platformengineer.util;
import io.github.cdimascio.dotenv.Dotenv;

public class ConfigManager {

    private static Dotenv dotenv = Dotenv.load();

    public static String getEnvVariable(String key, String defaultValue) {
        // Try to get the value from environment variables first
        String value = System.getenv(key);

        if (value == null || value.isEmpty()) {
            // If not found, try to get it from the .env file
            value = dotenv.get(key);
        }

        return value != null ? value : defaultValue;
    }
}
