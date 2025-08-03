package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.gui.containers.ContainerFridge;
import com.mrcrayfish.furniture.init.FurnitureSounds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;

public class TileEntityFridge extends TileEntityFurniture implements ISidedInventory
{
    private static final int[] slots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    public TileEntityFridge()
    {
        super("fridge", 15);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerFridge(playerInventory, this);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return slots;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
    {
        return !isLocked();
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return !isLocked();
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.fridge_open, SoundCategory.BLOCKS, 1.0F, 1.0F, true);
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.fridge_close, SoundCategory.BLOCKS, 1.0F, 1.0F, true);
    }
}
