package me.krystejj.ase.item;

import me.krystejj.ase.ASE;
import me.krystejj.ase.block.ASEBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class ASEItems {
    public static Map<String, BlockItem> elevatorBlockItems = new LinkedHashMap<>();

    public static void register() {
        for (Map.Entry<String, Block> entry : ASEBlocks.elevatorBlocks.entrySet()) {
            BlockItem blockItem = new BlockItem(entry.getValue(), new BlockItem.Settings());
            elevatorBlockItems.put(entry.getKey(), blockItem);
            Registry.register(Registries.ITEM, Identifier.of(ASE.MOD_ID, entry.getKey()), blockItem);
        }
    }
}
