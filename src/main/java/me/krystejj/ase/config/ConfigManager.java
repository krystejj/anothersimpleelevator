package me.krystejj.ase.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.krystejj.ase.ASE;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Files;

public class ConfigManager {
    protected static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
    public static Config config;
    private static File configFile;

    public static void init() {
        // Getting a config file path
        configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), ASE.MOD_ID + ".json");
        try {
            if (configFile.exists()) {
                // Read the config file if it exists
                ASE.LOGGER.info("Loading configuration...");
                config = GSON.fromJson(Files.readString(configFile.toPath()), Config.class);
            } else {
                // Create default config file if it doesn't exist
                ASE.LOGGER.info("Creating default configuration...");
                config = new Config();
                saveConfig();
            }
        } catch (Exception exception) {
            ASE.LOGGER.error("Failed to load configuration.", exception);
        }
    }

    public static void saveConfig() {
        try {
            // Write configuration to the file
            Files.writeString(configFile.toPath(), config.serialize());
        } catch (Exception exception) {
            ASE.LOGGER.error("Failed to save configuration.", exception);
        }
    }
}
