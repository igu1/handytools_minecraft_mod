package me.ez.handytools.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Hopper block entity that only accepts items matching its configured filter. */
public class FilterHopperBlockEntity extends HopperBlockEntity {

    private ItemStack filter = ItemStack.EMPTY;

    public FilterHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public void setFilter(ItemStack stack) {
        filter = stack.copyWithCount(1);
        setChanged();
    }

    public void clearFilter() {
        filter = ItemStack.EMPTY;
        setChanged();
    }

    public ItemStack filter() {
        return filter;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (filter.isEmpty()) {
            return super.canPlaceItem(slot, stack);
        }
        return ItemStack.isSameItem(filter, stack) && super.canPlaceItem(slot, stack);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("Filter", ItemStack.OPTIONAL_CODEC, filter);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        filter = input.read("Filter", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
    }
}
