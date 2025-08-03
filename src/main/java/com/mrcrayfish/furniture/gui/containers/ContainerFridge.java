package com.mrcrayfish.furniture.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFridge extends Container
{
    private IInventory lowerChestInventory;

    public ContainerFridge(IInventory playerInventory, IInventory fridgeInventory)
    {
        this.lowerChestInventory = fridgeInventory;
        this.lowerChestInventory.openInventory(null);

        int count = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                this.addSlotToContainer(new Slot(fridgeInventory, count, j * 18 + 44, i * 18 + 18));
                count++;
            }
        }

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 85));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, i * 18 + 8, 143));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.lowerChestInventory.isUsableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = ItemStack.EMPTY;
        Slot var4 = this.inventorySlots.get(par2);

        if(var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if(par2 < 16)
            {
                if(!this.mergeItemStack(var5, 16, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(var5, 0, 16, false))
            {
                return ItemStack.EMPTY;
            }

            if(var5.getCount() == 0)
            {
                var4.putStack(ItemStack.EMPTY);
            }
            else
            {
                var4.onSlotChanged();
            }
        }

        return var3;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        this.lowerChestInventory.closeInventory(par1EntityPlayer);
    }
}
