package com.fakeplayereventlogger.aurum;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

public class Config {
    private static final Properties properties = new Properties();
    private static final Path configDir = FabricLoader.getInstance().getConfigDir().resolve("carpet");
    private static final Path configPath = configDir.resolve("envetlogs.properties");

    static 
    {
        loadConfig();
    }

    private static void loadConfig() 
    {
        try {
            // Asegúrate de que el directorio existe
            Files.createDirectories(configDir);

            if (Files.exists(configPath)) {
                try (FileInputStream stream = new FileInputStream(configPath.toFile())) {
                    properties.load(stream);
                }
            } else {
                System.out.println("Config file does not exist, creating new one.");
                createDefaultConfig();
            }
        } catch (IOException e) {
            System.out.println("Failed to load config: " + e.getMessage());
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() 
    {
        properties.setProperty("bot_team_name", "zBot");
        properties.setProperty("server_host", "localhost");
        properties.setProperty("server_port", "25566");
        saveConfig();
    }

    private static void saveConfig() 
    {
        try {
            Files.createDirectories(configDir);
            try (FileOutputStream stream = new FileOutputStream(configPath.toFile())) {
                properties.store(stream, "EnvetLogs Mod Configuration");
            }
        } catch (IOException e) {
            System.out.println("Failed to save config: " + e.getMessage());
        }
    }

    public static String getBotTeamName() 
    {
        return properties.getProperty("bot_team_name", "zBot");
    }

    public static String getServerHost() 
    {
        return properties.getProperty("server_host", "localhost");
    }

    public static int getServerPort() 
    {
        return Integer.parseInt(properties.getProperty("server_port", "25566"));
    }
}