package me.krystejj.ase.item;

import me.krystejj.ase.ASE;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ASEItemGroups {
    public static final ItemGroup ELEVATORS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ASEItems.elevatorBlockItems.get("white_elevator")))
            .displayName(Text.translatableWithFallback("itemGroup.ase.elevators", "Elevators"))
            .entries((context, entries) -> {
                for (BlockItem blockItem : ASEItems.elevatorBlockItems.values()) {
                    entries.add(blockItem);
                }
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(ASE.MOD_ID, "elevators"), ELEVATORS);
    }
}
