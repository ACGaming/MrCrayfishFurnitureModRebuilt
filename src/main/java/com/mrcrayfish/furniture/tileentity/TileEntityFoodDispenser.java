package com.mrcrayfish.furniture.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

import com.mrcrayfish.furniture.gui.containers.ContainerBedsideCabinet;

public class TileEntityFoodDispenser extends TileEntityFurniture
{
    public TileEntityFoodDispenser()
    {
        super("food_dispenser", 9);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
    {
        this.fillWithLoot(player);
        return new ContainerChest(playerInventory, this, player);
    }
}
