package me.krystejj.ase.util;

import me.krystejj.ase.config.ConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlayerUtils {
    public static boolean isStandingOnElevator(PlayerEntity player) {
        BlockState standingBlockState = getStandingBlockState(player);
        if (TagUtils.isBlockElevator(standingBlockState)) return true;
        else return ConfigManager.config.allowCarpetsOnElevator && TagUtils.isBlockCarpet(standingBlockState)
                && TagUtils.isBlockElevator(getStandingBlockState(player, -1));
    }

    public static boolean canTpToElevator(World world, BlockPos elevPos) {
        BlockState firstState = world.getBlockState(elevPos.up());
        BlockState secondState = world.getBlockState(elevPos.up(2));
        if (firstState.isOf(Blocks.AIR) && secondState.isOf(Blocks.AIR)) return true;
        else return ConfigManager.config.allowCarpetsOnElevator && TagUtils.isBlockCarpet(firstState)
                && secondState.isOf(Blocks.AIR);
    }

    public static BlockState getStandingBlockState(PlayerEntity player) {
        return getStandingBlockState(player, 0);
    }

    public static BlockState getStandingBlockState(PlayerEntity player, int yOffset) {
        yOffset -= 1;
        return player.getWorld().getBlockState(player.getBlockPos().add(0, yOffset, 0));
    }
}
