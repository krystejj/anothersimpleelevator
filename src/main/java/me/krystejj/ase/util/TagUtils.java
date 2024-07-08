package me.krystejj.ase.util;

import me.krystejj.ase.ASE;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagUtils {
    public static boolean isBlockElevator(BlockState blockState) {
        return blockHasTag(blockState, Identifier.of(ASE.MOD_ID, "elevators"));
    }

    public static boolean isBlockCarpet(BlockState blockState) {
        return blockHasTag(blockState, Identifier.of(ASE.MOD_ID, "carpets"));
    }

    public static boolean blockHasTag(BlockState blockState, Identifier tagId) {
        return blockState.streamTags().toList().contains(TagKey.of(RegistryKeys.BLOCK, tagId));
    }
}
