package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class TileEntityBowlCatFood extends TileEntity implements ITickable {

    private int feedCounter = 0;
    private static final int MAX_FEED = 3;

    @Override
    public void update() {
        if (!world.isRemote) {
            if (world.getTotalWorldTime() % 20 == 0) {
                feedNearbyCats();
            }
        }
    }

    private void feedNearbyCats() {
        if (feedCounter >= MAX_FEED) {
            world.setBlockState(pos, FurnitureBlocks.BOWL.getDefaultState());
            return;
        }

        AxisAlignedBB area = new AxisAlignedBB(pos).grow(1.0D, 1.0D, 1.0D);
        List<EntityOcelot> cats = world.getEntitiesWithinAABB(EntityOcelot.class, area);

        if (!cats.isEmpty()) {
            for (EntityOcelot cat : cats) {
                if (cat.getHealth() < cat.getMaxHealth()) {
                    cat.heal(4.0F);
                    world.playSound(null, cat.getPosition(), SoundEvents.ENTITY_GENERIC_EAT,
                            SoundCategory.PLAYERS, 1F, 1.5F);
                    feedCounter++;
                    break;
                }
            }
        }
    }
}
