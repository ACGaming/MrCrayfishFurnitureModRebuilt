package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import com.mrcrayfish.furniture.gui.slots.SlotNonInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;

public class ContainerEnvelope extends ContainerItemInventory
{
    private ItemInventory itemInventory;

    public ContainerEnvelope(IInventory playerInventory, ItemInventory itemInventory)
    {
        this.itemInventory = itemInventory;

        this.addSlotToContainer(createEnvelopeSlot(itemInventory, 0, 8 + 63, 18));
        this.addSlotToContainer(createEnvelopeSlot(itemInventory, 1, 8 + 81, 18));

        this.addPlayerInventory(playerInventory);
    }

    private Slot createEnvelopeSlot(ItemInventory inv, int index, int x, int y)
    {
        return new SlotNonInventory(inv, index, x, y)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return isAllowed(stack);
            }
        };
    }

    private boolean isAllowed(ItemStack stack)
    {
        if (stack == null || stack.isEmpty())
            return false;

        return stack.getItem() == Items.PAPER
                || stack.getItem() == Items.BOOK
                || stack.getItem() == Items.WRITABLE_BOOK
                || stack.getItem() == Items.WRITTEN_BOOK
                || stack.getItem() == Items.ENCHANTED_BOOK
                || isCFMItem(stack, "item_coin")
                || isCFMItem(stack, "item_recipe_book");
    }

    private boolean isCFMItem(ItemStack stack, String name)
    {
        return stack.getItem().getRegistryName() != null
                && stack.getItem().getRegistryName().toString().equals("cfm:" + name);
    }

    @Override
    public ItemInventory getItemInventory()
    {
        return itemInventory;
    }
}
