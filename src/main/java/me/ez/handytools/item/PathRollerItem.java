package me.ez.handytools.item;

import me.ez.handytools.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Lays a plane of dirt paths on top of solid ground. */
public class PathRollerItem extends Item {

    public PathRollerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!Config.ENABLED.get() || !Config.PATH_ROLLER_ENABLED.get()) {
            return InteractionResult.PASS;
        }
        Level level = context.getLevel();
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockPos base = context.getClickedPos().above();
        int halfWidth = Config.PATH_ROLLER_WIDTH.get();
        int halfDepth = Config.PATH_ROLLER_DEPTH.get();
        int placed = 0;

        for (int dx = -halfWidth; dx <= halfWidth; dx++) {
            for (int dz = -halfDepth; dz <= halfDepth; dz++) {
                BlockPos pos = base.offset(dx, 0, dz);
                BlockPos below = pos.below();
                BlockState belowState = level.getBlockState(below);
                if (!level.getBlockState(pos).isAir()
                        || !belowState.isFaceSturdy(level, below, Direction.UP)) {
                    continue;
                }
                level.setBlockAndUpdate(pos, Blocks.DIRT_PATH.defaultBlockState());
                placed++;
            }
        }
        return placed > 0 ? InteractionResult.SUCCESS_SERVER : InteractionResult.PASS;
    }
}
