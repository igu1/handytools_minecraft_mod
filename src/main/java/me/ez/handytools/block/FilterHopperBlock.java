package me.ez.handytools.block;

import me.ez.handytools.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * A hopper that only accepts one kind of item. Right-click with an item to set the
 * filter, sneak + right-click with an empty hand to clear it, and right-click with an
 * empty hand to open the hopper's inventory.
 */
public class FilterHopperBlock extends HopperBlock {

    public FilterHopperBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FilterHopperBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!Config.ENABLED.get() || !Config.FILTER_HOPPER_ENABLED.get()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        if (!stack.isEmpty() && level.getBlockEntity(pos) instanceof FilterHopperBlockEntity hopper) {
            if (!level.isClientSide()) {
                hopper.setFilter(stack);
                if (level instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.WAX_ON, pos.getX() + 0.5,
                            pos.getY() + 0.9, pos.getZ() + 0.5, 5, 0.2, 0.1, 0.2, 0.01);
                }
                player.sendOverlayMessage(Component.translatable("handytools.filter_hopper.set", stack.getHoverName()));
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (Config.ENABLED.get() && Config.FILTER_HOPPER_ENABLED.get() && player.isShiftKeyDown()
                && level.getBlockEntity(pos) instanceof FilterHopperBlockEntity hopper) {
            if (!level.isClientSide()) {
                hopper.clearFilter();
                player.sendOverlayMessage(Component.translatable("handytools.filter_hopper.cleared"));
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}
