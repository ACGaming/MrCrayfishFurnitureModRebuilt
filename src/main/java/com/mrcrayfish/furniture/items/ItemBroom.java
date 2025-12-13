package com.mrcrayfish.furniture.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;

public class ItemBroom extends ItemGeneric
{
    public ItemBroom()
    {
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
    {
        if (world.isRemote || !isSelected || world.getTotalWorldTime() % 5 != 0) return;

        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, entity.getEntityBoundingBox().grow(3.0D));
        for (EntityItem item : items)
        {
            double dx = item.posX - entity.posX;
            double dz = item.posZ - entity.posZ;

            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance == 0) continue;

            double force = 0.5D;

            dx /= distance;
            dz /= distance;

            item.motionX += dx * force;
            item.motionZ += dz * force;

            item.velocityChanged = true;
        }

        if (!items.isEmpty())
        {
            world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.BLOCK_SAND_PLACE, SoundCategory.PLAYERS, 1.5F, 0.75F);
        }
    }
}
