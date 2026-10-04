package me.ez.handytools;

import me.ez.handytools.block.FilterHopperBlock;
import me.ez.handytools.item.PathRollerItem;
import me.ez.handytools.item.SignalMeterItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** All Handy Tools registry entries. */
public final class Init {

    private Init() {}

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MOD_ID);

    public static final DeferredBlock<FilterHopperBlock> FILTER_HOPPER = BLOCKS.registerBlock(
            "filter_hopper", FilterHopperBlock::new,
            props -> props.mapColor(MapColor.STONE).strength(3.0f, 4.8f).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops().noOcclusion());

    public static final DeferredItem<net.minecraft.world.item.BlockItem> FILTER_HOPPER_ITEM =
            ITEMS.registerSimpleBlockItem("filter_hopper", FILTER_HOPPER);

    public static final DeferredItem<PathRollerItem> PATH_ROLLER = ITEMS.registerItem(
            "path_roller", PathRollerItem::new, props -> props.stacksTo(1));
    public static final DeferredItem<SignalMeterItem> SIGNAL_METER = ITEMS.registerItem(
            "signal_meter", SignalMeterItem::new, props -> props.stacksTo(1));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_TABS.register("handytools",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.handytools"))
                    .icon(() -> new ItemStack(FILTER_HOPPER_ITEM.get()))
                    .displayItems(Init::addTabContents)
                    .build());

    private static void addTabContents(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        output.accept(PATH_ROLLER.get());
        output.accept(SIGNAL_METER.get());
        output.accept(FILTER_HOPPER_ITEM.get());
    }
}
