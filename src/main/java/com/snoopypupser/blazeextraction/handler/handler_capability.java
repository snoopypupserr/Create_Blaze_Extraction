package com.snoopypupser.blazeextraction.handler;

import com.snoopypupser.blazeextraction.inventory.spawner_inventory_holder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

public class handler_capability {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                net.minecraft.world.level.block.entity.BlockEntityType.MOB_SPAWNER,
                (SpawnerBlockEntity blockEntity, @Nullable Direction side) -> {
                    if (blockEntity instanceof spawner_inventory_holder holder) {
                        return holder.blazeExtraction$getInventory();
                    }
                    return null;
                }
        );
    }
}
