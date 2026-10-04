package me.ez.handytools.item;

import me.ez.handytools.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/** Reads the redstone signal at the targeted block. */
public class SignalMeterItem extends Item {

    public SignalMeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!Config.ENABLED.get() || !Config.SIGNAL_METER_ENABLED.get()) {
            return InteractionResult.PASS;
        }
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        int received = level.getBestNeighborSignal(pos);
        int emitted = level.getSignal(pos, face);
        boolean powered = received > 0;

        player.sendOverlayMessage(Component.translatable("handytools.signal_meter.reading",
                received, emitted, powered ? Component.translatable("handytools.signal_meter.on")
                        : Component.translatable("handytools.signal_meter.off")));
        return InteractionResult.SUCCESS_SERVER;
    }
}
