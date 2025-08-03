package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.containers.ContainerComputer;
import com.mrcrayfish.furniture.init.FurnitureSounds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;

public class TileEntityComputer extends TileEntityFurniture
{
    private int stockNum = 0;
    private boolean isTrading = false;

    public TileEntityComputer()
    {
        super("computer", 1);
    }

    public void takeEmeraldFromSlot(int price)
    {
        this.getStackInSlot(0).shrink(price);
        this.markDirty();
    }

    public void setBrowsingInfo(int stockNum)
    {
        if(stockNum >= 0 && stockNum < Recipes.getMineBayItems().length)
        {
            this.stockNum = stockNum;
        }
    }

    public int getBrowsingInfo()
    {
        return stockNum;
    }

    public void setTrading(boolean isUsing)
    {
        this.isTrading = isUsing;
    }

    public boolean isTrading()
    {
        return isTrading;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.stockNum = tagCompound.getInteger("StockNum");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("StockNum", stockNum);
        return tagCompound;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        setTrading(true);
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.computer, SoundCategory.BLOCKS, 1.0F, 1.0F, true);
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        setTrading(false);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerComputer(playerInventory, this);
    }
}
