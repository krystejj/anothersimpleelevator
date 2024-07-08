package me.krystejj.ase.block;

import me.krystejj.ase.ASESounds;
import me.krystejj.ase.config.ConfigManager;
import me.krystejj.ase.util.PlayerUtils;
import me.krystejj.ase.util.TagUtils;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ElevatorBlockFunctions {
    private static final Map<Direction, Integer> directionsYaw = new HashMap<>(Map.of(
            Direction.NORTH, 180,
            Direction.SOUTH, 0,
            Direction.WEST, 90,
            Direction.EAST, 270
    ));

    public static void tpUp(ServerPlayerEntity player) {
        // Iterate through higher blocks in allowed range
        BlockPos playerBlockPos = player.getBlockPos();
        for (int y = playerBlockPos.getY(); y <= playerBlockPos.getY() + ConfigManager.config.range - 1; y++)
            if (checkPosForElevator(y, player.getWorld(), playerBlockPos, player)) return;

        // Notify a player that there isn't any elevator above him
        if (!ConfigManager.config.elevatorNotFoundMsg) return;
        player.sendMessage(Text.translatableWithFallback("msg.ase.block.elevator.elevator_not_found_up",
                String.format("There isn't any elevator above you in range of %d blocks", ConfigManager.config.range),
                ConfigManager.config.range).formatted(Formatting.RED), true);
    }

    public static void tpDown(ServerPlayerEntity player) {
        // Iterate through lower blocks in allowed range
        BlockPos playerBlockPos = player.getBlockPos().down(2);
        for (int y = playerBlockPos.getY(); y >= playerBlockPos.getY() - ConfigManager.config.range + 1; y--)
            if (checkPosForElevator(y, player.getWorld(), playerBlockPos, player)) return;

        // Notify a player that there isn't any elevator under him
        if (!ConfigManager.config.elevatorNotFoundMsg) return;
        player.sendMessage(Text.translatableWithFallback("msg.ase.block.elevator.elevator_not_found_down",
                String.format("There isn't any elevator under you in range of %d blocks", ConfigManager.config.range),
                ConfigManager.config.range).formatted(Formatting.RED), true);
    }

    private static boolean checkPosForElevator(int y, World world, BlockPos playerBlockPos, ServerPlayerEntity player) {
        // Get block
        BlockPos nextElevPos = playerBlockPos.withY(y);
        BlockState nextElevState = world.getBlockState(nextElevPos);

        // Iterate further if the current block isn't an elevator
        if (!TagUtils.isBlockElevator(nextElevState)) return false;

        // Check if there is a space for player to teleport
        if (!PlayerUtils.canTpToElevator(world, nextElevPos)) {
            // Notify a player that there is no space to teleport him
            player.sendMessage(Text.translatableWithFallback("msg.ase.block.elevator.no_space_to_tp",
                    "There is no space above next elevator to teleport you").formatted(Formatting.RED), true);
        } else tpToElevator(player, nextElevPos, nextElevState);
        return true;
    }

    private static void tpToElevator(ServerPlayerEntity player, BlockPos elevPos, BlockState elevState) {
        // Calculate y coordinate for teleportation
        double y = elevPos.getY() + 1;
        if (TagUtils.isBlockCarpet(player.getWorld().getBlockState(elevPos.up()))) y += 0.06250d; // Carpet height

        // Teleport a player
        player.inTeleportationState = true;
        player.teleport(player.getServerWorld(), elevPos.getX() + 0.5d, y, elevPos.getZ() + 0.5d,
                directionsYaw.get(elevState.get(ElevatorBlock.DIRECTION_PROPERTY)),
                ConfigManager.config.centerPitchOnTp ? 0f : player.getPitch());

        // Play sound
        ASESounds.playElevatorUse(player);
    }
}
