package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.List;

public class ItemBroom extends Item {

    public ItemBroom() {
        this.setMaxStackSize(1);
        this.setRegistryName("item_broom");
        this.setTranslationKey("item_broom");
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        if (world.isRemote) return;
        if (!(entity instanceof EntityPlayer)) return;
        if (!isSelected) return;

        EntityPlayer player = (EntityPlayer) entity;
        double radius = 3.0D;

        List<EntityItem> items = world.getEntitiesWithinAABB(
                EntityItem.class,
                player.getEntityBoundingBox().grow(radius)
        );

        for (EntityItem item : items) {

            double dx = item.posX - player.posX;
            double dz = item.posZ - player.posZ;

            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance == 0) continue;

            double force = 0.1D;

            dx /= distance;
            dz /= distance;

            item.motionX += dx * force;
            item.motionZ += dz * force;

            item.velocityChanged = true;
        }
    }
}
