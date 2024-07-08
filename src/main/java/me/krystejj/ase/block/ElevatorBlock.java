package me.krystejj.ase.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ElevatorBlock extends Block {
    public static final DirectionProperty DIRECTION_PROPERTY = DirectionProperty.of("view_direction_after_tp");

    public ElevatorBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DIRECTION_PROPERTY, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION_PROPERTY);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // FIXME Wrong behavior when block is in offhand
        if (hand != Hand.MAIN_HAND || !player.getMainHandStack().isEmpty()
                || world.isClient) return ActionResult.PASS;

        // Calculate new direction
        int directionId = state.get(DIRECTION_PROPERTY).getId();
        if (directionId == 5) directionId = 2;
        else directionId++;

        // Set new direction
        Direction direction = Direction.byId(directionId);
        world.setBlockState(pos, state.with(DIRECTION_PROPERTY, direction), Block.NOTIFY_ALL);

        // Notify player about a new direction
        player.sendMessage(Text.translatableWithFallback("msg.ase.block.elevator.direction_set", "Set elevator direction to %s",
                Text.translatableWithFallback("ase.msg." + direction.asString(), direction.asString()).getString()), true);

        return ActionResult.SUCCESS;
    }
}
