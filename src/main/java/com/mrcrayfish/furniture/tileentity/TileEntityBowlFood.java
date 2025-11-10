package com.mrcrayfish.furniture.tileentity;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class TileEntityBowlFood extends TileEntity implements ITickable {
    @Override
    public void update() {
        if (!world.isRemote) {
            feedNearbyPets();
        }
    }

    private void feedNearbyPets() {
        AxisAlignedBB area = new AxisAlignedBB(pos).grow(1.0D, 1.0D, 1.0D);

        List<EntityWolf> wolves = world.getEntitiesWithinAABB(EntityWolf.class, area);
        for (EntityWolf wolf : wolves) {
            if (wolf.getHealth() < wolf.getMaxHealth()) {
                wolf.heal(4.0F);
                world.playSound(null, wolf.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.5F, 1.3F);
            }
        }

        List<EntityOcelot> cats = world.getEntitiesWithinAABB(EntityOcelot.class, area);
        for (EntityOcelot cat : cats) {
            if (cat.getHealth() < cat.getMaxHealth()) {
                cat.heal(4.0F);
                world.playSound(null, cat.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.5F, 1.5F);
            }
        }
    }
}
