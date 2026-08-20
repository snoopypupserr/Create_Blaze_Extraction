package com.snoopypupser.blazeextraction.mixin;

import com.snoopypupser.blazeextraction.inventory.spawner_inventory_holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnerBlockEntity.class)
public abstract class spawner_block_entity_mixin implements spawner_inventory_holder {

    @Unique
    private ItemStackHandler blazeExtraction$inventory;

    @Unique
    private ItemStackHandler blazeExtraction$getOrCreateInventory() {
        if (blazeExtraction$inventory == null) {
            SpawnerBlockEntity self = (SpawnerBlockEntity) (Object) this;
            blazeExtraction$inventory = new ItemStackHandler(1) {
                @Override
                protected void onContentsChanged(int slot) {
                    self.setChanged();
                }
            };
        }
        return blazeExtraction$inventory;
    }

    @Override
    public ItemStackHandler blazeExtraction$getInventory() {
        return blazeExtraction$getOrCreateInventory();
    }

    @Inject(method = "saveAdditional", at = @At("HEAD"))
    private void blazeExtraction$saveInventory(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (blazeExtraction$inventory != null) {
            tag.put("BlazeExtractionInventory", blazeExtraction$inventory.serializeNBT(registries));
        }
    }

    @Inject(method = "loadAdditional", at = @At("HEAD"))
    private void blazeExtraction$loadInventory(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (tag.contains("BlazeExtractionInventory")) {
            blazeExtraction$getOrCreateInventory().deserializeNBT(registries, tag.getCompound("BlazeExtractionInventory"));
        }
    }
}
