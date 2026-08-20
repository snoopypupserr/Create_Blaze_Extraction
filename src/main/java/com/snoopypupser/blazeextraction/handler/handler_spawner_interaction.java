package com.snoopypupser.blazeextraction.handler;

import com.snoopypupser.blazeextraction.inventory.spawner_inventory_holder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.ItemStackHandler;

public class handler_spawner_interaction {

    private static Item cachedEmptyBurner;
    private static Item cachedFilledBurner;

    private static Item getEmptyBurner() {
        if (cachedEmptyBurner == null) {
            cachedEmptyBurner = BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("create", "empty_blaze_burner"));
        }
        return cachedEmptyBurner;
    }

    private static Item getFilledBurner() {
        if (cachedFilledBurner == null) {
            cachedFilledBurner = BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath("create", "blaze_burner"));
        }
        return cachedFilledBurner;
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        Item emptyBurner = getEmptyBurner();
        if (emptyBurner == null || emptyBurner == Items.AIR || !stack.is(emptyBurner)) {
            return;
        }

        BlockPos pos = event.getPos();
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof SpawnerBlockEntity spawnerBE)) {
            return;
        }

        if (!(level.getBlockState(pos).getBlock() instanceof SpawnerBlock)) {
            return;
        }

        CompoundTag spawnerTag = new CompoundTag();
        spawnerBE.getSpawner().save(spawnerTag);
        String entityId = spawnerTag
                .getCompound("SpawnData")
                .getCompound("entity")
                .getString("id");
        if (!"minecraft:blaze".equals(entityId)) {
            return;
        }

        if (!(be instanceof spawner_inventory_holder holder)) {
            return;
        }

        Item filledItem = getFilledBurner();
        if (filledItem == null || filledItem == Items.AIR) {
            return;
        }

        Player player = event.getEntity();
        boolean isDeployer = player instanceof FakePlayer;

        if (isDeployer) {
            ItemStack filledBurner = new ItemStack(filledItem);
            ItemStackHandler inventory = holder.blazeExtraction$getInventory();

            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack testInsert = inventory.insertItem(i, filledBurner.copy(), true);
                if (testInsert.isEmpty()) {
                    stack.shrink(1);
                    inventory.insertItem(i, filledBurner.copy(), false);
                    player.setItemInHand(event.getHand(), stack);

                    spawnerBE.setChanged();
                    level.playSound(null, pos, SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS,
                            0.02f, 1.2f);
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.5;
                    double z = pos.getZ() + 0.5;
                    for (int p = 0; p < 8; p++) {
                        double dx = (level.random.nextDouble() - 0.5) * 0.8;
                        double dy = level.random.nextDouble() * 0.6;
                        double dz = (level.random.nextDouble() - 0.5) * 0.8;
                        ((ServerLevel) level).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x + dx, y + dy, z + dz, 1, 0.0, 0.02, 0.0, 0.01);
                    }
                    ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, x, y + 0.6, z, 4, 0.15, 0.1, 0.15, 0.005);
                    event.setCanceled(true);
                    return;
                }
            }
        } else {
            ItemStack filledBurner = new ItemStack(filledItem);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            player.setItemInHand(event.getHand(), filledBurner);

            level.playSound(null, pos, SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS,
                    0.02f, 1.2f);
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            for (int p = 0; p < 8; p++) {
                double dx = (level.random.nextDouble() - 0.5) * 0.8;
                double dy = level.random.nextDouble() * 0.6;
                double dz = (level.random.nextDouble() - 0.5) * 0.8;
                ((ServerLevel) level).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x + dx, y + dy, z + dz, 1, 0.0, 0.02, 0.0, 0.01);
            }
            ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, x, y + 0.6, z, 4, 0.15, 0.1, 0.15, 0.005);
            event.setCanceled(true);
        }
    }
}
