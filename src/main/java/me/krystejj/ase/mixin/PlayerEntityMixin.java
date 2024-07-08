package me.krystejj.ase.mixin;

import me.krystejj.ase.block.ElevatorBlockFunctions;
import me.krystejj.ase.util.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "jump()V", cancellable = true)
    private void injected(CallbackInfo ci) {
        if (this.getWorld().isClient) return;
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (player.isInSneakingPose() || !PlayerUtils.isStandingOnElevator(player))
            return;

        ElevatorBlockFunctions.tpUp(player);
        ci.cancel();
    }
}
