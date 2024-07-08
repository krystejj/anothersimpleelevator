package me.krystejj.ase;

import me.krystejj.ase.block.ASEBlocks;
import me.krystejj.ase.config.ConfigManager;
import me.krystejj.ase.item.ASEItemGroups;
import me.krystejj.ase.item.ASEItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ASE implements ModInitializer {
    public static String MOD_ID = "ase";
    public static String MOD_NAME = "AnotherSimpleElevator";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        ConfigManager.init();

        ASEBlocks.register();
        ASEItems.register();
        ASEItemGroups.register();
        ASESounds.register();

        LOGGER.info("Hello from {}, initialized!", MOD_NAME);
    }
}
