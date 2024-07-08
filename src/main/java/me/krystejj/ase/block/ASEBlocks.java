package me.krystejj.ase.block;

import me.krystejj.ase.ASE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class ASEBlocks {
    public static Map<String, Block> elevatorBlocks = new LinkedHashMap<>();

    public static void register() {
        for (DyeColor dyeColor : DyeColor.values()) {
            String id = dyeColor.getName() + "_elevator";
            Block block = new ElevatorBlock(FabricBlockSettings.create()
                    .mapColor(dyeColor)
                    .requiresTool()
                    .strength(3f, 5f)
                    .sounds(BlockSoundGroup.WOOL));
            elevatorBlocks.put(id, block);
            Registry.register(Registries.BLOCK, Identifier.of(ASE.MOD_ID, id), block);
        }
    }
}
