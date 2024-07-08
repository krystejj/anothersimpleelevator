package me.krystejj.ase;

import me.krystejj.ase.config.ConfigManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ASESounds {
    public static final Identifier ELEVATOR_USE = Identifier.of(ASE.MOD_ID, "block.elevator.use");
    public static final SoundEvent ELEVATOR_USE_SOUND_EVENT = SoundEvent.of(ELEVATOR_USE);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, ELEVATOR_USE, ELEVATOR_USE_SOUND_EVENT);
    }

    public static void playElevatorUse(PlayerEntity player) {
        if (!ConfigManager.config.playUseSound) return;

        player.getWorld().playSound(null,
                player.getBlockPos(), ELEVATOR_USE_SOUND_EVENT,
                SoundCategory.BLOCKS);
    }
}
