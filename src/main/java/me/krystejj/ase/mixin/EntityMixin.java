package me.krystejj.ase.mixin;

import me.krystejj.ase.block.ElevatorBlockFunctions;
import me.krystejj.ase.util.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow private World world;

    @Shadow
    public abstract boolean isPlayer();

    @Shadow
    public abstract boolean isOnGround();

    @Inject(at = @At("HEAD"), method = "setSneaking(Z)V")
    public void setSneaking(boolean sneaking, CallbackInfo ci) {
        /* Abandon if:
           - is a client,
           - entity isn't a player,
           - player isn't sneaking,
           - player isn't on the ground,
           - player isn't standing on elevator */
        if (this.world.isClient() || !this.isPlayer() || !sneaking || !this.isOnGround()) return;
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (!PlayerUtils.isStandingOnElevator(player)) return;

        ElevatorBlockFunctions.tpDown(player);
    }
}
