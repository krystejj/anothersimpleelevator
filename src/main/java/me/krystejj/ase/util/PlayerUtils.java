package me.krystejj.ase.util;

import me.krystejj.ase.config.ConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlayerUtils {
    public static boolean isStandingOnElevator(PlayerEntity player) {
        BlockState standingBlockState = player.getSteppingBlockState();
        BlockState lowerBlockState = player.getWorld().getBlockState(player.getSteppingPos().down());
        if (TagUtils.isBlockElevator(standingBlockState)) return true;
        else
            return ConfigManager.config.allowCarpetsOnElevator && TagUtils.isBlockCarpet(standingBlockState) && TagUtils.isBlockElevator(lowerBlockState);
    }

    public static boolean canTpToElevator(World world, BlockPos elevPos) {
        BlockState elevatorState = world.getBlockState(elevPos.up());
        BlockState secondBlockState = world.getBlockState(elevPos.up(2));
        if (elevatorState.isOf(Blocks.AIR) && secondBlockState.isOf(Blocks.AIR)) return true;
        else
            return ConfigManager.config.allowCarpetsOnElevator && TagUtils.isBlockCarpet(elevatorState) && secondBlockState.isOf(Blocks.AIR);
    }
}
