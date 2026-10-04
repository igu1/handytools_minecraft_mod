package me.ez.handytools;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "handytools";

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        Init.BLOCKS.register(modEventBus);
        Init.CREATIVE_TABS.register(modEventBus);
        Init.ITEMS.register(modEventBus);
        modEventBus.addListener(this::addValidBlocks);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * The Filter Hopper reuses the vanilla hopper block entity type (its block entity
     * subclasses {@link net.minecraft.world.level.block.entity.HopperBlockEntity}),
     * so its block has to be added to the hopper's valid-block set.
     */
    private void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.HOPPER, Init.FILTER_HOPPER.get());
    }
}
